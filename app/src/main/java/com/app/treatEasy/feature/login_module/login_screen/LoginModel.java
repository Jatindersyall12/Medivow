package com.app.treatEasy.feature.login_module.login_screen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status")
    @Expose
    public int mServerStatus;
    @SerializedName("message")
    @Expose
    public String mMessage;
    public String otp;

    public String mobileNumber;


    public boolean isPhoneNoValid() {
        return mobileNumber.length() != 10;
    }

    public static class PostLoginModel{
        public String mobile;
    }

}