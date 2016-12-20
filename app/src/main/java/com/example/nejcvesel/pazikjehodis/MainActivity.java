package com.example.nejcvesel.pazikjehodis;

import android.app.Activity;
import android.app.Fragment;
//import android.app.FragmentManager;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nejcvesel.pazikjehodis.retrofitAPI.BackendAPICall;
import com.example.nejcvesel.pazikjehodis.retrofitAPI.Models.Location;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

/**
 * Created by brani on 12/18/2016.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LocationFragment.OnListFragmentInteractionListener,
        LocationDetailFragment.OnFragmentInteractionListener{
    public String authToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FacebookSdk.sdkInitialize(getApplicationContext());

        AccessToken at = AccessToken.getCurrentAccessToken();
        if (at != null) {
            System.out.println("Token je ze: " + at.getToken());
            System.out.println("User id: " + at.getUserId());
            System.out.println("App id: " + at.getApplicationId());
            authToken = at.getToken();
        }
        else {
            /* authToken = at.getToken(); */
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MapsFragment fragment = (MapsFragment) getFragmentManager().findFragmentById(R.id.content_frame);
//                fragment.AddMapListener();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BackendAPICall bra = new BackendAPICall();
        bra.getAllLocations(authToken);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();

        switch (id){
            case R.id.nav_map:
                fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
                break;
            case R.id.nav_login:
                fm.beginTransaction().replace(R.id.content_frame, new LogInFragment()).commit();
                break;
            case R.id.nav_details:
                fm.beginTransaction().replace(R.id.content_frame, new LocationFragment()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    public void OpenDetailsViewer(){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new DetailsViewerFragment())
                .addToBackStack(null).commit();
    }

    public void OpenMapsFragment(){
        FragmentManager fm = getFragmentManager();
        boolean fragmentPopped = fm.popBackStackImmediate("MapsFragment", 0);
        if (!fragmentPopped) {
            fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
        }
//        fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

    public void CancelForm (View view){
        OpenMapsFragment();
    }

    public void SaveForm (View view){
        OpenMapsFragment();
    }

    public void AttachPic (View view){

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Location item) {

    }
}
