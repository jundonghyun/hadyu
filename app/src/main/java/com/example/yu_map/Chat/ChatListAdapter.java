package com.example.yu_map.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<ChatListData> mdata = new ArrayList<ChatListData>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        this.context = parent.getContext();

        return  new ChatListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    void addItem(ChatListData data){
        mdata.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        private ChatListData data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.chatitem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        v.getContext().startActivity(new Intent(ChatListAdapter.context, ChatActivity.class));
                    }
                }
            });
        }

        public void onBind(ChatListData data){
            this.data = data;
            textView.setText(data.getRoomName());
        }
    }
}
