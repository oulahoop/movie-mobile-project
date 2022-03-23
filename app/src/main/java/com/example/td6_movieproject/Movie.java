package com.example.td6_movieproject;

import com.google.gson.JsonObject;

public class Movie {
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

}
