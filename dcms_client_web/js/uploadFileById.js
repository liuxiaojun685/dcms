/**
 * Created by lenovo on 17-3-10.
 */

var account = window.location.protocol + "//" + window.location.host;
//var account = "http://47.93.78.186:8080";
// var account="http://127.0.0.1:8080";
// var account="http://192.168.0.21:8080";
//var account="http://192.168.0.5:8080";
//var account="http://192.168.0.123:8080";
var token;
// var token;
//获取地址栏参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
//让键盘只能输入数字
function onlyNumber(event) {
    var keyCode = event.keyCode;
    if ((keyCode < 48 && keyCode > 8) || (keyCode > 57 && keyCode < 96) || keyCode < 8 || keyCode > 105) {
        window.event.returnValue = false;
    }
}
var filename;
var pageinfor;
var papername;
var unitno1;
var uploadType;
var pageinfors;
var needUpload = 0;
var classifiedTime = 0;
var durationType = 0;
var drafter;
var classificationAuthority;
var issuer;
var classificationStatus = 0;
var historys;

$(function () {
    if($(".disp").children().length == 0){
        $('input, textarea').placeholder();
    }
    if($(".dispBasis").children().length == 0){
        $('input, textarea').placeholder();
    }
    $("#docNumber").keydown(onlyNumber);
    $("#duplicationAmount").keydown(onlyNumber);

    //初始化保密期限时间
    var class1 = document.getElementById("ocx");
    /*    pageinfors = [
     {
     "uploadType": 1,
     "filename": "111111.XLS",
     "urgency": 1,
     "uid": 29848984,
     "userName": "巴达",
     "token": "333333",
     "classificationIdentifier": "c21252145",
     "issueNumber": "111",
     "docNumber": 2,
     "duplicationAmount": 3,
     "operationControl": {
     "copyPolicy": 0,
     "pastePolicy": 0,
     "printPolicy": 1,
     "printHideWaterPolicy": 1,
     "modifyPolicy": 1,
     "screenShotPolicy": 1,
     "savePolicy": 0,
     "readPolicy": 1
     },
     "version": 1,
     "classificationLevel": 3,
     "classificationDuration": {
     "classifiedTime": 1492147422000,
     "longterm": 0,
     "classificationPeriod": "",
     "declassifiedTime": 1498147422000,
     "durationDescription": "主席访问xx后解密"
     },
     "drafter": {
     "personId": 29848984,
     "personName": "巴达"
     },
     "classificationAuthority": {
     "personId": 9813046,
     "personName": "刘晓军"
     },
     "issuer": {
     "personId": 0,
     "personName": ""
     },
     "classificationOrganizationMajor": {
     "organizationID": 10001,
     "organizationName": "北京xxx集团公司"
     },
     "classificationOrganizationMinor": [
     {
     "organizationID": 10002,
     "organizationName": "辅助定密单位1"
     },
     {
     "organizationID": 10003,
     "organizationName": "辅助定密单位2"
     },
     {
     "organizationID": 10004,
     "organizationName": "辅助定密单位3"
     }
     ],
     "accessDescription": "111111111111111",
     "accessScope": [
     {
     "organizationID": 10001,
     "userID": 8662774
     },
     {
     "organizationID": 10001,
     "userID": 5520593
     },
     {
     "organizationID": 10001,
     "userID": 2249249
     }
     ],
     "basisType": 0,
     "basisDescription": [
     {
     "basisLevel": 1,
     "basisContent": "11"
     },
     {
     "basisLevel": 1,
     "basisContent": "2222"
     }
     ],
     "classificationStatus": 1,
     "classificationHistory": [
     {
     "time": 1492147422000,
     "userID": 9813046,
     "historyType": 1,
     "classificationLevelBefore": 0,
     "historyDescription": "111",
     "durationBefore": {
     "classifiedTime": 1492147422000,
     "longterm": 1,
     "classificationPeriod": "300000",
     "declassifiedTime": 0,
     "durationDescription": ""
     }
     }
     ]
     },
     {
     "uploadType": 1,
     "filename": "2222222.XLS",
     "urgency": 1,
     "uid": 29848984,
     "userName": "巴达",
     "token": "333333",
     "classificationIdentifier": "b332521452b332521452b33252145221"
     }
     ];*/
    pageinfors = JSON.parse(class1.initFileHeader());
    if (pageinfors != null) {
        if (pageinfors.length > 0) {
            pageinfor = pageinfors[0]
        }
    }
    //alert(JSON.stringify(pageinfors));

    //初始化token
    token = pageinfor.token;
    uploadType = pageinfor.uploadType;
    if (pageinfor.classificationDuration != null && pageinfor.classificationDuration.classifiedTime != null) {
        classifiedTime = pageinfor.classificationDuration.classifiedTime;
    }

    drafter = pageinfor.drafter;
    if (drafter == null) {
        drafter = {
            "personId": 0,
            "personName": ""
        };
    }
    classificationAuthority = pageinfor.classificationAuthority;
    if (classificationAuthority == null) {
        classificationAuthority = {
            "personId": 0,
            "personName": ""
        };
    }
    issuer = pageinfor.issuer;
    if (issuer == null) {
        issuer = {
            "personId": 0,
            "personName": ""
        };
    }

    classificationStatus = pageinfor.classificationStatus;
    if (classificationStatus == null) {
        classificationStatus = 0;
    }

    historys = pageinfor.classificationHistory;
    if (historys == null) {
        historys = new Array();
    }

    //初始化页面头名称
    if (uploadType == 1) {
        $(".index").text("预定密")
        needUpload = 0;
        drafter = {
            "personId": parseInt(pageinfor.uid),
            "personName": pageinfor.userName
        };
        classificationStatus = 1;
    }
    if (uploadType == 2) {
        $(".index").text("正式定密")
        needUpload = 1;
        classificationAuthority = {
            "personId": parseInt(pageinfor.uid),
            "personName": pageinfor.userName
        };
        classificationStatus = 3;
    }
    if (uploadType == 3) {
        $(".index").text("文件签发")
        needUpload = 1;

        issuer = {
            "personId": parseInt(pageinfor.uid),
            "personName": pageinfor.userName
        };
        classificationStatus = 7;
    }
    if (uploadType == 4) {
        $(".index").text("密级变更")
        needUpload = 1;
        classificationAuthority = {
            "personId": parseInt(pageinfor.uid),
            "personName": pageinfor.userName
        };
        classificationStatus = 7;
    }
    if (uploadType == 5) {
        $(".index").text("文件解密")
        needUpload = 1;
        classificationAuthority = {
            "personId": parseInt(pageinfor.uid),
            "personName": pageinfor.userName
        };
        classificationStatus = 15;
    }


    var duration = pageinfor.classificationDuration;
    if (duration != null) {
        if (duration.longterm == 1) {
            durationType = 1;
        } else if (duration.classificationPeriod != null && duration.classificationPeriod != "") {
            durationType = 2;
        } else if (duration.declassifiedTime != null && duration.declassifiedTime != "") {
            durationType = 3;
        } else if (duration.durationDescription != null && duration.durationDescription != "") {
            durationType = 4;
        }

    }
    //初始化文件等级下拉菜单
    loadPolicy();

    applyValidPeriod(durationType);

    //初始化文件名称
    var filenames = "";
    if (pageinfors.length > 1) {
        for (var i = 0; i < pageinfors.length; i++) {
            if (i == 0) {
                filenames = pageinfors[i].filename;
            } else {
                filenames = filenames + " | " + pageinfors[i].filename;
            }
            $("#filename").val(filenames)
        }
        $("#fid").val(pageinfor.classificationIdentifier);
    } else {
        $("#filename").val(pageinfor.filename);
        //初始化文件id
        $("#fid").val(pageinfor.classificationIdentifier);
    }

    //初始化保密期限类型
    $("#durationType option[value='" + durationType + "']").attr("selected", true)


    //初始化密标版本
    $('#markVersion').val(pageinfor.version)
    //初始化紧急程度
    $("#urgency option[value='" + pageinfor.urgency + "']").attr("selected", true)
    //初始化定密类型下拉框
    $("#basisType option[value='" + pageinfor.basisType + "']").attr("selected", true)
    //定密依据内容
    bt2();
    bt3(pageinfor.basisDescription);
    //aBasis(pageinfor.applyBasis)
    //知悉范围初始化
    $("#fileScopeDesc").val(pageinfor.accessDescription)
    //初始化主定密单位
    if (pageinfor.classificationOrganizationMajor != null) {
        $("#major").val(pageinfor.classificationOrganizationMajor.organizationName);
        $("#unitNos").val(pageinfor.classificationOrganizationMajor.organizationID);
        unitno1 = pageinfor.classificationOrganizationMajor.organizationID
    } else {
        MajorList()
    }
    //初始化辅助定密单位
    Minor();
    Minor2(pageinfor.classificationOrganizationMinor);
    //初始化选中辅助定密单位下拉框
    //初始化文号
    $("#issueNumber").val(pageinfor.issueNumber)
    //初始化份号
    $("#docNumber").val(pageinfor.docNumber)
    //初始化签发份数
    $("#duplicationAmount").val(pageinfor.duplicationAmount)
    //初始化申请理由
    //$("#createComment").val(pageinfor.createComment)
    //初始化文件密级
    $("#ss option[value='" + pageinfor.classificationLevel + "']").attr("selected", true)
    //初始化审核人
    /* approves($("#ss option:selected").val())*/
    //初始化复选框
    if (uploadType == 1) {
        queryPermissionPolicy()
    } else {
        var permissions = pageinfor.operationControl

        if (permissions.readPolicy == 1) {
            $(".permission input[name='contentRead']").attr("checked", true)
        }
        if (permissions.printPolicy == 1) {
            $(".permission input[name='contentPrint']").attr("checked", true)
        }
        if (permissions.printHideWaterPolicy == 1) {
            $(".permission input[name='contentPrintHideWater']").attr("checked", true)
        }
        if (permissions.modifyPolicy == 1) {
            $(".permission input[name='contentModify']").attr("checked", true)
        }
        if (permissions.screenShotPolicy == 1) {
            $(".permission input[name='contentScreenShot']").attr("checked", true)
        }
        if (permissions.pastePolicy == 1) {
            $(".permission input[name='contentCopy']").attr("checked", true)
        }
        if (permissions.copyPolicy == 1) {
            $(".permission input[name='fileCopy']").attr("checked", true)
        }
        if (permissions.savePolicy == 1) {
            $(".permission input[name='fileSaveCopy']").attr("checked", true)
        }
    }


    tree(pageinfor.accessScope);
    //初始化知悉范围文本框
    if (pageinfor.accessScope != null) {
        $("#fileScopes").val(JSON.stringify(pageinfor.accessScope))
    }

    //知悉范围按钮
    $(".choose").unbind("click").click(function () {
        $("#treeworde").val("")

        var jsonData = [];
        if ($("#fileScopes").val() != null && $("#fileScopes").val() != "") {
            jsonData = JSON.parse($("#fileScopes").val());
        }
        tree(jsonData);
        layer.open({
            type: 1,
            title: "知悉范围",
            area: ['400px', '500px'],
            btn: ["确定", "取消"],
            yes: function (index, layero) {
                var fileScope = new Array();
                //获取主定密单位
                var unitNo = unitno1;
                //获取所有的CheckBox
                $("#browser :checkbox:checked").each(function () {
                    var uid = $(this).val();
                    if (uid.indexOf('did') == -1) {
                        var scope = {
                            "organizationID": unitNo,
                            "userID": uid
                        }
                        fileScope.push(scope);
                    }
                });
                var fileScope1 = new Array();
                $("#browser :checkbox:not(:checked)").each(function () {
                    var uid = $(this).val();
                    if (uid.indexOf('did') == -1) {
                        var scope = {
                            "organizationID": unitNo,
                            "userID": uid
                        }
                        fileScope1.push(scope);
                    }
                });
                var fileScopes1 = $("#fileScopes").val();
                if ($("#fileScopes").val() != "" && $("#fileScopes").val() != null) {
                    var jsonData = JSON.parse($("#fileScopes").val());
                    for (var i = 0; i < jsonData.length; i++) {
                        if (testtree(fileScope, jsonData[i].userID)) {
                            var scopes = {
                                "organizationID": jsonData[i].organizationID,
                                "userID": jsonData[i].userID
                            }
                            fileScope.push(scopes);
                        }
                    }
                    for (var i = 0; i < fileScope1.length; i++) {
                        for (var j = 0; j < fileScope.length; j++) {
                            if (fileScope[j].userID == fileScope1[i].userID) {
                                fileScope.splice(j, 1)
                                j--;
                            }
                        }
                    }
                }
                $("#treeworde").val("")
                tree(fileScope);

                $("#fileScopes").val(JSON.stringify(fileScope));

                $('#tooltips').pt({
                    position: 'b', // 默认属性值
                    align: 'r',	   // 默认属性值
                    arrow: true,
                    content: $('#tooltips').val()
                });
                layer.close(index);
            },
            content: $("#dtree") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    });

    $(".choose1").unbind("click").click(function () {
        $("#tooltips").show()
        $("#fileScopeDesc").hide()
    })

    $(".choose2").unbind("click").click(function () {
        $("#tooltips").hide()
        $("#fileScopeDesc").show()
    })
    $("#treechoose").unbind("click").click(function () {
        var jsonData = [];
        if ($("#fileScopes").val() != null && $("#fileScopes").val() != "") {
            jsonData = JSON.parse($("#fileScopes").val());
        }
        tree(jsonData);
    })


});

//测试时候tree重复
function testtree(a, b) {
    if (a != null) {
        for (var i = 0; i < a.length; i++) {
            if (a[i].userID == b) {
                return false
            }
        }
        return true
    }
}
//初始化知悉范围
function fileScope(s) {

    if (s != null) {
        for (var i = 0; i < s.length; i++) {
            $("#browser input[value='" + s[i].userID + "']").attr("checked", true)
            // $("#"+s[i].uid).attr("checked",true)
        }
    }
    var fileScope1 = "";
    $("#browser :checkbox:checked").each(function () {

        var uid = $(this).val();
        if (uid.indexOf('did') == -1) {

            fileScope1 += $(this).parent().text() + ",";
        } else if (uid == "did-root") {

            fileScope1 += $(this).parent().text();
        }
    });
    $("#tooltips").val(fileScope1);

}
//根据类型选择定密依据类容
function bt() {
    bt2()
}
//初始化定密依据类容
function bt2() {
    $.ajax({
        type: "post",
        url: account + "/dcms/api/v1/basis/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        cache: false,
        data: JSON.stringify({
            "token": token,
            "basisType": $("#basisType option:selected").val(),
            "basisLevel": $("#ss option:selected").val()
        }),
        success: function (result) {

            var dat = result.basisList;
            var str = ""
            var basisLevel
            if (dat != null && dat.length > 0) {
                for (var i = 0; i < dat.length; i++) {
                    if (dat[i].basisLevel == 0) {
                        basisLevel = '[公开]'
                    }
                    if (dat[i].basisLevel == 1) {
                        basisLevel = '[内部]'
                    }
                    if (dat[i].basisLevel == 2) {
                        basisLevel = '[秘密]'
                    }
                    if (dat[i].basisLevel == 3) {
                        basisLevel = '[机密]'
                    }
                    if (dat[i].basisLevel == 4) {
                        basisLevel = '[绝密]'
                    }
                    str += '<tr><td><input type="checkbox" >' + basisLevel + dat[i].basisContent + '</td></tr>'
                }
            }
            $("#basisContent").empty();
            $("#basisContent").append(str);
        },
        error: function (result) {
        }
    })
}


function bt3(s) {
    var basisContents = "";
    var basisLevel;
    var data = s;
    if (data == null) {
        return;
    }
    for (var i = 0; i < data.length; i++) {
        if (data[i].basisLevel == 0) {
            basisLevel = '[公开]'
        }
        if (data[i].basisLevel == 1) {
            basisLevel = '[内部]'
        }
        if (data[i].basisLevel == 2) {
            basisLevel = '[秘密]'
        }
        if (data[i].basisLevel == 3) {
            basisLevel = '[机密]'
        }
        if (data[i].basisLevel == 4) {
            basisLevel = '[绝密]'
        }
        basisContents = basisLevel + data[i].basisContent
        $(".dispBasis").append('<span  class="Basis"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div  class="dartbasis"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + basisContents + '</div><img onclick=" removeBasis(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
    }
}


//定密依据内容
function aBasis(result) {

    var basisContents = "";

    var basisLevel;
    for (var i = 0; i < result.length; i++) {
        if (result[i].basisLevel == 0) {
            basisLevel = '[公开]'
        }
        if (result[i].basisLevel == 1) {
            basisLevel = '[内部]'
        }
        if (result[i].basisLevel == 2) {
            basisLevel = '[秘密]'
        }
        if (result[i].basisLevel == 3) {
            basisLevel = '[机密]'
        }
        if (result[i].basisLevel == 4) {
            basisLevel = '[绝密]'
        }
        basisContents = basisLevel + result[i].basisContent
        $(".dispBasis").append('<span  class="Basis"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div  class="dartbasis"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + basisContents + '</div><img onclick=" removeBasis(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");

    }
}

function basisContentcontains(arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i].basisContent == obj) {
            return true;
        }
    }
    return false;
}

