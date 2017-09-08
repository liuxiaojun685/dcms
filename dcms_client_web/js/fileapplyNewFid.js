/**
 * Created by lenovo on 17-2-28.
 */

var account="http://localhost:8080";
var token="222222";
$(function(){
    //初始化我的待办项
    newfid();
});

function newfid(){
    $.ajax({
        type:"post",
        url:account+"/dcms/api/v1/file/applyNewFid",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:JSON.stringify({
            "token": token

        }),
        success:function(result){
            $("#fid").text(result.fid)

        },
        error:function(result){

        }
    })
}
