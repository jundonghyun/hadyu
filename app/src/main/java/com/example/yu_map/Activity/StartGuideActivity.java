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
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yu_map.MapPoint;
import com.example.yu_map.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StartGuideActivity extends AppCompatActivity {

    public static ArrayList<MapPoint> RouteMapPoint = new ArrayList<>();
    public static ArrayList<String> RouteDescription = new ArrayList<>();
    public static ArrayList<String> RouteIndex = new ArrayList<>();
    public static ArrayList<String> RouteDistance = new ArrayList<>();
    public static ArrayList<String> RouteTurn = new ArrayList<>();

    public Context context;


    private TextView GuideConer;
    private TMapView tMapView = null;
    private TMapPoint tp = null;

    private Location location;
    private double MyLatitude, MyLongitude;

    private static int mMarkerID;
    private static ArrayList<MapPoint> m_mapPoint = new ArrayList<>();


    private TMapPolyLine tMapPolyLine = new TMapPolyLine();
    private ArrayList<String> mArrayMakerID = new ArrayList<>();
    private String TAG = "StartGuideActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_guide);

        GuideConer = findViewById(R.id.DisplayTurn);
        Intent intent = getIntent();
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        RouteMapPoint = (ArrayList<MapPoint>) intent.getSerializableExtra("RouteMapPoint");
        RouteDescription = (ArrayList<String>) intent.getSerializableExtra("RouteDescription");
        RouteIndex = (ArrayList<String>) intent.getSerializableExtra("RouteIndex");
        RouteDistance = (ArrayList<String>) intent.getSerializableExtra("RouteDistance");
        RouteTurn = (ArrayList<String>) intent.getSerializableExtra("RouteTurn");

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

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    MyLatitude = location.getLatitude();
                    MyLongitude = location.getLongitude();
                }
                tp = new TMapPoint(MyLatitude, MyLongitude);
                markerItem1.setIcon(bitmap); // 마커 아이콘 지정
                markerItem1.setTMapPoint(tp); // 마커의 좌표 지정
                tMapView.addMarkerItem("markerItem1", markerItem1); // 지

                Log.d(TAG, tp.toString());
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);




        this.tMapView.setCenterPoint(MyLongitude, MyLatitude);
        this.tMapView.setCompassMode(true);
        this.tMapView.setMapPosition(TMapView.POSITION_NAVI);

        AddMyPoint();
        AddMarker();

        int count = 0;
        for(int i = 0; i < RouteMapPoint.size(); i++){
            String temp = RouteTurn.get(count);
            GuideConer.setText(temp);

            if(MyLatitude == RouteMapPoint.get(count).getLatitude() && MyLongitude == RouteMapPoint.get(count).getLongitude()){

                count++;
            }

        }

        ((LinearLayout) findViewById(R.id.StartGuide)).addView(tMapView);
    }

    public void AddMyPoint() {
        for(int i = 0; i < RouteMapPoint.size(); i++){
            this.m_mapPoint.add(new MapPoint("marker" + i, RouteMapPoint.get(i).getLatitude(), RouteMapPoint.get(i).getLongitude()));

        }
    }

    public void AddMarker() {
        for (int i = 0; i < this.m_mapPoint.size(); i++) {
            TMapPoint point = new TMapPoint(this.m_mapPoint.get(i).getLatitude(),
                    this.m_mapPoint.get(i).getLongitude());
            TMapMarkerItem item1 = new TMapMarkerItem();
            //tMapPolyLine.addLinePoint(point);
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
            //this.tMapView.addTMapPolyLine("Line1", tMapPolyLine);
            this.mArrayMakerID.add(stdID);

        }

    }
}