//时间验证
function check(n) {
    var te = /^[1-9]+[0-9]*]*$/

    if (!te.test(n)) {
        layer.alert('请输入两位正整数', {
            icon: 1,
            skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
        })
        return true
    } else {
        return false
    }
}
//期限下拉框
function dur() {
    var s = $("#durationType option:selected").val()
    applyValidPeriod(s);
}


function getTime() {
    var start = {
        elem: '#start',
        format: 'YYYY/MM/DD ',
        start: laydate.now(0, 'YYYY/MM/DD '),
        //istime: true,
        istoday: true
    };
    laydate(start);
}

function add0(m) {
    return m < 10 ? '0' + m : m
}
function getMyDate(str) {
    var oDate = new Date(str)
    oYear = oDate.getFullYear()
    oMonth = oDate.getMonth() + 1
    oDay = oDate.getDate()
//    oHour = oDate.getHours()
//    oMin = oDate.getMinutes()
//    oSen = oDate.getSeconds()
    oTime = oYear + '/' + add0(oMonth) + '/' + add0(oDay);//最后拼接时间
    return oTime;
};

//保密期限
function applyValidPeriod(s) {
    var str;
    var time;
    if (s == 2) {
    	
        str = '<input type="text" id="time" class="form-control" style="width: 50px;float: left; height: 25px; line-height: 25px;" ><select id="time2" style="float: left;width: 60px; margin-left: 5px;height: 25px; line-height: 25px;" ><option>年</option><option>月</option><option>日</option></select>'
    }
    if (s == 3) {
        if (pageinfor == null || pageinfor.classificationDuration == null || pageinfor.classificationDuration.declassifiedTime == null) {
            str = '<input style="height: 25px; line-height: 25px;float:left;padding: 0" type="text" class="laydate-icon" id="start" onclick="getTime()" >';
        } else {
            time = pageinfor.classificationDuration.declassifiedTime;
            str = '<input style="height: 25px; line-height: 25px;float:left;padding: 0" type="text" class="laydate-icon" id="start" onclick="getTime()" value="' + getMyDate(time) + '">';
        }
    }
    if (s == 4) {
        if (pageinfor == null || pageinfor.classificationDuration == null || pageinfor.classificationDuration.durationDescription == null) {
            str = '<textarea id="durationDescription"  class="form-control"  style=" resize:none;  height: 25px; line-height: 25px;width: 370px" ></textarea>'
        } else {
            str = '<textarea id="durationDescription"  class="form-control"  style="resize:none;  height: 25px; line-height: 25px;width: 370px" >' + pageinfor.classificationDuration.durationDescription + '</textarea>'
        }
    }
    $(".times").empty()
    $(".times").append(str)
    if (s == 2) {
        if (pageinfors == null || pageinfor.classificationDuration.classificationPeriod == null) {

        	//获取默认 保密期限
        	$.ajax({
        		type: "post",
                url: account + "/dcms/api/v1/securePolicy/queryValidPeriod",
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "fileLevel": $("#ss option:selected").val()
                }),
                success: function (result) {
                	if(result.validPeriod=="000000"){
                		$("#time").val("0")
                		$("#time2").find("option").eq(0).attr("selected", true)
                	}
                	time = result.validPeriod .split("");
                    if (time[0] + time[1] != "00") {
                        if (time[0] == "0") {
                            $("#time").val(time[1])
                        } else {
                            $("#time").val(time[0] + time[1])
                        }
                        $("#time2").find("option").eq(0).attr("selected", true)
                    }
                    if (time[2] + time[3] != "00") {
                        if (time[2] == "0") {
                            $("#time").val(time[3])
                        } else {
                            $("#time").val(time[2] + time[3])
                        }
                        $("#time2").find("option").eq(1).attr("selected", true)
                    }
                    if (time[4] + time[5] != "00") {
                        if (time[4] == "0") {
                            $("#time").val(time[5])
                        } else {
                            $("#time").val(time[4] + time[5])
                        }
                        $("#time2").find("option").eq(2).attr("selected", true)
                    }
                },
                error: function (result) {
                }
            });
        	
        }
        if (pageinfor.classificationDuration.classificationPeriod != null) {
            time = pageinfor.classificationDuration.classificationPeriod.split("")
            if (time[0] + time[1] != "00") {
                if (time[0] == "0") {
                    $("#time").val(time[1])
                } else {
                    $("#time").val(time[0] + time[1])
                }
                $("#time2").find("option").eq(0).attr("selected", true)
            }
            if (time[2] + time[3] != "00") {
                if (time[2] == "0") {
                    $("#time").val(time[3])
                } else {
                    $("#time").val(time[2] + time[3])
                }
                $("#time2").find("option").eq(1).attr("selected", true)
            }
            if (time[4] + time[5] != "00") {
                if (time[4] == "0") {
                    $("#time").val(time[5])
                } else {
                    $("#time").val(time[4] + time[5])
                }
                $("#time2").find("option").eq(2).attr("selected", true)
            }
        }
    }
}

