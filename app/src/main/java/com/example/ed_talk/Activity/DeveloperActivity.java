package com.example.ed_talk.Activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.ed_talk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeveloperActivity extends Activity {

    DatabaseReference mDeveloperImageRef;
    String profileImageUrl;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        imgView = findViewById(R.id.profileImg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDeveloperImageRef= FirebaseDatabase.getInstance().getReference("DeveloperPic");
        mDeveloperImageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileImageUrl=dataSnapshot.getValue().toString();

                Glide.with(DeveloperActivity.this)
                        .load(profileImageUrl)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.user_avtar)
                                .fitCenter())
                        .into(imgView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
