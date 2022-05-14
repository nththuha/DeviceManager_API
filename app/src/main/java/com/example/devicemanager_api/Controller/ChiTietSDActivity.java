package com.example.devicemanager_api.Controller;

import static java.sql.Date.*;

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
    EditText txtSoLuongM, txtSoLuongT;
    TextView tvMaPCTmuon,tvLoaiPCTmuon,tvMaTBCTmuon,tvTenTBCTmuon,tvSoLuongCTmuon,tvSoLuongCTconlai,tvNgayCTmuon,tvNgay,tvSoLuongDu,tvSoLuongM;

    AdapterChiTietSD adapterChiTietSD;
    AdapterMaPhong adapterMaPhong;
    AdapterMaTB adapterMaTB;
    ThietBiEntity thietBiEntity;
    ArrayList<ThietBiEntity> thietBis;
    PhongHocEntity phongHocEntity;
    ArrayList<PhongHocEntity> phongHocs;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;
    ArrayList<ChiTietSDEntity> filter;

    int tongsl = 0;
    int slmuon = 0;
    int sldu = 0;
    int slmuonM = 0;
    String maPhong = "";
    String maTB = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsd);
        context = this;
        date = new Date(millis);
        setContro();
        setEvent();
    }
    public void loadListView(){
        adapterChiTietSD = new AdapterChiTietSD(this,R.layout.activity_item_chitietsd, chiTietSDs);
        lvChiTietSD.setAdapter(adapterChiTietSD);
    }
    public void loadSpinner(){
        PhongHocAPI.apiPhonghocService.layDSPhongHoc().enqueue(new Callback<List<PhongHocEntity>>() {
            @Override
            public void onResponse(Call<List<PhongHocEntity>> call, Response<List<PhongHocEntity>> response) {
                List phongHoc = response.body();
                ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
                    @Override
                    public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                        List thietBi = response.body();
                        Log.d("ctshPhongHoc", "onResponse: "+phongHoc.size());
                        dialogMuonTB(Gravity.CENTER,thietBi,phongHoc);
                    }

                    @Override
                    public void onFailure(Call<List<ThietBiEntity>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<PhongHocEntity>> call, Throwable t) {

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
        btnMuonCTTB = findViewById(R.id.btnMuonCTTB);
        btnTra = findViewById(R.id.btnTra);
        btnHuyM = findViewById(R.id.btnHuyM);
        btnHuyT = findViewById(R.id.btnHuyT);
    }

    private void getDSChiTiet() {
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

    public void getThietBi(String maTB){
        try {
            ThietBiAPI.apiThietBiService.layThietBi(maTB).enqueue(new Callback<ThietBiEntity>() {
                @Override
                public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                    thietBiEntity = new ThietBiEntity();
                    if(response.isSuccessful()){
                        thietBiEntity = response.body();
                        tvTenTBCTmuon.setText(thietBiEntity.getTenTB());
                        //loadListView();
                        ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(thietBiEntity.getMaTB()).enqueue(new Callback<List<ChiTietSDEntity>>() {
                            @Override
                            public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                                chiTietSDs = new ArrayList<>();
                                if(response.isSuccessful()){
                                    chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                                    for(ChiTietSDEntity ctsd : chiTietSDs){
                                        slmuon += ctsd.getSoLuongSD();
                                    }
                                    //Log.d("slmmm", "onResponse: "+slmuon);
                                    tongsl = thietBiEntity.getSoLuong();
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
                    phongHocEntity = new PhongHocEntity();
                    if(response.isSuccessful()){
                        phongHocEntity = response.body();
                        tvLoaiPCTmuon.setText(phongHocEntity.getLoaiPhong());
                       // loadListView();
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
        getDSChiTiet();
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
        lvChiTietSD.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogTraThietBi(Gravity.CENTER,i);
                return true;
            }
        });
        btnMuonCTTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSpinner();
            }
        });
    }

    private void dialogTraThietBi(int gravity, int i) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tra_tb);

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

        TextView tvMaPhong = dialog.findViewById(R.id.tvMaPhongT);
        TextView tvMaThietBi = dialog.findViewById(R.id.tvMaThietBiT);
        TextView tvNgayMuon = dialog.findViewById(R.id.tvNgayMuon);
        txtSoLuongT = dialog.findViewById(R.id.txtSoLuongT);
        tvSoLuongM = dialog.findViewById(R.id.tvSoLuongM);
        btnTra = dialog.findViewById(R.id.btnTra);
        btnHuyT = dialog.findViewById(R.id.btnHuyT);

        tvMaPhong.setText(chiTietSDs.get(i).getMaPhong());
        tvMaThietBi.setText(chiTietSDs.get(i).getMaTB());
        tvNgayMuon.setText(chiTietSDs.get(i).getNgaySD().toString());
        tvSoLuongM.setText("/" + chiTietSDs.get(i).getSoLuongSD());
        slmuonM = chiTietSDs.get(i).getSoLuongSD();
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiYN(Gravity.CENTER,chiTietSDs.get(i));
                dialog.dismiss();
            }
        });
        btnHuyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void hienThiYN(int gravity,ChiTietSDEntity chiTietSD) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_logout);

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
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btnY = dialog.findViewById(R.id.btnYes);
        Button btnN = dialog.findViewById(R.id.btnNo);
        tvTitle.setText("Bạn thật sự muốn trả thiết bị "+ chiTietSD.getMaTB()+"?");

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sltra = Integer.parseInt(txtSoLuongT.getText().toString());
                int slmuon = 0;
                slmuon = slmuonM - sltra;
                if(slmuon == 0){
                    xoaChiTietSD(chiTietSD.getIdCTSD());
                    getDSChiTiet();
                    thongBaoThanhCong(Gravity.CENTER,"Trả thiết bị thành công!");
                    dialog.dismiss();
                }
                else{
                    suaChiTietSD(new ChiTietSDEntity(chiTietSD.getIdCTSD(),chiTietSD.getNgaySD(),slmuon,chiTietSD.getMaTB(),chiTietSD.getMaPhong()));
                    getDSChiTiet();
                    thongBaoThanhCong(Gravity.CENTER,"Trả thiết bị thành công!");
                    txtSoLuongT.setText("");
                    tvSoLuongM.setText("/" + slmuon);
                    dialog.show();
                }
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void xoaChiTietSD(int idCTSD) {
        ChiTietSDAPI.apiChiTietSDService.xoaChiTietSD(idCTSD).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getDSChiTiet();
                return;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void suaChiTietSD(ChiTietSDEntity chiTietSDEntity){
        ChiTietSDAPI.apiChiTietSDService.suaChiTietSD(chiTietSDEntity).enqueue(new Callback<ChiTietSDEntity>() {
            @Override
            public void onResponse(Call<ChiTietSDEntity> call, Response<ChiTietSDEntity> response) {
                getDSChiTiet();
                return;
            }

            @Override
            public void onFailure(Call<ChiTietSDEntity> call, Throwable t) {

            }
        });
    }
    private void dialogMuonTB(int gravity, List thietBi, List phongHoc) {
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
        txtSoLuongM = dialog.findViewById(R.id.txtSoLuongM);
        btnMuon = dialog.findViewById(R.id.btnMuon);
        btnHuyM = dialog.findViewById(R.id.btnHuyM);


        date = new Date(millis);
        tvNgay.setText(date.toString());
        //----------------------------spinner------------------------------
        adapterMaPhong = new AdapterMaPhong(context,R.layout.spinner_maphong_ctsd,phongHoc);
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
        adapterMaTB = new AdapterMaTB(getApplication(),R.layout.spinner_matb_ctsd,thietBi);
        spMaThietBi.setAdapter(adapterMaTB);
        spMaThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTB = adapterMaTB.getItem(i).getMaTB();
                ThietBiAPI.apiThietBiService.layThietBi(maTB).enqueue(new Callback<ThietBiEntity>() {
                    @Override
                    public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                        thietBiEntity = new ThietBiEntity();
                        if(response.isSuccessful()){
                            thietBiEntity = response.body();
                            int tongsl =  thietBiEntity.getSoLuong();
                            ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(maTB).enqueue(new Callback<List<ChiTietSDEntity>>() {
                                @Override
                                public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                                    chiTietSDs = new ArrayList<>();
                                    int slmuon = 0, sldu = 0;
                                    if(response.isSuccessful()){
                                        chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                                        for(ChiTietSDEntity ct : chiTietSDs){
                                            if(maTB.equals(ct.getMaTB())){
                                                slmuon += ct.getSoLuongSD();
                                            }
                                        }
                                        sldu = tongsl - slmuon;
                                        tvSoLuongDu.setText("/" + sldu);
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //----------------------------end_spinner------------------------------
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "on click", Toast.LENGTH_SHORT).show();
                int sl = Integer.parseInt(txtSoLuongM.getText().toString());
                if(sl <= 0 || sl > sldu){
                    Toast.makeText(ChiTietSDActivity.this, "Số lượng > 0 và < số lượng dư!", Toast.LENGTH_SHORT);
                    return;
                }
                //thongBaoThanhCong(Gravity.CENTER,"Mượn thiết bị thành công "+maTB+"!");
                themCTSBvaoDB(new ChiTietSDEntity(5,date,sl,maTB,maPhong));
                //themCTSBvaoDB(new ChiTietSDEntity(date,sl,maTB,maPhong));
                /*int slmoi = Integer.parseInt(dbThietBi.laySLThietBi(maThietBi)) - Integer.parseInt(dbChiTietSD.layTongSLMuonMatb(maThietBi));
                tvSoLuongDu.setText("/"+slmoi);*/
                txtSoLuongM.setText("");
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
        Log.d("abcccc", "themCTSBvaoDB: abc");
        ChiTietSDAPI.apiChiTietSDService.themChiTietSD(chiTietSD).enqueue(new Callback<ChiTietSDEntity>() {
            @Override
            public void onResponse(Call<ChiTietSDEntity> call, Response<ChiTietSDEntity> response) {
                if(response.isSuccessful()){
                    getDSChiTiet();
                    thongBaoThanhCong(Gravity.CENTER, "Mượn " + chiTietSD.getMaTB() + " thành công!");
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

}