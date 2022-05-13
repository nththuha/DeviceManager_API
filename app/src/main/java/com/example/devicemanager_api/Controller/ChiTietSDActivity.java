package com.example.devicemanager_api.Controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.API.PhongHocAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterChiTietSD;
import com.example.devicemanager_api.Adapter.AdapterMaPhong;
import com.example.devicemanager_api.Adapter.AdapterMaTB;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSDActivity extends AppCompatActivity {
    Context context;
    Date date;
    long millis = System.currentTimeMillis();

    ListView lvChiTietSD;
    LinearLayout llSoLuongMuon;
    Spinner spMaPhong,spMaThietBi;
    ProgressBar pbLoad;
    SearchView svCTSD;
    ImageButton imbBack;
    Button btnMuon, btnTra, btnHuyM, btnHuyT;
    Button btnMuonCTTB;
    EditText txtSoLuongSD;
    TextView tvMaPCTmuon,tvLoaiPCTmuon,tvMaTBCTmuon,tvTenTBCTmuon,tvSoLuongCTmuon,tvSoLuongCTconlai,tvNgayCTmuon,tvNgay,tvSoLuongDu;

    AdapterChiTietSD adapterChiTietSD;
    AdapterMaPhong adapterMaPhong;
    AdapterMaTB adapterMaTB;
    ThietBiEntity thietBi;
    ArrayList<ThietBiEntity> thietBis;
    PhongHocEntity phongHoc;
    ArrayList<PhongHocEntity> phongHocs;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;
    ArrayList<ChiTietSDEntity> filter;

    int tongsl = 0;
    int slmuon = 0;
    int sldu = 0;
    String maPhong = "";
    String maTB = "";

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

    public void getThietBi(String maTB){
        try {
            ThietBiAPI.apiThietBiService.layThietBi(maTB).enqueue(new Callback<ThietBiEntity>() {
                @Override
                public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                    thietBi = new ThietBiEntity();
                    if(response.isSuccessful()){
                        thietBi = response.body();
                        tvTenTBCTmuon.setText(thietBi.getTenTB());
                        loadListView();
                        ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(thietBi.getMaTB()).enqueue(new Callback<List<ChiTietSDEntity>>() {
                            @Override
                            public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                                chiTietSDs = new ArrayList<>();
                                if(response.isSuccessful()){
                                    chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                                    for(ChiTietSDEntity ctsd : chiTietSDs){
                                        slmuon += ctsd.getSoLuongSD();
                                    }
                                    Log.d("slmmm", "onResponse: "+slmuon);
                                    tongsl = thietBi.getSoLuong();
                                    sldu = tongsl - slmuon;
                                    tvSoLuongCTconlai.setText(sldu+"");
                                }
                            }
                            @Override
                            public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ThietBiEntity> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public void getPhongHoc(String maPhong){
        try {
            PhongHocAPI.apiPhonghocService.layPhongHoc(maPhong).enqueue(new Callback<PhongHocEntity>() {
                @Override
                public void onResponse(Call<PhongHocEntity> call, Response<PhongHocEntity> response) {
                    phongHoc = new PhongHocEntity();
                    if(response.isSuccessful()){
                        phongHoc = response.body();
                        tvLoaiPCTmuon.setText(phongHoc.getLoaiPhong());
                        loadListView();
                    }
                }

                @Override
                public void onFailure(Call<PhongHocEntity> call, Throwable t) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

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
        lvChiTietSD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogChiTietSD(Gravity.CENTER,i);
            }
        });
        btnMuonCTTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMuonTB(Gravity.CENTER);
            }
        });
    }

    private void dialogMuonTB(int gravity) {
        chiTietSDs = new ArrayList<>();
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_muon_tb);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra bên ngoài để tắt dialog
        //false = no; true = yes
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }

        spMaPhong = dialog.findViewById(R.id.spMaPhongM);
        spMaThietBi = dialog.findViewById(R.id.spMaThietBiM);
        llSoLuongMuon = dialog.findViewById(R.id.llSoLuongM);
        tvNgay = dialog.findViewById(R.id.tvNgayM);
        tvSoLuongDu = dialog.findViewById(R.id.tvSoLuongDu);

        btnMuon = dialog.findViewById(R.id.btnMuon);
        btnHuyM = dialog.findViewById(R.id.btnHuyM);


        date = new Date(millis);
        tvNgay.setText(date.toString());
        //----------------------------spinner------------------------------
        PhongHocAPI.apiPhonghocService.layDSPhongHoc().enqueue(new Callback<List<PhongHocEntity>>() {
            @Override
            public void onResponse(Call<List<PhongHocEntity>> call, Response<List<PhongHocEntity>> response) {
                phongHocs = new ArrayList<>();
                if(response.isSuccessful()){
                    phongHocs = (ArrayList<PhongHocEntity>) response.body();
                    adapterMaPhong = new AdapterMaPhong(context,R.layout.spinner_maphong_ctsd,phongHocs);
                    spMaPhong.setAdapter(adapterMaPhong);
                    spMaPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            maPhong = adapterMaPhong.getItem(i).getMaPhong();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PhongHocEntity>> call, Throwable t) {

            }
        });
        ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
            @Override
            public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                thietBis = new ArrayList<>();
                if(response.isSuccessful()){
                    thietBis = (ArrayList<ThietBiEntity>) response.body();
                    adapterMaTB = new AdapterMaTB(getApplication(),R.layout.spinner_matb_ctsd,thietBis);
                    spMaThietBi.setAdapter(adapterMaTB);
                    spMaThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            maTB = adapterMaTB.getItem(i).getMaTB();
                            tongsl = thietBis.get(i).getSoLuong();
                            sldu = tongsl - slmuon;
                            tvSoLuongDu.setText("/"+sldu);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ThietBiEntity>> call, Throwable t) {

            }
        });

        //----------------------------end_spinner------------------------------
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(txtSoLuongSD.getText().toString().trim());
                if (TextUtils.isEmpty(txtSoLuongSD.getText().toString().trim())){
                    txtSoLuongSD.requestFocus();
                    txtSoLuongSD.setError(context.getResources().getString(R.string.erorr_soLuongTrong));
                }
                if(sl > sldu || sl <= 0){
                    txtSoLuongSD.requestFocus();
                    txtSoLuongSD.setError(context.getResources().getString(R.string.erorr_soLuongMuon + sldu));
                }
                thongBaoThanhCong(Gravity.CENTER,"Mượn thiết bị thành công "+maTB+"!");
                themCTSBvaoDB(new ChiTietSDEntity(date,sl,maTB,maPhong));
                /*int slmoi = Integer.parseInt(dbThietBi.laySLThietBi(maThietBi)) - Integer.parseInt(dbChiTietSD.layTongSLMuonMatb(maThietBi));
                tvSoLuongDu.setText("/"+slmoi);
                txtSoLuongSD.setText("");*/
                dialog.show();

            }
        });
        btnHuyM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

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
    private void themCTSBvaoDB(ChiTietSDEntity chiTietSD){
        ChiTietSDAPI.apiChiTietSDService.themChiTietSD(chiTietSD).enqueue(new Callback<ChiTietSDEntity>() {
            @Override
            public void onResponse(Call<ChiTietSDEntity> call, Response<ChiTietSDEntity> response) {
                if(response.isSuccessful()){
                    thongBaoThanhCong(Gravity.CENTER, "Mượn " + chiTietSD.getMaTB() + " thành công!");
                    loadListView();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChiTietSDEntity> call, Throwable t) {
                thongBaoThanhCong(Gravity.CENTER, "Mượn " + chiTietSD.getMaTB() + " thất bại!");
            }
        });
    }

    private void dialogChiTietSD(int gravity,int i) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_chitietsd);

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
        tvMaPCTmuon = dialog.findViewById(R.id.tvMaPCTmuon);
        tvLoaiPCTmuon = dialog.findViewById(R.id.tvLoaiPCTmuon);
        tvMaTBCTmuon = dialog.findViewById(R.id.tvMaTBCTmuon);
        tvTenTBCTmuon = dialog.findViewById(R.id.tvTenTBCTmuon);
        tvSoLuongCTmuon = dialog.findViewById(R.id.tvSoLuongCTmuon);
        tvSoLuongCTconlai = dialog.findViewById(R.id.tvSoLuongCTconlai);
        tvNgayCTmuon = dialog.findViewById(R.id.tvNgayCTmuon);

        tvMaPCTmuon.setText(chiTietSDs.get(i).getMaPhong());
        getPhongHoc(chiTietSDs.get(i).getMaPhong());
        tvMaTBCTmuon.setText(chiTietSDs.get(i).getMaTB());
        getThietBi(chiTietSDs.get(i).getMaTB());
        tvSoLuongCTmuon.setText(chiTietSDs.get(i).getSoLuongSD()+"");
        tvNgayCTmuon.setText(chiTietSDs.get(i).getNgaySD().toString());

        dialog.show();
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
        btnMuonCTTB = findViewById(R.id.btnMuonCTTB);
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