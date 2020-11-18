package com.example.yu_map.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.FriendsListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private ChatAdapter adapter;
    private EditText send;
    private Button sendButton;
    private ChatData chatData;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private String FriendNickName = FriendsListAdapter.FriendName;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    int idx = Email.indexOf("@");
    String NickName = Email.substring(0, idx);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        send = findViewById(R.id.inputmessage);
        sendButton = findViewById(R.id.sendbutton);

        init();
        getData();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatData = new ChatData(NickName, send.getText().toString());
                DatabaseReference dbr = db.getReference();
                dbr.child("ChatRoom").child(NickName).child(FriendNickName).push().setValue(chatData);
                dbr.child("ChatRoom").child(FriendNickName).child(NickName).push().setValue(chatData);
                send.setText("");

            }
        });


    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.chatview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DatabaseReference dbr = db.getReference().child("ChatRoom").child(NickName).child(FriendNickName);
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatData = snapshot.getValue(ChatData.class);
                adapter.addItem(chatData);
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

    private void addItem(String nickName, String message){
        ChatData data = new ChatData(NickName, message);
        data.setNickName(nickName);
        data.setMessage(message);

        adapter.addItem(data);
    }
}