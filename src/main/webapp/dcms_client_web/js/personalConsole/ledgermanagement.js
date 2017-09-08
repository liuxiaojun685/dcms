/**
 * 个人文件查询
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-3-1
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
//1文件收件箱 2拟稿箱 3待解密(定密责任人专用) 4已解密(定密责任人专用)
$(function(){
    getPrivateList(0,5);
})
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1024, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
function getPrivateList(index, filter) {
	//密级
	var fileLevel;
	//文件状态
	var fileState;
	//关键字
	var keyword;
	if(filter==3){
		fileLevel = $("#queryByLevelIndecrypt option:selected").val();
		fileState = $("#queryByStateIndecrypt option:selected").val();
		keyword = $("#keywordIndecrypt").val();
	}else if(filter==4){
		fileLevel = $("#queryByLevelInDeclassified option:selected").val();
		fileState = $("#queryByStateInDeclassified option:selected").val();
		keyword = $("#keywordInDeclassified").val();
	}else if(filter==5){
		fileLevel = $("#queryByLevelInInbox option:selected").val();
		fileState = $("#queryByStateInInbox option:selected").val();
		keyword = $("#keywordInbox").val();
	}
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/file/queryPrivateList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": 10,
            "filter": filter,
            "fileLevel": fileLevel,
            "fileState": fileState,
            "keyword": keyword
        }),
        success: function (result) {
            if (result.code == 0) {
            	 if (filter == 3) {
                    $(".decryptList").empty();
                } else if (filter == 4){
                    $(".declassifiedList").empty();
                } else if(filter == 5) {
                	$(".ledgermanagementlist").empty();
                }
                    

                var datas = result.fileList;
                var str='';
                if(datas.length==0){
                	str+='<tr><td colspan="7" style="text-align:center">没有相关数据</td></tr>';
                }
                for (var i = 0; i < datas.length; i++) {
                    if (datas[i].fileState == 1) {
                        datas[i].fileState = "预定密"
                    }
                    if (datas[i].fileState == 2) {
                        datas[i].fileState = "正式定密"
                    }
                    if (datas[i].fileState == 3) {
                        datas[i].fileState = "文件签发"
                    }
                    if (datas[i].fileState == 4) {
                        datas[i].fileState = "密级变更"
                    }
                    if (datas[i].fileState == 5) {
                        datas[i].fileState = "文件解密"
                    }

                    if (datas[i].fileLevel == 0) {
                        datas[i].fileLevel = "公开"
                    }
                    if (datas[i].fileLevel == 1) {
                        datas[i].fileLevel = "内部"
                    }
                    if (datas[i].fileLevel == 2) {
                        datas[i].fileLevel = "秘密"
                    }
                    if (datas[i].fileLevel == 3) {
                        datas[i].fileLevel = "机密"
                    }
                    if (datas[i].fileLevel == 4) {
                        datas[i].fileLevel = "绝密"
                    }
                    var num = parseInt(i) + 1;
                    str += '<tr>' +
                        '<td>' + num + '</td>' +
                        '<td id="'+datas[i].fid+''+filter+'">' + datas[i].fileName + '</td>' +
                        '<td>' + datas[i].fileCreateUserName + '</td>' +
                        '<td>' + datas[i].fileLevel + '</td>' +
                        '<td>' + datas[i].fileState + '</td>' +
                        '<td>' + setfileurgency(datas[i].urgency) + '</td>' +
                        '<td>' + bytesToSize(datas[i].fileSize) + '</td>' +
                        '<td><span style="cursor: pointer;" onclick="findFileById(this.id,' + filter + ',1)" id=' + datas[i].fid + '>详情</span>|<span style="cursor: pointer;" onclick="downfile(this)"><input type="hidden" value="' + datas[i].fid + '">下载</span><a href="" id="excelhref" class="hide"><span id="excel"></span></a>|<span style="cursor: pointer;" onclick="track(this.id,' + filter + ')" id=' + datas[i].fid + '>追踪</span>|<span style="cursor: pointer;" onclick="findFileById(this.id,' + filter + ', 2)" id=' + datas[i].fid + '>溯源</span></td>' +
                        '</tr>';
                }
                
                if (filter == 3) {
                    $(".decryptList").append(str);
                } else if (filter == 4){
                    $(".declassifiedList").append(str);
                } else if(filter == 5) {
                	$(".ledgermanagementlist").append(str);
                }
                var options = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: index + 1, //当前页数
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
                    onPageClicked: function (event, originalEvent, type, page) {
                        getPrivateList(page - 1, filter)
                    }
                }

				if (filter == 3) {
                    $('#decryptpage').bootstrapPaginator(options);
                    $('#decryptpageTotal').text('共'+result.totalElements+'条');
                } else if(filter == 4) {
                    $('#declassifiedpage').bootstrapPaginator(options);
                    $('#declassifiedpageTotal').text('共'+result.totalElements+'条');
                }else if (filter == 5) {
                    $('#ledgermanagementpage').bootstrapPaginator(options);
                    $('#ledgermanagementpageTotal').text('共'+result.totalElements+'条');
                }
            }
        }
    })
}

//紧急程度
function setfileurgency(code){
    if(code == 0){
        return '一般';
    }else if(code == 1){
        return '重要';
    }else {
        return '加急';
    }
}

//根据id获取文件   type 1 详情 2 溯源
function findFileById(fid,filter,type) {
	if(type==1){
		if (filter == 3) {
			$(".decryptAll").hide();
			$(".decryptInfoAll").empty();
			$(".decryptInfoAll").show();
		} else if(filter == 4){
			$(".declassifiedAll").hide();
			$(".declassifiedInfoAll").empty();
			$(".declassifiedInfoAll").show();
		}else{
			$(".inboxAll").hide();
			$(".ledgermanagementAll").empty();
			$(".ledgermanagementAll").show();
		}
	}else if(type==2){
		
		if (filter == 3) {
			$(".decryptAll").hide();
			$(".decryptHistoryList").empty();
			$(".decryptHistory").show();
			$(".decryptHistory nav").remove();
		} else if(filter == 4){
			$(".declassifiedAll").hide();
			$(".declassifiedHistoryList").empty();
			$(".declassifiedHistory").show();
			$(".declassifiedHistory nav").remove();
		}else{
			$(".inboxAll").hide();
			$(".ledgermanagementHistoryList").empty();
			$(".ledgermanagementHistory").show();
			$(".ledgermanagementHistory nav").remove();
		}
	}
    
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/file/queryById",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        data: JSON.stringify({
            "token": token,
            "fid": fid
        }),
        success: function (result) {
            if (result.code == 0) {

            	if(type==1){
            		getInfoTable(result, filter);
            	}else if(type == 2) {
            		getHistory(result, filter);
            	}
            }
        }
    })
}


//追踪
function track(fid, filter){
	
	var myChart;
	if (filter == 3) {
        $(".decryptAll").hide();
        $("#decryptTrack").empty();
        $(".decrypttrack").show();
     // 基于准备好的dom，初始化echarts实例
        myChart = echarts.init(document.getElementById('decryptTrack'));
    } else if (filter == 4) {
        $(".declassifiedAll").hide();
        $("#declassifiedTrack").empty();
        $(".declassifiedtrack").show();
        myChart = echarts.init(document.getElementById('declassifiedTrack'));
    } else if (filter == 5) {
        $(".ledgermanagementAll").hide();
        $("#ledgermanagementTrack").empty();
        $(".ledgermanagementtrack").show();
        myChart = echarts.init(document.getElementById('ledgermanagementTrack'));
    }
	
	
	var option = {
    	    title: {
    	        text: '文件追踪'
    	    },
    	    tooltip : {//提示框，鼠标悬浮交互时的信息提示
    	    	trigger: 'item',
    	        formatter: function(params){//触发之后返回的参数，这个函数是关键
    	            return params.data.label;//返回标签
    	        },
    	      },
    	    animationDurationUpdate: 1500,
    	    animationEasingUpdate: 'quinticInOut',
    	    series : [
    	        {
    	            type: 'graph',
    	            layout: 'none',
    	            symbolSize: 50,
    	            roam: true,
    	            label: {//图形上的文本标签，可用于说明图形的一些数据信息
    	                normal: {
    	                  show : true,//显示
    	                  position: 'bottom',//相对于节点标签的位置
    	                  //回调函数，你期望节点标签上显示什么
    	                  formatter: function(params){
    	                    return params.data.label;
    	                  }
    	                }
    	              },
    	            edgeSymbol: ['circle', 'arrow'],
    	            edgeSymbolSize: [4, 10],
    	            edgeLabel: {
    	                normal: {
    	                    textStyle: {
    	                        fontSize: 20
    	                    }
    	                }
    	            },
    	            data: [],//节点数据
    	            // links: [],
    	            links: [],//边、联系数据 
    	            lineStyle: {
    	                normal: {
    	                    opacity: 0.9,
    	                    width: 2,
    	                    curveness: 0
    	                }
    	            }
    	        }
    	    ]
    	};
	
	
    var graph = {};//数据
    
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/file/fileTrack",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: false,
        async: false,
        data: JSON.stringify({
            "token": token,
            "fid": fid,
			"name": $("#"+fid+filter).text()
        }),
        success: function (result) {
        	if(result.code==0){
        		/*option.series[0].data = [{label:"711.txt",name:0,symbol:"image://css/images/icon/txt-2.png",x:400,y:0},{label:"711.txt",name:1,symbol:"image://css/images/icon/txt-1.png",x:600,y:-100}];
        		option.series[0].links = [{label:"预定密",source:0,target:1}];*/
        		option.series[0].data = result.nodes;
        		option.series[0].links = result.links;
        		myChart.setOption(option);
        		
        		if(filter==3){
        			$(".decryptback").unbind("click").click(function(){
            			gotoback(fid, filter);
            		});
        		}else if(filter==4){
        			$(".declassifiedback").unbind("click").click(function(){
            			gotoback(fid, filter);
            		});
        		}else if(filter==5){
        			$(".ledgermanagementback").unbind("click").click(function(){
            			gotoback(fid, filter);
            		});
        		}
        	}
        }
    });
   
}

