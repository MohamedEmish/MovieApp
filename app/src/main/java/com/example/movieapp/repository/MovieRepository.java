package com.example.movieapp.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.api.ApiInterface;
import com.example.movieapp.app.MyApplication;
import com.example.movieapp.model.ResponseMovie;

import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieRepository {
    @Inject
    Retrofit retrofit;
    @Inject
    Application application;
    private static MovieRepository repository;
    private ApiInterface apiInterface ;

    public MutableLiveData<Boolean> loadignBar = new MutableLiveData<>();

    public static MovieRepository getInstance(){
        if (repository == null){
            repository = new MovieRepository();
        }
        return repository;
    }

    public MovieRepository(){
        MyApplication.getComponent().injectMovie(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public MutableLiveData<ResponseMovie> getMovies(Map<String , String> stringMap,String sort){
        loadignBar.setValue(true);
        MutableLiveData<ResponseMovie> responseMovieMutableLiveData = new MutableLiveData<>();
        apiInterface.getMovies(sort,stringMap).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.isSuccessful()){
                    responseMovieMutableLiveData.setValue(response.body());
                    loadignBar.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                responseMovieMutableLiveData.setValue(null);
                loadignBar.setValue(false);
            }
        });
        return responseMovieMutableLiveData;
    }
}