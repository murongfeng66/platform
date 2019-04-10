(function () {
    const FormConfig = {
        serializeJsonConfig: {
            notEmpty: true
        }, loadFormConfig: {
            resetForm: true,
            notEmpty: true
        }
    };

    HTMLElement.prototype.getForm = function () {
        let _formItems = this.querySelectorAll('[name]');
        let formJson = {};

        _formItems.forEach(function (_item) {
            if (FormConfig.serializeJsonConfig.notEmpty && _item.value === '') {
                return
            }

            formJson[_item.name] = _item.value;
        });
        return formJson;
    };

    HTMLElement.prototype.setForm = function (formData) {
        let _this = this;
        if (FormConfig.loadFormConfig.resetForm) {
            _this.reset();
        }

        formData.forEach(function (key, value) {
            if (FormConfig.loadFormConfig.notEmpty && value === '') {
                return
            }

            let _formItems = _this.querySelectorAll('[name=' + key + ']');
            _formItems.forEach(function (_item) {
                _item.setValue(value);
            });
        });
    };

    HTMLElement.prototype.addReadOnly = function (readOnly) {
        this.removeReadOnly();
        let selector = readOnly ? '[data-readonly*="|' + readOnly + '|"]': '[data-readonly]';
        this.querySelectorAll(selector).forEach(function (_item) {
            _item.setAttribute('readonly', '');
        })
    };

    HTMLElement.prototype.removeReadOnly = function (readOnly) {
        let selector = readOnly ? '[data-readonly*="|' + readOnly + '|"]': '[data-readonly]';
        this.querySelectorAll(selector).forEach(function (_item) {
            _item.removeAttribute('readonly');
        })
    };

    Object.defineProperties(HTMLElement.prototype, {
        testFunction: {
            writable: true,
            enumerable: false,
            configurable: true,
            value: function () {
                console.info('这是一个测试属性');
            }
        }
    });
})();