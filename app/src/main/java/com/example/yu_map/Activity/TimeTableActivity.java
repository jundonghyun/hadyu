package com.example.yu_map.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yu_map.Lecture.Lecture;
import com.example.yu_map.Lecture.Lecture_ComputerEngineering;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.TimeTableAdapter;
import com.example.yu_map.TimeTable.List.ListDepartmentActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    private TimeTableAdapter adapter;
    private Button Selectmajor;
    public static com.example.yu_map.Activity.AutoResizeTextView Monday[] = new AutoResizeTextView[13], Tuesday[] = new AutoResizeTextView[13]
            , Wednesday[] = new AutoResizeTextView[13], Thursday[] = new AutoResizeTextView[13], Friday[] = new AutoResizeTextView[13];
    private Lecture_ComputerEngineering Computer = new Lecture_ComputerEngineering();
    private TextView SelectedDep;




    ArrayList<Lecture> lecture = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Selectmajor = findViewById(R.id.SelectMajor);

        Selectmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeTableActivity.this, ListDepartmentActivity.class));
            }
        });

        init();

        getData();

        Monday[1] = findViewById(R.id.monday1);
        Monday[2] = findViewById(R.id.monday2);
        Monday[3] = findViewById(R.id.monday3);
        Monday[4] = findViewById(R.id.monday4);
        Monday[5] = findViewById(R.id.monday5);
        Monday[6] = findViewById(R.id.monday6);
        Monday[7] = findViewById(R.id.monday7);
        Monday[8] = findViewById(R.id.monday8);
        Monday[9] = findViewById(R.id.monday9);
        Monday[10] = findViewById(R.id.monday10);
        Monday[11] = findViewById(R.id.monday11);
        Monday[12] = findViewById(R.id.monday12);

        Tuesday[1] = findViewById(R.id.tuesday1);
        Tuesday[2] = findViewById(R.id.tuesday2);
        Tuesday[3] = findViewById(R.id.tuesday3);
        Tuesday[4] = findViewById(R.id.tuesday4);
        Tuesday[5] = findViewById(R.id.tuesday5);
        Tuesday[6] = findViewById(R.id.tuesday6);
        Tuesday[7] = findViewById(R.id.tuesday7);
        Tuesday[8] = findViewById(R.id.tuesday8);
        Tuesday[9] = findViewById(R.id.tuesday9);
        Tuesday[10] = findViewById(R.id.tuesday10);
        Tuesday[11] = findViewById(R.id.tuesday11);
        Tuesday[12] = findViewById(R.id.tuesday12);

        Wednesday[1] = findViewById(R.id.wednesday1);
        Wednesday[2] = findViewById(R.id.wednesday2);
        Wednesday[3] = findViewById(R.id.wednesday3);
        Wednesday[4] = findViewById(R.id.wednesday4);
        Wednesday[5] = findViewById(R.id.wednesday5);
        Wednesday[6] = findViewById(R.id.wednesday6);
        Wednesday[7] = findViewById(R.id.wednesday7);
        Wednesday[8] = findViewById(R.id.wednesday8);
        Wednesday[9] = findViewById(R.id.wednesday9);
        Wednesday[10] = findViewById(R.id.wednesday10);
        Wednesday[11] = findViewById(R.id.wednesday11);
        Wednesday[12] = findViewById(R.id.wednesday12);

        Thursday[1] = findViewById(R.id.thursday1);
        Thursday[2] = findViewById(R.id.thursday2);
        Thursday[3] = findViewById(R.id.thursday3);
        Thursday[4] = findViewById(R.id.thursday4);
        Thursday[5] = findViewById(R.id.thursday5);
        Thursday[6] = findViewById(R.id.thursday6);
        Thursday[7] = findViewById(R.id.thursday7);
        Thursday[8] = findViewById(R.id.thursday8);
        Thursday[9] = findViewById(R.id.thursday9);
        Thursday[10] = findViewById(R.id.thursday10);
        Thursday[11] = findViewById(R.id.thursday11);
        Thursday[12] = findViewById(R.id.thursday12);

        Friday[1] = findViewById(R.id.friday1);
        Friday[2] = findViewById(R.id.friday2);
        Friday[3] = findViewById(R.id.friday3);
        Friday[4] = findViewById(R.id.friday4);
        Friday[5] = findViewById(R.id.friday5);
        Friday[6] = findViewById(R.id.friday6);
        Friday[7] = findViewById(R.id.friday7);
        Friday[8] = findViewById(R.id.friday8);
        Friday[9] = findViewById(R.id.friday9);
        Friday[10] = findViewById(R.id.friday10);
        Friday[11] = findViewById(R.id.friday11);
        Friday[12] = findViewById(R.id.friday12);
    }

    public void init(){
        RecyclerView recyclerView = findViewById(R.id.AddTimeTable);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TimeTableAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        final String Grade;
        final String LectureName;
        final String FirstDays;
        final String SecondDays;
        final String Professor;
        final String FirstDaysStartTime;
        final String FirstDaysFinishTime;
        final String SecondDaysStartTime;
        final String SecondDaysFinishTime;
        final String Required;
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu2);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.MakeTimeTable:

        }

        return false;
    }

}