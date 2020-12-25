package com.smartwebarts.mech24ra.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.retrofit.My_order_model;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Order_history extends Fragment {

RecyclerView recyclerView;
    private AppSharedPreferences sharedPreferences;
    private String get_id;

    public Order_history() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (container != null) {
            container.removeAllViews();
        }

        View view= inflater.inflate(R.layout.fragment_order_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        sharedPreferences = new AppSharedPreferences();

        get_id = sharedPreferences.getLoginUserLoginId(getActivity());
        Log.e("get_id",get_id);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            UtilMethods.INSTANCE.OrderHistory(getActivity(), sharedPreferences.getLoginUserLoginId(getActivity()), new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    Type listType = new TypeToken<ArrayList<My_order_model>>(){}.getType();
                    List<My_order_model> my_order_modelList = new Gson().fromJson(message, listType);
                    MyOrderAdapter myPendingOrderAdapter = new MyOrderAdapter(getActivity(), my_order_modelList, false);
                    recyclerView.setAdapter(myPendingOrderAdapter);
//                    myPendingOrderAdapter.notifyDataSetChanged();
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Check your Connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
