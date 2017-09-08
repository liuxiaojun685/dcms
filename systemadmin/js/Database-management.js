/**
 * Created by liuxiaojun on 17/2/14.
 */
$(function () {
    getData(0);
});
function select(s, index) {
    getData(index, $(s).val());
}
function getData(ways) {
    $.ajax({
        url: host + "/dcms/api/v1/backup/queryConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var list = result.backupLocation;
                $("#box_tab1 .plist").empty();
                var str = '<tr>' +
                    '<td>' +
                    '<select id="sel" onchange="select(this)"  >';
                if (ways == 'ip' || ways == undefined) {
                    str += '<option value="ip">ip</option>' +
                        '<option value="domainName">域名</option>' +
                        '</select>' +
                        '</td>' +
                        '<td> ';
                } else {
                    str += '<option value="ip">ip</option>' +
                        '<option value="domainName" selected>域名</option>' +
                        '</select>' +

                        '</td>' +
                        '<td> ';
                }
                if (ways == 'ip' || ways == undefined) {
                    str += '<input name="ip" type="text" value="' + list.ip + '"/>' +
                        '</td>' +
                        '</tr>';

                } else {
                    str += '<input name="domainName" type="text" value="' + list.domainName + '"/>' +
                        '</td>' +
                        '</tr>';

                }

                str += '<tr>' +
                    '<td>端口</td>' +
                    '<td><input name="port" type="text" value="' + list.port + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<tr>' +
                    '<td>协议</td>' +
                    '<td>' +
                    '<select id="protocol">';
                if (result.protocol == '本地' || result.protocol == undefined) {
                    str += '<option>本地</option>' +
                        '<option>http</option>' +
                        '<option>ftp</option>';
                }

                str += '</select>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>路径</td>' +
                    '<td><input name="path" type="text" value="' + list.path + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;">' +
                    '<button class="btn btn-primary" onclick="editpath()">保存</button>' +
                    '</td>' +
                    '</tr>';
                $("#box_tab1 .plist").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function editpath(index) {
    var valu = $('#sel option:selected').val();
    if (valu == "ip") {
        var data = {
            "token": token,
            "backupLocation": {
                "ip": $("input[name='ip']").val(),
                "port": $("input[name='port']").val(),
                "protocol": $("#protocol option:selected").val(),
                "path": $("input[name='path']").val(),
                "host": $("input[name='host']").val(),
                "passwd": $("input[name='passwd']").val()
            }
        }
    } else {
        var data = {
            "token": token,
            "backupLocation": {
                "domainName": $("input[name='domainName']").val(),
                "path": $("input[name='path']").val(),
                "port": $("input[name='port']").val(),
                "protocol": $("input[name='protocol']").val(),
                "host": $("input[name='host']").val(),
                "passwd": $("input[name='passwd']").val()
            }
        }
    }
    layer.open({
        type: 1,
        title: "提示",
        skin: 'layui-layer-rim', //加上边框
        area: ['200px', '150px'], //宽高
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/backup/updateConfig",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify(data),
                success: function (result) {
                    result.onSuccess = function () {
                        $(".cont1").show();
                        $(".edit-path").click(function () {
                            $(".cont1").hide();
                            getData(index, valu);
                        })
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index);
        },
        content: '<h5 class="text-center" style="margin-top: 20px;">是否保存？</h5>'
    });
}

//function reshTr(ckb) {
//获取选中的复选框，然后循环遍历删除
//var ckbs = $("input[name=" + ckb + "]:checked");
//ckbs.each(function(){
//$(this).parent().find("input").eq(0).removeAttr("disabled");
//})
//}
//function reshTr2(){
//reshTr('ckb');
//
//$(".save").click(function(){
//var  domainName=$(".domainName input").val();

//});
