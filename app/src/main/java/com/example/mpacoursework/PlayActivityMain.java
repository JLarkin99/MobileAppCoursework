package com.example.mpacoursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class PlayActivityMain extends AppCompatActivity {
    Intent intent;
    Pet userPet;
    TextView nameText;
    TextView hungerText;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userEmail = user.getEmail();
    String email = makeUsernameFromEmail(userEmail);
    final DatabaseReference ref = database.getReference("users");
    final int TIMER_LENGTH = 20000;

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
        public void onFragmentNameSelected(String itemName) {

        }

        @Override
        public void onFragmentFoodSelected(String name) {

        }


    };
    private FragmentManager fm = getFragmentManager();
    final feedFragment fm2 = new feedFragment();
    final PetSettingsFragment settingsFragment = new PetSettingsFragment();
    final foodPromptFragment promptFragment = new foodPromptFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_main);

        intent = new Intent(PlayActivityMain.this, MainActivity.class);


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
                        updateUI();
                    }

                }
                else{
                    userPet = new Pet(100,"Steve");
                    ref.child("users").child(email).child("userpet").setValue(userPet);
                    launchSettingsFragment();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("DSS cancel", "DSS cancel called");

            }
        });


        //userPet = new Pet();

        BottomNavigationView bottomNav = findViewById(R.id.playNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navSelectListener);



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
                            startActivity(intent);
                            return true;
                        case R.id.feedButton:
                            if(R.id.feedButton == R.id.Settings){
                                Log.i("AAA","UUUUUH");
                            }
                            item.setCheckable(true);
                            Log.i("NSI", "feed button selected");
                            launchFeedFragment();
                            return true;

                        case R.id.Settings:
                            item.setCheckable(true);
                            Log.i("NSI", "settings button selected");
                            launchSettingsFragment();
                            return true;


                    }

                    return false;
                }
            };

    public interface FeedListener{

        void onFragmentExit();

        void onFragmentNameSelected(String itemName);

        void onFragmentFoodSelected(String name);
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
        //startHunger
        recursiveCountdown();


    }


    void launchFeedFragment(){
        //create feed fragment
        //final FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragFrame, fm2, "oh wow");
        fragmentTransaction.commit();
        if (getFeedListener() != null){
            setFeedListenerListener(new FeedListener() {
                @Override
                public void onFragmentExit() {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.remove(fm2).commit();
                    //fm.popBackStack();
                    Log.i("OFE", "onFragmentExit triggered");
                }

                @Override
                public void onFragmentNameSelected(String itemName) {



                }
                @Override
                public void onFragmentFoodSelected(String name) {
                                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.remove(fm2).commit();
                    getUserPet().feedPet();
                    updateUI();
                    fm.popBackStack();
                    launchPromptFragment(name);

                }
            });
        }
    }

    void launchPromptFragment(String foodName){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragFrame, promptFragment, "oh wow");
        fragmentTransaction.commit();
        if (getFeedListener() != null){
            setFeedListenerListener(new FeedListener() {
                @Override
                public void onFragmentExit() {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.remove(promptFragment).commit();
                    //fm.popBackStack();
                    Log.i("OFE", "onFragmentExit triggered");
                }

                @Override
                public void onFragmentNameSelected(String itemName) {



                }
                @Override
                public void onFragmentFoodSelected(String name) {


                }
            });
        }
    }

    void launchSettingsFragment(){
        //final FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragFrame, settingsFragment, "HELLO");
        fragmentTransaction.commit();
        if (getFeedListener() != null){
            setFeedListenerListener(new FeedListener() {
                @Override
                public void onFragmentExit() {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.remove(settingsFragment).commit();
                    fm.popBackStack();
                    Log.i("OFE", "onFragmentExit triggered");
                }

                @Override
                public void onFragmentNameSelected(String itemName) {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.remove(settingsFragment).commit();
                    //fm.popBackStack();
                    getUserPet().setName(itemName);
                    ref.child("users").child(email).child("userpet").setValue(userPet);
                    updateUI();

                }

                @Override
                public void onFragmentFoodSelected(String name) {

                }
            });
        }
    }

    void updateUI(){
        if(userPet != null && hungerText != null && nameText != null){
            nameText.setText(userPet.getName());
            String hungerString = "Hunger: " + userPet.getHunger() + "/" + Pet.MAX_HUNGER;
            hungerText.setText(hungerString);
        }
    }


    void recursiveCountdown(){
        new CountDownTimer(TIMER_LENGTH, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                userPet.depleteHunger();
                updateUI();
                recursiveCountdown();
            }
        }.start();
    }
}