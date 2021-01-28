package com.smartwebarts.mech24ra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.activity.ViewDetailsActivity;
import com.smartwebarts.mech24ra.retrofit.OrderDetailModel;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.smartwebarts.mech24ra.retrofit.APIClient.BASE_URL;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    Context context;
    List<OrderDetailModel> list;

    public OrderDetailAdapter(Context context, List<OrderDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_order_detail_rv, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        OrderDetailModel detail = list.get(i);
        myViewHolder.price.setText(context.getString(R.string.currency)+" "+detail.getCost());
        myViewHolder.name.setText(detail.getBrand());

        Glide.with(context)
                .load(BASE_URL + "img/vehicle_model/"+ detail.getImg())
                .into(myViewHolder.productImage);
        myViewHolder.Qty.setText(detail.getQuantity());
        myViewHolder.unit.setText(detail.getModel());
        myViewHolder.status.setText(detail.getBrand());
        myViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {

                    UtilMethods.INSTANCE.returnProductByProductID(context, ""+detail.getOrderId(), new mCallBackResponse() {
                        @Override
                        public void success(String from, String message) {
                            SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                            dialog.setTitleText("Order Cancel!");
                            dialog.setContentText("Order Cancelled Successfully");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    ((ViewDetailsActivity) context).onBackPressed();
                                }
                            });
                            dialog.show();
                        }

                        @Override
                        public void fail(String from) {

                        }
                    });
                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<OrderDetailModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price, Qty, unit, name, status;
        ImageView productImage, cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unit = (TextView) itemView.findViewById(R.id.unit);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            Qty = (TextView) itemView.findViewById(R.id.Qty);
            status = (TextView) itemView.findViewById(R.id.status);

            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            cancel = (ImageView) itemView.findViewById(R.id.cancel);

        }
    }
}
