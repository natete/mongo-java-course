package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Spark;

import java.io.StringWriter;

import static spark.Spark.halt;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 16/08/16.
 */
public class HelloWorldMongoDBSparkFremarkerStyle {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFremarkerStyle.class, "/");

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("course");
        final MongoCollection<Document> collection = database.getCollection("hello");

        collection.drop();

        collection.insertOne(new Document("name", "Natete"));

        Spark.get("/", (req, res) -> {
            StringWriter writer =new StringWriter();

            try {
                final Template helloTemplate = configuration.getTemplate("hello.ftl");

                Document document = collection.find().first();

                helloTemplate.process(document, writer);
            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }

            return writer;
        });
    }
}
