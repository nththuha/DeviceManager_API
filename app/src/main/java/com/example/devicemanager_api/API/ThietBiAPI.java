package com.example.devicemanager_api.API;

import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThietBiAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ThietBiAPI apiThietBiService = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ThietBiAPI.class);

    @GET("thietbi")
    Call<List<ThietBiEntity>> layDSThietBi();

    @POST("thietbi")
    Call<ThietBiEntity> themThietBi(@Body ThietBiEntity thietBiEntity);

    @PUT("thietbi")
    Call<ThietBiEntity> suaThietBi(@Body ThietBiEntity thietBiEntity);

    @DELETE("thietbi/{maTB}")
    Call<Void> xoaThietBi(@Path("maTB") String maTB);
}
