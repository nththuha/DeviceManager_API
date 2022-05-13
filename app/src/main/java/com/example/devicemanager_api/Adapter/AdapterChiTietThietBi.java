package com.example.devicemanager_api.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Controller.ChiTietThietBiActivity;
import com.example.devicemanager_api.Controller.ThietBiActivity;
import com.example.devicemanager_api.Controller.ThongTinThietBiActivity;
import com.example.devicemanager_api.Entity.ChiTietTBEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterChiTietThietBi extends ArrayAdapter<ChiTietTBEntity> {
    Context context;
    int resource;
    ArrayList<ChiTietTBEntity> data;
    TextView tvMatb,tvMaCTTB,tvTrangthai;


    public AdapterChiTietThietBi(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietTBEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    public void setFilterList(ArrayList<ChiTietTBEntity> filter){
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
        tvMatb = convertView.findViewById(R.id.tvMaTB_2);
        tvMaCTTB = convertView.findViewById(R.id.tvMaCTTB);
        tvTrangthai = convertView.findViewById(R.id.tvTrangthai);
        ChiTietTBEntity chiTietTB = data.get(position);
        tvMatb.setText(chiTietTB.getMaThietBi());
        tvMaCTTB.setText(chiTietTB.getIdCTTB()+"");
        tvTrangthai.setText(chiTietTB.getTinhTrang());
        return convertView;
    }
}
