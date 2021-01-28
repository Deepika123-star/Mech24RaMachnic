package com.smartwebarts.mech24ra.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status_number")
    @Expose
    private Integer statusNumber;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(Integer statusNumber) {
        this.statusNumber = statusNumber;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public void setProfile(ProfileModel profile) {
        this.profile = new Profile(profile);
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Profile {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("shop_name")
        @Expose
        private String shopName;
        @SerializedName("GST")
        @Expose
        private String gST;
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
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("creation_date")
        @Expose
        private String creationDate;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("gstimage")
        @Expose
        private String gstimage;
        @SerializedName("shopimage")
        @Expose
        private String shopimage;
        @SerializedName("adhaarimage")
        @Expose
        private String adhaarimage;
        @SerializedName("vehicle_image")
        @Expose
        private String vehicle_image;
        @SerializedName("mechanicimage")
        @Expose
        private String mechanicimage;

        @SerializedName("vehicle_type")
        @Expose
        private String vehicleType;
        @SerializedName("driving_license")
        @Expose
        private String drivingLicense;

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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getGST() {
            return gST;
        }

        public void setGST(String gST) {
            this.gST = gST;
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

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getGstimage() {
            return gstimage;
        }

        public void setGstimage(String gstimage) {
            this.gstimage = gstimage;
        }

        public String getShopimage() {
            return shopimage;
        }

        public void setShopimage(String shopimage) {
            this.shopimage = shopimage;
        }

        public String getAdhaarimage() {
            return adhaarimage;
        }

        public void setAdhaarimage(String adhaarimage) {
            this.adhaarimage = adhaarimage;
        }

        public String getgST() {
            return gST;
        }

        public void setgST(String gST) {
            this.gST = gST;
        }

        public String getVehicle_image() {
            return vehicle_image;
        }

        public void setVehicle_image(String vehicle_image) {
            this.vehicle_image = vehicle_image;
        }

        public String getMechanicimage() {
            return mechanicimage;
        }

        public void setMechanicimage(String mechanicimage) {
            this.mechanicimage = mechanicimage;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getDrivingLicense() {
            return drivingLicense;
        }

        public void setDrivingLicense(String drivingLicense) {
            this.drivingLicense = drivingLicense;
        }

        public Profile(ProfileModel profileModel) {
            this.id = profileModel.getId();
            this.name = profileModel.getName();
            this.shopName = profileModel.getShop_name();
            this.gST = profileModel.getGST();
            this.contact = profileModel.getContact();
            this.fullAddress = profileModel.getFullAddress();
            this.city = profileModel.getCity();
            this.pin = profileModel.getPin();
            this.userid = profileModel.getUserid();
            this.email = profileModel.getEmail();
            this.password = profileModel.getPassword();
            this.apiKey = profileModel.getApiKey();
            this.role = profileModel.getRole();
            this.blocked = profileModel.getBlocked();
            this.image = profileModel.getImage();
            this.about = profileModel.getAbout();
            this.status = profileModel.getStatus();
            this.creationDate = profileModel.getCreationDate();
            this.accessToken = profileModel.getAccessToken();
            this.gstimage = profileModel.getGstimage();
            this.shopimage = profileModel.getShopimage();
            this.adhaarimage = profileModel.getAdhaarimage();
            this.vehicle_image = profileModel.getVehicle_image();
            this.mechanicimage = profileModel.getMechanicimage();
            this.vehicleType = profileModel.getVehicleType();
            this.drivingLicense = profileModel.getDrivingLicense();
        }
    }
}
