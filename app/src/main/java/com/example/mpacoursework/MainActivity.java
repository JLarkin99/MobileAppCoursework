package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageButton playButton;
    ImageButton cookButton;
    Button signOutButton;
    TextView idTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent playIntent = new Intent(MainActivity.this,PlayActivityMain.class);
        final Intent cookIntent = new Intent(MainActivity.this,cookActivityMain.class);

        mAuth = FirebaseAuth.getInstance();

        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(playIntent);
            }
        });

        cookButton = (ImageButton) findViewById(R.id.exitButtonSettings);
        cookButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(cookIntent);
            }
        });
        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mAuth.signOut();
                Intent signUpIntent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
        idTextView = (TextView) findViewById(R.id.idText);






    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent signUpIntent = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(signUpIntent);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user != null) {
            String userEmail = user.getEmail();
            idTextView.setText("Logged in as " + userEmail);
        }
        else{
            idTextView.setText("Not Logged in!");
        }
    }

    private String makeUsernameFromEmail(String Email){
        String username = Email;
        username.replaceAll("[^a-zA-Z0-9]","");
        return username;
    }
}