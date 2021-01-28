package com.smartwebarts.mech24ra;

import android.Manifest;
import android.app.AlertDialog;
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
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartwebarts.mech24ra.Fonts.CustomTypefaceSpan;
import com.smartwebarts.mech24ra.activity.NewRequest;
import com.smartwebarts.mech24ra.activity.profile.MyProfileFragment;
import com.smartwebarts.mech24ra.fragment.Order_history;
import com.smartwebarts.mech24ra.fragment.TodayOrder;
import com.smartwebarts.mech24ra.payment.PaymentHistoryActivity;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;
import com.smartwebarts.mech24ra.utils.AccessToken;
import com.smartwebarts.mech24ra.utils.GPSTracker;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_LOCATION = 202;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Menu nav_menu;
    private TextView tv_name;
    private LinearLayout ll_01_contentLayer_accept_or_reject_now;
    Toolbar toolbar;
    NewRequest newRequest;
    AppSharedPreferences preferences;

    String userid;
    private GoogleApiClient googleApiClient;
    GPSTracker gpsTracker;
    private boolean isNewRideAvailable;
    private TextView txtPickup, txt01Timer;
    private MediaPlayer mPlayer;
    private CountDownTimer countDownTimer;
    private Button btn_02_reject, btn_02_accept;
    private DatabaseReference dbRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new AppSharedPreferences();
        updateAcessToken();

        init();
        gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();

        userid = preferences.getLoginUserLoginId(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkPermission();

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
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

        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        navigationView.setNavigationItemSelectedListener(this);
        nav_menu = navigationView.getMenu();
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        header.setOnClickListener(v -> {
             Intent intent = new Intent(MainActivity.this, MyProfileFragment.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
             startActivity(intent);
        });
        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        updateHeader();
        sideMenu();

        UtilMethods.INSTANCE.version(this, new mCallBackResponse() {
            @Override
            public void success(String from, String message) {

            }

            @Override
            public void fail(String from) {

            }
        });
    }

    private void init() {
        isNewRideAvailable = getIntent().getExtras() != null && getIntent().getExtras().getBoolean("isNewRideAvailable", false);
        ll_01_contentLayer_accept_or_reject_now = findViewById(R.id.ll_01_contentLayer_accept_or_reject_now);
        txt01Timer = findViewById(R.id.txt01Timer);
        txtPickup = findViewById(R.id.txtPickup);
        btn_02_reject = findViewById(R.id.btn_02_reject);
        btn_02_accept = findViewById(R.id.btn_02_accept);

        String messageBody = getIntent().getExtras() != null ? getIntent().getExtras().getString("messageBody", "") : "";
        String msg = getIntent().getExtras() != null ? getIntent().getExtras().getString("msg", "") : "";
        String uid = getIntent().getExtras() != null ? getIntent().getExtras().getString("uid", "") : "";
        String key = getIntent().getExtras() != null ? getIntent().getExtras().getString("key", "") : "";
        Log.e(TAG, "init: " + messageBody + " : " + msg + " : " + uid + " : " + key + " : " + isNewRideAvailable );

        if (isNewRideAvailable) {
            ll_01_contentLayer_accept_or_reject_now.setVisibility(View.VISIBLE);

            try {
                dbRequests = FirebaseDatabase.getInstance().getReference("requests/" + key);
                txtPickup.setText(msg);
                newRequest = new NewRequest(uid, "");
            } catch (Exception ignored) {}

            countDownTimer = new CountDownTimer(30 * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    txt01Timer.setText(String.format("%s", millisUntilFinished / 1000));
                    //here you can have your logic to set text to edittext
                    if (mPlayer == null) {
                        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alert_tone);
                    } else {
                        if (!mPlayer.isPlaying()) {
                            mPlayer.start();
                        }
                    }
                }

                public void onFinish() {
                    if (mPlayer != null && mPlayer.isPlaying()) {
                        mPlayer.stop();
                        mPlayer = null;
                    }

                    txt01Timer.setText(getString(R.string.timeout));
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ll_01_contentLayer_accept_or_reject_now.setVisibility(View.GONE);
                            mPlayer = null;
                            if (countDownTimer!=null) {
                                countDownTimer.cancel();
                            }
                        }
                    }, 100);
                }

            };

            countDownTimer.start();
        } else {
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

            if (countDownTimer!=null) {
                countDownTimer.cancel();
            }
        }

        btn_02_accept.setOnClickListener(this);
        btn_02_reject.setOnClickListener(this);
    }

    private void updateAcessToken() {
        UtilMethods.INSTANCE.updateAccessToken(MainActivity.this,new AccessToken().getAccess_token(MainActivity.this), new mCallBackResponse() {
            @Override
            public void success(String from, String message) {

            }

            @Override
            public void fail(String from) {

            }
        });
    }

    public void updateHeader() {
        String getname = preferences.getLoginUserName(this);
        tv_name.setText(getname);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Font/bold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void sideMenu() {
        nav_menu.findItem(R.id.nav_log_out).setVisible(true);
    }

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
            preferences.logout(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    private void checkPermission() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) == PackageManager.PERMISSION_GRANTED
        ) {
            // You can use the API that requires the permission.
            turnongps();
        }  else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.FOREGROUND_SERVICE}, 101);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

            }
        }
    }

    private void turnongps() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {

            if (gpsTracker.canGetLocation()) {

                Intent intent = new Intent(this, BgService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            } else {
                gpsTracker.showSettingsAlert();
            }
        }

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
        Intent intent=new Intent(MainActivity.this, MyProfileFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

    @Override
    public void onClick(View v) {

        if (v == btn_02_accept) {

            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

            txt01Timer.setText(R.string.accepted);

            newRequest.mid = preferences.getLoginUserLoginId(this);
            dbRequests.setValue(newRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = new Intent( MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
        }

        if (v == btn_02_reject) {
            if (mPlayer != null && mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

            txt01Timer.setText(R.string.cancelled);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent( MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer!=null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

        if (countDownTimer!=null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        showHelpDialog();
    }

    public void showHelpDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Are you Sure ?");
        dialog.setMessage("To keep instant updates of new service requests, do not destroy this page and application instead just press power button and make your app sleep otherwise this application may not work properly or may not work");
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(true);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Got it!", (dialogInterface, i) -> {
            dialog.dismiss();
        });
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go Back!", (dialogInterface, i) -> {
            super.onBackPressed();
        });
        dialog.show();
    }
}
