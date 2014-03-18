function forgetpasswordSubmit(){
	jQuery.validator.addMethod("checkEmail",function(value, element) {
		//var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		var myreg = /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
		if (value != '') {if (!myreg.test(value)) {return false;}};
		return true;}, "请输入有效的邮箱地址，如:abc@abc.com");   
	//添加转换小写功能
	jQuery.validator.addMethod("toLowerCase", function(value, element) {
		value = $.trim(String(value));//去空
		if(value==""){element.value = ""; return this.optional(element)|| true;}
		if(value.toLowerCase()!=value) element.value = value.toLowerCase();
		return this.optional(element)|| true;
		}, "");
	$('#forgetpasswordform').validate({
		onfocusin:true,
		rules : {
			'email' : {
				required : true,
				maxlength : 128,
				email : true,
				checkEmail:true,
				toLowerCase:true
			}
		},
		messages : {
			'email' : {
				required : "请输入你的注册邮箱",
				maxlength : "请正确输入邮箱，最大长度为128字符",
				email : "请输入有效的邮箱地址，如:abc@abc.com"
			}
		}
	});
	if ($('#forgetpasswordform').valid()) {
		$('#forgetpasswordform').submit();
	}
}
