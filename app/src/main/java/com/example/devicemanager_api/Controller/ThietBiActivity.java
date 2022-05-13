package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanager_api.API.ChiTietThietBiAPI;
import com.example.devicemanager_api.API.LoaiThietBiAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterTenLoaiThietBi;
import com.example.devicemanager_api.Adapter.AdapterThietBi;
import com.example.devicemanager_api.Entity.ChiTietTBEntity;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.example.devicemanager_api.Entity.TaiKhoanEntity;
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
    AdapterTenLoaiThietBi adapterTenLoaiThietBi;
    ArrayList<ThietBiEntity> filter;
    ListView lvTb;
    ProgressBar pbLoad;
    Spinner snTB, spTenloaiTB;
    EditText txtMaTB, txtTenTB, txtXuatxu, txtSoluong;
    Button btnThoatTB, btnLuuTB;
    String maloaiTB;
    SearchView svTB;
    TextView tvTieuDe;
    public static List<ChiTietTBEntity> list;
    public static Integer max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);
        setControl();
        setEvent();

    }

    private void setEvent() {
        layDSThietBi();
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imbThemTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Them();
            }
        });

        svTB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter(newText);
                return false;
            }
        });
    }

    public void layDSThietBi() {
        ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                thietBis = (ArrayList<ThietBiEntity>) response.body();
                loadListView();
                pbLoad.setVisibility(View.GONE);
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
    private void getFilter(String s){
        filter = new ArrayList<>();
        for (ThietBiEntity tb: thietBis) {
            if(tb.getTenTB().toLowerCase().contains(s.toLowerCase())){
                filter.add(tb);
            }
        }
        adapterThietBi.setFilterList(filter);
        if(filter.isEmpty()){
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            /*new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();*/
        }
    }
    public void Them() {
        LoaiThietBiAPI.apiLoaiThietBiService.layDSLoaiThietBi().enqueue(new Callback<List<LoaiThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<LoaiThietBiEntity>> call, Response<List<LoaiThietBiEntity>> response) {
                List loaiTB = response.body();
                dialogThemThietBi(Gravity.CENTER, loaiTB);
            }

            @Override
            public void onFailure(Call<List<LoaiThietBiEntity>> call, Throwable t) {
                Toast.makeText(ThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ThemCTTB_API(ChiTietTBEntity chiTietTBEntity){
        ChiTietThietBiAPI.apiChiTietThietBiService.themChiTietThietBi(chiTietTBEntity).enqueue(new Callback<ChiTietTBEntity>() {
            @Override
            public void onResponse(Call<ChiTietTBEntity> call, Response<ChiTietTBEntity> response) {
            }

            @Override
            public void onFailure(Call<ChiTietTBEntity> call, Throwable t) {
                Toast.makeText(ThietBiActivity.this, "Xảy ra lổi ở Chi Tiết Thiết Bị", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogThemThietBi(int gravity, List loaiTB) {
        final Dialog dialog = new Dialog(ThietBiActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_them_thietbi);
        Window window = dialog.getWindow();

        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(false);
        }else{
            dialog.setCancelable(false);
        }
        tvTieuDe = dialog.findViewById(R.id.tvTieuDeTB);
        tvTieuDe.setText("THÊM THIỂT BỊ");
        txtMaTB = dialog.findViewById(R.id.txtMaTB);
        txtTenTB = dialog.findViewById(R.id.txtTenTB);
        txtXuatxu = dialog.findViewById(R.id.txtXuatxu);
        txtSoluong = dialog.findViewById(R.id.txtSoluongTB);
        txtTenTB = dialog.findViewById(R.id.txtTenTB);
        btnThoatTB = dialog.findViewById(R.id.btnThoatTB);
        btnLuuTB = dialog.findViewById(R.id.btnLuuTB);
        spTenloaiTB = dialog.findViewById(R.id.spTenloaiTB);
        adapterTenLoaiThietBi = new AdapterTenLoaiThietBi(getApplicationContext(),R.layout.item_selected_spinnermaloaitb,loaiTB);
        spTenloaiTB.setAdapter(adapterTenLoaiThietBi);
        spTenloaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloaiTB = adapterTenLoaiThietBi.getItem(i).getMaLoaiTB().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnLuuTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTB = txtMaTB.getText().toString();
                String tenTB = txtTenTB.getText().toString();
                String xuatxu = txtXuatxu.getText().toString();
                Integer soluong = Integer.parseInt(txtSoluong.getText().toString());

                if(maTB.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Mã thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(tenTB.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Tên thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }if(xuatxu.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Xuất xứ không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }if(soluong.equals("")){
                    Toast.makeText(ThietBiActivity.this, "Số lượg không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                thongBaoThanhCong(Gravity.CENTER,"Thêm thiết bị thành công!");
                themThietBi(new ThietBiEntity(maTB, tenTB, xuatxu,soluong,maloaiTB));
                themCTTB(soluong,maTB);

                dialog.dismiss();
            }
        });
        btnThoatTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    private void findIDMax(){
        max=0;
        for(ChiTietTBEntity a : list){
            if(a.getIdCTTB() > max){
                max = a.getIdCTTB();
            }
        }
    }
    public void themCTTB(Integer soluong, String maTB){
        ChiTietThietBiAPI.apiChiTietThietBiService.layDSChiTietThietBi().enqueue(new Callback<List<ChiTietTBEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietTBEntity>> call, Response<List<ChiTietTBEntity>> response) {
                list = response.body();
                findIDMax();
                Integer dem = max;
                for(int i = 0;i<soluong;i++){
                    dem = dem+1;
                    ChiTietTBEntity chiTietTBEntity = new ChiTietTBEntity(dem,"mới",maTB);
                    ThemCTTB_API(chiTietTBEntity);
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietTBEntity>> call, Throwable t) {
                Toast.makeText(ThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themThietBi(ThietBiEntity thietBiEntity){
        ThietBiAPI.apiThietBiService.themThietBi(thietBiEntity).enqueue(new Callback<ThietBiEntity>() {
            @Override
            public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                if(response.isSuccessful()){
                    thongBaoThanhCong(Gravity.CENTER, "Thêm thiết bị thành công!");
                    layDSThietBi();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ThietBiEntity> call, Throwable t) {
                Toast.makeText(ThietBiActivity.this, "Thêm thiết bị thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl() {
        lvTb = findViewById(R.id.lvTB);
        pbLoad = findViewById(R.id.pbLoad);
        snTB = findViewById(R.id.snTB);
        imbBack = findViewById(R.id.imbBackTB);
        svTB = findViewById(R.id.svTB);
        imbThemTB = findViewById(R.id.imbThemTB);
    }
    private void thongBaoThanhCong(int gravity, String text) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tbthanhcong);

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
        TextView tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText(text);
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        layDSThietBi();
    }
}