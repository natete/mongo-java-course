package com.mongodb;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
@Entity("students")
public class Student {
    @Id
    public int id;
    public String name;
    @Embedded
    public List<Score> scores = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
