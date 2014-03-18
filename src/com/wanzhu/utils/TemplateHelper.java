package com.wanzhu.utils;

import java.io.File;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.wanzhu.base.ResourceLoader;
import com.wanzhu.base.WebContainerListener;

public class TemplateHelper {
	private static VelocityEngine engine = null;

	private static void init() throws Exception {
		engine = new VelocityEngine();
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,
				new File(URLDecoder.decode(new ResourceLoader().getResource("recommendation.tpl").getFile())).getParent());
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		p.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		p.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		engine.init(p);
	}

	public static VelocityEngine getEngine() {
		try {
			if (engine == null)
				init();
		} catch (Exception e) {
			LogFactory.getLog(WebContainerListener.class).error("无法创建模板引擎！", e);
		}

		return engine;
	}

	public static StringBuffer merge(String templateFile, Map<String, String> pairs) throws Exception {
		VelocityEngine engine = getEngine();
		Template template = engine.getTemplate(templateFile);
		VelocityContext context = new VelocityContext();
		Iterator<String> keyIter = pairs.keySet().iterator();
		String key = null;
		while (keyIter.hasNext()) {
			key = keyIter.next();
			context.put(key, pairs.get(key));
		}

		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		writer.flush();
		StringBuffer sb = writer.getBuffer();
		writer.close();
		writer = null;
		return sb;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> pairs = new HashMap<String, String>();
		pairs.put("name", "涂传滨");
		pairs.put("eventList", "活动1");
		StringBuffer sb = TemplateHelper.merge("recommendation.tpl", pairs);

		System.out.println(sb.toString());
	}
}
