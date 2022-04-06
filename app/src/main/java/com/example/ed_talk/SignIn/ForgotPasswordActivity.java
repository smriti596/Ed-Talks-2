package com.example.ed_talk.SignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ed_talk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailUserEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;
    private TextView logIn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailUserEditText = (EditText) findViewById(R.id.editTextEmail);
        resetPasswordButton = (Button) findViewById(R.id.cirResetPasswordButton);
        resetPasswordButton.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        logIn = (TextView) findViewById(R.id.textview_logIn);
        logIn.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

//        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resetPassword();
//
//            }
//        });
        }
         @Override
         public void onClick(View view) {
             switch ((view.getId())){
                 case R.id.cirResetPasswordButton:
                     resetPassword();
                     break;
                 case R.id.textview_logIn:
                     startActivity(new Intent(this, LoginActivity.class));
                     break;
             }

        }

            private void resetPassword() {
                String email = emailUserEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailUserEditText.setError("Email is required");
                    emailUserEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailUserEditText.setError(("Please provide valid email"));
                    emailUserEditText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Try Again. Something went wrong.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });
            }


}
