/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-1
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
var userOnlineChart;
if($(window).width() >= 1920){
    $("#sidebar").css("height","89%")
}else if($(window).width() >= 1280 && $(window).width() <1920){
    $("#sidebar").css("height","84%")
}
$(function () {
    subok();
    userOnlineChart = echarts.init(document.getElementById('userstatus'));
    systemResourse();
    initUserOnline();
    refreshUserOnline();
    var hash = window.location.hash;
    var hashs = hash.slice(1);
    if (hash != '') {
        if ($("." + hashs).parent().parent().siblings() != 0) {
            $("." + hashs).parent().parent().siblings().each(function(){
                $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
                // 修改数字控制速度， slideUp(500)控制卷起速度
                $(this).next(".navContent").slideToggle(100);
            });
        }
        $("." + hashs).click();
    }

    $(".has-sub").click(function () {
        $(".has-sub").removeClass("action");
        $(this).addClass("action");
    })

    $(".subNav").click(function () {
        $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
        // 修改数字控制速度， slideUp(500)控制卷起速度
        $(this).next(".navContent").slideToggle(100).siblings(".navContent").slideUp(100);;
    })
    $(".Authorization").click(function () {
        $("#main-content").empty();
        $("#main-content").load("Authorization.html")
    })
    $(".system-information").click(function () {
        $("#main-content").empty();
        $("#main-content").load("system-information.html")
    })
    $(".resource-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("resource-management.html")
    })
    $(".compont-manager").click(function () {
        $("#main-content").empty();
        $("#main-content").load("compont-manager.html")
    })
    $(".Database-backup-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("Database-backup-management.html")
    })
    $(".File-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("File-management.html")
    })
    $(".Mail-storage-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("Mail-storage-management.html")
    })
    $(".User-authentication-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("User-authentication-management.html")
    })
    $(".Advanced-configuration-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("Advanced-configuration-management.html")
    })
    $(".integration-management").click(function () {
        $("#main-content").empty();
        $("#main-content").load("integration-management.html")
    })
    $(".client_info").click(function () {
        $("#main-content").empty();
        $("#main-content").load("client_info.html")
    })
    $(".client_Patch").click(function () {
        $("#main-content").empty();
        $("#main-content").load("client_Patch.html")
    })
    $(".Terminal-statistics").click(function () {
        $("#main-content").empty();
        $("#main-content").load("Terminal-statistics.html")
    })
    $(".user-formork").click(function () {
        $("#main-content").empty();
        $("#main-content").load("user-formork.html")
    })
    $(".user-group").click(function () {
        $("#main-content").empty();
        $("#main-content").load("user-group.html")
    })
    if (hash == "#Authorization") {
        $(".Authorization").click();
    }
    if (hash == "#system-information") {
        $(".system-information").click();
    }
    if (hash == "#resource-management") {
        $(".resource-management").click();
    }
    if (hash == "#compont-manager") {
        $(".compont-manager").click();
    }
    if (hash == "#Database-backup-management") {
        $(".Database-backup-management").click();
    }
    if (hash == "#File-management") {
        $(".File-management").click();
    }
    if (hash == "#Mail-storage-management") {
        $(".Mail-storage-management").click();
    }
    if (hash == "#User-authentication-management") {
        $(".User-authentication-management").click();
    }
    if (hash == "#Advanced-configuration-management") {
        $(".Advanced-configuration-management").click();
    }
    if (hash == "#integration-management") {
        $(".integration-management").click();
    }
    if (hash == "#client_info") {
        $(".client_info").click();
    }
    if (hash == "#client_Patch") {
        $(".client_Patch").click();
    }
    if (hash == "#Terminal-statistics") {
        $(".Terminal-statistics").click();
    }
    if (hash == "#user-formork") {
        $(".user-formork").click();
    }
    if (hash == "#user-group") {
        $(".user-group").click();
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
function initUserOnline() {
    //用户在线状态
    option = {
        "tooltip": {
            "trigger": "axis"
        },
        "legend": {
            "data": ["人数"],
            "show": true
        },
        "calculable": true,
        "toolbox": {
            "show": true, "feature": {
                "mark": {
                    "show": false
                },
                "dataView": {
                    "show": false, "readOnly": false
                }, "saveAsImage": {
                    "show": true
                }, "magicType": {
                    "show": true, "type": ["line", "bar"]
                }
            }
        },
        "xAxis": [{
            "type": "category",
            "data": ["总数", "在线", "离线"]
        }],
        "yAxis": [{
            "type": "value"
        }],
        "series": [{
            "name": "人数",
            "type": "bar",
            "data": [{"value": 0}, {"value": 0}, {"value": 0}],
            "markLine": {
                "data": [{
                    "type": "average", "name": "平均值"
                }]
            }, "markPoint": {
                "data": [{
                    "type": "max", "name": "最大值"
                }, {
                    "type": "min", "name": "最小值"
                }]
            }
        }]
    };
    userOnlineChart.setOption(option);
}

function refreshUserOnline() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/userOnline",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var option = userOnlineChart.getOption();
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                userOnlineChart.setOption(option);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function systemResourse() {
    var cpuChart = echarts.init(document.getElementById('cpu'));
    var memoryChart = echarts.init(document.getElementById('memory'));
    var hardChart = echarts.init(document.getElementById('hard'));


    var Option = {

        "title": {
            "text": "占用率",
            "subtext": "实时数据"
        },
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },

        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },

        series: [
            {
                name: '系统资源',
                type: 'gauge',
                detail: {
                    borderWidth: 0,
                    borderColor: '#fff',
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 5,
                    offsetCenter: [0, '60%'], // x, y，单位px
                    formatter: '{value}%',
                    textStyle: {
                        color: 'auto',
                        fontSize: 18,
                        fontFamily:"微软雅黑",
                        fontWeight: "weight"
                    }
                },
                pointer: {
                    width: 4,
                    shadowColor: '#fff', //默认透明
                    shadowBlur: 5
                },
                data: [{value: 0}]


            }
        ]
    };
    cpuChart.setOption(Option);
    memoryChart.setOption(Option);
    hardChart.setOption(Option);
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/environment",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                for (var i = 0; i < result.resourceList.length; i++) {
                    if (result.resourceList[i].type == 1) {
                        var option = cpuChart.getOption();
                        if (result.resourceList[i].percentage != '异常') {
                            option.series[0].data[0].value = result.resourceList[i].percentage;
                            option.title[0].text = result.resourceList[i].resourceName;
                        } else {
                            option.title[0].text = "CPU异常";
                        }

                        cpuChart.setOption(option);
                    } else if (result.resourceList[i].type == 2) {
                        var option = memoryChart.getOption();
                        option.series[0].data[0].value = result.resourceList[i].percentage;
                        option.title[0].text = result.resourceList[i].resourceName;
                        memoryChart.setOption(option);
                    } else {
                        var option = hardChart.getOption();
                        option.series[0].data[0].value = result.resourceList[i].percentage;
                        option.title[0].text = result.resourceList[i].resourceName;
                        hardChart.setOption(option);
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


