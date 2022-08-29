package com.totoit.test.api;

import com.totoit.test.model.ApiData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("Loader/Start/2237/wolfgold")
    Call<ApiData> getApiData(
            @Query("casinolobbyurl") String casino_lobby_url,
            @Query("funMode") String fun_mode,
            @Query("language") String language,
            @Query("launchApi") String launchApi,
            @Query("_sid64") String sid
    );
}
