package com.tengen;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;



public class HelloWorldSpark {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Spark.get(new Route("/") {
			public Object handle(Request arg0, Response arg1) {
				return "Hello World from Supreeth";
			}
		});
		Spark.get(new Route("/"){
			public Object handle(Request arg0, Response arg1) {
				return "Supreeth";
			}
		});
	}

}
