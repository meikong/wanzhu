/**
 * 获得网站主机域名或ip
 * 
 */
function sys_domain() {
	var protocol = window.location.protocol;
	var host = window.location.host;
	return (protocol + "//" + host);
};


/**
 * 扩展trim，用于去除字符串左右两边的空格
 * 
 * @returns
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * isNumber，判断是否为金钱类
 */
function isNumber(oNum) {
	if (!oNum) {
		return false;
	}
	var strP = /^\d+(\.\d+)?$/;
	if (!strP.test(oNum)) {
		return false;
	}
	try {
		if (parseFloat(oNum) != oNum) {
			return false;
		}
	} catch (ex) {
		return false;
	}
	return true;
}

/**
 * 是否为金额类型的数字，不能超过2位
 * @param string
 * @returns {Boolean}
 */
function  isRMB(string)
{
	if(!isNumber(string))
		return false;
	var isFloat=/^[0-9]*(\.[0-9]{1,2})?$/;//金额类型的数字，如不超过2个小数位
	if( isFloat.test(string))
		return true;
	return false;
}

/**
 *  把带有格式的时间转换 成 毫秒数
 */
function getDateTimeStamp(dateStr) {
	return Date.parse(dateStr.replace(/-/gi, "/"));
}

/**
 * 判断是否全为数字
 * 
 * @param str
 * @returns {Boolean}
 */
function isNum(str)
{
	if(/^\d+$/.test(str)) 
	{ 
	   return true;
	}
	return false;
}

/**
 * 定制个性化时间显示
 * 
 * @param string
 *            只接受2008-07-22 13:04:06格式的时间或时间戳
 * @returns
 */
var minute = 1000 * 60;
var hour = minute * 60;
var day = hour * 24;
var halfamonth = day * 15;
var month = day * 30;
function getDateDiff(string) {
	var normal = string;
	//判断是否为时间戳格式
	if(!isNum(string))
	{
		string = getDateTimeStamp(string);
	}
	var now = new Date().getTime();
	var diffValue = now - string;
	var monthC = diffValue / month;
	var weekC = diffValue / (7 * day);
	var dayC = diffValue / day;
	var hourC = diffValue / hour;
	var minC = diffValue / minute;
	
	if (monthC >= 1 || weekC >= 1) {
		//具体时间
		return normal;
	}else if (dayC >= 1) {
		result = parseInt(dayC) + "天前";
	} else if (hourC >= 1) {
		result = parseInt(hourC) + "个小时前";
	} else if (minC >= 1) {
		result = parseInt(minC) + "分钟前";
	} else
		result = "刚刚";

//	if (monthC >= 1) {
//		result = parseInt(monthC) + "个月前";
//	} else if (weekC >= 1) {
//		result =  parseInt(weekC) + "个星期前";
//	} else if (dayC >= 1) {
//		result = parseInt(dayC) + "天前";
//	} else if (hourC >= 1) {
//		result = parseInt(hourC) + "个小时前";
//	} else if (minC >= 1) {
//		result = parseInt(minC) + "分钟前";
//	} else
//		result = "刚刚";
	
	return result;
}

Date.prototype.format = function(fmt)  {
  var o = { 
    "M+" : this.getMonth()+1,                 //月份 
    "d+" : this.getDate(),                    //日 
    "h+" : this.getHours(),                   //小时 
    "m+" : this.getMinutes(),                 //分 
    "s+" : this.getSeconds(),                 //秒 
    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
    "S"  : this.getMilliseconds()             //毫秒 
  }; 
  if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
  for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
  return fmt; 
};


/**
 * 生成UUID
 */
var UUID={
		get:function(){
			return (this.random4() + this.random4() + "-" + this.random4() + "-" + this.random4() + "-" + this.random4() + "-" + this.random4() + this.random4() + this.random4());
		},
		random4:function(){
			return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		}
};

function convertUrl(url,point)
{
	if(null == point || point.length==0)
		return url;
	var idx = url.indexOf('?');
	if(idx>0)
		url = url.substring(0,idx);
    url+="?r="+new Date().getTime()+"#"+point;
	return url;
};