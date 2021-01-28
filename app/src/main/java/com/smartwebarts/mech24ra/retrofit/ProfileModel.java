package com.smartwebarts.mech24ra.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("driving_license")
    @Expose
    private String drivingLicense;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("blocked")
    @Expose
    private String blocked;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("c_long")
    @Expose
    private String cLong;
    @SerializedName("c_lat")
    @Expose
    private String cLat;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("vehicle_image")
    @Expose
    private String vehicle_image;
    @SerializedName("gstimage")
    @Expose
    private String gstimage;
    @SerializedName("adhaarimage")
    @Expose
    private String adhaarimage;
    @SerializedName("mechanicimage")
    @Expose
    private String mechanicimage;

    @SerializedName("shop_name")
    @Expose
    private String shop_name;
    @SerializedName("shopimage")
    @Expose
    private String shopimage;

    private String GST;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCLong() {
        return cLong;
    }

    public void setCLong(String cLong) {
        this.cLong = cLong;
    }

    public String getCLat() {
        return cLat;
    }

    public void setCLat(String cLat) {
        this.cLat = cLat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getcLong() {
        return cLong;
    }

    public void setcLong(String cLong) {
        this.cLong = cLong;
    }

    public String getcLat() {
        return cLat;
    }

    public void setcLat(String cLat) {
        this.cLat = cLat;
    }

    public String getVehicle_image() {
        return vehicle_image;
    }

    public void setVehicle_image(String vehicle_image) {
        this.vehicle_image = vehicle_image;
    }

    public String getGstimage() {
        return gstimage;
    }

    public void setGstimage(String gstimage) {
        this.gstimage = gstimage;
    }

    public String getAdhaarimage() {
        return adhaarimage;
    }

    public void setAdhaarimage(String adhaarimage) {
        this.adhaarimage = adhaarimage;
    }

    public String getMechanicimage() {
        return mechanicimage;
    }

    public void setMechanicimage(String mechanicimage) {
        this.mechanicimage = mechanicimage;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShopimage() {
        return shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }
}