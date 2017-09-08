/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-2-23
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */
//var host="http://47.93.78.186:8080";

$(function () {
    InitData();
    $(".creatwf").empty()
    $(".creatwf").append(getSelect());
});
//初始化数据，进入页面查询
function InitData() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryWorkFlow",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#tab").empty();
                var datas = result.workFlowPolicyList;
                for (var i = 0; i < datas.length; i++) {
                    if (i == 0) {
                        // allList(datas[i].workFlowPolicyId);
                    }
                    var num = parseInt(i + 1);
                    if (datas[i].workFlowType == 2) {
                        datas[i].workFlowType = "正式定密审核";
                    }
                    if (datas[i].workFlowType == 3) {
                        datas[i].workFlowType = "文件签发审核";
                    }
                    if (datas[i].workFlowType == 4) {
                        datas[i].workFlowType = "密级变更审核";
                    }
                    if (datas[i].workFlowType == 5) {
                        datas[i].workFlowType = "文件解密审核";
                    }
                    if (datas[i].workFlowType == 9) {
                        datas[i].workFlowType = "文件密文还原审核";
                    }
                    if (datas[i].fileLevel == 0) {
                        datas[i].fileLevel = "公开";
                    }
                    if (datas[i].fileLevel == 1) {
                        datas[i].fileLevel = "内部";
                    }
                    if (datas[i].fileLevel == 2) {
                        datas[i].fileLevel = "秘密";
                    }
                    if (datas[i].fileLevel == 3) {
                        datas[i].fileLevel = "机密";
                    }
                    if (datas[i].fileLevel == 4) {
                        datas[i].fileLevel = "绝密";
                    }
                    var str = '<tr style="cursor: pointer" onclick="allList(' + datas[i].workFlowPolicyId + ',' + num + ')"' +
                        ' id="' + datas[i].workFlowPolicyId + '">' +
                        '<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].workFlowPolicyId + '</td>' +
                        '<td>' + datas[i].workFlowType + '</td>' +
                        '<td>' + datas[i].fileLevel + '</td>' +
                        '<td><a href="javaScript:;;"' +
                        ' onclick="dalWorkFlowPolicy(' + datas[i].workFlowPolicyId + ') "><i class="fa fa-trash-o"></i></a></td>' +
                        '</tr>';
                    $("#tab").append(str);
                    //需要循环每级审批人
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
function allList(workFlowId, num) {
    var oldworkFlowId = $("#wf").val();
    $("#" + oldworkFlowId).css("background", "");
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryWorkFlowById",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "workFlowPolicyId": workFlowId
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#wf").val(workFlowId);
                $("#" + workFlowId).css("background", "#efefef");
                $(".creatwf").show();
                $(".c-body").show();
                $(".c-check").hide();
                $(".creatwf").empty();
                $(".c-body").empty();
                var str = '';
                if(num != undefined) {
                    str += '<span style="padding:0 10px;color:#4777c8;">编号：' + num + '</span><span>流程类型:</span>' ;
                }

                    str+='<select disabled>';
                if (result.workFlowType == 2) {
                    str += '<option value="2" selected>正式定密</option>' +
                        '<option value="3">文件签发</option>' +
                        '<option value="4">密级变更</option>' +
                        '<option value="5">文件解密</option>'+
                        '<option value="9">文件密文还原</option>';
                }
                if (result.workFlowType == 3) {
                    str += '<option value="2">正式定密</option>' +
                        '<option value="3" selected>文件签发</option>' +
                        '<option value="4">密级变更</option>' +
                        '<option value="5">文件解密</option>'+
                        '<option value="9">文件密文还原</option>';
                }

                if (result.workFlowType == 4) {
                    str += '<option value="2">正式定密</option>' +
                        '<option value="3">文件签发</option>' +
                        '<option value="4" selected>密级变更</option>' +
                        '<option value="5">文件解密</option>'+
                        '<option value="9">文件密文还原</option>';
                }
                if (result.workFlowType == 5) {
                    str += '<option value="2">正式定密</option>' +
                        '<option value="3">文件签发</option>' +
                        '<option value="4">密级变更</option>' +
                        '<option value="5" selected>文件解密</option>'+
                        '<option value="9">文件密文还原</option>';
                }
                if (result.workFlowType == 9) {
                    str += '<option value="2">正式定密</option>' +
                        '<option value="3">文件签发</option>' +
                        '<option value="4">密级变更</option>' +
                        '<option value="5">文件解密</option>'+
                        '<option value="9" selected>文件密文还原</option>';
                }

                str += '</select>' +

                    '<span style="margin-left: 10px;">文件密级:</span>' +
                    '<select disabled>';
                if (result.fileLevel == 0) {
                    str += '<option value="0" selected>公开</option>' +
                        '<option value="1">内部</option>' +
                        '<option value="2">秘密</option>' +
                        '<option value="3">机密</option>' +
                        '<option value="4">绝密</option>';
                }
                if (result.fileLevel == 1) {
                    str += '<option value="0">公开</option>' +
                        '<option value="1" selected>内部</option>' +
                        '<option value="2">秘密</option>' +
                        '<option value="3">机密</option>' +
                        '<option value="4">绝密</option>';
                }
                if (result.fileLevel == 2) {
                    str += '<option value="0">公开</option>' +
                        '<option value="1">内部</option>' +
                        '<option value="2" selected>秘密</option>' +
                        '<option value="3">机密</option>' +
                        '<option value="4">绝密</option>';
                }
                if (result.fileLevel == 3) {
                    str += '<option value="0">公开</option>' +
                        '<option value="1">内部</option>' +
                        '<option value="2">秘密</option>' +
                        '<option value="3" selected>机密</option>' +
                        '<option value="4">绝密</option>';
                }
                if (result.fileLevel == 4) {
                    str += '<option value="0">公开</option>' +
                        '<option value="1">内部</option>' +
                        '<option value="2">秘密</option>' +
                        '<option value="3">机密</option>' +
                        '<option value="4" selected>绝密</option>';
                }
                str += '</select>';
                //审批员列表
                var datas = result.workFlowRoleList;
                var str1 = '<span><img class="start" style="width: 35px;height: 35px;vertical-align: middle;cursor: pointer" src="../img/start.png" alt=""></span><span><img src="../img/arrow.png"></span>';
                if (datas.length > 0) {
                    for (var i = 0; i < datas.length; i++) {
                        //取出所有角色ID
                        var roleIds = new Array();
                        for (var j = 0; j < datas[i].roleList.length; j++) {
                            roleIds.push(datas[i].roleList[j].roleId)
                        }
                        if (i < datas.length - 1) {
                            str1 += '<span class="stepBtn"><input class="btn btn-info stepBtn" type="button"  onclick="getRoleList(this,' + datas[i].step + ',' + result.workFlowPolicyId + ',' + result.fileLevel + ',' + result.workFlowType + ',' + num + ')" value="' + datas[i].step + '级审批"><input type="hidden" value="' + roleIds + '"></span><span><img src="../img/arrow.png"></span>';
                        } else {
                            str1 += '<span class="stepBtn"><input class="btn btn-info stepBtn" type="button"  onclick="getRoleList(this,' + datas[i].step + ',' + result.workFlowPolicyId + ',' + result.fileLevel + ',' + result.workFlowType + ',' + num + ')" value="' + datas[i].step + '级审批"><input type="hidden" value="' + roleIds + '">' +
                                '<span style="margin-left: 5px">' +
                                '<i id="delStep" class="fa fa-minus-circle text-danger" style="font-size:15px;cursor: pointer" onclick="delStep(this,' + datas[i].step + ',' + result.workFlowPolicyId + ',' + num + ')"></i></span>' +
                                '</span>';
                        }


                    }
                    str1 += '<span style="margin-left: 20px;"><input class="btn-square" type="button"  onclick="addStep(this,' + result.workFlowPolicyId + ',' + result.workFlowType + ',' + result.fileLevel + ',' + datas[datas.length - 1].step + ',' + num + ')" value="下一级 》">' +
                        '<img style="margin-left:5px;width: 35px;height: 35px;vertical-align: middle;cursor: pointer" src="../img/end.png" alt="" onclick="addOver()"></span>';
                } else {
                    var step = 1;
                    str1 += '<span class="stepBtn"><input class="btn btn-info stepBtn" type="button"  onclick="getRoleList(this,' + step + ',' + result.workFlowPolicyId + ',' + result.fileLevel + ',' + result.workFlowType + ',' + num + ')" value="' + step + '级审批"><input type="hidden"></span>';
                }

                $(".creatwf").append(str);
                $(".c-body").append(str1);
                $(".c-body").show();
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })

    //event.stopPropagation();

}


