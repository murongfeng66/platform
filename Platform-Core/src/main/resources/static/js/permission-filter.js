function PermissionFilter() {
}

(() => {
    function init(){
        if(typeof PermissionFilter.urls === 'undefined'){
            let urls = sessionStorage.getItem('resourceUrls');
            if (urls) {
                PermissionFilter.urls = urls.split(',');
            }
        }
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
    PermissionFilter.check = function(url){
        if(!url){
            return true;
        }
        init();
        if(PermissionFilter.urls){
            return PermissionFilter.urls.indexOf(url) > 0;
        }
        return false;
    }
})();