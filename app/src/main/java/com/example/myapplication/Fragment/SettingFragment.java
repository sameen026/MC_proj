package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.AboutUsActivity;
import com.example.myapplication.ui.PrivacyPolicyActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.ResetPasswordActivity;

public class SettingFragment extends Fragment implements View.OnClickListener {
    @Nullable
    TextView privacy_tv;
    TextView about_tv;
    TextView changePassword_tv;
    TextView backBtn;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_setting, container, false);
        privacy_tv=v.findViewById(R.id.privacy_tv);
        about_tv=v.findViewById(R.id.about_us__tv);
        changePassword_tv=v.findViewById(R.id.change_pass_tv);

        privacy_tv.setOnClickListener(this);
        about_tv.setOnClickListener(this);
        changePassword_tv.setOnClickListener(this);
        backBtn=v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.privacy_tv)
        {
            Intent i=new Intent(getActivity(), PrivacyPolicyActivity.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.about_us__tv)
        {
            Intent i=new Intent(getActivity(), AboutUsActivity.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.change_pass_tv)
        {
            Intent i=new Intent(getActivity(), ResetPasswordActivity.class);
            startActivity(i);
        }
        else if (view.getId() == R.id.back_btn) {
            getActivity().onBackPressed();
        }
    }
}
