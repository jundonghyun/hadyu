package com.example.yu_map.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.FriendData;
import com.example.yu_map.Recycler.FriendsListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.yu_map.Recycler.FriendsListAdapter;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;


import java.util.HashMap;
import java.util.Map;

public class FriendLocationActivity extends AppCompatActivity {

    private GoogleMap map;
    private final int MY_PERMISSION_REQUEST_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private String TAG = "FriendLocationActivity";
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private Location location;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference().child("FindFriend");
    private DatabaseReference lo = db.getReference().child("Location");
    public double Long, Lang;
    private double MyLatitude, MyLongitude, Dis;
    public String id;
    private TMapView tMapView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_location);

        Context context;


        TMapData tMapData = new TMapData();
        tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xxe7ffbe0e991b4d41881ad8b70d4e1cc6");

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* FindFriend필드에서 친구아이디를 가져와서 id변수에 삽입 */
                id = snapshot.getValue().toString();


                /* lo는 Location필드에서 id변수에있는 아이디를 사용해 Location필드안에있는 id의 Latitude와 Longitude를 가져옴
                 *  Location -> "ID" -> Latitude, Longitude */
                lo.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Long = (double) snapshot.child("Longitude").getValue();
                        Lang = (double) snapshot.child("Latitude").getValue();

                        tMapView.setCenterPoint(Long, Lang);


                        addPoint();

                        ((LinearLayout) findViewById(R.id.FriendLocationMapView)).addView(tMapView);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        GetLastLocation();
    }

    public boolean onCreateOptionsMenu(Menu menu1) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu1);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Location endPoint = new Location("end");
        endPoint.setLatitude(Lang);
        endPoint.setLongitude(Long);

        if(location == null){
            Toast.makeText(this, "현재위치를 찾을 수 없습니다",Toast.LENGTH_SHORT).show();
        }
        else{
            double PointDis = location.distanceTo(endPoint);
            Dis = Double.parseDouble(String.format("%.1f", PointDis));
        }



            switch(item.getItemId()){
                case R.id.SearchFriendPath:

                    Intent intent = new Intent(this, FindFriendAndMePathActivity.class);
                    intent.putExtra("FriendId", id);
                    intent.putExtra("FriendLatitude", Lang);
                    intent.putExtra("FriendLongitude", Long);
                    if(Dis > 1000){
                        Toast.makeText(this, "직선거리가 1Km가 넘습니다",Toast.LENGTH_LONG).show();
                        break;
                    }
                    else if(location == null){
                        Toast.makeText(this, "현재위치를 찾을 수 없습니다",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        startActivity(intent);

                    }
                    break;
            }

        return super.onOptionsItemSelected(item);
    }


    public void addPoint(){
        TMapPoint point = new TMapPoint(Lang, Long);
        TMapMarkerItem item1 = new TMapMarkerItem();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.poi_dot);


        item1.setTMapPoint(point);
        item1.setName(id);
        item1.setVisible(2);
        item1.setIcon(bitmap);
        item1.setCalloutTitle(id);
        item1.setCanShowCallout(true);

        this.tMapView.addMarkerItem(id, item1);
    }

    public void GetLastLocation() {

        /*위치 권환 확인*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_LOCATION);
            }
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            final GeoPoint geoPoint = new GeoPoint(
                                    location.getLatitude(), location.getLongitude()
                            );

                            FirebaseDatabase fdb = FirebaseDatabase.getInstance();
                            final DatabaseReference Location = fdb.getReference().child("Location");

                            int idx = Email.indexOf("@");
                            final String NickName = Email.substring(0, idx);

                            Location.child(NickName).child("Longitude").setValue(geoPoint.getLongitude());
                            Location.child(NickName).child("Latitude").setValue(geoPoint.getLatitude());
                        }
                    }
                });
    }


}