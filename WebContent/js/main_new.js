// JavaScript Document
$(function(){
	var wHeight = $(window).height();
	$("#container").css("minHeight",wHeight-252);
	
	//header下拉菜单
	$('.nav li').hover(function(){
		$(this).children('a').toggleClass('cur');
		$(this).children('ul').stop(true,true).slideToggle(200);
	});
	//首页活动视频排序
	$('.activity').each(function(i){
		if(i%2 == 1){
			$(this).addClass('mr0');
		}
	});
	//活动视频hover效果
	$('.activity img').hover(function(){
		$(this).next('.acti_im').css('backgroundPosition', '-38px -55px');
	},function(){
		$(this).next('.acti_im').css('backgroundPosition', '0 -55px');
	});
	//登录框
	$('.inputStyle').focus(function(){
		$(this).addClass('inputFocus');//.css('border','1px solid #b8e2ff');
	});
	$('.inputStyle').blur(function(){
		$(this).removeClass('inputFocus');//.css('border','1px solid #fff');
	});
	
	//编辑标签功能
	$('#container .edit').click(function(){
		var tagLength = $('.tags li').length;
		if(tagLength<5){
			$('.add_tag').removeClass('dn');
		}else{
			$('.prompt').removeClass('dn');
		}
		$('.curr_tags li span').removeClass('dn');
		$(this).addClass('dn');	
		$('#autocomplete').val('');
	});
	
	$('.curr_tags input').focus(function(){
		$(this).css('color','#333');
		$('.errorTag').addClass('dn');
		$('.sameTag').addClass('dn');
	});
	$('.curr_tags input').blur(function(){	
		$(this).css('color','#aaa');
	});
	/*$('#container .tags span').on("click",function(){
		var tagLength = $('.tags li').length;
		if(tagLength == 5){
			$('.prompt').addClass('dn');
			$('#container .tags span').addClass('dn');
			$('#container .edit').removeClass('dn');
		}
		$(this).parents('li').remove();	
	});*/
	$('.prompt .close').live("click",function(){
		var tag = $(this).parent().parent();
		$('.prompt').addClass('dn');
		$('.errorTag').addClass('dn');
		$('.sameTag').addClass('dn');
		$('li span',tag).addClass('dn');
		$('.edit',tag).removeClass('dn');
	});
	$('.add_tag .close').live("click",function(){
		if($('.curr_tags .tags li').length == 0){
			$('.curr_tags .edit').text('添加标签');
		}
		var tag = $(this).parent().parent();
		$('.add_tag').addClass('dn');
		$('.errorTag').addClass('dn');
		$('.sameTag').addClass('dn');
		$('li span',tag).addClass('dn');
		$('.edit',tag).removeClass('dn');
	});
	//加好友
	$('.af').hover(function(){
		$(this).stop(true,true).delay(200).css('backgroundColor','#0061a6').animate({width:'40px'},200);
	},function(){
		$(this).stop(true,true).delay(200).animate({width:0},200).css('backgroundColor','#95a9b7');
	});
	
	//banner slideshow
	var bCount = 1;
	var bannerNum = $('#slideshow li').length;
	$('#slideshow li').eq(0).addClass('curr');
	$('#slideshow').css('width',720*bannerNum+'px');
	$('#bannersNum em').html(bannerNum);
	if(bannerNum <= 1){
		$('.ctrl_btns .bctrl,.ctrl_btns div').hide();
	}
	var gallery_interval = setInterval(galleryLoop,3000);

	//auto funcition
	function galleryLoop(){
			if(bannerNum == 1){
				return false;
			}
			$('#slideshow').stop(true,true).animate({'margin-left':'-'+720*bCount+'px'},500);
			$('#slideshow li').removeClass('curr').eq(bCount).addClass('curr');
			bCount ++;
			$('#bannersNum span').html(bCount);
			if(bCount == bannerNum){
				bCount = 0;
			}
	}
	//for view next img
	$('#next').click(function(){
		clearInterval(gallery_interval);
		bCount = $('#slideshow li').index($('#slideshow li.curr'))+1;
		if(bCount == bannerNum){
			bCount = 0;
		}
		galleryLoop();
		gallery_interval = setInterval(galleryLoop,3000);
		
	});
	//for view prev img
	$('#prev').click(function(){
		clearInterval(gallery_interval);
		bCount = $('#slideshow li').index($('#slideshow li.curr'))-1;
		if(bCount < 0){
			bCount = bannerNum-1;
		}
		galleryLoop();
		gallery_interval = setInterval(galleryLoop,3000);
	});
	$('#slide_wrapper').hover(function(){
		clearInterval(gallery_interval);
	},function(){
		gallery_interval = setInterval(galleryLoop,3000);
	});
	
	
	//banner slideshow (old)
	var bannerNumSub = $('#slideshow_sub li').length;
	if(bannerNumSub == 1){
		$('.ctrl_btns_sub .bctrl').hide();
	}
	if(bannerNumSub == 0){
		$('#slide_wrapper_sub').parents('.opinion').hide();
	}
	$('#slideshow_sub').css('width',200*bannerNumSub+'px');
	var gallery_interval_sub = setInterval(galleryLoopSub,3000);
	//auto funcition
	function galleryLoopSub(){
		if(bannerNumSub == 1){
			return false;
		}
		$('#slideshow_sub').animate({left:'-200px'},500,function(){
			$('#slideshow_sub li:nth-child(1)').appendTo('#slideshow_sub'); 
			$('#slideshow_sub').css('left',0);
		});
	}
	//for view next img
	$('#next_sub').click(function(){
		clearInterval(gallery_interval_sub);
		galleryLoopSub();
		gallery_interval_sub = setInterval(galleryLoopSub,3000);
	});
	//for view prev img
	$('#prev_sub').click(function(){
		clearInterval(gallery_interval_sub);
		$('#slideshow_sub li:nth-child('+bannerNumSub+')').prependTo('#slideshow_sub');
		$('#slideshow_sub').css('left','-200px');
		$('#slideshow_sub').animate({left:0},500);
		gallery_interval_sub = setInterval(galleryLoopSub,3000);
	});
	$('#slide_wrapper_sub').hover(function(){
		clearInterval(gallery_interval_sub);
	},function(){
		gallery_interval_sub = setInterval(galleryLoopSub,3000);
	});

});


