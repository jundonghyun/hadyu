package com.example.yu_map.Activity;

import androidx.annotation.NonNull;
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
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yu_map.MapPoint;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
    public static ArrayList<MapPoint> RoutGuide_MapPoint = new ArrayList<>();
    public static ArrayList<String> RouteDescription = new ArrayList<>();
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
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MyLatitude = location.getLatitude();
        MyLongitude = location.getLongitude();

        this.tMapView.setCenterPoint(MyLongitude, MyLatitude);

        Intent intent = getIntent();
        FriendId = intent.getExtras().getString("FriendId");
        FriendLatitude = intent.getExtras().getDouble("FriendLatitude");
        FriendLongitude = intent.getExtras().getDouble("FriendLongitude");

        AddMyPoint();
        AddMarker();

        if(PolyDistance > 1000){
            Toast.makeText(FindFriendAndMePathActivity.this, "직선거리가 1km가 넘습니다", Toast.LENGTH_SHORT).show();
        }
        else{
            start = new TMapPoint(MyLatitude, MyLongitude);
            end = new TMapPoint(FriendLatitude, FriendLongitude);

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
                                    //WirteTextFile(context, Filename, GuideCoordinates);//텍스트파일로 위도,경도를 휴대폰에 저장
                                    RoutGuide_MapPoint.add(new MapPoint("Point" + " " + count, finalLatitude, finalLongitude));

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
                    Log.d(TAG, "Break");
                    Element root = document.getDocumentElement();
                    NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                    for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                        NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                        for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                            if (nodeListPlacemarkItem.item(j).getNodeName().equals("description")) {
                                RouteDescription.add(nodeListPlacemarkItem.item(j).getTextContent().trim());
                                Log.d(TAG, nodeListPlacemarkItem.item(j).getTextContent().trim());
                            }
                        }
                    }
                }
            });


            Toast.makeText(FindFriendAndMePathActivity.this, "직선거리가 1km 이하입니다", Toast.LENGTH_SHORT).show();

        }


        PassPoint_Description();

        StartGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindFriendAndMePathActivity.this, StartGuideActivity.class));
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
            int i2 = mMarkerID;
            mMarkerID = i2 + 1;
            String stdID = String.format("pMaker%d", new Object[]{Integer.valueOf(i2)});
            this.tMapView.addMarkerItem(stdID, item1);
            //this.tMapView.addTMapPolyLine("Line1", tMapPolyLine);
            this.mArrayMakerID.add(stdID);

            PolyDistance = tMapPolyLine.getDistance();

        }

    }


    public void PassPoint_Description(){
        double latitude;
        double longitude;
        String temp;


        for(int i = 0; i < 1; i++){
            latitude = RoutGuide_MapPoint.get(i).getLatitude();
            longitude = RoutGuide_MapPoint.get(i).getLongitude();
        }

        for(int i = 0; i < RouteDescription.size(); i++){
            //latitude = RoutGuide_MapPoint.get(i).getLatitude();
            //longitude = RoutGuide_MapPoint.get(i).getLongitude();
            temp = RouteDescription.get(i);
        }
    }


    public File getPrivateAlbumStorageDir(Context context, String routecoordinates) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), routecoordinates);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}