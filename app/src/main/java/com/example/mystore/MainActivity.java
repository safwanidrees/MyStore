package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystore.Admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button signup;
    Button login;
    EditText loginEmailText;
    EditText loginPasswordText;
    FirebaseAuth mAuth;
    ProgressBar loginprogress;
    TextView forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        loginEmailText = findViewById(R.id.loginusername);
        loginPasswordText = findViewById(R.id.loginpassword);
        loginprogress = findViewById(R.id.loginProgress);
        forgetpass = findViewById(R.id.forgetpassword);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emaill = loginEmailText.getText().toString().trim();
                if (!TextUtils.isEmpty(emaill)) {


                    mAuth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Email Sent to :" + emaill, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i= new Intent(MainActivity.this, Home.class);
//                startActivity(i);

                String loginEmail = loginEmailText.getText().toString().trim();
                String loginPassword = loginPasswordText.getText().toString().trim();

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)) {

                    loginprogress.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                sendtoHome();

                            } else {
                                String errormessage = task.getException().getMessage();
                                Toast.makeText(MainActivity.this, "Error:" + errormessage, Toast.LENGTH_SHORT).show();
                            }
                            loginprogress.setVisibility(View.INVISIBLE);
                        }
                    });

                }

            }
        });
        ImageButton imageButton = findViewById(R.id.ima);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendtoHome();
        }
    }

    @Override
    public void onBackPressed() {

        finishAffinity();

    }

    private void sendtoHome() {
        Intent i = new Intent(MainActivity.this, Home.class);
        startActivity(i);
        finish();
    }
}
