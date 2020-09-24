package com.example.yu_map.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AddFriendPopUpActivity extends AppCompatActivity {

    private final String TAG = "AddFriendPopUpActivity";
    EditText id;
    Button btn;
    String idTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_pop_up);

        id = (EditText)findViewById(R.id.friend_id);
        btn = (Button)findViewById(R.id.OK);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriend();


                startActivity(new Intent(AddFriendPopUpActivity.this,AddFriendActivity.class));
            }
        });
    }

    private void AddFriend() {

        idTemp = id.getText().toString();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("User");
        Query query = col.whereEqualTo("Email", idTemp);

        db.collection("User")
                .whereEqualTo("Email", idTemp)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(AddFriendPopUpActivity.this, "친구추가 완료", Toast.LENGTH_LONG).show();

                            }
                        }
                        else{
                            Toast.makeText(AddFriendPopUpActivity.this, "사용자를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Error");
                        }
                    }
                });
    }

}