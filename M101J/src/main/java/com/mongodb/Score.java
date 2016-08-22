package com.mongodb;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 22/08/16.
 */
//@Entity("scores")
public class Score {
    public String type;
    public Double score;

    @Override
    public String toString() {
        return type + ": " + score;
    }
}
