package com.smartwebarts.mech24ra.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.activity.ViewDetailsActivity;
import com.smartwebarts.mech24ra.retrofit.My_order_model;

import java.util.List;

class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    Context context;
    List<My_order_model> list;
    boolean isToday;

    public MyOrderAdapter(Context context, List<My_order_model> list, boolean isToday) {
        this.context = context;
        this.list = list;
        this.isToday = isToday;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list, viewGroup, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_order_no.setText(list.get(i).getOrderId());
        myViewHolder.tv_recivername.setText(list.get(i).getName());
        myViewHolder.contact.setText(list.get(i).getContact());
        myViewHolder.amount.setText(String.format("%s %s", context.getString(R.string.currency), list.get(i).getCost()));
        myViewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewDetailsActivity.class);
            intent.putExtra("order", list.get(i));
            intent.putExtra("isToday", isToday);
            context.startActivity(intent);
        });

        if (isToday) {
            if (list.get(i).getOrderDateTime()!=null && !list.get(i).getOrderDateTime().trim().isEmpty()) {
                myViewHolder.datetime.setText(String.format("%s", list.get(i).getOrderDateTime()));
            }

        } else {

            myViewHolder.datetime.setText(R.string.delivered);
            myViewHolder.datetime.setBackground(context.getDrawable(R.drawable.bg_green_filled));
            myViewHolder.datetime.setTextColor(Color.WHITE);
        }
        if (!list.get(i).getAddress().trim().isEmpty()) {
            String address = list.get(i).getAddress().trim();
            myViewHolder.tvAddress.setText(address);
        }
    }

    @Override
    public int getItemCount() {
//        return 1;
        return list==null?0:list.size();
    }


    public void setList(List<My_order_model> my_order_modelList) {
        this.list = my_order_modelList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_no, tv_recivername, contact, datetime, tvAddress, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_recivername = itemView.findViewById(R.id.tv_recivername);
            contact = itemView.findViewById(R.id.contact);
            datetime = itemView.findViewById(R.id.datetime);
            tvAddress = itemView.findViewById(R.id.tv_address);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
