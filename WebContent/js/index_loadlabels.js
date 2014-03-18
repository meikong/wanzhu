function loadLabels() {
	jQuery.ajax({
 	   type:"POST",
 	   url:ctx+"/label/hotLables.json",
 	   success: function(result){
 		   if(result.success == false){
 			   return false;
 		   }else{
 			  for(var i=0; i<result.length; i++) {
				   $("#labels").append("<a href=\""+ctx+"/event/ae_"+result[i].labelId+".html\">"+result[i].label+"</a>");
			   }
 		   }
 	   }
 	});
}