package com.example.movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/{id}")
    Call<Movie> getMovie(
            @Path("id") int postId,
            @Query("api_key") String ApiKey
    );

    @GET("movie/{sorting}")
    Call<MovieList> getMovies(
            @Path("sorting") String sortingKey,
            @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailersList> getMovieTrailers(
            @Path("id") int id,
            @Query("api_key") String apiKey);
}