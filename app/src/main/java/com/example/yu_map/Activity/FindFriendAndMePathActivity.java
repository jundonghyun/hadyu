package com.example.yu_map.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu_map.MapPoint;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.TmapAuthentication;
import com.squareup.okhttp.Route;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.callback.Callback;

public class FindFriendAndMePathActivity extends AppCompatActivity{

    String FriendId;

    Button StartGuideButton;

    TextView find_friend_and_me_time, find_friend_and_me_distance;

    public static ArrayList<MapPoint> RouteMapPoint = new ArrayList<>();
    public static ArrayList<String> RouteDescription = new ArrayList<>();
    public static ArrayList<String> RouteIndex = new ArrayList<>();
    public static ArrayList<String> RouteDistance = new ArrayList<>();
    public static ArrayList<String> RouteTurn = new ArrayList<>();
    public Context context;

    private static int mMarkerID;

    private double MyLatitude, MyLongitude, FriendLatitude, FriendLongitude, PolyDistance;
    private String temp = "";
    private Location location;
    private TMapView tMapView = null;
    private String coordinateTeamp = "";
    private static ArrayList<MapPoint> m_mapPoint = new ArrayList<>();
    private ArrayList<MapPoint> FriendPoint = new ArrayList<>();
    private ArrayList<String> mArrayMakerID = new ArrayList<>();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference lo = db.getReference().child("Location");
    private String TAG = "FindFriendAndMePathActivity";



