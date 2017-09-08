/**
 * Created by lenovo on 17-2-10.
 */
function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '/' + getzf(oMonth) + '/' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}
$(function () {
    InitTable();
});
function InitTable() {
    $.ajax({
        url: host + "/dcms/api/v1/license/query",
        type: "post",
        async: false,
        data: JSON.stringify({
            //"token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                if (data.state == 1) {
                    data.state = "正式版";
                }
                if (data.state == 2) {
                    data.state = "试用版";
                }
                $("#plist1").empty();
                var str = '<tr>' +
                    '<td>授权源:</td>' +
                    '<td class="source">' +
                    data.source +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>客户编号:</td>' +
                    '<td class="customId">' +
                    data.customId +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>客户名称:</td>' +
                    '<td class="customName">' +
                    data.customName +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>授权状态:</td>' +
                    '<td class="state">' +
                    data.state +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>授权导入日期:</td>' +
                    '<td class="starttime">' +
                    getMyDate(data.startTime) +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>授权结束日期:</td>' +
                    '<td class="endtime">' +
                    getMyDate(data.endTime) +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>网络版最大数量:</td>' +
                    '<td class="onlinemax">' +
                    data.onlineMax +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>单机版最大数量:</td>' +
                    '<td class="offlinemax">' +
                    data.offlineMax +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="background: #efefef;">中间件最大数量:</td>' +
                    '<td class="middlewaremax">' +
                    data.middlewareMax +
                    '</td>' +
                    '</tr>';
                $("#plist1").append(str);
                if ($(".customId").text() == "undefined") {
                    $(".customId").parent().hide();
                }
                if ($(".customName").text() == "undefined") {
                    $(".customName").parent().hide();
                }
                if ($(".state").text() == "undefined") {
                    $(".state").parent().hide();
                }
                if ($(".onlinemax").text() == "undefined") {
                    $(".onlinemax").parent().hide();
                }
                if ($(".offlinemax").text() == "undefined") {
                    $(".offlinemax").parent().hide();
                }
                if ($(".middlewaremax").text() == "undefined") {
                    $(".middlewaremax").parent().hide();
                }
                if ($(".starttime").text() == "NaN/NaN/NaN NaN:NaN:NaN") {
                    $(".starttime").parent().hide();
                }
                if ($(".endtime").text() == "NaN/NaN/NaN NaN:NaN:NaN") {
                    $(".endtime").parent().hide();
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
$(".let").click(function () {
    if ($(".con").val() == '') {
        layer.alert("授权码为空，导入失败！", {
            icon: 2,
            skin: 'layer-ext-moon'
        });
        return;
    }
    $.ajax({
        url: host + "/dcms/api/v1/license/import",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "licenseText": $(".con").val()
        }),
        success: function (data) {
            data.onSuccess = function () {
                //console.log(data);
                window.location.href="login.html";
                layer.close(index);
            }
            showGlobleTip(data.msg)
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
})
function fileUploadChange(obj) {
    var str = $("#fileupload").val();
    var arr = str.split("\\");
    $("input[name='rfilename']").val(arr[arr.length - 1]);
    //var fileName = input.value;
    //if(fileName.length > 1 && fileName ) {
    //    var ldot = fileName.lastIndexOf(".");
    //    var type = fileName.substring(ldot + 1);
    //    if(type != "img") {
    //        alert(type);
    //        //清除当前所选文件
    //        input.outerHTML=input.outerHTML.replace(/(value=\").+\"/i,"$1\"");
    //    }
    //}
    //var array = new Array('gif', 'jpeg', 'png', 'jpg'); //可以上传的文件类型
    //if (obj.value == '') {
    //    alert("让选择要上传的图片!");
    //    return false;
    //}
    //else {
    //    var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用：）
    //    var isExists = false;
    //    for (var i in array) {
    //        if (fileContentType.toLowerCase() == array[i].toLowerCase()) {
    //            isExists = true;
    //            return true;
    //        }
    //    }
    //    if (isExists == false) {
    //        obj.value = null;
    //        alert("上传图片类型不正确!");
    //        return false;
    //    }
    //    return false;
    //}
}
var str = '<h4 class="text-center" style="vertical-align: middle">上传成功</h4>';
$(".imfile").click(function () {
    addFile();
})
//添加文件
function addFile(index) {
    var val = $("#fileupload").val();
    if (val == '') {
        layer.alert("未选择任何文件，导入失败！", {
            icon: 2,
            skin: 'layer-ext-moon'
        });
        return;
    }
    var form = $(".clientPatch");
    var data = JSON.stringify({
        "token": token
    });
    $("input[name='body']").val(data);
    var options = {
        url: host + '/dcms/api/v1/license/importFile',
        type: 'post',
        success: function (result) {
            result.onSuccess = function () {
                window.location.href="login.html";
                layer.close(index);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    };
    form.ajaxSubmit(options);
}
