package com.example.bullrunmarketapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bullrunmarketapplication.Model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

import static com.example.bullrunmarketapplication.Checkout.FIREBASE_DOMAIN_URL;

//refund class which uses various listeners
public class PaymentRefund extends Activity implements View.OnClickListener {

    private double total;
    EditText refundET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_refund);

        //associates refund button to listener & the refundEditText to the refund total value view
        findViewById(R.id.confirmRefund).setOnClickListener(this);
        refundET = findViewById(R.id.refundTV);

        //subtotal starts at 0 and then calculates based on the order items
        double subtotal = 0;
        for(Item foodItem : TruckOrdersClosed.selectedOrder.items){
            subtotal+= foodItem.price * foodItem.quantity;
        }
        //calculates the subtotal + tax to return the total order amount
        double tax = subtotal * 0.085; // 8.5%
        total = subtotal + tax;

        //ensures all the amounts are displayed in #.## format
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        ((TextView)findViewById(R.id.subTotal)).setText("$"+ decimalFormat.format(subtotal));
        ((TextView)findViewById(R.id.tax)).setText("$"+ decimalFormat.format(tax));
        ((TextView)findViewById(R.id.total)).setText("$"+ decimalFormat.format(total));

        //shows the amount of the order already refunded so the user knows how much remains
        ((TextView)findViewById(R.id.alreadyRefunded)).setText("Amount Refunded:$"+new DecimalFormat("#.##").format(TruckOrdersClosed.selectedOrder.refunded));
        //lists the items within the order for reference
        ((TextView)findViewById(R.id.items_details)).setText(TruckOrdersClosed.selectedOrder.toString());

        /*coding styles for popup window to appear as a floating window;
         * taken from https://www.youtube.com/watch?v=eX-TdY6bLdg via Angga Risky*/
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //setting values for window width and height
        getWindow().setLayout((int)(width*.9), (int)(height*.75));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

    }

    //function to govern the refund button in refund processing
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirmRefund){
            double actual;

            //issues the refund and adds the amount to the total amount refunded
            try {
                double amountToRefund = Double.parseDouble(refundET.getText().toString());
                actual = TruckOrdersClosed.selectedOrder.refunded + amountToRefund;

                // sum of all refunds made should be less or equal order payment
                if(actual > total){
                    refundET.setError("The refunded amount cannot exceed the total.");
                    return;
                }
            //throws invalid error if button is pressed without any number entered
            } catch (NumberFormatException e){
                refundET.setError("Invalid refund amount.");
                return;
            }

            //updates order object with refund amount
            TruckOrdersClosed.selectedOrder.refunded = actual;

            //saves the refunded amount to Firebase & toasts error if something goes wrong
            final FirebaseDatabase database =  FirebaseDatabase.getInstance(FIREBASE_DOMAIN_URL);
            database.getReference("orders")
                    .child(TruckOrdersClosed.truckId + "").child(TruckOrdersClosed.selectedOrder.id).setValue(TruckOrdersClosed.selectedOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(PaymentRefund.this, "Refund issued.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TruckOrders.class.getSimpleName(),e.getLocalizedMessage());
                    Toast.makeText(PaymentRefund.this, "Refund cannot be processed at this time. Try again later!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}