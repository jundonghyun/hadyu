package com.example.yu_map.TimeTable.List.Major.CollegeOfMechanicalAndItEngineering;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.TimeTableActivity;
import com.example.yu_map.R;
import com.example.yu_map.TimeTable.List.Grade.GradeActivity;
import com.example.yu_map.TimeTable.List.Grade.GradeAdapter;
import com.example.yu_map.TimeTable.List.Major.CollegeOfNaturalSciences.ListCollegeofNaturalSciencesActivity;
import com.example.yu_map.TimeTable.List.ShowLecture.ShowLectureActivity;
import com.example.yu_map.TimeTable.List.ShowLecture.ShowLectureAdapter;

import java.util.ArrayList;

public class ListCollegeOfMechanicalandITEngineeringAdapter extends RecyclerView.Adapter<ListCollegeOfMechanicalandITEngineeringAdapter.ViewHolder> {

    public static Context context;
    private ArrayList<ListCollegeOfMechanicalandITEngineeringViewItem> mData = new ArrayList<ListCollegeOfMechanicalandITEngineeringViewItem>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmajor_item, parent, false);
        this.context = parent.getContext();

        return new ListCollegeOfMechanicalandITEngineeringAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addItem(ListCollegeOfMechanicalandITEngineeringViewItem data){
        mData.add(data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        private ListCollegeOfMechanicalandITEngineeringViewItem data;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.MajorName);
        }

        void onBind(ListCollegeOfMechanicalandITEngineeringViewItem data){
            this.data = data;
            textView.setText(data.getMajorName());

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                ShowLectureActivity.FinalMajor = textView.getText().toString();
                v.getContext().startActivity(new Intent(ListCollegeOfMechanicalandITEngineeringAdapter.context, GradeActivity.class));
            }
        }
    }
}
