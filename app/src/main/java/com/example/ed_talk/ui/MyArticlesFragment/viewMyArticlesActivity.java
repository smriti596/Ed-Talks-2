package com.example.ed_talk.ui.MyArticlesFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ed_talk.Modals.InterviewExperience;
import com.example.ed_talk.R;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceActivity;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceAdapter;
import com.example.ed_talk.ViewInterviewExperiences.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewMyArticlesActivity extends Activity {
    DatabaseReference mMessagesDatabaseReference;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TextView toolbarTitle;
    ImageView searchIcon;
    ProgressBar progressBar;
    static List<InterviewExperience> interviewExperienceList;
    String mEmail="";
    FirebaseAuth mAuth;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_experience);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        progressBar=findViewById(R.id.progressBar);
        searchIcon = (ImageView)findViewById(R.id.searchIcon);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mEmail = user.getEmail();

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        String expType = getIntent().getStringExtra("ExpType");
        toolbarTitle.setText(getIntent().getStringExtra("Title"));




        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Internship");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        interviewExperienceList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(viewMyArticlesActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final InterviewExperienceAdapter adapter = new InterviewExperienceAdapter(getApplicationContext(), interviewExperienceList);
        recyclerView.setAdapter(adapter);

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                interviewExperienceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InterviewExperience interviewExperience = dataSnapshot.getValue(InterviewExperience.class);
//                    if (interviewExperience.getCnfStatus()==1)
                    if (interviewExperience.getEmail().equals(mEmail) && interviewExperience.getEmail() != null && mEmail != null)
                        interviewExperienceList.add(interviewExperience);
                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Placement");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        interviewExperienceList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(viewMyArticlesActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final InterviewExperienceAdapter aadapter = new InterviewExperienceAdapter(getApplicationContext(), interviewExperienceList);
        recyclerView.setAdapter(aadapter);

        mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);
                interviewExperienceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InterviewExperience interviewExperience = dataSnapshot.getValue(InterviewExperience.class);
//                    if (interviewExperience.getCnfStatus()==1)
                    if (interviewExperience.getEmail().equals(mEmail) && interviewExperience.getEmail() != null && mEmail != null)
                        interviewExperienceList.add(interviewExperience);
                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        // Search activity
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewMyArticlesActivity.this, SearchActivity.class));
            }
        });

    }

}
