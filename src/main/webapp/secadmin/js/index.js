/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-1
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
function formatLaydate(elem) {
    var date = {
        elem: elem,
        format: 'YYYY/MM/DD',
        istoday: true,
        choose: function(datas){
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    laydate(date);
}
if($(window).width() >= 1920){
    $("#sidebar").css("height","89%")
}else if($(window).width() >= 1280 && $(window).width() <1920){
    $("#sidebar").css("height","84%")
}
$(function () {
    subok();
    refreshFileChart();
    refreshWorkflowChart();
    refreshFileTrendChart();
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
    $(".systemLevel").click(function(){
        $("#main-content").empty();
        $("#main-content").load("systemLevel.html")
    })
    $(".user_management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("user_management.html")
    })
    $(".management_role").click(function(){
        $("#main-content").empty();
        $("#main-content").load("management_role.html")
    })
    $(".role_info").click(function(){
        $("#main-content").empty();
        $("#main-content").load("role_info.html")
    })
    $(".role_file").click(function(){
        $("#main-content").empty();
        $("#main-content").load("role_file.html")
    })
    $(".Privilege-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Privilege-management.html")
    })
    $(".businessAttr").click(function(){
        $("#main-content").empty();
        $("#main-content").load("businessAttr.html")
    })
    $(".WorkFlowPolicy").click(function(){
        $("#main-content").empty();
        $("#main-content").load("WorkFlowPolicy.html")
    })
    $(".Fixed-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Fixed-management.html")
    })
    $(".Fixed-density-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Fixed-density-management.html")
    })
    $(".Fixed-strategy-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Fixed-strategy-management.html")
    })
    $(".Advanced-security-policy").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Advanced-security-policy.html")
    })
    $(".File-ledger-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("File-ledger-management.html")
    })
    $(".Process-audit-log-audit").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Process-audit-log-audit.html")
    })
    $(".Client-Log").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Client-Log.html")
    })
    $(".Client-levelLog").click(function(){
        $("#main-content").empty();
        $("#main-content").load("Client-levelLog.html")
    })
    $(".ClientRequestLog").click(function(){
        $("#main-content").empty();
        $("#main-content").load("ClientRequestLog.html")
    })
    $(".statistics-analysis").click(function(){
        $("#main-content").empty();
        $("#main-content").load("statistics-analysis.html")
    })
    $(".key-management").click(function(){
        $("#main-content").empty();
        $("#main-content").load("key-management.html")
    })
    if (hash == "#systemLevel") {
        $(".systemLevel").click();
    }
    if (hash == "#user_management") {
        $(".user_management").click();
    }
    if (hash == "#management_role") {
        $(".management_role").click();
    }
    if (hash == "#role_info") {
        $(".role_info").click();
    }
    if (hash == "#role_file") {
        $(".role_file").click();
    }
    if (hash == "#Privilege-management") {
        $(".Privilege-management").click();
    }
    if (hash == "#businessAttr") {
        $(".businessAttr").click();
    }
    if (hash == "#WorkFlowPolicy") {
        $(".WorkFlowPolicy").click();
    }
    if (hash == "#Fixed-management") {
        $(".Fixed-management").click();
    }
    if (hash == "#Fixed-density-management") {
        $(".Fixed-density-management").click();
    }
    if (hash == "#Fixed-strategy-management") {
        $(".Fixed-strategy-management").click();
    }
    if (hash == "#Advanced-configuration-management") {
        $(".Advanced-configuration-management").click();
    }
    if (hash == "#Advanced-security-policy") {
        $(".Advanced-security-policy").click();
    }
    if (hash == "#File-ledger-management") {
        $(".File-ledger-management").click();
    }
    if (hash == "#Process-audit-log-audit") {
        $(".Process-audit-log-audit").click();
    }
    if (hash == "#Client-Log") {
        $(".Client-Log").click();
    }
    if (hash == "#Client-levelLog") {
        $(".Client-levelLog").click();
    }
    if (hash == "#ClientRequestLog") {
        $(".ClientRequestLog").click();
    }
    if (hash == "#statistics-analysis") {
        $(".statistics-analysis").click();
    }
    if (hash == "#key-management") {
        $(".key-management").click();
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
function refreshFileChart() {
    var fileChart = echarts.init(document.getElementById('file'));
    var option = {
        "title": {
            "subtext": ""
        },
        "tooltip": {
            "trigger": "axis"
        },
        "legend": {
            "data": ["数量"],
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
            "data": []
        }],
        "yAxis": [{
            "type": "value"
        }],
        "series": [{
            "name": "数量",
            "type": "bar",
            "data": [],
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
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/queryFileList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                fileChart.setOption(option);
            };
            processResponse(result, 0);
        },
        error: function () {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    fileChart.setOption(option);
}

function refreshWorkflowChart() {
    var workflowChart = echarts.init(document.getElementById('workflow'));
    var option = {
        "title": {
            "subtext": ""
        },
        "tooltip": {
            "trigger": "axis"
        },
        "legend": {
            "data": ["数量"],
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
            "data": []
        }],
        "yAxis": [{
            "type": "value"
        }],
        "series": [{
            "name": "数量",
            "type": "bar",
            "data": [],
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
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/queryWorkFlowList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "workflowType": $("#workflowType option:selected").val(),
            "startTime": new Date($("#workflowStart").val()).getTime(),
            "endTime": new Date($("#workflowEnd").val()).getTime()
        }),
        success: function (result) {
            result.onSuccess = function () {
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                workflowChart.setOption(option);
            };
            processResponse(result, 0);
        },
        error: function () {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    workflowChart.setOption(option);
}

function refreshFileTrendChart() {
    var fileTrendChart = echarts.init(document.getElementById('fileTrend'));
    var option = {
        "tooltip": {
            "trigger": "axis"
        },
        "legend": {
            "data": ["正式定密"],
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
            "data": []
        }],
        "yAxis": [{
            "type": "value"
        }],
        "series": [{
            "name": "正式定密",
            "type": "line",
            "data": [],
            "markLine": {
                "data": [{
                    "type": "average", "name": "平均值"
                }]
            },
            "markPoint": {
                "data": [{
                    "type": "max", "name": "最大值"
                }, {
                    "type": "min", "name": "最小值"
                }]
            }
        }]
    };
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/fileTrend",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "by": $("#fileTrendBy option:selected").val(),
            "startTime": new Date($("#start").val()).getTime(),
            "endTime": new Date($("#end").val()).getTime()
        }),
        success: function (result) {
            result.onSuccess = function () {
                option.xAxis[0].data = result.x;
                var legend = new Array();
                for (var i = 0; i < result.points.length; i++) {
                    legend.push(result.points[i].z);
                    option.series[i] = {
                        "name": result.points[i].z,
                        "type": "line",
                        "data": result.points[i].y,
                        "markLine": {
                            "data": [{
                                "type": "average", "name": "平均值"
                            }]
                        },
                        "markPoint": {
                            "data": [{
                                "type": "max", "name": "最大值"
                            }, {
                                "type": "min", "name": "最小值"
                            }]
                        }
                    }
                }
                option.legend.data = legend;
                fileTrendChart.setOption(option);
            };
            processResponse(result, 0);
        },
        error: function () {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    fileTrendChart.setOption(option);
}

