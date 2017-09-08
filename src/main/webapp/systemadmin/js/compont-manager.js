/**
 * Created by liuxiaojun on 17/1/23.
 */
$(function () {
    getData();
});
function getData() {
    $.ajax({
        url: host + "/dcms/api/v1/component/query",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                var select;
                for (var i = 0; i < data.componentList.length; i++) {
                    if (data.componentList[i].enable == 0) {
                        select = '<td><select name="" id="" ><option value="1">启用</option><option value="0" selected="selected">禁用</option></select></td>';
                    }
                    if (data.componentList[i].enable == 1) {
                        select = '<td><select name="" id="" ><option value="1" selected="selected">启用</option><option value="0" >禁用</option></select></td>';
                    }
                    var num = parseInt(i + 1);
                    var str = '<tr>' +
                        '<td>' + num + '</td>' +
                        '<td style="display: none">' + data.componentList[i].componentId + '</td>' +
                        '<td>' + data.componentList[i].name + '</td>' +
                        '<td>' + data.componentList[i].description + '</td>' +
                        select +
                        '<td><a href="javaScript:;;"><i onclick="save(this)" class="fa fa-save"></i></a></td>' +
                        '</tr>';
                    $(".list").append(str);
                }
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function save(s) {
    layer.alert("是否确定保存当前组件状态？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var componentId = $(s).parent().parent().parent().find("td").eq(1).text();
            var sec = $(s).parent().parent().parent().find("select").select().val();
            $.ajax({
                url: host + "/dcms/api/v1/component/updateById",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "componentId": componentId,
                    "enable": sec
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        //$(".list").empty();
                        //getData();
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });

}


