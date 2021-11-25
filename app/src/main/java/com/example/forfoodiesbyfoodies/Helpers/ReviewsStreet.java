package com.example.forfoodiesbyfoodies.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewsStreet implements Parcelable {
    private  String rating;
    private String review,firstName,lastName;
    private String imageURL;

    public ReviewsStreet(){

    }

    public ReviewsStreet(String rating, String review, String firstName, String lastName, String imageURL) {
        this.rating = rating;
        this.review = review;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
    }

    protected ReviewsStreet(Parcel in) {
        rating = in.readString();
        review = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<ReviewsStreet> CREATOR = new Creator<ReviewsStreet>() {
        @Override
        public ReviewsStreet createFromParcel(Parcel in) {
            return new ReviewsStreet(in);
        }

        @Override
        public ReviewsStreet[] newArray(int size) {
            return new ReviewsStreet[size];
        }
    };

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rating);
        dest.writeString(review);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(imageURL);
    }
}
