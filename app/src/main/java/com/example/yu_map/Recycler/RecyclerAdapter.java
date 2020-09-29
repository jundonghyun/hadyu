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

import com.example.yu_map.Activity.AddFriendPopUpActivity;
import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;

import javax.xml.XMLConstants;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();
    public static Context context;

    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        this.context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Data data){
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private Data data;



        ItemViewHolder(View itemView){
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

        }

        void onBind(Data data){
            this.data = data;

            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());

            itemView.setOnClickListener(this);
            textView1.setOnClickListener(this);
            textView2.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }
        Intent intent = new Intent(context, AddFriendPopUpActivity.class);

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    Toast.makeText(context, "TITLE : " + data.getTitle() + "\nContent : " + data.getContent(), Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                    break;
                case R.id.textView1:
                    Toast.makeText(context, data.getTitle(), Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);

                    break;
                case R.id.textView2:
                    Toast.makeText(context, data.getContent(), Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);

                    break;
                case R.id.imageView:
                    Toast.makeText(context, data.getTitle() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);


                    break;
            }
        }
    }
}
