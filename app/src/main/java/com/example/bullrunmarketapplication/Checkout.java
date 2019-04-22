package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.FoodItem;
import com.example.bullrunmarketapplication.Model.Order;

import java.text.DecimalFormat;
import java.util.List;

//implementing view onClickListener to we can take action on clicks when needed
public class Checkout extends AppCompatActivity implements View.OnClickListener {

    //declaring FoodItem list from the class & other necessary variables
    private List<FoodItem> items;
    private TextView textView;
    private RecyclerView recyclerView;
    private CheckoutAdapter mAdapter;
    private int truck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //assigning the total to the textview and listening for a click
        textView = findViewById(R.id.total);
        findViewById(R.id.makePayment).setOnClickListener(this);

        //parcelableArrayList creates a serializable list of items
        items = getIntent().getParcelableArrayListExtra("list");
        truck = getIntent().getIntExtra("truck",1);

        /*loading recycler view (handy with cards, server/json requests, refreshing data on swipes
        /and expandable list items) and declaring adapter to listen*/
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CheckoutAdapter(items, this);
        recyclerView.setAdapter(mAdapter);

        //calling updateTotal method
        updateTotal();
    }

    //shows the calculated total in $#.## format next to the total textview
    private void updateTotal() {
        double total = getTotal();
        textView.setText("Total: $" + new DecimalFormat("#.##").format(total));
    }

    /*function to calculate the subtotal; starts at 0 and scans through all items,
     multiplying the price and quantity as it goes*/
    private double getTotal() {
        double total = 0;
        for (FoodItem item : items) {
            total += item.price * item.quantity;
        }
        return total;
    }

    //function to increase or decrease quantity based on which button is selected
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add || v.getId() == R.id.sub) {
            int index = Integer.parseInt(String.valueOf(v.getTag()));
            //increases quantity based on the FoodItem index so it can populate/depopulate from checkout correctly
            FoodItem item = items.get(index);
            if (v.getId() == R.id.add) {
                item.quantity++;
            } else {
                item.quantity--;
            }

            //keeps the item visible in checkout if there is at least 1
            if (item.quantity > 0) {
                mAdapter.mItems = items;
                recyclerView.getAdapter().notifyDataSetChanged();

                updateTotal();

            //removes the item from checkout if it falls below 1
            } else {
                items.remove(index);

                mAdapter.mItems = items;
                if (items.size() > 0) {
                    //tracks & reflects item removal
                    recyclerView.getAdapter().notifyDataSetChanged();
                    updateTotal();
                //clears the cart if no items remain
                } else {
                    finish();
                }
            }

        /*navigates to payment processing if the button is clicked,
          collects subtotal to be used and references truck id code*/
        } else if (v.getId() == R.id.makePayment) {
            Intent pop = new Intent(getApplicationContext(), PaymentProcessing.class);
            pop.putExtra("subtotal",getTotal());
            startActivityForResult(pop,1234);
        }
    }

    //function for order creation TBD
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1234){
            if(resultCode == RESULT_OK){
                Order order = new Order(items,data.getStringExtra("token"),truck);
                // TODO save to firebase

                setResult(RESULT_OK);
                finish();
            //throws payment failed error if payment fails or user navigates away
            } else {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
