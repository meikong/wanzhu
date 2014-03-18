var cur_suu_size = 0;
var defaultSUUSize = 20;
/**
 * 获取报名用户列表html
 * @param list
 */
function userList2HTML(list){

	var appendStr = "";
	 for(var i=0;i<list.length;i++){
		 var data = list[i];
		 var cp1 = data.company+ " "+data.position;
		 var cp2 = cp1;
		 var weibo="";
		 var addFriend ="";
		 if($("#myId").val() != ''){
			 if(!data.isFriend && data.userid != $("#myId").val()){
				 addFriend = "<a href=\"javascript:add('"+data.userid+"')\" class='af br3'>加好友</a>";
			 }
		 }else{
			 addFriend = '';
		 }
			 
		 if(data.weibo!=null){
			 weibo= "<a href='"+data.weibo+"' class='wb' target='_blank'></a>";
		 }
		 /* if(cp1.length>15){
			 cp2 = cp1.substring(0,15);
			 cp2+="...";
		 }*/
		 appendStr+= "<li><a href='"+ctx+"/user/f_"+data.userid+".html'><img src='"+data.portrait+"' width='44' height='44' alt='' />"+
                            "<div>"+data.name+" <br /><span title="+cp1+">"+cp2+"</span>"+
                        	"</div></a>"+weibo+addFriend +"</li>";
	 }
	 return appendStr;
}

function listSignedUpUsers(eventid,strStart,strSize){
	var isFirstLoad = true; //是否第一次加载数据
	if(!strStart){
		strStart = cur_suu_size;
		isFirstLoad = false;		
	}
	if(isFirstLoad){
		if(!strSize){
			strSize = defaultSUUSize;
		}
		//显示等待加载
		//$(".loadingnobg").show();
		$("#searchMoreSignUp").hide();
		$.ajax({
			   type: "POST",
			   url: ctx+"/user/suu_"+strStart+"_"+strSize+"_"+eventid+".json",
//			   data: "name=John&location=Boston",
			   success: function(data){
				  // $(".loadingnobg").hide();
			     if(data){
			    	 if(data.success==true){
			    		var list = data.content.result;
			    		var totalCount = data.content.totalCount;
			    		var len = list.length;
		    			 	if(len==0 && isFirstLoad){
	 							//$("#suubox").append("<li><div class=\"feedBox clear\">暂无报名人员</div></li>");
		    			 		$("#suubox").parents('.opinion').hide();
		    			 	}else{
		    						$("#suubox").append(userList2HTML(list));  
		    						//加好友
		    						$('.af').hover(function(){
		    							$(this).stop(true,true).delay(200).css('backgroundColor','#0061a6').animate({width:'40px'},200);
		    						},function(){
		    							$(this).stop(true,true).delay(200).animate({width:0},200).css('backgroundColor','#95a9b7');
		    						});
		    				}
		    			 	//判断数据是否存在更多
							if((cur_suu_size+len)<totalCount){
								$("#searchMoreSignUp").show();
							}else{
								$("#searchMoreSignUp").hide();
							}
			    		 cur_suu_size+= len;		    			    		
			    	 }else{
			    		 showMsgPanel(data.msg);
//			    		 $("#searchMoreSignUp").before("<div class=\"feedBox clearfix\">"+data.msg+"</div>");
			    	 }
			     }
			   }
			}); 
	}else{
		document.location.href=ctx+"/user/sun_"+eventid+".html";
	}
	
}