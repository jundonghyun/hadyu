package com.example.yu_map.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;

import java.util.ArrayList;

public class FriendsListAdapter extends RecyclerView.Adapter<ItemViewHolder> {


    private static Context context;
    private ArrayList<FriendData> listData = new ArrayList<FriendData>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        this.context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(FriendData data){
        listData.add(data);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView;

        ItemViewHolder(View itemView) {
        super(itemView);

        textView1 = itemView.findViewById(R.id.textView1);
        textView2 = itemView.findViewById(R.id.textView2);
        imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(FriendData data){
        textView1.setText(data.getTitle());
        textView2.setText(data.getContent());
        imageView.setImageResource(data.getResId());
        }
}
