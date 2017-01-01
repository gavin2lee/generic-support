package com.lachesis.support.auth.demo.mongodb;

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
		
		db.createCollection("users");
		
		MongoCollection<Document>  collection = db.getCollection(collectionName);
		collection.insertOne(doc);
		
		mongoClient.close();
	}

	public static void main(String[] args) {
		new MongodbClientPilot().start();
	}

}
