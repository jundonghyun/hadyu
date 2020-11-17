package com.example.yu_map.TaxiCarPool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
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

        getPosition();


        if(TextUtils.isEmpty(leader.getText())){
            leader.setText("1번 사용자: " + NickName);
        }
        else{
            if(TextUtils.isEmpty(user1.getText())){
                user1.setText("2번 사용자: " + NickName);
            }
            else{
                user2.setText("3번 사용자: "+ NickName);
            }
        }
    }

    private void getPosition(){
        DatabaseReference dbr = db.getReference().child("TaxiCarPool").child(NickName);
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                start.setText("출발지: " + snapshot.child("출발지").getValue().toString());
                finish.setText("도착지: "+snapshot.child("도착지").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}