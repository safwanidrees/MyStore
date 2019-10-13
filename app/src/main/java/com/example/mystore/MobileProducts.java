package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobileProducts extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView mobile_products;
    ArrayList<Products> list=new ArrayList<Products>();
    ProductsAdapter productsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mobile_products);
        list=new ArrayList<>();


        mobile_products=findViewById(R.id.mobile_products);

        mobile_products.setLayoutManager(new LinearLayoutManager(this));




// get data from firebase






        reference= FirebaseDatabase.getInstance().getReference("Products").child("Mobiles");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
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
}