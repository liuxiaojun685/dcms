/**
 * Created by lenovo on 17-2-17.
 */
//var account="http://192.168.0.21:8080";
$(function () {
	InitTable(0);
});
function InitTable(type) {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/basis/queryBasisClassList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "basisType": type
        }),
        success: function (result) {
            result.onSuccess = function () {
            	$(".potions").show();
            	$(".basis").hide();
            	$(".item").hide();
            	$("#navigate").empty();
            	$("#navigate").append('<a href="javascript:void(0)" onclick=InitTable('+type+')>首页</a>');
            	if(type==0){
            		$(".list0").empty();
            	}
            	if(type==1){
            		$(".list1").empty();
            	}
            	if(type==2){
            		$(".list2").empty();
            	}
            	if(type==3){
            		$(".list3").empty();
            	}
                
                var datas = result.basisClassList;
                if (datas != undefined) {
                    for (var i = 0; i < datas.length; i++) {
                        var num = parseInt(i) + 1;
                        var str = '<tr>' +
                            '<td>' + num + '</td>' +
                            '<td style="display:none;">' + datas[i].basisId + '</td>' +
                            '<td><input id="class'+datas[i].basisId+'" type="text" value="' + datas[i].basisClass + '" class="form-control' +
                            ' text-center"' +
                            ' style="padding:3px;"/></td>' +
                            '<td><a href="javascript:void(0)"><i class="fa fa-ellipsis-h" style="margin-left:10px;" onclick="nextBasis('+datas[i].basisId+','+type+')"></i></a><a href="javascript:void(0)"><i class="fa fa-trash-o" style="margin-left:10px;" onclick="delClass('+datas[i].basisId+','+type+')"></i></a><a href="javascript:void(0)"><i class="fa fa-save" style="margin-left:10px;" onclick="updateClass('+datas[i].basisId+','+type+')"></i></a></td>' +
                            '</tr>';
                        if(type==0){
                    		$(".list0").append(str);
                    	}
                    	if(type==1){
                    		$(".list1").append(str);
                    	}
                    	if(type==2){
                    		$(".list2").append(str);
                    	}
                    	if(type==3){
                    		$(".list3").append(str);
                    	}
                    	
                    	
                    }
                }
                
                $(".add-fixed").unbind("click").click(function(){
                	$("#basisClass").val("");
            		layer.open({
            	        type: 1,
            	        title: '添加定密依据分类',
            	        shadeClose: true,
            	        shade: 0.3,
            	        skin: 'layui-layer-rim', //加上边框
            	        area: ['500px', '180px'], //宽高
            	        btn: ['确定', '取消'],
            	        yes: function (index, layero) {
            	            $(".error-span").text("");
            	            if ($("#basisClass").val() == '') {
            	                $(".error-span").text("定密依据分类不能为空！");
            	                return;
            	            } else {
            	            addClass(type);
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

function delClass(basisId,type) {
    layer.alert("确定删除该定密依据分类？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/basis/deleteBasisClass",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "basisId": basisId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $("#class"+basisId).parent().parent().remove();
                        InitTable(type);
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
function addClass(type) {

        $.ajax({
            type: "post",
            url: host + "/dcms/api/v1/basis/addBasisClass",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                "token": token,
                "basisClass": $("#basisClass").val(),
                "basisType": type
            }),
            success: function (result) {
                result.onSuccess = function () {
                	InitTable(type);
                }
                processResponse(result, 1);
            },
            error: function (result) {
                showGlobleTip("服务器异常，请联系管理员");
            }
        });


}
//修改
function updateClass(basisId,type) {
    $.ajax({
        type: "post",
        url: host + "/dcms/api/v1/basis/updateBasisClass",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "basisId": basisId,
            "basisClass": $("#class"+basisId).val()
        }),
        success: function (result) {
            result.onSuccess = function () {
            	InitTable(type);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

/**定密依据*/
//定密依据列表
function nextBasis(basisId, type){
	$.ajax({
    type: "post",
    url: host + "/dcms/api/v1/basis/queryBasisList",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify({
        "token": token,
        "basisId": basisId
    }),
    success: function (result) {
        result.onSuccess = function () {
        	$(".potions").hide();
        	$(".basis").show();
        	$(".item").hide();
        	$("#navigate").empty();
        	$("#navigate").append('<a href="javascript:void(0)" onclick=InitTable('+type+')>首页</a>/<a href="javascript:void(0)" onclick=nextBasis('+basisId+','+type+')>'+$("#class"+basisId).val()+'</a>');
        	if(type==0){
        		$(".basis0").empty();
        	}
        	if(type==1){
        		$(".basis1").empty();
        	}
        	if(type==2){
        		$(".basis2").empty();
        	}
        	if(type==3){
        		$(".basis3").empty();
        	}
            
            var datas = result.basisList;
            if (datas != undefined) {
                for (var i = 0; i < datas.length; i++) {
                	if(datas[i].basisName==''){
                		continue;
                	}
                    var num = parseInt(i) + 1;
                    var str = '<tr>' +
                        '<td>' + num + '</td>' +
                        '<td style="display:none;">' + datas[i].basisId + '</td>' +
                        '<td><input id="basis'+datas[i].basisId+'" type="text" value="' + datas[i].basisName + '" class="form-control' +
                        ' text-center"' +
                        ' style="padding:3px;"/></td>' +
                        '<td><a href="javascript:void(0)"><i class="fa fa-ellipsis-h" style="margin-left:10px;" onclick="nextItem('+datas[i].basisId+','+type+','+basisId+')"></i></a><a href="javascript:void(0)"><i class="fa fa-trash-o" style="margin-left:10px;" onclick="delBasis('+datas[i].basisId+','+type+')"></i></a><a href="javascript:void(0)"><i class="fa fa-save" style="margin-left:10px;" onclick="updateBasis('+datas[i].basisId+','+type+')"></i></a></td>' +
                        '</tr>';
                    if(type==0){
                		$(".basis0").append(str);
                	}
                	if(type==1){
                		$(".basis1").append(str);
                	}
                	if(type==2){
                		$(".basis2").append(str);
                	}
                	if(type==3){
                		$(".basis3").append(str);
                	}
                	
                	
                }
            }
            
            $(".add-basis").unbind("click").click(function(){
            	$("#basisContent").val("");
        		layer.open({
        	        type: 1,
        	        title: '添加定密依据内容',
        	        shadeClose: true,
        	        shade: 0.3,
        	        skin: 'layui-layer-rim', //加上边框
        	        area: ['500px', '180px'], //宽高
        	        btn: ['确定', '取消'],
        	        yes: function (index, layero) {
        	            $(".error-span").text("");
        	            if ($("#basisContent").val() == '') {
        	                $(".error-span").text("定密依据内容不能为空！");
        	                return;
        	            } else {
        	            addBasis(basisId,type);
        	            layer.close(index);
        	            }
        	        },
        	        content: $('#layer3')
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

//添加定密依据
function addBasis(basisId,type){
	$.ajax({
    type: "post",
    url: host + "/dcms/api/v1/basis/addBasis",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify({
        "token": token,
        "basisId": basisId,
        "basisName": $("#basisContent").val()
    }),
    success: function (result) {
        result.onSuccess = function () {
        	nextBasis(basisId, type);
        }
        processResponse(result, 1);
    },
    error: function (result) {
        showGlobleTip("服务器异常，请联系管理员");
    }
});
}

//修改定密依据
function updateBasis(basisId, type){
	$.ajax({
    type: "post",
    url: host + "/dcms/api/v1/basis/updateBasis",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    data: JSON.stringify({
        "token": token,
        "basisId": basisId,
        "basisName": $("#basis"+basisId).val()
    }),
    success: function (result) {
        result.onSuccess = function () {
        	nextBasis(basisId, type);
        }
        processResponse(result, 1);
    },
    error: function (result) {
        showGlobleTip("服务器异常，请联系管理员");
    }
});
}

//删除定密依据
function delBasis(basisId, type){
	layer.alert("确定删除该定密依据？", {
    icon: 3,
    skin: 'layer-ext-moon',
    btn: ['确定', '取消'],
    yes: function (index, layero) {
        $.ajax({
            type: "post",
            url: host + "/dcms/api/v1/basis/deleteBasis",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                "token": token,
                "basisId": basisId
            }),
            success: function (result) {
                result.onSuccess = function () {
                    $("#basis"+basisId).parent().parent().remove();
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


/**定密依据事项*/
//定密依据列表
function nextItem(basisId, type,parentId){
	$.ajax({
        type: "post",
        url: host + "/dcms/api/v1/basis/queryBasisItemList",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "basisId": basisId
        }),
        success: function (result) {
            result.onSuccess = function () {
            	$(".potions").hide();
            	$(".basis").hide();
            	$(".item").show();
            	$("#navigate").empty();
            	$("#navigate").append('<a href="javascript:void(0)" onclick=InitTable('+type+')>首页</a>/<a href="javascript:void(0)" onclick=nextBasis('+parentId+','+type+')>'+$("#class"+parentId).val()+'</a>/<a href="javascript:void(0)" onclick=nextItem('+basisId+','+type+','+parentId+')>'+$("#basis"+basisId).val()+'</a>');
            	if(type==0){
            		$(".item0").empty();
            	}
            	if(type==1){
            		$(".item1").empty();
            	}
            	if(type==2){
            		$(".item2").empty();
            	}
            	if(type==3){
            		$(".item3").empty();
            	}
                
                var datas = result.basisItemList;
                if (datas != undefined) {
                    for (var i = 0; i < datas.length; i++) {
                    	if(datas[i].basisItem==''){
                    		continue;
                    	}
                        var num = parseInt(i) + 1;
                        var str = '<tr>' +
                            '<td>' + num + '</td>' +
                            '<td style="display:none;">' + datas[i].basisId + '</td>' +
                            '<td><select id="basisLevel'+datas[i].basisId+'">';
                        if(datas[i].basisLevel==0){
                        	str+='<option value="0" selected>公开</option>'+
                        	'<option value="1">内部</option>'+
                        	'<option value="2">秘密</option>'+
                        	'<option value="3">机密</option>'+
                        	'<option value="4">绝密</option>';
                        }
                        if(datas[i].basisLevel==1){
                        	str+='<option value="0">公开</option>'+
                        	'<option value="1" selected>内部</option>'+
                        	'<option value="2">秘密</option>'+
                        	'<option value="3">机密</option>'+
                        	'<option value="4">绝密</option>';
                        }
                        if(datas[i].basisLevel==2){
                        	str+='<option value="0">公开</option>'+
                        	'<option value="1" selected>内部</option>'+
                        	'<option value="2">秘密</option>'+
                        	'<option value="3">机密</option>'+
                        	'<option value="4">绝密</option>';
                        }
                        if(datas[i].basisLevel==3){
                        	str+='<option value="0">公开</option>'+
                        	'<option value="1" selected>内部</option>'+
                        	'<option value="2">秘密</option>'+
                        	'<option value="3">机密</option>'+
                        	'<option value="4">绝密</option>';
                        }
                        if(datas[i].basisLevel==4){
                        	str+='<option value="0">公开</option>'+
                        	'<option value="1">内部</option>'+
                        	'<option value="2">秘密</option>'+
                        	'<option value="3">机密</option>'+
                        	'<option value="4" selected>绝密</option>';
                        }
                        
                            str+='</select></td>'+
                            '<td><input id="item'+datas[i].basisId+'" type="text" value="' + datas[i].basisItem + '" class="form-control' +
                            ' text-center"' +
                            ' style="padding:3px;"/></td>' +
                            '<td><a href="javascript:void(0)"><i class="fa fa-trash-o" style="margin-left:10px;" onclick="delItem('+datas[i].basisId+','+type+','+parentId+')"></i></a><a href="javascript:void(0)"><i class="fa fa-save" style="margin-left:10px;" onclick="updateItem('+datas[i].basisId+','+type+','+parentId+')"></i></a></td>' +
                            '</tr>';
                        if(type==0){
                    		$(".item0").append(str);
                    	}
                    	if(type==1){
                    		$(".item1").append(str);
                    	}
                    	if(type==2){
                    		$(".item2").append(str);
                    	}
                    	if(type==3){
                    		$(".item3").append(str);
                    	}
                    	
                    }
                }
                
                $(".add-item").unbind("click").click(function(){
                	$("#basisItem").val("");
            		layer.open({
            	        type: 1,
            	        title: '添加定密依据事项',
            	        shadeClose: true,
            	        shade: 0.3,
            	        skin: 'layui-layer-rim', //加上边框
            	        area: ['500px', '220px'], //宽高
            	        btn: ['确定', '取消'],
            	        yes: function (index, layero) {
            	            $(".error-span").text("");
            	            if ($("#basisItem").val() == '') {
            	                $(".error-span").text("定密依据事项不能为空！");
            	                return;
            	            } else {
            	            addItem(basisId,type,parentId);
            	            layer.close(index);
            	            }
            	        },
            	        content: $('#layer4')
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

//添加定密依据
function addItem(basisId,type,parentId){
	$.ajax({
        type: "post",
        url: host + "/dcms/api/v1/basis/addBasisItem",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "basisId": basisId,
            "basisLevel": $("#basisLevel option:selected").val(),
            "basisItem": $("#basisItem").val()
        }),
        success: function (result) {
            result.onSuccess = function () {
            	nextItem(basisId, type, parentId);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//修改定密依据
function updateItem(basisId, type, parentId){
	$.ajax({
        type: "post",
        url: host + "/dcms/api/v1/basis/updateBasisItem",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({
            "token": token,
            "basisId": basisId,
            "basisLevel": $("#basisLevel"+basisId+" option:selected").val(),
            "basisItem": $("#item"+basisId).val()
        }),
        success: function (result) {
            result.onSuccess = function () {
            	nextItem(basisId, type, parentId);
            }
            processResponse(result, 1);
        },
        error: function (result) {
            showGlobleTip("服务器异常，请联系管理员");
        }
    });
}

//删除定密依据
function delItem(basisId, type, parentId){
	layer.alert("确定删除该定密依据事项？", {
        icon: 3,
        skin: 'layer-ext-moon',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            $.ajax({
                type: "post",
                url: host + "/dcms/api/v1/basis/deleteBasisItem",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify({
                    "token": token,
                    "basisId": basisId
                }),
                success: function (result) {
                    result.onSuccess = function () {
                        $("#item"+basisId).parent().parent().remove();
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


