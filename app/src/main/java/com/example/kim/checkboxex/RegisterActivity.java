package com.example.kim.checkboxex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText mNameTxt;
    private EditText mEmailTxt;
    private EditText mPasswordTxt;
    private Button mSubmitBtn;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameTxt = (EditText) findViewById(R.id.name_txt);
        mEmailTxt = (EditText) findViewById(R.id.email_txt);
        mPasswordTxt = (EditText) findViewById(R.id.pass_txt);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailTxt.getText().toString().trim();
                String password = mPasswordTxt.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    signupUser(email,password);

                }

            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    private void signupUser(String email, String password){


        mProgress  = new ProgressDialog(this);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setMessage("Wait as your account is created....");

        mProgress.show();
        mSubmitBtn.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    mProgress.dismiss();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();

                }else {
                    mProgress.dismiss();
                    mSubmitBtn.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "Failure in creating User" + task.getException(),Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
