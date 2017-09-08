/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 17-2-22
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 */
var account="http://47.93.78.186:8080";
var token1="222222";
//$(document).ready(function(){
    tree();

    $(".save-ok").click(function(){
        //定义一个数组
        var drmList=new Array();
        $(".list tr").each(function(){
            var vids=$(this).find("td").eq(0).find("input").val();
            //一个一个来
            var drms={
                "contentRead":isChecked($(this),'contentRead'),
                "contentReadWatermark":isChecked($(this),'contentReadWatermark'),
                "contentPrint":isChecked($(this),'contentPrint'),
                "contentPrintWatermark":isChecked($(this),'contentPrintWatermark'),
                "contentModify":isChecked($(this),'contentModify'),
                "contentScreenShot":isChecked($(this),'contentScreenShot'),
                "contentCopy":isChecked($(this),'contentCopy'),
                "fileCopy":isChecked($(this),'fileCopy'),
                "fileDelete":isChecked($(this),'fileDelete'),
                "fileSaveCopy":isChecked($(this),'fileSaveCopy'),
                "fileOpenOffline":isChecked($(this),'fileOpenOffline'),
                "fileAuthorization":isChecked($(this),'fileAuthorization'),
                "contentFullControl":null,
                "fileLevelDecide":null,
                "fileLevelChange":null,
                "fileDispatch":null,
                "fileDecrypt":null,
                "fileUnbunding":null,
                "varIdType":isvarIdType(vids),
                "varId":vids
            }
            drmList.push(drms);
        });
        $.ajax({
            url:account +"/dcms/api/v1/file/authorization",
            type:"POST",
            contentType: "application/json; charset=utf-8",
            data:JSON.stringify({
                "token": token1,
                "fid":"fid-0ff61c3a-de1f-425e-b597-7ae685aed7b6",
                "drmList":JSON.stringify(drmList)
            }),
            success:function(rusult){
                alert(rusult.code)
                if(rusult.code==0){
                    initData();
                }
            }
        })

    });
//});

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
    if(t.find("td").eq(1).find("input[name="+values+"]").is(':checked')){
        return 1;
    }else{
        return 0;
    }
}
//如果文件有默认权限，就有初始数据
function initData(){

    $.ajax({
        type:"POST",
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify({
            "token": token1,
            "fid":"fid-0ff61c3a-de1f-425e-b597-7ae685aed7b6"
        }),
        url:account +"/dcms/api/v1/file/queryDispatchList",
        dataType:"json",
        success:function(result){
            var str;
            if(result.code==0){
                for(var i=0;i<result.drmList.length;i++){
                    $("input[name="+result.drmList[i].varId+"]").attr("checked",true);
                     str+='<tr id="tr'+result.drmList[i].varId+'">'+
                        '<td>'+$("input[name="+result.drmList[i].varId+"]").val()+ '<input type="hidden" value='+result.drmList[i].varId+'></td>'+
                        '<td>'+
                            '<div class="checkbox">'+
                                '<label>';
                        if(result.drmList[i].contentRead==0){
                           str+= '<input type="checkbox"  name="contentRead" checked>内容阅读';
                        }else{
                            str+= '<input type="checkbox"  name="contentRead" >内容阅读';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentReadWatermark==1){
                            str+= '<input value="1" type="checkbox"  name="contentReadWatermark" checked>阅读时隐藏水印';
                        }else{
                            str+= '<input value="1" type="checkbox"  name="contentReadWatermark" >阅读时隐藏水印';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentPrint==1){
                            str+= '<input type="checkbox"  name="contentPrint" checked>内容打印';
                        }else{
                            str+= '<input type="checkbox"  name="contentPrint" >内容打印';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentPrintWatermark==1){
                            str+= '<input type="checkbox"  name="contentPrintWatermark" checked>打印时隐藏水印';
                        }else{
                            str+= '<input type="checkbox"  name="contentPrintWatermark" >打印时隐藏水印';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentModify==1){
                            str+= '<input type="checkbox"  name="contentModify" checked>内容修改';
                        }else{
                            str+= '<input type="checkbox"  name="contentModify" >内容修改';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentScreenShot==1){
                            str+= '<input type="checkbox"  name="contentScreenShot" checked>内容截屏';
                        }else{
                            str+= '<input type="checkbox"  name="contentScreenShot" >内容截屏';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].contentCopy==1){
                            str+= '<input type="checkbox"  name="contentCopy" checked>内容拷贝';
                        }else{
                            str+= '<input type="checkbox"  name="contentCopy" >内容拷贝';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].fileCopy==1){
                            str+= '<input type="checkbox"  name="fileCopy" checked>文件拷贝';
                        }else{
                            str+= '<input type="checkbox"  name="fileCopy" >文件拷贝';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].fileDelete==1){
                            str+= '<input type="checkbox"  name="fileDelete" checked>文件删除';
                        }else{
                            str+= '<input type="checkbox"  name="fileDelete" >文件删除';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].fileSaveCopy==1){
                            str+= '<input type="checkbox"  name="fileSaveCopy" checked>另存副本';
                        }else{
                            str+= '<input type="checkbox"  name="fileSaveCopy" >另存副本';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].fileOpenOffline==1){
                            str+= '<input type="checkbox"  name="fileOpenOffline" checked>离线打开';
                        }else{
                            str+= '<input type="checkbox"  name="fileOpenOffline" >离线打开';
                        }
                        str+='</label>'+
                            '<label>';
                        if(result.drmList[i].fileAuthorization==1){
                            str+= '<input type="checkbox"  name="fileAuthorization" checked>文件授权';
                        }else{
                            str+= '<input type="checkbox"  name="fileAuthorization" >文件授权';
                        }
                        str+='</label>'+
                            '</div>'+
                        '</td>'+


                    '</tr>'
                }
                $(".list").append(str);
            }

        }
    });
}
//调用树接口
function tree(name){
    $.ajax({
        type:"POST",
        data: JSON.stringify({
            "token": token1,
            "hasUser": 1,
            "hasGroup": 1,
            "keyword":name ,
            "fileLevel":""
        }),
        url:account +"/dcms/api/v1/department/queryTree",
        dataType: "json",
        success : function(data) {

            $("#browser").append(maketree(data));
            $("#browser").treeview({
                collapsed:false,
                add:$("#browser").html
            });
            initData();

        }
    });

}

