package com.example.yu_map.TimeTable.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yu_map.R;

import org.w3c.dom.CDATASection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListDepartmentActivity extends AppCompatActivity {

    private ListDepartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_department);

       init();

       getData();

    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.DepartmentList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ListDepartmentAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        addItem("자연과학대학");
        addItem("기계IT대학");

        adapter.notifyDataSetChanged();

    }

    public void addItem(String st){
        ListDepartmentViewItem data = new ListDepartmentViewItem();
        data.setDepartmentname(st);

        adapter.addItem(data);
    }
}