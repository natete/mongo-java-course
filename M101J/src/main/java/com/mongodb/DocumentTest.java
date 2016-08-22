package com.mongodb;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.Helpers.printJson;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 10/08/16.
 */
public class DocumentTest {
    public static void main(String[] args) {
        Document document = new Document()
                .append("string", "MongoDB, Hello")
                .append("int", 42)
                .append("l", 1L)
                .append("double", 1.1)
                .append("bool", false)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1, 2, 3));

        printJson(document);
    }

}
