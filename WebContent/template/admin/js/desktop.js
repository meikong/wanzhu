/*<%@ page pageEncoding="UTF-8"%>*/
var GLOBAL = {};
GLOBAL.namespace = function(str) {
	var arr = str.split("."), o = GLOBAL;
	for (i = (arr[0] == "GLOBAL") ? 1 : 0; i < arr.length; i++) {
		o[arr[i]] = o[arr[i]] || {};
		o = o[arr[i]];
	}
};
GLOBAL.namespace("Dom");
GLOBAL.namespace("Event");
GLOBAL.namespace("Lang");
// 封装getElementById方法
GLOBAL.Dom.$ = function(node) {
	node = typeof node == "string" ? document.getElementById(node) : node;
	return node;
};
// 封装on函数 绑定事件函数
GLOBAL.Event.on = function(node, eventType, handler, scope, arg) {
	node = typeof node == "string" ? document.getElementById(node) : node;
	scope = scope || node;
	var arg = arg || [];
	if (document.all) {
		node.attachEvent("on" + eventType, function(e) {
			e = window.event || e;
			arg.unshift(e);
			handler.apply(scope, arg);
			arg.shift();
		});
	} else {
		node.addEventListener(eventType, function(e) {
			e = window.event || e;
			arg.unshift(e);
			handler.apply(scope, arg);
			arg.shift();
		}, false)
	}
};
// 封装off函数 解除绑定事件函数
GLOBAL.Event.off = function(node, eventType, handler) {
	node = typeof node == "string" ? document.getElementById(node) : node;
	if (document.all)
		node.detachEvent("on" + eventType, handler);
	else
		node.removeEventListener(eventType, handler, false);
};
// 获取事件触发的节点对象
GLOBAL.Event.getEventTarget = function(e) {
	e = window.event || e;
	return e.srcElement || e.target;
};
// 定义方法getElementsByClassName
GLOBAL.Dom.getElementsByClassName = function(str, root, tag) {
	if (root)
		root = typeof root == "string" ? document.getElementById(root) : root;
	else
		root = document.body;
	tag = tag || "*";
	var els = root.getElementsByTagName(tag), arr = [];
	for ( var i = 0, n = els.length; i < n; i++)
		for ( var j = 0, k = els[i].className.split(" "), l = k.length; j < l; j++)
			if (k[j] == str) {
				arr.push(els[i]);
				break;
			}
	return arr;
};
// 读取当前样式
GLOBAL.Dom.CurrentStyle = function(element) {
	return element.currentStyle || document.defaultView.getComputedStyle(element, null);
};
// addClass方法
GLOBAL.Dom.addClass = function(node, str) {
	if (!new RegExp("(^|\\s+)" + str).test(node.className))
		node.className = node.className + " " + str;
};
// removeClass方法
GLOBAL.Dom.removeClass = function(node, str) {
	node.className = node.className.replace(new RegExp("(^|\\s+)" + str), "");
};
// 获取下一个节点
GLOBAL.Dom.getNextNode = function(node) {
	node = typeof node == "string" ? document.getElementById(node) : node;
	var nextNode = node.nextSibling;
	if (!nextNode)
		return null;
	if (!document.all)
		while (true) {
			if (nextNode.nodeType == 1)
				break;
			else {
				if (nextNode.nextSibling) {
					nextNode = nextNode.nextSibling;
				} else {
					break;
				}
			}
		}
	return nextNode;
};
GLOBAL.Dom.setOpacity=function(node,level){
	node=typeof node=="string" ? document.getElementById(node):node;
	if(document.all){
		node.style.filter='alpha(opacity='+level+')';
	} else{
		node.style.opacity=level/100;
	}
}
Array.prototype.remove=function(dx) {  //删除指定数组
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
        if(this[i]!=this[dx])
            this[n++]=this[i];
    this.length-=1;
}
Array.prototype.getIndexByValue= function(value) {
    var index = -1;
    for (var i = 0; i < this.length; i++)
        if (this[i] == value) {
            index = i;
            break;
        }
    return index;
}
// 去除首尾空格
GLOBAL.Lang.trim = function(ostr) {
	return ostr.replace(/^\s+|\s+$/g, "");
};
// 拖拽
GLOBAL.Drag = function(c_obj, d_obj,isPupUpBox) {
	var mouseStrat = {x : 0, y : 0};
	var divStrat = {x : 0, y : 0};
	var newSpan;
	c_obj.onmousedown = startDrag;
	// 开始
	function startDrag(e) {
		var oEvent = e || event;
		if(isPupUpBox){
			d_obj.firstChild.style.display = "block";
		}	
		// 获取鼠标和目标的初始位置
		mouseStrat.x = oEvent.clientX;
		mouseStrat.y = oEvent.clientY;
		divStrat.x = d_obj.offsetLeft;
		divStrat.y = d_obj.offsetTop;
		// 创建透明层，防止选择bug
		newSpan = document.createElement("span");
		d_obj.appendChild(newSpan);
		// 事件获取（并作兼容判断）
		if (c_obj.setCapture) {
			c_obj.onmousemove = doDrag;
			c_obj.onmouseup = stopDrag;
			c_obj.setCapture();
		} else {
			document.addEventListener("mousemove", doDrag, true);
			document.addEventListener("mouseup", stopDrag, true);
		}
	}
	// 进行
	function doDrag(e) {
		var oEvent = e || event;
		// 公式：当前鼠标位置-起始鼠标位置+目标位置
		d_obj.style.left = oEvent.clientX - mouseStrat.x + divStrat.x + "px";
		d_obj.style.top = oEvent.clientY - mouseStrat.y + divStrat.y + "px";
	}
	// 停止
	function stopDrag() {
		// 取消透明层
		if(isPupUpBox){
			d_obj.firstChild.style.display = "none";
		}
		d_obj.removeChild(newSpan);
		// 取消事件（并作兼容判断）
		if (c_obj.releaseCapture) {
			c_obj.onmousemove = null;
			c_obj.onmouseup = null;
			c_obj.releaseCapture();
		} else {
			document.removeEventListener("mousemove", doDrag, true);
			document.removeEventListener("mouseup", stopDrag, true);
		}
	}
};





