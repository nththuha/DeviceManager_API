package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.devicemanager_api.API.ChiTietThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterChiTietThietBi;
import com.example.devicemanager_api.Adapter.AdapterThietBi;
import com.example.devicemanager_api.Entity.ChiTietTBEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietThietBiActivity extends AppCompatActivity {
    ImageView imbBack;
    AdapterChiTietThietBi adapterChiTietThietBi;
    public static ArrayList<ChiTietTBEntity> listCTTB;
    ListView lv;
    ProgressBar pbLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_thiet_bi);
        setControl();
        setEvent();
    }

    public void loadListView(List<ChiTietTBEntity> list){
        adapterChiTietThietBi = new AdapterChiTietThietBi(this, R.layout.layout_chi_tiet_thiet_bi, listCTTB);
        lv.setAdapter(adapterChiTietThietBi);
    }

    private List<ChiTietTBEntity> locDL(List<ChiTietTBEntity> list){
        Intent intent = getIntent();
        ThietBiEntity thietBi = (ThietBiEntity) intent.getSerializableExtra("thietbi");
        listCTTB = new ArrayList<>();
        for(ChiTietTBEntity a : list){
            if(a.getMaThietBi().equals(thietBi.getMaTB())){
                listCTTB.add(a);
            }
        }
        return listCTTB;
    }

    public void layDSCCTB(){
        ChiTietThietBiAPI.apiChiTietThietBiService.layDSChiTietThietBi().enqueue(new Callback<List<ChiTietTBEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietTBEntity>> call, Response<List<ChiTietTBEntity>> response) {
               loadListView(locDL(response.body()));
                pbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ChiTietTBEntity>> call, Throwable t) {
                Toast.makeText(ChiTietThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setEvent() {
        layDSCCTB();
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setControl() {
        imbBack = findViewById(R.id.imbBackCTTB);
        pbLoad = findViewById(R.id.pbLoad);
        lv = findViewById(R.id.lvCTTB);
    }
}