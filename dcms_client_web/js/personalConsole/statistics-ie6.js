$(function(){
	statisticsfileLevelChart();
	statisticsfileStateChart();
});

function statisticsfileLevelChart(){
	var fileChart = echarts.init(document.getElementById('statisticsfileLevel'));
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
        url: host + "/dcms/api/v1/statistics/fileInLevel",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
        	if(result.code==0) {
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
            };
            fileChart.setOption(option);
        }
    });
    fileChart.setOption(option);
}

function statisticsfileStateChart(){
	var fileChart = echarts.init(document.getElementById('statisticsfileState'));
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
        url: host + "/dcms/api/v1/statistics/fileInState",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            if(result.code==0) {
                option.xAxis[0].data = result.x;
                option.series[0].data = result.y;
            };
            fileChart.setOption(option);
        }
    });
    fileChart.setOption(option);
}