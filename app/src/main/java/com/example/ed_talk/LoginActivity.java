package com.example.ed_talk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //finding textview clicking on which we are redirected to register page from login page.
        register=(TextView) findViewById(R.id.textview_register);
        //seting on click listener on textview register
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textview_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }

    }
}