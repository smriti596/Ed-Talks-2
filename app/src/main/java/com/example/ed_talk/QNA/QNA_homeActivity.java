package com.example.ed_talk.QNA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ed_talk.Activity.MainActivity;
import com.example.ed_talk.Modals.Post;
import com.example.ed_talk.R;
import com.example.ed_talk.SignIn.LoginActivity;
import com.example.ed_talk.SignIn.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QNA_homeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    private ProgressBar progressCircular;
    private LinearLayoutManager linearLayoutManager;

    private PostAdapter postAdapter;
    private List<Post> postList;

    private CircleImageView navHeaderImage;
    private TextView navEmail, navUsername;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_home);
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QNA_homeActivity.this, QNA_Activity.class);
                startActivity(intent);

            }});
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(QNA_homeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        loadAllPost();

    }

    private void loadAllPost() {
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("questions_posts");
        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post == null) {
                        break;
                    }
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();
                progressCircular.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(QNA_homeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }


    public void initView() {
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        progressCircular = findViewById(R.id.progress_circular);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(QNA_homeActivity.this, postList);
        recyclerView.setAdapter(postAdapter);

        userReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
}