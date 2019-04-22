package com.example.bullrunmarketapplication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

//extending BaseMenu to track item details and OnClick to listen for actions
public class Pizza extends BaseMenuActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        truckId = 3;

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
    }

    //current menu item names and prices
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pepperoni:
                addFoodToCart("Pepperoni", 10);
                break;
            case R.id.cheese:
                addFoodToCart("Cheese Pizza",9);
                break;
            case R.id.spicyWings:
                addFoodToCart("Spicy Wings", 6);
                break;
            case R.id.breadsticks:
                addFoodToCart("Breadsticks",5);
                break;
            case R.id.coke:
                addFoodToCart("Coca-Cola", 2);
                break;
            case R.id.sprite:
                addFoodToCart("Sprite",2);
                break;
        }

    }
}
