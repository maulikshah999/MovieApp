package com.movieapp.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mauli on 3/21/2018.
 */

public class Client {

    public static final String BASE_URL_V4 = "https://api.themoviedb.org/4/";
    public static final String BASE_URL_V3 = "http://api.themoviedb.org/3/";

    public static Retrofit retrofit = null;

    //using retrofit library for API call
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_V3)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }

}
