package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bjl.tannum.wellnessathome.Controller.Library.AlertDialog;
import com.bjl.tannum.wellnessathome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnResetPassword;
    EditText txtEmail;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        txtEmail = (EditText)findViewById(R.id.editTextResetPassword);
        btnResetPassword = (Button)findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(this);

        //Mask: Initial progress dialog.
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait...");
        pDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnResetPassword){
            ResetPasswordHandler();
        }
    }

    private void ResetPasswordHandler(){

        //Mask: Initial object
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = txtEmail.getText().toString();

        //Mask: set waiting dialog.
        pDialog.show();
        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    pDialog.dismiss();
                    AlertDialog.ShowSuccessDialog(ResetPasswordActivity.this, "Success", "Please check your email", new AlertDialog.OnDialogConfirmListener() {
                        @Override
                        public void onDialogConfirm() {
                            finish();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pDialog.dismiss();
                AlertDialog.ShowErrorDialog(ResetPasswordActivity.this,"Error",e.getLocalizedMessage());
            }
        });
    }
}
