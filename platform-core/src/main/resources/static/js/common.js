const common = {};

/**
 * 控件加载配置
 */
common.config = {
    plugsConfig: {
        request: {js: 'https://static.platform.jwzhu.com/js/request.js'},
        form: {js: 'https://static.platform.jwzhu.com/js/form.js', css: 'https://static.platform.jwzhu.com/css/form.css'},
        dialog: {js: 'https://static.platform.jwzhu.com/js/dialog.js', css: 'https://static.platform.jwzhu.com/css/dialog.css'},
        table: {js: 'https://static.platform.jwzhu.com/js/table.js', css: 'https://static.platform.jwzhu.com/css/table.css', loadCss: null},
        datePicker: {js: 'https://static.platform.jwzhu.com/js/date-picker.js', css: 'https://static.platform.jwzhu.com/css/date-picker.css', loadJs: () => DatePicker.init},
        permission: {js: 'https://static.platform.jwzhu.com/js/permission-filter.js', loadJs: () => PermissionFilter.filter},
        fontIcon: {css: 'https://static.platform.jwzhu.com/font-awesome-4.7.0/css/font-awesome.min.css'}
    }
};

/**
 * 初始化页面及控件，需放在最后调用
 */
function initHtml() {
    removeFormDefaultEvent();
    // loadJs(common.config.plugsConfig.request.js);
    // loadCss(common.config.plugsConfig.fontIcon.css);
    let plugs = document.body.getAttribute('data-plugs');
    if (plugs) {
        plugs = plugs.split(',');
        plugs.forEach(function (item) {
            let config = common.config.plugsConfig[item];
            loadJs(config.js).then(config.loadJs);
            loadCss(config.css).then(config.loadCss);
        });
    } else {
        if (typeof PermissionFilter !== 'undefined') {
            PermissionFilter.filter();
        }
        if (typeof DatePicker !== 'undefined') {
            DatePicker.init();
        }
    }
}

/**
 * 加载JS文件
 * @param url 文件URL
 * @returns {Promise<resolve>} 异步处理
 */
function loadJs(url) {
    return new Promise(resolve => {
        if (!url) {
            return;
        }
        let $script = document.createElement('script');
        $script.type = 'text/javascript';
        $script.src = url;
        document.body.appendChild($script);
        loadFinish($script, resolve);
    });
}

/**
 * 加载CSS文件
 * @param url 文件URL
 * @returns {Promise<resolve>} 异步处理
 */
function loadCss(url) {
    return new Promise(resolve => {
        if (!url) {
            return;
        }
        let $link = document.createElement('link');
        $link.rel = 'stylesheet';
        $link.href = url;
        document.head.appendChild($link);
        loadFinish($link, resolve);
    });
}

/**
 * 处理JS或CSS文件加载完成
 * @param $element 文件元素
 * @param resolve 成功回调
 */
function loadFinish($element, resolve) {
    if (!resolve || typeof resolve === 'undefined') {
        return;
    }
    if ($element.readyState) {
        $element.onreadystatechange = function () {
            if ($element.readyState === "loaded" || $link.readyState === "complete") {
                $element.onreadystatechange = null;
                resolve();
            }
        }
    } else {
        $element.onload = function () {
            resolve();
        }
    }
}

/**
 * 取消Form表单的默认提交事件
 */
function removeFormDefaultEvent() {
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
    let active = 'toast-active';
    let div = document.createElement('div');
    div.classList.add('toast-container');
    div.innerHTML = `<div class="toast-message-container toast-${type}">${message}</div>`;
    div.addEventListener('webkitTransitionEnd', function () {
        div.classList.contains(active) || div.parentNode.removeChild(div);
    });
    document.body.appendChild(div);
    div.classList.add(active);
    setTimeout(function () {
        div.classList.remove(active);
    }, time);
};

common.string = {};
/**
 * 处理字符串空值
 * @param str 待处理字符串
 * @param defaultValue 默认值
 * @returns ''/str 空串或传入字符串
 */
common.string.dealEmpty = function (str, defaultValue) {
    return str || defaultValue || '';
};

/**
 * 返回指定长度的字符串
 * @param length 长度
 * @param char 填充字符
 * @param position 填充位置：prefix=前面，默认后面
 * @returns {string} 处理完成的字符串
 */
Object.prototype.fixLength = function (length, char, position) {
    let str = this.toString();
    if (str.length === length) {
        return str;
    }
    if (str.length > length) {
        return str.substr(0, length);
    }
    let chars = '';
    for (let i = 0; i < length - str.length; i++) {
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
 * 初始化select元素
 * @param url 地址
 * @param addAddOption 是否添加【全部】选项
 * @param map key和value的字段名称
 */
HTMLElement.prototype.initSelect = function (url, addAddOption, map) {
    if (this.localName !== 'select') {
        return;
    }

    let _this = this;
    Request.get({
        url: url,
        showMessage: false,
        success: (data) => {
            let htmlString = '';
            if (addAddOption) {
                htmlString += '<option value="">全部</option>';
            }
            data.forEach(function (key, value) {
                if (map) {
                    htmlString += `<option value="${key[map.value]}">${key[map.text]}</option>`;
                } else {
                    htmlString += `<option value="${key}">${value}</option>`;
                }
            });
            _this.innerHTML = htmlString;
        }
    });
};

/**
 * 设置Html值
 * 触发onchange事件
 * @param value 值
 */
HTMLElement.prototype.setValue = function (value) {
    this.value = common.string.dealEmpty(value);
    let event = document.createEvent('HTMLEvents');
    event.initEvent('change', false, true);
    this.dispatchEvent(event);
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

    option.dialogId = `dialog_select_${option.id}`;
    option.tableId = `table_select_${option.id}`;

    if (!document.getElementById(option.dialogId)) {
        let $dialog = document.createElement('div');
        $dialog.id = option.dialogId;
        $dialog.classList.add('dialog');
        $dialog.innerHTML = `<div class="dialog-body"><div id="${option.tableId}" class="dialog-content"></div></div>`;
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
        $dialog.innerHTML = `<div class="dialog-body"><div class="dialog-content">${info}</div></div>`;
        document.body.appendChild($dialog);
    }

    Dialog.create({
        dialogId: 'dialog_confirm',
        titleFaClass: 'fa-exclamation-triangle dialog-top-font-confirm',
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
                Request.post({
                    url: url,
                    params: {id: id},
                    success: () => {
                        Table.reload(tableId);
                    }
                });
            }
        }]
    }).open();
};

initHtml();