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
        let formItems = this.querySelectorAll('[name]');
        let formJson = {};
        for (let i in formItems) {
            if (!formItems.hasOwnProperty(i)) {
                continue
            }

            let item = formItems[i];
            if (FormConfig.serializeJsonConfig.notEmpty && item.value === '') {
                continue
            }

            formJson[formItems[i].name] = formItems[i].value;
        }
        return formJson;
    };

    HTMLElement.prototype.setForm = function (formData) {
        if(FormConfig.loadFormConfig){
            this.reset();
        }
        for (let name in formData) {
            if (!formData.hasOwnProperty(name)) {
                continue
            }

            if (FormConfig.loadFormConfig.notEmpty && formData[name] === '') {
                continue
            }

            let formItems = this.querySelectorAll('[name='+name+']');
            for (let i in formItems) {
                if (!formItems.hasOwnProperty(i)) {
                    continue
                }

                formItems[i].value = formData[name];
            }
        }
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