package com.example.myapplication.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.AboutUsActivity;
import com.example.myapplication.ui.EnterEmail;
import com.example.myapplication.ui.MainScreenActivity;
import com.example.myapplication.ui.PrivacyPolicyActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.ResetPasswordActivity;
import com.example.myapplication.ui.SigninActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

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
        else if (view.getId() == R.id.back_btn) {
            getActivity().onBackPressed();
        } else if(view.getId() == R.id.change_pass_tv){
//            Toast.makeText(getContext(), "Click Listener working fine", Toast.LENGTH_SHORT).show();
            AlertDialog at = AskOption();
            at.show();
        }
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Change Password")
                .setMessage("A password change link will be sent to your email("+ FirebaseAuth.getInstance().getCurrentUser().getEmail()+")")
                .setIcon(R.drawable.ic_error_black_24dp)

                .setPositiveButton("Send", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Email sending code
                        sendEmail();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    private void sendEmail(){
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Email Sent", Toast.LENGTH_LONG).show();
                            //Remove loading bar

                        }else if(task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                            Toast.makeText(getContext(), "Email not registered.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else if(task.getException().getMessage().equals("The email address is badly formatted.")){
                            Toast.makeText(getContext(), "Email is badly formatted.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else if(task.getException().getMessage().equals("A network error (such as timeout, interrupted connection or unreachable host) has occurred.")){
                            Toast.makeText(getContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
                            //Remove Loading bar
                        }else{
                            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                            Log.d("Umar4",task.getException().getMessage());
                            //Remove Loading bar
                        }
                    }
                });
    }
}
