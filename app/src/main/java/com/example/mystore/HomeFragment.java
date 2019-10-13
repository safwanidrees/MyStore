package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageButton back;
    RecyclerView mRecyclerView;
    List<Products> mproduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

                ImageButton  back=(ImageButton) view.findViewById(R.id.back_Button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               onBackPressed();
            }
        });
        mproduct=new ArrayList<>();

        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.valueOf(R.drawable.shirt).toString()));
        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));
        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));
        mproduct.add(new Products("Levis","New Stylish Casual Shirt For Mens","Rs.900","5",Integer.toString(R.drawable.shirt)));

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.products);
        ProductsAdapter productsAdapter=new ProductsAdapter(getActivity(),mproduct);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(productsAdapter);
return view;
    }



    public void onBackPressed() {
        Intent myIntent = new Intent(getContext(), MainActivity.class);

        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);

        return;
    }
}