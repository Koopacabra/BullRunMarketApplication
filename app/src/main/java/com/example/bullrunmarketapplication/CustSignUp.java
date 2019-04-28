package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class CustSignUp extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    EditText edtUsername, edtPassword, edtEmail;
    Button btnToSignIn, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_cust_signup);

        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);

        btnRegister = findViewById(R.id.btnRegister);
        btnToSignIn = findViewById(R.id.btnToSignIn);

        btnToSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustLogin.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(edtUsername.getText().toString(),
                        edtPassword.getText().toString(), edtEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(CustSignUp.this, "This Username already exists!", Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(CustSignUp.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //custom code
                    }
                });
            }
        });
    }
}

