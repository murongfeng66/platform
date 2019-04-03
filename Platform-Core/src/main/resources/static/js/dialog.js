function Dialog() {
}

const DialogHtmlCache = {};
const DialogCache = {};
(function () {

    Dialog.create = function (o) {
        if (!o.dialogId) {
            throw 'ID不能为空';
        }

        let option = {
            dialogId: o.dialogId,
            title: o.title,
            topButtons: o.topButtons || [],
            bottomButtons: o.bottomButtons || [],
            width: o.width || '30%'
        };

        let dialogHtmlCache = DialogHtmlCache[option.dialogId];
        if (dialogHtmlCache) {
            return DialogCache[option.dialogId];
        } else {
            return init(option);
        }
    };

    Dialog.prototype.open = function () {
        DialogHtmlCache[this.dialogId]._dialogHtml.style.display = 'block';
        return this;
    };

    Dialog.prototype.close = function () {
        DialogHtmlCache[this.dialogId]._dialogHtml.style.display = '';
        return this;
    };

    Dialog.prototype.get = function (dialogId) {
        return DialogCache[dialogId];
    };

    Dialog.prototype.setTitle = function (title) {
        let dialogHtmlCache = DialogHtmlCache[this.dialogId];
        dialogHtmlCache._dialogTopHtml.title.selfHtml.innerHTML = title;
        return this;
    };

    Dialog.prototype.destroy = function () {
        destroy(this.dialogId);
    };

    Dialog.prototype.hideTopButton = function (index) {
        let dialogHtmlCache = DialogHtmlCache[this.dialogId];
        dialogHtmlCache._dialogTopHtml.buttons.childrenHtml[index].style.display = 'none';
        return this;
    };

    Dialog.prototype.showTopButton = function (index) {
        let dialogHtmlCache = DialogHtmlCache[this.dialogId];
        dialogHtmlCache._dialogTopHtml.buttons.childrenHtml[index].style.display = '';
        return this;
    };

    Dialog.prototype.hideBottomButton = function (index) {
        let dialogHtmlCache = DialogHtmlCache[this.dialogId];
        dialogHtmlCache._dialogBottomHtml.childrenHtml[index].style.display = 'none';
        let display = 'none';
        for(let index in dialogHtmlCache._dialogBottomHtml.childrenHtml){
            let _itemHtml = dialogHtmlCache._dialogBottomHtml.childrenHtml[index];
            if(_itemHtml.style.display === ''){
                display = 'block';
                break;
            }
        }
        dialogHtmlCache._dialogBottomHtml.selfHtml.style.display = display;
        return this;
    };

    Dialog.prototype.showBottomButton = function (index) {
        let dialogHtmlCache = DialogHtmlCache[this.dialogId];
        dialogHtmlCache._dialogBottomHtml.childrenHtml[index].style.display = '';
        dialogHtmlCache._dialogBottomHtml.selfHtml.style.display = 'block';
        return this;
    };

    function destroy(dialogId) {
        delete DialogCache[dialogId];
        let dialogHtmlCache = DialogHtmlCache[dialogId];
        dialogHtmlCache._dialogTopHtml.selfHtml.remove();
        dialogHtmlCache._dialogBottomHtml.remove();
        dialogHtmlCache._dialogHtml.style.display = '';
        delete DialogHtmlCache[dialogId];
    }

    function init(option) {
        let dialogHtmlCache = {
            option: option,
            _dialogHtml: null,
            _dialogBodyHtml: null,
            _dialogTopHtml: {
                selfHtml: null,
                title: {
                    selfHtml: null
                },
                buttons: {
                    selfHtml: null,
                    childrenHtml: []
                },
                closeButton: {
                    selfHtml: null
                }
            },
            _dialogContentHtml: {
                selfHtml: null
            },
            _dialogBottomHtml: {
                selfHtml: null,
                childrenHtml: []
            }
        };

        DialogHtmlCache[option.dialogId] = dialogHtmlCache;

        return initDialog.call(dialogHtmlCache);
    }

    function initDialog() {
        let _this = this;

        let dialog = new Dialog();
        dialog.dialogId = _this.option.dialogId;
        DialogCache[dialog.dialogId] = dialog;

        _this._dialogHtml = document.getElementById(_this.option.dialogId);
        _this._dialogHtml.innerHTML = '<div class="dialog-body">' + _this._dialogHtml.innerHTML + '</div>';

        _this._dialogBodyHtml = _this._dialogHtml.querySelector('.dialog-body');
        _this._dialogBodyHtml.style.width = _this.option.width;

        _this._dialogContentHtml = _this._dialogHtml.querySelector('.dialog-content');

        initTop.call(_this);
        initBottom.call(_this);

        return dialog;
    }

    function initTop() {
        let _this = this;

        _this._dialogTopHtml.selfHtml = document.createElement('div');
        _this._dialogTopHtml.selfHtml.classList.add('dialog-top');
        _this._dialogTopHtml.selfHtml.innerHTML = '<span class="dialog-top-title">' + _this.option.title + '</span><div class="dialog-top-buttons"></div><div class="close fa fa-times">';
        _this._dialogTopHtml.selfHtml.querySelector('.close').onclick = function () {
            _this._dialogHtml.style.display = '';
        };
        _this._dialogTopHtml.title.selfHtml = _this._dialogTopHtml.selfHtml.querySelector('.dialog-top-title');
        _this._dialogTopHtml.buttons.selfHtml = _this._dialogTopHtml.selfHtml.querySelector('.dialog-top-buttons');
        _this._dialogTopHtml.closeButton.selfHtml = _this._dialogTopHtml.selfHtml.querySelector('.close');
        _this.option.topButtons.forEach(function (button, index) {
            let _buttonHtml = document.createElement('span');
            _buttonHtml.innerHTML = button.title;
            _buttonHtml.classList.add('dialog-top-buttons-item', 'margin-left-5');
            if(button.classNames){
                _buttonHtml.classList.add(button.classNames);
            }
            _buttonHtml.onclick = function () {
                if (typeof button.onclick === 'function') {
                    button.onclick.call(_buttonHtml);
                }
            };
            _this._dialogTopHtml.buttons.selfHtml.appendChild(_buttonHtml);
            _this._dialogTopHtml.buttons.childrenHtml[index] = _buttonHtml;
        });

        _this._dialogBodyHtml.insertBefore(_this._dialogTopHtml.selfHtml, _this._dialogContentHtml);
    }

    function initBottom(){
        let _this = this;

        _this._dialogBottomHtml.selfHtml = document.createElement('div');
        _this._dialogBottomHtml.selfHtml.classList.add('dialog-bottom');
        _this._dialogBodyHtml.appendChild(_this._dialogBottomHtml.selfHtml);
        _this.option.bottomButtons.forEach(function (button, index) {
            let _buttonHtml = document.createElement('button');
            _buttonHtml.innerHTML = button.title;
            _buttonHtml.classList.add('dialog-bottom-button', 'margin-left-10');
            if(button.classNames){
                _buttonHtml.classList.add(button.classNames);
            }
            _buttonHtml.onclick = function () {
                if (typeof button.onclick === 'function') {
                    button.onclick.call(_buttonHtml);
                }
            };
            _this._dialogBottomHtml.selfHtml.appendChild(_buttonHtml);
            _this._dialogBottomHtml.selfHtml.style.display = 'block';
            _this._dialogBottomHtml.childrenHtml[index] = _buttonHtml;
        });
        if(_this.option.bottomButtons.length>0){
            _this._dialogBottomHtml.selfHtml.style.display = 'block';
        }
    }

})();