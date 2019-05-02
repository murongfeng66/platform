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
 * 初始化页面
 * 1、移除所有form元素的onsubmit事件默认行为
 */
common.initHtml = function () {
    document.querySelectorAll('form').forEach(function ($form) {
        $form.onsubmit = () => {
            window.event.preventDefault();
        };
    });
    document.querySelectorAll('input.date-picker').forEach(function ($input) {
        let dateFormat = $input.getAttribute('data-date-format');
        let id = $input.getAttribute('id');
        id = id || $input.getAttribute('name');
        id += '_date_picker';
        $input.onfocus = () => {
            function createPickerBody(id, monthChangeValue) {
                let tableBodyId = id + '_body_table_body';
                let topYearId = id + '_body_top_year';
                let topMonthId = id + '_body_top_month';
                let $datePicker = document.getElementById(id);

                let now = new Date();

                let currentDate;
                let dateStr = $datePicker.getAttribute('data-selected');
                if (dateStr) {
                    currentDate = new Date(dateStr);
                } else {
                    currentDate = new Date();
                }

                let currentYear = document.getElementById(topYearId).innerText;
                currentYear = currentYear || currentDate.getFullYear();
                currentDate.setFullYear(currentYear);
                let currentMonth = document.getElementById(topMonthId).innerText;
                currentMonth = currentMonth ? currentMonth - 1 : currentDate.getMonth();
                currentDate.setMonth(currentMonth + monthChangeValue);

                let isNow = now.getFullYear() === currentDate.getFullYear() && now.getMonth() === currentDate.getMonth();
                let isCurrent = false;
                let date;
                if(dateStr){
                    date = new Date(dateStr);
                    isCurrent = date.getFullYear() === currentDate.getFullYear() && date.getMonth() === currentDate.getMonth();
                }
                document.getElementById(topYearId).innerText = currentDate.getFullYear();
                document.getElementById(topMonthId).innerText = (currentDate.getMonth() + 1).fixLength(2, '0', 'prefix');

                currentDate.setDate(1);
                let beginWeek = currentDate.getDay();
                currentDate.setMonth(currentDate.getMonth() + 1, 0);
                let monthMaxDay = currentDate.getDate();
                let days = [];
                currentDate.setDate(0);
                let preMonthMaxDay = currentDate.getDate();

                for (let dayIndex = preMonthMaxDay - beginWeek + 1; dayIndex <= preMonthMaxDay; dayIndex++) {
                    days.push(dayIndex);
                }
                let currentBeginIndex = days.length;
                for (let dayIndex = 1; dayIndex <= monthMaxDay; dayIndex++) {
                    if (dayIndex > 0) {
                        days.push(dayIndex);
                    }
                }
                let currentEndIndex = days.length - 1;
                for (let dayIndex = 1; days.length % 7 !== 0; dayIndex++) {
                    days.push(dayIndex);
                }

                let htmlString = '';
                days.forEach(function (item, index) {
                    let remainder = (index + 1) % 7;
                    if (remainder === 1) {
                        htmlString += '<tr>';
                    }
                    let classNames = '';
                    if (index < currentBeginIndex) {
                        classNames = 'prefix';
                    } else if (index > currentEndIndex) {
                        classNames = 'next';
                    } else {
                        classNames = 'current';
                    }

                    if (isNow && item === now.getDate()) {
                        classNames += ' now';
                    }
                    if(isCurrent && item === date.getDate()){
                        classNames += ' active';
                    }
                    htmlString += '<td class="' + classNames + '">' + item + '</td>';
                    if (remainder === 0) {
                        htmlString += '</tr>';
                    }
                });
                document.getElementById(tableBodyId).innerHTML = htmlString;
            }

            let $datePicker = document.getElementById(id);
            if (!$datePicker) {
                $datePicker = document.createElement('div');
                $datePicker.id = id;
                $datePicker.classList.add('date-picker-body');
                document.body.appendChild($datePicker);
                let tableBodyId = id + '_body_table_body';
                let topYearId = id + '_body_top_year';
                let topMonthId = id + '_body_top_month';

                $datePicker.innerHTML = '<div class="date-picker-top">' +
                    '<span class="date-picker-top-left prefixMonth">&lt;</span>' +
                    '<span class="date-picker-top-left prefixYear">&laquo;</span>' +
                    '<span id="' + topYearId + '" class="margin-right-5"></span>' +
                    '<span id="' + topMonthId + '" class="margin-left-5"></span>' +
                    '<span class="date-picker-top-right nextMonth">&gt;</span>' +
                    '<span class="date-picker-top-right nextYear">&raquo;</span>' +
                    '</div>' +
                    '<table class="date-picker-body-table">' +
                    '<thead><tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr></thead>' +
                    '<tbody id="' + tableBodyId + '" class="date-picker-body-table-body">' +
                    '</tbody>' +
                    '</table>';

                $datePicker.querySelector('.prefixYear').onclick = function () {
                    createPickerBody(id, -12);
                };
                $datePicker.querySelector('.prefixMonth').onclick = function () {
                    createPickerBody(id, -1);
                };
                $datePicker.querySelector('.nextYear').onclick = function () {
                    createPickerBody(id, 12);
                };
                $datePicker.querySelector('.nextMonth').onclick = function () {
                    createPickerBody(id, 1);
                };
                document.getElementById(tableBodyId).onclick = function () {
                    window.event.stopPropagation();
                    let target = window.event.target;
                    let currentActive = document.getElementById(tableBodyId).querySelector('.active');
                    if(currentActive){
                        currentActive.classList.remove('active');
                    }
                    target.classList.add('active');
                    $datePicker.style.display = 'none';

                    let year = document.getElementById(topYearId).innerText;
                    let month = document.getElementById(topMonthId).innerText.fixLength(2, '0', 'prefix');
                    let day = target.innerText.fixLength(2, '0', 'prefix');
                    let selected = year + '-' + month + '-' + day;
                    $input.setValue(selected);
                    $datePicker.setAttribute('data-selected', selected);
                }
            }
            $datePicker.setAttribute('data-selected', $input.value);
            createPickerBody(id, 0);
            $datePicker.style.top = ($input.offsetTop + $input.offsetHeight) + 'px';
            $datePicker.style.left = $input.offsetLeft + 'px';
            $datePicker.style.display = '';
        };
    });
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