package com.example.devicemanager_api.API;

import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public interface LoaiThietBiAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    LoaiThietBiAPI apiLoaiThietBiService = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(LoaiThietBiAPI.class);

    @GET("loaithietbi")
    Call<List<LoaiThietBiEntity>> layDSLoaiThietBi();

    @POST("loaithietbi")
    Call<LoaiThietBiEntity> themLoaiThietBi(@Body LoaiThietBiEntity loaiThietBiEntity);

    @PUT("loaithietbi")
    Call<LoaiThietBiEntity> suaLoaiThietBi(@Body LoaiThietBiEntity loaiThietBiEntity);

    @DELETE("loaithietbi/{maLoaiTB}")
    Call<Void> xoaLoaiThietBi(@Path("maLoaiTB") String maLoaiTB);
}
