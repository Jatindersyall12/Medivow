package com.app.treatEasy.banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerSendModel {
    @SerializedName("location")
    @Expose
    private String location;

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

}
