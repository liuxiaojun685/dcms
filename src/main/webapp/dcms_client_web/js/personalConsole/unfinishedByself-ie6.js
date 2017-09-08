/**
 * 我的待办
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-2-27
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
//var host="http://47.93.78.186:8080";
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
//让键盘只能输入数字
function onlyNumber(event){
    var keyCode = event.keyCode;
    if((keyCode<48&&keyCode>8) ||(keyCode>57&&keyCode<96) ||keyCode<8||keyCode>105){
        window.event.returnValue= false;
    }
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
$(function(){
    myapply(0,1);
    $("#docNumber").keydown(onlyNumber);
    $("#duplicationAmount").keydown(onlyNumber);
});
function myfinished(index,types){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/queryFinished",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "pageNumber":index,
            "pageSize":10
        }),
        success:function(result){
            if(result.code==0){
                $(".finishList").empty();
                var str='';
                //拼接显示列表
                str=makeList(str,result,types);

                $(".finishList").append(str);
                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: index+1, //当前页数
                    totalPages: result.totalPages, //总页数
                    numberOfPages: 5,
                    itemTexts: function (type, page, current) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },
                    onPageClicked:function(event, originalEvent, type, page){
                        myfinished(page-1,types)
                    }}
                $('#myfinish').bootstrapPaginator(options);
                $('#myfinishTotal').text('共'+result.totalElements+'条');

            }
        }
    })
}

//获取我的申请
function myapply(index,types){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/queryMyRequest",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "pageNumber":index,
            "pageSize":10,
            "state":types
        }),
        success:function(result){
        	$('#apply').empty();
            if(result.code==0){
                $(".applyList").empty();
                var str='';
                //拼接显示列表
                str=makeList(str,result,types);

				$(".applyed").hide();
				$(".applyall").show();
                $(".applyList").append(str);
                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: index+1, //当前页数
                    totalPages: result.totalPages, //总页数
                    numberOfPages: 5,
                    itemTexts: function (type, page, current) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },
                    onPageClicked:function(event, originalEvent, type, page){
                        myapply(page-1,types)
                    }}
                $('#apply').bootstrapPaginator(options);
                $('#applyTotal').text('共'+result.totalElements+'条');

            }
        }
    })
}

//生成列表页
function makeList(str,result,type){
    var datas=result.workFlowList;
    if(datas.length == 0){
    	return '<tr><td colspan="7" style="text-align:center">没有相关数据</td></tr>';
    }
    for(var i=0;i<datas.length;i++){
        if(datas[i].applyFileLevel==0){
            datas[i].applyFileLevel="公开"
        }
        if(datas[i].applyFileLevel==1){
            datas[i].applyFileLevel="内部"
        }
        if(datas[i].applyFileLevel==2){
            datas[i].applyFileLevel="秘密"
        }
        if(datas[i].applyFileLevel==3){
            datas[i].applyFileLevel="机密"
        }
        if(datas[i].applyFileLevel==4){
            datas[i].applyFileLevel="绝密"
        }
        var num=parseInt(i)+1;
        str+='<tr>'+
            '<td>'+num+'</td>'+
            '<td>'+getWorkFlowType(datas[i].workFlowType)+'流程</td>'+
            '<td>'+datas[i].srcFile.fileName+'</td>'+
            '<td>'+datas[i].applyFileLevel+'</td>'+
            '<td>'+datas[i].createUser.name+'</td>'+
            '<td>'+getLocalTime(datas[i].createTime)+'</td>'+
            '<td><span style="cursor: pointer;" onclick="finishedInfo('+datas[i].workFlowId+','+type+')">详情</span>|<span style="cursor: pointer;" onclick="downUnfinish('+datas[i].workFlowId+')">下载</span><a href="" id="excelhref" class="hide"><span id="excel"></span></a></td>'+
            '</tr>';

    }
    return str;
}

//查看流程详情
function finishedInfo(workflowId,type){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/queryById",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "workFlowId":workflowId
        }),
        success:function(result){
            if(result.code==0){
                if(type == 2){
                    $(".finishall").hide();
                }
                if(type == 0 || type == 1){

                    $(".applyall").hide();
                }


                if(result.flowState==1){
                    result.flowState="已完成"
                }
                if(result.flowState==0){
                    result.flowState="未完成"
                }

                if(result.srcFile.fileState==1){
                    result.srcFile.fileState="预定密"
                }
                if(result.srcFile.fileState==2){
                    result.srcFile.fileState="正式定密"
                }
                if(result.srcFile.fileState==3){
                    result.srcFile.fileState="文件签发"
                }
                if(result.srcFile.fileState==4){
                    result.srcFile.fileState="密级变更"
                }
                if(result.srcFile.fileState==5){
                    result.srcFile.fileState="文件解密"
                }

                if(result.srcFile.fileLevel==0){
                    result.srcFile.fileLevel="公开"
                }
                if(result.srcFile.fileLevel==1){
                    result.srcFile.fileLevel="内部"
                }
                if(result.srcFile.fileLevel==2){
                    result.srcFile.fileLevel="秘密"
                }
                if(result.srcFile.fileLevel==3){
                    result.srcFile.fileLevel="机密"
                }
                if(result.srcFile.fileLevel==4){
                    result.srcFile.fileLevel="绝密"
                }

                if(result.createUser.level==0){
                    result.createUser.level="默认"
                }
                if(result.createUser.level==1){
                    result.createUser.level="一般"
                }
                if(result.createUser.level==2){
                    result.createUser.level="重要"
                }
                if(result.createUser.level==3){
                    result.createUser.level="核心"
                }


                if(type==2){
                    setValues(result,"i");
                    $(".finished").show();
                }
                if(type==1 || type==0){
                    setValues(result,"m");
                    $(".applyed").show();
                }
                //下载
                $(".downfile").unbind("click").click(function(){
                    downUnfinish(workflowId);
                });

            }
        }
    })
}


//设置数据
function setValues(result,s){
    //流程类型
    $("#"+s+"workFlowType").val(getWorkFlowType(result.workFlowType)+"流程");
    //流程状态
    $("#"+s+"flowState").val(result.flowState);
    //申请总级数
    $("#"+s+"totalStep").val(result.totalStep);
    //文件名称
    $("#"+s+"fileName").val(result.srcFile.fileName);
    //紧急程度
    $("#"+s+"urgency option[value='" + result.urgency + "']").attr("selected", true);
    //密级标志版本
    $("#"+s+"markVersion").val(result.markVersion);
    //保密期限
    $("#"+s+"durationType option[value='" + result.durationType + "']").attr("selected", true)
    applyfValidPeriod(result,s);
    //文件密级
    $("#"+s+"applyFileLevel option[value='" + result.applyFileLevel + "']").attr("selected", true)
    //主定密单位
    $("#"+s+"major").val(result.majorUnit.name);
    $("#"+s+"unitNos").val(result.majorUnit.unitNo);
    //辅助定密单位
    iMinor(result,s);

    //定密依据
    $("#"+s+"basisType option[value='" + result.basisType + "']").attr("selected", true)
    getApplyfBasis(result,s);

    //知悉范围
    filefScoped(result,s);
    permissionData(result.permission,s);
    //扩展属性
    $("#"+s+"issueNumber").val(result.issueNumber);
    $("#"+s+"docNumber").val(result.docNumber);
    $("#"+s+"duplicationAmount").val(result.duplicationAmount);
    //发起人
    $("#"+s+"createUser").val(result.createUser.name);
    $("#"+s+"createLevel").val(result.createUser.level);
    $("#"+s+"createTime").val(getLocalTime(result.createTime));
    //业务属性
    $("#"+s+"business").val(result.business);
    //申请理由
    $("#"+s+"createComment").val(result.createComment);

    //需要循环每级审批人
    var str = '<td class="rowname" style="border-right:1px solid #000;"><span>审批信息：</span>' +
        '<table style="display: inline-block;vertical-align: middle;width:710px;margin-left:95px;margin-top:-25px;" class="table table-bordered">' +
        '<thead style="width:600px;">'+
        '<tr>'+
        '<th style="width:15%;text-align: center;font-size:12px;">审批级别</th>'+
		'<th style="width:10%;text-align: center;font-size:12px;">审批人</th>'+
		'<th style="width:15%;text-align: center;font-size:12px;">审批状态</th>'+
		'<th style="width:15%;text-align: center;font-size:12px;">审批时间</th>'+
		'<th style="width:35%;text-align: center;font-size:12px;">审批意见</th>'+
        '</tr>'+
        '</thead><tbody style="width:600px;">';
    for(var i=0;i<result.flowTrack.length;i++){
        str+='<tr>' +
            ' <td style="text-align: center">'+
            +result.flowTrack[i].approveStep+ '级审批' +
            '</td>'+
            '<td id="级数'+result.flowTrack[i].approveStep+'" style="text-align: center">';
        if(result.flowTrack[i].approveUser!=null){
            str+=isUndefined(result.flowTrack[i].approveUser.name)
        }

        str+= '</td>'+
            '<td style="text-align: center">'+
            getApproveState(result.flowTrack[i].approveState)+
            '</td>'+
            '<td style="text-align: center">'+
            getLocalTime(result.flowTrack[i].approveTime)+
            '</td>'+
            '<td style="text-align: center">'+
            result.flowTrack[i].approveComment+
            '</td>'+
            '</tr>';

    }
    str+='</tbody></table></td>';
    $("#"+s+"approveUser").empty();
    $("#"+s+"approveUser").append(str);

    //点击描述
    $(".choosefDesc").unbind("click").click(function(){
        $("#"+s+"tooltips").hide()
        $("#"+s+"scopeDesc").show()
    });

    //点击列表选择
    $(".clickfList").unbind("click").click(function(){

        $("#"+s+"scopeDesc").hide();
        $("#"+s+"tooltips").show();
    })
}

//初始化辅助定密单位
function iMinor(unit,s){

    var units=unit.applyMinorUnit;
    if(units.length>0){
        for(var j=0;j<units.length;j++){
            // var rds=$("#unitNo"+units[j].unitNo).text();
            var rds="["+units[j].unitNo+"]"+units[j].name;

            var parten = /^\s*$/ ;
            if(parten.test(rds)){
            }else{
                var sortable = $(".ui-sortable");
                $("."+s+"disp").append('<span id="dc" class="sort"  style="white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">'+ rds +'</div></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
        }
    }
}

//定密依据
function getApplyfBasis(result,s){

	//初始化
    var applyBasis=result.applyBasis;
    $("#"+s+"id_basisDescription").empty();
    var rds='';
    for(var i=0;i<applyBasis.length;i++){
        var basisLevel =applyBasis[i].basisLevel;

        if (i > 0) {
        	rds += "\r\n";
        }
        rds += '['+parseLevelToName(basisLevel)+']'+applyBasis[i].basisContent;
       
      //  $(".list-name-select").height($(".i_inpBasis").height()+1);
    }
    $("#"+s+"id_basisDescription").val(rds);

}

//保密期限
function applyfValidPeriod(result,s){

    if(result.durationType==2){
        var str = '<input type="text" id="ftime" readonly style="margin-right: 5px;width: 36px;float: left;margin-left: 5px; border-radius: 3px; border: 0; margin-top: -10px; height:5px;" >' +
            '<select id="ftime2" disabled style="float: left;width: 36px;" ><option>年</option><option>月</option><option>日</option></select>';

    }
    if(result.durationType==3){
        var str='<input style="margin-left: 5px;height: 5px" readonly type="text" class="laydate-icon"  value="'+getDate(result.fileDecryptTime)+'">';
    }
    if(result.durationType==4){

        var str='<input id="durationDescription" style="width: 70%;margin-left: 5px;height: 5px" readonly type="text" value="'+result.durationDescription+'">';
    }
    $("."+s+"times").empty();
    $("."+s+"times").append(str)


    var str="";

    if(result.durationType==2){
        //获取到传过来的期限类型
        var applyValidPeriod=result.applyValidPeriod;
        var arra=applyValidPeriod.split("");
        if(parseInt(arra[0]+arra[1])>0){
            $("#ftime").val(arra[0] + arra[1]);
            $("#ftime2").find("option").eq(0).attr("selected", true);

        }
        if(parseInt(arra[2]+arra[3])>0){
            $("#ftime").val(arra[2] + arra[3]);
            $("#ftime2").find("option").eq(1).attr("selected", true);

        }
        if(parseInt(arra[4]+arra[5])>0){
            $("#ftime").val(arra[4] + arra[5]);
            $("#ftime2").find("option").eq(2).attr("selected", true);

        }

    }
    return str;
}

//知悉范围人
function filefScoped(result,s){


    var scopes=result.fileScope;
    var names='';
    for(var i=0;i<scopes.length;i++){
        names+=scopes[i].name+','
    }
    $("#"+s+"tooltips").text(names);

    $('#'+s+'tooltips').pt({
        position: 'b', // 默认属性值
        align: 'r',	   // 默认属性值
        arrow:true,
        content: $('#'+s+'tooltips').val()
    });
}


//返回到已完成审批的列表页
function goback(type){
    if(type == 0 || type == 1){
		$(".applyall").show();
        $(".applyed").hide();
        
    }else if(type == 2){
        $(".finishall").show();
        $(".finished").hide();
    }

}




//如果文件有默认权限，就有初始数据
function permissionData(dispatch,s){

    var str='';

    if(dispatch.contentRead==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;" name="contentRead" checked>内容阅读';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentRead" >内容阅读';
    }
    if(dispatch.contentModify==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentModify" checked>内容修改';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentModify" >内容修改';
    }
    if(dispatch.contentPrint==1){
        str+= '<input value="1" type="checkbox" style="border:0;font-size:12px;"  name="contentPrint" checked>打印';
    }else{
        str+= '<input value="1" type="checkbox" style="border:0;font-size:12px;"  name="contentPrint" >打印';
    }
    if(dispatch.contentPrintHideWater==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentPrintHideWater" checked>打印隐藏水印';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentPrintHideWater" >打印隐藏水印';
    }

    if(dispatch.contentScreenShot==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentScreenShot" checked>截屏';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentScreenShot" >截屏';
    }
    if(dispatch.contentCopy==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentCopy" checked>内容拷贝';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="contentCopy" >内容拷贝';
    }
    if(dispatch.fileCopy==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="fileCopy" checked>文件拷贝';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="fileCopy" >文件拷贝';
    }

    if(dispatch.fileSaveCopy==1){
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="fileSaveCopy" checked>另存副本';
    }else{
        str+= '<input type="checkbox" style="border:0;font-size:12px;"  name="fileSaveCopy" >另存副本';
    }
    $("."+s+"permission").empty();
    $("."+s+"permission").append(str);
}
//我的待办
function myUnfinished(index){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/queryUnfinished",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "pageNumber":index,
            "pageSize":10
        }),
        success:function(result){
            if(result.code==0){
                $(".unfinishList").empty();
                var datas=result.workFlowList;
                if(datas.length==0){
                	$(".unfinishList").append('<tr><td colspan="7" style="text-align:center">没有相关数据</td></tr>');
                }
                for(var i=0;i<datas.length;i++){

                    if(datas[i].applyFileLevel==0){
                        datas[i].applyFileLevel="公开"
                    }
                    if(datas[i].applyFileLevel==1){
                        datas[i].applyFileLevel="内部"
                    }
                    if(datas[i].applyFileLevel==2){
                        datas[i].applyFileLevel="秘密"
                    }
                    if(datas[i].applyFileLevel==3){
                        datas[i].applyFileLevel="机密"
                    }
                    if(datas[i].applyFileLevel==4){
                        datas[i].applyFileLevel="绝密"
                    }
                    var num=parseInt(i)+1;
                    var str='<tr>'+
                        '<td>'+num+'</td>'+
                        '<td>'+getWorkFlowType(datas[i].workFlowType)+'流程</td>'+
                        '<td>'+datas[i].srcFile.fileName+'</td>'+
                        '<td>'+datas[i].applyFileLevel+'</td>'+
                        '<td>'+datas[i].createUser.name+'</td>'+
                        '<td>'+getLocalTime(datas[i].createTime)+'</td>'+
                        '<td><span style="cursor: pointer;" onclick="workflowInfo('+datas[i].workFlowId+')">审批</span>|<span style="cursor: pointer;" onclick="downUnfinish('+datas[i].workFlowId+')">下载</span><a href="" id="excelhref" class="hide"><span id="excel"></span></a></td>'+
                        '</tr>';
                    $(".unfinishList").append(str);
                }

                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: index+1, //当前页数
                    totalPages: result.totalPages, //总页数
                    numberOfPages: 5,
                    itemTexts: function (type, page, current) {
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },
                    onPageClicked:function(event, originalEvent, type, page){
                        myUnfinished(page-1)
                    }}
                $('#example').bootstrapPaginator(options);
                $('#exampleTotal').text('共'+result.totalElements+'条');

            }
        }
    });
}
//下载文件
function downUnfinish(workFlowId){
    //获取表单
    var url=host+"/dcms/api/v1/workFlow/downloadFileById";
    url = url + "?token="+token+"&workFlowId="+workFlowId;
    var excelhref = $("#excelhref");
    excelhref.attr("href",url);
    $("#excel").click();
}


//获取文件类型
function getWorkFlowType(workFlowType){
    if(workFlowType==2){
        return "正式定密"
    }
    if(workFlowType==3){
        return "文件签发"
    }
    if(workFlowType==4){
        return "密级变更"
    }
    if(workFlowType==5){
        return "文件解密"
    }
    if(workFlowType==9){
        return "密文还原"
    }
}
//查看流程详情
function workflowInfo(workflowId){

    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/queryById",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "workFlowId":workflowId
        }),
        success:function(result){

            if(result.code==0){
                $(".unlist").hide();
                $(".wfwInfo").empty();



                if(result.flowState==1){
                    result.flowState="已完成"
                }
                if(result.flowState==0){
                    result.flowState="未完成"
                }

                if(result.srcFile.fileState==1){
                    result.srcFile.fileState="预定密"
                }
                if(result.srcFile.fileState==2){
                    result.srcFile.fileState="正式定密"
                }
                if(result.srcFile.fileState==3){
                    result.srcFile.fileState="文件签发"
                }
                if(result.srcFile.fileState==4){
                    result.srcFile.fileState="密级变更"
                }
                if(result.srcFile.fileState==5){
                    result.srcFile.fileState="文件解密"
                }


                if(result.srcFile.fileLevel==0){
                    result.srcFile.fileLevel="公开"
                }
                if(result.srcFile.fileLevel==1){
                    result.srcFile.fileLevel="内部"
                }
                if(result.srcFile.fileLevel==2){
                    result.srcFile.fileLevel="秘密"
                }
                if(result.srcFile.fileLevel==3){
                    result.srcFile.fileLevel="机密"
                }
                if(result.srcFile.fileLevel==4){
                    result.srcFile.fileLevel="绝密"
                }

                if(result.createUser.level==0){
                    result.createUser.level="默认"
                }
                if(result.createUser.level==1){
                    result.createUser.level="一般"
                }
                if(result.createUser.level==2){
                    result.createUser.level="重要"
                }
                if(result.createUser.level==3){
                    result.createUser.level="核心"
                }
                //审批标题
                $("#uTitle").text(getWorkFlowType(result.workFlowType)+"审批");

                //流程id
                $("#workFlowId").val(result.workFlowId);
                //流程类型
                $("#workFlowType").val(getWorkFlowType(result.workFlowType)+"流程");
                //流程状态
                $("#flowState").val(result.flowState);
                //申请总级数
                $("#totalStep").val(result.totalStep);
                //文件名称
                $("#fileName").val(result.srcFile.fileName);
                //紧急程度
                $("#urgency option[value='" + result.urgency + "']").attr("selected", true);
                //密级标志版本
                $("#markVersion").val(result.markVersion);
                //保密期限
                $("#durationType option[value='" + result.durationType + "']").attr("selected", true)
                applyValidPeriod(result);

                //文件密级
                $("#applyFileLevel option[value='" + result.applyFileLevel + "']").attr("selected", true)
                //主定密单位
                $("#major").val(result.majorUnit.name);
                $("#unitNos").val(result.majorUnit.unitNo);
                //辅助定密单位
                Minor(result);

                //定密依据
                $("#basisType option[value='" + result.basisType + "']").attr("selected", true)
                loadBasisTree(result.basisType);
                getApplyBasis(result);

                //知悉范围
                fileScoped(result);
                $("#fileScopeDesc").val(isUndefined(result.fileScopeDesc))
                initData(result.permission);
                //扩展属性
                $("#issueNumber").val(isUndefined(result.issueNumber));
                $("#docNumber").val(isUndefined(result.docNumber));
                $("#duplicationAmount").val(isUndefined(result.duplicationAmount));
                //发起人
                $("#createUser").val(result.createUser.name);
                $("#createLevel").val(result.createUser.level);
                $("#createTime").val(getLocalTime(result.createTime));
                //申请理由
                $("#createComment").val(result.createComment);
                //业务属性
                $("#business").val(result.business);

                    //需要循环每级审批人
                var str = '<td class="rowname" style="border-right:1px solid #000;" id="approveUser"><span>审批信息：</span>' +
                    '<table style="display: inline-block;vertical-align: middle;width:710px;margin-left:95px;margin-top:-25px;" class="table table-bordered">' +
                    '<thead>'+
                    '<tr>'+
                    '<th style="width:15%;text-align: center;font-size:12px;">审批级别</th>'+
                    '<th style="width:10%;text-align: center;font-size:12px;">审批人</th>'+
                    '<th style="width:15%;text-align: center;font-size:12px;">审批状态</th>'+
                    '<th style="width:15%;text-align: center;font-size:12px;">审批时间</th>'+
                    '<th style="width:35%;text-align: center;font-size:12px;">审批意见</th>'+
                    '</tr>'+
                    '</thead><tbody style="width:600px;">';
                for(var i=0;i<result.flowTrack.length;i++){
                    str+='<tr>' +
                        ' <td style="text-align: center">'+
                        +result.flowTrack[i].approveStep+ '级审批' +
                        '</td>'+
                        '<td id="级数'+result.flowTrack[i].approveStep+'" style="text-align: center">';
                    if(result.flowTrack[i].approveUser!=null){
                        str+=isUndefined(result.flowTrack[i].approveUser.name)
                    }

                    str+= '</td>'+
                        '<td style="text-align: center">'+
                        getApproveState(result.flowTrack[i].approveState)+
                        '</td>'+
                        '<td style="text-align: center">'+
                        getLocalTime(result.flowTrack[i].approveTime)+
                        '</td>'+
                        '<td style="text-align: center">'+
                        result.flowTrack[i].approveComment+
                        '</td>'+
                        '</tr>';

                }
                //str+='</tbody></table></td>';
                //    var str = '<td  class="rowname" colspan="2" id="approveUser">'+
                //        '<span>审批信息：</span> ';
                //    for(var i=0;i<result.flowTrack.length;i++){
                //
                //        str+='<div style="display: inline-block;">'+
                //            '<div class="" style="display: inline-block;">'+result.flowTrack[i].approveStep+ '级审批人</div>'+
                //            '<div class="" style="display: inline-block;">'+
                //            '<div class="" style="display: inline-block;" id="级数'+result.flowTrack[i].approveStep+'">姓名:';
                //        if(result.flowTrack[i].approveUser!=null){
                //            str+=isUndefined(result.flowTrack[i].approveUser.name)
                //        }
                //
                //        str+= '</div>'+
                //            '<div class="" style="display: inline-block;">审批状态:'+
                //            getApproveState(result.flowTrack[i].approveState)+
                //            '</div>'+
                //            '<div class="" style="display: inline-block;">审批时间:'+
                //            getLocalTime(result.flowTrack[i].approveTime)+
                //            '</div>'+
                //            '<div class=""style="display: inline-block;">意见:'+
                //            result.flowTrack[i].approveComment+
                //            '</div>'+
                //            '</div>'+
                //            '</div>';

                        /* str+='<tr><td colspan="6" style="border-bottom-width: 2px;font-weight: bold">'+result.flowTrack[i].approveStep+ '级审批人</td>'+

                         '</tr>'+
                         '<tr>' +
                         '<td class="rowsname">姓名</td>'+
                         '<td  id="级数'+result.flowTrack[i].approveStep+'">';
                         if(result.flowTrack[i].approveUser!=null){
                         str+=isUndefined(result.flowTrack[i].approveUser.name)
                         }
                         str+='</td>'+
                         '<td class="rowsname">审批状态</td>'+
                         '<td>'+getApproveState(result.flowTrack[i].approveState)+'</td>'+
                         '<td class="rowsname">审批时间</td>'+
                         '<td>'+getLocalTime(result.flowTrack[i].approveTime)+'</td>'+
                         '</tr>'+
                         '<tr>' +
                         '<td class="rowsname">意见</td>'+
                         '<td colspan="5">'+result.flowTrack[i].approveComment+'</td>'+
                         '</tr>';*/
                    //}
                    //str+='</div>'+
                    //    '</div>'+
                    //    '</td>';
                    $("#approveUser").empty();
                    $("#approveUser").append(str);

                $(".unfinished").show();

                chooseApproveUser(result);

                $(".chooseList").unbind("click").click(function(){

                    var manorUnits=$(".ipvamajor");

                    //判断是否已选择过
                    var fileScope= JSON.parse($("#scopeValue").val());
                    tree(fileScope);
					$("#layer_shaow").show();
					$("#dtrees").show();
					$("#basisType").hide();	
					$("#ok_dtree").click(function(){
						 var fileScope = new Array();
                            //获取主定密单位
                            var unitNo=$("#unitNos").val();
                            var names='';
                            //获取所有的CheckBox
                            $("#browser :checkbox:checked").each(function(){

                                var uid=$(this).val();
                                /*if(uid=="did-root"){
                                    var scope={
                                        "unitNo":munitNo,
                                        "uid":null
                                    }
                                    fileScope.push(scope);
                                    names+=$(this).parent().text();
                                }else */if(uid.indexOf('did') == -1){
                                    var scope={
                                        "unitNo":unitNo,
                                        "uid":uid
                                    }
                                    fileScope.push(scope);
                                    names+=$(this).parent().text()+",";
                                }

                            });

                            //存到隐藏标签中
                            $("#scopeValue").val(JSON.stringify(fileScope));
                            $("#tooltips").val(names);
                            // layer.close(index);
                            $('#tooltips').pt({
                                position: 'b', // 默认属性值
                                align: 'r',	   // 默认属性值
                                width:200,
                                height:'auto',
                                arrow:true,
                                content: $('#tooltips').val()
                            });
							$("#layer_shaow").hide();
					        $("#dtrees").hide();
							$("#basisType").show();
					});
					$("#cel_dtree").click(function(){
						$("#layer_shaow").hide();
					    $("#dtrees").hide();
						$("#basisType").show();
					});
					
                    // layer.open({
                        // type: 1,
                        // title:"知悉范围",
                        // area: ['400px', '500px'],
                        // btn: ["确定","取消"],
                        // yes: function(index, layero){
                            // var fileScope = new Array();
                            // 获取主定密单位
                            // var unitNo=$("#unitNos").val();
                            // var names='';
                            // 获取所有的CheckBox
                            // $("#browser :checkbox:checked").each(function(){

                                // var uid=$(this).val();
                                // /*if(uid=="did-root"){
                                    // var scope={
                                        // "unitNo":munitNo,
                                        // "uid":null
                                    // }
                                    // fileScope.push(scope);
                                    // names+=$(this).parent().text();
                                // }else */if(uid.indexOf('did') == -1){
                                    // var scope={
                                        // "unitNo":unitNo,
                                        // "uid":uid
                                    // }
                                    // fileScope.push(scope);
                                    // names+=$(this).parent().text()+",";
                                // }

                            // });

                            // 存到隐藏标签中
                            // $("#scopeValue").val(JSON.stringify(fileScope));
                            // $("#tooltips").val(names);
                            // layer.close(index);
                            // $('#tooltips').pt({
                                // position: 'b', // 默认属性值
                                // align: 'r',	   // 默认属性值
                                // width:200,
                                // height:'auto',
                                // arrow:true,
                                // content: $('#tooltips').val()
                            // });

                        // },
                        // content:$("#dtree") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    // });
                });
                
                $("#business").unbind("click").click(function () {
                	loadBusinessTree();
					$("#layer_shaow").show();
					$("#id_layerBusinessTree").show();
                    // layer.open({
                        // type: 1,
                        // title: "选择业务属性",
                        // area: ['400px', '500px'],
                        // btn: ["关闭"],
                        // yes: function (index, layero) {
                            // layer.close(index);
                        // },
                        // content: $("#id_layerBusinessTree") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    // });
                });
                $("#cel_layerBusinessTree").click(function(){
					$("#layer_shaow").hide();
				    $("#id_layerBusinessTree").hide();
				 })
                //点击描述
                $(".chooseDesc").unbind("click").click(function(){
                    $("#tooltips").hide()
                    $("#fileScopeDesc").show()
                });

                //点击列表选择
                $(".clickList").unbind("click").click(function(){

                    $("#fileScopeDesc").hide();
                    $('#tooltips').show();					
                })
                //通过
                $(".pass-ok").unbind("click").click(function(){
                    approval(1);

                });

                //不通过
                $(".nopass").unbind("click").click(function(){
                    approval(0);
                });
                //打印
                $(".u-print").unbind("click").click(function(){
                    $("#u-table").printArea();
                });
                //下载
                $(".downfile").unbind("click").click(function(){
                    downUnfinish(workflowId);
                });
                $(".cel").unbind("click").click(function(){
                    $(".unlist").show();
                    $(".unfinished").hide();
                })
            }
        }
    });
}

