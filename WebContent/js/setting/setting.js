//绑定新浪微博
function bindOauth(){
	document.location.href = ctx+"/ologin/auth/sina?operate=bindOauth";
}

/**
 * 修改会员密码
 */
jQuery.validator.addMethod("checkPassword",function(value, element) {
	var myreg = /^[^\s]{6,40}$/;
	if (value != '') {if (!myreg.test(value)) {return false;}};
	return true;}, "请输入有效密码"); 
$('#editpwd_form').ajaxForm({
	dataType : "json",
    beforeSubmit: function(a,f,o) {
    	$('#editpwd_form').validate({
    		onfocusin:true,
    		rules : {
    			'oldpwd' : {
    				required : true,
    				// xu	rangelength: [6,18],
    				checkPassword:true
    			},
    			'newpwd' : {
    				required : true,
    				rangelength: [6,18],
    				checkPassword:true
    			},
    			'newrepwd' : {
    				equalTo:"#_newpwd"
    			}
    		},
    		messages : {
    			'oldpwd' : {
    				required : "请输入旧密码",
    			//xu	rangelength: "旧密码必须由6-18个字符组成"
    			},
    			'newpwd' : {
    				required : "请输入密码",
    				rangelength: "新密码必须由6-18个字符组成"
    			},
    			'newrepwd' : {
    				equalTo:"新密码与重复输入密码不一致"
    			}
    		}
    	});
    	if ($('#editpwd_form').valid()) {
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					$('#setErrorMsg').hide();
					$("input[name=oldpwd]").val("");
					$("input[name=newpwd]").val("");
					$("input[name=newrepwd]").val("");
					showMsgPanel();
				} else {
					//登录超时
					if(data.msg == 20003)
					{
						showLoginDialog(function (data)
						{
							closeLoginDialog();
							$('#editpwd_form').submit();
						},null,true);
						return;
					}else{
						$('#setErrorMsg').hide();
						$('#setErrorMsg').html(data.msg + '<em class=\"error_arrow\"></em>');
						$('#setErrorMsg').show("fast");
					}	
				}
			}
    		
    	}
    });
