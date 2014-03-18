var cur_siu_size = 0;
var defaultSIUSize = 20;
/**
 * 获取参加用户列表html
 * @param list
 */
function usersiList2HTML(list){
	var appendStr = "";
	 for(var i=0;i<list.length;i++){
		 var data = list[i];
		 var cp1 = data.company+ " "+data.position;
		 var cp2 = cp1;
		 var weibo="";
		var addFriend = "";
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
		 /*if(cp1.length>15){
			 cp2 = cp1.substring(0,15);
			 cp2+="...";
		 }*/
		 appendStr+= "<li><a href='"+ctx+"/user/f_"+data.userid+".html'><img src='"+data.portrait+"' width='44' height='44' alt='' />"+
			         "<div>"+data.name+" <br /><span title="+cp1+">"+cp2+"</span>"+
			     	"</div></a>"+weibo+addFriend+"</li>";
	 }
	 return appendStr;
}
function listSignedInUsers(eventid,strStart,strSize){
	var isFirstLoad = true; //是否第一次加载数据
	if(!strStart){
		strStart = cur_siu_size;
		isFirstLoad = false;
	}
	if(!strSize){
		strSize = defaultSIUSize;
	}
	if(isFirstLoad){
		//显示等待加载
		//$(".loadingnobg").show();
		$("#searchMoreSignIn").hide();
		$.ajax({
			   type: "POST",
			   url: ctx+"/user/siu_"+strStart+"_"+strSize+"_"+eventid+".json",
//			   data: "name=John&location=Boston",
			   success: function(data){
				   //$(".loadingnobg").hide();
			     if(data){
			    	 if(data.success==true){
			    			var list = data.content.result;
				    		var totalCount = data.content.totalCount;
				    		var len = list.length;
				    		
				    		if(len==0 && isFirstLoad){
	 							//$("#siubox").append("<li><div>无参加人员</div></li>");
	 							$("#siubox").parents('.opinion').hide();
				    		}else{
		    						$("#siubox").append(usersiList2HTML(list));
		    						//加好友
		    						$('.af').hover(function(){
		    							$(this).stop(true,true).delay(200).css('backgroundColor','#0061a6').animate({width:'40px'},200);
		    						},function(){
		    							$(this).stop(true,true).delay(200).animate({width:0},200).css('backgroundColor','#95a9b7');
		    						});
		    				}
		    			 	//判断数据是否存在更多
							if((cur_siu_size+len)<totalCount){
								$("#searchMoreSignIn").show();
							}else{
								$("#searchMoreSignIn").hide();
							}
			    			 cur_siu_size+= len;
			    	 }else{
			    		 showMsgPanel(data.msg);
//			    		 $("#searchMoreSignUp").before("<div class=\"feedBox clearfix\">"+data.msg+"</div>");
			    	 }
			     }
			   }
			}); 
	}else{
		document.location.href=ctx+"/user/sin_"+eventid+".html";
	}
	
}

function sumarySeeMore() {
	document.getElementById("sumaryContent").innerHTML = document.getElementById("sumarycontainer").innerHTML;
	document.getElementById("sumaryseemore").style.display = "none";
}

var success = function() {
	window.location.reload();
}

//视频播放(播放次数+1)
function ShowFlash(vid, vs, p) {
	var IdStr = "#videoBox" + p;
	if(vs == 2) {
		url = "http://player.youku.com/embed/"+vid;
		var youku = "#youkuVideo"+p;
		$(youku).attr("src", url);
	}
	$(IdStr).show();
	$("#videoSmallBox"+p).hide();
	//updateCount("#playCount"+p);
}
//收起视频播放
function HideFlash(vs, p) {
	var IdStr = "#videoBox" + p;
	$("#videoSmallBox"+p).slideDown();
	$(IdStr).slideUp("slow");
	if(vs == 1) {
		//$(IdStr + " .videoZoom").css("display", "none");
	} else {
		//$(IdStr + " .videoZoom").html("");
		var youku = "#youkuVideo"+p;
		$(youku).attr("src", "");
	}
}

//更新下载或播放计数
function updateCount(objectId) {
	var summaryId = $(objectId).attr("_val");
	jQuery.ajax({
		type : "POST",
		url : ctx+"/summary/downloadCount/" + summaryId,
		data: "summaryId=" + summaryId,
		success : function(rs) {
			$(objectId).text(new Number($(objectId).text())+1);
		}
	});
}

//一键添加好友
function addAll(eventId)
{
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/relationship/add_friends_'+eventId+'.json',
		success : function(data) {
			//登录超时
			if(data.msg == 20003)
			{
				showLoginDialog(function (data){
					if(data.success){
						closeLoginDialog();
					}
				},null,true);
				return;
			}
			var message = "";
			if(data.success)
					message = "一键添加好友成功....";
			else
				message = "一键添加好友失败....";
			jQuery.blockUI({ css: {  backgroundColor: '#0061a6',   opacity: .8,   color: '#fff'  },
		        overlayCSS:  {  backgroundColor: 'none' },
		        message:message
				}); 
		    setTimeout(jQuery.unblockUI, 2000); 
		},
		error : function(err) {
		},
		complete : function() {
		}
	});
}
function downLoad(url, objectId) {
	window.open(url);
	updateCount(objectId);
}