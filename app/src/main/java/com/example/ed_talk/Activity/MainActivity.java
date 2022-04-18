package com.example.ed_talk.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ed_talk.R;
import com.example.ed_talk.SignIn.LoginActivity;
import com.example.ed_talk.Utils.SharedPrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ed_talk.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    DrawerLayout drawer;
    NavigationView navigationView;
    View mHeaderView;
    private FirebaseAuth mAuth;
    TextView mUserName, mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;




        // Passing each menu ID as a set of Ids because each
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_gallery, R.id.nav_slideshow, R.id.bookmarksFragment, R.id.qnaFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPrefManager sharedPrefManager=new SharedPrefManager(this);
        if(sharedPrefManager.isLoggedIn()){
            updateSideNavHeader();
        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void logout(MenuItem item) {
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
     /*   GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();*/
        FirebaseAuth.getInstance().signOut();
        SharedPrefManager shared = new SharedPrefManager(this);
        shared.setIsLoggedIn(false);
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }

    public void updateSideNavHeader() {
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderView = mNavigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        // View
        mUserName = (TextView) mHeaderView.findViewById(R.id.name);
        mUserEmail = (TextView) mHeaderView.findViewById(R.id.email);
        //mUserImage = (ImageView) mHeaderView.findViewById(R.id.imageView);

        // Set username & email

        mUserName.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
        mUserEmail.setText(mAuth.getCurrentUser().getEmail());
       /* Glide.with(getApplicationContext())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(mUserImage);*/
    }
    public void openDevActivity(MenuItem item) {
        drawer.closeDrawers();
        Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
        startActivity(intent);
    }
    public void openWebsite(MenuItem item) {
        drawer.closeDrawers();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.banasthali.org/banasthali/wcms/en/home/")));
    }
    public void placementBrochure(MenuItem item) {
        drawer.closeDrawers();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.banasthali.org/banasthali/admissions/brochures/banasthali_text_broucher.pdf")));
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}