package com.example.yu_map.Activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yu_map.R;

public class AddFriendPopUpActivity extends AppCompatActivity {

    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_pop_up);

        id = (EditText)findViewById(R.id.friend_id);
    }
}