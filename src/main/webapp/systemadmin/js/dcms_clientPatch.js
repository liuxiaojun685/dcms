// index begin
$(function () {
    //初始化页面
    InitTable(0);
});
//转换文件大小
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
//翻页调用
function PageCallback(index, jq) {
    InitTable(index.getCurrent() - 1);
}
function InitTable(index) {
    var pageSize = 10;
    $(".potions1").hide();
    $(".potions").show();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/client/queryClientPatchList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list tr:gt(0)").remove();
                for (var i = 0; i < result.patchList.length; i++) {
                    //版本类型
                    var versionType = isUndefined(result.patchList[i].versionType);
                    if (versionType != "") {
                        if (versionType == 1) {
                            versionType = '网络版';
                        }
                        if (versionType == 2) {
                            versionType = '单机版';
                        }
                    }
                    var str = '<tr>' +
                        '<td><input type="checkbox" name="ckb"></td>' +
                        '<td>' + parseInt(index * pageSize + i + 1) + '</td>' +
                        '<td style="display:none">' + result.patchList[i].patchId + '</td>' +
                        '<td style="width:100px;">' + isUndefined(result.patchList[i].name) + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].location) + '</td>' +
                        '<td>' + bytesToSize(isUndefined(result.patchList[i].size)) + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].osType) + '</td>' +
                        '<td>' + versionType + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].versionCode) + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].versionName) + '</td>' +
                        '<td>' + getLocalTime(result.patchList[i].createTime) + '</td>' +
                        '<td><a onclick="allInfo(' + result.patchList[i].patchId + ')" href="#"><i class="fa fa-list"></i></a></td>' +
                        '</tr>';

                    //添加详情页
                    var info = '<table id="' + result.patchList[i].patchId + '" style="display:none" class="table' +
                        ' table-hover table-bordered table-condensed">' +
                        '<tr>' + '<td>' + '描述' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].description) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '补丁ID' + ':' + '</td>' +
                        '<td>' + result.patchList[i].patchId + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '补丁名' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].name) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本号' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].versionCode) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本名' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].versionName) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '补丁大小' + ':' + '</td>' +
                        '<td>' + bytesToSize(isUndefined(result.patchList[i].size)) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '操作系统' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].osType) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本类别' + ':' + '</td>' +
                        '<td>' + versionType + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '创建时间' + ':' + '</td>' +
                        '<td>' + getLocalTime(result.patchList[i].createTime) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '文件MD5' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].md5) + '</td>' + '</tr>' +
                        '<tr>' +
                        '<tr>' + '<td>' + '补丁存储路径' + ':' + '</td>' +
                        '<td>' + isUndefined(result.patchList[i].location) + '</td>' + '</tr>' +
                        '<td style="text-align: center;background: #efefef;" colspan="2">' +
                        '<div class="user-button">' +
                        '<button class="client-list btn btn-primary" onclick="cancel()">' + '返回' + '</button>' +
                        '</div>' +
                        '</td>' +
                        '</tr>' +
                        '</table>';
                    $(".potions1").append(info);
                    $(".list").append(str);
                    $(".list tbody td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }
                $(".delPatch").unbind("click").click(function () {
                    delTr('ckb', index);
                });
                if (index == 0) {
                    $('.M-box3').pagination({
                        coping: true,
                        jump:true,
                        pageCount: result.totalPages,
                        callback: PageCallback
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
$(".potion").click(function () {
    $(".potions").show();
})
//删除补丁
function delTr(ckb, index) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0 || ckbs.size() > 1) {
        layer.alert("请至选择一个删除！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index1, layero) {
                layer.close(index1);
            }
        });
        return;
    }
    layer.alert("确定删除已经选中的补丁？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            //定义一个存放cid的数组
            var patchIds = $(ckbs).parent().parent().find("td").eq(2).text();
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/client/deleteClientPatch",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "patchId": patchIds
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        InitTable(index);
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });
}
$("#box_tab1 .add-patch").click(function () {
    $("input[name='versionCode']").val("");
    $("input[name='versionName']").val("");
    $("input[name='name']").val("");
    $("input[name='description']").val("");
    $("#fileupload").val("");
    layer.open({
        type: 1,
        title: '上传补丁',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '430px'],
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            $(".versionCode").text("");
            $(".versionName").text("");
            $(".name").text("")
            var keys = 0;
            var versionCode = $("input[name='versionCode']").val();
            if (versionCode == null || versionCode.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".versionCode").text("版本号不能为空！")
                keys = 1;
            }
            var versionName = $("input[name='versionName']").val();
            if (versionName == null || versionName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".versionName").text("版本名不能为空！")
                keys = 1;
            }
            var name = $("input[name='name']").val();
            if (name == null || name.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".name").text("补丁名不能为空！")
                keys = 1;
            }
            if (keys == 0) {
                addPatch(index1);
                layer.close(index1);
            }
        },
        content: $('#up-clientpatch')
    });
});

