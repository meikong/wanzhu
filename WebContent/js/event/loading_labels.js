/**
 * @param recent 0-往期 1-近期
 * @param cityCode 0-所有城市， else-指定城市
 * @param eventCount event总数
 * @param currentLabelId 当前页的labelId
 */
function loadingLabels(recent, cityCode, eventCount, currentLabelId) {
	var totalLabelHtml = "<li ";
	if(currentLabelId == 0) {
		totalLabelHtml += " class='current' ";
	}
	totalLabelHtml += "><a href='"+ctx+"/event/e_"+recent+"_"+cityCode+"_0.html'>全部<span>（"+eventCount+"个活动）</span></a></li>";
	$("#labels").append(totalLabelHtml);
	
	jQuery.ajax({
		type : "POST",
		url : ctx+"/label/lc.json",
		data : "recent="+recent+"&cityCode="+cityCode,
		success : function(rs) {
			for(var i=0; i<rs.length; i++) {
			   if(currentLabelId == rs[i].labelId) {
				   $("#labels").append("<li class=\"current\"><a href=\""+ctx+"/event/e_"+recent+"_"+cityCode+"_"+rs[i].labelId+".html\">"+rs[i].label+"<span>（"+rs[i].eventCount+"个活动）</span></a></li>");
			   } else {
				   if(i%2==0) {
					   $("#labels").append("<li class=\"special\"><a href=\""+ctx+"/event/e_"+recent+"_"+cityCode+"_"+rs[i].labelId+".html\">"+rs[i].label+"<span>（"+rs[i].eventCount+"个活动）</span></a></li>");
				   } else {
					   $("#labels").append("<li><a href=\""+ctx+"/event/e_"+recent+"_"+cityCode+"_"+rs[i].labelId+".html\">"+rs[i].label+"<span>（"+rs[i].eventCount+"个活动）</span></a></li>");
				   }
			   }
		   }
		}
	});
}