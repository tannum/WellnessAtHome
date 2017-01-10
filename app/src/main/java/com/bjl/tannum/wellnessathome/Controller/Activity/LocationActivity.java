package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bjl.tannum.wellnessathome.Controller.Library.GPSTracker;
import com.bjl.tannum.wellnessathome.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {



    MapFragment map;
    GPSTracker gps;
    double latitude = 0.0;
    double longitude =  0.0;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Log.d("debug","Initial GPS");
        gps = new GPSTracker(this);
        //Mask: get current location
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("debug","Can Get Location");
            Log.d("debug","lat = " + String.valueOf(latitude));
            Log.d("debug","log = " + String.valueOf(longitude));
        }
        else{
            latitude = 0.0;
            longitude = 0.0;
            Log.d("debug","Can not Get Location");
        }
        location = String.valueOf(latitude)+ " , " + String.valueOf(longitude);
        Log.d("debug","location = " + location);


        //Initial Google Map
        map = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_map_container, map);
        fragmentTransaction.commit();
        map.getMapAsync(this);


    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


//        LatLng coordinate = new LatLng(latitude, longitude);
//        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
//        googleMap.animateCamera(yourLocation);
//



//        mMap = googleMap;
//
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//
//
//        LatLng myPosition;
//
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String provider = locationManager.getBestProvider(criteria, true);
//        Location location = locationManager.getLastKnownLocation(provider);
//
//
//        if (location != null) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            LatLng latLng = new LatLng(latitude, longitude);
//            myPosition = new LatLng(latitude, longitude);
//
//
//            LatLng coordinate = new LatLng(latitude, longitude);
//            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 19);
//            mMap.animateCamera(yourLocation);
//        }
    }
}
