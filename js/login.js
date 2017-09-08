/**
 * Created by liuxiaojun on 17/3/21.
 */
//var host=window.location.host;
// var host = "127.0.0.1:8080";
// var host="47.93.78.186:8080";
//$(function(){
//   var str='<img id="img" src="'+host+'/dcms/IdentifyingCode" style="margin-left:-25px;">';
//    $("#code").append(str);
//})
$('input, textarea').placeholder();
$(function(){
    var str='<img id="img" src="" style="margin-left:-25px;">';
    $("#code").append(str);
    var url =host+"/dcms/api/v1/admin/identifyingCode";
    $("#img").attr("src",url);
})
function onKeyDown(event) {
    var e = event || window.event;
    var kcode = e.which || e.keyCode;
    if (kcode == 13)   //回车键登录
        $("#sys").click();
}
document.onkeydown = onKeyDown;
function refreshTestNum() {
    var num = Math.floor(Math.random() * 9000) + 1000;
    $(".test-number span>i").text(num);
    $(".test-number input").val("");
}

$("#sys").click(function () {
    var accounts = $(".login-name").val();
    var passwd = $("#pwd").val();
    var keyCode = $("#keycode").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/admin/login",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "account": accounts,
            "passwd": passwd,
            "keyCode":keyCode
        }),
        success: function (result) {
            sessionStorage.setItem("token", result.token);
            sessionStorage.setItem("account", result.account);
            sessionStorage.setItem("name", result.name);
            sessionStorage.setItem("adminType", result.adminType);
            sessionStorage.setItem("createFrom", result.createFrom);
            if (result.adminType == 1) {
                window.location.href = "logadmin/index.html";
            }
            if (result.adminType == 2) {
                window.location.href = "systemadmin/index.html";
            }
            if (result.adminType == 3) {
                window.location.href = "secadmin/index.html";
            }
            if (result.code != 0) {
                //"账号或密码错误，请检查后重新输入！"
                $(".content").text(result.msg);
                change();
            }

        },
        error: function (result) {
            showGlobleTip("服务器连接异常，请检查网络或联系管理员！");
        }
    });
    //sessionStorage.getItem("token");
})
function downLoadclient_windows_x86() {
    var url=host+"/dcms/msclient_windows.zip";
    var excelhref = $("#excelhref");
    excelhref.attr("href",url);
    $("#excel").click();
}