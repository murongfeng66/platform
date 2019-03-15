(function ($) {
    function initList(option) {
        var o = {
            tableId: option.tableId,
            url: option.url,
            queryParam: option.queryParam || {},
            column: option.column
        };

        if (!o.column || o.column.length === 0) {
            throw 'column is empty';
        }

        common.ajax.get(o.url, o.queryParam, function (data) {
            var $tHead = $('<thead></thead>');
            var $headRow = $('<tr></tr>');
            $.each(o.column, function (index, item) {
                $headRow.append('<td>' + common.string.checkEmpty(item.title) + '</td>');
            });
            $tHead.append($headRow);

            var $tBody = $('<tbody></tbody>');
            $.each(data.data, function (index, item) {
                var $row = $('<tr></tr>');
                $.each(o.column, function (columnIndex, columnItem) {
                    $row.append('<td>' + common.string.checkEmpty(item[columnItem.name]) + '</td>');
                });
                $tBody.append($row);
            });

            var $table_body = $('#' + o.tableId);
            $table_body.addClass('table table-hover');
            $table_body.append($tHead);
            $table_body.append($tBody);

            var $page = updatePage(data.currentPage, data.totalPage);
            $table_body.after($page);
        }, true)
    }

    var updatePage = function(currentPage, totalPage){
        if (totalPage > 1) {
            var $page = $('<ul class="pagination"></ul>');
            if (currentPage > 1) {
                var $prePage = $('<li class="page-item margin-auto margin-right-no"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a></li>');
                $page.append($prePage);
            }

            for (var i = 1; i <= totalPage; i++) {
                var $pageItem = $('<li class="page-item"><a class="page-link" href="#">' + i + '</a></li>');

                if(i === 1){
                    $pageItem.addClass('margin-auto margin-right-no');
                }

                if(i === totalPage){
                    $pageItem.addClass('margin-auto margin-left-no');
                }

                if (i === currentPage) {
                    $pageItem.addClass('disabled');
                }
                $page.append($pageItem);
            }

            if (currentPage < totalPage && totalPage > 3) {
                var $sufPage = $('<li class="page-item margin-auto margin-left-no"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span><span class="sr-only">Next</span></a></li>');
                $page.append($sufPage);
            }
            return $page;
        }else{
            return null;
        }
    };

    $.extend({
        queryList: initList
    });

})(jQuery);