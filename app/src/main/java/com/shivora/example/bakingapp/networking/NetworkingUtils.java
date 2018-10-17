package com.shivora.example.bakingapp.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingUtils {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(){
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
