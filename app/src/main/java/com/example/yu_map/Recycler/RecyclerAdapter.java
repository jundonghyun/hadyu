package com.example.yu_map.Recycler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yu_map.Activity.AddFriendPopUpActivity;
import com.example.yu_map.Activity.HomeActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();
    public static Context context;
    private String TAG = "RecyclerAdapter";
    private String MyEmail = ((LoginActivity) com.example.yu_map.Activity.LoginActivity.context).GlobalEmail;
    String FriendEmail = ((AddFriendPopUpActivity) AddFriendPopUpActivity.context).Use_FriendActivity_Email;



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
                    break;
                case R.id.textView1:


                    final FirebaseFirestore db = FirebaseFirestore.getInstance();


                    Map<String, String> User = new HashMap<>();

                    User.put("FriendRequest", MyEmail);


                    DocumentReference newUserRef = db
                            .collection("User")
                            .document(FriendEmail);

                    newUserRef.set(User, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Document successfully Written");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Document Writing Failure");
                                }
                            });




                    /* 내 Realtime DB에 친구 아이디 넣기 */
                    FirebaseDatabase Fdb = FirebaseDatabase.getInstance();
                    final DatabaseReference addFriend = Fdb.getReference().child("FriendList");

                    int idx = MyEmail.indexOf("@");
                    int idx1 = FriendEmail.indexOf("@");

                    final String NickName = MyEmail.substring(0, idx);
                    final String NickName1 = FriendEmail.substring(0, idx1);

                    addFriend.child(NickName).child(NickName1).setValue(NickName1);

                    Toast.makeText(context, "친구요청을 보냈습니다!", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(new Intent(context, AddFriendActivity.class));

                    break;
                case R.id.textView2:
                    Toast.makeText(context, data.getContent(), Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(new Intent(context, AddFriendActivity.class));

                    break;
                case R.id.imageView:
                    Toast.makeText(context, data.getTitle() + " 이미지 입니다.", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(new Intent(context, AddFriendActivity.class));


                    break;
            }
        }
    }
}
