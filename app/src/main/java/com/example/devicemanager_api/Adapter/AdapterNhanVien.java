package com.example.devicemanager_api.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.Entity.TaiKhoanEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;

public class AdapterNhanVien extends ArrayAdapter<TaiKhoanEntity> {
    Context context;
    int resource;
    ArrayList<TaiKhoanEntity> data;

    public AdapterNhanVien(@NonNull Context context, int resource, @NonNull ArrayList<TaiKhoanEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public void setFilterList(ArrayList<TaiKhoanEntity> filter){
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
        TextView tvMaNV = convertView.findViewById(R.id.tvMaNV);
        TextView tvTenNV = convertView.findViewById(R.id.tvTenNV);
        RelativeLayout rlItemClick = convertView.findViewById(R.id.rlItemClick);
        TaiKhoanEntity nhanVien = data.get(position);
        tvMaNV.setText(nhanVien.getMaTaiKhoan());
        tvTenNV.setText(nhanVien.getHo() + " " + nhanVien.getTen());
        rlItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThongTin(Gravity.CENTER, nhanVien);
            }
        });
        return convertView;
    }

    private void hienThongTin(int gravity, TaiKhoanEntity nhanVien){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_thongtin_nhanvien);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }

        TextView tvMaNV = dialog.findViewById(R.id.tvMaNV);
        TextView tvTenNV = dialog.findViewById(R.id.tvTenNV);
        TextView tvEmail = dialog.findViewById(R.id.tvEmail);
        TextView tvLoai = dialog.findViewById(R.id.tvLoai);

        tvMaNV.setText(nhanVien.getMaTaiKhoan());
        tvTenNV.setText(nhanVien.getHo() + " " + nhanVien.getTen());
        tvEmail.setText(nhanVien.getEmail());
        tvLoai.setText(nhanVien.getLoai());

        dialog.show();
    }


}
