package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView mobile_products;
    ArrayList<Products> list = new ArrayList<Products>();
    ProductsAdapter productsAdapter;
    String child;
private View view;
    ImageButton back;
    RecyclerView mRecyclerView;
    List<Products> mproduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view= inflater.inflate(R.layout.fragment_home, container, false);

                ImageButton  back=(ImageButton) view.findViewById(R.id.back_Button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
            }
        });

//        mproduct=new ArrayList<>();
//
//        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.valueOf(R.drawable.shirt).toString()));
//        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));
//        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));
//        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));
//
//        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
//
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.products);
//        ProductsAdapter productsAdapter=new ProductsAdapter(getActivity(),mproduct);
////        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        mRecyclerView.setLayoutManager(layoutManager);
//
//        mRecyclerView.setAdapter(productsAdapter);
        RetrieveAllData();



return view;
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
        list=new ArrayList<>();


        mobile_products=view.findViewById(R.id.products);
//        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        mobile_products.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        reference= FirebaseDatabase.getInstance().getReference("Products").child(child);

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


                productsAdapter = new ProductsAdapter(getContext(), list);
                productsAdapter.notifyDataSetChanged();
                mobile_products.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(getContext(), MainActivity.class);

        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);

        return;
    }


}
