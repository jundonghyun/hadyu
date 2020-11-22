package com.example.yu_map.TimeTable.List.Major.CollegeOfMechanicalAndItEngineering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;
import com.example.yu_map.TimeTable.List.Grade.GradeActivity;
import com.example.yu_map.TimeTable.List.ShowLecture.ShowLectureActivity;

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


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        private ListCollegeOfMechanicalandITEngineeringViewItem data;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.MajorName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        ShowLectureActivity.FinalMajor = textView.getText().toString();
                        ((Activity)v.getContext()).finish();

                        v.getContext().startActivity(new Intent(ListCollegeOfMechanicalandITEngineeringAdapter.context, GradeActivity.class));
                    }
                }
            });
        }

        void onBind(ListCollegeOfMechanicalandITEngineeringViewItem data){
            this.data = data;
            textView.setText(data.getMajorName());
        }
    }
}
