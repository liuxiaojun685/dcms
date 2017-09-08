/**
 * Created by liuxiaojun on 17/2/15.
 */
$(function () {
    init();
})
//判断邮箱是否合理
function CheckMail(mail) {
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (filter.test(mail)) {
        return mail;
    } else {
        //$(".mail span").text('');
        $(".mail span").html('*您的电子邮件格式不正确');
        return;
    }
}
//输入值大于0
function control(e,o){
    var v=o.value;
    if(v<0||v>100){
        o.value='';
        o.focus();
    }
};
//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}
function init() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryMailConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            result.onSuccess = function () {
                var str = '<tr>' +
                    '<td colspan="2" style="text-align: left;background:#efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">配置告警邮件账号信息</h5></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>邮件告警发件人邮箱:</td>' +
                    '<td class="mail">' +
                    '<input type="text" style="width:300px" value="' + result.mailSenderAccount + '"/><span>*</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>邮件告警发件人密码:</td>' +
                    '<td class="mailpaw">' +
                    '<input type="password" style="width:300px" value="' + result.mailSenderPasswd + '"/><span>*</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>邮件告警SMTP服务器地址:</td>' +
                    '<td class="mailaddress">' +
                    '<input type="text" style="width:300px" value="' + result.mailSenderSmtpAddr + '"/><span>*</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>邮件告警SMTP服务器端口号:</td>' +
                    '<td class="mailport">' +
                    '<input type="text" style="width:300px" value="' + result.mailSenderSmtpPort + '"/><span>*</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>系统资源告警收件地址:</td>' +
                    '<td class="resourceaddress">' +
                    '<input type="text" style="width:300px" value="' + isUndefined(result.mailRecvResourceAccount) + '"/><span>*(邮箱之间以“;”分割)</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: left;background: #efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">系统资源告警配置</h5></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>cpu使用率:</td>' +
                    '<td class="cpu">' +
                    '<input  id="cpu" type="number" min="0" style="width:150px" value="' + result.mailRecvResourceThreshold.cpuFree + '" onkeyup="control(event,this)"/><span style="color:#000">%</span><span> * 取值范围(0-100) 高于设定值后周期性触发邮件告警</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>内存空闲率:</td>' +
                    '<td class="Rbyte">' +
                    '<input id="Rbyte" type="number" min="0" style="width:150px" value="' + result.mailRecvResourceThreshold.memFree + '" onkeyup="control(event,this)"/><span style="color:#000">%</span><span> * 取值范围(0-100) 低于设定值后周期性触发邮件告警</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>硬盘剩余容量:</td>' +
                    '<td class="Hbyte">' +
                    '<input id="Hbyte" type="number" min="0" style="width:150px" value="' + result.mailRecvResourceThreshold.hdFree + '" onkeyup="control(event,this)"/><span style="color:#000">%</span><span> * 取值范围(0-100) 低于设定值后周期性触发邮件告警</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>日志存储剩余容量:</td>' +
                    '<td class="logbyte">' +
                    '<input id="logbyte" type="number" min="0" style="width:150px" value="' + result.mailRecvLogStorageThreshold.free + '" onkeyup="control(event,this)"/><span style="color:#000">%</span><span> * 取值范围(0-100) 低于设定值后周期性触发邮件告警</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>文件存储剩余容量:</td>' +
                    '<td class="filebyte">' +
                    '<input id="filebyte" type="number" min="0" style="width:150px" value="' + result.mailRecvFileStorageThreshold.free + '" onkeyup="control(event,this)"/><span style="color:#000">%</span><span> * 取值范围(0-100) 低于设定值后周期性触发邮件告警</span>' +
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;background: #efefef;">' +
                    '<button  class="mail-save btn btn-primary">保存</button>' +
                    '</td>' +
                    '</tr>';
                $("#box_tab1 .plist").empty();
                $("#box_tab1 .plist").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    $('.mail input').blur(function () {
        CheckMail($(".mail input").val())
    })
    $(".mail-save").click(function () {
        layer.alert("是否保存邮件存储设置？", {
            icon: 3,
            skin: 'layer-ext-moon',
            btn: ['确定', '取消'],
            yes: function (index1, layero) {
                var mailSenderhost = $(".mail input").val();
                var mailSenderPasswd = $(".mailpaw input").val();
                var mailSenderSmtpAddr = $(".mailaddress input").val();
                var mailSenderSmtpPort = $(".mailport input").val();
                var mailRecvResourcehost = $(".resourceaddress input").val();
                var cpuFree = [];
                var cpu = $(".cpu input").val();
                cpuFree.push(cpu);
                var memFree = [];
                var mem = $(".Rbyte input").val();
                memFree.push(mem);
                var hdFree = [];
                var hd = $(".Hbyte input").val();
                hdFree.push(hd);
                var mailRecvLogStorageAddr = $(".resourceaddress input").val();
                var mailRecvFileStorageAddr = $(".resourceaddress input").val();
                var lfree = [];
                var free1 = $(".logbyte input").val();
                lfree.push(free1);
                var ffree = [];
                var free2 = $(".filebyte input").val();
                ffree.push(free2);

                $.ajax({
                    url: host + "/dcms/api/v1/systemConfig/updateMailConfig",
                    type: "post",
                    async: false,
                    crossDomain: false,
                    data: JSON.stringify({
                        "token": token,
                        "mailSenderAccount": mailSenderhost,
                        "mailSenderPasswd": mailSenderPasswd,
                        "mailSenderSmtpAddr": mailSenderSmtpAddr,
                        "mailSenderSmtpPort": mailSenderSmtpPort,
                        "mailRecvResourceAccount": mailRecvResourcehost,
                        "mailRecvResourceThreshold": {
                            "cpuFree": cpuFree,
                            "memFree": memFree,
                            "hdFree": hdFree
                        },
                        "mailRecvLogStorageAddr": mailRecvLogStorageAddr,
                        "mailRecvFileStorageAddr": mailRecvFileStorageAddr,
                        "mailRecvLogStorageThreshold": {
                            "free": lfree
                        },
                        "mailRecvFileStorageThreshold": {
                            "free": ffree
                        }
                    }),
                    success: function (result) {
                        result.onSuccess = function () {
                            init();
                        }
                        processResponse(result, 1)
                    },
                    error: function (result) {
                        showGlobleTip("服务器异常，请联系管理员");
                    }
                });
                layer.close(index1);
            }
        });
    })

}
