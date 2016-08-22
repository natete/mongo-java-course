package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Hello world!
 */
public class InsertTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        MongoClient client = new MongoClient("localhost", 27017);

        MongoDatabase db = client.getDatabase("test");

        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name", "Smith")
                .append("age", 30)
                .append("profession", "programmer");

        Document jones = new Document("name", "Jones")
                .append("age", 25)
                .append("profession", "hacker");

        Helpers.printJson(smith);

        coll.insertOne(smith);
        smith.remove("_id");
        coll.insertOne(smith);

//        coll.insertMany(Arrays.asList(smith, jones));

        Helpers.printJson(smith);
    }
}
