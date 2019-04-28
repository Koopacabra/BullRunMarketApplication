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

/*checkout adapter to help bridge the data and checkout view;
recycler view can refresh data on swipes and is handy with collections*/
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder> {

    List<FoodItem> mItems;
    View.OnClickListener mListener;

    public CheckoutAdapter(List<FoodItem> items,View.OnClickListener listener){
        this.mItems = items;
        mListener = listener;
    }

    /*Builds the layout for each item index;
     *Any item can populate in the list based on which view/order/truck it is for.*/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_checkout_item,viewGroup,false));
    }

    //Binds the data to the layout that was just built
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        FoodItem item = mItems.get(position);
        myViewHolder.name.setText(item.name);
        myViewHolder.price.setText("$"+new DecimalFormat("#.##").format(item.price));
        myViewHolder.quantity.setText(""+item.quantity);
        myViewHolder.add.setOnClickListener(mListener);
        myViewHolder.sub.setOnClickListener(mListener);
        myViewHolder.add.setTag(""+position);
        myViewHolder.sub.setTag(""+position);
    }

    //counts the number of items in the recyclerView
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //ViewHolder class that keeps track of all the views in the recyclerView layout
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
