/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-1
 * Time: 下午1:46
 * To change this template use File | Settings | File Templates.
 */
var sysChart;
var secChart;
if($(window).width() >= 1920){
    $("#sidebar").css("height","89%")
}else if($(window).width() >= 1280 && $(window).width() <1920){
    $("#sidebar").css("height","84%")
}
$(function(){
    subok();
    sysChart = echarts.init(document.getElementById('sysadmin'));
    secChart = echarts.init(document.getElementById('secadmin'));
    initSysadmim(["登录","注销","修改密码","重置用户密码","用户与组织管理","终端管理","配置管理","授权许可","组件管理","备份管理","集成管理"], [{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0}]);
    initSecadmim(["登录","注销","修改密码","用户与组织管理","用户角色管理","定密策略管理"], [{"value":0},{"value":0},{"value":0},{"value":0},{"value":0},{"value":0}]);
    sysadmimOp();
    secadminOp();
    var hash = window.location.hash;
    var hashs = hash.slice(1);
    if (hash != '') {
        if ($("." + hashs).parent().parent().siblings() != 0) {
            $("." + hashs).parent().parent().siblings().each(function(){
                $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
                // 修改数字控制速度， slideUp(500)控制卷起速度
                $(this).next(".navContent").slideToggle(100)
            });
        }
        $("." + hashs).click();
    }
    $(".has-sub").click(function(){
        $(".has-sub").removeClass("action");
        $(this).addClass("action");
    })
    $(".subNav").click(function(){
        $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
        // 修改数字控制速度， slideUp(500)控制卷起速度
        $(this).next(".navContent").slideToggle(100).siblings(".navContent").slideUp(100);
    })
    $(".logadmin").click(function(){
        $("#main-content").empty();
        $("#main-content").load("logadmin.html")
    })
    if (hash == "#logadmin") {
        $(".logadmin").click();
    }
});
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
                if (result.level == 2) {
                    result.level = "秘密"
                    $("#displaylevel").text("系统定级："+result.level)
                } else if(result.level == 3) {
                    result.level = "机密"
                    $("#displaylevel").text("系统定级："+result.level)
                } else if(result.level == 4) {
                    result.level = "绝密"
                    $("#displaylevel").text("系统定级："+result.level)
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function initSysadmim(x, y) {
    sysoption={
        "title":{
            "subtext":""
        },
        "tooltip":{
            "trigger":"axis"
        },
        "legend":{
            "data":["次数"],
            "show":true
        },
        "calculable":true,
        "toolbox":{
            "show":true,"feature":{
                "mark":{
                    "show":false
                },
                "dataView":{
                    "show":false,"readOnly":false
                },"saveAsImage":{
                    "show":true
                },"magicType":{
                    "show":true,"type":["bar","line"]
                }
            }
        },
        "xAxis":[{
            "type":"category",
            "data":x}],
        "yAxis":[{
            "type":"value"
        }],
        "series":[{
            "name":"次数",
            "type":"bar",
            "data":y,
            "markLine":{
                "data":[{
                    "type":"average","name":"平均值"
                }]
            },"markPoint":{
                "data":[{
                    "type":"max","name":"最大值"},{"type":"min","name":"最小值"
                }]
            }
        }]
    };
    sysChart.setOption(sysoption);
}


function initSecadmim(x, y){
    secoption={
        "title":{
            "subtext":""
        },
        "tooltip":{
            "trigger":"axis"
        },
        "legend":{
            "data":["次数"],
            "show":true
        },
        "calculable":true,
        "toolbox":{
            "show":true,"feature":{
                "mark":{
                    "show":false
                },
                "dataView":{
                    "show":false,"readOnly":false
                },"saveAsImage":{
                    "show":true
                },"magicType":{
                    "show":true,"type":["bar","line"]
                }
            }
        },
        "xAxis":[{
            "type":"category",
            "data":x
        }],
        "yAxis":[{
            "type":"value"
        }],
        "series":[{
            "name":"次数",
            "type":"bar",
            "data":y,
            "markLine":{
                "data":[{
                    "type":"average","name":"平均值"
                }]
            },"markPoint":{
                "data":[{
                    "type":"max","name":"最大值"},{"type":"min","name":"最小值"
                }]
            }
        }]
    };
    secChart.setOption(secoption);
}

function sysadmimOp() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/adminUsage",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "adminType": 2,
            "threshold": $("#sysadminThreshold option:selected").val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                var option = sysChart.getOption();
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                sysChart.setOption(option);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function secadminOp() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/adminUsage",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "adminType": 3,
            "threshold": $("#secadminThreshold option:selected").val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                var option = secChart.getOption();
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                secChart.setOption(option);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

