package com.smartwebarts.mech24ra.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.activity.Splash;

public class MyMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e("onNewToken", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String str_from = "" + remoteMessage.getFrom();
//        Log.e("From : ", str_from);

        String str_title = "" + remoteMessage.getNotification().getTitle();
//        Log.e("Title", str_title);

        String str_body = "" + remoteMessage.getNotification().getBody();
//        Log.e("Body", str_body);

        String str_msg = "" + remoteMessage.getNotification().getBody();
//        Log.e("Msg", str_msg);

//        String str_img = "" + remoteMessage.getNotification().getImageUrl();
//        Log.e("Img", str_img);

//        Bitmap image = null;
//        try {
//            URL url = new URL(str_img);
//            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch(IOException e) {
//            System.out.println(e.getMessage());
//        }

        sendNotification(str_title, str_body, str_msg/*, image*/);

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody, String msg/*, Bitmap picture*/) {

        Intent intent = new Intent(this, Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("msg", msg);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        // Get the PendingIntent containing the entire back stack
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        String channelId = getString(R.string.default_notification_channel_id);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ask_suyash))
                        .setContentTitle(title)
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(picture))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
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

        }

    }


}
