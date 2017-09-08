/**
 * Created by lenovo on 17-2-4.
 */
//login begin
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
$(function () {
    tree();
    getData(0);
});
$(".user-list").click(function () {
    $(".potions1").hide();
    $(".potions").show();
});

//用户详情
$(".all").css("cursor", "pointer");
$(".all").click(function () {
    $(".potions1").show();
    $(".potions").hide();
});
$(".potion").click(function () {
    $(".potions").show();
})

//调用树接口
function tree(name) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0,
            "keyword": name,
            "fileLevel": ""
        }),
        url: host + "/dcms/api/v1/department/queryTree",
        async: false,
        dataType: "json",
        success: function (data) {
            var str = '<li><span style="cursor: pointer;" class="com"  id=' + data.did + ' onclick="clickget(this.id)">' + data.name + '</span>' +
                maketree(data) +
                '</li>';
            $("#browser").append(str);
            $("#browser").treeview({
                collapsed: true,
                add: $("#browser").html
            });

        }
    });

}


function clickget(id) {
    var oldid = $("#eqid").val();
    $("#eqid").val(id);
    getData(0, id);

    if (oldid != '') {
        $("#" + oldid).css("color", '');
    }
    $("#" + id).css("color", 'red');
}

//生成树
function maketree(data) {
    var str = '';
    /*for(var i = 0; i<data.childGroupList.length;i++) {
     str += '<ul>'+
     '<li><span class="folder" onclick="clickget(this.id)" id='+data.childGroupList[i].gid+'>'+data.childGroupList[i].name+'</span>';
     var userlength = data.childGroupList[i].childUserList.length;
     console.log(userlength)
     for(var j = 0; j<userlength;j++) {
     var ob = data.childGroupList[i].childUserList[j];
     str += '<ul>'+
     '<li><span class="file">'+ob.name+'</span>'+
     '</li>'+
     '</ul>'

     }
     str += '</li>'+
     '</ul>'
     }

     for(var i = 0; i<data.childUserList.length;i++) {
     str += '<ul>'+
     '<li><span class="user">'+data.childUserList[i].name+'</span>'+
     '</li>'+
     '</ul>'

     }*/

    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>' +
            '<li><span style="cursor: pointer;" class="dep"  id=' + data.childDepartmentList[i].did + ' onclick="clickget(this.id)">' +data.childDepartmentList[i].name + '</span>' +
            maketree(data.childDepartmentList[i]) +
                '</li>'+
            '</ul>'
    }
    return str;

}


