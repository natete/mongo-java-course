package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/08/16.
 */
public class DeleteTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("DeleteTest");

        collection.drop();

        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                    .append("_id", i));
        }

//        collection.deleteMany(Filters.gt("_id", 4));

        collection.deleteOne(Filters.eq("_id", 4));

        List<Document> all = collection.find()
                .into(new ArrayList<>());

        for (Document cur : all) {
            Helpers.printJson(cur);
        }
    }
}
