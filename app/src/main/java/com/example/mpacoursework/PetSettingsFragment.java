package com.example.mpacoursework;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class PetSettingsFragment extends Fragment {
    ImageButton exitButton;
    Button confirmButton;
    EditText nameEdit;

    public PetSettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_settings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        exitButton = getActivity().findViewById(R.id.exitButtonSettings);
        exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i("OCL","click on exitbutton detected");
                closeFragment();
            }
        });

        nameEdit = getActivity().findViewById(R.id.editNameBox);
        confirmButton = getActivity().findViewById((R.id.confirmButton));
        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Log.v("EditText", nameEdit.getText().toString());
                //pass name to activity
                String name = nameEdit.getText().toString();
                if( name.length() <= 8) {
                    ((PlayActivityMain) getActivity()).getFeedListener().onFragmentNameSelected(nameEdit.getText().toString());
                }

            }
        });



    }

    public void closeFragment(){
        if(((PlayActivityMain) getActivity()) != null) {
            ((PlayActivityMain) getActivity()).getFeedListener().onFragmentExit();
        }
//        ((PlayActivityMain) getActivity()).getFeedListener().onFragmentExit();
    }
}