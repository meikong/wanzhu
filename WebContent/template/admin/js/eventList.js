$.fx.speeds._default = 400;
var ids=null;
$(function() {	   	
	var urls=document.getElementById("url").value;	
		 $( "#search" ).button({
            icons: {
                primary: "ui-icon-search"
            }
        });	 
		  $( "#add" ).button({
            icons: {
                primary: "ui-icon-plus"
            }
        });	  
		 //日期选择
		 $( "#datePicker" ).datepicker({
			showOn: "button",
			buttonImage: urls+"images/calendar.gif",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear:true,
			dateFormat: "yy-mm-dd",
			dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ] ,			
			monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],			
			onSelect: function(dateText, inst) { 
				$("#time").val(dateText);
			}
		});
		//日期选择
		 $( "#datePicker2" ).datepicker({
			showOn: "button",
			buttonImage: urls+"images/calendar.gif",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear:true,
			dateFormat: "yy-mm-dd",
			dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ] ,			
			monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],			
			onSelect: function(dateText, inst) { 
				$("#time2").val(dateText);
			}
		});
		//删除
		$( "#delConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
            buttons: {
                "确定": function() {
                	ondelEvent(ids);
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		$("#add").click(function(){
		   
		});
	});
	function del(id) {
			ids=id;
			$( "#delConfirm" ).dialog( "open" );
			return false;
		}	
