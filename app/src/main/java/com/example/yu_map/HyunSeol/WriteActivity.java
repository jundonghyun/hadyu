package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import android.widget.EditText;
import android.widget.Toast;

public class WriteActivity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_save;

    private EditText editText_title;
    private EditText editText_content;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_write);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_save = (Button)findViewById(R.id.btn_save);
        editText_title = (EditText)findViewById(R.id.editText_title);
        editText_content = (EditText)findViewById(R.id.editText_content);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mref = firebaseDatabase.getReference("community");
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            pos = Integer.parseInt(ds.getKey());
                        }
                        // databaseReference.child("10").child("title").push().setValue(string_title.toString());
                        mref.child(String.valueOf(pos+1)).child("title").setValue(editText_title.getText().toString());
                        mref.child(String.valueOf(pos+1)).child("content").setValue(editText_content.getText().toString());
                        Toast.makeText(WriteActivity.this, "데이터 저장 완료", Toast.LENGTH_SHORT).show();
                        //for(DataSnapshot ds : snapshot.getChildren()){
                        //content = snapshot.getValue().toString();
                        //textView_content.setText(content);
                        Intent intent = new Intent(v.getContext(), CommunityActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommunityActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}