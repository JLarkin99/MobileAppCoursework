package com.example.mpacoursework;

public class Pet {

    public static final int MAX_HUNGER = 100;
    private final int FEED_INCREMENT = 20;
    private final int HUNGER_STEP = 20;
    private int hunger = MAX_HUNGER;
    private String name;

    public Pet(){
        //setHunger(MAX_HUNGER);

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

    public void feedPet(){
        hunger += FEED_INCREMENT;
        if(hunger > MAX_HUNGER){
            hunger = MAX_HUNGER;
        }
    }

    public void depleteHunger(){
        hunger -= HUNGER_STEP;
        if (hunger < 0){
            hunger = 0;
        }
    }






}
