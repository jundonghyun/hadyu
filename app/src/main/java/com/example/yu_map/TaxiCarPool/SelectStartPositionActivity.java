package com.example.yu_map.TaxiCarPool;

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
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yu_map.R;
import com.skt.Tmap.TMapAddressInfo;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class SelectStartPositionActivity extends AppCompatActivity implements TMapView.OnClickListenerCallback {

    private TMapView tMapView = null;
    private Location location;
    private double mylang, mylong;
    private Context context;
    private String TAG = "SelectStartPositionActivity";
    private TMapData tMapData = new TMapData();
    public static double CarpoolStartLatitude, CarpoolStartLongitude;
    public static String CarpoolStartAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_start_position);



        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xxe7ffbe0e991b4d41881ad8b70d4e1cc6");
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);





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


        if(location != null){
            mylang = location.getLatitude();
            mylong = location.getLongitude();
        }

        /*this.tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                tMapData.reverseGeocoding(CarpoolStartLatitude, CarpoolStartLongitude, "A04", new TMapData.reverseGeocodingListenerCallback() {
                    @Override
                    public void onReverseGeocoding(TMapAddressInfo tMapAddressInfo) {
                        CarpoolStartAddress = tMapAddressInfo.strFullAddress;
                    }
                });
                Toast.makeText(SelectStartPositionActivity.this, "출발지가 설정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectStartPositionActivity.this, SelectedFinishPositionActivity.class));
            }
        });*/

        tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
                TMapPoint tpoint = tMapPoint;
                TMapMarkerItem markerItem1  = new TMapMarkerItem();
                markerItem1 .setTMapPoint(tpoint );
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.poi_dot);
                Bitmap bitmap_1 = BitmapFactory.decodeResource(getResources(), R.mipmap.i_go);
                markerItem1 .setIcon(bitmap);
                markerItem1.setCalloutRightButtonImage(bitmap_1);
                markerItem1 .setPosition(1/2,1);
                markerItem1.setCanShowCallout(true);
                markerItem1.setCalloutTitle("출발지 선택");
                CarpoolStartLatitude = markerItem1.latitude;
                CarpoolStartLongitude = markerItem1.longitude;

                tMapData.reverseGeocoding(CarpoolStartLatitude, CarpoolStartLongitude, "A04", new TMapData.reverseGeocodingListenerCallback() {
                    @Override
                    public void onReverseGeocoding(TMapAddressInfo tMapAddressInfo) {
                        CarpoolStartAddress = tMapAddressInfo.strFullAddress;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                tMapView.addMarkerItem("markerItem1",markerItem1);
            }
        });

        tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                continueDialog();
            }
        });



        this.tMapView.setCenterPoint(mylong, mylang);
        ((LinearLayout)findViewById(R.id.startpointtmapview)).addView(tMapView);
    }

    private void continueDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectStartPositionActivity.this);
        builder.setTitle("선택된 주소는 "+CarpoolStartAddress+"입니다.").setPositiveButton("출발지 설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SelectStartPositionActivity.this, "출발지가 설정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectStartPositionActivity.this, SelectedFinishPositionActivity.class));

            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

        return false;
    }

    @Override
    public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
        return false;
    }
}