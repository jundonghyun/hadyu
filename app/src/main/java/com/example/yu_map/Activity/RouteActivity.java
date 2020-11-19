package com.example.yu_map.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
//import com.example.yumap_tmap.R;
import com.example.yu_map.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RouteActivity extends AppCompatActivity {
    double finish_Latitude;
    double finish_Longitude;
    double start_Latitude;
    double start_Longitude;

    TextView Time, Distance;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_route);
        TMapData tMapData = new TMapData();
        final TMapView tMapView = new TMapView(this);
        Time = findViewById(R.id.Route_Activity_time);
        Distance = findViewById(R.id.Route_Activity_distance);
        Intent intent = getIntent();
        this.start_Latitude = intent.getExtras().getDouble("start_Latitude");
        this.start_Longitude = intent.getExtras().getDouble("start_Longitude");
        this.finish_Latitude = intent.getExtras().getDouble("finish_Latitude");
        this.finish_Longitude = intent.getExtras().getDouble("finish_Longitude");
        tMapView.setSKTMapApiKey("l7xxe7ffbe0e991b4d41881ad8b70d4e1cc6");
        tMapView.setCenterPoint(128.75444d, 35.830629d);
        TMapPoint tMapPointStart = new TMapPoint(this.start_Latitude, this.start_Longitude);
        TMapPoint tMapPointEnd = new TMapPoint(this.finish_Latitude, this.finish_Longitude);
        ((LinearLayout) findViewById(R.id.mapview)).addView(tMapView);
        try {
            Document document = tMapData.findPathDataAll(tMapPointStart, tMapPointEnd);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
        } catch (SAXException e3) {
            e3.printStackTrace();
        }
        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd, new TMapData.FindPathDataListenerCallback() {
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapView.addTMapPath(tMapPolyLine);
            }
        });
        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, tMapPointStart, tMapPointEnd, new TMapData.FindPathDataAllListenerCallback() {
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
                Time.setText(num + "분");
                temp = Integer.parseInt(dis);
                dtemp = temp/1000;
                dis = String.format("%.1f", dtemp);
                Distance.setText(dis + "km");
            }
        });
    }
}