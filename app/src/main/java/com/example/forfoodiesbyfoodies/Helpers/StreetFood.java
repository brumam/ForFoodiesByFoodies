package com.example.forfoodiesbyfoodies.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

public class StreetFood implements Parcelable {
    private String name, description, imageURL;

    private String checkbox;
    public StreetFood(){

    }

    public StreetFood(String name, String description, String imageURL,String checkbox) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.checkbox = checkbox;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    protected StreetFood(Parcel in) {
        name = in.readString();
        description = in.readString();
        imageURL = in.readString();
        checkbox = in.readString();
    }

    public static final Creator<StreetFood> CREATOR = new Creator<StreetFood>() {
        @Override
        public StreetFood createFromParcel(Parcel in) {
            return new StreetFood(in);
        }

        @Override
        public StreetFood[] newArray(int size) {
            return new StreetFood[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageURL);
        dest.writeString(checkbox);
    }
}
