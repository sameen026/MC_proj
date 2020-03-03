package com.example.myapplication.Model;

public class User {
    String userId;
    String email;
    String name;
    String type;
    String imageURL;

    public User(){}
    public User(String id, String email, String name, String type, String imageURL) {
        this.userId = id;
        this.email = email;
        this.name = name;
        this.type = type;
        this.imageURL = imageURL;
    }

    public User(User user){
        this.userId = user.userId;
        this.email = user.email;
        this.name = user.name;
        this.type = user.type;
        this.imageURL = user.imageURL;
    }
    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }
    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
