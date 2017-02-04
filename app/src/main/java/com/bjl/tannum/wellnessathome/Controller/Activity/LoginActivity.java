package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.Controller.Database.DatabaseHelper;
import com.bjl.tannum.wellnessathome.Controller.Library.AlertDialog;
import com.bjl.tannum.wellnessathome.Model.UserInfo;
import com.bjl.tannum.wellnessathome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnForgot;
    Button btnLogin;
    Button btnSignup;
    EditText editTextUserName;
    EditText editTextPassword;
    DatabaseHelper databaseHelper;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    //private SharedPreferences sharedPreferences;
    SharedPreferences.Editor preferences;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("debug","start login activity");

        //Mask: Init Component
        btnForgot = (Button) findViewById(R.id.btnForgotPwd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignup = (Button)findViewById(R.id.btnSignUp);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        btnForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);

        //Mask: Initial database
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.InitDB();

        //Mask: Initial SharedPreferences
        preferences = getSharedPreferences("USER",MODE_PRIVATE).edit();


        //Mask: Initial Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Mask: User already signed in -> save User ID
                    preferences.putString("user_id",user.getUid());
                    preferences.commit();

                    //Mask: Start Main Activity and finish this activity
                    startActivity(new Intent(LoginActivity.this,BenefitActivity.class));
                    finish();
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnForgot){
            Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
            startActivity(intent);
        }
        else if(view == btnLogin){
             LoginHandler();
        }
        else if(view == btnSignup){
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private void LoginHandler(){

        String email = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(email) || (TextUtils.isEmpty(password))){
            AlertDialog.ShowErrorDialog(LoginActivity.this,"Login Error","Fields are empty");
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Mask: Signed in complete and save USER ID
                    FirebaseUser user = task.getResult().getUser();
                    preferences.putString("user_id",user.getUid());
                    preferences.commit();
                    Toast.makeText(LoginActivity.this,"Sign In Complete : UID : " + user.getUid(),Toast.LENGTH_LONG).show();

                    //Mask: Start Main activity and finish this activity
                    startActivity(new Intent(LoginActivity.this,BenefitActivity.class));
                    finish();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.ShowErrorDialog(LoginActivity.this,"Login Error",e.getLocalizedMessage());
            }
        });
    }

    //    private boolean LoginHandler(){
//
//        boolean result;
//        Random r = new Random();
//        int UserId = r.nextInt();
//
//        String userName;
//        String password;
//
//        Log.d("debug","LoginHandler");
//        userName = editTextUserName.getText().toString();
//        password = editTextPassword.getText().toString();
//        result = false;
//        //Mask: check user exist.
//        Cursor res = databaseHelper.SqlSelect("select * from user_login where user_name = "+"'"+userName+"'");
//        if(res.getCount() == 0){
//            Log.d("debug","No user in database : verify user with server");
//            //Mask: no user -> verify user with server
//            result = true;
//
//            //Mask: Save user to tb_login_info
//            boolean result1 = databaseHelper.InsertData_tbLoginInfo(UserId++,userName,1);
//            Log.d("debug","insert data = " + String.valueOf(result1));
//
//        }
//        else {
//            result = true;
//            Log.d("debug", "Have user in database : update status");
//            //have user
//            if (res.moveToNext() == true) {
//
//                //Mask: Get data from database
//                int user_active = res.getInt(2);
//                UserId = res.getInt(0);
//                //Mask: update data if user status = 0
//                Log.d("debug", "user active = " + user_active);
//                if (user_active != 1) {
//                    Log.d("debug", "user not active -> update user status");
//                    //Mask: Udate user status = 1
//                    boolean result1 = databaseHelper.UpdateData_tbLoginInfo(UserId, userName, 1);
//                    Log.d("debug", "update data = " + String.valueOf(result1));
//                }
//            }
//        }
//        return result;
//
//    }
}
