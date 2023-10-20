package com.app.treatEasy.state;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitySendModel {
    @SerializedName("stateid")
    @Expose
    private String stateid;
    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

}
