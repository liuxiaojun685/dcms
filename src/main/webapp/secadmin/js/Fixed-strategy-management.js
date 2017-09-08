/**
 * Created by lenovo on 17-2-27.
 */
$(function () {
    showtab();
    advancedControl();
});
function showtab() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/securePolicy/queryPermissionPolicyList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "fileState": $('#level option:selected').val(),
            "roleType": $('#versionType option:selected').val()
        }),
        success: function (result) {
            result.onSuccess = function () {
                var data = result.policyList;
                $("#list").empty()
                if (data.length > 0) {
                    var name;
                    var str;
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].fileLevel == 4) {
                            name = '绝密文件'
                        }
                        if (data[i].fileLevel == 3) {
                            name = '机密文件'
                        }
                        if (data[i].fileLevel == 2) {
                            name = '秘密文件'
                        }
                        if (data[i].fileLevel == 1) {
                            name = '内部文件'
                        }
                        if (data[i].fileLevel == 0) {
                            name = '公开文件'
                        }
                        str += '<tr id="' + data[i].fileLevel + '">' +
                            '<td style="background:#efefef;width:20%;">' + name + ':</td>' +
                            '<td style="width:75%;">'
                        if (data[i].permission.contentRead == 1) {
                            str += '<span> 内容阅读</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentRead" checked/>'
                        } else {
                            str += '<span> 内容阅读</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentRead"/>'
                        }
                        if (data[i].permission.contentPrint == 1) {
                            str += '<span> 内容打印</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentPrint" checked/>'
                        } else {
                            str += '<span>内容打印</span> <input style="margin:5px;vertical-align: middle" type="checkbox" name="contentPrint"/>'
                        }
                        if (data[i].permission.contentPrintHideWater == 1) {
                            str += '<span> 打印时隐藏水印</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentPrintHideWater" checked/>'
                        } else {
                            str += '<span> 打印时隐藏水印 </span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentPrintHideWater"/>'
                        }
                        if (data[i].permission.contentModify == 1) {
                            str += '<span> 内容修改</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentModify" checked/>'
                        } else {
                            str += '<span>内容修改</span> <input style="margin:5px;vertical-align: middle" type="checkbox" name="contentModify"/>'
                        }
                        if (data[i].permission.contentScreenShot == 1) {
                            str += '<span> 内容截屏</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentScreenShot" checked/>'
                        } else {
                            str += '<span>内容截屏</span> <input style="margin:5px;vertical-align: middle" type="checkbox" name="contentScreenShot"/>'
                        }
                        if (data[i].permission.contentCopy == 1) {
                            str += '<span> 内容拷贝</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentCopy" checked/>'
                        } else {
                            str += '<span> 内容拷贝</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="contentCopy"/>'
                        }
                        if (data[i].permission.fileCopy == 1) {
                            str += '<span> 文件拷贝</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="fileCopy" checked/>'
                        } else {
                            str += '<span> 文件拷贝</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="fileCopy"/>'
                        }
                        if (data[i].permission.fileSaveCopy == 1) {
                            str += '<span> 另存副本</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="fileSaveCopy" checked/>'
                        } else {
                            str += '<span> 另存副本</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="fileSaveCopy"/>'
                        }
                        str += '</td>' +
                            '<td style="text-align:center;width:5%;"><a href="javaScript:;;"><i class="fa fa-save" onclick="updatatab(' + data[i].fileLevel + ')"></i></a></td>' +
                            '</tr>';
                    }
                    $("#list").append(str);
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function seachUser() {
    showtab();
}
function isChecked(s, name) {
    if ($("#" + s).find("input[name=" + name + "]").is(':checked')) {
        return 1
    } else {
        return 0
    }
}
function updatatab(s) {
    layer.alert("当前修改确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定','取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/securePolicy/updatePermissionPolicy",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "fileState": $('#level option:selected').val(),
                    "roleType": $('#versionType option:selected').val(),
                    "fileLevel": s,
                    "permission": {
                        contentRead: isChecked(s, 'contentRead'),
                        contentprint: isChecked(s, 'contentPrint'),
                        contentPrintHideWater: isChecked(s, 'contentPrintHideWater'),
                        contentmodify: isChecked(s, 'contentModify'),
                        contentscreenShot: isChecked(s, 'contentScreenShot'),
                        contentcopy: isChecked(s, 'contentCopy'),
                        fileCopy: isChecked(s, 'fileCopy'),
                        fileSaveCopy: isChecked(s, 'fileSaveCopy')

                    }
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        //showtab();
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })
            layer.close(index);
        }
    });
}

//高级管控
function advancedControl(){
	$.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/queryAdvancedControlStrategy",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
        	result.onSuccess = function () {
        		var str='';
                $("#lista").empty()
                    
                str += '<tr>' +
                    '<td style="background:#efefef;width:20%;">高级管控策略:</td>' +
                    '<td style="text-align:left;width:75%;">'
                if (result.prohibitRename == 1) {
                    str += '<span> 禁止改名</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitRename" checked/>'
                } else {
                    str += '<span> 禁止改名</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitRename"/>'
                }
                if (result.prohibitDelete == 1) {
                    str += '<span> 禁止删除</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitDelete" checked/>'
                } else {
                    str += '<span> 禁止删除</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitDelete"/>'
                }
                
                if (result.prohibitNetwork == 1) {
                    str += '<span> 禁止网络发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitNetwork" checked/>'
                } else {
                    str += '<span> 禁止网络发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitNetwork"/>'
                }
                if (result.prohibitMailSend == 1) {
                    str += '<span> 禁止邮件发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitMailSend" checked/>'
                } else {
                    str += '<span> 禁止邮件发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitMailSend"/>'
                }
                if (result.prohibitRightSend == 1) {
                    str += '<span> 禁止右键发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitRightSend" checked/>'
                } else {
                    str += '<span> 禁止右键发送</span><input style="margin:5px;vertical-align: middle" type="checkbox" name="prohibitRightSend"/>'
                }
                
                str += '</td>' +
                    '<td style="text-align:center;width:5%;"><a href="javaScript:;;"><i class="fa fa-save" onclick="updateControl()"></i></a></td>' +
                    '</tr>';
                    $("#lista").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
	});
}
	
function updateControl(){
	layer.alert("当前修改确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定','取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/systemConfig/updateAdvancedControlStrategy",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "prohibitRename": isCheck("prohibitRename"),
                    "prohibitDelete": isCheck("prohibitDelete"),
                    "prohibitRemove": isCheck("prohibitRemove"),
                    "prohibitNetwork": isCheck("prohibitNetwork"),
                	"prohibitMailSend": isCheck("prohibitMailSend"),
                    "prohibitRightSend": isCheck("prohibitRightSend")
                }),
                success: function (result) {
                    result.onSuccess = function () {
                    	advancedControl();
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            })
            layer.close(index);
        }
    });
}

function isCheck(name) {
    if ($("input[name=" + name + "]").is(':checked')) {
        return 1
    } else {
        return 0
    }
}