package com.example.ed_talk.QNA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ed_talk.Modals.Comment;
import com.bumptech.glide.Glide;
import com.example.ed_talk.R;
import com.example.ed_talk.SignIn.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


import com.example.ed_talk.SignIn.User;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private String postid;
    private FirebaseUser user;

    public CommentAdapter(Context context, List<Comment> commentList, String postid) {
        this.context = context;
        this.commentList = commentList;
        this.postid = postid;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        Comment comment = commentList.get(position);

        holder.commentorComment.setText(comment.getComment());
        holder.commentDate.setText(comment.getDate());
        setUserInformation( holder.commentorUsername, comment.getPublisher());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView commentorProfileImage;
        public TextView commentorUsername, commentorComment, commentDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
           // commentorProfileImage = itemView.findViewById(R.id.commentor_profile_image);
            commentorUsername = itemView.findViewById(R.id.commentor_username);
            commentorComment = itemView.findViewById(R.id.commentor_comment);
            commentDate = itemView.findViewById(R.id.commentDate);
        }
    }

    private void setUserInformation( TextView usernameTextView, String publisherID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
               // Glide.with(context).load(user.getProfileimageurl()).into(profileImageView);

                // assert user != null;

                //inplace of assert
                if(user!=null)
                    usernameTextView.setText(user.getFullName());


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
