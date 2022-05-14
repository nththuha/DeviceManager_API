package com.example.devicemanager_api.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterChiTietSD extends ArrayAdapter<ChiTietSDEntity> {
    Context context;
    int resource;
    int sumSL = 0;
    ArrayList<ChiTietSDEntity> data;
    ThietBiEntity thietBi;
    TextView tvMaPhongSD, tvMaTBSD, tvSoLuongSD;


    public AdapterChiTietSD(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietSDEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public void setFilterList(ArrayList<ChiTietSDEntity> filter) {
        this.data = filter;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        tvMaPhongSD = convertView.findViewById(R.id.tvMaPhongSD);
        tvMaTBSD = convertView.findViewById(R.id.tvMaTBSD);
        tvSoLuongSD = convertView.findViewById(R.id.tvSoLuongSD);

        ChiTietSDEntity chiTietSD = data.get(position);
        /*ThietBiAPI.apiThietBiService.layThietBi(chiTietSD.getMaTB()).enqueue(new Callback<ThietBiEntity>() {
            @Override
            public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                thietBi = new ThietBiEntity();
                if(response.isSuccessful()){
                    thietBi = response.body();
                    sumSL = thietBi.getSoLuong();
                    Log.d("SumSL", "Sum = "+sumSL);
                    tvSoLuongSD.setText(chiTietSD.getSoLuongSD() + "/"+sumSL);
                }
            }
            @Override
            public void onFailure(Call<ThietBiEntity> call, Throwable t) {

            }
        });*/
        tvMaPhongSD.setText(chiTietSD.getMaPhong());
        tvMaTBSD.setText(chiTietSD.getMaTB());
        tvSoLuongSD.setText(chiTietSD.getSoLuongSD()+"");
        return convertView;
    }

}