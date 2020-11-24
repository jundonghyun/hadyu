package com.example.yu_map.Activity.JiSu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.example.yu_map.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class FoodActivity extends AppCompatActivity  {

    Button txtview_day;
    ImageView imgview_snatural,imgview_stfnatural,imgview_stfliberal,imgview_sliberal;
    TextView txtview_liberal, txtview_natural;


    long now = System.currentTimeMillis();
    Date mdate = new Date(now);

    SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd E요일", Locale.KOREAN);
    String getTime = simpleDate.format(mdate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        txtview_day = (Button) findViewById(R.id.txtview_day);

        imgview_snatural = (ImageView) findViewById(R.id.imgview_snatural);
        imgview_stfnatural = (ImageView) findViewById(R.id.imgview_stfnatural);

        imgview_sliberal = (ImageView) findViewById(R.id.imgview_sliberal);
        imgview_stfliberal = (ImageView) findViewById(R.id.imgview_stfliberal);

        txtview_liberal = (TextView)findViewById(R.id.txtview_liberal);
        txtview_natural = (TextView)findViewById(R.id.txtview_natural);

        imgview_snatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodActivity.this, FullScreen.class);

                imgview_snatural.buildDrawingCache();
                Bitmap bitmap = imgview_snatural.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);

                startActivity(intent);
            }
        });

        imgview_stfnatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodActivity.this, FullScreen.class);

                imgview_stfnatural.buildDrawingCache();
                Bitmap bitmap = imgview_stfnatural.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);

                startActivity(intent);
            }
        });

        imgview_sliberal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodActivity.this, FullScreen.class);

                imgview_sliberal.buildDrawingCache();
                Bitmap bitmap = imgview_sliberal.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);

                startActivity(intent);
            }
        });

        imgview_stfliberal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodActivity.this, FullScreen.class);

                imgview_stfliberal.buildDrawingCache();
                Bitmap bitmap = imgview_stfliberal.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);

                startActivity(intent);
            }
        });

        txtview_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtview_natural.setVisibility(View.VISIBLE);
                txtview_liberal.setVisibility(View.VISIBLE);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef_N = storage.getReference("natural/");
                storageRef_N.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for(final StorageReference item : listResult.getItems()){
                            Log.d("t", listResult.getItems().toString());
                            item.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(item.getName().equals("20201110055413_natural_staff.jpg")){
                                        Glide.with(FoodActivity.this).load(task.getResult()).into(imgview_stfnatural);

                                    }
                                    else if(item.getName().equals("20201110055445_natural_student.jpg")){
                                        Glide.with(FoodActivity.this).load(task.getResult()).into(imgview_snatural);
                                    }
                                    else{
                                        Toast.makeText(FoodActivity.this, "자연계 학식표 불러오기 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                FirebaseStorage storage1 = FirebaseStorage.getInstance();
                StorageReference storageRef_L = storage1.getReference("liberal/");
                storageRef_L.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for(final StorageReference item : listResult.getItems()){
                            item.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(item.getName().equals("20201116021728_liberal_staff.jpg")){
                                        Glide.with(FoodActivity.this).load(task.getResult()).into(imgview_stfliberal);
                                    }
                                    else if(item.getName().equals("20201116021735_liberal_student.jpg")){
                                        Glide.with(FoodActivity.this).load(task.getResult()).into(imgview_sliberal);
                                    }
                                    else{
                                        Toast.makeText(FoodActivity.this, "인문계 학식표 불러오기 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        txtview_day.setText(getTime);

    }

}