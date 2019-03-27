const Table = {};

(function () {
    Table.query = function(option){
        initList(option);
    };
    function initList(option){
        let o = {
            tableId: option.tableId,
            url: option.url,
            queryParam: option.queryParam || {},
            column: option.column
        };

        if (!o.column || o.column.length === 0) {
            throw '列为空';
        }

        Request.get(o.url, o.queryParam).then(function (data) {
            let _tHeadHtml = document.createElement('thead');
            let _headTrHtml = document.createElement('tr');
            o.column.forEach(function(item){
                let _headTrThHtml = document.createElement('th');
                _headTrThHtml.innerHTML = common.string.checkEmpty(item.title);
                _headTrHtml.append(_headTrThHtml);
            });
            _tHeadHtml.append(_headTrHtml);

            let _tBodyHtml = document.createElement('tbody');
            data.forEach(function(rowItem){
                let _bodyTrHtml = document.createElement('tr');
                o.column.forEach(function(columnItem){
                    let _bodyTrTdHtml = document.createElement('td');
                    _bodyTrTdHtml.innerHTML = common.string.checkEmpty(rowItem[columnItem.name]);
                    _bodyTrHtml.append(_bodyTrTdHtml);
                });
                _tBodyHtml.append(_bodyTrHtml);
            });

            let _tableHtml = document.getElementById(o.tableId);
            _tableHtml.classList.add('table','table-hover');
            _tableHtml.append(_tHeadHtml);
            _tableHtml.append(_tBodyHtml);

            // let $page = updatePage(data.currentPage, data.totalPage);
            // _tableHtml.after($page);
        }, true)
    }
})();

// (function ($) {
//     function initList(option) {
//         var o = {
//             tableId: option.tableId,
//             url: option.url,
//             queryParam: option.queryParam || {},
//             column: option.column
//         };
//
//         if (!o.column || o.column.length === 0) {
//             throw 'column is empty';
//         }
//
//         common.ajax.get(o.url, o.queryParam, function (data) {
//             var $tHead = $('<thead></thead>');
//             var $headRow = $('<tr></tr>');
//             $.each(o.column, function (index, item) {
//                 $headRow.append('<td>' + common.string.checkEmpty(item.title) + '</td>');
//             });
//             $tHead.append($headRow);
//
//             var $tBody = $('<tbody></tbody>');
//             $.each(data.data, function (index, item) {
//                 var $row = $('<tr></tr>');
//                 $.each(o.column, function (columnIndex, columnItem) {
//                     $row.append('<td>' + common.string.checkEmpty(item[columnItem.name]) + '</td>');
//                 });
//                 $tBody.append($row);
//             });
//
//             var $table_body = $('#' + o.tableId);
//             $table_body.addClass('table table-hover');
//             $table_body.append($tHead);
//             $table_body.append($tBody);
//
//             var $page = updatePage(data.currentPage, data.totalPage);
//             $table_body.after($page);
//         }, true)
//     }
//
//     var updatePage = function(currentPage, totalPage){
//         if (totalPage > 1) {
//             var $page = $('<ul class="pagination"></ul>');
//             if (currentPage > 1) {
//                 var $prePage = $('<li class="page-item margin-auto margin-right-no"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>');
//                 $page.append($prePage);
//             }
//
//             for (var i = 1; i <= totalPage; i++) {
//                 var $pageItem = $('<li class="page-item"><a class="page-link" href="#">' + i + '</a></li>');
//
//                 if(i === 1){
//                     $pageItem.addClass('margin-auto margin-right-no');
//                 }
//
//                 if(i === totalPage){
//                     $pageItem.addClass('margin-auto margin-left-no');
//                 }
//
//                 if (i === currentPage) {
//                     $pageItem.addClass('disabled');
//                 }
//                 $page.append($pageItem);
//             }
//
//             if (currentPage < totalPage && totalPage > 3) {
//                 var $sufPage = $('<li class="page-item margin-auto margin-left-no"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>');
//                 $page.append($sufPage);
//             }
//             return $page;
//         }else{
//             return null;
//         }
//     };
//
//     $.extend({
//         queryList: initList
//     });
//
// })(jQuery);