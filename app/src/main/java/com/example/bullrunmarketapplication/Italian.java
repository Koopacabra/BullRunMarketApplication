package com.example.bullrunmarketapplication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

//extending BaseMenu to track item details and OnClick to listen for actions
public class Italian extends BaseMenuActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_italian);
        truckId = 2;

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
    }

    //current menu item names and prices
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shells:
                addFoodToCart("Stuffed Shells",9);
                break;
            case R.id.spaghetti:
                addFoodToCart("Spaghetti & Meatballs", 10);
                break;
            case R.id.oyster:
                addFoodToCart("Oyster & Mushroom Salad", 7);
                break;
            case R.id.garlicKnots:
                addFoodToCart("Garlic Knots",6);
                break;
            case R.id.virginLim:
                addFoodToCart("Virgin Limoncello",3.50);
                break;
            case R.id.icedTea:
                addFoodToCart("Iced Tea", 2);
                break;
        }
    }
}