$.fx.speeds._default = 400;
var ids=0;
	$(function() {
		$( "#add" ).button({
            icons: {
                primary: "ui-icon-plus"
            }
        });	   
			  
		jQuery.validator.addMethod("userName", function(value, element) {       
		    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
		 }, "只能包括中文字、英文字母、数字和下划线");   
		
		//添加	   
		$( "#dialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
			width:450,
            buttons: {
                "添加": function() {                	
                	$('#saveform').validate({
                		rules : {
                			'administrator' : {
                				required : true,
                				rangelength: [6,64],
                				userName:true
                			},
                			'password' : {
                				required : true,
                				rangelength: [6,64]
                			},
                			'passwords' : {
                				required : true,
                				rangelength: [6,64],
                				equalTo: "#password"
                			},
                			'select':{
                				required : true
                			}
                		},
                		messages : {
                			'administrator' : {
                				required : "请填写用户名。",
                				rangelength: "请输入有效用户名。",
                				userName:"用户名无效。"
                			},
                			'password' : {
                				required : "请填写密码。",
                				rangelength: "请输入有效密码。"
                			},
                			'passwords' : {
                				required : "请填写确认密码。",
                				rangelength: "请输入有效密码。",
                				equalTo:"两次填写的密码不一致。"
                			},
                			'select2':{
                				required : "请选择角色。"
                			}
                		}
                	});
                	if(!$("#saveform").valid()){;return;}
                	
                	var administrator=$("#administrator").val();
                	var password=$("#password").val();
                	var role=$("#select").val();
                	var id="";
                	onUpdateAdmin(administrator,password,role,id);
                },
                "取消": function() {
                	$("label.error").remove();
                    $( this ).dialog( "close" );
                }
            }
		});
		$( "#add" ).click(function() {
			$("#administrator").val("");
			$("#password").val("");
			$("#passwords").val("");
			$("label.error").remove();
			$( "#dialog" ).dialog( "open" );
			return false;
		});
		//删除
		$( "#delConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			width:300,
			hide: "explode",
			modal: true,
            buttons: {
                "确定": function() {
                	delAdmin(ids);
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		//编辑	   
		$( "#editDialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
			width:450,
            buttons: {
                "修改": function() {
                	$('#subform2').validate({
                		rules : {
                			'administrator2' : {
                				rangelength: [6,64],
                				userName:true
                			},
                			'password2' : {
                				rangelength: [6,64]
                			},
                			'passwords2' : {
                				rangelength: [6,64],
                				equalTo: "#password2"
                			},
                			'select2':{
                				required : true
                			}
                		},
                		messages : {
                			'administrator2' : {
                				rangelength: "请输入有效用户名。",
                				userName:"用户名无效。"
                			},
                			'password2' : {
                				rangelength: "请输入有效密码."
                			},
                			'passwords2' : {
                				rangelength: "请输入有效密码。",
                				equalTo:"两次填写的密码不一致。"
                			},
                			'select2':{
                				required : "请选择角色。"
                			}
                		}
                	});
                	if(!$("#subform2").valid()){return;}
                	
                	var administrator=$("#administrator2").val();
                	var password=$("#password2").val();
                	var role=$("#select2").val();
                	var id=ids;
                	onUpdateAdmin(administrator,password,role,id);
                },
                "取消": function() {
                	$("label.error").remove();
                    $( this ).dialog( "close" );
                }
            }
		});
	});
	function del(id,name) {
			ids=id;
			$("#delConfirm").text("");
			$("#delConfirm").append("确认删除  "+name+" 吗？");
			$( "#delConfirm" ).dialog( "open" );
			return false;
		}
	function edit(id){
		$("label.error").remove();
		document.getElementById("password2").focus();
		$("#administrator2").val("");
		$("#password2").val("");
		$("#passwords2").val("");
		ids=id;
		queryAdmin(id);
		$("#editDialog" ).dialog( "open" );
			return false;
		}	