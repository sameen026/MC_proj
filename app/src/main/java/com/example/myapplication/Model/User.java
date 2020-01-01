package com.example.myapplication.Model;

public class User {
    String userId;
    String email;
    String name;
    String password;
    String type;

    public User(String id, String email, String name, String password, String type) {
        this.userId = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.type = type;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
