package com.smartwebarts.mech24ra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.smartwebarts.mech24ra.activity.LogInActivity;
import com.smartwebarts.mech24ra.retrofit.LoginResponse;


public class AppSharedPreferences {

//    INSTANCE;

    private static final String PREF_NAME = "SMS";
    private static final String loginDetails = "loginDetails";

    SharedPreferences preferences;

    public SharedPreferences getPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public String getLoginDetails(Context context) {
        return getPreferences(context).getString(loginDetails, "");
    }

    public void setLoginDetails(Context context, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(loginDetails, value);
        editor.apply();
    }

    public String getLoginUserName(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null){
                return data.getProfile().getName();
            }
        }
        return "";
    }



    public String getLoginUserLoginId(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null){
                return data.getProfile().getId();
            }
        }
        return "";
    }
    public String getLoginUserApiKey(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null){
                return data.getProfile().getApiKey();
            }
        }
        return "";
    }
    public void logout(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
