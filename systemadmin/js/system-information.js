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
                $(".osType").text(result.osType)
                $(".platformVersion").text(result.platformVersion);
                $(".datebase").text(result.datebase);
            }
            processResponse(result, 0);

        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
});
