package com.example.yu_map.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatListActivity extends AppCompatActivity {

    private ChatListAdapter adapter;
    private ChatListData data;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    int idx = Email.indexOf("@");
    String NickName = Email.substring(0, idx);
    String TAG = "ChatListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        init();
        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.ChatRoomList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DatabaseReference dbr = db.getReference().child("ChatRoom").child(NickName);
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                addItem(snapshot.getKey());
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
    private void addItem(String name){
        ChatListData data = new ChatListData();
        data.setRoomName(name);

        adapter.addItem(data);
    }
}