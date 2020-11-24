package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yu_map.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.os.Bundle;
import com.example.yu_map.R;

public class AnnounceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnnounceAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Announce> items = new ArrayList<>();

    private List<String> num;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String st;
    private String st2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_announce);

        //ViewGroup rootView = (ViewGroup)getLayoutInflater().inflate(R.layout.a_announce, container, false);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_announce);
        adapter = new AnnounceAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mref = firebaseDatabase.getReference("community");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    st = ds.getKey();
                    if(st != null){
                        num = Arrays.asList(st);
                        for(int i=0; i<num.size(); i++){
                            st2 = ds.child("title").getValue().toString();
                            adapter.addItem(new Announce(st, st2));
                            recyclerView.setAdapter(adapter);
                            st = ds.getKey();
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Button btn_main = (Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}