//加载业务属性树
function loadBusinessTree() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token
        }),
        url: host + "/dcms/api/v1/business/queryTree",
        dataType: "json",
        cache: false,
        async: false,
        crossDomain: false,
        success: function (data) {
            if (data.code == 0 && data.bList != null) {
                var str = installBusinessTree(data);
                $("#id_businessTree").empty();
                $("#id_businessTree").append(str);
                $("#id_businessTree").treeview({
                    collapsed: true,
                    add: $("#id_businessTree").html
                });
				
            }
        }
    });
}

function installBusinessTree(data) {
    var str = "";
    for (var i = 0; i < data.bList.length; i++) {
        var bId = data.bList[i].bId;
        var name = data.bList[i].name;
        var childs = data.bList[i];
        str += '<ul id="id_businessTree" class="filetree treeview"><li><span class="file" style="color: #000; cursor:pointer" name="' + bId + '" value="' + name + '" onclick="onBusinessSelected(this)">' + name + '</span>' + installBusinessTree(childs) + '</li></ul>';
    }
    return str;
}

function onBusinessSelected(t) {
    $("#business").val($(t).text());
    // layer.closeAll();
	$("#layer_shaow").hide();
	$("#id_layerBusinessTree").hide();
}

//选择下级审批人
function chooseApproveUser(data){

    if(data.currentStep != data.totalStep){
        $.ajax({
            type:"post",
            url:host+"/dcms/api/v1/workFlow/queryApproverSelectList",
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            crossDomain:false,
            data:JSON.stringify({
                "token": token,
                "workFlowType":data.workFlowType,
                "applyFileLevel":$("#applyFileLevel").val(),
                "workFlowId":data.workFlowId,
                "step":data.currentStep+1
            }),
            success:function(result){

                if(result.code==0){
                    $("#级数"+parseInt(data.currentStep+1)).empty();
                    var stepList = result.stepList[0];
                    var str = '<select id="approverStep" style="width:100px;">' ;
                    if(stepList.roleList.length > 0) {
                    	
                    	for(var i=0;i<stepList.roleList.length;i++){
                    		str+= '<option value="'+stepList.roleList[i].roleId+'">'+stepList.roleList[i].name+'</option>';
                    		
                    	}
                    }
                    str+='</select>';
                    $("#级数"+parseInt(data.currentStep+1)).append(str);

                }
            }
        })
    }
}

