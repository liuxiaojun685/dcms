/**
 * Created by lenovo on 17-2-10.
 */
$(function () {


    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryEnvironment",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".cpuFree").text(result.cpuFree);
                $(".cpuInfo").text(result.cpuInfo);
                $(".memFree").text(result.memFree);
                $(".memTotal").text(result.memTotal);
                $(".hdFree").text(result.hdFree);
                $(".hdTotal").text(result.hdTotal);
                $(".logFree").text(result.logFree);
                $(".logTotal").text(result.logTotal);
                $(".fileFree").text(result.fileFree);
                $(".fileTotal").text(result.fileTotal);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
});