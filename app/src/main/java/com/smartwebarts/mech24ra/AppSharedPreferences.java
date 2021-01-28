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
            try {
                LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
                if (data != null && data.getProfile() !=null) {
                    return data.getProfile().getName();
                } else {
                    return "";
                }
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }



    public String getLoginUserLoginId(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            try {
                LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
                if (data != null && data.getProfile()!=null){
                    return data.getProfile().getId();
                } else {
                    return "";
                }
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
    public String getLoginUserApiKey(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null && data.getProfile()!=null){
                return data.getProfile().getApiKey();
            } else {
                return "";
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

    public String getLoginUserVehicleImage(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getShopimage()!=null){
                return data.getProfile().getShopimage();
            }
        }
        return "";
    }

    public String getLoginUserLicense(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getDrivingLicense()!=null){
                return data.getProfile().getDrivingLicense();
            }
        }
        return "";
    }

    public String getLoginUserAdhaarImage(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getAdhaarimage()!=null){
                return data.getProfile().getAdhaarimage();
            }
        }
        return "";
    }

    public String getLoginUserMechanicImage(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getMechanicimage()!=null){
                return data.getProfile().getMechanicimage();
            }
        }
        return "";
    }

    public void setLoginUserStatus(Context context, String status){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null){
                data.getProfile().setStatus(status);
                setLoginDetails(context, new Gson().toJson(data));
            }
        }
    }

    public String getLoginUserAddress(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getFullAddress()!=null){
                return data.getProfile().getFullAddress();
            }
        }
        return "";
    }

    public String getLoginVehicle(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getVehicleType()!=null){
                return data.getProfile().getVehicleType();
            }
        }
        return "";
    }


    public String getLoginEmail(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getEmail()!=null){
                return data.getProfile().getEmail();
            }
        }
        return "";
    }

    public String getLoginMobile(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getContact()!=null){
                return data.getProfile().getContact();
            }
        }
        return "";
    }

    public String getLoginUserStatus(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getStatus()!=null){
                return data.getProfile().getStatus();
            }
        }
        return "";
    }

    public String getLoginUserImage(Context context){
        String temp = getLoginDetails(context);
        if (!temp.isEmpty())
        {
            LoginResponse data = new Gson().fromJson(getLoginDetails(context), LoginResponse.class);
            if (data != null  && data.getProfile() !=null && data.getProfile().getImage()!=null){
                return data.getProfile().getImage();
            }
        }
        return "";
    }
}