function fileUploadChange() {
    var str = $("#fileupload").val();
    var arr = str.split("\\");
    $("input[name='name']").val(arr[arr.length - 1]);
}
//添加补丁
function addPatch(index) {
    var form = $(".clientPatch");
    var osType = $("input[name='osType']:checked").val();
    var versionType = $("input[name='versionType']:checked").val();
    var versionCode = $("input[name='versionCode']").val();
    var versionName = $("input[name='versionName']").val();
    var name = $("input[name='name']").val();
    var description = $("input[name='description']").val();
    var data = JSON.stringify({
        "token": token,
        "osType": osType,
        "versionType": versionType,
        "versionCode": versionCode,
        "versionName": versionName,
        "name": name,
        "description": description
    });
    $("input[name='body']").val(data);
    var options = {
        url: host + '/dcms/api/v1/client/addClientPatch',
        type: 'post',
        crossDomain: false,
        success: function (result) {
			result = JSON.parse(result);
            result.onSuccess = function () {
                InitTable(0);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    };
    form.ajaxSubmit(options);

}

//查看详情
//终端详情
function allInfo(patchid) {
    //获取该行的id
    $("#" + patchid).show();
    $(".potions1").show();

    $(".potions").hide();
}
//返回
function cancel() {
    $(".potions1 table").hide();
    $(".potions1").hide();
    $(".potions").show();
}
//重置
function reset() {
    $(".keyword").val("");
    $(".bengintime").val("");
    $(".endtime").val("");

}

//卸载终端密码
//加载卸载密码列表
//翻页调用
function pwdPageCallback(index, jq) {
    pwdInitTable(index.getCurrent() - 1);

}
//卸载密码列表
function InitPwdTable() {
    $(".potions1").hide();
    $(".pwpotions").show();
    pwdInitTable(0)
}
function pwdInitTable(index) {
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/client/queryClientUninstallPasswdList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize
        }),
        success: function (result) {
            $(".plist").empty();
            result.onSuccess = function () {

                for (var i = 0; i < result.uninstallPasswdList.length; i++) {
                    var str = '<tr>' +
                        '<td>' + parseInt(index * pageSize + i + 1) + '</td>' +
                        '<td style="text-align: center;display:none">' + result.uninstallPasswdList[i].passwdId + '</td>' +
                        '<td>' + isUndefined(result.uninstallPasswdList[i].passwd) + '</td>' +
                        '<td>' + isUndefined(result.uninstallPasswdList[i].syncNum) + '</td>' +
                        '<td>' + isUndefined(result.uninstallPasswdList[i].unsyncNum) + '</td>' +
                        '<td>' + isUndefined(result.uninstallPasswdList[i].description) + '</td>' +
                        '<td>' + getLocalTime(result.uninstallPasswdList[i].createTime) + '</td>' +
                        '</tr>';

                    $(".plist").append(str);
                    $(".plist td").each(function () {
                        $(this).attr("title", $(this).text());

                    });
                }
                if (index == 0) {
                    $('.M-box3').pagination({
                        coping: true,
                        jump:true,
                        pageCount: result.totalPages,
                        callback: pwdPageCallback
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
$("#box_tab2 .add-patch").click(function () {
    $("#passwd").val("");
    $("#pwdescription").val("");
    layer.open({
        type: 1,
        title: '修改卸载密码',
        shadeClose: true,
        shade: 0.3,
        area: ['500px', '220px'],
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            //keys判断数据是否有合法值
            $(".passwd").text("")
            $(".pwdescription").text("")
            var keys = 0;
            var passwd = $("#passwd").val();
            if (passwd == null || passwd.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".passwd").text("新卸载密码不能为空！")
                keys = 1;
            }
            var pwdescription = $("#pwdescription").val();
            if (pwdescription == null || pwdescription.replace(/(^s*)|(s*$)/g, "").length == 0) {
                $(".pwdescription").text("描述不能为空！")
                keys = 1;
            }
            if (keys == 0) {
                addPwd();
                layer.close(index1);
            }
        },
        content: $('#add-patch')
    });
});
//添加修改密码
function addPwd() {
    //keys判断数据是否有合法值
    var passwd = $("#passwd").val();
    var pwdescription = $("#pwdescription").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/client/changeClientUninstallPasswd",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "passwd": passwd,
            "description": pwdescription
        }),
        success: function (result) {
            result.onSuccess = function () {
                InitPwdTable();
            }
            processResponse(result, 1);

        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}