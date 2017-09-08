/**
 * Created by liuxiaojun on 17/2/14.
 */
$(function () {
    middleware(1);
});
function middleware(a) {
    $.ajax({
        url: host + "/dcms/api/v1/middleware/queryList",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var midlelist = result.middlewareList;
                for (var i = 0; i < midlelist.length; i++) {
                    if (midlelist[i].enable == 1) {
                        midlelist[i].enable = "启用";
                    }
                    if (midlelist[i].enable == 0) {
                        midlelist[i].enable = "禁用";
                    }
                    var str = '<tr  style="background: #efefef;" id="display_' + midlelist[i].middlewareId + '">' +
                        '<th style="text-align: left;display:none;">' +
                        midlelist[i].middlewareId +
                        '</th>' +
                        '<th class="name"  style="text-align: left;width:15%;">' +
                        midlelist[i].name +
                        ':</th>' +
                        '<td  style="text-align: left;width:75%;">' +
                        midlelist[i].description +
                        '</td>' +
                        '<td style="text-align: left;width:5%;">' +
                        midlelist[i].enable +
                        '</td>' +
                        '<td  style="text-align: left;width:5%;">' +
                        '<span><a href="javaScript:;;" onclick="displayMiddle(' + midlelist[i].middlewareId + ')">';
                    //if (a == 0) {
                    //    str += '<i class="fa  fa-angle-up"></i>';
                    //} else {
                    str += '<i class="fa  fa-angle-down"></i>';
                    //}
                    str += '</a></span>' +
                        '</td>' +
                        '</tr>' +
                        '<tr>' +
                        '<td colspan="4">' +
                        '<input type="hidden" value="' + midlelist[i].middlewareId + '">' +
                        '<table class="table table-bordered table-hover table-condensed" style="display:none;">' +
                        '<tr>' +
                        '<th  style="text-align: center;">编号</th>' +
                        '<th  style="text-align: center;">中间件应用IP</th>' +
                        '<th  style="text-align: center;">中间件应用是否允许接入</th>' +
                        '<th  style="text-align: center;">中间件应用是否启用授权口令</th>' +
                        '<th style="text-align: center;">中间件应用授权口令</th>' +
                        '<th style="text-align: center;">操作</th>' +
                        '</tr>';
                    for (var j = 0; j < midlelist[i].middlewareUserList.length; j++) {
                        str += '<tr>' +
                            '<td  style="text-align: center;">' +
                            parseInt(j + 1) +
                            '</td>' +
                            '<td  style="text-align: center;display: none;">' +
                            midlelist[i].middlewareUserList[j].middlewareACLId +
                            '</td>' +
                            '<td  style="text-align: center;"><input type="text" class="midip form-control' +
                            ' text-center"' +
                            ' value="' + midlelist[i].middlewareUserList[j].ip + '" style="padding:0 12px;"/></td>';
                        if (midlelist[i].middlewareUserList[j].enable == 1) {
                            str += '<td  style="text-align: center;"><input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_enable" value="1" checked/>是 <input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_enable" value="2"/>否</td>';
                        }
                        if (midlelist[i].middlewareUserList[j].enable == 2) {
                            str += '<td  style="text-align: center;"><input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_enable" value="1"/>是 <input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_enable" value="2" checked/>否</td>';
                        }
                        if (midlelist[i].middlewareUserList[j].passwdEnable == 1) {
                            str += '<td  style="text-align: center;"><input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_passwdEnable" value="1" checked/>是 <input type="radio" value="2" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_passwdEnable"/>否</td>';
                        }
                        if (midlelist[i].middlewareUserList[j].passwdEnable == 2) {
                            str += '<td  style="text-align: center;"><input type="radio" value="1" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_passwdEnable">是 <input type="radio" name="' + midlelist[i].middlewareUserList[j].middlewareACLId + '_passwdEnable" value="2" checked/>否</td>';
                        }
                        str += '<td  style="text-align: center;"><input class="midpasswd form-control text-center" style="padding: 0 12px;" type="text" value="' + midlelist[i].middlewareUserList[j].passwd + '"/></td>' +
                            '<td  style="text-align: center;"><a href="javaScript:;;" onclick="delTr(this,' + midlelist[i].middlewareId + ')"><i class="fa' +
                            ' fa-trash-o"></i></a><span style="padding:5px 10px;"></span><a href="javaScript:;;"' +
                            ' onclick="update(this,' + midlelist[i].middlewareId + ')"><i' +
                            ' class="fa fa-save"></i></a></td>' +
                            '</tr>';
                    }
                    str += '<tr>' +
                        '<td colspan="6">' +
                        '<div class="row">' +
                        '<div class="col-xs-1">' +
                        '<a href="#" style="padding-right:10px;text-decoration: none;" id="' + midlelist[i].middlewareId + '"><input type="hidden" value="' + midlelist[i].middlewareId + '"/>' +
                        '<i class="fa fa-plus"></i>' +
                        '</a>' +
                        '</div>' +
                        '</div>' +
                        '</td>' +
                        '</tr>' +
                        '</table>' +
                        '</td>' +
                        '</tr>';
                    $("#tab").append(str);
                    $("#tab td").each(function () {
                        $(this).attr("title", $(this).text());

                    });

                    /*if (a == 1) {
                     $("#tab>tbody").find("tr").eq(1).find("table").show();
                     $("#tab>tbody").find("tr").eq(0).find("td").eq(4).find("i").removeClass("fa-angle-down");
                     $("#tab>tbody").find("tr").eq(0).find("td").eq(4).find("i").addClass("fa-angle-up");

                     } else {*/
                    // }
                    $("#" + midlelist[i].middlewareId).unbind("click").click(function () {
                        var midid = $(this).find("input").eq(0).val();
                        $(".ip").val("");
                        //$("input[name='versionType']:checked").val("");
                        //$("input[name='versionType1']:checked").val("");
                        $(".passwd").val("");
                        //alert(midid)
                        layer.open({
                            type: 1,
                            title: '添加中间件策略',
                            shadeClose: true,
                            shade: 0.3,
                            area: ['500px', '290px'],
                            btn: ['确定', '取消'],
                            yes: function (index, layero) {
                                $(".osType2").text("");
                                $(".description").text("");
                                var keys = 0;
                                var ip = $(".ip").val();
                                if (ip == null || ip.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                    $(".osType2").text("中间件应用不能为空！");
                                    keys = 1;
                                }
                                var passwd = $(".passwd").val();
                                if (passwd == null || passwd.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                    $(".description").text("授权口令不能为空！");
                                    keys = 1;
                                }
                                if (keys == 0) {
                                    addTr2(midid);
                                    layer.close(index);
                                }
                            },
                            content: $('#integration')
                        });
                    });
                }
                displayMiddle(a);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
};
function displayMiddle(a) {
    if ($("#display_" + a).find("i").hasClass("fa-angle-down")) {
        $("#display_" + a).next().find("td").eq(0).find("table").show();
        $("#display_" + a).find("i").removeClass("fa-angle-down");
        $("#display_" + a).find("i").addClass("fa-angle-up");
    } else {
        $("#display_" + a).find("i").removeClass("fa-angle-up");
        $("#display_" + a).find("i").addClass("fa-angle-down");
        $("#display_" + a).next().find("td").eq(0).find("table").hide();
    }
}
function addTr2(a) {
    var middlewareId = a;
    var ip = $(".ip").val();
    var enable = $("input[name='versionType']:checked").val();
    var passwdEnable = $("input[name='versionType1']:checked").val();
    var passwd = $(".passwd").val();
    $.ajax({
        url: host + "/dcms/api/v1/middleware/addACL",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "ip": ip,
            "middlewareId": middlewareId,
            "enable": enable,
            "passwdEnable": passwdEnable,
            "passwd": passwd
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#tab").empty();
                middleware(a);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function delTr(ckb, a) {
    layer.alert("确定删除选中的中间件应用？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var middlewareACLId = $(ckb).parent().parent().find("td").eq(1).text();
            $.ajax({
                url: host + "/dcms/api/v1/middleware/deleteACLById ",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "middlewareACLId": middlewareACLId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(ckb).parent().parent().remove();
                        $("#tab").empty();
                        middleware(a);
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

}
function update(u, a) {
    layer.alert("确定保存当前中间件应用的修改？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var middlewareACLId = $(u).parent().parent().find("td").eq(1).text();
            var ip = $(u).parent().parent().find("td").eq(2).find("input").eq(0).val();
            var enable = $("input[name='" + middlewareACLId + "_enable']:checked").val();
            var passwdEnable = $("input[name='" + middlewareACLId + "_passwdEnable']:checked").val();
            var passwd = $(u).parent().parent().find("td").eq(5).find("input").eq(0).val();
            $.ajax({
                url: host + "/dcms/api/v1/middleware/updateACLById",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "middlewareACLId": middlewareACLId,
                    "enable": enable,
                    "ip": ip,
                    "passwdEnable": passwdEnable,
                    "passwd": passwd
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        //$("#tab").empty();
                        //middleware(a);
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

}