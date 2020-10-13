package com.example.yu_map.Recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.FriendLocationActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<ItemViewHolder> {



    public static Context context;
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

    public String getItem(FriendData data){
        return data.getTitle();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(FriendData data){
        listData.add(data);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView;
    private FriendData data;
    FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
    final DatabaseReference friend = Fdb.getReference().child("FindFriend");

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(FriendData data){
            this.data = data;
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());

            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
        }



    @Override
    public void onClick(View v) {

        int pos = getAdapterPosition();
        if(pos != RecyclerView.NO_POSITION){

            if(textView2.getText() == "오프라인"){
                Toast.makeText(FriendsListAdapter.context, "친구가 오프라인 입니다", Toast.LENGTH_SHORT).show();
            }
            else{
                /* 클릭한 친구에서 친구의 이름을 가져와서 Realtime DB의 FindFriend필드에 넣음 */
                friend.setValue(textView1.getText());
                v.getContext().startActivity(new Intent(FriendsListAdapter.context, FriendLocationActivity.class));
            }

        }

    }
}
