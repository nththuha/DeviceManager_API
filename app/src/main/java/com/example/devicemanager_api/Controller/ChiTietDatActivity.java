package com.example.devicemanager_api.Controller;

import static java.sql.Date.*;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanager_api.API.ChiTietDatAPI;
import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.API.PhongHocAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Adapter.AdapterChiTietDat;
import com.example.devicemanager_api.Adapter.AdapterChiTietSD;
import com.example.devicemanager_api.Adapter.AdapterMaPhong;
import com.example.devicemanager_api.Adapter.AdapterMaTB;
import com.example.devicemanager_api.Entity.ChiTietDatEntity;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDatActivity extends AppCompatActivity {
    Context context;
    DatePickerDialog datePickerDialog;
    Date date;
    long millis = System.currentTimeMillis();

    ListView lvChiTietD;
    Spinner spMaPhong, spMaThietBi;
    ProgressBar pbLoad;
    SearchView svCTSD;
    ImageButton imbBack;
    Button btnDat, btnHuyD;
    Button btnDatCTTB;
    EditText txtSoLuongM,txtNgayD;
    TextView tvSoLuongDu;

    AdapterChiTietDat adapterChiTietDat;
    AdapterMaPhong adapterMaPhong;
    AdapterMaTB adapterMaTB;
    ThietBiEntity thietBiEntity;
    public static ArrayList<ChiTietDatEntity> chiTietDs;
    public static ArrayList<ChiTietSDEntity> chiTietSDs;
    ArrayList<ChiTietDatEntity> filter;
    String maPhong = "";
    String maTB = "";
    int sl = 0;
    int lastYear, lastMonth, lastDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_dat);
        context = this;
        date = new Date(millis);
        setControl();
        setEvent();
    }
    public Boolean checkNgayDat(String lastDate){
        date = new Date(millis);
        String nowDate = date.toString();
        String[] sLastDate = lastDate.split("-");
        String[] sNowDate = nowDate.split("-");
        int yL = Integer.parseInt(sLastDate[0]);
        int mL = Integer.parseInt(sLastDate[1]);
        int dL = Integer.parseInt(sLastDate[2]);

        int yN = Integer.parseInt(sNowDate[0]);
        int mN = Integer.parseInt(sNowDate[1]);
        int dN = Integer.parseInt(sNowDate[2]);
        if(lastDate.equals(nowDate)){
            return false;
        }
        if(yL == yN ){
            if(mL == mN )
                if(dL > dN)
                    return true;
            else if(mL > mN){
                    return true;
                }
            return false;
        }
        else if(yL >= yN ){
            return true;
        }
        return false;
    }
    private void setControl() {
        lvChiTietD = findViewById(R.id.lvChiTietD);
        pbLoad = findViewById(R.id.pbLoad);
        svCTSD = findViewById(R.id.svCTSD);
        imbBack = findViewById(R.id.imbBackCTD);
        btnDatCTTB = findViewById(R.id.btnDatCTTB);
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
        btnDatCTTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSpinner();
            }
        });
    }

    public void loadListView() {
        adapterChiTietDat = new AdapterChiTietDat(this, R.layout.activity_item_chitietsd, chiTietDs);
        lvChiTietD.setAdapter(adapterChiTietDat);
    }

    public void loadSpinner() {
        PhongHocAPI.apiPhonghocService.layDSPhongHoc().enqueue(new Callback<List<PhongHocEntity>>() {
            @Override
            public void onResponse(Call<List<PhongHocEntity>> call, Response<List<PhongHocEntity>> response) {
                List phongHoc = response.body();
                ThietBiAPI.apiThietBiService.layDSThietBi().enqueue(new Callback<List<ThietBiEntity>>() {
                    @Override
                    public void onResponse(Call<List<ThietBiEntity>> call, Response<List<ThietBiEntity>> response) {
                        List thietBi = response.body();
                        Log.d("ctshPhongHoc", "onResponse: " + phongHoc.size());
                        dialogDatTB(Gravity.CENTER, thietBi, phongHoc);
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
        for (ChiTietDatEntity e : chiTietDs) {
            if (e.getMaTB().toLowerCase().contains(s.toLowerCase()) || e.getMaPhong().toLowerCase().contains(s.toLowerCase())) {
                filter.add(e);
            }
        }
        adapterChiTietDat.setFilterList(filter);
        if (filter.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            /*new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();*/
        }
    }

    public void getDSChiTiet() {
        ChiTietDatAPI.apiChiTietDatService.layDSChiTietDat().enqueue(new Callback<List<ChiTietDatEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietDatEntity>> call, Response<List<ChiTietDatEntity>> response) {
                chiTietDs = (ArrayList<ChiTietDatEntity>) response.body();
                loadListView();
                pbLoad.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<ChiTietDatEntity>> call, Throwable t) {
                Toast.makeText(ChiTietDatActivity.this, "Load chi tiết thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogDatTB(int gravity, List thietBi, List phongHoc) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_dat_tb);

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
        spMaPhong = dialog.findViewById(R.id.spMaPhongD);
        spMaThietBi = dialog.findViewById(R.id.spMaThietBiD);
        txtNgayD = dialog.findViewById(R.id.txtNgayD);
        tvSoLuongDu = dialog.findViewById(R.id.tvSoLuongDu);
        txtSoLuongM = dialog.findViewById(R.id.txtSoLuongD);
        btnDat = dialog.findViewById(R.id.btnDat);
        btnHuyD = dialog.findViewById(R.id.btnHuyD);

        //----------------------------spinner------------------------------
        adapterMaPhong = new AdapterMaPhong(context, R.layout.spinner_maphong_ctsd, phongHoc);
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
        adapterMaTB = new AdapterMaTB(getApplication(), R.layout.spinner_matb_ctsd, thietBi);
        spMaThietBi.setAdapter(adapterMaTB);
        spMaThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTB = adapterMaTB.getItem(i).getMaTB();
                ThietBiAPI.apiThietBiService.layThietBi(maTB).enqueue(new Callback<ThietBiEntity>() {
                    @Override
                    public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                        thietBiEntity = new ThietBiEntity();
                        if (response.isSuccessful()) {
                            thietBiEntity = response.body();
                            ChiTietDatAPI.apiChiTietDatService.layDSTheoMaTBD(maTB).enqueue(new Callback<List<ChiTietDatEntity>>() {
                                @Override
                                public void onResponse(Call<List<ChiTietDatEntity>> call, Response<List<ChiTietDatEntity>> response) {
                                    chiTietDs = new ArrayList<>();
                                    if(response.isSuccessful()){
                                        chiTietDs = (ArrayList<ChiTietDatEntity>) response.body();
                                        ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(maTB).enqueue(new Callback<List<ChiTietSDEntity>>() {
                                            @Override
                                            public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                                                int tongsl = 0, sldat = 0, slmuon = 0, sldu = 0;
                                                tongsl = thietBiEntity.getSoLuong();
                                                chiTietSDs = new ArrayList<>();
                                                if (response.isSuccessful()) {
                                                    chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                                                    getDSChiTiet();
                                                    for(ChiTietDatEntity ctd : chiTietDs){
                                                        if(maTB.equals(ctd.getMaTB())){
                                                            sldat += ctd.getSoLuongD();
                                                        }
                                                    }
                                                    for (ChiTietSDEntity ctsd : chiTietSDs) {
                                                        if (maTB.equals(ctsd.getMaTB())) {
                                                            slmuon += ctsd.getSoLuongSD();
                                                        }
                                                    }
                                                    sldu = tongsl - slmuon - sldat;
                                                    if(sldu == 0){
                                                        txtSoLuongM.setEnabled(false);
                                                        tvSoLuongDu.setText("/" + sldu);
                                                    }
                                                    else {
                                                        txtSoLuongM.setEnabled(true);
                                                        tvSoLuongDu.setText("/" + sldu);
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<ChiTietDatEntity>> call, Throwable t) {
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
        btnDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl = Integer.parseInt(txtSoLuongM.getText().toString());
                String ngayD = txtNgayD.getText().toString().trim();
                if(ngayD.isEmpty()){
                    Toast.makeText(ChiTietDatActivity.this, "Vui lòng nhập ngày đặt!", Toast.LENGTH_SHORT).show();
                    txtNgayD.setText("");
                    return;
                }
                if(checkNgayDat(ngayD) == false){
                    Toast.makeText(ChiTietDatActivity.this, "Vui lòng nhập ngày đặt lớn hơn ngày hiện tại!", Toast.LENGTH_SHORT).show();
                    txtNgayD.setText("");
                    return;
                }
                if(txtSoLuongM.getText().toString().isEmpty()){
                    Toast.makeText(ChiTietDatActivity.this, "Vui lòng nhập vào số lượng!", Toast.LENGTH_SHORT).show();
                    txtNgayD.setText("");
                    return;
                }
                if (sl <= 0) {
                    Toast.makeText(ChiTietDatActivity.this, "Số lượng đặt phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    txtSoLuongM.setText("");
                    return;
                }
                ChiTietDatAPI.apiChiTietDatService.layDSTheoMaTBD(maTB).enqueue(new Callback<List<ChiTietDatEntity>>() {
                    @Override
                    public void onResponse(Call<List<ChiTietDatEntity>> call, Response<List<ChiTietDatEntity>> response) {
                        chiTietDs = new ArrayList<>();
                        if (response.isSuccessful()) {
                            chiTietDs = (ArrayList<ChiTietDatEntity>) response.body();
                            ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(maTB).enqueue(new Callback<List<ChiTietSDEntity>>() {
                                @Override
                                public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                                    int slmuon = 0, sldu = 0, sldat = 0;
                                    int tongsl = thietBiEntity.getSoLuong();
                                    chiTietSDs = new ArrayList<>();
                                    if (response.isSuccessful()) {
                                        chiTietSDs = (ArrayList<ChiTietSDEntity>) response.body();
                                        for(ChiTietDatEntity ctd : chiTietDs){
                                            if(maTB.equals(ctd.getMaTB())){
                                                sldat += ctd.getSoLuongD();
                                            }
                                        }
                                        for (ChiTietSDEntity ctsd : chiTietSDs) {
                                            if (maTB.equals(ctsd.getMaTB())) {
                                                slmuon += ctsd.getSoLuongSD();
                                            }
                                        }
                                        sldu = tongsl - slmuon - sldat;
                                        if (sl > sldu) {
                                            Toast.makeText(ChiTietDatActivity.this, "Số lượng đặt ít hơn số lượng hiện tại!", Toast.LENGTH_SHORT).show();
                                            txtSoLuongM.setText("");
                                            return;
                                        }
                                        else{
                                            themCTDvaoDB(new ChiTietDatEntity(5, txtNgayD.getText().toString(), sl, maTB, maPhong));
                                            getDSChiTiet();
                                            dialog.dismiss();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ChiTietDatEntity>> call, Throwable t) {

                    }
                });
                ChiTietSDAPI.apiChiTietSDService.laySoLuongTheoMaTB(maTB).enqueue(new Callback<List<ChiTietSDEntity>>() {
                    @Override
                    public void onResponse(Call<List<ChiTietSDEntity>> call, Response<List<ChiTietSDEntity>> response) {
                        chiTietSDs = new ArrayList<>();

                    }
                    @Override
                    public void onFailure(Call<List<ChiTietSDEntity>> call, Throwable t) {
                    }
                });
            }
        });
        btnHuyD.setOnClickListener(new View.OnClickListener() {
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

    private void themCTDvaoDB(ChiTietDatEntity chiTietDat) {
        ChiTietDatAPI.apiChiTietDatService.themChiTietDat(chiTietDat).enqueue(new Callback<ChiTietSDEntity>() {
            @Override
            public void onResponse(Call<ChiTietSDEntity> call, Response<ChiTietSDEntity> response) {
                if (response.isSuccessful()) {
                    getDSChiTiet();
                    thongBaoThanhCong(Gravity.CENTER, "Đặt " + chiTietDat.getMaTB() + " thành công!");
                    return;
                }
            }
            @Override
            public void onFailure(Call<ChiTietSDEntity> call, Throwable t) {
                thongBaoThanhCong(Gravity.CENTER, "Đặt " + chiTietDat.getMaTB() + " thất bại!");
            }
        });
    }
}















