package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Home extends AppCompatActivity {
    ImageButton back;
    RecyclerView mRecyclerView;
    List<Products>mproduct;
    private BottomNavigationView bottomNavigationView;

    private View notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        bottomNavigationView=findViewById(R.id.bottom);
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
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
//        TextView emailuser=findViewById(R.id.useremaill);
//
//        emailuser.setText(currentUser.getEmail());

        if(currentUser == null){
            Intent i= new Intent(Home.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {

        }
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
//                    addBadgeView();
                    selected_Fragment=new CategoriesFragment();
                    loadFragment(selected_Fragment);

                    return true;

                case R.id.navigation_cart:
//                    refreshBadgeView();

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
    private void addBadgeView() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationBadge = LayoutInflater.from(this).inflate(R.layout.view_notification_badge, menuView, false);

        itemView.addView(notificationBadge);
    }
    private void refreshBadgeView() {
        boolean badgeIsVisible = notificationBadge.getVisibility() != VISIBLE;
        notificationBadge.setVisibility(badgeIsVisible ? VISIBLE : GONE);
//        button.setText(badgeIsVisible ? "Hide badge" : " Show badge");
    }
}
