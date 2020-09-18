package com.example.yu_map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
//import com.example.yumap_tmap.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivitiy";
    private Button shutdown, Login, Map, FriendLocation;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);

        shutdown = findViewById(R.id.finishbutton);
        Login = findViewById(R.id.loginbutton);
        Map = findViewById(R.id.map);
        FriendLocation = findViewById(R.id.FriendLocationButton);

        FriendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,FriendLocationActivity.class));

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
        Map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this.getApplicationContext(), MainActivity.class));
            }
        });
        shutdown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(HomeActivity.this, "YuApp이 종료되었습니다", Toast.LENGTH_LONG).show();

                /* 어플 종료 방법 */

                moveTaskToBack(true); //1단계 태스크 백그라운드로 이동
                finishAndRemoveTask(); // 2단계 액티비티종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid()); // 3단계 앱 프로세스 종료
                Log.d(TAG, "application shutdown");
            }
        });
    }
}