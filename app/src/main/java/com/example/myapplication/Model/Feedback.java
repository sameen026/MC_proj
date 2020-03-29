package com.example.myapplication.Model;

public class Feedback {
    String userId, plazaId, ratings, review, userName, imageURL;


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

    public String getReview() { return review; }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserName(){ return this.userName;}

    public void setUserName(String userName){ this.userName = userName;}

    public String getImageURL(){return this.imageURL;}

    public void setImageURL(String imageURL){this.imageURL = imageURL;}

    public Feedback(String userId, String plazaId, String ratings, String review, String userName, String imageURL) {
        this.userId = userId;
        this.plazaId = plazaId;
        this.ratings = ratings;
        this.review = review;
        this.imageURL = imageURL;
        this.userName = userName;
    }
    public Feedback(){}


}
