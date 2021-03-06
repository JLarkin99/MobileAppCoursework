package com.example.mpacoursework;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class webRecipeListFragment extends Fragment {

    ArrayAdapter<String> webArrayAdapter;
    final Map<String, String> webRecipes = new HashMap<String, String>();

    public webRecipeListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onStart() {
        super.onStart();
        final Intent webRecipeIntent = new Intent(webRecipeListFragment.this.getActivity(),WebRecipeView.class);
        ListView webListView = getView().findViewById(R.id.webRecipeListView);
        List<String> webRecipeList = new ArrayList<>();

        webListView.setClickable(true);
        webListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                String name = (String) parent.getItemAtPosition(position);
                webRecipeIntent.putExtra("RecipeName", webRecipes.get(name));
                startActivity(webRecipeIntent);
            }
        });
        //TODO: find out why web recipes don't show
        //web recipe names and links
        webRecipes.put("Brownies","https://www.bbcgoodfood.com/recipes/vegan-brownies");
        webRecipes.put("Lasagne","https://www.bbcgoodfood.com/recipes/next-level-lasagne");

        //add map keys o list to be displayed
        for(String key: webRecipes.keySet()){
            webRecipeList.add(key);
            Log.d("current key:",key);
        }

        webArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1 , webRecipeList);
        webListView.setAdapter(webArrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((cookActivityMain)getActivity()).setFragmentRefreshListener(new cookActivityMain.FragmentRefreshListener() {
            @Override
            public void onRefresh(String newText) {
                // your method

                webArrayAdapter.getFilter().filter(newText);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_recipe_list, container, false);
    }
}