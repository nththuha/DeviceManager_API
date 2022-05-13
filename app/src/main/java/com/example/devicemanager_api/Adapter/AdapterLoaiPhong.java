package com.example.devicemanager_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.Entity.LoaiPhong;
import com.example.devicemanager_api.R;

import java.util.List;

public class AdapterLoaiPhong extends ArrayAdapter<LoaiPhong> {
    public AdapterLoaiPhong(@NonNull Context context, int resource, @NonNull List<LoaiPhong> object) {
        super(context, resource, object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelected);
        LoaiPhong lp = (LoaiPhong) this.getItem(position);
        if(lp != null){
            tvSelected.setText(lp.getLoai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phonghoc_spinner, parent, false);
        TextView tvPHoc = convertView.findViewById(R.id.tvPHoc);
        LoaiPhong lp = (LoaiPhong) this.getItem(position);
        if(lp != null){
            tvPHoc.setText(lp.getLoai());
        }
        return convertView;
    }
}
