/**
 * Created by lenovo on 17-2-28.
 */

var account="http://localhost:8080";
var token="654321";


function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

$(function(){
    //初始化我的待办项




    if(GetQueryString("id")=="zsdm"){
        $(".index").text("正式定密")
        sectime();
        MajorList()
        Minor()
        basis()
      
    }
    if(GetQueryString("id")=="zsdmsq"){
        $(".index").text("正式定密申请")
        sectime();
        MajorList()
        Minor()
        basis()
      
    }
    if(GetQueryString("id")=="mjbg"){
        $(".index").text("密级变更")
        sectime();
        $(".dmdw").hide()
        $(".fffw").hide()
       
    }
    if(GetQueryString("id")=="mjbgsq"){
        $(".index").text("密级变更申请")
        sectime();
        $(".dmdw").hide()
        $(".fffw").hide()
        $(".sqly").show()
       
    }
    if(GetQueryString("id")=="wjqf"){
        $(".index").text("文件签发")
        $(".bmqx").hide()
        $(".mmdj").hide()
        $(".dmdw").hide()
       
    }
    if(GetQueryString("id")=="wjqfsq"){
        $(".index").text("文件签发申请")
        $(".bmqx").hide()
        $(".mmdj").hide()
        $(".dmdw").hide()
        $(".sqly").show()
       
    }
    if(GetQueryString("id")=="wjjm"){
        $(".index").text("文件解密")
        $(".bmqx").hide()
        $(".mmdj").hide()
        $(".dmdw").hide()
        $(".fffw").hide()
       
    }
    if(GetQueryString("id")=="wjjmsq"){
        $(".index").text("文件解密申请")
        $(".bmqx").hide()
        $(".mmdj").hide()
        $(".dmdw").hide()
        $(".fffw").hide()
        $(".sqly").show()
       
    }

});
//时间验证
function check(n)
{
    var te= /^[0-9]+[0-9]*]*$/
    if (!te.test(n))
    {
        layer.alert('请输入正整数', {
            icon: 1,
            skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
        })
        return false

    }
}



//下拉菜单
function jump(){
    sectime()
}

//保密期限
function sectime(){

    $.ajax({
        type:"post",
        url:account+"/dcms/api/v1/securePolicy/queryValidPeriod",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:JSON.stringify({
            "token": token,
            "fileLevel": $("#ss option:selected").val()
        }),
        success:function(result){
            var time=result.validPeriod/3600000/365/24;

            if(time.toString().indexOf(".")==-1){
                $("#time").val(result.validPeriod/3600000/365/24)
                $("#shijian option[value='1']").attr("selected",true)
                $("#shijian option[value='2']").attr("selected",false)
            }else{
                $("#time").val(result.validPeriod/3600000/24)
                $("#shijian option[value='2']").attr("selected",true)
                $("#shijian option[value='1']").attr("selected",false)
            }



        },
        error:function(result){
        }
    })
}

//主定密单位
function MajorList(){
    $.ajax({
        type:"post",
        url:account+"/dcms/api/v1/unit/queryMajorList",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:JSON.stringify({
            "token": token

        }),
        success:function(result){
            $("#Major").text(result.name);
            $("#unitNo").text(result.unitNo);
        },
        error:function(result){
        }
    })
}
//辅助定密单位
function Minor(){
    $.ajax({
        type:"post",
        url:account+"/dcms/api/v1/unit/queryMinorList",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:JSON.stringify({
            "token": token,
            "pageNumber": 0,
            "pageSize": 10

        }),
        success:function(result){
            var sr='<select id="example-getting-started" multiple="multiple">'
            var dat=result.unitList
            for(var i=0;i<dat.length;i++){
                sr+='<option value="'+dat[i].unitNo+'" >'+dat[i].name+'</option>'
            }
            sr+='</select>';
            $("#Minor").append(sr);
            $('#example-getting-started').multiselect();
        },
        error:function(result){
        }
    })
}
//定密依据
function basis(){
    $.ajax({
        type:"post",
        url:account+"/dcms/api/v1/basis/queryList",
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        data:JSON.stringify({
            "token": token,
            "pageNumber": 0,
            "pageSize": 10

        }),
        success:function(result){
            var sr='<select  style="text-align:center">'
            var dat=result.unitList
            for(var i=0;i<dat.length;i++){
                sr+='<option value="'+dat[i].basisId+'" >'+dat[i].basis+'</option>'
            }
            sr+='</select>';
            $("#basis").append(sr);
        },
        error:function(result){
        }
    })
}
function fileshow(){

    $(".filetree").show()
    $(".fileding").hide()

}

function filetree2(){

    $(".filetree").hide()
    $(".fileding").show()
}
//提交表单
function tjsj(){

    $("input[name=fid]").val($("#fid").text())
    var da=$("#shijian option:selected").val()
    var time;
//    check($("#time").val())
    if(da==1){
        time=$("#time").val()*3600000*365*24
    }
    if(da==2){
        time=$("#time").val()*3600000*24
    }
    $("input[name=fileValidPeriod]").val(time)
    var fileMajorUnit  = {
        unitNo:$("#unitNo").text(),
        name:$("#Major").text()
    };



    $("input[name=fileMajorUnit]").val($("#Major").text())
    var Minor=$('#Minor button').attr('title').split(",")
    if(Minor==""||Minor==null){
        Minor=[]
    }

    $("input[name=fileMinorUnit]").val(JSON.stringify(Minor))



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



    var te= /^[0-9]+[0-9]*]*$/
    if (!te.test($("#time").val()))
    {
        layer.alert('请输入正整数', {
            icon: 1,
            skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
        })}else{
        var form = $("form[name=fileForm]");
        var options  = {
            url:'/dcms/fileUpload',
            contentType: "application/json; charset=utf-8",
            type:'post',
            success:function(data)
            {
                var jsondata = eval("("+data+")");
                alert(data)
                $.ajax({
                    type:"post",
                    url:account+"/dcms/api/v1/workFlow/create",
                    contentType: "application/json; charset=utf-8",
                    dataType:"json",
                    data:JSON.stringify({
                        "token":"222222",
                        "workFlowType":2,
                        "srcFid": $("#fid").text(),
                        "applyFileLevel": $("select[name=fileLevel] option:selected").val(),
                        "applyValidPeriod": time,
                        "applyDispatchList":drmList,
                        "applyBasis": $("#basis option:selected").text(),
                        "applyMinorUnit":Minor,
                        "createComment": "不开心"

                    }),
                    success:function(result){
                        alert(result.code)
                    },
                    error:function(result){
                    }
                })

            },
            error:function(data){
                alert("失败")
            }
        };
        form.ajaxSubmit(options);
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