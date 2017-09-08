/**
 * Created by lenovo on 17-2-4.
 */
//login begin
//$(".del-c").click(function () {
//    $(".btns1").show();
//    $(".btns4").hide();
//
//});
$(".add-sea").click(function () {
    var name = $(".bmname").val()
    tree(name)
});
$(".cancle-sea").click(function () {
    $(".btns3").hide();
    $(".btns1").show();
});
//添加或者修改组
function saveGroup() {
    var type = $("#type").val();
    var did = $("#eqid").val();
    if (type == 1) {
        addgroup(did)
    }
    if (type == 2) {
        reshgroup(did)
    }
}
//添加组
function addgroup(did) {
    var name = $(".nam").val();
    if (name == null || name.replace(/(^s*)|(s*$)/g, "").length == 0) {
        $(".error-span").text("不能为空！")
        return
    }
    var description = $(".de").val();
    if (description == null || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
        $(".error-span").text("不能为空！")
        return
    }
    $.ajax({
        url: host + "/dcms/api/v1/group/add",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "name": $(".nam").val(),
            "description": $(".de").val(),
            "did": did
        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
            }
            processResponse(data, 1);
        },
        error: function (data) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//修改组
function reshgroup(gid) {
    $.ajax({
        url: host + "/dcms/api/v1/group/update",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "gid": gid,
            "name": $(".nam").val(),
            "description": $(".de").val()
            //"did":did
        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
                $(".btns2").hide();
                $(".btns1").show();
            }
            processResponse(data, 1);
        },
        error: function (data) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//删除组

function deletegroup(id) {
    $.ajax({
        url: host + "/dcms/api/v1/group/delete",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "gid": id

        }),
        success: function (data) {
            data.onSuccess = function () {
                tree();
            }
            processResponse(data, 1);
        },
        error: function (data) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

$(".cancle-p").click(function () {
    $(".de").val("");
    $(".nam").val("");
    $(".btns1").show();
    $(".btns2").hide();
});
function tree(name) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 0,
            "hasGroup": 1,
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
        }
    });

}
//组移除用户
function groupRemoveUser(ckb) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        layer.alert("请至少选择一个用户组删除！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index1, layero) {
                layer.close(index1);
            }
        });
        return;
    }
    layer.alert("确定从系统中删除选中组用户？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var gid = $("#eqid").val();
            var uids = new Array(ckbs.size());
            for (var i = 0; i < ckbs.length; i++) {
                uids[i] = $(ckbs[i]).parent().parent().find("td").eq(2).text();
            }
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/group/deleteUserInBatches",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "uidList": uids,
                    "gid": gid
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        getData(0, gid);
                    }
                    processResponse(result, 1);
                }
            });
            layer.close(index1);
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
        $(".resh").attr("disabled", true);
        $(".delt").attr("disabled", true);
        //添加
        $(".add").unbind("click").click(function () {
            $("#type").val(1);
            $(".btns1").hide();
            $(".btns2").show();
        });

    } else if ($("#" + id).siblings().length > 0) {
        $(".opation").show();
        $(".add").attr("disabled", false);
        $(".resh").attr("disabled", true);
        $(".delt").attr("disabled", true);
        //添加
        $(".add").unbind("click").click(function () {
            $("#type").val(1);
            $(".btns1").hide();
            $(".btns2").show();


        });


    } else {
        $(".opation").show();
        //判断最后一级是组还是部门，通过id判断
        if (id.indexOf('did') != -1) {
            $(".add").attr("disabled", false);
            $(".delt").attr("disabled", true);
            $(".resh").attr("disabled", true);
        }
        if (id.indexOf('gid') != -1) {
            $(".add").attr("disabled", true);
            $(".delt").attr("disabled", false);
            $(".resh").attr("disabled", false);
            $("#panel a").show();
        }

        //添加
        $(".add").click(function () {
            $("#type").val(1);
            $(".btns1").hide();
            $(".btns2").show();


        });
        //修改
        $(".resh").unbind("click").click(function () {
            $("#type").val(2);
            //根据gid查找组
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/group/queryById",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "gid": id
                }),
                success: function (result) {
                    $(".nam").val(result.name);
                    $(".de").val(result.description);
                }
            })
            $(".btns2").show();
            $(".btns1").hide();

        });
        //删除
        $(".delt").unbind("click").click(function () {
            layer.alert('确定删除选中的用户组？', {
                icon: 3,
                skin: 'layer-ext-moon',
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    deletegroup(id);
                    layer.close(index);
                }
            });
        });
    }
    $("#eqid").val(id);
    if (id != undefined && id != null) {
        if (id.indexOf('did') != -1) {
            $("#panel a").hide();
            $(".potions").show();
            getData(0, id);
        }
        if (id.indexOf('gid') != -1) {
            $("#panel a").show();
            $(".potions").show();
            getData(0, id);
        }
    }
}
//生成树
function maketree(data) {
    var str = '';
    for (var i = 0; i < data.childGroupList.length; i++) {
        str += '<ul style="cursor: pointer">' +
            '<li><span class="group" onclick="clickget(this.id)" id=' + data.childGroupList[i].gid + '>' + data.childGroupList[i].name + '</span>' +
            '</li>' +
            '</ul>'
    }

    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul  style="cursor: pointer">' +
            '<li><span class="dep"  id=' + data.childDepartmentList[i].did + ' onclick="clickget(this.id)">' + data.childDepartmentList[i].name + '</span>' +
            maketree(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>'
    }

    return str;

}
$(function () {
    allCheck("allCkb", "ckb");
    tree();
    getData(0);
});
//添加用户到用户组
function adduser() {
    groupUser(0);
    layer.open({
        type: 1,
        title: '添加组用户',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '400px'],
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            addusers();
            layer.close(index1);
        },
        content: $('#add-groupuser')
    });
}
function addusers() {
    var id = $("#eqid").val();
    gaddu('ckbg', id);
}
function pageCallback(index) {
    var strid = $("#eqid").val();
    getData(index.getCurrent() - 1, strid);
}
function getData(index, strid) {
    $("#thead").empty();
    var did;
    var gid;
    var level = $('.level option:selected').val();
    var str1 = '<tr>';
    if (strid == undefined) {
        str1 += '<th style="text-align: center;width: 3%"><input id="allCkb" type="checkbox" disabled="disabled"></th>';
    }else{
        if (strid.indexOf('did') != -1) {
            did = strid;
            str1 += '<th style="text-align: center;width: 3%"><input id="allCkb" type="checkbox"' +
                'disabled="disabled"></th>';
        }
        if (strid.indexOf('gid') != -1) {
            gid = strid;
            str1 += '<th style="text-align: center;width: 3%"><input id="allCkb" type="checkbox"></th>';
        }
    }
    str1 += '<th style="text-align: center;width: 8%;">编号</th>' +
        '<th style="text-align: center;display: none">用户ID</th>' +
        '<th style="text-align: center;width: 17%;">登录名</th>' +
        '<th style="text-align: center;width: 10%;">用户姓名</th>' +
        '<th style="text-align: center;width: 7%;">密级</th>' +
        '<th style="text-align: center;width: 25%;">所属部门</th>' +
        '<th style="text-align: center;width: 10%;">用户状态</th>' +
        '<th style="text-align: center;width: 10%;">在线状态</th>' +
        '</tr>';
    $("#thead").append(str1);
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
            "gid": gid,
            "did": did
        }),
        success: function (result) {
            $(".list").empty();

            var datas = result.userList;
            result.onSuccess = function () {
                for (var i = 0; i < datas.length; i++) {
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
                    }
                    var num = index * pageSize + parseInt(i) + 1;
                    var str = '<tr>' +
                        '<td>';
                    if (strid == undefined) {
                        str += '<input type="checkbox" name="ckb" disabled="disabled"></td>';
                    } else {
                            if (strid.indexOf('did') != -1) {
                                str += '<input type="checkbox" name="ckb" disabled="disabled"></td>';
                            }
                            if (strid.indexOf('gid') != -1) {
                                str += '<input type="checkbox" name="ckb"></td>';
                            }

                    }
                    str += '<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].uid + '</td>' +
                        '<td>' + datas[i].account + '</td>' +
                        '<td>' + datas[i].name + '</td>' +
                        '<td>' + datas[i].level + '</td>' +
                        '<td>' + datas[i].departmentName + '</td>' +
                        '<td>' + datas[i].state + '</td>' +
                        '<td>' + datas[i].online + '</td>' +
                        '</tr>';
                    $(".list").append(str);
                    $(".list td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }
                allCheck("allCkb", "ckb");
            }
            processResponse(result, 0);
            if (index == 0) {
                $('.M-box3').pagination({
                    pageCount: result.totalPages,
                    coping: true,
                    jump: true,
                    callback: pageCallback
                });
            }
        }
    });
}

