package com.example.movieapp.adapter_db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.movieapp.model.Movie;
import com.example.movieapp.R;
import com.example.movieapp.model_db.Movie_DB;
import com.example.movieapp.util.Constant;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapterOffline extends RecyclerView.Adapter<MovieAdapterOffline.MovieOfflineHolder> {

    private Context mContext;
    private List<Movie_DB> movie_dbList;
    private int row_index = 0;

    public MovieAdapterOffline(Context mContext, List<Movie_DB> movieDbList) {
        this.mContext = mContext;
        this.movie_dbList = movieDbList;
    }

    private static MovieAdapterOffline instance;

    public static MovieAdapterOffline getInstance(Context context, List<Movie_DB> bookingAll_itemsDBList) {
        if (instance == null) {
            synchronized (Movie.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new MovieAdapterOffline(context, bookingAll_itemsDBList);
                }
            }
        }
        return instance;
    }

    @Override
    public MovieOfflineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.match_calender_item_db, parent, false);
        MovieOfflineHolder holder = new MovieOfflineHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieOfflineHolder holder, int position) {
        final Movie_DB movie_db = movie_dbList.get(position);
        Glide.with(mContext).load(Constant.Api.BASE_IMAGE_URL + movie_db.posterPath).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movie_dbList.size();
    }

    public class MovieOfflineHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MovieOfflineHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_item_movie_dp);
        }
    }
}