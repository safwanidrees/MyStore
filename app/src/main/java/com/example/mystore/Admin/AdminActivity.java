package com.example.mystore.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mystore.MainActivity;
import com.example.mystore.MobileProducts;
import com.example.mystore.Products;
import com.example.mystore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminActivity extends AppCompatActivity {
    Spinner product_spinner;
    TextView prod;
    ImageButton imageButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ImageView imageView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private EditText product_brand, prductdesc, product_price, product_rating;
    private Button uploadproduct;
    private ProgressBar mProgressBar;
    private String child;
    private int buttonState = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        product_brand = findViewById(R.id.product_brand);
        prductdesc = findViewById(R.id.product_Decription);
        product_price = findViewById(R.id.product_price);
        product_rating = findViewById(R.id.product_rating);
        uploadproduct = findViewById(R.id.product_upload);
mProgressBar=findViewById(R.id.progress_bar);
        // spinnner is used to select categories
        product_spinner = findViewById(R.id.spinner);

        prod = findViewById(R.id.spinner_values);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.products_Subcatigories, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_spinner.setAdapter(adapter);

        product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = parent.getItemAtPosition(position).toString();
                prod.setText(text);
                child = prod.getText().toString();
                // main operation to upload data in database
                upload(child);
                Log.w("da", "my" + child);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imageButton = findViewById(R.id.upload_image);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChoose();
            }
        });
    }

    private void upload(final String child) {

        mStorageRef = FirebaseStorage.getInstance().getReference("Products").child(child);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products").child(child);


        uploadproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()) {

//                    uploadproduct.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    Toast.makeText(AdminActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
//                    uploadproduct.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                    uploadFile(child);


                }

                mProgressBar.setVisibility(View.INVISIBLE);
//                buttonState++;

            }
        });

    }

    // it is use to open mobile folder hwere we upload image

    private void imageChoose() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //put selected image in image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            imageView = findViewById(R.id.show_image);

            mImageUri = data.getData();

//            Glide.with(this).load(mImageUri).into(imageView);
            Picasso.get().load(mImageUri).into(imageView);

            imageView.setImageURI(mImageUri);


        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(final String Pro) {
        if (mImageUri != null) {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));


            mUploadTask = fileReference.putFile(mImageUri)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mProgressBar.setVisibility(View.VISIBLE);
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();


                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(AdminActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();

                            String uploadId = mDatabaseRef.push().getKey();

                            Products upload = new Products(Pro,uploadId,product_brand.getText().toString().trim(), prductdesc.getText().toString().trim(), product_price.getText().toString().trim(), product_rating.getText().toString().trim(),
                                    downloadUri.toString());
                           Log.d("pro ","proo :"+Pro);

                            mDatabaseRef.child(uploadId).setValue(upload);

                            Intent i = new Intent(AdminActivity.this, AdminActivity.class);
                            startActivity(i);
                            finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
//uploadproduct.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//
//
//                        }


//                    });



        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
