package com.app.treatEasy.feature.login_module.signup_newuser;

import android.text.TextUtils;
import android.util.Patterns;

import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;

public class SignUpModel {

    public String userName = "";
    public String phone = "";
    public String emailAddress = "";
    public String dateOfBirth = "";

    public UserDetailModel data;


    public boolean isUserNameValid() {
        return !TextUtils.isEmpty(userName);
    }

    public boolean isValidPhoneNumber() {
        return phone.length() < 10;
    }

    public boolean isEmailValid() {
        return !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }
    public static class PostSignUpModel {
        public String fullName;
        public String mobile;
        public String dob;
        public String gsm_token;
        public String pincode;
    }
}