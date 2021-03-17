package com.example.mpacoursework;

public class Pet {

    private static final int MAX_HUNGER = 100;
    private int hunger;
    private String name;

    public Pet(){
        setHunger(MAX_HUNGER);

    }

    public Pet(int hunger, String name){
        setHunger(hunger);
        setName(name);
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public float getHungerRatio(){
        return ((float) hunger / (float) MAX_HUNGER);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






}
