package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

//to be used with firebase database order creation & labeling as closed or not
@IgnoreExtraProperties
public class Order {
    @Exclude
    public String id;
    public List<Item> items;
    public String token;
    public String customer;
    public boolean isClosed = false;

    //DO NOT DELETE -- used by json
    public Order(){
    }

    //assigning variables during order creation respective to the items in the current activity
    public Order(List<Item> items, String token, String customer) {
        this.items = items;
        this.token = token;
        this.customer = customer;
    }

    //includes the item names in string format
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Item item : items){
            builder.append(item.toString()+"\n");
        }
        return  builder.toString();
    }

    //calculates the purchase amount to be displayed with the orders on the truck side
    public double getAmountPaid(){
        double subtotal = 0;
        for(Item item : items){
            subtotal+=item.price*item.quantity;
        }

        double tax = subtotal * 0.085; // 8.5%
        return subtotal + tax; //aka the total
    }

    //displays the orderID
    @Override
    public boolean equals(Object obj) {
        return id.equals(((Order)obj).id);
    }
}
