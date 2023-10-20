package com.app.treatEasy.feature.family_member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MemberDetailResponse {

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

    public static class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("patientid")
        @Expose
        private String patientid;
        @SerializedName("member_name")
        @Expose
        private String memberName;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("relation")
        @Expose
        private String relation;
        @SerializedName("member_photo")
        @Expose
        private String memberPhoto;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("craeted_at")
        @Expose
        private String craetedAt;
        @SerializedName("gender")
        @Expose
        private String gender;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @SerializedName("age")
        @Expose
        private String age;
        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPatientid() {
            return patientid;
        }

        public void setPatientid(String patientid) {
            this.patientid = patientid;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getMemberPhoto() {
            return memberPhoto;
        }

        public void setMemberPhoto(String memberPhoto) {
            this.memberPhoto = memberPhoto;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCraetedAt() {
            return craetedAt;
        }

        public void setCraetedAt(String craetedAt) {
            this.craetedAt = craetedAt;
        }

    }
}
