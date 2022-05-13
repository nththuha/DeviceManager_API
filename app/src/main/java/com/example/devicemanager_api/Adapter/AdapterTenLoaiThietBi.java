package com.example.devicemanager_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.R;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;

import java.util.List;

public class AdapterTenLoaiThietBi extends ArrayAdapter<LoaiThietBiEntity> {
    public AdapterTenLoaiThietBi(@NonNull Context context, int resource, @NonNull List<LoaiThietBiEntity> object) {
        super(context, resource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinnermaloaitb, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelected_maloaitb);
        LoaiThietBiEntity loaiThietBi = (LoaiThietBiEntity) this.getItem(position);
        if(loaiThietBi != null){
            tvSelected.setText(loaiThietBi.getTenLoaiTB());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thietbi_spinner, parent, false);
        TextView tvThietBi = convertView.findViewById(R.id.tvThietBi);
        LoaiThietBiEntity loaiThietBi = (LoaiThietBiEntity) this.getItem(position);
        if(loaiThietBi != null){
            tvThietBi.setText(loaiThietBi.getTenLoaiTB());
        }
        return convertView;
    }
}
