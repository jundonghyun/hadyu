package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yu_map.R;

public class HyunseolActivity extends AppCompatActivity {

    Button btn_announce, btn_survey, btn_community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_hyunseol);

        btn_announce = (Button)findViewById(R.id.btn_announce);
        btn_survey = (Button)findViewById(R.id.btn_survey);
        btn_community = (Button)findViewById(R.id.btn_community);

        btn_announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnnounceActivity.class);
                startActivity(intent);
            }
        });
        btn_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SurveyActivity.class);
                startActivity(intent);
            }
        });
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommunityActivity.class);
                startActivity(intent);
            }
        });

    }
}