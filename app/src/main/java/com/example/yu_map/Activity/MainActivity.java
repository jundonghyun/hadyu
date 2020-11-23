package com.example.yu_map.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import com.example.yumap_tmap.R;
import com.example.yu_map.MapPoint;
import com.example.yu_map.R;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.TmapAuthentication;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mArrayMakerID = new ArrayList<>();
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<>();
    private TMapView tMapView = null;

    private String[] RadioItems = new String[]{"출발지", "도착지"};
    private int[] selectedItem = {0};
    private int mMarkerID;
    private double finish_Latitude;
    private double finish_Longitude;
    private double start_Latitude;
    private double start_Longitude;

    Context mContext = null;
    ActionBar actionBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        final TextView startmsg = (TextView) findViewById(R.id.start);
        final TextView finishmsg = (TextView) findViewById(R.id.finish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_map_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);


        ((Button) findViewById(R.id.find_route)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent start = new Intent(MainActivity.this.getApplicationContext(), RouteActivity.class);
                start.putExtra("start_Latitude", MainActivity.this.start_Latitude);
                start.putExtra("start_Longitude", MainActivity.this.start_Longitude);
                start.putExtra("finish_Latitude", MainActivity.this.finish_Latitude);
                start.putExtra("finish_Longitude", MainActivity.this.finish_Longitude);
                MainActivity.this.startActivity(start);
            }
        });
        this.mContext = this;
        TMapView tMapView2 = new TMapView(this);
        this.tMapView = tMapView2;
        tMapView2.setCenterPoint(128.75444d, 35.830629d);
        this.tMapView.setSKTMapApiKey("l7xxe7ffbe0e991b4d41881ad8b70d4e1cc6");
        Bitmap decodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.i_go);
        addPoint();
        showMarkerPoint();
        ((LinearLayout) findViewById(R.id.mapview)).addView(this.tMapView);
        this.tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("기능을 선택하세요")
                        .setSingleChoiceItems(RadioItems, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0] = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(RadioItems[selectedItem[0]].equals("출발지")){
                                    String start = String.valueOf(tMapMarkerItem.getCalloutTitle());
                                    TMapPoint startlocation = tMapMarkerItem.getTMapPoint();
                                    startmsg.setText(start);
                                    MainActivity.this.start_Latitude = startlocation.getLatitude();
                                    MainActivity.this.start_Longitude = startlocation.getLongitude();

                                }
                                else{
                                    String finish = String.valueOf(tMapMarkerItem.getCalloutTitle());
                                    TMapPoint finishlocation = tMapMarkerItem.getTMapPoint();
                                    finishmsg.setText(finish);
                                    MainActivity.this.finish_Latitude = finishlocation.getLatitude();
                                    MainActivity.this.finish_Longitude = finishlocation.getLongitude();

                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "취소하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

    }


    public void addPoint() {
        this.m_mapPoint.add(new MapPoint("IT학과", 35.830612d, 128.754461d));
        this.m_mapPoint.add(new MapPoint("본부본관", 35.829979d, 128.761373d));
        this.m_mapPoint.add(new MapPoint("박물관", 35.83647d, 128.756305d));
        this.m_mapPoint.add(new MapPoint("학생지원센터", 35.835277d, 128.75616d));
        this.m_mapPoint.add(new MapPoint("디자인관", 35.83523d, 128.757284d));
        this.m_mapPoint.add(new MapPoint("우체국", 35.834357d, 128.756034d));
        this.m_mapPoint.add(new MapPoint("법정관", 35.833685d, 128.756479d));
        this.m_mapPoint.add(new MapPoint("상경관", 35.832797d, 128.755977d));
        this.m_mapPoint.add(new MapPoint("중앙도서관", 35.833132d, 128.757968d));
        this.m_mapPoint.add(new MapPoint("인문관", 35.831895d, 128.758562d));
        this.m_mapPoint.add(new MapPoint("사범대학", 35.834092d, 128.759119d));
        this.m_mapPoint.add(new MapPoint("조형대학실기동", 35.835581d, 128.759481d));
        this.m_mapPoint.add(new MapPoint("음악대학", 35.834911d, 128.76138d));
        this.m_mapPoint.add(new MapPoint("승리관", 35.833968d, 128.761719d));
        this.m_mapPoint.add(new MapPoint("학군단", 35.833005d, 128.762458d));
        this.m_mapPoint.add(new MapPoint("종합강의동", 35.832394d, 128.760055d));
        this.m_mapPoint.add(new MapPoint("외국어교육원", 35.831712d, 128.760153d));
        this.m_mapPoint.add(new MapPoint("생활과학대학본관", 35.829294d, 128.758742d));
        this.m_mapPoint.add(new MapPoint("법학전문 대학원", 35.828378d, 128.759354d));
        this.m_mapPoint.add(new MapPoint("기계관", 35.826561d, 128.754139d));
        this.m_mapPoint.add(new MapPoint("소재관", 35.827107d, 128.75401d));
        this.m_mapPoint.add(new MapPoint("기계공작실습실", 35.828247d, 128.753941d));
        this.m_mapPoint.add(new MapPoint("화공관", 35.828984d, 128.754317d));
        this.m_mapPoint.add(new MapPoint("섬유관", 35.829334d, 128.754306d));
        this.m_mapPoint.add(new MapPoint("전기관", 35.829902d, 128.754354d));
        this.m_mapPoint.add(new MapPoint("공과대학본관/IT대학", 35.830617d, 128.754429d));
        this.m_mapPoint.add(new MapPoint("약대본관", 35.830545d, 128.755598d));
        this.m_mapPoint.add(new MapPoint("건축관", 35.829864d, 128.755416d));
        this.m_mapPoint.add(new MapPoint("정보전산원", 35.829314d, 128.755792d));
        this.m_mapPoint.add(new MapPoint("건설관", 35.828864d, 128.755545d));
        this.m_mapPoint.add(new MapPoint("자연계식당", 35.828401d, 128.756328d));
        this.m_mapPoint.add(new MapPoint("생명공학관", 35.828038d, 128.756253d));
        this.m_mapPoint.add(new MapPoint("생명응용과학대 제3실험동", 35.827753d, 128.755336d));
        this.m_mapPoint.add(new MapPoint("생명응용과학대본관", 35.827699d, 128.756838d));
        this.m_mapPoint.add(new MapPoint("생명응용과학대 제2실험동", 35.827314d, 128.756779d));
        this.m_mapPoint.add(new MapPoint("LINC사업단", 35.825972d, 128.757069d));
        this.m_mapPoint.add(new MapPoint("이과대학/제1과학관", 35.830089d, 128.757918d));
        this.m_mapPoint.add(new MapPoint("제2과학관", 35.829452d, 128.757581d));
        this.m_mapPoint.add(new MapPoint("제3과학관", 35.829619d, 128.756991d));
        this.m_mapPoint.add(new MapPoint("제2과학관", 35.829452d, 128.757581d));
    }

    public void showMarkerPoint() {
        for (int i = 0; i < this.m_mapPoint.size(); i++) {
            TMapPoint point = new TMapPoint(this.m_mapPoint.get(i).getLatitude(), this.m_mapPoint.get(i).getLongitude());
            TMapMarkerItem item1 = new TMapMarkerItem();
            Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.poi_dot);
            item1.setTMapPoint(point);
            item1.setName(this.m_mapPoint.get(i).getName());
            item1.setVisible(2);
            item1.setIcon(bitmap);
            item1.setCalloutTitle(this.m_mapPoint.get(i).getName());
            item1.setCanShowCallout(true);
            Bitmap bitmap_1 = BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.i_go);
            Bitmap bitmap_2 = BitmapFactory.decodeResource(this.mContext.getResources(), R.mipmap.end);
            item1.setCalloutRightButtonImage(bitmap_1);
            item1.setCalloutLeftImage(bitmap_2);
            int i2 = mMarkerID;
            mMarkerID = i2 + 1;
            String strId = String.format("pmaker%d", new Object[]{Integer.valueOf(i2)});
            this.tMapView.addMarkerItem(strId, item1);
            this.mArrayMakerID.add(strId);
        }
    }
}