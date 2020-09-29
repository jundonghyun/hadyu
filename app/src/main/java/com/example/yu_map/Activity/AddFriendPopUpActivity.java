package com.example.yu_map.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.FriendActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class AddFriendPopUpActivity extends AppCompatActivity {

    private final String TAG = "AddFriendPopUpActivity";
    public static Context context;
    EditText id;
    Button btn;
    String idTemp;
    public String Use_FriendActivity_Email;


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
        Use_FriendActivity_Email = idTemp;
        context = this;

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("User")
                .whereEqualTo("Email", idTemp)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        /* DB에 이메일이 없을때 */
                        if(e != null || queryDocumentSnapshots.size() == 0){
                            Log.d(TAG, "친구추가 오류");
                            Toast.makeText(AddFriendPopUpActivity.this, "친구를 찾을 수 없습니다", Toast.LENGTH_LONG).show();
                        }
                        /* DB에 이메일이 있을 경우 */
                        else if(queryDocumentSnapshots != null && db.collection("User").whereEqualTo("Email", idTemp) != null){
                            Log.d(TAG, "친구추가 완료");
                            startActivity(new Intent(AddFriendPopUpActivity.this, FriendActivity.class));
                        }
                    }
                });

    }

}