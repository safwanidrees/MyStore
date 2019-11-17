package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductsAdapter.OnItemClickListener {
    ProgressBar mprogress;
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

         mprogress=view.findViewById(R.id.progresshome);


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

        AllData(child);

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

        mprogress.setVisibility(View.VISIBLE);

        mobile_products=view.findViewById(R.id.products);
//        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        mobile_products.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        reference= FirebaseDatabase.getInstance().getReference("Products");
        Query lastQuery = reference.child(child).orderByKey().limitToLast(3);


        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clone();

//                productsAdapter.notifyDataSetChanged();
//                productsAdapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

//
//Mydoes is use because of arraylist name

                    Products p = dataSnapshot1.getValue(Products.class);
                    list.add(p);

                }


                productsAdapter = new ProductsAdapter(getContext(), list);
                productsAdapter.notifyDataSetChanged();
                mobile_products.setAdapter(productsAdapter);
                productsAdapter.setOnItemClickListener(HomeFragment.this);
                mprogress.setVisibility(View.INVISIBLE);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        }

        );
    }


    @Override
    public void onItemClick(int positon) {



        Products order = list.get(positon);


        String a=  order.getDetailID();
        String b= order.getProduct();
        Toast.makeText(getContext(),"id :"+b,Toast.LENGTH_LONG).show();
showdata(order.getProduct(),order.getDetailID(),order.getBrand(),order.getDescription(),order.getPrice(),order.getRating(),order.getThumbnail());

    }

    public void showdata(String Product,String DetailId,String brand, String description, String price, String rating, String thumbnail) {

        Intent i= new Intent(getContext(), Product_Details.class);
        i.putExtra("Product",Product);
        i.putExtra("ID",DetailId);
        startActivity(i);




    }
}
