/**
 * Created by lenovo on 17-2-9.
 */

//var host="http://47.93.78.186:8080";
$(function () {
    subok(null);
    systemConfig();
})
function sumbitBtn() {
    if($("input[name='level']:checked")){
        layer.alert("系统已经定级，无法修改!", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                var chiose = $("input[name='level']:checked").val();
                subok(chiose);
                layer.close(index);
            }
        });
        return;
    }
    layer.alert("系统定级后无法修改，确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var chiose = $("input[name='level']:checked").val();
            subok(chiose);
            layer.close(index);
        }
    });
}
function subok(chiose) {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/systemLevel",
        type: "post",
        data: JSON.stringify({
            "token": token,
            "level": chiose
        }),
        success: function (result) {
            result.onSuccess = function () {
                if (result.level != 0) {
                    $("#level" + result.level).attr("checked", true);
                    $("input[name='level']").attr("disabled", true);
                } else {
                    $("#level2").attr("checked", true);
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
queryOtherSecurityConfig();
function queryOtherSecurityConfig() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryOtherSecurityConfig",
        type: "post",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            console.log(result)
            result.onSuccess = function () {
              var str='<span style="padding:0 5px;">是</span><input type="radio" name="change" value="1" id="yes" style="vertical-align: middle"/><span style="padding:0 5px;">否</span><input style="vertical-align: middle" type="radio" value="0" id="no" name="change"/>';
                $("#queryOtherSecurityConfig").append(str);
                if(result.preclassifiedForce == 1){
                    $("#yes").attr("checked",true);
                }else if(result.preclassifiedForce == 0){
                    $("#no").attr("checked",true);
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function updateOtherSecurityConfig() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/updateOtherSecurityConfig",
        type: "post",
        data: JSON.stringify({
            "token": token,
            "preclassifiedForce":$("input[name='change']:checked").val()
        }),
        success: function (result) {
            console.log(result)
            result.onSuccess = function () {
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
$(".updateOtherSecurityConfig").click(function () {
    layer.alert("当前设置修改成功，是否保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            updateOtherSecurityConfig();
            layer.close(index);
        }
    });
})
function systemConfig() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryOrganization",
        type: "post",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                if (result.organizationCode == null) {
                    $(".formek-ma").val("");
                } else {
                    $(".formek-ma").val(result.organizationCode);
                }
                $(".formek-name").val(result.organizationName);
                $(".formek-des").val(result.organizationDescription);
            }
            processResponse(result,0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
$(".update-formek").click(function () {
    layer.alert("当前设置修改成功，是否保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            updateOrganization();
            layer.close(index);
        }
    });
})
function updateOrganization() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/updateOrganization",
        type: "post",
        data: JSON.stringify({
            "token": token,
            "organizationCode": $(".formek-ma").val(),
            "organizationName": $(".formek-name").val(),
            "organizationDescription": $(".formek-des").val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                systemConfig();
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}