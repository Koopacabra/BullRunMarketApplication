package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //THIS 'private Button button;' IS FOR TESTING ACTIVITY NAVIGATION; SHOULD BE REMOVED/ALTERED WHEN HEATHER
    private Button button;
    //REMOVE/ALTER ABOVE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THIS IS FOR TESTING ACTIVITY NAVIGATION; SHOULD BE REMOVED/ALTERED WHEN HEATHER BEGINS DEVELOPMENT
        button = findViewById(R.id.button_to_truck_selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTruckSelection();
            }
        });
        //REMOVE/ALTER ABOVE
    }

    //THIS IS FOR TESTING ACTIVITY NAVIGATION; SHOULD BE REMOVED/ALTERED WHEN HEATHER BEGINS DEVELOPMENT
    public void openTruckSelection() {
        Intent intent = new Intent(this, TruckSelection.class);
        startActivity(intent);
    }
    //REMOVE/ALTER ABOVE
}
