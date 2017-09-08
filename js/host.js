// var host="http://127.0.0.1:8080";
var host = window.location.protocol + "//" + window.location.host;
//var host = "http://47.93.78.186:8080";
//var host = "http://192.168.0.8:9090";
// var host = "http://192.168.0.4:8080";
//var host = "http://192.168.0.11:8080";
var token = "";
var t = sessionStorage.getItem("token");
if (t != null) {
    token = t;
}
var account = '';
var m = sessionStorage.getItem("account");
if (m != null) {
    account = m;
}
var n = sessionStorage.getItem("name");
if (n != null) {
    name = n;
}
var a = sessionStorage.getItem("adminType");
if (a != null) {
    adminType = a;
    if(adminType == 1){
        adminType = "安全审计员";
    }
    if(adminType == 2){
        adminType = "系统管理员";
    }
    if(adminType == 3){
        adminType = "安全保密管理员";
    }
}
var c = sessionStorage.getItem("createFrom");
if (c != null) {
    createFrom = c;
    if(createFrom == 1){
        $("#account").text(account+" "+adminType);
    }
    if(createFrom == 2){
        $("#account").text(name+"/"+account+" 用户角色：" +adminType);
    }
}
function showGlobleTip(tip, isout, code) {
    if (code == 0) {
        layer.alert(tip, {
            icon: 1,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                layer.close(index);
                /*if (isout) {
                 quit()
                 }*/
            }
        });
    } else {
        layer.alert(tip, {
            icon: 2,
            skin: 'layer-ext-moon',
            btn: ['关闭'],
            yes: function (index, layero) {
                layer.close(index);
                /*if (isout) {
                 quit()
                 }*/
            }
        });
    }
}
function processResponse(data, disp, k) {
    if (data.code == 2) {
        showGlobleTip(data.msg);
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        window.location.href = projectName + '/login.html';
    } else if (disp == 1) {
        showGlobleTip(data.msg, k, data.code);
    }
    if (data.code == 0 && data.onSuccess != null) {
        data.onSuccess();
    }
}
function quit() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/admin/logout",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            //result.onSuccess = function () {
            window.location.href = "../login.html";
            //}
            //processResponse(result, 1);
        }
    })
}
$("#quit").click(function () {
    layer.alert("确定是否退出该管理平台？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            quit();
            layer.close(index);
        }
    });

});
function addText() {
    $.ajax({
        url: host + "/dcms/api/v1/user/queryPasswdRule",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            if (result.code == 0){
                if(result.containsLetter == 1){
                    result.containsLetter = "字母大小写、";
                }
                if(result.containsLetter == 0){
                    result.containsLetter = "";
                }
                if(result.containsNumber  == 1){
                    result.containsNumber  = "数字、";
                }
                if(result.containsNumber  == 0){
                    result.containsNumber  = "";
                }
                if(result.containsSpecial  == 1){
                    result.containsSpecial  = "特殊字符";
                }
                $("#infoText").text("密码范围["+result.minLength+"-"+ result.maxLength+"]个字符、包含"+result.containsLetter+ result.containsNumber+result.containsSpecial);
                if(result.containsSpecial  == 0){
                    $("#infoText").text("密码范围["+result.minLength+"-"+ result.maxLength+"]个字符、包含字母大小写、数字");
                }


            }
        }
    })
}
$("#resh").click(function () {
    addText();
    layer.open({
        type: 1,
        title: "修改密码",
        skin: 'layui-layer-rim', //加上边框
        area: ['500px', '270px'], //宽高
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var keys = 0;
            var passwd = $("#newpasswd").val();
            var okpasswd = $("#okpasswd").val();
            //var regex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}');
            //if(!regex.test(passwd)){
            //    layer.alert("密码不符合复杂度要求!", {
            //        icon: 2,
            //        skin: 'layer-ext-moon',
            //        btn: ['关闭'],
            //        yes: function (index, layero) {
            //            layer.close(index);
            //        }
            //    });
            //    keys = 1;
            //}
            if (passwd != okpasswd) {
                layer.alert("两次输入的新密码不一致!", {
                    icon: 2,
                    skin: 'layer-ext-moon',
                    btn: ['关闭'],
                    yes: function (index, layero) {
                        layer.close(index);
                    }
                });
                keys = 1;
            }
            if (keys == 0) {
                resh(index1);
            }
        },
        content: $('#resh-save')
    });
})
function resh(index) {
    var oldPasswd = $("#oldpasswd").val();
    var passwd = $("#newpasswd").val();
    $.ajax({
        url: host + "/dcms/api/v1/admin/update",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "passwd": passwd,
            "oldPasswd": oldPasswd
        }),
        success: function (result) {
            result.onSuccess = function () {
                layer.close(index);
            }
            processResponse(result, 1);
        }
    })
}