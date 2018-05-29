package com.comunidad.musulmana.ahmadia.alislames;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    WebView myWebView;
    String web;
    String url;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("Allah");
        Log.d("1","TOKEN" + FirebaseInstanceId.getInstance().getToken());

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("LINK")!=null) {
                Intent i=new Intent(this,WebViewActivity.class);
                i.putExtra("link",getIntent().getStringExtra("LINK"));
                MainActivity.this.startActivity(i);
                Log.d("1","STARTING INTENT");
                finish();
            }}
            else {
            myWebView = findViewById(R.id.webview);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.getSettings().setDomStorageEnabled(true);
            myWebView.getSettings().setUseWideViewPort(true);
            myWebView.getSettings().setBuiltInZoomControls(true);
            myWebView.getSettings().setDisplayZoomControls(false);
            myWebView.setWebViewClient(new WebViewClient());
            Log.d("1","LOADING ALISLAM");
            myWebView.loadUrl("http://www.alislam.es");
        }

    }


}

