package com.myapp.loader.myapplication.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.myapp.loader.myapplication.R;

public class WebViewActivity extends AppCompatActivity {
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

       myWebView=findViewById(R.id.webview);
        myWebView.loadUrl(getIntent().getExtras().getString("URL"));
    }
}
