/**
 * Created by lenovo on 17-2-23.
 */
$(function () {
    InitTable();
});
//将时间戳转化为时间
function add0(m) {
    return m < 10 ? '0' + m : m
}
function getLocalTime(shijianchuo) {
//shijianchuo是整数，否则要parseInt转换
    if (shijianchuo == undefined || shijianchuo == 0) {
        return "";
    }
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '/' + add0(m) + '/' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}
function InitTable() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryMarkKeyList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var datas = result.markKeyList;
                $("#MarkKey").empty();
                var da;
                if (datas.length > 0) {
                    for (var i = 0; i < datas.length; i++) {
                        var createFrom;
                        if (datas[i].createFrom == 0) {
                            createFrom = "系统预置";
                        }
                        if (datas[i].createFrom == 1) {
                            createFrom = "手动创建";
                        }
                        if (datas[i].createFrom == 2) {
                            createFrom = "导入创建";
                        }
                        var num = parseInt(i + 1);
                        da = getLocalTime(datas[i].createTime);
                        var str = '<tr>' +
                            '<td>' + num + '</td>' +
                            '<td>' + datas[i].keyId + '</td>' +
                            '<td class="name">' + datas[i].keyName + '</td>' +
                            '<td>' + datas[i].keyLength + '</td>' +
                            '<td>' + datas[i].admin.account + '</td>' +
                            '<td>' + da + '</td>' +
                            '<td>' + createFrom + '</td>' +
                            '<td>';
                        if (datas[i].enable == 0) {
                            str += '是<input type="radio" name="' + datas[i].keyId + '_enable" value="1" onclick="updateMarkKey(this)"/>否<input type="radio" name="' + datas[i].keyId + '_enable" value="0"  onclick="updateMarkKey(this)" checked/>';
                        }
                        if (datas[i].enable == 1) {
                            str += '是<input type="radio" name="' + datas[i].keyId + '_enable" value="1"  onclick="updateMarkKey(this)" checked/>否<input type="radio" name="' + datas[i].keyId + '_enable"  onclick="updateMarkKey(this)" value="0" disabled/>';
                        }
                        str += '</td>' +
                            '<td style="text-align: center;">' + datas[i].markVersion + '</td>' +
                                //'<td style="text-align: center;"><a href="#" onclick="deleteMarkKey(this)"><i class="fa fa-trash-o"></i></a></td>' +
                            '</tr>';
                        $("#MarkKey").append(str);
                        $("#MarkKey  td").each(function () {
                            $(this).attr("title", $(this).text());

                        });
                    }
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    queryMarkKeyHistory();
}
function addMarkKey() {
    $("#addMarkKeys").val("");
    layer.open({
        type: 1,
        title: '创建新的密钥',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '155px'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var names = new Array()
            var name = $(".name");
            name.each(function () {
                names.push($(this).text())
            })

            $(".error-span").text("");
            var keys = 0;
            var keyName = $("#addMarkKeys").val();
            if (keyName == null || keyName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".error-span").text("备份描述不能为空！");
                keys = 1;
            }
            for (var n = 0; n < names.length; n++) {
                if (names[n] == keyName) {
                    layer.alert("已存在相同名称的密钥！", {
                        icon: 2,
                        skin: 'layer-ext-moon',
                        btn: ['关闭'],
                        yes: function (index1, layero) {
                            layer.close(index1);
                        }
                    });
                    keys = 1;
                }
            }
            if (keys == 0) {
                $("#addMarkKey").val("");
                addMarkKeys();
                layer.close(index);
            }
        }
        ,
        content: $('#addMarkKey')
    });
}
function addMarkKeys() {
    var keyName = $("#addMarkKeys").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/addMarkKey",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "keyName": keyName
        }),
        success: function (data) {
            data.onSuccess = function () {
                InitTable();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
function deleteMarkKey(d) {
    var keyId = $(d).parent().parent().find("td").eq(1).text();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/deleteMarkKey",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "keyId": keyId
        }),
        success: function (data) {
            data.onSuccess = function () {

                InitTable();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
function updateMarkKey(u) {
    var keyId = $(u).parent().parent().find("td").eq(1).text();
    var keyName = $(u).parent().parent().find("td").eq(2).text();
    var enable = $("input[name='" + keyId + "_enable']:checked").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/updateMarkKey",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "keyId": keyId,
            "keyName": keyName,
            "enable": enable
        }),
        success: function (data) {
            data.onSuccess = function () {
                //alert(123)
                //InitTable();
            }
            processResponse(data, 1);
            InitTable();
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//导出秘钥
//var str='<input type="password" class="form-contorl">'
function backupMarkKey() {
    $("#MarkKeys").val("");
    layer.open({
        type: 1,
        title: '请输入密码',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '135px'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            //$(".passwd").text("");
            //var passwd = $("#MarkKeys").val()
            var keys = 0;
            var passwd = $("#MarkKeys").val();
            if (passwd == null || passwd.replace(/(^s*)|(s*$)/g, "").length == 0) {
                layer.alert("密码不能为空字符串！", {
                    icon: 2,
                    skin: 'layer-ext-moon',
                    btn: ['关闭'],
                    yes: function (index1, layero) {
                        layer.close(index1);
                    }
                });
                keys = 1;
            }
            if (keys == 0) {
                var url = host + "/dcms/api/v1/securePolicy/backupMarkKey";
                url = url + "?token=" + token + "&passwd=" + passwd;
                var excelhref = $("#excelhref");
                excelhref.attr("href", url);
                $("#excel").click();
                layer.close(index);
            }
        },
        content: $("#Key")
    });

}
//导入
function importMarkKey() {
    layer.open({
        type: 1,
        title: '导入密钥',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '200px'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $(".keyName").text("");
            var keys = 0;
            var keyName = $("#keyName").val();
            if (keyName == null || keyName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".keyName").text("密钥名称不能为空！");
                keys = 1;
            }
            if (keys == 0) {
                //$("#keyName").val("");
                importMarkKeys();
                layer.close(index);
            }
        },
        content: $('#importMarkKey')
    });
}
function importMarkKeys() {
    var form = $(".importMarkKey");
    var keyName = $("#keyName").val();
    var data = JSON.stringify({
        "token": token,
        "keyName": keyName
    });
    $("input[name='body']").val(data);
    var options = {
        url: host + '/dcms/api/v1/securePolicy/importMarkKey ',
        type: 'post',
        crossDomain: false,
        success: function (result) {
			result = JSON.parse(result);
            result.onSuccess = function () {
                InitTable(0);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    };
    form.ajaxSubmit(options);

}
//还原
function recoverMarkKey() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/recoverMarkKey",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                InitTable();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//查询秘钥历史
function queryMarkKeyHistory() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryMarkKeyHistory",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            //console.log(result)
            result.onSuccess = function () {
                var datas = result.markKeyHistoryList;
                $("#queryMarkKeyHistory").empty();
                var enableTime, disableTime;
                if (datas.length > 0) {
                    for (var i = 0; i < datas.length; i++) {
                        var num = parseInt(i + 1);
                        enableTime = getLocalTime(datas[i].enableTime);
                        disableTime = getLocalTime(datas[i].disableTime);
                        if (disableTime == "") {
                            disableTime = "--";
                        }
                        var str = '<tr>' +
                            '<td style="text-align: center;">' + num + '</td>' +
                            '<td style="text-align: center;">' + datas[i].keyId + '</td>' +
                            '<td style="text-align: center;">' + datas[i].keyName + '</td>' +
                            '<td style="text-align: center;">' + enableTime + '</td>' +
                            '<td style="text-align: center;">' + disableTime + '</td>' +
                            '</tr>';
                        $("#queryMarkKeyHistory").append(str);
                        $("#queryMarkKeyHistory  td").each(function () {
                            $(this).attr("title", $(this).text());
                        });
                    }
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
