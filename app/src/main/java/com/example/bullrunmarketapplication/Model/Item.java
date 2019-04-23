package com.example.bullrunmarketapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

//parcelable creates a serializable list of items we can add and remove to/from
@IgnoreExtraProperties
public class Item {

    public String name;
    public double price;
    public int quantity;

    public Item() {

    }

    //public function used during checkout to increment/decrement along with the correct item details
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
