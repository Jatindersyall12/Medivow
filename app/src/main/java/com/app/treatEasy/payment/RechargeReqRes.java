package com.app.treatEasy.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeReqRes {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("recharge_id")
        @Expose
        private Integer rechargeId;

        public Integer getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(Integer rechargeId) {
            this.rechargeId = rechargeId;
        }

    }
}
