/**
 * ajaxform表单提交
 */
$('#bind_form').ajaxForm({
    beforeSubmit: function(a,f,o) {
    	//添加转换小写功能
    	jQuery.validator.addMethod("toLowerCase", function(value, element) {
    		value = $.trim(String(value));//去空
    		if(value==""){element.value = ""; return this.optional(element)|| true;}
    		if(value.toLowerCase()!=value) element.value = value.toLowerCase();
    		return this.optional(element)|| true;
    		}, "");
    	jQuery.validator.addMethod("checkEmail",function(value, element) {
    		//var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    		var myreg = /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
    		if (value != '') {if (!myreg.test(value)) {return false;}};
    		return true;}, "请输入有效邮箱"); 
    	$('#bind_form').validate({
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
    				rangelength: [6,18]
    			},
    			'name' : {
    				required : true,
    				maxlength: 64,
    			}
    		},
    		messages : {
    			'email' : {
    				required : "请输入帐号邮箱",
    				maxlength: "请正确输入邮箱，最大长度为128字符",
    				email: "对不起，你输入的E-mail格式不正确，格式：you@163.com"
    			},
    			'password' : {
    				required : "请输入密码",
    				rangelength: "6~18个字符，由数字、英文字母和下划线组成"
    			},
    			'name' : {
    				required : "请输入用户名",
    				maxlength: "请正确输入用户名，最大长度为64字符",
    			}
    		}
    	});
    	if ($('#bind_form').valid()) {    		
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					$('#errorMsg').hide();
					if(data.content){
						document.location.href=data.content;
					}else{
						document.location.href=ctx+"/index.html";
					}
				} else {
					$('#errorMsg').hide();
		    		$('#errorMsg').html(data.msg);
		    		$('#errorMsg').show("fast");
				}
			}
    	}
    });