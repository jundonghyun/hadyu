package com.example.yu_map.TimeTable.List;

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
import com.example.yu_map.TimeTable.List.Major.CollegeOfMechanicalAndItEngineering.ListCollegeOfMechanicalandITEngineeringActivity;
import com.example.yu_map.TimeTable.List.Major.CollegeOfNaturalSciences.ListCollegeofNaturalSciencesActivity;

import java.util.ArrayList;

public class ListDepartmentAdapter extends RecyclerView.Adapter<ListDepartmentAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<ListDepartmentViewItem> mData = new ArrayList<ListDepartmentViewItem>();

    @NonNull
    @Override
    public ListDepartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdepartment_item, parent, false);
        this.context = parent.getContext();

        return new ListDepartmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDepartmentAdapter.ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addItem(ListDepartmentViewItem data){
        mData.add(data);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        private ListDepartmentViewItem data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.DepartmentName);
        }

        void onBind(ListDepartmentViewItem data){
            this.data = data;
            textView.setText(data.getDepartmentname());

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                if(textView.getText().equals("기계IT대학")){
                    ((Activity)v.getContext()).finish();
                    v.getContext().startActivity(new Intent(ListDepartmentAdapter.context, ListCollegeOfMechanicalandITEngineeringActivity.class));
                }
                else if(textView.getText().equals("자연과학대학")){
                    ((Activity)v.getContext()).finish();
                    v.getContext().startActivity(new Intent(ListDepartmentAdapter.context, ListCollegeofNaturalSciencesActivity.class));
                }
            }
        }
    }
}