(function() {
	// resize方法
	var $ = function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};
	var Class = {
		create : function() {
			return function() {
				this.initialize.apply(this, arguments);
			}
		}
	};
	var Bind = function(object, fun) {
		return function() {
			return fun.apply(object, arguments);
		}
	};
	var BindAsEventListener = function(object, fun) {
		var args = Array.prototype.slice.call(arguments).slice(2);
		return function(event) {
			return fun.apply(object, [ event || window.event ].concat(args));
		}
	};
	function addEventHandler(oTarget, sEventType, fnHandler) {
		if (oTarget.addEventListener)
			oTarget.addEventListener(sEventType, fnHandler, false);
		else if (oTarget.attachEvent)
			oTarget.attachEvent("on" + sEventType, fnHandler);
		else
			oTarget["on" + sEventType] = fnHandler;
	};
	function removeEventHandler(oTarget, sEventType, fnHandler) {
		if (oTarget.removeEventListener)
			oTarget.removeEventListener(sEventType, fnHandler, false);
		else if (oTarget.detachEvent)
			oTarget.detachEvent("on" + sEventType, fnHandler);
		else
			oTarget["on" + sEventType] = null;
	};
	// 缩放程序
	var SimpleResize = Class.create();
	SimpleResize.prototype = {
		initialize : function(obj, options) {
			this._obj = $(obj);
			this._fR = BindAsEventListener(this, this.Resize);
			this._fS = Bind(this, this.Stop);
			this._obj.style.position = "absolute";
		},
		// 设置触发对象
		Set : function(resize, side) {
			var resize = $(resize), fun;
			if (!resize)
				return;
			switch (side.toLowerCase()) {
			case "up":
				fun = this.Up;
				break;
			case "down":
				fun = this.Down;
				break;
			case "left":
				fun = this.Left;
				break;
			case "right":
				fun = this.Right;
				break;
			case "left-up":
				fun = this.LeftUp;
				break;
			case "right-up":
				fun = this.RightUp;
				break;
			case "left-down":
				fun = this.LeftDown;
				break;
			case "right-down":
			default:
				fun = this.RightDown;
			}
			addEventHandler(resize, "mousedown", BindAsEventListener(this, this.Start, fun));
		},
		// 准备缩放
		Start : function(e, fun) {
			this._obj.firstChild.style.display = "block";
			this._fun = fun;
			this._styleWidth = this._obj.clientWidth;
			this._styleHeight = this._obj.clientHeight;
			this._styleLeft = this._obj.offsetLeft;
			this._styleTop = this._obj.offsetTop;
			this._sideLeft = e.clientX - this._styleWidth;
			this._sideRight = e.clientX + this._styleWidth;
			this._sideUp = e.clientY - this._styleHeight;
			this._sideDown = e.clientY + this._styleHeight;
			this._fixLeft = this._styleWidth + this._styleLeft;
			this._fixTop = this._styleHeight + this._styleTop;
			addEventHandler(document, "mousemove", this._fR);
			addEventHandler(document, "mouseup", this._fS);
		},
		// 缩放
		Resize : function(e) {
			this._fun(e);
			with (this._obj.style) {
				width = this._styleWidth + "px";
				height = this._styleHeight + "px";
				top = this._styleTop + "px";
				left = this._styleLeft + "px";
			}
			var iframe_wrap = GLOBAL.Dom.getElementsByClassName("iframe_wrap", this._obj)[0];
			iframe_wrap.style.height = (this._obj.offsetHeight - 36) + "px";
		},
		// 缩放程序
		// 上
		Up : function(e) {
			this._styleHeight = Math.max(this._sideDown - e.clientY, 0);
			this._styleTop = this._fixTop - this._styleHeight;
		},
		// 下
		Down : function(e) {
			this._styleHeight = Math.max(e.clientY - this._sideUp, 0);
		},
		// 左
		Left : function(e) {
			this._styleWidth = Math.max(this._sideRight - e.clientX, 0);
			this._styleLeft = this._fixLeft - this._styleWidth;
		},
		// 右
		Right : function(e) {
			this._styleWidth = Math.max(e.clientX - this._sideLeft, 0);
		},
		// 左上
		LeftUp : function(e) {
			this.Left(e);
			this.Up(e);
		},
		// 左下
		LeftDown : function(e) {
			this.Left(e);
			this.Down(e);
		},
		// 右上
		RightUp : function(e) {
			this.Right(e);
			this.Up(e);
		},
		// 右下
		RightDown : function(e) {
			this.Right(e);
			this.Down(e);
		},
		// 停止缩放
		Stop : function() {
			this._obj.firstChild.style.display = "none";
			removeEventHandler(document, "mousemove", this._fR);
			removeEventHandler(document, "mouseup", this._fS);
		}
	};
	// 后台的到的shortcutItems列表项 
	var shortcutItems=document.getElementById("data").innerHTML;
	shortcutItems = eval('(' + shortcutItems + ')');
	/* 总共shortcutItems的数量 */
	var shortcutNumner=shortcutItems.length;
	/* 桌面图标位置数组 */
	var locations = [];
	var zIndex = 1;
	var popUpBoxStatus = {};
	/* 初始化桌面元素 */
	var desktop = GLOBAL.Dom.$("desktop");
	for ( var i = 0; i < shortcutNumner; i++) {
		var shortcutItem = document.createElement("div");
		shortcutItem.className = "ux-shortcut-item";
		shortcutItem.setAttribute("_index", i);
		shortcutItem.innerHTML = "<img src='" + shortcutItems[i].icon + "'><div class='ux-shortcut-btn-text'>" + shortcutItems[i].text + "</div>";
		GLOBAL.Event.on(shortcutItem, "click", clickShortcutItem);
		desktop.appendChild(shortcutItem);
		createBottomItem(shortcutItem);/* 初始化底部栏图标 */
		createPopUpBox(shortcutItem);/* 初始化弹出框 */
	}
	resize();
	/* 绑定resize事件，resize后重新定位 */
	window.onresize = resize;
	/* 计算每个shortcutItem的location */
	function resize() {
		var deskHeight = document.body.clientHeight;
		/*************根据视口大小定背景图片大小*********************************************/
		var bgImg=document.getElementById('bgImg');
		bgImg.style.width=document.body.clientWidth+'px';
		bgImg.style.height=deskHeight+'px';
		/************************************************************/
		var columnNumber = Math.floor((deskHeight - 60) / 140);
		for ( var j = 0; j < shortcutNumner; j++) {
			var row;
			var column = (j % columnNumber + 1);
			for ( var n = 1; n < 30; n++)
				if (j <= columnNumber * n - 1) {
					row = n;
					break;
				}
			locations[j] = {top : (30 + (column - 1) * 140) + "px", left : (30 + (row - 1) * 140) + "px"}
		}
		for ( var i = 0; i < shortcutNumner; i++) {
			GLOBAL.Dom.$(desktop).childNodes[i].style.top = locations[i].top;
			GLOBAL.Dom.$(desktop).childNodes[i].style.left = locations[i].left;
		}
	}
	/* 点击桌面图标事件函数 */
	function clickShortcutItem() {
		var _index = this.getAttribute("_index");
		var popUpBoxs = GLOBAL.Dom.getElementsByClassName("window_content");
		for ( var i = 0; i < popUpBoxs.length; i++)
			if (popUpBoxs[i].getAttribute("_index") == _index)
				changeZIndex(popUpBoxs[i]);
		if (this.getAttribute("switch") == "on")
			return true;
		else {
			var taskItems = GLOBAL.Dom.getElementsByClassName("taskItem");
			var bottomBar = GLOBAL.Dom.$("bottomBar");
			for ( var i = 0; i < taskItems.length; i++)
				if (taskItems[i].getAttribute("_index") == _index) {
					taskItems[i].style.display = "block";
					var currentTaskItem = GLOBAL.Dom.getElementsByClassName("current-taskItem", bottomBar)[0];
					if (currentTaskItem)
						GLOBAL.Dom.removeClass(currentTaskItem, "current-taskItem");
					GLOBAL.Dom.addClass(taskItems[i], "current-taskItem");
					break;
				}
			for ( var i = 0; i < popUpBoxs.length; i++)
				if (popUpBoxs[i].getAttribute("_index") == _index) {
					initPopUpBox(popUpBoxs[i]);
					break;
				}
			this.setAttribute("switch", "on");
		}
	}
	/* 创建弹出框 */
	function createPopUpBox(obj) {
		var that = obj;
		var _index = that.getAttribute("_index");
		var popUpBox = document.createElement("div");
		var wrap_popUpBox = GLOBAL.Dom.$("wrap-popUpBox");
		var closePopUpBox;
		var minPopUpBox;
		popUpBox.className = "window_content";
		popUpBox.style.zIndex = zIndex;
		popUpBox.setAttribute("_index", _index);
		popUpBox.id = "window_content" + _index;
		GLOBAL.Event.on(popUpBox, "click", clickPopUpBox);
		popUpBox.onclick = clickPopUpBox;
		popUpBox.innerHTML = "<div class='popUpBox_mask'></div><div id='rRightDown" + _index + "' class='rRightDown'>1 </div><div id='rLeftDown" + _index + "' class='rLeftDown'> 2</div><div id='rRightUp" + _index + "' class='rRightUp'> 3</div><div id='rLeftUp" + _index + "' class='rLeftUp'> 4</div><div id='rRight" + _index + "' class='rRight'>5 </div><div id='rLeft" + _index + "' class='rLeft'>6 </div><div id='rUp" + _index + "' class='rUp'> 7</div><div id='rDown" + _index + "' class='rDown'>8</div><div class='window_titleBar'><div class='window_titleButtonBar'><a  class='window_action_button window_close' title='关闭' href='#'></a><a class='window_action_button window_max' id='window_max' title='最大化' href='#'></a><a class='window_action_button window_restore' title='还原' href='#' style='display:none'></a><a class='window_action_button window_min' title='最小化' href='#'></a></div><div class='window_title titleText'><img src='" + shortcutItems[_index].smallicon + "' class='window_title_ico'/><span class='window_title_text'>" + shortcutItems[_index].text + "</span></div></div><div class='iframe_wrap' id='iframe_wrap_" + shortcutItems[_index].name + "'></div>";
		wrap_popUpBox.appendChild(popUpBox);
		popUpBox.style.display = "none";
		var popUpBox_bottomItems = GLOBAL.Dom.$("popUpBox_bottomItems" + _index);
		var window_titleBar = GLOBAL.Dom.getElementsByClassName("window_titleBar", popUpBox)[0];
		window_titleBar.setAttribute("dbclick", "off");
		var minPopUpBox = GLOBAL.Dom.getElementsByClassName("window_min", popUpBox)[0];
		var closePopUpBox = GLOBAL.Dom.getElementsByClassName("window_close", popUpBox)[0];
		var maxPopUpBox = GLOBAL.Dom.getElementsByClassName("window_max", popUpBox)[0];
		var restorePopUpBox = GLOBAL.Dom.getElementsByClassName("window_restore", popUpBox)[0];
		GLOBAL.Event.on(window_titleBar, "dblclick", dbclick_PopUpBox_title);
		GLOBAL.Event.on(minPopUpBox, "click", clickMinPopUpBox);
		GLOBAL.Event.on(closePopUpBox, "click", clickClosePopUpBox);
		GLOBAL.Event.on(maxPopUpBox, "click", clickMaxPopUpBox);
		GLOBAL.Event.on(restorePopUpBox, "click", clickRestorePopUpBox);
		GLOBAL.Drag(window_titleBar, popUpBox);
		var rs = new SimpleResize("window_content" + _index);
		rs.Set("rRightDown" + _index, "right-down");
		rs.Set("rLeftDown" + _index, "left-down");
		rs.Set("rRightUp" + _index, "right-up");
		rs.Set("rLeftUp" + _index, "left-up");
		rs.Set("rRight" + _index, "right");
		rs.Set("rLeft" + _index, "left");
		rs.Set("rUp" + _index, "up");
		rs.Set("rDown" + _index, "down");
		zIndex++;
	}
	/* 创建底部图标 */
	function createBottomItem(obj) {
		that = obj;
		var _index = that.getAttribute("_index");
		var bottomBar = GLOBAL.Dom.$("bottomBar");
		var currentTaskItem = GLOBAL.Dom.getElementsByClassName("current-taskItem", bottomBar)[0];
		if (currentTaskItem)
			GLOBAL.Dom.removeClass(currentTaskItem, "current-taskItem");
		var taskItem = document.createElement("div");
		taskItem.className = "taskItem";
		taskItem.setAttribute("_index", _index);
		taskItem.style.display = "none";
		GLOBAL.Event.on(taskItem, "click", clickTaskItem);
		taskItem.innerHTML = "<div class='taskItemIcon'><img src='" + shortcutItems[_index].icon + "'></img></div><div class='taskItemTxt'>" + shortcutItems[_index].text + "</div>";
		bottomBar.appendChild(taskItem);
	}
	function changeZIndex(obj) {
		obj.style.zIndex = zIndex;
		zIndex++;
	}
	/* 点击底部栏图标 */
	function clickTaskItem() {
		var bottomBar = GLOBAL.Dom.$("bottomBar");
		var currentTaskItem = GLOBAL.Dom.getElementsByClassName("current-taskItem", bottomBar)[0];
		if (currentTaskItem)
			GLOBAL.Dom.removeClass(currentTaskItem, "current-taskItem");
		GLOBAL.Dom.addClass(this, "current-taskItem");
		var _index = this.getAttribute("_index");
		var popUpBoxs = GLOBAL.Dom.getElementsByClassName("window_content");
		for ( var i = 0; i < popUpBoxs.length; i++)
			if (popUpBoxs[i].getAttribute("_index") == _index) {
				popUpBoxs[i].style.display == "none" ? popUpBoxs[i].style.display = "block" : popUpBoxs[i].style.display = "none";
				changeZIndex(popUpBoxs[i]);
				break;
			}
	}
	/* 点击弹出框 */
	function clickPopUpBox() {
		var _index = this.getAttribute("_index");
		var taskItems = GLOBAL.Dom.getElementsByClassName("taskItem");
		var bottomBar = GLOBAL.Dom.$("bottomBar");
		var currentTaskItem = GLOBAL.Dom.getElementsByClassName("current-taskItem", bottomBar)[0];
		if (currentTaskItem)
			GLOBAL.Dom.removeClass(currentTaskItem, "current-taskItem");
		for ( var i = 0; i < taskItems.length; i++)
			if (taskItems[i].getAttribute("_index") == _index) {
				GLOBAL.Dom.addClass(taskItems[i], "current-taskItem");
				changeZIndex(this);
				break;
			}
	}
	/* 点击弹出框最小化按钮 */
	function clickMinPopUpBox() {
		this.parentNode.parentNode.parentNode.style.display = "none";
	}
	/* 点击弹出框关闭按钮 */
	function clickClosePopUpBox() {
		var _index = this.parentNode.parentNode.parentNode.getAttribute("_index");
		var taskItems = GLOBAL.Dom.getElementsByClassName("taskItem");
		var shortcutItems = GLOBAL.Dom.getElementsByClassName("ux-shortcut-item");
		for ( var i = 0; i < shortcutItems.length; i++)
			if (shortcutItems[i].getAttribute("_index") == _index) {
				shortcutItems[i].setAttribute("switch", "off");
				break;
			}
		for ( var i = 0; i < taskItems.length; i++)
			if (taskItems[i].getAttribute("_index") == _index) {
				taskItems[i].style.display = "none";
				break;
			}
		this.parentNode.parentNode.parentNode.style.display = "none";
	}
	/* 点击弹出框最大化按钮 */
	function clickMaxPopUpBox() {
		this.style.display = "none";
		this.nextSibling.style.display = "block";
		var popUpBox = this.parentNode.parentNode.parentNode;
		var width = popUpBox.clientWidth;
		var height = popUpBox.clientHeight;
		var top = popUpBox.style.top;
		var left = popUpBox.style.left;
		this.parentNode.parentNode.setAttribute("dbclick", "on");
		popUpBoxStatus = {
			"width" : width,
			"height" : height,
			"top" : top,
			"left" : left
		};
		popUpBox.style.height = document.body.clientHeight + "px";
		popUpBox.style.width = document.body.clientWidth + "px";
		popUpBox.style.top = "0px";
		popUpBox.style.left = "0px";
		var iframe_wrap = GLOBAL.Dom.getElementsByClassName("iframe_wrap", popUpBox)[0];
		iframe_wrap.style.height = (popUpBox.offsetHeight - 36) + "px";
	}
	/* 点击弹出框还原按钮 */
	function clickRestorePopUpBox() {
		var popUpBox = this.parentNode.parentNode.parentNode;
		this.style.display = "none";
		this.previousSibling.style.display = "block";
		popUpBox.style.width = popUpBoxStatus.width + "px";
		popUpBox.style.height = popUpBoxStatus.height + "px";
		popUpBox.style.left = popUpBoxStatus.left;
		popUpBox.style.top = popUpBoxStatus.top;
		this.parentNode.parentNode.setAttribute("dbclick", "off");
		var iframe_wrap = GLOBAL.Dom.getElementsByClassName("iframe_wrap", popUpBox)[0];
		iframe_wrap.style.height = (popUpBox.offsetHeight - 36) + "px";
	}
	/* 双击弹出框顶部 */
	function dbclick_PopUpBox_title() {
		if (this.getAttribute("dbclick") == "off") {
			clickMaxPopUpBox.call(this.firstChild.childNodes[1]);
			this.setAttribute("dbclick", "on");
		} else {
			clickRestorePopUpBox.call(this.firstChild.childNodes[2]);
			this.setAttribute("dbclick", "off");
		}
	}
	/* 初始化弹出框 */
	function initPopUpBox(obj) {
		_index = obj.getAttribute("_index");
		obj.style.display = "block";
		var iframe_wrap = GLOBAL.Dom.getElementsByClassName('iframe_wrap', obj)[0];
		iframe_wrap.innerHTML = "<iframe id='wrap_flex" + _index + "' class='iframe' frameborder='no' border='0' allowtransparency='true' src='" + shortcutItems[_index].href + "'></iframe>";
/*		if(obj.id=='window_content0' ||obj.id=='window_content1' ){
			obj.style.width = '100%';
			obj.style.height = '100%';
		}else{
			obj.style.width = '80%';
			obj.style.height = '80%';
		}
		 */
		obj.style.width = '98%';
		obj.style.height = '98%';
		var iframe_wrap = GLOBAL.Dom.getElementsByClassName("iframe_wrap", obj)[0];
		iframe_wrap.style.height = (obj.offsetHeight - 36) + "px";
		obj.style.left = (document.body.clientWidth - obj.offsetWidth) / 2 +10+ "px";
		obj.style.top = (document.body.clientHeight - obj.offsetHeight) / 2 + "px";
	}
	//setTimeout(function(){document.getElementById('loading-mask').style.display='none';},1000);
})();


