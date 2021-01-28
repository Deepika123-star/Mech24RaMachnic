package com.smartwebarts.mech24ra;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartwebarts.mech24ra.activity.Mechanic;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;
import com.smartwebarts.mech24ra.utils.GPSTracker;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BgService extends Service {
    private static final String TAG = BgService.class.getSimpleName();
    private static Timer timer = new Timer();
    private Context context;
    private GPSTracker gps;
    private double previousLat = 0, previousLng = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setAutoCancel(false)
                    .setContentText("").build();

            startForeground(1, notification);
        }

        gps = new GPSTracker(getApplicationContext());
        gps.getLocation();
        startService();
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            toastHandler.sendEmptyMessage(0);
        }
    }

    public final Handler toastHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);



            if(gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                if (latitude!=0 && longitude!=0) {

                    if (previousLat == latitude && previousLng == longitude) {
                        //do nothing
                    } else {
                        saveLatLng(latitude, longitude);
                    }
                } else {
//                    saveLatLng(latitude, longitude);
                }
            } else {
                Log.e(TAG, "handleMessage: " + "Unable to get location" );
            }
        }
    };

    private void saveLatLng(double latitude, double longitude) {
        //for saving data in firebase database

        previousLat = latitude;
        previousLng = longitude;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        AppSharedPreferences preferences = new AppSharedPreferences();

        if (preferences.getLoginUserLoginId(getApplicationContext()).isEmpty()) {
            return;
        }
        DatabaseReference myRef = database.getReference("drivers/"+preferences.getLoginUserLoginId(getApplicationContext()) +"/");
        myRef.setValue(new Mechanic(latitude, longitude)).addOnCompleteListener(task -> {

//            Log.e(TAG, "saveLatLng: " + task.isSuccessful() + task.getException().getMessage());
            if (UtilMethods.INSTANCE.isNetworkAvialable(getApplicationContext())) {
                UtilMethods.INSTANCE.updateLatLng(context, preferences, previousLat, previousLng, new mCallBackResponse() {
                    @Override
                    public void success(String from, String message) {

                    }

                    @Override
                    public void fail(String from) {
                    }
                });
            }

        });
    }

}
