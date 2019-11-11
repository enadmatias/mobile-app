package com.example.appthesis;

public class Previews {
    String fullname;
    float ratings;
    String comment;
    String date;

    public Previews(String fullname, float ratings, String comment, String date) {
        this.fullname = fullname;
        this.ratings = ratings;
        this.comment = comment;
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
