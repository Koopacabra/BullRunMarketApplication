package com.example.bullrunmarketapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.text.DecimalFormat;

public class PaymentProcessing extends Activity implements View.OnClickListener, TokenCallback {

    //initializing public test key from Chris' Stripe dashboard to verify the key can successfully generate a token
    private static final String TEST_API_KEY = "pk_test_4nzAtAH3VJFpqBLQQzrUFTKL00MEotGJeR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_processing);

        findViewById(R.id.confirmPurchase).setOnClickListener(this);
        double subtotal = getIntent().getDoubleExtra("subtotal",0.0);
        double tax = subtotal * 0.085; // 8.5%
        double total = subtotal + tax;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        ((TextView)findViewById(R.id.subTotal)).setText("$"+ decimalFormat.format(subtotal));
        ((TextView)findViewById(R.id.tax)).setText("$"+ decimalFormat.format(tax));
        ((TextView)findViewById(R.id.total)).setText("$"+ decimalFormat.format(total));

        //coding styles for popup window to appear as a floating window
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirmPurchase){
            CardInputWidget ciw = findViewById(R.id.card_input_widget);
            Card card = ciw.getCard();
            if(card==null){
                Toast.makeText(this, "Invalid card info", Toast.LENGTH_SHORT).show();
                return;
            }

            startPayment(card);
        }
    }

    void startPayment(Card card){
        new Stripe(this,TEST_API_KEY)
                .createToken(card,this);
    }

    @Override
    public void onError(Exception error) {
        Log.e("Payment",error.getLocalizedMessage());
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSuccess(Token token) {
        Log.i("Payment","Token:"+token.getId());
        Intent intent = new Intent();
        intent.putExtra("token",token.getId());
        setResult(RESULT_OK,intent);
        finish();
    }
}