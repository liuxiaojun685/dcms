/**
 * Created by liuxiaojun on 17/4/10.
 */
/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-1
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */

var clientChart;
$(function(){
    systemResourse();
    refreshClientOnline();
});
function systemResourse(){
    clientChart = echarts.init(document.getElementById('userstatus'));
    //用户在线状态
    option={
        "tooltip":{
            "trigger":"axis"
        },
        "legend":{
            "data":["数量"],
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
                },"restore":{
                    "show":true
                },"saveAsImage":{
                    "show":true
                },"magicType":{
                    "show":true,"type":["line","bar"]
                }
            }
        },
        "xAxis":[{
            "type":"category",
            "data":["总数","在线","离线"]}],
        "yAxis":[{
            "type":"value"
        }],
        "series":[{
            "name":"人数",
            "type":"line",
            "data":[{"value":0},{"value":0},{"value":0}],
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
    clientChart.setOption(option);
}

function refreshClientOnline() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/statistics/clientOnline",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var option = clientChart.getOption();
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
                clientChart.setOption(option);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}


