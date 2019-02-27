package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static String API_KEY = "c346dddb6a140ff72fb752665c7135d0";

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;

    TextView error;
    ImageView progress;

    List<Movie> movieList = new ArrayList<>();

    private MovieApi movieApi;

    String sort = "now_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        error = findViewById(R.id.error);
        progress = findViewById(R.id.progress_animation);

        setTitle("Now playing Movies");

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        movieApi = retrofit.create(MovieApi.class);

        getMovies();
    }

    private void getMovies() {

        Call<MovieList> result = movieApi.getMovies(sort,API_KEY);
        result.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();

                    error.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    error.setText(response.code());

                    return;
                }

                movieList = response.body().getResults();

                movieAdapter = new MovieAdapter(MainActivity.this, movieList);
                movieAdapter.setClickListener(MainActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                recyclerView.setAdapter(movieAdapter);

                progress.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }


            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {

                error.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                error.setText(t.toString());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        int id = movieList.get(position).getId();
        String image = movieList.get(position).getImgUrl();
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("api_key", API_KEY);
        startActivity(intent);

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
                sort = "top_rated";
                setTitle("Top rated Movies");
                progress.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                getMovies();
                return true;
            case R.id.most_popular:
                sort = "popular";
                setTitle("Popular Movies");
                progress.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                getMovies();
                return true;
            case R.id.now_playing:
                sort = "now_playing";
                setTitle("Now playing Movies");
                progress.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                getMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
