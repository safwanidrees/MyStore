package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

public class ProfileFragment  extends Fragment implements PopupMenu.OnMenuItemClickListener {
    FirebaseAuth mAuth;
    ImageButton menubtn;
    SparkButton sparkButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
mAuth=FirebaseAuth.getInstance();
 menubtn=view.findViewById(R.id.menubtn);

menubtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        PopupMenu popupMenu=new PopupMenu(getContext(),menubtn);

        popupMenu.getMenuInflater().inflate(R.menu.logout,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        logout();

                        return true;
                    default:
                        return false;
                }

            }
        });
        popupMenu.show();


    }
});


        return view;
    }

public  void Show (View v){
    PopupMenu popupMenu=new PopupMenu(getContext(),menubtn);

    popupMenu.getMenuInflater().inflate(R.menu.logout,popupMenu.getMenu());
    popupMenu.setOnMenuItemClickListener(this);
    popupMenu.show();


}


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
logout();

return true;
default:
    return false;
        }


    }
public void logout(){

//    FirebaseAuth.AuthStateListener fireauth=new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser currentUser=mAuth.getCurrentUser();
//            if(currentUser==null){
//
//                Intent i = new Intent(getContext(), MainActivity.class);
//                startActivity(i);
//            }
//            else {
//
//            }
            mAuth.signOut();
    Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
//        }
//    };


}

}