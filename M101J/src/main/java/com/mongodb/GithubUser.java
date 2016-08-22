package com.mongodb;

import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
@Entity(value = "users", noClassnameStored = true)
@Indexes({
        @Index(value = "userName, -followers", name = "popular"),
        @Index(value = "lastActive", name = "idle", expireAfterSeconds = 1000000000)
})
public class GithubUser {
    @Id
    public String userName;
    public String fullName;
    @Property("since")
    public Date memberSince;
    public Date lastActive;
    @Reference(lazy = true)
    public List<Repository> repositories = new ArrayList<>();
    public int followers = 0;
    public int following = 0;

    public GithubUser() {

    }

    public GithubUser(final String userName) {
        this.userName = userName;
    }
}
