package com.app.treatEasy.feature.packages.doctors.package_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsByCategoryModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("doctor_name")
    @Expose
    public String doctorName;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("specialities")
    @Expose
    public String specialities;
    @SerializedName("department")
    @Expose
    public String department;
    @SerializedName("timing")
    @Expose
    public String timing;
    @SerializedName("status")
    @Expose
    public String status;

    public static class PostDoctorByCategoryModel{
        public String pkgcateid;
        public String pkgid;
        public String pincode;
    }

    protected DoctorsByCategoryModel(Parcel in) {
        id = in.readString();
        doctorName = in.readString();
        profileImage = in.readString();
        description = in.readString();
        specialities = in.readString();
        department = in.readString();
        timing = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(doctorName);
        dest.writeString(profileImage);
        dest.writeString(description);
        dest.writeString(specialities);
        dest.writeString(department);
        dest.writeString(timing);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DoctorsByCategoryModel> CREATOR = new Creator<DoctorsByCategoryModel>() {
        @Override
        public DoctorsByCategoryModel createFromParcel(Parcel in) {
            return new DoctorsByCategoryModel(in);
        }

        @Override
        public DoctorsByCategoryModel[] newArray(int size) {
            return new DoctorsByCategoryModel[size];
        }
    };
}
