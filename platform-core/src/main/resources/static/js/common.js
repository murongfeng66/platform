const common = {};

loadJs('/js/request.js');
initHtml();
function initHtml() {
    removeFormDefaultEvent();
    if(document.querySelector('.form')){
        loadJs('/js/form.js');
        loadCss('/css/form.css');
    }
    if(document.querySelector('.dialog')){
        loadJs('/js/dialog.js');
        loadCss('/css/dialog.css');
    }
    if(document.querySelector('[data-table]')){
        loadJs('/js/table.js');
        loadCss('/css/table.css');
    }
    if(document.querySelector('input.date-picker')){
        loadJs('/js/date-picker.js').then(()=>{
            DatePicker.init();
        });
        loadCss('/css/date-picker.css');
    }
    if(document.querySelector('[data-permission]')){
        loadJs('/js/permission-filter.js').then(()=>{
            PermissionFilter.filter();
        });
    }
}

function loadJs(url) {
    return new Promise(resolve => {
        let $script = document.createElement('script');
        $script.type = 'text/javascript';
        if (typeof resolve !== 'undefined') {
            if ($script.readyState) {
                $script.onreadystatechange = function () {
                    if ($script.readyState === "loaded" || $script.readyState === "complete") {
                        $script.onreadystatechange = null;
                        resolve();
                    }
                }
            } else {
                $script.onload = function () {
                    resolve();
                }
            }
        }
        $script.src = url;
        document.body.appendChild($script);
    });
}

function loadCss(url) {
    return new Promise(resolve => {
        let $link = document.createElement('link');
        $link.rel = 'stylesheet';
        if (typeof resolve !== 'undefined') {
            if ($link.readyState) {
                $link.onreadystatechange = function () {
                    if ($link.readyState === "loaded" || $link.readyState === "complete") {
                        $link.onreadystatechange = null;
                        resolve();
                    }
                }
            } else {
                $link.onload = function () {
                    resolve();
                }
            }
        }
        $link.href = url;
        document.head.appendChild($link);
    });
}

function removeFormDefaultEvent(){
    document.querySelectorAll('form').forEach(function ($form) {
        $form.onsubmit = () => {
            window.event.preventDefault();
        };
    });
}

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

common.string = {};
common.string.dealEmpty = function (str) {
    return str || '';
};

Object.prototype.fixLength = function (length, char, position) {
    let str = this.toString();
    if (str.length === length) {
        return str;
    }
    if (str.length > length) {
        return str.substr(0, length);
    }
    let chars = '';
    for (let i = 0; i < length - this.length; i++) {
        chars += char;
    }
    if (position === 'prefix') {
        return chars + str;
    }
    return str + chars;
};

/**
 * 隐藏Html元素
 * 添加.hide
 */
HTMLElement.prototype.hide = function () {
    this.classList.add('hide');
};

/**
 * 显示Html元素
 * 移除.hide
 */
HTMLElement.prototype.show = function () {
    this.classList.remove('hide');
};

/**
 * 遍历Object对象中的数据
 */
Object.prototype.forEach = function (itemFunction) {
    if (typeof itemFunction !== 'function') {
        return;
    }
    for (let objectKey in this) {
        if (!this.hasOwnProperty(objectKey)) {
            continue;
        }
        itemFunction.call(this, objectKey, this[objectKey]);
    }
};

/**
 * 遍历Object对象中的数据
 */
Object.prototype.dealEmpty = function () {
    return this || '';
};

/**
 * 初始化select元素
 * @param url 地址
 * @param addAddOption 是否添加【全部】选项
 */
HTMLElement.prototype.initSelect = function (url, addAddOption, map) {
    if (this.localName !== 'select') {
        return;
    }

    let _this = this;
    Request.get(url, null, false, false).then((data) => {
        let htmlString = '';
        if (addAddOption) {
            htmlString += '<option value="">全部</option>';
        }
        data.forEach(function (key, value) {
            if (map) {
                htmlString += '<option value="' + key[map.value] + '">' + key[map.text] + '</option>';
            } else {
                htmlString += '<option value="' + key + '">' + value + '</option>';
            }
        });
        _this.innerHTML = htmlString;
    })
};

/**
 * 设置Html值
 * 触发onchange事件
 * @param value 值
 */
HTMLElement.prototype.setValue = function (value) {
    this.value = common.string.dealEmpty(value);
    if (this.fireEvent) {
        this.fireEvent('onchange');
    } else {
        let event = document.createEvent('HTMLEvents');
        event.initEvent('change', false, true);
        this.dispatchEvent(event);
    }
};

/**
 * 绑定键盘事件
 */
HTMLElement.prototype.keyDownEnter = function (resolve) {
    this.onkeydown = function (ev) {
        if (ev.keyCode === 13 && typeof resolve === 'function') {
            resolve();
        }
    };
};

const SelectDialog = {};

SelectDialog.admin = function (o) {
    let option = {
        id: o.id,
        title: o.title || '选择管理员',
        selectBack: o.selectBack,
        url: o.url || '/admin/queryByParam',
        column: o.column || [
            {title: '名称', name: 'nickname'}]
    };
    SelectDialog.createSelect(option);
};

SelectDialog.createSelect = function (o) {
    let option = {
        id: o.id,
        title: o.title,
        selectBack: o.selectBack,
        url: o.url,
        column: o.column
    };

    if (!option.id) {
        throw 'id不能为空';
    }

    if (!option.url) {
        throw 'url不能为空';
    }

    if (!option.column || option.column.length === 0) {
        throw 'column不能为空';
    }

    option.dialogId = 'dialog_select_' + option.id;
    option.tableId = 'table_select_' + option.id;

    if (!document.getElementById(option.dialogId)) {
        let $dialog = document.createElement('div');
        $dialog.id = option.dialogId;
        $dialog.classList.add('dialog');
        $dialog.innerHTML = '<div class="dialog-body"><div id="' + option.tableId + '" class="dialog-content"></div></div>';
        document.body.appendChild($dialog);
    }

    let dialogSelectAdmin = Dialog.create({
        dialogId: option.dialogId,
        title: option.title,
        minWidth: '600px'
    }).open();

    Table.init({
        tableDivId: option.tableId,
        url: option.url,
        column: option.column,
        rowDbClick: function () {
            if (option.selectBack && typeof option.selectBack === 'function') {
                option.selectBack.call(this);
            }
            dialogSelectAdmin.close();
        }
    });
};

common.confirmRequest = function (id, url, tableId, info) {
    let $dialog = document.getElementById('dialog_confirm');
    if ($dialog) {
        $dialog.querySelector('.dialog-content').innerHTML = info;
    } else {
        $dialog = document.createElement('div');
        $dialog.id = 'dialog_confirm';
        $dialog.classList.add('dialog');
        $dialog.innerHTML = '<div class="dialog-body"><div class="dialog-content">' + info + '</div></div>';
        document.body.appendChild($dialog);
    }

    Dialog.create({
        dialogId: 'dialog_confirm',
        titleFaClass: 'fa-exclamation-triangle dialog-confirm',
        title: '确认提示',
        width: '25%',
        destroyAfterClose: true,
        bottomButtons: [{
            id: 'cancel',
            title: '取消',
            classNames: 'dialog-confirm-cancel',
            closeAfterClick: true
        }, {
            id: 'ok',
            title: '确认',
            classNames: 'dialog-confirm-ok',
            closeAfterClick: true,
            onclick: function () {
                Request.post(url, {id: id}).then(function () {
                    Table.reload(tableId);
                });
            }
        }]
    }).open();
};