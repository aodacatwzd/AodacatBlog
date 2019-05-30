var flag = 0;
var a;

window.addEventListener("scroll", function (event) {
    var bar = $("#bottom-bar");
    var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
    if (document.documentElement.scrollHeight <= document.documentElement.clientHeight +2 + scrollTop && flag === 0) {
        bar.css("display", "flex");
        bar.css("animation", "showIndex 0.3s");
        flag = 1;
    } else if (flag === 1 && document.documentElement.scrollHeight >= document.documentElement.clientHeight + scrollTop + 100) {
        bar.css("animation", "hideIndex 0.3s");
        setTimeout(function () {
            bar.css("display", "none");
        }, 150);
        flag = 0;
    }
});


