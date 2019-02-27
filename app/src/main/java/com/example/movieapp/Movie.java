package com.example.movieapp;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String name;

    @SerializedName("backdrop_path")
    private String imgUrl;

    @SerializedName("release_date")
    private String year;

    @SerializedName("runtime")
    private int duration;

    @SerializedName("vote_average")
    private float rate;

    @SerializedName("overview")
    private String overview;

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public float getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }
}