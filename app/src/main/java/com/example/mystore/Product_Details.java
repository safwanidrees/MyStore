package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Product_Details extends AppCompatActivity {
    Context mContext;
    Typeface Roboticfont;
    TextView Brandname, Branddescription, BrandPrice, BrandRating;
    ImageView Brandimage;
    Activity Product_details;
    RatingBar ratingBar;
    List<Products> products;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String myemail;
    public int extentprices;

    public String productname;
    public String productdescripition;
    public String productprice;
    public String productquantity;
    public String imageurl;

    int positiion = 1;
    Button addtocart;
    ArrayList<Products> list = new ArrayList<Products>();
    int newprice = 1;
    ImageButton quantityadd, quantityminus;
    ImageButton backtohome;
    TextView quantitytext;
    DatabaseReference brand, desc, price, image, rating;
    String cartbrand, cartdesc, cartprice, cartcatigorie;

    private BottomNavigationView bottomNavigationView;

    private View notificationBadge;
    private String productID = "";
    int quantity = 1;
    private AlphaAnimation buttonClick;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product__details);
        productID = getIntent().getStringExtra("pid");
//        myemail=findViewById(R.id.myemail);
        backtohome = findViewById(R.id.backtohomes);

        BrandRating = findViewById(R.id.brandratingnumber);
        Brandname = findViewById(R.id.brand_name);
        Branddescription = findViewById(R.id.brand_descripition);
        BrandPrice = findViewById(R.id.brand_price);
        ratingBar = findViewById(R.id.rating_bar);
        addtocart = findViewById(R.id.addtocart);
        Brandimage = findViewById(R.id.brandimage);

        quantityadd = findViewById(R.id.quantiy_add);
        quantityminus = findViewById(R.id.quantity_minus);

        quantitytext = findViewById(R.id.quantity_text);

        bottomNavigationView = findViewById(R.id.bottom);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myemail = firebaseUser.getEmail();
//        myemail.setText(b);


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CART();

            }
        });

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Product_Details.this, Home.class);
//                startActivity(i);
                finish();
            }
        });
        quantityadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityadd();
            }
        });
        quantityminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityminus();
            }
        });

        RETERIVEDATA();


    }

    private void RETERIVEDATA() {

        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
        final String Product = intent.getStringExtra("Product");
        Log.d("details", "id :" + ID);
        Log.d("details", "id :" + Product);


        brand = FirebaseDatabase.getInstance().getReference().child("Products").child(Product).child(ID).child("brand");

        brand.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Brandname.setText(dataSnapshot.getValue(String.class));
//cart(dataSnapshot.getValue(String.class));
                productname = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        desc = FirebaseDatabase.getInstance().getReference().child("Products").child(Product).child(ID).child("description");
        desc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Branddescription.setText(dataSnapshot.getValue(String.class));
                productdescripition = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        price = FirebaseDatabase.getInstance().getReference().child("Products").child(Product).child(ID).child("price");
        price.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BrandPrice.setText(dataSnapshot.getValue(String.class));
                newprice = Integer.parseInt(dataSnapshot.getValue(String.class));
                Log.d("valueee", "my " + newprice);

                extentPrice(newprice);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rating = FirebaseDatabase.getInstance().getReference().child("Products").child(Product).child(ID).child("rating");
        rating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BrandRating.setText(dataSnapshot.getValue(String.class));

                Float ratings = Float.valueOf(dataSnapshot.getValue(String.class));
                ratingBar.setRating(ratings);
//                ratingBar.setStepSize(ratings);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        image = FirebaseDatabase.getInstance().getReference().child("Products").child(Product).child(ID).child("thumbnail");
        image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Picasso.get().load(dataSnapshot.getValue(String.class)).into(Brandimage);
                imageurl = dataSnapshot.getValue(String.class);

                imageUrl(imageurl);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CART() {

//             buttonClick = new AlphaAnimation(1F, 0.8F);
//
//                v.startAnimation(buttonClick);


        Calendar calendar = Calendar.getInstance();
        String datee = DateFormat.getDateInstance().format(calendar.getTime());


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart of peoples").child("View");
//                startButtonAnimation(addtocart);

        String a = " " + Brandname.getText().toString() + " " + Branddescription.getText().toString() + " " + BrandPrice.getText().toString() + "  " + quantity;

        Log.i("my", "as" + a);


        String email = firebaseUser.getEmail();
        String brandname = Brandname.getText().toString();
        String branddesc = Branddescription.getText().toString();
        String brandprice = BrandPrice.getText().toString();
        int brandquantity = quantity;
        String brandimageURL = imageurl;
        Log.i("image", "ss" + imageurl);

        String id = databaseReference.push().getKey();

        Cart cart = new Cart(email, brandname, branddesc, brandprice, brandquantity, brandimageURL);
//                Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss a");
        Date date = new Date(System.currentTimeMillis());
        String currentdate = format.format(date);

//                String saveCD=currentdate.format(calendar.getTime());
//
//                SimpleDateFormat currentTIME=new SimpleDateFormat("HH:mm:ss a");
//                String saveCT=currentdate.format(calendar.getTime());
        int number = 1;
        String no = String.valueOf((number));

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("order").child(currentdate).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Product_Details.this, "Order added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Product_Details.this, "Order not added", Toast.LENGTH_LONG).show();
                }

            }

        });

        finish();


    }


    private void addBadgeView() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(0);

        notificationBadge = LayoutInflater.from(getBaseContext()).inflate(R.layout.view_notification_badge, menuView, true);

        itemView.addView(notificationBadge);
    }

    private void startButtonAnimation(Button btn) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.name_shake);
        btn.setAnimation(shake);
        btn.startAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//
//        Fragment selected_Fragment =new CartFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(selected_Fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();

                finish();
//        overridePendingTransition(R.anim.fui_slide_out_left, R.anim.fui_slide_in_right);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


//    private void setNewprice(int qun,int pri){
//
//    }
//    private se

    private int extentPrice(int price) {
        return price;
    }

    private void extentPrice(int quantity, int price) {
        BrandPrice.setText(" " + quantity * price);

        extentprices = quantity * price;


        Log.d("valuee", "quantityssss" + extentprices);

//     BrandPrice.setText(""+extentprices);
        Log.d("valuee", "my" + price);
//        cart();


    }

    private int quantityadd() {
        if (quantity >= 5) {
            Toast.makeText(Product_Details.this, "Please Select Less than 5", Toast.LENGTH_LONG).show();
        } else {
            quantity = quantity + 1;


            quantitytext.setText("" + quantity);

            extentPrice(quantity, newprice);

            ;
//                    newprice(quantity);
//                    newprice(quantity,newprice);


        }
        return quantity;
    }

    private int quantityminus() {
        if (quantity <= 1) {
            Toast.makeText(Product_Details.this, "Please Select Atleast 1", Toast.LENGTH_LONG).show();
        } else {
            quantity = quantity - 1;

            quantitytext.setText("" + quantity);
            extentPrice(quantity, newprice);


        }
        return quantity;
    }

    public String imageUrl(String imageurl) {

        return imageurl;
    }

}
