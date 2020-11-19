package com.example.yu_map.TaxiCarPool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaxiCarPoolUserWaitActivity extends AppCompatActivity {

    TextView start, finish, leader, user1, user2;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    int idx = Email.indexOf("@");
    String NickName = Email.substring(0, idx);
    String Time = "1000";
    String id;
    String enterid;
    String key, otherkey;


    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_car_pool_user_wait);

        start = findViewById(R.id.taxicarpoolStartLocation);
        finish = findViewById(R.id.taxicarpoolFinishLocation);
        leader = findViewById(R.id.CarpoolLeader);
        user1 = findViewById(R.id.CarpoolParticipants1);
        user2 = findViewById(R.id.CarpoolParticipants2);

        Intent intent = getIntent();
        intent.getExtras();
        int switchnum = 0;

        if(intent.getExtras().getString("makerid") != null){
            switchnum = 1;
        }
        else if(intent.getExtras().getString("entererid") != null){
            switchnum = 2;
        }

        switch (switchnum){
            case 1:
                id = intent.getExtras().getString("makerid");
                getPosition();
                break;

            case 2:
                enterid = intent.getExtras().getString("entererid");
                continuePosition();
                break;
        }


        /*if(TextUtils.isEmpty(leader.getText())){
            leader.setText("1번 사용자: " + NickName);
        }
        else{
            if(TextUtils.isEmpty(user1.getText())){
                user1.setText("2번 사용자: " + NickName);
            }
            else{
                user2.setText("3번 사용자: "+ NickName);
            }
        }*/
    }

    private void continuePosition(){

        DatabaseReference dbr = db.getReference().child("TaxiCarPool");

        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("makerID").getValue().equals(enterid)){
                        otherkey = ds.getKey();
                        start.setText("출발지: " + ds.child("start").getValue().toString());
                        finish.setText("도착지: "+ds.child("end").getValue().toString());

                        leader.setText("1번 사용자: " + ds.child("FirstUser").getValue());

                        if(ds.child("SecondUser") == null){
                            dbr.child(otherkey).child("SecondUser").setValue(enterid);
                            user1.setText("2번 사용자: " + NickName);
                        }
                        else{
                            dbr.child(otherkey).child("ThirdUser").setValue(enterid);
                            user2.setText("3번 사용자: "+ NickName);
                        }
                    }
                }
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

    private void getPosition(){


        DatabaseReference dbr = db.getReference().child("TaxiCarPool");

        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("makerID").getValue().equals(id)){
                        key = ds.getKey();
                        start.setText("출발지: " + ds.child("start").getValue().toString());
                        finish.setText("도착지: "+ds.child("end").getValue().toString());
                        dbr.child(key).child("FirstUser").setValue(id);
                        leader.setText("1번 사용자: " + NickName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}