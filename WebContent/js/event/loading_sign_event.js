function loadingSignedEvents(target, userId) {
	jQuery.ajax({
		type : "POST",
		url : ctx + "/event/signUpedEvents.json?userId="+userId,
		success : function(rs) {
			var html = "";
			var rlength = rs.length;
			if(rlength == 0){
				$(target).parents('.opinion').hide();
			}else{
				for(var i=0; i<rlength; i++) {
					var longTopic = rs[i].topic;
					var shortTopic = rs[i].topic;
					if(shortTopic.length>15) {
						shortTopic = shortTopic.substring(0, 14) + "...";
					}
					if(rs[i].state != 2) {
						shortTopic = '（未开始）'+shortTopic;
					}
					if(i%2==0) {
						html += "<li class=\"special\"><a title=\""+longTopic+"\" href=\""+ctx+"/event/d_"+rs[i].eventId+".html\">"+shortTopic+"<span></span></a></li>";
					} else {
						html += "<li><a title=\""+longTopic+"\" href=\""+ctx+"/event/d_"+rs[i].eventId+".html\">"+shortTopic+"<span></span></a></li>";
					}
				}
				$(target).append(html);
			}
		}
	});
}