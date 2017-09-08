/**
 * Created by liuxiaojun on 17/2/16.
 */
/**
 * Created by lenovo on 17-2-10.
 */
if($(window).width() >= 1920){
    $(".potions1").css("height","720px")
}else if($(window).width() >= 1280 && $(window).width() <1920){
    $(".potions1").css("height","480px")
}
function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '/' + getzf(oMonth) + '/' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}
function control(e, o) {
    var v = o.value;
    if (v < 0) {
        o.value = '';
        o.focus();
    }
};
function control1(e, o) {
    var v = o.value;
    if (v < 8 || v < 10) {
        o.value = '';
        o.focus();
    }
};
$(function () {
    InitTable();
});
function InitTable() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryLocalAuthConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                $("#box_tab1 .plist3").empty();
                var str = '<tr>' +
                    '<td colspan="2" style="background:#efefef;padding-left:2px; "><h5 style="font-weight:bold;font-size:15px;">本地认证</h5></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否启用:</td>';
                if (data.localAuthEnable == 1) {
                    str += '<td><input type="radio" name="active" value="1" onclick="user(this)" checked/>是 <input type="radio" name="active" onclick="user(this)" value="0"/>否    <span style="margin-left: 10px;">(启用本地认证之后,AD认证不能启用)</span></td>'
                }
                if (data.localAuthEnable == 0) {
                    str += '<td><input type="radio" name="active" onclick="user(this)" value="1"/>是 <input type="radio" name="active" value="0" onclick="user(this)" checked/>否    <span style="margin-left: 10px;">(启用本地认证之后,AD认证不能启用)</span></td>'
                }
                str += '</tr>' +
                    '<tr>' +
                    '<td>密码长度最小值:</td>' +
                    '<td class="minpaw"><input type="number" min="8" max="10" value="' + data.localAuthPasswdMin + '"/><span>*(密码最小长度不能为空,必须为数字类型8-10之间)</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>密码长度最大值:</td>' +
                    '<td class="maxpaw"><input type="number" min="11" max="30" name="points" value="' + data.localAuthPasswdMax + '"/><span>*(密码最大长度不能为空,必须为数字类型11-30之间)</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>密码复杂程度设置:</td>' +
                    '<td><span style="color:#000;vertical-align: middle;margin: 0 5px;">数字</span>';
                if (data.localAuthPasswdContainsNumber == 1) {
                    str += '<input type="checkbox" value="1" name="number" checked/>';
                }
                if (data.localAuthPasswdContainsNumber == 0) {
                    str += '<input type="checkbox" value="0" name="number"/>';
                }
                str += '<span style="color:#000;vertical-align: middle;margin: 0 5px;">字母</span>';
                if (data.localAuthPasswdContainsLetter == 1) {
                    str += '<input type="checkbox" value="1" name="letter" checked/>';
                }
                if (data.localAuthPasswdContainsLetter == 0) {
                    str += '<input type="checkbox" value="0" name="letter"/>';
                }
                str += '<span style="color:#000;vertical-align: middle;margin: 0 5px;">特殊字符</span>';
                if (data.localAuthPasswdContainsSpecial == 1) {
                    str += '<input type="checkbox" value="1" name="special" checked/>';
                }
                if (data.localAuthPasswdContainsSpecial == 0) {
                    str += '<input type="checkbox" value="0" name="special"/>';
                }
                str += '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>连续密码错误锁定阀值:</td>' +
                    '<td class="lockthreshold"><input type="number" value="' + data.localAuthPasswdLockThreshold + '" onkeyup="control(event,this)"/></td>' +
                    '</tr>' +
                    '<tr style="display:none;">' +
                    '<td>连续密码错误锁定时间设置:</td>' +
                    '<td class="locktime"><input type="text" value="' + getMyDate(data.localAuthPasswdLockTime) + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否强制定期修改密码:</td>';
                if (data.localAuthPasswdForceChange == 1) {
                    str += '<td><input type="radio" value="1" name="password" checked/>是 <input type="radio" name="password" value="0"/>否</td>';
                }
                if (data.localAuthPasswdForceChange == 0) {
                    str += '<td><input type="radio" value="1" name="password"/>是 <input type="radio" name="password" value="0" checked/>否</td>';
                }
                str += '</tr>' +
                    '<tr>' +
                    '<td>定期修改密码时间设置:</td>' +
                    '<td class="pawperiod"><input type="number" value="' + data.localAuthPasswdPeriod / 86400000 + '" onkeyup="control(event,this)"/><span style="margin-left:10px;color:#000;font-size: 12px;">天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否必须管理员解锁:</td>';
                if (data.localAuthUnlockByAdmin == 1) {
                    str += '<td><input type="radio" name="lock" value="1" checked/>是 <input type="radio" name="lock" value="0"/>否</td>';
                }
                if (data.localAuthUnlockByAdmin == 0) {
                    str += '<td><input type="radio" name="lock" value="1"/>是 <input type="radio" name="lock" value="0" checked/>否</td>';
                }
                str += '</tr>' +
                    '<tr>' +
                    '<td>初始化密码设置:</td>' +
                    '<td class="intpaw"><input type="text" value="' + data.localAuthInitPasswd + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否强制修改初始密码:</td>';
                if (data.localAuthInitPasswdForceChange == 1) {
                    str += '<td><input type="radio" name="change" value="1" checked/>是 <input type="radio" name="change"  value="0"/>否</td>';
                }
                if (data.localAuthInitPasswdForceChange == 0) {
                    str += '<td><input type="radio" name="change" value="1"/>是 <input type="radio" name="change"  value="0" checked/>否</td>';
                }
                str += '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;background: #efefef;"><button class="btn btn-primary"' +
                    '  onclick="saveLocal()">保存</button></td>' +
                    '</tr>';
                $("#box_tab1 .plist3").append(str);
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryAdAuthConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (data) {
            data.onSuccess = function () {
                $("#box_tab1 .plist4").empty();
                var str1 = '<tr>' +
                    //'<td colspan="2" style="background:#efefef;padding-left:2px; "><h5 style="font-weight:bold;font-size:15px;">AD认证</h5></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否启用:</td>';
                if (data.adAuthEnable == 1) {
                    str1 += '<td><input type="radio" name="active1" value="1" onclick="user1(this)" checked/>是 <input type="radio" name="active1" onclick="user1(this)" value="0"/>否    <span style="margin-left: 10px;">(启用本地认证之后,AD认证不能启用)</span></td>'
                }
                if (data.adAuthEnable == 0) {
                    str1 += '<td><input type="radio" name="active1" onclick="user1(this)" value="1"/>是 <input type="radio" name="active1" value="0" onclick="user1(this)" checked/>否    <span style="margin-left: 10px;">(启用本地认证之后,AD认证不能启用)</span></td>'
                }
                str1 += '</tr>' +
                    '<tr>' +
                    '<td><input type="radio" name="select" value="1" onclick="select(this)" checked/>IP <input type="radio" name="select" onclick="select(this)" value="2"/>域名</td>' +
                    '<td class="text"><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td class="text1">端口:</td>' +
                    '<td><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>路径:</td>' +
                    '<td><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>账号:</td>' +
                    '<td><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>密码:</td>' +
                    '<td><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>AD连接帐号全DN:</td>' +
                    '<td><input type="text"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>是否启用AD自动同步:</td>' +
                    '<td><input type="radio" name="as" value="yes"/>是 <input type="radio" name="as" value="no"/>否</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>AD同步根DN:</td>' +
                    '<td><input type="checkbox"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;background: #efefef;"><button class="btn btn-primary">保存</button></td>' +
                    '</tr>';
                $("#box_tab1 .plist4").append(str1);
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
if ($("input[name='active']:checked").val() == 1) {
    $("#box_tab1 .plist4 input").attr("disabled", "disabled");
}
if ($("input[name='active']:checked").val() == 0) {
    $("#box_tab1 .plist4 input").removeAttr("disabled");
}
if ($("input[name='active1']:checked").val() == 1) {
    $("#box_tab1 .plist3 input").attr("disabled", "disabled");
}
if ($("input[name='active1']:checked").val() == 0) {
    $("#box_tab1 .plist3 input").removeAttr("disabled");
}
function user(u) {
    var radio = $('input[name="active"]');
    for (var i = 0; i < radio.length; i++) {

        if (radio[i].checked) {

            var userid = radio[i].value;

        }
    }
    if (userid == 1) {
        $("#box_tab1 .plist4 input").attr("disabled", "disabled");
    }
    if (userid == 0) {
        $("#box_tab1 .plist4 input").removeAttr("disabled");
    }
}
function user1(u) {
    var radio = $('input[name="active1"]');
    for (var i = 0; i < radio.length; i++) {

        if (radio[i].checked) {

            var userid = radio[i].value;

        }
    }
    if (userid == 1) {
        $("#box_tab1 .plist3 input").attr("disabled", "disabled");
    }
    if (userid == 0) {
        $("#box_tab1 .plist3 input").removeAttr("disabled");
    }
}
function select(s) {
    var select = $("input[name='select']").val();
    for (var i = 0; i < select.length; i++) {

        if (select[i].checked) {

            var userid = select[i].value;

        }
    }
    if (userid == 1) {
        $(".text1").text("");
        $(".text1").text("端口");
    }
    if (userid == 2) {

        $(".text1").text("协议")
    }
}
function isChecked(t) {
    if ($(t).is(':checked')) {
        return 1;
    } else {
        return 0;
    }
}
function saveLocal() {
    //alert($(".minpaw input").val())
    //alert($(".maxpaw input").val())
    layer.alert("本地认证设置完成，确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var localAuthEnable = $('input[name="active"]:checked').val();
            var localAuthPasswdMin = $(".minpaw input").val();
            var localAuthPasswdMax = $(".maxpaw input").val();
            var localAuthPasswdContainsNumber = isChecked($('input[name="number"]'));
            var localAuthPasswdContainsLetter = isChecked($('input[name="letter"]'));
            var localAuthPasswdContainsSpecial = isChecked($('input[name="special"]'));
            var localAuthPasswdLockThreshold = $(".lockthreshold input").val();
            //var localAuthPasswdLockTime=$(".locktime input").val();
            var localAuthPasswdForceChange = $('input[name="password"]:checked').val();
            var localAuthPasswdPeriod = parseInt($(".pawperiod input").val() * 3600000 * 24);
            var localAuthUnlockByAdmin = $('input[name="lock"]:checked').val();
            var localAuthInitPasswd = $(".intpaw input").val();
            var localAuthInitPasswdForceChange = $('input[name="change"]:checked').val();
            $.ajax({
                url: host + "/dcms/api/v1/systemConfig/updateLocalAuthConfig",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "localAuthEnable": localAuthEnable,
                    "localAuthPasswdMin": localAuthPasswdMin,
                    "localAuthPasswdMax": localAuthPasswdMax,
                    "localAuthPasswdContainsNumber": localAuthPasswdContainsNumber,
                    "localAuthPasswdContainsLetter": localAuthPasswdContainsLetter,
                    "localAuthPasswdContainsSpecial": localAuthPasswdContainsSpecial,
                    "localAuthPasswdLockThreshold": localAuthPasswdLockThreshold,
                    // "localAuthPasswdLockTime":localAuthPasswdLockTime,
                    "localAuthPasswdForceChange": localAuthPasswdForceChange,
                    "localAuthPasswdPeriod": localAuthPasswdPeriod,
                    "localAuthUnlockByAdmin": localAuthUnlockByAdmin,
                    "localAuthInitPasswd": localAuthInitPasswd,
                    "localAuthInitPasswdForceChange": localAuthInitPasswdForceChange
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        InitTable();
                    }
                    processResponse(data, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })
            layer.close(index1);
        }
    });

}
