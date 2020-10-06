package com.example.yu_map.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
//import com.example.yumap_tmap.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivitiy";
    private Button Login, Map, FriendLocation, FriendRequestCheck;
    private final int MY_PERMISSION_REQUEST_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    private String request;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);

        Login = findViewById(R.id.loginbutton);
        Map = findViewById(R.id.map);
        FriendLocation = findViewById(R.id.FriendLocationButton);
        FriendRequestCheck = findViewById(R.id.CheckFriendRequest);

        FriendRequestCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmRequest();
            }
        });

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

        ConfirmRequest();
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

    public void ConfirmRequest() {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase Fdb = FirebaseDatabase.getInstance();

        DocumentReference ref = db.collection("User").document(Email);

        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot docsnap = task.getResult();
                    if (docsnap != null) {
                        request = docsnap.getString("FriendRequest");

                        if(request.equals("false")){
                            Log.d(TAG, "친구요청이 없습니다");

                        }
                        else{
                            Log.d(TAG, "친구요청이 있습니다");

                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                            builder.setTitle("친구요청이 있습니다");
                            builder.setPositiveButton("수락하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(HomeActivity.this, "친구요청이 수락되었습니다",Toast.LENGTH_LONG).show();

                                    final DatabaseReference addFriend = Fdb.getReference().child("FriendList");

                                    int idx = Email.indexOf("@");
                                    int idx1 = request.indexOf("@");

                                    final String NickName = Email.substring(0, idx);
                                    final String NickName1 = request.substring(0, idx1);

                                    addFriend.child(NickName).push().setValue(NickName1);



                                    ref.update("FriendRequest", "false")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "FriendRequest update true to false");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Error Update Document");
                                                }
                                            });

                                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));

                                }
                            });


                            builder.setNegativeButton("거부하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(HomeActivity.this, "친구요청이 거부되었습니다.",Toast.LENGTH_LONG).show();

                                    ref.update("FriendRequest", "false")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "FriendRequest update true to false");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Error Update Document");
                                                }
                                            });
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
            }
        });

    }

}