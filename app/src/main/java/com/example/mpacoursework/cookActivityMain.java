package com.example.mpacoursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cookActivityMain extends AppCompatActivity {
    ArrayAdapter<String> storedArrayAdapter;
    ArrayAdapter<String> webArrayAdapter;
    private FragmentRefreshListener fragmentRefreshListener;
    private WebFragmentRefreshListener webFragmentRefreshListener;
    Intent backIntent;
    Intent timerIntent;
    SearchView searchView;


    final static String extraName = "RecipeName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);

        BottomNavigationView bottomNav = findViewById(R.id.playNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navSelectListener);
        backIntent = new Intent(cookActivityMain.this, MainActivity.class);
        timerIntent = new Intent(cookActivityMain.this, TimerActivity.class);

        searchView = findViewById(R.id.mSearchView);







    }

    private BottomNavigationView.OnNavigationItemSelectedListener navSelectListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    //return false;
                    //FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    switch(item.getItemId()){
                        case R.id.backButton:
                            item.setCheckable(true);
                            Log.i("NSI", "back button selected");
                            startActivity(backIntent);
                            return true;
                        case R.id.Timer:

                            item.setCheckable(true);
                            startActivity(timerIntent);
                            return true;



                    }

                    return false;
                }
            };
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh(String newText);
    }

    public interface WebFragmentRefreshListener{
        void onRefresh(String newText);
    }

    public WebFragmentRefreshListener getWebFragmentRefreshListener() {
        return webFragmentRefreshListener;
    }

    public void setWebFragmentRefreshListener(WebFragmentRefreshListener fragmentRefreshListener) {
        this.webFragmentRefreshListener = fragmentRefreshListener;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.cook_search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        //SearchView searchView = (SearchView) menuItem.getActionView();



        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener().onRefresh(newText);
                }
                if(getWebFragmentRefreshListener()!= null){
                    getWebFragmentRefreshListener().onRefresh(newText);
                }

                //storedArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}