//主定密单位
function MajorList() {

    $.ajax({
        type: "post",
        url: account + "/dcms/api/v1/unit/queryMajorList",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            $("#major").val(result.name);
            $("#unitNos").val(result.unitNo);
            unitno1 = result.unitNo;
        },
        error: function (result) {
        }
    });
}

function contains(arr, obj) {
    var i = arr.length - 1;
    while (i >= 0) {
        if (arr[i].unitNo == obj) {
            return i;
        }
        i--
    }
    return -1;
}


//辅助定密单位

function Minor() {
    var str = '';
    $.ajax({
        type: "post",
        url: account + "/dcms/api/v1/unit/queryMinorList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": 0,
            "pageSize": 10
        }),
        success: function (result) {
            var dat = result.unitList;
            str = ''
            for (var i = 0; i < dat.length; i++) {
                //str+='<tr><td id="unitNo'+dat[i].unitNo+'"><input type="checkbox" onclick=Minorchoose('+dat[i].unitNo+')>['+dat[i].unitNo+']'+dat[i].name+'</td></tr>'
                str += '<tr><td id="unitNo' + dat[i].unitNo + '"><input type="checkbox" >[' + dat[i].unitNo + ']' + dat[i].name + '</td></tr>'
            }

            $("#applyMinorUnits").prepend(str);
        },
        error: function (result) {
        }
    });

}

