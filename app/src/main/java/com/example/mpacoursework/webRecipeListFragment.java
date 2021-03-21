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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class webRecipeListFragment extends Fragment {

    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> userFoodList;
    String email;
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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        email = makeUsernameFromEmail(userEmail);
        userFoodList = new ArrayList<>();
        final DatabaseReference ref = database.getReference("users");

        ref.child("users").child(email).child("foodlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                Log.i("DSS","DSS was called");
                if(dataSnapshot.getValue(t) != null) {
                    Log.i("DSS","value isn't null");
                    userFoodList = dataSnapshot.getValue(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("DSS cancel", "DSS cancel called");
                ref.child("users").child(email).child("foodlist").setValue(userFoodList);
            }
        });

        webListView.setClickable(true);
        webListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                String name = (String) parent.getItemAtPosition(position);

                userFoodList.add(name);
                ref.child("users").child(email).child("foodlist").setValue(userFoodList);



                webRecipeIntent.putExtra("RecipeName", webRecipes.get(name));
                startActivity(webRecipeIntent);
            }
        });
        //web recipe names and links
        webRecipes.put("Brownies","https://www.bbcgoodfood.com/recipes/vegan-brownies");
        webRecipes.put("Lasagne","https://www.bbcgoodfood.com/recipes/next-level-lasagne");
        webRecipes.put("Mushroom Potato Soup","https://www.bbcgoodfood.com/recipes/mushroom-potato-soup");
        webRecipes.put("Greek Roast Lamb","https://www.bbcgoodfood.com/recipes/greek-roast-lamb");
        webRecipes.put("Chorizo Risotto", "https://www.bbcgoodfood.com/recipes/chorizo-rosemary-pearl-barley-risotto");
        webRecipes.put("Salmon Traybake", "https://www.bbcgoodfood.com/recipes/creamy-salmon-leek-potato-traybake");

        //add map keys o list to be displayed
        for(String key: webRecipes.keySet()){
            webRecipeList.add(key);
            Log.d("current key:",key);
        }

        mArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1 , webRecipeList);
        webListView.setAdapter(mArrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((cookActivityMain)getActivity()).setWebFragmentRefreshListener(new cookActivityMain.WebFragmentRefreshListener() {
            @Override
            public void onRefresh(String newText) {
                // your method

                mArrayAdapter.getFilter().filter(newText);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_recipe_list, container, false);
    }

    private String makeUsernameFromEmail(String Email){
        String username = Email;
        username = username.replace(".","");
        Log.i("Tag", username);
        return username;
    }
}