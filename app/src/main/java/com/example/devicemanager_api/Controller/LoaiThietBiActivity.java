package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.devicemanager_api.API.LoaiThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterLoaiThietBi;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoaiThietBiActivity extends AppCompatActivity {

    ListView lvLTB;
    ImageButton imbThemLTB, imbBack;
    SearchView svLTB;
    Button btnLuu, btnThoat;
    EditText txtTenLTB, txtMaLTB;

    AdapterLoaiThietBi adapterLoaiThietBi;
    public static ArrayList<LoaiThietBiEntity> DSLTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_thiet_bi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        layDSLoaiThietBi();
    }

    public void loadListView(){
        adapterLoaiThietBi = new AdapterLoaiThietBi(this, R.layout.layout_loai_thiet_bi, DSLTB);
        lvLTB.setAdapter(adapterLoaiThietBi);
    }

    private void layDSLoaiThietBi() {
        LoaiThietBiAPI.apiLoaiThietBiService.layDSLoaiThietBi().enqueue(new Callback<List<LoaiThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<LoaiThietBiEntity>> call, Response<List<LoaiThietBiEntity>> response) {
                DSLTB = (ArrayList<LoaiThietBiEntity>) response.body();
                loadListView();
            }

            @Override
            public void onFailure(Call<List<LoaiThietBiEntity>> call, Throwable t) {
                Toast.makeText(LoaiThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
        lvLTB = findViewById(R.id.lvLTB);
        imbThemLTB = findViewById(R.id.imbThemLTB);
        svLTB = findViewById(R.id.svLTB);
        imbBack = findViewById(R.id.imbBack);
    }
}