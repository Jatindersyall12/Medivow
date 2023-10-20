package com.app.treatEasy.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSend {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("search")
    @Expose
    private String search;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
