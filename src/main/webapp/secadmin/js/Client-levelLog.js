/**
 * Created by lenovo on 17-2-23.
 */
$(function () {
    var start = {
        elem: '#start',
        //format: 'YYYY-MM-DD hh:mm:ss',
        //istime: true,
        istoday: true
    };
    var end = {
        elem: '#end',
        //format: 'YYYY-MM-DD hh:mm:ss',
        //istime: true,
        istoday: true
    };
    laydate(start);
    laydate(end);
    getProcess(0);
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
//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}
//翻页调用
function PageCallback(index) {
    getProcess(index.getCurrent() - 1);
}
function getProcess(index) {
    var riskLevel = $("#roleType option:selected").val();
    var operate = $("#operType option:selected").val();
    var pageSize = 10;
    var operateTypes = new Array();
    if(operate==''){
        $("#operType option").each(function () {
            if ($(this).val() != '') {
                operateTypes.push($(this).val());
            }
        });
    }else{
    	operateTypes.push(operate);
    }
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/log/queryClientLog",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize,
            "riskLevel": riskLevel,
            "operateTimeStart": new Date($("#start").val()).getTime(),
            "operateTimeEnd":  new Date($("#end").val()).getTime(),
            "keyword":$(".keyword").val(),
            "operateTypes": operateTypes
        }),
        success: function (result) {
            result.onSuccess = function () {
                var datas = result.clientLogList;
                $("#list").empty();
                var da;
                if (datas.length > 0) {
                    for (var i = 0; i < datas.length; i++) {
                        var num = index * pageSize + parseInt(i) + 1;
                        if (datas[i].operateResult == 1) {
                            datas[i].operateResult = "成功";
                        }
                        if (datas[i].operateResult == 2) {
                            datas[i].operateResult = "失败";
                        }
                        if (datas[i].riskLevel == 1) {
                            datas[i].riskLevel = "一般";
                        }
                        if (datas[i].riskLevel == 2) {
                            datas[i].riskLevel = "较重";
                        }
                        if (datas[i].riskLevel == 3) {
                            datas[i].riskLevel = "严重";
                        }
                        da = getLocalTime(datas[i].operateTime);

                        var str = '<tr>' +
                            '<td style="text-align: center;">' + num + '</td>' +
                            '<td style="text-align: center;">' + da + '</td>' +
                            '<td style="text-align: center;">' + datas[i].operateType + '</td>' +
                            '<td style="text-align: center;">' + datas[i].operateDescription + '</td>' +
                            '<td style="text-align: center;">' + datas[i].operateResult + '</td>' +
                            '<td style="text-align: center;">' + datas[i].user.name + '</td>';
                        if (datas[i].riskLevel == "一般") {
                            str += '<td style="text-align: center;"><i class="text" style="padding:0 10px;background:#666666"></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                        }
                        if (datas[i].riskLevel == "较重") {
                            str += '<td style="text-align: center;"><i class="text" style="padding:0 10px;background:#cc3300"></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                        }
                        if (datas[i].riskLevel == "严重") {
                            str += '<td style="text-align: center;"><i class="text" style="padding:0 10px;background:#ff0000"></i><span style="margin-left:5px;">' + datas[i].riskLevel + '</span></td>';
                        }

                        str += '</tr>';
                        $("#list").append(str);
                        $("#list  td").each(function () {
                            $(this).attr("title", $(this).text());

                        });
                    }
                }
                if (index == 0) {
                    $('.M-box3').pagination({
                        coping: true,
                        jump:true,
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
function seachUser() {
    getProcess(0);
}

