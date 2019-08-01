package com.example.movieapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityDetailseBinding;
import com.example.movieapp.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityDetailseBinding detailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailse);
        Intent intent = getIntent();
        if (intent != null) {
            Movie movie = intent.getParcelableExtra("Movie");
            detailsBinding.setMovie(movie);
        }
    }
}
