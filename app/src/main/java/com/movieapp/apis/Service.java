package com.movieapp.apis;

import com.movieapp.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mauli on 3/21/2018.
 */

public interface Service {

    @GET("list/{list_id}")
    Call<MovieResponse> getNowPlayingMoviesV4(@Query("api_key") String apiKey, @Path("list_id") int listId, @Query("page") int page, @Header("Content-Type") String contentType);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMoviesV3(@Query("api_key") String apiKey, @Query("page") int page, @Header("Content-Type") String contentType);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpComingMoviesV3(@Query("api_key") String apiKey, @Query("page") int page, @Header("Content-Type") String contentType);
}
