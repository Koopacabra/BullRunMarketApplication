package com.example.bullrunmarketapplication.Model;

import java.util.List;

//to be used with firebase database order creation
public class Order {
    public List<FoodItem> items;
    public String token;
    public int truck;


    public Order(List<FoodItem> items, String token, int truck) {
        this.items = items;
        this.token = token;
        this.truck = truck;
    }
}
