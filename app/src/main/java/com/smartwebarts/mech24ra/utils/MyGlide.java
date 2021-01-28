package com.smartwebarts.mech24ra.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.smartwebarts.mech24ra.R;

public class MyGlide {

    public static void with(Context context, Uri url, ImageView imageView) {
        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();// The attributes for a ShimmerDrawable is set by this builder
        builder.setDuration(1800); // how long the shimmering animation takes to do one full sweep
        builder .setBaseAlpha(0.7f); //the alpha of the underlying children
        builder.setHighlightAlpha(0.6f); // the shimmer alpha amount
        builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        builder.setAutoStart(true);
        builder.build();
        // This is the placeholder for the imageView
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(builder.build());

        Glide.with(context)
                .load(url)
                .placeholder(shimmerDrawable)
                .into(imageView);

    }

    public static void with(Context context, Drawable url, ImageView imageView) {
        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();// The attributes for a ShimmerDrawable is set by this builder
        builder.setDuration(1800); // how long the shimmering animation takes to do one full sweep
        builder .setBaseAlpha(0.7f); //the alpha of the underlying children
        builder.setHighlightAlpha(0.6f); // the shimmer alpha amount
        builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        builder.setAutoStart(true);
        builder.build();
        // This is the placeholder for the imageView
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(builder.build());

        Glide.with(context)
                .load(url)
                .placeholder(shimmerDrawable)
//                .error(R.drawable.logo)
                .into(imageView);
    }

    public static void withCircle(final Context context, String url, final ImageView imageView) {
        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();// The attributes for a ShimmerDrawable is set by this builder
        builder.setDuration(1800); // how long the shimmering animation takes to do one full sweep
        builder .setBaseAlpha(0.7f); //the alpha of the underlying children
        builder.setHighlightAlpha(0.6f); // the shimmer alpha amount
        builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        builder.setAutoStart(true);
        builder.build();
        // This is the placeholder for the imageView
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(builder.build());

        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .placeholder(shimmerDrawable)
//                .error(R.drawable.logo)
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    public static void withCircle(final Context context, Drawable url, final ImageView imageView) {
        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();// The attributes for a ShimmerDrawable is set by this builder
        builder.setDuration(1800); // how long the shimmering animation takes to do one full sweep
        builder .setBaseAlpha(0.7f); //the alpha of the underlying children
        builder.setHighlightAlpha(0.6f); // the shimmer alpha amount
        builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        builder.setAutoStart(true);
        builder.build();
        // This is the placeholder for the imageView
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(builder.build());

        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .placeholder(shimmerDrawable)
//                .error(R.drawable.logo)
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }


    public static void profile(final Context context, Uri url, final ImageView imageView) {
        Shimmer.AlphaHighlightBuilder builder = new Shimmer.AlphaHighlightBuilder();// The attributes for a ShimmerDrawable is set by this builder
        builder.setDuration(1800); // how long the shimmering animation takes to do one full sweep
        builder .setBaseAlpha(0.7f); //the alpha of the underlying children
        builder.setHighlightAlpha(0.6f); // the shimmer alpha amount
        builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
        builder.setAutoStart(true);
        builder.build();
        // This is the placeholder for the imageView
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(builder.build());

        Glide.with(context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .placeholder(shimmerDrawable)
                .error(R.drawable.ic_profile)
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                        roundedBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }
}
