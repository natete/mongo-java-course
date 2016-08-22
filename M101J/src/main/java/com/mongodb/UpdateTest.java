package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/08/16.
 */
public class UpdateTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("UpdateTest");

        collection.drop();

        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                    .append("_id", i)
                    .append("x", i)
                    .append("y", true));
        }


//        collection.replaceOne(Filters.eq("x", 5), new Document("x", 20).append("updated", true));

//        collection.updateOne(Filters.eq("x", 5), new Document("$set",
//                new Document("x", 20).append("updated", true)));

//        collection.updateOne(Filters.eq("x", 5), Updates.combine(
//                Updates.set("x", 20),
//                Updates.set("updated", true)));
//
//        collection.updateOne(Filters.eq("_id", 9), Updates.combine(
//                Updates.set("x", 20),
//                Updates.set("updated", true)),
//                new UpdateOptions().upsert(true));

        collection.updateMany(Filters.gte("x", 5), Updates.inc("x", 1));

        List<Document> all = collection.find()
                .into(new ArrayList<>());

        for (Document cur : all) {
            Helpers.printJson(cur);
        }


    }
}