//添加流程策略
function creatWorkflow() {
	$(".c-check").hide();
    $(".creatwf").show();
    $(".creatwf").empty();
    $(".c-body").empty();
    $(".creatwf").append(getSelect());
}

//配置检测
function checkWorkflow(){
	$.ajax({
        url: host + "/dcms/api/v1/systemConfig/connectTest",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "type": 15
        }),
        success: function (data) {
        	$("#checkInfo").empty();
    		data.onSuccess = function () {
    			$(".c-check").show();
    		    $(".creatwf").hide();
    		    $(".c-body").hide();
    		    $(".c-check").empty();
    		    if(data.resultList.length == 0) {
    		    	var str = '<div>所有流程均缺少审批人</div>';
                $(".c-check").append(str);
    		    }
				for (var i = 0; i < data.resultList.length; i++) {
					 var str = '<div>' + data.resultList[i] +
                     '</div>';
                 $(".c-check").append(str);
				}
        	}
    		processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//保存流程
function saveWorkflow(t) {
    //流程类型
    var workflowType = $("#workflowType").val();
    if (workflowType == '') {
        layer.alert("请选择流程类型", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                layer.close(index);
            }
        });
        return;
    }
    //用户密级
    var fileLevel = $("#fileLevel").val();
    if (fileLevel == '') {
        layer.alert("请选择文件密级！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                layer.close(index);
            }
        });
        return;
    }

    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/addWorkFlow",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "fileLevel": fileLevel,
            "workFlowType": workflowType
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".c-body").show();
                $(t).hide();
                $(".c-body").append('<span><img class="start" style="width: 35px;height: 35px;vertical-align: middle;cursor: pointer" src="../img/start.png" alt=""></span>');
                $(".start").unbind("click").click(function () {
                    if ($(this).parent().next().length == 0) {
                        workStart(this, result.workFlowPolicyId, workflowType, fileLevel);
                    }
                });
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}
//流程开始
function workStart(t, workFlowPolicyId, workflowType, fileLevel) {
    //if成功，当前级数为默认0级
    var step = 1;
    var str = '<span><img src="../img/arrow.png"></span><span class="stepBtn" style="width: 120px;"><input class="btn btn-info stepBtn" type="button"  onclick="getRoleList(this,' + step + ',' + workFlowPolicyId + ',' + fileLevel + ',' + workflowType + ')" value="' + step + '级审批"><input type="hidden"></span>';
    $(t).parent().after(str);
}
//添加级数
function addStep(t, workFlowPolicyId, workFlowType, fileLelel, count, num) {

    count++;
    $("#delStep").parent().remove();
    var str = '<span><img src="../img/arrow.png"></span><span class="stepBtn"><input class="btn btn-info stepBtn" type="button"  onclick="getRoleList(this,' + count + ',' + workFlowPolicyId + ',' + fileLelel + ',' + workFlowType + ','+num+')" value="' + count + '级审批"><input type="hidden"></span>';
    $(t).parent().after(str);
    $(t).parent().hide();
    /*if ($(t).next().length == 0) {
     $(t).after('<i class="fa fa-trash-o" style="cursor: pointer" onclick="delStep(this)"></i>');
     } else {
     $(t).next().show();
     }*/

}
function getSelect() {
    var str = '<span>流程类型:</span>' +
        '<select id="workflowType">' +
        '<option value="">选择</option>' +
        '<option value="2">正式定密</option>' +
        '<option value="3">文件签发</option>' +
        '<option value="4">密级变更</option>' +
        '<option value="5">文件解密</option>' +
        '<option value="9">文件密文还原</option>' +
        '</select>' +


        '<span style="margin-left: 10px;">文件密级:</span>' +
        '<select id="fileLevel">' +
        '<option value="">选择</option>' +
        '<option value="0">公开</option>' +
        '<option value="1">内部</option>' +
        '<option value="2">秘密</option>' +
        '<option value="3">机密</option>' +
        '<option value="4">绝密</option>' +
        '</select>' +
        '<span style="margin-left: 10px;" ><img style="width: 35px;height: 35px;vertical-align: middle;cursor: pointer" src="../img/create.png" alt="" onclick="saveWorkflow(this)"></span>';
    return str;
}
//删除整个流程
function dalWorkFlowPolicy(workFlowPolicyId) {
    layer.alert("确定删除选中的流程？", {
        icon: 1,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "POST",
                url: host + "/dcms/api/v1/securePolicy/deleteWorkFlow",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "workFlowPolicyId": workFlowPolicyId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        InitData();
                        creatWorkflow()
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index);
        }
    });
    //event.stopPropagation();
}