function pageCallback(index) {
    getData(index.getCurrent() - 1);
}
function getData(index, strid) {
    var did;
    var gid;
    if (strid != undefined && strid != null) {
        if (strid.indexOf('did') != -1) {
            did = strid;
        }
        if (strid.indexOf('gid') != -1) {
            gid = strid;
        }
    }
    var level = $('.level option:selected').val()
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/user/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize,
            "keyword": $(".potions table tr input").val(),
            "level": level,
            "state": $('.state option:selected').val(),
            "did": did,
            "gid": gid
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list").empty();
                var datas = result.userList;
                for (var i = 0; i < datas.length; i++) {
                    var level = datas[i].level;
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
                            level = "一般";
                        }
                        if (datas[i].level == 2) {
                            level = "重要";
                        }
                        if (datas[i].level == 3) {
                            level = "核心";
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
                        if (datas[i].adminType == undefined ) {
                            datas[i].adminType = '';
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
                        if (firstLoginTime == 0) {
                            firstLoginTime = "";
                        }
                        if (lastLoginTime == 0) {
                            lastLoginTime= "";
                        }
                    }
                    var str = '<tr>' +
                        '<td style="display:none;"><input type="checkbox" name="ckb"></td>' +
                        '<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].uid + '</td>' +
                        '<td>' + datas[i].account + '</td>' +
                        '<td>' + isUndefined(datas[i].name) + '</td>' +
                        '<td>' + level + '</td>' +
                        '<td>' + isUndefined(datas[i].departmentName) + '</td>' +
                        '<td style="display:none;">' + datas[i].state + '</td>' +
                        '<td>' + datas[i].online + '</td>' +
                        '<td class="all" onclick="allList(this)" style="cursor: pointer;"><a href="#"><i' +
                        ' class="fa fa-list"></i></a></td>' +
                        '</tr>';
                    $(".list").append(str);
                    $(".list td").each(function () {
                        $(this).attr("title", $(this).text());

                    });

                    var str1 = '<table id="' + datas[i].uid + '" style="display:none;background:#fff;margin-top:30px;"' +
                        ' class="user-list table  table-hover table-condensed">' +
                        '<tr>' +
                        '<td>用户ID：</td>' +
                        '<td>' + datas[i].uid + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td style="width:15%;">管理员类型：</td>' +
                        '<td>' + datas[i].adminType +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户姓名：</td>' +
                        '<td>' + isUndefined(datas[i].name) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>性别：</td>' +
                        '<td>' + datas[i].gender + '</td>' + '</tr>' +
                        '<tr>' +
                        '<td>电话：</td>' +
                        '<td>' + isUndefined(datas[i].phone) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>邮箱：</td>' +
                        '<td>' + isUndefined(datas[i].mail) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>职务：</td>' +
                        '<td>' + isUndefined(datas[i].position) + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户密级：</td>';
                    if (datas[i].level == 1) {
                        str1 += '<td><select name="" id="level' + datas[i].uid + '" disabled="disabled"><option value="1" selected>一般</option><option value="2">重要</option><option value="3">核心</option></select></td>';
                    } else if (datas[i].level == 2) {
                        str1 += '<td><select name="" id="level' + datas[i].uid + '" disabled="disabled"><option value="1">一般</option><option value="2" selected>重要</option><option value="3">核心</option></select></td>';
                    } else if (datas[i].level == 3) {
                        str1 += '<td><select name="" id="level' + datas[i].uid + '" disabled="disabled"><option value="1">一般</option><option value="2">重要</option><option value="3" selected>核心</option></select></td>';
                    }
                    str1 += '</tr>' +
                        '<tr>' +
                        '<td>所属组织机构：</td>' +
                        '<td>' + isUndefined(datas[i].departmentName) + '</td>' +
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
                        '<button style="margin-left:10px;" class="btn btn-primary" onclick="resh(this.id)" id=' + datas[i].uid + '>修改密级</button>' +
                        '<button style="margin-left:10px;" class="btn btn-primary">单机策略导出</button>' +
                        '<button style="margin-left:10px;" class="btn btn-primary" onclick="goback(' + index + ')">返回</button>' +
                        '</div>' +
                        '<div id="resh-button' + datas[i].uid + '" style="display:none;">' +
                        '<button style="margin-left:10px;" class="btn btn-primary" onclick="resh_save(this)">保存</button>' +
                        '<button style="margin-left:10px;" class="btn btn-primary" onclick="cel(this)">取消</button>' +
                        '</div>' +
                        ' </td>' +
                        '</tr>' +
                        '</table>';
                    $(".potions1").append(str1);
                }
                if (index == 0) {
                    $('.M-box3').pagination({
                        pageCount: result.totalPages,
                        coping: true,
                        jump:true,
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

//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}
//点击详情
function allList(p) {
    var uid = $(p).parent().find("td").eq(2).text();
    $("#" + uid).show();
    $(".potions1").show();
    $(".potions").hide();
}
//用户信息修改
function resh(uid) {
    //将要修改的密级变为可修改
    $("#level" + uid).attr("disabled", false);
    $("#resh-button" + uid).show();
    $("#user-button" + uid).hide();
}
//用户信息修改和保存
function resh_save(s) {
    //获取用户id
    var uid = $(s).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    var level = $("#level" + uid).val();
    $.ajax({
        url: host + "/dcms/api/v1/user/updateLevel",
        type: "post",
        async: false,
        data: JSON.stringify({
            "token": token,
            "uid": uid,
            "level": level
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#level" + uid).attr("disabled", true);
                $("#resh-button" + uid).hide();
                $("#user-button" + uid).show();
            }
            processResponse(result, 1);

        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}
//取消修改
function cel(s) {
    //获取用户id
    var uid = $(s).parent().parent().parent().parent().find("tr").eq(0).find("td").eq(1).text();
    var level = $("#level" + uid).val();
    $("#level" + uid).attr("disabled", true);
    $("#user-button" + uid).show();
    $("#resh-button" + uid).hide();
}


//返回
function goback(g) {
    getData(g);
    $(".potions1 table").hide();
    $(".potions1").hide();
    $(".potions").show();
    // window.location.reload();
}
//查询用户
function seachUser() {
    getData(0);
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


$(".del").click(function () {
    $(".cont1").show();
});
$(".cel").click(function () {
    $(".cont1").hide();
});
function delTr2() {
    delTr('ckb');
    $(".cont1").hide();

}
