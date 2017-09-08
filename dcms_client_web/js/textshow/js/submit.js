  
  function xianshi(){	
 
   
  var arr = new Array($(".disp:eq(0) span").length) 
  	 for(var i=0; i<$(".disp:eq(0) span").length; i++){
	 	  var ise = $(".disp:eq(0) span:eq("+i+") div").text() ;
       arr[i] = $(".disp:eq(0) span:eq("+i+") div").text()
	 } 
	vis = arr.join("#")
	 $("#xuke").attr("value",vis)
/*  ith = $("#xuke").val()
	 alert(ith) */
	  
	 var arr1 = new Array($(".disp:eq(1) span").length) 
  	 for(var i=0; i<$(".disp:eq(1) span").length; i++){
	 	  var ise = $(".disp:eq(1) span:eq("+i+") div").text() ;
       arr1[i] = $(".disp:eq(1) span:eq("+i+") div").text()
	 } 
	vis = arr1.join("#")
	 $("#yuyan").attr("value",vis)
/*  ith = $("#yuyan").val()
	 alert(ith) */
   
   
   }