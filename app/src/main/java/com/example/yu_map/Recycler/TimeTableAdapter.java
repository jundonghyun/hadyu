package com.example.yu_map.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Lecture.Lecture;
import com.example.yu_map.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TimeTableAdapter  extends RecyclerView.Adapter<TimeTabeItemViewHolder> {

    public static Context context;
    private ArrayList<Lecture> listData = new ArrayList<Lecture>();


    @NonNull
    @Override
    public TimeTabeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlecture_item, parent, false);
        this.context = parent.getContext();

        return new TimeTabeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTabeItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public String getItem(Lecture data){
        return data.getLectureName();
    }

    void addItem(Lecture data){
        listData.add(data);
    }
}

class TimeTabeItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView LecName;
    private TextView LecProfessor;
    private TextView LecTime;
    private TextView LecGrade;
    private TextView LecRequire;

    private Lecture lecture;
    FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
    final DatabaseReference lec = Fdb.getReference().child("Lecture");

    TimeTabeItemViewHolder(View itemView) {
        super(itemView);
        LecName = itemView.findViewById(R.id.LectureName);
        LecProfessor = itemView.findViewById(R.id.LectureProfessorName);
        LecTime = itemView.findViewById(R.id.LectureTime);
        LecGrade = itemView.findViewById(R.id.LectureGrade);
        LecRequire = itemView.findViewById(R.id.LectureRequire);

    }


    void onBind(Lecture lecture) {
        this.lecture = lecture;
        LecName.setText(lecture.getLectureName());
        LecProfessor.setText(lecture.getProfessor());
        LecTime.setText(lecture.getFirstDays() + lecture.getFirstDaysStartTime() + "~" + lecture.getFirstDaysFinishTime()
                + "," + lecture.getSecondDays() + lecture.getSecondDaysStartTime() + "~" + lecture.getSecondDaysFinishTime());
        LecGrade.setText(lecture.getGrade());
        LecRequire.setText(lecture.getRequired());

        LecName.setOnClickListener(this);
        LecProfessor.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int pos = getAdapterPosition();
        if(pos!= RecyclerView.NO_POSITION){

        }
    }

}
