package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DelightTruck_OpenOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delight_open_orders);

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

       /* //listView for open orders
        String[] openOrders = {"Order 1", "Order 2", "Order 3", "Order 4", "Order 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView[].getContext(), android.R.layout.simple_expandable_list_item_1, openOrders);
        getListView().setAdapter(adapter);*/
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
                Intent signOut = new Intent(this, MainActivity.class);
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
}

