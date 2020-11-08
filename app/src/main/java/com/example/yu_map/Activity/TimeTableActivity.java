package com.example.yu_map.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
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
import com.example.yu_map.TimeTable.List.ShowLecture.ShowLectureAdapter;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    private TimeTableAdapter adapter;
    private Button Selectmajor;
    public static TextView Monday[] = new TextView[13];
    public static  TextView Tuesday[] = new TextView[13];
    public static  TextView Wednesday[] = new TextView[13];
    public static  TextView Thursday[] = new TextView[13];
    public static  TextView Friday[] = new TextView[13];

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

        if(ShowLectureAdapter.GlobalFirstDay != null){
            if(ShowLectureAdapter.GlobalFirstDay.equals("Monday")){
                if(ShowLectureAdapter.Firsttemp == 0){
                    Monday[ShowLectureAdapter.GlobalFirstTime].setWidth(Monday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Monday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else{
                    Monday[ShowLectureAdapter.GlobalFirstTime].setWidth(Monday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Monday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Monday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setWidth(Monday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].getWidth());
                    Monday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
            else if(ShowLectureAdapter.GlobalSecondDay.equals("Monday")){
                if(ShowLectureAdapter.Secondemp == 0){
                    Monday[ShowLectureAdapter.GlobalSecondTime].setWidth(Monday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Monday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else {
                    Monday[ShowLectureAdapter.GlobalSecondTime].setWidth(Monday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Monday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setWidth(Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].getWidth());
                    Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Monday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            } // 월요일 끝
            if(ShowLectureAdapter.GlobalFirstDay.equals("Tuesday")){
                if(ShowLectureAdapter.Firsttemp == 0){
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setWidth(Tuesday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else{
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setWidth(Tuesday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Tuesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setWidth(Tuesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
            else if(ShowLectureAdapter.GlobalSecondDay.equals("Tuesday")){
                if(ShowLectureAdapter.Secondemp == 0){
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setWidth(Tuesday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else {
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setWidth(Tuesday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setWidth(Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].getWidth());
                    Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Tuesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }// 화요일 끝
            if(ShowLectureAdapter.GlobalFirstDay.equals("Wednesday")){
                if(ShowLectureAdapter.Firsttemp == 0){
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setWidth(Wednesday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else{
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setWidth(Wednesday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Wednesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setWidth(Wednesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
            else if(ShowLectureAdapter.GlobalSecondDay.equals("Wednesday")){
                if(ShowLectureAdapter.Secondemp == 0){
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setWidth(Wednesday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else {
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setWidth(Wednesday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setWidth(Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].getWidth());
                    Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Wednesday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            } // 수요일 끝
            if(ShowLectureAdapter.GlobalFirstDay.equals("Thursday")){
                if(ShowLectureAdapter.Firsttemp == 0){
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setWidth(Thursday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else{
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setWidth(Thursday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Thursday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setWidth(Wednesday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].getWidth());
                    Thursday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
            else if(ShowLectureAdapter.GlobalSecondDay.equals("Thursday")){
                if(ShowLectureAdapter.Secondemp == 0){
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setWidth(Thursday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else {
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setWidth(Thursday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setWidth(Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].getWidth());
                    Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Thursday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }// 목요일 끝
            if(ShowLectureAdapter.GlobalFirstDay.equals("Friday")){
                if(ShowLectureAdapter.Firsttemp == 0){
                    Friday[ShowLectureAdapter.GlobalFirstTime].setWidth(Friday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Friday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else{
                    Friday[ShowLectureAdapter.GlobalFirstTime].setWidth(Friday[ShowLectureAdapter.GlobalFirstTime].getWidth());
                    Friday[ShowLectureAdapter.GlobalFirstTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalFirstTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalFirstTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Friday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setWidth(Friday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].getWidth());
                    Friday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalFirstTime + ShowLectureAdapter.Firsttemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
            else if(ShowLectureAdapter.GlobalSecondDay.equals("Friday")){
                if(ShowLectureAdapter.Secondemp == 0){
                    Friday[ShowLectureAdapter.GlobalSecondTime].setWidth(Friday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Friday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);
                }
                else {
                    Friday[ShowLectureAdapter.GlobalSecondTime].setWidth(Friday[ShowLectureAdapter.GlobalSecondTime].getWidth());
                    Friday[ShowLectureAdapter.GlobalSecondTime].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalSecondTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalSecondTime].setText(ShowLectureAdapter.GlobalLectureName);

                    Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setWidth(Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].getWidth());
                    Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setHeight(15);
                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    Friday[ShowLectureAdapter.GlobalSecondTime + ShowLectureAdapter.Secondemp].setText(ShowLectureAdapter.GlobalLectureName);
                }
            }
        }
        else{
            return;
        }
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