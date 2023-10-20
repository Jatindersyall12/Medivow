package com.app.treatEasy.feature.login_module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("value")
    @Expose
    private String value;
}
