package com.example.fastfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.fastfoodie.database.Database;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class LocationSignUpActivity extends AppCompatActivity {
  private MapView mMapView;
  private TextInputEditText inputAddress, inputNote;
  private String labelText;
  private Button btn_location;

  // Selection buttons
  private MaterialButton btnHome, btnWork, btnOther;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Mapbox.getInstance(this, getString(R.string.access_token));
    setContentView(R.layout.activity_location_sign_up);
    getIds();
    mMapView.onCreate(savedInstanceState);

    mMapView.getMapAsync(
        new OnMapReadyCallback() {
          @Override
          public void onMapReady(MapboxMap mapboxMap) {
            mapboxMap.setStyle(Style.LIGHT);
            // Zoom to current location of user

            mapboxMap.addMarker(
                new MarkerOptions().position(new LatLng(28.4964, 77.1394)).title("Place"));

            mapboxMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                        .target(new LatLng(28.4964, 77.1394))
                        .zoom(10)
                        .build()),
                5000);
            mapboxMap.getUiSettings().setAttributionEnabled(false);
            mapboxMap.getUiSettings().setLogoEnabled(false);
          }
        });

    labelButtonClickEvent();

    // Validate address and note on click "Save Location"
    btn_location.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String address = inputAddress.getText().toString();
            String note = inputNote.getText().toString();

            // Save to database
            Database _db = Database.getDatabaseInstance();
            _db.saveLocationAtSignUp(address, note, labelText);
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

  void labelButtonClickEvent() {

    // When home button is clicked
    btnHome.setOnClickListener(
        new View.OnClickListener() {
          @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
          @Override
          public void onClick(View view) {
            btnHome.setBackgroundTintList(
                getResources().getColorStateList(R.color.google_accent_color));
            btnHome.setTextColor(getResources().getColorStateList(R.color.white));
            labelText = btnHome.getText().toString();
          }
        });

    btnWork.setOnClickListener(
            new View.OnClickListener() {
              @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
              @Override
              public void onClick(View view) {
                btnWork.setBackgroundTintList(
                        getResources().getColorStateList(R.color.google_accent_color));
                btnWork.setTextColor(getResources().getColorStateList(R.color.white));
                labelText = btnWork.getText().toString();
              }
            });

    btnOther.setOnClickListener(
            new View.OnClickListener() {
              @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
              @Override
              public void onClick(View view) {
                btnOther.setBackgroundTintList(
                        getResources().getColorStateList(R.color.google_accent_color));
                btnOther.setTextColor(getResources().getColorStateList(R.color.white));
                labelText = btnOther.getText().toString();
              }
            });
  }

  void getIds() {
    mMapView = (MapView) findViewById(R.id.map_set_location);
    inputAddress = findViewById(R.id.input_address);
    inputNote = findViewById(R.id.input_note);
    btn_location = findViewById(R.id.submit_location);
    btnHome = findViewById(R.id.btn_home);
    btnWork = findViewById(R.id.btn_work);
    btnOther = findViewById(R.id.btn_other);
  }
}
