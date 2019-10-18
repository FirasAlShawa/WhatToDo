package com.firasshawa.whattodo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firasshawa.whattodo.R;
import com.firasshawa.whattodo.Models.UserAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUp_Activity extends AppCompatActivity {

    private FirebaseAuth auth;

    //declaration
    TextView RegisterNow_tv ;
    EditText Email_et,Password_et;
    Button Signup_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        auth = FirebaseAuth.getInstance();

        //initialization
        RegisterNow_tv = findViewById(R.id.RegisterNow_tv);
        Email_et = findViewById(R.id.Email_et);
        Password_et = findViewById(R.id.Password_et);
        Signup_btn =findViewById(R.id.Signup_btn);

        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup();
            }
        });

    }

    public UserAuth Collect(){
        UserAuth user = new UserAuth();

        if(!Email_et.getText().toString().isEmpty()){
            if(Patterns.EMAIL_ADDRESS.matcher(Email_et.getText().toString().trim()).matches()){
                user.setEmail(Email_et.getText().toString().trim());
            }else{
                Email_et.setError("Wrong Email format");
                Email_et.requestFocus();
                return null;
            }
        }else{
            Email_et.setError("Fill The Email Please");
            Email_et.requestFocus();
            return null;
        }

        if(!Password_et.getText().toString().isEmpty()){
            if(Password_et.length() > 6){
                user.setPassword(Password_et.getText().toString().trim());
            }else{
                Password_et.setError("The Password More than 6 char");
                Password_et.requestFocus();
                return null;
            }
        }else{
            Password_et.setError("Fill The Password Please");
            Password_et.requestFocus();
            return null;
        }


        return user;
    }

    public void Signup(){
        UserAuth user = Collect();

        if(user != null)
            auth.
                createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Completed!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp_Activity.this,ProfileSetup_Activity.class));

                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

}
