package com.example.yu_map.Recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class FriendsListActivity extends AppCompatActivity{

    private FriendsListAdapter adapter;
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

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        final DatabaseReference addFriend = Fdb.getReference().child("FriendList").child(NickName);

        final DatabaseReference connectRegerence = db.getReference().child("connection");


        addFriend.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String id = ds.getValue().toString();

                    connectRegerence.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                if(ds.getValue(Boolean.class) == true){
                                    FriendData OnlineData = new FriendData();

                                    String online = ds.getKey().toString();

                                    if(id.equals(online)){
                                        Id = Arrays.asList(id);
                                        content = Arrays.asList("온라인");
                                        resID = Arrays.asList(R.drawable.ic_baseline_brightness_online_24);

                                        for(int i = 0; i < Id.size(); i++){
                                            OnlineData.setTitle(Id.get(i));
                                            OnlineData.setContent(content.get(i));
                                            OnlineData.setResId(resID.get(i));

                                            adapter.addItem(OnlineData);
                                        }
                                    }
                                }
                                else{
                                    FriendData Offlinedata = new FriendData();

                                    String offline = ds.getKey().toString();

                                    if(id.equals(offline)){
                                        Id = Arrays.asList(id);
                                        content = Arrays.asList("오프라인");
                                        resID = Arrays.asList(R.drawable.ic_baseline_brightness_offline_24);

                                        for(int i = 0; i < Id.size(); i++){
                                            Offlinedata.setTitle(Id.get(i));
                                            Offlinedata.setContent(content.get(i));
                                            Offlinedata.setResId(resID.get(i));

                                            adapter.addItem(Offlinedata);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friendlistmenu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.AddFriendButton:
                startActivity(new Intent(FriendsListActivity.this, AddFriendPopUpActivity.class));


        }
        return super.onOptionsItemSelected(item);
    }
}