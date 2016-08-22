package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/08/16.
 */
public class FindTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        MongoClient client = new MongoClient("localhost", 27017);

        MongoDatabase db = client.getDatabase("test");

        MongoCollection<Document> coll = db.getCollection("findTest");

        coll.drop();

        for (int i = 0; i < 10; i++) {
            coll.insertOne(new Document("x", i));
        }

        System.out.println("Find one");
        Document first = coll.find().first();

        Helpers.printJson(first);

        System.out.println("Find all with into: ");
        ArrayList<Document> all = coll.find().into(new ArrayList<Document>());
        for (Document cur : all) {
            Helpers.printJson(cur);
        }

        System.out.println("Find all with iteration: ");
        MongoCursor<Document> cursor = coll.find().iterator();
        try {
            while (cursor.hasNext()) {
                Helpers.printJson(cursor.next());
            }
        } finally {
            cursor.close();
        }

        System.out.println("Count: ");
        long count = coll.count();
        System.out.println(count);
    }
}
