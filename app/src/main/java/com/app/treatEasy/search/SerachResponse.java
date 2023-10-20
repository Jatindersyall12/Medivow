package com.app.treatEasy.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerachResponse {
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

        @SerializedName("display_name")
        @Expose
        private String displayName;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("specialities")
        @Expose
        private Object specialities;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("reviews")
        @Expose
        private Integer reviews;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getDisplayName() {
            return displayName;
        }
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
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

        public Object getSpecialities() {
            return specialities;
        }

        public void setSpecialities(Object specialities) {
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

    }
}
