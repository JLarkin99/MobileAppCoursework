package com.example.mpacoursework;

import android.app.Activity;
import android.content.Context;
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
import java.util.List;


public class storedRecipeListFragment extends Fragment {
    ArrayAdapter<String> mArrayAdapter;
    Activity a;
    ArrayList<String> userFoodList;
    String email;
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
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        email = makeUsernameFromEmail(userEmail);
        final DatabaseReference ref = database.getReference("users");
        userFoodList = new ArrayList<>();
        //ArrayList<String> userFoodList = new ArrayList<String>();

        //ref.child("foodlist").setValue(userFoodList);

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




        ListView storedListView = getView().findViewById(R.id.storedRecipeListView);

        List<String> storedRecipeList = new ArrayList<>();

        storedRecipeList.add("Spaghetti");
        storedRecipeList.add("Burgers");
        storedRecipeList.add("Cookies");
        storedRecipeList.add("Fruit Salad");
        storedRecipeList.add("Scotch Broth");
        storedRecipeList.add("Irish Stew");


        storedListView.setClickable(true);
        storedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked perform some action...
                //TODO: get food name -> get user food list -> add food item to list -> update foodlist
                //get food name
                String name = (String) parent.getItemAtPosition(position);
                //push foodlist item
                userFoodList.add(name);
                if(userFoodList == null){
                    Log.i("UFL", "UFL is null");
                }
                else if(name == null){
                    Log.i("name", "name is null");
                }
                ref.child("users").child(email).child("foodlist").setValue(userFoodList);


                //start intent
                storedRecipeIntent.putExtra("RecipeName", name.replace(" ",""));
                startActivity(storedRecipeIntent);
            }
        });
        //prototype, change later


        //creates null object reference?
        mArrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1 , storedRecipeList);
        storedListView.setAdapter(mArrayAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((cookActivityMain)getActivity()).setFragmentRefreshListener(new cookActivityMain.FragmentRefreshListener() {
            @Override
            public void onRefresh(String newText) {
                // your method

                mArrayAdapter.getFilter().filter(newText);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stored_recipe_list, container, false);
    }

    private void updateFoodList(ArrayList<String> list){

    }

    private String makeUsernameFromEmail(String Email){
        String username = Email;
        username = username.replace(".","");
        Log.i("Tag", username);
        return username;
    }
}