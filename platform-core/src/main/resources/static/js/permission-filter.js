function PermissionFilter() {
}

(() => {
    function init() {
        if (typeof PermissionFilter.codes !== 'undefined') {
            return;
        }
        let codes = sessionStorage.getItem('resourceUrls');
        if (codes) {
            PermissionFilter.codes = codes.split(',');
            return;
        }
        Request.get({
            url: '/getPermissionCodes',
            async: false,
            success:function(data){
                PermissionFilter.codes = data;
            }
        });
    }

    PermissionFilter.filter = function () {
        document.querySelectorAll('[data-permission]').forEach(function ($item) {
            let url = $item.getAttribute('data-permission');
            if (PermissionFilter.check(url)) {
                $item.show();
            } else {
                $item.hide();
            }
        });
    };
    PermissionFilter.check = function (code) {
        if (!code) {
            return true;
        }
        init();
        if (PermissionFilter.codes) {
            return PermissionFilter.codes.indexOf(code) > 0;
        }
        return false;
    }
})();