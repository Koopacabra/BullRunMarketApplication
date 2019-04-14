package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TruckLogin extends AppCompatActivity {

    private static final String TAG = TruckLogin.class.getSimpleName();
/*
    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;*/

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_cust_login);

        //Firebase

        FirebaseApp.initializeApp(this);

       /* database = FirebaseDatabase.getInstance();
        users = database.getReference("User");*/

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*signIn(edtUsername.getText().toString(),
                        edtPassword.getText().toString());*/

                signInReadUser(edtUsername.getText().toString(),
                        edtPassword.getText().toString());
            }
        });
    }

    private void signInReadUser(final String username, final String password) {

        if (TextUtils.isEmpty(username)) {
            Log.e(TAG, "Username is empty");
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("User").child(username);

        // single value event comparing only specific users vs. the entire table (as the app does with customers)
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                //if statement leading to switches to determine which page the truck navigates to
                if (user != null && user.getPassword().equals(password)) {
                    switch (username) {
                        case "Ricos Tacos": {
                            Intent intent = new Intent(getApplicationContext(), TacosTruck_OrdersOpen.class);
                            startActivity(intent);
                            break;
                        }
                        case "Italian on Wheels": {
                            Intent intent = new Intent(getApplicationContext(), ItalianTruck_OrdersOpen.class);
                            startActivity(intent);
                            break;
                        }
                        case "Pizza Time": {
                            Intent intent = new Intent(getApplicationContext(), PizzaTruck_OrdersOpen.class);
                            startActivity(intent);
                            break;
                        }
                        case "Hyderabadi Delight": {
                            Intent intent = new Intent(getApplicationContext(), DelightTruck_OrdersOpen.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }
                //else statement to toasty user that there was a mismatch
                else {
                    Toast.makeText(TruckLogin.this, "Username or password did not match", Toast.LENGTH_SHORT).show();
                }

            }

            //should throw error if username is not found
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Toast.makeText(TruckLogin.this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void signIn(final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(username).exists()) { //user
                    if (!username.isEmpty()) {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword() != null && login.getPassword().equals(password) ) {

                            Toast.makeText(TruckLogin.this, "Login success!", Toast.LENGTH_SHORT).show();

                            if (username.equals("Ricos Tacos")) {
                                Intent intent = new Intent(getApplicationContext(), TacosTruck_OrdersOpen.class);
                                startActivity(intent);
                            }

                            if (username.equals("Italian on Wheels")) {
                                Intent intent = new Intent(getApplicationContext(), ItalianTruck_OrdersOpen.class);
                                startActivity(intent);
                            }

                            if (username.equals("Pizza Time")) {
                                Intent intent = new Intent(getApplicationContext(), PizzaTruck_OrdersOpen.class);
                                startActivity(intent);
                            }

                            if (username.equals("Hyderabadi Delight")) {
                                Intent intent = new Intent(getApplicationContext(), DelightTruck_OrdersOpen.class);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(TruckLogin.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(TruckLogin.this, "Username not registered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //custom code
                Log.e("Signin", "ERROR!:" + databaseError.getMessage());
            }
        });
    }*/
}
