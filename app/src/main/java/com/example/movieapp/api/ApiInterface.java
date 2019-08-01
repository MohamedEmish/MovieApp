package com.example.movieapp.api;

import com.example.movieapp.model.ResponseMovie;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("movie/{sorting}")
    Call<ResponseMovie> getMovies(
            @Path("sorting") String sort,
            @QueryMap Map<String , String> queryParameters
    );
}