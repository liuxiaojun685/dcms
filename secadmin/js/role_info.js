/**
 * Created by lenovo on 17-2-4.
 */
//login begin
$(function () {
	var height=$(window).height()-$("#header").height()-$("#footer").height()-130;
    $("#box_tab1 .role_info").css("max-height",height);
    getData();
});
//调用树接口
function tree1(name, val) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0,
            "keyword": name,
            "fileLevel": val
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        dataType: "json",
        success: function (data) {
            data.onSuccess = function () {
                if (val == 0) {
                    $("#browser").empty();
                }
                if (val == 1) {
                    $("#browser").empty();
                }
                if (val == 2) {
                    $("#browser").empty();
                }
                if (val == 3) {
                    $("#browser").empty();
                }
                if (val == 4) {
                    $("#browser").empty();
                }
                if (val == "") {
                    $("#browser").empty();
                }
                var str = '<li><span class="com">' + data.name + '</span>' +
                    maketree1(data) +
                    '</li>';
                $("#browser").append(str);
                $("#browser").treeview({
                    collapsed: false,
                    add: $("#browser").html
                });
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
$(".add-role").click(function () {
    $("#browser").empty();
    tree1();
    layer.open({
        type: 1,
        title: '添加角色',
        shadeClose: true,
        shade: 0.3,
        area: ['280px', '50%'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            addTr2();
            layer.close(index);
        },
        content: $('#layer1')
    });
});
//生成树
function maketree1(data) {
    var str = '';
    for (var i = 0; i < data.childUserList.length; i++) {
        str += '<ul>' +
            '<li><input value=' + data.childUserList[i].uid + ' name="ckbs" type="checkbox"/><span class="user">' + data.childUserList[i].name + '</span>' +
            '</li>' +
            '</ul>';
    }
    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>' +
            '<li><span class="dep">' + data.childDepartmentList[i].name + '</span>' +
            maketree1(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>';
    }
    return str;
}
//单击CheckBox
function addTr(ckbs) {
    var listckb = $("input[name=" + ckbs + "]:checked");
    /*if(listckb.size()==0){
     $(".contsing").show()
     $(".sing-ok").click(function(){
     $(".contsing").hide();
     });
     return;
     }*/
    listckb.each(function () {
        if ($("input[name=" + ckbs + "]").is(':checked')) {
            $.ajax({
                url: host + "/dcms/api/v1/role/add",
                type: "post",
                async: false,
                data: JSON.stringify({
                    "token": token,
                    "roleType": 2,
                    "uid": $(this).val()
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        getData(0);
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
        }
    })
}
function addTr2() {
    addTr('ckbs');
    $(".cont").hide();
}
$(".cancel1").click(function () {
    $(".cont").hide();
    //t.attr("disabled", false);
});
function getData() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/role/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "roleLevel": "",
            "roleType": 2
        }),
        success: function (data) {
            data.onSuccess = function () {
                $("#tab").empty();
                var datas = data.roleList;
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
                    var str = '<tr>' +
                        '<td class="text-center">' + parseInt(i + 1) + '</td>' +
                        '<td class="text-center">' + datas[i].name + '</td>' +
                        '<td  style="text-align: center;display: none;">' + datas[i].roleId + '</td>' +
                        '<td class="text-center">';
                    for (var j = 0; j < datas[i].roleLevelList.length; j++) {
                        if (datas[i].roleLevelList[j].roleLevel == 0) {
                            datas[i].roleLevelList[j].roleLevel = "公开";
                        }
                        if (datas[i].roleLevelList[j].roleLevel == 1) {
                            datas[i].roleLevelList[j].roleLevel = "内部";
                        }
                        if (datas[i].roleLevelList[j].roleLevel == 2) {
                            datas[i].roleLevelList[j].roleLevel = "秘密";
                        }
                        if (datas[i].roleLevelList[j].roleLevel == 3) {
                            datas[i].roleLevelList[j].roleLevel = "机密";
                        }
                        if (datas[i].roleLevelList[j].roleLevel == 4) {
                            datas[i].roleLevelList[j].roleLevel = "绝密";
                        }
                        if (datas[i].roleLevelList[j].care == 0) {
                            str += '<span>' + datas[i].roleLevelList[j].roleLevel + '</span><input type="checkbox"  style="margin:5px;vertical-align: middle" disabled />';
                        } else {
                            if (datas[i].roleLevelList[j].enable == 1) {
                                str += '<span>' + datas[i].roleLevelList[j].roleLevel + '</span><input type="checkbox" onclick="roleLevel(this)"  style="margin:5px;vertical-align: middle"  checked/>';
                            }
                            if (datas[i].roleLevelList[j].enable == 0) {
                                str += '<span>' + datas[i].roleLevelList[j].roleLevel + '</span><input type="checkbox"  style="margin:5px;vertical-align: middle" onclick="roleLevel(this)" />';
                            }
                        }
                    }
                    str += '</td>' +
                        '<td class="text-center"><a href="javaScript:;;" onclick="queryScope(this.id)" id="' + datas[i].roleId + '" style=""><span>可视范围</span></a></td>' +
                        '<td class="text-center"><a href="javaScript:;;"><i class="fa fa-save" onclick="saveRolelevel(this)"></i></a><span style="padding: 0 10px;"></span><a href="javaScript:;;"><i class="fa fa-trash-o" onclick="delTr(this)"></i></a></td>' +
                        '</tr>';
                    $("#tab").append(str);
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function saveRolelevel(role) {
    layer.alert("定密责任人属性保存成功！", {
        icon: 1,
        skin: 'layer-ext-moon',
        btn: ['关闭'],
        yes: function (index, layero) {
            layer.close(index);
        }
    })
}
function delTr(ckb) {
    layer.alert("确定删除该定密责任人？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                url: host + "/dcms/api/v1/role/deleteById",
                type: "post",
                async: false,
                data: JSON.stringify({
                    "token": token,
                    "roleId": $(ckb).parent().parent().parent().find("td").eq(2).text()
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        $(ckb).parent().parent().parent().remove();
                        getData(0)
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }

            });
            layer.close(index);
        }
    });

}
function isChecked(t) {
    if ($(t).is(':checked')) {
        return 1;
    } else {
        return 0;
    }
}
function roleLevel(t) {
    var roleLevel = $(t).prev().text();
    if (roleLevel == "公开") {
        roleLevel = 0;
    }
    if (roleLevel == "内部") {
        roleLevel = 1;
    }
    if (roleLevel == "秘密") {
        roleLevel = 2;
    }
    if (roleLevel == "机密") {
        roleLevel = 3;
    }
    if (roleLevel == "绝密") {
        roleLevel = 4;
    }
    $.ajax({
        url: host + "/dcms/api/v1/role/updateLevel",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "roleId": $(t).parent().parent().find("td").eq(2).text(),
            "roleLevel": roleLevel,
            "enable": isChecked($(t))
        }),
        success: function (data) {
            //data.onSuccess = function () {
            getData(0);
            //}
            //processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//单击CheckBox
//判断是什么类型  1uid 2gid 3did
function isvarIdType(varId) {
    if (varId.indexOf('uid') != -1) {
        return 1;
    } else if (varId.indexOf('gid') != -1) {
        return 2;
    } else {
        return 3;
    }
}
//调用树接口
function tree(roleid) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 1,
            "fileLevel": ""
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        async: false,
        dataType: "json",
        success: function (data) {
            data.onSuccess = function () {
                var str = '<li><input type="hidden" value=' + roleid + ' id="roleid"/><input type="checkbox" value=' + data.did + ' id=' + data.did + ' name="only" onclick="checkRoot(this.id,1)"><span class="com">' + data.name + '</span>' +
                    maketree(data) +
                    '</li>'
                $("#browser1").append(str);
                $("#browser1").treeview({
                    collapsed: false,
                    add: $("#browser1").html
                });
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//生成树
function maketree(data) {
    var str = '';
    for (var i = 0; i < data.childGroupList.length; i++) {
        str += '<ul>' +
            '<li><input type="checkbox" data-toggle="' + data.did + '" value=' + data.childGroupList[i].gid + ' id=' + data.childGroupList[i].gid + ' name="only" onclick="checkRoot(this.id,0)"><span class="group">' + data.childGroupList[i].name + '</span>';
        var userlength = data.childGroupList[i].childUserList.length;
        str += '</li>' +
            '</ul>'
    }

    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>' +
            '<li><input type="checkbox" data-toggle="' + data.did + '" value=' + data.childDepartmentList[i].did + ' id=' + data.childDepartmentList[i].did + ' name="only" onclick="checkRoot(this.id,0)"><span class="dep">' + data.childDepartmentList[i].name + '</span>'
            + maketree(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>';
    }
    return str;
}



//取消每级父节点
function findParents(parentId) {
    if ($("#" + parentId).is(':checked')) {
        $("#" + parentId).prop("checked", false);
        findParents($("#" + parentId).attr("data-toggle"));
    }
}

//子节点全选后，父节点要勾选上
function checkParents(id){
	$("input[value='" + id + "']").prop("checked", true);
	if(id != "did-root"){
		
		var parentid = $("#" + id).attr("data-toggle");
		if ($("input[data-toggle='" + parentid + "']").length == $("input[data-toggle='" + parentid + "']:checked").length) {
			checkParents(parentid);
			
		}
	}
 

}

//key代表是否是点击根节点  0：否  1：是
function checkRoot(varId,key) {
    if ($("#" + varId).is(':checked')) {

    	$("input[data-toggle=" + varId + "]").prop("checked", true);
    	//判断它的兄弟是否全部选中
    	$("input[data-toggle=" + varId + "]").each(function () {
    		
    		checkRoot($(this).val());
    		
    	});
    	if(key == 0) {
    		
    		var parentid = $("#" + varId).attr("data-toggle");
    		if ($("input[data-toggle='" + parentid + "']").length == $("input[data-toggle='" + parentid + "']:checked").length) {
    			checkParents(parentid);
    			
    		}
    	}
        

    } else {

        $("input[data-toggle=" + varId + "]").prop("checked", false);
        $("input[data-toggle=" + varId + "]").each(function () {
            checkRoot($(this).val());
        });
        var parents = $("#" + varId).attr("data-toggle");
        findParents(parents);
    }

}

//返回树根节点
function getParentIds(t,scopeList){

    $(t).children("ul").each(function(){
        if($(this).children("li").children("input[type=checkbox]").is(":checked")){
            var id = $(this).children("li").children("input[type=checkbox]").val();
            var type = parseInt(isvarIdType($(this).children("li").children("input[type=checkbox]").val()));
            var scope = {
                "varId": id,
                "varType": type
            }
            scopeList.push(scope);
            return;
        }else{
            getParentIds($(this).children("li").children("input[type=checkbox]").parent(),scopeList);
        }
    })

    return scopeList;

}

function saverole() {
    var scopeList = new Array();
    var roleId = $("#roleid").val();

    //判断根节点是否选中
    if($("#did-root").is(":checked")){
        var id = $("#did-root").val();
        var type = parseInt(isvarIdType($("#did-root").val()));
        var scope = {
            "varId": id,
            "varType": type
        }
        scopeList.push(scope);
    }else{
        getParentIds($("#did-root").parent(),scopeList);
    }

   /* $("#browser1 input[name=only]:checked").each(function () {
        var id = $(this).val();
        var type = parseInt(isvarIdType($(this).val()));
        var scope = {
            "varId": id,
            "varType": type
        }
        scopeList.push(scope);
    });*/
    $.ajax({
        url: host + "/dcms/api/v1/role/updateScope",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "roleId": roleId,
            "scopeList": scopeList
        }),
        success: function (data) {
            data.onSuccess = function () {
                //$(".cont2").hide();
                getData(0);
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function queryScope(roleid) {
    var scopelist = $("#browser1 input[name='only']");
    layer.open({
        type: 1,
        title: '添加范围',
        shadeClose: true,
        shade: 0.3,
        area: ['280px', '50%'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            saverole();
            layer.close(index);
        },
        content: $('#layer5')
    });
    $.ajax({
        url: host + "/dcms/api/v1/role/queryScope",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "roleId": roleid
        }),
        success: function (data) {
            data.onSuccess = function () {
                //$("#list").empty();

                $("#browser1").empty();
                tree(roleid);
                //scopelist.each(function () {
                for (var i = 0; i < data.scopeList.length; i++) {
                    //$("#" + data.scopeList[i].varId).prop("checked", true);

                    $("#" + data.scopeList[i].varId).click();
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//检查未配置责任人的部门
function checkInfo(){
	$.ajax({
        url: host + "/dcms/api/v1/systemConfig/connectTest",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "type": 13
        }),
        success: function (data) {
        	$("#checkInfo").empty();
        	if(data.code==6){
        		$("#checkInfo").append("所有部门均未配置定密责任人！");
        	}else {
        		data.onSuccess = function () {
        			if(data.resultList.length == 0){
        				$("#checkInfo").append("配置正确！");
        			}else{
        				for (var i = 0; i < data.resultList.length; i++) {
        					 var str = '<div>' + data.resultList[i] +
                             '</div>';
        					 $("#checkInfo").append(str);
        				}
        			}
        		}
        		processResponse(data, 0);
        	}
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}