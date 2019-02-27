package com.example.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends AppCompatActivity {

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static String BAST_TRAILER_URL = "https://www.youtube.com/watch?v=";
    private MovieApi movieApi;

    TextView name,year,duration,
            rate,overview,
            trailer1Name,trailer2Name;

    LinearLayout trailer1,trailer2,
            trailerOnOff;

    ImageView eImage;

    String api_key;

    int id;

    View view;

    LinearLayout details;

    ImageView progress;

    List<Trailer> trailerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Movie Detail");


        api_key = getIntent().getStringExtra("api_key");
        id = getIntent().getIntExtra("id",0);

        details = findViewById(R.id.all_details);
        progress = findViewById(R.id.progress_animation_details);

        name = findViewById(R.id.movie_name);
        year = findViewById(R.id.movie_year);
        duration = findViewById(R.id.movie_duration);
        rate = findViewById(R.id.movie_rate);
        overview = findViewById(R.id.movie_overview);

        trailer1 =findViewById(R.id.movie_trailer_1);
        trailer2 = findViewById(R.id.movie_trailer_2);
        trailerOnOff = findViewById(R.id.movie_trailer_on);

        trailer1Name = findViewById(R.id.movie_trailer_1_name);
        trailer2Name = findViewById(R.id.movie_trailer_2_name);

        view = findViewById(R.id.movie_trailer_view);

        eImage = findViewById(R.id.movie_image);

        trailer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo(trailerList.get(0).getKey());
            }
        });

        trailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo(trailerList.get(1).getKey());
            }
        });

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

        getMovieDetails(id,api_key);
        getMovieTrailers(id,api_key);
    }

    private void openVideo(String key) {
        String videoUrl = BAST_TRAILER_URL+key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);
    }

    private void getMovieTrailers(int id, String api_key) {
        Call<TrailersList> call = movieApi.getMovieTrailers(id,api_key);
        call.enqueue(new Callback<TrailersList>() {
            @Override
            public void onResponse(Call<TrailersList> call, Response<TrailersList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MovieDetailActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                trailerList = response.body().getResults();
                if (trailerList.size()<2 && trailerList.size()>0){
                    trailer1Name.setText(trailerList.get(0).getName());
                    trailerOnOff.setVisibility(View.VISIBLE);
                    trailer2.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }else if (trailerList.size()>2){
                    trailer1Name.setText(trailerList.get(0).getName());
                    trailer2Name.setText(trailerList.get(1).getName());
                    trailerOnOff.setVisibility(View.VISIBLE);
                }else {
                    trailerOnOff.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TrailersList> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Code: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovieDetails(int id,String api_key) {

        Call<Movie> call = movieApi.getMovie(id,api_key);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MovieDetailActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Movie movie = response.body();

                name.setText(movie.getName());
                year.setText(getYearNumber(movie.getYear()));
                duration.setText(String.valueOf(movie.getDuration()));
                rate.setText(String.valueOf(movie.getRate()));
                overview.setText(movie.getOverview());

                progress.setVisibility(View.GONE);
                details.setVisibility(View.VISIBLE);

                String imageUrl = "https://image.tmdb.org/t/p/w780"+movie.getImgUrl();

                Picasso.get()
                        .load(imageUrl)
                        .placeholder( R.drawable.progress_animation )
                        .into(eImage);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "Code: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(MovieDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getYearNumber(String year){

        String[] cutYearFromRest = year.split("-");
        String yearNumber = cutYearFromRest[0];

        return yearNumber;
    }
}