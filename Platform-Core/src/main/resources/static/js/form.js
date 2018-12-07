(function ($) {
    var formConfig = {};

    formConfig.serializeJsonConfig = {
        notEmpty: true
    };
    formConfig.loadFormConfig = {
        resetForm: true,
        notEmpty: true
    };

    $.fn.extend({
        serializeJson: function (config) {
            var _config = config || formConfig.serializeJsonConfig;
            var formArray = this.serializeArray();
            var formJson = {};
            for (var i in formArray) {
                if (!formArray.hasOwnProperty(i)) {
                    continue
                }

                var item = formArray[i];
                if (_config.notEmpty && item.value === '') {
                    continue
                }

                formJson[formArray[i].name] = formArray[i].value;
            }
            return formJson;
        },
        loadForm: function (dataJson, config) {
            var _config = config || formConfig.loadFormConfig;
            if (_config.resetForm) {
                this.resize();
            }
            for (var key in dataJson) {
                if (!dataJson.hasOwnProperty(key)) {
                    continue
                }

                if (_config.notEmpty && dataJson[key] === '') {
                    continue
                }

                var formItem = this.find('[name=' + key + ']');
                if (formItem.length > 0) {
                    formItem.val(dataJson[key]);
                }
            }
        }
    });
    $.extend({
        setGlobalConfig: function (config) {
            formConfig = $.extend(true,formConfig,config);
        },
        getGlobalConfig: function () {
            return formConfig;
        }
    });
})(jQuery);