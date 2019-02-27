package com.example.movieapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMoviesArrayList;
    private MovieClickListener mClickListener;

    public MovieAdapter(Context context, List<Movie> arrayList) {
        mContext = context;
        mMoviesArrayList = arrayList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        String imageString = mMoviesArrayList.get(position).getImgUrl();
        String imageUrl = "https://image.tmdb.org/t/p/w780"+imageString;

        Picasso.get()
                .load(imageUrl)
                .placeholder( R.drawable.progress_animation )
                .into(holder.eImage);
    }

    @Override
    public int getItemCount() {
        return mMoviesArrayList.size();
    }

    // allows clicks Movies to be caught
    public void setClickListener(MovieClickListener MovieClickListener) {
        this.mClickListener = MovieClickListener;
    }

    // return data
    public int getItemName(int id) {
        return mMoviesArrayList.get(id).getId();
    }

    // parent activity will implement this method to respond to click Movies
    public interface MovieClickListener {
        void onItemClick(View view, int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView eImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            eImage = itemView.findViewById(R.id.movie_item_image);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }


    }
}