//人员密集
function setuserLevel(code){
    if(code == 0){
        return '一般';
    }else if(code == 1){
        return '重要';
    }else {
        return '核心';
    }
}
//审批状态
function getApproveState(code) {
    if(code == 0) {
        return '未审批';
    }else if(code == 1){
        return '通过';
    }else{
        return '否决';
    }
}
//主定密单位
function majorUnit(majors){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/unit/queryMajorList",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token
        }),
        success:function(result){


            var units=majors.majorUnit;
            $(".majordisp").empty();
            var str='<select class="list-major-input" id="majorunitNo" onclick="majorchoose()">'+
                '<option id="majorunitNo'+result.unitNo+'" value="'+result.unitNo+'">['+result.unitNo+']'+result.name+'</option>'+
                '</select>';
            $("#majorunitNo").remove();
            $("#applyManorUnit").prepend(str);
            var rds=$("#majorunitNo"+units.unitNo).text();
            var parten = /^\s*$/ ;
            if(parten.test(rds)){
            }else{
                var sortable = $(".ui-sortable");
                $("#majorunitNo").parent().parent().find(".majordisp").append('<span id="dc" class="sort"  style="   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartmajorUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">'+ rds +'</div><img onclick=" removeMajorUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
            $(".list-major-input").height($(".i_inpmajor").height()+1);
        }
    })
}




