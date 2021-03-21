package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecipeViewActivity extends AppCompatActivity {
    FileReader fileReader;
    TextView textView;
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Intent intent = getIntent();
        String extra = intent.getStringExtra(cookActivityMain.extraName);
        String filePath = extra + ".txt";
        FileReader fileReader = new FileReader(this);

        TextView textView = findViewById(R.id.recipeTextView);
        textView.setText(fileReader.readFile(filePath));
        textView.setMovementMethod(new ScrollingMovementMethod());

        backButton = findViewById(R.id.recipeBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeViewActivity.this, cookActivityMain.class);
                startActivity(intent);
            }
        });






    }


}