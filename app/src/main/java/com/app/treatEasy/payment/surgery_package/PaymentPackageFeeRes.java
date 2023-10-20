package com.app.treatEasy.payment.surgery_package;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentPackageFeeRes {

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

        @SerializedName("booking_id")
        @Expose
        private String bookingId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("amount_paid")
        @Expose
        private String amountPaid;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("partial_amount")
        @Expose
        private Integer partialAmount;
        @SerializedName("partial_payment_option_to_show")
        @Expose
        private Integer partialPaymentOptionToShow;
        @SerializedName("full_payment_option_to_show")
        @Expose
        private Integer fullPaymentOptionToShow;
        @SerializedName("full_amount")
        @Expose
        private String fullAmount;

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(String amountPaid) {
            this.amountPaid = amountPaid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getPartialAmount() {
            return partialAmount;
        }

        public void setPartialAmount(Integer partialAmount) {
            this.partialAmount = partialAmount;
        }

        public Integer getPartialPaymentOptionToShow() {
            return partialPaymentOptionToShow;
        }

        public void setPartialPaymentOptionToShow(Integer partialPaymentOptionToShow) {
            this.partialPaymentOptionToShow = partialPaymentOptionToShow;
        }

        public Integer getFullPaymentOptionToShow() {
            return fullPaymentOptionToShow;
        }

        public void setFullPaymentOptionToShow(Integer fullPaymentOptionToShow) {
            this.fullPaymentOptionToShow = fullPaymentOptionToShow;
        }

        public String getFullAmount() {
            return fullAmount;
        }

        public void setFullAmount(String fullAmount) {
            this.fullAmount = fullAmount;
        }


    }
}
