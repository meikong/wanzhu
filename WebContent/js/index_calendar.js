$(function() {
	var options = {
		height : 260,
		width : 300,
		navHeight : 10,
		labelHeight : 10,
		navLinks : {
			enableToday : true,
			enableNextYear : false,
			enablePrevYear : false,
			p : '&lt;&lt;',
			n : '&gt;&gt;',
			t : '今天'
		},
		onMonthChanging : function(dateIn) {
			loadEvents(dateIn);
			return true;
		},
		onEventLinkClick : function(event) {
			loadEventsOfaDay(event.StartDateTime, xx, yy);
			return false;
		},
		onEventBlockClick : function(event) {
			loadEventsOfaDay(event.StartDateTime, xx, yy);
			return true;
		},
		onEventBlockOver : function(event) {
			loadEventsOfaDay(event.StartDateTime, xx, yy);
			return true;
		},
		onEventBlockOut : function(event) {
			xx = null;
			yy = null;
			$("#CalendarInfo").hide();
			return true;
		},
		onDayLinkClick : function(date) {
			// alert(date.toLocaleDateString());
			return true;
		},
		onDayCellClick : function(date) {
			// alert(date.toLocaleDateString());
			return true;
		},
		locale : {
			days : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
			daysShort : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
			daysMin : [ "日", "一", "二", "三", "四", "五", "六", "日" ],
			months : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12" ],
			monthsShort : [ "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
					"十一", "十二" ],
			weekMin : '周'
		}
	};

	var year = new Date().getFullYear();
	var month = new Date().getMonth() + 1;

	jQuery.ajax({
		type : "POST",
		url : ctx + "/event/range.json",
		data : "needFormat=1&start=" + getFormatedDate(year, month, true)
				+ "&end=" + getFormatedDate(year, month, false),
		success : function(rs) {
			var events = formatResult(rs);
			jQuery.jMonthCalendar.Initialize(options, events);
		}
	});
	
	$("#CalendarInfo").mouseover(function() {
		$(this).show();
	});
	$("#CalendarInfo").mouseout(function() {
		$(this).hide();
	});

});

// 活动日历
function getFormatedDate(year, month, isStart) {
	if (!isStart) {
		if (month == 12) {
			month = 1;
			year = year + 1;
		} else {
			month = month + 1;
		}
	}

	if (month < 10) {
		month = "0" + month;
	}

	return year + "-" + month + "-01 00:00:00";
}

function loadEvents(d) {
	var year = d.getFullYear();
	var month = d.getMonth() + 1;

	jQuery.ajax({
		type : "POST",
		url : ctx + "/event/range.json",
		data : "needFormat=1&start=" + getFormatedDate(year, month, true)
				+ "&end=" + getFormatedDate(year, month, false),
		async : true,
		timeout : 10000,
		success : function(rs) {
			$.jMonthCalendar.ReplaceEventCollection(formatResult(rs));
		}
	});
}

function loadEventsOfaDay(d, xx, yy) {
	//$("#CalendarInfo").hide();
	var start = d + " 00:00:00";
	var end = d + " 23:59:59";
	
	$("#CalendarInfo").css("top", yy).css("left", xx - 140);
	$("#CalendarInfo").show();
	jQuery.ajax({
		type : "POST",
		url : ctx + "/event/range.json",
		data : "needFormat=0&start=" + start + "&end=" + end,
		success : function(rs) {
			var html = "";
			var itemInfo_css = "itemInfo";
			for ( var i = 0; i < rs.length; i++) {
				
				if(i+1==rs.length)  itemInfo_css = "itemInfo itemInfo02";//最后一条数据的样式
				
				var e = rs[i];
				var time;
				if (Date.parse(e.startTime).format("MMdd") == Date.parse(
						e.endTime).format("MMdd")) {
					time = Date.parse(e.startTime).format("hh:mm") + "-"
							+ Date.parse(e.endTime).format("hh:mm");
				} else {
					time = Date.parse(e.startTime).format("MM月dd日") + "-"
							+ Date.parse(e.endTime).format("MM月dd日");
				}
				var topic = e.topic;
				if (topic.length > 15) {
					topic = topic.substring(0, 15) + "...";
				}

				html += "<div class='itemBox03'>";
				html += "<div class='"+itemInfo_css+"'>";
				html += "<h4>";
				html += "<a href='" + ctx + "/event/d_" + e.eventId + ".html'>"
						+ topic + "</a>";
				html += "</h4>";
				html += "<ul><li>";
				html += "<span>分享嘉宾：</span>";
				for ( var j = 0; j < e.lectrues.length; j++) {
					html += "<em class='name'><a href='" + ctx + "/user/f_"
							+ e.lectrues[j].userId + ".html'>"
							+ e.lectrues[j].name + "</a>&nbsp;</em>";
					if (j == 1 && e.lectrues.length > 2) {
						html += "...";
						break;
					}
				}
				html += "</li><li><span>时间：</span>" + time + "</li>";
				html += "<li><a href='" + ctx + "/event/d_" + e.eventId
						+ ".html'>查看活动详情>></a></li>";
				html += "</ul>";
				html += "</div>";
				html += "</div>";
			}

			if (xx != null && yy != null) {
				$("#infobox").html(html);
				//$("#CalendarInfo").css("top", yy).css("left", xx - 140);
				//$("#CalendarInfo").show();
			}
		}
	});
}

function formatResult(rs) {
	var events = [];
	for ( var i = 0; i < rs.length; i++) {
		var vo = rs[i];
		var event = {
			EventID : vo.eventId,
			StartDateTime : Date.parse(vo.startTime).format("yyyy-MM-dd"),
			CssClass : "EventBg"
		};
		events.push(event);

	}
	// $("#debug").text(JSON.stringify(events));
	return events;
}