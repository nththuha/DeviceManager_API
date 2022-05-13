package com.example.devicemanager_api.API;

import com.example.devicemanager_api.Entity.ChiTietTBEntity;
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

public interface ChiTietThietBiAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ChiTietThietBiAPI apiChiTietThietBiService = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ChiTietThietBiAPI.class);

    @GET("chitietthietbi")
    Call<List<ChiTietTBEntity>> layDSChiTietThietBi();

    @POST("chitietthietbi")
    Call<ChiTietTBEntity> themChiTietThietBi(@Body ChiTietTBEntity chiTietTBEntity);

    @PUT("chitietthietbi")
    Call<ChiTietTBEntity> suaChitietThietBi(@Body ChiTietTBEntity chiTietTBEntity);

    @DELETE("chitietthietbi/{maThietBi}")
    Call<Void> xoaChiTietThietBi(@Path("maThietBi") String maThietBi);

}
