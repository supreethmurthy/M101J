package com.tengen;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HelloWorldFreeMarkerStyle {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void main(String[] args) throws IOException, TemplateException 
	{
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldFreeMarkerStyle.class, "/");
		Template helloTemplate = configuration.getTemplate("hello.ftl");
		StringWriter writer = new StringWriter();
		Map<String,Object> helloMap = new HashMap<String,Object>();
		helloMap.put("name", "Deepashree");
		
		helloTemplate.process(helloMap, writer);
		System.out.println(writer);
	}

}
