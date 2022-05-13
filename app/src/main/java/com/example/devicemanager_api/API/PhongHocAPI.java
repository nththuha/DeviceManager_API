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
import retrofit2.http.Query;

public interface PhongHocAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PhongHocAPI apiPhonghocService = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(PhongHocAPI.class);

    @GET("phonghoc")
    Call<List<PhongHocEntity>> layDSPhongHoc();

    @GET("phonghoc/ph")
    Call<PhongHocEntity> layPhongHoc(@Query("maPhong") String maPhong);

    @POST("phonghoc")
    Call<PhongHocEntity> themPhongHoc(@Body PhongHocEntity phongHocEntity);

    @PUT("phonghoc")
    Call<PhongHocEntity> suaPhongHoc(@Body PhongHocEntity phongHocEntity);

    @DELETE("phonghoc/{maPhong}")
    Call<Void> xoaPhongHoc(@Path("maPhong") String maPhong);
}
