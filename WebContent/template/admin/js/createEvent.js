$.fx.speeds._default = 300;
var type=0;
var ids=0;
var flags=0;
var arrUser=new Array(); 
var arrName=new Array();

function indexOf(val) {
    for (var i = 0; i <  arrUser.length; i++) {
        if (arrUser[i] == val) {
            return i;
        }
    }
    return -1;
};
function removeArray(val) {
    var index = indexOf(val);
    if (index > -1) {
    	 arrUser.splice(index, 1);
    	arrName.splice(index, 1);
    }
};


	$(function() {	
		var size=$("#picContent3 .picBox").size();
		if(size==1){
			$("#addopenFile3").hide();
		}
		 $( "#save" ).button({
            icons: {
                primary: "ui-icon-check"
            }
        });	 
		$( "#cancel" ).button({
            icons: {
                primary: "ui-icon-cancel"
            }
        });	
		$( "#addMan" ).button({
            icons: {
                primary: "ui-icon-plusthick"
            },
			text:false
        });	
		$(".add").button({
			 icons: {
                primary: "ui-icon-plus"
            }			 
						 });
		$(".upfile").button({
			 icons: {
                primary: "ui-icon-extlink"
            }			 
						 });
		//Tabs
		$( "#tabs" ).tabs();
		//spinner
		$( ".hours" ).spinner({ disabled: false,max:23,min:0 });
		$( ".minis" ).spinner({ disabled: false,max:59,min:0,step:1 });
		$( ".seconds" ).spinner({ disabled: false,max:59,min:0,step:1 });		
		//日期选择
		 $( ".datePicker" ).datepicker({			
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear:true,
			dateFormat: "yy-mm-dd",
			dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ] ,			
			monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
		});
		 //富文本编辑器
//		 $('#text1').wysiwyg();
//		 $('#text2').wysiwyg();
		//添加分享嘉宾
		$( "#memberList" ).dialog({
			autoOpen: false,
			show: "clip",
			height:512,
			hide: "explode",
			//modal: true,
			width:620,
            buttons: {
                "确定": function() {	                	
                	var flag=0;
                	var value=$("#shareMan").html();             
					$("#Meniframe").contents().find("input[name='check']:checkbox:checked").each(function(){
						var idStr="'"+$(this).val()+"'";
						var id=$(this).val();
						nameStr=$(this).parent().next("td.name").text();
						$('#shareMan a').each(function(i){
							if($(this).attr("id")==id){
								flag=1;
							}
							for ( var user = 0; user < arrUser.length; user++) {
								if(arrUser[user]==$(this).attr("id")){
									 removeArray(arrUser[user]);
								}
							}
						});

						for ( var user = 0; user < arrUser.length; user++) {
							value+=" "+"<a id="+arrUser[user]+" onclick=delMan('"+arrUser[user]+"') title='点击删除'>"+arrName[user]+"</a>";
						}
						if(flag==0){
							value+=" "+"<a id="+idStr+" onclick=delMan("+idStr+") title='点击删除'>"+nameStr+"</a>";
						}
						arrUser.length=0;
						arrName.length=0;
						flag=0;
					});					
					$(".shareMan").html(value);
					 $( this ).dialog( "close" );
                },
                "取消": function() {
                	$( this ).dialog( "close" );
                	arrUser.length=0;
                    
                }
            }
		});
		$("#addMan").click(function(){	
			var url=$("#userurl").val();
			var userids="";
			$('#shareMan a').each(function(i){
				userids+=$(this).attr("id")+"|";
			});
			$( "#memberList" ).html("<iframe width=\"100%\" height=\"100%\" id=\"Meniframe\" name=\"Meniframe\"  frameborder=\"0\" scrolling=\"no\" src="+url+"?userid="+userids+"></iframe>");
            $( "#memberList" ).dialog( "open" );
			return false;
		});		
		//上传
		$( "#uploadeDialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			//modal: true,
			width:620,
            buttons: {
                "上传": function() {		
                	if($("#typeValue").val()==0){
                		var txtImg=document.getElementById("myfiles2");
                		   if (txtImg.value==""){
                		   alert("请点击浏览按钮，选择您要上传的截图!");
                		   txtImg.focus();
                		   return false;
                		}
                		$('#uploadform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				},
                				'url' : {
                					required : true
                					//url:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				},
                				'url' : {
                					required: "必填字段"
                					//url: "请输入合法的网址"
                				}
                			}
                		});
                		if(!$("#uploadform").valid()){return;}
                		if(!validateTime("timeLong")){return;}    
                		uploadImage();
                	}
                	if($("#typeValue").val()==1){
                		var txtImg=document.getElementById("myfiles");
	             		   if (txtImg.value==""){
		             		   alert("请点击浏览按钮，选择您要上传的PPT文件!");
		             		   txtImg.focus();
		             		   return false;
	             		   }
                		var txtImg2=document.getElementById("myfiles2");
	             		   if (txtImg2.value==""){
		             		   alert("请点击浏览按钮，选择您要上传的截图!");
		             		   txtImg2.focus();
		             		   return false;
	             		   }
                		$('#uploadform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				}
                			}
                		});
                		if(!$("#uploadform").valid()){return;}
                		uploadppt();
                	}
                	if($("#typeValue").val()==3){
                		var txtImg=document.getElementById("myfiles");
	             		   if (txtImg.value==""){
		             		   alert("请点击浏览按钮，选择您要上传的海报!");
		             		   txtImg.focus();
		             		   return false;
	             		   }
                		$('#uploadform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				}
                			}
                		});
                		if(!$("#uploadform").valid()){return;}
                		
                		var size=$("#picContent3 div").size();
                		if(size==1){
                			alert("只能上传一张海报!");
                		}else{
                			uploadsnapshot();
                		}
                	}
                    $( this ).dialog( "close" );
                    $("#text001").val("");
                    $("#text002").val("");
                    $("#myfiles").val("");
                    $("#myfiles2").val("");
                    $("#timeLong label.error").remove();
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                    $("#text001").val("");
                    $("#text002").val("");
                    $("#timeLong label.error").remove();
                }
            }
		});
		
		$( "#playRedio" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			width:530,
			height:520,
			position:'top',
            buttons: {
                "关闭": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		
		//编辑
		$( "#editUploadeDialog" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			//modal: true,
			width:620,
            buttons: {
                "保存": function() {		
                	if($("#typeValue").val()==0){
                		$('#upform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				},
                				'url' : {
                					required : true
                					//url:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				},
                				'url' : {
                					required: "必填字段"
                					//url: "请输入合法的网址"
                				}
                			}
                		});
                		if(!$("#upform").valid()){return;}
                		if(!validateTime("EtimeLong")){return;}
                		upImage();
                	}
                	if($("#typeValue").val()==1){
                		$('#upform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				}
                			}
                		});
                		if(!$("#upform").valid()){return;}
                		upppt();
                	}
                	if($("#typeValue").val()==3){
                		$('#upform').validate({
                			rules : {
                				'words' : {
                					required : true
                				},
                				'num' : {
                					required : true,
                					rangelength: [0,2],
                					digits:true
                				}
                			},
                			messages : {
                				'words' : {
                					required: "必填字段"
                				},
                				'num' : {
                					required: "必填字段",
                					rangelength: jQuery.validator.format("序号无效。"),   
                					digits: "只能输入正整数"
                				}
                			}
                		});
                		if(!$("#upform").valid()){return;}
                		upsnapshot();
                	}
                    $( this ).dialog( "close" );
                    $("#EtimeLong label.error").remove();
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                    $("#EtimeLong label.error").remove();
                }
            }
		});
		
		
		//删除
		$( "#delConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			width:300,
			hide: "explode",
			//modal: true,
            buttons: {
                "确定": function() {
                	delsummary(ids,flags);
                    $( this ).dialog( "close" );
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		//新增标签
		$( "#addSign" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			width:200,
            buttons: {
                "确定": function() {	
                	if($("#newSign").val()==""){
                		alert("请填写标签名!");
                	}else{
                		saveLable($("#newSign").val());
                	}
                	
                },
                "取消": function() {
                	 $("#newSign").val("");
                    $( this ).dialog( "close" );
                }
            }
		});
		$("#addSignBtn").click(function(){			
            $( "#addSign" ).dialog( "open" );
			return false;
		});
		//添加标签
		var colors = new Array();
		colors[0]="#66b82b";colors[1]="#e050c3";colors[2]="#406ec3";colors[3]="#00998a";colors[4]="#ff6600";colors[5]="#52e9da";colors[6]="#85C034";
		$("#sign li a").click(function(){
			var ids=$(this).attr('name');
			var flag=0;
			$('#signBoxChoose a').each(function(i){
				if($(this).attr("id")==ids)
				{
					flag=1;
					alert("已选择该标签,请选择其他标签!");
				}
			})
			if(flag==0){
				 var styleStr="style='background-color:"+colors[parseInt(7*Math.random())]+"';";
				   if($(this).attr("id")== "addSignBtn") return ;							   
		           var str=$("#signBoxChoose").html();
				   str+=" ";
				   str+="<a id='"+$(this).attr('name')+"' onclick=\"delSign('"+$(this).attr('name')+"')\"  "+styleStr+" title='点击删除'>"+$(this).text()+"</a>";
				   $("#signBoxChoose").html(str);
			}
		});
		
	});
	//删除分享嘉宾
	function delMan(id){
		var Id="#"+id;
		$(Id).remove();
	}
	//删除标签
	function delSign(id){
		var Id="#"+id;
		$(Id).remove();
	}
	
	function validateTime(vid){
		var idStr="#"+vid;
		var errorMsg="";
		var reg=/^[-+]?\d*$/;     
		if($(idStr+" input.hours").val()==null && $(idStr+" input.minis").val()==null && $(idStr+" input.seconds").val()==null ){
		   errorMsg="时长不能为空.";
		   $(idStr).append("<label class=error>"+errorMsg+"</label>");
		   return false;
		}else if (!reg.test($(idStr+" input.hours").val())) {
		   errorMsg="请输入有效数字.";
		   $(idStr).append("<label class=error>"+errorMsg+"</label>");
		   return false;
		}else{
		   $(idStr+" label.error").remove();
		   return true;
		}
	}
	
	function openFileDialog(typeValue){
			ajaxbg2.hide();
		    $("#typeValue").val(typeValue);
		    type=typeValue;
			$("#cutPic,#videolink,#fileChoose,#timeLong,#wenzi,#xuhao").show();
			if(typeValue==0){//视频
				$("#myfiles2").val("");
        		$("#h").val("0");
        		$("#m").val("0");
        		$("#seconds").val("0");
        		$("#words").val("");
        		$("#num").val("");
        		$("#url").val("");
				$("#fileChoose").hide();
				$("#myfiles2").attr("accept","image/x-png, image/jpeg");
				}
			if(typeValue==1){//ppt
				$("#myfiles").val("");
				$("#myfiles2").val("");
        		$("#words").val("");
        		$("#num").val("");
				$("#videolink,#timeLong,#videoSource").hide();
				$("#myfiles").attr("accept","application/vnd.ms-powerpoint");
				$("#myfiles2").attr("accept","image/x-png, image/jpeg");
				}
			if(typeValue==3){//海报
				$("#xuhao").hide();
				$("#wenzi").hide();
				$("#myfiles").val("");
        		$("#words").val("海报");
        		$("#num").val("");
				$("#cutPic,#videolink,#timeLong,#showerror,#videoSource").hide();
				$("#myfiles").attr("accept","image/x-png, image/jpeg");
				}
			$("label.error").remove();
		    $( "#uploadeDialog" ).dialog( "open" );
			return false;
		}
	function editFileDialog(typeValue,id){
		ajaxbg2.hide();
			$("#Evideolink").hide();
			if(typeValue==0){//视频
				$("#Evideolink,#EtimeLong").show();
				}	
			if(typeValue==1){//PPT
				$("#Evideolink,#EtimeLong,#videoSourceEdit").hide();
				}	
			if(typeValue==3){//海报
				$("#Evideolink,#EtimeLong,#xuhao2").hide();
				}	
			$("label.error").remove();
			getsummary(typeValue,id);
			return false;
		}
	function del(id) {
			ids=id;
			$( "#delConfirm" ).dialog( "open" );
			return false;
		}
	function dels(id,flag) {
		ids=id;
		flags=flag;
		$( "#delConfirm" ).dialog( "open" );
		return false;
	}
	
	function load()
	{
		//添加标签
		var colors = new Array();
		colors[0]="#66b82b";colors[1]="#e050c3";colors[2]="#406ec3";colors[3]="#00998a";colors[4]="#ff6600";colors[5]="#52e9da";colors[6]="#85C034";
		$("#sign li span a").click(function(){
			var ids=$(this).attr('name');
			var flag=0;
			$('#signBoxChoose a').each(function(i){
				if($(this).attr("id")==ids)
				{
					flag=1;
					alert("已选择该标签,请选择其他标签!");
				}
			})
			if(flag==0){
			   var styleStr="style='background-color:"+colors[parseInt(7*Math.random())]+"';";
			   if($(this).attr("id")== "addSignBtn") return ;							   
	           var str=$("#signBoxChoose").html();
			   str+=" ";
			   str+="<a id='"+$(this).attr('name')+"' onclick=\"delSign('"+$(this).attr('name')+"')\"  "+styleStr+" title='点击删除'>"+$(this).text()+"</a>";
			   $("#signBoxChoose").html(str);
			}
		});
	}
	
	function secondToDate(second,displaycount) {
	    if (!second) {
	        return 0;
	    }
	    var time = '';
	    if (second >= 24 * 3600) {
	        time += parseInt(second / 24 * 3600) + ':';
	        second %= 24 * 3600;
	    }
	    if (second >= 3600) {
	    	time += parseInt(second / 3600) + ':';
	    	second %= 3600;
	    }
	    if (second >= 60) {
	        time += parseInt(second / 60) + ':';
	        second %= 60;
	    } else if(second < 60) {
	    	time += "0:";
	    }
	    if (second > 0) {
	        time += second;
	    } else if(second <= 0) {
	    	time += "0";
	    }
	    var arr=time.split(":");
	    var day="",h="",min="",sec="";
	    var str="";
	        if(arr.length==1)
	        {
	           sec=arr[0]<10?"0"+arr[0]:arr[0];
	           str=displaycount==4?("00:00:00:"+sec):("00:00:"+sec);
	        }
	        if(arr.length==2)
	        {
	             min=arr[0]<10?"0"+arr[0]:arr[0];
	             sec=arr[1]<10?"0"+arr[1]:arr[1];
	             str=displaycount==4?("00:00:"+min+":"+sec):("00:"+min+":"+sec);
	        }
	        if(arr.length==3)
	        {
	             h=arr[0]<10?"0"+arr[0]:arr[0];
	             min=arr[1]<10?"0"+arr[1]:arr[1];
	             sec=arr[2]<10?"0"+arr[2]:arr[2];
	             str=displaycount==4?("00:"+h+":"+min+":"+sec):(h+":"+min+":"+sec);
	        }
	        if(arr.length==4)
	        {
	             day=arr[0]<10?"0"+arr[0]:arr[0];
	             h=arr[1]<10?"0"+arr[1]:arr[1];
	             min=arr[2]<10?"0"+arr[2]:arr[2];
	             sec=arr[3]<10?"0"+arr[3]:arr[3];
	             str=displaycount==4?(day+":"+h+":"+min+":"+sec):h+":"+min+":"+sec;
	        }
	        return str;
	}
	
