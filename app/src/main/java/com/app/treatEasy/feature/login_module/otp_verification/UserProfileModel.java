package com.app.treatEasy.feature.login_module.otp_verification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel {
    @SerializedName("status_code")
    @Expose
    public String statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public UserDetailModel data;
    public String otp;

    public String mobileNumber;

    public boolean isOTPValid() {
        return otp.length() == 5;
    }

    public static class PostOtpModel{

        public String mobile;
        public String otp;
    }

    public static class PostGetProfile{
        public String userid;
    }

    public static class PostUpdateProfile{
        @SerializedName("userid")
        @Expose
        public String userid;
        @SerializedName("patient_name")
        @Expose
        public String patientName;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile_no")
        @Expose
        public String mobileNo;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("profession")
        @Expose
        public String profession;
        @SerializedName("health_status")
        @Expose
        public Integer healthStatus;
        @SerializedName("company_name")
        @Expose
        public String companyName;
        @SerializedName("policy_number")
        @Expose
        public String policyNumber;
        @SerializedName("gsm_token")
        @Expose
        public String gsmToken;

        @SerializedName("pincode")
        @Expose
        public String pincode;
    }

}