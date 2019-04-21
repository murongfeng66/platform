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

common.string = {};
common.string.dealEmpty = function (str) {
    return str || '';
};

/**
 * 移除所有form元素的onsubmit事件默认行为
 */
common.removeOnSubmitEvent = function () {
    document.querySelectorAll('form').forEach(function (_form) {
        _form.onsubmit = function () {
            window.event.preventDefault();
        };
    })
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
HTMLElement.prototype.initSelect = function (url, addAddOption) {
    if (this.localName !== 'select') {
        return;
    }

    let _this = this;
    Request.get(url, null).then(function (data) {
        let htmlString = '';
        if (addAddOption) {
            htmlString += '<option value="">全部</option>';
        }
        data.forEach(function (key, value) {
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

SelectDialog.createSelect = function(o) {
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
        minWidth:'600px'
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
}