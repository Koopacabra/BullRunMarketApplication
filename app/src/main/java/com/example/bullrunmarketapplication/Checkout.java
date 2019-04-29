package com.example.bullrunmarketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.FoodItem;
import com.example.bullrunmarketapplication.Model.Item;
import com.example.bullrunmarketapplication.Model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//implementing view onClickListener so we can take action on clicks when needed
public class Checkout extends AppCompatActivity implements View.OnClickListener, OnSuccessListener, OnFailureListener {

    //declaring Firebase database as a public variable for other classes to use
    public static final String FIREBASE_DOMAIN_URL = "https://bullrunmarketapplication.firebaseio.com/";

    //declaring FoodItem list from FoodItem.java (the cart) & other necessary variables
    private List<FoodItem> items;
    private TextView textView;
    private RecyclerView recyclerView;
    private CheckoutAdapter mAdapter;
    private int truckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //assigning the total to the textView and listening for a click
        textView = findViewById(R.id.total);
        findViewById(R.id.makePayment).setOnClickListener(this);

        /*parcelableArrayList creates an editable bundled list of items and associates a truckID;
        truckID defaults to 1 unless declared/captured otherwise*/
        items = getIntent().getParcelableArrayListExtra("list");
        truckId = getIntent().getIntExtra("truck", 1);

        //statement to allow the cart to be viewed even if it is empty (this resolved the crash we had before)
        if(items==null || items.size()==0){
            items = new ArrayList<>();
            Toast.makeText(this, "No Items in Cart!", Toast.LENGTH_SHORT).show();
            findViewById(R.id.makePayment).setEnabled(false);
            return;
        }
        /*loading recycler view (handy with cards, server/json requests, refreshing data on swipes
        /and expandable list items) and declaring adapter to listen*/
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CheckoutAdapter(items, this);
        recyclerView.setAdapter(mAdapter);

        //calling updateTotal method
        updateTotal();
    }

    //shows the calculated total in $#.## format next to the total textView
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

            //items only appear in the list/recyclerView if there is at least 1; updates the adapter;
            if (item.quantity > 0) {
                mAdapter.mItems = items;
                recyclerView.getAdapter().notifyDataSetChanged();

                updateTotal();

            //removes the item from cart if it falls below 1 & updates adapter
            } else {
                items.remove(index);

                mAdapter.mItems = items;

                //checks if the cart is empty or not and updates the total based on the recyclerView adapter
                if (items.size() > 0) {
                    recyclerView.getAdapter().notifyDataSetChanged();
                    updateTotal();

                //Closes the activity and clears the cart
                } else {
                    finish();
                }
            }

        /*navigates to payment processing if the button is clicked,
          collects subtotal to be used and references the RESULT_OK code*/
        } else if (v.getId() == R.id.makePayment) {
            Intent pop = new Intent(getApplicationContext(), Payment.class);
            pop.putExtra("subtotal", getTotal());
            startActivityForResult(pop, 1234);
        }
    }

    //function to save the order to the firebase on a successful purchase or error if it failed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1234) {
            if (resultCode == RESULT_OK) {
                saveOrderToFirebase(items, data.getStringExtra("token"));
            } else {
                //throws payment failed error if payment fails or user navigates away
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Prepares the order object that will be stored in firebase
    void saveOrderToFirebase(List<FoodItem> foodItems, String token) {

        //converts FootItem(Parcelable object only used to send objects between activities) to Item(Firebase JSON object)
        List<Item> items = new ArrayList<>();
        for (FoodItem foodItem : foodItems) {
            items.add(foodItem.toItem());
        }

        /*saves the items ordered + the token from the successful purchase;
        * uses the timestamp the order was placed as the orderID rather than random generation;
        * includes the truckID so that the order goes to the correct truck;
        * this saved structure matches the Firebase structure*/
        Order order = new Order(items, token,CustLogin.getUsername(this), CustLogin.getUserEmail(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        database.getReference("orders")
                .child(truckId + "")
                .child(System.currentTimeMillis() + "")
                .setValue(order)
                .addOnSuccessListener(this)
                .addOnFailureListener(this);
    }

    //quick function for onSuccess actions
    @Override
    public void onSuccess(Object o) {
        setResult(RESULT_OK);
        finish();
    }

    //quick function for onFailure actions & a polite toasty if something does go wrong
    @Override
    public void onFailure(@NonNull Exception e) {
        Log.i(Checkout.class.getSimpleName(),e.getLocalizedMessage());
        Toast.makeText(this, "Unable to place an order. Amount will be returned if deducted.", Toast.LENGTH_SHORT).show();
    }
}
