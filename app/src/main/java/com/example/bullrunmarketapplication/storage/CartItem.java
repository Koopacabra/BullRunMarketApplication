package com.example.bullrunmarketapplication.storage;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cart")
public class CartItem {



    @ColumnInfo(name = "truck_id")
    public String truck_id;
    @PrimaryKey(autoGenerate =  true)
    @ColumnInfo(name = "order_id")
    public int orderID;
    @ColumnInfo(name = "item_id")
    private int id;
    @Override
    public String toString() {
        return "CartItem{" +
                "orderID=" + orderID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", truck_id='" + truck_id + '\'' +
                '}';
    }

    @ColumnInfo(name = "item_name")
    private String name ;
    @ColumnInfo(name = "item_amount")
    private Double amount;

    public String getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(String truck_id) {
        this.truck_id = truck_id;
    }



    public int getOrderId() {
        return orderID;
    }

    public void setOrderId(int orderID) {
        this.orderID = orderID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
