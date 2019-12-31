//
//package com.example.myapplication;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import android.view.Gravity;
//import android.view.MenuItem;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.core.view.GravityCompat;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.google.android.material.navigation.NavigationView;
//import com.mancj.materialsearchbar.MaterialSearchBar;
//
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.view.Menu;
//import android.widget.Button;
//import android.widget.Toast;
//
//import java.util.List;
//import java.util.Objects;
//
//
//public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,MaterialSearchBar.OnSearchActionListener
//{
//    private AppBarConfiguration mAppBarConfiguration;
//    private GoogleMap mMap;
//    SupportMapFragment sMApFragment;
//    private List<String> lastSearches;
//    private MaterialSearchBar searchBar;
//    DrawerLayout drawer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //for coordinates
//        sMApFragment=SupportMapFragment.newInstance();
//
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_savedPlazas, R.id.nav_profile, R.id.nav_filter,
//                R.id.nav_logout).setDrawerLayout(drawer).build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        //coordinates
//        sMApFragment.getMapAsync(this);
//        getSupportFragmentManager().beginTransaction().add(R.id.map,sMApFragment).commit();
//        getSupportActionBar().hide();
//
//
////        searchBar = findViewById(R.id.searchBar);
////        searchBar.setOnSearchActionListener(this);
////        searchBar.inflateMenu(R.menu.main);
////        searchBar.setCardViewElevation(10);
////        searchBar.setPlaceHolder("search here");
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//             Intent i;
//            switch (menuItem.getItemId()) {
//                case R.id.nav_profile:
//                    Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
//                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ToolsFragment()).commit();
//                    break;
//                case R.id.nav_logout:
//                    i=new Intent(getApplicationContext(), firstStartActivity.class);
//                    startActivity(i);
//                    finish();
//                    break;
//                case R.id.nav_savedPlazas:
//                    //Do some thing here
//                    // add navigation drawer item onclick method here
//                    break;
//            }
//        drawer.closeDrawer(GravityCompat.START);
//            return true;
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        //mMap.setMyLocationEnabled(true);
//        // Add a marker in Sydney and move the camera
//        LatLng pucit = new LatLng(31.570611, 74.310223);
//        mMap.addMarker(new MarkerOptions().position(pucit).title("Marker in PUCIT"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pucit,10F));
//
//    }
//
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //save last queries to disk
//        //saveSearchSuggestionToDisk(searchBar.getLastSuggestions());
//    }
//    @Override
//    public void onSearchStateChanged(boolean enabled) {
//        String s = enabled ? "enabled" : "disabled";
//        Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onSearchConfirmed(CharSequence text) {
//        startSearch(text.toString(), true, null, true);
//    }
////search bar
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
//    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
