package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yu_map.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComContentActivity extends AppCompatActivity {
    private TextView textView_title;
    private TextView textView_content;
    private String number;
    private String title;
    private String content;
    private Button btn_back;
    Integer num;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_com_content);

        btn_back = (Button)findViewById(R.id.btn_back);
        textView_title = findViewById(R.id.textview_announce_title);
        textView_content = findViewById(R.id.textview_announce_content);

        Intent intent = getIntent();

        number = intent.getStringExtra("pos");
        title = intent.getStringExtra("title");
        textView_title.setText(title);

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mref = firebaseDatabase.getReference("announce").child(number).child("content");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //for(DataSnapshot ds : snapshot.getChildren()){
                content = snapshot.getValue().toString();
                textView_content.setText(content);

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