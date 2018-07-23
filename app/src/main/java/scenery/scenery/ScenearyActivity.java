package scenery.scenery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/*
Main Activity to Start
Contains most of the functionality of the Main App Screen
*/


public class ScenearyActivity extends BaseMapsActivity implements
        ClusterManager.OnClusterClickListener<Place>,
        ClusterManager.OnClusterInfoWindowClickListener<Place>,
        ClusterManager.OnClusterItemClickListener<Place>,
        ClusterManager.OnClusterItemInfoWindowClickListener<Place> {

    private static final int FILTER_RESULT = 1;
    private static final int CALENDAR_RESULT = 2;
    private static final int ADDEVENT_RESULT = 3;


    private ArrayList<FilterItem> filters;
    private Place lastMarkerPlace;
    private float lastZoom;

    public ArrayList<Place> beginPlaces;
    public ArrayList<Place> currentPlaces;

    private static Calendar calendar;

    private ClusterManager<Place> mClusterManager;

    private String currentView = "Map";
    public DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ACTIVITY:", "CREATE");

        super.onCreate(savedInstanceState);

        db = DBHandler.getsInstance(getApplicationContext());

        //Restore Data if App was stopped

        if (savedInstanceState != null) {
            calendar = (Calendar) savedInstanceState.getSerializable("calendar");
            filters = (ArrayList<FilterItem>) savedInstanceState.getSerializable("filters");
            lastMarkerPlace = (Place) savedInstanceState.getSerializable("lastPlace");
            lastZoom = savedInstanceState.getFloat("zoom");
            beginPlaces = (ArrayList<Place>) savedInstanceState.getSerializable("places");
        } else {
            createFilterList();
            calendar = Calendar.getInstance();
        }
        //Log.e("Mlast",mLastLocation.toString());
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
            mClusterManager.cluster();
            updateList();

        }
        Log.e("ACTIVITY:", "RESUME");
        //redraws markers when activity resumes
    }

    private void updateList() {
        if(currentView.equals("List")){
            RecyclerViewFragment newFragment = new RecyclerViewFragment();

            getSupportFragmentManager().popBackStack();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.remove(newFragment);

            fragmentTransaction.replace(R.id.fragment_container,newFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapReady) {

            CreateMarkers();
            mClusterManager.cluster();

        }
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
        outState.putSerializable("calendar", calendar);
        outState.putSerializable("filters", filters);
        outState.putSerializable("lastPlace", lastMarkerPlace);
        outState.putFloat("zoom", lastZoom);
        outState.putSerializable("places", beginPlaces);
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
        clearEventView();

        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_filter:
                // User chose the "Filter" action
                Intent filterIntent = new Intent(ScenearyActivity.this, FilterActivity.class);
                filterIntent.putExtra("fil", filters);
                startActivityForResult(filterIntent, FILTER_RESULT);
                return true;

            case R.id.action_calendar:
                // User chose the "Filter" action
                showDatePickerDialog(findViewById(android.R.id.content));
                return true;

            case R.id.action_addevent:
                Intent addEvent = new Intent(ScenearyActivity.this, AddEventActivity.class);
                startActivityForResult(addEvent, ADDEVENT_RESULT);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar newCal = Calendar.getInstance();
            newCal.clear();
            newCal.set(year,month,day);
            calendar = newCal;

            ((ScenearyActivity)getActivity()).CreateMarkers();
            ((ScenearyActivity)getActivity()).mClusterManager.cluster();
            ((ScenearyActivity)getActivity()).reCenterMap();
            ((ScenearyActivity)getActivity()).updateList();

        }
    }

    private void reCenterMap() {
        if (mapReady) {
            MoveMap(startLoc, false, 12);
        }
    }

    @Override
    public void StartActivity() {
        //sets location to last marker location, or default location
        if (lastMarkerPlace == null) {
            MoveMap(startLoc, false, 12);
        } else {
            MoveMap(new LatLng(lastMarkerPlace.Latitude, lastMarkerPlace.Longitude), false, lastZoom);
            setInfoWindow(lastMarkerPlace);
        }


        mClusterManager = new ClusterManager<Place>(this, mMap);
        mClusterManager.setRenderer(new PlaceRenderer());
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mClusterManager.setAlgorithm(new NewAlgorithm<Place>());

        currentPlaces = new ArrayList<Place>();

        CreateMarkers();
        mClusterManager.cluster();

        //clears info window on map click
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                clearEventView();
            }
        });

        setUpTabs();
    }

    //clear existing info window
    public void clearEventView() {
        RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);
        if (info.getVisibility() == RelativeLayout.VISIBLE) {
            info.setVisibility(RelativeLayout.INVISIBLE);
        }
        lastMarkerPlace = null;
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

        //mMap.clear();
        mClusterManager.clearItems();
        currentPlaces.clear();

        setDateText();



            if (beginPlaces == null) {
                try {
                    beginPlaces = DummyPlaces.parseSheet();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (db.checkEmpty()) {
                    beginPlaces.addAll(db.placeArrayfromDB());
                }

                if (beginPlaces != null) {
                    for (Place p : beginPlaces) {

                        p.Icon = setMarkerIcon(p);
                        AddCluster(p);
                    }

                }

            } else {
                for (Place p : beginPlaces) {

                    p.Icon = setMarkerIcon(p);
                    if (p.Latitude != null) {
                        AddCluster(p);
                    }
                }
            }
            currentPlaces = sortPlacesByDistance(currentPlaces);
        }



    private void setDateText() {

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);

        TextView dateText = (TextView) findViewById(R.id.datetext);
        dateText.setText(new StringBuilder().append("Viewing events for: ").append(ConvertDay(dow) + ", ").append(month + 1).append("/")
                .append(day).append("/").append(year));
    }

    public void AddCluster(Place place) {

        if ((ConvertDay(calendar.get(Calendar.DAY_OF_WEEK))).equals(place.Day)) {

            if (checkFilter(place)) {

                mClusterManager.addItem(place);
                currentPlaces.add(place);
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public int setMarkerIcon(Place place) {

        if (place.Type.equals("Comedy Open Mic")) {
            return R.mipmap.mic_icon;
        } else if (place.Type.equals("Trivia")) {
            return R.mipmap.trivia_icon;
        } else if (place.Type.equals("Meal Deals")) {
            return R.mipmap.meals;
        } else if (place.Type.equals("Karaoke")) {
            return R.mipmap.karaoke;
        } else if (place.Type.equals("Live Music")) {
            return R.mipmap.livemusic;
        } else if (place.Type.equals("Dancing")) {
            return R.mipmap.danceicon;
        } else return R.mipmap.mic_icon;
    }

    //draw info window
    public void setInfoWindow(final Place place) {

        RelativeLayout info = (RelativeLayout) findViewById(R.id.eventview);

        if (info.getVisibility() == View.INVISIBLE) {
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

        ImageView nextIcon = (ImageView) findViewById(R.id.nexteventicon);
        nextIcon.setVisibility(ImageView.INVISIBLE);

        lastMarkerPlace = place;

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void setInfoWindowArray(final ArrayList<Place> placeArr, final int index) {

        setInfoWindow(placeArr.get(index));

        ImageView nextIcon = (ImageView) findViewById(R.id.nexteventicon);


        int nextIndex = index + 1;
        if (nextIndex == (placeArr.size())) {
            nextIndex = 0;
        }

        nextIcon.setImageResource(setMarkerIcon(placeArr.get(nextIndex)));
        nextIcon.setVisibility(ImageView.VISIBLE);
        nextIcon.setScaleX(.6f);
        nextIcon.setScaleY(.6f);

        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nextIndex = index + 1;
                if (nextIndex == (placeArr.size())) {
                    nextIndex = 0;
                }
                setInfoWindowArray(placeArr, nextIndex);

            }
        });


    }

    //adds directions that open in Google Navigation
    private void getDirections(Place place) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place.Address);

        float distance = getDistance(place);
        float mile = 1609;
        Log.e("distance", Float.toString(distance));
        if (distance < mile) {
            gmmIntentUri = Uri.parse("google.navigation:q=" + place.Address + "&mode=w");
        }
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private float getDistance(Place place) {
        Location placeLoc = new Location("");
        placeLoc.setLatitude(place.Latitude);
        placeLoc.setLongitude(place.Longitude);
        Log.e("pLoc",placeLoc.toString());
        Log.e("mLastLocation",mLastLocation.toString());

        return placeLoc.distanceTo(mLastLocation);
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
            case ADDEVENT_RESULT:
                break;
           }
        reCenterMap();

    }

    public String ConvertDay(int i) {
        String dateString = "";
        switch (i) {
            case (1):
                dateString = "Sunday";
                break;
            case (2):
                dateString = "Monday";
                break;
            case (3):
                dateString = "Tuesday";
                break;
            case (4):
                dateString = "Wednesday";
                break;
            case (5):
                dateString = "Thursday";
                break;
            case (6):
                dateString = "Friday";
                break;
            case (7):
                dateString = "Saturday";
                break;

        }
        return dateString;
    }

    private boolean checkFilter(Place place) {

        String placeType = place.Type;

        for (FilterItem p : filters) {
            if (p.getName().equals(placeType) && p.getChecked()) {
                return true;
            }
        }
        return false;
    }

    private void createFilterList() {

        ArrayList<FilterItem> filters = new ArrayList<FilterItem>();

        filters.add(new FilterItem("Comedy Open Mic", true));
        filters.add(new FilterItem("Trivia", true));
        filters.add(new FilterItem("Karaoke", true));
        filters.add(new FilterItem("Live Music", true));
        filters.add(new FilterItem("Meal Deals", true));
        filters.add(new FilterItem("Dancing", true));

        this.filters = filters;
    }

    @Override
    public boolean onClusterClick(Cluster<Place> cluster) {
        clearEventView();

        // String firstName = cluster.getItems().iterator().next().Name;
        //t.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
        ArrayList<Place> placeArr = new ArrayList<Place>();

        for (Place place : cluster.getItems()) {
            placeArr.add(place);
        }

        if (isSameLocation(cluster)) {
            Place place = placeArr.get(0);
            setInfoWindowArray(placeArr, 0);
            MoveMap(new LatLng(place.Latitude, place.Longitude), true, mMap.getCameraPosition().zoom);
            return true;
        }

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());

        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();


        // Animate camera to the bounds
        try {

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Place> cluster) {

    }

    @Override
    public boolean onClusterItemClick(final Place place) {
        clearEventView();
        setInfoWindow(place);
        MoveMap(new LatLng(place.Latitude, place.Longitude), true, mMap.getCameraPosition().zoom);
        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(Place place) {

    }

    private class PlaceRenderer extends DefaultClusterRenderer<Place> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final TextView mTextView;
        private final int mDimension;

        public PlaceRenderer() {
            super(getApplicationContext(), mMap, mClusterManager);

            View numberIcon = getLayoutInflater().inflate(R.layout.numbericon, null);
            mClusterIconGenerator.setContentView(numberIcon);
            mClusterIconGenerator.setBackground(null);

            mClusterImageView = (ImageView) numberIcon.findViewById(R.id.numberIconImage);
            //mClusterImageView.setScaleX(.4f);
            //mClusterImageView.setScaleY(.4f);

            mTextView = (TextView) numberIcon.findViewById(R.id.numberIconNumber);

            mImageView = new ImageView(getApplicationContext());
            mDimension = (int) getResources().getDimension(R.dimen.icon_size);
            //mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            //mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
            mIconGenerator.setBackground(null);
        }

        @Override
        protected void onBeforeClusterItemRendered(Place place, MarkerOptions markerOptions) {
            // Draw a single person.
            // Set the info window to show their name.
            mClusterImageView.setImageResource(place.Icon);
            mTextView.setText("");
            //mImageView.setScaleX(.4f);
            //mImageView.setScaleY(.4f);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(place.Icon));
            Bitmap icon = mClusterIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));

        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Place> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).


            /*

            for (Place p : cluster.getItems()) {
                // Draw 4 at most.
                if (placeIcons.size() == 4) break;

                Drawable drawable = getResources().getDrawable(p.Icon, getTheme());

                if(difficons.contains(p.Icon) == false) {
                    difficons.add(p.Icon);
                    drawable.setBounds(0, 0, width, height);
                    placeIcons.add(drawable);
                }
            }
            */

            /*
            if(isSameLocation(cluster)){

                List<Drawable> placeIcons = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
                List<Integer> difficons = new ArrayList<Integer>();

                int width = mDimension;
                int height = mDimension;

                for (Place p : cluster.getItems()) {
                    // Draw 4 at most.
                    if (placeIcons.size() == 4) break;

                    Drawable drawable = getResources().getDrawable(p.Icon, getTheme());


                    if(difficons.contains(p.Icon) == false) {
                        difficons.add(p.Icon);
                        drawable.setBounds(0, 0, width, height);
                        placeIcons.add(drawable);
                    }
                }

                MultiDrawable multiDrawable = new MultiDrawable(placeIcons);
                multiDrawable.setBounds(0, 0, width, height);
                mClusterImageView.setImageDrawable(multiDrawable);
                mTextView.setText("");
                Bitmap icon = mClusterIconGenerator.makeIcon();
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
                return;
            }
            */
            //MultiDrawable multiDrawable = new MultiDrawable(placeIcons);
            //multiDrawable.setBounds(0, 0, width, height);

            //mClusterImageView.setImageDrawable(multiDrawable);
            mClusterImageView.setImageResource(R.mipmap.numbericon);
            mTextView.setText(String.valueOf(cluster.getSize()));
            Bitmap icon = mClusterIconGenerator.makeIcon();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }

    }

    public boolean isSameLocation(Cluster<Place> cluster) {

        LatLng location = null;

        for (Place p : cluster.getItems()) {
            double latitude = p.Latitude;
            double longitude = p.Longitude;

            LatLng compareLocation = new LatLng(latitude, longitude);

            if (location != null && !compareLocation.equals(location)) {
                Log.e("isl", "false");
                return false;
            }
            location = compareLocation;

        }
        Log.e("isl", "true");
        return true;
    }

    public ArrayList<Place> sortPlacesByDistance(ArrayList<Place> placeArr) {

        for (Place place : placeArr) {
            place.Distance = getDistance(place);
        }

        Collections.sort(placeArr, new Comparator<Place>() {
            @Override
            public int compare(Place o1, Place o2) {
                return Float.compare(o1.Distance, o2.Distance);
            }
        });

        return placeArr;
    }

    public void setUpTabs(){


        final Fragment newFragment = new RecyclerViewFragment();

        TabLayout tabLayout =  (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.e("Tabselected","yes");

                final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch(tab.getPosition()) {
                    case 0:

                        currentView = "Map";


                        fragmentTransaction.remove(newFragment);

                        getSupportFragmentManager().popBackStack();



                        Log.e("CASE1","yes");

                        break;
                    case 1:

                        clearEventView();

                        currentView = "List";




                        fragmentTransaction.replace(R.id.fragment_container,newFragment);
                        fragmentTransaction.addToBackStack(null);


                        Log.e("CASE2","yes");

                        break;
                }


                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }






}