//知悉范围人
function fileScoped(result){


    var fileScope=result.fileScope;
    //var scopes=result.fileScope;
    //将签发范围存到sessions...中
    var scope=new Array();
    var names='';
    for(var i=0;i<fileScope.length;i++){
        var oldScope={
            "unitNo":fileScope[i].unitNo,
            "uid":fileScope[i].uid
        }
        scope.push(oldScope);
        names+=fileScope[i].name+','
    }

    $("#scopeValue").val(JSON.stringify(scope));
    $("#tooltips").val(names);
    $('#tooltips').pt({
        position: 'b', // 默认属性值
        align: 'r',	   // 默认属性值
        width:200,
        height:'auto',
        arrow:true,
        content: $('#tooltips').val()
    });
}


//获取定密依据类型
function getBasisType(t){
    var val=$(t).val();
    getBasisByType(val)

}
//根据类型查找定密依据
function getBasisByType(basisType){

    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/basis/queryList",
        contentType: "application/json; charset=utf-8",
        async:false,
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "basisType":basisType,
            "basisLevel":$("#applyFileLevel").val()

        }),
        success:function(result){

            if(result.code==0){

                var str='';
                $("#basisContentNo").empty();
                for(var i=0;i<result.basisList.length;i++){
                    var basisLevel=result.basisList[i].basisLevel;
                    if(basisLevel==0){
                        basisLevel="公开"
                    }
                    if(basisLevel==1){
                        basisLevel="内部"
                    }
                    if(basisLevel==2){
                        basisLevel="秘密"
                    }
                    if(basisLevel==3){
                        basisLevel="机密"
                    }
                    if(basisLevel==4){
                        basisLevel="绝密"
                    }
                    str+='<tr><td><input type="checkbox" >['+basisLevel+']'+result.basisList[i].basisContent+'</td></tr>'
                }
                $("#basisContentNo").append(str);
               // $(".list-name-select").height($(".i_inpBasis").height()+1);
            }

        }
    });


}

