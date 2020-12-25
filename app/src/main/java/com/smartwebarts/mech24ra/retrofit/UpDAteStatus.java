package com.smartwebarts.mech24ra.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpDAteStatus {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("recentinsertedid")
    @Expose
    private String recentinsertedid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecentinsertedid() {
        return recentinsertedid;
    }

    public void setRecentinsertedid(String recentinsertedid) {
        this.recentinsertedid = recentinsertedid;
    }

}
