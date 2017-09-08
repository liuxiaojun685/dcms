/**
 * Created by besthyhy on 17-2-16.
 */
//var host = "http://47.93.78.186:8080";
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
$(function () {
    getData(0);
});
//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}
function pageCallback(index) {
    getData(index.getCurrent() - 1);
}
function getData(index) {
    var keyword = $("#keyword").val();
    var pageSize = 10;
    $.ajax({
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: host + "/dcms/api/v1/file/queryList",
        data: JSON.stringify({
                "token": token,
                "pageNumber": index,
                "pageSize": pageSize,
                "keyword": keyword
            }
        ),
        success: function (result) {
            result.onSuccess = function () {
                var table = $("#list");
                table.empty();
                var list = result.fileList;
                for (i = 0; i < result.fileList.length; i++) {
                    //alert(list[i].fileDispatchTime)
                    switch (list[i].fileLevel) {
                        //1公开 2内部 3秘密 4机密 5绝密
                        case 0:
                            list[i].fileLevel = "公开";
                            break;
                        case 1:
                            list[i].fileLevel = "内部";
                            break;
                        case 2:
                            list[i].fileLevel = "秘密";
                            break;
                        case 3:
                            list[i].fileLevel = "机密";
                            break;
                        case 4:
                            list[i].fileLevel = "绝密";
                            break;
                    }
                    switch (list[i].fileState) {
                        //1预定密 2正式定密 3文件签发 4密级变更 5文件解密
                        case 1:
                            list[i].fileState = "预定密";
                            break;
                        case 2:
                            list[i].fileState = "正式定密";
                            break;
                        case 3:
                            list[i].fileState = "文件签发";
                            break;
                        case 4:
                            list[i].fileState = "密级变更";
                            break;
                        case 5:
                            list[i].fileState = "文件解密";
                            break;
                    }
                    if (list[i].fileCreateTime == 0) {
                        list[i].fileCreateTime = '';
                    } else {
                        list[i].fileCreateTime = getLocalTime(list[i].fileCreateTime);
                    }
                    if (list[i].fileDecryptTime == 0) {
                        list[i].fileDecryptTime = '';
                    } else {
                        list[i].fileDecryptTime = getLocalTime(list[i].fileDecryptTime);
                    }
                    if (list[i].fileDispatchTime == 0) {
                        list[i].fileDispatchTime = '';
                    } else {
                        list[i].fileDispatchTime = getLocalTime(list[i].fileDispatchTime);
                    }
                    if (list[i].fileLevelChangeTime == 0) {
                        list[i].fileLevelChangeTime = '';
                    } else {
                        list[i].fileLevelChangeTime = getLocalTime(list[i].fileLevelChangeTime);
                    }
                    if (list[i].fileLevelDecideTime == 0) {
                        list[i].fileLevelDecideTime = '';
                    } else {
                        list[i].fileLevelDecideTime = getLocalTime(list[i].fileLevelDecideTime);
                    }
                    if (list[i].fileValidPeriod == 0) {
                        list[i].fileValidPeriod = '';
                    } else {
                        list[i].fileValidPeriod = getLocalTime(list[i].fileValidPeriod);
                    }
                    if (list[i].fileLevelDecideRoleName == undefined) {
                        list[i].fileLevelDecideRoleName = '';
                    }
                    var master = '<tr>' +
                        '<td style="display:none">' + list[i].fid + '</td>' +
                        '<td style="text-align: center;">' + (index * pageSize + i + 1) + '</td>' +
                        '<td style="text-align: center;">' + list[i].fileName + '</td>' +
                        '<td style="text-align: center;">' + list[i].fileCreateTime + '</td>' +
                        '<td style="text-align: center;">' + list[i].fileLevel + '</td>' +
                        '<td style="text-align: center;">' + list[i].fileState + '</td>' +
                        '<td style="display:none;">' + list[i].fileSize + '</td>' +
                        '<td style="display:none;">' + isUndefined(list[i].fileCreateUserName) + '</td>' +
                        '<td style="display:none;">' + isUndefined(list[i].fileDispatchRoleName) + '</td>' +
                        '<td class="all" onclick="showDetail(this,' + index + ')" style="cursor: pointer;text-align: center;"><a' +
                        ' href="javaScript:;;"><i' +
                        ' class="fa' +
                        ' fa-list"></i></a></td>' +
                        '</tr>';

                    var detail = '<table id="' + list[i].fid + '" style="display:none" class="table table-bordered table-hover' +
                        ' table-condensed">' +
                        '<tr>' +
                        '<td>' + "文件起草日期" + '</td>' +
                        '<td>' + list[i].fileCreateTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件起草人" + '</td>' +
                        '<td>' + isUndefined(list[i].fileCreateUserName) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件解密日期" + '</td>' +
                        '<td>' + list[i].fileDecryptTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件签发人" + '</td>' +
                        '<td>' + isUndefined(list[i].fileDispatchRoleName) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件签发日期" + '</td>' +
                        '<td>' + list[i].fileDispatchTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件密级" + '</td>' +
                        '<td>' + list[i].fileLevel + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件密级变更日期(末次)" + '</td>' +
                        '<td>' + list[i].fileLevelChangeTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "定密责任人(末次)" + '</td>' +
                        '<td>' + list[i].fileLevelDecideRoleName + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "正式定密日期" + '</td>' +
                        '<td>' + list[i].fileLevelDecideTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件存储位置" + '</td>' +
                        '<td>' + list[i].fileLocation + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "主定密单位" + '</td>' +
                        '<td>' + list[i].fileMajorUnit.name + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件MD5" + '</td>' +
                        '<td>' + list[i].fileMd5 + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "辅助定密单位" + '</td>' +
                        '<td>' + isUndefined(list[i].fileMinorUnit.name) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件名称" + '</td>' +
                        '<td>' + list[i].fileName + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "定密状态" + '</td>' +
                        '<td>' + list[i].fileState + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "保密期限" + '</td>' +
                        '<td>' + list[i].fileValidPeriod + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>' + "文件属性详情" + '</td>' +
                        '<td>' + list[i].description + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td style="text-align: center;"colspan="2">' +
                        '<button onclick="showMaster()" class="btn btn-primary">返回</button>' +
                        '</td>' +
                        '</tr>' +
                        '</table>';

                    $(".potions1").append(detail);
                    $("#list").append(master);
                    $("#list  td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }

                if (index == 0) {
                    $('.M-box2').pagination({
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
    });
}

function showDetail(e, index) {
    getData(index);
    var fid = $(e).parent().find("td").eq(0).text();
    $("#" + fid).show();
    $(".potions").hide();
    $(".potions1").show();
}
function showMaster() {
    $(".potions1").hide();
    $(".potions1 table").hide();
    $(".potions").show();
}
function queryby() {
    getData(0);
}

//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}