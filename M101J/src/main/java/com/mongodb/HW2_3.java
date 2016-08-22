package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 16/08/16.
 */
public class HW2_3 {
    // Write a program in the language of your choice that will remove the grade of type "homework"
    // with the lowest score for each student from the dataset in the handout. Since each document is one grade,
    // it should remove one document per student. This will use the same data set as the last problem,
    // but if you don't have it, you can download and re-import.

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> collection = db.getCollection("grades");

        Bson sort = Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score"));

        List<Document> all = collection.find(Filters.eq("type", "homework"))
                .sort(sort)
                .into(new ArrayList<>());

        Document lastDeleted = null;
        List<Document> filtered = new ArrayList<>();

        for (Document cur : all) {
            if (lastDeleted == null ||
                    (!cur.getInteger("student_id").equals(lastDeleted.getInteger("student_id")))) {
                lastDeleted = cur;
                filtered.add(cur);
                Helpers.printJson(cur);
                collection.deleteOne(cur);

            }
        }
        System.out.println(filtered.size());

    }

}
