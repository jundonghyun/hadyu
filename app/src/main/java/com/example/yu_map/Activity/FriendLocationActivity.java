package com.example.yu_map.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yu_map.R;
import com.example.yu_map.Recycler.FriendData;
import com.example.yu_map.Recycler.FriendsListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
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


import java.util.HashMap;
import java.util.Map;

public class FriendLocationActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private GoogleMap map;
    private final int MY_PERMISSION_REQUEST_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private String TAG = "FriendLocationActivity";
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private Double Latitude, Longitude;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference dr = db.getReference().child("FindFriend");
    private DatabaseReference lo = db.getReference().child("Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_location);



        MapFragment mapFragment = (MapFragment) this.getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(FriendLocationActivity.this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        GetLastLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        /* dr은 Realtime DB에서 FindFriend필드의 snapshot을 찍게하는 변수 */
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* FindFriend필드에서 친구아이디를 가져와서 id변수에 삽입 */
               String id = snapshot.getValue().toString();

               /* lo는 Location필드에서 id변수에있는 아이디를 사용해 Location필드안에있는 id의 Latitude와 Longitude를 가져옴
               *  Location -> "ID" -> Latitude, Longitude */
               lo.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       double Long = (double) snapshot.child("Longitude").getValue();
                       double Lang = (double) snapshot.child("Latitude").getValue();

                       map = googleMap;

                       setUpMap();


                        /* 구글맵이 열리면 친구의 위치로 바로 이동되고 카메라도 자동으로 확대됨 */
                       LatLng FriendLocation = new LatLng(0+Lang, 0+Long);

                       CameraPosition cameraPosition = new CameraPosition.Builder().target(FriendLocation)
                               .zoom(14f).build();

                       MarkerOptions markerOptions = new MarkerOptions();
                       markerOptions.position(FriendLocation);
                       markerOptions.title(id);
                       map.addMarker(markerOptions);

                       map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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



    }

    private void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
        map.setMyLocationEnabled(true);
    }

    public void GetLastLocation() {

        final Map<String, Double> User = new HashMap<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


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

                            db.collection("UserLocation")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){

                                                /* 새로운 UserLocation생성 */
                                                if(task.getResult().size() > 0){
                                                    User.put("Latitude", geoPoint.getLatitude());
                                                    User.put("Longitude", geoPoint.getLongitude());

                                                    DocumentReference newUserRef = db
                                                            .collection("UserLocation")
                                                            .document(Email);

                                                    newUserRef.set(User)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    Log.d(TAG, "GeoPoint successfully Written");

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {

                                                                    Log.d(TAG, "GeoPoint Writing Failure");

                                                                }
                                                            });
                                                }
                                            }
                                            /* email로 시작하는 UserLocation이 있는경우 */

                                            else{
                                                DocumentReference newUserLocationRef = db
                                                        .collection("UserLocation")
                                                        .document(Email);

                                                newUserLocationRef
                                                        .update("Longitude", geoPoint.getLongitude())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot added");

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d(TAG, "Error adding document", e);

                                                            }
                                                        });

                                                newUserLocationRef
                                                        .update("Latitude", geoPoint.getLatitude())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "DocumentSnapshot added");

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d(TAG, "Error adding document", e);
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });

        /*firestore에서 위도와 경도 받아옴*/
        DocumentReference U_location = db.collection("UserLocation").document(Email);
        U_location.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Latitude = documentSnapshot.getDouble("Latitude");
                Longitude = documentSnapshot.getDouble("Longitude");
                Log.d(TAG, Latitude+" "+Longitude);
            }
        });
    }


}