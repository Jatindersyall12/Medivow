package com.app.treatEasy.profile.doctorprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorProfileRes {

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
    public class Hospital {

        @SerializedName("timing")
        @Expose
        private String timing;
        @SerializedName("days")
        @Expose
        private String days;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("client_name")
        @Expose
        private String clientName;

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }
    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        @SerializedName("client_id")
        @Expose
        private String client_id;

        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("specialities")
        @Expose
        private String specialities;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("reviews")
        @Expose
        private Integer reviews;
        @SerializedName("hospitals")
        @Expose
        private List<Hospital> hospitals = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
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

        public List<Hospital> getHospitals() {
            return hospitals;
        }

        public void setHospitals(List<Hospital> hospitals) {
            this.hospitals = hospitals;
        }

    }
    }
