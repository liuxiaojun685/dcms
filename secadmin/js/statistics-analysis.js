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

$(function () {
    refreshFileChart();
    refreshWorkflowChart();
    refreshFileTrendChart();
});

function refreshFileChart() {
    var fileChart = echarts.init(document.getElementById('file'));
    var option = {
        "title": {
            "text": "文件台账",
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
            "text": "流程审批",
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
        "title": {
            "text": "趋势"
        },
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
        url: host + "/dcms/api/v1/statistics/fileManyTrend",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "by": $("#fileTrendBy option:selected").val(),
            "startTime": new Date($("#start").val()).getTime(),
            "endTime": new Date($("#end").val()).getTime(),
            "fileLevel": $("#fileTrendLevel option:selected").val(),
            "scope":$("#scopeValue").val()==''?"":JSON.parse($("#scopeValue").val())
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

//统计范围
function scopeUser(){
	 //判断是否已选择过
	var fileScope;
	if($("#scopeValue").val() != ''){
		fileScope = JSON.parse($("#scopeValue").val());
	}
    tree(fileScope);
    layer.open({
        type: 1,
        title:"统计范围",
        area: ['400px', '500px'],
        btn: ["确定","取消"],
        yes: function(index, layero){
        	var fileScope = new Array();
        	$("#browser :checkbox:checked").each(function(){

                var uid=$(this).val();if(uid.indexOf('did') == -1){
                    fileScope.push(uid);
                }

            });

            //存到隐藏标签中
            $("#scopeValue").val(JSON.stringify(fileScope));
            layer.close(index);
        },
        content:$("#dtree")
    })
} 


//调用树接口
function tree(scopeData){
    //默认的知悉范围
    $.ajax({
        type:"POST",
        crossDomain:false,
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0,
            "fileLevel":""
        }),
        url:host +"/dcms/api/v1/department/queryTree",
        dataType: "json",
        success : function(data) {
            var str = '<ul id="browser" class="filetree treeview">'+
                '<li id="root"><span class="com"><input type="checkbox" onclick="clickget(this.id)" value='+data.did+' id="'+data.did+'">'+data.name+'</span>'+
                maketree(data)+'</li>'+
                '</ul>'


            $("#browser").remove();
            $("#dtree").append(str);
            $("#browser").treeview({
                collapsed:false,
                add:$("#browser").html
            });

            //initData(dispatch);
            //让默认的人选中
            if(typeof(scopeData) != 'undefined') {
            	for(var i=0;i<scopeData.length;i++){
                    //可得到用户的id，用ID将树上的用户默认选上
                    var uid=scopeData[i];
                    $("#"+uid).prop("checked",true);
                }
            }
           

        }
    });

}

//生成树
function maketree(data) {

    var str=''

    for(var i = 0; i<data.childUserList.length;i++) {
        str += '<ul>'+
            '<li><span class="user"><input type="checkbox" onclick="clickget(this.id)" name="'+data.did+'" value='+data.childUserList[i].uid+' id='+data.childUserList[i].uid+'>'+data.childUserList[i].name+'</span>'+
            '</li>'+
            '</ul>'

    }

    for(var i = 0; i<data.childDepartmentList.length;i++) {
        str += '<ul>'+
            '<li><span class="dep"><input type="checkbox" onclick="clickget(this.id)" name="'+data.did+'" value='+data.childDepartmentList[i].did+' id="'+data.childDepartmentList[i].did+'">'+data.childDepartmentList[i].name+'</span>'
            + maketree(data.childDepartmentList[i])+
            '</li>'+
            '</ul>'
    }
    /*str+='</li>'*/
    return str;

}
//取消每级父节点
function findParents(parentId){
    if($("#"+parentId).is(':checked')){
        $("#"+parentId).prop("checked",false);
        findParents($("#"+parentId).attr("name"));

    }

}

//如果是根节点
function checkRoot(varId){

    if($("#did-root").is(':checked')){
        $("input[name="+varId+"]").prop("checked",false);
        $("input[name="+varId+"]").each(function(){
            checkRoot($(this).val());
        });
    }
}
//单击CheckBox
function clickget(varId){
    /*if(varId=='did-root'){
        checkRoot(varId);

    }else{*/
        if($("#"+varId).is(':checked')){
            /*if($("#did-root").is(':checked')){
                $("#did-root").prop("checked",false);
            }*/
            $("input[name="+varId+"]").prop("checked",true);
            $("input[name="+varId+"]").each(function(){
                clickget($(this).val());
            });
        }else{

            $("input[name="+varId+"]").prop("checked",false);
            $("input[name="+varId+"]").each(function(){
                clickget($(this).val());
            });
            //查找父节点，父节点选中的须被取消
            var parents=$("#"+varId).attr("name");
            findParents(parents);
        }

   // }

}