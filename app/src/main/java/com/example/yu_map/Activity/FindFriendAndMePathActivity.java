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
import android.os.Bundle;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FindFriendAndMePathActivity extends AppCompatActivity {

    Button StartGuideButton;
    double MyLatitude, MyLongitude, FriendLatitude, FriendLongitude, PolyDistance;
    private static int mMarkerID;
    private Location location;
    private TMapView tMapView = null;
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<>();
    public ArrayList<MapPoint> RoutGuide_MapPoint = new ArrayList<>();
    private ArrayList<String> mArrayMakerID = new ArrayList<>();
    private ArrayList Coordinates = new ArrayList();
    String FriendId;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    TMapPoint start, end;
    private DatabaseReference lo = db.getReference().child("Location");
    private String TAG = "FindFriendAndMePathActivity";
    TMapPolyLine tMapPolyLine = new TMapPolyLine();


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

        lo.child(FriendId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FriendLatitude = (double) snapshot.child("Latitude").getValue();
                FriendLongitude = (double) snapshot.child("Longitude").getValue();

                addPoint();
                AddMarker();
                //Log.d(TAG, String.valueOf(PolyDistance));

                if(PolyDistance > 1000){
                    Toast.makeText(FindFriendAndMePathActivity.this, "직선거리가 1km가 넘습니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    start = new TMapPoint(MyLatitude, MyLongitude);
                    end = new TMapPoint(FriendLatitude, FriendLongitude);
                    /*try{
                        Document document = tMapData.findPathDataAll(start, end);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataListenerCallback() {
                        public void onFindPathData(TMapPolyLine tMapPolyLine) {
                            tMapView.addTMapPath(tMapPolyLine);
                        }
                    });*/

                    tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataAllListenerCallback() {
                        @Override
                        public void onFindPathDataAll(Document document) {
                            String temp = "";
                            Element root = document.getDocumentElement();
                            int count = 0;
                            /*Placemark는 point와 LineString로 구분됩니다. point의 경우,
                            각 좌표의 구간정보 LineString의 경우, 경로의 좌표정보가 결과값으로 나옵니다.*/
                            NodeList nodeListPlacemark = root.getElementsByTagName("LineString");
                            for(int i = 0; i < nodeListPlacemark.getLength(); i++){
                                NodeList nodeListPlaceMarkItem = nodeListPlacemark.item(i).getChildNodes();
                                //Log.d(TAG, nodeListPlacemark.item(i).getTextContent().trim());
                                for(int j = 0; j < nodeListPlaceMarkItem.getLength(); j++){
                                    if(nodeListPlaceMarkItem.item(j).getNodeName().equals("coordinates")){
                                        /* 좌표값을 RouteGuide에 저장하는 것 */
                                        temp = nodeListPlaceMarkItem.item(j).getTextContent().trim();
                                        String[] temp1 = temp.split(" ");
                                        for(int k = 0; k < temp1.length; k++){
                                            int idx = temp1[k].indexOf(",");
                                            int idx2 = temp1[k].length();
                                            String TempLatitude, TempLongitude;
                                            double finalLatitude, finalLongitude;

                                            TempLatitude = temp1[k].substring(idx+1, idx2);
                                            TempLongitude = temp1[k].substring(0, idx);

                                            finalLatitude = Double.parseDouble(TempLatitude);
                                            finalLongitude = Double.parseDouble(TempLongitude);
                                            RoutGuide_MapPoint.add(new MapPoint("Point" +" "+ count, finalLatitude, finalLongitude));

                                        }
                                    }
                                }
                            }
                        }
                    });

                    tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end,
                            new TMapData.FindPathDataListenerCallback(){
                                @Override
                                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                                    tMapView.addTMapPath(tMapPolyLine);
                                }
                            });

                    tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataAllListenerCallback() {

                        @Override
                        public void onFindPathDataAll(Document document) {
                            Element root = document.getDocumentElement();
                            NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");

                            for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                                NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                                for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                                    if (nodeListPlacemarkItem.item(j).getNodeName().equals("description")) {
                                        Log.d(TAG, nodeListPlacemarkItem.item(j).getTextContent().trim());
                                    }
                                }
                            }
                        }
                    });




                    Toast.makeText(FindFriendAndMePathActivity.this, "직선거리가 1km 이하입니다",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        StartGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindFriendAndMePathActivity.this, StartGuideActivity.class));
            }
        });


        ((LinearLayout) findViewById(R.id.FriendAndMePath)).addView(tMapView);

    }

    public void addPoint(){
        this.m_mapPoint.add(new MapPoint("My", MyLatitude, MyLongitude));
        this.m_mapPoint.add(new MapPoint("Friend", FriendLatitude, FriendLongitude));
    }

    public void AddMarker(){
        for(int i = 0; i < this.m_mapPoint.size(); i++){
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

}