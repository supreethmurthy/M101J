package com.tengen;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.ServerAddress;

public class HelloWorldMongoDB 
{

	public void deleteHomeworkGrades() throws UnknownHostException
	{
		DBCursor dbCursor = null;
		try
		{
			MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
			DB blogDatabase = client.getDB("blog");
			DBCollection usersCollection = blogDatabase.getCollection("users");
			
			 DBObject query = new BasicDBObject("username", "supreethmurthy");
			 DBObject	user = usersCollection.findOne(query);
			 System.out.println(user);
//			DB database = client.getDB("students");
//			DBCollection collection = database.getCollection("grades");
//			DBObject query = new BasicDBObject("type", "homework");
//			dbCursor = collection.find(query).sort(new BasicDBObject("student_id",1).append("score",1));
//			Iterator<DBObject> ite = dbCursor.iterator();
//			int i=1;
//			while (ite.hasNext())
//			{
//				DBObject document = ite.next();
//				if (i%2!=0)
//				{
//					ObjectId id = (ObjectId)document.get("_id");
//					//System.out.println(document);
//					collection.remove(new BasicDBObject("_id",id));
//				}
//				i++;
//				
//			}
		}finally
		{
			//dbCursor.close();
		}
	}
	
	public void deleteLowestHomeWorkGrades() throws UnknownHostException
	{
		DBCursor dbCursor = null;
		try
		{
			MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
			DB blogDatabase = client.getDB("school");
			DBCollection studentsCollection = blogDatabase.getCollection("students");
			DBObject query = new BasicDBObject("scores.type", "homework");
			//BasicDBObject fields=new BasicDBObject("scores.$", 1).append("scores.$", 1);
			
			dbCursor = studentsCollection.find(query);
			Iterator<DBObject> ite = dbCursor.iterator();
			while(ite.hasNext())
			{
				DBObject document = ite.next();
				Object id = document.get("_id");
				//System.out.println(id);
				BasicDBList list = (BasicDBList)document.get("scores");
				Iterator<Object> scoresListIte = list.iterator();
				List<BigDecimal> homeWorkList = new ArrayList<BigDecimal>();
				while(scoresListIte.hasNext())
				{
					BasicDBObject obj = (BasicDBObject)scoresListIte.next();
					String type = (String)obj.get("type");
					if (type.equalsIgnoreCase("Homework"))
					{
						Double score = (Double)obj.get("score");
						String string = score.toString();
						homeWorkList.add(new BigDecimal(string));
					}
				}
				Collections.sort(homeWorkList);
				BigDecimal lowestHomeWorkScore = homeWorkList.get(0);
				String lowestHWScore = lowestHomeWorkScore.toPlainString();
				scoresListIte = list.iterator();
				while(scoresListIte.hasNext())
				{
					BasicDBObject obj = (BasicDBObject)scoresListIte.next();
					String type = (String)obj.get("type");
					if (type.equalsIgnoreCase("Homework"))
					{
						Double score = (Double)obj.get("score");
						String homeWorkString = score.toString();
						if (homeWorkString.equals(lowestHWScore))
							scoresListIte.remove();
					}
				}
				BasicDBObject match = new BasicDBObject("_id", id); 
				studentsCollection.update(match, document);
				System.out.println("Removing "+lowestHomeWorkScore.toPlainString() + " from Id: "+id);
			}
		}
		finally
		{
			dbCursor.close();
		}
	}
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException 
	{
		HelloWorldMongoDB hello = new HelloWorldMongoDB();
		hello.deleteLowestHomeWorkGrades();
		/*DBCursor dbCursor = null;
		try
		{
			MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
			DB database = client.getDB("test");
			DBCollection collection = database.getCollection("names");
			//DBObject query = new BasicDBObject("type", "homework");
			//dbCursor = collection.find(query).sort(new BasicDBObject("student_id",1).append("score",1));
			dbCursor = collection.find();
			Iterator<DBObject> ite = dbCursor.iterator();
			int i=1;
			while (ite.hasNext())
			{
				DBObject document = ite.next();
				if (i%2!=0)
				{
					ObjectId id = (ObjectId)document.get("_id");
					
					collection.remove(new BasicDBObject("_id",id));
				}
				i++;
				
			}
		}finally
		{
			dbCursor.close();
		}*/
	}

}
