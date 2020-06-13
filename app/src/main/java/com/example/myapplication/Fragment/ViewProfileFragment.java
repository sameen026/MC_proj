package com.example.myapplication.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ViewProfileFragment extends Fragment implements View.OnClickListener {
    TextView name, email;
    FirebaseUser currentUser;
    CircleImageView profileImageView;
    Uri imageURI;
    ProgressBar mProgressBar;
    public Button backBtn;

    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_view_profile,container,false);
       name = v.findViewById(R.id.tv2_view_profile_rl);
       email = v.findViewById(R.id.tv4_view_profile_rl);
       profileImageView = v.findViewById(R.id.profile_image);
       mProgressBar = v.findViewById(R.id.progressBar);
       backBtn = v.findViewById(R.id.back_btn);
       backBtn.setOnClickListener(this);

       profileImageView.setOnClickListener(this);
       currentUser = FirebaseAuth.getInstance().getCurrentUser();
       mStorageRef = FirebaseStorage.getInstance().getReference("userImages");
       mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user");
       if(currentUser == null){
           email.setText("Please Log in to View");
       }else{
           email.setText(currentUser.getEmail());
           name.setText(currentUser.getDisplayName());
           if(currentUser.getPhotoUrl()!= null){
               Glide.with(this).load(currentUser.getPhotoUrl().toString()).into(profileImageView);
           }
//           Toast.makeText(getActivity(), currentUser.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
//           Log.i("Umar",currentUser.getPhotoUrl().toString());
       }


        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_image){
            openFileChooser();
        }else if (v.getId() == R.id.back_btn) {
            getActivity().onBackPressed();
        }
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    private void uploadImage(){
        if(imageURI != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageURI));
            fileReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getActivity(), "Upload Successfull", Toast.LENGTH_SHORT).show();
//                            progressBar.setProgress(0);
                            Query query = FirebaseDatabase.getInstance().getReference("user").
                                    orderByChild("email").equalTo(currentUser.getEmail());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        User usr = new User();
                                        for(DataSnapshot data: dataSnapshot.getChildren()){
                                            usr = data.getValue(User.class);
                                        }
                                        final User ussr = new User(usr);
                                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                ussr.setImageURL(uri.toString());
//                                        Toast.makeText(getActivity(), usr.getId(), Toast.LENGTH_SHORT).show();
                                                FirebaseDatabase.getInstance().getReference("user").child(ussr.getId()).setValue(ussr);
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setPhotoUri(uri)
                                                        .build();
                                                currentUser.updateProfile(profileUpdates);
                                                mProgressBar.setVisibility(View.GONE);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Writing to database failed", Toast.LENGTH_SHORT).show();
                                                mProgressBar.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getActivity(), "Query Execution Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            Log.i("Umar: ViewProfileFragment-UploadFfailed",e.getMessage());
                        }
                    }).
                    addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int)progress);

                        }
                    });
        }else{
            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private  String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageURI = data.getData();
//            Glide.with(this).load(currentUser.getPhotoUrl().toString()).into(profileImageView);
            Glide.with(this).load(imageURI).into(profileImageView);
            //
            mProgressBar.setVisibility(View.VISIBLE);
            uploadImage();
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
}