    TMapPolyLine tMapPolyLine = new TMapPolyLine();
    TMapPoint start, end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_and_me_path);

        StartGuideButton = findViewById(R.id.StartGuideButton);
        find_friend_and_me_time = findViewById(R.id.Find_Friend_and_me_Time);
        find_friend_and_me_distance = findViewById(R.id.Find_Friend_and_me_Distance);


        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        TMapData tMapData = new TMapData();
        tMapView = new TMapView(this);
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
        /* 내 위도,경도 불러오기 */
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        MyLatitude = location.getLatitude();
        MyLongitude = location.getLongitude();

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    MyLatitude = location.getLatitude();
                    MyLongitude = location.getLongitude();
                }
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        Log.d(TAG, String.valueOf(MyLatitude));

        this.tMapView.setCenterPoint(MyLongitude, MyLatitude);

        Intent intent = getIntent();
        FriendId = intent.getExtras().getString("FriendId");
        FriendLatitude = intent.getExtras().getDouble("FriendLatitude");
        FriendLongitude = intent.getExtras().getDouble("FriendLongitude");

        AddMyPoint();
        AddMarker();

        start = new TMapPoint(MyLatitude, MyLongitude);
        end = new TMapPoint(FriendLatitude, FriendLongitude);

        Intent StartRoutintent = new Intent(FindFriendAndMePathActivity.this, StartGuideActivity.class);

        StartRoutintent.putExtra("RouteMapPoint", RouteMapPoint);
        StartRoutintent.putExtra("RouteDescription", RouteDescription);
        StartRoutintent.putExtra("RouteIndex", RouteIndex);
        StartRoutintent.putExtra("RouteDistance", RouteDistance);
        StartRoutintent.putExtra("RouteTurn", RouteTurn);

        StartGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(StartRoutintent);

            }
        });

        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                String temp = "";
                Element root = document.getDocumentElement();
                int count = 0;
                    /*Placemark는 point와 LineString로 구분됩니다. point의 경우,
                    각 좌표의 구간정보 LineString의 경우, 경로의 좌표정보가 결과값으로 나옵니다.*/
                NodeList nodeListPlacemark = root.getElementsByTagName("Point");

                for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                    NodeList nodeListPlaceMarkItem = nodeListPlacemark.item(i).getChildNodes();
                    //Log.d(TAG, nodeListPlacemark.item(i).getTextContent().trim());
                    for (int j = 0; j < nodeListPlaceMarkItem.getLength(); j++) {
                        if (nodeListPlaceMarkItem.item(j).getNodeName().equals("coordinates")) {
                            /* 좌표값을 RouteGuide에 저장하는 것 */
                            temp = nodeListPlaceMarkItem.item(j).getTextContent().trim();
                            //Log.d(TAG, temp);
                            String[] temp1 = temp.split(" ");
                            for (int k = 0; k < temp1.length; k++) {
                                int idx = temp1[k].indexOf(",");
                                int idx2 = temp1[k].length();
                                String TempLatitude, TempLongitude;
                                double finalLatitude, finalLongitude;

                                TempLatitude = temp1[k].substring(idx + 1, idx2);
                                TempLongitude = temp1[k].substring(0, idx);

                                finalLatitude = Double.parseDouble(TempLatitude);
                                finalLongitude = Double.parseDouble(TempLongitude);
                                coordinateTeamp += TempLatitude + "," + TempLongitude + "\n";
                                count++;
                                RouteMapPoint.add(new MapPoint("Point" + " " + count, finalLatitude, finalLongitude));
                            }
                        }

                    }
                }
            }
        });

        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end,
                new TMapData.FindPathDataListenerCallback() {
                    @Override
                    public void onFindPathData(TMapPolyLine tMapPolyLine) {
                        tMapView.addTMapPath(tMapPolyLine);
                    }
                });

        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataAllListenerCallback() {

            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();

                int length = root.getElementsByTagName("Placemark").getLength();
                for(int i=0; i<length; i++) {
                    Node placemark = root.getElementsByTagName("Placemark").item(i);
                    Node descriptionNode = ((Element) placemark).getElementsByTagName("description").item(0);
                    Node nodeType = ((Element) placemark).getElementsByTagName("tmap:nodeType").item(0);

                    String nodetype = nodeType.getTextContent();

                    if (nodeType.getTextContent().equals("LINE")) {
                        Node distance = ((Element) placemark).getElementsByTagName("tmap:distance").item(0);
                        RouteDistance.add(distance.getTextContent().trim()); //포인트 간 거리
                    }
                    else if(nodeType.getTextContent().equals("POINT")){
                        Node turn = ((Element) placemark).getElementsByTagName("tmap:turnType").item(0);
                        RouteTurn.add(turn.getTextContent().trim()); //회전정보
                    }
                    RouteDescription.add(descriptionNode.getTextContent().trim()); //경로 설명
                }



            }
        });

        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataAllListenerCallback() {
            public void onFindPathDataAll(Document document) {
                String dis = "";
                String time = "";
                int num = 0;
                double temp;
                double dtemp;
                NodeList nodeListPlacemark = document.getDocumentElement().getElementsByTagName("Document");
                for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                    NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                    for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                        if (nodeListPlacemarkItem.item(j).getNodeName().equals("tmap:totalDistance")) {
                            Log.d("Distance", nodeListPlacemarkItem.item(j).getTextContent().trim());
                            dis = dis + nodeListPlacemarkItem.item(j).getTextContent().trim();
                        }
                        if (nodeListPlacemarkItem.item(j).getNodeName().equals("tmap:totalTime")) {
                            Log.d("Time", nodeListPlacemarkItem.item(j).getTextContent().trim());
                            time = time + nodeListPlacemarkItem.item(j).getTextContent().trim();
                            num = Integer.parseInt(time) / 60;
                        }
                    }
                }
                find_friend_and_me_time.setText(num + "분");
                temp = Integer.parseInt(dis);
                dtemp = temp/1000;
                dis = String.format("%.1f", dtemp);
                find_friend_and_me_distance.setText(dis + "km");
            }
        });

        ((LinearLayout) findViewById(R.id.FriendAndMePath)).addView(tMapView);

    }

    public void AddMyPoint() {
        this.m_mapPoint.add(new MapPoint("My", MyLatitude, MyLongitude));
        this.m_mapPoint.add(new MapPoint("Friend", FriendLatitude, FriendLongitude));
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
            item1.setAutoCalloutVisible(true);
            int i2 = mMarkerID;
            mMarkerID = i2 + 1;
            String stdID = String.format("pMaker%d", new Object[]{Integer.valueOf(i2)});
            this.tMapView.addMarkerItem(stdID, item1);
            this.mArrayMakerID.add(stdID);

            PolyDistance = tMapPolyLine.getDistance();

        }
    }
}