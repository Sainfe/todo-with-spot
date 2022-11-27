package com.sainfe.todowithspot.view.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.sainfe.todowithspot.databinding.ActivityCreateBinding;
import com.sainfe.todowithspot.view.today.OnSwipeTouchListener;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.view.today.TodayActivity;
import com.sainfe.todowithspot.viewmodel.create.CreateViewModel;

import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity
        implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    ActivityCreateBinding binding;
    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final String TAG = "TodoWithSpot_googleMap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;

    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    Location mCurrentLocation;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create);
        binding.setViewModel(new CreateViewModel());
        binding.executePendingBindings();

        locationRequest = new LocationRequest.Builder(1000L)
                .setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL_MS)
                .setMaxUpdateDelayMillis(UPDATE_INTERVAL_MS)
                .build();

//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(locationRequest);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        binding.backwardButton.setOnClickListener(view -> {
            finish();
        });

        binding.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.leaveButton.setEnabled(true);
                    binding.arriveButton.setEnabled(true);
                } else {
                    binding.leaveButton.setEnabled(false);
                    binding.arriveButton.setEnabled(false);
                }
            }
        });

//        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timePickerListener, 15, 24, false);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding.datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        binding.createLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Intent swipeLeftIntent = new Intent(getApplicationContext(), TodayActivity.class);
                startActivity(swipeLeftIntent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            try {
                Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {

        }
    };

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        setDefaultLocation();

        // 1. location permission check
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // permission 여부에 따른 분기 처리
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            // user가 permission을 거부한 적이 있는지에 따른 분기 처리
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                Snackbar.make(binding.createLayout, R.string.explain_location_permission_need, Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.check), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 확인을 클릭 시 location permission 요청
                                ActivityCompat.requestPermissions(CreateActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                            }
                        }).show();
            } else {
                // user가 permission을 거부한 적이 없다면 location permission 바로 요청
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }


    }

    private void startLocationUpdates() {

    }

    public void setDefaultLocation() { // Default Location 서울로 설정
        LatLng SEOUL = new LatLng(37.56, 126.97);

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(String.valueOf(R.string.unable_get_location_information));
        markerOptions.snippet(String.valueOf(R.string.check_location_permission));
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
    }
}