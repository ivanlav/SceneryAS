package scenery.scenery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

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


   private Calendar setDate;
    //public LatLng currLoc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ACTIVITY:","CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        if(savedInstanceState != null){
            setDate = (Calendar) savedInstanceState.getSerializable("calendar");
            filters = (ArrayList<FilterItem>) savedInstanceState.getSerializable("filters");}
        else{
            createFilterList();
            setDate =Calendar.getInstance();
        }

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        SetUpMap();

    }

    @Override
    protected void onStop(){

        super.onStop();
        Log.e("ACTIVITY:","STOP");
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mapReady){
            CreateMarkers();
        }
        Log.e("ACTIVITY:","RESUME");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.e("ACTIVITY:","START");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("ACTIVITY:","PAUSE");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("ACTIVITY:","DESTROY");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable("calendar",setDate);
        outState.putSerializable("filters",filters);

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
                Intent filterIntent = new Intent(MapsActivity.this, FilterActivity.class);
                filterIntent.putExtra("fil",filters);
                startActivityForResult(filterIntent,FILTER_RESULT);

                return true;

            case R.id.action_calendar:
                // User chose the "Filter" action
                Intent calendarIntent = new Intent(MapsActivity.this, PickDate.class);
                calendarIntent.putExtra("cal",setDate);
                startActivityForResult(calendarIntent,CALENDAR_RESULT);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
        mapReady = true;
        Log.e("ACTIVITY:","MAPREADY");

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
            }
            else {
                Log.d("AFL", "PERMISSION CHECK NOT COMPLETE");
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        MoveMap(startLoc, false, 12);

        CreateMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                setInfoWindow(marker);
                MoveMap(marker.getPosition(),true,mMap.getCameraPosition().zoom);
                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);
                if(info.getVisibility() == RelativeLayout.VISIBLE){
                    info.setVisibility(RelativeLayout.INVISIBLE);
                }
            }
        });
    }


    public void SetUpMap() {

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
    }

    public void MoveMap(LatLng location, boolean animateCam,float zoom) {

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

    public void CreateMarkers(){


        mMap.clear();
        setDateText();

        if(filters.get(0).getChecked()) {
            CreateComedyMarkers();
        }
        if(filters.get(1).getChecked()) {
            CreateTriviaMarkers();
        }

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


    public void AddMarker(Place place, int res){
        Log.e("Mar", "First: " + ConvertDay(setDate.get(Calendar.DAY_OF_WEEK)));
        Log.e("Mar", "Second: " + place.Day);
        if((ConvertDay(setDate.get(Calendar.DAY_OF_WEEK))).equals(place.Day)){

            Log.e("Mar", "AddMarker: ");

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(place.Latitude, place.Longitude))
                    .title(place.Name)
                    .icon(BitmapDescriptorFactory.fromResource(res))
                    .snippet(place.Day + ", " + place.Time + "\n" + place.Establishment + "\n" + place.Address));
            marker.setTag(place);
        }
    }

    public void CreateTriviaMarkers() {

        Place[] newPlaceArr = DummyPlaces.CreateTriviaPlacesArr();
        for (Place i : newPlaceArr
                ) {
            AddMarker(i,setMarkerIcon(i));

        }
    }

    public void CreateComedyMarkers() {

            Place[] newPlaceArr = DummyPlaces.CreateComedyPlacesArr();
            for (Place i : newPlaceArr
                    ) {
                AddMarker(i,setMarkerIcon(i));
            }

    }

    public int setMarkerIcon(Place place){

        if(place.Type.equals("Comedy")){
            return R.mipmap.mic_icon;
        }
        else if(place.Type.equals("Trivia")){
            return R.mipmap.trivia_icon;
        }
        else return R.mipmap.mic_icon;
    }

    public void setInfoWindow(Marker marker){

        RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);

        if(info.getVisibility() == RelativeLayout.INVISIBLE){
            info.setVisibility(RelativeLayout.VISIBLE);
        }

        Place place = (Place) marker.getTag();

        TextView title = (TextView) findViewById(R.id.eventtitle);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, Typeface.BOLD);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) findViewById(R.id.eventinfotext);
        snippet.setTextColor(Color.GRAY);
        snippet.setText(marker.getSnippet());

        ImageView icon = (ImageView) findViewById(R.id.eventicon);
        icon.setImageResource(setMarkerIcon(place));
    }

    public void createInfoWindows() {

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                /*

                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.


                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());


                info.addView(title);
                info.addView(snippet);

*/
                return null;

            }
        });

    }

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

    private void createFilterList(){

        ArrayList<FilterItem> filters = new ArrayList<FilterItem>();

        filters.add(new FilterItem("Comedy", true));
        filters.add(new FilterItem("Trivia", true));
        filters.add(new FilterItem("Karaoke", false));
        filters.add(new FilterItem("Music", false));

        this.filters = filters;
    }


}
