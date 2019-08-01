package com.example.movieapp.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

public class Connection extends Application {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager
                = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
