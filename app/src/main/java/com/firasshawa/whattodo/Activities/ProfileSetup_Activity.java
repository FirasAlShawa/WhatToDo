package com.firasshawa.whattodo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firasshawa.whattodo.Models.UserAuth;
import com.firasshawa.whattodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class ProfileSetup_Activity extends AppCompatActivity {

    private static final int REQURESTCODE = 0712 ;
    Button Save_btn;
    EditText ProfileName_et;
    ImageView ProfileImage_im;


    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    Uri ProfileImagmeURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_layout);

        auth = FirebaseAuth.getInstance();
        Save_btn = findViewById(R.id.Save_btn);
        ProfileImage_im = findViewById(R.id.ProfileImage_im);
        ProfileName_et = findViewById(R.id.ProfileName_et);

        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAuth user = Collect();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getName())
                            .setPhotoUri(ProfileImagmeURI)
                            .build();
                    firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(ProfileSetup_Activity.this,Home_Activity.class));
                                finish();
                            }
                        }
                    });
                }
        });

        ProfileImage_im.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                PickImage();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = auth.getCurrentUser();
    }

    public UserAuth Collect(){
        UserAuth user = new UserAuth();

        if(!ProfileName_et.getText().toString().isEmpty()){
            user.setName(ProfileName_et.getText().toString().trim());
        }else{
            ProfileName_et.setError("Fill The Name Please");
            ProfileName_et.requestFocus();
            return null;
        }

        return user;
    }

    public void PickImage(){
        Intent gallaryIntent = new Intent();
        gallaryIntent.setType("image/*");
        gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallaryIntent,"Select your profile picture"),REQURESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQURESTCODE && resultCode == RESULT_OK){
            if(data != null && data.getData() != null){
                ProfileImagmeURI = data.getData();
                System.out.println(ProfileImagmeURI);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ProfileImagmeURI);
                    ProfileImage_im.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
