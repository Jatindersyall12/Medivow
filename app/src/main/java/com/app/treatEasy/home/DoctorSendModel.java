package com.app.treatEasy.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorSendModel {

    @SerializedName("userid")
    @Expose
    private String userid;

    @SerializedName("city_id")
    @Expose
    private String city_id;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

}
