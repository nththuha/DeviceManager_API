package com.example.devicemanager_api.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.API.LoaiThietBiAPI;
import com.example.devicemanager_api.Controller.LoaiThietBiActivity;
import com.example.devicemanager_api.Entity.LoaiThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterLoaiThietBi extends ArrayAdapter<LoaiThietBiEntity> {
    Context context;
    int resource;
    ArrayList<LoaiThietBiEntity> data;

    Button btnLuu, btnThoat, btnKhongXoa, btnDongYXoa;
    EditText txtMaLTB, txtTenLTB;
    TextView tvTieuDe, tvConfirmXoa;

    public void setFilterList(ArrayList<LoaiThietBiEntity> filter) {
        this.data = filter;
        notifyDataSetChanged();
    }

    public AdapterLoaiThietBi(@NonNull Context context, int resource, @NonNull ArrayList<LoaiThietBiEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvLoaiTB = convertView.findViewById(R.id.tvLoaiTB);
        TextView tvTenTB = convertView.findViewById(R.id.tvTenTB);
        ImageButton imbSua = convertView.findViewById(R.id.imbSua);
        ImageButton imbXoa = convertView.findViewById(R.id.imbXoa);

        LoaiThietBiEntity loaiThietBi = data.get(position);
        tvLoaiTB.setText(loaiThietBi.getMaLoaiTB());
        tvTenTB.setText(loaiThietBi.getTenLoaiTB());

        imbSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaLTB(Gravity.CENTER, loaiThietBi.getMaLoaiTB(), loaiThietBi.getTenLoaiTB());
            }
        });

        imbXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaLTB(Gravity.CENTER, loaiThietBi.getMaLoaiTB());
            }
        });

        return convertView;
    }

    private void suaLTB(int gravity, String maLoai, String tenLoai) {
        final Dialog dialog = new Dialog(context);
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
        tvTieuDe = dialog.findViewById(R.id.tvTieuDe);

        tvTieuDe.setText("CHỈNH SỬA LOẠI THIẾT BỊ");

        txtMaLTB.setEnabled(false);
        txtMaLTB.setText(maLoai);
        txtTenLTB.setText(tenLoai);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLTB = txtMaLTB.getText().toString();
                String tenLTB = txtTenLTB.getText().toString();
                if (maLTB.equals("")) {
                    Toast.makeText(context, "Mã loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if (tenLTB.equals("")) {
                    Toast.makeText(context, "Tên loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                suaLTBvaoDB(new LoaiThietBiEntity(maLTB, tenLTB));
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

    private void suaLTBvaoDB(LoaiThietBiEntity loaiThietBiEntity) {
        LoaiThietBiAPI.apiLoaiThietBiService.suaLoaiThietBi(loaiThietBiEntity).enqueue(new Callback<LoaiThietBiEntity>() {
            @Override
            public void onResponse(Call<LoaiThietBiEntity> call, Response<LoaiThietBiEntity> response) {
                if (response.isSuccessful()) {
                    thongBaoThanhCong(Gravity.CENTER, "Cập nhập loại thiết bị thành công!");
                    ((LoaiThietBiActivity) context).layDSLoaiThietBi();
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoaiThietBiEntity> call, Throwable t) {
                Toast.makeText(context, "Sửa loại thiết bị thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void xoaLTB(int gravity, String maLoai) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_xoaltb);

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

        btnKhongXoa = dialog.findViewById(R.id.btnKhongXoa);
        btnDongYXoa = dialog.findViewById(R.id.btnDongYXoa);
        tvConfirmXoa = dialog.findViewById(R.id.tvConfirmXoa);

        tvConfirmXoa.setText("Bạn có thật sự muốn xóa " + maLoai + "?");

        btnDongYXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaLTBvaoDB(maLoai);
                dialog.dismiss();
            }
        });

        btnKhongXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void xoaLTBvaoDB(String maLoai) {
        LoaiThietBiAPI.apiLoaiThietBiService.xoaLoaiThietBi(maLoai).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    thongBaoThanhCong(Gravity.CENTER, "Xóa loại thiết bị thành công!");
                    ((LoaiThietBiActivity) context).layDSLoaiThietBi();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Xóa loại thiết bị thất bại!", Toast.LENGTH_SHORT).show();
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
