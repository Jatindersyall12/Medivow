package com.app.treatEasy.feature.packages.doctor_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsDetailModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("client_name")
    @Expose
    public String clientName;
    @SerializedName("doctorfee")
    @Expose
    public String doctorfee;
    @SerializedName("discounted_fee")
    @Expose
    public String discountedFee;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("city_name")
    @Expose
    public String cityName;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("services")
    @Expose
    public String services;
    @SerializedName("status")
    @Expose
    public String status;

    public static class PostDoctorDetailModel {
        public String doctorsid;
        public String pincode;
    }

}
