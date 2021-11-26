package com.example.forfoodiesbyfoodies.StreetFood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddStreetFoodActivity extends AppCompatActivity {

//    Declare impostors
    private ImageButton mPostImage;
    private CheckBox veggy;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitButton,location;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;
    private Uri mImageUri;
    private String checkVeg;
    private static final int GALLERY_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_street_food);

//        Bind impostors

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference("STREET_PICS");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("StreetFood");

        mPostImage = findViewById(R.id.street_imageButton);
        mPostTitle = findViewById(R.id.street_postTitleEt);
        mPostDesc = findViewById(R.id.street_descriptionEt);
        mSubmitButton = findViewById(R.id.street_submitPost);
        location = findViewById(R.id.str_add_loc);
        veggy = findViewById(R.id.checkBox);


//        Set on check change listener - Checkbox button - get string
        veggy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    checkVeg = "Vegetarian";

                }else{
                    checkVeg = "Non-Vegetarian";
                }
            }
        });

//        auto fill Database with Non-vegetarian if the checkbox is unchecked
        if(!veggy.isChecked()){
            checkVeg = "Non-Vegetarian";
        }


//        set on click Maps activity
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStreetFoodActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

//        Set on click - get Content images-
        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

//        Calling method startPosting
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Posting to our database
                startPosting();
            }
        });

    }

//    Check requestcode - add data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mPostImage.setImageURI(mImageUri);
            Picasso.get().load(mImageUri).fit().into(mPostImage);


        }
    }

//    Start Posting new Street food Stall
    private void startPosting() {

        mProgress.setMessage("Hmm... Adding fresh food");
        mProgress.show();

        final String titleVal = mPostTitle.getText().toString().trim();
        final String descVal = mPostDesc.getText().toString().trim();



        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)
                && mImageUri != null) {
            //start the uploading...

            String name = mDatabase.push().getKey();
            StorageReference reference = mStorage.child(name+"."+getExt(mImageUri));
            reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();

                            DatabaseReference newPost = mDatabase.push();


                            Map<String, String> dataToSave = new HashMap<>();
                            dataToSave.put("name", titleVal);
                            dataToSave.put("description", descVal);
                            dataToSave.put("imageURL", downloadUrl);
                            dataToSave.put("checkbox", checkVeg);



                            newPost.setValue(dataToSave);




                            mProgress.dismiss();

                            startActivity(new Intent(AddStreetFoodActivity.this, StreetRecyclerActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            reference.delete();
                        }
                    });
                }
            });

        } else{
            Toast.makeText(AddStreetFoodActivity.this, "Please try to complete all fields.", Toast.LENGTH_SHORT).show();
            mProgress.dismiss();
            return;
        }

    }

//    Content extension resolver
    private String getExt( Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }
}