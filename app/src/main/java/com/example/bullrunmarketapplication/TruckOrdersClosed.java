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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.bullrunmarketapplication.Checkout.FIREBASE_DOMAIN_URL;

//extending OrderAdapter to listen for when the order state transitions to closed
public class TruckOrdersClosed extends AppCompatActivity implements OrderAdapter.InteractionListener{

    List<Order> orders = new ArrayList<>();
    private OrderAdapter mOrdersAdapter;
    int truckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_orders);

        ((TextView)findViewById(R.id.orders)).setText("Closed Orders");

        truckId = getIntent().getIntExtra("truckId",1);
        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //cases to change the title of the activity in the app bar based on the truckID
        String title = "";
        switch (truckId){
            case 1:
                title = "Hyderabadi Delight";
                break;
            case 2:
                title = "Italian On Wheels";
                break;
            case 3:
                title = "Pizza Time";
                break;
            case 4:
                title = "Rico's Tacos";
                break;

        }

        //setting the title
        getSupportActionBar().setTitle(title);

        //recycler view to display the orders relative to what truckID they're from
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrdersAdapter = new OrderAdapter(orders,this);
        recyclerView.setAdapter(mOrdersAdapter);

        //captures the Firebase instance and runs through the onChildAdded function
        final FirebaseDatabase database =  FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        database.getReference("orders")
                .child(truckId + "").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                //grabbing the id key
                order.id = dataSnapshot.getKey();
                //places order in closedOrders activity if it is closed
                if(order.isClosed) {
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                    //updates the list of orders
                    mOrdersAdapter.notifyDataSetChanged();
                }
            }

            //function to remove the order from the list if it changes (open -> closed)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                order.id = dataSnapshot.getKey();

                orders.remove(order);
                //places order in closedOrders activity if it is closed
                if(order.isClosed){
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                }
                // updates the list of items
                mOrdersAdapter.notifyDataSetChanged();
            }

            //constructors to employ removed/moved/cancelled
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //function to create the options/overflow menu for the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_button_orders_open, menu);
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
            case R.id.orders_open:
                //toast to float message going to Cart
                Toast.makeText(getApplicationContext(), "These orders are open", Toast.LENGTH_SHORT).show();
                //intent to navigate to Checkout activity
                Intent openOrders = new Intent(this, TruckOrders.class);
                openOrders.putExtra("truckId",truckId);
                openOrders.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openOrders);
            default:
                //unknown error action TBD
        }

        return super.onOptionsItemSelected(item);
    }

    //executes onItemSelected
    @Override
    public void onItemSelected(Order order) {

    }
}
