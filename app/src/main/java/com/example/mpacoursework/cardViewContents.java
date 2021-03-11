package com.example.mpacoursework;

public class cardViewContents {
    private int imageResource;
    private String textLine;

    public cardViewContents(int imRes,String text ){
        imageResource = imRes;
        textLine = text;
    }

    public int getImageResource(){
        return imageResource;
    }

    public String getText(){
        return textLine;
    }

}
