package com.example.myapplication.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.SavedPlazaFragment;
import com.example.myapplication.Fragment.SettingFragment;
import com.example.myapplication.Fragment.ViewProfileFragment;
import com.example.myapplication.Model.Plaza;
import com.example.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;


    //widgets
    private DrawerLayout drawer;
    private MaterialSearchBar searchBar;
    View mapView;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    DatabaseReference firebaseDatabase;
    Marker marker;
    Location currentLocation;
    Address address;
    private Polyline currentPolyline;
    Marker marker1;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchBar = findViewById(R.id.searchBar);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("plaza");
        getLocationPermission();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//         AutocompleteFilter filter = new AutocompleteFilter.Builder()
//                .setCountry("IN")
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
//                .build();
//        autocompleteFragment.setFilter(filter);
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(com.google.android.libraries.places.compat.Place place) {
//
//            }
//
//            @Override
//            public void onError(Status status) {
//                status.toString();
//            }
//        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionsGranted){
                    initMap();
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
//                    Intent intent = new Intent(getApplicationContext(),ViewPlazaDetailsActivity.class);
//                    startActivity(intent);
                    LatLng pos = marker.getPosition();
                    //Umar's Code

                    Query query = FirebaseDatabase.getInstance().getReference("plaza")
                            .orderByChild("plazaLatitude")
                            .equalTo(pos.latitude);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.getChildrenCount() == 1){
                                    Plaza p = dataSnapshot.getValue(Plaza.class);
                                    for(DataSnapshot data: dataSnapshot.getChildren()){
                                        p = data.getValue(Plaza.class);
                                    }
                                    if(p.getStatus().equals("Registered")) {
                                        Intent i = new Intent(getApplicationContext(), ViewPlazaDetailsActivity.class);
                                        i.putExtra("plaza", p);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"This Plaza is not Registered.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Not found but working",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            mMap.setMyLocationEnabled(true);
            googleMap.setOnMarkerClickListener(this);


            firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot s:dataSnapshot.getChildren())
                    {

                        Plaza p=s.getValue(Plaza.class);
                        LatLng location=new LatLng(p.getPlazaLatitude(),p.getPlazaLongitude());

                        MarkerOptions options=new MarkerOptions().position(location).title(p.getPlazaName());


                        if(p.getStatus().equals("unRegistered"))
                        {
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                        }
                        else
                        {
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }

                        mMap.addMarker(options);

                    }
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
            init();
        }
    }
    private void init() {
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.setPlaceHolder("search here");
        geoLocate();
    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = searchBar.getText();

        Geocoder geocoder = new Geocoder(MainScreenActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));

        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainScreenActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            if(marker!=null)
                marker.remove();
            marker=mMap.addMarker(options);
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
//                    Intent intent = new Intent(getApplicationContext(),ViewPlazaDetailsActivity.class);
//                    startActivity(intent);
                    LatLng pos = marker.getPosition();
                    //Umar's Code

                    Query query = FirebaseDatabase.getInstance().getReference("plaza")
                            .orderByChild("plazaLatitude")
                            .equalTo(pos.latitude);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(dataSnapshot.getChildrenCount() == 1){
                                    Plaza p = dataSnapshot.getValue(Plaza.class);
                                    for(DataSnapshot data: dataSnapshot.getChildren()){
                                        p = data.getValue(Plaza.class);
                                    }
                                    if(p.getStatus().equals("Registered")) {
                                        Intent i = new Intent(getApplicationContext(), ViewPlazaDetailsActivity.class);
                                        i.putExtra("plaza", p);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"This Plaza is not Registered.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Not found but working",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }

        hideSoftKeyboard(this,mapView);
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(MainScreenActivity.this);


        mapView = mapFragment.getView();
        if (mapView != null &&
                mapView.findViewById(1) != null) {
            View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 400);
        }
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if(isMapsEnabled()) {
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        mLocationPermissionsGranted = true;
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
    }


    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    //    private void hideSoftKeyboard() {
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk
        //lastSearches=searchBar.getLastSuggestions();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().add(new HomeFragment(), "HomeFragment").addToBackStack("HomeFragment").replace(R.id.fragment_container,
                        new ViewProfileFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingFragment()).add(new HomeFragment(), "HomeFragment").addToBackStack("HomeFragment").commit();
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_savedPlazas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SavedPlazaFragment()).add(new HomeFragment(), "HomeFragment").addToBackStack("HomeFragment").commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text){
        hideSoftKeyboard(this,mapView);
        geoLocate();


    }
    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }

    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;

    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
                Toast.makeText(getApplicationContext(),"Signout Successfully",Toast.LENGTH_LONG);

                startActivity(new Intent(getApplicationContext(),SigninActivity.class));

            }
        });
        finish();
    }


}







///////////////////////////////////////////////////////////////////////////////////////////////////////////




















