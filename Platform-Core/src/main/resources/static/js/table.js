function Table() {
}

const TableCache = {};
(() => {
    Table.init = function(o)  {
        let option = {
            tableDivId: o.tableDivId,
            queryFormId: o.queryFormId,
            url: o.url,
            queryParam: o.queryParam || {},
            defaultQueryParam: o.defaultQueryParam,
            column: o.column,
            selectModel: o.selectModel || 'single',
            bottomButtons: o.bottomButtons || [],
            reloadButton: o.reloadButton || true,
            pageSizes: o.pageSizes || [15, 30, 45],
            rowClick: o.rowClick,
            rowDbClick: o.rowDbClick,
            columnClick: o.columnClick,
            rowOperate: o.rowOperate
        };

        if (!option.tableDivId) {
            throw 'ID为空';
        }

        let tableCache = TableCache[option.tableDivId];
        if (tableCache) {
            requestData.call(tableCache);
        } else {
            if (!option.column || option.column.length === 0) {
                throw '列为空';
            }

            tableCache = init(option);
        }
        return tableCache;
    };

    Table.reload = function(tableId){
        let tableCache = TableCache[tableId];
        if (tableCache) {
            tableCache.reload();
        }
    };

    Table.prototype = {
        reload: function (param1, param2) {
            let tableCache = TableCache[this.tableDivId];
            if (!tableCache) {
                return;
            }
            if (param1) {
                if (typeof param1 === 'string') {
                    tableCache.option.url = param1;
                } else if (typeof param1 === 'object') {
                    updateDefaultQueryParam.call(param1);
                }
            }
            if (param2) {
                if (param2 === 'string') {
                    tableCache.option.url = param2;
                } else if (typeof param2 === 'object') {
                    this.updateDefaultQueryParam(param2);
                }
            }
            requestData.call(tableCache);
        },
        updateDefaultQueryParam: function (param) {
            if (this.option.defaultQueryParam) {
                this.option.defaultQueryParam.forEach((item, index) => {
                    delete this.option.queryParam[index];
                });
            }

            this.option.defaultQueryParam = param;
            if (this.option.defaultQueryParam) {
                this.option.defaultQueryParam.forEach((item, index) => {
                    if (item) {
                        this.option.queryParam[index] = item;
                    } else {
                        delete this.option.queryParam[index];
                    }
                });
            }
        }
    };

    function init(option) {
        let table = new Table();
        table.option = option;
        table.tableDivId = option.tableDivId;

        table.data = {list: [], currentPage: 1, totalPage: 0};
        table.tableId = table.tableDivId + '_table';
        table.tableHeadId = table.tableId + '_head';
        table.tableBodyId = table.tableId + '_body';
        table.tableBottomId = table.tableId + '_bottom';
        table.tableBottomPageId = table.tableBottomId + '_page';
        table.tableBottomPageFirstId = table.tableBottomPageId + '_first';
        table.tableBottomPagePreId = table.tableBottomPageId + '_pre';
        table.tableBottomPagePageId = table.tableBottomPageId + '_page';
        table.tableBottomPageNextId = table.tableBottomPageId + '_next';
        table.tableBottomPageLastId = table.tableBottomPageId + '_last';
        table.tableBottomPageSizeId = table.tableBottomPageId + '_size';
        table.tableBottomInfoId = table.tableBottomId + '_info';
        table.operateButtons = [];

        if (table.option.reloadButton) {
            table.option.bottomButtons.unshift({
                title: '刷新',
                faClass: 'fa-refresh',
                classNames:'margin-right-10',
                onclick: requestData
            });
        }

        if (typeof table.option.rowOperate === 'function') {
            table.option.column.push({
                title: '操作',
                name: 'operate'
            });
        }

        initTable.call(table);

        requestData.call(table);

        TableCache[table.option.tableDivId] = table;

        return table;
    }

    function initTable() {
        this.option.queryParam.needCut = this.option.queryParam.needCut ? this.option.queryParam.needCut : 1;
        createTable.call(this);

        let $tableBodyHtml = document.getElementById(this.tableBodyId);
        $tableBodyHtml.onclick = () => {
            bodyClick.call(this);
        };
        $tableBodyHtml.ondblclick = () => {
            bodyDbClick.call(this);
        };

        bindPageEvent.call(this);
        initBottom.call(this);
    }

    function createTable() {
        let $tableDivHtml = document.getElementById(this.tableDivId);
        let averageColumnNum = this.option.column.length;
        let tableRemainWidth = $tableDivHtml.clientWidth;
        this.option.column.forEach((item) => {
            if (item.width) {
                averageColumnNum--;
                tableRemainWidth -= item.width;
            }
        });
        let columnDefaultWidth = (tableRemainWidth / averageColumnNum / $tableDivHtml.clientWidth * 100).toFixed(2);

        let htmlString = '<table id="' + this.tableId + '" class="table"><thead id="' + this.tableHeadId + '" class="table-head">';
        htmlString += '<tr>';

        this.option.column.forEach((item) => {
            item.width = item.width ? (item.width / $tableDivHtml.clientWidth * 100).toFixed(2) : columnDefaultWidth;

            htmlString += '<th style="width: ' + item.width + '%">' + item.title + '</th>';
        });
        htmlString += '</tr></thead>';

        htmlString += '<tbody id="' + this.tableBodyId + '" class="table-body table-interval">';
        htmlString += '<tr id="' + this.tableBodyId + '_row_empty" class="opacity-0-1"><td class="text-center table-empty" colspan="' + this.option.column.length + '">暂无数据</td></tr>';
        htmlString += '</tbody></table>';

        htmlString += '<div id="' + this.tableBottomId + '" class="table-bottom">';
        htmlString += '<div id="' + this.tableBottomPageId + '" class="table-bottom-page">';
        htmlString += '<span id="' + this.tableBottomPageFirstId + '" class="page-item" data-page="firstPage" disabled>&laquo;</span>';
        htmlString += '<span id="' + this.tableBottomPagePreId + '" class="page-item" data-page="prePage" disabled>&lt;</span>';
        htmlString += '<input id="' + this.tableBottomPagePageId + '" type="number" class="page-item page-input" min="1">';
        htmlString += '<span id="' + this.tableBottomPageNextId + '" class="page-item" data-page="nextPage" disabled>&gt;</span>';
        htmlString += '<span id="' + this.tableBottomPageLastId + '" class="page-item" data-page="lastPage" disabled>&raquo;</span>';
        htmlString += '<select id="' + this.tableBottomPageSizeId + '" class="page-item page-size">';
        this.option.pageSizes.forEach((pageSize) => {
            htmlString += '<option value="' + pageSize + '">' + pageSize + '</option>';
        });
        htmlString += '</select>';
        htmlString += '</div>';

        htmlString += '<span id="' + this.tableBottomInfoId + '" class="table-bottom-info">';
        htmlString += '</span>';

        htmlString += '</div>';

        $tableDivHtml.innerHTML = htmlString;
    }

    function bindPageEvent() {
        document.getElementById(this.tableBottomPageFirstId).onclick = () => {
            gotoPage.call(this);
        };
        document.getElementById(this.tableBottomPagePreId).onclick = () => {
            gotoPage.call(this);
        };
        document.getElementById(this.tableBottomPageNextId).onclick = () => {
            gotoPage.call(this);
        };
        document.getElementById(this.tableBottomPageLastId).onclick = () => {
            gotoPage.call(this);
        };
        document.getElementById(this.tableBottomPagePageId).onkeydown = () => {
            if (window.event.keyCode === 13) {
                this.option.queryParam.currentPage = this.value;
                gotoPage.call(this);
            }
        };
        document.getElementById(this.tableBottomPageSizeId).onchange = () => {
            this.option.queryParam.pageSize = this.value;
            requestData.call(this);
        };
    }

    function initBottom() {
        let $bottomButtonsHtml = document.createElement('span');
        $bottomButtonsHtml.classList.add('table-bottom-buttons');
        this.option.bottomButtons.forEach((button) => {
            let $buttonHtml = document.createElement('span');
            $buttonHtml.classList.add('table-bottom-button');
            if (button.classNames) {
                $buttonHtml.classList.add(button.classNames);
            }
            $buttonHtml.style.color = button.color;
            let htmlString = '';
            if (button.faClass) {
                htmlString += '<i class="fa ' + button.faClass + '"></i>';
            }
            htmlString += button.title;
            $buttonHtml.innerHTML = htmlString;
            $buttonHtml.onclick = () => {
                if (typeof button.onclick === 'function') {
                    button.onclick.call(this);
                }
            };
            $bottomButtonsHtml.appendChild($buttonHtml);
        });

        document.getElementById(this.tableBottomId).insertBefore($bottomButtonsHtml, document.getElementById(this.tableBottomPageId));
    }

    function bodyClick() {
        window.event.stopPropagation();

        let data = getClickData.call(this);

        if (this.option.selectModel !== 'double') {
            document.getElementById(this.tableBodyId).querySelectorAll('tr[selected]').forEach(($selectedItem) => {
                $selectedItem.removeAttribute('selected');
            });
        }

        if (data.selected) {
            data.$tr.removeAttribute('selected');
        } else {
            data.$tr.setAttribute('selected', '');
        }

        if (typeof this.option.rowClick === 'function') {
            this.option.rowClick.call(data.rowData, data.rowData, data.rowIndex);
        }

        if (typeof this.option.columnClick === 'function') {
            let columnName = this.option.column[data.columnIndex].name;
            let columnData = rowData[columnName];
            this.option.columnClick.call(data.rowData, columnData, data.columnIndex, columnName);
        }

        if (data.$target.classList.contains('table-row-operate')) {
            let buttonIndex = data.$target.getAttribute('data-buttonIndex');
            let button = this.operateButtons[data.rowIndex][buttonIndex];
            if (button && typeof button.onclick === 'function') {
                button.onclick.call(data.rowData);
            }
        }
    }

    function getClickData() {
        let data = {
            $tr: null,
            $target: null,
            selected: false,
            rowIndex: null,
            columnIndex: null,
            rowData: null
        };

        data.$target = window.event.target;
        let $trResearch = data.$target.parentElement;
        while (true) {
            if ($trResearch.localName === 'tr') {
                data.$tr = $trResearch;
            }
            if (data.$tr || $trResearch.classList.contains('table-body')) {
                break;
            }
            $trResearch = $trResearch.parentElement;
        }

        data.selected = data.$tr.getAttribute('selected') !== null;

        let currentHtml = data.$target;
        while (true) {
            data.rowIndex = currentHtml.getAttribute('data-rowIndex');
            data.columnIndex = currentHtml.getAttribute('data-columnIndex');

            if (data.rowIndex != null && data.columnIndex != null) {
                break;
            }
            if (currentHtml.classList.contains('table-body')) {
                break
            }
            currentHtml = currentHtml.parentElement;
        }

        data.rowData = this.data.list[data.rowIndex];
        return data;
    }

    function bodyDbClick() {
        window.event.stopPropagation();

        if (typeof this.option.rowDbClick === 'function') {
            let data = getClickData.call(this);
            this.option.rowDbClick.call(data.rowData, data.rowData, data.rowIndex);
        }
    }

    function requestData() {
        if (!this.option.url) {
            return;
        }

        if (this.option.queryFormId) {
            document.getElementById(this.option.queryFormId).querySelectorAll('[name]').forEach(($item) => {
                if ($item.value === '') {
                    delete this.option.queryParam[$item.name];
                } else {
                    this.option.queryParam[$item.name] = $item.value;
                }
            });
        }

        this.option.queryParam.pageSize = this.option.queryParam.pageSize ? this.option.queryParam.pageSize : document.getElementById(this.tableBottomPageSizeId).value;

        Request.get(this.option.url, this.option.queryParam, true).then((data) => {
            let htmlString = '';
            if (data.totalCount === 0) {
                htmlString += '<tr id="' + this.tableBodyId + 'row_empty" class="opacity-0-1"><td class="text-center table-empty" colspan="' + this.option.column.length + '">暂无数据</td></tr>';
            } else {
                data.data.forEach((rowItem, rowIndex) => {
                    this.data.list[rowIndex] = rowItem;
                    this.operateButtons[rowIndex] = [];

                    htmlString += '<tr id="' + this.tableBodyId + '_row_' + rowIndex + '" class="opacity-0-1" data-rowIndex="' + rowIndex + '">';

                    this.option.column.forEach((columnItem, columnIndex) => {
                        htmlString += '<td data-rowIndex="' + rowIndex + '" data-columnIndex="' + columnIndex + '">';
                        if (columnItem.name === 'operate' && typeof this.option.rowOperate === 'function') {
                            let buttons = this.option.rowOperate.call(rowItem);
                            buttons.forEach((button, index) => {
                                this.operateButtons[rowIndex][index] = button;

                                htmlString += '<span class="table-row-operate ' + button.classNames + '" data-buttonIndex="' + index + '">';
                                if (button.faClass) {
                                    htmlString += '<i class="fa ' + button.faClass + '"></i>';
                                }
                                htmlString += button.title + '</span>';
                            });
                        } else {
                            if (columnItem.formatter && typeof columnItem.formatter === 'function') {
                                htmlString += common.string.dealEmpty(columnItem.formatter.call(rowItem, rowItem[columnItem.name]));
                            } else {
                                htmlString += common.string.dealEmpty(rowItem[columnItem.name]);
                            }
                        }
                        htmlString += '</td>';
                    });

                    htmlString += '</tr>';
                });
            }
            document.getElementById(this.tableBodyId).innerHTML = htmlString;

            this.data.currentPage = data.currentPage;
            this.data.totalPage = data.totalPage;
            this.data.totalCount = data.totalCount;
            this.data.pageSize = data.pageSize;
            updatePage.call(this);
        }, true);
    }

    function updatePage() {
        document.getElementById(this.tableBottomInfoId).innerHTML = '共' + this.data.totalPage + '页  ' + this.data.totalCount + '行';

        let $pageInput = document.getElementById(this.tableBottomPagePageId);
        $pageInput.value = this.data.currentPage;
        $pageInput.setAttribute('max', this.data.totalPage);

        let $firstHtml = document.getElementById(this.tableBottomPageFirstId);
        let $preHtml = document.getElementById(this.tableBottomPagePreId);
        if (this.data.currentPage === 1) {
            $firstHtml.setAttribute('disabled', '');
            $preHtml.setAttribute('disabled', '');
        } else {
            $firstHtml.removeAttribute('disabled');
            $preHtml.removeAttribute('disabled');
        }

        let $nextHtml = document.getElementById(this.tableBottomPageNextId);
        let $lastHtml = document.getElementById(this.tableBottomPageLastId);
        if (this.data.currentPage >= this.data.totalPage) {
            $nextHtml.setAttribute('disabled', '');
            $lastHtml.setAttribute('disabled', '');
        } else {
            $nextHtml.removeAttribute('disabled');
            $lastHtml.removeAttribute('disabled');
        }
    }

    function gotoPage() {
        let $liHtml = window.event.target;
        let page = $liHtml.getAttribute('data-page');

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