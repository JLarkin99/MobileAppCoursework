package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebRecipeView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_recipe_view);

        WebView myWebView = new WebView(this);
        setContentView(myWebView);

        Intent intent = getIntent();
        String URL = intent.getStringExtra(cookActivityMain.extraName);
        myWebView.loadUrl(URL);
    }
}