package com.example.bullrunmarketapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bullrunmarketapplication.repository.CartItemRepository;
import com.example.bullrunmarketapplication.storage.CartItem;

import java.text.NumberFormat;
import java.util.List;

public class Checkout extends AppCompatActivity {

    //initializing purchase button which will activate the PaymentProcessing popup
    Button btnPurchase;


    //intitalize views to display cart content
    TextView priceView,quantityView;
    ListView listView;
    //ArrayList<String> cart_choices;
    Double price_usd;
    //int quantity;
    NumberFormat price = NumberFormat.getCurrencyInstance();

    private CartItemRepository cartItemRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartItemRepository  = new  CartItemRepository(getApplicationContext());

        listView = (ListView) findViewById(R.id.ListViewCatalog);
        priceView = (TextView) findViewById(R.id.TextViewSubtotal);
        //quantityView = (TextView) findViewById(R.id.cart_product_quantity);

        LiveData<List<CartItem>> items = cartItemRepository.getCartItem();


        items.observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(@Nullable List<CartItem> cartItems) {

                CartItemAdapter adapter = new CartItemAdapter(getApplicationContext(), cartItems);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);

                //added != null statement to avoid possible nullPointerException from the println; 3/28CC
                assert cartItems != null;
                System.out.println(cartItems.toString());
            }
        });


        Intent i = getIntent();
        price_usd = i.getDoubleExtra("Total",Italian.cartTotal);
        priceView.setText(price.format(price_usd));
        //quantity = i.getIntExtra("Quantity", IOW.quantity);
        //priceView.setText(price.format(price_usd));


        //linking the intent to open PaymentProcessing to the purchaseButton
        btnPurchase = findViewById(R.id.purchaseButton);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pop = new Intent(getApplicationContext(), PaymentProcessing.class);
                startActivity(pop);
            }
        });

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

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
                Toast.makeText(getApplicationContext(), "What else is there to know?", Toast.LENGTH_SHORT).show();
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
