/**
 * Created by lenovo on 17-2-17.
 */

$(function () {
    var start = {
        elem: '#start',
        format: 'YYYY/MM/DD hh:mm:ss',
        istime: true,
        istoday: true
    };
    var end = {
        elem: '#end',
        format: 'YYYY/MM/DD hh:mm:ss',
        istime: true,
        istoday: true
    };
    laydate(start);
    laydate(end);
    getdate(0)
});

function pageCallback(index) {

    getdate(index.getCurrent() - 1);
}

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
function getdate(index) {

    var begintime = $("#start").val();
    var lastLoginTimeStart = new Date(begintime).getTime();
    var endtime = $("#end").val();
    var lastLoginTimeEnd = new Date(endtime).getTime();
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/log/queryAdminLog",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize,
            "operateType": $(".state option:selected").val(),
            "keyword": $('.ke').val(),
            "operateTimeStart": lastLoginTimeStart,
            "operateTimeEnd": lastLoginTimeEnd,
            "adminType": $('.leixing option:selected').val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list").empty();
                var datas = result.adminLogList;

                for (var i = 0; i < datas.length; i++) {
                    if (datas[i].riskLevel == 1) {
                        datas[i].riskLevel = "一般"
                    }
                    if (datas[i].riskLevel == 2) {
                        datas[i].riskLevel = "较重"
                    }
                    if (datas[i].riskLevel == 3) {
                        datas[i].riskLevel = "严重"
                    }
                    var operateTime = getMyDate(datas[i].operateTime);
                    //console.log(operateTime);
                    var num = index * pageSize + parseInt(i) + 1;
                    var str = '<tr>' +
                        '<td style="display: none;"><input type="checkbox" name="ckb"></td>' +
                        '<td class="text-center">' + num + '</td>';
                    if (datas[i].admin.name == null) {
                        str += '<td style="display:none;"><span style="padding:0 10px;"></span></td>';
                    }
                    if (datas[i].admin.name != null) {
                        str += '<td style="display:none;">' + datas[i].admin.name + '</td>';
                    }
                    if (datas[i].admin.account == null) {
                        str += '<td><span style="padding:0 10px;"></span></td>';
                    }
                    if (datas[i].admin.account != null) {
                        str += '<td class="text-center">' + datas[i].admin.account + '</td>';
                    }
                    str += '<td class="text-center">' + operateTime + '</td>' +
                        '<td class="text-center">' + datas[i].operateType + '</td>' +
                        '<td class="text-center">' + datas[i].operateDescription + '</td>' +
                        '<td class="text-center">' + datas[i].ip + '</td>';
                    if (datas[i].riskLevel == "一般") {
                        str += '<td class="text-center"><i class="text" style="padding:0 10px;background:#666666; "></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                    }
                    if (datas[i].riskLevel == "较重") {
                        str += '<td class="text-center"><i class="text" style="padding:0 10px;background:#cc3300; "></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                    }
                    if (datas[i].riskLevel == "严重") {
                        str += '<td class="text-center"><i class="text" style="padding:0 10px;background:#ff0000; "></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                    }
                    str += '</tr>';
                    $(".list").append(str);

                }
                $(".downLog").unbind("click").click(function () {
                    downLog();
                })
                if (index == 0) {
                    $('.M-box3').pagination({
                        pageCount: result.totalPages,
                        coping: true,
                        jump: true,
                        callback: pageCallback
                    });
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })

}
//下载日志
function downLog() {
    var begintime = $("#start").val();
    var lastLoginTimeStart = new Date(begintime).getTime();
    if (isNaN(lastLoginTimeStart)) {
        lastLoginTimeStart = '';
    }
    var endtime = $("#end").val();
    var lastLoginTimeEnd = new Date(endtime).getTime();
    if (isNaN(lastLoginTimeEnd)) {
        lastLoginTimeEnd = '';
    }
    var operateType = $(".state option:selected").val();
    var keyword = $('.ke').val();
    var adminType = $('.leixing option:selected').val();
    var url = host + "/dcms/api/v1/log/exportAdminLog";
    url = url + "?token=" + token + "&adminType=" + adminType + "&keyword=" + keyword + "&operateType=" + operateType + "&operateTimeEnd=" + lastLoginTimeEnd + "&operateTimeStart=" + lastLoginTimeStart;
    var excelhref = $("#excelhref");
    excelhref.attr("href", url);
    $("#excel").click();
}
function logArchive() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/log/logArchive",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                getdate(0);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function seachUser() {
    getdate(0);
}