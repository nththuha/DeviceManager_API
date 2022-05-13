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

import com.example.devicemanager_api.API.PhongHocAPI;
import com.example.devicemanager_api.Adapter.AdapterLoaiPhong;
import com.example.devicemanager_api.Adapter.AdapterPhongHoc;
import com.example.devicemanager_api.Entity.LoaiPhong;
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
    ProgressBar pbLoad;
    SearchView svPH;
    Button btnLuu, btnThoat;
    EditText txtMaPhong, txtLoaiPhong, txtTang;
    Spinner snPH;
    AdapterPhongHoc adapterPhongHoc;
    AdapterLoaiPhong adapterLoaiPhong;
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
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imbThemPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themPhongHoc(Gravity.CENTER);
            }
        });

        svPH.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        adapterLoaiPhong = new AdapterLoaiPhong(this, R.layout.item_selected_spinner, getloaiPhong());
        snPH.setAdapter(adapterLoaiPhong);
        snPH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String loai = adapterLoaiPhong.getItem(i).getLoai();
                if(!loai.equals("Chọn loại phòng")){
                    if (loai.equals("Lý thuyết")) {
                        filter = new ArrayList<>();
                        for (PhongHocEntity ph : DSPH) {
                            if (ph.getLoaiPhong().equals("Lý thuyết")) {
                                filter.add(ph);
                            }
                        }
                        adapterPhongHoc.setFilterList(filter);
                    } else {
                        filter = new ArrayList<>();
                        for (PhongHocEntity ph : DSPH) {
                            if (ph.getLoaiPhong().equals("Thực hành")) {
                                filter.add(ph);
                            }
                        }
                        adapterPhongHoc.setFilterList(filter);
                    }
                }
                else{
                    layDSPhongHoc();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        for (PhongHocEntity ph: DSPH) {
            if(ph.getMaPhong().toLowerCase().contains(s.toLowerCase())){
                filter.add(ph);
            }
        }
        adapterPhongHoc.setFilterList(filter);
        if(filter.isEmpty()){
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            /*new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();*/
        }
    }

    public void layDSPhongHoc() {
        PhongHocAPI.apiPhonghocService.layDSPhongHoc().enqueue(new Callback<List<PhongHocEntity>>() {
            @Override
            public void onResponse(Call<List<PhongHocEntity>> call, Response<List<PhongHocEntity>> response) {
                DSPH = (ArrayList<PhongHocEntity>) response.body();
                loadListView();
                pbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<PhongHocEntity>> call, Throwable t) {
                Toast.makeText(PhongHocActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void themPhongHoc(int gravity) {
        final Dialog dialog = new Dialog(PhongHocActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_them_sua_ph);

        Window window = dialog.getWindow();
        if (window == null) return;
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
        txtMaPhong = dialog.findViewById(R.id.txtMaPhong);
        txtLoaiPhong = dialog.findViewById(R.id.txtLoaiPhong);
        txtTang = dialog.findViewById(R.id.txtTang);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = txtMaPhong.getText().toString();
                String loaiPhong = txtLoaiPhong.getText().toString();
                String tang = txtTang.getText().toString();
                if (maPhong.equals("")) {
                    Toast.makeText(PhongHocActivity.this, "Mã phòng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if (loaiPhong.equals("")) {
                    Toast.makeText(PhongHocActivity.this, "Loại phòng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if (tang.equals("")) {
                    Toast.makeText(PhongHocActivity.this, "Tầng không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                String lp1 = loaiPhong.substring(0, 1);
                String lp2 = loaiPhong.substring(1, loaiPhong.length());
                loaiPhong = lp1.toUpperCase() + lp2.toLowerCase();
                themPhongHocvaoDB(new PhongHocEntity(maPhong, loaiPhong, Integer.parseInt(tang)));
//                layDSPhongHoc();
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

    private List getloaiPhong() {
        List<LoaiPhong> LP = new ArrayList<>();
        LP.add(new LoaiPhong("Chọn loại phòng"));
        LP.add(new LoaiPhong("Lý thuyết"));
        LP.add(new LoaiPhong("Thực hành"));
        return LP;
    }

    private void themPhongHocvaoDB(PhongHocEntity phongHocEntity) {
        PhongHocAPI.apiPhonghocService.themPhongHoc(phongHocEntity).enqueue(new Callback<PhongHocEntity>() {
            @Override
            public void onResponse(Call<PhongHocEntity> call, Response<PhongHocEntity> response) {
                if (response.isSuccessful()) {
                    thongBaoThanhCong(Gravity.CENTER, "Thêm phòng học " + phongHocEntity.getMaPhong() + " thành công!");
                    layDSPhongHoc();
                    return;
                }
            }

            @Override
            public void onFailure(Call<PhongHocEntity> call, Throwable t) {
                Toast.makeText(PhongHocActivity.this, "Thêm phòng học thất bại", Toast.LENGTH_SHORT).show();
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
        lvPH = findViewById(R.id.lvPH);
        pbLoad = findViewById(R.id.pbLoad);
        imbBack = findViewById(R.id.imbBack);
        imbThemPH = findViewById(R.id.imbThemPH);
        imbSua = findViewById(R.id.imbSua);
        imbXoa = findViewById(R.id.imbXoa);
        svPH = findViewById(R.id.svPH);
        snPH = findViewById(R.id.snPH);
    }

    public void loadListView() {
        adapterPhongHoc = new AdapterPhongHoc(this, R.layout.layout_phong_hoc, DSPH);
        lvPH.setAdapter(adapterPhongHoc);
    }
}