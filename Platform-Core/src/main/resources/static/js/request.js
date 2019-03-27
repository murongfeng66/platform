const Request = {};
(function () {
    Request.get = function (url, params) {
        return request(url, 'GET', params);
    };

    Request.post = function (url, params) {
        return request(url, 'POST', params);
    };

    /**
     * @return {string}
     */
    Request.JsonToUrlParam = function (json) {
        let urlParam = '';
        for (let name in json) {
            if (!json.hasOwnProperty(name)) {
                continue;
            }
            urlParam += name + '=' + json[name] + '&';
        }
        return urlParam.endsWith('&') ? urlParam.substr(0, urlParam.length - 1) : urlParam;
    };

    Request.addParamToUrl = function (url, name, value) {
        if (url.indexOf('?') > 0) {
            url += '&';
        } else {
            url += '?';
        }
        if (name) {
            url += name + '=';
        }
        url += value;
        return url;
    };

    function request(url, type, params) {
        return new Promise(function (resolve) {
            let xhr = new XMLHttpRequest();
            let paramStr = Request.JsonToUrlParam(params);
            if ('GET' === type) {
                url = Request.addParamToUrl(url, null, paramStr);
            }
            xhr.open(type, url, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            xhr.setRequestHeader("x-requested-with", "XMLHttpRequest");

            if ('POST' === type) {
                xhr.send(paramStr);
            } else {
                xhr.send();
            }

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        if (!xhr.responseText) {
                            resolve();
                        } else {
                            let responseContentTypes = xhr.getResponseHeader('Content-Type').split(';');
                            if (responseContentTypes.indexOf('application/json') !== -1) {
                                try {
                                    let data = JSON.parse(xhr.responseText);
                                    if (data.code === 1) {
                                        resolve(data.data);
                                    } else {
                                        common.toast.error(data.message);
                                    }
                                    if (data.redirect) {
                                        window.location.href = data.redirect;
                                    }
                                } catch (e) {
                                    common.toast.error('请求失败');
                                    console.error('响应内容错误：' + url);
                                    console.error(e);
                                }
                            } else {
                                common.toast.error('请求失败');
                                console.error('响应类型[' + responseContentTypes + ']不支持：' + url);
                            }
                        }
                    } else {
                        common.toast.error('请求失败');
                        console.error('请求失败：' + url);
                    }
                }
            }
        })
    }
})();