//判断值是否未定义
function isUndefined(values){
    if(typeof(values) == 'undefined'|| values=="null") {
        return "";
    }
    return values;
}


//获取历史信息
function getHistory(result, filter){
	var strinfo='';
	for (var j = 0; j < result.historyList.length; j++) {
        strinfo += '<tr>' +
            '<td>' + result.historyList[j].user.name + '</td>' +
            '<td>' + getLocalTime(result.historyList[j].creatTime) + '</td>' +
            '<td>' + result.historyList[j].description + '</td>' +
            '</tr>';
    }
	var str='<nav class="text-right">' +
			    '<div class="btn-group" style="margin-top: 5px;margin-right: 45px;">' +
			    '<button type="button" class="btn btn-primary" onclick="gotoback(this.name,' + filter + ')" name=' + result.fid + '>返回</button>' +
			    '</div>' +
		    '</nav>';
	
	if (filter == 3) {
		$(".decryptHistoryList").append(strinfo);
		$(".decryptHistory").append(str);
	} else if(filter == 4){
		$(".declassifieHistoryList").append(strinfo);
		$(".declassifieHistory").append(str);
	}else{
		$(".ledgermanagementHistoryList").append(strinfo);
		$(".ledgermanagementHistory").append(str);
	}
}

//获取文件详情表格
function getInfoTable(result, filter) {

    if (result.fileLevel == 0) {
        result.fileLevel = "公开"
    }
    if (result.fileLevel == 1) {
        result.fileLevel = "内部"
    }
    if (result.fileLevel == 2) {
        result.fileLevel = "秘密"
    }
    if (result.fileLevel == 3) {
        result.fileLevel = "机密"
    }
    if (result.fileLevel == 4) {
        result.fileLevel = "绝密"
    }
    var strinfo = '<div style="width:800px;margin:0 auto;background: #fff;"><table style="width:700px;margin:0 auto;border: 1px solid #4777c8;">' +
        '<tr>' +
        '<td style="border:1px solid #4777c8"><h4 style="font-weight: bold;text-align: center;">文件详情</h4></td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span>文件名称：</span><input style="width:350px;"  readonly value="' + result.fileName + '"/><span style="padding-left:5px;">标志版本：</span><input readonly  value="' + result.markVersion + '"/></td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span style="letter-spacing: 0;">文件起草人：</span><input style="margin-left:2px;width:131px;" readonly value="' + isUndefined(result.fileCreateUserName) + '"/><span style="letter-spacing: 0;padding-left:4px;">定密责任人：</span><input style="margin-left:2px;width:134px;" readonly value="' + isUndefined(result.fileLevelDecideRoleName) + '"/><span style="letter-spacing: 0;padding-left:4px;">文件签发人：</span><input style="margin-left:2px;width:134px;" readonly value="' + isUndefined(result.fileDispatchRoleName) + '"/></td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span>起草日期：</span><input readonly value="' + getDate(result.fileCreateTime) + '"/> <span>定密日期：</span><input readonly value="' + getDate(result.fileLevelDecideTime) + '"/> <span>签发日期：</span><input readonly value="' + getDate(result.fileDispatchTime) + '"/></td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span>秘密等级：</span><input type="text" readonly value="' + result.fileLevel + '"><span style="padding-left:5px;">紧急程度：</span><input style="margin-left:1px;" readonly  value="' + setfileurgency(result.urgency) + '"/> <span style="padding-left:2px;">保密期限：</span>' +
        '<select id="pdurationType" name="durationType" disabled class="time1">' +
        '<option value="0" selected>不限</option>' +
        '<option value="1">长期</option>' +
        '<option value="2">期限</option>' +
        '<option value="3">解密日期</option>' +
        '<option value="4">条件</option>' +
        '</select>' +
        '<div class="ptimes ptime1" style="display: inline-block"></div>' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span style="letter-spacing: 0;">主定密单位：</span><input style="width:560px;" type="text"readonly value="' + result.fileMajorUnit.name + '"/></td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span style="letter-spacing: 0;">辅定密单位：</span>' +
        '<div class="list-major-input" style="border-left: 0;height: 37px;display:inline-block;width: 80%">' +
        '<div class="i_inp" style="overflow-y: auto;width: 560px;height: 40px;top:10px;">' +
        '     <span class="ui-sortable pdisp " style="font-size: 12px;">' +
        '      </span>' +
        '   </div>' +
        '</div>' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span> 定密依据：</span>' +
        '<select id="pbasisType" name="basisType" disabled>' +
        '<option value="0">确定性定密</option>' +
        ' <option value="1">不明确事项</option>' +
        '  <option value="2">无权定密事项</option>' +
        '   <option value="3">派生定密</option>' +
        '</select>' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;">' +
        '<div id="pcontent" class="list-name-select" style="border-left: 0;height: 37px;display:inline-block;margin-left:75px;">' +
        '<div class="i_inpBasis" style="width:560px;overflow-y: auto;height: 40px;">' +
        '<textarea id="pid_basisDescription" style="width: 550px;height: 35px;overflow-y: auto;color:#000;"></textarea>' +
        '</span>' +
        '</div>' +
        '</div>' +
        '</td>' +
        '</tr>' +
        ' <tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span>知悉范围：</span>' +
        '<div class="box" style="border: 0;display: inline-block;">' +
        '<div class="box-body" style="background:#fff;">' +
        '<div class="tabbable header-tabs">' +
        '<ul class="nav nav-tabs" style="top: 0;">' +
        '<li class="clickpList active"><input type="hidden" value="0"/><a href="#box_tab5" data-toggle="tab">' +
        ' <i class="fa fa-cog"></i> <span class="hidden-inline-mobile">列举方式</span></a></li>' +
        '<li class="choosepDesc"><input type="hidden" value="1"/><a href="#box_tab4" data-toggle="tab">' +
        '<i class="fa fa-cog"></i> ' +
        ' <span class="hidden-inline-mobile">描述方式</span></a></li>' +
        '</ul>' +
        '<div class="tab-content"  style="margin-top: 0px">' +
        '<div class="tab-pane fade in active"><input type="hidden" id="iscopeValue"/>' +
        '<textarea id="ptooltips" rows="2" cols="100" readonly class="chooseList" style="resize:none"></textarea>' +
        '<textarea id="pscopeDesc" rows="2" cols="100"  readonly style="display: none;resize:none">' + result.fileScopeDesc + '</textarea>' +
        ' </div>' +
        ' </div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<td  class="rowname" style="border-right: 1px solid #4777c8;"><span>安全控制：</span>' +
        '<div class="ppermission" style="display:inline-block;">' +
        '</div>' +
        '</td>' +
        '</tr>' +
        ' <tr>' +
        '<td class="rowname" style="border-right: 1px solid #4777c8;"><span>扩展属性：</span>' +
        '<span>文号：</span>' +
        '<input value="' + result.issueNumber + '" readonly name="issueNumber" type="text" />' +
        '<span> 份号：</span>' +
        '<input readonly name="docNumber" type="text" value="' + result.docNumber + '"/>' +
        '<span>份数：</span><input readonly name="duplicationAmount" type="text" value="' + result.duplicationAmount + '"/>' +
        '</td>' +
        '</tr>' +
        
        '<tr>'+
	        '<td class="rowname" colspan="2" style="border-right: 1px solid #4777c8;">' +
	            '<span>业务属性：</span>' +
	            
	            '<textarea style="overflow-y: auto;color:#000;" rows="3" cols="89">'+result.business+'</textarea>' +
	        '</td>' +
	    '</tr>' +
        '</table>' +
        '<nav class="text-right">' +
        '<div class="btn-group" style="margin-top: 5px;margin-right: 45px;">' +
        '<button type="button" class="btn btn-success" onclick="downfile(this.name,1)" name=' + result.fid + '>下载</button>' +
        '<button type="button" class="btn btn-primary" onclick="gotoback(this.name,' + filter + ')" name=' + result.fid + '>返回</button>' +
        '</div>' +
        '</nav></div>';

    if (filter == 3) {
        $(".decryptInfoAll").append(strinfo);
    } else if(filter == 4){
        $(".declassifiedInfoAll").append(strinfo);
    }else {
		$(".ledgermanagementAll").append(strinfo);
	}
        

    //保密期限
    $("#pdurationType option[value='" + result.durationType + "']").attr("selected", true)
    filepScoped(result);
    pMinor(result);
    pValidPeriod(result);
    //定密依据
    $("#pbasisType option[value='" + result.basisType + "']").attr("selected", true)
    getpBasis(result);
    fileHistory(result);
    filePermissionData(result.permission);


    //点击描述
    $(".choosepDesc").unbind("click").click(function () {
        $("#ptooltips").hide()
        $("#pscopeDesc").show()
    });

    //点击列表选择
    $(".clickpList").unbind("click").click(function () {

        $("#pscopeDesc").hide();
        $('#ptooltips').show();
    })

}

