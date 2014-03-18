
/**
 * @param pageNo 当前页码
 * @param cityCode
 * @param labelId
 * @param target 目标div
 * @param loadingHint 加载提示div
 * @param loadingBtn 加载按钮
*/

function loadComings(pageNo, pageSize, cityCode, labelId, target, loadingHint, loadingBtn) {
	loadingHint.show();
	loadingBtn.hide();
 	jQuery.ajax({
  	   type:"POST",
  	   url:ctx+"/event/e_1_"+cityCode+"_"+labelId+".html",
  	   data: "pageSize="+pageSize+"&view=json&pageNo="+(pageNo+1),
  	   success: function(rs){
  		   //alert(JSON.stringify(result));
  		   pn = rs.currentPageNo;
  		   ps = pageSize;
  		   hasNextPage = rs.hasNextPage;
  		   loadingHint.hide();
  		   var events = rs.result;
  		   loadingBtn.children("a").unbind("click");
  		   loadingBtn.children("a").click(function() {
  			 loadComings(rs.currentPageNo, pageSize, cityCode, labelId, target, loadingHint, loadingBtn)
  		   });
  		   if(!rs.hasNextPage) {
  			 loadingBtn.hide();
  		   } else {
  			 loadingBtn.show();
  		   }
  		   for(var i=0; i<events.length; i++) {
  			    var event = events[i];
  			    var html = "";
  			  	html += "<div class=\"itemBox clear\">";
  				html += "<a href=\""+ctx+"/event/d_"+event.eventId+".html\"><img src=\"";
  				
  				if(event.hasPoster) {
  					html += event.posterUrl;
  				} else {
  					html += ctx+"/images/noPic.jpg";
  				}
  				
  				html += "\" width=\"200\" height=\"250\" /></a>";
  				
  				html += "<div class=\"acti_info\">";
  				html += "<a href=\""+ctx+"/event/d_"+event.eventId+".html\" title=\""+event.topic+"\"><h2>"+event.topic+"</h2></a>";
  				html += "<p>";
  				html += "<span>分享嘉宾：";
  				for(var j=0; j<event.lectrues.length; j++) {
	  				html += "<a class=\"name tipALink\"  uid='"+event.lectrues[j].userId+"'  point='"+event.eventId+"' href=\""+ctx+"/user/f_"+event.lectrues[j].userId+".html\">"+event.lectrues[j].name+"</a>";
  				}
  				html += "</span><br />";
  				html += "地点："+event.place+"<br />";
  				
  				var start = Date.parse(event.startTime);
				var end = Date.parse(event.endTime);
				if(start.getYear()==end.getYear() && start.getMonth()==end.getMonth() && start.getDate()==end.getDate()) {
					html += "时间：" 
						+ start.format("yyyy年MM月dd日 hh:mm")
						+ " - "
						+ end.format("hh:mm")
						+ "<br />";
				} else {
					html += "时间：" 
						+ start.format("yyyy年MM月dd日 hh:mm")
						+ " - "
						+ end.format("yyyy年MM月dd日 hh:mm")
						+ "<br />";
				}
  				
  				html += "费用：";
  				html += "<em>"+event.expenseOnline+"</em>元/人";
  				html += "<br />";
  				html += "限额： <em>"+event.quota+"</em>人  &nbsp; <span class='greyfont'>（<em>"+event.applyCount+"</em>人已报名）</span>";
  				html += "</p>";
  				
  				if(event.hasSignuped) {
  					if(event.audit==false) {
  						html += "<a  class='sign br5' name='audit_"+event.eventId+"'>审核中</a>";
  					} else {
  						html += "<a  class='sign br5' name='signed_"+event.eventId+"'>已报名</a>";
  					}
  				} else if(event.quota<=event.applyCount) {
  					html += "<a  class='sign br5'>报名人数已满</a>";
  				} else {
  					if(event.state != 1) {
  						html += "<a href=\""+ctx+"/event/d_"
  							+ event.eventId + ".html\" class='sign br5'>报名参加</a>";
  					} else {
  						html += "<a href=\""+ctx+"/event/d_"
  							+ event.eventId + ".html\" class='sign br5'>报名参加</a>";
  					}
  				}
  				html += "</div>";
  				html += "</div>";
  				target.append(html);
  				
  				redrawBottun();
  		   }

  	   }
 });
 }