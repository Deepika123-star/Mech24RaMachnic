package com.smartwebarts.mech24ra.retrofit;

public class VersionModel {

    String id;
    String vcode;
    String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVcode() {
        return Integer.parseInt(vcode);
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
