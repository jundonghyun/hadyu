package com.example.yu_map.TaxiCarPool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.yu_map.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaxiCarPoolShowWatiListActivity extends AppCompatActivity {

    private TaxiCarPoolShowWaitListAdapter adapter;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private TaxiCarPoolShowWaitListData mdata;
    private String TAG = "TaxiCarPoolShowWatiListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_car_pool_show_wati_list);

        init();

        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.TaxiCarPoolWatiList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TaxiCarPoolShowWaitListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DatabaseReference dbr = db.getReference().child("TaxiCarPool");
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mdata = snapshot.getValue(TaxiCarPoolShowWaitListData.class);
                adapter.addItem(mdata);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}