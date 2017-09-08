/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-4-17
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
var token;
var index = 0;
$(function(){
    //取url参数
    token=GetQueryString("token");
    //获取数据
    initData();
});

//获取历史消息
function initData(){

    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/user/queryMessageHistory",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "pageNumber": index,
            "pageSize": 10

        }),
        success:function(result){
            if(result.code == 0) {
                $("#list").empty();
                var str = '';
                for(var i= 0;i<result.msgList.length;i++){
                    str+='<div class="eachLine" id="line'+result.msgList[i].msgId+'">'+
                        '<div class="showType">' ;
                    if(result.msgList[i].type=='wf'){
                        str+='<span class="typeWf">流</span>' ;
                    }else if(result.msgList[i].type=='apv'){
                        str+='<span class="typeWf">流</span>' ;
                    }else if(result.msgList[i].message.indexOf("您有一个新的补丁包")!=-1){
                        str+='<span class="typePatch">补</span>' ;
                    }else if(result.msgList[i].message.indexOf("您有一个文件已正式定密")!=-1){
                        str+='<span class="typeWf" style="font-family:"黑体"">定</span>' ;
                    }else if(result.msgList[i].message.indexOf("您有一个文件已签发")!=-1){
                        str+='<span class="typeDispatch">签</span>' ;
                    }else if(result.msgList[i].message.indexOf("您有一个文件发生密级变更")!=-1){
                        str+='<span class="typeChange">变</span>' ;
                    }else if(result.msgList[i].message.indexOf("您有一个文件已解密")!=-1){
                        str+='<span class="typePatch">解</span>' ;
                    }

                            str+='</div>'+
                        '<div style="float:left;width: 400px;" >'+
                            '<div style="height: 20px;">'+
                             '<div style="float:left;font-weight: bold;">';
                    if(result.msgList[i].type=='wf'){
                        str+= '流程通知';
                    }else if(result.msgList[i].type=='apv'){
                        str+= '流程通知';
                    }else if(result.msgList[i].type=='file'){
                        str+= '文件通知';
                    }else{
                        str+= '补丁通知';
                    }

                               str+= '</div><div style="float:right;">'+getLocalTime(result.msgList[i].createTime)+'</div>'+
                            '</div>'+
                        '<div class="message"><a target="_blank" href="'+host+result.msgList[i].url+'&token='+token+'">'+result.msgList[i].message+'</a></div>'+
                            '</div>'+
                    '<div class="remove" ><span style="cursor: pointer" onclick="dalMsg('+result.msgList[i].msgId+')"><img src="css/images/del.png"></span></div>'+
                    '</div>'
                }

                $("#list").append(str);

                //设置当前页
                $("#current").text(index+1);
                //总页数
                $("#total").text(result.totalPages);
                //上一页
                $(".prevPage").unbind("click").click(function(){
                    if(index > 0){
                        index--;
                        initData(index);
                    }

                });

                //下一页
                $(".nextPage").unbind("click").click(function(){
                    if(index < result.totalPages-1){
                        index++;
                        initData();
                    }

                });
                //跳转
                $(".gopage").unbind("click").click(function(){
                    goPage(result.totalPages);
                });
            }


        }
    });
}

//删除消息
function dalMsg(msgId){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/user/deleteMessageHistory",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token,
            "msgId": msgId
        }),
        success:function(result){
            if(result.code == 0){
                $("#line"+msgId).fadeOut(500,function(){
                    initData();
                });
            }
        }
    });
}
function goPage(total){
    var num = $("#need").val();
    var parten=/^\d{1,}$/;
    if(parten.test(num)){
        if(total >= num) {
            index = num-1;
            initData();
        }

    }
}


//刷新
function refresh(){
    $.ajax({
        type:"post",
        url:host+"/dcms/api/v1/user/refreshMessage",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        crossDomain:false,
        data:JSON.stringify({
            "token": token
        }),
        success:function(result){
            if(result.code == 0){
                index = 0;
                initData();
            }
        }
    })
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