//package com.example.ed_talk.ui.MyArticlesFragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.ed_talk.Modals.InterviewExperience;
//import com.example.ed_talk.R;
//import com.example.ed_talk.SignIn.ForgotPasswordActivity;
//import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceActivity;
//import com.example.ed_talk.ViewInterviewExperiences.myArticles;
//import com.example.ed_talk.databinding.FragmentCompaniesBinding;
//import com.example.ed_talk.databinding.FragmentMyarticlesBinding;
//import com.example.ed_talk.ui.CompaniesFragment.SlideshowViewModel;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.List;
//
//public class MyArticlesFragment extends Fragment {
//
//
//    private FragmentMyarticlesBinding binding;
//    private GalleryViewModel GalleryViewModel;
//    Button button69;
//
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
//        View root = binding.getRoot();
//        button69=root.findViewById(R.id.button69);
//        binding = FragmentMyarticlesBinding.inflate(inflater, container, false);
//
//
//        //final TextView textView = binding.TextS;
//        GalleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//        //        textView.setText(s);
//            }
//        });
////        button69.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent i = new Intent(getContext(), myArticles.class);
////                startActivity(i);
////            }
////        });
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ed_talk.Modals.InterviewExperience;
import com.example.ed_talk.R;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceActivity;
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
    TextView toolbarTitle;
    ImageView searchIcon;
    ProgressBar progressBar;
    static List<InterviewExperience> interviewExperienceList;
    String mEmail="";
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentMyarticlesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        toolbarTitle = (TextView)root.findViewById(R.id.toolbar_title);
        progressBar=root.findViewById(R.id.progressBar);
        searchIcon = (ImageView)root.findViewById(R.id.searchIcon);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mEmail = user.getEmail();

        }

        String expType = getActivity().getIntent().getStringExtra("ExpType");
        toolbarTitle.setText(getActivity().getIntent().getStringExtra("Title"));




        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Internship");
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        interviewExperienceList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final InterviewExperienceAdapter adapter = new InterviewExperienceAdapter(getContext(), interviewExperienceList);
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
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        interviewExperienceList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final InterviewExperienceAdapter aadapter = new InterviewExperienceAdapter(getContext(), interviewExperienceList);
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
                startActivity(new Intent(getContext(),SearchActivity.class));
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