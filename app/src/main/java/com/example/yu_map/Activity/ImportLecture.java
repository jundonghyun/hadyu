package com.example.yu_map.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yu_map.Lecture.Lecture;
import com.example.yu_map.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ImportLecture extends AppCompatActivity {

    private String[] Monday[], Tuesday[], Wednesday[], Thursday[], Friday[];

    private String Grade;
    private String LectureName;
    private String FirstDays;
    private String SecondDays;
    private String Professor;
    private String FirstDaysStartTime;
    private String FirstDaysFinishTime;
    private String SecondDaysStartTime;
    private String SecondDaysFinishTime;
    private String Required;

    FirebaseDatabase fdb = FirebaseDatabase.getInstance();


    ArrayList<Lecture> Academic_English = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_lecture);


        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "애런애덜즈먼", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "데보라마제룰레", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "질리안듀베이", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "그렉레이노", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "토마스듀버네이", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "에릭쉬나이더", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "제니퍼데니스", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "윌리암알지오", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));
        Academic_English.add(new Lecture("1","교양필수",  "Academic_English", "엘라도런", "Tuesday", "1700", "1750", "Thursday", "1700", "1750"));




        for(int i = 0; i < Academic_English.size(); i++){
            Grade = Academic_English.get(i).getGrade();
            LectureName = Academic_English.get(i).getLectureName();
            Required = Academic_English.get(i).getRequired();
            Professor = Academic_English.get(i).getProfessor();
            FirstDays = Academic_English.get(i).getFirstDays();
            FirstDaysStartTime = Academic_English.get(i).getFirstDaysStartTime();
            FirstDaysFinishTime = Academic_English.get(i).getFirstDaysFinishTime();
            SecondDays = Academic_English.get(i).getSecondDays();
            SecondDaysStartTime = Academic_English.get(i).getSecondDaysStartTime();
            SecondDaysFinishTime = Academic_English.get(i).getSecondDaysFinishTime();


            DatabaseReference Lecture = fdb.getReference().child("Lecture").child("Major").child("컴퓨터공학과").child("Grade").child(Grade)
                    .child(LectureName + "+" + Professor);

            Lecture.child("이수구분").setValue(Required);
            Lecture.child("FirstDays").setValue(FirstDays);
            Lecture.child("FirstDaysStartTime").setValue(FirstDaysStartTime);
            Lecture.child("FirstDaysFinishTime").setValue(FirstDaysFinishTime);
            Lecture.child("SecondDays").setValue(SecondDays);
            Lecture.child("SecondDaysStartTime").setValue(SecondDaysStartTime);
            Lecture.child("SecondDaysFinishTime").setValue(SecondDaysFinishTime);


        }


    }
}