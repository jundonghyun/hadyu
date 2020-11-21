package com.example.yu_map.HyunSeol;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.R;

import java.util.ArrayList;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {
    private static Context context;
    private ArrayList<Announce> items = new ArrayList<Announce>();

    private AdapterView.OnItemClickListener mListener = null ;

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.hs_announce_item, parent, false);
        // ㄴ인플레이션을 통해 뷰 객체 만들기
        return new ViewHolder(itemView);
        // ㄴ뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환하기
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Announce item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder { // 잠깐 static 뺐음
        TextView textView;
        TextView textView2;

        // 뷰 홀더 생성자로 전달되는 뷰 객체 참조하기
        public ViewHolder(View itemView) { // View itemVIew
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            // 추가
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(v.getContext(), SurCotentActivity.class);
                        intent.putExtra("title", items.get(pos).getTitle());
                        intent.putExtra("pos", items.get(pos).number);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);
                    }
                }
            });

        }

        public void setItem(Announce item) {
            textView.setText(item.getNumber());
            textView2.setText(item.getTitle());
        }
    }

    public void addItem(Announce item) {
        items.add(item);
    }

    public void setItems(ArrayList<Announce> items) {
        this.items = items;
    }

    public Announce getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Announce item) {
        items.set(position, item);
    }

}