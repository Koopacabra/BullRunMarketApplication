package com.example.bullrunmarketapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.FoodItem;

import java.util.ArrayList;

//ignoring Lint warnings
@SuppressLint("Registered")
//truckId ranges from 1-4, defaults to 1 unless declared otherwise.  Adds the menu items to the checkout list
public class BaseMenuActivity extends AppCompatActivity {
    protected int truckId = 1;
    protected ArrayList<FoodItem> itemsToCheckout =new ArrayList<>();

    // Adds the item to the cart. Increases the quantity by 1 if the item is already in the cart.
    void addFoodToCart(String name, double price){
        Toast.makeText(this, name+" added to cart", Toast.LENGTH_SHORT).show();
        FoodItem foodItem = new FoodItem( name, price);
        int index = itemsToCheckout.indexOf(foodItem);

        /*checks if the added foodItem is not already in the cart and if not, it is added;
         else, the existing item in the cart has its quantity increased.*/
        if(index == -1){
            itemsToCheckout.add(foodItem);
        } else {
            itemsToCheckout.get(index).quantity++;
        }
    }

    //finishes the activity on a successful purchase (clears dialogues, cursors, etc.)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234 && resultCode == RESULT_OK) {
            finish();
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
                //intent to navigate to Checkout activity
                Intent checkout = new Intent(this, Checkout.class);
                checkout.putParcelableArrayListExtra("list",itemsToCheckout);
                checkout.putExtra("truck",truckId);
                startActivityForResult(checkout,1234);
                break;
            default:
                //unknown error action TBD
        }

        return super.onOptionsItemSelected(item);
    }

}
