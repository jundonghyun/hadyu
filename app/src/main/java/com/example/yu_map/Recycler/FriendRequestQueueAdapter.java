package com.example.yu_map.Recycler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.HomeActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRequestQueueAdapter extends RecyclerView.Adapter<FriendRequestQueueViewHolder> {

    public static Context FriendQueuecontext;
    private ArrayList<FriendData> listData = new ArrayList<FriendData>();


    @NonNull
    @Override
    public FriendRequestQueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        this.FriendQueuecontext = parent.getContext();

        return new FriendRequestQueueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestQueueViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(FriendData data) {
        listData.add(data);
    }
}

class FriendRequestQueueViewHolder extends RecyclerView.ViewHolder{

    private TextView textView1;
    private TextView textView2;
    private ImageView imageView;
    private FriendData data;
    private String Friendemail;
    private Context context;
    private String MyEmail = ((LoginActivity) com.example.yu_map.Activity.LoginActivity.context).GlobalEmail;
    private String TAG = "FriendRequestQueueViewHolder";
    private List<String> Id;


    FriendRequestQueueViewHolder(View itemView) {
        super(itemView);

        textView1 = itemView.findViewById(R.id.textView1);
        textView2 = itemView.findViewById(R.id.textView2);
        imageView = itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase Fdb = FirebaseDatabase.getInstance();

                int idx = MyEmail.indexOf("@");
                final String NickName = MyEmail.substring(0, idx);

                /* 친구추가 대기 목록에서 친구를 선택하면 선택한 위치가 반환됨 */
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    Friendemail = data.getTitle();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendRequestQueueAdapter.FriendQueuecontext);
                builder.setTitle("친구요청을 수락하시겠습니까?");
                builder.setPositiveButton("수락하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        final DatabaseReference AddFriend_Waiting_Queue = Fdb.getReference().child("FriendList");



                        AddFriend_Waiting_Queue.child(NickName).child(Friendemail).setValue(Friendemail);
                        AddFriend_Waiting_Queue.child(Friendemail).child(NickName).setValue(NickName);

                        final DatabaseReference DeleteFriend = Fdb.getReference().child("Waiting Friend Request").child(NickName).child(Friendemail);
                        Query query = DeleteFriend.equalTo(Friendemail);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().removeValue();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(FriendRequestQueueAdapter.FriendQueuecontext, "친구추가를 수락했습니다!", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(new Intent(FriendRequestQueueAdapter.FriendQueuecontext, HomeActivity.class));
                    }
                });
                builder.setNegativeButton("거부하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference DeleteFriend = Fdb.getReference().child("Waiting Friend Request").child(NickName).child(Friendemail);
                        Query query = DeleteFriend.equalTo(Friendemail);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().removeValue();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(FriendRequestQueueAdapter.FriendQueuecontext, "친구추가를 거부했습니다!", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(new Intent(FriendRequestQueueAdapter.FriendQueuecontext, HomeActivity.class));
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    void onBind(FriendData data) {
        this.data = data;

        textView1.setText(data.getTitle());
        textView2.setText(data.getContent());
        imageView.setImageResource(data.getResId());
    }
}
