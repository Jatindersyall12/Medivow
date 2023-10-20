package com.app.treatEasy.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("doctor_id")
        @Expose
        private String doctorId;
        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("amount_paid")
        @Expose
        private String amountPaid;
        @SerializedName("surgery_date")
        @Expose
        private String surgeryDate;
        @SerializedName("booking_status")
        @Expose
        private String bookingStatus;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("checkup_required")
        @Expose
        private String checkupRequired;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("patient_name")
        @Expose
        private String patientName;
        @SerializedName("member_name")
        @Expose
        private Object memberName;
        @SerializedName("client_name")
        @Expose
        private String clientName;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("partial_payment_option_to_show")
        @Expose
        private Integer partialPaymentOptionToShow;
        @SerializedName("full_payment_option_to_show")
        @Expose
        private Integer fullPaymentOptionToShow;
        @SerializedName("partial_amount")
        @Expose
        private Integer partialAmount;
        @SerializedName("full_amount")
        @Expose
        private Integer fullAmount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
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

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
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

        public String getSurgeryDate() {
            return surgeryDate;
        }

        public void setSurgeryDate(String surgeryDate) {
            this.surgeryDate = surgeryDate;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getCheckupRequired() {
            return checkupRequired;
        }

        public void setCheckupRequired(String checkupRequired) {
            this.checkupRequired = checkupRequired;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public Object getMemberName() {
            return memberName;
        }

        public void setMemberName(Object memberName) {
            this.memberName = memberName;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
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

        public Integer getPartialAmount() {
            return partialAmount;
        }

        public void setPartialAmount(Integer partialAmount) {
            this.partialAmount = partialAmount;
        }

        public Integer getFullAmount() {
            return fullAmount;
        }

        public void setFullAmount(Integer fullAmount) {
            this.fullAmount = fullAmount;
        }


    }
}
