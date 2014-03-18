$(function() {
	$(".editABtn").click(function(){
		if($(this).parent().siblings(".new_work").css("display")=='none'){						  
			$(this).parent().siblings(".new_work").slideDown();
		}else{
			$(this).parent().siblings(".new_work").slideUp();
		}
		if($(this).parent().siblings(".editPwd").css("display")=='none')						  
			$(this).parent().siblings(".editPwd").slideDown();
		else
			$(this).parent().siblings(".editPwd").slideUp();
	});
		//编辑个人信息
		$("#editPersonal").click(function() {
			if ($("#personal").css("display") == 'none')
				$("#personal").slideDown("fast");
			else
				$("#personal").slideUp("fast");
		});
	});

/**
 * 编辑用户信息
 */
$(document).ready(function() {
	$(".editInfoBox02 a.editABtn").parent("span").hide();
	$(".itemInfo").children("ul").children("li.name").children("span").hide();
	$(".editInfoBox02").mouseover(function(){
		$(this).children("h4.clearfix").children("span").show();
	}).mouseout(function(){
		$(this).children("h4.clearfix").children("span").hide();
	});
	$(".personalInfo").mouseover(function(){
		$(this).children(".itemInfo").children("ul").children("li.name").children("span").show();
	}).mouseout(function(){
		$(this).children(".itemInfo").children("ul").children("li.name").children("span").hide();
	});
	var textTemp="none";
	$(".experienceBox").live('mouseover',function(){
		textTemp=$(this).children(".itemInfo").children("h4").children("span").css("display");
		if(textTemp=="none"){
			$(this).children(".itemInfo").children("h4").children("span").text("设为当前公司");
			$(this).children(".itemInfo").children("h4").children("span").css("color","#0061a6").css("cursor","pointer");
			$(this).children(".itemInfo").children("h4").children("span").show();
		}
	}).live('mouseout',function(){		
		$(this).children(".itemInfo").children("h4").children("span").text("当前公司");
		if(textTemp == "none"){
			$(this).children(".itemInfo").children("h4").children("span").hide();
		}
	});
	$(".experienceBox .itemInfo h4 span").live('click',function(){
		if($(this).text()=="设为当前公司"){
			var param = new Object();
			param.workexperienceid = $(this).attr("cid");
			$.ajax({
				   type: "POST",
				   dataType : "json",
				   url: ctx+"/user/cjob.json",
				   data: param,
				   success: function(data){
					   if(data){
						   if(data.success==true){
							   textTemp="";
							   $(".experienceBox .itemInfo h4 span").css('display','none');
							   $(".experienceBox .itemInfo h4 span[cid="+param.workexperienceid+"]").css('color','').css("cursor","").text("当前公司").show();
							   $("#box_"+param.workexperienceid).insertBefore($("#box_"+param.workexperienceid).parents(".job").children(".experienceList").first().children(".experienceBox").first());
							   reOrderList("job");
							   //$(this).show();
						   }else{
							 //登录超时
								if(data.msg == 20003)
								{
									showLoginDialog(function(data){
										closeLoginDialog();
										delWe(weid);
									},null,true);
									return;
								}else{
									showMsgPanel(data.msg);
								}	
						   }
					   }
				   }
				}); 
//			alert("t"+$(this).attr("cid"));
		}
	});
});
$('#edituser_form').ajaxForm({
	dataType : "json",
    beforeSubmit: function(a,f,o) {
    /*	// 联系电话(手机/电话皆可)验证   
    	jQuery.validator.addMethod("isMobilePhone", function(value,element) {   
       		return this.optional(element) || is_mobile(value) ;   
    	}, "请正确填写您的手机号码"); 	*/
    	jQuery.validator.addMethod("checkName",function(value, element) {
//    		var myreg = /^[\u4e00-\u9fa5]+$/;
    		var myreg = /^[\u4e00-\u9fa5\A-Za-z\s]+$/;
    		if (value != '') {if (!myreg.test(value)) {return false;}};
    		return true;}, "请输入你的真实姓名"); 
    	//添加转换小写功能
    	jQuery.validator.addMethod("toLowerCase", function(value, element) {
    		value = $.trim(String(value));//去空
    		if(value==""){element.value = ""; return this.optional(element)|| true;}
    		if(value.toLowerCase()!=value) element.value = value.toLowerCase();
    		return this.optional(element)|| true;
    		}, "");
    	$('#edituser_form').validate({
    		onfocusin:true,
    		rules : {
    			'name' : {
    				required : true,
    				maxlength: 64,
    				checkName:true,
    				minlength:2
    			},    			
    			/*'phone' : {
    				required : true,
    				isMobilePhone:true
    			},
    			'personalemail' : {
    				required : true,
    				maxlength: 128,
    				email: true,
    				toLowerCase:true
    			},*/
    			'summary':{
    				maxlength:100
    			}
    		},
    		messages : {
    			'name' : {
    				required : "请输入你的真实姓名",
    				maxlength: "请输入你的真实姓名",
    				minlength: "请输入你的真实姓名"
    			},
    			/*'phone' : {
    				required : "请填写手机号码!",
    				isMobilePhone: "请正确填写您的手机号码。"
    			},
    			'personalemail' : {
    				required : "请填写个人邮箱!",
    				maxlength: "请正确填写个人邮箱，最大长度为128字符！",
    				email: "对不起，您填写的E-mail格式不正确！格式：you@163.com。"
    			},*/
    			'summary':{
    				maxlength:"请正确输入介绍,最大长度为100个字"
    			}
    		}
    	});
    	if ($('#edituser_form').valid()) {    
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					//更新完善度
					$(".degreeBox span").html("<em style=\"width:"+data.content+";\">"+data.content+"</em>");
					updateUserInfo();
					showMsgPanel();
				} else {
					//登录超时
					if(data.msg == 20003)
					{
						showLoginDialog(function (data)
						{
							closeLoginDialog();
							$('#edituser_form').submit();
						},null,true);
						return;
					}else{
						showMsgPanel(data.msg);
					}					
				}
			}
    	}
    });


