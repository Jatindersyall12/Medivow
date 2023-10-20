package com.app.treatEasy.feature.packages.HospitalModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsSend {
    @SerializedName("pkgid")
    @Expose
    private String pkgid;
    @SerializedName("clientid")
    @Expose
    private String clientid;

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

}
