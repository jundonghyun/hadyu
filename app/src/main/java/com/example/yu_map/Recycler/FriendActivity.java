package com.example.yu_map.Recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu_map.Activity.AddFriendPopUpActivity;
import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class FriendActivity extends AppCompatActivity{

    public RecyclerAdapter adapter;
    String Email = ((AddFriendPopUpActivity) AddFriendPopUpActivity.context).Use_FriendActivity_Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        init();

        getData();

    }


    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        List<String> listTitle = Arrays.asList(Email);
        List<String> listContent = Arrays.asList("친구요청을 보내려면 클릭");
        List<Integer> listResId = Arrays.asList( R.mipmap.ic_launcher);

        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();


    }
}