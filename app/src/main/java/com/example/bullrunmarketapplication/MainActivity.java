package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setContentView(R.layout.activity_main_screen);

        button = findViewById(R.id.btnWelcome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });
    }

    public void openSignIn() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
