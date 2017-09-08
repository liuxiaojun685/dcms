/**
 * Created by lenovo on 17-2-9.
 */
//var host="http://47.93.78.186:8080";
$(function () {
    getData(0);
});

function getData(index) {

    var did;
    var gid;
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/user/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize
//          "keyword": $(".potions table tr input").val(),
//           "level": $('.level option:selected').val(),
//           "state":$('.state option:selected').val(),
//           "did":did,
//            "gid":gid
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list").empty();
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
                            datas[i].online = "在线";
                        }
                        if (datas[i].online == 2) {
                            datas[i].online = "离线";
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
                    var createTime = new Date(datas[i].createTime).toLocaleDateString();
                    var firstLoginTime = new Date(datas[i].firstLoginTime).toLocaleDateString();
                    var lastLoginTime = new Date(datas[i].lastLoginTime).toLocaleDateString();
                    var num = index * pageSize + parseInt(i) + 1;
                    var str = '<tr>' +
                        '<td><input type="checkbox" name="ckb"></td>' +
                        '<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].uid + '</td>' +
                        '<td>' + datas[i].uid + '</td>' +
                        '<td>' + datas[i].name + '</td>' +
                        '<td>' + datas[i].level + '</td>' +
                        '<td>' + datas[i].departmentName + '</td>' +
                        '<td>' + datas[i].state + '</td>' +
                        '<td>' + datas[i].online + '</td>' +
                        '<td class="all" onclick="allList(this)" style="cursor: pointer;">用户详情</td>' +
                        '</tr>';
                    $(".list").append(str);

                    var str1 = '<table id="' + datas[i].uid + '" style="display:none;" class="user-list">' +
                        '<tr>' +
                        '<td>用户ID：</td>' +
                        '<td><span>' + datas[i].uid + '</span>(用户ID不能改)</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户姓名：</td>' +
                        '<td><input type="text" value="' + datas[i].name + '" disabled="disabled"/></td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>性别：</td>';
                    if (datas[i].gender == "男") {
                        str1 += '<td><select name="" id=""><option value="1" selected>男</option><option value="2">女</option></select>&nbsp;&nbsp;(可以修改)</td>';
                    } else if (datas[i].gender == "女") {
                        str1 += '<td><select name="" id=""><option value="1">男</option><option value="2" selected>女</option></select>&nbsp;&nbsp;(可以修改)</td>';
                    }
                    str1 += '</tr>' +
                        '<tr>' +
                        '<td>电话：</td>' +
                        '<td><input type="text" value="' + datas[i].phone + '" disabled="disabled"/>&nbsp;&nbsp;(可以修改)</td>' +
                        '</tr>' +
                        '<td>邮箱：</td>' +
                        '<td><input type="text" value="' + datas[i].mail + '" disabled="disabled"/>&nbsp;&nbsp;(可以修改)</td>' +
                        '</tr>' +
                        '<tr style="border:1px dashed #000;">' +
                        '<td>职务：</td>' +
                        '<td><input type="text" value="' + datas[i].position + '" disabled="disabled"/>&nbsp;&nbsp;(可以修改)</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>用户密级：</td>' +
                        '<td>' + datas[i].level + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td>所属组织机构：</td>' +
                        '<td><input type="text" value="' + datas[i].departmentName + '" disabled="disabled"/>&nbsp;&nbsp;(可以修改)</td>' +
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
                        '<td>' + datas[i].lastLoginType + '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td style="border:1px dashed #000;text-align: center;"colspan="2">' +
                        '<div id="user-button' + datas[i].uid + '">' +
                        '<button onclick="resh(this)" style="margin-left:10px;">修改用户</button>';
                    if (datas[i].state == "默认") {
                        str1 += '<button onclick="unlock(this)" style="margin-left:10px;" disabled="disabled">解锁用户</button>';
                    } else if (datas[i].state == "锁定") {
                        str1 += '<button onclick="unlock(this)" style="margin-left:10px;">解锁用户</button>';
                    }
                    str1 += '<button style="margin-left:10px;" onclick="resetPasswd(this)">重置密码</button>' +
                        '<button style="margin-left:10px;">单机策略导出</button>' +
                        '<button style="margin-left:10px;" onclick="goback(' + index + ')">返回</button>' +
                        '</div>' +
                        '<div id="resh-button' + datas[i].uid + '" style="display:none;">' +
                        '<button style="margin-left:10px;" onclick="resh_save(this)">保存</button>' +
                        '<button style="margin-left:10px;" onclick="cel(this)">取消</button>' +
                        '</div>' +
                        ' </td>' +
                        '</tr>' +
                        '</table>';
                    $(".potions1").append(str1);
                }
                if (index == 0) {
                    $('.M-box3').pagination({
                        totalData: result.totalElements,
                        showData: 3,
                        coping: true,
                        homePage: '首页',
                        endPage: '末页',
                        prevContent: '上页',
                        nextContent: '下页'
                        /* callback:pageCallback*/
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

function setlever() {
    $(".lever").show()
}

$(".add-p").click(function () {
    $(".lever").hide()

    if ($("input[name='ckb']:checked").length >= 2) {
        alert("只能选择一条")
    } else {
        var uid = $("input[name='ckb']:checked").parent().parent().children("td").eq(3).text();
        alert(uid)
        $.ajax({
            url: host + "/dcms/api/v1/user/updateLevel",
            type: "post",
            async: false,
            data: JSON.stringify({
                "token": token,
                "uid": uid,
                "level": $("input[name='level']:checked").val()

            }),
            success: function (result) {
                result.onSuccess = function () {
                    getData(0);
                }
                processResponse(result, 1);
            },
            error: function (result) {
                showGlobleTip("服务器异常，请联系管理员");
            }
        });
    }


});