function Minor2(s) {
    if (s == null) {
        return;
    }
    if (s.length > 0) {
        var num
        var rds
        for (var i = 0; i < s.length; i++) {
            rds = "[" + s[i].organizationID + "]" + s[i].organizationName;

            $(".disp").append('<span class="sort"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div   style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + rds + '</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");

        }
    }


}
//
function getBlur(t) {



    $(".ipva").focus();

    $(".ipva").unbind("keypress").bind("keypress", function () {
        if (window.event) {
            oEvent = window.event;		//处理兼容性，获得事件对象
            //设置IE的charCode值
            oEvent.charCode = (oEvent.type == "keypress") ? oEvent.keyCode : 0;
        }

        if (oEvent.keyCode == 13) {
            var rds = $(this).val();
            var key;
            $(".sort div").each(function () {
                if ($(this).text() == rds) {
                    key = 1;
                }
            });
            if($(".disp").children().length != 0){
                $('input, textarea').removeAttr("placeholder");
            }
            if (key == 1) {
                layer.alert("不能输入相同的定密单位")
                $(".ipva").val("")
                return;
            }
            var parten = /\[[a-zA-Z0-9]\]*/;

            if (parten.test(rds)) {
                var sortable = $(".ui-sortable");
                $(".disp").append('<span  class="sort"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div   style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + rds + '</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
            } else {
                layer.alert("请按‘[’+单位编号+‘]’+单位名称格式输入")
            }
            $(this).val("");

        }
    });

}

//选择辅助定密单位

function Minorchoose() {
    var rds = ''
    var key
    var parten = /^\s*$/;
    var Minors = new Array();
    $("#applyMinorUnits :checkbox:checked").each(function () {
        rds = $(this).parent().text();
        Minors.push(rds)

    })
    for (var i = 0; i < Minors.length; i++) {
        key = 0
        $(".sort div").each(function () {

            if ($(this).text() == Minors[i]) {
                key = 1
            }
        })
        if (key != 1) {
            if (parten.test(rds)) {
            } else {
                var sortable = $(".ui-sortable");
                $(".disp").append('<span  class="sort"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div    style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + Minors[i] + '</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
        }
    }

}

//移除
function removeUnit(t) {
    $(t).parent().remove();
}
//获取字段
function field(s) {
    return s.substring(s.indexOf("[") + 1, s.indexOf("]"))
}

//定密依据获取
function applyBasis(result) {

    var applyBasis = [];
    if (result.length > 0) {

        for (var i = 0; i < result.length; i++) {

            if (field(result[i]) == "公开") {
                var Basis = {
                    "basisLevel": 0,
                    "basisContent": result[i].substring(result[i].indexOf("]") + 1)
                }
            }
            if (field(result[i]) == "内部") {
                var Basis = {
                    "basisLevel": 1,
                    "basisContent": result[i].substring(result[i].indexOf("]") + 1)
                }
            }
            if (field(result[i]) == "秘密") {
                var Basis = {
                    "basisLevel": 2,
                    "basisContent": result[i].substring(result[i].indexOf("]") + 1)
                }
            }
            if (field(result[i]) == "机密") {
                var Basis = {
                    "basisLevel": 3,
                    "basisContent": result[i].substring(result[i].indexOf("]") + 1)
                }
            }
            if (field(result[i]) == "绝密") {
                var Basis = {
                    "basisLevel": 4,
                    "basisContent": result[i].substring(result[i].indexOf("]") + 1)
                }
            }
            applyBasis.push(Basis)
        }
    }
    return applyBasis
}

//提交表单
function tjsj() {
    //定密依据
    var applyBasis1 = new Array()
    $(".Basis div").each(function () {
        applyBasis1.push($(this).text())
    })
    /*if (applyBasis1 == null || applyBasis1.length == 0) {
     layer.alert('请选择定密依据', {
     icon: 1,
     skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
     })
     return
     }*/

    var applyBasises = applyBasis(applyBasis1)

    /*   if($("#applyBasis2").val()!=null&&$("#applyBasis2").val()!=""){
     var Basis={
     "basisLevel":$("#ss option:selected").val(),
     "basisContent":$("#applyBasis2").val()
     }
     applyBasises.push(Basis)
     }*/

    //获得辅助定密单位
    var sorts = new Array()
    $(".sort div").each(function () {
        sorts.push($(this).text())
    })
    // if (sorts == null || sorts.length == 0) {
    //     layer.alert('请选择辅助定密单位', {
    //         icon: 1,
    //         skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
    //     })
    //     return
    // }

    var applyMinorUnits = new Array()
    var applyMinorUnit;
    if (sorts.length > 0) {
        for (var i = 0; i < sorts.length; i++) {
            applyMinorUnit = {
                organizationID: parseInt(field(sorts[i])),
                organizationName: sorts[i].substring(sorts[i].indexOf("]") + 1)
            }
            applyMinorUnits.push(applyMinorUnit)
        }
    }
    //主定密单位
    var applyMajorUnit = {
        organizationID: parseInt($("#unitNos").val()),
        organizationName: $("#major").val()
    }

    //获得时间
    var applyValidPeriod;
    var durationType = $("#durationType option:selected").val();
    var durationDescription;
    var fileDecryptTime;
    var longterm = 0;
    var currentTime = (new Date()).valueOf();
    if (uploadType == 2) {
        classifiedTime = currentTime;
    }
    var classificationDuration;
    if (durationType == 1) {
        longterm = 1
        classificationDuration={
            "classifiedTime": classifiedTime,
            "longterm": longterm
        }
    }
    if (durationType == 2) {
        applyValidPeriod = $("#time").val()

        if (check(applyValidPeriod)) {
            return
        }
        if (applyValidPeriod.length == 1) {
            applyValidPeriod = "0" + applyValidPeriod
        }
        if ($("#time2").find("option").eq(0).is(":selected")) {
            applyValidPeriod = applyValidPeriod + "0000"
        }
        if ($("#time2").find("option").eq(1).is(":selected")) {
            applyValidPeriod = "00" + applyValidPeriod + "00"
        }
        if ($("#time2").find("option").eq(2).is(":selected")) {
            applyValidPeriod = "0000" + applyValidPeriod
        }
/*        durationDescription = "";
        fileDecryptTime = "";*/
        classificationDuration={
            "classifiedTime": classifiedTime,
            "longterm": longterm,
            "classificationPeriod": applyValidPeriod
        }
    }
    if (durationType == 3) {
/*        applyValidPeriod = ""
        durationDescription = "";*/
        var date = new Date($("#start").val());
        fileDecryptTime = date.getTime();
        classificationDuration={
            "classifiedTime": classifiedTime,
            "longterm": longterm,
            "declassifiedTime": fileDecryptTime
        }
    }
    if (durationType == 4) {
   /*     applyValidPeriod = "";
        fileDecryptTime = "";*/
        durationDescription = $("#durationDescription").val();
        classificationDuration={
            "classifiedTime": classifiedTime,
            "longterm": longterm,
            "declassifiedTime": fileDecryptTime,
            "durationDescription": durationDescription
        }
    }
    //获取知悉范围
    var fileScopes = [];
    var scope;
    if ($("#fileScopes").val() != null && $("#fileScopes").val() != "") {
        var fileScopes1 = JSON.parse($("#fileScopes").val())
        for (var i = 0; i < fileScopes1.length; i++) {
            scope = {
                "organizationID": parseInt(fileScopes1[i].organizationID),
                "userID": parseInt(fileScopes1[i].userID)
            }
            fileScopes.push(scope);
        }
    }

    //获得份数
    var duplicationAmount = parseInt($("#duplicationAmount").val());
    if (duplicationAmount == null) {
        duplicationAmount = 0;
    }
    var docNumber = parseInt($("#docNumber").val());
    if (docNumber == null) {
        docNumber = 0;
    }
    if (uploadType != 1) {
        //if ($("#createComment").val() == null || $("#createComment").val() == "") {
        //    layer.alert('请填写文件概要', {
        //        icon: 1,
        //        skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
        //    })
        //    return
        //
        //}
    }



    var markVersion = 1;
    if (pageinfor.version != null) {
        markVersion = parseInt(pageinfor.version);
    }

    if (uploadType != 1) {
        if (pageinfor != null && pageinfor.classificationLevel != null && pageinfor.classificationLevel != $("#ss option:selected").val()) {
            var history = {
                "time": currentTime,
                "userID": parseInt(pageinfor.uid),
                "historyType": 0,
                "classificationLevelBefore": pageinfor.classificationLevel,
                "historyDescription": $("#createComment").val()
            }
            historys.push(history);
        }

        if (pageinfor != null && pageinfor.classificationDuration != null) {
            if (pageinfor.classificationDuration.longterm != longterm || pageinfor.classificationDuration.classificationPeriod != applyValidPeriod
                || pageinfor.classificationDuration.declassifiedTime != fileDecryptTime || pageinfor.classificationDuration.durationDescription != durationDescription) {
                var history = {
                    "time": currentTime,
                    "userID": pageinfor.uid,
                    "historyType": 1,
                    "historyDescription": $("#createComment").val(),
                    "durationBefore": {
                        "classifiedTime": currentTime,
                        "longterm": pageinfor.classificationDuration.longterm,
                        "classificationPeriod": pageinfor.classificationDuration.classificationPeriod,
                        "declassifiedTime": pageinfor.classificationDuration.declassifiedTime,
                        "durationDescription": pageinfor.classificationDuration.durationDescription
                    }
                }
                historys.push(history);
            }
        }
    }

    var optionses = new Array();
    var options1;
    for (var i = 0; i < pageinfors.length; i++) {
        options1 = {
            "token": token,
            "issueNumber": $("#issueNumber").val() == "" ? "" : $("#issueNumber").val(),
            "docNumber": docNumber,
            "duplicationAmount": duplicationAmount,
            "operationControl": {
                "copyPolicy": isChecked($(".permission"), "fileCopy"),
                "pastePolicy": isChecked($(".permission"), "contentCopy"),
                "printPolicy": isChecked($(".permission"), "contentPrint"),
                "printHideWaterPolicy": isChecked($(".permission"), "contentPrintHideWater"),
                "modifyPolicy": isChecked($(".permission"), "contentModify"),
                "screenShotPolicy": isChecked($(".permission"), "contentScreenShot"),
                "savePolicy": isChecked($(".permission"), "fileSaveCopy"),
                "readPolicy": isChecked($(".permission"), "contentRead")
            },
            "version": markVersion,
            "classificationLevel": parseInt($("#ss option:selected").val()),
            "classificationIdentifier": pageinfors[i].classificationIdentifier,
            "classificationDuration": classificationDuration,
            "drafter": {
                "personId": drafter.personId,
                "personName": drafter.personName
            },
            "classificationAuthority": {
                "personId": classificationAuthority.personId,
                "personName": classificationAuthority.personName
            },
            "issuer": {
                "personId": issuer.personId,
                "personName": issuer.personName
            },
            "classificationOrganizationMajor": applyMajorUnit,
            "classificationOrganizationMinor": applyMinorUnits,
            "accessDescription": $("#fileScopeDesc").val() == "" ? "" : $("#fileScopeDesc").val(),
            "accessScope": fileScopes,
            "basisType": parseInt($("#basisType option:selected").val()),
            "basisDescription": applyBasises,
            "classificationStatus": classificationStatus,
            "classificationHistory": historys,
            "urgency": parseInt($("#urgency option:selected").val())
        }
        optionses.push(options1)
    }


    var fileState = 0;
    if (pageinfor.classificationStatus == 1) {
        fileState = 1;
    } else if (pageinfor.classificationStatus == 3) {
        fileState = 2;
    } else if (pageinfor.classificationStatus == 7) {
        fileState = 3;
    } else if (pageinfor.classificationStatus == 15) {
        fileState = 5;
    }

    var params = {
        "token": token,
        "uploadType": 6,
        "fid": $("#fid").val(),
        "fileLevel": $("#ss option:selected").val(),
        "fileState": fileState
    }

    $.ajax({
        type: "post",
        url: account + "/dcms/api/v1/file/canUpload",
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "uploadType": uploadType,
            "fid": $("#fid").val(),
            "fileLevel": $("#ss option:selected").val(),
            "fileState": fileState
        }),
        success: function (result) {
            if (result.code == 0) {
                //alert("头：" + JSON.stringify(optionses) + "\n是否需要上传：" + needUpload + "\n上传参数：" + JSON.stringify(params))
                var class1 = document.getElementById("ocx");
                class1.saveFileHeader(JSON.stringify(optionses), needUpload, JSON.stringify(params))
            } else {
                layer.alert(result.msg)
            }

        },
        error: function (result) {
        }
    });

}
//判断是否选中
function isChecked(t, values) {
    if (t.find("input[name=" + values + "]").is(':checked')) {
        return 1;
    } else {
        return 0;
    }
}


//调用树接口
function tree(s) {
    //默认的知悉范围
    var keyword = $("#treeworde").val()
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0,
            "fileLevel": "",
            "keyword": keyword == "" ? null : keyword//进入客户端容器“”值到后台会被转换成字符串“null”而不是null
        }),
        url: account + "/dcms/api/v1/department/queryTree",
        dataType: "json",
        cache: false,
        async: false,
        crossDomain: false,
        success: function (data) {

            var str = '<ul id="browser" class="filetree treeview">' +
                '<li id="root"><span class="dep"><input type="checkbox" onclick="clickget(this.id)" value=' + data.did + ' id="' + data.did + '">' + data.name + '</span>' +
                maketree(data) + '</li></ul>'
            $("#browser").empty();

            $("#browser").append(str);
            $("#browser").treeview({
                collapsed: false,
                add: $("#browser").html
            });

            fileScope(s)


        }
    });

}

