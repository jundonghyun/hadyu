package com.example.yu_map.TaxiCarPool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yu_map.R;

public class TaxiCarPoolMainActivity extends AppCompatActivity {

    Button CreateCarpool, ShowCarpoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_car_pool_main);

        CreateCarpool = findViewById(R.id.createtaxicarpool);
        ShowCarpoolList = findViewById(R.id.showlisttaxicarpool);


        CreateCarpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxiCarPoolMainActivity.this, SelectStartPositionActivity.class));
            }
        });
    }
}