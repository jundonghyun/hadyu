package com.example.yu_map.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yu_map.MapPoint;
import com.example.yu_map.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class StartGuideActivity extends AppCompatActivity {

    public static ArrayList<MapPoint> RouteMapPoint = new ArrayList<>();
    public static ArrayList<String> RouteDescription = new ArrayList<>();
    public static ArrayList<String> RouteIndex = new ArrayList<>();
    public static ArrayList<String> RouteDistance = new ArrayList<>();
    public static ArrayList<String> RouteTurn = new ArrayList<>();

    public Context context;


    private ImageView GuideConer;
    private TextView speed;
    private TextView distance;
    private Button PointMyLocation;
    private TMapView tMapView = null;
    private TMapPoint tp = null;
    private TMapPoint start, end;
    private Bitmap bitmap;

    private Location location;
    private double MyLatitude, MyLongitude, RemainDistance;
    private int count = 1;
    private int MapPointcount = 0;


    private static int mMarkerID;
    private static ArrayList<MapPoint> m_mapPoint = new ArrayList<>();


    private TMapPolyLine tMapPolyLine = new TMapPolyLine();
    private ArrayList<String> mArrayMakerID = new ArrayList<>();
    private String TAG = "StartGuideActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_guide);


        Intent intent = getIntent();
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Handler OneSecond = new Handler(); // 1초마다 반복시키는 핸들러

        RouteMapPoint = (ArrayList<MapPoint>) intent.getSerializableExtra("RouteMapPoint");
        RouteDescription = (ArrayList<String>) intent.getSerializableExtra("RouteDescription");
        RouteIndex = (ArrayList<String>) intent.getSerializableExtra("RouteIndex");
        RouteDistance = (ArrayList<String>) intent.getSerializableExtra("RouteDistance");
        RouteTurn = (ArrayList<String>) intent.getSerializableExtra("RouteTurn");

        speed = findViewById(R.id.speed);
        distance = findViewById(R.id.distance);
        PointMyLocation = findViewById(R.id.PickMyLocation);

        TMapData tMapData = new TMapData();
        TMapMarkerItem markerItem1 = new TMapMarkerItem();
        this.tMapView = new TMapView(this);
        tMapPolyLine.setLineWidth(2);
        tMapPolyLine.setLineColor(Color.BLUE);

        tMapView.setSKTMapApiKey("l7xxe7ffbe0e991b4d41881ad8b70d4e1cc6");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        MyLatitude = location.getLatitude();
        MyLongitude = location.getLongitude();

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.end);

        PointMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setCenterPoint(MyLongitude, MyLatitude, true);
                tMapView.setTrackingMode(true);
                tMapView.setCompassMode(true);
                tMapView.setZoomLevel(18);

            }
        });

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Location endPoint = new Location("end");
                endPoint.setLatitude(RouteMapPoint.get(MapPointcount).getLatitude());
                endPoint.setLongitude(RouteMapPoint.get(MapPointcount).getLongitude());

                if (location != null) {
                    MyLatitude = location.getLatitude();
                    MyLongitude = location.getLongitude();
                }
                double getSpeed = Double.parseDouble(String.format("%.1f", location.getSpeed()));
                speed.setText("속도 "+getSpeed);

                if(MyLatitude == RouteMapPoint.get(MapPointcount).getLatitude() && MyLongitude == RouteMapPoint.get(MapPointcount).getLongitude()){

                    MapPointcount++;
                    GuideConer.setVisibility(View.INVISIBLE);
                }

                double PointDis_temp = location.distanceTo(endPoint);
                double Point_distance = Double.parseDouble(String.format("%.1f", PointDis_temp));
                RemainDistance = Point_distance;
                distance.setText(Point_distance +"m");

                tp = new TMapPoint(MyLatitude, MyLongitude);
                markerItem1.setIcon(bitmap); // 마커 아이콘 지정
                markerItem1.setTMapPoint(tp); // 마커의 좌표 지정
                tMapView.addMarkerItem("markerItem1", markerItem1); // 지
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationListener);



        this.tMapView.setCenterPoint(MyLongitude, MyLatitude, true);
        this.tMapView.setCompassMode(true);
        this.tMapView.setMapPosition(TMapView.POSITION_NAVI);
        this.tMapView.setZoomLevel(18);
        this.tMapView.setPOIRotate(true);
        this.tMapView.setMarkerRotate(true);
        this.tMapView.setPathRotate(true);

        AddMyPoint();
        AddMarker();

        int lastpoint = RouteMapPoint.size()-1;
        start = new TMapPoint(MyLatitude, MyLongitude);
        end = new TMapPoint(RouteMapPoint.get(lastpoint).getLatitude(), RouteMapPoint.get(lastpoint).getLongitude());

        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end,
                new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                        tMapView.addTMapPath(tMapPolyLine);
                    }
                });



        OneSecond.postDelayed(new Runnable() {
            @Override
            public void run() {
                double doubletemp = 5.0;
                String temp;

                if(doubletemp > RemainDistance || doubletemp == RemainDistance){

                    if(RemainDistance == 0.0){
                        return;
                    }

                    count++;
                    GuideConer.setVisibility(View.INVISIBLE);

                    temp = RouteTurn.get(count);

                    MatchTurnImage(temp);

                    OneSecond.postDelayed(this, 1000);

                }
                else{
                    temp = RouteTurn.get(count);

                    MatchTurnImage(temp);

                    OneSecond.postDelayed(this, 1000);
                }
            }
        }, 1000);

        ((LinearLayout) findViewById(R.id.StartGuide)).addView(tMapView);
    }

    public void MatchTurnImage(String temp){
        if(temp.equals("11")){//직진
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_straight_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("12")){//좌회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_left_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_left_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("13")){//우회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_right_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_right_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("14")){//유턴
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_uturn_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_uturn_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("16")){//8시방향 좌회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_eightleft_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_eightleft_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("17")){//10시 방향 좌회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_tenleft_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_tenleft_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("18")){//2시 방향 우회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_baseline_wifi_24);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_tworight_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("19")){//4시 방향 우회전
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_fourright_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_fourright_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("211")){//횡단보도
            //bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.navigation_crosswalk_black_18dp);
            GuideConer = findViewById(R.id.guideconer);
            GuideConer.setImageResource(R.drawable.navigation_crosswalk_black_18dp);
            GuideConer.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("212")){//좌측 횡단보도

        }
        else if(temp.equals("213")){//우측 횡단보도

        }
        else if(temp.equals("214")){//8시 방향 횡단보도

        }
        else if(temp.equals("215")){//10시 방향 횡단보도

        }
        else if(temp.equals("216")){//2시 방향 횡단보도

        }
        else if(temp.equals("217")){//4시 방향 횡단보도

        }

    }

    public void AddMyPoint() {
        String start = "출발지";
        String end = "목적지";

        for(int i = 0; i < RouteMapPoint.size(); i++){
            if(i == 0){
                this.m_mapPoint.add(new MapPoint(start, RouteMapPoint.get(i).getLatitude(), RouteMapPoint.get(i).getLongitude()));

            }
            else if(i == RouteMapPoint.size()-1){
                this.m_mapPoint.add(new MapPoint(end, RouteMapPoint.get(i).getLatitude(), RouteMapPoint.get(i).getLongitude()));

            }
            this.m_mapPoint.add(new MapPoint("", RouteMapPoint.get(i).getLatitude(), RouteMapPoint.get(i).getLongitude()));

        }
    }

    public void AddMarker() {
        for (int i = 0; i < this.m_mapPoint.size(); i++) {
            TMapPoint point = new TMapPoint(this.m_mapPoint.get(i).getLatitude(),
                    this.m_mapPoint.get(i).getLongitude());
            TMapMarkerItem item1 = new TMapMarkerItem();
            tMapPolyLine.addLinePoint(point);
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.poi_dot);

            item1.setTMapPoint(point);
            item1.setName(this.m_mapPoint.get(i).getName());
            item1.setVisible(2);
            item1.setIcon(bitmap);
            item1.setCalloutTitle(this.m_mapPoint.get(i).getName());
            item1.setCanShowCallout(true);
            int i2 = mMarkerID;
            mMarkerID = i2 + 1;
            String stdID = String.format("pMaker%d", new Object[]{Integer.valueOf(i2)});
            this.tMapView.addMarkerItem(stdID, item1);
            this.mArrayMakerID.add(stdID);
        }

    }
}