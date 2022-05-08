package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devicemanager_api.API.PhongHocAPI;
import com.example.devicemanager_api.Adapter.AdapterPhongHoc;
import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhongHocActivity extends AppCompatActivity {

    ListView lvPH;
    ImageButton imbBack, imbThemPH, imbSua, imbXoa;
    SearchView svPH;
    Button btnLuu, btnThoat;
    EditText txtMaPhong, txtLoaiPhong, txtTang;
    Spinner snPH;
    AdapterPhongHoc adapterPhongHoc;
//    AdapterLoaiPhong adapterLoaiPhong;
    public static ArrayList<PhongHocEntity> DSPH;
    ArrayList<PhongHocEntity> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        setControl();
        setEvent();
    }

    private void setEvent() {
        layDSPhongHoc();
    }

    private void layDSPhongHoc() {
        PhongHocAPI.apiPhonghocService.layDSPhongHoc().enqueue(new Callback<List<PhongHocEntity>>() {
            @Override
            public void onResponse(Call<List<PhongHocEntity>> call, Response<List<PhongHocEntity>> response) {
                DSPH = (ArrayList<PhongHocEntity>) response.body();
                loadListView();
            }

            @Override
            public void onFailure(Call<List<PhongHocEntity>> call, Throwable t) {
                Toast.makeText(PhongHocActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
        lvPH = findViewById(R.id.lvPH);
        imbBack = findViewById(R.id.imbBack);
        imbThemPH = findViewById(R.id.imbThemPH);
        imbSua = findViewById(R.id.imbSua);
        imbXoa = findViewById(R.id.imbXoa);
        svPH = findViewById(R.id.svPH);
        snPH = findViewById(R.id.snPH);
    }

    public void loadListView(){
        adapterPhongHoc = new AdapterPhongHoc(this, R.layout.layout_phong_hoc, DSPH);
        lvPH.setAdapter(adapterPhongHoc);
    }
}