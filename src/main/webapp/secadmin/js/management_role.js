/**
 * Created by lenovo on 17-2-4.
 */
//login begin
$(function () {
    getData();
});
//调用树接口
function tree1(datas) {
    $.ajax({
        type: "POST",
        //async: false,
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        dataType: "json",
        success: function (data) {
            data.onSuccess = function () {
                    $("#browser").empty();
                var str = '<li><span class="com">' + data.name + '</span>' +
                    maketree1(data) +
                    '</li>';
                $("#browser").append(str);
                $("#browser").treeview({
                    collapsed: false,
                    add: $("#browser").html
                });
                for (var i = 0; i < datas.userList.length; i++) {
                    $("#role" + datas.userList[i].uid).remove();
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//生成树
function maketree1(data) {
    var str = '';
    for (var i = 0; i < data.childUserList.length; i++) {
        str += '<ul id="role' + data.childUserList[i].uid + '">' +
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
function getData() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/admin/queryDefaultAdmin",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        //async: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            $("#tab").empty();
            $("#managerole>a").empty();
            data.onSuccess = function () {
                var roletype = data.adminList;
                var adminType;
                for (var i = 0; i < roletype.length; i++) {
                    if (roletype[i].adminType == 1) {
                        adminType = "安全审计员";
                    }
                    if (roletype[i].adminType == 2) {
                        adminType = "系统管理员";
                    }
                    if (roletype[i].adminType == 3) {
                        adminType = "安全保密管理员";
                    }
                    if (roletype[i].account == 1) {
                        roletype[i].account = "logadmin";
                    }
                    if (roletype[i].account == 2) {
                        roletype[i].account = "sysadmin";
                    }
                    if (roletype[i].account == 3) {
                        roletype[i].account = "secadmin";
                    }
                    if (roletype[i].createFrom == 1) {
                        roletype[i].createFrom = "系统内置";
                    }
                    if (roletype[i].createFrom == 2) {
                        roletype[i].createFrom = "角色授权";
                    }
                    var str = '<tr>' +
                        '<td>' + parseInt(i + 1) + '</td>' +
                        '<td>' + adminType + '</td>' +
                        '<td style="display:none;">' + roletype[i].aid + '</td>' +
                        '<td>' + roletype[i].description + '</td>' +
                        '<td>' + roletype[i].account + '</td>' +
                        '<td>' + roletype[i].createFrom + ' </td>' +
                        '<td><a href="#" onclick="alllist('+roletype[i].adminType+')"><i class="fa fa-list"></i></a></td>' +
                        '</tr>';
                    $("#tab").append(str);
                    $("#tab td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//点击查看授权
function alllist(adminType) {
    layer.open({
        type: 1,
        title: '添加关联用户',
        shadeClose: true,
        shade: 0.3,
        area: ['600px', '400px'],
        btn: ['关闭'],
        yes: function (index, layero) {
            layer.close(index);
        },
        content: $("#managerole")
    });
    getAlllist(adminType);
}
function getAlllist(adminType){
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/admin/queryDeriveAdmin",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        //async: false,
        data: JSON.stringify({
            "token": token,
            "adminType": adminType
        }),
        success: function (data) {
            var queryDeriveAdmin = data.adminList;
            data.onSuccess = function () {
                $("#emptyRole").empty();
                $("#tabs>#footer").empty();
                var str1 = '';
                for (var m = 0; m < queryDeriveAdmin.length; m++) {
                    str1 += '<tr>' +
                        '<td>' + parseInt(m + 1) + '</td>' +
                        '<td style="display:none;">' + queryDeriveAdmin[m].aid + '</td>' +
                        '<td style="display:none;">' + queryDeriveAdmin[m].uid + '</td>' +
                        '<td>' + queryDeriveAdmin[m].account + '</td>' +
                        '<td>' + queryDeriveAdmin[m].name + '</td>' +
                        '<td>' + queryDeriveAdmin[m].parentAdmin + '</td>' +
                        '<td><a href="#" onclick="delTr(this,'+adminType+')"><i class="fa fa-trash-o"></i></a></td>' +
                        '</tr>';
                }
                $("#emptyRole").append(str1);
                var str2 = '<tfoot id="footer" style="border: 0"><tr style="border: 0"><td colspan="5" class="text-left" style="border: 0"><a href="#" id="role' + adminType + '" onclick="addAdmins(this)">' +
                    '<i class="fa fa-plus"  style="padding-left:10px;"></i>' +
                    '</a></td></tr></tfoot>';
                $("#tabs").append(str2);
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//单击CheckBox
function addAdmin(admintype) {

    $.ajax({
        url: host + "/dcms/api/v1/admin/queryScopeAdmin",
        type: "post",
        //async: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            tree1(data);


        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

    $("#browser").empty();

    layer.open({
        type: 1,
        title: '添加角色',
        shadeClose: true,
        shade: 0.3,
        area: ['280px', '50%'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var userIdList = new Array();
            var listckb = $("input[name='ckbs']:checked");
            if (listckb.length != 0) {
                for (var i = 0; i < listckb.length; i++) {
                    userIdList.push(listckb[i].value);
                }
            }
            $.ajax({
                url: host + "/dcms/api/v1/admin/addAdmin",
                type: "post",
                async: false,
                data: JSON.stringify({
                    "token": token,
                    "adminType": admintype,
                    "userIdList": userIdList
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        getAlllist(admintype);
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index);
        },
        content: $('#tree')
    });
}
function addAdmins(a) {

    var admintype1 = a.id.slice(4);
    addAdmin(admintype1);
}
function delTr(ckb,adminType) {
    layer.alert("确定删除该关联用户？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                url: host + "/dcms/api/v1/admin/deleteAdmin",
                type: "post",
                async: false,
                data: JSON.stringify({
                    "token": token,
                    "aid": $(ckb).parent().parent().find("td").eq(1).text()
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        //$(ckb).parent().parent().remove();
                        getAlllist(adminType)
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