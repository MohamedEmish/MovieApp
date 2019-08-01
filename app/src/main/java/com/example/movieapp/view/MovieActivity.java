package com.example.movieapp.view;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.movieapp.app.MyApplication;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.R;
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.model.Movie;
import com.example.movieapp.util.Constant;
import com.example.movieapp.viewModel.MovieViewModel;

import java.util.List;

import javax.inject.Inject;
import retrofit2.Retrofit;

public class MovieActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    @Inject
    Application application;
    private ActivityMainBinding binding;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ((MyApplication) getApplication()).getComponent().inject(this);
        binding.setLifecycleOwner(this);

        getData(Constant.Api.NOW_PLAYING);
        setTitle("Now playing Movies");
    }

    private void getData(String sort){
        showLoadingBar();
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies(this, sort);
        observableChanges();
    }

    private void observableChanges() {
        movieViewModel.movieList.observe(this, responseMovie -> Recycler(responseMovie.getMovies()));

        movieViewModel.errorMessage.observe(this, s -> Toast.makeText(MovieActivity.this, s, Toast.LENGTH_LONG).show());

        movieViewModel.loadignBar.observe(this, show -> {
            if (show)
                showLoadingBar();
            else
                hideLoadingBar();
        });
    }

    private void showLoadingBar() {
        binding.loadignBar.setVisibility(View.VISIBLE);
        binding.mainRecyclerView.setVisibility(View.GONE);
    }

    private void hideLoadingBar() {
        binding.loadignBar.setVisibility(View.GONE);
        binding.mainRecyclerView.setVisibility(View.VISIBLE); }

    private void Recycler(List<Movie> movies) {
        binding.setAdapter(new MovieAdapter(movies, MovieActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                setTitle("Top rated Movies");
                getData(Constant.Api.TOP_RATED);
                return true;
            case R.id.most_popular:
                setTitle("Popular Movies");
                getData(Constant.Api.POPULAR);
                return true;
            case R.id.now_playing:
                setTitle("Now playing Movies");
                getData(Constant.Api.NOW_PLAYING);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