//定密依据
function getApplyBasis(result){

    //初始化
    var applyBasis=result.applyBasis;
    $("#id_basisDescription").empty();
    var rds='';
    for(var i=0;i<applyBasis.length;i++){
        var basisLevel =applyBasis[i].basisLevel;

        if (i > 0) {
        	rds += "\r\n";
        }
        rds += '['+parseLevelToName(basisLevel)+']'+applyBasis[i].basisContent;
       
      //  $(".list-name-select").height($(".i_inpBasis").height()+1);
    }
    $("#id_basisDescription").val(rds);


    //return str;

}
//定密依据列表
function basisshow(){
	$("#layer_shaow").show();
	$("#id_layerBasisTree").show();
	$("#basisType").hide();
	// layer.open({
        // type: 1,
        // title: "添加定密依据",
        // area: ['400px', '500px'],
        // btn: ["添加", "关闭"],
        // yes: function (index, layero) {
            
            // layer.close(index);
        // },
        // no: function () {
        // },
        // content: $("#id_layerBasisTree") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
    // });
	$("#ok_layerBasisTree").click(function(){
	$("#id_basisTree input:checkbox:checked").each(function (index, element) {
                var str = $("#id_basisDescription").val();
                var content = $(this).parent().text();
                var desc = content.slice(0, 4) + "《" + $(this).val() + "》" + content.slice(4);
                if (str.indexOf(desc) < 0) {
                    // 判断无重复，则追加
                    if (str != "") {
                        str += "\r\n";
                    }
                    str += desc;
                }
                $("#id_basisDescription").val(str);
    });
	$("#layer_shaow").hide();
	$("#id_layerBasisTree").hide();
	$("#basisType").show();
	
});
$("#cel_layerBasisTree").click(function(){
	$("#layer_shaow").hide();
	$("#id_layerBasisTree").hide();
	$("#basisType").show();
	
});
}