//详情
function fileInfo(fid, filter) {
        $(".inboxAll").hide();
        $(".ledgermanagementAll").show();
    $("#" + filter + fid).show();
}
//返回
function gotoback(fid, filter) {
	if (filter == 3) {
        $(".decryptAll").show();
        $(".decryptInfoAll").hide();
        $(".decryptHistory").hide();
        $(".decrypttrack").hide();
    } else if(filter == 4){
        $(".declassifiedAll").show();
        $(".declassifiedInfoAll").hide();
        $(".declassifiedHistory").hide();
        $(".declassifiedtrack").hide();
    } else{
		$(".inboxAll").show();
        $(".ledgermanagementAll").hide();
        $(".ledgermanagementHistory").hide();
        $(".ledgermanagementtrack").hide();
	}
        
}

//下载文件
function downfile(t, type) {

    //获取表单
    var url = host + "/dcms/api/v1/file/downloadFileById";
    var fid = $(t).find("input").val();
    if (type == 1) {
        fid = t;
    }
    url = url + "?token=" + token + "&fid=" + fid;
    var excelhref = $("#excelhref");
    excelhref.attr("href", url);
    $("#excel").click();


}


//文件历史
function fileHistory(result) {
    var historyList = result.historyList;
    var flag = true //区分当前状态是展开状态还是收起状态，ture为收起状态，false为展开状态
    $(".titleBox").click(function () {
        if (flag) {
            $(".bigBox").slideDown(500);   //展开
            flag = false
        } else {
            $(".bigBox").slideUp(500);   //收起
            flag = true
        }
    })
}

