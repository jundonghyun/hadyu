package com.example.yu_map.TimeTable.List.Grade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;
import com.example.yu_map.TimeTable.List.ShowLecture.ShowLectureActivity;

import java.util.ArrayList;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<GradeViewItem> mData = new ArrayList<GradeViewItem>();
    public static String grade;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listgrade_item, parent, false);
        this.context = parent.getContext();

        return new GradeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addItem(GradeViewItem data){
        mData.add(data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        private GradeViewItem data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.GradeName);
        }

        void onBind(GradeViewItem data){
            this.data = data;
            textView.setText(data.getGrade());

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                grade = textView.getText().toString();
                v.getContext().startActivity(new Intent(GradeAdapter.context, ShowLectureActivity.class));

            }
        }
    }
}
