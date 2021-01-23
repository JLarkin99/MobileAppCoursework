package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton playButton;
    ImageButton cookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent playIntent = new Intent(MainActivity.this,PlayActivityMain.class);
        final Intent cookIntent = new Intent(MainActivity.this,cookActivityMain.class);
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(playIntent);
            }
        });

        cookButton = (ImageButton) findViewById(R.id.cookButton);
        cookButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(cookIntent);
            }
        });

    }
}