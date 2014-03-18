	var topicStart = 0;
	var remarkStart = 0;
	var topicSize = 10;
	var remarkSize = 5;
	var eventId = "";
	var sessionUserId = "";
	
	function getTopicHTML(topic,topicid) {
		//vee changed
		var remarkPortrait,remarkPerson,remarkContent,html;
		var judge = false;
		$.ajax({
			type: "GET",
			dataType: "json",
			async:false,
			url: ctx + '/discuz/queryRemarks.json',
			data: "topicId=" + topicid + "&start=" + remarkStart + "&size=" + remarkSize,
			success: function(result) {
				var remarks = result.content;
				for(var i=0; i< remarks.length;i++){
					if(i==0){
						var hyperText = remarks[i].content;
						if(hyperText && hyperText.trim() != "") {
							var plainText = convertContentToPlainText(hyperText);
							if(plainText.length > 100) {
								var substr = plainText.substr(0, 100);
								hyperText = substr + '...&nbsp;<a href="' + ctx + '/discuz/t_' + topicId + '.html" class="link_b">显示全部>></a>';
							} 
							judge = true;
						}
						remarkPortrait = '<a href="' + ctx + '/user/f_' + remarks[i].userid + '.html" ><img src="'+ getPortrait(remarks[i].portrait) +'" width="44" height="44" alt="" class="tipALink"  uid="'+remarks[i].userid+'" id="'+UUID.get()+'" /></a>';
						remarkPerson = "<a href='" + ctx + "/user/f_" + remarks[i].userid + ".html' class='tipALink'  uid='"+remarks[i].userid+"' id='"+UUID.get()+"'>" + remarks[i].username + "</a> 回复了以下话题";
						remarkContent = "<div class='remark_cont'>" + hyperText + "</div>";
						
					}
				}
				
			}
		});
		if(judge){
			html = '<div class="topic">'+
						remarkPortrait+
		                '<dl>'+
		               		'<dt class="clear">'+
		                    	//'<a href="' + ctx + '/user/f_' + topic.userid + '.html" class="tipALink"  uid="'+topic.userid+'" id="'+UUID.get()+'">'+ topic.username + '<span>'+ topicUserPos(topic.userid) + '</span></a>'+
		                        remarkPerson + 
		               			'<span class="time">'+ topic.createtime +'</span>'+
		                    '</dt>'+
		                    '<dd class="clear">'+
		                    	'<a href="' + ctx + '/discuz/t_' + topicid + '.html"><h3>'+ topic.content +'</h3></a>'+
		                        remarkContent + 
		                    	getRemarkCount(topic) +
		                    '</dd>'+
		               	'</dl>'+
		            '</div>';
		}else{
			var tPortrait;
			if(topic.portrait == ''){
				tPortrait = ctx + '/images/icon_man.png';
			}else{
				tPortrait = topic.portrait;
			}
			html = '<div class="topic">'+
					'<a href="' + ctx + '/user/f_' + topic.userid + '.html" ><img src="'+ tPortrait +'" width="44" height="44" alt="" class="tipALink"  uid="'+topic.userid+'" id="'+UUID.get()+'" /></a>'+
					'<dl>'+
		           		'<dt class="clear">'+
		                	'<a href="' + ctx + '/user/f_' + topic.userid + '.html" class="tipALink"  uid="'+topic.userid+'" id="'+UUID.get()+'">'+ topic.username + '<span>'+ topicUserPos(topic.userid) + '</span></a>'+
		                    '<span class="time">'+ topic.createtime +'</span>'+
		                '</dt>'+
		                '<dd class="clear">'+
		                	'<a href="' + ctx + '/discuz/t_' + topicid + '.html"><h3>'+ topic.content +'</h3></a>'+
		                	FirstRemark(topicid) +
		                	getRemarkCount(topic) +
		                '</dd>'+
		           	'</dl>'+
		        '</div>';
		}
		return html;
	}
	function topicUserPos(uid){
		var html='', uid= uid;
		topicUserPosAjax(uid);
		return html;
		function topicUserPosAjax(uid){
			jQuery.ajax({
				type : 'POST',
				dataType : "json",
				async:false,
				url : ctx+'/discuz/aboutUser.json',
				data:"userId=$!session.getAttribute('USER_CONTEXT').getUserid()&friendId="+uid,
				success : function(result) {
					if(!result.success)
						return;
					var vo = result.content,company,workPosition;
					company = vo.company;
					workPosition =vo.workPosition;
					if(company == null){
						company = '';
					}
					if(workPosition == null){
						workPosition = '';
					}
					html = company + '&nbsp;' + workPosition;
				}
			});
		}
	}
	
	function getRemarkHTML(remark, topicId) {
		var agreecount,disagreecount;
		if(typeof(remark.agreecount) == "number"){
			agreecount = remark.agreecount;
		}else{
			agreecount = 0;
		}
		if(typeof(remark.disagreecount) == "number"){
			disagreecount = remark.disagreecount;
		}else{
			disagreecount = 0;
		}
		//var agreecount = remark.agreecount =  ? 0 :remark.agreecount; 
		//var disagreecount = remark.disagreecount = 0 ? 0 :remark.disagreecount; 
		var html = "<div class=\"topic\"><a href=\"" + ctx + "/user/f_" + remark.userid + ".html\"><img src=\"" + getPortrait(remark.portrait) + "\" width=\"44\" height=\"44\" alt=\"\" class=\"tipALink\"  uid=\""+remark.userid+"\" id=\""+UUID.get()+"\" /></a>"+
			"<dl><dt class=\"clear\"><a href=\"" + ctx + "/user/f_" + remark.userid + ".html\" class=\"tipALink\"  uid=\""+remark.userid+"\" id=\""+UUID.get()+"\">" + remark.username + "</a>"+
                "<span class=\"time\">"+ remark.createtime +"</span></dt>"+
            "<dd class=\"clear\"><div>"+ remark.content +"</div>"+
                "<a onclick='agree(\"" + remark.remarkid + "\");' class=\"a_ico like\">赞（<span id=\"agree_"+remark.remarkid+"\">" + agreecount + "</span>）</a>"+
                "<a onclick='disagree(\"" + remark.remarkid + "\");' class=\"a_ico unlike\">踩（<span id=\"disagree_"+remark.remarkid+"\">" + disagreecount + "</span>）</a>"+
                "</dd></dl></div>";
			
		return html;
	}
	
	function isAllowAgreeOrDisagree(topic) {
		var html = "";			
		if(topic.userid == sessionUserId) {
			html = "赞一个(<b id='agree_" + topic.topicid + "'>" + topic.agreecount + "</b>)&nbsp;&nbsp;&nbsp;&nbsp;"+
			       "踩一脚(<b id='disagree" + topic.topicid + "'>" + topic.disagreecount + "</b>)";
		} else {
			html = "<a onclick='agree(\"" + topic.topicid + "\");'>赞一个(<b id='agree_" + topic.topicid + "'>" + topic.agreecount + "</b>)</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
				   "<a onclick='disagree(\"" + topic.topicid + "\");'>踩一脚(<b id='disagree_" + topic.topicid + "'>" + topic.disagreecount + "</b>)</a>";
		}
		return html;
	}
	
	function getRemarkCount(topic) {
		var html = "";
		if(topic.remarkcount > 0) {
			html = '<a href="' + ctx + '/discuz/t_' + topic.topicid + '.html" id="' + topic.topicid + '" class="a_ico reply">回复（<span id="remarkcount_' + topic.topicid + '">'+ topic.remarkcount +'</span>）</a>';
		} else {
			html = '<a href="' + ctx + '/discuz/t_' + topic.topicid + '.html" id="' + topic.topicid + '" class="a_ico reply">回复</a>';
		}
		return html;
	}
	
	
	//回复
	$(".discussBox02 .itemInfo h4.clearfix span a").live('click', function() {
		$(".replyBox02").slideUp("fast");	
		if($(this).parents(".discussBox02").children(".replyBox02").css("display")=="none"){
		$(this).parents(".discussBox02").children(".replyBox02").slideDown("fast");
		}
		else {
			$(this).parents(".discussBox02").children(".replyBox02").slideUp("fast");
		}
	});
	//查看评论
	$(".discussBox .itemInfo .review span a").live('click',function(){
		if($(this).parent().parent().parent().children(".replyBox").size()==0) return;	
		$(".replyBox").children(".content").children(".remarkcontainer_class").html("");
		if($(this).parent().parent().next(".replyBox").css("display")=="none"){		
			remarkStart = 0;
			loadRemark($(this).attr("id"));			
			$(".replyBox").slideUp();
	    	$(this).parent().parent().next(".replyBox").slideDown();	    	
		}else{
			$(this).parent().parent().next(".replyBox").slideUp();
			displayStatus=true;
			}
	});
	//头像
	function getPortrait(portrait) {
		if(portrait == "")
			return ctx + "/images/icon_man.png";
		else
			return portrait;
	}

	//加载话题
	function loadTopic(_eventId) {//(_eventId, _sessionUserId)
		eventId = _eventId;
		//sessionUserId = _sessionUserId;
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/queryTopics.json',
			data: "eventId=" + eventId + "&start=" + topicStart + "&size=" + topicSize,
			success: function(result) {
				if(result.success) {
					var topics = result.content;
					var html = "";
					var tlength = topics.length > 10 ? topicSize : topics.length;
					for(var i = 0; i < tlength; i++) {
						html += getTopicHTML(topics[i],topics[i].topicid);
					}
					document.getElementById("eventTopic").innerHTML += html;
					topicStart += topicSize;
					/*if(result.content.length != topicSize) {*/
					if(topics.length >= topicSize) {
						$("#eventTopic").parents('.opinion').children('dt').append('<a href="'+ctx +'/discuz/s_'+eventId+'.html" class="more link_b">查看更多话题>></a>');
					}
					
					if(topics.length < 10){
						$('#btnMore').addClass('dn');
					}else{
						$('#btnMore').removeClass('dn');
					}	
					
				} /*else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
							//loadTopic(eventId, sessionUserId);
							loadTopic(eventId);
						},null,true);
						return;
					}else{
						alert(data.msg);
					}
				}*/
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}

	//获取评论
	function loadRemark(topicId,remarkStart) {
		jQuery.ajax({
			type: "GET",
			dataType: "json",
			url: ctx + '/discuz/queryRemarks.json',
			data: "topicId=" + topicId + "&start=" + remarkStart + "&size=" + remarkSize,
			success: function(result) {
				var remarks = result.content;
				var html = "";
				for(var i = 0; i < remarks.length; i++) {
					html += getRemarkHTML(remarks[i], topicId);
				}
				document.getElementById("remarkcontainer_" + topicId).innerHTML += html;
				remarkStart += remarkSize;
				
				if(remarks.length < remarkSize){
					$('#btnMore').addClass('dn');
				}else{
					$('#btnMore').removeClass('dn');
				}
				/*if(result.content.length != remarkSize) {
					document.getElementById("remarkmore_" + topicId).style.display = "none";
				} else {
					document.getElementById("remarkmore_" + topicId).style.display = "";
				}	*/
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	//获取首个评论
	function FirstRemark(topicId){
		var html = '',topicId = topicId;
		loadFirstRemark(topicId);
		function loadFirstRemark(topicId) {
			jQuery.ajax({
				type: "GET",
				dataType: "json",
				async:false,
				url: ctx + '/discuz/queryRemarks.json',
				data: "topicId=" + topicId + "&start=" + remarkStart + "&size=" + remarkSize,
				success: function(result) {
					var remarks = result.content;
					for(var i=0; i< remarks.length;i++){
						if(i==0){
							var hyperText = remarks[i].content;
							if(hyperText && hyperText.trim() != "") {
								var plainText = convertContentToPlainText(hyperText);
								if(plainText.length > 100) {
									var substr = plainText.substr(0, 100);
									hyperText = substr + '...&nbsp;<a href="' + ctx + '/discuz/t_' + topicId + '.html" class="link_b">显示全部>></a>';
								} 
							}
							html += "<span><a href='" + ctx + "/user/f_" + remarks[i].userid + ".html' class='tipALink'  uid='"+remarks[i].userid+"' id='"+UUID.get()+"'>" + remarks[i].username + "</a></span>"+
							"<div class='remark_cont'>" + hyperText + "</div>";
						}
					}
					
				}
			});
		}
		return html;
	}
	
	
	//回复评论  changed by vee
	function sendReplay(eventId,_topicId, _remarkId, _userName) {
		var textHtml=$("#txtReplay").val().trim();
		if(textHtml.length>1000 ){
			jQuery.blockUI({ css: {
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"你输入的文本过长，请确认后再发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		if(textHtml.length==0 ){
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"请输入回复,确认后发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		//var _content = document.getElementById("txtReplay").value;
		//_content = "@" + _userName + "：" + textHtml;
		
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/publish.json',
			data: {operateType : 3, eventId : eventId, topicId : _topicId, remarkId : _remarkId, content : textHtml},
			success: function(result) {
				if(result.success) {
					var html = getRemarkHTML(result.content, _topicId);
					document.getElementById("remarkcontainer_" + _topicId).innerHTML = html + document.getElementById("remarkcontainer_" + _topicId).innerHTML;
					
					if(document.getElementById("remarkcount_" + _topicId).innerHTML != "")
						document.getElementById("remarkcount_" + _topicId).innerHTML = parseInt(document.getElementById("remarkcount_" + _topicId).innerHTML) + 1;
					else
						document.getElementById(_topicId).innerHTML = "回复(<span id='remarkcount_" + _topicId + "'>1</span>)";
					
					document.getElementById("txtReplay").value = "";
				} else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
						},null,true);
						return;
					} else {
						alert(data.msg);
					}
				}	
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	
	//发表评论
	function sendRemark(_topicId) {
		var textHtml=jQuery("#txtRemark_"+_topicId).val().trim();
		if(textHtml.length>200 ){
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"你输入的文本过长，请确认后再发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		if(textHtml.length==0 ){
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"请输入评论,确认后发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		var _content = document.getElementById("txtRemark_" + _topicId).value;
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/publish.json',
			data: {operateType : 2, eventId : eventId, topicId : _topicId, remarkId : "", content : _content},
			success: function(result) {
				if(result.success) {
					var html = getRemarkHTML(result.content, _topicId);
					document.getElementById("remarkcontainer_" + _topicId).innerHTML = html + document.getElementById("remarkcontainer_" + _topicId).innerHTML;
					
					
					if(document.getElementById("remarkcount_" + _topicId).innerHTML != "") {
						document.getElementById("remarkcount_" + _topicId).innerHTML = parseInt(document.getElementById("remarkcount_" + _topicId).innerHTML) + 1;
					} else {
						document.getElementById(_topicId).innerHTML = "回复(<b id='remarkcount_" + _topicId + "'>1</b>)";
					}
					document.getElementById("txtRemark_" + _topicId).value = "";
				} else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
						},null,true);
						return;
					} else {
						alert(data.msg);
					}
				}
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	
	//发表话题
	function sendTopic() {
		var textHtml=jQuery("#txtTopic").val().trim();
		if(textHtml.length>200 ){
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"你输入的文本过长，请确认后再发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		if(textHtml.length==0 ){
			jQuery.blockUI({ css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#0061a6', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .8, 
	            color: '#fff' 
	        },
	        overlayCSS:  { 
	            backgroundColor: 'none', 
	            opacity:         0.6, 
	            cursor:          'wait' 
	        },
	        message:"请输入话题,确认后发送..."
			}); 
	        setTimeout(jQuery.unblockUI, 1000); 
			return;
		}
		var _content = document.getElementById("txtTopic").value;
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/publish.json',
			data: {operateType : 1, eventId : eventId, topicId : "", remarkId : "", content : _content},
			success: function(result) {
				if(result.success) {
					topic = result.content;
					var html = getTopicHTML(topic,topic.topicid);
					document.getElementById("eventTopic").innerHTML = html + document.getElementById("eventTopic").innerHTML;
					document.getElementById("txtTopic").value = "";
				} else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
						},null,true);
						return;
					} else {
						alert(data.msg);
					}
				}
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	
	//赞
	function agree(remarkId) {
		var agreeCount = parseInt($("#agree_" + remarkId).html())+1;
		$("#agree_" + remarkId).html(agreeCount);
		var disagreeCount = $("#disagree_" + remarkId).html();
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/agreeRemark.json?remarkId=' + remarkId+ '&agreecount=' +agreeCount+'&disagreecount='+disagreeCount,
			//data: "remarkId=" + remarkId+ "&agreecount=" +agreeCount+"&disagreecount="+disagreeCount,
			success: function(result) {
				if(result.success) {
					//document.getElementById("agree_" + topicId).innerHTML = result.content;
					return;
				} else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
						},null,true);
						return;
					} else {
						jQuery.blockUI({ css: {  backgroundColor: '#0061a6',   opacity: .8,   color: '#fff'  },
					        overlayCSS:  {  backgroundColor: 'none' },
					        message: result.msg
							}); 
					        setTimeout(jQuery.unblockUI, 1000); 
					}
				}
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	
	//踩
	function disagree(remarkId) {
		var disagreeCount = parseInt($("#disagree_" + remarkId).html())+1;
		$("#disagree_" + remarkId).html(disagreeCount);
		var agreeCount = $("#agree_" + remarkId).html();
		jQuery.ajax({
			type: "POST",
			dataType: "json",
			url: ctx + '/discuz/agreeRemark.json?remarkId=' + remarkId+ '&agreecount=' +agreeCount+'&disagreecount='+disagreeCount,
			//data: "remarkId=" + remarkId+ "&agreecount=" +agreeCount+"&disagreecount="+disagreeCount,
			success: function(result) {
				if(result.success) {
					//document.getElementById("disagree_" + topicId).innerHTML = result.content;
					return;
				} else {
					//登录超时
					if(result.msg == 20003) {
						showLoginDialog(function (data) {
							closeLoginDialog();
						},null,true);
						return;
					} else {
						jQuery.blockUI({ css: {  backgroundColor: '#0061a6',   opacity: .8,   color: '#fff'  },
					        overlayCSS:  {  backgroundColor: 'none' },
					        message: result.msg
							}); 
					        setTimeout(jQuery.unblockUI, 1000); 
					}
				}	
			},
			error: function(err) {
				alert("操作失败");
			},
			complete: function() {
			}
		});
	}
	//
	jQuery(function(){
	jQuery('.textareaCount').live('keyup', 
			function() { 
			var remain = jQuery(this).val().length; 
			if (remain > 200) { 
				var textValue=jQuery(this).val().substring(0,200);
				jQuery(this).val(textValue)
			} 
			});
	});