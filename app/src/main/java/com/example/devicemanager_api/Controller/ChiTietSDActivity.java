package com.example.devicemanager_api.Controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.Adapter.AdapterChiTietSD;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSDActivity extends AppCompatActivity {
    ListView lvChiTietSD;
    ProgressBar pbLoad;
    SearchView svCTSD;
    ImageButton imbBack;
    Button btnMuon, btnTra, btnHuyM, btnHuyT;

    AdapterChiTietSD adapterChiTietSD;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;
    ArrayList<ChiTietSDEntity> filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsd);
        setContro();
        setEvent();
    }
    public void loadListView(){
        adapterChiTietSD = new AdapterChiTietSD(this,R.layout.activity_item_chitietsd, chiTietSDs);
        lvChiTietSD.setAdapter(adapterChiTietSD);
    }

    private void setEvent() {
        setCallAPI();
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        svCTSD.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void getFilter(String s) {
        filter = new ArrayList<>();
        for(ChiTietSDEntity e : chiTietSDs){
            if(e.getMaTB().toLowerCase().contains(s.toLowerCase()) || e.getMaPhong().toLowerCase().contains(s.toLowerCase())){
                filter.add(e);
            }
        }
        adapterChiTietSD.setFilterList(filter);
        if (filter.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            /*new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();*/
        }
    }

    private void setContro() {
        lvChiTietSD = findViewById(R.id.lvChiTietSD);
        pbLoad = findViewById(R.id.pbLoad);
        svCTSD = findViewById(R.id.svCTSD);
        imbBack = findViewById(R.id.imbBackCTSD);
        btnMuon = findViewById(R.id.btnMuon);
        btnTra = findViewById(R.id.btnTra);
        btnHuyM = findViewById(R.id.btnHuyM);
        btnHuyT = findViewById(R.id.btnHuyT);
    }

    private void setCallAPI() {
        ChiTietSDAPI.apiChiTietSDService.layDSChiTietSD().enqueue(new Callback<List<ChiTietSDEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                Toast.makeText(ChiTietSDActivity.this, "Load chi tiết thành công!", Toast.LENGTH_SHORT).show();
                loadListView();
                pbLoad.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {
                Toast.makeText(ChiTietSDActivity.this, "Load chi tiết thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}