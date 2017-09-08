/**
 * Created by lenovo on 17-2-17.
 */
//var account="http://192.168.0.21:8080";

function navigate(t,parentId){
	$(t).nextAll().remove();
	InitTable(parentId,0);
}
$(function () {
	var parentId;
	InitTable(parentId,1);
});
function InitTable(parentId,type) {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/business/queryList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "parentId": parentId
        }),
        success: function (result) {
            result.onSuccess = function () {
            	if(type==1){
            		
            		if(parentId==undefined){
            			$("#navigate").append('<a href="javascript:void(0)" onclick=navigate(this,'+parentId+')>首页</a>');
            		}else {
            			var nav=$("#class"+parentId).val();
            			$("#navigate").append('<span>/</span><a href="javascript:void(0)" onclick=navigate(this,'+parentId+')>'+nav+'</a>');
            		}
            	}
            	$(".list").empty();
                var datas = result.bList;
                if (datas != undefined) {
                    for (var i = 0; i < datas.length; i++) {
                        var num = parseInt(i) + 1;
                        var str = '<tr>' +
                            '<td class="text-center">' + num + '</td>' +
                            '<td style="display:none;">' + datas[i].bId + '</td>' +
                            '<td class="text-center"><input id="class'+datas[i].bId+'" type="text" value="' + datas[i].name + '" class="form-control' +
                            ' text-center"' +
                            ' style="padding:3px;"/></td>' +
                            '<td class="text-center"><a href="javascript:void(0)"><i class="fa fa-ellipsis-h" style="margin-left:10px;" onclick="InitTable('+datas[i].bId+',1)"></i></a><a href="javascript:void(0)"><i class="fa fa-trash-o" style="margin-left:10px;" onclick="delClass('+datas[i].bId+')"></i></a><a href="javascript:void(0)"><i class="fa fa-save" style="margin-left:10px;" onclick="updateClass('+datas[i].bId+','+parentId+')"></i></a></td>' +
                            '</tr>';
                    	$(".list").append(str);
                    }
                }
                
                $(".add-business").unbind("click").click(function(){
                	$("#basisClass").val("");
            		layer.open({
            	        type: 1,
            	        title: '添加业务属性',
            	        shadeClose: true,
            	        shade: 0.3,
            	        skin: 'layui-layer-rim', //加上边框
            	        area: ['500px', '180px'], //宽高
            	        btn: ['确定', '取消'],
            	        yes: function (index, layero) {
            	            $(".error-span").text("");
            	            if ($("#basisClass").val() == '') {
            	                $(".error-span").text("业务属性不能为空！");
            	                return;
            	            } else {
            	            addClass(parentId);
            	            layer.close(index);
            	            }
            	        },
            	        content: $('#layer2')
            	    });
            	});
            }
            processResponse(result, 0);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

function delClass(bId) {
    layer.alert("确定删除该业务属性？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/business/delete",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "bId": bId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $("#class"+bId).parent().parent().remove();
                    }
                    processResponse(result, 1);
                },
                error: function (result) {
                    showGlobleTip("服务器异常，请联系管理员");
                }
            });
            layer.close(index);
        }
    });
}
//添加定密依据类型
function addClass(parentId) {

        $.ajax({
            type: "post",
            url: host + "/dcms/api/v1/business/add",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                "token": token,
                "parentId": parentId,
                "name": $("#basisClass").val()
            }),
            success: function (result) {
                result.onSuccess = function () {
                	InitTable(parentId,0);
                }
                processResponse(result, 1);
            },
            error: function (result) {
                showGlobleTip("服务器异常，请联系管理员");
            }
        });


}
//修改
function updateClass(bId,parentId) {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/business/update",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "bId": bId,
            "name": $("#class"+bId).val()
        }),
        success: function (result) {
            result.onSuccess = function () {
            	InitTable(parentId,0);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}




