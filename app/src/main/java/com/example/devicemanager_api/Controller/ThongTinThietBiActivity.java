package com.example.devicemanager_api.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietThietBiAPI;
import com.example.devicemanager_api.API.LoaiThietBiAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterTenLoaiThietBi;
import com.example.devicemanager_api.Entity.ChiTietTBEntity;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinThietBiActivity extends AppCompatActivity {

    Spinner spTenloaiTB;
    TextView txtMaTB, txtTenTB, txtXuatxu, txtSoluong;
    Button btnCapNhatTB;
    AdapterTenLoaiThietBi adapterTenLoaiThietBi;
    String maloaiTB;
    ImageView imbBack;
    TextView tvTieuDeTB;
    Integer slbandau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thiet_bi);
        setControl();
        setEvent();
        LoadChiTietTB();
    }

    public void LoadChiTietTB() {
        LoaiThietBiAPI.apiLoaiThietBiService.layDSLoaiThietBi().enqueue(new Callback<List<LoaiThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<LoaiThietBiEntity>> call, Response<List<LoaiThietBiEntity>> response) {
                List loaiTB = response.body();
                loadDL(loaiTB);
            }

            @Override
            public void onFailure(Call<List<LoaiThietBiEntity>> call, Throwable t) {
                Toast.makeText(ThongTinThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDL(List loaiTB) {
        Intent intent = getIntent();
        ThietBiEntity thietBi = (ThietBiEntity) intent.getSerializableExtra("thietbi");
        tvTieuDeTB.setText("THÔNG TIN THIẾT BỊ: " + thietBi.getTenTB());
        txtMaTB.setText(thietBi.getMaTB());
        txtMaTB.setEnabled(false);
        txtTenTB.setText(thietBi.getTenTB());
        txtXuatxu.setText(thietBi.getXuatXu());
        txtSoluong.setText(thietBi.getSoLuong() + "");
        slbandau = thietBi.getSoLuong();
        adapterTenLoaiThietBi = new AdapterTenLoaiThietBi(getApplicationContext(), R.layout.item_selected_spinnermaloaitb, loaiTB);
        spTenloaiTB.setAdapter(adapterTenLoaiThietBi);
        ArrayList<LoaiThietBiEntity> loaiThietBis = (ArrayList<LoaiThietBiEntity>) loaiTB;
        for (int i = 0; i < loaiThietBis.size(); i++) {
            if (loaiThietBis.get(i).getMaLoaiTB().trim().equals(thietBi.getMaLoaiTB().trim())) {
                spTenloaiTB.setSelection(i);
            }
        }

    }

    private void setEvent() {
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spTenloaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloaiTB = adapterTenLoaiThietBi.getItem(i).getMaLoaiTB().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCapNhatTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTB = txtMaTB.getText().toString();
                String tenTB = txtTenTB.getText().toString();
                String xuatxu = txtXuatxu.getText().toString();
                String soluong = txtSoluong.getText().toString();
                if (tenTB.equals("")) {
                    Toast.makeText(ThongTinThietBiActivity.this, "Tên thiết bị không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (xuatxu.equals("")) {
                    Toast.makeText(ThongTinThietBiActivity.this, "Xuất xứ không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (soluong.equals("")) {
                    Toast.makeText(ThongTinThietBiActivity.this, "Số lượng không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(soluong) < slbandau) {
                    Toast.makeText(ThongTinThietBiActivity.this, "Số lượng phải lớn hơn hoặc bằng số lượng ban đầu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                suaThietBi(new ThietBiEntity(maTB, tenTB, xuatxu, Integer.parseInt(soluong), maloaiTB));
                tvTieuDeTB.setText("THÔNG TIN THIẾT BỊ: " + tenTB);
                Integer slthaydoi = Integer.parseInt(soluong) - slbandau;
                if (slthaydoi == 0) return;
                themCTTB(slthaydoi, maTB);
            }
        });
    }

    private void suaThietBi(ThietBiEntity thietbi) {
        ThietBiAPI.apiThietBiService.suaThietBi(thietbi).enqueue(new Callback<ThietBiEntity>() {
            @Override
            public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                thongBaoThanhCong(Gravity.CENTER, "Cập nhật thành công " + thietbi.getTenTB() + "!");
            }

            @Override
            public void onFailure(Call<ThietBiEntity> call, Throwable t) {
                Toast.makeText(ThongTinThietBiActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void themCTTB(Integer soluong, String maTB) {
        ChiTietThietBiAPI.apiChiTietThietBiService.layDSChiTietThietBi().enqueue(new Callback<List<ChiTietTBEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietTBEntity>> call, Response<List<ChiTietTBEntity>> response) {
                List<ChiTietTBEntity> list = new ArrayList<>();
                list = response.body();
                Integer dem = list.get((list.size() - 1)).getIdCTTB();
                for (int i = 0; i < soluong; i++) {
                    dem = dem + 1;
                    ChiTietTBEntity chiTietTBEntity = new ChiTietTBEntity(dem, "mới", maTB);
                    ThemCTTB_API(chiTietTBEntity);
                }
            }

            public void ThemCTTB_API(ChiTietTBEntity chiTietTBEntity) {
                ChiTietThietBiAPI.apiChiTietThietBiService.themChiTietThietBi(chiTietTBEntity).enqueue(new Callback<ChiTietTBEntity>() {
                    @Override
                    public void onResponse(Call<ChiTietTBEntity> call, Response<ChiTietTBEntity> response) {
                    }

                    @Override
                    public void onFailure(Call<ChiTietTBEntity> call, Throwable t) {
                        Toast.makeText(ThongTinThietBiActivity.this, "Xảy ra lổi ở Chi Tiết Thiết Bị", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ChiTietTBEntity>> call, Throwable t) {
                Toast.makeText(ThongTinThietBiActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void setControl() {
        txtMaTB = findViewById(R.id.txtMaTB);
        txtTenTB = findViewById(R.id.txtTenTB);
        txtXuatxu = findViewById(R.id.txtXuatxu);
        txtSoluong = findViewById(R.id.txtSoluongTB);
        txtTenTB = findViewById(R.id.txtTenTB);
        btnCapNhatTB = findViewById(R.id.btnLuuTB);
        spTenloaiTB = findViewById(R.id.spTenloaiTB);
        imbBack = findViewById(R.id.imbBackCTTB);
        tvTieuDeTB = findViewById(R.id.tvTieuDeTB);
    }

}