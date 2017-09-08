/**
 * Created by liuxiaojun on 17/2/13.
 */

$(function () {
    InitPwdTable();

});
function select(t) {
    if ($(t).val() == "local" || $(t).val() == undefined) {
        $(t).parent().parent().find("td").eq(2).find("select").attr("disabled", true);
        $(t).parent().parent().find("td").eq(2).find("input").attr("disabled", true);
        $(t).parent().parent().find("td").eq(2).find("input").val("");
        $(t).parent().parent().find("td").eq(3).find("input").attr("disabled", true);
        $(t).parent().parent().find("td").eq(3).find("input").val("");
        $(t).parent().parent().find("td").eq(4).find("input").attr("disabled", true);
        $(t).parent().parent().find("td").eq(4).find("input").val("");
        $(t).parent().parent().find("td").eq(5).find("input").attr("disabled", true);
        $(t).parent().parent().find("td").eq(5).find("input").val("");
    } else {
        $(t).parent().parent().find("td").eq(2).find("select").attr("disabled", false);
        $(t).parent().parent().find("td").eq(2).find("input").attr("disabled", false);
        $(t).parent().parent().find("td").eq(3).find("input").attr("disabled", false);
        $(t).parent().parent().find("td").eq(4).find("input").attr("disabled", false);
        $(t).parent().parent().find("td").eq(5).find("input").attr("disabled", false);
    }
};
function sel(s, way) {
    way = $(s).val();
    alert(way)
    //InitPwdTable(way,$(s).val());
}
function InitPwdTable() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/queryLocationList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#tab1").empty();

                var fileLocations = new Array();
                fileLocations.push(result.fileLocation0, result.fileLocation1, result.fileLocation2, result.fileLocation3, result.fileLocation4, result.logLocation, result.patchLocation, result.backupLocation);
                for (var i = 0; i < fileLocations.length; i++) {
                    var str1 = "";
                    //判断是ip还域名
                    var way = fileLocations[i].domainName;
                    if (fileLocations[i] == result.fileLocation4) {
                        str1 = "绝密文件:";
                        getTab(way, str1, fileLocations[i], "file4");

                    }
                    if (fileLocations[i] == result.fileLocation3) {
                        str1 = "机密文件:";
                        getTab(way, str1, fileLocations[i], "file3");
                    }
                    if (fileLocations[i] == result.fileLocation2) {
                        str1 = "秘密文件:";
                        getTab(way, str1, fileLocations[i], "file2");
                    }
                    if (fileLocations[i] == result.fileLocation1) {
                        str1 = "内部文件:";
                        getTab(way, str1, fileLocations[i], "file1");
                    }
                    if (fileLocations[i] == result.fileLocation0) {
                        str1 = "公开文件:";
                        getTab(way, str1, fileLocations[i], "file0");
                    }
                    if (fileLocations[i] == result.logLocation) {
                        str1 = "日志文件:";
                        getTab(way, str1, fileLocations[i], "logl");
                    }
                    if (fileLocations[i] == result.patchLocation) {
                        str1 = "补丁文件:";
                        getTab(way, str1, fileLocations[i], "patch");
                    }
                    if (fileLocations[i] == result.backupLocation) {
                        str1 = "数据库文件:";
                        getTab(way, str1, fileLocations[i], "db");
                    }
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//文件存储列表
function getTab(way, str1, fileLocations, local) {
    var str = '<tr>' +
        '</tr>' +
        '<tr>' +
        '<td style="width:10%;background: #e7e7e7;">' + str1 + '</td>' +
        '<td><span style="margin-right: 2px;">协议:</span>' +
        '<select id="protocol' + local + '"  onchange="select(this)" >';
    if (fileLocations.protocol == 'local') {
        str += '<option value="local" selected>本地</option>' +
            '<option value="SFTP">SFTP</option>' +
            '<option value="SMB">SMB</option>';
    } else if (fileLocations.protocol == 'SFTP') {
        str += '<option value="local">本地</option>' +
            '<option value="SFTP" selected>SFTP</option>' +
            '<option value="SMB">SMB</option>';
    } else if (fileLocations.protocol == 'SMB') {
        str += '<option value="local">本地</option>' +
            '<option value="SFTP">SFTP</option>' +
            '<option value="SMB" selected>SMB</option>';
    }

    str += '</select>' +
        '</td>' +
        '<td class="">' +
        '<select  onchange="sel(this,' + way + ')" disabled id="sel' + local + '">';
    if (way == "" || way == undefined) {
        str += '<option value="ip">ip</option>' +
            '<option value="domainName">域名</option>';
    } else {
        str += '<option value="domainName">域名</option>' +
            '<option value="ip">ip</option>';
    }
    str += '</select><span style="padding:0 2px;"></span>';
    if (way == '' || way == undefined) {
        str += '<input name="ways' + local + '" type="text" value="' + fileLocations.ip + '" style="width:100px;" disabled/>';
    } else {
        str += '<input name="ways' + local + '" type="text" value="' + fileLocations.domainName + '" style="width:100px;" disabled/>';
    }
    str += '</td>' +
        '<td><span>端口:</span> <input name="port' + local + '" type="text" value="' + fileLocations.port + '" style="width:50px;" disabled/></td>' +
        '<td><span>账号:</span> ';
    if (fileLocations.account != undefined) {
        str += '<span><input name="account' + local + '" type="text" value="' + fileLocations.account + '" style="width:100px;" disabled/></span>';
    } else {
        str += '<span><input name="account' + local + '" type="text" value="" style="width:100px;" disabled/></span>';
    }
    str += '</td>' +
        '<td><span>密码:</span> ';
    if (fileLocations.passwd != undefined) {
        str += '<span><input name="passwd' + local + '" type="password" value="' + fileLocations.passwd + '" style="width:100px;" disabled/></span>';
    } else {
        str += '<span><input name="passwd' + local + '" type="password" value="" style="width:100px;" disabled/></span>';
    }
    str += '</td>' +
        '<td><span>路径:</span><input name="path' + local + '" type="text" value="' + fileLocations.path + '" style="width:180px;"/></td>' +
            /*'<td style="text-align: center;">' +
             '<a href="#" onclick="editpath()"><i class="fa fa-save"></i></i>' +
             '</td>' +*/
        '</tr>';

    $("#tab1").append(str);
    select($("#protocol" + local));
}


function getPath(local){
    var text = $("input[name='path" + local + "']").parent().parent().find("td").eq(0).text();
    if ($("input[name='path" + local + "']").val() == '') {
        layer.alert(text + "的路径不能为空！", {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['确定'],
            yes: function (index, layero) {
                //getEnable();
                layer.close(index);
            }
        });

        return false;
    }
    return true;
}
function editpath(num) {

    if(!getPath("file0")){
        return;
    }
    if(!getPath("file1")){
        return;
    }
    if(!getPath("file2")){
        return;
    }
    if(!getPath("file3")){
        return;
    }
    if(!getPath("file4")){
        return;
    }
    if(!getPath("logl")){
        return;
    }
    if(!getPath("patch")){
        return;
    }
    if(!getPath("db")){
        return;
    }

    layer.alert("文件存储设置成功，是否确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {

            //获取数据
            var data = {
                "token": token,
                "fileLocation0": getData("file0"),
                "fileLocation1": getData("file1"),
                "fileLocation2": getData("file2"),
                "fileLocation3": getData("file3"),
                "fileLocation4": getData("file4"),
                "logLocation": getData("logl"),
                "patchLocation": getData("patch"),
                "backupLocation": getData("db")
            }

            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/systemConfig/updateLocation",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: false,
                data: JSON.stringify(data),
                success: function (result) {
                    result.onSuccess = function () {
                        InitPwdTable();
                    }
                    processResponse(result, 1);

                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })
            layer.close(index1);
        }
    });
}

function getData(local) {
    //alert($("input[name='path" + local + "']").val())
    //$("input[name='path" + local + "']").each(function(){

        //})
        var protocol = $("#protocol" + local).val();
        var name = $("#sel" + local).val();
        var ip = "";
        var domainName = "";
        if (name == 'ip') {
            ip = $("input[name='ways" + local + "']").val();
        } else {
            domainName = $("input[name='ways" + local + "']").val();
        }
        var data = {
            "ip": ip,
            "domainName": domainName,
            "port": $("input[name='port" + local + "']").val(),
            "protocol": protocol,
            "path": $("input[name='path" + local + "']").val(),
            "account": $("input[name='account" + local + "']").val(),
            "passwd": $("input[name='passwd" + local + "']").val()
        }
        return data;
}
