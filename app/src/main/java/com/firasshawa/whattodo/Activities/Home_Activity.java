package com.firasshawa.whattodo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firasshawa.whattodo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roacult.backdrop.BackdropLayout;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Home_Activity extends AppCompatActivity {

    private FirebaseAuth auth;

    BackdropLayout backdropLayout;
    View backview , frontview;
    TextView ProfileEmail_tv,ProfileName_tv;
    ImageView ProfileImage_im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        auth = FirebaseAuth.getInstance();

        backdropLayout = findViewById(R.id.container);
        backview = backdropLayout.getChildAt(0);
        frontview = backdropLayout.getChildAt(1);

        ProfileEmail_tv= backview.findViewById(R.id.ProfileEmail_tv);
        ProfileName_tv= backview.findViewById(R.id.ProfileName_tv);
        ProfileImage_im= backview.findViewById(R.id.ProfileImage_im);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        ProfileName_tv.setText(user.getDisplayName());
        ProfileEmail_tv.setText(user.getEmail());

        Glide.with(this).load(user.getPhotoUrl()).into(ProfileImage_im);

    }
}
