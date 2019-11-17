package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    Button create_Account, login_back;
    EditText signupEmail, signupPassword, confirmpassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        create_Account = findViewById(R.id.create_account);
        login_back = findViewById(R.id.loginback);
        signupEmail = findViewById(R.id.signupemail);
        signupPassword = findViewById(R.id.signuppassword);
        confirmpassword = findViewById(R.id.confirmpassword);
login_back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(SignupActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
});
        create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmpasswor = confirmpassword.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpasswor)) {
                    if (password.equals(confirmpasswor)) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    sendtoHome();
                                } else {
                                    String errormessage = task.getException().getMessage();
                                    Toast.makeText(SignupActivity.this, "Error:" + errormessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignupActivity.this, "Confirm Password First", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    private void sendtoHome() {
        Intent i = new Intent(SignupActivity.this, Home.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent i = new Intent(SignupActivity.this, Home.class);
            startActivity(i);
            finish();
        }
    }
}
