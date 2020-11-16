package com.example.yu_map.TimeTable.List.ShowLecture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.HomeActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.Activity.TimeTableActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class ShowLectureAdapter extends RecyclerView.Adapter<ShowLectureAdapter.ViewHolder> {
    private static Context context;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    public static ArrayList<ShowLectureViewItem> mData = new ArrayList<ShowLectureViewItem>();
    public static String GlobalFirstDay = null, GlobalSecondDay = null;
    public static String GlobalLectureName = null;
    public static int GlobalFirstTime = 0, Firsttemp = 0, GlobalSecondTime = 0, Secondemp = 0;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlecture_item, parent, false);
        this.context = parent.getContext();

        return new ShowLectureAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addItem(ShowLectureViewItem data) {
        mData.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView LecName;
        TextView LecProName;
        TextView LecTime;
        TextView LecGrade;
        TextView LecRequire;
        private ShowLectureViewItem data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            LecName = itemView.findViewById(R.id.LectureName);
            LecProName = itemView.findViewById(R.id.LectureProfessorName);
            LecTime = itemView.findViewById(R.id.LectureTime);
            LecGrade = itemView.findViewById(R.id.LectureGrade);
            LecRequire = itemView.findViewById(R.id.LectureRequire);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
                    int idx = Email.indexOf("@");
                    String NickName = Email.substring(0, idx);
                    int pos = getAdapterPosition();

                    DatabaseReference Lecture = db.getReference().child("TimeTable").child(NickName);

                    if(pos != RecyclerView.NO_POSITION){

                        int FirstDayStart, FirstDayFinish, SecondDayStart, SecondDayFinsih;
                        String FirstDay, SecondDay;
                        FirstDay = data.getFirstDays();
                        FirstDayStart = Integer.parseInt(data.getFirstDaysStartTime().substring(0, 2)) - 8;
                        FirstDayFinish = Integer.parseInt(data.getFirstDaysFinishTime().substring(0, 2)) - 8;
                        SecondDay = data.getSecondDays();
                        SecondDayStart = Integer.parseInt(data.getSecondDaysStartTime().substring(0, 2)) - 8;
                        SecondDayFinsih = Integer.parseInt(data.getSecondDaysFinishTime().substring(0, 2)) - 8;

                        if(FirstDay.equals("Monday")){
                            if(TimeTableActivity.Monday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Monday[FirstDayFinish].getText().toString().equals("")){
                                GlobalFirstDay = "Monday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());


                                if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                    Firsttemp = FirstDayFinish - FirstDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(SecondDay.equals("Monday")){
                            if(TimeTableActivity.Monday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Monday[SecondDayFinsih].getText().toString().equals("")){
                                GlobalSecondDay = "Monday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                    Secondemp = SecondDayFinsih - SecondDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                            }
                        } // 월요일 끝
                        if(FirstDay.equals("Tuesday")){
                            if(TimeTableActivity.Tuesday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Tuesday[FirstDayFinish].getText().toString().equals("")){
                                GlobalFirstDay = "Tuesday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                    Firsttemp = FirstDayFinish - FirstDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else if(SecondDay.equals("Tuesday")){
                            if(TimeTableActivity.Tuesday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Tuesday[SecondDayFinsih].getText().toString().equals("")){
                                GlobalSecondDay = "Tuesday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                    Secondemp = SecondDayFinsih - SecondDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } // 화요일 끝
                        if(FirstDay.equals("Wednesday")){
                            if(TimeTableActivity.Wednesday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Wednesday[FirstDayFinish].getText().toString().equals("")){
                                GlobalFirstDay = "Wednesday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;                        }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                    Firsttemp = FirstDayFinish - FirstDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else if(SecondDay.equals("Wednesday")){
                            if(TimeTableActivity.Wednesday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Wednesday[SecondDayFinsih].getText().toString().equals("")){
                                GlobalSecondDay = "Wednesday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;                        }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                    Secondemp = SecondDayFinsih - SecondDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                            }
                        }//수요일 끝
                        if(FirstDay.equals("Thursday")){
                            if(TimeTableActivity.Thursday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Thursday[FirstDayFinish].getText().toString().equals("")){
                                GlobalFirstDay = "Thursday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;                        }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                    Firsttemp = FirstDayFinish - FirstDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(SecondDay.equals("Thursday")){
                            if(TimeTableActivity.Thursday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Thursday[SecondDayFinsih].getText().toString().equals("")){
                                GlobalSecondDay = "Thursday";


                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;                        }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                    Secondemp = SecondDayFinsih - SecondDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                            }
                        }//목요일 끝
                        if(FirstDay.equals("Friday")){
                            if(TimeTableActivity.Friday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Friday[FirstDayFinish].getText().toString().equals("")){
                                GlobalFirstDay = "Friday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;                        }
                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalFirstTime = FirstDayStart;
                                    Firsttemp = FirstDayFinish - FirstDayStart;
                                }
                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(SecondDay.equals("Friday")){
                            if(TimeTableActivity.Friday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Friday[SecondDayFinsih].getText().toString().equals("")){
                                GlobalSecondDay = "Friday";

                                Lecture.child(data.getLectureName()).child("FirstDays").setValue(data.getFirstDays());
                                Lecture.child(data.getLectureName()).child("FirstDaysStartTime").setValue(data.getFirstDaysStartTime());
                                Lecture.child(data.getLectureName()).child("FirstDaysFinishTime").setValue(data.getFirstDaysFinishTime());
                                Lecture.child(data.getLectureName()).child("SecondDays").setValue(data.getSecondDays());
                                Lecture.child(data.getLectureName()).child("SecondDaysStartTime").setValue(data.getSecondDaysStartTime());
                                Lecture.child(data.getLectureName()).child("SecondDaysFinishTime").setValue(data.getSecondDaysFinishTime());

                                if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;                        }

                                else{
                                    GlobalLectureName = data.getLectureName();
                                    GlobalSecondTime = SecondDayStart;
                                    Secondemp = SecondDayFinsih - SecondDayStart;
                                }

                            }
                            else{
                                Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                            }
                        } // 금요일 끝

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((Activity)context).finish();
                        v.getContext().startActivity(new Intent(ShowLectureAdapter.context, TimeTableActivity.class));
                    }
                }
            });
        }

        void onBind(ShowLectureViewItem data) {
            this.data = data;
            LecName.setText(data.getLectureName());
            LecProName.setText(data.getLectureProfessorName());
            LecTime.setText(data.getTotalSchedule());
            LecGrade.setText(data.getLectureGrade());
            LecRequire.setText(data.getLectureRequire());
        }
    }
}
