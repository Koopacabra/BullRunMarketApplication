package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference user;

    EditText edtUsername, edtPassword;
    Button btnSignIn, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_sign_in);

        //Firebase
        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(), SignIn.class);
                startActivity(s);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(edtUsername.getText().toString(),
                        edtPassword.getText().toString());

                user.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(SignIn.this, "This Username already exists!", Toast.LENGTH_SHORT).show();
                        else {
                            user.child(user.getUsername()).setValue(user);
                            Toast.makeText(SignIn.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
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
