package com.example.ed_talk.ui.MyArticlesFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ed_talk.Modals.InterviewExperience;
import com.example.ed_talk.R;
import com.example.ed_talk.ViewInterviewExperiences.InterviewExperienceAdapter;
import com.example.ed_talk.ViewInterviewExperiences.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class my_Articles_adapter extends RecyclerView.Adapter<my_Articles_adapter.InterviewExperienceViewHolder> {

    private Context myContext;

    //creating list to store all ojass departments
    private List<InterviewExperience> placementList;

    DatabaseReference mMessagesDatabaseReference;

    FirebaseAuth auth;

    //getting the context and ojass department list with constructor
    public my_Articles_adapter(Context myContext, List<InterviewExperience> placementList){
        this.myContext = myContext;
        this.placementList = placementList;

        auth=FirebaseAuth.getInstance();
        mMessagesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("2021").child("Placement");
    }

    @NonNull
    @Override
    public my_Articles_adapter.InterviewExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflating and returning our view holder
        LayoutInflater layoutInflater = LayoutInflater.from(myContext);
        View view = layoutInflater.inflate(R.layout.card_my_experience, null);

        return new InterviewExperienceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final InterviewExperienceViewHolder interviewExperienceViewHolder, final int position) {
        final InterviewExperience interviewExperience = placementList.get(position);

        interviewExperienceViewHolder.mStudName.setText(interviewExperience.getStudName());
        interviewExperienceViewHolder.mStudCompany.setText(interviewExperience.getCompanyName());
        interviewExperienceViewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletePost(interviewExperience.getChildKey());

            }
        });
        /*
        Glide.with(myContext)
                .load(interviewExperience.getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.user_avtar)
                        .fitCenter())
                .into(interviewExperienceViewHolder.mStudPic);
*/
        interviewExperienceViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(myContext, UserProfileActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("StudentDetails",interviewExperience);
                myContext.startActivity(i);

            }
        });

    }

    private void deletePost(String childKey) {

        AlertDialog.Builder alert = new AlertDialog.Builder(myContext);
        alert.setTitle("Warning");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage("Do you really want to delete this post ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference curr_add= mMessagesDatabaseReference
                        .child(childKey);

                curr_add.removeValue();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

    @Override
    public int getItemCount() {
        return placementList.size();
    }


    public static class InterviewExperienceViewHolder extends RecyclerView.ViewHolder {

        // ImageView mStudPic;
        TextView mStudName,mStudCompany;
        CardView mCardView;
        ImageView deleteItem;


        public InterviewExperienceViewHolder(@NonNull final View itemView) {
            super(itemView);
            //   mStudPic = itemView.findViewById(R.id.photoImageView);
            mStudName = itemView.findViewById(R.id.nameTextView);
            mStudCompany = itemView.findViewById(R.id.studCompanyName);
            mCardView = itemView.findViewById(R.id.cardView);
            deleteItem = itemView.findViewById(R.id.delete);
        }


    }}