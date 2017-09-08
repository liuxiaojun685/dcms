$(".framework").click(function () {
    $(".frameworks").show();
    $(".user-groups").hide();
});
$(".cancle-p").click(function () {
    $(".de").val("");
    $(".nam").val("");
    $(".btns1").show();
    $(".btns2").hide();
});
//将时间戳转化为时间
function add0(m) {
    return m < 10 ? '0' + m : m
}
function getLocalTime(shijianchuo) {
//shijianchuo是整数，否则要parseInt转换
    if (shijianchuo == undefined || shijianchuo == 0) {
        return "";
    }
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '/' + add0(m) + '/' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}
//用户列表的获取
// use-list and page
$(function () {
	allCheck("allCkb", "ckb");
    tree();
    getData(0);
});
function pageCallback(index) {
    var strid = $("#eqid").val();
    getData(index.getCurrent() - 1, strid);
}
function getData(index, id) {
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/user/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize,
            "keyword": $(".potions table tr input").val(),
            "level": $('.level option:selected').val(),
            "state": $('.state option:selected').val(),
            "onlineState": $('.passwdState option:selected').val(),
            "did": id
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list").empty();
                $(".potions1").empty();
                var datas = result.userList;
                for (var i = 0; i < datas.length; i++) {
                    var createTime = getLocalTime(datas[i].createTime);
                    var firstLoginTime = getLocalTime(datas[i].firstLoginTime);
                    var lastLoginTime = getLocalTime(datas[i].lastLoginTime);
                    var num = index * pageSize + parseInt(i) + 1;
                    if (datas.length != 0) {
                        if (datas[i].state == 0) {
                            datas[i].state = "默认";
                        }
                        if (datas[i].state == 4) {
                            datas[i].state = "锁定";
                        }
                        if (datas[i].online == 1) {
                            datas[i].online = "<img src='../css/images/user.png'>";
                        }
                        if (datas[i].online == 2) {
                            datas[i].online = "<img src='../css/images/user-off.png'>";
                        }
                        if (datas[i].gender == 1) {
                            datas[i].gender = "男";
                        }
                        if (datas[i].gender == 2) {
                            datas[i].gender = "女";
                        }
                        if (datas[i].level == 1) {
                            datas[i].level = "一般";
                        }
                        if (datas[i].level == 2) {
                            datas[i].level = "重要";
                        }
                        if (datas[i].level == 3) {
                            datas[i].level = "核心";
                        }
                        if (datas[i].adminType == undefined) {
                            datas[i].adminType = '普通用户';
                        }
                        if (datas[i].adminType == 1) {
                            datas[i].adminType = "审计管理员";
                        }
                        if (datas[i].adminType == 2) {
                            datas[i].adminType = "系统管理员";
                        }
                        if (datas[i].adminType == 3) {
                            datas[i].adminType = "安全保密管理员";
                        }
                        if (datas[i].createFrom == 1) {
                            datas[i].createFrom = "手动创建";
                        }
                        if (datas[i].createFrom == 2) {
                            datas[i].createFrom = "导入创建";
                        }
                        if (datas[i].createFrom == 3) {
                            datas[i].createFrom = "同步创建";
                        }
                        if (firstLoginTime == 0) {
                            firstLoginTime = ""
                        }
                        if (lastLoginTime == 0) {
                            lastLoginTime = ""
                        }
                    }
                    var str = '<tr>' ;
                    if(datas[i].isHaveRole == 1){
                    	
                    	str+='<td><input type="checkbox" disabled="disabled"></td>' ;
                    }else{
                    	str+='<td><input type="checkbox" name="ckb"></td>' ;
                    }
                        str+='<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].uid + '</td>' +
                        '<td>' + datas[i].account + '</td>' +
                        '<td>' + datas[i].name + '</td>' +
                        '<td>' + datas[i].level + '</td>' +
                        '<td>' + datas[i].departmentName + '</td>' +
                        '<td>' + datas[i].state + '</td>' +
                        '<td>' + datas[i].online + '</td>' +
                        '<td class="all" onclick="allList(this)" style="cursor: pointer;"><a href="javaScript:;;"><i' +
                        ' class="fa fa-list"></i></a></td>' +
                        '</tr>';
                    $(".list").append(str);
                    $(".list td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                    var str1 = '<table id="' + datas[i].uid + '" style="display:none;" class="user-list table' +
                        ' table-bordered table-hover' +
                        ' table-condensed">' +
                        '<tr>' +
                        '<td style="width:15%;">用户ID：</td>' +
                        '<td>' + datas[i].uid +
                        '</tr>' +
                        '<tr>' +
                        '<td style="width:15%;">系统角色：</td>' +
                        '<td>' + datas[i].adminType +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户姓名：</td>' +
                        '<td><input type="text" value="' + datas[i].name + '" disabled="disabled"/></td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>性别：</td>';
                    if (datas[i].gender == "男") {
                        str1 += '<td><select name="" id=""><option value="1" selected>男</option><option value="2">女</option></select></td>';
                    } else if (datas[i].gender == "女") {
                        str1 += '<td><select name="" id=""><option value="1">男</option><option value="2" selected>女</option></select></td>';
                    }
                    str1 += '</tr>' +
                        '<tr>' +
                        '<td>电话：</td>' +
                        '<td><input type="text" value="' + datas[i].phone + '" disabled="disabled"/></td>' +
                        '</tr>' +
                        '<td>邮箱：</td>' +
                        '<td><input type="text" value="' + datas[i].mail + '" disabled="disabled"/></td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>职务：</td>' +
                        '<td><input type="text" value="' + datas[i].position + '" disabled="disabled"/></td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户密级：</td>' +
                        '<td>' + datas[i].level + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>所属组织机构：</td>' +
                        '<td><input type="text" class="departmentName"   id="role_' + datas[i].uid + '"  value="' + datas[i].departmentName + '" disabled="disabled"/><input type="hidden"  value="" /><input type="hidden" id="deparments" value="" /></td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>状态：</td>' +
                        '<td>' + datas[i].state + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>来源：</td>' +
                        '<td>' + datas[i].createFrom + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户创建日期：</td>' +
                        '<td>' + createTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>首次登录日期：</td>' +
                        '<td>' + firstLoginTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>末次登录日期：</td>' +
                        '<td>' + lastLoginTime + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>末次登录终端：</td>' +
                        '<td>' + datas[i].lastLoginAddress + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td style="text-align: center;"colspan="2">' +
                        '<div id="user-button' + datas[i].uid + '">' +
                        '<button onclick="resh(this)" style="margin-left:10px;" class="btn btn-primary">修改用户</button>';
                    if (datas[i].state == "默认") {
                        str1 += '<button onclick="unlock(this)" style="margin-left:10px;" disabled="disabled"' +
                            ' class="btn btn-primary">解锁用户</button>';
                    } else if (datas[i].state == "锁定") {
                        str1 += '<button onclick="unlock(this)" style="margin-left:10px;" class="btn btn-primary">解锁用户</button>';
                    }
                    str1 += '<button style="margin-left:10px;" onclick="resetPasswd(this)" class="btn' +
                        ' btn-primary">重置密码</button>' +
                        '<button style="margin-left:10px;" class="btn btn-primary">单机策略导出</button>' +
                        '<button style="margin-left:10px;" onclick="goback(this,' + index + ')" class="btn' +
                        ' btn-primary">返回</button>' +
                        '</div>' +
                        '<div id="resh-button' + datas[i].uid + '" style="display:none;">' +
                        '<button style="margin-left:10px;" onclick="resh_save(this)" class="btn btn-primary">保存</button>' +
                        '<button style="margin-left:10px;" onclick="cel(this)" class="btn btn-primary">取消</button>' +
                        '</div>' +
                        ' </td>' +
                        '</tr>' +
                        '</table>';
                    $(".potions1").append(str1);
                    $("#role_" + datas[i].uid).unbind("click").click(function () {
                        changeDeparment(this);
                    })
                }
                $(".del").unbind("click").click(function () {
                    delTr('ckb', index);
                })
                if (index == 0) {
                    $('.M-box3').pagination({
                        pageCount: result.totalPages,
                        jump:true,
                        coping: true,
                        homePage: '首页',
                        endPage: '末页',
                        prevContent: '上页',
                        nextContent: '下页',
                        callback: pageCallback
                    });
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//点击详情
function allList(p) {
    var uid = $(p).parent().find("td").eq(2).text();
    $("#" + uid).show();
    $(".potions1").show();
    $(".potions").hide();
}

//用户信息修改
function resh(r) {

    //获取用户id
    var uid = $(r).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    //alert(uid);
    var name = $(r).parent().parent().parent().parent().find("tr").eq(2).find("td").eq(1).find("input").eq(0);
    var gender = $(r).parent().parent().parent().parent().find("tr").eq(3).find("td").eq(1).find("select").eq(0);
    var phone = $(r).parent().parent().parent().parent().find("tr").eq(4).find("td").eq(1).find("input").eq(0);
    var mail = $(r).parent().parent().parent().parent().find("tr").eq(5).find("td").eq(1).find("input").eq(0);
    var position = $(r).parent().parent().parent().parent().find("tr").eq(6).find("td").eq(1).find("input").eq(0);
    var deparment = $(r).parent().parent().parent().parent().find("tr").eq(8).find("td").eq(1).find("input").eq(0);
    var remove = [];
    remove.push(name, phone, mail, position, deparment, gender);
    for (var i = 0; i < remove.length; i++) {
        remove[i][0].disabled = false;
        remove[i][0].className = "class";
    }
    $("#resh-button" + uid).show();
    $(r).parent().hide();
}
//调用树接口
function tree1(id) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 1,
            "fileLevel": ""
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        //async: false,
        dataType: "json",
        success: function (data) {
            data.onSuccess = function () {
                var str = '<li><input type="hidden"  id="roleid"/>';
                if ($(id).val() == data.name) {
                    $("#deparments").val("change_"+data.did);
                    str += '<input type="checkbox" checked value=' + data.did + ' id="change_' + data.did + '" name="only" onclick="checkRoot(this.id)">';
                }else{
                    str+='<input type="checkbox" value=' + data.did + ' id="change_' + data.did + '" name="only" onclick="checkRoot(this.id)">';
                }
                str += '<span class="com">' + data.name + '</span>' +
                    maketree1(data,id) +
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
    //alert()
}
//生成树
function maketree1(data,id) {
    var str = '';
    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>' +
            '<li>';
        if($(id).val() == data.childDepartmentList[i].name){
            $("#deparments").val("change_"+data.childDepartmentList[i].did);
            str+='<input type="checkbox" checked data-toggle="' + data.did + '" value=' + data.childDepartmentList[i].did + ' id="change_' + data.childDepartmentList[i].did + '" name="only" onclick="checkRoot(this.id)">';
        }else{
            str+='<input type="checkbox" data-toggle="' + data.did + '" value=' + data.childDepartmentList[i].did + ' id="change_' + data.childDepartmentList[i].did + '" name="only" onclick="checkRoot(this.id)">';
        }
           str+= '<span class="dep">' + data.childDepartmentList[i].name + '</span>'
            + maketree1(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>';
    }
    return str;
}
function checkRoot(id) {
    var did=$("#deparments").val();
    if(did != ""){
        $("#"+did).prop("checked",false);
    }
    $("#deparments").val(id);
    //if ($("input[name='only']:checked").length == 1) {
    //    $("input[name='only']").prop("disabled", true);
    //    $("input[name='only']:checked").prop("disabled", false);
    //    $("input[name='only']:checked").prop("checked", true);
    //} else {
    //    $("input[name='only']:checked").prop("checked", false);
    //    $("input[name='only']").prop("disabled", false);
    //}
}
function changeDeparment(id) {
    $("#browser1").empty();
    tree1(id);
    //alert(123)
    layer.open({
        type: 1,
        title: '选择部门',
        shadeClose: true,
        shade: 0.3,
        area: ['280px', '50%'],
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var name = $("input[name='only']:checked").siblings("span").text();
            var did = $("input[name='only']:checked").val();
            $(id).val(name);
            $(id).siblings("input").val(did);
            //saverole();
            layer.close(index);
        },
        content: $('#deparment')
    });
}

//用户信息修改和保存
function resh_save(s) {
    //获取用户id
    var uid = $(s).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    var name = $(s).parent().parent().parent().parent().find("tr").eq(2).find("td").eq(1).find("input").eq(0);
    var gender = $(s).parent().parent().parent().parent().find("tr").eq(3).find("td").eq(1).find("select").eq(0);
    var phone = $(s).parent().parent().parent().parent().find("tr").eq(4).find("td").eq(1).find("input").eq(0);
    var mail = $(s).parent().parent().parent().parent().find("tr").eq(5).find("td").eq(1).find("input").eq(0);
    var position = $(s).parent().parent().parent().parent().find("tr").eq(6).find("td").eq(1).find("input").eq(0);
    var deparment = $(s).parent().parent().parent().parent().find("tr").eq(8).find("td").eq(1).find("input").eq(0);
    var did = $(s).parent().parent().parent().parent().find("tr").eq(8).find("td").eq(1).find("input").eq(1).val();
    var remove = [];
    remove.push(name, phone, mail, position, gender, deparment);
    $.ajax({
        url: host + "/dcms/api/v1/user/updateBase",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "uid": uid,
            "name": name.val(),
            "gender": gender.find("option:selected").val(),
            "phone": phone.val(),
            "mail": mail.val(),
            "position": position.val(),
            "did": did,
        }),
        success: function (data) {
            data.onSuccess = function () {
                for (var i = 0; i < remove.length; i++) {
                    remove[i][0].disabled = "disabled";
                    remove[i][0].className = "";
                }
                $("#user-button" + uid).show();
                $(s).parent().hide();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//取消修改
function cel(c) {
    var name = $(c).parent().parent().parent().parent().find("tr").eq(2).find("td").eq(1).find("input").eq(0);
    var gender = $(c).parent().parent().parent().parent().find("tr").eq(3).find("td").eq(1).find("select").eq(0);
    var phone = $(c).parent().parent().parent().parent().find("tr").eq(4).find("td").eq(1).find("input").eq(0);
    var mail = $(c).parent().parent().parent().parent().find("tr").eq(5).find("td").eq(1).find("input").eq(0);
    var position = $(c).parent().parent().parent().parent().find("tr").eq(6).find("td").eq(1).find("input").eq(0);
    var deparment = $(c).parent().parent().parent().parent().find("tr").eq(8).find("td").eq(1).find("input").eq(0);
    var remove = [];
    remove.push(name, phone, mail, position, deparment, gender);
    for (var i = 0; i < remove.length; i++) {
        remove[i][0].disabled = "disabled";
        remove[i][0].className = "";
    }
    var uid = $(c).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    $("#user-button" + uid).show();
    $(c).parent().hide();
}
//用户解锁
function unlock(u) {
    var uid = $(u).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).find("span").eq(0).text();
    var state = $(u).parent().parent().parent().parent().find("tr").eq(8).find("td").eq(1);
    var button = $(u).parent().parent().parent().parent().find("tr").eq(15).find("td").eq(0).find("div").eq(0).find("button").eq(1);
    $.ajax({
        url: host + "/dcms/api/v1/user/unlock",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "uid": uid
        }),
        success: function (data) {
            data.onSuccess = function () {
                state.text("默认");
                button.attr("disabled", "disabled");
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//重置密码
function resetPasswd(t) {
    var uid = $(t).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    //alert(uid)
    $.ajax({
        url: host + "/dcms/api/v1/user/unlock",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "uid": uid
        }),
        success: function (data) {
            data.onSuccess = function () {
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//返回
function goback(g, index) {
    getData(index);
    var uid = $(g).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).find("span").eq(0).text();
    $("#" + uid).hide();
    $(".potions1").hide();
    $(".potions").show();
    // window.location.reload();
}
//查询用户
function seachUser() {
    var id = $("#eqid").val();
    getData(0, id);
}
//重置查询
function rest() {
    $(".potions table tr input").val("");
    var level = $(".level");
    for (var i = 0; i < level.length; i++) {
        level[i].options[0].selected = true;
    }
    var state = $(".state");
    for (var i = 0; i < state.length; i++) {
        state[i].options[0].selected = true;
    }
}
function delTr(ckb, index) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        layer.alert("请至少选择一个用户删除！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index1, layero) {
                layer.close(index1);
            }
        });
        return;
    }
    layer.alert("确定从系统中删除选中的用户？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var id = $("#eqid").val();
            var uids = new Array(ckbs.size());
            for (var i = 0; i < ckbs.length; i++) {
                uids[i] = $(ckbs[i]).parent().parent().find("td").eq(2).text();
            }
            //ckbs.each(function () {
            $.ajax({
                url: host + "/dcms/api/v1/user/deleteByIds",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "uids": uids
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        $(this).parent().parent().remove();
                        getData(index, id)
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            //});
            layer.close(index1);
        }
    });
}

//树
function tree(name) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 0,
            "hasGroup": 0,
            "keyword": name,
            "fileLevel": ""
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        dataType: "json",
        crossDomain: false,
        success: function (data) {
            data.onSuccess = function () {
                var str = '<li><span class="com"  id=' + data.did + ' onclick="clickget(this.id)">' + data.name + '</span>' +
                    maketree(data) +
                    '</li>';
                $("#browser").empty();
                $("#browser").append(str);
                $("#browser").treeview({
                    collapsed: true
                    //add:$("#browser").html
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

    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul style="cursor: pointer">' +
            '<li><span class="dep"  id=' + data.childDepartmentList[i].did + ' onclick="clickget(this.id)">' + data.childDepartmentList[i].name + '</span>' +
            maketree(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>'
    }

    return str;

}
//添加用户
function adduser() {
    $(".login-name").val("");
    $(".add-name").val("");
    $(".phone").val("");
    $(".email").val("");
    $(".position").val("");
    layer.open({
        type: 1,
        title: '添加用户',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '400px'],
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            $(".name").text("");
            $(".account").text("");
            var keys = 0;
            var account = $(".login-name").val();
            if (account == null || account.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".account").text("登录名不能为空！")
                keys = 1;
            }
            var name = $(".add-name").val();

            if (name == null || name.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".name").text("用户姓名不能为空！")
                keys = 1;
            }
            if (keys == 0) {
                saveuser();
                layer.close(index1);
            }
        },
        content: $('#add-formorkuser')
    });

}
function saveuser() {
    var id = $("#eqid").val();
    $.ajax({
        url: host + "/dcms/api/v1/user/add",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "account": $(".login-name").val(),
            "name": $(".add-name").val(),
            "gender": $("input[name='sex']:checked").val(),
            "phone": $(".phone").val(),
            "mail": $(".email").val(),
            "position": $(".position").val(),
            "did": id
        }),
        success: function (data) {
            data.onSuccess = function () {
                getData(0, id);
                $(".cont").hide();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//点击树节点后的操作
function clickget(id) {
    var oldid = $("#eqid").val();
    if (oldid != '') {
        $("#" + oldid).css("color", '');
    }
    $("#" + id).css("color", 'red');

    //判断是否是父部门，如果是。不能删除
    if ('did-root' == id) {
        $(".opation").show();
        $(".add").attr("disabled", false);
        $(".resh").attr("disabled", false);
        $(".delt").attr("disabled", true);
        //添加
        $(".add").unbind("click").click(function () {
            $("#type").val(1);
            $(".btns1").hide();
            $(".btns2").show();

        });
        //修改
        $(".resh").unbind("click").click(function () {
            $("#type").val(2);
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/department/queryById",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "did": id
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(".btns1").hide();
                        $(".nam").val(result.name);
                        $(".de").val(result.description);
                        $(".btns2").show();
                    }
                    processResponse(result, 0);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })

        });

    } else {
        $(".opation").show();
        $(".add").attr("disabled", false);
        $(".resh").attr("disabled", false);
        $(".delt").attr("disabled", false);
        //添加
        $(".add").unbind("click").click(function () {
            $("#type").val(1);
            $(".btns1").hide();
            $(".btns2").show();


        });
        //修改
        $(".resh").unbind("click").click(function () {
            $("#type").val(2);
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/department/queryById",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "did": id
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(".btns1").hide();
                        $(".nam").val(result.name);
                        $(".de").val(result.description);
                        $(".btns2").show();
                    }
                    processResponse(result, 0);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })

        });

        //删除
        $(".delt").unbind("click").click(function () {
            layer.alert("确定删除选中的用户部门？", {
                icon: 3,
                skin: 'layer-ext-moon',
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    deletedepartment(id);
                    layer.close(index);
                }
            });
        });
    }
    $("#eqid").val(id);
    getData(0, id);
}

//添加或修改部门
//type 1 添加；2修改
function saveDepartment() {
    var did = $("#eqid").val();
    var type = $("#type").val();
    if (type == 1) {
        adddepartment(did);
    } else if (type == 2) {
        reshdepartment(did)
    }
}

//添加部门
function adddepartment(did) {

    var keys = 0;
    var name = $(".nam").val();
    if (name == null || name.replace(/(^s*)|(s*$)/g, "").length == 0) {
        $(".error-span").text("不能为空！")
        return
    }
    var description = $(".de").val();
    // if (description == null || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
    //     $(".error-span").text("不能为空！")
    //     return
    // }
    $.ajax({
        url: host + "/dcms/api/v1/department/add",
        type: "post",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "name": $(".nam").val(),
            "description": $(".de").val(),
            "parentDid": did
        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//修改组
function reshdepartment(did) {
    $.ajax({
        url: host + "/dcms/api/v1/department/updateById",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "did": did,
            "name": $(".nam").val(),
            "description": $(".de").val()
            //"parentDid":did
        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
                $(".btns2").hide();
                $(".btns1").show();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//删除组

function deletedepartment(id) {
    $.ajax({
        url: host + "/dcms/api/v1/department/deleteById",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "did": id

        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
            }
            processResponse(data, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//查询部门
$(".add-sea").click(function () {
    var name = $(".bmname").val()
    tree(name)
});

function allCheck(allCkb, items) {
    $("#" + allCkb).click(function () {
        if (this.checked) {
            $('[name=' + items + ']:checkbox').prop("checked", true);
        } else {
            $('[name=' + items + ']:checkbox').prop("checked", false);
        }
    });

}