//保密期限
function pValidPeriod(result) {

    if (result.durationType == 2) {
        var str = '<input type="text" id="ptime" readonly style="margin-right: 5px;width: 36px;display: inline-block;margin-left: 5px; border-radius: 3px; border: 0; margin-top: 3px; height: 20px;" >' +
            '<select id="ptime2" disabled style="display:inline-block;width: 36px;" ><option>年</option><option>月</option><option>日</option></select>';

    }
    if (result.durationType == 3) {
        var str = '<input style="margin-left: 5px;height: 23px" readonly type="text" class="laydate-icon"  value="' + getDate(result.fileDecryptTime) + '">';
    }
    if (result.durationType == 4) {

        var str = '<input  style="width: 40%;margin-left: 5px;height: 23px" readonly type="text" value="' + result.durationDescription + '">';
    }
    $(".ptimes").empty();
    $(".ptimes").append(str)


    var str = "";

    if (result.durationType == 2) {
        //获取到传过来的期限类型
        var applyValidPeriod = result.fileValidPeriod;
        var arra = applyValidPeriod.split("");
        if (parseInt(arra[0] + arra[1]) > 0) {
            $("#ptime").val(arra[0] + arra[1]);
            $("#ptime2").find("option").eq(0).attr("selected", true);

        }
        if (parseInt(arra[2] + arra[3]) > 0) {
            $("#ptime").val(arra[2] + arra[3]);
            $("#ptime2").find("option").eq(1).attr("selected", true);

        }
        if (parseInt(arra[4] + arra[5]) > 0) {
            $("#ptime").val(arra[4] + arra[5]);
            $("#ptime2").find("option").eq(2).attr("selected", true);

        }

    }
    return str;
}

