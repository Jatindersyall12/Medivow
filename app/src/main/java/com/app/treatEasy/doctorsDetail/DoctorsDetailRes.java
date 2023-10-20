package com.app.treatEasy.doctorsDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorsDetailRes {

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
        @SerializedName("client_name")
        @Expose
        private String clientName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("department_id")
        @Expose
        private String departmentId;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("images")
        @Expose
        private List<Image> images = null;
        @SerializedName("services")
        @Expose
        private String services;
        @SerializedName("doctors")
        @Expose
        private List<Doctor> doctors = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

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

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }

        public List<Doctor> getDoctors() {
            return doctors;
        }

        public void setDoctors(List<Doctor> doctors) {
            this.doctors = doctors;
        }

        public class Doctor {

            @SerializedName("fee")
            @Expose
            private String fee;
            @SerializedName("discounted_fee")
            @Expose
            private String discountedFee;
            @SerializedName("timing")
            @Expose
            private String timing;
            @SerializedName("doctor_name")
            @Expose
            private String doctorName;
            @SerializedName("specialities")
            @Expose
            private String specialities;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("profile_image")
            @Expose
            private String profileImage;

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

            public String getTiming() {
                return timing;
            }

            public void setTiming(String timing) {
                this.timing = timing;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getSpecialities() {
                return specialities;
            }

            public void setSpecialities(String specialities) {
                this.specialities = specialities;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

        }
        public class Image {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("client_image")
            @Expose
            private String clientImage;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClientImage() {
                return clientImage;
            }

            public void setClientImage(String clientImage) {
                this.clientImage = clientImage;
            }

        }

    }

}
