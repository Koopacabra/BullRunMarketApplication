package com.example.bullrunmarketapplication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

//extending BaseMenu to track item details and OnClick to listen for actions
public class Tacos extends BaseMenuActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacos);
        //assigning any menu items to the Tacos TruckID
        truckId = 4;

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
    }

    //current menu item names and prices
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.beefTaco:
                addFoodToCart("Beef Taco", 1.5);
                break;
            case R.id.chickenTaco:
                addFoodToCart("Chicken Taco",1.5);
                break;
            case R.id.combo:
                addFoodToCart("Flautas",8);
                break;
            case R.id.cheeseQuesadilla:
                addFoodToCart("Cheese Quesadilla", 7);
                break;
            case R.id.horchata:
                addFoodToCart("Horchata", 3.5);
                break;
            case R.id.mex:
                addFoodToCart("Jarritos-Orange",2.5);
                break;
        }
    }
}
