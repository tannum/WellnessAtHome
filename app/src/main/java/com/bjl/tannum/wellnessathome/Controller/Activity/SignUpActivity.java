package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bjl.tannum.wellnessathome.Controller.Adapter.ListAdapter;
import com.bjl.tannum.wellnessathome.Controller.Library.AlertDialog;
import com.bjl.tannum.wellnessathome.Controller.Library.HelperFunc;
import com.bjl.tannum.wellnessathome.Model.RegisterInfo;
import com.bjl.tannum.wellnessathome.Model.UserInfo;
import com.bjl.tannum.wellnessathome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener  {



    static final int MY_PERMISSIONS_ACCESS_CAMERA = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_PHOTO_FROM_GALLERY =2;

    private int dialogId = 0;
    private ImageView registerImg;
    private EditText name;
    private EditText username;
    private EditText bio;
    private EditText email;
    private EditText password;
    private EditText tel;
    private Button btnCancel;
    private Button btnDone;
    private Button btnChoosePhoto;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    SweetAlertDialog pDialog;
    SharedPreferences.Editor preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerImg  =(ImageView)findViewById(R.id.register_pic);
        name  = (EditText)findViewById(R.id.editProfileName);
        username = (EditText)findViewById(R.id.editProfileUserName);
        bio = (EditText)findViewById(R.id.editProfileBio);
        email = (EditText)findViewById(R.id.editProfileEmail);
        password = (EditText)findViewById(R.id.editProfilePassword);
        tel = (EditText)findViewById(R.id.editTextTel);
        btnCancel = (Button)findViewById(R.id.btnProfileRegisterCancel);
        btnDone = (Button)findViewById(R.id.btnProfileRegisterDone);
        btnChoosePhoto = (Button)findViewById(R.id.btnChoose_profile_photo);

        //Mask: Initial SharedPreferences
        preferences = getSharedPreferences("USER",MODE_PRIVATE).edit();

        //Mask: add event listener
        btnCancel.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnChoosePhoto.setOnClickListener(this);
        bio.setClickable(true);
        bio.setOnClickListener(this);

        //Mask: request camera permission
        int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_ACCESS_CAMERA);
        }

        //Mask: Initial Firebase auth
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef  = FirebaseStorage.getInstance().getReference();

        //Mask: Initial progress dialog.
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        //Mask: Input demo informatin
        name.setText("user2");
        username.setText("Username_user2");
        bio.setText("Male");
        tel.setText("1111111111");
        email.setText("user2@hotmail.com");
        password.setText("123456");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnChoose_profile_photo){
            dialogId = 1;
            HelperFunc.hideSoftKeyboard(this);
            choosePictureHandler();
        }
        else if(id == R.id.editProfileBio){
            dialogId = 2;
            HelperFunc.hideSoftKeyboard(this);
            choseGenderHandler();
        }
        else if(id == R.id.btnProfileRegisterDone){
            RegisterProfileHandler();

        }
        else if(id == R.id.btnProfileRegisterCancel){
            finish();
        }
    }

    final float progress = 0;
    private void RegisterProfileHandler(){

        //Mask: Check Information
        if(TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(username.getText().toString()) ||
                TextUtils.isEmpty(bio.getText().toString())||
                TextUtils.isEmpty(email.getText().toString())||
                TextUtils.isEmpty(password.getText().toString())||
                TextUtils.isEmpty(tel.getText().toString())){
            Log.d("debug","Register Information Error!!!");
            return;
        }

        //Mask: set waiting dialog.
        pDialog.show();

        //Mask: Register user by email.
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Log.d("debug","Register Auth Success " + task.getResult().toString());

                   //Mask: Update user information to database
                   final FirebaseUser user = task.getResult().getUser();
                   RegisterInfo info = new RegisterInfo(bio.getText().toString(),
                           email.getText().toString(),
                           name.getText().toString(),
                           tel.getText().toString(),
                           username.getText().toString());
                   mDatabaseRef.child("users").child(user.getUid()).setValue(info);

                   //Mask: Update picture
                   Bitmap bitmap = ((BitmapDrawable)registerImg.getDrawable()).getBitmap();
                   ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                   UploadTask uploadTask = mStorageRef.child("user/" + user.getUid()).putBytes(baos.toByteArray());
                   uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("debug","upload profile picture success");
                           pDialog.dismiss();
                           AlertDialog.ShowSuccessDialog(SignUpActivity.this,
                                   "Congratulation","Your registration success",
                                   new AlertDialog.OnDialogConfirmListener() {
                               @Override
                               public void onDialogConfirm() {
                                   //Mask: Save User ID and go to main activity
                                   preferences.putString("user_id",user.getUid());
                                   preferences.commit();

                                   //Mask: finish this activity and start MainActivity
                                   finish();
                                   startActivity(new Intent(SignUpActivity.this,BenefitActivity.class));
                               }
                           });
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.d("debug","upload profile picture error");
                           pDialog.dismiss();
                           AlertDialog.ShowWaringDialog(SignUpActivity.this,
                                   "Information",
                                   "Some registration not complete",
                                   e.getLocalizedMessage(),
                                   new AlertDialog.OnDialogConfirmListener() {
                               @Override
                               public void onDialogConfirm() {
                                   //Mask: Save User ID and go to main activity
                                   preferences.putString("user_id",user.getUid());
                                   preferences.commit();

                                   //Mask: finish this activity and start MainActivity
                                   finish();
                                   startActivity(new Intent(SignUpActivity.this,BenefitActivity.class));
                               }
                           });
                       }
                   }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                           Log.d("debug","progress change .. ");
                           pDialog.getProgressHelper().setProgress(progress + 1);
                       }
                   });
               }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("debug","Register Auth Error1" + e.getLocalizedMessage());
                pDialog.dismiss();
                AlertDialog.ShowErrorDialog(SignUpActivity.this,"Registration Error",e.getLocalizedMessage());
            }
        });
    }

    private boolean checkRegisterProfile(){
        if(TextUtils.isEmpty(name.getText().toString()) ||
                TextUtils.isEmpty(username.getText().toString()) ||
                TextUtils.isEmpty(bio.getText().toString())||
                TextUtils.isEmpty(email.getText().toString())||
                TextUtils.isEmpty(password.getText().toString())||
                TextUtils.isEmpty(tel.getText().toString())){

            return false;
        }
        return true;
    }
    private void choseGenderHandler(){
        //Initial Dialog
        int[] resId = {R.drawable.gender_male1, R.drawable.gender_female1};
        String[] list = { "Male", "Female"};
        ListAdapter adapter = new ListAdapter(this,list,resId);
        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(this)
                .setExpanded(true)
                .setHeader(R.layout.choose_sex_header)
                .create();
        dialogPlus.show();
    }
    private void choosePictureHandler(){

        //Initial Dialog
        int[] resId = {R.drawable.takepic1, R.drawable.gallery1};
        String[] list = { "Take Picture", "From Gallery"};
        ListAdapter adapter = new ListAdapter(this,list,resId);
        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(this)
                .setExpanded(true)
                .setHeader(R.layout.choose_picture_header)
                .create();
        dialogPlus.show();
    }

    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
        Log.d("debug","position : " + String.valueOf(position));
        if(dialogId == 1) {
            if (position == 0) {
                //Mask: Take photo from camera
                dispatchTakePictureIntent();
                dialog.dismiss();

            } else if (position == 1) {
                //Mask: choose photo from gallery
                dispatchChoosePictureFromGalleryIntent();
                dialog.dismiss();
            }
        }
        else if(dialogId == 2){
            if (position == 0) {
                //Mask: Take photo from camera
                bio.setText("Male");
                dialog.dismiss();

            } else if (position == 1) {
                //Mask: choose photo from gallery
                bio.setText("Female");
                dialog.dismiss();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void dispatchChoosePictureFromGalleryIntent(){

        //Mask: non-filter intent
//        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        choosePictureIntent.setType("image/*");
//        startActivityForResult(choosePictureIntent,PICK_PHOTO_FROM_GALLERY);

        //Mask: filter intent
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_PHOTO_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            registerImg.setImageBitmap(imageBitmap);
        }
        else if(requestCode == PICK_PHOTO_FROM_GALLERY && resultCode == RESULT_OK && data != null){

            //Mask: non-fileter intent
//            Uri selectedImage = data.getData();
//            Bitmap bmp = null;
//            try {
//                bmp = getBitmapFromUri(selectedImage);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            registerImg.setImageBitmap(bmp);

            //Mask: filter intent
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                registerImg.setImageBitmap(newProfilePic);
            }

        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }


}

