package com.example.yu_map.HyunSeol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yu_map.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComContentActivity extends AppCompatActivity {
    private TextView textView_title;
    private TextView textView_content;
    private String number;
    private String title;
    private String content;
    private Button btn_back;
    private EditText editText_comment;
    private Button btn_comment;
    private String comment;
    private String comment_num;

    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Announce> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private int init_pos;
    private int pos;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hs_activity_com_content);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_comment);
        adapter = new CommentAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btn_back = (Button)findViewById(R.id.btn_back);
        textView_title = (TextView)findViewById(R.id.textview_announce_title);
        textView_content = (TextView)findViewById(R.id.textview_announce_content);
        editText_comment = (EditText)findViewById(R.id.editText_comment);
        btn_comment = (Button)findViewById(R.id.btn_comment);

        Intent intent = getIntent();

        number = intent.getStringExtra("pos");
        title = intent.getStringExtra("title");
        textView_title.setText(title);

        //firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mref = firebaseDatabase.getReference("community").child(number);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //
                content = snapshot.child("content").getValue().toString();
                textView_content.setText(content);
                for(DataSnapshot ds : snapshot.child("comment").getChildren()){
                    pos = Integer.parseInt(ds.getKey());
                    comment_num = ds.getKey();
                    comment = ds.getValue().toString();
                    adapter.addItem(new Announce(comment_num, comment));
                    recyclerView.setAdapter(adapter);
                }
                btn_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mref.child("comment").child(String.valueOf(pos+1)).setValue(editText_comment.getText().toString());
                        Toast.makeText(ComContentActivity.this, "작성되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}