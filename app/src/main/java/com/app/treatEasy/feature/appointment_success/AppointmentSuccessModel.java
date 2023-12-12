package com.app.treatEasy.feature.appointment_success;

import com.google.gson.annotations.SerializedName;

public class AppointmentSuccessModel {

    @SerializedName("message")
    String message;

    @SerializedName("status_code")
    int statusCode;

    @SerializedName("data")
    Data data;


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppointment_date() {
            return appointment_date;
        }

        public void setAppointment_date(String appointment_date) {
            this.appointment_date = appointment_date;
        }



        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        @SerializedName("id")
        String id;

        @SerializedName("appointment_date")
        String appointment_date;

        @SerializedName("doctor_name")
        String doctor_name;

        public String getToken_no() {
            return token_no;
        }

        public void setToken_no(String token_no) {
            this.token_no = token_no;
        }

        @SerializedName("token_no")
        String token_no;

        @SerializedName("approximate_time")
        String approximate_time;

        @SerializedName("address")
        String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }



        @SerializedName("member_name")
        String member_name;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        @SerializedName("appointment_no")
        String appointment_no;

        public String getAppointment_no() {
            return appointment_no;
        }

        public void setAppointment_no(String appointment_no) {
            this.appointment_no = appointment_no;
        }

        public String getApproximate_time() {
            return approximate_time;
        }

        public void setApproximate_time(String approximate_time) {
            this.approximate_time = approximate_time;
        }
    }

}
