package com.mongodb;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
@Entity("organizations")
public class Organization {
    @Id
    public String name;

    @Version("v")
    public long version = 1L;
}
