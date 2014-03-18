/*
用途：检查输入对象的值是否符合E-Mail格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false	
*/
function is_email(str){  
	var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
	if(myReg.test( str)) return true; 
	return false; 
}

/*
要求：
	 一、移动电话号码为11或12位，如果为12位,那么第一位为0
	 二、11位移动电话号码的第一位和第二位为"13"
	 三、12位移动电话号码的第二位和第三位为"13"
用途：检查输入手机号码是否正确
输入：
	s：字符串
返回：
	如果通过验证返回true,否则返回false	
*/
function is_mobile(s){   
	var regu =/(^[1][3-58][0-9]{9}$)|(^0[1][3][0-9]{9}$)/;
	var re = new RegExp(regu);
	if (re.test(s)) {
	  return true;
	}
	return false;	
}

/*
要求：
	 一、电话号码由数字、"("、")"和"-"构成
	 二、电话号码为3到8位
	 三、如果电话号码中包含有区号，那么区号为三位或四位
	 四、区号用"("、")"或"-"和其他部分隔开
用途：检查输入的电话号码格式是否正确
输入：
	strPhone：字符串
返回：
	如果通过验证返回true,否则返回false	
*/
function f_check_phone(strPhone) 
{
	//var regu =/(^([0][1-9]{2,3}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0][1-9]{2,3}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/; 
	var regu = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{3,8})(-(\d{3,8}))?$/;
	var re = new RegExp(regu);
	if (re.test(strPhone)) {
	  return true;
	}
	return false;
}
