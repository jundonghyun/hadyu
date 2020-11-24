package com.example.yu_map.Activity.JiSu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.yu_map.R;

public class FullScreen extends AppCompatActivity {

    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ivImage = findViewById(R.id.pto_view);


        ivImage.setImageBitmap(image);
    }
}