package com.example.bullrunmarketapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.bullrunmarketapplication.storage.CartItem;


public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private List<CartItem> cartList = new  ArrayList<CartItem>();

    public CartItemAdapter(Context context, List<CartItem> items ){
        super(context, -1, items);
        this.context = context;
        this.cartList = items;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView  = inflater.inflate(R.layout.activity_cart_item, null, false);
        TextView name  = rowView.findViewById(R.id.cart_item_name);
        TextView amount = rowView.findViewById(R.id.cart_item_amount);

        CartItem item = cartList.get(position);

        name.setText(item.getName());
        amount.setText( "$" + item.getAmount().toString());

        return rowView;

    }



    @Override
    public int getCount() {
        return cartList.size();
    }


    @Override
    public CartItem getItem(int position) {
        return cartList.get(position);
    }
}
