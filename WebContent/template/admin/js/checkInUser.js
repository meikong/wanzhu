$.fx.speeds._default = 400;
var ids=null;
var audit=1;
var stas=1;
var audits=0;
var users="";
var names="";
var auts=0;
var userId = "";
$(function(){
	$( ".search" ).button({
            icons: {
                primary: "ui-icon-search"
            },
			text:false
    });	
	//Tabs
		$( "#tabs" ).tabs();
		
		//dialog
		$( "#stateConfirm" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
            buttons: {
                "确定": function() {
                	signupEvent(ids,audits,names,stas,users,auts,userId);
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
		
		$( "#stateConfirm2" ).dialog({
			autoOpen: false,
			show: "clip",
			hide: "explode",
			modal: true,
            buttons: {
                "确定": function() {
                	auditEvent(ids,audits,names,stas,users,auts,userId);
                },
                "取消": function() {
                    $( this ).dialog( "close" );
                }
            }
		});
});

function check(id,disable,name,flag,sta,user,aut,_userId) {
	ids=id;
	audits=disable;
	names=flag;
	stas=sta;
	users=user;
	auts=aut;
	userId = _userId;
	document.getElementById("stateConfirm").innerHTML="是否将 "+user+" 设置为 "+name+"?";
	$( "#stateConfirm" ).dialog( "open" );
	return false;
}
function check2(id,disable,name,flag,sta,user,aut,_userId) {
	ids=id;
	audits=disable;
	names=flag;
	stas=sta;
	users=user;
	auts=aut;
	userId = _userId;
	document.getElementById("stateConfirm2").innerHTML="是否把 "+user+" "+name+"?";
	$( "#stateConfirm2" ).dialog( "open" );
	return false;
}