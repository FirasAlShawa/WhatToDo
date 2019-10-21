package com.firasshawa.whattodo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firasshawa.whattodo.Models.UserAuth;
import com.firasshawa.whattodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class ProfileSetup_Activity extends AppCompatActivity {

    private static final int REQURESTCODE = 0712 ;

    //Widgets Vars
    Button Save_btn;
    EditText ProfileName_et;
    ImageView ProfileImage_im;

    //Firebase Vars
    FirebaseAuth auth;
    StorageReference storageReference;
    FirebaseUser firebaseUser;

    //Image URI
    Uri ProfileImagmeURI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup_layout);

        //Firebase SetUp
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("UsersProfileImages");

        //Widgets SetUp
        Save_btn = findViewById(R.id.Save_btn);
        ProfileImage_im = findViewById(R.id.ProfileImage_im);
        ProfileName_et = findViewById(R.id.ProfileName_et);


        //OnclickListenders

            //Save Btn
                Save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadFile();
                    }
            });

            //Pick Up Image
                ProfileImage_im.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View view) { PickImage();
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();

        //get the current signed in user
            firebaseUser = auth.getCurrentUser();
    }


    //function that help to collect the data from every Edittext
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
        //Android Code to open the gallary and choose image
            //Intent gallaryIntent = new Intent();
            //gallaryIntent.setType("image/*");
            //gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
            //startActivityForResult(Intent.createChooser(gallaryIntent,"Select your profile picture"),REQURESTCODE);

        //Crop Library ( it will open the gallary and start a crop activity )
        //and it will return a unqie request code to catch the result in the onActivityResult
        CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

    }

    //Function to get the file Extension
    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile(){
        //if the profileimageURI is null thats means the user didn't pick an image
        if(ProfileImagmeURI != null){

            //Storage Reference to the user profile image
            // it will be child of the Main StorageReference File
            // the name of picture will be the current time in millseconds and the extesion file
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(ProfileImagmeURI));

            //At the profileimage referance we call the function .putFile(URI)
            //we can check the task states by attaching a onSuccessListener OR onCompleteListener
            reference.putFile(ProfileImagmeURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //we can getDownloadUrl from the reference object
                            //it will a task so will can trace it by listening to
                            //onSuccessListener OR OnCompleteListener
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //get the Data from all the EditTexts
                                    UserAuth user = Collect();

                                    //Each user in firebase have a profile that containes a DisplayName and PhotoUri
                                    //to edit and update that you need to make request by UserProfileChangeRequest
                                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(user.getName())
                                            .setPhotoUri(uri)
                                            .build();

                                    //Calling the .UpdateProfile(UserProfileChangeRequest object)
                                    //it will return  a task and you can trace it by listening to
                                    //onSuccessListener OR onCompleteListener
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

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileSetup_Activity.this, "Failuer!", Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(this, "No file Selected !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQURESTCODE && resultCode == RESULT_OK){
//            if(data != null && data.getData() != null){
//
//                ProfileImagmeURI = data.getData();
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(1,1)
//                        .start(this);
//
//                CropImage.
//                        activity(ProfileImagmeURI)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAspectRatio(1,1)
//                        .start(this);


                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        ProfileImagmeURI = resultUri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                            ProfileImage_im.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }




//                System.out.println(ProfileImagmeURI);
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ProfileImagmeURI);
//                    ProfileImage_im.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
