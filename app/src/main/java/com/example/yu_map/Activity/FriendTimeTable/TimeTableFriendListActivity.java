package com.example.yu_map.Activity.FriendTimeTable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TimeTableFriendListActivity extends AppCompatActivity {

    private TimeTableFriendListAdapter adapter;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_friend_list);

        init();

        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.TimeTableFriendList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TimeTableFriendListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        int idx = Email.indexOf("@");

        final String NickName = Email.substring(0, idx);

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference addFriend = db.getReference().child("FriendList").child(NickName);

        addFriend.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String id = ds.getValue().toString();
                    addItem(id);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addItem(String s){
        TimeTableFriendListViewItem data = new TimeTableFriendListViewItem();
        data.setName(s);

        adapter.addItem(data);
    }
}