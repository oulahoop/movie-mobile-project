package com.example.td6_movieproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class Movie implements Parcelable {
    private String titre;
    private String resume;
    private String image;
    private String year;

    public Movie(String titre, String resume, String image, String year) {
        this.titre = titre;
        this.resume = resume;
        this.image = image;
        this.year = year;
    }

    public Movie(JsonObject json){
        this.titre = json.get("title").getAsString();
        this.resume = json.get("overview").getAsString();
        this.image = json.get("poster_path").isJsonNull() ? "" : json.get("poster_path").getAsString();
        this.year = json.get("release_date").getAsString().split("-")[0];
    }

    protected Movie(Parcel in) {
        titre = in.readString();
        resume = in.readString();
        image = in.readString();
        year = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitre() {
        return titre;
    }

    public String getResume() {
        return resume;
    }

    public String getImage() {
        return image;
    }

    public String getYear() {
        return year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titre);
        dest.writeString(resume);
        dest.writeString(image);
        dest.writeString(year);
    }
}
