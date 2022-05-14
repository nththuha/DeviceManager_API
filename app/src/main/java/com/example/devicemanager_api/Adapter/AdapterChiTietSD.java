package com.example.devicemanager_api.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.API.ChiTietSDAPI;
import com.example.devicemanager_api.API.PhongHocAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Controller.ChiTietSDActivity;
import com.example.devicemanager_api.Entity.ChiTietSDEntity;
import com.example.devicemanager_api.Entity.PhongHocEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterChiTietSD extends ArrayAdapter<ChiTietSDEntity> {
    Context context;
    int resource;
    int slmuon = 0, sldu = 0, tongsl = 0, slmuonM;
    ArrayList<ChiTietSDEntity> data;
    ArrayList<ChiTietSDEntity> chiTietSDs;
    PhongHocEntity phongHocEntity;
    ThietBiEntity thietBiEntity;
    ThietBiEntity thietBi;
    LinearLayout llClickItem;
    EditText txtSoLuongT;
    Button btnTra,  btnHuyT;
    TextView tvMaPhongSD, tvMaTBSD, tvSoLuongSD;
    TextView tvMaPCTmuon, tvLoaiPCTmuon, tvMaTBCTmuon, tvTenTBCTmuon, tvSoLuongCTmuon, tvSoLuongCTconlai, tvNgayCTmuon;
    TextView tvSoLuongM;


    public AdapterChiTietSD(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietSDEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public void setFilterList(ArrayList<ChiTietSDEntity> filter) {
        this.data = filter;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        llClickItem = convertView.findViewById(R.id.llClickItem);
        tvMaPhongSD = convertView.findViewById(R.id.tvMaPhongSD);
        tvMaTBSD = convertView.findViewById(R.id.tvMaTBSD);
        tvSoLuongSD = convertView.findViewById(R.id.tvSoLuongSD);

        ChiTietSDEntity chiTietSD = data.get(position);
        tvMaPhongSD.setText(chiTietSD.getMaPhong());
        tvMaTBSD.setText(chiTietSD.getMaTB());
        tvSoLuongSD.setText(chiTietSD.getSoLuongSD()+"");

        llClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llClickItem.setBackgroundResource(R.drawable.activity_onclick_item);
                dialogChiTietSD(Gravity.CENTER, chiTietSD);
                ((ChiTietSDActivity) context).getDSChiTiet();
            }
        });
        llClickItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                llClickItem.setBackgroundResource(R.drawable.activity_onclick_item);
                ((ChiTietSDActivity) context).getDSChiTiet();
                dialogTraThietBi(Gravity.CENTER, chiTietSD);
                return true;
            }
        });

        return convertView;
    }
    private void dialogTraThietBi(int gravity, ChiTietSDEntity chiTietSD) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(context);
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

        tvMaPhong.setText(chiTietSD.getMaPhong());
        tvMaThietBi.setText(chiTietSD.getMaTB());
        tvNgayMuon.setText(chiTietSD.getNgaySD());
        tvSoLuongM.setText("/" + chiTietSD.getSoLuongSD());
        slmuonM = chiTietSD.getSoLuongSD();
        btnTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sltra = Integer.parseInt(txtSoLuongT.getText().toString());
                int slmuon = 0;
                slmuon = slmuonM - sltra;
                if(sltra == 0 && slmuon != 0){
                    Toast.makeText(context, "Số lượng trả lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    txtSoLuongT.setText("");
                    return;
                }
                if(slmuon < 0){
                    Toast.makeText(context, "Số lượng trả nhiều hơn số lượng mượn!", Toast.LENGTH_SHORT).show();
                    txtSoLuongT.setText("");
                    return;
                }
                hienThiYN(Gravity.CENTER,chiTietSD);
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

    private void dialogChiTietSD(int gravity, ChiTietSDEntity chiTietSD) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(context);
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

        tvMaPCTmuon.setText(chiTietSD.getMaPhong());
        getPhongHoc(chiTietSD.getMaPhong());
        tvMaTBCTmuon.setText(chiTietSD.getMaTB());
        getThietBi(chiTietSD.getMaTB());
        tvSoLuongCTmuon.setText(chiTietSD.getSoLuongSD()+"");
        tvNgayCTmuon.setText(chiTietSD.getNgaySD().toString());

        dialog.show();
    }

    private void getPhongHoc(String maPhong) {
        try {
            PhongHocAPI.apiPhonghocService.layPhongHoc(maPhong).enqueue(new Callback<PhongHocEntity>() {
                @Override
                public void onResponse(Call<PhongHocEntity> call, Response<PhongHocEntity> response) {
                    phongHocEntity = new PhongHocEntity();
                    if(response.isSuccessful()){
                        phongHocEntity = response.body();
                        tvLoaiPCTmuon.setText(phongHocEntity.getLoaiPhong());
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

    private void getThietBi(String maTB) {
        try {
            ThietBiAPI.apiThietBiService.layThietBi(maTB).enqueue(new Callback<ThietBiEntity>() {
                @Override
                public void onResponse(Call<ThietBiEntity> call, Response<ThietBiEntity> response) {
                    thietBiEntity = new ThietBiEntity();
                    if(response.isSuccessful()){
                        thietBiEntity = response.body();
                        tvTenTBCTmuon.setText(thietBiEntity.getTenTB());
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

    private void hienThiYN(int gravity,ChiTietSDEntity chiTietSD) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(context);
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
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
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
                if(slmuon == 0 && sltra != 0){
                    xoaChiTietSD(chiTietSD.getIdCTSD());
                    thongBaoThanhCong(Gravity.CENTER,"Trả thiết bị thành công!");
                    dialog.dismiss();
                }
                else{
                    suaChiTietSD(new ChiTietSDEntity(chiTietSD.getIdCTSD(),chiTietSD.getNgaySD(),slmuon,chiTietSD.getMaTB(),chiTietSD.getMaPhong()));
                    thongBaoThanhCong(Gravity.CENTER,"Trả thiết bị thành công!");
                    txtSoLuongT.setText("");
                    tvSoLuongM.setText("/" + slmuon);
                    dialog.dismiss();
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
                ((ChiTietSDActivity) context).getDSChiTiet();
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
                ((ChiTietSDActivity) context).getDSChiTiet();
                return;
            }
            @Override
            public void onFailure(Call<ChiTietSDEntity> call, Throwable t) {
            }
        });
    }
    private void thongBaoThanhCong(int gravity, String text) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(context);
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
}