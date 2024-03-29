package com.example.devicemanager_api.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanager_api.API.TaiKhoanAPI;
import com.example.devicemanager_api.Entity.TaiKhoanEntity;
import com.example.devicemanager_api.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser, txtPass;
    Button btnLogin;
    TextView tvForgotPass, tvTieuDeQL;

    final String mail = "thuhango0204@gmail.com";
    final String password = "tvchhnsxhegolohs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setConTrol();
        startAnimation();
        setEvent();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtUser.getText().toString().equals("")) {
                    thongBao(Gravity.CENTER, "Tên đăng nhập không được để trống!");
                    return;
                }
                if (txtPass.getText().toString().equals("")) {
                    thongBao(Gravity.CENTER, "Mật khẩu không được để trống!");
                    return;
                }
                xetDangNhap();
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtUser.getText().toString().equals("")) {
                    thongBao(Gravity.CENTER, "Bạn cần nhập tên đăng nhập!");
                    return;
                }
                xetQuenMatKhau();
            }
        });
    }

    private void xetQuenMatKhau() {
        TaiKhoanAPI.apiTaiKhoanService.layTaiKhoan(txtUser.getText().toString()).enqueue(new Callback<TaiKhoanEntity>() {
            @Override
            public void onResponse(Call<TaiKhoanEntity> call, Response<TaiKhoanEntity> response) {
                TaiKhoanEntity tk = response.body();
                String matKhauMoi = taoMatKhau();
                try {
                    tk.setMatKhau(maHoa(matKhauMoi));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                TaiKhoanAPI.apiTaiKhoanService.suaTaiKhoan(tk).enqueue(new Callback<TaiKhoanEntity>() {
                    @Override
                    public void onResponse(Call<TaiKhoanEntity> call, Response<TaiKhoanEntity> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Sửa mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaiKhoanEntity> call, Throwable t) {

                    }
                });
                guiMail(tk.getEmail(), matKhauMoi);
            }

            @Override
            public void onFailure(Call<TaiKhoanEntity> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                thongBao(Gravity.CENTER, "Xem lại tên đăng nhập");
            }
        });
    }

    private void xetDangNhap(){
        TaiKhoanAPI.apiTaiKhoanService.layTaiKhoan(txtUser.getText().toString()).enqueue(new Callback<TaiKhoanEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<TaiKhoanEntity> call, Response<TaiKhoanEntity> response) {
                TaiKhoanEntity tk = response.body();
                String matKhau = "";
                try {
                    matKhau = maHoa(txtPass.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                if(tk != null){
                    if(tk.getMatKhau().equals(matKhau)){
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        //sử dụng bundle gửi dữ liệu của object nhân viên
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("thong_tin_nv", tk);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        thongBao(Gravity.CENTER, "Xem lại tên đăng nhập và mật khẩu");
                    }
                }
                else{
                    thongBao(Gravity.CENTER, "Xem lại tên đăng nhập và mật khẩu");
                }
            }

            @Override
            public void onFailure(Call<TaiKhoanEntity> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void thongBao(int gravity, String noiDung){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_thongbao_dangnhap);

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

        TextView tvThongBao = dialog.findViewById(R.id.tvThongbao_DangNhap);
        tvThongBao.setText(noiDung);
        dialog.show();
    }

    public String taoMatKhau() {
        Random generator = new Random();
        int value = generator.nextInt((999999 - 100000) + 1) + 100000;
        return value + "";
    }

    private void guiMail(String mailToSend, String matKhauMoi) {
        Properties pros = new Properties();
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.smtp.host", "smtp.gmail.com");
        pros.put("mail.smtp.port", "587");
        Session session = Session.getInstance(pros, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToSend));
            message.setSubject("APP QUẢN LÝ THIẾT BỊ TRƯỜNG HỌC - LẤY LẠI MẬT KHẨU");
            message.setText("Mật khẩu mới của bạn là: " + matKhauMoi);
            new sendMail().execute(message);
            thongBao(Gravity.CENTER, "Mật khẩu mới đã được gửi vào mail!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        tvForgotPass.setEnabled(true);
    }

    private class sendMail extends AsyncTask<Message, String, String>{
        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Thành công";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Thất bại";
            }
        }
    }

    private void startAnimation() {
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.anim);
        tvTieuDeQL.setAnimation(animation);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String maHoa(String original) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String SECRET_KEY = "12345678";
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] byteEncrypted = cipher.doFinal(original.getBytes());
        String encrypted = null;
        encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
        return encrypted;
    }

    private void setConTrol() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvTieuDeQL = findViewById(R.id.tvTieuDeQL);
    }
}