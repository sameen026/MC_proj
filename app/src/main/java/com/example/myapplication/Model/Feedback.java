package com.example.myapplication.Model;

public class Feedback {
    String userId;
    String plazaId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Feedback(String userId, String plazaId, String ratings, String review) {
        this.userId = userId;
        this.plazaId = plazaId;
        this.ratings = ratings;
        this.review = review;
    }

    String ratings;
    String review;
}
