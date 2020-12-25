package com.smartwebarts.mech24ra;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.smartwebarts.mech24ra.Fonts.CustomTypefaceSpan;
import com.smartwebarts.mech24ra.fragment.Order_history;
import com.smartwebarts.mech24ra.fragment.TodayOrder;
import com.smartwebarts.mech24ra.payment.PaymentHistoryActivity;
import com.smartwebarts.mech24ra.profile.MachanicProfileActivity;
import com.smartwebarts.mech24ra.retrofit.LoginResponse;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;
import com.smartwebarts.mech24ra.utils.AccessToken;
import com.smartwebarts.mech24ra.utils.GPSTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener

        /*ConnectivityReceiver.ConnectivityReceiverListener, LocationListener*/ {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION = 202;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Menu nav_menu;
    private TextView tv_name;

    ImageView imageView;

    TextView mTitle;

    Toolbar toolbar;

    AppSharedPreferences preferences;

    String userid;
    private GoogleApiClient googleApiClient;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new AppSharedPreferences();
       UtilMethods.INSTANCE.updateAccessToken(MainActivity.this,new AccessToken().getAccess_token(MainActivity.this), new mCallBackResponse() {
            @Override
            public void success(String from, String message) {

            }

            @Override
            public void fail(String from) {

            }
        });

        //UtilMethods.INSTANCE.updateAccessToken(MainActivity.this,new AccessToken().getAccess_token());
       /* FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        Log.e(TAG, token);
                    }
                });
*/

        gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();

        if (gpsTracker.canGetLocation()) {
            double lat = gpsTracker.getLatitude();
            double lng = gpsTracker.getLongitude();
//for saving data in firebase database
            DatabaseReference myRef = database.getReference(preferences.getLoginUserLoginId(this) +"/"+"lat");
            DatabaseReference myRef2 = database.getReference(preferences.getLoginUserLoginId(this) +"/"+"lng");
            myRef.setValue(lat);
            myRef2.setValue(lng);
        } else {
            //Todo show settings dialog;
        }

        userid = preferences.getLoginUserLoginId(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);

            if (view instanceof TextView) {
                TextView textView = (TextView) view;

                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "Font/Bold.otf");
                textView.setTypeface(myCustomFont);
            }

        }

        checkPermission();

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();

        for (
                int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            applyFontToMenuItem(mi);
        }

//
//        sessionManagement = new Session_management(MainActivity.this);

        View headerView = navigationView.getHeaderView(0);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        navigationView.setNavigationItemSelectedListener(this);
        nav_menu = navigationView.getMenu();
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
//        iv_profile = (ImageView) header.findViewById(R.id.iv_header_img);

        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        updateHeader();
        sideMenu();
/*
        Fragment fm = null;
        Bundle args = new Bundle();
        TodayOrder tm = new TodayOrder();
        androidx.fragment.app.FragmentManager manager21 = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction transaction21 = manager21.beginTransaction();
        transaction21.add(R.id.contentPanel, tm);
        transaction21.commit();*/

    }

    public void updateHeader() {
        String getname = preferences.getLoginUserName(this);
        tv_name.setText(getname);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Font/Bold.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    //
//
    public void sideMenu() {
        nav_menu.findItem(R.id.nav_log_out).setVisible(true);
    }
    //
//
//    public void setTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }
//
//
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fm = null;
        Bundle args = new Bundle();
        if (id == R.id.nav_order) {
            TodayOrder tm = new TodayOrder();
            androidx.fragment.app.FragmentManager manager21 = getSupportFragmentManager();
            androidx.fragment.app.FragmentTransaction transaction21 = manager21.beginTransaction();
            transaction21.add(R.id.contentPanel, tm);
            transaction21.commit();

        }
        else  if (id==R.id.nav_Nextorder){
//            NextOrder category_fragment = new NextOrder();
//            FragmentManager manager2 = getSupportFragmentManager();
//            FragmentTransaction transaction2 = manager2.beginTransaction();
//            transaction2.replace(R.id.contentPanel, category_fragment);
//            transaction2.commit();

        }
        else  if (id==R.id.nav_order_history){
            Fragment fma = new Order_history();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.contentPanel, fma, "Order_History")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

        }
        else if (id == R.id.nav_log_out) {
//            sessionManagement.logoutSession();
            preferences.logout(this);
//            finish();
        }
        if (fm != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().add(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    private void checkPermission() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            turnongps();
        }  else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    private void turnongps() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
//            Toast.makeText(getApplicationContext(),"Gps already enabled",Toast.LENGTH_SHORT).show();
            //getActivity().finish();
        }
        // Todo Location Already on  ... end

        if(!hasGPSDevice(this)){
//            Toast.makeText(getApplicationContext(),"Gps not Supported",Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            // Log.e("Neha","Gps already enabled");
//            Toast.makeText(getApplicationContext(),"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
        }else{
            // Log.e("Neha","Gps already enabled");
//            Toast.makeText(getApplicationContext(),"Gps already enabled",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }
    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(7 * 1000);  //30 * 1000
            locationRequest.setFastestInterval(5 * 1000); //5 * 1000
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient,builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity.this,REQUEST_LOCATION);

                                // getActivity().finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    turnongps();
                }  else {
                    turnongps();
                }
                return;
        }
    }

    public void profile(View view) {
        Intent intent=new Intent(MainActivity.this, MachanicProfileActivity.class);
        startActivity(intent);
    }

    public void paymentHistory(View view) {
        Intent intent=new Intent(MainActivity.this, PaymentHistoryActivity.class);
        startActivity(intent);
    }

    public void Todayorder(View view) {
        Fragment fm = null;
        Bundle args = new Bundle();
        TodayOrder tm = new TodayOrder();
        androidx.fragment.app.FragmentManager manager21 = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction transaction21 = manager21.beginTransaction();
        transaction21.add(R.id.contentPanel, tm);
        transaction21.commit();
    }

    public void OrderHistory(View view) {
        Fragment fma = new Order_history();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.contentPanel, fma, "Order_History")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }
}
