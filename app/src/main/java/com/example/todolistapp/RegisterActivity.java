package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    Button btnTologin;
    ImageButton btnNextActivity;
    EditText username, password, email,phoneNumber;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todolist-e5a6e-default-rtdb.firebaseio.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();


        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from edit text
                final String userNameText, emailText,phoneNumberText,passwordText;
                userNameText = username.getText().toString();
                emailText  = email.getText().toString();
                passwordText = password.getText().toString();
                phoneNumberText = phoneNumber.getText().toString();
                //check is data not empty
                if(userNameText.isEmpty()||emailText.isEmpty()||phoneNumberText.isEmpty()||passwordText.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }

                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if phone is not registered before
                            if(snapshot.hasChild(phoneNumberText)){
                                Toast.makeText(RegisterActivity.this,"Phone is already exsist",Toast.LENGTH_SHORT).show();
                            }

                            else{
                                //sending data to firebase
                                databaseReference.child("users").child(phoneNumberText).child("fullname").setValue(userNameText);
                                databaseReference.child("users").child(phoneNumberText).child("email").setValue(emailText);
                                databaseReference.child("users").child(phoneNumberText).child("password").setValue(passwordText);

                                Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("id",phoneNumberText);
                                intent.putExtras(bundle);
                                startActivity(intent);

                                startActivity(intent);

                                finish();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });













                }

            }
        });




        btnTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,loginActivity.class));
                finish();
            }
        });
    }

    private void initViews() {
        btnTologin = findViewById(R.id.btn_go_to_login_from_reg);
        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_password);
        email = findViewById(R.id.et_register_email);
        phoneNumber = findViewById(R.id.et_register_phone);
        btnNextActivity = findViewById(R.id.btn_reg_ok);


    }
}