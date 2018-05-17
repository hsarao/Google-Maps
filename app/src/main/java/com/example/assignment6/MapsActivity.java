package com.example.assignment6;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng london;
    private LatLng barnala;
    private LatLng winnipeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.london:
                CameraUpdate londonlatlng = CameraUpdateFactory.newLatLngZoom(london, 15);
                mMap.animateCamera(londonlatlng);
                break;
            case R.id.barnala:
                CameraUpdate barnalalatlang = CameraUpdateFactory.newLatLngZoom(barnala, 15);
                mMap.animateCamera(barnalalatlang);
                break;
            case R.id.winnipeg:
                CameraUpdate winnipeglatlang = CameraUpdateFactory.newLatLngZoom(winnipeg, 15);
                mMap.animateCamera(winnipeglatlang);
                break;

        }
//        if (id == R.id.normal) {
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("Jody", "enabling my location");
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
     //   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        barnala = new LatLng(30.381945, 75.546798);
        london = new LatLng(51.507351, -0.127758);
        winnipeg = new LatLng(49.895136, -97.138374);


//            var url = new URL("http://icons.iconarchive.com/icons/archigraphs/oldies/512/Old-House-icon.png");
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            mMap.addMarker(new MarkerOptions()
                    .position(barnala)
                    .title("Home Town")
                    .snippet("I was born and raised here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.barnala)));


        mMap.addMarker(new MarkerOptions()
        .position(london)
        .title("City of London")
        .snippet("Capital of England").icon(BitmapDescriptorFactory.fromResource(R.drawable.london2)));
        mMap.addMarker(new MarkerOptions()
                .position(winnipeg)
                .title("My City")
                .snippet("I currently live here.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.test2)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(winnipeg,15));
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(barnala));
      //  CameraUpdateFactory.zoomBy(1000000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("Jody", "onRequestPermissionsResult");
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //either check to see if permission is available, or handle a potential SecurityException before calling mMap.setMyLocationEnabled
                try {
                    mMap.setMyLocationEnabled(true);
                } catch(SecurityException e) {
                    Log.d("Jody", "SecurityException in MapsActivity.onRequestPermissionsResult: " + e.getMessage());
                }
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(MapsActivity.this, "Permission to access your location was denied so your location cannot be displayed on the map.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
