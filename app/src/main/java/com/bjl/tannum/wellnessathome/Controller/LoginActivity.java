package com.bjl.tannum.wellnessathome.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjl.tannum.wellnessathome.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnForgot;
    Button btnLogin;
    Button btnSignup;
    EditText editTextUserName;
    EditText editTextPassword;
    SharedPreferences.Editor sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("debug","start login activity");
        //Mask: Init SharePreferences
        sharedPreferences = getSharedPreferences("USERINFO",MODE_PRIVATE).edit();
        //Mask: Init Component
        btnForgot = (Button) findViewById(R.id.btnForgotPwd);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignup = (Button)findViewById(R.id.btnSignUp);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        btnForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnForgot){
            Log.d("debug","btn forgot was clicked");
            Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
            startActivity(intent);
        }
        else if(view == btnLogin){
//            Log.d("debug","btn login was clicked");
//            boolean result = LoginHandler();
//            if(result == true){
//                Log.d("debug","Login Success+++");
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//            else
//            {
//                Log.d("debug","Login Error---");
//            }

        }
        else if(view == btnSignup){
            Log.d("debug","btn signup was clicked");
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }


}
