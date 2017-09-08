/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-21
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */

var host = window.location.protocol + "//" + window.location.host;
//var host="http://47.93.78.186:8080";
// var host = "http://192.168.0.3:8080";
//var host = "http://192.168.0.11:8080";
$(function () {
    changeDisplay()
    //fid或者workFlowId
    var workflowId = GetQueryString("workflowId");
    var fid = GetQueryString("fid");
    var fileName = GetQueryString("fileName");
    //初始化我的待办项
    if (workflowId == null && fid == null) {
        //myapply(0,2);
        //myUnfinished(0);
        //$("#loading").click();
    } else if (workflowId != null) {
        $.ajax({
            type: "post",
            url: host + "/dcms/api/v1/workFlow/queryById",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            crossDomain: false,
            data: JSON.stringify({
                "token": token,
                "workFlowId": workflowId
            }),
            success: function (result) {
                if (result.code == 0) {

                    if (result.flowState == 1) {
                        finishedInfo(workflowId, 1);
                        //$("#fapplys").click();
                    } else {
                        workflowInfo(workflowId);
                        $("#loading").click();
                    }
                }
            }
        });

    }else if(fid != null && fileName != null){
    	track(fid, 1, fileName);
    	$("#fileInbox").click();
    } else if (fid != null) {
        //通过消息查看文件详情
        findFileById(fid, 1, 1);
        $("#fileInbox").click();
    }
})
//取url参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}

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

//将时间戳转化为日期
function add0(m) {
    return m < 10 ? '0' + m : m
}
function getDate(shijianchuo) {
//shijianchuo是整数，否则要parseInt转换
    if (shijianchuo == undefined || shijianchuo == 0) {
        return "";
    }
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    return y + '/' + add0(m) + '/' + add0(d);
}
var token;
token = GetQueryString("token");

var str = '<ul>' +
    '<li id="index" class="has-sub"><a id="first" href="index.html?token=' + token + '#index" ><i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">流程管理</span></a></li>' +
    '<li id="myselfInfo" class="has-sub"><a id="two" href="myselfInfo.html?token=' + token + '#myselfInfo"><i class="fa fa-bookmark-o fa-fw"></i> <span class="menu-text">个人台账</span></a></li>' +
    '<li id="ledgermanagement" class="has-sub" style="display: block;"><a id="tree" href="ledgermanagement.html?token=' + token + '#ledgermanagement"><i class="fa fa-list fa-fw"></i> <span class="menu-text">台账管理</span></a></li>' +
    '<li id="statistics" class="has-sub" style="display: block;"><a id="tree" href="statistics.html?token=' + token + '#statistics"><i class="fa fa-columns fa-fw"></i> <span class="menu-text">统计分析</span></a></li>' +
    '</ul>';
var str1 = '<ul>' +
    '<li id="index" class="has-sub"><a id="first" href="index.html?token=' + token + '#index"><i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">流程管理</span></a></li>' +
    '<li id="myselfInfo" class="has-sub"><a id="two" href="myselfInfo.html?token=' + token + '#myselfInfo"><i class="fa fa-bookmark-o fa-fw"></i> <span class="menu-text">个人台账</span></a></li>' +
    '<li id="statistics" class="has-sub" style="display: block;"><a id="tree" href="statistics.html?token=' + token + '#statistics"><i class="fa fa-columns fa-fw"></i> <span class="menu-text">统计分析</span></a></li>' +
    '</ul>';
function changeDisplay() {

    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/user/queryRoleType",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            if (result.code == 0) {
                var key = 0;

                for (var i = 0; i < result.roleTypes.length; i++) {

                    if (result.roleTypes[i] == 2) {
                        key = 1;
                    }
					if (result.roleTypes[i] == 3) {
                        key = 2;
                    }
                }

                if (key == 1) {
                    $(".menu-left").append(str);
                    $("#finishedwf").show();
                    $("#myUnfinished").show();
                    $("#myapplyli0").show();
                    $("#myapplyli1").show();
                } else if(key == 2){
                    $(".menu-left").append(str1);
					$("#finishedwf").show();
                    $("#myUnfinished").show();
                    $("#myapplyli0").show();
                    $("#myapplyli1").show();
                }else{
					$(".menu-left").append(str1);
                    $("#myapplyli0").show();
                    $("#myapplyli1").show();
				}
                var index = window.location.pathname;
                var hash = window.location.hash;
                if (index.indexOf("index") != -1) {
                    $("#index").addClass("action");
                }else if (index.indexOf("myselfInfo") != -1) {
                    $("#myselfInfo").addClass("action");
                }else if (index.indexOf("statistics") != -1) {
                    $("#statistics").addClass("action");
                }
                var id = hash.slice(1);
                $("#" + id).addClass("action");
            }
        }
    });
}

//紧急程度
function setfileurgency(code) {
    if (code == 0) {
        return '一般';
    } else if (code == 1) {
        return '重要';
    } else {
        return '加急';
    }
}


