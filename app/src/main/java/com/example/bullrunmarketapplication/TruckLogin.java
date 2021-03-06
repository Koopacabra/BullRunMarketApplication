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

    EditText edtUsername, edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_cust_login);

        //Firebase initialization
        FirebaseApp.initializeApp(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsername.setError(null);
                edtPassword.setError(null);

                String username  =  edtUsername.getText().toString();
                String password  = edtPassword.getText().toString();

                //conditionals so the username or password cannot be empty
                if(TextUtils.isEmpty(username)){
                    edtUsername.setError("Cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Cannot be empty");
                    return;
                }
                signInReadUser(username,password);
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

                /*if statement leading to switches to determine which page the truck navigates to;
                 * also assigns the truckId per truck;*/
                if (user != null && user.getPassword().equals(password)) {
                    switch (username) {
                        case "Ricos Tacos": {
                            Intent intent = new Intent(getApplicationContext(), TruckOrders.class);
                            intent.putExtra("truckId",4);
                            startActivity(intent);
                            break;
                        }
                        case "Italian on Wheels": {
                            Intent intent = new Intent(getApplicationContext(), TruckOrders.class);
                            intent.putExtra("truckId",2);
                            startActivity(intent);
                            break;
                        }
                        case "Pizza Time": {
                            Intent intent = new Intent(getApplicationContext(), TruckOrders.class);
                            intent.putExtra("truckId",3);
                            startActivity(intent);
                            break;
                        }
                        case "Hyderabadi Delight": {
                            Intent intent = new Intent(getApplicationContext(), TruckOrders.class);
                            intent.putExtra("truckId",1);
                            startActivity(intent);
                            break;
                        }
                    }
                }
                //else statement to toast user that there was a mismatch
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
}