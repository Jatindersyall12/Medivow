package com.app.treatEasy.new_feature.home;

import com.app.treatEasy.feature.home_screen.Doctor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {

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
        @SerializedName("top_banners")
        @Expose
        private List<TopBanner> topBanners = null;
        @SerializedName("middle_banners")
        @Expose
        private List<MiddleBanner> middleBanners = null;
        @SerializedName("doctors")
        @Expose
        private List<Doctor> doctors = null;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getWalletAmount() {
            return walletAmount;
        }

        public void setWalletAmount(String walletAmount) {
            this.walletAmount = walletAmount;
        }

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("wallet_amount")
        @Expose
        private String walletAmount;

        public List<TopBanner> getTopBanners() {
            return topBanners;
        }
        public void setTopBanners(List<TopBanner> topBanners) {
            this.topBanners = topBanners;
        }
        public List<MiddleBanner> getMiddleBanners() {
            return middleBanners;
        }
        public void setMiddleBanners(List<MiddleBanner> middleBanners) {
            this.middleBanners = middleBanners;
        }
        public List<Doctor> getDoctors() {
            return doctors;
        }
        public void setDoctors(List<Doctor> doctors) {
            this.doctors = doctors;
        }

    }

  /*  public class Doctor {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("specialities")
        @Expose
        private String specialities;
        @SerializedName("hospitals")
        @Expose
        private String hospitals;

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

        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getHospitals() {
            return hospitals;
        }

        public void setHospitals(String hospitals) {
            this.hospitals = hospitals;
        }

    }*/

    public class MiddleBanner {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("banner")
        @Expose
        private String banner;
        @SerializedName("locationpin")
        @Expose
        private String locationpin;
        @SerializedName("landing_type")
        @Expose
        private String landingType;
        @SerializedName("landing_id")
        @Expose
        private String landingId;
        @SerializedName("status")
        @Expose
        private String status;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getLocationpin() {
            return locationpin;
        }

        public void setLocationpin(String locationpin) {
            this.locationpin = locationpin;
        }

        public String getLandingType() {
            return landingType;
        }

        public void setLandingType(String landingType) {
            this.landingType = landingType;
        }

        public String getLandingId() {
            return landingId;
        }

        public void setLandingId(String landingId) {
            this.landingId = landingId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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


    }
    public class TopBanner {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("banner")
        @Expose
        private String banner;
        @SerializedName("locationpin")
        @Expose
        private String locationpin;
        @SerializedName("landing_type")
        @Expose
        private String landingType;
        @SerializedName("landing_id")
        @Expose
        private String landingId;
        @SerializedName("status")
        @Expose
        private String status;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getLocationpin() {
            return locationpin;
        }

        public void setLocationpin(String locationpin) {
            this.locationpin = locationpin;
        }

        public String getLandingType() {
            return landingType;
        }

        public void setLandingType(String landingType) {
            this.landingType = landingType;
        }

        public String getLandingId() {
            return landingId;
        }

        public void setLandingId(String landingId) {
            this.landingId = landingId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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


    }

    public class Location {

        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("state")
        @Expose
        private String state;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

    }
}
