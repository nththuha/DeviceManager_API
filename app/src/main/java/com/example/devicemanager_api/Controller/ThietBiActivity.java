package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterThietBi;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThietBiActivity extends AppCompatActivity {

    ImageButton imbBack,imbThemTB;
    public static ArrayList<ThietBiEntity> thietBis;
    AdapterThietBi adapterThietBi;
//    AdapterTenLoaiThietBi adapterTenLoaiThietBi;
    ArrayList<ThietBiEntity> filter;
    ListView lvTb;
    Spinner snTB, spTenloaiTB;
    EditText txtMaTB, txtTenTB, txtXuatxu, txtSoluong;
    Button btnThoatTB, btnLuuTB;
    String maloaiTB;
    SearchView svTB;
    TextView tvTieuDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        layDSThietBi();
    }

    private void layDSThietBi() {
        ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                thietBis = (ArrayList<ThietBiEntity>) response.body();
                loadListView();
            }

            @Override
            public void onFailure(Call<List<ThietBiEntity>> call, Throwable t) {
                Toast.makeText(ThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadListView(){
        adapterThietBi = new AdapterThietBi(this, R.layout.layout_thiet_bi, thietBis);
        lvTb.setAdapter(adapterThietBi);
    }

    private void setControl() {
//        ThietBi tb = new ThietBi("TB1","Loa","Anh","5","DC");
//        dbThietBi.themThietBi(tb);
        lvTb = findViewById(R.id.lvTB);
        snTB = findViewById(R.id.snTB);
        imbBack = findViewById(R.id.imbBackTB);
        svTB = findViewById(R.id.svTB);
        imbThemTB = findViewById(R.id.imbThemTB);
    }
}