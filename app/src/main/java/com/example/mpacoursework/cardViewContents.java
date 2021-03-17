package com.example.mpacoursework;

import android.util.Log;

public class cardViewContents {
    private int imageResource;
    private String textLine;

    public cardViewContents(int imRes,String text ){
        imageResource = imRes;
        textLine = text;
        Log.i("CVC", "Card created with text: " + textLine);
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getText(){
        return textLine;
    }

}
