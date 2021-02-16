package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class cookActivityMain extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        
        ListView listView = findViewById(R.id.cookSearchBar);
        List<String> cookList = new ArrayList<>();
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                Toast.makeText(cookActivityMain.this,"ListView: " + parent.toString() + "\n" +
                                "View: " + view.toString() + "\n" +
                                "position: " + String.valueOf(position) + "\n" +
                                "id: " + String.valueOf(id),
                        Toast.LENGTH_LONG).show();
            }
        });
        //prototype, change later
        cookList.add("Spaghetti");
        cookList.add("burger");
        cookList.add("Crumbs");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , cookList);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.cook_search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }




}