/**
 * 更新用户信息
 */
function updateUserInfo(){
	$("#editPersonal").click();
	//$('#user_portrait').attr("src",$("[name='portrait']").val());
	$('#user_name').html($("[name='name']").val());
	if($("[name='sex']:checked").val()==0){
		$('#user_sexName').html("保密");
	}else if($("[name='sex']:checked").val()==1){
		$('#user_sexName').html("<img src=\""+ctx+"/images/icon_woman02.gif\" />");
	}else if($("[name='sex']:checked").val()==2){
		$('#user_sexName').html("<img src=\""+ctx+"/images/icon_man02.gif\"/>");
	}else{
		$('#user_sexName').html("未知性别");
	}
	$('#user_summary').text($("[name='summary']").val());
	$('#user_phone').html($("[name='phone']").val());
	$('#user_personalemail').html($("[name='personalemail']").val());
	//更新头部名称
	$(".topA em").html($("[name='name']").val());	
}

/**
 * 添加工作经历
 */
$('#add_we_form').ajaxForm({
	dataType : "json",
    beforeSubmit: function(a,f,o) {
    	$('#add_we_form').validate({
    		onfocusin:true,
    		rules : {
    			'company' : {
    				required : true,
    				maxlength: 64
    			},    			
    			'position' : {
    				required : true,
    				maxlength: 64
    			}
    		},
    		messages : {
    			'company' : {
    				required : "请输入你的公司名称",
    				maxlength: "请输入你的公司名称"
    			},
    			'position' : {
    				required : "请输入你的职位名称",
    				maxlength: "请输入你的职位名称"
    			}
    		}
    	});
    	if ($('#add_we_form').valid()) {    	
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					updateCompList(data.content);
					$('#add_we_form').parent().slideUp();
					showMsgPanel();
				} else {
					//登录超时
					if(data.msg == 20003)
					{
						showLoginDialog(function (data)
						{
							closeLoginDialog();
							$('#add_we_form').submit();
						},null,true);
						return;
					}else{
						showMsgPanel(data.msg);
					}	
				}
			}
    	}
    });

/**
 * 更新工作经历列表
 * @param data
 */
