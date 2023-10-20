package com.app.treatEasy.feature.packages.HospitalModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HospitalSendModel {
    @SerializedName("pkgid")
    @Expose
    private String pkgid;

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }
}
