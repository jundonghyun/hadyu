package com.example.yu_map.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu_map.Lecture.Lecture;
import com.example.yu_map.Lecture.Lecture_ComputerEngineering;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.TimeTableAdapter;
import com.example.yu_map.TimeTable.List.ListDepartmentActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableActivity extends AppCompatActivity {

    private TimeTableAdapter adapter;
    private Button Selectmajor;
    public static TextView Monday[] = new TextView[13];
    public static  TextView Tuesday[] = new TextView[13];
    public static  TextView Wednesday[] = new TextView[13];
    public static  TextView Thursday[] = new TextView[13];
    public static  TextView Friday[] = new TextView[13];
    private FirebaseDatabase dbs;
    private DatabaseReference Dbr;


    private Lecture_ComputerEngineering Computer = new Lecture_ComputerEngineering();
    private String TAG = "TimeTableActivity";
    private String[] RadioItems = new String[]{"과목삭제", "출석알람"};
    private int[] selectedItem = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        Selectmajor = findViewById(R.id.SelectMajor);

        Selectmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        Get_TimeTable_MyDatabase();
    }

    public void Get_TimeTable_MyDatabase(){
        String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
        int idx = Email.indexOf("@");
        String NickName = Email.substring(0, idx);


        dbs = FirebaseDatabase.getInstance();
        Dbr = dbs.getReference().child("TimeTable").child(NickName);

        Dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String Lecture = ds.getKey();
                    String FirstDays = snapshot.child(Lecture).child("FirstDays").getValue().toString();
                    String FirstDaysStartTimeTemp = snapshot.child(Lecture).child("FirstDaysStartTime").getValue().toString();
                    int FirstDaysStartTime  = Integer.parseInt(FirstDaysStartTimeTemp.substring(0, 2)) - 8;
                    String FirstDaysFinishTimeTemp = snapshot.child(Lecture).child("FirstDaysFinishTime").getValue().toString();
                    int FirstDaysFinishTime = Integer.parseInt(FirstDaysFinishTimeTemp.substring(0, 2)) - 8;
                    String SecondDays = snapshot.child(Lecture).child("SecondDays").getValue().toString();
                    String SecondDaysStartTimeTemp = snapshot.child(Lecture).child("SecondDaysStartTime").getValue().toString();
                    int SecondDaysStartTime = Integer.parseInt(SecondDaysStartTimeTemp.substring(0, 2)) - 8;
                    String SecondDaysFinishTimeTemp = snapshot.child(Lecture).child("SecondDaysFinishTime").getValue().toString();
                    int SecondDaysFinishTime = Integer.parseInt(SecondDaysFinishTimeTemp.substring(0, 2)) - 8;
                    int Firsttemp = FirstDaysFinishTime - FirstDaysStartTime;
                    int Secondemp = SecondDaysFinishTime - SecondDaysStartTime;


                    if(FirstDays.equals("Monday")){
                        if(Firsttemp == 0){
                            Monday[FirstDaysStartTime].setWidth(15);
                            Monday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[FirstDaysStartTime].setText(Lecture);
                            Monday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else{
                            Monday[FirstDaysStartTime].setWidth(15);
                            Monday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[FirstDaysStartTime].setText(Lecture);
                            Monday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Firsttemp > 1){
                                for(int i = 0; i < Firsttemp; i++){
                                    Monday[FirstDaysStartTime + i+1].setWidth(15);
                                    Monday[FirstDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Monday[FirstDaysStartTime + i+1].setText(Lecture);
                                    Monday[FirstDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Monday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Monday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Monday[FirstDaysStartTime + Firsttemp].setText(Lecture);
                                Monday[FirstDaysStartTime + Firsttemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }
                    else if(SecondDays.equals("Monday")){
                        if(Secondemp == 0){
                            Monday[SecondDaysStartTime].setWidth(15);
                            Monday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[SecondDaysStartTime].setText(Lecture);
                            Monday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else {
                            Monday[SecondDaysStartTime].setWidth(15);
                            Monday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[SecondDaysStartTime].setText(Lecture);
                            Monday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Secondemp > 1){
                                for(int i = 0; i < Secondemp; i++){
                                    Monday[SecondDaysStartTime + i+1].setWidth(15);
                                    Monday[SecondDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Monday[SecondDaysStartTime + i+1].setText(Lecture);
                                    Monday[SecondDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Monday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Monday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Monday[SecondDaysStartTime + Secondemp].setText(Lecture);
                                Monday[SecondDaysStartTime + Secondemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }//월요일끝


                    if(FirstDays.equals("Tuesday")){
                        if(Firsttemp == 0){


                            Tuesday[FirstDaysStartTime].setWidth(15);
                            Tuesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[FirstDaysStartTime].setText(Lecture);
                            Tuesday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else{
                            Tuesday[FirstDaysStartTime].setWidth(15);
                            Tuesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[FirstDaysStartTime].setText(Lecture);
                            Tuesday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Firsttemp > 1){
                                for(int i = 0; i < Firsttemp; i++){
                                    Tuesday[FirstDaysStartTime + i+1].setWidth(15);
                                    Tuesday[FirstDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Tuesday[FirstDaysStartTime + i+1].setText(Lecture);
                                    Tuesday[FirstDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Tuesday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Tuesday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Tuesday[FirstDaysStartTime + Firsttemp].setText(Lecture);
                                Tuesday[FirstDaysStartTime + Firsttemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }
                    else if(SecondDays.equals("Tuesday")){
                        if(Secondemp == 0){
                            Tuesday[SecondDaysStartTime].setWidth(Tuesday[SecondDaysStartTime].getWidth());
                            Tuesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[SecondDaysStartTime].setText(Lecture);
                            Tuesday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else {
                            Tuesday[SecondDaysStartTime].setWidth(Tuesday[SecondDaysStartTime].getWidth());
                            Tuesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[SecondDaysStartTime].setText(Lecture);
                            Tuesday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Secondemp > 1){
                                for(int i = 0; i < Secondemp; i++){
                                    Tuesday[SecondDaysStartTime + i+1].setWidth(15);
                                    Tuesday[SecondDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Tuesday[SecondDaysStartTime + i+1].setText(Lecture);
                                    Tuesday[SecondDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Tuesday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Tuesday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Tuesday[SecondDaysStartTime + Secondemp].setText(Lecture);
                                Tuesday[SecondDaysStartTime + Secondemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    } //화요일끝


                    if(FirstDays.equals("Wednesday")){
                        if(Firsttemp == 0){
                            Wednesday[FirstDaysStartTime].setWidth(15);
                            Wednesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[FirstDaysStartTime].setText(Lecture);
                            Wednesday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else{
                            Wednesday[FirstDaysStartTime].setWidth(15);
                            Wednesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[FirstDaysStartTime].setText(Lecture);
                            Wednesday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Firsttemp > 1){
                                for(int i = 0; i < Firsttemp; i++){
                                    Wednesday[FirstDaysStartTime + i+1].setWidth(15);
                                    Wednesday[FirstDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Wednesday[FirstDaysStartTime + i+1].setText(Lecture);
                                    Wednesday[FirstDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Wednesday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Wednesday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Wednesday[FirstDaysStartTime + Firsttemp].setText(Lecture);
                                Wednesday[FirstDaysStartTime + Firsttemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }
                    else if(SecondDays.equals("Wednesday")){
                        if(Secondemp == 0){
                            Wednesday[SecondDaysStartTime].setWidth(Wednesday[SecondDaysStartTime].getWidth());
                            Wednesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[SecondDaysStartTime].setText(Lecture);
                            Wednesday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else {
                            Wednesday[SecondDaysStartTime].setWidth(Wednesday[SecondDaysStartTime].getWidth());
                            Wednesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[SecondDaysStartTime].setText(Lecture);
                            Wednesday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Secondemp > 1){
                                for(int i = 0; i < Secondemp; i++){
                                    Wednesday[SecondDaysStartTime + i+1].setWidth(15);
                                    Wednesday[SecondDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Wednesday[SecondDaysStartTime + i+1].setText(Lecture);
                                    Wednesday[SecondDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Wednesday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Wednesday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Wednesday[SecondDaysStartTime + Secondemp].setText(Lecture);
                                Wednesday[SecondDaysStartTime + Secondemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }//수요일끝

                    if(FirstDays.equals("Thursday")){
                        if(Firsttemp == 0){
                            Thursday[FirstDaysStartTime].setWidth(15);
                            Thursday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[FirstDaysStartTime].setText(Lecture);
                            Thursday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else{
                            Thursday[FirstDaysStartTime].setWidth(15);
                            Thursday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[FirstDaysStartTime].setText(Lecture);
                            Thursday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Firsttemp > 1){
                                for(int i = 0; i < Firsttemp; i++){
                                    Thursday[FirstDaysStartTime + i+1].setWidth(15);
                                    Thursday[FirstDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Thursday[FirstDaysStartTime + i+1].setText(Lecture);
                                    Thursday[FirstDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Thursday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Thursday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Thursday[FirstDaysStartTime + Firsttemp].setText(Lecture);
                                Thursday[FirstDaysStartTime + Firsttemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }
                    else if(SecondDays.equals("Thursday")){
                        if(Secondemp == 0){
                            Thursday[SecondDaysStartTime].setWidth(15);
                            Thursday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[SecondDaysStartTime].setText(Lecture);
                            Thursday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else {
                            Thursday[SecondDaysStartTime].setWidth(15);
                            Thursday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[SecondDaysStartTime].setText(Lecture);
                            Thursday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Secondemp > 1){
                                for(int i = 0; i < Secondemp; i++){
                                    Thursday[SecondDaysStartTime + i+1].setWidth(15);
                                    Thursday[SecondDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Thursday[SecondDaysStartTime + i+1].setText(Lecture);
                                    Thursday[SecondDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Thursday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Thursday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Thursday[SecondDaysStartTime + Secondemp].setText(Lecture);
                                Thursday[SecondDaysStartTime + Secondemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }//목요일끝

                    if(FirstDays.equals("Friday")){
                        if(Firsttemp == 0){
                            Friday[FirstDaysStartTime].setWidth(15);
                            Friday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[FirstDaysStartTime].setText(Lecture);
                            Friday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else{
                            Friday[FirstDaysStartTime].setWidth(15);
                            Friday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[FirstDaysStartTime].setText(Lecture);
                            Friday[FirstDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Firsttemp > 1){
                                for(int i = 0; i < Firsttemp; i++){
                                    Friday[FirstDaysStartTime + i+1].setWidth(15);
                                    Friday[FirstDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Friday[FirstDaysStartTime + i+1].setText(Lecture);
                                    Friday[FirstDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Friday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Friday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Friday[FirstDaysStartTime + Firsttemp].setText(Lecture);
                                Friday[FirstDaysStartTime + Firsttemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            }
                        }
                    }
                    else if(SecondDays.equals("Friday")){
                        if(Secondemp == 0){
                            Friday[SecondDaysStartTime].setWidth(15);
                            Friday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[SecondDaysStartTime].setText(Lecture);
                            Friday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });
                        }
                        else {
                            Friday[SecondDaysStartTime].setWidth(15);
                            Friday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[SecondDaysStartTime].setText(Lecture);
                            Friday[SecondDaysStartTime].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DeleteTimeTable(Lecture);
                                }
                            });

                            if(Secondemp > 1){
                                for(int i = 0; i < Secondemp; i++){
                                    Friday[SecondDaysStartTime + i+1].setWidth(15);
                                    Friday[SecondDaysStartTime + i+1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime + i+1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Friday[SecondDaysStartTime + i+1].setText(Lecture);
                                    Friday[SecondDaysStartTime + i+1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DeleteTimeTable(Lecture);
                                        }
                                    });
                                }
                            }
                            else{
                                Friday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Friday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Friday[SecondDaysStartTime + Secondemp].setText(Lecture);
                                Friday[SecondDaysStartTime + Secondemp].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DeleteTimeTable(Lecture);
                                    }
                                });
                            };
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onBackPressed(){
        ActivityCompat.finishAffinity(this);
        startActivity(new Intent(TimeTableActivity.this, HomeActivity.class));
    }

    private void DeleteTimeTable(String Lecture){
        String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
        int idx = Email.indexOf("@");
        String NickName = Email.substring(0, idx);
        Dbr = dbs.getReference().child("TimeTable").child(NickName).child(Lecture);

        AlertDialog.Builder builder = new AlertDialog.Builder(TimeTableActivity.this);
        builder.setTitle("기능을 선택하세요")
                .setSingleChoiceItems(RadioItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedItem[0] = which;
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dbr.removeValue();
                        if(RadioItems[selectedItem[0]].equals("과목삭제")){
                            for(int i = 1; i < Monday.length; i++){
                                if(Monday[i].getText().equals(Lecture)){
                                    Monday[i].setText("");
                                }
                            }
                            for(int i = 1; i < Tuesday.length; i++){
                                if(Tuesday[i].getText().equals(Lecture)){
                                    Tuesday[i].setText("");
                                }
                            }
                            for(int i = 1; i < Wednesday.length; i++){
                                if(Wednesday[i].getText().equals(Lecture)){
                                    Wednesday[i].setText("");
                                }
                            }
                            for(int i = 1; i < Thursday.length; i++){
                                if(Thursday[i].getText().equals(Lecture)){
                                    Thursday[i].setText("");
                                }
                            }
                            for(int i = 1; i < Friday.length; i++){
                                if(Friday[i].getText().equals(Lecture)){
                                    Friday[i].setText("");
                                }
                            }
                            Toast.makeText(TimeTableActivity.this, Lecture+"과목을 삭제했습니다", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(TimeTableActivity.this, "출석알림 선택", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TimeTableActivity.this, "취소하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create();
        builder.show();
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