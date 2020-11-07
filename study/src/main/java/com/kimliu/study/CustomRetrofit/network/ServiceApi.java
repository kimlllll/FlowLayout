package com.kimliu.study.CustomRetrofit.network;


import com.kimliu.study.CustomRetrofit.ann.Field;
import com.kimliu.study.CustomRetrofit.ann.GET;
import com.kimliu.study.CustomRetrofit.ann.POST;
import com.kimliu.study.CustomRetrofit.ann.Query;

import okhttp3.Call;

public interface ServiceApi {


    @POST("/v3/weather/weatherInfo")
    Call postWeather(@Field("city") String city,@Field("key") String key);

    @GET("/v3/weather/weatherInfo")
    Call getWeather(@Query("city") String city,@Query("key") String key);

}
