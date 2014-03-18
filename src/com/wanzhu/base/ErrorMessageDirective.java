package com.wanzhu.base;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import com.wanzhu.utils.ErrorHelper;

public class ErrorMessageDirective extends Directive {

	@Override
	public String getName() {
		return "errorMsg";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		SimpleNode sn_region = (SimpleNode) node.jjtGetChild(0);     
        String region = (String)sn_region.value(context);   
        if(null != region) {
        	String errorMsg = ErrorHelper.error(region);
        	if(null != errorMsg) {
        		writer.write(errorMsg);
        		return true;
        	} else {
        		writer.write(ErrorHelper.error("33001"));
        	}
        }
		return false;
	}


}
