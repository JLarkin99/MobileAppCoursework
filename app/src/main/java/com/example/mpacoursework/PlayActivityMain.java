package com.example.mpacoursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlayActivityMain extends AppCompatActivity {
    Intent intent;
    private Pet userPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_main);

        intent = new Intent(PlayActivityMain.this, MainActivity.class);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navSelectListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navSelectListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    //return false;

                    switch(item.getItemId()){
                        case R.id.backButton:
                            startActivity(intent);
                        case R.id.feedButton:
                            //create feed fragment
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            feedFragment fm2 = new feedFragment();
                            fragmentTransaction.add(R.id.fragFrame, fm2, "HELLO");
                            fragmentTransaction.commit();
                    }

                    return true;
                }
            };



}