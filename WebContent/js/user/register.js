/**
 * 更新激活码
 */
function updateValidateCode(){
	document.getElementById('checkImage').src=ctx+'/user/getValidateCode?rand='+Math.random(1000);
}

/**
 * 重新发送激活邮件
 */
function resendmail(){
	var param = new Object;
	param.userid=$('#userid_hide').val();
	jQuery.ajax({
		type : 'POST',
		url : ctx+"/user/resendActivatedMail",
		//contentType : "application/json",
		data : param,
		success: function(data, textStatus) {
			if (data) {
				alert(data.msg);
			}
		}
	});
}

/**
 * ajaxform表单提交  
 */
jQuery.validator.addMethod("checkEmail",function(value, element) {
	//var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var myreg = /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
	if (value != '') {if (!myreg.test(value)) {return false;}};
	return true;}, "请输入有效的邮箱地址，如:abc@abc.com");    
jQuery.validator.addMethod("checkPassword",function(value, element) {
	var myreg = /^[^\s]{6,18}$/;
	if (value != '') {if (!myreg.test(value)) {return false;}};
	return true;}, "密码为6-16位，请区分大小写"); 
jQuery.validator.addMethod("checkName",function(value, element) {
//	var myreg = /^[\u4e00-\u9fa5\A-Za-z]+$/;
	var myreg = /^[\u4e00-\u9fa5\A-Za-z\s]+$/;
	if (value != '') {if (!myreg.test(value)) {return false;}};
	return true;}, "请输入你的真实姓名"); 
//添加转换小写功能
jQuery.validator.addMethod("toLowerCase", function(value, element) {
	value = $.trim(String(value));//去空
	if(value==""){element.value = ""; return this.optional(element)|| true;}
	if(value.toLowerCase()!=value) element.value = value.toLowerCase();
	return this.optional(element)|| true;
	}, "");
$('#registerform').ajaxForm({
    beforeSubmit: function(a,f,o) {
    	$('#registerform').validate({
    		onfocusin:true,
    		rules : {
    			'email' : {
    				required : true,
    				maxlength: 128,
    				checkEmail:true, 
    				toLowerCase:true,
    				email: true
    			},
    			'password' : {
    				required : true,
    				rangelength: [6,18],
    				checkPassword:true
    			},
    			'name' : {
    				required : true,
    				maxlength: 64,
    				minlength: 2,
    				checkName:true
    			},
    		/*	'vcodeclient':{
    				required: true
    			},*/
    			'isRead':{
    				required: true
    			}
    		},
    		messages : {
    			'email' : {
    				required : "请输入你的常用邮箱",
    				maxlength: "请输入你的常用邮箱",
    				email: "请输入有效的邮箱地址，如:abc@abc.com"
    			},
    			'password' : {
    				required : "密码为6-16位，请区分大小写",
    				rangelength: "密码为6-16位，请区分大小写"
    			},
    			'name' : {
    				required : "请输入你的真实姓名",
    				maxlength: "请输入你的真实姓名",
    				minlength: "请输入你的真实姓名"
    			},
    			/*'vcodeclient':{
    				required: "验证码不能为空!"
    			},*/
    			'isRead':{
    				required: "请先阅读协议"
    			}
    		},
    		errorPlacement: function (error, element) { //指定错误信息位置
    		      if (element.is(':radio') || element.is(':checkbox')) {
    		          var eid = element.attr('name');
    		          error.appendTo(element.parent());
    		      } else {
    		          error.insertAfter(element);
    		     }
    		 } 

    	});
    	if ($('#registerform').valid()) {
    		$(".loadingL").css({ display: "block", height: $(document).height() });
    	} else
    		return false;
    	},
    	success: function(data){
    		$(".loadingL").hide();
    		if (data) {
				if (data.success == true) {
					$('#errorMsg').hide();
					/*//变更校验码
					updateValidateCode();*/
					$('#userid_hide').val(data.content.userid);
					$('#tomail').html(data.content.email);
					var mailkey = data.content.email.split("@")[1];
					var mailServer;
					if(mailkey=="gmail.com"){
						mailServer = "http://mail.google.com";
					}else{
						mailServer = "http://mail."+ mailkey;
					}
					$('#tomaillink').attr("href",mailServer);
					$('#aTemp').click();
				} else {
					if(data.msg=="该邮箱地址已被注册！"){
						$('#_hadr').hide();
			    		$('#_hadr').show("fast");
					}else{
						$('#errorMsg').hide();
			    		$('#errorMsg').html(data.msg + '<em class="error_arrow"></em>');
			    		$('#errorMsg').show("fast");
					}
				}
			}
    	}
    });

$(function(){
	$("#emailV").focus(function(){
		$("#errorMsg").slideUp();
		$('#_hadr').hide("fast");
	});
})