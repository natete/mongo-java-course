package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/08/16.
 */
public class FindWithFilterTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("i", i));
        }

//        Bson filter = new Document("x", 0)
//                .append("y", new Document("$gt", 10).append("$lt", 90));

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

//        Bson projection = new Document("y", 0)
//                .append("_id", 0);

//        Bson projection = Projections.include("y", "i");

        Bson projection = fields(include("y", "i"),
                excludeId());

        List<Document> all = collection.find(filter)
                .projection(projection)
                .into(new ArrayList<>());

        for (Document cur : all) {
            Helpers.printJson(cur);
        }

        System.out.println(collection.count(filter));

    }
}
