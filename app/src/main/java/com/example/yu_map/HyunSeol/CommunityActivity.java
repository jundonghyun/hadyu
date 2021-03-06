package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yu_map.Activity.HomeActivity;
import com.example.yu_map.HyunSeol.Announce;
import com.example.yu_map.HyunSeol.CommunityAdapter;
import com.example.yu_map.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Announce> items = new ArrayList<>();

    private List<String> num;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FloatingActionButton fa_btn;

    private String st;
    private String st2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_community);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_community);
        adapter = new CommunityAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fa_btn = (FloatingActionButton)findViewById(R.id.fa_btn);

        //num = new ArrayList<>(); //추가

        //firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mref = firebaseDatabase.getReference("Community");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //num.clear(); //추가
                for(DataSnapshot ds : snapshot.getChildren()){
                    //Announce anList = ds.getValue(Announce.class);
                    //num.add(11, "1");
                    st = ds.getKey();
                    st2 = ds.child("title").getValue().toString();
                    adapter.addItem(new Announce(st, st2));
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        fa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WriteActivity.class);
                finish();
                startActivity(intent);

            }
        });

        Button btn_main = (Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(CommunityActivity.this, HomeActivity.class));
            }
        });
    }
}