//生成树
function maketree(data) {

    var str = '<li><span class="dep"><input type="checkbox" onclick="clickget(this.id)" value='+data.name+' id='+data.did+'>'+data.name+'</span>';
    for(var i = 0; i<data.childGroupList.length;i++) {
        str += '<ul>'+
            '<li><span class="group"><input type="checkbox" onclick="clickget(this.id)" value='+data.childGroupList[i].name+' id='+data.childGroupList[i].gid+'>'+data.childGroupList[i].name+'</span>';
        var userlength = data.childGroupList[i].childUserList.length;
        for(var j = 0; j<userlength;j++) {
            var ob = data.childGroupList[i].childUserList[j];
            str += '<ul>'+
                '<li><span class="user"><input type="checkbox" onclick="clickget(this.name,this)" value='+ob.name+' name='+ob.uid+'>'+ob.name+'</span>'+
                '</li>'+
                '</ul>'

        }
        str += '</li>'+
            '</ul>'
    }

    for(var i = 0; i<data.childUserList.length;i++) {
        str += '<ul>'+
            '<li><span class="user"><input type="checkbox" onclick="clickget(this.name,this)" value='+data.childUserList[i].name+' name='+data.childUserList[i].uid+'>'+data.childUserList[i].name+'</span>'+
            '</li>'+
            '</ul>'

    }

    for(var i = 0; i<data.childDepartmentList.length;i++) {
        str += '<ul>'
            + maketree(data.childDepartmentList[i])+
            '</ul>'
    }
    str+='</li>'
    return str;

}

//单击CheckBox
function clickget(only,t){
    var str;
    if(only.indexOf('uid') != -1){
        if($(t).is(':checked')){
            $("input[name="+only+"]").prop("checked",true);
            str+='<tr id="tr'+only+'">'+
                '<td>'+$("input[name="+only+"]").val()+ '<input type="hidden" value='+only+'></td>'+
                '<td>'+
                createCheckBox()+

                '</td>'+

                '</tr>'

            $(".list").append(str);
        }else{
            $("input[name="+only+"]").prop("checked",false);
            $("#tr"+only).remove();
        }

    }else{
        if($("#"+only).is(':checked')){
            str+='<tr id="tr'+only+'">'+
                '<td>'+$("#"+only).val()+ '<input type="hidden" value='+only+'></td>'+
                '<td>'+
                createCheckBox()+

                '</td>'+

                '</tr>'

            $(".list").append(str);

        }else{
            $("#tr"+only).remove();
        }
    }


}

function createCheckBox(){
    var str = '<div class="checkbox">'+
        '<label>'+
        '<input type="checkbox"  name="contentRead">内容阅读'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentReadWatermark" >阅读时隐藏水印'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentPrint">内容打印'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentPrintWatermark">打印时隐藏水印'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentModify">内容修改'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentScreenShot">内容截屏'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="contentCopy">内容拷贝'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileCopy">文件拷贝'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileDelete">文件删除'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileSaveCopy">另存副本'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileOpenOffline">离线打开'+
        '</label>'+
        '<label>'+
        '<input type="checkbox"  name="fileAuthorization">文件授权'+
        '</label>'+
        '</div>';
    return str;
}

