package com.example.yu_map.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.yu_map.Activity.TimeTable.TimeTableActivity;
import com.example.yu_map.HyunSeol.HyunseolActivity;
import com.example.yu_map.R;
import com.example.yu_map.Recycler.FriendRequestQueueActivity;
import com.example.yu_map.Recycler.FriendsListActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
//import com.example.yumap_tmap.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivitiy";
    private final int MY_PERMISSION_REQUEST_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView drawer_header_username, drawer_header_welcome;
    private Button test;
    private ImageView Qr;
    private String Email = ((LoginActivity) LoginActivity.context).GlobalEmail;
    static private Context context;
    private Location location;
    private String request;
    private String schoolnum_temp;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_home);

        int idx = Email.indexOf("@");
        final String NickName = Email.substring(0, idx);


        navigationView = findViewById(R.id.home_navigation_view);
        drawerLayout = findViewById(R.id.home_drawer);
        test = findViewById(R.id.test);
        Qr = findViewById(R.id.SchoolnumQRcode);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HyunseolActivity.class));
            }
        });

        ShowQRcode(Email);


        View heaer = navigationView.getHeaderView(0);
        drawer_header_username = heaer.findViewById(R.id.home_navigation_drawer_header_username);
        drawer_header_welcome = heaer.findViewById(R.id.home_navigation_drawer_header_welcome);
        drawer_header_welcome.setText("환영합니다");
        drawer_header_username.setText(NickName+"님");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                

                switch (menuItem.getItemId()){
                    case R.id.home_navigation_menu_map:
                        startActivity(new Intent(HomeActivity.this.getApplicationContext(), MainActivity.class));
                        break;

                    case R.id.home_navigation_menu_timetable:
                        startActivity(new Intent(HomeActivity.this, TimeTableActivity.class));
                        break;

                    case R.id.home_navigation_menu_friendrequestcheck:
                        startActivity(new Intent(HomeActivity.this, FriendRequestQueueActivity.class));
                        break;

                    case R.id.home_navigation_menu_friendlist:
                        startActivity(new Intent(HomeActivity.this, FriendsListActivity.class));
                        break;

                    case R.id.home_navigation_menu_logout:
                        moveTaskToBack(true); //1단계 태스크 백그라운드로 이동
                        finishAndRemoveTask(); // 2단계 액티비티종료 + 태스크 리스트에서 지우기
                        android.os.Process.killProcess(android.os.Process.myPid()); // 3단계 앱 프로세스 종료
                        break;
                }

                drawerLayout.closeDrawer(navigationView);

                return false;
            }
        });

        //Drawer 조절용 토글 버튼 객체 생성
        barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //실선아이콘 모양으로 보이도록
        //토글버튼의 동기를 맞추기
        barDrawerToggle.syncState();
        // 실선 아이콘과 화살표아이콘이 자동으로 전환되도록
        drawerLayout.addDrawerListener(barDrawerToggle);

        ConfirmRequest();

        //UpdeateLocation();

    }

    private void ShowQRcode(String email){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newUserRef = db
                .collection("User")
                .document(email);
        newUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.getString("학번") != null){
                    schoolnum_temp = documentSnapshot.getString("학번");

                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(schoolnum_temp, BarcodeFormat.QR_CODE, 100, 100);
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        Qr.setImageBitmap(bitmap);
                    }catch (Exception e){}
                }
                else{
                    Toast.makeText(HomeActivity.this, "학번이 등록되지않았습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdeateLocation() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final DatabaseReference Location = fdb.getReference().child("Location");
        int idx = Email.indexOf("@");
        final String NickName = Email.substring(0, idx);

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

        Location.child(NickName).child("Longitude").setValue(location.getLatitude());
        Location.child(NickName).child("Latitude").setValue(location.getLongitude());


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        barDrawerToggle.onOptionsItemSelected(item);

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

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /* 친구요청확인 하는 함수 */
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

                            /* 친구요청있으면 팝업 띄우는 빌더 */

                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                            builder.setTitle(request+"님의"+"친구요청이 있습니다");
                            builder.setPositiveButton("수락하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(HomeActivity.this, "친구요청이 수락되었습니다",Toast.LENGTH_LONG).show();

                                    /* 친구요청을 수락하면 Realtime DB에 요청한 친구 아이디가 요청받은 친구 DB에 생성됨 */
                                    final DatabaseReference addFriend = Fdb.getReference().child("FriendList");

                                    int idx = Email.indexOf("@"); /* 요청받은 친구 ID*/
                                    int idx1 = request.indexOf("@"); /* 요청한 친구 ID */

                                    final String NickName = Email.substring(0, idx);
                                    final String NickName1 = request.substring(0, idx1);

                                    addFriend.child(NickName).child(NickName1).setValue(NickName1);


                                    /* 친구요청을 완료하면 다시 대기모드로 전환 */
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

                            /* 친구요청을 거부했을 경우 */
                            builder.setNegativeButton("거부하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(HomeActivity.this, "친구요청이 거부되었습니다.",Toast.LENGTH_LONG).show();

                                    /* 친구요청을 거부하면 요청한 아이디의 DB에 요청한 친구 ID를 넣고 거부되었다고 저장*/
                                    final FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    java.util.Map<String, String> User = new HashMap<>();

                                    User.put("FriendReject", request);


                                    DocumentReference newUserRef = db
                                            .collection("User")
                                            .document(request);

                                    newUserRef.set(User, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "Document successfully Written");
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Document Writing Failure");
                                                }
                                            });

                                    /* 요청을 거부한 친구의 DB에 다시 친구요청 대기모드로 전환하게함 */
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

                                    /*친구를 요청한 Realtime DB에 요청했었던 친구 ID를 뺌*/

                                    int idx = Email.indexOf("@"); /* 요청받은 친구 ID*/
                                    int idx1 = request.indexOf("@"); /* 요청한 친구 ID */

                                    final String NickName = Email.substring(0, idx);
                                    final String NickName1 = request.substring(0, idx1);

                                    final DatabaseReference DeleteFriend = Fdb.getReference().child("FriendList").child(NickName1).child("ID");



                                    Query query = DeleteFriend.equalTo(NickName);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            snapshot.getRef().removeValue();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

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