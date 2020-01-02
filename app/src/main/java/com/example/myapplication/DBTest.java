package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBTest extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    DatabaseReference myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        btn =findViewById(R.id.click);
        btn.setOnClickListener(this);
        myDB= FirebaseDatabase.getInstance().getReference("user");
    }

    @Override
    public void onClick(View view) {
        String id=myDB.push().getKey();
        User usr=new User(id,"abc@gmail.com","sameen javed","abc","membership");
        //jkkjhk
        myDB.child(id).setValue(usr);
    }
}