function loadBasisTree(basisType) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "token": token,
            "basisType": basisType,
            "basisLevel": 4
        }),
        url: host + "/dcms/api/v1/basis/queryBasisTree",
        dataType: "json",
        cache: false,
        async: false,
        crossDomain: false,
        success: function (data) {
            if (data.code == 0) {
                var str = installBasisTreeClass(data);
                $("#id_basisTree").empty();
                $("#id_basisTree").append(str);
                $("#id_basisTree").treeview({
                    collapsed: true,
                    add: $("#id_basisTree").html
                });
            }
        }
    });
}

function installBasisTreeClass(data) {
    var str = "";
    for (var i = 0; i < data.basisClassList.length; i++) {
        var className = data.basisClassList[i].basisClass;
        str += '<ul id="id_basisTree" class="filetree treeview"><li><span class="folder" style="color: #000;" value="' + className + '">' + className + '</span>' + installBasisTreeBasis(data.basisClassList[i]) + '</li></ul>';
    }
    return str;
}

function installBasisTreeBasis(data) {
    var str = "";
    for (var i = 0; i < data.basisList.length; i++) {
        var basisName = data.basisList[i].basisName;
        var parentClassName = data.basisClass;
        str += '<ul id="id_basisTree" class="filetree treeview"><li><span class="folder" style="color: #000;" name="' + parentClassName + '" value="' + basisName + '">' + basisName + '</span>' + installBasisTreeItem(data.basisList[i]) + '</li></ul>';
    }
    return str;
}

function installBasisTreeItem(data) {
    var str = "";
    for (var i = 0; i < data.basisItemList.length; i++) {
        var basisId = data.basisItemList[i].basisId;
        var basisLevel = data.basisItemList[i].basisLevel;
        var basisItem = data.basisItemList[i].basisItem;
        var parentBasisName = data.basisName;
        var basisContent = "[" + parseLevelToName(basisLevel) + "]" + basisItem;
        str += '<ul id="id_basisTree" class="filetree treeview"><li><span class="file" style="color: #000;"><input style="width:20px;height:20px;vertical-align:middle;" type="checkbox" value="' + parentBasisName + '">' + basisContent + '</span></li></ul>';
    }
    return str;
}
//移除定密依据
function removeBasis(t){
    $(t).parent().remove();
   // $(".list-name-select").height($(".i_inpBasis").height()+1);
}
function select(t){

    var val=$(t).val();
    if(val==2){
        var str = '<input type="text" id="time" style="margin-right: 5px;width: 36px;display:inline-block;margin-left: 5px; border-radius: 3px; border: 1px solid #ddd; margin-top: 0px; height: 5px;" >' +
            '<select id="time2" style="display:inline-block;width: 36px;" ><option>年</option><option>月</option><option>日</option></select>';

    }
    if(val==3){
        var str='<input style="margin-left: 5px;height: 5px" type="text" class="laydate-icon" id="start" onclick="getTime()">';
    }
    if(val==4){

        var str='<input id="durationDescription" style="width: 70%;margin-left: 5px;height: 5px" type="text">';
    }
    $(".times").empty();
    $(".times").append(str)

};
function getTime(){
    var start = {
        elem: '#start',
        format: 'YYYY/MM/DD',
        start: laydate.now(0, 'YYYY/MM/DD'),
        istoday: true
    };
    laydate(start);
}
//保密期限
function applyValidPeriod(result){

    if(result.durationType==2){
        var str = '<input type="text" id="time" style="margin-right: 5px;display:inline-block;margin-left: 5px; border-radius: 3px; border: 1px solid #ddd; margin-top: 0px; height: 5px;" >' +
            '<select id="time2" style="display:inline-block;" ><option>年</option><option>月</option><option>日</option></select>';

    }
    if(result.durationType==3){
        var str='<input style="margin-left: 5px;height: 5px" type="text" class="laydate-icon" id="start" onclick="getTime()" value="'+getDate(result.fileDecryptTime)+'">';
    }
    if(result.durationType==4){

        var str='<input id="durationDescription" style="margin-left: 5px;height: 5px" type="text" value="'+result.durationDescription+'">';
    }
    $(".times").empty();
    $(".times").append(str)

    if(result.durationType==2){
        //获取到传过来的期限类型
        var applyValidPeriod=result.applyValidPeriod;
        var arra=applyValidPeriod.split("");
        if(parseInt(arra[0]+arra[1])>0){
            $("#time").val(arra[0] + arra[1]);
            $("#time2").find("option").eq(0).attr("selected", true);

        }
        if(parseInt(arra[2]+arra[3])>0){
            $("#time").val(arra[2] + arra[3]);
            $("#time2").find("option").eq(1).attr("selected", true);

        }
        if(parseInt(arra[4]+arra[5])>0){
            $("#time").val(arra[4] + arra[5]);
            $("#time2").find("option").eq(2).attr("selected", true);

        }

    }

}

