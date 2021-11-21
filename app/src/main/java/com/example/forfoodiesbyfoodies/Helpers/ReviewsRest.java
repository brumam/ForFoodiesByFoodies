package com.example.forfoodiesbyfoodies.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewsRest implements Parcelable {
    private  String rating;
    private String review,firstName,lastName;
    private String imageURL;



    public ReviewsRest(){

    }

    public ReviewsRest(String rating, String review, String firstName, String lastName, String imageURL) {
        this.rating = rating;
        this.review = review;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
    }

    protected ReviewsRest(Parcel in) {

        rating = in.readString();
        review = in.readString();
        firstName = in.readString();
        lastName = in.readString();
    }

    public static final Creator<ReviewsRest> CREATOR = new Creator<ReviewsRest>() {
        @Override
        public ReviewsRest createFromParcel(Parcel in) {
            return new ReviewsRest(in);
        }

        @Override
        public ReviewsRest[] newArray(int size) {
            return new ReviewsRest[size];
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
    }
}
