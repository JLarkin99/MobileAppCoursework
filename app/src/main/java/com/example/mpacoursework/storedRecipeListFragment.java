package com.example.mpacoursework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class storedRecipeListFragment extends Fragment {
    ArrayAdapter<String> storedArrayAdapter;
    Activity a;
    public storedRecipeListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            a=(Activity) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final Intent storedRecipeIntent = new Intent(storedRecipeListFragment.this.getActivity(),RecipeViewActivity.class);





        ListView storedListView = getView().findViewById(R.id.storedRecipeListView);

        List<String> storedRecipeList = new ArrayList<>();

        //TODO: find out what causes error with this.getActivity
        storedListView.setClickable(true);
        storedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...

                String name = (String) parent.getItemAtPosition(position);
                storedRecipeIntent.putExtra("RecipeName", name);
                startActivity(storedRecipeIntent);
            }
        });
        //prototype, change later
        storedRecipeList.add("Spaghetti");
        storedRecipeList.add("burger");
        storedRecipeList.add("Crumbs");

        //creates null object reference?
        storedArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1 , storedRecipeList);
        storedListView.setAdapter(storedArrayAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((cookActivityMain)getActivity()).setFragmentRefreshListener(new cookActivityMain.FragmentRefreshListener() {
            @Override
            public void onRefresh(String newText) {
                // your method

                storedArrayAdapter.getFilter().filter(newText);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stored_recipe_list, container, false);
    }
}