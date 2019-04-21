function Dialog() {
}

(() => {
    const DialogCache = {};

    Dialog.create = (o) => {
        if (!o.dialogId) {
            throw 'ID不能为空';
        }

        let option = {
            dialogId: o.dialogId,
            title: o.title,
            topButtons: o.topButtons || [],
            bottomButtons: o.bottomButtons || [],
            width: o.width || '30%',
            minWidth: o.minWidth
        };

        let dialogCache = DialogCache[option.dialogId];
        if (!dialogCache) {
            dialogCache = init(option);
        }
        return dialogCache;
    };

    Dialog.get = (dialogId) => {
        return DialogCache[dialogId];
    };

    Dialog.prototype = {
        open: function () {
            document.getElementById(this.dialogId).style.display = 'block';
            return this;
        },
        close: function () {
            document.getElementById(this.dialogId).style.display = '';
            return this;
        },
        setTitle: function (title) {
            document.getElementById(DialogCache[this.dialogId].dialogTopTitleId).innerHTML = title;
            return this;
        },
        destroy: function () {
            destroy(this.dialogId);
        },
        hideTopButton: function (id) {
            if (id) {
                document.getElementById(this.dialogTopId + '_button_' + id).style.display = 'none';
            } else {
                document.querySelectorAll('#' + this.dialogTopId + ' .button').forEach((_item) => {
                    _item.style.display = 'none';
                });
            }
            return this;
        },
        showTopButton: function (id) {
            if (id) {
                document.getElementById(this.dialogTopId + '_button_' + id).style.display = '';
            }
            return this;
        },
        hideBottomButton: function (id) {
            if (id) {
                document.getElementById(this.dialogBottomId + '_button_' + id).style.display = 'none';
            } else {
                document.querySelectorAll('#' + this.dialogBottomId + ' .button').forEach((_item) => {
                    _item.style.display = 'none';
                });
            }

            let display = 'none';
            let $buttonsHtml = document.querySelectorAll('#' + this.dialogBottomId + ' .button');
            for (let index = 0; index < $buttonsHtml.length; index++) {
                let $itemHtml = $buttonsHtml[index];
                if ($itemHtml.style.display === '') {
                    display = 'block';
                    break;
                }
            }
            document.getElementById(this.dialogBottomId).style.display = display;
            return this;
        },
        showBottomButton: function (id) {
            if (id) {
                document.getElementById(this.dialogBottomId).style.display = 'block';
                document.getElementById(this.dialogBottomId + '_button_' + id).style.display = '';
            }
            return this;
        },
    };

    function destroy(dialogId) {
        let dialogCache = DialogCache[dialogId];
        document.getElementById(dialogCache.dialogTopId).remove();
        document.getElementById(dialogCache.dialogBottomId).remove();
        document.getElementById(dialogCache.dialogId).style.display = '';
        delete DialogCache[dialogId];
    }

    function init(option) {
        let dialog = new Dialog();
        dialog.option = option;
        dialog.dialogId = option.dialogId;

        dialog.dialogTopId = dialog.dialogId + '_top';
        dialog.dialogTopTitleId = dialog.dialogTopId + '_title';
        dialog.dialogBodyId = dialog.dialogId + '_body';
        dialog.dialogContentId = dialog.dialogBodyId + '_content';
        dialog.dialogBottomId = dialog.dialogId + '_bottom';

        DialogCache[dialog.dialogId] = dialog;

        initDialog.call(dialog);

        return dialog;
    }

    function initDialog() {
        let $dialogBodyHtml = document.querySelector('#' + this.option.dialogId + ' .dialog-body');
        if ($dialogBodyHtml.getAttribute('id')) {
            this.dialogBodyId = $dialogBodyHtml.getAttribute('id');
        } else {
            $dialogBodyHtml.id = this.dialogBodyId;
        }
        $dialogBodyHtml.style.width = this.option.width;
        $dialogBodyHtml.style.minWidth = this.option.minWidth;
        let $dialogContentHtml = $dialogBodyHtml.querySelector('.dialog-content');
        if ($dialogContentHtml.getAttribute('id')) {
            this.dialogContentId = $dialogContentHtml.getAttribute('id');
        } else {
            $dialogContentHtml.id = this.dialogContentId;
        }

        initTop.call(this);
        initBottom.call(this);
    }

    function initTop() {
        let $dialogTopHtml = document.createElement('div');
        $dialogTopHtml.id = this.dialogTopId;
        $dialogTopHtml.classList.add('dialog-top');
        $dialogTopHtml.innerHTML = '<span id="' + this.dialogTopTitleId + '" class="dialog-top-title">' + common.string.dealEmpty(this.option.title) + '</span><div class="dialog-top-buttons"></div><div class="close fa fa-times"></div>';
        $dialogTopHtml.querySelector('.close').onclick = () => {
            document.getElementById(this.dialogId).style.display = '';
        };

        let $dialogTopButtonsHtml = $dialogTopHtml.querySelector('.dialog-top-buttons');
        this.option.topButtons.forEach((button) => {
            let $buttonHtml = document.createElement('span');
            $buttonHtml.id = this.dialogTopId + '_button_' + button.id;
            $buttonHtml.innerHTML = button.title;
            $buttonHtml.classList.add('dialog-top-buttons-item', 'margin-left-5');
            if (button.classNames) {
                $buttonHtml.classList.add(button.classNames);
            }

            $buttonHtml.onclick = () => {
                if (typeof button.onclick === 'function') {
                    button.onclick.call($buttonHtml);
                }
            };
            $dialogTopButtonsHtml.appendChild($buttonHtml);
        });

        document.getElementById(this.dialogBodyId).insertBefore($dialogTopHtml, document.getElementById(this.dialogContentId));
    }

    function initBottom() {
        let $dialogBottomHtml = document.createElement('div');
        $dialogBottomHtml.id = this.dialogBottomId;
        $dialogBottomHtml.classList.add('dialog-bottom');
        document.getElementById(this.dialogBodyId).appendChild($dialogBottomHtml);

        this.option.bottomButtons.forEach((button) => {
            let $buttonHtml = document.createElement('button');
            $buttonHtml.id = this.dialogBottomId + '_button_' + button.id;
            $buttonHtml.innerHTML = button.title;
            $buttonHtml.classList.add('button', 'button-s', 'margin-left-10');
            if (button.classNames) {
                $buttonHtml.classList.add(button.classNames);
            }

            $buttonHtml.onclick = () => {
                if (typeof button.onclick === 'function') {
                    button.onclick.call($buttonHtml);
                }
                if (button.closeAfterClick === true) {
                    document.getElementById(this.dialogId).style.display = '';
                }
            };
            $dialogBottomHtml.appendChild($buttonHtml);
        });
        if (this.option.bottomButtons.length > 0) {
            $dialogBottomHtml.style.display = 'block';
        }
    }

})();