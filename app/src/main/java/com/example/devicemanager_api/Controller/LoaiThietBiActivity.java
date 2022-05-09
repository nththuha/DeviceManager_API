package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
        imbThemLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themLTB(Gravity.CENTER);
            }
        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loadListView(){
        adapterLoaiThietBi = new AdapterLoaiThietBi(this, R.layout.layout_loai_thiet_bi, DSLTB);
        lvLTB.setAdapter(adapterLoaiThietBi);
    }

    public void layDSLoaiThietBi() {
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

    private void themLTB(int gravity){
        final Dialog dialog = new Dialog(LoaiThietBiActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_themltb);

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
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }

        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnThoat = dialog.findViewById(R.id.btnThoat);
        txtMaLTB = dialog.findViewById(R.id.txtMaLTB);
        txtTenLTB = dialog.findViewById(R.id.txtTenLTB);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLTB = txtMaLTB.getText().toString();
                String tenLTB = txtTenLTB.getText().toString();
                if(maLTB.equals("")){
                    Toast.makeText(LoaiThietBiActivity.this, "Mã loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(tenLTB.equals("")){
                    Toast.makeText(LoaiThietBiActivity.this, "Tên loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                themLTBvaoDB(new LoaiThietBiEntity(maLTB, tenLTB));
                dialog.dismiss();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void themLTBvaoDB(LoaiThietBiEntity loaiThietBiEntity){
        LoaiThietBiAPI.apiLoaiThietBiService.themLoaiThietBi(loaiThietBiEntity).enqueue(new Callback<LoaiThietBiEntity>() {
            @Override
            public void onResponse(Call<LoaiThietBiEntity> call, Response<LoaiThietBiEntity> response) {
                if (response.isSuccessful()) {
                    thongBaoThanhCong(Gravity.CENTER,"Thêm thiết bị "+loaiThietBiEntity.getTenLoaiTB()+" thành công!");
                    layDSLoaiThietBi();
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoaiThietBiEntity> call, Throwable t) {
                Toast.makeText(LoaiThietBiActivity.this, "Thêm loại thiết bị thất bại", Toast.LENGTH_SHORT).show();
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
        lvLTB = findViewById(R.id.lvLTB);
        imbThemLTB = findViewById(R.id.imbThemLTB);
        svLTB = findViewById(R.id.svLTB);
        imbBack = findViewById(R.id.imbBack);
    }
}