package com.example.forfoodiesbyfoodies.Helpers;

import android.os.Parcel;
import android.os.Parcelable;



public class Restaurant implements Parcelable {
    private String name, description, imageURL, bookingURL;
    private boolean canBook;


    public Restaurant (){

    }


    public Restaurant(String name, String description, String imageURL, String bookingURL, boolean canBook) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.bookingURL = bookingURL;
        this.canBook = canBook;
    }




    protected Restaurant(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageURL = in.readString();
        bookingURL = in.readString();
        canBook = in.readByte() != 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBookingURL() {
        return bookingURL;
    }

    public void setBookingURL(String bookingURL) {
        this.bookingURL = bookingURL;
    }

    public boolean isCanBook() {
        return canBook;
    }

    public void setCanBook(boolean canBook) {
        this.canBook = canBook;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageURL);
        dest.writeString(bookingURL);
        dest.writeByte((byte) (canBook ? 1 : 0));
    }
}
