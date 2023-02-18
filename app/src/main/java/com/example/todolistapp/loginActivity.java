package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;

public class loginActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todolist-e5a6e-default-rtdb.firebaseio.com");
    EditText phoneNumber, password;
    Button btnToRegister;
    ImageButton btnOK;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        initViews();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user!= null){
                    Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,RegisterActivity.class));
                finish();
            }
        });


btnOK.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        final String   phoneText = phoneNumber.getText().toString();
        final  String passwordText = password.getText().toString();
        if(phoneText.isEmpty()||passwordText.isEmpty())
        {
            Toast.makeText(loginActivity.this,"Please enter your mobile or password",Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //check if phone exist
                    if(snapshot.hasChild(phoneText)) {
                        //get password from user to mach
                        final String getPassword = snapshot.child(phoneText).child("password").getValue(String.class);
                        if(getPassword.equals(passwordText)){
                            Toast.makeText(loginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();

                            Intent intent = (new Intent(loginActivity.this,HomeActivity.class));
                            Bundle bundle = new Bundle();
                            bundle.putString("id",phoneText);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(loginActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(loginActivity.this,"Wrong Mobile Number",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
});

    }
    private void initViews() {
        phoneNumber = findViewById(R.id.et_PhoneNumber);
        password = findViewById(R.id.et_Code);
        btnToRegister = findViewById(R.id.btn_go_to_register);
        btnOK = findViewById(R.id.btn_next);
    }

}