package com.example.yu_map.Activity.FriendTimeTable;

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
import com.example.yu_map.Recycler.RecyclerAdapter;

import java.util.ArrayList;

public class TimeTableFriendListAdapter extends RecyclerView.Adapter<TimeTableFriendListAdapter.ViewHolder> {

    public static String Friendname;
    private static Context context;
    private ArrayList<TimeTableFriendListViewItem> mdata = new ArrayList<TimeTableFriendListViewItem>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtimetablefriendlist_item, parent, false);
        this.context = parent.getContext();

        return new TimeTableFriendListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    void addItem(TimeTableFriendListViewItem data){
        mdata.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        private TimeTableFriendListViewItem data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Friendname = textView.getText().toString();
                        ((Activity)v.getContext()).finish();
                        v.getContext().startActivity(new Intent(TimeTableFriendListAdapter.context, FriendTimeTableActivity.class));
                    }
                }
            });
        }

        void onBind(TimeTableFriendListViewItem data){
            this.data = data;
            textView.setText(data.getName());
        }
    }
}
