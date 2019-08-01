package com.example.movieapp.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.model.ResponseMovie;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.util.Connection;
import com.example.movieapp.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class MovieViewModel extends AndroidViewModel {
    public MutableLiveData<ResponseMovie> movieList = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadignBar = new MutableLiveData<>();
    private MovieRepository movieRepository = new MovieRepository();
    private boolean isConnected;

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMovies(Context mContext ,String sort) {
        movieRepository.loadignBar.observe((LifecycleOwner) mContext, show -> loadignBar.setValue(show));
        isConnected = Connection.isNetworkAvailable(getApplication());
        if (isConnected) {
            Map<String, String> map = new HashMap<>();
            map.put(Constant.Api.TOKEN_NAME, Constant.Api.TOKEN_VALUE);
            movieList = movieRepository.getMovies(map, sort);
            movieRepository.loadignBar.observe((LifecycleOwner) mContext, show -> loadignBar.setValue(show));
        } else {
            errorMessage.setValue("No internet Connection");
            loadignBar.setValue(false);
        }
    }
}