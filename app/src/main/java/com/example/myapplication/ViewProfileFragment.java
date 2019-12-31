package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewProfileFragment extends Fragment implements View.OnClickListener {
    Button img1;
    Button img2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_view_profile,container,false);
        img1=v.findViewById(R.id.img1);
        img2=v.findViewById(R.id.img2);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.img1)
        {
            Intent i=new Intent(getActivity(), PopupNameActivity.class);
            startActivity(i);
        }
        else
        {
            Intent i=new Intent(getActivity(), PopupEmailActivity.class);
            startActivity(i);
        }
    }
}
