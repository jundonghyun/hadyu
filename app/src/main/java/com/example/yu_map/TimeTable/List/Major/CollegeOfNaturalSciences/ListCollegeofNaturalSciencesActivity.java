package com.example.yu_map.TimeTable.List.Major.CollegeOfNaturalSciences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;

import java.util.List;

public class ListCollegeofNaturalSciencesActivity extends AppCompatActivity {

    private ListCollegeofNaturalSciencesAdapter adapter;
    private List<String> College_of_Mechanical_and_IT_Engineering;
    private List<String> College_of_Natural_Science;

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

        adapter = new ListCollegeofNaturalSciencesAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){

        addItem("수학과");
        addItem("통계학과");
        addItem("물리학과");
        addItem("화학생화학부");
        addItem("생명과학과");

        adapter.notifyDataSetChanged();
    }

    public void addItem(String st){
        ListCollegeofNaturalSciencesViewItem data = new ListCollegeofNaturalSciencesViewItem();
        data.setMajorName(st);

        adapter.addItem(data);
    }
}