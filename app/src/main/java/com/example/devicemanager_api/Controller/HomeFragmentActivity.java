package com.example.devicemanager_api.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanager_api.Entity.TaiKhoanEntity;
import com.example.devicemanager_api.R;

public class HomeFragmentActivity extends Fragment {

    TaiKhoanEntity nhanVienLogin;
    TextView tvName;
    ImageButton imvLogout;
    Button btnY, btnN;
    LinearLayout llPhongHoc, llLoaiTB, llThietBi, llNhanVien, llThongKe, llCTSuDung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_activity, container, false);

        tvName = view.findViewById(R.id.tvName);
        imvLogout = view.findViewById(R.id.imvLogout);

        llPhongHoc = view.findViewById(R.id.llPhongHoc);
        llLoaiTB = view.findViewById(R.id.llLoaiTB);
        llThietBi = view.findViewById(R.id.llThietBi);
        llNhanVien = view.findViewById(R.id.llNhanVien);
        llThongKe = view.findViewById(R.id.llThongKe);
        llCTSuDung = view.findViewById(R.id.llCTSuDung);

        //nhận data từ login
        Bundle bundle = getActivity().getIntent().getExtras();
        nhanVienLogin = (TaiKhoanEntity) bundle.getSerializable("thong_tin_nv");

        getEvent();
        return view;
    }

    private void getEvent() {
        tvName.setText("Xin chào, " + nhanVienLogin.getHo() + " " + nhanVienLogin.getTen());
        llPhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPhongHoc.setBackgroundResource(R.drawable.round_border_green20);

                llLoaiTB.setBackgroundResource(R.drawable.round_border_while30);
                llThietBi.setBackgroundResource(R.drawable.round_border_while30);
                llNhanVien.setBackgroundResource(R.drawable.round_border_while30);
                llThongKe.setBackgroundResource(R.drawable.round_border_while30);
                llCTSuDung.setBackgroundResource(R.drawable.round_border_while30);

                Toast.makeText(getContext(), "Quản lý phòng học", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),PhongHocActivity.class));
            }
        });
        llLoaiTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLoaiTB.setBackgroundResource(R.drawable.round_border_green20);

                llPhongHoc.setBackgroundResource(R.drawable.round_border_while30);
                llThietBi.setBackgroundResource(R.drawable.round_border_while30);
                llNhanVien.setBackgroundResource(R.drawable.round_border_while30);
                llThongKe.setBackgroundResource(R.drawable.round_border_while30);
                llCTSuDung.setBackgroundResource(R.drawable.round_border_while30);

                Toast.makeText(getContext(), "Quản lý loại thiết bị", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),LoaiThietBiActivity.class));
            }
        });
        llThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llThietBi.setBackgroundResource(R.drawable.round_border_green20);

                llLoaiTB.setBackgroundResource(R.drawable.round_border_while30);
                llPhongHoc.setBackgroundResource(R.drawable.round_border_while30);
                llNhanVien.setBackgroundResource(R.drawable.round_border_while30);
                llThongKe.setBackgroundResource(R.drawable.round_border_while30);
                llCTSuDung.setBackgroundResource(R.drawable.round_border_while30);

                Toast.makeText(getContext(), "Quản lý thiết bị", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(),ThietBiActivity.class));

            }
        });
        llNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llNhanVien.setBackgroundResource(R.drawable.round_border_green20);

                llLoaiTB.setBackgroundResource(R.drawable.round_border_while30);
                llThietBi.setBackgroundResource(R.drawable.round_border_while30);
                llPhongHoc.setBackgroundResource(R.drawable.round_border_while30);
                llThongKe.setBackgroundResource(R.drawable.round_border_while30);
                llCTSuDung.setBackgroundResource(R.drawable.round_border_while30);

                Toast.makeText(getContext(), "Quản lý nhân viên", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getContext(),NhanVienActivity.class));
            }
        });
        llThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llThongKe.setBackgroundResource(R.drawable.round_border_green20);

                llLoaiTB.setBackgroundResource(R.drawable.round_border_while30);
                llThietBi.setBackgroundResource(R.drawable.round_border_while30);
                llNhanVien.setBackgroundResource(R.drawable.round_border_while30);
                llPhongHoc.setBackgroundResource(R.drawable.round_border_while30);
                llCTSuDung.setBackgroundResource(R.drawable.round_border_while30);
                Toast.makeText(getContext(), "Thống kê", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getContext(),ThongKeActivity.class));

            }
        });
        llCTSuDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCTSuDung.setBackgroundResource(R.drawable.round_border_green20);

                llLoaiTB.setBackgroundResource(R.drawable.round_border_while30);
                llThietBi.setBackgroundResource(R.drawable.round_border_while30);
                llNhanVien.setBackgroundResource(R.drawable.round_border_while30);
                llThongKe.setBackgroundResource(R.drawable.round_border_while30);
                llPhongHoc.setBackgroundResource(R.drawable.round_border_while30);

                Toast.makeText(getContext(), "Chi tiết sử dụng", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getContext(),ChiTietSuDungActivity.class));
            }
        });

        imvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hienThiYN(Gravity.CENTER);
            }
        });

    }

    private void hienThiYN(int gravity) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(getContext());
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

        btnY = dialog.findViewById(R.id.btnYes);
        btnN = dialog.findViewById(R.id.btnNo);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
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
}