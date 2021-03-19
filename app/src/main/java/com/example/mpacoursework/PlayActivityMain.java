package com.example.mpacoursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlayActivityMain extends AppCompatActivity {
    Intent intent;
    Pet userPet;
    String email;
    TextView nameText;
    TextView hungerText;

    public Pet getUserPet() {
        return userPet;
    }

    public void setUserPet(Pet userPet) {
        this.userPet = userPet;
    }


    private FeedListener feedListener = new FeedListener() {
        @Override
        public void onFragmentExit() {

        }

        @Override
        public void onFragmentItemSelected(String itemName) {

        }
    };
    private FragmentManager fm = getFragmentManager();
    final feedFragment fm2 = new feedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_main);

        intent = new Intent(PlayActivityMain.this, MainActivity.class);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        email = makeUsernameFromEmail(userEmail);
        final DatabaseReference ref = database.getReference("users");
        nameText = findViewById(R.id.nameText);
        hungerText = findViewById(R.id.hungerText);

        ref.child("users").child(email).child("userpet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                GenericTypeIndicator<Pet> t = new GenericTypeIndicator<Pet>() {};
                Log.i("DSS","DSS was called");
                if(dataSnapshot.getValue(t) != null) {
                    Log.i("DSS","value isn't null");
                    setUserPet(dataSnapshot.getValue(t));
                    Log.i("UPN", getUserPet().getName());
                    if(userPet != null){
                        Log.i("UPN","user pet is not null");
                        nameText.setText(userPet.getName());
                    }
                    if(userPet != null){
                        String hungerString = "Hunger: " + userPet.getHunger() + "/" + Pet.MAX_HUNGER;
                        hungerText.setText(hungerString);
                    }
                }
                else{
                    userPet = new Pet(100,"Steve");
                    ref.child("users").child(email).child("userpet").setValue(userPet);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("DSS cancel", "DSS cancel called");

            }
        });


        //userPet = new Pet();

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

                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.add(R.id.fragFrame, fm2, "HELLO");
                            fragmentTransaction.commit();
                            if (getFeedListener() != null){
                                setFeedListenerListener(new FeedListener() {
                                    @Override
                                    public void onFragmentExit() {
                                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.remove(fm2).commit();
                                        Log.i("OFE", "onFragmentExit triggered");
                                    }

                                    @Override
                                    public void onFragmentItemSelected(String itemName) {
                                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.remove(fm2).commit();

                                        getUserPet().feedPet();


                                    }
                                });
                            }
                    }

                    return true;
                }
            };

    public interface FeedListener{

        void onFragmentExit();

        void onFragmentItemSelected(String itemName);
    }
    public FeedListener getFeedListener() {
        return feedListener;
    }

    public void setFeedListenerListener(FeedListener feedListener) {
        this.feedListener = feedListener;
    }

    private String makeUsernameFromEmail(String Email){
        String username = Email;
        username = username.replace(".","");
        Log.i("Tag", username);
        return username;
    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}