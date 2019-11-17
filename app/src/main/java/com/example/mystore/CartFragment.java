package com.example.mystore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView cartitems;
    ArrayList<Cart> list = new ArrayList<>();

    DataSnapshot data;
    List<OrderPlace>datas=new ArrayList<>();
    CartAdapter cartAdapter;
    Button Confirm_Order;
    LinearLayout total_price;

List da;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
Confirm_Order=view.findViewById(R.id.confirm_order);
total_price=view.findViewById(R.id.texttt);

Confirm_Order.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        databaseReference.updateChildren(new )
//        databaseReference = FirebaseDatabase.getInstance().getReference("Cart of peoples").child("View").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("order").removeValue();
        orderconfirm();

    }
});

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Confirm_Order.setVisibility(View.INVISIBLE);
        total_price.setVisibility(View.INVISIBLE);

        cartitems = view.findViewById(R.id.cartlist);
        cartitems.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(getContext(), list);
        cartitems.setAdapter(cartAdapter);



        databaseReference = FirebaseDatabase.getInstance().getReference("Cart of peoples").child("View").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("order");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
list.clear();

                cartAdapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    Cart p = dataSnapshot1.getValue(Cart.class);
                    String a=dataSnapshot.toString();


                    if (list.add(p)){
                     Confirm_Order.setVisibility(View.VISIBLE);
                        total_price.setVisibility(View.VISIBLE);

                    }

                    order(list);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
private List order(List saf){
    Log.d("safwa","xxd "+saf);
    da=saf;
    return da;
}

    private void  orderconfirm() {
//        databaseReference=FirebaseDatabase.getInstance().getReference().child("Order");
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date(System.currentTimeMillis());
        String currentdate = format.format(date);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child(currentdate).child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        AlertDialog.Builder alertDialg=new AlertDialog.Builder(getContext());
        alertDialg.setTitle("One More Step") ;
    alertDialg.setMessage("Enter Your Address");
    final EditText address=new EditText(getContext());
    LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    address.setLayoutParams(lp);
    alertDialg.setView(address);
    alertDialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
           Toast.makeText(getContext(),"sas",Toast.LENGTH_SHORT).show();
       OrderPlace orderPlace=new OrderPlace(
               address.getText().toString(),
               da
       );
       databaseReference.child("Client Product").setValue(orderPlace);

        }
    });
    alertDialg.show();


    }

    private void deletefromcart() {

        databaseReference=FirebaseDatabase.getInstance().getReference("Cart of peoples").child("View").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("order");
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Confirm_Order.setVisibility(View.INVISIBLE);
                total_price.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Order Success", Toast.LENGTH_LONG).show();}
            }
        });
    }

}
