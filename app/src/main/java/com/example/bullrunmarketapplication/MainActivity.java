package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializing Firebase app to this activity
        FirebaseApp.initializeApp(this);
        //linking to appropriate layout
        setContentView(R.layout.activity_selection_screen);

        button = findViewById(R.id.btnCustomer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
        button = findViewById(R.id.btnTrucks);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTruckLogin();
            }
        });
    }

    public void openSignUp() {
        Intent intent = new Intent(this, CustSignUp.class);
        startActivity(intent);
    }

    public void openTruckLogin() {
        Intent intent = new Intent(this, TruckLogin.class);
        startActivity(intent);
    }
}

