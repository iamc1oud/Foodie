package com.example.fastfoodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodie.fragments.LocationSearchFragment;
import com.example.fastfoodie.models.FirebaseUserModel;
import com.example.fastfoodie.services.LocationTrackService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapzen.android.lost.api.FusedLocationProviderApi;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapViewActivity extends AppCompatActivity {
  private MapView mMapView;
  private TextView latText;
  private TextView longText;
  LocationManager locationManager;
    Location location;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Mapbox.getInstance(
        this,
        "pk.eyJ1IjoiY2xvdWRtYXgiLCJhIjoiY2tmejRiOTE4MDdoMTJ6cGRrcmJocHAzbCJ9.LJjV1aK-18AFc_fTFmomSQ");
    setContentView(R.layout.activity_map_view);
    setMapPreferences(savedInstanceState);
    ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION}, 1);
    latText = findViewById(R.id.lat_location);
    longText = findViewById(R.id.long_location);

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    if (ActivityCompat.checkSelfPermission(
                MapViewActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
                MapViewActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
          this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    } else {
      location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      if (location != null) {
        latText.setText(String.valueOf(location.getLatitude()));
        longText.setText(String.valueOf(location.getLongitude()));
      } else {
        Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void setMapPreferences(Bundle savedInstanceState) {
    mMapView = (MapView) findViewById(R.id.main_view_map);

    mMapView.onCreate(savedInstanceState);

    mMapView.getMapAsync(
        new OnMapReadyCallback() {
          @Override
          public void onMapReady(MapboxMap mapboxMap) {
            mapboxMap.setStyle(Style.MAPBOX_STREETS);
            mapboxMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(10)
                        .tilt(45.0)
                        .build()),
                10000);
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
}
