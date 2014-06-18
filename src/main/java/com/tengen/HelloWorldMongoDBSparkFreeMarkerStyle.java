package com.tengen;

import java.io.StringWriter;
import java.net.UnknownHostException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldMongoDBSparkFreeMarkerStyle {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException 
	{
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldSparkFreeMarkerStyle.class, "/");
		MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
		DB database = client.getDB("test");
		final DBCollection collection = database.getCollection("names");
		
		Spark.get(new Route("/") {
			public Object handle(Request arg0, Response arg1) 
			{
				StringWriter writer = new StringWriter();
				try
				{
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					DBObject document = collection.findOne();
					helloTemplate.process(document, writer);
					
				}catch(Exception ex)
				{
					halt(500);
					ex.printStackTrace();
				}
				return writer;
			}
		});

	}

}
