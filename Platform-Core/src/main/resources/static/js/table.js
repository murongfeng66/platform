function Table() {
}

(function () {
    const TableHtmlCache = {};

    Table.init = function (o) {
        let option = {
            tableId: o.tableId,
            queryFormId: o.queryFormId,
            url: o.url,
            queryParam: o.queryParam || {},
            column: o.column,
            selectModel: o.selectModel || 'single',
            bottomButtons: o.bottomButtons || [],
            reloadButton: o.reloadButton || true,
            pageSizes: o.pageSizes || [15, 30, 45],
            rowClick: o.rowClick,
            columnClick: o.columnClick,
            rowOperate: o.rowOperate
        };

        if (!option.tableId) {
            throw 'ID为空';
        }

        let tableHtmlCache = TableHtmlCache[option.tableId];
        if (!tableHtmlCache) {
            if (!option.url) {
                throw 'URL为空';
            }
            if (!option.column || option.column.length === 0) {
                throw '列为空';
            }

            tableHtmlCache = init(option);
        }
        return tableHtmlCache.operator;
    };

    Table.prototype = {
        reload: function () {
            let tableHtmlCache = TableHtmlCache[this.tableId];
            if (!tableHtmlCache) {
                return;
            }
            requestData.call(tableHtmlCache);
        }
    };

    function init(option) {
        let tableHtmlCache = {
            option: option,
            operator: null,
            data: {list: [], currentPage: 1, totalPage: 0},
            _tableDivHtml: null,
            _tBodyHtml: null,
            _tPageHtml: null,
            _tableBottomInfoHtml: null,
            _pageHtmlList: {},
            _queryItemsHtml: [],
            operateButtons: []
        };

        if (tableHtmlCache.option.reloadButton) {
            tableHtmlCache.option.bottomButtons.unshift({
                title: '刷新',
                faClass: 'fa-refresh',
                onclick: requestData
            });
        }

        if (typeof tableHtmlCache.option.rowOperate === 'function') {
            tableHtmlCache.option.column.push({
                title: '操作',
                name: 'operate'
            });
        }

        initTable.call(tableHtmlCache);

        requestData.call(tableHtmlCache);

        TableHtmlCache[tableHtmlCache.option.tableId] = tableHtmlCache;

        tableHtmlCache.operator = new Table();
        tableHtmlCache.operator.tableId = tableHtmlCache.option.tableId;
        return tableHtmlCache;
    }

    function initTable() {
        this.option.queryParam.needCut = this.option.queryParam.needCut ? this.option.queryParam.needCut : 1;
        this._tableDivHtml = document.getElementById(this.option.tableId);

        createTable.call(this);

        this._tPageHtml = this._tableDivHtml.querySelector('.table-bottom-page');
        this._tableBottomInfoHtml = this._tableDivHtml.querySelector('.table-bottom-info');


        this._tBodyHtml = this._tableDivHtml.querySelector('.table-body');

        initPage.call(this);
        initBottom.call(this);

        let selector = '#' + this.option.queryFormId + ' [name]';
        this._queryItemsHtml = document.querySelectorAll(selector);

        bindBodyClick.call(this);
    }

    function createTable() {
        let _this = this;

        let htmlString = '<table class="table"><thead class="table-head">';
        htmlString += '<tr>';

        let tableRemainWidth = _this._tableDivHtml.clientWidth;
        let columnDefaultWidth = (tableRemainWidth / _this.option.column.length).toFixed(0);
        _this.option.column.forEach(function (item, index) {
            let width = item.width ? item.width : columnDefaultWidth;
            width = index === _this.option.column.length - 1 ? tableRemainWidth : width;
            tableRemainWidth -= width;
            _this.option.column[index].width = width;
            htmlString += '<th style="width: ' + width + 'px">' + item.title + '</th>';
        });
        htmlString += '</tr></thead>';
        htmlString += '<tbody class="table-body table-interval"></tbody></table>';

        htmlString += '<div class="table-bottom">';
        htmlString += '<span class="table-bottom-buttons"></span>';

        htmlString += '<div class="table-bottom-page">';
        htmlString += '<span class="page-item" data-page="firstPage">&laquo;</span>';
        htmlString += '<span class="page-item" data-page="prePage">&lt;</span>';
        htmlString += '<input type="number" class="page-item page-input" min="1">';
        htmlString += '<span class="page-item" data-page="nextPage">&gt;</span>';
        htmlString += '<span class="page-item" data-page="lastPage">&raquo;</span>';
        htmlString += '<select class="page-item page-size">';
        _this.option.pageSizes.forEach(function (pageSize) {
            htmlString += '<option value="' + pageSize + '">' + pageSize + '</option>';
        });
        htmlString += '</select>';
        htmlString += '</div>';

        htmlString += '<span class="table-bottom-info">';
        htmlString += '</span>';

        htmlString += '</div>';

        _this._tableDivHtml.innerHTML = htmlString;
    }

    function initPage() {
        let _this = this;

        _this._pageHtmlList.firstPage = _this._tPageHtml.querySelector('[data-page=firstPage]');
        _this._pageHtmlList.firstPage.onclick = function () {
            gotoPage.call(_this);
        };
        _this._pageHtmlList.prePage = _this._tPageHtml.querySelector('[data-page=prePage]');
        _this._pageHtmlList.prePage.onclick = function () {
            gotoPage.call(_this);
        };
        _this._pageHtmlList.nextPage = _this._tPageHtml.querySelector('[data-page=nextPage]');
        _this._pageHtmlList.nextPage.onclick = function () {
            gotoPage.call(_this);
        };
        _this._pageHtmlList.lastPage = _this._tPageHtml.querySelector('[data-page=lastPage]');
        _this._pageHtmlList.lastPage.onclick = function () {
            gotoPage.call(_this);
        };
        _this._pageHtmlList.input = _this._tPageHtml.querySelector('.page-input');
        _this._pageHtmlList.input.onkeydown = function () {
            if (window.event.keyCode === 13) {
                _this.option.queryParam.currentPage = this.value;
                gotoPage.call(_this);
            }
        };
        _this._pageHtmlList.size = _this._tPageHtml.querySelector('.page-size');
        _this._pageHtmlList.size.onchange = function () {
            _this.option.queryParam.pageSize = this.value;
            requestData.call(_this);
        };
    }

    function initBottom() {
        let _this = this;
        let _bottomButtons = _this._tableDivHtml.querySelector('.table-bottom-buttons');
        _this.option.bottomButtons.forEach(function (button) {
            let _buttonHtml = document.createElement('span');
            _buttonHtml.classList.add('table-bottom-button', 'margin-right-10');
            _buttonHtml.style.color = button.color;
            let htmlString = '<span class="margin-left-10 ' + button.classNames + '">';
            if (button.faClass) {
                htmlString += '<i class="fa ' + button.faClass + '"></i>';
            }
            htmlString += button.title + '</span>';
            _buttonHtml.innerHTML = htmlString;
            _buttonHtml.onclick = function () {
                if (typeof button.onclick === 'function') {
                    button.onclick.call(_this);
                }
            };
            _bottomButtons.appendChild(_buttonHtml);
        });
    }

    function bindBodyClick() {
        let _this = this;
        _this._tBodyHtml.onclick = function () {
            window.event.stopPropagation();
            let _target = window.event.target;

            let _tr;
            let _trResearch = _target.parentElement;
            while (true) {
                if(_trResearch.localName === 'tr'){
                    _tr = _trResearch;
                }
                if(_tr || _trResearch.classList.contains('table-body')){
                    break;
                }
                _trResearch = _trResearch.parentElement;
            }

            let selected = _tr.getAttribute('selected') !== null;

            if (_this.option.selectModel !== 'double') {
                _this._tBodyHtml.querySelectorAll('tr[selected]').forEach(function (_selectedItem) {
                    _selectedItem.removeAttribute('selected');
                });
            }

            if (selected) {
                _tr.removeAttribute('selected');
            } else {
                _tr.setAttribute('selected', '');
            }

            let rowIndex;
            let columnIndex;
            let currentHtml = _target;
            while (true) {
                rowIndex = currentHtml.getAttribute('data-rowIndex');
                columnIndex = currentHtml.getAttribute('data-columnIndex');

                if (rowIndex != null && columnIndex != null) {
                    break;
                }
                if (currentHtml.classList.contains('table-body')) {
                    break
                }
                currentHtml = currentHtml.parentElement;
            }


            let rowData = _this.data.list[rowIndex];

            if (typeof _this.option.rowClick === 'function') {
                _this.option.rowClick.call(rowData, rowData, rowIndex);
            }

            if (typeof _this.option.columnClick === 'function') {
                let columnName = _this.option.column[columnIndex].name;
                let columnData = rowData[columnName];
                _this.option.columnClick.call(rowData, columnData, columnIndex, columnName);
            }

            if (_target.classList.contains('table-row-operate')) {
                let buttonIndex = _target.getAttribute('data-buttonIndex');
                let button = _this.operateButtons[rowIndex][buttonIndex];
                if (button && typeof button.onclick === 'function') {
                    button.onclick.call(rowData);
                }
            }
        };
    }

    function requestData() {
        let _this = this;

        _this._queryItemsHtml.forEach(function (_item) {
            if (_item.value === '') {
                delete _this.option.queryParam[_item.name];
            } else {
                _this.option.queryParam[_item.name] = _item.value;
            }
        });

        _this.option.queryParam.pageSize = _this.option.queryParam.pageSize ? _this.option.queryParam.pageSize : _this._pageHtmlList.size.value;

        Request.get(_this.option.url, _this.option.queryParam, true).then(function (data) {
            let htmlString = '';
            data.data.forEach(function (rowItem, rowIndex) {
                _this.data.list[rowIndex] = rowItem;
                _this.operateButtons[rowIndex] = [];

                htmlString += '<tr class="opacity-0-1" data-rowIndex="' + rowIndex + '">';

                _this.option.column.forEach(function (columnItem, columnIndex) {
                    htmlString += '<td data-rowIndex="' + rowIndex + '" data-columnIndex="' + columnIndex + '">';
                    if (columnItem.name === 'operate' && typeof _this.option.rowOperate === 'function') {
                        let buttons = _this.option.rowOperate.call(rowItem);
                        buttons.forEach(function (button, index) {
                            _this.operateButtons[rowIndex][index] = button;

                            htmlString += '<span class="table-row-operate margin-left-10 ' + button.classNames + '" data-buttonIndex="' + index + '">';
                            if (button.faClass) {
                                htmlString += '<i class="fa ' + button.faClass + '"></i>';
                            }
                            htmlString += button.title + '</span>';
                        });
                    } else {
                        htmlString += common.string.dealEmpty(rowItem[columnItem.name]);
                    }
                    htmlString += '</td>';
                });

                htmlString += '</tr>';
            });
            _this._tBodyHtml.innerHTML = htmlString;

            _this.data.currentPage = data.currentPage;
            _this.data.totalPage = data.totalPage;
            _this.data.totalCount = data.totalCount;
            _this.data.pageSize = data.pageSize;
            updatePage.call(_this);
        }, true);
    }

    function updatePage() {
        this._tableBottomInfoHtml.innerHTML = this.data.totalPage + ' / ' + this.data.totalCount;

        this._pageHtmlList.input.value = this.data.currentPage;
        this._pageHtmlList.input.setAttribute('max', this.data.totalPage);

        if (this.data.currentPage === 1) {
            this._pageHtmlList.firstPage.setAttribute('disabled', '');
            this._pageHtmlList.prePage.setAttribute('disabled', '');
        } else {
            this._pageHtmlList.firstPage.removeAttribute('disabled');
            this._pageHtmlList.prePage.removeAttribute('disabled');
        }

        if (this.data.currentPage >= this.data.totalPage) {
            this._pageHtmlList.nextPage.setAttribute('disabled', '');
            this._pageHtmlList.lastPage.setAttribute('disabled', '');
        } else {
            this._pageHtmlList.nextPage.removeAttribute('disabled');
            this._pageHtmlList.lastPage.removeAttribute('disabled');
        }
    }

    function gotoPage() {
        let _liHtml = window.event.target;
        let page = _liHtml.getAttribute('data-page');

        if (page === 'firstPage') {
            this.option.queryParam.currentPage = 1;
        } else if (page === 'lastPage') {
            this.option.queryParam.currentPage = this.data.totalPage;
        } else if (page === 'prePage') {
            this.option.queryParam.currentPage = this.data.currentPage - 1;
        } else if (page === 'nextPage') {
            this.option.queryParam.currentPage = this.data.currentPage + 1;
        }

        if (this.option.queryParam.currentPage === this.data.currentPage) {
            return;
        }

        this.option.queryParam.currentPage = this.option.queryParam.currentPage < 1 ? 1 : this.option.queryParam.currentPage;
        this.option.queryParam.currentPage = this.option.queryParam.currentPage > this.data.totalPage ? this.data.totalPage : this.option.queryParam.currentPage;

        requestData.call(this);
    }
})();