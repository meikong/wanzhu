/**
 *	查询某个用户的好友 
 */
function friends(_currentPage,_pageSize,_userId)
{
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/relationship/u_'+_userId+'.json',
		data : "currentPage="+_currentPage+"&pageSize="+_pageSize,
		success : function(data) {
			 var result = data.content.result;
			 if(data.success && null != result && result.length>0)
				{
					var portrait = "";
					for(var vo in result)
					{
						portrait =  result[vo].portrait;
						if(""== portrait || null == portrait) portrait = ctx+"/images/icon_man.png";
						if(null == result[vo].company) result[vo].company = "";
						if(null == result[vo].workPosition) result[vo].workPosition = "";

						var addFriend ="";
						
						 if($("#myId").val() != ''){
							 if(!result[vo].isFriend && result[vo].userId != $("#myId").val()){
								 addFriend = "<a href=\"javascript:add('"+result[vo].userId+"')\" class='af br3'>加好友</a>";
							 }
						 }else{
							 addFriend = '';
						 }
						
						 if( result[vo].userId == $("#myId").val()){
							 var appendContent = "<li id='"+result[vo].userId+"'><a href='"+ctx+"/user/i.html' target='_blank'><img src='"+portrait+"' width='44' height='44' alt='' />"+
		                        "<div><a href=\""+ctx+"/user/i.html\" target=\"_blank\">"+result[vo].username+"</a> <br /><span>"+cj(result[vo]) +"</span></div></a></li>";
						 }else{
							 var appendContent = "<li id='"+result[vo].userId+"'><a href='"+ctx+"/user/f_"+result[vo].userId+".html' target='_blank'><img src='"+portrait+"' width='44' height='44' alt='' />"+
					                        "<div><a href=\""+ctx+"/user/f_"+result[vo].userId+".html\" target=\"_blank\">"+result[vo].username+"</a> <br /><span>"+cj(result[vo]) +"</span></div></a>"+addFriend +"</li>";
						 }
						 
						$("#userFriendsContent").append(appendContent);
						//加好友
						$('.af').hover(function(){
							$(this).stop(true,true).delay(200).css('backgroundColor','#0061a6').animate({width:'40px'},200);
						},function(){
							$(this).stop(true,true).delay(200).animate({width:0},200).css('backgroundColor','#95a9b7');
						});
					}
					//是否显示更多好友链接
					if(data.content.totalCount>_pageSize)  $("#moreFriends").show();
				}else{
					//$("#userFriendsContent").append("<div>暂无好友</div>");
					$("#userFriendsContent").parents('.opinion').hide();
				}
		},
		error : function(err) {
		},
		complete : function() {
		}
	});
}

//获取单位和职位
function cj(vo)
{
	var result = "";
	if(null != vo.company)
	{
		result = vo.company;
	}
	
	if(null != vo.company && vo.company.length>0 && vo.workPosition && vo.workPosition.length>0)
		result+="  ";
	
	if(vo.workPosition)
	{
		result+=vo.workPosition;
	}
	return result;
}
function openLoginDialog(){
	$.unblockUI();
	showLoginDialog(function (data){
		if(data.success)
		{
			closeLoginDialog();
			window.location.reload();
		}
	},null,true);
}
//添加好友
function addFriend(){
	var invitation = $("#invitation").val();
	if(invitation.length>50 ){
		jQuery.blockUI({ css: {  backgroundColor: '#0061a6',   opacity: .8,   color: '#fff'  },
        overlayCSS:  {  backgroundColor: 'none' },
        message:"你输入的文本过长，请输入不多于50个字后再发送..."
		}); 
        setTimeout(jQuery.unblockUI, 2000); 
		return;
	}
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/relationship/addFriend.json',
		data : "invitation="+invitation+"&userByFriendid.userid="+$("#addFriend_friendId").val()+"&userByUserid.userid="+$("#userId").val(),
		success : function(data) {
			//登录超时
			if(data.msg == 20003)
			{
				tb_remove();
				jQuery.blockUI({ css: {  backgroundColor: '#0061a6',   padding: '15px',  opacity: .8,   color: '#fff'  },
			        overlayCSS:  {  backgroundColor: 'none' },
			        message:"登录超时，请重新&nbsp;&nbsp;<a style='color:#fff;font-size=12px; font-weight:bold;' onclick='openLoginDialog()'>登录&nbsp;</a>"
					}); 
				$('.blockOverlay').click($.unblockUI); 
				return;
			}
			if(data.success)
			{
				tb_remove();
				$.blockUI({ css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#0061a6', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px',  
		            color: '#fff' 
		        },
		        overlayCSS:  { 
		            backgroundColor: 'none', 
		            opacity:         0.6, 
		            cursor:          'wait' 
		        },
		        message:"添加成功，等待对方同意。"
				}); 
				setTimeout($.unblockUI, 600); 
				$("#message").hide();
			}else{
				$.blockUI({ css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#0061a6', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px',  
		            color: '#fff' 
		        },
		        overlayCSS:  { 
		            backgroundColor: 'none', 
		            opacity:         0.6, 
		            cursor:          'wait' 
		        },
		        message:"添加失败。"
				});
				setTimeout($.unblockUI, 600); 
			}
			$("#invitation").html("");
		},
		error : function(err) {
		},
		complete : function() {
		}
	});
}

//解除好友关系
function del(userId,currentUserId)
{
	if (!confirm('你确定要删除该好友吗？'))
		return;
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/relationship/del_'+userId+'.json',
		success : function(data) {
			//登录超时
			if(data.msg == 20003)
			{
				showLoginDialog(function(data){
					if(data.success)
					{
						closeLoginDialog();
						window.location.reload();
					}
				},null,true);
				return;
			}
			if(data.success)
			{
				tb_remove();
				$.blockUI({ css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#0061a6', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px',  
		            color: '#fff' 
		        },
		        overlayCSS:  { 
		            backgroundColor: 'none', 
		            opacity:         0.6, 
		            cursor:          'wait' 
		        },
		        message:"删除成功。"
				}); 
				setTimeout($.unblockUI, 600); 
				$("#message").hide();
				if($("#friend_em"))  $("#friend_em").html("<a href=\"javascript:add()\">加为好友</a>");
				if($("#friendNav_"+userId))
				{
					$("#friendNav_"+userId).attr("class","btnS3");
					$("#friendNav_"+userId).html("<a href=\"javascript:add('"+userId+"')\">加为好友</a>");
				} 
				if($("#"+currentUserId))  $("#"+currentUserId).remove();
			}else{
				$.blockUI({ css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#0061a6', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px',  
		            color: '#fff' 
		        },
		        overlayCSS:  { 
		            backgroundColor: 'none', 
		            opacity:         0.6, 
		            cursor:          'wait' 
		        },
		        message:"添加失败。"
				}); 
			}
		},
		error : function(err) {
		},
		complete : function() {
		}
	});
}
//
jQuery(function(){
jQuery('.textareaCount').val("");
jQuery('.statementText').html("还可以输入200字"); 
var limitNum = 200; 
var pattern = '还可以输入' + limitNum + '字'; 
jQuery('.statementText').html(pattern); 
jQuery('.textareaCount').live('keyup', 
function() { 
var remain = jQuery(this).val().length; 
if (remain > limitNum) { 
pattern = jQuery('字数超过限制，请适当删除部分内容'); 
} 
else { 
var result = limitNum - remain; 
pattern = '还可以输入' + result + '字'; 
} 
jQuery(this).siblings('.statementText').html(pattern); 
} 
); 
});
