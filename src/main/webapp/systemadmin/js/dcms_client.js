$(function () {
    var start = {
        elem: '#start',
        format: 'YYYY/MM/DD',
        //istime: true,
        istoday: true,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#end',
        format: 'YYYY/MM/DD',
        // istime: true,
        istoday: true
    };
    laydate(start);
    laydate(end);

    //初始化页面
    InitTable(0);

})
//翻页调用
function PageCallback(index) {
    InitTable(index.getCurrent() - 1);
}
function InitTable(index) {
    //查询参数
    var level = $("#level  option:selected").val();
    var online = $("#online  option:selected").val();
    var versionType = $("#versionType  option:selected").val();
    var keyword = $(".keyword").val();
    var begintime = $("#start").val();
    var lastLoginTimeStart = new Date(begintime).getTime();
    var endtime = $("#end").val();
    var lastLoginTimeEnd = new Date(endtime).getTime();
    var pageSize = 10;
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/client/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize,
            "keyword": keyword,
            "level": level,
            "versionType": versionType,
            "online": online,
            "lastLoginTimeStart": lastLoginTimeStart,
            "lastLoginTimeEnd": lastLoginTimeEnd
        }),
        success: function (result) {
            result.onSuccess = function () {
                $(".list tr:gt(0)").remove();
                for (var i = 0; i < result.clientList.length; i++) {
                    //判断终端密级
                    var level = isUndefined(result.clientList[i].level);

                    if (level == 0) {
                        level = '公开';

                    }
                    if (level == 1) {
                        level = '内部';

                    }
                    if (level == 2) {
                        level = '秘密';
                    }
                    if (level == 3) {
                        level = '机密';
                    }
                    if (level == 4) {
                        level = '绝密';
                    }

                    //版本类型
                    var versionType = isUndefined(result.clientList[i].versionType);
                    if (versionType == 1) {
                        versionType = '网络版';
                    }
                    if (versionType == 2) {
                        versionType = '单机版';
                    }
                    //在线状态
                    var online = isUndefined(result.clientList[i].online);
                    if (online == 1) {
                        online = '在线';
                    }
                    if (online == 2) {
                        online = '离线';
                    }
                    var str = '<tr>' +
                        '<td><input type="checkbox" name="ckb"></td>' +
                        '<td>' + parseInt(index * pageSize + i + 1) + '</td>' +
                        '<td style="text-align: center;display:none">' + result.clientList[i].cid + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].pcName) + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].ip) + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].mac) + '</td>' +
                        '<td>' + level + '</td>' +
                        '<td>' + versionType + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].versionCode) + '</td>' +
                        '<td>' + online + '</td>' +
                        '<td class="description">' + isUndefined(result.clientList[i].description) + '</td>' +
                        '<td><a href="javaScript:;;" onclick="editsave(this)"><i class="fa fa-edit"></i></a><span style="padding:5px 10px;"></span><a' +
                        ' href="javaScript:;;"' +
                        ' onclick="allInfo(this)"><i class="fa fa-list"></i></a></td>' +
                        '</tr>';

                    //添加详情页
                    var info = '<table id="' + result.clientList[i].cid + '" style="display:none" class="table' +
                        ' table-bordered table-hover' +
                        ' table-condensed">' +
                        '<tr>' + '<td>' + '描述' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].description) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '终端ID' + ':' + '</td>' +
                        '<td>' + result.clientList[i].cid + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本名' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].versionName) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本号' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].versionCode) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + 'ip地址' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].ip) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + 'Mac地址' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].mac) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '终端密级' + ':' + '</td>' +
                        '<td>' + level + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '在线状态' + ':' + '</td>' +
                        '<td>' + online + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '操作系统' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].osType) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '版本类型' + ':' + '</td>' +
                        '<td>' + versionType + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '终端机器名' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].pcName) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '首次登录用户' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].firstLoginUser.name) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '末次登录用户' + ':' + '</td>' +
                        '<td>' + isUndefined(result.clientList[i].lastLoginUser.name) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '首次登录时间' + ':' + '</td>' +
                        '<td>' + isUndefined(getLocalTime(result.clientList[i].firstLoginTime)) + '</td>' + '</tr>' +
                        '<tr>' + '<td>' + '末次登录时间' + ':' + '</td>' +
                        '<td>' + isUndefined(getLocalTime(result.clientList[i].lastLoginTime)) + '</td>' + '</tr>' +
                        '<tr>' +
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
                $(".del").unbind("click").click(function () {
                    delTr('ckb', index);
                });

                $(".add-client").unbind("click").click(function () {
                    $("#versionCode").val("");
                    $("#ip").val("");
                    $("#mac").val("");
                    $("#versionName").val("");
                    layer.open({
                        type: 1,
                        title: '添加终端',
                        shadeClose: true,
                        shade: 0.3,
                        area: ['500px', '390px'],
                        btn: ['确定', '取消'],
                        yes: function (index1, layero) {
                            $(".versionCode").text("");
                            $(".ip").text("");
                            $(".mac").text("");
                            $(".versionName").text("");
                            var keys = 0;
                            var versionCode = $("#versionCode").val();
                            if (versionCode == null || versionCode.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".versionCode").text("版本号不能为空！");
                                keys = 1;
                            }
                            var ip = $("#ip").val();
                            if (ip == null || ip.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".ip").text("ip不能为空！");
                                keys = 1;
                            }
                            var mac = $("#mac").val();
                            if (mac == null || mac.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".mac").text("mac地址不能为空！");
                                keys = 1;
                            }
                            var versionName = $("#versionName").val();
                            if (versionName == null || versionName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".versionName").text("版本名不能为空！");
                                keys = 1;
                            }
                            if (keys == 0) {
                                addTr2();
                                layer.close(index1);
                            }
                        },
                        content: $('#add-client')
                    });
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
    if (shijianchuo == undefined) {
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
// table 的增删该查
//修改终端
function editsave(t) {
    //alert($(t).parent().find("td").eq(0).text())
    var str = $(t).children("i");
    //得到需要修改的那一个表格
    var tdenbale = $(t).parent().parent().find("td").eq(10);
    if (str.hasClass("fa-edit")) {
        $(t).children("i").removeClass("fa-edit");
        $(t).children("i").addClass("fa-save");
        // $(t).val(str);   // 按钮被点击后，在“编辑”和“确定”之间切换

        obj_text = tdenbale.find("input:text");    // 判断单元格下是否有文本框
        //设置文本框
        var input = "<input class='editinput' type='text' value='" + tdenbale.text() + "'>";
        if (!obj_text.length) {   // 如果没有文本框，则添加文本框使之可以编辑
            tdenbale.html(input);
            //设置文本框的样式
            $(".editinput").css("border", "0");
            $(".editinput").css("font-size", "14px");
            $(".editinput").css("text-align", "left");
            //设置文本框宽度等于td的宽度
            $(".editinput").width(tdenbale.width());
            $(".editinput").focus();
            //当文本框失去焦点时重新变为文本
            $(".editinput").blur(function () {
                var input_blur = $(this);
                //保存当前文本框的内容
                var newText = input_blur.val();
                tdenbale.html(newText);
            });
        }
        else   // 如果已经存在文本框，则将其显示为文本框修改的值

            tdenbale.html(obj_text.val());
    } else {
        $(t).children("i").removeClass("fa-save");
        $(t).children("i").addClass("fa-edit");
        //获取到修改后的值
        var description = tdenbale.text();
        //需要修改的id
        var cid = $(t).parent().parent().find("td").eq(2).text();
        $.ajax({
            type: "post",
            url: host + "/dcms/api/v1/client/updateById",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            crossDomain: false,
            data: JSON.stringify({
                "token": token,
                "cid": cid,
                "description": description
            }),
            success: function (result) {
                result.onSuccess = function () {
                    InitTable(0);
                }
                processResponse(result, 1);
            },
            error: function (result) {
                showGlobleTip("服务器异常，请联系管理员");
            }
        });
    }


}
//删除终端
function delTr(ckb, index) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        layer.alert("请至少选择一个删除！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index1, layero) {
                layer.close(index1);
            }
        });
        return;
    }
    layer.alert("确定删除已经选中的终端？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            //定义一个存放cid的数组
            var cids = new Array(ckbs.size());
            for (var i = 0; i < ckbs.length; i++) {
                //alert(ckbs[i].parent().parent().find("td").eq(2).text());
                cids[i] = $(ckbs[i]).parent().parent().find("td").eq(2).text();
            }
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/client/deleteInBatches",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "cidList": cids
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

/**
 * 全选
 *
 * allCkb 全选复选框的id
 * items 复选框的name
 */
function allCheck(allCkb, items) {
    $("#" + allCkb).click(function () {
        if (this.checked) {
            $('[name=' + items + ']:checkbox').prop("checked", true);
        } else {
            $('[name=' + items + ']:checkbox').prop("checked", false);
        }
    });

}

////////添加一行、删除一行测试方法///////
$(function () {
    //全选
    allCheck("allCkb", "ckb");
});

//添加终端
function addTr2() {
    //keys判断数据是否有合法值
    var osType = $("input[name='osType']:checked").val();
    var versionCode = $("#versionCode").val();
    var ip = $("#ip").val();
    var mac = $("#mac").val();
    var versionName = $("#versionName").val();
    var versionType = $("input[name='versionType']:checked").val()
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/client/add",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "ip": ip,
            "mac": mac,
            "osType": osType,
            "versionType": versionType,
            "versionName": versionName,
            "versionCode": versionCode
        }),
        success: function (result) {
            result.onSuccess = function () {
                InitTable(0);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

}


//查看详情
//终端详情
function allInfo(t) {
    //获取该行的id
    var cid = $(t).parent().parent().find("td").eq(2).text();
    $("#" + cid).show();
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
    var level = $("#level");
    for (var i = 0; i < level.length; i++) {
        level[i].options[0].selected = true;
    }
    var level = $("#level");
    for (var i = 0; i < level.length; i++) {
        level[i].options[0].selected = true;
    }
    var online = $("#online");
    for (var i = 0; i < online.length; i++) {
        online[i].options[0].selected = true;
    }
    var versionType = $("#versionType");
    for (var i = 0; i < versionType.length; i++) {
        versionType[i].options[0].selected = true;
    }

}
//传参查询
function queryby() {
    InitTable(0)
}