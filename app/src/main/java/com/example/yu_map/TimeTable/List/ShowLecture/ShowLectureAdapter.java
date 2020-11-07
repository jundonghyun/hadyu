package com.example.yu_map.TimeTable.List.ShowLecture;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.TimeTableActivity;
import com.example.yu_map.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class ShowLectureAdapter extends RecyclerView.Adapter<ShowLectureAdapter.ViewHolder> {
    private static Context context;
    public static ArrayList<ShowLectureViewItem> mData = new ArrayList<ShowLectureViewItem>();

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

    void addItem(ShowLectureViewItem data){
        mData.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
        }

        void onBind(ShowLectureViewItem data){
            this.data = data;
            LecName.setText(data.getLectureName());
            LecProName.setText(data.getLectureProfessorName());
            LecTime.setText(data.getTotalSchedule());
            LecGrade.setText(data.getLectureGrade());
            LecRequire.setText(data.getLectureRequire());

            LecName.setOnClickListener(this);
            LecProName.setOnClickListener(this);
            LecTime.setOnClickListener(this);
            LecGrade.setOnClickListener(this);
            LecRequire.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            int FirstDayStart, FirstDayFinish, SecondDayStart, SecondDayFinsih;
            String FirstDay, SecondDay;

            if(pos != RecyclerView.NO_POSITION){
                FirstDay = data.getFirstDays();
                FirstDayStart = Integer.parseInt(data.getFirstDaysStartTime().substring(0, 2)) % 12;
                FirstDayFinish = Integer.parseInt(data.getFirstDaysFinishTime().substring(0, 2)) % 12;
                SecondDay = data.getSecondDays();
                SecondDayStart = Integer.parseInt(data.getSecondDaysStartTime().substring(0, 2)) % 12;
                SecondDayFinsih = Integer.parseInt(data.getSecondDaysFinishTime().substring(0, 2)) % 12;

                if(FirstDay.equals("Monday")){
                    if(TimeTableActivity.Monday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Monday[FirstDayFinish].getText().toString().equals("")){
                        if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Monday[FirstDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = FirstDayFinish - FirstDayStart;
                            TimeTableActivity.Monday[FirstDayStart].setText(data.getLectureName());
                            TimeTableActivity.Monday[FirstDayStart+temp].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(SecondDay.equals("Monday")){
                    if(TimeTableActivity.Monday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Monday[SecondDayFinsih].getText().toString().equals("")){
                        if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Monday[SecondDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = SecondDayFinsih - SecondDayStart;
                            TimeTableActivity.Monday[SecondDayStart].setText(data.getLectureName());
                            TimeTableActivity.Monday[SecondDayFinsih].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                    }
                } // 월요일 끝
                if(FirstDay.equals("Tuesday")){
                    if(TimeTableActivity.Tuesday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Tuesday[FirstDayFinish].getText().toString().equals("")){
                        if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Tuesday[FirstDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = FirstDayFinish - FirstDayStart;
                            TimeTableActivity.Tuesday[FirstDayStart].setText(data.getLectureName());
                            TimeTableActivity.Tuesday[FirstDayStart+temp].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(SecondDay.equals("Tuesday")){
                    if(TimeTableActivity.Tuesday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Tuesday[SecondDayFinsih].getText().toString().equals("")){
                        if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Tuesday[SecondDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = SecondDayFinsih - SecondDayStart;
                            TimeTableActivity.Tuesday[SecondDayStart].setText(data.getLectureName());
                            TimeTableActivity.Tuesday[SecondDayFinsih].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                    }
                } // 화요일 끝
                if(FirstDay.equals("Wednesday")){
                    if(TimeTableActivity.Wednesday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Wednesday[FirstDayFinish].getText().toString().equals("")){
                        if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Wednesday[FirstDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = FirstDayFinish - FirstDayStart;
                            TimeTableActivity.Wednesday[FirstDayStart].setText(data.getLectureName());
                            TimeTableActivity.Wednesday[FirstDayStart+temp].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(SecondDay.equals("Wednesday")){
                    if(TimeTableActivity.Wednesday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Wednesday[SecondDayFinsih].getText().toString().equals("")){
                        if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Wednesday[SecondDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = SecondDayFinsih - SecondDayStart;
                            TimeTableActivity.Wednesday[SecondDayStart].setText(data.getLectureName());
                            TimeTableActivity.Wednesday[SecondDayFinsih].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                    }
                }//수요일 끝
                if(FirstDay.equals("Thursday")){
                    if(TimeTableActivity.Thursday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Thursday[FirstDayFinish].getText().toString().equals("")){
                        if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Thursday[FirstDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = FirstDayFinish - FirstDayStart;
                            TimeTableActivity.Thursday[FirstDayStart].setText(data.getLectureName());
                            TimeTableActivity.Thursday[FirstDayStart+temp].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(SecondDay.equals("Thursday")){
                    if(TimeTableActivity.Thursday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Thursday[SecondDayFinsih].getText().toString().equals("")){
                        if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Thursday[SecondDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = SecondDayFinsih - SecondDayStart;
                            TimeTableActivity.Thursday[SecondDayStart].setText(data.getLectureName());
                            TimeTableActivity.Thursday[SecondDayFinsih].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                    }
                }//목요일 끝
                if(FirstDay.equals("Friday")){
                    if(TimeTableActivity.Friday[FirstDayStart].getText().toString().equals("") && TimeTableActivity.Friday[FirstDayFinish].getText().toString().equals("")){
                        if(FirstDayStart == FirstDayFinish){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Friday[FirstDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = FirstDayFinish - FirstDayStart;
                            TimeTableActivity.Friday[FirstDayStart].setText(data.getLectureName());
                            TimeTableActivity.Friday[FirstDayStart+temp].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(SecondDay.equals("Friday")){
                    if(TimeTableActivity.Friday[SecondDayStart].getText().toString().equals("") && TimeTableActivity.Friday[SecondDayFinsih].getText().toString().equals("")){
                        if(SecondDayStart == SecondDayFinsih){ //시작시간과 끝나는시간이 1시간 차이일때
                            TimeTableActivity.Friday[SecondDayStart].setText(data.getLectureName());
                        }
                        else{
                            int temp = SecondDayFinsih - SecondDayStart;
                            TimeTableActivity.Friday[SecondDayStart].setText(data.getLectureName());
                            TimeTableActivity.Friday[SecondDayFinsih].setText(data.getLectureName());
                        }
                    }
                    else{
                        Toast.makeText(ShowLectureAdapter.context, "시간표가 겹칩니다", Toast.LENGTH_SHORT).show();

                    }
                } // 금요일 끝


                v.getContext().startActivity(new Intent(ShowLectureAdapter.context, TimeTableActivity.class));
            }
        }
    }
}
