// JavaScript Document
$(document).ready(function(){		
	$("#Float").css("top",$(window).height()-300+"px");	
	$("#Float").css("right",$(window).width()/2-460+"px");	
	$(window).scroll(function (){							   
		var offsetTop = $(window).scrollTop() + 120 +"px";
		if($(document).scrollTop() + $(window).height() == $(document).height()){			
			offsetTop=$(window).height()-150;
			}
		$("#Float").animate({top : offsetTop },{ duration:500 , queue:false });
	}); 
}); 