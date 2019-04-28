package com.example.bullrunmarketapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;


 /*This class is our virtual cart which holds the added items;
 Made parcelable so multiple items can be bundled easier
  */
public class FoodItem implements Parcelable {

    int id;
    public String name;
    public double price;
    public int quantity;

    //initializes the Object with name, price and default quantity of 1
    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    // parcelable implementation
    protected FoodItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    /*parcelable implementation similar to Vogella.com examples*/
    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    // FoodItems identified by their name
    @Override
    public boolean equals(Object obj) {
        return name.equals(((FoodItem) obj).name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

     /*parcelable implementation similar to Vogella.com examples*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }

    public Item toItem() {
        return new Item(name, price, quantity);
    }

    //prepares string which includes quantity and name. Can be used to display in lists/activities
    @Override
    public String toString() {
        return "("+quantity+") " + name;
    }
}
