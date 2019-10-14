package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class MobileProducts extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference reference;
    RecyclerView mobile_products;
    ArrayList<Products> list = new ArrayList<Products>();
    ProductsAdapter productsAdapter;
    String child;
    Button allbtn, mobilebtn, headphonesbtn, chargersbtn, coversbtn, protectorsbtn;
    private int buttonState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_products);
        allbtn = findViewById(R.id.all_btn);
        mobilebtn = findViewById(R.id.mobiles_btn);
        headphonesbtn = findViewById(R.id.headphones_btn);
        chargersbtn = findViewById(R.id.chargers_btn);
        coversbtn = findViewById(R.id.cover_btn);
        protectorsbtn = findViewById(R.id.protectors_btn);
        allbtn.setOnClickListener(this);

        mobilebtn.setOnClickListener(this);
        headphonesbtn.setOnClickListener(this);
        chargersbtn.setOnClickListener(this);
        coversbtn.setOnClickListener(this);
        protectorsbtn.setOnClickListener(this);


// get data from firebase

        RetrieveAllData();


    }

    private void RetrieveAllData() {
        child = "Mobiles";

        AllData("Mobiles");
//list.clear();
        child = "Headphones";
        AllData(child);

        child = "Chargers";
        AllData(child);

        child = "Covers";
        AllData(child);

        child = "Protectors";
        AllData(child);

    }

    private void AllData(String child) {
        list = new ArrayList<>();


        mobile_products = findViewById(R.id.mobile_products);

        mobile_products.setLayoutManager(new GridLayoutManager(this, 2));
        reference = FirebaseDatabase.getInstance().getReference("Products").child(child);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clone();
//
//                productsAdapter.notifyDataSetChanged();
//                productsAdapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


//Mydoes is use because of arraylist name

                    Products p = dataSnapshot1.getValue(Products.class);
                    list.add(p);

                }


                productsAdapter = new ProductsAdapter(MobileProducts.this, list);
                productsAdapter.notifyDataSetChanged();
                mobile_products.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void ViewDataFromFirebase(String child) {

        list = new ArrayList<>();


        mobile_products = findViewById(R.id.mobile_products);

        mobile_products.setLayoutManager(new GridLayoutManager(this, 2));
        reference = FirebaseDatabase.getInstance().getReference("Products").child(child);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
//
//                productsAdapter.notifyDataSetChanged();
//                productsAdapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


//Mydoes is use because of arraylist name

                    Products p = dataSnapshot1.getValue(Products.class);
                    list.add(p);

                }


                productsAdapter = new ProductsAdapter(MobileProducts.this, list);
                productsAdapter.notifyDataSetChanged();
                mobile_products.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.all_btn:
                RetrieveAllData();
                Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
//        allbtn.setBackgroundColor(R.drawable.pro);

                break;
            case R.id.mobiles_btn:
                child = "Mobiles";
                Toast.makeText(this, child, Toast.LENGTH_SHORT).show();

                ViewDataFromFirebase(child);
                break;
            case R.id.headphones_btn:
                child = "Headphones";
                Toast.makeText(this, child, Toast.LENGTH_SHORT).show();
                ViewDataFromFirebase(child);
                break;
            case R.id.chargers_btn:
                child = "Chargers";
                Toast.makeText(this, child, Toast.LENGTH_SHORT).show();
                ViewDataFromFirebase(child);
                break;
            case R.id.cover_btn:
                child = "Covers";
                Toast.makeText(this, child, Toast.LENGTH_SHORT).show();
                ViewDataFromFirebase(child);
                break;
            case R.id.protectors_btn:
                child = "Protectors";
                Toast.makeText(this, child, Toast.LENGTH_SHORT).show();
                ViewDataFromFirebase(child);
                break;

        }
    }
}