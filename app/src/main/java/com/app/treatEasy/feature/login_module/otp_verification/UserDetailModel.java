package com.app.treatEasy.feature.login_module.otp_verification;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailModel {
    @SerializedName("uid")
    @Expose
    public String uid;
    @SerializedName("patient_name")
    @Expose
    public String patientName;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("mobile_no")
    @Expose
    public String mobileNo;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("service_id")
    @Expose
    public String serviceId;
    @SerializedName("city_id")
    @Expose
    public String cityId;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("wallet_amount")
    @Expose
    public String walletAmount;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("govt_id")
    @Expose
    public String govtId;
    @SerializedName("profession")
    @Expose
    public String profession;
    @SerializedName("health_status")
    @Expose
    public String healthStatus;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    @SerializedName("policy_number")
    @Expose
    public String policyNumber;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("otp")
    @Expose
    public String otp;

    public String userName = "";
    public String phone = "";
    public String emailAddress = "";
    public String dateOfBirth = "";
    public String pincode = "";

    public boolean isUserNameValid() {
        return !TextUtils.isEmpty(userName);
    }

    public boolean isValidPhoneNumber() {
        return phone.length() < 10;
    }

    public boolean isEmailValid() {
        return !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }
}
