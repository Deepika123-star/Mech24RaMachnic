package com.smartwebarts.mech24ra.retrofit;

public class DeliveryProductDetails {

    private String id;
    private String qty;
    private String proId;
    private String amount;
    private String name;
    private String unit;
    private String unit_in;
    private String thumbnail;
    private String mobile;
    private String orderid;
    private String checkout;
    private String paymentmethod;
    private String address;
    private String landmark;
    private String pincode;
    private String userdate;
    private String usertime;

    public DeliveryProductDetails(String id, String qty, String proId, String amount, String name, String unit, String unit_in, String thumbnail, String mobile, String orderid, String checkout, String paymentmethod, String address, String landmark, String pincode, String userdate, String usertime) {
        this.id = id;
        this.qty = qty;
        this.proId = proId;
        this.amount = amount;
        this.name = name;
        this.unit = unit;
        this.unit_in = unit_in;
        this.thumbnail = thumbnail;
        this.mobile = mobile;
        this.orderid = orderid;
        this.checkout = checkout;
        this.paymentmethod = paymentmethod;
        this.address = address;
        this.landmark = landmark;
        this.pincode = pincode;
        this.userdate = userdate;
        this.usertime = usertime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_in() {
        return unit_in;
    }

    public void setUnit_in(String unit_in) {
        this.unit_in = unit_in;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getUserdate() {
        return userdate;
    }

    public void setUserdate(String userdate) {
        this.userdate = userdate;
    }

    public String getUsertime() {
        return usertime;
    }

    public void setUsertime(String usertime) {
        this.usertime = usertime;
    }
}
