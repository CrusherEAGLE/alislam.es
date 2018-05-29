package com.comunidad.musulmana.ahmadia.alislames;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by What's That Lambda on 11/6/17.
 */

public class MessagingService extends FirebaseMessagingService {

    Bitmap bitmap;


    String url;
    String message;
    String imageUri;
    String category;
    Drawable drawble;

    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {
            message = remoteMessage.getData().get("message");
            imageUri = remoteMessage.getData().get("image");
            category = remoteMessage.getData().get("category");
            url = remoteMessage.getData().get("url");
            bitmap = getBitmapfromUrl(imageUri);
            switch (category) {
                case "Articulo":
                    sendNotification(category, message, bitmap, url, 0); break;
                case "Sermon del Viernes":
                    sendNotification(category, message, bitmap, url, 1); break;
                case "Comunicado":
                    sendNotification(category, message, bitmap, url, 2); break;
                case "etc":
                    sendNotification(category, message, bitmap, url, 3); break;
                default:
                    sendNotification(category, message, bitmap, url, 4); break;
            }

        }
    }


    public void sendNotification(String cat, String messageBody, Bitmap image, String link, int no) {
        Intent intent = new Intent(this, MainActivity.class);



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("LINK",link);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.minarete))/*Notification icon image*/
                .setSmallIcon(R.drawable.ic_stat_call_white)
                .setColor(getResources().getColor(R.color.white))
                .setContentTitle("Alislam.es")
                .setContentText(cat + ": " + messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId("ch_1");;



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("ch_1", "Main", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(no /* ID of notification */, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
