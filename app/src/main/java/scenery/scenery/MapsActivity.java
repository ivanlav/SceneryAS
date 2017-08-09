package scenery.scenery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.view.ActionMode;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int FILTER_RESULT = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int CALENDAR_RESULT = 2;
    //private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private GoogleMap mMap;
    public LatLng startLoc = new LatLng(42.351035, -71.115051);
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private Toolbar myToolbar;
    ArrayList<FilterItem> filters;
    private Location mLastLocation;
    private boolean mapReady = false;
    private Place lastMarkerPlace;
    private float lastZoom;
    private ArrayList<Place> beginPlaces;

    private Calendar setDate;
    //public LatLng currLoc;

    //first to run
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ACTIVITY:", "CREATE");
        super.onCreate(savedInstanceState);

        //set layout
        setContentView(R.layout.activity_maps);

        //if activity was previously stopped, save and restore data
        if (savedInstanceState != null) {
            setDate = (Calendar) savedInstanceState.getSerializable("calendar");
            filters = (ArrayList<FilterItem>) savedInstanceState.getSerializable("filters");
            lastMarkerPlace = (Place) savedInstanceState.getSerializable("lastPlace");
            lastZoom = savedInstanceState.getFloat("zoom");
            beginPlaces = (ArrayList<Place>) savedInstanceState.getSerializable("places");
        } else {
            createFilterList();
            setDate = Calendar.getInstance();
        }

        //set toolbar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //set up map
        SetUpMap();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ACTIVITY:", "STOP");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapReady) {

            CreateMarkers();
        }
        Log.e("ACTIVITY:", "RESUME");
        //redraws markers when activity resumes
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ACTIVITY:", "START");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ACTIVITY:", "PAUSE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ACTIVITY:", "DESTROY");
    }

    //saves data when activity is stopped
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        lastZoom = mMap.getCameraPosition().zoom;
        outState.putSerializable("calendar", setDate);
        outState.putSerializable("filters", filters);
        outState.putSerializable("lastPlace", lastMarkerPlace);
        outState.putFloat("zoom", lastZoom);
        outState.putSerializable("places",beginPlaces);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;


            case R.id.action_filter:
                // User chose the "Filter" action
                clearInfo();
                Intent filterIntent = new Intent(MapsActivity.this, FilterActivity.class);
                filterIntent.putExtra("fil", filters);
                startActivityForResult(filterIntent, FILTER_RESULT);

                return true;

            case R.id.action_calendar:
                // User chose the "Filter" action
                clearInfo();
                Intent calendarIntent = new Intent(MapsActivity.this, PickDate.class);
                calendarIntent.putExtra("cal", setDate);
                startActivityForResult(calendarIntent, CALENDAR_RESULT);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //called when map loads, no major code should run before this is called
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        Log.e("ACTIVITY:", "MAPREADY");

        //set map style to particular json style file
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("AFL", "PERMISSION CHECK COMPLETE");

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                Log.d("AFL", "PERMISSION CHECK NOT COMPLETE");
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        //sets location to last marker location, or default location
        if (lastMarkerPlace == null) {
            MoveMap(startLoc, false, 12);
        } else {
            MoveMap(new LatLng(lastMarkerPlace.Latitude, lastMarkerPlace.Longitude), false, lastZoom);
            setInfoWindow(lastMarkerPlace);
        }

        CreateMarkers();

        //centers marker and creates info window on marker click
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                centerMarker(marker);
                return true;
            }
        });

        //clears info window on map click
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                clearInfo();
            }
        });

    }

    public void centerMarker(Marker marker) {

        Place mPlace = (Place) marker.getTag();
        setInfoWindow(mPlace);
        MoveMap(marker.getPosition(), true, mMap.getCameraPosition().zoom);
    }

    //clear existing info window
    public void clearInfo() {
        RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);
        if (info.getVisibility() == RelativeLayout.VISIBLE) {
            info.setVisibility(RelativeLayout.INVISIBLE);
        }
        lastMarkerPlace = null;
    }

    public void SetUpMap() {

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    public void MoveMap(LatLng location, boolean animateCam, float zoom) {

        CameraPosition.Builder builder = CameraPosition.builder();

        builder.target(location);
        builder.zoom(zoom);

        CameraPosition cameraPosition = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        if (mMap != null) {
            if (animateCam) {
                mMap.animateCamera(cameraUpdate);
            } else {
                mMap.moveCamera(cameraUpdate);
            }
        }

    }

    //create markers from json in DummyPlaces class
    public void CreateMarkers() {

        mMap.clear();
        setDateText();

        if (beginPlaces == null) {

            try {
                beginPlaces = DummyPlaces.parseSheet();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (beginPlaces != null) {
                for (Place p : beginPlaces) {

                    //Log.e("latlng", p.Address);

                    LatLng ll = new LatLng(p.Latitude,p.Longitude);
                    AddMarker(p, setMarkerIcon(p));
                    /*LatLng ll = getLocationFromAddress(getBaseContext(), p.Address);
                    if (ll != null) {
                        p.Latitude = ll.latitude;
                        p.Longitude = ll.longitude;
                        AddMarker(p, setMarkerIcon(p));
                    }
                    */
                }

            }

        }else {
            for (Place p : beginPlaces) {
               // Log.e("plac",p.Name);
                if(p.Latitude != null) {
                    AddMarker(p, setMarkerIcon(p));
                }
            }
        }
        /*
        if(filters.get(0).getChecked()) {
            CreateComedyMarkers();
        }
        if(filters.get(1).getChecked()) {
            CreateTriviaMarkers();
        }
        */

        //createInfoWindows();
    }

    private void setDateText(){

        int year = setDate.get(Calendar.YEAR);
        int month = setDate.get(Calendar.MONTH);
        int day = setDate.get(Calendar.DAY_OF_MONTH);
        int dow = setDate.get(Calendar.DAY_OF_WEEK);

        TextView dateText = (TextView) findViewById(R.id.datetext);
        dateText.setText(new StringBuilder().append("Viewing events for: ").append(ConvertDay(dow) + ", ").append(month + 1).append("/")
                .append(day).append("/").append(year));
    }

    public void AddMarker(Place place, int res){
        Log.e("Mar", "First: " + ConvertDay(setDate.get(Calendar.DAY_OF_WEEK)));
        Log.e("Mar", "Second: " + place.Day);
        if((ConvertDay(setDate.get(Calendar.DAY_OF_WEEK))).equals(place.Day)){

            Log.e("Mar", "AddMarker: ");

            if(checkFilter(place)) {

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.Latitude, place.Longitude))
                        .title(place.Name)
                        .icon(BitmapDescriptorFactory.fromResource(res)));
                marker.setTag(place);
            }
        }
    }

    public int setMarkerIcon(Place place){

        if(place.Type.equals("Comedy Open Mic")){
            return R.mipmap.mic_icon;
        }
        else if(place.Type.equals("Trivia")){
            return R.mipmap.trivia_icon;
        }else if(place.Type.equals("Meal Deals")){
            return R.mipmap.meals;
        }else if(place.Type.equals("Karaoke")){
            return R.mipmap.karaoke;
        }else if(place.Type.equals("Live Music")){
            return R.mipmap.livemusic;
        }else if(place.Type.equals("Dancing")){
            return R.mipmap.danceicon;
        }
        else return R.mipmap.mic_icon;
    }

    //draw info window
    public void setInfoWindow(final Place place){

        RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);

        if(info.getVisibility() == View.INVISIBLE){
            info.setVisibility(RelativeLayout.VISIBLE);
        }

        TextView title = (TextView) findViewById(R.id.eventtitle);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setText(place.Name);

        TextView snippet = (TextView) findViewById(R.id.eventinfotext);
        snippet.setTextColor(Color.GRAY);
        snippet.setText(place.Day + ", " + place.Time + "\n" + place.Establishment);

        ImageView icon = (ImageView) findViewById(R.id.eventicon);
        icon.setImageResource(setMarkerIcon(place));

        lastMarkerPlace = place;

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing yet
            }
        });

        Button directions = (Button) findViewById(R.id.directionsButton);

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirections(place);
            }
        });
    }

    //adds directions that open in Google Navigation
    private void getDirections(Place place){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+place.Address);

        Location placeLoc = new Location("");
        placeLoc.setLatitude(place.Latitude);
        placeLoc.setLongitude(place.Longitude);
        float distance = mLastLocation.distanceTo(placeLoc);
        float mile = 1609;
        Log.e("distance",Float.toString(distance));
        if(distance < mile){
            gmmIntentUri = Uri.parse("google.navigation:q="+place.Address+"&mode=w");
        }
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    //called when Filter or Calendar exits, saves relevant data
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("er","is this ever called?");
        switch (requestCode) {
            case FILTER_RESULT:
                Bundle b = data.getExtras();
                filters = (ArrayList<FilterItem>) b.getSerializable("FilterItems");

                break;
            case CALENDAR_RESULT:
                Log.d("er","is this ever called?");
                Bundle c = data.getExtras();

                Calendar newCal = (Calendar) c.getSerializable("cal");
                //Log.d("er",newCal.toString());
                setDate = newCal;
                //Log.d("er",setDate.toString());

                break;

        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
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

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
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

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

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

    //converts Addresses to Coordinates, too slow, to be replaced
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null || address.size() == 0) {
                return null;
            }
            try{
            Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new LatLng(location.getLatitude(), location.getLongitude() );}
            catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public String ConvertDay(int i){
        String dateString = "";
        switch(i){
            case(1): dateString = "Sunday";
                break;
            case(2): dateString = "Monday";
                break;
            case(3): dateString = "Tuesday";
                break;
            case(4): dateString = "Wednesday";
                break;
            case(5): dateString = "Thursday";
                break;
            case(6): dateString = "Friday";
                break;
            case(7): dateString = "Saturday";
                break;

        }
        return dateString;
    }

    private boolean checkFilter(Place place){

        String placeType = place.Type;

        for(FilterItem p : filters){
            if(p.getName().equals(placeType) && p.getChecked()){
                return true;
            }
        }
        return false;



    }

    private void createFilterList(){

        ArrayList<FilterItem> filters = new ArrayList<FilterItem>();

        filters.add(new FilterItem("Comedy Open Mic", true));
        filters.add(new FilterItem("Trivia", true));
        filters.add(new FilterItem("Karaoke", true));
        filters.add(new FilterItem("Live Music", true));
        filters.add(new FilterItem("Meal Deals", true));
        filters.add(new FilterItem("Dancing",true));

        this.filters = filters;
    }







}
