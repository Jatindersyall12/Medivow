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

        @SerializedName("approximate_time")
        String approximate_time;

        public String getApproximate_time() {
            return approximate_time;
        }

        public void setApproximate_time(String approximate_time) {
            this.approximate_time = approximate_time;
        }
    }

}
