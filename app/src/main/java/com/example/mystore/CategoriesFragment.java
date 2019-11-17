package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoriesFragment extends Fragment implements View.OnClickListener {
    ImageButton Mobilebtn,Chargerbtn,Coverbtn,Headphonebtn,Protectorsbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_categories, container, false);
Mobilebtn=view.findViewById(R.id.mob_btn);
Headphonebtn=view.findViewById(R.id.headpho_btn);
Chargerbtn=view.findViewById(R.id.char_btn);
Coverbtn=view.findViewById(R.id.cov_btn);
Protectorsbtn=view.findViewById(R.id.prot_btn);

Mobilebtn.setOnClickListener(this);
Headphonebtn.setOnClickListener(this);
Chargerbtn.setOnClickListener(this);
Coverbtn.setOnClickListener(this);
Protectorsbtn.setOnClickListener(this);


        return view;
    }

    public void Intents(String title){




        Intent i= new Intent(getContext(), Show.class);
        i.putExtra("Title",title);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mob_btn:
                Intents("Mobiles");

                break;
            case  R.id.headpho_btn:
                Intents("Headphones");
                break;
            case R.id.char_btn:
                Intents("Chargers");
                break;
            case  R.id.cov_btn:
                Intents("Covers");
                break;
            case  R.id.prot_btn:
                Intents("Protectors");
                break;
        }
    }
}