package com.example.ed_talk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText editTextUserEmail,editTextUserPassword;
    private Button logIn;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //finding textview clicking on which we are redirected to register page from login page.
        register=(TextView) findViewById(R.id.textview_register);
        //seting on click listener on textview register
        register.setOnClickListener(this);

        logIn =(Button) findViewById(R.id.cirLoginButton);
        logIn.setOnClickListener(this);

        editTextUserEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextUserPassword=(EditText) findViewById(R.id.editTextPassword);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textview_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.cirLoginButton:
                userLogIn();
        }

    }

    private void userLogIn() {
        String email=editTextUserEmail.getText().toString().trim();
        String password=editTextUserPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextUserEmail.setError("Email is required");
            editTextUserEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextUserEmail.setError(("Please enter valid email"));
            editTextUserEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextUserPassword.setError("Password is required");
            editTextUserPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextUserPassword.setError("Min password length is 6 characters long!");
            editTextUserPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //redirect
                    //displaying success message temporarily
                    Toast.makeText(LoginActivity.this,"Successfully logged in you FUCKIN' DICKHEAD",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility((View.GONE));
                }else
                {
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your credentials.",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility((View.GONE));
                }
            }
        });
    }
}