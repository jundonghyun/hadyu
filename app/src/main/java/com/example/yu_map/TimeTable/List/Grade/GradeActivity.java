package com.example.yu_map.TimeTable.List.Grade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.yu_map.R;

public class GradeActivity extends AppCompatActivity {

    private GradeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade1);

        init();

        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.GradeList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new GradeAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        addItem("1");
        addItem("2");
        addItem("3");
        addItem("4");

        adapter.notifyDataSetChanged();
    }

    private void addItem(String s){
        GradeViewItem data = new GradeViewItem();
        data.setGrade(s);

        adapter.addItem(data);
    }
}