//删除级数
function delStep(t, step, workFlowPolicyId, num) {
    $.ajax({
        type: "POST",
        url: host + "/dcms/api/v1/securePolicy/updateWorkFlow",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "workFlowPolicyId": workFlowPolicyId,
            "roleId": [],
            "step": step
        }),
        success: function (result) {
            result.onSuccess = function () {
                allList(workFlowPolicyId, num);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }

    })
}
//获取用户角色列表
var stepnow
var tt;
function getRoleList(t, step, workFlowPolicyId, fileLevel, workflowType, num) {

    stepnow = step;
    var roleType;
    //文件签发流程
    if (workflowType == 3) {
        roleType = 3;
    } else {
        roleType = 2;
    }
    $.ajax({
        url: host + "/dcms/api/v1/role/queryList",
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "roleType": roleType,
            "roleLevel": fileLevel
        }),
        success: function (result) {
            result.onSuccess = function () {

                $(".cont").show();
                $(".coolist").empty();
                var datas = result.roleList;
                for (var i = 0; i < datas.length; i++) {
                    if (datas[i].level == 1) {
                        datas[i].level = "一般";
                    }
                    if (datas[i].level == 2) {
                        datas[i].level = "重要";
                    }
                    if (datas[i].level == 3) {
                        datas[i].level = "核心";
                    }
                    if (datas[i].online == 0) {
                        datas[i].online = "离线";
                    }
                    if (datas[i].online == 1) {
                        datas[i].online = "在线";
                    }
                    if (datas[i].roleLevel == 0) {
                        datas[i].roleLevel = "公开";
                    }
                    if (datas[i].roleLevel == 1) {
                        datas[i].roleLevel = "内部";
                    }
                    if (datas[i].roleLevel == 2) {
                        datas[i].roleLevel = "秘密";
                    }
                    if (datas[i].roleLevel == 3) {
                        datas[i].roleLevel = "机密";
                    }
                    if (datas[i].roleLevel == 4) {
                        datas[i].roleLevel = "绝密";
                    }
                    if (datas[i].roleType == 2) {
                        datas[i].roleType = "定密责任人";
                    }
                    if (datas[i].roleType == 3) {
                        datas[i].roleType = "文件签发人";
                    }
                    if (datas[i].roleType == 7) {
                        datas[i].roleType = "签入人";
                    }
                    if (datas[i].roleType == 8) {
                        datas[i].roleType = "签出人";
                    }
                    var str = '<tr>' +
                        '<td  style="text-align: center;"><input name="ckb" type="checkbox" id="roleId' + datas[i].roleId + '"></td>' +
                        '<td  style="text-align: center;display: none">' + datas[i].roleId + '</td>' +
                        '<td  style="text-align: center;">' + datas[i].name + '</td>' +
                        '<td  style="text-align:center">' + datas[i].level + '</td>' +
                        '<td  style="text-align: center;">' + datas[i].roleType + '</td>' +
                        '</tr>';
                    $(".coolist").append(str);
                }

                //得到该级之前选中的用户
                var roleIds = $(t).next().val();
                if (roleIds != '') {
                    //拆分角色id
                    var roleId = roleIds.split(',');

                    for (var i = 0; i < roleId.length; i++) {
                        $("#roleId" + roleId[i]).prop("checked", true);
                    }
                }
                layer.open({
                    type: 1,
                    title: '选择角色',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['400px', '400px'],
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                    	checkApprole(t, index, stepnow, workFlowPolicyId, workflowType, fileLevel, num);
                        /*addRole(t, index, stepnow, workFlowPolicyId, workflowType, fileLevel, num);*/


                    },
                    content: $('#cont')
                });
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}


//添加审批人
function addRole(t, index, step, workFlowPolicyId, workflowType, fileLevel, num, roleIds) {
   
    
    //将得到角色ID存上
    $(t).next().val(roleIds);
    $.ajax({
        url: host + "/dcms/api/v1/securePolicy/updateWorkFlow",
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "workFlowPolicyId": workFlowPolicyId,
            "roleId": roleIds,
            "step": step
        }),
        success: function (result) {
            //result.onSuccess = function () {
            layer.close(index);
            if ($(t).parent().next().length == 0) {
                //添加删除级数按钮
                $(t).parent().append('<span style="margin-left: 5px"><i id="delStep" class="fa fa-minus-circle text-danger" style="font-size:15px;cursor: pointer" onclick="delStep(this,' + step + ',' + workFlowPolicyId + ',' + num + ')"></i></span>');
                var str = '<span style="margin-left: 20px;"><input class="btn-square" type="button"  onclick="addStep(this,' + workFlowPolicyId + ',' + workflowType + ',' + fileLevel + ',' + step + ',' + num + ')" value="下一级》">' +
                    '<img style="margin-left:5px;width: 35px;height: 35px;vertical-align: middle;cursor: pointer" src="../img/end.png" alt="" onclick="addOver()"></span>';
                $(t).parent().after(str);
            }
            //}
            //processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}

function addOver() {
    layer.alert("流程创建结束！", {
        icon: 1,
        title: "提示",
        skin: 'layer-ext-moon',
        btn: ['关闭'],
        yes: function (index, layero) {
            InitData()
            layer.close(index);
        }
    });

}

//检查部门是否缺少审批人
function checkApprole(t, index1, step, workFlowPolicyId, workflowType, fileLevel, num){
	
	
	//获取选中的角色
    var cbks = $("input[name=ckb]:checked");
    if (cbks.size() == 0) {
        layer.alert("请至少选择一个责任人！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                layer.close(index);
            }
        });
        return;
    }
    //获取角色id
    var roleIds = new Array();
    var username = '';
    $("#check .coolist1").empty();
    cbks.each(function () {
        var roleId = $(this).parent().parent().find("td").eq(1).text();
        roleIds.push(roleId);
    });
	
	$.ajax({
        url: host + "/dcms/api/v1/securePolicy/checkWorkFlow",
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "token": token,
            "workFlowPolicyId": workFlowPolicyId,
            "roleId": roleIds,
            "step": step
        }),
        success: function (result) {
        	var datas=result.checkList;
        	if(datas.length==0){
        		addRole(t, index1, step, workFlowPolicyId, workflowType, fileLevel, num, roleIds);
        		return;
        	}
        	var str='';
        	for (var i = 0; i < datas.length; i++) {
     		   if(i==datas.length-1){
     			   str+=datas[i].varName;
     		   }else{
     			  str+=datas[i].varName + '、';
     		   }
     	   }
        	str+="等"+datas.length+"个部门缺少审批人！";
        	
        	layer.alert(str, {
                icon: 0,
                title:'提示',
                skin: 'layer-ext-moon',
                btn: ['忽略','重选'],
                yes: function (index, layero) {
                	layer.close(index);
                	addRole(t, index1, step, workFlowPolicyId, workflowType, fileLevel, num, roleIds);
                }
            });
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