//生成树
function maketree(data) {

    var str = ''

    for (var i = 0; i < data.childUserList.length; i++) {
        str += '<ul>' +
            '<li><span class="user"><input type="checkbox" onclick="clickget(this.id)" id="' + data.childUserList[i].uid + '" name="' + data.did + '" value="' + data.childUserList[i].uid + '">' + data.childUserList[i].name + '</span>' +
            '</li>' +
            '</ul>'

    }

    for (var i = 0; i < data.childDepartmentList.length; i++) {
        str += '<ul>' +
            '<li><span class="dep"><input type="checkbox" onclick="clickget(this.id)" id="' + data.childDepartmentList[i].did + '" name="' + data.did + '" value="' + data.childDepartmentList[i].did + '">' + data.childDepartmentList[i].name + '</span>'
            + maketree(data.childDepartmentList[i]) +
            '</li>' +
            '</ul>'
    }
    return str;

}

//取消每级父节点
function findParents(parentId) {
    if ($("#" + parentId).is(':checked')) {
        $("#" + parentId).prop("checked", false);
        findParents($("#" + parentId).attr("name"));
    }
}
//如果是根节点
function checkRoot(varId) {

    if ($("#did-root").is(':checked')) {
        $("input[name=" + varId + "]").prop("checked", false);
        $("input[name=" + varId + "]").each(function () {
            checkRoot($(this).val());
        });
    }
}
//单击CheckBox
function clickget(varId) {

    {
        if ($("#" + varId).is(':checked')) {
            $("input[name=" + varId + "]").prop("checked", true);
            $("input[name=" + varId + "]").each(function () {
                clickget($(this).val());
            });
        } else {

            $("input[name=" + varId + "]").prop("checked", false);
            $("input[name=" + varId + "]").each(function () {
                clickget($(this).val());
            });
            //查找父节点，父节点选中的须被取消
            var parents = $("#" + varId).attr("name");
            findParents(parents);
        }

    }

}


