/**
 * 改变标签
 * @param list
 */
function saveTag(eventId,labelId,labelName){
	//var labelid = $('.tagField').attr('id');
	var judge = true;
	var re = /[^\u4e00-\u9fa5a-zA-Z0-9]/ig;
	var val = $('.tagField').val().trim();
	
	if(!re.test(val)){
		$('.curr_tags .tags li').each(function(){
			if(val == $('a',this).text().trim()){
				$('.sameTag').removeClass('dn');
				judge = false;
			}
		});
		if(judge){
			if(labelId == ''){
				data = "eventid="+eventId+"&labelName="+labelName;
			}else{
				data = "eventid="+eventId+"&labelName="+labelName+"&labelid="+labelId;
			}
			$.ajax({
				type: "POST",
				   url: ctx+"/label/saveUserLabel",
				   data: data,
				   async:false,
				   success: function(result){
						var id = result.labelId, name = result.label;
						if(id == '' && name ==''){
						}else{
							$('.tags').append('<li class="br5" id="'+id+'"><a href="${base}/event/e_1_0_'+id+'.html">'+name+'</a><span class="dn">x</span></li>');
						}
					}
			});
			$('.add_tag').addClass('dn');
			$('.curr_tags li span').addClass('dn');
			$('.curr_tags .edit').text('编辑').removeClass('dn');
			$('.sameTag').addClass('dn');
		}
	}else{
		$('.sameTag').removeClass('dn');
	}
}