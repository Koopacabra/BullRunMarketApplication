package com.example.bullrunmarketapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

//parcelable creates a serializable list of items we can add and remove to/from
public class FoodItem implements Parcelable {

    int id;
    public String name;
    public double price;
    public int quantity;

    //public function used during checkout to increment/decrement along with the correct item details
    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    //declaring variables to be read into the parcelable list
    protected FoodItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    //creates a new parcelable list
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

    //records the item name
    @Override
    public boolean equals(Object obj) {
        return name.equals(((FoodItem)obj).name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //adding the variables into the parcelable list
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }
}
