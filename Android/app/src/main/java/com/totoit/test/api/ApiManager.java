package com.totoit.test.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.totoit.test.R;

public class ApiManager {

    public static ApiServices getService(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit restAdapter = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter.create(ApiServices.class);
    }

}