function getBasisBlur(t) {
    $(".ipvabasis").focus();
    // layer.tips('输入完成后请按回车！', '#content');

    $(".ipvabasis").unbind("keypress").bind("keypress", function () {
        if (window.event) {
            oEvent = window.event;		//处理兼容性，获得事件对象
            //设置IE的charCode值
            oEvent.charCode = (oEvent.type == "keypress") ? oEvent.keyCode : 0;
        }
        if (oEvent.keyCode == 13) {
            var rds = $(this).val();
            var key;
            $(".dartbasis").each(function () {
                if ($(this).text() == rds) {
                    key = 1;
                }
            });
            if($(".dispBasis").children().length != 0){
                $('input, textarea').placeholder();
            }
            if (key == 1) {
                layer.alert("不能输入相同的定密依据")
                $(".ipvabasis").val("")
                return;
            }
            var parten1 = /\[公开\]*/;
            var parten2 = /\[内部\]*/;
            var parten3 = /\[秘密\]*/;
            var parten4 = /\[机密\]*/;
            var parten5 = /\[绝密\]*/;

            if (parten1.test(rds) || parten2.test(rds) || parten3.test(rds) || parten4.test(rds) || parten5.test(rds)) {
                var sortable = $(".ui-sortable");
                $(".dispBasis").append('<span  class="Basis"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div  class="dartbasis"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + rds + '</div><img onclick=" removeBasis(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
            } else {
                layer.alert("请按‘[’+秘密等级+‘]’+依据内容")
                //$("##unitNo option:selected").attr("value","")
            }
            $(this).val("");

        }
    });

}

//选择定密依据
function Basischoose() {

    //用于标识是否有相同的

    var rds = ''
    var key
    var parten = /^\s*$/;
    var Minors = new Array();
    $("#basisContent :checkbox:checked").each(function () {
        rds = $(this).parent().text();
        Minors.push(rds)

    })

    for (var i = 0; i < Minors.length; i++) {
        key = 0
        $(".dartbasis").each(function () {

            if ($(this).text() == Minors[i]) {
                key = 1
            }
        })
        if (key != 1) {
            if (parten.test(rds)) {
            } else {
                var sortable = $(".ui-sortable");
                $(".dispBasis").append('<span  class="Basis"  style=" background:#CCC;   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:1em; "><div  class="dartbasis"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + Minors[i] + '</div><img onclick=" removeBasis(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");               //$("##unitNo option:selected").attr("value","")
            }
        }
    }
}

function removeBasis(t) {
    $(t).parent().remove();
}


function Minorshow() {
    layer.open({
        type: 1,
        title: "辅助定密单位",
        area: ['300px', '300px'],
        btn: ["确定", "取消"],
        yes: function (index, layero) {

            Minorchoose()

            layer.close(index);
        },
        content: $("#MinorUnits") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
    });
}

function dispBasisshow() {
    bt2();
    layer.open({
        type: 1,
        title: "定密依据内容",
        area: ['300px', '300px'],
        btn: ["确定", "取消"],
        yes: function (index, layero) {

            Basischoose()

            layer.close(index);
        },
        content: $("#basisContents") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
    });
}

//查询文件默认权限策略
function queryPermissionPolicy() {

    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "fileState": 1,
            "fileLevel": $("#ss option:selected").val(),
            "roleType": 4
        }),
        url: account + "/dcms/api/v1/securePolicy/queryPermissionPolicy",
        dataType: "json",
        cache: false,
        crossDomain: false,
        success: function (data) {
            //alert(JSON.stringify())
            var permissions = data.permission
            if (permissions.contentRead == 1) {
                $(".permission input[name='contentRead']").attr("checked", true)
            } else {
                $(".permission input[name='contentRead']").attr("checked", false)
            }
            if (permissions.contentPrint == 1) {
                $(".permission input[name='contentPrint']").attr("checked", true)
            } else {
                $(".permission input[name='contentPrint']").attr("checked", false)
            }
            if (permissions.contentPrintHideWater == 1) {
                $(".permission input[name='contentPrintHideWater']").attr("checked", true)
            } else {
                $(".permission input[name='contentPrintHideWater']").attr("checked", false)
            }
            if (permissions.contentModify == 1) {
                $(".permission input[name='contentModify']").attr("checked", true)
            } else {
                $(".permission input[name='contentModify']").attr("checked", false)
            }
            if (permissions.contentScreenShot == 1) {
                $(".permission input[name='contentScreenShot']").attr("checked", true)
            } else {
                $(".permission input[name='contentScreenShot']").attr("checked", false)
            }
            if (permissions.contentCopy == 1) {
                $(".permission input[name='contentCopy']").attr("checked", true)
            } else {
                $(".permission input[name='contentCopy']").attr("checked", false)
            }
            if (permissions.fileCopy == 1) {
                $(".permission input[name='fileCopy']").attr("checked", true)
            } else {
                $(".permission input[name='fileCopy']").attr("checked", false)
            }
            if (permissions.fileSaveCopy == 1) {
                $(".permission input[name='fileSaveCopy']").attr("checked", true)
            } else {
                $(".permission input[name='fileSaveCopy']").attr("checked", false)
            }

        }
    });

}


