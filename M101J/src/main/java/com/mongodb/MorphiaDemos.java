package com.mongodb;


import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
public class MorphiaDemos extends BaseTest {
    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private GithubUser evanchooly;
    private Date date;

    public MorphiaDemos() {
        try {
            date = sdf.parse("6-15-1987");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void basicUser() {
        evanchooly = new GithubUser("evanchooly");
        evanchooly.fullName = "Justin Lee";
        evanchooly.memberSince = date;
        evanchooly.following = 1000;

        ds.save(evanchooly);
    }

    @Test(dependsOnMethods = {"repositories"})
    public void query() {
        Query<Repository> query = ds.createQuery(Repository.class);

        Repository repository = query.get();

        List<Repository> repositories = query.asList();

        Iterable<Repository> fetch = query.fetch();

        ((MorphiaIterator) fetch).close();

        Iterator<Repository> iterator = fetch.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }

        iterator = fetch.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        query.field("owner").equal(evanchooly).get();

        GithubUser memberSince = ds.createQuery(GithubUser.class).field("since").equal(date).get();
        System.out.println("memberSince = " + memberSince);

        GithubUser since = ds.createQuery(GithubUser.class).field("memberSince").equal(date).get();

        System.out.println("since = " + since);
    }

    @Test(dependsOnMethods = {"repositories"})
    public void updates() {
        evanchooly.followers = 12;
        evanchooly.following = 678;
        ds.save(evanchooly);
    }

    @Test(dependsOnMethods = {"repositories"})
    public void massUpdates() {
        UpdateOperations<GithubUser> update = ds.createUpdateOperations(GithubUser.class)
                .inc("followers")
                .set("following", 42);

        Query<GithubUser> query = ds.createQuery(GithubUser.class)
                .field("followers").equal(0);
        ds.update(query, update);
    }

    @Test(dependsOnMethods = {"repositories"},
    expectedExceptions = {ConcurrentModificationException.class})
    public void versioned() {
        Organization orgnization1 = ds.createQuery(Organization.class).get();
        Organization orgnization2 = ds.createQuery(Organization.class).get();

        Assert.assertEquals(orgnization1.version, 1L);
        ds.save(orgnization1);

        Assert.assertEquals(orgnization1.version, 2L);
        ds.save(orgnization1);

        Assert.assertEquals(orgnization1.version, 3L);
        ds.save(orgnization2);
    }
}
