package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.SavedPlazaFragment;
import com.example.myapplication.Fragment.SettingFragment;
import com.example.myapplication.Fragment.ViewProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

public class MainScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener,OnMapReadyCallback {

    private DrawerLayout drawer;
    private MaterialSearchBar searchBar;
    private GoogleMap mMap;
    SupportMapFragment sMApFragment;
    Fragment fragment=null;
    private List<String> lastSearches;
    private Button delete;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        sMApFragment= SupportMapFragment.newInstance();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sMApFragment.getMapAsync((OnMapReadyCallback) this);
        getSupportFragmentManager().beginTransaction().add(R.id.map,sMApFragment).commit();


        //For materialSearchBar
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.setPlaceHolder("search here");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {


            /////Not null means that account was successfully signed in and here are the further steps
//            user.setPersonName(acct.getDisplayName());
//            user.setPersonGivenName(acct.getGivenName());
//            user.setPersonFamilyName(acct.getFamilyName());
//            user.setPersonEmail(acct.getEmail());
//            user.setPersonId(acct.getId());
//            user.setPersonPhoto(acct.getPhotoUrl());
            Toast.makeText(getApplicationContext(),"Your name is: "+acct.getDisplayName()+" and email is: "+acct.getEmail(),Toast.LENGTH_LONG);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk
        lastSearches=searchBar.getLastSuggestions();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (item.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").replace(R.id.fragment_container,
                        new ViewProfileFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingFragment()).add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").commit();
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_savedPlazas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SavedPlazaFragment()).add(new HomeFragment(),"HomeFragment").addToBackStack("HomeFragment").commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(),ViewPlazaDetailsActivity.class);
                startActivity(intent);


            }
        });
        //mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng pucit = new LatLng(31.570611, 74.310223);
        mMap.addMarker(new MarkerOptions().position(pucit).title("Marker in PUCIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pucit,10F));


    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
                Toast.makeText(getApplicationContext(),"Signout Successfully",Toast.LENGTH_LONG);
                Intent i=new Intent(getApplicationContext(),firstStartActivity.class);
                startActivity(i);

            }
        });
    }

}
