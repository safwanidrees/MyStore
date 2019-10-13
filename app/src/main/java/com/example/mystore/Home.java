package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    ImageButton back;
    RecyclerView mRecyclerView;
    List<Products>mproduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        loadFragment(new HomeFragment());

//        ImageButton  back=(ImageButton) findViewById(R.id.back_Button);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               onBackPressed();
//            }
//        });








    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(this,MainActivity.class);
        startActivity(i);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected_Fragment;

            switch (item.getItemId()){
                case R.id.navigation_home:
                    selected_Fragment=new HomeFragment();
                    loadFragment(selected_Fragment);
                    return true;
                case R.id.navigation_categories:
                    selected_Fragment=new CategoriesFragment();
                    loadFragment(selected_Fragment);

                    return true;

                case R.id.navigation_cart:
                    selected_Fragment=new CartFragment();
                    loadFragment(selected_Fragment);

                    return true;

                case R.id.navigation_profile:
                    selected_Fragment=new ProfileFragment();
                    loadFragment(selected_Fragment);

                    return true;



            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.re, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
