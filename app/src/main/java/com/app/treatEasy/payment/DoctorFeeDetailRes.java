package com.app.treatEasy.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorFeeDetailRes {

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

        @SerializedName("doctor_id")
        @Expose
        private String doctorId;
        @SerializedName("fee")
        @Expose
        private String fee;
        @SerializedName("discounted_fee")
        @Expose
        private String discountedFee;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("specialities")
        @Expose
        private Object specialities;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("reviews")
        @Expose
        private Integer reviews;

        public String getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(String doctorId) {
            this.doctorId = doctorId;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getDiscountedFee() {
            return discountedFee;
        }

        public void setDiscountedFee(String discountedFee) {
            this.discountedFee = discountedFee;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public Object getSpecialities() {
            return specialities;
        }

        public void setSpecialities(Object specialities) {
            this.specialities = specialities;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getReviews() {
            return reviews;
        }

        public void setReviews(Integer reviews) {
            this.reviews = reviews;
        }

    }
}