//辅助定密单位
function pMinor(unit) {
    var units = unit.fileMinorUnit;
    $(".pdisp").empty();
    if (units.length > 0) {
        for (var j = 0; j < units.length; j++) {
            // var rds=$("#unitNo"+units[j].unitNo).text();
            var rds = "[" + units[j].unitNo + "]" + units[j].name;

            var parten = /^\s*$/;
            if (parten.test(rds)) {
            } else {
                var sortable = $(".ui-sortable");
                $(".pdisp").append('<span id="dc" class="sort"  style="white-space:nowrap; float:left; display:block;   margin:5px;   height:22px; font-size:12px; "><div  class="dartUnit"  style="padding:0px 6px; cursor:move; height:22px; display:block; float:left;   line-height:22px;">' + rds + '</div></span>').sortable("refresh");
                //$("##unitNo option:selected").attr("value","")
            }
        }
    }
}

//知悉范围人
function filepScoped(result) {

    var scopes = result.fileScope;
    var names = '';
    for (var i = 0; i < scopes.length; i++) {
        names += scopes[i].name + ','
    }
    $("#ptooltips").text(names);
    $('#ptooltips').pt({
        position: 'b', // 默认属性值
        align: 'r',	   // 默认属性值
        arrow: true,
        content: $('#ptooltips').val()
    });
}

