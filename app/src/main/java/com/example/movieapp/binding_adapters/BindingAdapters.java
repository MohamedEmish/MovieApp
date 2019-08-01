package com.example.movieapp.binding_adapters;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.example.movieapp.util.Constant;

public class BindingAdapters {
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(Constant.Api.BASE_IMAGE_URL + imageUrl).into(view);
    }
}