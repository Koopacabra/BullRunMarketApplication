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
import java.util.List;

import static com.example.bullrunmarketapplication.Checkout.FIREBASE_DOMAIN_URL;

//extending the listeners from both OrderAdapter and CloseOrderDialogFragment to allow the dialog popup and transition orders to closed
public class TruckOrders extends AppCompatActivity implements OrderAdapter.InteractionListener, CloseOrderDialogFragment.InteractionListener {

    List<Order> orders = new ArrayList<>();
    private OrderAdapter mOrdersAdapter;
    int truckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_orders);

        //setting TextView label at the top
        ((TextView)findViewById(R.id.orders)).setText("Open Orders");

        truckId = getIntent().getIntExtra("truckId",1);
        //casting toolbar as an actionbar
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);

        //cases to change the appbar title depending on which truck it is
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

        //displaying the recyclerView which displays the orders appropriate to the truck
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrdersAdapter = new OrderAdapter(orders,this);
        recyclerView.setAdapter(mOrdersAdapter);

        //calling the firebase orders collection and labeling if closed or not
        final FirebaseDatabase database =  FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        database.getReference("orders")
                .child(truckId + "").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                order.id = dataSnapshot.getKey();
                if(!order.isClosed) {
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                    // update list
                    mOrdersAdapter.notifyDataSetChanged();
                }
            }

            //removes the order if the order status is changed
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                order.id = dataSnapshot.getKey();

                orders.remove(order);

                //lists order as closed
                if(!order.isClosed){
                    orders.add(order);
                    Log.i(TruckOrders.class.getSimpleName(), order.toString());
                }
                // update list
                mOrdersAdapter.notifyDataSetChanged();
            }

            //constructors needed for removed/moved/cancelled
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
                //toast to float message going to Cart
                Toast.makeText(getApplicationContext(), "These orders are completed", Toast.LENGTH_SHORT).show();
                //intent to navigate to Checkout activity
                Intent checkout = new Intent(this, TruckOrdersClosed.class);
                checkout.putExtra("truckId",truckId);
                checkout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(checkout);
            default:
                //unknown error action TBD
        }

        return super.onOptionsItemSelected(item);
    }

    //displays the dialog to close the order when selected
    Order selectedOrder;
    @Override
    public void onItemSelected(Order order) {
        selectedOrder = order;
        CloseOrderDialogFragment fragment = new CloseOrderDialogFragment();
        fragment.show(getSupportFragmentManager(), CloseOrderDialogFragment.class.getSimpleName());
    }

    //labels the order as closed within firebase if the user closes the order and toasts accordingly
    @Override
    public void closeOrderClicked() {
        selectedOrder.isClosed = true;
        final FirebaseDatabase database =  FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
        database.getReference("orders")
                .child(truckId + "").child(selectedOrder.id).setValue(selectedOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(TruckOrders.this, "Order Closed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TruckOrders.class.getSimpleName(),e.getLocalizedMessage());
                Toast.makeText(TruckOrders.this, "Order cannot be closed. Try Later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
