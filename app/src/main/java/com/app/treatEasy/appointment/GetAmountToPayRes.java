package com.app.treatEasy.appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAmountToPayRes {

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
        public String getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            this.doctor_id = doctor_id;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getDiscounted_fee() {
            return discounted_fee;
        }

        public void setDiscounted_fee(String discounted_fee) {
            this.discounted_fee = discounted_fee;
        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getReviews() {
            return reviews;
        }

        public void setReviews(int reviews) {
            this.reviews = reviews;
        }

        public String getConvenience_fee() {
            return convenience_fee;
        }

        public void setConvenience_fee(String convenience_fee) {
            this.convenience_fee = convenience_fee;
        }

        public String doctor_id;
        public String fee;
        public String discounted_fee;
        public String doctor_name;
        public String specialities;
        public String profile_image;
        public int rating;
        public int reviews;
        public String convenience_fee;
    }
}
