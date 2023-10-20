package com.app.treatEasy.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistoryRes {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("doctor_id")
        @Expose
        private String doctorId;
        @SerializedName("booking_id")
        @Expose
        private String bookingId;
        @SerializedName("payment_for")
        @Expose
        private String paymentFor;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("txn_on")
        @Expose
        private String txnOn;
        @SerializedName("client_name")
        @Expose
        private Object clientName;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("surgery_package_name")
        @Expose
        private String surgeryPackageName;
        @SerializedName("payment_for_display")
        @Expose
        private String paymentForDisplay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getPaymentFor() {
            return paymentFor;
        }

        public void setPaymentFor(String paymentFor) {
            this.paymentFor = paymentFor;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTxnOn() {
            return txnOn;
        }

        public void setTxnOn(String txnOn) {
            this.txnOn = txnOn;
        }

        public Object getClientName() {
            return clientName;
        }

        public void setClientName(Object clientName) {
            this.clientName = clientName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getSurgeryPackageName() {
            return surgeryPackageName;
        }

        public void setSurgeryPackageName(String surgeryPackageName) {
            this.surgeryPackageName = surgeryPackageName;
        }

        public String getPaymentForDisplay() {
            return paymentForDisplay;
        }

        public void setPaymentForDisplay(String paymentForDisplay) {
            this.paymentForDisplay = paymentForDisplay;
        }

    }
}
