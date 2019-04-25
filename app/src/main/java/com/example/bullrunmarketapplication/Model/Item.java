package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

//parcelable creates a serializable list of items we can add and remove to/from
@IgnoreExtraProperties
public class Item {

    public String name;
    public double price;
    public int quantity;

    //DO NOT DELETE -- used by json
    public Item() {
    }

    //function to assign variables to the items within the current activity
    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //pulls the item name from Order.java's toString and includes the quantity
    @Override
    public String toString() {
        return "("+quantity+") " + name;
    }
}