//辅助定密单位
function Minorshow(){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/unit/queryMinorList",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token

        }),
        success:function(result){
            if(result.code==0){
                $("#applyMinorUnits").empty();
                var dat=result.unitList;

                var str='';
                for(var i=0;i<dat.length;i++){
                    str+='<tr><td id="unitNo'+dat[i].unitNo+'"><input style="width:20px;height:20px;" type="checkbox" >['+dat[i].unitNo+']'+dat[i].name+'</td></tr>'
                }
                $("#applyMinorUnits").append(str);
				$("#layer_shaow").show();
				$("#MinorUnits").show();
				$("#basisType").hide();
                // layer.open({
                    // type: 1,
                    // title:"辅助定密单位",
                    // area: ['300px', '300px'],
                    // btn: ["确定","取消"],
                    // yes: function(index, layero){

                        // Minorchoose()

                        // layer.close(index);
                    // },
                    // content:$("#MinorUnits") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                // });
            }

        }
    })
	$("#ok_MinorUnits").click(function(){
		Minorchoose();
		$("#layer_shaow").hide();
		$("#MinorUnits").hide();
		$("#basisType").show();
	})
	$("#cel_MinorUnits").click(function(){
		$("#layer_shaow").hide();
		$("#MinorUnits").hide();
		$("#basisType").show();
	})
}
//初始化辅助定密单位
function Minor(unit){

    $(".disp").empty();
    var units=unit.applyMinorUnit;
    if(units.length>0){
        for(var j=0;j<units.length;j++){
            // var rds=$("#unitNo"+units[j].unitNo).text();
            var rds="["+units[j].unitNo+"]"+units[j].name;

            var parten = /^\s*$/ ;
            if(parten.test(rds)){
            }else{
                var sortable = $(".ui-sortable");
                $(".disp").append('<span id="dc" class="sort"  style="    white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">'+ rds +'</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
        }
    }
}
//
function getBlur(t){
    // $(".ipva").css("display","block");
    $(".ipva").focus();

    $(".ipva").unbind("keypress").bind("keypress",function(){
        if(window.event){
            oEvent = window.event;		//处理兼容性，获得事件对象
            //设置IE的charCode值
            oEvent.charCode = (oEvent.type == "keypress") ? oEvent.keyCode : 0;
        }
        if( oEvent.keyCode == 13){
            var rds = $(this).val();
            var key=0;
            $(".dartUnit").each(function(){
                if($(this).text()==rds){
                    key=1;
                }
            });
            if(key==1){
                alert("该单位已选择！");
                $(this).val("");
                return;
            }
            var parten = /\[[a-zA-Z0-9]\]*/ ;
            if(parten.test(rds)){
                var sortable = $(".ui-sortable");
                $(".disp").append('<span id="dc" class="sort"  style="   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">'+ rds +'</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }else{
               alert("请按‘[’+编号+‘]’+单位名称格式输入");
            }
            $(this).val("");
           // $(".list-name-input").height($(t).height()+1);
        }
    });


}
//移除辅助定密单位
function removeUnit(t){
    $(t).parent().remove();
    //$(".list-name-input").height($(".i_inp").height()+1);
}


//选择辅助定密单位
function Minorchoose(){
    var key;
    var Minors=new Array();
    $("#applyMinorUnits :checkbox:checked").each(function(){
        key = 0;
        var rds = $(this).parent().text();
        $(".dartUnit").each(function(){
            if($(this).text()==rds){
                key = 1;
            }
        });
        if(key!=1){
            var parten = /^\s*$/ ;
            if(parten.test(rds)){
            }else{
                var sortable = $(".ui-sortable");
                $(".disp").append('<span id="dc" class="sort"  style="   white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">'+ rds +'</div><img onclick=" removeUnit(this)" src="js/textshow/images/sfwrg.jpg" style="float:left; display:block; cursor:pointer; " /></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
           // $(".list-name-input").height($(".i_inp").height()+1);
        }
    })

}

//审批流程
function approval(approveState){


    var workFlowId = $("#workFlowId").val();
    var approveLevel = $("#applyFileLevel").val();
    var params={
        "workFlowId":workFlowId,
        "approveLevel":approveLevel
    }
    //保密期限
    var durationType=$("#durationType").val();
    params["durationType"]=durationType;
    var approveValidPeriod;
    if(durationType==2){

        //获取期限数字
        var te= /^[0-9]{1,2}$/;
        var num=$("#time").val();
        if(!te.test(num)){
            // layer.
			alert("保密时长最多输入两位有效数字！"
			// ,{
                // icon: 2,
                // skin: 'layer-ext-moon'
            // }
			);
            return;
        }
        //判断是年 月 或日
        if ($("#time2").find("option").eq(0).is(":selected")) {
        	if(num.length==1){
                approveValidPeriod='0'+num+'0000';
            }else{
                approveValidPeriod=num+'0000';
            }
        }
        if ($("#time2").find("option").eq(1).is(":selected")) {
        	if(num.length==1){
                approveValidPeriod='000'+num+'00';
            }else{
                approveValidPeriod='00'+num+'00';
            }
        }
        if ($("#time2").find("option").eq(2).is(":selected")) {
        	if(num.length==1){
                approveValidPeriod='00000'+num;
            }else{
                approveValidPeriod='0000'+num;
            }
        }
        
        params["approveValidPeriod"]=approveValidPeriod;
    }else if(durationType==3){
        var fileDecrypt=$("#start").val();
        var fileDecryptTime =new Date(fileDecrypt).getTime();
        params["fileDecryptTime"]=fileDecryptTime;
    }else{
        var durationDescription=$("#durationDescription").val();
        params["durationDescription"]=durationDescription;
    }
    //分发范围
    var fileScope= $("#scopeValue").val();
    params["fileScope"]=JSON.parse(fileScope);

    //fileScopeDesc 分发范围描述
    params["fileScopeDesc"]=$("#fileScopeDesc").val();
    //定密依据
    var approveBasis = new Array();
    
    var basisText = $("#id_basisDescription").val();
    if (basisText != null) {
        var basisArr = basisText.split("\n");
        for (var index in basisArr) {
            var item = basisArr[index];
            if (item.length > 4) {
                var levelName = item.slice(1, 3);
                var content = item.slice(4);
                var level;
                if (levelName == "公开") {
                    level = 0;
                } else if (levelName == "内部") {
                    level = 1;
                } else if (levelName == "秘密") {
                    level = 2;
                } else if (levelName == "机密") {
                    level = 3;
                } else if (levelName == "绝密") {
                    level = 4;
                } else {
                    // layer.
					alert('定密依据格式不正确！（[秘密]依据内容）'
					// , {
                        // icon: 1,
                        // skin: 'layer-ext-moon'
                    // }
					);
                    return;
                }
                approveBasis.push({
                    "basisLevel": level,
                    "basisContent": content
                });
            }
        }
    }
    
    params["approveBasis"]=approveBasis;


    //定密依据类型 basisType;
    params["basisType"]=$("#basisType").val();

    //文号
    params["issueNumber"]=$("#issueNumber").val();
    //份号
    params["docNumber"]=$("#docNumber").val();
    //份数
    params["duplicationAmount"]=$("#duplicationAmount").val();

    //majorUnit 主定密单位
    var majorUnit={
        "unitNo":$("#unitNos").val(),
        "name":$("#major").val()
    }
    params["majorUnit"]=majorUnit;
    //辅助定密单位 approveMinorUnit
    var approveMinorUnit = new Array();
    var minorUnits=$(".dartUnit");
    //拆分获取定密单位编号，名称
    for(var i=0;i<minorUnits.length;i++){
        var minorUnit=$(minorUnits[i]).text();
        var unitNo=minorUnit.substr(1,minorUnit.indexOf(']')-1);
        var name=minorUnit.substr(minorUnit.indexOf(']')+1);
        var unit={
            "unitNo":unitNo,
            "name":name
        }
        approveMinorUnit.push(unit);
    }
    params["approveMinorUnit"]=approveMinorUnit;

    //审批意见
    var approveComment=$("#approveComment").val();
    //获取下一级的角色id
    var roleId = $("#approverStep").val();
    var approverBySteps = new Array();
    approverBySteps.push(roleId);
    params["approverByStep"]=approverBySteps;

    if ($.trim(approveComment) == '') {
        // layer.
		alert("审批意见必填！"
		// ,{
            // icon: 2,
            // skin: 'layer-ext-moon'
        // }
		);
        return;
    }
    params["approveComment"]=$("#approveComment").val();

    //审批状态 1通过 2否决
    params["approveState"]=approveState;
    //权限
    var permission={
        "contentRead":isChecked($(".permission"),'contentRead'),
        "contentPrint":isChecked($(".permission"),'contentPrint'),
        "contentPrintHideWater":isChecked($(".permission"),'contentPrintHideWater'),
        "contentModify":isChecked($(".permission"),'contentModify'),
        "contentScreenShot":isChecked($(".permission"),'contentScreenShot'),
        "contentCopy":isChecked($(".permission"),'contentCopy'),
        "fileCopy":isChecked($(".permission"),'fileCopy'),
        "fileSaveCopy":isChecked($(".permission"),'fileSaveCopy')
    }
    params["permission"]=permission;
    params["token"]=token;
    //密标版本
    params["markVersion"]=$("#markVersion").val();
    //文件紧急程度
    params["urgency"]=$("#urgency").val();
    params["business"]=$("#business").val();
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/workFlow/approval",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify(params),
        success:function(result){
            if(result.code==0){
                // layer.
				alert(result.msg
				// ,{
                    // icon: 1,
                    // skin: 'layer-ext-moon'
                // }
				);
                myUnfinished(0);
                $(".unlist").show();
                $(".unfinished").hide();
            }else if(result.code==7){
                // layer.
				alert(result.msg
				// ,{
                    // icon: 2,
                    // skin: 'layer-ext-moon'
                // }
				);
            }


        }
    });
}

//判断是什么类型  1uid 2gid 3did
function isvarIdType(varId){
    if(varId.indexOf('uid') != -1){
        return 1;
    }else if(varId.indexOf('gid') != -1){
        return 2;
    }else{
        return 3;
    }
}

//判断是否选中
function isChecked(t,values){
    if(t.find("input[name="+values+"]").is(':checked')){
        return 1;
    }else{
        return 0;
    }
}

//将时间戳转化为时间
function add0(m){return m<10?'0'+m:m }
function getLocalTime(shijianchuo)
{
//shijianchuo是整数，否则要parseInt转换
    if(shijianchuo == undefined||shijianchuo==0) {
        return "";
    }
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'/'+add0(m)+'/'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
}

//将时间戳转化为日期
function add0(m){return m<10?'0'+m:m }
function getDate(shijianchuo)
{
//shijianchuo是整数，否则要parseInt转换
    if(shijianchuo == undefined || shijianchuo == 0) {
        return "";
    }
    var time = new Date(shijianchuo);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    return y+'/'+add0(m)+'/'+add0(d);
}




<!--分发范围数据-->
//如果文件有默认权限，就有初始数据
function initData(dispatch){

    var str='';

    if(dispatch.contentRead==1){
        str+= '<span>内容阅读</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentRead" checked>';
    }else{
        str+= '<span>内容阅读</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentRead" >';
    }
    if(dispatch.contentPrint==1){
        str+= '<span>内容打印</span><input style="border:0;font-size:12px;" value="1" type="checkbox"  name="contentPrint" checked>';
    }else{
        str+= '<span>内容打印</span><input style="border:0;font-size:12px;" value="1" type="checkbox"  name="contentPrint" >';
    }
    if(dispatch.contentPrintHideWater==1){
        str+= '<span>打印隐藏水印</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentPrintHideWater" checked>';
    }else{
        str+= '<span>打印隐藏水印</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentPrintHideWater" >';
    }
    if(dispatch.contentModify==1){
        str+= '<span>内容修改</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentModify" checked>';
    }else{
        str+= '<span>内容修改</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentModify" >';
    }
    if(dispatch.contentScreenShot==1){
        str+= '<span>内容截屏</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentScreenShot" checked>';
    }else{
        str+= '<span>内容截屏</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentScreenShot" >';
    }
    if(dispatch.contentCopy==1){
        str+= '<span>内容拷贝</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentCopy" checked>';
    }else{
        str+= '<span>内容拷贝</span><input style="border:0;font-size:12px;" type="checkbox"  name="contentCopy" >';
    }
    if(dispatch.fileCopy==1){
        str+= '<span>文件拷贝</span><input style="border:0;font-size:12px;" type="checkbox"  name="fileCopy" checked>';
    }else{
        str+= '<span>文件拷贝</span><input style="border:0;font-size:12px;" type="checkbox"  name="fileCopy" >';
    }

    if(dispatch.fileSaveCopy==1){
        str+= '<span>另存副本</span><input style="border:0;font-size:12px;" type="checkbox"  name="fileSaveCopy" checked>';
    }else{
        str+= '<span>另存副本</span><input style="border:0;font-size:12px;" type="checkbox"  name="fileSaveCopy" >';
    }
    $(".permission").empty();
    $(".permission").append(str);
   // return str;

}
//调用树接口
function tree(scopeData){
    //默认的知悉范围
    $.ajax({
        type:"POST",
        crossDomain:false,
        data: JSON.stringify({
            "token": token,
            "hasUser": 1,
            "hasGroup": 0,
            "fileLevel":""
        }),
        url:host +"/dcms/api/v1/department/queryTree",
        dataType: "json",
        success : function(data) {

            var str = '<ul id="browser" class="filetree treeview">'+
                '<li id="root"><span class="dep"><input style="width:20px;height:20px;" type="checkbox" onclick="clickget(this.id)" value='+data.did+' id="'+data.did+'">'+data.name+'</span>'+
                maketree(data)+'</li>'+
                '</ul>'


            $("#browser").remove();
            $("#dtree").append(str);
            $("#browser").treeview({
                collapsed:false,
                add:$("#browser").html
            });

            //initData(dispatch);
            //让默认的人选中
            for(var i=0;i<scopeData.length;i++){
                //可得到用户的id，用ID将树上的用户默认选上
                var uid=scopeData[i].uid;
                $("#"+uid).prop("checked",true);
            }

        }
    });

}

//生成树
function maketree(data) {

    var str=''

    for(var i = 0; i<data.childUserList.length;i++) {
        str += '<ul>'+
            '<li><span class="user"><input style="width:20px;height:20px;" type="checkbox" onclick="clickget(this.id)" name="'+data.did+'" value='+data.childUserList[i].uid+' id='+data.childUserList[i].uid+'>'+data.childUserList[i].name+'</span>'+
            '</li>'+
            '</ul>'

    }

    for(var i = 0; i<data.childDepartmentList.length;i++) {
        str += '<ul>'+
            '<li><span class="dep"><input style="width:20px;height:20px;" type="checkbox" onclick="clickget(this.id)" name="'+data.did+'" value='+data.childDepartmentList[i].did+' id="'+data.childDepartmentList[i].did+'">'+data.childDepartmentList[i].name+'</span>'
            + maketree(data.childDepartmentList[i])+
            '</li>'+
            '</ul>'
    }
    /*str+='</li>'*/
    return str;

}
//取消每级父节点
function findParents(parentId){
    if($("#"+parentId).is(':checked')){
        $("#"+parentId).prop("checked",false);
        findParents($("#"+parentId).attr("name"));

    }

}

//如果是根节点
function checkRoot(varId){

    if($("#did-root").is(':checked')){
        $("input[name="+varId+"]").prop("checked",false);
        $("input[name="+varId+"]").each(function(){
            checkRoot($(this).val());
        });
    }
}
//单击CheckBox
function clickget(varId){
    /*if(varId=='did-root'){
        checkRoot(varId);

    }else{*/
        if($("#"+varId).is(':checked')){
            /*if($("#did-root").is(':checked')){
                $("#did-root").prop("checked",false);
            }*/
            $("input[name="+varId+"]").prop("checked",true);
         // 如果子节点全部勾选，自动勾选父节点
            var parents=$("#"+varId).attr("name");
            if ($("input[name='" + parents + "']").length == $("input[name='" + parents + "']:checked").length) {
                $("#"+parents).prop("checked", true);
            }
            $("input[name="+varId+"]").each(function(){
                clickget($(this).val());
            });
        }else{

            $("input[name="+varId+"]").prop("checked",false);
            $("input[name="+varId+"]").each(function(){
                clickget($(this).val());
            });
            //查找父节点，父节点选中的须被取消
            var parents=$("#"+varId).attr("name");
            findParents(parents);
        }

   // }

}

function createCheckBox(){
    var str = '<div class="checkbox">'+
        '<label>'+
        '<input type="checkbox"  name="contentRead" checked>内容阅读'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentModify" checked>内容修改'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentReadWatermark" >复制'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentPrint">粘贴'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentPrintWatermark">打印'+
        '</label>'+

        '<label>'+
        '<input type="checkbox"  name="contentScreenShot">打印隐藏水印'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentCopy">另存为'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileCopy">截屏'+
        '</label>'+

        '</div>';
    return str;
}


//判断值是否未定义
function isUndefined(values){
    if(typeof(values) == 'undefined'|| values=="null") {
        return "";
    }
    return values;
}

function parseLevelToName(level) {
    if (level == null || level == 0) {
        return "公开";
    } else if (level == 1) {
        return "内部";
    } else if (level == 2) {
        return "秘密";
    } else if (level == 3) {
        return "机密";
    } else if (level == 4) {
        return "绝密";
    } else {
        return "公开";
    }
}
