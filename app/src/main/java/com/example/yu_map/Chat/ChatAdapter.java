package com.example.yu_map.Chat;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.example.yu_map.TimeTable.List.ListDepartmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<ChatData> mdata = new ArrayList<ChatData>();
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    int idx = Email.indexOf("@");
    String NickName = Email.substring(0, idx);



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);

        this.context = parent.getContext();

        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    void addItem(ChatData data){
        mdata.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        String name;
        TextView textView1;
        private ChatData data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.chatitem);
        }

        public void onBind(ChatData data) {
            this.data = data;
            if (data.getNickName().equals(NickName)){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                textView1.setLayoutParams(params);
                textView1.setText(data.getMessage());
            }
            else{
                textView1.setText(data.getMessage());
            }
        }
    }
}
