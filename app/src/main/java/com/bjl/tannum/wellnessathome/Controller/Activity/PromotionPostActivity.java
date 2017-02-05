package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bjl.tannum.wellnessathome.Model.PromotionInfo;
import com.bjl.tannum.wellnessathome.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PromotionPostActivity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitPost;
    private Uri imageUri;
    private StorageReference mStoreage;
    private DatabaseReference mDatabase;
    private static final int GALLERY_REQUEST = 1;
    SweetAlertDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_post);


        //Mask: Initial firebase storeage.
        mStoreage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotion_blog");

        //Mask: Initial component.
        mSelectImage = (ImageButton)findViewById(R.id.postImage);
        mPostTitle = (EditText)findViewById(R.id.btnPostTitle);
        mPostDesc = (EditText)findViewById(R.id.btnPostContent);
        mSubmitPost = (Button)findViewById(R.id.btnSubmitPost);


        //Mask: Initial progress dialog.
        mProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgress.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgress.setTitleText("Loading");
        mProgress.setCancelable(false);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });
        mSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        final String title = mPostTitle.getText().toString();
        final String desc = mPostDesc.getText().toString();
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && imageUri != null){
            mProgress.show();
            final StorageReference filepath = mStoreage.child("promotion_thumbnail").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    //Mask: create random id and post it to firebase database
                    DatabaseReference newPost = mDatabase.push();
                    newPost.setValue(new PromotionInfo(title,desc,downloadUri.toString()));
                    mProgress.dismiss();
                    finish();

                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            mSelectImage.setImageURI(imageUri);
        }
    }
}
