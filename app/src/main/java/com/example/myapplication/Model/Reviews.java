package com.example.myapplication.Model;

public class Reviews {
    public Reviews(String reviewId, String plazaId, String userId, String feedback, String ratings) {
        this.reviewId = reviewId;
        this.plazaId = plazaId;
        this.userId = userId;
        this.feedback = feedback;
        this.ratings = ratings;
    }

    String reviewId;
    String plazaId;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    String userId;
    String feedback;
    String ratings;
}
