const common = {};

const Toast = {};
Toast.info = function (message) {
    Toast.show(message);
};
Toast.error = function (message) {
    Toast.show(message, 'error', 2500);
};
Toast.show = function (message, type, time) {
    type = type || 'info';
    time = time || 2000;
    let active = "toast-active";
    let div = document.createElement("div");
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

common.keydown = {};
common.keydown.enter = function (id) {
    return new Promise(function (resolve) {
        document.getElementById(id).onkeydown = function (ev) {
            if (ev.keyCode === 13) {
                resolve();
            }
        };
    });
};

common.string = {};
common.string.dealEmpty = function (str) {
    return str || '';
};

/**
 * 隐藏Html元素<br>
 * 添加.hide
 */
HTMLElement.prototype.hide = function () {
    this.classList.add('hide');
};

/**
 * 显示Html元素<br>
 * 移除.hide
 */
HTMLElement.prototype.show = function () {
    this.classList.remove('hide');
};

/**
 * 遍历Object对象中的数据
 */
Object.prototype.forEach = function(itemFunction){
    if(typeof itemFunction !== 'function'){
        return;
    }
    for (let objectKey in this) {
        if(!this.hasOwnProperty(objectKey)){
            continue;
        }
        itemFunction.call(this, objectKey, this[objectKey]);
    }
};

/**
 * 初始化select元素
 * @param url 地址
 * @param addAddOption 是否添加【全部】选项
 */
HTMLElement.prototype.initSelect = function (url, addAddOption) {
    if (this.localName !== 'select') {
        return;
    }

    let _this = this;
    Request.get(url, null).then(function (data) {
        let htmlString = '';
        if(addAddOption){
            htmlString += '<option value="">全部</option>';
        }
        data.forEach(function(key, value){
            htmlString += '<option value="' + key + '">' + value + '</option>';
        });
        _this.innerHTML = htmlString;
    })
};

/**
 * 设置Html值<br>
 * 触发onchange事件
 * @param value 值
 */
HTMLElement.prototype.setValue = function(value){
    this.value = common.string.dealEmpty(value);
    if(this.fireEvent){
        this.fireEvent('onchange');
    }else{
        let event = document.createEvent('HTMLEvents');
        event.initEvent('change', false, true);
        this.dispatchEvent(event);
    }
};