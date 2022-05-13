package com.example.devicemanager_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.R;

import java.util.List;

public class AdapterMaPhong extends ArrayAdapter<PhongHocEntity> {
    Context context;
    int resource;
    List<PhongHocEntity> data;
    public AdapterMaPhong(@NonNull Context context, int resource, @NonNull List<PhongHocEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_maphong_ctsd, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelectedMaPhong);
        PhongHocEntity phongHoc = (PhongHocEntity) this.getItem(position);
        if (phongHoc != null) {
            tvSelected.setText(phongHoc.getMaPhong());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_maphong, parent, false);
        TextView tvPhongHoc = convertView.findViewById(R.id.tvMaPhongSpinner);
        PhongHocEntity phongHoc = (PhongHocEntity) this.getItem(position);
        if (phongHoc != null) {
            tvPhongHoc.setText(phongHoc.getMaPhong());
        }
        return convertView;
    }
}
