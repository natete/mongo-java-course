package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.Helpers.printJson;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/08/16.
 */
public class SortSkipLimitTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("SortSkipLimitTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());
//        Bson sort = new Document("i", 1).append("j", -1);

        Bson sort = orderBy(ascending("i"), descending("j"));

        List<Document> all = collection.find()
                .projection(projection)
                .sort(sort)
                .skip(20)
                .limit(50)
                .into(new ArrayList<>());

        for (Document cur : all) {
            printJson(cur);
        }

//
    }
}
