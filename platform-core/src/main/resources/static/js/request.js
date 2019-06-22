const Request = {};
(function () {
    Request.get = function (o) {
        return request(createOption(o, 'GET'));
    };

    Request.post = function (o) {
        return request(createOption(o, 'POST'));
    };

    function createOption(o, type) {
        return {
            url: o.url,
            type: type,
            params: o.params,
            returnAll: o.returnAll === undefined ? false : o.returnAll,
            showMessage: o.showMessage === undefined ? true : o.showMessage,
            async: o.async === undefined ? true : o.async,
            success: o.success,
            fail: o.fail
        };
    }

    /**
     * @return {string}
     */
    Request.JsonToUrlParam = function (json) {
        if (!json) {
            return '';
        }
        let urlSearchParams = new URLSearchParams();
        json.forEach(function (key, value) {
            if (!key) {
                return;
            }
            urlSearchParams.set(key, value);
        });
        return urlSearchParams.toString();
    };

    Request.addParamToUrl = function (url, name, value) {
        if (!name) {
            return url;
        }
        let urls = url.split('?');
        let urlSearchParams = new URLSearchParams(urls[1]);
        urlSearchParams.set(name, value);
        return `${urls[0]}?${urlSearchParams.toString()}`;
    };

    function response(xhr, o) {
        let data;
        try {
            data = JSON.parse(xhr.responseText);
        } catch (e) {
            Toast.error('请求失败');
            console.error(`响应内容错误：${o.url}`);
            console.error(e);
        }

        let showMessage = o.showMessage !== false && data.message && '请求成功' !== data.message;

        if (data.code === 1) {
            if (showMessage) {
                Toast.info(data.message);
            }
            if (typeof o.success === 'function') {
                o.success(o.returnAll === true ? data : data.data);
            }
        } else {
            if (showMessage) {
                Toast.error(data.message);
            }
            if (typeof o.fail === 'function') {
                o.fail(o.returnAll === true ? data : data.data);
            }
        }

        if (data.redirect) {
            window.location.href = Request.addParamToUrl(data.redirect, 'originUrl', window.location.href);
        }
    }

    function requestSuccess(xhr, o) {
        if (!xhr.responseText && typeof o.success === 'function') {
            o.success();
        } else {
            let responseContentTypes = xhr.getResponseHeader('Content-Type').split(';');
            if (responseContentTypes.indexOf('application/json') !== -1) {
                response(xhr, o);
            } else {
                Toast.error('请求失败');
                console.error(`响应类型[${responseContentTypes}]不支持：${o.url}`);
            }
        }
    }

    function finish(xhr, o) {
        xhr.onload = function () {
            requestSuccess(xhr, o);
        };
        xhr.onerror = function () {
            Toast.error('请求失败');
            console.error(`请求失败：${o.url}`);
        };
    }

    function request(o) {
        let xhr = new XMLHttpRequest();
        if ('GET' === o.type && o.params) {
            let urls = o.url.split('?');
            let urlSearchParams = new URLSearchParams(urls[1]);
            o.params.forEach(function (key, value) {
                if (!key) {
                    return;
                }
                urlSearchParams.set(key, value);
            });
            o.url = `${urls[0]}?${urlSearchParams.toString()}`;
        }

        xhr.open(o.type, o.url, o.async);
        xhr.withCredentials = true;
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        xhr.setRequestHeader("x-requested-with", "XMLHttpRequest");

        finish(xhr, o);

        if ('POST' === o.type) {
            let paramStr = Request.JsonToUrlParam(o.params);
            xhr.send(paramStr);
        } else {
            xhr.send();
        }
    }
})();