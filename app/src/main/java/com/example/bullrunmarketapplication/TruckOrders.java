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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.example.bullrunmarketapplication.Checkout.FIREBASE_DOMAIN_URL;

//extending the listeners from both OrderAdapter and CloseOrderDialogFragment to allow the dialog popup and transition orders to closed
public class TruckOrders extends AppCompatActivity implements OrderAdapter.InteractionListener, CloseOrderDialogFragment.InteractionListener {

    //obtaining & listing the orders from Orders.java, declaring local order adapter and truckID
    List<Order> orders = new ArrayList<>();
    private OrderAdapter mOrdersAdapter;
    int truckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_orders);

        //setting TextView label at the top
        ((TextView) findViewById(R.id.orders)).setText("Open Orders");

        /*locates the truckID from the order to only display orders for that truck;
        defaults to 1 but everyone order is associated with a truckID from when they browsed the menu*/
        truckId = getIntent().getIntExtra("truckId", 1);

        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //cases to change the appbar title depending on which truck it is
        String title = "";
        switch (truckId) {
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

        //displaying the recyclerView which displays Open orders appropriate to the truck
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrdersAdapter = new OrderAdapter(orders, this);
        recyclerView.setAdapter(mOrdersAdapter);

        //reads all the orders from Firebase for the related truckID
        final FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        database.getReference("orders")
                .child(truckId + "").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                //grabbing the id key
                order.id = dataSnapshot.getKey();

                //only looks for open orders, !/not isClosed
                if (!order.isClosed) {
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                    //updates the list of orders
                    mOrdersAdapter.notifyDataSetChanged();
                }
            }

            //called when the order details or status changes (open to closed)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                //grabbing the id key
                order.id = dataSnapshot.getKey();

                //updating the order in the list includes removing it from the list
                orders.remove(order);

                //interested only in open orders & adds to the list of orders if so
                if (!order.isClosed) {
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                }
                //updates the list
                mOrdersAdapter.notifyDataSetChanged();
            }

            /*Firebase callbacks which track the orders for the current truckID
            which is assigned to the truck's login credentials and in turn when they sign in*/

            //if the order is removed from Firebase
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            //if the order is moved within Firebase
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            //cancelled/cannot listen to Firebase changes
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //function to create the options/overflow menu for the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_button_orders_closed, menu);
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
            case R.id.orders_closed:
                //toast to float message going to closed orders
                Toast.makeText(getApplicationContext(), "These orders are closed", Toast.LENGTH_SHORT).show();
                //intent to navigate to closed orders activity
                Intent checkout = new Intent(this, TruckOrdersClosed.class);
                checkout.putExtra("truckId", truckId);
                checkout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(checkout);
            default:
                //unknown error action TBD
        }

        return super.onOptionsItemSelected(item);
    }

    //displays the dialog to close the order when selected (function below)
    Order selectedOrder;

    @Override
    public void onItemSelected(Order order) {
        selectedOrder = order;
        CloseOrderDialogFragment fragment = new CloseOrderDialogFragment();
        fragment.show(getSupportFragmentManager(), CloseOrderDialogFragment.class.getSimpleName());
    }

    //labels the order as closed within firebase if the user closes the order; toasts accordingly
    @Override
    public void closeOrderClicked() {
        selectedOrder.isClosed = true;
        final FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        // update the order closed status.
        database.getReference("orders")
                .child(truckId + "")
                .child(selectedOrder.id)
                .setValue(selectedOrder)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // order status updated successfully
                        Toast.makeText(TruckOrders.this, "Order Closed", Toast.LENGTH_SHORT).show();

                        //calls the sendMail function below (when an order is closed)
                        sendMail(selectedOrder);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // failed to update order status
                        Log.e(TruckOrders.class.getSimpleName(), e.getLocalizedMessage());
                        Toast.makeText(TruckOrders.this, "Order cannot be closed. Try Later!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*implementing the functions from the removed Email.java class
     * to be used here when the order is changed to closed;
     * calling the email object and orderID from the order details (Order.java via OrderAdapter.java);
     * calling the truck name from the function below which gets it from the app bar title*/
    private void sendMail(Order selectedOrder) {
        Hashtable<String, String> params = new Hashtable<>();
        params.put("to", selectedOrder.email);
        params.put("from", "bullrunmarketapp@gmail.com");
        params.put("subject", "Your BRM Order: "+selectedOrder.id+" is Ready");
        params.put("text", "Hello,\n\nYour order from "+ getTruckName()+" is ready for pickup!\n\n-Bull Run Market");

        new SendGridAsync().execute(params);
    }

    //pulls the truck name from the app bar title to be used in the email
    String getTruckName(){
        return getSupportActionBar().getTitle().toString();
    }

}
