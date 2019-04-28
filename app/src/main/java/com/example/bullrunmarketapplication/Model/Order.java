package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/*This class is used to create the order to be saved. This object is stored in firebase to be used by trucks.
 */
@IgnoreExtraProperties
public class Order {
    @Exclude
    public String id;
    public List<Item> items;
    public String token;
    public String customer;
    public boolean isClosed = false;
    public double refunded = 0;

    //DO NOT DELETE -- used by Firebase's JSON to convert Firebase data to objects
    public Order(){
    }

    // builds the object based off Item.java
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
            //new line for each item/string
            builder.append(item.toString()+"\n");
        }
        return  builder.toString();
    }

    //calculates the purchase amount to be displayed with the orders on the truck side & in Firebase
    public double getAmountPaid(){
        double subtotal = 0;
        for(Item item : items){
            subtotal+=item.price*item.quantity;
        }

        double tax = subtotal * 0.085; // 8.5% state+local tax
        return subtotal + tax; //aka the total
    }

    /*order objects are differentiated with orderID in Firebase*/
    @Override
    public boolean equals(Object obj) {
        return id.equals(((Order)obj).id);
    }
}