function PermissionPolicychoose() {
    if (uploadType == 1) {
        queryPermissionPolicy()
    }
  //默认保密期限
    queryValidPeriod($("#ss option:selected").val());
}

//默认保密期限
function queryValidPeriod(fileLevel){
	//获取默认 保密期限
	if($("#durationType").find("option").eq(2).is(":selected")){
		
		$.ajax({
			type: "post",
			url: account + "/dcms/api/v1/securePolicy/queryValidPeriod",
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			crossDomain: false,
			data: JSON.stringify({
				"token": token,
				"fileLevel": $("#ss option:selected").val()
			}),
			success: function (result) {
				if(result.validPeriod=="000000"){
            		$("#time").val("0")
            		$("#time2").find("option").eq(0).attr("selected", true)
            	}
				var time = result.validPeriod .split("");
				if (time[0] + time[1] != "00") {
					if (time[0] == "0") {
						$("#time").val(time[1])
					} else {
						$("#time").val(time[0] + time[1])
					}
					$("#time2").find("option").eq(0).attr("selected", true)
				}
				if (time[2] + time[3] != "00") {
					if (time[2] == "0") {
						$("#time").val(time[3])
					} else {
						$("#time").val(time[2] + time[3])
					}
					$("#time2").find("option").eq(1).attr("selected", true)
				}
				if (time[4] + time[5] != "00") {
					if (time[4] == "0") {
						$("#time").val(time[5])
					} else {
						$("#time").val(time[4] + time[5])
					}
					$("#time2").find("option").eq(2).attr("selected", true)
				}
			},
			error: function (result) {
			}
		});
	}
}
//文件等级下拉框
function loadPolicy() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "policyType": 5

        }),
        url: account + "/dcms/api/v1/client/loadPolicy",
        dataType: "json",
        cache: false,
        crossDomain: false,
        async: false,
        success: function (data) {
            var fileLevelAccessPolicy = data.fileLevelAccessPolicy;
            if (fileLevelAccessPolicy != null) {
                var length = fileLevelAccessPolicy.length;
                var fileLevel = '<select id="ss" name="fileLevel" class="form-control" onclick="PermissionPolicychoose()">'
                for (var i = 0; i < length; i++) {
                    fileLevel += '<option value="' + fileLevelAccessPolicy[i] + '">' + fileLevelname(fileLevelAccessPolicy[i]) + '</option>'

                }
                fileLevel += '</select>'
                $("#fileLevel").empty();
                $("#fileLevel").append(fileLevel);
            }


        }
    });

}

function fileLevelname(s) {
    var name;
    if (s == 0) {
        name = "公开"
    }
    if (s == 1) {
        name = "内部"
    }
    if (s == 2) {
        name = "秘密"
    }
    if (s == 3) {
        name = "机密"
    }
    if (s == 4) {
        name = "绝密"
    }
    return name
}











