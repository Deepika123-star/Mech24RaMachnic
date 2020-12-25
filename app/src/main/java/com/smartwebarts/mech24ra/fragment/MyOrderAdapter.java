package com.smartwebarts.mech24ra.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailsActivity.class);
                intent.putExtra("order", list.get(i));
                intent.putExtra("isToday", isToday);
                context.startActivity(intent);
            }
        });

        if (isToday) {
            if (list.get(i).getOrderDateTime()!=null && !list.get(i).getOrderDateTime().trim().isEmpty()) {
                myViewHolder.datetime.setText(String.format("%s %s", list.get(i).getOrderDateTime(), list.get(i).getOrderDateTime()));
            }

        } else {

            myViewHolder.datetime.setText("Delivered");
            myViewHolder.datetime.setBackground(context.getDrawable(R.drawable.bg_green_filled));
            myViewHolder.datetime.setTextColor(Color.WHITE);
//            myViewHolder.tvAddress.setVisibility(View.GONE);
        }
        if (list.get(i).getLandmark()!=null && !list.get(i).getAddress().trim().isEmpty()) {
            String address = list.get(i).getAddress().trim();
            String landmark = list.get(i).getLandmark().trim();
            myViewHolder.tvAddress.setText(address+"\n"+landmark);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_no, tv_recivername, contact, datetime, tvAddress;
//        TextView getDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_recivername = itemView.findViewById(R.id.tv_recivername);
            contact = itemView.findViewById(R.id.contact);
            datetime = itemView.findViewById(R.id.datetime);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
