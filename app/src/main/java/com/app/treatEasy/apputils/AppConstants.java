package com.app.treatEasy.apputils;

/**
 * Created by Vinod Kumar (Aug 2019).
 */
public interface AppConstants {

    int SPLASH_DISPLAY_TIME = 3000;

    String KEY_BUNDLE_DATA = "bundle_data";
    String SPAN_SPLIT = "?";
    String MOBILE_NUMBER = "mobileNumber";

    String PASSWORD_8_DIGIT = "is8DigitPassword";
    String PASSWORD_HAVE_CAPITAL_NUMBER = "isCapitalLetter";
    String PASSWORD_HAVE_SMALL_NUMBER = "isSmallLetter";
    String PASSWORD_HAVE_USERNAME = "isPasswordHaveUsername";
    String PASSWORD_HAVE_1_NUMERIC = "isNumeric";
    String TOTAL_AMOUNT = "totalAmount";
    String PACKAGE_ID = "packageId";
    String PACKAGE_Cat_ID = "packageCatId";
    String PACKAGE_NAME = "packageName";
    String PACKAGE_IMAGE = "packageImage";
    String PACKAGE_DESCRIPTION = "packageDescription";
    String PACKAGE_FEE = "packageFee";
    String PACKAGE_DISCOUNT_FEE = "packageDiscountFee";

    int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 0x03;

    String USER_ID = "userId";
    String OTP = "otp";
    int UPDATE_PROFILE_REQUEST_CODE = 1001;
    int ADD_MONEY_REQUEST_CODE = 1002;

    String[] GENDER = { "Male", "Female", "Others"};
    String[] Specialties = { "Doctor", "Hospital", "Packages","Service"};

    String[] PROFESSION = { "Select Profession", "Doctor", "Engineer", "Software Engineer", "Teacher"};

    String[] RELATIONS = {"Self","Husband", "Father", "Mother", "Brother", "Sister", "Wife", "Daughter", "Son"};

}
