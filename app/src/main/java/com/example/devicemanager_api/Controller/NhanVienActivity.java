package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.devicemanager_api.API.LoaiThietBiAPI;
import com.example.devicemanager_api.API.TaiKhoanAPI;
import com.example.devicemanager_api.Adapter.AdapterNhanVien;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.example.devicemanager_api.Entity.TaiKhoanEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienActivity extends AppCompatActivity {

    ListView lvNV;
    ImageButton imbBack;
    SearchView svNV;
    AdapterNhanVien adapterNhanVien;
    ArrayList<TaiKhoanEntity> DSNV;
    ArrayList<TaiKhoanEntity> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        setContentView(R.layout.activity_nhan_vien);
        setControl();
        setEvent();
    }

    private void setEvent() {
        layDSNhanVien();

        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        svNV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter(s);
                return false;
            }
        });
    }

    public void layDSNhanVien() {
        TaiKhoanAPI.apiTaiKhoanService.layDSTaiKhoan().enqueue(new Callback<List<TaiKhoanEntity>>() {
            @Override
            public void onResponse(Call<List<TaiKhoanEntity>> call, Response<List<TaiKhoanEntity>> response) {
                DSNV = (ArrayList<TaiKhoanEntity>) response.body();
                adapterNhanVien = new AdapterNhanVien(NhanVienActivity.this, R.layout.layout_nhan_vien, DSNV);
                lvNV.setAdapter(adapterNhanVien);
            }

            @Override
            public void onFailure(Call<List<TaiKhoanEntity>> call, Throwable t) {
                Toast.makeText(NhanVienActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        String temp = "";
        for (TaiKhoanEntity nv: DSNV) {
            temp = nv.getHo() + " " + nv.getTen();
            if(temp.toLowerCase().contains(s.toLowerCase())){
                filter.add(nv);
            }
        }
        adapterNhanVien.setFilterList(filter);
        if(filter.isEmpty()){
             Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
//            new AlertDialog.Builder(this)
//                    .setTitle("Thông báo")
//                    .setMessage("Không có dữ liệu để hiển thị!")
//                    .setCancelable(true)
//                    .show();
        }
    }

    private void setControl() {
        lvNV = findViewById(R.id.lvNV);
        imbBack = findViewById(R.id.imbBack);
        svNV = findViewById(R.id.svNV);
    }
}