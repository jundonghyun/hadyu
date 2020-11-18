package com.example.yu_map.Recycler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.FriendLocationActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.Chat.ChatActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHoler> {

    public static Context context;
    private ArrayList<FriendData> listData = new ArrayList<FriendData>();
    public static String FriendName;

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        this.context = parent.getContext();

        return new FriendsListAdapter.ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
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

    public class ViewHoler extends RecyclerView.ViewHolder{
        private String[] RadioItems = new String[]{"채팅하기", "위치보기"};
        private int[] selectedItem = {0};
        String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
        int idx = Email.indexOf("@");
        String NickName = Email.substring(0, idx);

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private FriendData data;
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
        final DatabaseReference friend = Fdb.getReference().child("FindFriend");

        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        /*팝업 띄우기 채팅할껀지 친구 위치 찾을껀지*/
                        /*위치는 오프라인이면 못찾도록*/
                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsListAdapter.context);
                        builder.setTitle("기능을 선택하세요").setSingleChoiceItems(RadioItems, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0] = which;
                            }
                        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (RadioItems[selectedItem[0]].equals("채팅하기")) {
                                    FriendsListAdapter.FriendName = textView1.getText().toString();
                                    v.getContext().startActivity(new Intent(FriendsListAdapter.context, ChatActivity.class));
                                } else {
                                    if (textView2.getText() == "오프라인") {
                                        Toast.makeText(FriendsListAdapter.context, "친구가 오프라인 입니다", Toast.LENGTH_SHORT).show();
                                    } else {
                                        /* 클릭한 친구에서 친구의 이름을 가져와서 Realtime DB의 FindFriend필드에 넣음 */
                                        friend.setValue(textView1.getText());
                                        v.getContext().startActivity(new Intent(FriendsListAdapter.context, FriendLocationActivity.class));
                                    }
                                }
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        builder.create();
                        builder.show();
                    }
                }
            });
        }
        void onBind(FriendData data){
            this.data = data;
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());
        }
    }
}
