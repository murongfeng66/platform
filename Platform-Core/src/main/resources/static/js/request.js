const Request = {};
(function(){
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

    function request(url, type, params){
        return new Promise(function (resolve) {
            let xhr = new XMLHttpRequest();
            xhr.open(type, url, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            xhr.setRequestHeader("x-requested-with", "XMLHttpRequest");

            let formData = new FormData();
            for (let paramName in params) {
                if (!params.hasOwnProperty(paramName)) {
                    continue;
                }
                formData.append(paramName, params[paramName]);
            }

            xhr.send(Request.JsonToUrlParam(params));

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        if (!xhr.responseText) {
                            resolve();
                        } else {
                            let responseContentTypes = xhr.getResponseHeader('Content-Type').split(';');
                            if (responseContentTypes.indexOf('application/json')!==-1) {
                                try {
                                    let data = JSON.parse(xhr.responseText);
                                    if(data.code === 1){
                                        resolve(data.data);
                                    }else{
                                        common.toast.error(data.message);
                                    }
                                } catch (e) {
                                    common.toast.error('响应内容错误');
                                }
                            } else {
                                common.toast.error('响应类型不支持');
                            }
                        }
                    } else {
                        common.toast.error('请求失败');
                    }
                }
            }
        })
    }
})();