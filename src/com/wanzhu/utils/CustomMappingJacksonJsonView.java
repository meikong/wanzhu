package com.wanzhu.utils;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class CustomMappingJacksonJsonView extends MappingJacksonJsonView {

	@Override
	protected Object filterModel(Map<String, Object> model) {
		Map<?, ?> result = (Map<?, ?>) super.filterModel(model);  
        if (result.size() == 1) {  
            return result.values().iterator().next();  
        } else {  
            return result;  
        } 
	}

	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		super.setObjectMapper(objectMapper);
	}

    public static void main(String arg){
    	System.out.println("ccccccccccccccccc");
    }
}
