package com.example.bullrunmarketapplication.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CartItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public  abstract CartItemDao cartItemDao();
}
