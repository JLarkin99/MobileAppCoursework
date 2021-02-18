package com.example.mpacoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class RecipeViewActivity extends AppCompatActivity {
    FileReader fileReader;
    TextView textView;
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






    }


}