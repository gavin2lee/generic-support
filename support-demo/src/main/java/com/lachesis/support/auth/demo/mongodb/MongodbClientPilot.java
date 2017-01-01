package com.lachesis.support.auth.demo.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongodbClientPilot {
	int port = 27017;
	String host = "localhost";
	String dbName = "db";
	String collectionName = "users";
	
	public void start(){
		MongoClient mongoClient = new MongoClient( host , port );
		MongoDatabase db = mongoClient.getDatabase(dbName);
		
		
		Document doc = new Document("username", "mongo-test");
		doc.append("password", "123456");
		doc.append("gender", "M");
		
		
		MongoCollection<Document>  collection = null;
		
		collection = db.getCollection(collectionName);
		
		if(collection == null ){
			db.createCollection("users");
			collection = db.getCollection(collectionName);
		}
		collection.insertOne(doc);
		
		List<Document> docs = new ArrayList<Document>();
		
		for(int i=0; i < 10; i++){
			docs.add(new Document("i", i));
		}
		
		collection.insertMany(docs);
		
		mongoClient.close();
	}

	public static void main(String[] args) {
		new MongodbClientPilot().start();
	}

}
