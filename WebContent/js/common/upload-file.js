$('#_portrait_form').ajaxForm({
	dataType : "json",
	    beforeSubmit: function(a,f,o) {
	    	jQuery.validator.addMethod("checkimgtype",function(value, element) {
	    		var point = value.lastIndexOf(".");
	    		 var type = value.substr(point);
	    		  if(type==".jpg" || type==".gif" || type==".JPG" || type==".GIF" || type==".PNG" || type==".png"){
	    			  return true;
	    		  }else{
	    			  return false;
	    			  }
	    		}, "只能上传jpg、gif或者png格式的图片"); 
	    	$('#_portrait_form').validate({
	    		onfocusin:true,
	    		rules : {
	    			'portrait_file' : {
	    				required : true,
	    				checkimgtype:true
	    			}
	    		},
	    		messages : {
	    			'portrait_file' : {
	    				required : "请先选择文件"
	    			}
	    		}
	    	});
	    	if ($('#_portrait_form').valid()) {
	    		//验证文件类型
	    		var ajaxbg = $("#background,#progressBar"); 
	    		ajaxbg.show();
	    	} else
	    		return false;
	    	},
	    	success: function(data,status){
	    		if(data){
	    			var ajaxbg = $("#background,#progressBar"); 
		    		ajaxbg.hide(); 
	    			if(data.success==true){
	    				//回显图片
			    		 $('#_portrait_img').attr('src',data.content);
			    		 $('#user_portrait').attr('src',data.content);		    
			    		//更新头部图像
			    		$(".topA #uicon").attr("src",data.content);		 
		    		}else{
		    			//登录超时
						if(data.msg == 20003)
						{
							showLoginDialog(function (data)
									{
								closeLoginDialog();
								$('#_portrait_form').submit();
							},null,true);
							return;
						}else{
							 alert(data.msg);
						} 
		    		}
	    			
	    		}
	    		
	    		if(status=="success"){	    				
	    			
		    		
	    		}
	    		
	    	}
	    });

function uploadPic(picformid){
	$('#_portrait_form').submit();
}
/*function uploadPic(picformid){
	var param = new Object();
	param.tempPath=$('#_portrait_img').attr('src');
	$.ajax({
		   type: "POST",
		   url: ctx+"/user/upf.json",
		   data: param,
		   success: function(data){
		     if(data){
		    	 if(data.success==true){
		    		 $('#user_portrait').attr('src',data.content);	
		    	 }else{
		    		 alert(data.msg);
		    	 }
		     }
		   }
		}); 
} */

function openFileDialog(){
	//$('#_portrait_file').click();
}
function changesrc(){
	//$('#_portrait_form').submit();
}

/*var  img=null;
 function checkPic(picForm){
  var location=picForm.pic.value;
  if(location==""){
   alert("请先选择图片文件");
   return false;
  }
  var point = location.lastIndexOf(".");
  var type = location.substr(point);
  if(type==".jpg"||type==".gif"||type==".JPG"||type==".GIF"||type==".PNG"||type==".png"){
   img=document.createElement("img"); 
   img.src=location;
   if(img.fileSize>2048000){
    alert("图片尺寸请不要大于2MB");
    return false;
   }else
      return true;
  }
  else{
   alert("只能输入jpg、gif或者png格式的图片");
   return false;
  }
  return true;   
 }
 



*//**
 *//*
function fileChoose(file,nodeid){
	alert($('#_portrait_file').val());
	var returnMsg="";
	var validate=/\jpg|\png|\gif|\bmp/i;
	var filename = file.value;
	var newFileName = filename.split('.');        
	newFileName = newFileName[newFileName.length-1];  	
	if (newFileName.search(validate) == -1) { 
		returnMsg="选择失败:文件类型错误。仅支持 :*.jpg|*.gif|*.png|*.bmp";
		alert(returnMsg);
		file.value="";
		return false;
	}
	
	$("#"+nodeid).attr("src",filename);
	alert($("#"+nodeid).attr("src"));
}
*/
