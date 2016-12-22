package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjl.tannum.wellnessathome.Controller.Database.DatabaseHelper;
import com.bjl.tannum.wellnessathome.R;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnForgot;
    Button btnLogin;
    Button btnSignup;
    EditText editTextUserName;
    EditText editTextPassword;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    @Override
    public void onClick(View view) {
        if(view == btnForgot){
            Log.d("debug","btn forgot was clicked");
            Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
            startActivity(intent);
        }
        else if(view == btnLogin){
            Log.d("debug","btn login was clicked");
            boolean result = LoginHandler();
            if(result == true){
                Log.d("debug","Login Success+++");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                //Mask: Close this activity
                finish();
            }
            else
            {
                Log.d("debug","Login Error---");
            }

        }
        else if(view == btnSignup){
            Log.d("debug","btn signup was clicked");
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private boolean LoginHandler(){

        boolean result;
        Random r = new Random();
        int UserId = r.nextInt();

        String userName;
        String password;

        Log.d("debug","LoginHandler");
        userName = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();
        result = false;
        //Mask: check user exist.
        Cursor res = databaseHelper.SqlSelect("select * from user_login where user_name = "+"'"+userName+"'");
        if(res.getCount() == 0){
            Log.d("debug","No user in database : verify user with server");
            //Mask: no user -> verify user with server
            result = true;

            //Mask: Save user to tb_login_info
            boolean result1 = databaseHelper.InsertData_tbLoginInfo(UserId++,userName,1);
            Log.d("debug","insert data = " + String.valueOf(result1));

        }
        else {
            result = true;
            Log.d("debug", "Have user in database : update status");
            //have user
            if (res.moveToNext() == true) {

                //Mask: Get data from database
                int user_active = res.getInt(2);
                UserId = res.getInt(0);
                //Mask: update data if user status = 0
                Log.d("debug", "user active = " + user_active);
                if (user_active != 1) {
                    Log.d("debug", "user not active -> update user status");
                    //Mask: Udate user status = 1
                    boolean result1 = databaseHelper.UpdateData_tbLoginInfo(UserId, userName, 1);
                    Log.d("debug", "update data = " + String.valueOf(result1));
                }
            }
        }
        return result;

    }

}
