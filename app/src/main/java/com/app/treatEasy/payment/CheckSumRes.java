package com.app.treatEasy.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckSumRes {

    @SerializedName("checksum")
    @Expose
    private String checksum;
    @SerializedName("params")
    @Expose
    private Params params;

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public class Params {

        @SerializedName("MID")
        @Expose
        private String mid;
        @SerializedName("ORDER_ID")
        @Expose
        private String orderId;
        @SerializedName("CUST_ID")
        @Expose
        private String custId;
        @SerializedName("INDUSTRY_TYPE_ID")
        @Expose
        private String industryTypeId;
        @SerializedName("CHANNEL_ID")
        @Expose
        private String channelId;
        @SerializedName("TXN_AMOUNT")
        @Expose
        private String txnAmount;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @SerializedName("EMAIL")
        @Expose
        private String email;

        @SerializedName("MOBILE_NO")
        @Expose
        private String mobileNo;
        @SerializedName("WEBSITE")
        @Expose
        private String website;
        @SerializedName("CALLBACK_URL")
        @Expose
        private String callbackUrl;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getIndustryTypeId() {
            return industryTypeId;
        }

        public void setIndustryTypeId(String industryTypeId) {
            this.industryTypeId = industryTypeId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getTxnAmount() {
            return txnAmount;
        }

        public void setTxnAmount(String txnAmount) {
            this.txnAmount = txnAmount;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public void setCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
        }

    }
}
