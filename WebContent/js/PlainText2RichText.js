
function convertContentToPlainText(hyperText)
{
	hyperText = hyperText.replace(/<[\s]*?script[^>]*?>[\s\S]*?<[\s]*?\/[\s]*?script[\s]*?>/img,"");
	hyperText = hyperText.replace(/<[\s]*?style[^>]*?>[\s\S]*?<[\s]*?\/[\s]*?style[\s]*?>/img,"");
	hyperText = hyperText.replace(/&nbsp;/mg," ");
	hyperText = hyperText.replace(/<\/p>/img,"\r\n");
	hyperText = hyperText.replace(/<br>/img,"\r\n");
	hyperText = hyperText.replace(/<.+?>/mg,"");
	hyperText = hyperText.replace(/&lt;/mg, "<");
	hyperText = hyperText.replace(/&gt;/mg, ">");
	
	return hyperText;
}


function convertContentToHyperText(plainText)
{
	plainText = plainText.replace(/</mg, "&lt;");
	plainText = plainText.replace(/>/mg, "&gt;");
	plainText = plainText.replace(/[\r\n]+/mg, "<\/p><p>");
	plainText = "<p>" + plainText + "<\/p>";
	return plainText;
}