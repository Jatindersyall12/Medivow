package com.app.treatEasy.feature.packages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageDeatil {

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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("price_detail")
        @Expose
        private List<PriceDetail> priceDetail = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public List<PriceDetail> getPriceDetail() {
            return priceDetail;
        }

        public void setPriceDetail(List<PriceDetail> priceDetail) {
            this.priceDetail = priceDetail;
        }

    }

    public class PriceDetail {

        @SerializedName("package_id")
        @Expose
        private String packageId;
        @SerializedName("client_id")
        @Expose
        private String clientId;
        @SerializedName("fee")
        @Expose
        private String fee;
        @SerializedName("discounted_fee")
        @Expose
        private String discountedFee;
        @SerializedName("doctorsid")
        @Expose
        private String doctorsid;
        @SerializedName("client_name")
        @Expose
        private String clientName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("client_image")
        @Expose
        private String clientImage;
        @SerializedName("city_name")
        @Expose
        private String cityName;

        public Integer getIsBooked() {
            return isBooked;
        }

        public void setIsBooked(Integer isBooked) {
            this.isBooked = isBooked;
        }

        @SerializedName("is_booked")
        @Expose
        private Integer isBooked;
        @SerializedName("doctors")
        @Expose
        private List<Doctor> doctors = null;

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

        public String getDoctorsid() {
            return doctorsid;
        }

        public void setDoctorsid(String doctorsid) {
            this.doctorsid = doctorsid;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getClientImage() {
            return clientImage;
        }
        public void setClientImage(String clientImage) {
            this.clientImage = clientImage;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public List<Doctor> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<Doctor> doctors) {
            this.doctors = doctors;
        }

    }
    public class Doctor {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("doctor_name")
        @Expose
        private String doctorName;

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

      }
    }
