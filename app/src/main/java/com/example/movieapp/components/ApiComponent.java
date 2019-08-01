package com.example.movieapp.components;

import com.example.movieapp.module.ApiClientModule;
import com.example.movieapp.module.AppModule;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.view.MovieActivity;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApiClientModule.class, AppModule.class})
public interface ApiComponent {
    void inject(MovieActivity movieActivity);

    void injectMovie(MovieRepository movieRepository);
}