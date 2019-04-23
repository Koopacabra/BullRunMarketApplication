package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

//to be used with firebase database order creation
@IgnoreExtraProperties
public class Order {
    public List<Item> items;
    public String token;

    public Order(){
        // DONOT remove. used to by json
    }

    //assigns the items and token from the current activity's successful purchase
    public Order(List<Item> items, String token) {
        this.items = items;
        this.token = token;
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
}
