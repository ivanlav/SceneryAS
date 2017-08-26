package scenery.scenery;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Ivan on 8/18/2017.
 */

abstract class BaseMapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    protected GoogleMap mMap;
    private Toolbar myToolbar;
    protected boolean mapReady = false;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public LatLng startLoc = new LatLng(42.351035, -71.115051);
    protected Location mLastLocation;


    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onStart() {
     super.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        */


        mLastLocation = new Location("");
        //mLastLocation.setLongitude(startLoc.longitude);
        //mLastLocation.setLatitude(startLoc.latitude);

        //checkLocationPermission();


        buildGoogleApiClient();
        getLastLocation();

        CreateToolbar();


    }

    private void CreateToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void SetUpMap() {

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Called when Map is ready

        mapReady = true;

        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        SetMapStyleFromJSon();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("AFL", "PERMISSION CHECK COMPLETE");


                mMap.setMyLocationEnabled(true);
            } else {
                Log.d("AFL", "PERMISSION CHECK NOT COMPLETE");
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        StartActivity();


    }

    private void SetMapStyleFromJSon() {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //getLastLocation();
        SetUpLocationRequest();
        StartLocationUpdates();



    }

    private void StartLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.d("mlast", mLastLocation.toString());

        }
    }

    private void SetUpLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        /*
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        */

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void checkLocationPermission() {
        //Checks to see if Location Permission is granted

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    abstract void StartActivity();

    public GoogleMap getMap() {
        return mMap;
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.e("cloc","checkLocationGood");
        checkLocationPermission();

        //Starts map after first location call is made
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                       @Override
                       public void onSuccess(Location location) {
                           //Log.e("cloc",location.toString());

                           // GPS location can be null if GPS is switched off
                           if (location != null) {

                               mLastLocation = location;
                               //onLocationChanged(location);
                               SetUpMap();
                               Log.e("cmloc",mLastLocation.toString());
                           }else{
                               mLastLocation = new Location("");
                               mLastLocation.setLongitude(startLoc.longitude);
                               mLastLocation.setLatitude(startLoc.latitude);
                               SetUpMap();
                           }
                       }
                   })
                .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.e("MapDemoActivity", "Error trying to get last GPS location");
                           e.printStackTrace();
                           mLastLocation = new Location("");
                           mLastLocation.setLongitude(startLoc.longitude);
                           mLastLocation.setLatitude(startLoc.latitude);
                           SetUpMap();
                       }
                   });
    }



}