function delTr(ckb) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        alert("要删除指定行，需选中要删除的行！");
        return;
    }
    ckbs.each(function () {
        $.ajax({
            url: host + "/dcms/api/v1/user/deleteById",
            type: "post",
            async: false,
            crossDomain: false,
            data: JSON.stringify({
                "token": token,
                "uid": $(this).parent().parent().find("td").eq(2).text()
            }),
            success: function (data) {
                data.onSuccess = function () {
                    $(this).parent().parent().remove();
                    window.location.href = "../user-formork.html";
                }
                processResponse(data, 1);
            },
            error: function (data) {
                showGlobleTip("服务器异常，请联系管理员");
            }
        });
    });
}
function groupUser(index) {
    allChecko("allCkbo", "ckbg");
    $(".cont").show();
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
            "pageSize": pageSize
        }),
        success: function (result) {
            $(".coolist").empty();
            var datas = result.userList;
            for (var i = 0; i < datas.length; i++) {
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
                }
                var num = index * pageSize + parseInt(i) + 1;
                var str = '<tr>' +
                    '<td><input type="checkbox" name="ckbg"></td>' +
                    '<td>' + num + '</td>' +
                    '<td style="display:none;">' + datas[i].uid + '</td>' +
                    '<td>' + datas[i].account + '</td>' +
                    '<td>' + datas[i].name + '</td>' +
                    '<td>' + datas[i].level + '</td>' +
                    '<td>' + datas[i].departmentName + '</td>' +
                    '</tr>';
                $(".coolist").append(str);
            }
            if (index == 0) {
                $('.M-box2').pagination({
                    pageCount: result.totalPages,
                    coping: true,
                    callback: pageCallbacktwo
                });
            }
        }
    });


}

function pageCallbacktwo(index) {
    groupUser(index.getCurrent() - 1)
}
function delTr2() {
    delTr('ckb');
}
function allCheck(allCkb, items) {
    $("#" + allCkb).click(function () {
        if (this.checked) {
            $('[name=' + items + ']:checkbox').prop("checked", true);
        } else {
            $('[name=' + items + ']:checkbox').prop("checked", false);
        }
    });

}
function allChecko(allCkbo, items) {
    $("#" + allCkbo).click(function () {
        if (this.checked) {
            $('[name=' + items + ']:checkbox').prop("checked", true);
        } else {
            $('[name=' + items + ']:checkbox').prop("checked", false);
        }
    });

}


function gaddu(ckb, pid) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        $(".contsing").show()
        $(".sing-ok").click(function () {
            $(".contsing").hide();
        });
        return;
    }

    //定义一个存放cid的数组
    var uids = new Array(ckbs.size());

    for (var i = 0; i < ckbs.length; i++) {
        uids[i] = $(ckbs[i]).parent().parent().find("td").eq(2).text();
    }
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/group/addUserInBatches",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "uidList": uids,
            "gid": pid
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".cont").hide();
                getData(0, pid);
            }
            processResponse(result, 1);
        }
    });


}