//import static com.example.myapplication.util.Constants.ERROR_DIALOG_REQUEST;
//import static com.example.myapplication.util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
//import static com.example.myapplication.util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;
//    private static final String TAG = "msg";
//    private DrawerLayout drawer;
//    private MaterialSearchBar searchBar;
//    private GoogleMap mMap;
//    SupportMapFragment sMApFragment;
//    private boolean mLocationPermissionGranted;
//    public View mapView;
//    DatabaseReference firebaseDatabase;
//    Marker marker;
//    Location currentLocation;
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int REQUEST_CODE=101;
//    private static final float DEFAULT_ZOOM = 15f;
//    MarkerOptions options;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_screen);
//
//        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this);
//        fetchLastLocation();
//
//        drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
//                findFragmentById(R.id.map);
//        View mapView = mapFragment.getView();
//        if (mapView != null &&
//                mapView.findViewById(1) != null) {
//            View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
//                    locationButton.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//            layoutParams.setMargins(0, 0, 30, 30);
//        }
//
//        //For materialSearchBar
//        searchBar = findViewById(R.id.searchBar);
//        searchBar.setOnSearchActionListener(this);
//        searchBar.setCardViewElevation(10);
//        searchBar.setPlaceHolder("search here");
//
//
//        checkMapServices();
//       firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("plaza");
//       firebaseDatabase.push().setValue(marker);
////        Plaza p = new Plaza("the plaza", 500, 200, 40, 20, "daily", "daily", "registered", 31.541979, 74.300196, "sameen");
////        firebaseDatabase.push().setValue(p);
////        p = new Plaza("plaza", 500, 200, 40, 20, "daily", "daily", "registered", 31.542445, 74.301355, "sameen");
////        firebaseDatabase.push().setValue(p);
////        p = new Plaza("piyara plaza", 500, 200, 40, 20, "daily", "daily", "registered", 31.537209, 74.300283, "sameen");
////        firebaseDatabase.push().setValue(p);
//
//
//    }
//
//    private void fetchLastLocation() {
//        Task<Location> task=fusedLocationProviderClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if(location!=null)
//                {
//                    currentLocation=location;
//                    SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
//                    supportMapFragment.getMapAsync(MainScreenActivity.this);
//                }
//            }
//        });
//
//    }
//
//
//    //////////////////////////////////////////////////////////////////////////////////////
//    private boolean checkMapServices(){
//        if(isServicesOK()){
//            if(isMapsEnabled()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    public boolean isMapsEnabled(){
//        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }
//
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//            //getChatrooms();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    public boolean isServicesOK(){
//        Log.d(TAG, "isServicesOK: checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainScreenActivity.this);
//
//        if(available == ConnectionResult.SUCCESS){
//            //everything is fine and the user can make map requests
//            Log.d(TAG, "isServicesOK: Google Play Services is working");
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but we can resolve it
//            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainScreenActivity.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }else{
//            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    fetchLastLocation();
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: called.");
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ENABLE_GPS: {
//                if(mLocationPermissionGranted){
//                    //getChatrooms();
//                }
//                else{
//                    getLocationPermission();
//                }
//            }
//        }
//
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        if(mLocationPermissionGranted)
//        {
//
//        }
//        else
//        {
//            getLocationPermission();
//        }
//    }
//    //////////////////////////////////////////////////////////////
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //save last queries to disk
//        //lastSearches=searchBar.getLastSuggestions();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        }
//        else {
//            super.onBackPressed();
//        }
//
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        switch (item.getItemId()) {
//            case R.id.nav_profile:
//                getSupportFragmentManager().beginTransaction().add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").replace(R.id.fragment_container,
//                        new ViewProfileFragment()).commit();
//                break;
//            case R.id.nav_settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SettingFragment()).add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").commit();
//                break;
//            case R.id.nav_logout:
//                Intent i=new Intent(getApplicationContext(),firstStartActivity.class);
//                startActivity(i);
//                break;
//            case R.id.nav_savedPlazas:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new SavedPlazaFragment()).add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").commit();
//                break;
//
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    @Override
//    public void onSearchStateChanged(boolean enabled) {
//    }
//
//    @Override
//    public void onSearchConfirmed(CharSequence text) {
//        geoLocate();
//
//
//    }
//
//    private void geoLocate() {
//        Log.d(TAG, "geoLocate: geolocating");
//
//        String searchString = searchBar.getText();
//
//        Geocoder geocoder = new Geocoder(MainScreenActivity.this);
//        List<Address> list = new ArrayList<>();
//        try{
//            list = geocoder.getFromLocationName(searchString, 1);
//        }catch (IOException e){
//            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
//        }
//
//        if(list.size() > 0){
//            Address address = list.get(0);
//
//            Log.d(TAG, "geoLocate: found a location: " + address.toString());
//            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
//            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
//                    address.getAddressLine(0));
//
//        }
//    }
//    private void moveCamera(LatLng latLng, float zoom, String title){
//        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//
//        if(!title.equals("My Location")){
//           options = new MarkerOptions()
//                    .position(latLng)
//                    .title(title);
//            mMap.addMarker(options);
//            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                @Override
//                public void onInfoWindowClick(Marker marker) {
//
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onButtonClicked(int buttonCode) {
//        switch (buttonCode){
//            case MaterialSearchBar.BUTTON_NAVIGATION:
//                drawer.openDrawer(Gravity.LEFT);
//                break;
//            case MaterialSearchBar.BUTTON_BACK:
//                searchBar.disableSearch();
//                break;
//        }
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        mMap = googleMap;
//        LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
//       // moveCamera(latLng,DEFAULT_ZOOM,"your Location");
////        options=new MarkerOptions().position(latLng).title("your location");
////        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
////        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15 ));
////        googleMap.addMarker(options);
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(getApplicationContext(),ViewPlazaDetailsActivity.class);
//                startActivity(intent);
//            }
//        });
//        mMap.setMyLocationEnabled(true);
//        googleMap.setOnMarkerClickListener(this);
//        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot s:dataSnapshot.getChildren())
//                {
//                    Plaza p=s.getValue(Plaza.class);
//                    LatLng location=new LatLng(p.getPlazaLatitude(),p.getPlazaLongitude());
//                    mMap.addMarker(new MarkerOptions().position(location).title(p.getPlazaName()));
//                }
//            }
//
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//    }
//
//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        return false;
//    }
//}