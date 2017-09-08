/**
 * Created by besthyhy on 17-2-21.
 */
//主定密单位初始化
$(function () {
    InitTable();
    getData();
});
function InitTable() {
    $.ajax({
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: host + "/dcms/api/v1/unit/queryMajorList",
        data: JSON.stringify({
                "token": token
            }
        ),
        success: function (result) {
            result.onSuccess = function () {
                $("#editView_main_no").val(result.unitNo);
                $("#editView_main_name").val(result.name);
                $("#editView_main_desc").val(result.description);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
$("#button_main_save").click(function () {
    $.ajax({
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: host + "/dcms/api/v1/unit/updateMajor",
        data: JSON.stringify({
            "token": token,
            "unitNo": $("#editView_main_no").val(),
            "name": $("#editView_main_name").val(),
            "description": $("#editView_main_desc").val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                InitTable();
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });

})

//获取辅助定密单位列表
function getData() {
    $.ajax({
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: host + "/dcms/api/v1/unit/queryMinorList",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#tab").empty();
                for (var i = 0; i < result.unitList.length; i++) {
                    var str = '<tr>' +
                        '<td style="display:none;">' + parseInt(i + 1) + '</td>' +
                        '<td>' + result.unitList[i].unitNo + '</td>' +
                        '<td>' + result.unitList[i].name + '</td>' +
                        '<td>' + result.unitList[i].description + '</td>' +
                        '<td><a href="javaScript:;;"><i class="fa fa-trash-o" onclick="delTr(this)"/></i></a></td>' +
                        '</tr>';
                    $("#tab").append(str);
                }
                //添加辅助定密单位
                $("#add-fixed").unbind("click").click(function () {
                    $("#et_add_helper_no").val("");
                    $("#et_add_helper_name").val("");
                    $("#et_add_helper_desc").val("");
                    layer.open({
                        type: 1,
                        title: '添加定密依据',
                        shadeClose: true,
                        shade: 0.3,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['500px', '270px'], //宽高
                        btn: ['确定', '取消'],
                        yes: function (index, layero) {
                            $(".et_add_helper_no").text("");
                            $(".et_add_helper_name").text("");
                            $(".et_add_helper_desc").text("");
                            var keys = 0;
                            var unitNo = $("#et_add_helper_no").val();
                            if (unitNo == null || unitNo.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".et_add_helper_no").text("定密单位标识不能为空！");
                                keys = 1;
                            }
                            var name = $("#et_add_helper_name").val();
                            if (name == null || name.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".et_add_helper_name").text("定密单位名称不能为空！");
                                keys = 1;
                            }
                            var description = $("#et_add_helper_desc").val();
                            if (description == null || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".et_add_helper_desc").text("定密单位描述不能为空！");
                                keys = 1;
                            }
                        if(keys == 0) {
                                addTr2();
                                layer.close(index);
                            }
                        },
                        content: $('#from_add_helper_unit')
                    });
                })
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//删除辅助定密单位
function delTr(ckb) {
    layer.alert("确定删除选中的辅助定密单位？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var unitNo = $(ckb).parent().parent().parent().find("td").eq(1).text();
            $.ajax({
                type: "post",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                url: host + "/dcms/api/v1/unit/deleteMinor",
                data: JSON.stringify({
                    "token": token,
                    "unitNo": unitNo
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(ckb).parent().parent().parent().remove();
                        getData();
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

function addTr2() {
    var unitNo = $("#et_add_helper_no").val();
    var name = $("#et_add_helper_name").val();
    var description = $("#et_add_helper_desc").val();
    $.ajax({
        type: "post",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: host + "/dcms/api/v1/unit/addMinor",
        data: JSON.stringify({
            "token": token,
            "unitNo": unitNo,
            "name": name,
            "description": description
        }),
        success: function (result) {
            result.onSuccess = function () {
                getData();
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
};