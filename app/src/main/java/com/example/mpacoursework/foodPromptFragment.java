package com.example.mpacoursework;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class foodPromptFragment extends Fragment {
    TextView promptText;
    ImageButton exitButton;

    public foodPromptFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        exitButton = getActivity().findViewById(R.id.exitButtonPrompt);
        promptText = getActivity().findViewById((R.id.promptText));

        exitButton = (ImageButton) getActivity().findViewById(R.id.exitButtonPrompt);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("OCL", "click on exitbutton detected");
                closeFragment();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_prompt, container, false);
    }

    public void closeFragment(){
        Log.i("FPF","Frag close called");
        ((PlayActivityMain)getActivity()).getFeedListener().onFragmentExit();

    }
}