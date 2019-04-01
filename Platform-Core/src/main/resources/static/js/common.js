const common = {};
common.token = null;

common.toast = {};
common.toast.info = function (message) {
    common.toast.show(message);
};
common.toast.error = function (message) {
    common.toast.show(message, 'error', 2500);
};
common.toast.show = function (message, type, time) {
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
common.keydown.enter = function(id){
    return new Promise(function(resolve){
        document.getElementById(id).onkeydown = function (ev) {
            if (ev.keyCode === 13) {
                resolve();
            }
        };
    });
};

common.string = {};
common.string.checkEmpty = function (str) {
    return str || '';
};

HTMLElement.prototype.hide = function(){
    this.classList.add('hide');
};

HTMLElement.prototype.show = function(){
    this.classList.remove('hide');
};