package com.example.mpacoursework;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class feedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private feedAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<cardViewContents> cards;

    private ArrayList<String> foodList;
    ArrayList<String> userFoodList;
    String email;
    ImageButton exitButton;



    public feedFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        email = makeUsernameFromEmail(userEmail);;
        final DatabaseReference ref = database.getReference("users");
        userFoodList = new ArrayList<>();
        cards = new ArrayList<>();

        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new feedAdapter(cards);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        exitButton = (ImageButton) getActivity().findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("OCL","click on exitbutton detected");
                closeFragment();
            }
        });


        ref.child("users").child(email).child("foodlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};

                userFoodList = dataSnapshot.getValue(t);
                if(userFoodList != null) {
                    Log.i("FF","user foodlist triggered from DSS");
                    cards.clear();
                    for (String word : userFoodList) {
                        Log.i("FF",word);
                        cards.add(new cardViewContents(R.drawable.fastfood_24px, word));
                        //adapter.notifyItemInserted(userFoodList.indexOf(word));
                    }
                    updateRecyclerView();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });



        if(userFoodList != null) {
            for (String word : userFoodList) {
                cards.add(new cardViewContents(R.drawable.fastfood_24px, word));
            }
        }

//        cards.add(new cardViewContents(R.drawable.fastfood_24px, "test"));
//        cards.add(new cardViewContents(R.drawable.fastfood_24px, "test"));





        //ArrayList<String> userFoodList = new ArrayList<String>();

        //ref.child("foodlist").setValue(userFoodList);



        adapter.setOnItemClickListener(new feedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //remove item from list in database
                String foodName = cards.get(position).getText();
                if (userFoodList != null){
                    userFoodList.remove(position);
                    //adapter.notifyDataSetChanged();
                    //updateRecyclerView();
                    adapter.notifyItemRangeRemoved(position,userFoodList.size());
                    //update database with new list
                    ref.child("users").child(email).child("foodlist").setValue(userFoodList);

                    closeFragment(foodName);

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playfragment,null);
    }

    private String makeUsernameFromEmail(String Email){
        String username = Email;
        username = username.replace(".","");
        Log.i("Tag", username);
        return username;
    }

    private void updateRecyclerView(){
        if(mRecyclerView != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    public void closeFragment(){
        ((PlayActivityMain)getActivity()).getFeedListener().onFragmentExit();
    }

    public void closeFragment(String itemName){
        ((PlayActivityMain)getActivity()).getFeedListener().onFragmentItemSelected(itemName);
    }
}