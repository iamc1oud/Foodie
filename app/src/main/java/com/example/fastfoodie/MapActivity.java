package com.example.fastfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiY2xvdWRtYXgiLCJhIjoiY2tmejRiOTE4MDdoMTJ6cGRrcmJocHAzbCJ9.LJjV1aK-18AFc_fTFmomSQ");
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.map_view);

        mMapView.onCreate(savedInstanceState);

//    mMapView.getMapAsync(
//        new OnMapReadyCallback() {
//          @Override
//          public void onMapReady(MapboxMap mapboxMap) {
//            mapboxMap.setStyle(Style.MAPBOX_STREETS);
//          }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}