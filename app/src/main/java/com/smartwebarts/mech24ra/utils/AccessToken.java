package com.smartwebarts.mech24ra.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessToken {

    private String access_token;
    private static SharedPreferences sharedpreferences;

    public static SharedPreferences getPreferences(Context context) {
        if (sharedpreferences==null)
            sharedpreferences = context.getSharedPreferences("TOKEN_PREFERENCES", Context.MODE_PRIVATE);
        return sharedpreferences;
    }

    public String getAccess_token(Context context) {
        access_token = getPreferences(context).getString("access_token", "");
        return access_token;
    }

    public void setAccess_token(Context context, String access_token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("access_token", access_token);
        editor.apply();
        this.access_token = access_token;
    }
}
