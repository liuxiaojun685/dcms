/**
 * Created by liuxiaojun on 17/3/3.
 */
/**
 * Created by lenovo on 17-2-4.
 */
$(function () {
    InitTable(7);
});
function InitTable(num) {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/role/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "roleLevel": "",
            "roleType": num
        }),
        success: function (data) {
            data.onSuccess = function () {
                if (num == 7) {
                    $("#box_tab1 .tab").empty();
                } else if (num == 8) {
                    $("#box_tab2 .tab").empty();
                }
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
                        '<td  style="text-align: center;">' + parseInt(i + 1) + '</td>' +
                        '<td  style="text-align: center;">' + datas[i].name + '</td>' +
                        '<td  style="text-align: center;display: none;">' + datas[i].roleId + '</td>' +
                        '<td  style="text-align: center;">';
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
                        if (datas[i].roleLevelList[j].enable == 1) {
                            str += '<span>' + datas[i].roleLevelList[j].roleLevel + '</span><input type="checkbox" onclick="roleLevel(this)"  checked/>';
                        }
                        if (datas[i].roleLevelList[j].enable == 0) {
                            str += '<span>' + datas[i].roleLevelList[j].roleLevel + '</span><input type="checkbox" onclick="roleLevel(this)" />';
                        }
                    }
                    str += '</td>' +
                        '<td  style="text-align: center;"><a href="#" onclick="queryScope(this.id)" id="' + datas[i].roleId + '"><span style="padding:20px 60px;">' + datas[i].roleDispScope + '</span></a></td>' +
                        '<td  style="text-align: center;"><input type="button" value="删除" onclick="delTr(this)"/></td>' +
                        '</tr>';
                    if (num == 7) {
                        $("#box_tab1 .tab").append(str);
                    } else if (num == 8) {
                        $("#box_tab2 .tab").append(str);
                    }
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
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
            $("#browser").append(maketree1(data));
            $("#browser").treeview({
                collapsed: false,
                add: $("#browser").html
            });
        }
    });
}
$(".add-role").click(function () {
    $("#box_tab1 .cont").show();
    tree1();
});
//生成树
function maketree1(data) {
    var str = '<li><span class="dep">' + data.name + '</span>';
    for (var i = 0; i < data.childUserList.length; i++) {
        str += '<ul>' +
            '<li><span class="user"><input value=' + data.childUserList[i].uid + ' name="ckbs" type="checkbox"/>' + data.childUserList[i].name + '</span>' +
            '</li>' +
            '</ul>';
    }
    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>'
            + maketree1(data.childDepartmentList[i]) +
            '</ul>';
    }
    str += '</li>';
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
                    "roleType": 7,
                    "uid": $(this).val()
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        InitTable(7);
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
    $("#box_tab1 .cont").hide();
}
function delTr(ckb) {
    $.ajax({
        url: host + "/dcms/api/v1/role/deleteById",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "roleId": $(ckb).parent().parent().find("td").eq(2).text()
        }),
        success: function (data) {
            data.onSuccess = function () {
                $(ckb).parent().parent().remove();
                InitTable(0);
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
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
            data.onSuccess = function () {
                InitTable(7);
            }
            processResponse(data, 1);
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
        dataType: "json",
        success: function (data) {
            $("#browser1").append(maketree(data, roleid));
            $("#browser1").treeview({
                collapsed: false,
                add: $("#browser1").html
            });
        }
    });

}
//生成树
function maketree(data, roleid) {
    var str = '<li><input type="hidden" value=' + roleid + ' id="roleid"/><span class="dep"><input type="checkbox"  value=' + data.name + ' id=' + data.did + ' name="only">' + data.name + '</span>';
    for (var i = 0; i < data.childGroupList.length; i++) {
        str += '<ul>' +
            '<li><span class="group"><input type="checkbox" value=' + data.childGroupList[i].name + ' id=' + data.childGroupList[i].gid + ' name="only">' + data.childGroupList[i].name + '</span>';
        var userlength = data.childGroupList[i].childUserList.length;
        for (var j = 0; j < userlength; j++) {
            var ob = data.childGroupList[i].childUserList[j];
            str += '<ul>' +
                '<li><span class="user">' + ob.name + '</span>' +
                '</li>' +
                '</ul>'

        }
        str += '</li>' +
            '</ul>'
    }
    for (var i = 0; i < data.childUserList.length; i++) {
        str += '<ul>' +
            '<li><span class="user">' + data.childUserList[i].name + '</span>' +
            '</li>' +
            '</ul>'

    }
    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>'
            + maketree(data.childDepartmentList[i]) +
            '</ul>'
    }
    str += '</li>'
    return str;
}
function clickget(only) {
    var list = $("input[name=" + only + "]:checked");
    list.each(function () {
        var uid = this.id;
        var key = 0;
        var str = '<tr id="tr' + only + '">' +
            '<td><input type="checkbox"  name="ckh"/><span style="padding:0 10px;"></span></td>' +
            '<td style="display:none;">' + this.id + '</td>' +
            '<td style="display:none;">' + isvarIdType(this.id) + '</td>' +
            '<td>' + $(this).val() + '<input type="hidden" value=' + only + '></td>' +
            '</tr>';
        $("#list tr").each(function () {
            if (uid == $(this).find("td").eq(1).text()) {
                key = 1;
            }
        });
        if (key == 0) {
            $("#box_tab1 .list").append(str);
        }

    })
}
function clickget2() {
    alert(123);
    clickget('only');
}
function clickdel(ckh) {
    var ckhs = $("input[name=" + ckh + "]:checked");
    ckhs.each(function () {
        //alert($(this).parent());
        $(this).parent().parent().remove();
    })
}
function clickdel2() {
    clickdel('ckh');
}
function saverole() {
    var scopeList = new Array();
    var roleId = $("#roleid").val();
    $(".list tr").each(function () {
        var id = $(this).find("td").eq(1).text();
        var type = parseInt($(this).find("td").eq(2).text());
        var scope = {
            "varId": id,
            "varType": type
        }
        scopeList.push(scope);
    });
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
                InitTable(7);
                $(".cont2").hide();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function queryScope(roleid) {
    $(".cont2").show();
    tree(roleid);
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
                $("#list").empty();
                for (var i = 0; i < data.scopeList.length; i++) {
                    var str = '<tr>' +
                        '<td colspan="2"><input type="checkbox" name="ckh"/>' +
                        '<span style="padding:0 10px;"></span>' +
                        data.scopeList[i].varName +
                        '</td>' +
                        '<td style="display:none;">' +
                        data.scopeList[i].varId +
                        '</td>' +
                        '<td style="display:none;">' +
                        data.scopeList[i].varType +
                        '</td>' +
                        '</tr>';
                    $("#box_tab1 #list").append(str);
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
