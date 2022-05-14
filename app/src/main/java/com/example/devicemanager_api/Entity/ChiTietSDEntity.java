package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTietSDEntity implements Serializable {
    private int idCTSD;
    private String ngaySD;
    private int soLuongSD;
    private String maTB;
    private String maPhong;

    public ChiTietSDEntity(int idCTSD, String ngaySD, int soLuongSD, String maTB, String maPhong) {
        this.idCTSD = idCTSD;
        this.ngaySD = ngaySD;
        this.soLuongSD = soLuongSD;
        this.maTB = maTB;
        this.maPhong = maPhong;
    }

    public int getIdCTSD() {
        return idCTSD;
    }

    public void setIdCTSD(int idCTSD) {
        this.idCTSD = idCTSD;
    }

    public void setNgaySD(String ngaySD) {
        this.ngaySD = ngaySD;
    }

    public String getNgaySD(){
        return this.ngaySD;
    }

    public int getSoLuongSD() {
        return soLuongSD;
    }

    public void setSoLuongSD(int soLuongSD) {
        this.soLuongSD = soLuongSD;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }
}
