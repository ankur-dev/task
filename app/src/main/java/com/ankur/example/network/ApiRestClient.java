package com.ankur.example.network;


import com.ankur.example.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by vs on 9/8/2016.
 */
public class ApiRestClient {

    private static OkHttpClient httpClient;

    static {
        setupHttpClient();

    }

    String baseUrl = "";

    private static void setupHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.BUILD_TYPE.contains("debug")) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        }

        httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor).connectTimeout(90 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor).readTimeout(90 * 1000, TimeUnit.MILLISECONDS)
                .build();
    }


    public static <T> T getApiService(Class<T> service, String baseURL) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient);
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(service);

    }
}
