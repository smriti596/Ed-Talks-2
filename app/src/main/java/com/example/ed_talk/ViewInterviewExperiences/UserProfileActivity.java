package com.example.ed_talk.ViewInterviewExperiences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ValueEventListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ed_talk.Modals.InterviewExperience;
import com.example.ed_talk.Modals.Post;
import com.example.ed_talk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class UserProfileActivity extends Activity {

    TextView mStudName,mBranch,mCompanyName,mJobTitle,mInternCompany;
    TextView mProjectDesc,mOnlineRound,mTechRound,mHrRound,mInterviewMode,mInterviewDifficulty,mWordsToJr;
    private ImageView mImageView;
    private Button mStudentLinkedIn,mStudentPhone,mStudentWhatsapp;
     TextView likeCount, dislikeCount, reportCount;
     ImageView  like,dislike,report;
    private FirebaseUser user;
    public Context context;
    private List<InterviewExperience> placementList;
    InterviewExperience interviewExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //mImageView=(ImageView)findViewById(R.id.photoUrl);
        mStudName=(TextView)findViewById(R.id.studName);
        mStudentLinkedIn=(Button) findViewById(R.id.linkedInId);
        mStudentPhone=(Button) findViewById(R.id.phoneNum);
        mBranch=(TextView)findViewById(R.id.branch);
        mCompanyName=(TextView)findViewById(R.id.companyName);
        mJobTitle=(TextView)findViewById(R.id.jobTitle);
        mInternCompany=(TextView)findViewById(R.id.internCompany);
        mProjectDesc=(TextView)findViewById(R.id.projectDesc);
        mOnlineRound=(TextView)findViewById(R.id.onlineRound);
        mTechRound=(TextView)findViewById(R.id.techRound);
        mHrRound=(TextView)findViewById(R.id.hrRound);
        mInterviewDifficulty=(TextView)findViewById(R.id.interviewDifficulty);
        mInterviewMode=(TextView)findViewById(R.id.interviewMode);
        mWordsToJr=(TextView)findViewById(R.id.wordsToJr);
        mStudentWhatsapp=(Button)findViewById(R.id.whatsappNum);
        likeCount=findViewById(R.id.likeCount_article);
        dislikeCount=findViewById(R.id.dislikeCount_article);
       // reportCount=findViewById(R.id.ReportCount_article);
        like=findViewById(R.id.like_article);
        dislike=findViewById(R.id.dislike_article);
        //report=findViewById(R.id.report_article);

      //  InterviewExperience
        user = FirebaseAuth.getInstance().getCurrentUser();
                interviewExperience = (InterviewExperience)getIntent().getSerializableExtra("StudentDetails");
       // InterviewExperience curr=
        /*
        Glide.with(getApplicationContext())
                .load(interviewExperience.getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_avtar)
                        .centerCrop()
                        .fitCenter())
                .into(mImageView);

        */
        mStudName.setText(interviewExperience.getStudName());
        mStudentLinkedIn.setText(interviewExperience.getLinkedInId());
        mStudentPhone.setText(interviewExperience.getWhatsappNum());
        mBranch.setText(interviewExperience.getBranch());
        mCompanyName.setText(interviewExperience.getCompanyName());
        mJobTitle.setText(interviewExperience.getJobTitle());
        mInternCompany.setText(interviewExperience.getInternCompany());
        mProjectDesc.setText(interviewExperience.getProjectDesc());
        mOnlineRound.setText(interviewExperience.getOnlineRound());
        mTechRound.setText(interviewExperience.getTechRound());
        mHrRound.setText(interviewExperience.getHrRound());
        mInterviewDifficulty.setText(interviewExperience.getInterviewDifficulty());
        mInterviewMode.setText(interviewExperience.getInterviewMode());
        mWordsToJr.setText(interviewExperience.getWordsToJr());

        String whatsappUrl = "https://api.whatsapp.com/send?phone=" + "+91" + interviewExperience.getWhatsappNum();
        mStudentWhatsapp.setText(whatsappUrl);
        isLiked(interviewExperience.getChildKey(),like);
        isDisLiked(interviewExperience.getChildKey(), dislike);
        getDislikesCount(dislikeCount, interviewExperience.getChildKey());
       getLikesCount(likeCount, interviewExperience.getChildKey());
       //getCommentCount(holder.commentTextView, interviewExperience.getChildKey();


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getTag().equals("like") && dislike.getTag().equals("dislike")) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("likes")
                            .child(interviewExperience.getChildKey())

                            .child(user.getUid())
                            .setValue(true);
                } else if (like.getTag().equals("like") && dislike.getTag().equals("disliked") ) {
                    //un dislike
                    FirebaseDatabase.getInstance().getReference()
                            .child("dislikes")
                            .child(interviewExperience.getChildKey())

                            .child(user.getUid())
                            .removeValue();
                    //set like to true
                    FirebaseDatabase.getInstance().getReference()
                            .child("likes")
                            .child(interviewExperience.getChildKey())

                            .child(user.getUid())
                            .setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("likes")
                            .child(interviewExperience.getChildKey())

                            .child(user.getUid())
                            .removeValue();
                }
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dislike.getTag().equals("dislike") && like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference()
                            .child("dislikes")
                            .child(interviewExperience.getChildKey())
                            .child(user.getUid())
                            .setValue(true);
                }else if (dislike.getTag().equals("dislike") && like.getTag().equals("liked")){
                    FirebaseDatabase.getInstance().getReference()
                            .child("likes")
                            .child(interviewExperience.getChildKey())
                            .child(user.getUid())
                            .removeValue();
                    FirebaseDatabase.getInstance().getReference()
                            .child("dislikes")
                            .child(interviewExperience.getChildKey())
                            .child(user.getUid())
                            .setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("dislikes")
                            .child(interviewExperience.getChildKey())

                            .child(user.getUid())
                            .removeValue();
                }
            }
        });
      }

        public void isLiked(String postID, ImageView like) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("likes").child(postID);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.child(userID).exists() ){
                        like.setImageResource(R.drawable.ic_liked);
                        like.setTag("liked");
                    } else {
                        like.setImageResource(R.drawable.ic_like);
                        like.setTag("like");
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void isDisLiked(String postID, ImageView dislike) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("dislikes").child(postID);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.child(userID).exists()) {
                        dislike.setImageResource(R.drawable.ic_disliked);
                        dislike.setTag("disliked");
                    } else {
                        dislike.setImageResource(R.drawable.ic_dislike);
                        dislike.setTag("dislike");
                    }
                }

                @Override
                public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            }
    public void getLikesCount(TextView lileTxt, String postID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("likes").child(postID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lileTxt.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDislikesCount(TextView dislikeTxt, String postID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dislikes").child(postID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                dislikeTxt.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}