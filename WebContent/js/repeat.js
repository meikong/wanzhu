/**
 *  限制重复提交频率：默认为1s
 */
//存储变量信息
var VAR = {
	repeatTemp : []
};
var RP = {
	repeat : function(target, t) {//限制执行频率，默认为1秒 允许执行时返回false
		t = t ? t * 1000 : 1000;//毫秒
		var time = this.microtime();
		if (!VAR.repeatTemp[target]) {
			VAR.repeatTemp[target] = time;
			return false;//允许
		} else {
			var ts = t - (time - VAR.repeatTemp[target]);
			ts = parseInt(ts / 1000);
			if (ts > 0) {
				return true;//禁止执行
			} else {
				VAR.repeatTemp[target] = time;//更新时间
				return false;//允许
			}
		}
	},
	microtime : function() {//获取毫秒级时间戳
		return new Date().getTime();
	}
};
