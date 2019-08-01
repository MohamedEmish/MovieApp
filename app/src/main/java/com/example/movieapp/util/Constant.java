package com.example.movieapp.util;

public interface Constant {

    interface Api {
        //LINK:- (BASE_URL) for any Movie
        String BASE_URL = "https://api.themoviedb.org/3/";

        // addQueryParameter (Token)--->(key , value)
        String TOKEN_NAME = "api_key";  // Keys
        String TOKEN_VALUE = "c346dddb6a140ff72fb752665c7135d0";  // Values
        String SORT = "sort_by";
        String TOP_RATED = "top_rated";
        String POPULAR = "popular";
        String NOW_PLAYING = "now_playing";

        // addQueryParameter (LINK:- Type of Movies Loaded)---> Type Loading Movies (popular)----(top_rated)
        String POPULAR_MOVIES_KEY = "popular";
        String TOP_RATED_MOVIES_KEY = "top_rated";

        //Picasso (LINK:- BASE_IMAGE_URL)
        String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";


        //LINK:- GET_Movies_videos
        String MOVIE_GET_VIDEO_LINK = "{movie_id}/videos";

        // KEY---> addPathParameter (LINK:- {movie_id})------> Change (id)
        String KEY_ID_GET_VIDEO = "movie_id";
    }

    interface EXTRA {

        String MOVIE = "movie";
        String TRAILER = "trailer";
        String ID_MOVIE = "id";
        String FAV_MOVIES ="fav_movies";


        //key saved SharedPreferance
        String POPULAR_MOVIES_CACHED ="pop";
        String TOP_RATED_MOVIES_CACHED ="top";

    }
}
