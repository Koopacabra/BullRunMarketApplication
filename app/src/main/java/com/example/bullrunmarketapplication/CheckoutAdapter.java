package com.example.bullrunmarketapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bullrunmarketapplication.Model.FoodItem;

import java.text.DecimalFormat;
import java.util.List;

//checkout adapter needed to bridge the view and data; recycler view can refresh data on swipes and is handy with collections
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    List<FoodItem> mItems;
    View.OnClickListener mListener;

    public CheckoutAdapter(List<FoodItem> items,View.OnClickListener listener){
        this.mItems = items;
        mListener = listener;
    }

    //inserts items into the checkout list
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_checkout_item,viewGroup,false));
    }

    //contains items listed in the view and listens for adding / subtracting quantity
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        FoodItem item = mItems.get(i);
        myViewHolder.name.setText(item.name);
        myViewHolder.price.setText("$"+new DecimalFormat("#.##").format(item.price));
        myViewHolder.quantity.setText(""+item.quantity);
        myViewHolder.add.setOnClickListener(mListener);
        myViewHolder.sub.setOnClickListener(mListener);
        myViewHolder.add.setTag(""+i);
        myViewHolder.sub.setTag(""+i);
    }

    //used for subtotal calculation
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //declares item details to be tracked in the view
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        Button add;
        Button sub;
        TextView quantity;

        //assigns the above variables to their respective layout objects
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.add);
            sub = itemView.findViewById(R.id.sub);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
