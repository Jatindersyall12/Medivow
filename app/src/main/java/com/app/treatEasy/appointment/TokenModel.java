package com.app.treatEasy.appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TokenModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("token_no")
        @Expose
        private Integer tokenNo;
        @SerializedName("is_available")
        @Expose
        private Integer isAvailable;

        @SerializedName("selectedStatus")
        @Expose
        private boolean selectedStatus = false;


        public Boolean getSelectedStatus() {
            return selectedStatus;
        }

        public void setSelectedStatus(Boolean status) {
            this.selectedStatus = status;
        }

        public Integer getTokenNo() {
            return tokenNo;
        }

        public void setTokenNo(Integer tokenNo) {
            this.tokenNo = tokenNo;
        }

        public Integer getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Integer isAvailable) {
            this.isAvailable = isAvailable;
        }

    }
}
