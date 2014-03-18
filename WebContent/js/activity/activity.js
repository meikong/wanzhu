//别人的个人动态
function activityOfUser(_currentPage,_pageSize,_userId)
{
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/activity/act_'+_userId+'.json',
		data : "currentPage="+_currentPage+"&pageSize="+_pageSize,
		success : function(data) {
				var result = data.content.result;
				if(data.success && null != result && result.length>0)
				{
					for(var vo in result)
					{
		            	var appendContent = "<div class=\"noticeBox clearfix\"><span>"+getDateDiff(""+result[vo].time+"")+"</span>"+result[vo].content+"</div>";
						$("#activityContent").append(appendContent);
					}
					currentPage++;
					$(".btnMore").show();
				}
				//是否显示更多按钮
			    if(data.content.start+result.length == data.content.totalCount)
			    {
			    	$(".btnMore").hide();
			    }
		},
		error : function(err) {
		},
		complete : function() {
		}
	});
}