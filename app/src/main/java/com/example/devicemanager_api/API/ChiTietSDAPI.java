package com.example.devicemanager_api.API;

import com.example.devicemanager_api.Controller.ChiTietSDActivity;
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

public interface ChiTietSDAPI {
    BaseURL baseUrl = new BaseURL();
    String url = baseUrl.getBaseURL();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ChiTietSDAPI apiChiTietSDService = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ChiTietSDAPI.class);

    @GET("chitietsd")
    Call<List<ChiTietSDEntity>> layDSChiTietSD();

    @GET("chitietsd/tb")
    Call<List<ChiTietSDEntity>> laySoLuongTheoMaTB(@Query("maTB") String maTB);

    @GET("chitietsd/ph")
    Call<List<ChiTietSDEntity>> laySoLuongTheoMaPH(@Query("maPhong") String maPhong);

    @POST("chitietsd")
    Call<ChiTietSDEntity> themChiTietSD(@Body ChiTietSDEntity chiTietSDEntity);

    @PUT("chitietsd")
    Call<ChiTietSDEntity> suaChiTietSD(@Body ChiTietSDEntity chiTietSDEntity);

    @DELETE("chitietsd/{idCTSD}")
    Call<Void> xoaChiTietSD(@Path("idCTSD") Integer idCTSD);

}
