package com.example.yu_map.Activity.TimeTable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.FriendTimeTable.FriendTimeTableActivity;
import com.example.yu_map.Activity.FriendTimeTable.TimeTableFriendListAdapter;
import com.example.yu_map.Activity.FriendTimeTable.TimeTableFriendListViewItem;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder>{

    public static String Friendname;
    private static Context context;
    private ArrayList<TimeTableViewItem> mdata = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtimetablefriendlist_item, parent, false);
        this.context = parent.getContext();

        return new TimeTableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public void addItem(TimeTableViewItem data){
        mdata.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        private TimeTableViewItem data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.Name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Friendname = textView.getText().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference dbr = db.getReference().child("TimeTable").child(Friendname);

                        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists() != false){
                                    ((Activity)v.getContext()).finish();
                                    v.getContext().startActivity(new Intent(TimeTableAdapter.context, FriendTimeTableActivity.class));
                                }
                                else{
                                    Toast.makeText(TimeTableAdapter.context, Friendname + "님의 시간표가 없거나 공유되지 않았습니다", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            });
        }
        void onBind(TimeTableViewItem data){
            this.data = data;
            textView.setText(data.getName());
        }
    }
}
