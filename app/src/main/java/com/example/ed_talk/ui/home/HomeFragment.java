package com.example.ed_talk.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.ed_talk.AddInterviewExperience.AddInterviewExperienceActivity;
//import com.example.ed_talk.Carousel.SliderAdapter;
import com.example.ed_talk.Modals.SliderImageAndText;
import com.example.ed_talk.R;
import com.example.ed_talk.Utils.SharedPrefManager;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceActivity;
import com.example.ed_talk.databinding.FragmentHomeBinding;
import com.example.ed_talk.ui.static_pages.About;
import com.example.ed_talk.ui.static_pages.Past_Recruiters;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    ViewPager viewPager;
    TabLayout indicator;
    List<String> sliderImages;
    List<String> sliderText;
    String sliderText1,sliderText2,sliderText3;
    String sliderImage1,sliderImage2,sliderImage3;
    DatabaseReference mPlacementYearReference; //to get current placement year
    String currPlacementYear;
    DatabaseReference mSliderReference;
    TextView mCurrPlacementYear;
    CardView Past_Companies,about;

    LinearLayout internshipExperience, shareInterviewExperience,viewInterviewExperiences;

    private SharedPrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        indicator = (TabLayout) root.findViewById(R.id.indicator);
        viewInterviewExperiences =(LinearLayout)root.findViewById(R.id.placementExperiences);
        shareInterviewExperience = (LinearLayout)root.findViewById(R.id.shareInterviewExperience);
        internshipExperience = (LinearLayout)root.findViewById(R.id.internshipExprience);
        mCurrPlacementYear = (TextView)root.findViewById(R.id.currPlacementYear);
        Past_Companies=(CardView)root.findViewById(R.id.pastRecruiters);
        about=(CardView)root.findViewById(R.id.about);
        prefManager = new SharedPrefManager(getActivity());
        if(prefManager.isPopupDialogShown()==false){
            showReportedDialog();
        }

        getPlacementYear();
        //setCarouselViewPager();

        viewInterviewExperiences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), InterviewExperienceActivity.class);
                i.putExtra("ExpType", "Placement");
                i.putExtra("Title", "Interview Experiences");
                startActivity(i);
            }
        });

        shareInterviewExperience = (LinearLayout)root.findViewById(R.id.shareInterviewExperience);
        shareInterviewExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddInterviewExperienceActivity.class);
                startActivity(i);
            }
        });

        internshipExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), InterviewExperienceActivity.class);
                i.putExtra("ExpType", "Internship");
                i.putExtra("Title", "Internship Experiences");
                startActivity(i);
            }
        });
        Past_Companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Past_Recruiters.class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), About.class);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getPlacementYear() {
        mPlacementYearReference= FirebaseDatabase.getInstance().getReference("PlacementYear");
        mPlacementYearReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currPlacementYear=dataSnapshot.getValue().toString();
                mCurrPlacementYear.setText(currPlacementYear);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void showReportedDialog() {
        prefManager.setPopupShown(true);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.interview_exp_request_dialog, null, false);


        Button addButton = dialogView.findViewById(R.id.okBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

}