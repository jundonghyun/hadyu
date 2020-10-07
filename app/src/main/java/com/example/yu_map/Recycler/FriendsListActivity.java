package com.example.yu_map.Recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.yu_map.Activity.AddFriendPopUpActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.IDN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {

    public FriendsListAdapter adapter;
    private List<String> Id;
    private List<String> content;
    private List<Integer> resID;

    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private String TAG = "FriendsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.yu_map.R.layout.activity_friends_list);


        init();

        getData();
    }


    private void init(){
        RecyclerView recyclerView = findViewById(R.id.FriendsView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new FriendsListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        int idx = Email.indexOf("@"); /* 로그인한 ID */
        final String NickName = Email.substring(0, idx);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        final DatabaseReference addFriend = Fdb.getReference().child("FriendList").child(NickName);

        addFriend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String id = ds.getValue(String.class);
                    Id = Arrays.asList(id);
                    content = Arrays.asList("친구입니다");
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

        /*List<String> listTitle = Arrays.asList(Email);
        List<String> listContent = Arrays.asList("친구입니다");
        List<Integer> listResId = Arrays.asList( R.mipmap.ic_launcher);

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            FriendData data = new FriendData();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();*/


    }
}