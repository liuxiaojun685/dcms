$(function () {
    InitTable(0);
    getEnable();
});
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
//转换文件大小
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
function getEnable() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/backup/queryConfig",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                $("#tab1").empty();
                var str1 = '<tr>' +
                    '<td colspan="9" style="text-align: right;border-top:0;">是否开启自动备份：';
                if (data.autoBackupEnable == 1) {
                    str1 += '<input type="radio" name="enable" checked value="1" onclick="enable(this)"/>是 <input type="radio" name="enable" value="0"/>否';
                }
                if (data.autoBackupEnable == 0) {
                    str1 += '<input type="radio" name="enable" value="1" onclick="enable(this)"/>是 <input type="radio" name="enable" value="0"  checked />否';
                }
                str1 += '</td>' +
                    '</tr>';
                $("#tab1").append(str1);
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function enable(action) {
    var radio = $('input[name="enable"]');
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            userid = radio[i].value;
        }
    }
    var autoBackupEnable = userid;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/backup/updateConfig ",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "autoBackupEnable": autoBackupEnable
        }),
        success: function (data) {
            //data.onSuccess = function () {
            layer.alert("开启自动备份成功！", {
                icon: 1,
                skin: 'layer-ext-moon',
                btn: ['关闭'],
                yes: function (index, layero) {
                    getEnable();
                    layer.close(index);
                }
            });
            //}
            //processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//翻页调用
function PageCallback(index) {
    InitTable(index.getCurrent() - 1);
}
function InitTable(index) {
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/backup/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#tab").empty();
                for (var i = 0; i < result.backupList.length; i++) {
                    if (result.backupList[i].createFrom == 1) {
                        result.backupList[i].createFrom = "手动";
                    }
                    if (result.backupList[i].createFrom == 2) {
                        result.backupList[i].createFrom = "自动";
                    }
                    var str = '<tr>' +
                        '<td class="text-center">' + parseInt(index * pageSize + i + 1) + '</td>' +
                        '<td style="text-align: center;display:none">' + result.backupList[i].backupId + '</td>' +
                        '<td class="text-center">' + result.backupList[i].name + '</td>' +
                        '<td class="text-center">' + result.backupList[i].description + '</td>' +
                        '<td class="text-center">' + result.backupList[i].createFrom + '</td>' +
                        '<td class="text-center">' + getMyDate(result.backupList[i].createTime) + '</td>' +
                        '<td class="text-center">' + bytesToSize(result.backupList[i].size) + '</td>' +
                        '<td class="text-center">' + result.backupList[i].location + '</td>' +
                        '<td class="text-center"><a href="javaScript:;;"><i style="margin-right:5px;"  class="del-data fa fa-trash-o"' +
                        ' onclick="delTr(this,' + index + ')"></i></a></td>' +
                        '</tr>';

                    $("#tab").append(str);
                    $("#tab td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }
                if (index == 0) {
                    $('.M-box3').pagination({
                        coping: true,
                        jump: true,
                        pageCount: result.totalPages,
                        callback: PageCallback
                    });
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function addBackup() {
    $("#data").val("");
    layer.open({
        type: 1,
        title: '创建新的数据库备份',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '155px'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $(".error-span").text("");
            var keys = 0;
            var description = $("#data").val();
            if (description == null || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".error-span").text("备份描述不能为空！");
                keys = 1;
            }
            if (keys == 0) {
                addTr2();
                layer.close(index);
            }
        },
        content: $('#Database')
    });
}
function addTr2() {
    var description = $("#data").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/backup/add",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "description": description
        }),
        success: function (data) {
            data.onSuccess = function () {
                InitTable(0);
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
function delTr(ckb, index) {
    layer.alert("是否确定删除当前备份？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var backupId = $(ckb).parent().parent().parent().find("td").eq(1).text();
            $.ajax({
                url: host + "/dcms/api/v1/backup/deleteById",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "backupId": backupId
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        $(ckb).parent().parent().parent().remove();
                        InitTable(index);
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });

}