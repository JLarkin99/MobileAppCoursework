package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cookActivityMain extends AppCompatActivity {
    ArrayAdapter<String> storedArrayAdapter;
    ArrayAdapter<String> webArrayAdapter;
    final Map<String, String> webRecipes = new HashMap<String, String>();
    final static String extraName = "RecipeName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);

        final Intent storedRecipeIntent = new Intent(cookActivityMain.this,RecipeViewActivity.class);
        final Intent webRecipeIntent = new Intent(cookActivityMain.this,WebRecipeView.class);

        
        ListView storedListView = findViewById(R.id.storedRecipeListView);
        ListView webListView = findViewById(R.id.webRecipeListView);
        List<String> storedRecipeList = new ArrayList<>();
        List<String> webRecipeList = new ArrayList<>();
        //Map<String, String> webRecipes = new HashMap<String, String>();

        storedListView.setClickable(true);
        storedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                Toast.makeText(cookActivityMain.this,"ListView: " + parent.toString() + "\n" +
                                "View: " + view.toString() + "\n" +
                                "position: " + String.valueOf(position) + "\n" +
                                "id: " + String.valueOf(id),
                        Toast.LENGTH_LONG).show();
                String name = (String) parent.getItemAtPosition(position);
                storedRecipeIntent.putExtra("RecipeName", name);
                startActivity(storedRecipeIntent);
            }
        });

        webListView.setClickable(true);
        webListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                Toast.makeText(cookActivityMain.this,"ListView: " + parent.toString() + "\n" +
                                "View: " + view.toString() + "\n" +
                                "position: " + String.valueOf(position) + "\n" +
                                "id: " + String.valueOf(id),
                        Toast.LENGTH_LONG).show();
                String name = (String) parent.getItemAtPosition(position);
                storedRecipeIntent.putExtra("RecipeName", webRecipes.get(name));
                startActivity(storedRecipeIntent);
            }
        });
        //prototype, change later
        storedRecipeList.add("Spaghetti");
        storedRecipeList.add("burger");
        storedRecipeList.add("Crumbs");
        //web recipe names and links
        webRecipes.put("Brownies","https://www.bbcgoodfood.com/recipes/vegan-brownies");
        webRecipes.put("Lasagne","https://www.bbcgoodfood.com/recipes/next-level-lasagne");

        //add map keys o list to be displayed
        for(String key: webRecipes.keySet()){
            webRecipeList.add(key);
        }

        storedArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , storedRecipeList);
        storedListView.setAdapter(storedArrayAdapter);

        webArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , webRecipeList);
        storedListView.setAdapter(webArrayAdapter);


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
                storedArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }




}