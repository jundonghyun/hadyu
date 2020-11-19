package com.example.yu_map.TaxiCarPool;

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

public class TaxiCarPoolShowWaitListAdapter extends RecyclerView.Adapter<TaxiCarPoolShowWaitListAdapter.ViewHolder> {

    private static Context context;
    private ArrayList<TaxiCarPoolShowWaitListData> mData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_taxt_carpool_wait_item, parent, false);
        this.context = parent.getContext();

        return new TaxiCarPoolShowWaitListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addItem(TaxiCarPoolShowWaitListData data){
        mData.add(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Maker, StartPos, FinishPos;
        private TaxiCarPoolShowWaitListData data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Maker = itemView.findViewById(R.id.RoomMaker);
            StartPos = itemView.findViewById(R.id.StartPos);
            FinishPos = itemView.findViewById(R.id.FinishPos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(TaxiCarPoolShowWaitListAdapter.context, TaxiCarPoolUserWaitActivity.class);
                        intent.putExtra("entererid", data.getMakerID());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        void onBind(TaxiCarPoolShowWaitListData data){
            this.data = data;
            Maker.setText("만든사람: "+data.getMakerID());
            StartPos.setText("출발지: "+data.getStart());
            FinishPos.setText("도착지: "+data.getEnd());
        }
    }
}
