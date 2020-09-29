package com.example.yu_map.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.yu_map.AddFriendActivity;
import com.example.yu_map.Recycler.FriendActivity;
import com.example.yu_map.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
//import com.example.yumap_tmap.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivitiy";
    private Button Login, Map, FriendLocation;
    private final int MY_PERMISSION_REQUEST_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);

        Login = findViewById(R.id.loginbutton);
        Map = findViewById(R.id.map);
        FriendLocation = findViewById(R.id.FriendLocationButton);

        FriendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,FriendLocationActivity.class));

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
        Map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HomeActivity.this.startActivity(new Intent(HomeActivity.this.getApplicationContext(), MainActivity.class));
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG );

        switch (item.getItemId()){
            case R.id.Logout:
                Toast.makeText(HomeActivity.this, "YuApp이 종료되었습니다", Toast.LENGTH_LONG).show();

                /* 어플 종료 방법 */

                moveTaskToBack(true); //1단계 태스크 백그라운드로 이동
                finishAndRemoveTask(); // 2단계 액티비티종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid()); // 3단계 앱 프로세스 종료
                Log.d(TAG, "application shutdown");

                break;

            case R.id.Location:


                final java.util.Map<String, Double> User = new HashMap<>();
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
                break;

            case R.id.FriendBtn:
                startActivity(new Intent(HomeActivity.this, AddFriendActivity.class));

                break;
        }
        toast.show();

        return super.onOptionsItemSelected(item);
    }

}