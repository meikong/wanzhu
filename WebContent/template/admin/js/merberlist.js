$.fx.speeds._default = 400;
var ids=null;
var audit=1;
var stas=1;
var audits=0;
var names="";
var users="";
	$(function() {	   	
		 $( "#search" ).button({
            icons: {
                primary: "ui-icon-search"
            }
        });	   
		//审核	   
		$( "#checkDialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
			width:750,
			height:600,
			position:'top',
            buttons:{
//            	"设为不受限": function() {
//            		if(audits==0)
//            	    {
//            			onAudit(ids,audit);
//            			if(document.getElementById("flag").value="1")
//            			{
//            				document.getElementById("tds"+ids).innerHTML="<font color=green>不受限</font>";
//            				 $( this ).dialog( "close" );
//            			}
//            		}else{
//            			alert("该会员不受限制,无需修改,请点击取消!");
//            		}
//                },
                "关闭": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		//查看  
		$( "#viewDialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
			width:750,
			height:600,
			position:'top',
            buttons: {    
                "关闭": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		//禁用
		$( "#disableConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			width: 300,
			modal: true,
            buttons: {
                "确定": function() {
                	onDisable(ids,audits);
                	if(document.getElementById("flag").value="1")
        			{
                		if(names==1){
                			document.getElementById("tda"+ids).innerHTML="<font color=green>激活</font>";
							var divin="<a href=\"#\" onclick=\"check('"+ids+"','../adminuser/queryuser',"+stas+")\">查看</a>&nbsp;|&nbsp;";
							if(stas==0){
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',1,'设为不受限',1,"+stas+",'"+users+"')\">设为不受限</a>&nbsp;|&nbsp;";
							}else{
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',0,'设为受限',0,"+stas+",'"+users+"')\">设为受限</a>&nbsp;|&nbsp;";
							}
							if(audits==0){
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',1,'激活',1,"+stas+",'"+users+"')\">激活</a>";      
							}else{
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',0,'禁用',0,"+stas+",'"+users+"')\">禁用</a>";
							}
                			document.getElementById("tdc"+ids).innerHTML=divin;
                		}else{
                			document.getElementById("tda"+ids).innerHTML="未激活";
                			var divin="<a href=\"#\" onclick=\"check('"+ids+"','../adminuser/queryuser',"+stas+")\">查看</a>&nbsp;|&nbsp;";
							if(stas==0){
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',1,'设为不受限',1,"+stas+",'"+users+"')\">设为不受限</a>&nbsp;|&nbsp;";
							}else{
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',0,'设为受限',0,"+stas+",'"+users+"')\">设为受限</a>&nbsp;|&nbsp;";
							}
							if(audits==0){
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',1,'激活',1,"+stas+",'"+users+"')\">激活</a>";      
							}else{
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',0,'禁用',0,"+stas+",'"+users+"')\">禁用</a>";
							}
                			document.getElementById("tdc"+ids).innerHTML=divin;
                		}
        				$( this ).dialog( "close" );
        			}
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		
		$( "#stateConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			width: 300, 
			modal: true,
            buttons: {
                "确定": function() {
                	onAudit(ids,audits);
                	if(document.getElementById("flag").value="1")
        			{
                		if(names==1){
                			document.getElementById("tds"+ids).innerHTML="<font color=green>不受限</font>";
							var divin="<a href=\"#\" onclick=\"check('"+ids+"','../adminuser/queryuser',"+stas+")\">查看</a>&nbsp;|&nbsp;";
							if(audits==0){
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',1,'设为不受限',1,"+stas+",'"+users+"')\">设为不受限</a>&nbsp;|&nbsp;";
							}else{
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',0,'设为受限',0,"+stas+",'"+users+"')\">设为受限</a>&nbsp;|&nbsp;";
							}
							if(stas==0){
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',1,'激活',1,"+stas+",'"+users+"')\">激活</a>";      
							}else{
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',0,'禁用',0,"+stas+",'"+users+"')\">禁用</a>";
							}
                			document.getElementById("tdc"+ids).innerHTML=divin;
                			}else{
                			document.getElementById("tds"+ids).innerHTML="受限";
                			var divin="<a href=\"#\" onclick=\"check('"+ids+"','../adminuser/queryuser',"+stas+")\">查看</a>&nbsp;|&nbsp;";
							if(audits==0){
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',1,'设为不受限',1,"+stas+",'"+users+"')\">设为不受限</a>&nbsp;|&nbsp;";
							}else{
								divin+="<a href=\"#\" onclick=\"state('"+ids+"',0,'设为受限',0,"+stas+",'"+users+"')\">设为受限</a>&nbsp;|&nbsp;";
							}
							if(stas==0){
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',1,'激活',1,"+stas+",'"+users+"')\">激活</a>";      
							}else{
								divin+="<a href=\"#\" onclick=\"disable('"+ids+"',0,'禁用',0,"+stas+",'"+users+"')\">禁用</a>";
							}
                			document.getElementById("tdc"+ids).innerHTML=divin;
                		}
        				$( this ).dialog( "close" );
        			}
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
	});
	function check(id,url,audit){
		ids=id;
		audits=audit;
		$("#checkDialog").load(url+"?userId="+id);
		$( "#checkDialog" ).dialog( "open" );
			return false;
		}
	function view(id,url){
		$("#viewDialog").load(url+"?userId="+id);
		$( "#viewDialog" ).dialog( "open" );
			return false;
		}	
	function disable(id,disable,name,flag,sta,user) {
			ids=id;
			audits=disable;
			names=flag;
			stas=sta;
			users=user;
			document.getElementById("disableConfirm").innerHTML="是否"+name+" "+user+" ?";
			$( "#disableConfirm" ).dialog( "open" );
			return false;
		}
	function state(id,disable,name,flag,sta,user) {
		ids=id;
		audits=disable;
		names=flag;
		stas=sta;
		users=user;
		document.getElementById("stateConfirm").innerHTML="是否把 "+user+" "+name+"?";
		$( "#stateConfirm" ).dialog( "open" );
		return false;
	}