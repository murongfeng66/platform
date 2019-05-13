const Request = {};
(function () {
    Request.get = function (url, params, returnAll, showMessage) {
        return request(url, 'GET', params, returnAll, showMessage);
    };

    Request.post = function (url, params, returnAll, showMessage) {
        return request(url, 'POST', params, returnAll, showMessage);
    };

    /**
     * @return {string}
     */
    Request.JsonToUrlParam = function (json) {
        if(!json){
            return '';
        }
        let urlParam = '';
        json.forEach(function(key, value){
            urlParam += key + '=' + value + '&';
        });
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

    function request(url, type, params, returnAll, showMessage) {
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
                                        if(showMessage !== false && data.message && '请求成功' !== data.message){
                                            Toast.info(data.message);
                                        }
                                        resolve(returnAll === true ? data : data.data);
                                    } else {
                                        Toast.error(data.message);
                                        if(returnAll === true){
                                            resolve(data);
                                        }
                                    }

                                    if (data.redirect) {
                                        window.location.href = data.redirect;
                                    }
                                } catch (e) {
                                    Toast.error('请求失败');
                                    console.error('响应内容错误：' + url);
                                    console.error(e);
                                }
                            } else {
                                Toast.error('请求失败');
                                console.error('响应类型[' + responseContentTypes + ']不支持：' + url);
                            }
                        }
                    } else {
                        Toast.error('请求失败');
                        console.error('请求失败：' + url);
                    }
                }
            }
        })
    }
})();