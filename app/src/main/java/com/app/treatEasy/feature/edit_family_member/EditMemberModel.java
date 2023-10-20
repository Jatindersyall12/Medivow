package com.app.treatEasy.feature.edit_family_member;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditMemberModel {

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

        @SerializedName("id")
        String id;

        @SerializedName("patientid")
        String patientid;

        @SerializedName("member_name")
        String memberName;

        @SerializedName("dob")
        String dob;

        @SerializedName("gender")
        String gender;

        @SerializedName("relation")
        String relation;

        @SerializedName("member_photo")
        String memberPhoto;

        @SerializedName("is_deleted")
        String isDeleted;

        @SerializedName("status")
        String status;

        @SerializedName("created_at")
        String createdAt;


        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setPatientid(String patientid) {
            this.patientid = patientid;
        }
        public String getPatientid() {
            return patientid;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }
        public String getMemberName() {
            return memberName;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }
        public String getDob() {
            return dob;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getGender() {
            return gender;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
        public String getRelation() {
            return relation;
        }

        public void setMemberPhoto(String memberPhoto) {
            this.memberPhoto = memberPhoto;
        }
        public String getMemberPhoto() {
            return memberPhoto;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
        public String getIsDeleted() {
            return isDeleted;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
        public String getCreatedAt() {
            return createdAt;
        }

    }

}