function updateCompList(data){
	var appendStr="<li id=\"box_"+data.workexperienceid+"\" class=\"experienceBox\">"+
	"<img src=\""+data.logo+"\" width=\"50\" height=\"50\" /><div class=\"itemInfo\">"+
    "<h4 id=\"cur_"+data.workexperienceid+"\">"+data.company+"";
	if(data.current=="1"){
		appendStr+="<span cid="+data.workexperienceid+">";
	}else{
		appendStr+="<span cid="+data.workexperienceid+" style=\"display:none\">";
	}
	appendStr+="当前公司";
	appendStr+="</span></h4><span>"+data.position+"</span> <a onclick='delWe(\""+data.workexperienceid+"\")' class='link_b'>删除</a></div></li>";
	
	$('#workWrapper').append(appendStr);
	//清空表单数据
	$("input[name='company']").val("");
	$("input[name='position']").val("");
	return ;
}

/**
 * 删除工作经历
 */
function delWe(weid){
	var param = new Object();
	param.workexperienceid = weid;
	$.ajax({
		   type: "POST",
		   dataType : "json",
		   url: ctx+"/user/dwe.json",
		   data: param,
		   success: function(data){
			   if(data){
				   if(data.success==true){
					   $("#box_"+weid).remove();
					   reOrderList("job");
					   if(data.content){
						   $("#cur_"+data.content+" em").css('color','').show();
					   }
				   }else{
					 //登录超时
						if(data.msg == 20003)
						{
							showLoginDialog(function(data){
								closeLoginDialog();
								delWe(weid);
							},null,true);
							return;
						}else{
							showMsgPanel(data.msg);
						}	
				   }
			   }
		   }
		}); 
}



/**
 * 添加教育经历
 */
$('#add_ee_form').ajaxForm({
	dataType : "json",
    beforeSubmit: function(a,f,o) {
    	$('#add_ee_form').validate({
    		onfocusin:true,
    		rules : {
    			'school' : {
    				required : true,
    				maxlength: 64
    			}
    		},
    		messages : {
    			'school' : {
    				required : "请输入你的学校名称",
    				maxlength: "请输入你的学校名称",
    			}
    		}
    	});
    	if ($('#add_ee_form').valid()) {  
    	} else
    		return false;
    	},
    	success: function(data){
    		if (data) {
				if (data.success == true) {
					updateEEList(data.content);
					$('#add_ee_form').parent().slideUp();
					showMsgPanel();
				} else {
					//登录超时
					if(data.msg == 20003)
					{
						showLoginDialog(function (data)
						{
							closeLoginDialog();
							$('#add_ee_form').submit();
						},null,true);
						return;
					}else{
						showMsgPanel(data.msg);
					}	
				}
			}
    	}
    });

/**
 * 更新教育经历
 * @param data
 */
function updateEEList(data){
	var appendStr = "<li id=\"box_"+data.educationexperienceid+"\" class=\"experienceBox\">"+
                    		"<img src=\""+data.logo+"\" width=\"50\" height=\"50\" />"+
               		        "<div class=\"itemInfo\"><h4>"+data.school+"</h4>"+
                   			"<a  onclick=\"delEE('"+data.educationexperienceid+"')\" class=\"link_b\">删除</a></div></li>";
	
	$("#educationWrapper").append(appendStr);
	//清空表单数据
	$("input[name='school']").val("");
	return;
	
}

/**
 * 删除教育经历
 * @param eeid
 */
function delEE(eeid){
	var param = new Object();
	param.educationexperienceid = eeid;
	$.ajax({
		   type: "POST",
		   dataType : "json",
		   url: ctx+"/user/dee.json",
		   data: param,
		   success: function(data){
			   if(data){
				   if(data.success==true){
					   $("#box_"+eeid).remove();
					   reOrderList("education");
				   }else{
					 //登录超时
						if(data.msg == 20003)
						{
							showLoginDialog(function (data)
									{
								closeLoginDialog();
								delEE(eeid);
							},null,true);
							return;
						}else{
							showMsgPanel(data.msg);
						}	
				   }
			   }
		   }
		}); 
}
function reOrderList(navClass){
	var nav="."+navClass;
	var reHtml="";
	var count=0;
	if($(nav+" .experienceList").children(".experienceBox").size()>0){
		$($(nav+" .experienceList").children(".experienceBox")).each(function(){
			var boxId=$(this).attr("id");
			var boxStr="<div id=\""+boxId+"\" class=\"experienceBox clear\">"+$(this).html()+"</div>";
				if(count%2==0){
					reHtml+="<div class=\"experienceList clear\">"+boxStr;
			}else{
				reHtml+=boxStr+"</div>";
			}
				count++;
		});		
		$(nav+" .experienceList").remove();
		$(nav).append(reHtml);
	}
}

