package com.smartwebarts.mech24ra.retrofit;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String BASE_URL = "http://mech24ra.com/";
    public static final String USER_PROFILE_IMAGE = BASE_URL + "img/user/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            ////////////////////////////////////////////////////

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(60, TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .writeTimeout(60, TimeUnit.MINUTES)
                    .build();


            ////////////////////////////////////////////////////
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
