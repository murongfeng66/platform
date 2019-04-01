const Table = {};

const TableCache = {};
(function () {
    Table.query = function (option) {
        let o = {
            tableId: option.tableId,
            url: option.url,
            queryParam: option.queryParam || {},
            column: option.column
        };

        if (TableCache[o.tableId]) {

        } else {
            if (!option.tableId) {
                throw '列表ID为空';
            }
            if (!option.url) {
                throw '列表URL为空';
            }
            if (!option.column || option.column.length === 0) {
                throw '列表列为空';
            }

            let tableCache = {
                option: o,
                data: {list: [], currentPage: 1, totalPage: 0},
                _tableDivHtml: null,
                _tBodyHtml: null,
                _tPageHtml: null,
                _pageHtmlList: {}
            };

            initList.call(tableCache);
            TableCache[tableCache.option.tableId] = tableCache;
        }
    };

    function initList() {
        initTable.call(this);

        requestData.call(this);
    }

    function initTable() {
        let _this = this;
        _this.option.queryParam.needCut = _this.option.queryParam.needCut ? _this.option.queryParam.needCut : 1;
        _this._tableDivHtml = document.getElementById(_this.option.tableId);

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
        htmlString += '<div class="table-page">';
        htmlString += '<div class="page-item" data-page="firstPage">&laquo;</div>';
        htmlString += '<div class="page-item" data-page="prePage">&lt;</div>';
        htmlString += '<div class="page-item" data-page="nextPage">&gt;</div>';
        htmlString += '<div class="page-item" data-page="lastPage">&raquo;</div>';
        htmlString += '</div>';

        _this._tableDivHtml.innerHTML = htmlString;

        _this._tBodyHtml = _this._tableDivHtml.querySelector('.table-body');
        _this._tPageHtml = _this._tableDivHtml.querySelector('.table-page');
        _this._pageHtmlList.firstPage = _this._tPageHtml.querySelector('[data-page=firstPage]');
        _this._pageHtmlList.firstPage.onclick = function(){
            gotoPage.call(_this);
        };
        _this._pageHtmlList.prePage = _this._tPageHtml.querySelector('[data-page=prePage]');
        _this._pageHtmlList.prePage.onclick = function(){
            gotoPage.call(_this);
        };
        _this._pageHtmlList.nextPage = _this._tPageHtml.querySelector('[data-page=nextPage]');
        _this._pageHtmlList.nextPage.onclick = function(){
            gotoPage.call(_this);
        };
        _this._pageHtmlList.lastPage = _this._tPageHtml.querySelector('[data-page=lastPage]');
        _this._pageHtmlList.lastPage.onclick = function(){
            gotoPage.call(_this);
        };
    }

    function requestData() {
        let _this = this;
        Request.get(_this.option.url, _this.option.queryParam, true).then(function (data) {

            let htmlString = '';
            data.data.forEach(function (rowItem, index) {
                _this.data.list[index] = rowItem;
                htmlString += '<tr class="opacity-0-1">';
                _this.option.column.forEach(function (columnItem) {
                    htmlString += '<td>';
                    htmlString += common.string.checkEmpty(rowItem[columnItem.name]);
                    htmlString += '</td>';
                });
                htmlString += '</tr>';
            });
            _this._tBodyHtml.innerHTML = htmlString;

            _this.data.currentPage = data.currentPage;
            _this.data.totalPage = data.totalPage;
            updatePage.call(_this);
        }, true);
    }

    function updatePage() {
        if(this.data.totalPage === 1){
            this._tPageHtml.hide();
            return
        }else{
            this._tPageHtml.show();
        }

        if (this.data.currentPage === 1) {
            this._pageHtmlList.firstPage.setAttribute('disabled', '');
            this._pageHtmlList.prePage.setAttribute('disabled', '');
        }else{
            this._pageHtmlList.firstPage.removeAttribute('disabled');
            this._pageHtmlList.prePage.removeAttribute('disabled');
        }

        if (this.data.currentPage === this.data.totalPage) {
            this._pageHtmlList.nextPage.setAttribute('disabled', '');
            this._pageHtmlList.lastPage.setAttribute('disabled', '');
        }else{
            this._pageHtmlList.nextPage.removeAttribute('disabled');
            this._pageHtmlList.lastPage.removeAttribute('disabled');
        }
    }

    function gotoPage(){
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
        } else {
            this.option.queryParam.currentPage = parseInt(page);
        }

        if (this.option.queryParam.currentPage < 1 || this.option.queryParam.currentPage > this.data.totalPage || this.option.queryParam.currentPage === this.data.currentPage) {
            return;
        }

        requestData.call(this);
    }
})();