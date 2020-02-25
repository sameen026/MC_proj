package com.example.myapplication.Fragment;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.User;
import com.example.myapplication.ui.PopupEmailActivity;
import com.example.myapplication.ui.PopupNameActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewProfileFragment extends Fragment implements View.OnClickListener {
    TextView name, email;
    String nameString, emailString;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_view_profile,container,false);
       name = v.findViewById(R.id.tv2_view_profile_rl);
       email = v.findViewById(R.id.tv4_view_profile_rl);
        name.setText("Loading..");
        email.setText("Loading..");


       Query query = FirebaseDatabase.getInstance().getReference().child("user")
               .orderByChild("email")
               .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   for(DataSnapshot data: dataSnapshot.getChildren()){
                       User user = data.getValue(User.class);
                       name.setText(user.getName());
                       email.setText(user.getEmail());
                   }
               }else{

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
