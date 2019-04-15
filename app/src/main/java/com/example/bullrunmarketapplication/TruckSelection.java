package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class TruckSelection extends AppCompatActivity implements View.OnClickListener{

    private String truck_id;
    public  static  String DELIGHT = "truck_delight";
    public  static  String ITALIAN = "truck_italian";
    public  static  String PIZZA = "truck_pizza";
    public  static  String TACOS = "truck_tacos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_selection);
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //delight image button intent to navigate to hyderabadi delight landing page
        ImageView view = findViewById(R.id.cardImage_delight);
        CardView cardView = findViewById(R.id.cardView_delight);
        view.setOnClickListener(this);
        //if else to decide whether to show the normal or greyed-out icon for the truck based on the set schedule
        if(isAvailable(Delight_Landing.class)){
            view.setImageResource(R.drawable.delight);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        } else {
            view.setImageResource(R.drawable.delight_grey);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.darker_gray));
        }

        //delight image button intent to navigate to pizza time landing page
        view = findViewById(R.id.cardImage_pizza);
        view.setOnClickListener(this);
        cardView = findViewById(R.id.cardView_pizza);
        //if else to decide whether to show the normal or greyed-out icon for the truck based on the set schedule
        if(isAvailable(Pizza_Landing.class)){
            view.setImageResource(R.drawable.pizza);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        } else {
            view.setImageResource(R.drawable.pizza_grey);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.darker_gray));
        }

        //delight image button intent to navigate to italian on wheels landing page
        view = findViewById(R.id.cardImage_italian);
        view.setOnClickListener(this);
        cardView = findViewById(R.id.cardView_italian);
        //if else to decide whether to show the normal or greyed-out icon for the truck based on the set schedule
        if(isAvailable(Italian_Landing.class)){
            view.setImageResource(R.drawable.italian);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        } else {
            view.setImageResource(R.drawable.italian_grey);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.darker_gray));
        }

        //delight image button intent to navigate to ricos tacos landing page
        view = findViewById(R.id.cardImage_tacos);
        view.setOnClickListener(this);
        cardView = findViewById(R.id.cardView_tacos);
        //if else to decide whether to show the normal or greyed-out icon for the truck based on the set schedule
        if(isAvailable(Tacos_Landing.class)){
            view.setImageResource(R.drawable.tacos);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        } else {
            view.setImageResource(R.drawable.tacos_grey);
            cardView.setCardBackgroundColor(ContextCompat.getColor(this,android.R.color.darker_gray));
        }
    }

    //function to create the options/overflow menu for the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*function to determine what happens when an item is selected
    from the options menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case for Sign Out
            case R.id.options_signOut:
                //toast to float Goodbye message on sign out
                Toast.makeText(getApplicationContext(), "Goodbye!", Toast.LENGTH_SHORT).show();
                //intent to navigate to Main (sign in activity)
                Intent signOut = new Intent(this, SelectionScreen.class);
                startActivity(signOut);
                break;
            //case to go to About
            case R.id.options_about:
                //toast to float message going to About activity
                Toast.makeText(getApplicationContext(), "A little more about us...", Toast.LENGTH_SHORT).show();
                //intent to navigate to About activity
                Intent about = new Intent(this, About.class);
                startActivity(about);
                break;
            //case to go to Checkout
            case R.id.options_cart:
                //toast to float message going to Cart
                Toast.makeText(getApplicationContext(), "Does everything look correct?", Toast.LENGTH_SHORT).show();
                //intent to navigate to Checkout activity
                Intent checkout = new Intent(this, Checkout.class);
                startActivity(checkout);
            default:
                //unknown error action TBD
        }

        return super.onOptionsItemSelected(item);
    }

    //method for each activity's navigation intent
    @Override
    public void onClick(View v) {
        Class activityToStart = null;
        switch (v.getId()){
            case R.id.cardImage_delight:
                truck_id = DELIGHT;
                activityToStart = Delight_Landing.class;
                break;
            case R.id.cardImage_italian:

                truck_id = ITALIAN;
                activityToStart = Italian_Landing.class;
                break;
            case R.id.cardImage_pizza:

                truck_id = PIZZA;
                activityToStart = Pizza_Landing.class;
                break;
            case R.id.cardImage_tacos:
                truck_id = TACOS;
                activityToStart = Tacos_Landing.class;
                break;
        }

        //if else to navigate to landing page if it is available based on the method below
        if(activityToStart!=null){
            if(isAvailable(activityToStart)) {
               Intent  intent = new Intent(TruckSelection.this, activityToStart);
               intent.putExtra("truck_id", truck_id );
                startActivity(intent);
            } else {
                truckNotAvailableToast();
            }
        }
    }

    //method which determines which trucks are available on which days
    boolean isAvailable(Class clazz){
        Calendar now = Calendar.getInstance();
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

        // No trucks are available on Sunday -- changes all icons to greyscale
        if(dayOfWeek == 1){
            return false;
        }


        if(clazz.equals(Delight_Landing.class)){
            // Monday, Wed, Thursday, Fri, Sat
            return (dayOfWeek == 2 || dayOfWeek == 4 || dayOfWeek == 5 || dayOfWeek == 6 || dayOfWeek == 7);
        } else if(clazz.equals(Italian_Landing.class)){
            // Mon, Tue, Thur, Fri, Sat
            return (dayOfWeek == 2 || dayOfWeek == 3 || dayOfWeek == 5 || dayOfWeek == 6 || dayOfWeek == 7);
        } else if(clazz.equals(Pizza_Landing.class)){
            // Mon, Tue, Wed, Fri, Sat
            return (dayOfWeek == 2 || dayOfWeek == 3 || dayOfWeek == 4 ||dayOfWeek == 6 || dayOfWeek == 7);
        } else if(clazz.equals(Tacos_Landing.class)){
            // Tue, Wed, Thur, Fri, Sat
            return (dayOfWeek == 3 || dayOfWeek == 4 || dayOfWeek == 5 || dayOfWeek == 6 || dayOfWeek == 7);
        }
        return false;
    }

    //toasty to let the user know the truck is not available today (only shown if the truck is not scheduled for today)
    void truckNotAvailableToast(){
        Toast.makeText(this, "Truck is not available today", Toast.LENGTH_SHORT).show();
    }
}