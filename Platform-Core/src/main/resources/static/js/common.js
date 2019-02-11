var common = {};
common.token = null;

common.toast = {};
common.toast.info = function (message) {
    common.toast.show(message);
};
common.toast.error = function (message) {
    common.toast.show(message, 'error', 2500);
};
common.toast.show = function (message, type, time) {
    type = type || 'info';
    time = time || 2000;
    var active = "toast-active";
    var div = document.createElement("div");
    div.classList.add("toast-container");
    div.innerHTML = '<div class="toast-message-container toast-' + type + '">' + message + "</div>";
    div.addEventListener("webkitTransitionEnd", function () {
        div.classList.contains(active) || div.parentNode.removeChild(div);
    });
    document.body.appendChild(div);
    div.offsetHeight;
    div.classList.add(active);
    setTimeout(function () {
        div.classList.remove(active)
    }, time);
};

common.ajax = {};
common.ajax.get = function (url, data, success) {
    common.ajax.request(url, 'GET', data, success);
};
common.ajax.post = function (url, data, success) {
    common.ajax.request(url, 'POST', data, success);
};
common.ajax.request = function (url, type, data, success, async) {
    async = async || true;
    type = type || 'GET';
    var headers = {};
    if (common.token) {
        headers.Token = common.token;
    }
    $.ajax({
        url: url,
        async: async,
        type: type,
        data: data,
        dataType: 'JSON',
        headers: headers,
        success: function (data) {
            if (data.code === 1) {
                if (success && typeof success === 'function') {
                    success(data.data);
                } else {
                    common.toast.info(data.message);
                }
            } else {
                common.toast.error(data.message);
            }

            common.token = data.token || common.token;
        },
        error: function () {
            common.toast.error('请求失败');
        }
    });
};

common.keydown = {};
common.keydown.enter = function (id, callback) {
    $('#' + id).bind('keyup', function (e) {
        if (e.keyCode === 13 && callback && typeof callback === 'function') {
            callback();
        }
    })
};