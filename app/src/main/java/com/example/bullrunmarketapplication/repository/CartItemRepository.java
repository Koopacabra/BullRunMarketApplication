package com.example.bullrunmarketapplication.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import com.example.bullrunmarketapplication.repository.CartItemRepository;
import com.example.bullrunmarketapplication.storage.AppDatabase;
import com.example.bullrunmarketapplication.storage.CartItem;

public class CartItemRepository {
    public  String  DATABASE_NAME = "brm_db";
    private AppDatabase appDatabase;

    public CartItemRepository(Context context){
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }

    public void saveCartItem(final CartItem item){

         class Task extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.cartItemDao().insertCartItem(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                System.out.println("Item was Added");
            }
        }

        new Task().execute();
    }

    public LiveData<List<CartItem>> getCartItem (){
        return appDatabase.cartItemDao().getAllCartItems();
    }
}
