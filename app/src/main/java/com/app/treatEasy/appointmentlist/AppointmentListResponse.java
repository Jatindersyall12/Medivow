package com.app.treatEasy.appointmentlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AppointmentListResponse {
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
        @SerializedName("token_no")
        @Expose
        private String token_no;

        @SerializedName("doctor_name")
        @Expose
        private String doctor_name;

        @SerializedName("doctor_profile_image")
        @Expose
        private String doctor_profile_image;

        @SerializedName("gender")
        @Expose
        private String gender;

        public String getDoctor_profile_image() {
            return doctor_profile_image;
        }

        @SerializedName("appointment_date")
        @Expose
        private String appointment_date;


        public String getAppointment_date() {
            return appointment_date;
        }

        public void setAppointment_date(String appointment_date) {
            this.appointment_date = appointment_date;
        }

        public String getApproximate_time() {
            return approximate_time;
        }

        public void setApproximate_time(String approximate_time) {
            this.approximate_time = approximate_time;
        }

        @SerializedName("approximate_time")
        @Expose
        private String approximate_time;

        public void setDoctor_profile_image(String doctor_profile_image) {
            this.doctor_profile_image = doctor_profile_image;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @SerializedName("age")
        @Expose
        private String age;

        @SerializedName("specialities")
        @Expose
        private String specialities;

        @SerializedName("client_name")
        @Expose
        private String client_name;


        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        @SerializedName("member_name")
        @Expose
        private String member_name;


        public String getSpecialities() {
            return specialities;
        }

        public void setSpecialities(String specialities) {
            this.specialities = specialities;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getDoctor_name() {
            return doctor_name;
        }



        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getToken_no() {
            return token_no;
        }

        public void setToken_no(String token_no) {
            this.token_no = token_no;
        }
    }
}
