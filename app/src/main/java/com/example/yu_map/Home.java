package com.example.yu_map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
//import com.example.yumap_tmap.R;

public class Home extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);
        ((Button) findViewById(R.id.loginbutton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,LoginActivity.class));
            }
        });
        ((Button) findViewById(R.id.map)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Home.this.startActivity(new Intent(Home.this.getApplicationContext(), MainActivity.class));
            }
        });
        ((Button) findViewById(R.id.finishbutton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(Home.this).setTitle((CharSequence) "YuApp 종료").setMessage((CharSequence) "어플을 종료하시겠습니까?").setPositiveButton((CharSequence) "확인", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Home.this, "YuApp이 종료되었습니다", Toast.LENGTH_LONG).show();
                        Home.this.finish();
                        ActivityCompat.finishAffinity(Home.this);
                        System.exit(0);
                    }
                });
            }
        });
    }
}