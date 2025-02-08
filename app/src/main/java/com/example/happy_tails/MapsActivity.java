package com.example.happy_tails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private Button mapsActivityStartDogWalkButton;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private boolean isWalking = false;

    private Marker startMarker;
    private ArrayList<LatLng> mapPoints;
    private PolylineOptions polylineOptions;
    private LatLng mapStartPoint;
    private LatLng mapEndPoint;
    private double totalWalkingDistance = 0;
    private LocalDate currentDate;
    private LocalTime walkingStartTime;
    private LocalTime walkingEndTime;
    private long totalWalkingTime;

    private DatabaseHelper databaseHelper;
    private User user;
    private WalkingActivity walkingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Get user email from previous activity
        Intent previousLoginAreaIntent = getIntent();
        String userEmail = previousLoginAreaIntent.getStringExtra("ownersEmailForPassOn");

        initObjects();
        initViews();
        initListeners();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initViews() {
        mapsActivityStartDogWalkButton = findViewById(R.id.MapsActivityStartDogWalkButton);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        user = new User();
        walkingActivity = new WalkingActivity();

        mapPoints = new ArrayList<>();
        polylineOptions = new PolylineOptions().color(Color.BLUE).width(15);
    }

    private void initListeners() {
        mapsActivityStartDogWalkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.MapsActivityStartDogWalkButton) {
            if (!isWalking) {
                startDogWalk();
            } else {
                endDogWalk();
            }
        }
    }

    private void startDogWalk() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        Toast.makeText(this, "Starting walk...", Toast.LENGTH_SHORT).show();
        isWalking = true;
        mapsActivityStartDogWalkButton.setText("Finish Your Dog Walk");

        fusedLocationProviderClient.requestLocationUpdates(getLocationRequest(), getLocationCallback(), null);
        currentDate = LocalDate.now();
        walkingStartTime = LocalTime.now();
    }

    private void endDogWalk() {
        Toast.makeText(this, "Ending walk...", Toast.LENGTH_SHORT).show();
        isWalking = false;
        mapsActivityStartDogWalkButton.setText("Start Your Dog Walk");

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        walkingEndTime = LocalTime.now();
        totalWalkingTime = Duration.between(walkingStartTime, walkingEndTime).toMinutes();

        Intent previousMainAreaIntent = getIntent();
        String userEmail = previousMainAreaIntent.getStringExtra("ownersEmailForPassOn");

        user = databaseHelper.getSpecificUser(userEmail);
        walkingActivity.setUserID(user.getUserID());
        walkingActivity.setDog(user.getDogName());
        walkingActivity.setWalkingDate(currentDate);
        walkingActivity.setWalkingStartTime(walkingStartTime);
        walkingActivity.setWalkingEndTime(walkingEndTime);
        walkingActivity.setTotalWalkingTime(totalWalkingTime);
        walkingActivity.setTotalWalkingDistance(totalWalkingDistance);
        walkingActivity.setWalkingDescription("Walk completed.");
        databaseHelper.addWalkingActivity(walkingActivity);

        mMap.clear();
    }

    private LocationRequest getLocationRequest() {
        return LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private LocationCallback getLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                for (Location location : locationResult.getLocations()) {
                    LatLng newPoint = new LatLng(location.getLatitude(), location.getLongitude());

                    if (mapStartPoint == null) {
                        mapStartPoint = newPoint;
                        startMarker = mMap.addMarker(new MarkerOptions().position(mapStartPoint).title("Start Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapStartPoint, 15));
                    }

                    mapEndPoint = newPoint;
                    mapPoints.add(mapEndPoint);
                    polylineOptions.add(mapEndPoint);
                    mMap.addPolyline(polylineOptions);

                    // Calculate the distance
                    if (mapPoints.size() > 1) {
                        Location previousLocation = new Location("");
                        previousLocation.setLatitude(mapPoints.get(mapPoints.size() - 2).latitude);
                        previousLocation.setLongitude(mapPoints.get(mapPoints.size() - 2).longitude);

                        totalWalkingDistance += previousLocation.distanceTo(location);
                    }
                }
            }
        };
        return locationCallback;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng skopje = new LatLng(41.9981, 21.4254);
        mMap.addMarker(new MarkerOptions().position(skopje).title("Marker in Skopje"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(skopje, 12));
    }
}
