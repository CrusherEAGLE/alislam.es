package com.comunidad.musulmana.ahmadia.alislames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView myWebView;
    String web;
    String url;
    Intent i;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        i = getIntent();
        url = i.getStringExtra("link");


        myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.setWebViewClient(new WebViewClient());

        Log.d("1","URL IS: " + url);
        if (url.isEmpty())
        {
            myWebView.loadUrl("http://www.alislam.es");
        }
        else {
            myWebView.loadUrl(url);
        }
    }
}
