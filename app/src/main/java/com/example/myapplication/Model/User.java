package com.example.myapplication.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User {
    String userId;
    String email;
    String name;
    String password;
    String type;



    public User() {

    }

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

//    public boolean findUserByID(String email){
//        return false;
//    }

    public boolean login(String email, String password){
        DatabaseReference myDB;
        myDB = FirebaseDatabase.getInstance().getReference("user");
        Query query = myDB.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Email found

                    //Do something to verify password
//                    Query query = myDB.orderByChild("email").equalTo(email);
                }else{
                    //Email not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return false;
    }
}
