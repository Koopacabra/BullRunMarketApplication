package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bullrunmarketapplication.repository.CartItemRepository;
import com.example.bullrunmarketapplication.storage.CartItem;

import java.util.ArrayList;

public class Pizza extends AppCompatActivity {

    Button pepporoniP, cheeseP, bread, spicy, coke, sprite;

    //string to hold food items and double to hold total price amount
    public static ArrayList<String> cart = new ArrayList<>();
    public static Double cartTotal = 0.00;
    public static int quantity = 0;

    private CartItemRepository cartItemRepository;
    String truck_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        truck_id =  getIntent().getStringExtra("truck_id");

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        pepporoniP = findViewById(R.id.pepperoni);
        cheeseP = findViewById(R.id.cheese);
        bread = findViewById(R.id.breadsticks);
        spicy = findViewById(R.id.spicyWings);
        coke = findViewById(R.id.coke);
        sprite = findViewById(R.id.sprite);


        cartItemRepository  = new  CartItemRepository(getApplicationContext());
    }

    private void addItem(String name , Double amount){
        CartItem item  = new CartItem();
        item.setName(name);
        item.setAmount(amount);
        item.setId(3);
        item.setTruck_id(truck_id);
        System.out.println(item.toString());
        cartItemRepository.saveCartItem(item);
    }

    //function to add food items to cart
    public void add_to_cart(View view) {

        if (view == findViewById(R.id.pepperoni)) {
            addItem("Pepperoni Pizza", 10.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 10.00;

        } else if (view == findViewById(R.id.cheese)) {

            addItem("Cheese Pizza",10.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 10.00;
        } else if (view == findViewById(R.id.breadsticks)) {

            addItem("Breadsticks",5.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 5.00;
        } else if (view == findViewById(R.id.coke)) {
            addItem("Coca Cola",2.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 2.00;
        } else if (view == findViewById(R.id.sprite)) {
            addItem("Sprite",2.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 2.00;
        } else if (view == findViewById(R.id.spicyWings)) {
            addItem("Spicy Wings",5.00);
            Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
            cartTotal += 5.00;
        }
    }

    public void checkout(View view) {

        Intent i = new Intent(Pizza.this, Checkout.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("Cart", cart);
        i.putExtra("Total", cartTotal);
        i.putExtra("Quantity", quantity);
        i.putExtras(bundle);
        startActivity(i);

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

}
