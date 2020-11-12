package com.example.yu_map.TimeTable.List.ShowLecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.yu_map.R;
import com.example.yu_map.TimeTable.List.Grade.GradeAdapter;
import com.example.yu_map.TimeTable.List.Major.CollegeOfMechanicalAndItEngineering.ListCollegeOfMechanicalandITEngineeringAdapter;
import com.google.api.LogDescriptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.Collection;


public class ShowLectureActivity extends AppCompatActivity {
    private ShowLectureAdapter adapter;
    public static String FinalMajor;
    private String Grade = GradeAdapter.grade;
    private String TAG = "ShowLectureActivity";
    private String ProfessorName, Require, Lecture, FirstDays, FirstDaysStartTime, FirstDaysFinishTime, SecondDays, SecondDaysStartTime,  SecondDaysFinishTime, TotalSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lecture);

        init();

        getData();

    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.LectureList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ShowLectureAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        if(adapter.getItemCount() == 0){
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference GetLecture = db.getReference().child("Lecture")
                    .child("Major").child(FinalMajor).child("Grade").child(Grade);
            GetLecture.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Lecture = ds.getKey();
                        int idx = Lecture.indexOf("+");
                        final String lec = Lecture.substring(0, idx);
                        ProfessorName = Lecture.substring(idx + 1, Lecture.length());

                        //GetLecture.child(Lecture)
                        FirstDays = snapshot.child(Lecture).child("FirstDays").getValue().toString();
                        FirstDaysStartTime = snapshot.child(Lecture).child("FirstDaysStartTime").getValue().toString();
                        FirstDaysFinishTime = snapshot.child(Lecture).child("FirstDaysFinishTime").getValue().toString();
                        SecondDays = snapshot.child(Lecture).child("SecondDays").getValue().toString();
                        SecondDaysStartTime = snapshot.child(Lecture).child("SecondDaysStartTime").getValue().toString();
                        SecondDaysFinishTime = snapshot.child(Lecture).child("SecondDaysFinishTime").getValue().toString();
                        Require = snapshot.child(Lecture).child("이수구분").getValue().toString();
                        TotalSchedule = FirstDays + "" + FirstDaysStartTime + "-" + FirstDaysFinishTime + "," + SecondDays + "" + SecondDaysStartTime + "-" + SecondDaysFinishTime;
                        addItem(Grade, lec, ProfessorName, FirstDays, FirstDaysStartTime,
                                FirstDaysFinishTime, SecondDays, SecondDaysStartTime, SecondDaysFinishTime, Require, TotalSchedule);

                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            return;
        }
    }

    private void addItem(String grade, String lecturename, String professor, String FirstDays, String FirstDaysStartTime
    , String FirstDaysFinishTime, String SecondDays, String SecondDaysStartTime, String SecondDaysFinishTime, String require, String TotalSchedule){

        ShowLectureViewItem data = new ShowLectureViewItem();

        data.setLectureGrade(grade+"학년");
        data.setLectureName(lecturename);
        data.setLectureProfessorName(professor);
        data.setFirstDays(FirstDays);
        data.setFirstDaysStartTime(FirstDaysStartTime);
        data.setFirstDaysFinishTime(FirstDaysFinishTime);
        data.setSecondDays(SecondDays);
        data.setSecondDaysStartTime(SecondDaysStartTime);
        data.setSecondDaysFinishTime(SecondDaysFinishTime);
        data.setLectureRequire(require);
        data.setTotalSchedule(TotalSchedule);

        adapter.addItem(data);
    }
}