//我的个人动态
function activityOfMine(_currentPage,_pageSize)
{
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/activity/queryActivitiesOfMine.json',
		data : "currentPage="+_currentPage+"&pageSize="+_pageSize,
		success : function(data) {
			  //登录超时
				if(data.msg == 20003)
				{
					showLoginDialog(loginsuccess,null,true);
					return;
				}
				var result = data.content.result;
				if(data.success && null != result && result.length>0)
				{
					for(var vo in result)
					{
		            	var appendContent = "<div class=\"noticeBox clearfix\"><span>"+getDateDiff(""+result[vo].time+"")+"</span>"+result[vo].content+"</div>";
						$("#activityOfMineContent").append(appendContent);
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

//加载更多
function more()
{
	activityOfMine(currentPage,pageSize);
}

//登录成功回调
function loginsuccess(data)
{
	if(data.success)
		closeLoginDialog();
}

//我的好友
function friends()
{
	var size = 10;
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/relationship/friends.json',
		data : "currentPage=1&pageSize="+size,
		success : function(data) {
			if(data.msg == 20003)
				return;
			
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
						
						var appendContent ="<li id='"+result[vo].userId+"'><a href='"+ctx+"/user/f_"+result[vo].userId+".html' target='_blank'><img src='"+portrait+"' width='44' height='44' alt='' />"+
                        "<div><a href=\""+ctx+"/user/f_"+result[vo].userId+".html\" target=\"_blank\">"+result[vo].username+"</a> <br /><span>"+cj(result[vo]) +"</span></div></a></li>";
						
					$("#friendsContent").append(appendContent);
					}
					//是否显示更多好友链接
					if(data.content.totalCount>size) 
						$("#moreFriends").show();
					
			}else{
				//$("#friendsContent").append("<div class=\"feedBox clearfix\">暂无好友</div>");
				$("#friendsContent").parents('.opinion').hide();
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

//公司模糊查询提示
//li mousehover
$("#ctip li.emailBox").live('mouseover',function(){
	$(this).css("background-color","#ccc");
});
//li mouseout
$("#ctip li.emailBox").live('mouseout',function(){
	$(this).css("background-color","#fff");
});
//选择
$("#ctip li.emailBox").live('click',function(){
	var nameStr=$(this).text();
	$("input[name='company']").val(nameStr);
		$(".searchListBox02 ul").html("");
	$(".searchListBox02").hide();
});	
function showCTip() {
	var availableCompanys = new Array();
	var _leftName = document.getElementById("_company").value;	
	if(_leftName != "") {
		jQuery.post(ctx+"/user/queryCompanyLikeName.json", 
				   {leftName: _leftName},	
				   function(data) {
						//var html = "";
						for(var i = 0; i < data.content.length; i++) {
							 /* html += "<li class='emailBox'>"+
		                      			 data.content[i].company + 
		                  	  		  "</li>";*/
							availableCompanys.push(data.content[i].company);
				   		}
						$( "#_company" ).autocomplete({
							source: availableCompanys
						});
						
						/*document.getElementById("ctip").innerHTML = html;
						$(".searchListBox02").show();*/
		});
	}
}

//学校模糊查询提示
//li mousehover
$("#stip li.emailBox").live('mouseover',function(){
	$(this).css("background-color","#ccc");
});
//li mouseout
$("#stip li.emailBox").live('mouseout',function(){
	$(this).css("background-color","#fff");
});
//选择
$("#stip li.emailBox").live('click',function(){
	var nameStr=$(this).text();
	$("input[name='school']").val(nameStr);
		$(".searchListBox02 ul").html("");
	$(".searchListBox02").hide();
});	
function showSTip() {
	var availableSchools = new Array();
	var _leftName = $("#schoolValid").val();
	if(_leftName != "") {
		jQuery.post(ctx+"/user/querySchoolLikeName.json", 
				   {leftName: _leftName},	
				   function(data) {
						//var html = "";
						for(var i = 0; i < data.content.length; i++) {
							  /*html += "<li class='emailBox'>"+
		                      			 data.content[i].school + 
		                  	  		  "</li>";*/
							availableSchools.push(data.content[i].school);
				   		}
						$( "#schoolValid" ).autocomplete({
							source: availableSchools
						});
						
		});
	}
}
