/**
 * Created by liuxiaojun on 17/2/15.
 */
// if($(window).width() >= 1920){	
    // $("#box_tab1 .potions1").css("height","650px")
// }else if($(window).width() >= 1280 && $(window).width() <1920){
    // $("#box_tab1 .potions1").css("height","480px")
// }
var height=$(window).height()-$("#header").height()-$("#footer").height()-120;
 $("#box_tab1 .potions1").css("height",height)
function formatLaydate(elem) {
    var date = {
        elem: '#'+elem,
        format: 'YYYY/MM/DD hh:mm:ss',
        istime:true,
        istoday: true
    };
    laydate(date);
}
//获得年月日  得到日期oTime
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
$(function () {
    //初始化水印界面
    Watermark(1);
    
});
//水印配置
function Watermark(type) {

    //水印配置信息获取
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryWatermarkConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "type":type
        }),
        success: function (data) {
            data.onSuccess = function () {
            	$("#box_tab1 .plist").empty();
                var str = '<tr>' +
                '<td class="wcont" colspan="4" style="background:#efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">水印内容</h5></td>' +
                '</tr>' +
                	'<tr>' +
                    '<td>水印内容:</td>' +
                    '<td class="wcont" colspan="3"><input id="text" type="text" style="width:300px;" value="' + data.text + '"/><span>*</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>设置样式:</td>' +
                    '<td class="wcont" colspan="3">字体：<select id="fontName" style="width:112px;"><option value="微软雅黑">微软雅黑</option><option value="宋体">宋体</option><option value="新宋体">新宋体</option><option value="仿宋" selected>仿宋</option><option value="黑体" selected>黑体</option><option value="楷体">楷体</option></select>&nbsp大小：<input id="fontSize" max="500" min="1" type="number" style="width:112px;" value="' + data.fontSize + '" onkeyup="control(event,this)"/><span>px*(输入值在[1-500])</span>&nbsp</td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td class="wcont" colspan="4" style="background:#efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">参数设置</h5></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>设置水印倾斜度:</td>' +
                    '<td  colspan="3">'+
                    '<input id="fontEscapement" type="text" style="width:300px;" value="' + data.fontEscapement + '"/>度<span>*</span>'+
                    '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>设置水印显示透明度:</td>' +
                    '<td class="walpha" colspan="3"><input type="range" id="trackBar2" min="1" id="colorA" max="255" step="1" value="' + data.colorA + '" /><span id="cont" style="vertical-align:top;margin-left:5px;padding:5px;color:black;">' +
                    data.colorA +
                    '</span><span style="vertical-align: top;">*(输入值变化在1-255范围内)</span></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>水印颜色:</td>' +
                    '<td class="walpha" colspan="3"><input id="colorRGB" type="text" style="width:300px;display:none;" value="' + data.colorRGB + '" onclick="changeColor()"/></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>设置水印位置:</td>' +
                    '<td class="" colspan="3">';
                if (data.markLocal == 0) {
                    str += '<select id="markLocal"><option value="0" selected>浮于内容上方</option><option value="1">衬于内容下方</option></select><span>*</span>';
                }
                if (data.markLocal == 1) {
                    str += '<select id="markLocal"><option value="0">浮于内容上方</option><option value="1" selected>衬于内容下方</option></select><span>*</span>';
                }
                str += '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>行数/列数:</td>' +
                    '<td class="wsize" colspan="3">行：<input id="markRows" type="number" autocomplete="6 " value="' + data.markRows + '"/>列：<input id="markCols" type="number" autocomplete="6 " value="' + data.markCols + '"/><span>*</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>设置页面的边距(cm):</td>' +
                    '<td class="wsize" colspan="3">上：<input id="top" type="number" autocomplete="6 " value="' + data.top + '"/>下：<input id="bottom" type="number" autocomplete="6 " value="' + data.bottom + '"/>左：<input id="left" type="number" autocomplete="6 " value="' + data.left + '"/>右：<input id="right" type="number" autocomplete="6 " value="' + data.right + '"/><span>*</span></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td class="wcont" colspan="4" style="background:#efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">显示位置</h5></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>计算机名称:</td>' +
                    '<td  style="text-align:left;">';
                if (data.compNameType == 0) {
                    str += '<select id="compNameType" style="width:300px;"><option value="0" selected>不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.compNameType == 1) {
                	str += '<select id="compNameType" class="walign" style="width:300px;"><option value="0">不选</option><option value="1" selected>页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.compNameType == 2) {
                    str += '<select id="compNameType" class="walign" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2" selected>页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.compNameType == 4) {
                	str += '<select id="compNameType" class="walign" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4" selected>左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.compNameType == 8) {
                    str += '<select id="compNameType" class="walign" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8" selected>右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.compNameType == 16) {
                	str += '<select id="compNameType" class="walign" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16" selected>跟随水印</option></select>';
                }
                str += '</td>' +
                    /*'</tr>' +
                    
                    '<tr>' +*/
                    '<td style="width:20%;background:#EFEFEF;font-weight:bold;text-align:left;vertical-align:middle;padding-left:20px;">Ip地址:</td>' +
                    '<td>';
                if (data.hostAddrType == 0) {
                    str += '<select id="hostAddrType" style="width:300px;"><option value="0" selected>不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.hostAddrType == 1) {
                	str += '<select id="hostAddrType" style="width:300px;"><option value="0">不选</option><option value="1" selected>页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.hostAddrType == 2) {
                    str += '<select id="hostAddrType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2" selected>页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.hostAddrType == 4) {
                	str += '<select id="hostAddrType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4" selected>左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.hostAddrType == 8) {
                    str += '<select id="hostAddrType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8" selected>右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.hostAddrType == 16) {
                	str += '<select id="hostAddrType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16" selected>跟随水印</option></select>';
                }
                str += '</td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>用户名称:</td>' ;
                if(type==2){
                	str+='<td  style="text-align:left;">';
                }else{
                	str+='<td style="text-align:left;">';
                }
                    
                if (data.userNameType == 0) {
                    str += '<select id="userNameType" style="width:300px;"><option value="0" selected>不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.userNameType == 1) {
                	str += '<select id="userNameType" style="width:300px;"><option value="0">不选</option><option value="1" selected>页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.userNameType == 2) {
                    str += '<select id="userNameType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2" selected>页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.userNameType == 4) {
                	str += '<select id="userNameType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4" selected>左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.userNameType == 8) {
                    str += '<select id="userNameType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8" selected>右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.userNameType == 16) {
                	str += '<select id="userNameType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16" selected>跟随水印</option></select>';
                }
                str += '</td>' ;
                    /*'</tr>' +
                    
                    '<tr>' +*/
                if(type==1){
                	str+='<td style="width:20%;background:#EFEFEF;font-weight:bold;text-align:left;vertical-align:middle;padding-left:20px;">打印时间:</td>' +
                    '<td>';
                if (data.printTimeType == 0) {
                    str += '<select id="printTimeType" style="width:300px;"><option value="0" selected>不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.printTimeType == 1) {
                	str += '<select id="printTimeType" style="width:300px;"><option value="0">不选</option><option value="1" selected>页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.printTimeType == 2) {
                    str += '<select id="printTimeType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2" selected>页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.printTimeType == 4) {
                	str += '<select id="printTimeType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4" selected>左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.printTimeType == 8) {
                    str += '<select id="printTimeType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8" selected>右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.printTimeType == 16) {
                	str += '<select id="printTimeType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16" selected>跟随水印</option></select>';
                }
                str += '</td>';
                }
                if(type==2){
                	str+='<td style="width:20%;background:#EFEFEF;"></td><td></td>';
                }
                    
                    str+='</tr>';
                    
                   /* '<tr>' +
                    '<td>自定义信息:</td>' +
                    '<td class="" colspan="3">';
                if (data.custInfoType == 0) {
                    str += '<select id="custInfoType" style="width:300px;"><option value="0" selected>不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.custInfoType == 1) {
                	str += '<select id="custInfoType" style="width:300px;"><option value="0">不选</option><option value="1" selected>页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.custInfoType == 2) {
                    str += '<select id="custInfoType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2" selected>页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.custInfoType == 4) {
                	str += '<select id="custInfoType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4" selected>左侧</option><option value="8">右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.custInfoType == 8) {
                    str += '<select id="custInfoType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8" selected>右侧</option><option value="16">跟随水印</option></select>';
                }
                if (data.custInfoType == 16) {
                	str += '<select id="custInfoType" style="width:300px;"><option value="0">不选</option><option value="1">页眉</option><option value="2">页脚</option><option value="4">左侧</option><option value="8">右侧</option><option value="16" selected>跟随水印</option></select>';
                }
                str += '</td>' +
                    '</tr>';*/
                    
                if(type == 1) {
                	str+='<tr>' +
                    '<td class="wcont" colspan="4" style="background:#efefef;padding-left:2px;"><h5 style="font-weight:bold;font-size:15px;">对齐方式</h5></td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>页眉:</td>' +
                    '<td style="text-align:left;">';
                if (data.markTop == 0) {
                    str += '<select id="markTop" style="width:300px;"><option value="0" selected>左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markTop == 1) {
                	str += '<select id="markTop" style="width:300px;"><option value="0">左对齐</option><option value="1" selected>居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markTop == 2) {
                	str += '<select id="markTop" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2" selected>右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markTop == 3) {
                	str += '<select id="markTop" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3" selected>不显示</option></select>';
                }
                str += '</td>' +
                    /*'</tr>' +

                    '<tr>' +*/
                    '<td style="width:20%;background:#EFEFEF;font-weight:bold;text-align:left;vertical-align:middle;padding-left:20px;">页脚:</td>' +
                    '<td>';
                if (data.markBottom == 0) {
                    str += '<select id="markBottom" style="width:300px;"><option value="0" selected>左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markBottom == 1) {
                	str += '<select id="markBottom" style="width:300px;"><option value="0">左对齐</option><option value="1" selected>居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markBottom == 2) {
                	str += '<select id="markBottom" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2" selected>右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markBottom == 3) {
                	str += '<select id="markBottom" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3" selected>不显示</option></select>';
                }
                str += '</td>' +
                    '</tr>' +
                    
                    '<tr>' +
                    '<td>左侧:</td>' +
                    '<td style="text-align:left;">';
                if (data.markLeft == 0) {
                    str += '<select id="markLeft" style="width:300px;"><option value="0" selected>左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markLeft == 1) {
                	str += '<select id="markLeft" style="width:300px;"><option value="0">左对齐</option><option value="1" selected>居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markLeft == 2) {
                	str += '<select id="markLeft" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2" selected>右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markLeft == 3) {
                	str += '<select id="markLeft" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3" selected>不显示</option></select>';
                }
                str += '</td>' +
                    /*'</tr>' +
                    
                    '<tr>' +*/
                    '<td style="width:20%;background:#EFEFEF;font-weight:bold;text-align:left;vertical-align:middle;padding-left:20px;">右侧:</td>' +
                    '<td>';
                if (data.markRight == 0) {
                    str += '<select id="markRight" style="width:300px;"><option value="0" selected>左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markRight == 1) {
                	str += '<select id="markRight" style="width:300px;"><option value="0">左对齐</option><option value="1" selected>居中</option><option value="2">右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markRight == 2) {
                	str += '<select id="markRight" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2" selected>右对齐</option><option value="3">不显示</option></select>';
                }
                if (data.markRight == 3) {
                	str += '<select id="markRight" style="width:300px;"><option value="0">左对齐</option><option value="1">居中</option><option value="2">右对齐</option><option value="3" selected>不显示</option></select>';
                }
                str += '</td>' +
                    '</tr>';
                }
                if(type==2){
                	str += '<tr>' +
                    '<td>文件级别设置：</td>' +
                    '<td colspan="3">';
                for (var i = 0; i < data.showWatermark.length; i++) {
                    var filelevel;
                    if (data.showWatermark[i].fileLevel == 0) {
                        filelevel = "公开";
                    }
                    if (data.showWatermark[i].fileLevel == 1) {
                        filelevel = "内部";
                    }
                    if (data.showWatermark[i].fileLevel == 2) {
                        filelevel = "秘密";
                    }
                    if (data.showWatermark[i].fileLevel == 3) {
                        filelevel = "机密";
                    }
                    if (data.showWatermark[i].fileLevel == 4) {
                        filelevel = "绝密";
                    }
                    if (data.showWatermark[i].isDesensity == 0) {
                        str += filelevel + '<input name="showWatermark" style="vertical-align:middle;margin:0 10px 0 5px;" id="' + data.showWatermark[i].isDesensity + '" value=' + data.showWatermark[i].fileLevel + ' type="checkbox" onclick="checkedbox(this.name)"/>';
                    }
                    if (data.showWatermark[i].isDesensity == 1) {
                        str += filelevel + '<input name="showWatermark" style="vertical-align:middle;margin:0 10px 0 5px;" id="' + data.showWatermark[i].isDesensity + '" value=' + data.showWatermark[i].fileLevel + ' type="checkbox" checked onclick="checkedbox(this.name)"/>';
                    }
                }
                str += '</td>' +
                    '</tr>'
                }
                    
                    str+='<tr>' +
                    '<td colspan="4" style="text-align: center;background: #efefef;">' +
                    '<button class="btn btn-primary" onclick="waterUpdte('+type+')">保存</button>' +
                    '</td>' +
                    '</tr>';
                
            	$("#box_tab1 .plist").append(str);
                $("#trackBar2").on("change", function () {
                    $("#cont").text("");
                    $("#cont").text(this.value);

                });
                
                $("#fontName option[value='" + data.fontName + "']").attr("selected", true);
                
                $('#colorRGB').jPicker(
                		{},
                		function(color, context)
                        {
                			changeColor();
                        },
                        {},
                        function(color, context)
                        {
                        	changeColor();
                        }
                        
                );
            }
            processResponse(data, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    
    
};

//隐藏字体颜色
function changeColor(){
	var colorRGB = $("#colorRGB").val();
    $("#colorRGB").css("color","#"+colorRGB);
}

//获取系统字体
function setFont()
{
var cnt = dlgHelper.fonts.count
alert(cnt)
for (var i = 1; i < cnt; i++)
{
      the_font.options[the_font.options.length] = new Option(dlgHelper.fonts(i),dlgHelper.fonts(i));
}	
}



//定时配置
function Timer() {
	
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryTimerConfig",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
			
            $("#box_tab3 .plist").empty()
            result.onSuccess = function () {
                var str1 = '<tr>' +
                    '<td>邮件告警定时配置起始时间:</td>' +
                    '<td class="mt"><input style="width:150px;" type="text" class="laydate-icon" id="MailAlarmStartTime" value="' + getMyDate(result.timerMailAlarmStartTime) + '" /></span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>邮件告警定时配置周期:</td>' +
                    '<td class="mp"><input style="width:100px;" type="number" value="' + parseInt(result.timerMailAlarmPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>数据库备份定时配置起始时间:</td>' +
                    '<td class="bt"><input style="width:150px;" type="text" class="laydate-icon" id="BackupStartTime"  value="' + getMyDate(result.timerBackupStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>数据库备份定时配置周期:</td>' +
                    '<td class="bp"><input style="width:100px;" type="number" value="' + parseInt(result.timerBackupPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr style="display:none;">' +
                    '<td>日志上传定时配置起始时间:</td>' +
                    '<td><input style="width:150px;" type="text" class="laydate-icon" id="ClientLogStartTime" value="' + getMyDate(result.timerClientLogStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr style="display:none;">' +
                    '<td>日志上传定时配置周期:</td>' +
                    '<td><input style="width:100px;" type="number" value="' + parseInt(result.imerClientLogPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>AD同步定时配置起始时间:</td>' +
                    '<td class="st"><input style="width:150px;" type="text" class="laydate-icon" id="AdSyncStartTime" value="' + getMyDate(result.timerAdSyncStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>AD同步定时配置周期:</td>' +
                    '<td class="sp"><input style="width:100px;" type="number" value="' + parseInt(result.timerAdSyncPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>文件自动解密定时配置起始时间:</td>' +
                    '<td class="at"><input style="width:150px;" type="text" class="laydate-icon" id="AutoDecodeStartTime" value="' + getMyDate(result.timerAutoDecodeStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>文件自动解密定时配置周期:</td>' +
                    '<td class="ap"><input style="width:100px;" type="number" value="' + parseInt(result.timerAutoDecodePeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr style="display:none;">' +
                    '<td>密级文件统计定时配置起始时间:</td>' +
                    '<td><input style="width:150px;" type="text" class="laydate-icon" id="FileStatisticStartTime" value="' + getMyDate(result.timerFileStatisticStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr style=display:none;>' +
                    '<td>密级文件统计定时配置周期:</td>' +
                    '<td><input style="width:100px;" type="number" value="' + parseInt(result.timerFileStatisticPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>取消审批代理人权限定时配置起始时间:</td>' +
                    '<td class="ct"><input style="width:150px;" type="text" class="laydate-icon" id="CancelApproverStartTime" value="' + getMyDate(result.timerCancelApproverStartTime) + '"/></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>取消审批代理人权限定时配置周期:</td>' +
                    '<td class="cp"><input style="width:100px;" type="number" value="' + parseInt(result.timerCancelApproverPeriod / 86400000) + '" onkeyup="control(event,this)"/><span>天</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;background: #efefef;">' +
                    '<button onclick="saveTimer()" class="btn btn-primary">保存</button>' +
                    '</td>' +
                    '</tr>';
                $("#box_tab3 .plist").append(str1);
                $("#MailAlarmStartTime").unbind("click").click(function(){
					 formatLaydate('MailAlarmStartTime');
				})
                  $("#BackupStartTime").unbind("click").click(function(){
					 formatLaydate('BackupStartTime');
				})
				  $("#ClientLogStartTime").unbind("click").click(function(){
					 formatLaydate('ClientLogStartTime');
				})
				   $("#AdSyncStartTime").unbind("click").click(function(){
					formatLaydate('AdSyncStartTime');
				})
				   $("#AutoDecodeStartTime").unbind("click").click(function(){
					 formatLaydate('AutoDecodeStartTime');
				})
                 $("#CancelApproverStartTime").unbind("click").click(function(){
					 formatLaydate('CancelApproverStartTime');
				})
                  
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//水印配置信息修改
function waterUpdte(type) {
	$("#text").next().text('*');
	if($.trim($("#text").val()) == ''){
		$("#text").next().append('水印内容必填');
		return;
	}
	
	$("#fontEscapement").next().text('*');
	if($.trim($("#fontEscapement").val()) == ''){
		$("#fontEscapement").next().append('水印倾斜度必填');
		return;
	}
	$("#markCols").next().text('*');
	if($.trim($("#markRows").val()) == ''||$.trim($("#markCols").val()) == ''){
		$("#markCols").next().append('行/列必填');
		return;
	}
	
	$("#right").next().text('*');
	if($.trim($("#left").val()) == ''||$.trim($("#top").val()) == ''||$.trim($("#right").val()) == ''||$.trim($("#bottom").val()) == ''){
		$("#right").next().append('水印倾斜度必填');
		return;
	}
	var parten = /^\#.*$/;
	var colorRGB = $("#colorRGB").val();
	if(!parten.test(colorRGB)) {
		colorRGB = '#' + colorRGB;
	}
	
	var desensityList = new Array();
    $("input[name='showWatermark']").each(function () {
        var isDesensity = 0;
        if ($(this).is(':checked')) {
            isDesensity = 1;
        }
        var fileLevel = $(this).val();
        var data = {
            "fileLevel": fileLevel,
            "isDesensity": isDesensity
        }
        desensityList.push(data)
    })
	
    layer.alert("水印配置设置成功，是否保存设置？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            
            $.ajax({
                url: host + "/dcms/api/v1/systemConfig/updateWatermarkConfig",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "type":type,
                    "left": $("#left").val(),
                    "top": $("#top").val(),
                    "right": $("#right").val(),
                    "bottom": $("#bottom").val(),
                    "fontSize": $("#fontSize").val(),
                    "colorA": $("#cont").text(),
                    "colorRGB": colorRGB,
                    "fontEscapement": $("#fontEscapement").val(),
                    "markLocal": $("#markLocal option:selected").val(),
                    "markRows": $("#markRows").val(),
                    "markCols": $("#markCols").val(),
                    "markTop": $("#markTop option:selected").val(),
                    "markLeft": $("#markLeft option:selected").val(),
                    "markRight": $("#markRight option:selected").val(),
                    "markBottom": $("#markBottom option:selected").val(),
                    "compNameType": $("#compNameType option:selected").val(),
                    "userNameType": $("#userNameType option:selected").val(),
                    "hostAddrType": $("#hostAddrType option:selected").val(),
                    "custInfoType": $("#custInfoType option:selected").val(),
                    "printTimeType": $("#printTimeType option:selected").val(),
                    "fontName": $("#fontName option:selected").val(),
                    "text": $("#text").val(),
                    "custInfo": $("#custInfo").val(),
                    "showWatermark": desensityList
                }),
                success: function (data) {
                    data.onSuccess = function () {
                        $("#box_tab1 .plist").empty();
                        Watermark(type);
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
//定时修改4
function saveTimer() {
    layer.alert("定时任务设置成功，是否确定保存？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var timerMailAlarmStartTime = new Date($(".mt input").val()).getTime();
            var timerMailAlarmPeriod = $(".mp input").val() * 86400000;
            var timerBackupStartTime = new Date($(".bt input").val()).getTime();
            var timerBackupPeriod = $(".bp input").val() * 86400000;
            var timerAdSyncStartTime = new Date($(".st input").val()).getTime();
            var timerAdSyncPeriod = $(".sp input").val() * 86400000;
            var timerAutoDecodeStartTime = new Date($(".at input").val()).getTime();
            var timerAutoDecodePeriod = $(".ap input").val() * 86400000;
            var timerCancelApproverStartTime = new Date($(".ct input").val()).getTime();
            var timerCancelApproverPeriod = $(".cp input").val() * 86400000;
            $.ajax({
                url: host + "/dcms/api/v1/systemConfig/updateTimerConfig",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "timerMailAlarmStartTime": timerMailAlarmStartTime,
                    "timerMailAlarmPeriod": timerMailAlarmPeriod,
                    "timerBackupStartTime": timerBackupStartTime,
                    "timerBackupPeriod": timerBackupPeriod,
                    "timerAdSyncStartTime": timerAdSyncStartTime,
                    "timerAdSyncPeriod": timerAdSyncPeriod,
                    "timerAutoDecodeStartTime": timerAutoDecodeStartTime,
                    "timerAutoDecodePeriod": timerAutoDecodePeriod,
                    "timerCancelApproverStartTime": timerCancelApproverStartTime,
                    "timerCancelApproverPeriod": timerCancelApproverPeriod
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $("#box_tab3 .plist").empty();
                        Timer();
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });

}
//翻页调用
function PageCallback(index) {
    InitTable(index.getCurrent() - 1);
}

//判断值是否未定义
function isUndefined(values) {
    if (typeof(values) == 'undefined') {
        return "";
    }
    return values;
}

//可信应用软件列表获取
function InitTable(index) {
    var pageSize = 10;
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryTrustedAppList",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": pageSize
        }),
        success: function (result) {
            result.onSuccess = function () {
                $("#box_tab2 .plist tr:gt(0)").remove();
                var datas = result.trustedAppList;
                for (var i = 0; i < datas.length; i++) {
                    var str = '<tr>' +
                        '<td>' + parseInt(index * pageSize + i + 1) + '</td>' +
                        '<td style="display: none;">' + datas[i].trustedAppId + '</td>' +
                        '<td><input type="text" class="text-center form-control" style="padding:2px' +
                        ' 12px;"' +
                        ' value="' + datas[i].description + '"/ ></td>' +
                        '<td><input type="text" class="text-center form-control" style="padding:2px' +
                        ' 12px;"' +
                        ' value="' + datas[i].processName + '"/></td>' +
                        '<td><input type="text" class="text-center form-control" style="padding:2px' +
                        ' 12px;"' +
                        ' value="' + datas[i].extensionName + '"/></td>' +
                        '<td><a href="javaScript:;"><i class="fa fa-save"' +
                        ' onclick="updateTrustedApp(this,' + index + ')"></i></a><span' +
                        ' style="padding:0 10px;"></span><a' +
                        ' href="javaScript:;;"><i' +
                        ' class="fa' +
                        ' fa-trash-o" onclick="delTr(this,' + index + ')"></i></a></td>' +
                        '</tr>';
                    $("#box_tab2 .plist").append(str);
                }
                $(".add-software").unbind("click").click(function () {
                    $("#data1").val("");
                    $("#data2").val("");
                    $("#data3").val("");
                    layer.open({
                        type: 1,
                        title: '添加可信应用软件',
                        shadeClose: true,
                        shade: 0.3,
                        area: ['500px', '265px'],
                        btn: ['确定', '取消'],
                        yes: function (index, layero) {
                            $(".data1").text("");
                            $(".data2").text("");
                            $(".data3").text("")
                            var keys = 0;
                            var processName = $("#data1").val();
                            if (processName == null || processName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".data1").text("进程名不能为空！")
                                keys = 1;
                            }
                            var description = $("#data2").val();
                            if (description == null || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".data2").text("应用程序描述不能为空！")
                                keys = 1;
                            }
                            var extensionName = $("#data3").val();
                            if (extensionName == null || extensionName.replace(/(^s*)|(s*$)/g, "").length == 0) {
                                $(".data3").text("关联扩展名不能为空！")
                                keys = 1;
                            }
                            if (keys == 0) {
                                addTr2();
                                layer.close(index);
                            }
                        },
                        content: $('#contclient')
                    });
                });
                if (index == 0) {
                    $('.M-box2').pagination({
                        coping: true,
                        pageCount: result.totalPages,
                        callback: PageCallback
                    });
                }
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

function addTr2() {
    var processName = $("#data1").val();
    var description = $("#data2").val();
    var extensionName = $("#data3").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/addTrustedApp",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "processName": processName,
            "extensionName": extensionName,
            "description": description
        }),
        success: function (result) {
            result.onSuccess = function () {
                InitTable(0);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function updateTrustedApp(u, index) {
    layer.alert("是否确认修改当前可信应用软件配置？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var trustedAppId = $(u).parent().parent().parent().find("td").eq(1).text();
            var processName = $(u).parent().parent().parent().find("td").eq(3).find("input").val();
            var extensionName = $(u).parent().parent().parent().find("td").eq(4).find("input").val();
            var description = $(u).parent().parent().parent().find("td").eq(2).find("input").val();
            $.ajax({
                url: host + "/dcms/api/v1/systemConfig/updateTrustedApp",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "processName": processName,
                    "extensionName": extensionName,
                    "description": description,
                    "trustedAppId": trustedAppId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(ckb).parent().parent().parent().remove();
                        InitTable(index);
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });

}
function delTr(ckb, index) {
    layer.alert("是否删除当前可信应用软件？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index1, layero) {
            var trustedAppId = $(ckb).parent().parent().parent().find("td").eq(1).text();
            $.ajax({
                url: host + "/dcms/api/v1/systemConfig/deleteTrustedAppById",
                type: "post",
                async: false,
                crossDomain: false,
                data: JSON.stringify({
                    "token": token,
                    "trustedAppId": trustedAppId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $(ckb).parent().parent().parent().remove();
                        InitTable(index);
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index1);
        }
    });
}
//脱敏配置
function queryDesensity() {
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/queryDesensity",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            $(".queryDesensity").empty()
            result.onSuccess = function () {
                var str = '<tr>' +
                    '<td>文件脱敏级别设置：</td>' +
                    '<td>';
                for (var i = 0; i < result.desensityList.length; i++) {
                    var filelevel;
                    if (result.desensityList[i].fileLevel == 0) {
                        filelevel = "公开";
                    }
                    if (result.desensityList[i].fileLevel == 1) {
                        filelevel = "内部";
                    }
                    if (result.desensityList[i].fileLevel == 2) {
                        filelevel = "秘密";
                    }
                    if (result.desensityList[i].fileLevel == 3) {
                        filelevel = "机密";
                    }
                    if (result.desensityList[i].fileLevel == 4) {
                        filelevel = "绝密";
                    }
                    if (result.desensityList[i].isDesensity == 0) {
                        str += filelevel + '<input name="isDesensity" style="vertical-align:middle;margin:0 10px 0 5px;" id="' + result.desensityList[i].isDesensity + '" value=' + result.desensityList[i].fileLevel + ' type="checkbox" onclick="checkedbox(this.name)"/>';
                    }
                    if (result.desensityList[i].isDesensity == 1) {
                        str += filelevel + '<input name="isDesensity" style="vertical-align:middle;margin:0 10px 0 5px;" id="' + result.desensityList[i].isDesensity + '" value=' + result.desensityList[i].fileLevel + ' type="checkbox" checked onclick="checkedbox(this.name)"/>';
                    }
                }
                str += '</td>' +
                    '</tr>' +
                        //'<tr>' +
                        //'<td>模块设置：</td>'+
                        //'<td>文本台账<input type="checkbox"/>流程审核日志审计<input type="checkbox"/></td>' +
                        //'</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="text-align: center;background: #efefef">' +
                    '<button class="btn btn-primary" onclick="savedesensityList()">保存</button>' +
                    '</td>' +
                    '</tr>';
                $(".queryDesensity").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
function checkedbox(sname) {
	
    $("input[name="+sname+"]").each(function () {
        if ($(this).val() == 0 && $(this).is(':checked')) {
            for (var i = 0; i < $("input[name="+sname+"]").length; i++) {
                $("input[name="+sname+"]")[i].checked = "checked";
            }
        }
        if ($(this).val() == 1 && $(this).is(':checked')) {
            $("input[name="+sname+"]")[2].checked = "checked";
            $("input[name="+sname+"]")[3].checked = "checked";
            $("input[name="+sname+"]")[4].checked = "checked";
        }
        if ($(this).val() == 2 && $(this).is(':checked')) {
            $("input[name="+sname+"]")[3].checked = "checked";
            $("input[name="+sname+"]")[4].checked = "checked";
        }
        if ($(this).val() == 3 && $(this).is(':checked')) {
            $("input[name="+sname+"]")[4].checked = "checked";
        }
    })

}
function savedesensityList() {
    var desensityList = new Array();
    $("input[name='isDesensity']").each(function () {
        var isDesensity = 0;
        if ($(this).is(':checked')) {
            isDesensity = 1;
        }
        var fileLevel = $(this).val();
        var data = {
            "fileLevel": fileLevel,
            "isDesensity": isDesensity
        }
        desensityList.push(data)
    })
    $.ajax({
        url: host + "/dcms/api/v1/systemConfig/desensitization",
        type: "post",
        async: false,
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "desensityList": desensityList
        }),
        success: function (result) {
            result.onSuccess = function () {
                queryDesensity()
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}
//
function queryLogArchiveKeepTime() {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/queryLogArchiveKeepTime",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            $("#box_tab6 .queryLogArchiveKeepTime").empty();
            result.onSuccess = function () {
                var str = '<tr>' +
                    '<td>是否开启自动归档:</td>' +
                    '<td>';
                if (result.autoLogArchiveEnable == 0) {
                    str += '<input   type="radio" name="yes" value="1"/>是&nbsp;&nbsp;<input  type="radio" name="yes" value="0" checked/>否';
                }else if (result.autoLogArchiveEnable == 1) {
                    str += '<input  type="radio" name="yes" value="1" checked/>是&nbsp;&nbsp;<input  type="radio" name="yes" value="0"/>否';
                }
                str += '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>一般日志保留时间:</td>' +
                    '<td><input type="text" class="riskLevel1" value="' + result.riskLevel1 + '"><span>月</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>重要日志保留时间:</td>' +
                    '<td><input type="text" class="riskLevel2" value="' + result.riskLevel2 + '"><span>月</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>严重日志保留时间:</td>' +
                    '<td><input type="text" class="riskLevel3" value="' + result.riskLevel3 + '"><span>月</span></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="background: #efefef;text-align: center;"><button onclick="updateLogArchiveKeepTime()" class="btn btn-primary">保存</button></td>' +
                    '</tr>';
                $("#box_tab6 .queryLogArchiveKeepTime").append(str);
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
    queryDesensity();
}
//修改
function updateLogArchiveKeepTime(){
    var  riskLevel1=$(".riskLevel1").val();
    var  riskLevel2=$(".riskLevel2").val();
    var  riskLevel3=$(".riskLevel3").val();
    var autoLogArchiveEnable = $("input[name='yes']:checked").val();
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/updateLogArchiveKeepTime",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "riskLevel1": riskLevel1,
            "riskLevel2": riskLevel2,
            "riskLevel3": riskLevel3,
            "autoLogArchiveEnable":autoLogArchiveEnable
        }),
        success: function (result) {
            result.onSuccess = function () {
                queryLogArchiveKeepTime();
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}

function queryClassifiedWhiteList() {

    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/queryClassifiedWhiteList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token
        }),
        success: function (result) {
            $("#box_tab7 .queryClassifiedWhiteList").empty();
            result.onSuccess = function () {
                var str = '<tr>' +
                    '<td>标密白名单（路径以|分割）:</td>' +
                    '<td>';
                str += '<textarea class="classifiedWhiteList" name="a" style="width:800px;height:200px;">' + result.classifiedWhiteList + '</textarea>';
                str += '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td colspan="2" style="background: #efefef;text-align: center;"><button onclick="updateClassifiedWhiteList()" class="btn btn-primary">保存</button></td>' +
                    '</tr>';
                $("#box_tab7 .queryClassifiedWhiteList").append(str);
            };
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

function updateClassifiedWhiteList(){
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/systemConfig/updateClassifiedWhiteList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "classifiedWhiteList": $(".classifiedWhiteList").val(),
        }),
        success: function (result) {
            result.onSuccess = function () {
                queryLogArchiveKeepTime();
            };
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    })
}