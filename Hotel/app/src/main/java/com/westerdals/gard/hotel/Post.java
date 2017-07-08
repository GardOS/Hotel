package com.westerdals.gard.hotel;

/**
 * Created by Gard on 16.05.2017.
 */

public class Post {
    private int id;
    private int score;
    private String text;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}