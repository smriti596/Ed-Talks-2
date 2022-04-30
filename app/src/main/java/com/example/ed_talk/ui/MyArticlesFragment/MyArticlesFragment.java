package com.example.ed_talk.ui.MyArticlesFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ed_talk.Modals.InterviewExperience;
import com.example.ed_talk.R;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceAdapter;
import com.example.ed_talk.ViewInterviewExperiences.SearchActivity;

import com.example.ed_talk.databinding.FragmentMyarticlesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyArticlesFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentMyarticlesBinding binding;

    DatabaseReference mMessagesDatabaseReference;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TextView toolbarTitle, setTitle;
    ImageView searchIcon;
    ProgressBar progressBar;
    static List<InterviewExperience> interviewExperienceList;
    String mEmail="";
    FirebaseAuth mAuth;
    Button placementBtn, internshipBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentMyarticlesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        placementBtn= root.findViewById(R.id.placement);
        internshipBtn= root.findViewById(R.id.internship);
        setTitle= root.findViewById(R.id.setTitleTV);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        toolbarTitle = (TextView)root.findViewById(R.id.toolbar_title);
        progressBar=root.findViewById(R.id.progressBar);
        searchIcon = (ImageView)root.findViewById(R.id.searchIcon);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mEmail = user.getEmail();

        }


        //Placement Experience
        placementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CORRECTION DONEEEEE
                // VIEWING MY ARTICLES
                //CHANGE THIS QUERY
                setTitle.setText("Placement Experiences");

                mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Placement");
                recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

                interviewExperienceList = new ArrayList<>();

                layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);

                final my_Articles_adapter adapter = new my_Articles_adapter(getContext(), interviewExperienceList);
                recyclerView.setAdapter(adapter);

                mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        interviewExperienceList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            InterviewExperience interviewExperience = dataSnapshot.getValue(InterviewExperience.class);
//                    if (interviewExperience.getCnfStatus()==1)
                            if (interviewExperience.getCnfStatus()==1 && interviewExperience.getEmail().equals(mEmail) && interviewExperience.getEmail() != null && mEmail != null)
                                interviewExperienceList.add(interviewExperience);
                        }
                        adapter.notifyDataSetChanged();
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });


        //internship experience
        internshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CORRECTION DONEEEEE
                // VIEWING MY ARTICLES
                //CHANGE THIS QUERY
                setTitle.setText("Internship Experiences");

                mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Internship");
                recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

                interviewExperienceList = new ArrayList<>();

                layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);

                final my_Articles_adapter adapter = new my_Articles_adapter(getContext(), interviewExperienceList);
                recyclerView.setAdapter(adapter);

                mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        interviewExperienceList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            InterviewExperience interviewExperience = dataSnapshot.getValue(InterviewExperience.class);
//                    if (interviewExperience.getCnfStatus()==1)
                            if (interviewExperience.getCnfStatus()==1 && interviewExperience.getEmail().equals(mEmail) && interviewExperience.getEmail() != null && mEmail != null)
                                interviewExperienceList.add(interviewExperience);
                        }
                        adapter.notifyDataSetChanged();
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });



        // Search activity
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}