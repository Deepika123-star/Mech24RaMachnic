package com.smartwebarts.mech24ra.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartwebarts.mech24ra.Fonts.LatoBLack;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.adapter.OrderDetailAdapter;
import com.smartwebarts.mech24ra.retrofit.DeliveryProductDetails;
import com.smartwebarts.mech24ra.retrofit.My_order_model;
import com.smartwebarts.mech24ra.retrofit.OrderDetailModel;
import com.smartwebarts.mech24ra.retrofit.OrderedResponse;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    My_order_model my_order_model;
    RecyclerView recyclerView;
    LatoBLack order_id, tv_recivername, contact, fulladdress, landmark, tv_order_price, paymentmethod;
    boolean isToday;
    Button status;
    ImageView imageView;
    private List<OrderDetailModel> list = new ArrayList<>();
    private OrderDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        order_id = findViewById(R.id.order_id);
        tv_recivername = findViewById(R.id.tv_recivername);
        fulladdress = findViewById(R.id.fulladdress);
        landmark = findViewById(R.id.landmark);
        contact = findViewById(R.id.contact);
        tv_order_price = findViewById(R.id.tv_order_price);
        paymentmethod = findViewById(R.id.paymentmethod);
        status = findViewById(R.id.status);
        imageView = findViewById(R.id.cancelorder);
        adapter = new OrderDetailAdapter(ViewDetailsActivity.this, list);
        recyclerView.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        my_order_model = (My_order_model) getIntent().getSerializableExtra("order");
        isToday = getIntent().getBooleanExtra("isToday", false);

        if (isToday) {
            status.setText("Mark Delivered");
        } else {
            status.setText("Delivered");
            status.setEnabled(false);
            imageView.setEnabled(false);
            imageView.setVisibility(View.GONE);
            recyclerView.setEnabled(false);
        }

        order_id.setText(my_order_model.getOrderId());
        tv_recivername.setText(my_order_model.getName());
        contact.setText(my_order_model.getContact());
        paymentmethod.setText(my_order_model.getPaymentmethod());
        landmark.setText(my_order_model.getLandmark());
fulladdress.setText(my_order_model.getAddress());

        getOrderDetails();
    }

    private void getOrderDetails() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.OrderDetails(this,"1", new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    Type listType = new TypeToken<ArrayList<OrderDetailModel>>() {
                    }.getType();
                    list = new Gson().fromJson(message, listType);
                  /*  fulladdress.setText("" + list.get(0).getAddress());
                    landmark.setText("" + list.get(0).getLandmark());
                    paymentmethod.setText("" + list.get(0).getPaymentmethod());*/
                    adapter.setList(list);
                    updatePrice(list);
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(ViewDetailsActivity.this, getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePrice(List<OrderDetailModel> list) {
        tv_order_price.setText(String.format("%s %s", getString(R.string.currency), list.get(0).getCost()));
    }

    public void openMap(View view) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + fulladdress.getText()));
        startActivity(intent);

//        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
//            Intent intent = new Intent(this, WebMapActivity.class);
//            String url = "https://www.google.com/maps/dir/"+ "/" +fulladdress.getText();
//            intent.putExtra(WebMapActivity.STR_URL, url);
//            startActivity(intent);
//        } else {
//            UtilMethods.INSTANCE.internetNotAvailableMessage(this);
//        }
    }


    public void addSignature(View view) {
        Intent intent = new Intent(this, AddSignatureActivity.class);
        intent.putExtra(AddSignatureActivity.ID, my_order_model);
        intent.putExtra("order_id",my_order_model.getOrder_request_id());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void cancelorder(View view) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Cancel Order");
        dialog.setMessage("Do you really want to cancel this order ?");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(ViewDetailsActivity.this)) {

                    UtilMethods.INSTANCE.returnProductByOrderId(ViewDetailsActivity.this, order_id.getText().toString().trim(),
                            new mCallBackResponse() {
                                @Override
                                public void success(String from, String message) {
                                    SweetAlertDialog dialog = new SweetAlertDialog(ViewDetailsActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                    dialog.setTitleText("Order Cancel!");
                                    dialog.setContentText("Order Cancelled Successfully");
                                    dialog.setCancelable(false);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(ViewDetailsActivity.this, MainActivity.class));
                                            finishAffinity();
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }

                                @Override
                                public void fail(String from) {

                                }
                            });
                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(ViewDetailsActivity.this);
                }
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openDialog(View view) {
        Dialog alertDialog = new Dialog(ViewDetailsActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setContentView(R.layout.order_layout);
        alertDialog.setTitle("Add User");
        Button buttonok = alertDialog.findViewById(R.id.userOk);
        Button buttoncancle = alertDialog.findViewById(R.id.usercancle);
        EditText name = alertDialog.findViewById(R.id.et_name);
        EditText price = alertDialog.findViewById(R.id.et_login_pass);
        alertDialog.show();
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Enter your Services Name");
                    return;
                } else if (price.getText().toString().isEmpty()) {
                    price.setError("Enter your Services Price");
                    return;
                }

                alertDialog.dismiss();
                addCheckOut(name.getText().toString(), price.getText().toString());

            }

        });

        buttoncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void addCheckOut(String sName, String sPrice) {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        DeliveryProductDetails product = new DeliveryProductDetails(list.get(0).getOrderId(), "1",
                "0",
                sPrice, sName, "1", "NA", "NA", my_order_model.getContact(), my_order_model.getOrderId(), "1", "COD", my_order_model.getAddress(), my_order_model.getLandmark(),"", date, "");


        UtilMethods.INSTANCE.order(this, product, list.get(0).getCost(), "0", "0", new mCallBackResponse() {
            @Override
            public void success(String from, String message) {
//                        Toast.makeText(DeliveryOptionActivity.this, "Ordered", Toast.LENGTH_SHORT).show();

                OrderedResponse response = new Gson().fromJson(message, OrderedResponse.class);
                if (response != null && response.getMessage() != null && response.getMessage().equalsIgnoreCase("success")) {
                    showSuccessMessage(response);
                } else {
                    showErrorMessage(response);
                }
            }

            @Override
            public void fail(String from) {
                showFailMessage(null);
            }
        });
    }

    private void showSuccessMessage(OrderedResponse response) {
        SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sad.setTitleText("Order Completed");
        sad.setCustomImage(R.drawable.successimage);
        sad.setCancelable(false);
        sad.setCanceledOnTouchOutside(false);
        sad.setContentText("" + response.getMessage());
        sad.setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.dismissWithAnimation();
                getOrderDetails();
            }
        });
        sad.show();
    }

    private void showErrorMessage(OrderedResponse response) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Retry")
                .setContentText("" + response.getMessage())
                .show();
    }

    private void showFailMessage(OrderedResponse response) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Failed")
                .show();
    }

}


