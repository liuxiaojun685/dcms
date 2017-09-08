/**
 * Created by BESTSEC002 on 2017/5/27.
 */
$(function(){
    $("#pwd").blur(function(){
        if($("#pwd").val().trim() == ""){
            $(this).hide();
            $("#pwdSpan").show();
        }
    })
    $("#pwdSpan").focus(function(){
        $(this).css("display", "none");
        $("#pwd").show().focus();
    })
})