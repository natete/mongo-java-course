package com.mongodb;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;

import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
public class HW3_1 {
    // Write a program in the language of your choice that will remove the lowest homework score for each student.
    // Since there is a single document for each student containing an array of scores, you will need to update
    // the scores array and remove the homework.
    public static void main(String[] args) {
        final Morphia morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("org.mongodb");

        // create the Datastore connecting to the default port on the local host
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "school");

        Query<Student> query = datastore.createQuery(Student.class);

        Iterable<Student> fetch = query.fetch();

        Iterator<Student> iterator = fetch.iterator();
        while(iterator.hasNext()) {
            Student student = iterator.next();

            student.scores.sort((o1, o2) -> (int) (o1.score - o2.score));

            Score lowestHomework = student.scores.stream()
                    .filter(score -> score.type.equals("homework"))
                    .sorted((o1, o2) -> (int) (o1.score - o2.score))
                    .collect(Collectors.toList())
                    .get(0);

            student.scores.remove(lowestHomework);

            System.out.println(lowestHomework);

            datastore.save(student);
        }

        ((MorphiaIterator) fetch).close();

        System.out.println();
    }
}
