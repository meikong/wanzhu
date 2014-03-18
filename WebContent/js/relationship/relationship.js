//删除
function del(userId) {
	if (confirm('你确定要删除该好友吗？'))
		location.href = ctx+"/relationship/del_" + userId + ".html";
}
//发私信
function sendMessage() {
	var textHtml=$("#msgContent").val();
	if(textHtml.length<=0){
		$.blockUI({ css: { 
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
        message:"写点东西吧，内容不能为空哦"
		}); 
        setTimeout($.unblockUI, 500); 
		return;
	}
	if(textHtml.length>200 ){
		$.blockUI({ css: { 
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
        setTimeout($.unblockUI, 500); 
		return;
	}
	jQuery.ajax({
		type : 'POST',
		dataType : "json",
		url : ctx+'/message/sendMessage.json',
		data : "friendId="+$("#toUserId").val()+"&content="+$("#msgContent").val(),
		success : function(data) {
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
		        message:"发送成功。"
				}); 
				setTimeout($.unblockUI, 600); 
				$("#message").hide();
			}else{
				alert("发送失败。");
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
		        message:"发送失败。"
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
