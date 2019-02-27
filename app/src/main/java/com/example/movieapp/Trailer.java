package com.example.movieapp;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("name")
    String name;
    @SerializedName("key")
    String key;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
