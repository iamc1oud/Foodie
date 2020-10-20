package com.example.fastfoodie;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.Calendar;

public class MapViewActivity extends AppCompatActivity implements LocationListener {
    private MapView mMapView;
    private TextView latText;
    private TextView longText;
    private double latitude, longitude;
    LocationManager locationManager;

    private MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private LocationManager mLocationManager;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(
                this,
                "pk.eyJ1IjoiY2xvdWRtYXgiLCJhIjoiY2tmejRiOTE4MDdoMTJ6cGRrcmJocHAzbCJ9.LJjV1aK-18AFc_fTFmomSQ");
        setContentView(R.layout.activity_map_view);
        setMapPreferences(savedInstanceState);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getApplicationContext(),
                        String.format("%f",location.getLatitude()),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        final LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // This is the Best And IMPORTANT part
        final Looper looper = null;

        locationManager.requestSingleUpdate(criteria, locationListener, looper);
    }

    private void setMapPreferences(Bundle savedInstanceState)
    {
        mMapView = (MapView) findViewById(R.id.main_view_map);

        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        mapboxMap.setStyle(Style.MAPBOX_STREETS);
                        mapboxMap.addMarker(new MarkerOptions().setPosition(new LatLng(latitude, longitude))
                        .title("You are here!"));
                        mapboxMap.animateCamera(
                                CameraUpdateFactory.newCameraPosition(
                                        new CameraPosition.Builder()
                                                .target(new LatLng(latitude, longitude))
                                                .zoom(10)
                                                .build()),
                                5000);

                        mapboxMap.getUiSettings().setAttributionEnabled(false);
                        mapboxMap.getUiSettings().setLogoEnabled(false);
                    }
                });
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

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Toast.makeText(getApplicationContext(), "Location Changed " + location.getLatitude() + " and " + location.getLongitude(), Toast.LENGTH_LONG).show();
            // You need to call this whenever you are done:
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
