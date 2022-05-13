package com.example.devicemanager_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;

public class AdapterMaTB extends ArrayAdapter<ThietBiEntity> {
    Context context;
    int resource;
    ArrayList<ThietBiEntity> data;
    public AdapterMaTB(@NonNull Context context, int resource, @NonNull ArrayList<ThietBiEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.data =  data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_matb_ctsd, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tvSelectedMaThietBi);
        ThietBiEntity thietBi = (ThietBiEntity) this.getItem(position);
        if (thietBi != null)
            tvSelected.setText(thietBi.getMaTB());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_matb, parent, false);
        TextView tvThietBi = convertView.findViewById(R.id.tvMaThietBiSpinner);
        ThietBiEntity thietBi = (ThietBiEntity) this.getItem(position);
        if (thietBi != null)
            tvThietBi.setText(thietBi.getMaTB());
        return convertView;
    }
}
