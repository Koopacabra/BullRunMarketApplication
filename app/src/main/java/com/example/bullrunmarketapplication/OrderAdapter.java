package com.example.bullrunmarketapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bullrunmarketapplication.Model.Order;

import java.text.DecimalFormat;
import java.util.List;


//adapter used to display the list of items in the activity_truck_orders activity via view_order.xml
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> implements View.OnClickListener{


    interface InteractionListener{
        void onItemSelected(Order order);

    }

    private InteractionListener mListener;
    private List<Order> mOrders;

    //adapter to bridge order data with the app
    public OrderAdapter(List<Order> orders,InteractionListener listener){
        mOrders = orders;
        mListener = listener;
    }

    //inflates/makes visible the items via the view_order.xml
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_order,viewGroup,false);
        return new MyViewHolder(view);
    }

    //displays the total for the order based on the getAmountPaid from Order.java
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Order order = mOrders.get(i);
        myViewHolder.token.setText(order.id);
        myViewHolder.items.setText(order.toString());
        myViewHolder.total.setText("Amount Paid: $"+ new DecimalFormat("#.##").format(order.getAmountPaid()));
        myViewHolder.username.setText(order.customer);
        myViewHolder.itemView.setOnClickListener(this);
        myViewHolder.itemView.setTag(""+i);
    }

    //displays quantity
    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    @Override
    public void onClick(View v) {
        mListener.onItemSelected(mOrders.get(Integer.parseInt(v.getTag()+"")));
    }

    //function to determine which items (based on their id) and the total from the respective order to be displayed
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView token;
        TextView items;
        TextView total;
        TextView username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            token = itemView.findViewById(R.id.token);
            items = itemView.findViewById(R.id.items);
            total = itemView.findViewById(R.id.total);
            username = itemView.findViewById(R.id.username);
        }
    }
}
