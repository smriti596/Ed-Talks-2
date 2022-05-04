package com.example.ed_talk.ui.static_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ed_talk.R;

public class About extends AppCompatActivity {


    TextView mAbout,mOnus,mTeam;
    Button fbLink,websiteLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAbout=(TextView)findViewById(R.id.about);
        mOnus=(TextView)findViewById(R.id.onus);
        mTeam=(TextView)findViewById(R.id.team);

        String about="The Training & Placement Cell, Banasthali Vidyapith facilitates the process of placement of students passing out from the Institute besides collaborating with leading organizations and institutes in setting up of internship and training program of students. The office liaises with various industrial establishments, corporate houses etc which conduct campus interviews and select graduate and post-graduate students from all disciplines. The Training & Placement Cell provides the infra-structural facilities to conduct group discussions, tests and interviews besides catering to other logistics. The Office interacts with many industries in the country, of which nearly 50+ companies visit the campus for holding campus interviews. The industries which approach the institute come under the purview of:";
        String onus="•\tCore Engineering industries\n•\tIT & IT enabled services\n•\tManufacturing Industries\n•\tConsultancy Firms\n•\tFinance Companies\n•\tManagement Organisations\n•\tR & D laboratories";
        String team="The placement season runs through the course of the year commencing the last week of July through to June. Pre-Placement Talks are also conducted in this regard as per mutual convenience. Job offers, dates of interviews, selection of candidates etc. are announced through the Training & Placement Cell. The Placement Office is assisted by a committee comprising representatives of students from the under-graduate and post-graduate engineering streams. The committee evolves a broad policy framework every year besides a set of rules which are inviolable. Students members are closely co-opted in implementing these policy decisions.\n\n";

        mAbout.setText(Html.fromHtml(about));
        mOnus.setText(onus);
        mTeam.setText(team);

        fbLink=(Button)findViewById(R.id.fbLink);
        websiteLink=(Button)findViewById(R.id.websiteLink);

        fbLink.setText("https://www.facebook.com/banasthali.org");
        websiteLink.setText("http://www.banasthali.org/banasthali/wcms/en/home/");
    }
}