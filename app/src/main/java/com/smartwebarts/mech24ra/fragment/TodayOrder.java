package com.smartwebarts.mech24ra.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class TodayOrder extends Fragment {
    private RecyclerView rv_myorder;
    List<My_order_model> my_order_modelList = new ArrayList<>();
    TextView status;
    String get_id;
    AppSharedPreferences sharedPreferences;
    SwipeRefreshLayout swipe;
    private MyOrderAdapter myPendingOrderAdapter;
    private SwitchCompat sw;

    public TodayOrder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.frag_today, container, false);

        sharedPreferences = new AppSharedPreferences();

        get_id = sharedPreferences.getLoginUserLoginId(getActivity());
//        Log.e("get_id",get_id);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        status = view.findViewById(R.id.status);
        rv_myorder = view.findViewById(R.id.rv_myorder);
        swipe = view.findViewById(R.id.swipe);
        sw = (SwitchCompat) view.findViewById(R.id.switch_but);

        myPendingOrderAdapter = new MyOrderAdapter(getActivity(), my_order_modelList,true);
        rv_myorder.setAdapter(myPendingOrderAdapter);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setStatus("active");
                } else {
                    setStatus("inactive");

                }
            }
        });

        getList();
        getStatus();

        return view;
    }

    private void setStatus(String status) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            UtilMethods.INSTANCE.setActive(requireActivity(), sharedPreferences.getLoginUserLoginId(getActivity()), status, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    TodayOrder.this.status.setText(message);
                    if (message.equalsIgnoreCase("active")) {
                        sw.setChecked(true);
                    } else {
                        sw.setChecked(false);
                    }
                }

                @Override
                public void fail(String from) {

                }
            });

        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
        }
    }


    private void getStatus() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            UtilMethods.INSTANCE.getActive(requireActivity(), sharedPreferences.getLoginUserLoginId(getActivity()), new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    TodayOrder.this.status.setText(message);
                    if (message.equalsIgnoreCase("active")) {
                        sw.setChecked(true);
                    } else {
                        sw.setChecked(false);
                    }
                }

                @Override
                public void fail(String from) {

                }
            });

        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
        }
    }

    public void getList() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(requireActivity())) {
            UtilMethods.INSTANCE.TodayOrder(requireActivity(), sharedPreferences.getLoginUserLoginId(getActivity()), new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    Type listType = new TypeToken<ArrayList<My_order_model>>(){}.getType();
                    List<My_order_model> my_order_modelList = new Gson().fromJson(message, listType);
                    TodayOrder.this.my_order_modelList = my_order_modelList;
                    myPendingOrderAdapter.setList(TodayOrder.this.my_order_modelList);
                    swipe.setRefreshing(false);
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                }
            });

        } else {
            Toast.makeText(getActivity(), "Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }
}