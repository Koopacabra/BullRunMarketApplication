package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        button = findViewById(R.id.btnWelcome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpizzatime();
            }
        });
    }

    public void openpizzatime() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}
