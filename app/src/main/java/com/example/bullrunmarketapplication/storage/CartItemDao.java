package com.example.bullrunmarketapplication.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CartItemDao {

    /**
     * Get all cart items from the db
     * @return
     */
    @Query("SELECT * FROM cart")
    public LiveData<List<CartItem>> getAllCartItems();

    /**
     * insert a cart intem to the db
     * @param item
     */
    @Insert
    void insertCartItem(CartItem item);

    @Query("SELECT * FROM cart WHERE truck_id = :truck_id LIMIT 10")
    LiveData<List<CartItem>> getTruckItems(String truck_id);
}
