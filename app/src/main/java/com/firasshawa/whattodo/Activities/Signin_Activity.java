package com.firasshawa.whattodo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firasshawa.whattodo.R;

public class Signin_Activity extends AppCompatActivity {

    //declaration
    TextView RegisterNow_tv ;
    EditText Email_et,Password_et;
    Button Signin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        //initialization
        RegisterNow_tv = findViewById(R.id.RegisterNow_tv);
        Email_et = findViewById(R.id.Email_et);
        Password_et = findViewById(R.id.Password_et);
        Signin_btn =findViewById(R.id.Signin_btn);

        //onclicklisteners
        RegisterNow_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signin_Activity.this,SignUp_Activity.class));
            }
        });

    }

}
