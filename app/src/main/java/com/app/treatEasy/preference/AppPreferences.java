package com.app.treatEasy.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*Shared mSharedPreferences.. */
public class AppPreferences implements PreferenceAttribute {

    public static AppPreferences instance;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;


    private AppPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static AppPreferences getPreferenceInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context);
            return instance;
        }
        return instance;
    }


    /**
     * Sets user login status
     *
     * @param isLogin
     */
    public void setUserLogin(boolean isLogin) {
        mEditor.putBoolean(IS_USER_LOGIN, isLogin);
        mEditor.commit();
    }

    public boolean getIsUserLogin() {
        return mSharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }

    /**
     * Sets user type
     *
     * @param userId
     */
    public void setUserId(String userId) {
        mEditor.putString(USER_ID, userId);
        mEditor.commit();
    }

    public void setMobile(String mobile) {
        mEditor.putString(MOBILE, mobile);
        mEditor.commit();
    }

    public void setUserName(String userName) {
        mEditor.putString(USER_NAME, userName);
        mEditor.commit();
    }

    public void setUserImage(String userImage) {
        mEditor.putString(USER_IMAGE, userImage);
        mEditor.commit();
    }

    public void setUserLocation(String userLocation) {
        mEditor.putString(USER_LOCATION, userLocation);
        mEditor.commit();
    }

    public void setCityNAme(String userLocation) {
        mEditor.putString(CITY_NAME, userLocation);
        mEditor.commit();
    }

    public String getUserName() {
        return mSharedPreferences.getString(USER_NAME, null);
    }
    public String getUserImage() {
        return mSharedPreferences.getString(USER_IMAGE, null);
    }
    public String getUserLocation() {
        return mSharedPreferences.getString(USER_LOCATION, null);
    }
    public String getUserAmount() {
        return mSharedPreferences.getString(AMOUNT, null);
    }

    public String getUserCity() {
        return mSharedPreferences.getString(CITY_NAME, null);
    }

    public String getUserId() {
        return mSharedPreferences.getString(USER_ID, null);
    }
    public void setPayment(String payment) {
        mEditor.putString(PAYMENT, payment);
        mEditor.commit();
    }


    public String getPayment() {
        return mSharedPreferences.getString(PAYMENT, null);
    }
    public String getMobile() {
        return mSharedPreferences.getString(MOBILE, null);
    }
    public void clearPreferenceData() {
        boolean firstTime = getFirstTime();
        mEditor.clear();
        mEditor.apply();
        setFirstTime(firstTime);
    }

    /**
     * Sets entity values
     *
     * @param fcmToken
     */
    public void setFCMToken(String fcmToken) {
        mEditor.putString(FCM_TOKEN, fcmToken);
        mEditor.commit();
    }
    public String getFCMToken() {
        return mSharedPreferences.getString(FCM_TOKEN, "123456");
    }
    /**
     * Sets user login status
     *
     * @param isLogin
     */
    public void setFirstTime(boolean isLogin) {
        mEditor.putBoolean(FIRST_TIME_USER, isLogin);
        mEditor.commit();
    }
    public boolean getFirstTime() {
        return mSharedPreferences.getBoolean(FIRST_TIME_USER, false);
    }
    /**
     * Sets User Login Data
     *
     * @param loginData
     */
    public void setUserMeData(String loginData) {
        mEditor.putString(LOGIN_DATA, loginData);
        mEditor.commit();
    }
    public String getUserMeData() {
        return mSharedPreferences.getString(LOGIN_DATA, null);
    }

}
