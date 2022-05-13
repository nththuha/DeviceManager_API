package com.example.devicemanager_api.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanager_api.API.ChiTietThietBiAPI;
import com.example.devicemanager_api.API.ThietBiAPI;
import com.example.devicemanager_api.Controller.ChiTietThietBiActivity;
import com.example.devicemanager_api.Controller.ThongTinThietBiActivity;
import com.example.devicemanager_api.Controller.ThietBiActivity;
import com.example.devicemanager_api.Entity.ChiTietTBEntity;
import com.example.devicemanager_api.Entity.ThietBiEntity;
import com.example.devicemanager_api.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterThietBi extends ArrayAdapter<ThietBiEntity> {
    Context context;
    int resource;
    ArrayList<ThietBiEntity> data;
    TextView tvMatb,tvTentb,tvSoluong,tvConfirmXoaTB;
    ImageView imvSuaTb,imvXoaTB;
    Button btnHuy, btnXoa;


    public AdapterThietBi(@NonNull Context context, int resource, @NonNull ArrayList<ThietBiEntity> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    public void setFilterList(ArrayList<ThietBiEntity> filter){
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
        tvMatb = convertView.findViewById(R.id.tvMaTB);
        tvTentb = convertView.findViewById(R.id.tvTenTB);
        tvSoluong = convertView.findViewById(R.id.tvSoluongTB);
        imvSuaTb = convertView.findViewById(R.id.imvSuaTB);
        imvXoaTB = convertView.findViewById(R.id.imvXoaTB);
        ThietBiEntity thietBi = data.get(position);
        tvMatb.setText(thietBi.getMaTB());
        tvTentb.setText(thietBi.getTenTB());
        tvSoluong.setText(thietBi.getSoLuong() + "");
        imvSuaTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThongTinThietBiActivity.class);
                intent.putExtra("thietbi",thietBi);
                context.startActivity(intent);
            }
        });

        imvXoaTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaThietBi(Gravity.CENTER, thietBi.getMaTB());
            }
        });

        tvTentb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChiTietThietBiActivity.class);
                intent.putExtra("thietbi",thietBi);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private void xoaThietBi(int gravity, String maTB){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_xoa_thietbi);

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
            dialog.setCancelable(true);
        }
        btnXoa = dialog.findViewById(R.id.btnXoaTB);
        btnHuy = dialog.findViewById(R.id.btnHuyTB);
        tvConfirmXoaTB = dialog.findViewById(R.id.tvConfirmXoaTB);
        tvConfirmXoaTB.setText("Bạn có thật sự muốn xóa "+maTB+" không?");
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoa(maTB);
                dialog.dismiss();

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa(String maThietBi){
        ChiTietThietBiAPI.apiChiTietThietBiService.layDSChiTietThietBi().enqueue(new Callback<List<ChiTietTBEntity>>() {
            @Override
            public void onResponse(Call<List<ChiTietTBEntity>> call, Response<List<ChiTietTBEntity>> response) {
                List<ChiTietTBEntity> list = new ArrayList<>();
                for(ChiTietTBEntity x : response.body()){
                    if(x.getMaThietBi().equals(maThietBi)){
                        list.add(x);
                    }
                }
                check(list,maThietBi);
            }

            @Override
            public void onFailure(Call<List<ChiTietTBEntity>> call, Throwable t) {
                Toast.makeText(context, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void check(List<ChiTietTBEntity> list,String maTB){
        int check=0;
        for(int i =0; i< list.size();i++){
            if(!(list.get(i).getTinhTrang().trim().toString().equals("mới"))){
                check=1;
            }
        }
        if(check==0){
            xoaChiTietThietBi(maTB);
        }else{
            thongBaoThanhCong(Gravity.CENTER, "Xóa thất bại thiết bị "+ maTB+"!");
        }

    }
    private void xoaThietBi_API(String maTB){
        ThietBiAPI.apiThietBiService.xoaThietBi(maTB).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                thongBaoThanhCong(Gravity.CENTER, "Xóa thành công thiết bị "+ maTB+"!");
                ((ThietBiActivity)context).layDSThietBi();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Xóa thiết bị thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void xoaChiTietThietBi(String maTB){
        ChiTietThietBiAPI.apiChiTietThietBiService.xoaChiTietThietBi(maTB).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                xoaThietBi_API(maTB);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Xóa chi tiết thiết bị thất bại!", Toast.LENGTH_SHORT).show();
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
