package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;


/*class Item to convert local cart/order to be sent to Firebase;
stores the name, price and quantity*/
@IgnoreExtraProperties
public class Item {

    public String name;
    public double price;
    public int quantity;

    //DO NOT DELETE -- used by Firebase's JSON to convert Firebase data to objects
    public Item() {
    }

    // builds the object
    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //prepares string which includes quantity and name. Can be used to display in lists/activities
    @Override
    public String toString() {
        return "("+quantity+") " + name;
    }
}