//如果文件有默认权限，就有初始数据
function filePermissionData(dispatch) {

    var str = '';

    if (dispatch.contentRead == 1) {
        str += '<input type="checkbox"  name="contentRead" checked>内容阅读';
    } else {
        str += '<input type="checkbox"  name="contentRead" >内容阅读';
    }
    if (dispatch.contentModify == 1) {
        str += '<input type="checkbox"  name="contentModify" checked>内容修改';
    } else {
        str += '<input type="checkbox"  name="contentModify" >内容修改';
    }
    if (dispatch.contentPrint == 1) {
        str += '<input value="1" type="checkbox"  name="contentPrint" checked>打印';
    } else {
        str += '<input value="1" type="checkbox"  name="contentPrint" >打印';
    }
    if (dispatch.contentPrintHideWater == 1) {
        str += '<input type="checkbox"  name="contentPrintHideWater" checked>打印时隐藏水印';
    } else {
        str += '<input type="checkbox"  name="contentPrintHideWater" >打印时隐藏水印';
    }

    if (dispatch.contentScreenShot == 1) {
        str += '<input type="checkbox"  name="contentScreenShot" checked>截屏';
    } else {
        str += '<input type="checkbox"  name="contentScreenShot" >截屏';
    }
    if (dispatch.contentCopy == 1) {
        str += '<input type="checkbox"  name="contentCopy" checked>内容拷贝';
    } else {
        str += '<input type="checkbox"  name="contentCopy" >内容拷贝';
    }
    if (dispatch.fileCopy == 1) {
        str += '<input type="checkbox"  name="fileCopy" checked>文件拷贝';
    } else {
        str += '<input type="checkbox"  name="fileCopy" >文件拷贝';
    }

    if (dispatch.fileSaveCopy == 1) {
        str += '<input type="checkbox"  name="fileSaveCopy" checked>另存副本';
    } else {
        str += '<input type="checkbox"  name="fileSaveCopy" >另存副本';
    }
    $(".ppermission").empty();
    $(".ppermission").append(str);
}

//定密依据
function getpBasis(result) {
	//初始化
    var applyBasis = result.basis;
    $("#pid_basisDescription").empty();
    var rds='';
    for(var i=0;i<applyBasis.length;i++){
        var basisLevel =applyBasis[i].basisLevel;

        if (i > 0) {
        	rds += "\r\n";
        }
        rds += '['+parseLevelToName(basisLevel)+']'+applyBasis[i].basisContent;
       
      //  $(".list-name-select").height($(".i_inpBasis").height()+1);
    }
    $("#pid_basisDescription").val(rds);

}

//查询
function queryby(filter) {
	getPrivateList(0,filter);
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