/**
 * Created by besthyhy on 17-2-16.
 */
$(function () {
    InitTable();
})
function control(e, o) {
    var v = o.value;
    if (v < 0 || v > 99) {
        o.value = '';
        o.focus();
    }
};
function InitTable() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryValidPeriodList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#box_tab1 .plist").empty();
                var data = result.validPeriodList;
                for (var i = 0; i < data.length; i++) {
                    //alert(data[4].validPeriod.slice(0 ,1));
                    if (data[i].validPeriod.slice(0, 1) == 0) {
                        data[i].validPeriod = data[i].validPeriod.slice(1, 2);
                    } else {
                        data[i].validPeriod = data[i].validPeriod.slice(0, 2);
                    }
                }
                //alert(data[4].validPeriod.slice(0 ,1));
                var str = '<tr>' +
                    '<td>绝密级默认保密期限设置:</td>' +
                    '<td style="display:none;">' + data[4].fileLevel + '</td>' +
                    '<td><input type="text" value="' + parseInt(data[4].validPeriod) + '" onkeyup="control(event,this)"/><span style="color:red;">*(单位为：年，为整数)</span>  <span style="padding:0 10px;"></span><a href="javaScript:;;"><i class="fa fa-save" onclick="xiugai(this)"></i></a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>机密级默认保密期限设置:</td>' +
                    '<td style="display:none;">' + data[3].fileLevel + '</td>' +
                    '<td><input type="text" value="' + parseInt(data[3].validPeriod) + '" onkeyup="control(event,this)"/><span style="color:red;">*(单位为：年，为整数)</span> <span style="padding:0 10px;"></span><a href="javaScript:;;"><i class="fa fa-save" onclick="xiugai(this)"></i></a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>秘密级默认保密期限设置:</span></td>' +
                    '<td style="display:none;">' + data[2].fileLevel + '</td>' +
                    '<td><input type="text" value="' + parseInt(data[2].validPeriod) + '" onkeyup="control(event,this)"/><span style="color:red;">*(单位为：年，为整数)</span> <span style="padding:0 10px;"></span><a href="javaScript:;;"><i class="fa fa-save" onclick="xiugai(this)"></i></a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>内部级默认保密期限设置:<span style="color:red;"></span></td>' +
                    '<td style="display:none;">' + data[1].fileLevel + '</td>' +
                    '<td><input type="text" value="' + parseInt(data[1].validPeriod) + '" onkeyup="control(event,this)"/><span style="color:red;">(单位为：年，为整数)可为空</span> <span style="padding:0 10px;"></span><a href="javaScript:;;"><i class="fa fa-save" onclick="xiugai(this)"></i></a></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="background: #efefef">公开级默认保密期限设置:</td>' +
                    '<td style="display:none;">' + data[0].fileLevel + '</td>' +
                    '<td><input type="text" value="' + parseInt(data[0].validPeriod) + '" onkeyup="control(event,this)"/><span style="color:red;">(单位为：年，为整数)可为空</span> <span style="padding:0 10px;"></span><a href="javaScript:;;"><i class="fa fa-save" onclick="xiugai(this)"></i></a></td>' +
                    '</tr>';
                $("#box_tab1 .plist").append(str);
            };
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function xiugai(t) {
    var validPeriod = parseInt($(t).parent().parent().find("input").eq(0).val())
    if (validPeriod < 10) {
        validPeriod = '0' + validPeriod + '0000';
    } else {
        validPeriod = validPeriod + '0000';
    }
    //alert(validPeriod)
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/updateValidPeriod",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "fileLevel": $(t).parent().parent().prev().text(),
            "validPeriod": validPeriod
        }),
        success: function (result) {
            result.onSuccess = function () {
                InitTable();
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function showtab2() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryFileAccessPolicyList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var data = result.fileAccessPolicyList;
                var ids;
                for (var i = 0; i < data.length; i++) {
                    var ids = data[i].fileLevel * 10 + data[i].userLevel;
                    if (data[i].enable == 1) {
                        document.getElementById(ids).checked = true;
                    } else {
                        document.getElementById(ids).checked = false;
                    }
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function updatatab2(s) {
    var aa;
    if (document.getElementById(s).checked == true) {
        aa = 1
    }
    if (document.getElementById(s).checked == false) {
        aa = 0
    }
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/updateFileAccessPolicy",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "fileLevel": Math.floor(s / 10 % 10),
            "userLevel": s % 10,
            "enable": aa

        }),
        success: function (result) {
            //result.onSuccess = function () {
            showtab2();
            //}
            //processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function showtab3() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryClientAccessPolicyList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token

        }),
        success: function (result) {
            result.onSuccess = function () {
                var data = result.fileAccessPolicyList;
                var ids;
                for (var i = 0; i < data.length; i++) {
                    ids = data[i].clientLevel * 10 + data[i].userLevel
                    if (data[i].enable == 1) {
                        document.getElementById("tab3" + ids.toString()).checked = true;
                    } else {
                        document.getElementById("tab3" + ids.toString()).checked = false;
                    }
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function updatatab3(s) {
    var aa;
    if (document.getElementById("tab3" + s).checked == true) {
        aa = 1
    }
    if (document.getElementById("tab3" + s).checked == false) {
        aa = 0
    }
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/updateClientAccessPolicy",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "clientLevel": Math.floor(s / 10 % 10),
            "userLevel": s % 10,
            "enable": aa

        }),
        success: function (result) {
            //result.onSuccess = function () {
            showtab3();
            //}
            //processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function queryKeywordPolicy(){
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryKeywordPolicy",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            console.log(result);
            result.onSuccess = function () {
                $("#box_tab4 .plist").empty();
                var str = '<tr>' +
                    '<td>公开密点分析策略设置:</td>' +
                    '<td><textarea id="keyword0" rows="1" cols="90">' + result.keyword0 + '</textarea><span>*关键词间用|分割</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>内部密点分析策略设置:</td>' +
                    '<td><textarea id="keyword1" rows="1" cols="90">' + result.keyword1 + '</textarea><span>*关键词间用|分割</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>秘密密点分析策略设置:</span></td>' +
                    '<td><textarea id="keyword2" rows="1" cols="90">' + result.keyword2 + '</textarea><span>*关键词间用|分割</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>机密密点分析策略设置:</td>' +
                    '<td><textarea id="keyword3" rows="1" cols="90">' + result.keyword3 + '</textarea><span>*关键词间用|分割</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="background: #efefef">绝密密点分析策略设置:</td>' +
                    '<td><textarea id="keyword4" rows="1" cols="90">' + result.keyword4 + '</textarea><span>*关键词间用|分割</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="background: #efefef;text-align:center;" colspan="2"><button class="btn btn-primary" onclick="updateKeywordPolicy()">保存</button></td>' +
                    '</tr>';
                $("#box_tab4 .plist").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function updateKeywordPolicy(){
    var  keyword0=$("#keyword0").val();
    var  keyword1=$("#keyword1").val();
    var  keyword2=$("#keyword2").val();
    var  keyword3=$("#keyword3").val();
    var  keyword4=$("#keyword4").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/updateKeywordPolicy",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "keyword0": keyword0,
            "keyword1": keyword1,
            "keyword2": keyword2,
            "keyword3": keyword3,
            "keyword4": keyword4
        }),
        success: function (result) {
            result.onSuccess = function () {
                queryKeywordPolicy();
            };
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}