package com.example.devicemanager_api.API;

import com.example.devicemanager_api.Entity.ChiTietDatEntity;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
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

public interface ChiTietDatAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ChiTietDatAPI apiChiTietDatService = new Retrofit
            .Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ChiTietDatAPI.class);

    @GET("chitietdat")
    Call<List<ChiTietDatEntity>> layDSChiTietDat();

    @GET("chitietdat/tb")
    Call<List<ChiTietDatEntity>> layDSTheoMaTBD(@Query("maTB") String maTB);

    @GET("chitietdat/ph")
    Call<List<ChiTietDatEntity>> layDSTheoMaPHD(@Query("maPhong") String maPhong);

    @POST("chitietdat")
    Call<ChiTietSDEntity> themChiTietDat(@Body ChiTietDatEntity chiTietDatEntity);

    @PUT("chitietdat")
    Call<ChiTietSDEntity> suaChiTietDat(@Body ChiTietDatEntity chiTietDatEntity);

    @DELETE("chitietdat/{idCTD}")
    Call<Void> xoaChiTietDat(@Path("idCTD") Integer idCTD);

}
