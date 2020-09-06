package com.rishavjyoti.covidindia.api;

import com.rishavjyoti.covidindia.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(
        @Query("country") String country,
        @Query("category") String category,
        @Query("apiKey") String apiKey
    );
}
