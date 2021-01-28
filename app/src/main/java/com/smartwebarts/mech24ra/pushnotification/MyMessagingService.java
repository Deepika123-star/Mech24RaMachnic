package com.smartwebarts.mech24ra.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.utils.AccessToken;

public class MyMessagingService extends FirebaseMessagingService {

    private String messageBody;
    private String msg;
    private String img;
    private String title;
    private String key;
    private String uid;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("onNewToken", "Refreshed token: " + refreshedToken);
        new AccessToken().setAccess_token(getApplicationContext(), refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String str_from = "" + remoteMessage.getFrom();
        Log.e("From : ", str_from);

        title = "" + remoteMessage.getData().get("title");
        Log.e("Title", title);

        messageBody = "" + remoteMessage.getData().get("body");
        Log.e("Body", messageBody);

        msg = "" + remoteMessage.getData().get("msg");
        Log.e("Msg", msg);

        img = "" + remoteMessage.getData().get("img");
        Log.e("Img", img);

        key = "" + remoteMessage.getData().get("key");
        Log.e("Key", key);

        uid = "" + remoteMessage.getData().get("uid");
        Log.e("Uid", uid);

        sendNotification();

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     */

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isNewRideAvailable", true);
        intent.putExtra("messageBody", messageBody);
        intent.putExtra("msg", msg);
        intent.putExtra("img", img);
        intent.putExtra("uid", uid);
        intent.putExtra("key", key);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        // Get the PendingIntent containing the entire back stack
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int numMessages = 0;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                        .setContentTitle(title)
                        .setNumber(numMessages++)
                        .setStyle(new NotificationCompat.BigTextStyle())
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setTimeoutAfter(30*1000)
                        .addAction(0, "Accept", pendingIntent)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (notificationManager!=null){

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 , notificationBuilder.build());

            registerReceiver(broadcastReceiver,
                    new IntentFilter("com.from.notification"));

            Intent intentNotification = new Intent();
            intentNotification.setAction("com.from.notification");
            intentNotification.putExtra("isNewRideAvailable", true);
            intentNotification.putExtra("messageBody", messageBody);
            intentNotification.putExtra("msg", msg);
            intentNotification.putExtra("img", img);
            intentNotification.putExtra("uid", uid);
            intentNotification.putExtra("key", key);
            sendBroadcast(intentNotification);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // do your stuff related to start activity
            Toast.makeText(context, "broadcastReceiver", Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("isNewRideAvailable", true);
            intent1.putExtra("messageBody", messageBody);
            intent1.putExtra("msg", msg);
            intent1.putExtra("img", img);
            intent1.putExtra("uid", uid);
            intent1.putExtra("key", key);
            getApplicationContext().startActivity(intent1);

        }
    };
}