$.fx.speeds._default = 400;
$(function() {
	//
	//注销
	$( "#delConfirm" ).dialog({
		autoOpen: false,
		show: "clip",
		hide: "explode",
		width:300,
		modal: true,
        buttons: {
            "确定": function() {
            	logout();
                $( this ).dialog( "close" );
            },
            "取消": function() {
                $( this ).dialog( "close" );
            }
        }
	});
	//修改密码	   
	$( "#editDialog" ).dialog({
		autoOpen: false,
		show: "clip",
		hide: "explode",
		modal: true,
		width:450,
        buttons: {
            "修改": function() {		
            	$('#upPassword').validate({
            		rules : {
            			'password' : {
            				required : true,
            				rangelength: [6,64]
            			},
            			'ypassword' : {
            				required : true,
            				rangelength: [6,64]
            			},
            			'qpassword' : {
            				required : true,
            				rangelength: [6,64],
            				equalTo: "#ypassword"
            			}
            		},
            		messages : {
            			'password' : {
            				required : "请填写原密码。",
            				rangelength: "请输入有效密码."
            			},
            			'ypassword' : {
            				required : "请填写新密码。",
            				rangelength: "请输入有效密码."
            			},
            			'qpassword' : {
            				required : "确认密码不能为空!",
            				rangelength: "请输入有效密码.",
            				equalTo:"两次填写的密码不一致。"
            			}
            		}
            	});
            	if(!$("#upPassword").valid()){return;}
            	var password=$("#password").val();
            	var ypassword=$("#ypassword").val();
            	var qpassword=$("#qpassword").val();
            	onUpdatePassword(password,ypassword,qpassword);
            },
            "取消": function() {
                $( this ).dialog( "close" );
            }
        }
	});
});

function logout()
{
	document.getElementById("logout").submit();
}
function out() {
		$( "#delConfirm" ).dialog( "open" );
		return false;
	}
function edit(){
	var url=document.getElementById("url").value;
	$("#editDialog").load(url);
	$( "#editDialog" ).dialog( "open" );
		return false;
	}	
