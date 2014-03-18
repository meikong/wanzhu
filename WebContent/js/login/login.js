var loginCallBack = new Object();
//设置默认登录成功的回调函数
loginCallBack.loginsuccess = function(data){
	if(data.content.loginToUrl){
		document.location.href=data.content.loginToUrl;
	}else{
		document.location.href="index.html";
	}
};
/**
 * ajaxform表单提交
 */
jQuery.validator.addMethod("checkEmail",function(value, element) {
	var myreg =  /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
	if (value != '') {if (!myreg.test(value)) {return false;}};
	return true;}, "请输入有效邮箱");   
//添加转换小写功能
jQuery.validator.addMethod("toLowerCase", function(value, element) {
	value = $.trim(String(value));//去空
	if(value==""){element.value = ""; return this.optional(element)|| true;}
	if(value.toLowerCase()!=value) element.value = value.toLowerCase();
	return this.optional(element)|| true;
	}, ""); 
$('#loginPageForm').validate({
	onfocusin:true,
	rules : {
		'email' : {
			required : true,
			maxlength: 128,
			checkEmail:true,
			toLowerCase : true,
			email: true
		},
		'password' : {
			required : true,
			rangelength: [6,18]
		}
	},
	messages : {
		'email' : {
			required : "请输入有效邮箱",
			maxlength: "请输入有效邮箱",
			email: "请输入有效邮箱"
		},
		'password' : {
			required : "请输入密码",
//			rangelength: "6~18个字符,由数字、英文字母和字符组成."
			rangelength: "6~18个字符"
		}
	}
});

$('#loginPageForm').ajaxForm({
    beforeSubmit: function(a,f,o) {
    	if ($('#loginPageForm').valid()) { 
    		if($('.checkBox').attr("checked")){
      			$('.checkBox').val(1);
      		}else{
      			$('.checkBox').val(0);
      		}
    		console.log($('.checkBox').val());
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					$('#loginErrorMsg').hide();
					//更新头部信息
					var appendStr = "<li class=\"special\"><a href=\""+ctx+"/user/i.html\" class=\"topA\"><img "+
						"src=\""+data.content.portrait+"\" id=\"uicon\" width=\"30\" height=\"30\" /> <em>"+data.content.name+"</em></a>"+
					"<ul><li><a href=\""+ctx+"/user/i.html\">我的主页</a></li><li><a href=\""+ctx+"/user/qs.html\">帐号设置</a></li>"+
					"<li><a href=\""+ctx+"/user/lo.html\">退出</a></li></ul></li>"+
				"<li class=\"special\"><a href=\"#\" class=\"topA\">消息 <strong></strong></a>"+
				"<ul><li><a href=\""+ctx+"/notification/notificationList.html\" class=\"clearfix\"><span id=\"unc\"></span>通知</a></li>"+
				"<li><a href=\""+ctx+"/message/messageList.html\" class=\"clearfix\"><span id=\"umc\"></span>私信</a></li>"+
				"</ul></li>";
					$(".topBar ul").html(appendStr);	
					jQuery.ajax({
						type : "POST",
						url : ctx + "/qumc.json",
						success : function(data) {
							if (data) {
								if (data.success == true) {
									if (data.content.uc > 0) {
										if(data.content.uc > 99){
											jQuery(".special .topA strong").html("<b>"+data.content.uc+"+"+"</b>");
										}else{
											jQuery(".special .topA strong").html("<b>"+data.content.uc+"</b>");
										}
										/*jQuery("#eicon").attr("src",
												ctx + "/images/icon_email02.gif");*/
									}
									if(data.content.unc>99){
										jQuery("#unc").html(data.content.unc + "+");
									}else if(data.content.unc==0){
										jQuery("#unc").html("");
									}else{
										jQuery("#unc").html(data.content.unc );
									}
									if(data.content.umc>99){
										jQuery("#umc").html(data.content.umc + "+");
									}else if(data.content.umc==0){
										jQuery("#umc").html("");
									}else{
										jQuery("#umc").html(data.content.umc );
									}
								} else {
									jQuery("#unc").html("");
									jQuery("#umc").html("");
								}
							}
						}
					});
					loginCallBack.loginsuccess(data);
				} else {
					$('#loginErrorMsg').hide();
					$('#loginErrorMsg').html(data.msg + '<em class=\"error_arrow\"></em>');
					$('#loginErrorMsg').show("fast");
					loginCallBack.loginfail(data);
				}
			}
    	}
    });

$("input[name='password'],input[name='email']").keydown(function() {
	$("#loginErrorMsg").hide();
});

/**
 * 登录控件
 * @param successcall  业务上成功后的回调函数，需要关闭窗口自己实现
 * @param failcall 业务上的失败回调函数，非网络连接不上
 * @param needOauthLoginBack 设置第三方帐户登录时是否需要回到本页,true=需要,false=不需要
 */
function showLoginDialog(successcall,failcall,needOauthLoginBack){
	//显示logindialog
	$('#loginlink').click();
	//保存回调参数到登录上下文
	if(successcall){
		loginCallBack.loginsuccess = successcall;//保存登录成功的回调函数
	}
	if(failcall){
		loginCallBack.loginfail = failcall;//保存登录失败的回调函数
	}	
	if(needOauthLoginBack){
		$("#oauthlink").attr("href",ctx+"/ologin/auth/sina?toUrl="+document.location.href);
	}else{
		$("#oauthlink").attr("href",ctx+"/ologin/auth/sina");
	}
	
}
 
/**
 * 关闭登录窗口
 */
 function closeLoginDialog(){
	tb_remove();
}
