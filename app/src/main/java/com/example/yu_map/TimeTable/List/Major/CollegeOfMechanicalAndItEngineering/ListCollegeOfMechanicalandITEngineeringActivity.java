package com.example.yu_map.TimeTable.List.Major.CollegeOfMechanicalAndItEngineering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yu_map.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCollegeOfMechanicalandITEngineeringActivity extends AppCompatActivity {

    private ListCollegeOfMechanicalandITEngineeringAdapter adapter;
    private ArrayList<ListCollegeOfMechanicalandITEngineeringViewItem> mlist = new ArrayList<ListCollegeOfMechanicalandITEngineeringViewItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_major);

        init();

        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.MajorList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListCollegeOfMechanicalandITEngineeringAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        List<String> listTitle = Arrays.asList();

        addItem("컴퓨터공학과");
        addItem("기계공학부");
        addItem("전기공학과");
        addItem("전자공학과");
        addItem("정보통신공학과");
        addItem("로봇기계공학과");
        addItem("전기공학과");

        adapter.notifyDataSetChanged();
    }

    public void addItem(String st){
        ListCollegeOfMechanicalandITEngineeringViewItem data = new ListCollegeOfMechanicalandITEngineeringViewItem();
        data.setMajorName(st);

        adapter.addItem(data);
    }
}