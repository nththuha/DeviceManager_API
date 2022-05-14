package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTietDatEntity implements Serializable {
    private int idCTD;
    private String ngayD;
    private int soLuongD;
    private String maTB;
    private String maPhong;

    public ChiTietDatEntity(int idCTD, String ngayD, int soLuongD, String maTB, String maPhong) {
        this.idCTD = idCTD;
        this.ngayD = ngayD;
        this.soLuongD = soLuongD;
        this.maTB = maTB;
        this.maPhong = maPhong;
    }

    public int getIdCTD() {
        return idCTD;
    }

    public void setIdCTD(int idCTD) {
        this.idCTD = idCTD;
    }

    public String getNgayD() {
        return ngayD;
    }

    public void setNgayD(String ngayD) {
        this.ngayD = ngayD;
    }

    public int getSoLuongD() {
        return soLuongD;
    }

    public void setSoLuongD(int soLuongD) {
        this.soLuongD = soLuongD;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }
}
