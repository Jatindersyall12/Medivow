package com.app.treatEasy.doctorscat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsListSend {

    @SerializedName("cateid")
    @Expose
    private String cateid;

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }
}
