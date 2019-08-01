package com.example.movieapp.app;

import android.app.Application;
import com.example.movieapp.components.ApiComponent;
import com.example.movieapp.components.DaggerApiComponent;
import com.example.movieapp.module.ApiClientModule;
import com.example.movieapp.module.AppModule;
import com.example.movieapp.util.Constant;

public class MyApplication extends Application {

    public static ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiClientModule(new ApiClientModule(Constant.Api.BASE_URL))
                .build();
    }

    public static ApiComponent getComponent() {
        return mApiComponent;
    }
}
