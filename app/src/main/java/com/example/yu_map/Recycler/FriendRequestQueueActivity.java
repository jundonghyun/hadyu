package com.example.yu_map.Recycler;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class FriendRequestQueueActivity extends AppCompatActivity {

    private FriendRequestQueueAdapter adapter;
    private List<String> Id;
    private List<String> content;
    private List<Integer> resID;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private String TAG = "FriendRequestQueueActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_queue);


        init();

        getData();
    }




    private void init(){
        RecyclerView recyclerView = findViewById(R.id.Friend_Request_Queue_View);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new FriendRequestQueueAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        int idx = Email.indexOf("@"); /* 로그인한 ID */
        final String NickName = Email.substring(0, idx);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        final DatabaseReference addFriend = Fdb.getReference().child("Waiting Friend Request").child(NickName);

        addFriend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.getValue(String.class);
                    Id = Arrays.asList(id);
                    content = Arrays.asList("친구요청을 수락하려면 누르세요");
                    resID = Arrays.asList(R.mipmap.ic_launcher);

                    for (int i = 0; i < Id.size(); i++) {
                        FriendData data = new FriendData();
                        data.setTitle(Id.get(i));
                        data.setContent(content.get(i));
                        data.setResId(resID.get(i));

                        adapter.addItem(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}