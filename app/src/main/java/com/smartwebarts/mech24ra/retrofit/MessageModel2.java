package com.smartwebarts.mech24ra.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageModel2 {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName(value = "message", alternate = "error")
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
}
