/**
 * @param pageNo
 *            当前页码
 * @param cityCode
 * @param labelId
 * @param target
 *            目标div
 * @param loadingHint
 *            加载提示div
 * @param loadingBtn
 *            加载按钮
 */
function loadPasts(pageNo, pageSize, cityCode, labelId, target, loadingHint, loadingBtn) {
	loadingHint.show();
	loadingBtn.hide();
	jQuery.ajax({
		type : "POST",
		url : ctx+"/event/e_0_"+cityCode+"_"+labelId+".html",
		data : "pageSize="+pageSize+"&view=json&pageNo=" + (pageNo + 1),
		success : function(rs) {
			loadingHint.hide();
			pn = rs.currentPageNo;
  		    ps = pageSize;
  		    hasNextPage = rs.hasNextPage;
			var events = rs.result;
			loadingBtn.children("a").unbind("click");
			loadingBtn.children("a").click(function() {
				loadPasts(rs.currentPageNo, pageSize, cityCode, labelId, target, loadingHint, loadingBtn);
			});
			if (!rs.hasNextPage) {
				loadingBtn.hide();
			} else {
				loadingBtn.show();
			}
			for ( var i = 0; i < events.length; i++) {
				var event = events[i];
				var html = "";
				html += "<div class=\"itemBox clear\" id="+ event.eventId+">";
				html += "<a href=\""+ctx+"/discuz/s_" + event.eventId
					+ ".html\">";
				
				html += "<img width=\"200\" height=\"250\" src=\"";
				if (event.hasPoster) {
					html += event.posterUrl;
				} else {
					html += ctx+"/images/noPic.jpg";
				}
				html += "\" /></a>";
				html += "<div class=\"acti_info\">";
				html += "<a href=\""+ctx+"/discuz/s_" + event.eventId
					+ ".html\" title=\""+event.topic+"\">";
				
				html += "<h2>" + event.topic + "</h2></a>";
				html += "<p><span>分享嘉宾：";
				for ( var j = 0; j < event.lectrues.length; j++) {
					html += "<a class=\"name tipALink\"  uid='"+event.lectrues[j].userId+"' point='"+event.eventId+"' href=\""+ctx+"/user/f_"+event.lectrues[j].userId+".html\">" + event.lectrues[j].name + "</a>";
				}
				html += "</span><br />";
				html += "地点："+event.place+"<br />";
				var start = Date.parse(event.startTime);
				var end = Date.parse(event.endTime);
				if (start.getYear() == end.getYear()
						&& start.getMonth() == end.getMonth()
						&& start.getDate() == end.getDate()) {
					html += "时间："
							+ start.format("yyyy年MM月dd日 hh:mm") + " - "
							+ end.format("hh:mm")+"<br />";
				} else {
					html += "时间："
							+ start.format("yyyy年MM月dd日 hh:mm") + " - "
							+ end.format("yyyy年MM月dd日 hh:mm")+"<br />";
				}
				html += "</p><div class=\"btnOver br5\">活动结束</div></div></div>";
				target.append(html);
			}
		}
	});
}