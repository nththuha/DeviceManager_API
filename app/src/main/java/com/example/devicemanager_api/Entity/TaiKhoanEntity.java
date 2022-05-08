package com.example.devicemanager_api.Entity;

import java.io.Serializable;

public class TaiKhoanEntity implements Serializable {
    private String maTaiKhoan;
    private String matKhau;
    private String ho;
    private String ten;
    private String email;
    private String hinhAnh;
    private String loai;

    public TaiKhoanEntity() {
    }

    public TaiKhoanEntity(String maTaiKhoan, String matKhau, String ho, String ten, String email, String hinhAnh, String loai) {
        this.maTaiKhoan = maTaiKhoan;
        this.matKhau = matKhau;
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.hinhAnh = hinhAnh;
        this.loai = loai;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}
