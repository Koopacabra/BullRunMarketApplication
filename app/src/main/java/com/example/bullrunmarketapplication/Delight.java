package com.example.bullrunmarketapplication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


//extending BaseMenu to track item details and OnClick to listen for actions
public class Delight extends BaseMenuActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delight);
        truckId = 1;

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
    }

    //current menu item names and prices
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.biry:
                addFoodToCart("Chicken Biryani", 10.00);
                break;
            case R.id.mysoremasala:
                addFoodToCart("Mysore Masala Dosa", 10);
                break;
            case R.id.jelebi:
                addFoodToCart("Jelebi", 5);
                break;
            case R.id.gulab:
                addFoodToCart("Gulab Jamun", 5);
                break;
            case R.id.thumbs:
                addFoodToCart("Thums Up", 2);
                break;
            case R.id.lassi:
                addFoodToCart("Mango Lassi", 3);
                break;
        }
    }

}
