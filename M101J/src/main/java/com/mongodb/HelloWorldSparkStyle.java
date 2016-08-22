package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 02/08/16.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {

        Spark.get("/hello/:thing", (req, res) -> {
            final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setClassForTemplateLoading(HelloWorldSparkStyle.class, "/");
            StringWriter writer = new StringWriter();

            try {
                final Template helloTemplate = configuration.getTemplate("hello.ftl");
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", req.params(":thing"));

                helloTemplate.process(helloMap, writer);

            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }

            return writer;
        });

        Spark.get("/fruitPicker", (req, res) -> {
            final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setClassForTemplateLoading(HelloWorldSparkStyle.class, "/");
            StringWriter writer = new StringWriter();

            try {
                final Template helloTemplate = configuration.getTemplate("fruitPicker.ftl");
                Map<String, Object> helloMap = new HashMap<>();
                helloMap.put("fruits", Arrays.asList("Apple", "Orange", "Banana", "Peach"));

                helloTemplate.process(helloMap, writer);

            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }

            return writer;
        });

        Spark.post("/favoriteFruit", ((request, response) -> {
            final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setClassForTemplateLoading(HelloWorldSparkStyle.class, "/");
            StringWriter writer = new StringWriter();

            try {
                final Template helloTemplate = configuration.getTemplate("favFruit.ftl");
                final Map<String, Object> helloMap = new HashMap<>();
                final String fruit = request.queryParams("fruit");
                String message;
                if(fruit == null) {
                    message = "Why don't you pick a fruit?";
                } else {
                    message = "Your favorite fruit is " + fruit;
                }
                helloMap.put("favFruit", message);

                helloTemplate.process(helloMap, writer);

            } catch (Exception e) {
                halt(500);
                e.printStackTrace();
            }

            return writer;
        }));
    }
}
