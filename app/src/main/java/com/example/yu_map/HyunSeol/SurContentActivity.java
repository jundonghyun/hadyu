package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;

import com.example.yu_map.R;

public class SurContentActivity extends AppCompatActivity {
    private android.webkit.WebView WebView; // 웹뷰 선언
    private android.webkit.WebSettings WebSettings; //웹뷰세팅
    private TextView textView_title;
    private String number;
    private String title;
    private String content;
    private Button btn_back;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_sur_content);
        btn_back = (Button)findViewById(R.id.btn_announce);
        WebView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();

        textView_title = findViewById(R.id.textview_survey_title);
        number = intent.getStringExtra("pos");
        title = intent.getStringExtra("title");
        textView_title.setText(title);
        //매니페스트 인터넷 연결 + android:usesCleartextTraffic="true"

        //firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mref = firebaseDatabase.getReference("survey").child(number).child("link");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //for(DataSnapshot ds : snapshot.getChildren()){
                content = snapshot.getValue().toString();
                WebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
                WebSettings = WebView.getSettings(); //세부 세팅 등록
                WebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
                WebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
                WebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                WebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
                WebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
                WebSettings.setSupportZoom(false); // 화면 줌 허용 여부
                WebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
                //WebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
                WebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
                WebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
                WebView.loadUrl(content);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}