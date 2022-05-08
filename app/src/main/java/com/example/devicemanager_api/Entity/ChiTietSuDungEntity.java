package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTietSuDungEntity implements Serializable {
    private int idCTSD;
    private Date ngaySD;
    private int soLuongSD;
    private String maTB;
    private String maPhong;

    public ChiTietSuDungEntity() {
    }

    public ChiTietSuDungEntity(int idCTSD, Date ngaySD, int soLuongSD, String maTB, String maPhong) {
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

    public Date getNgaySD() {
        return ngaySD;
    }

    public void setNgaySD(Date ngaySD) {
        this.ngaySD = ngaySD;
    }

    public int getSoLuongSD() {
        return soLuongSD;
    }

    public void setSoLuongSD(int soLuongSD) {
        this.soLuongSD = soLuongSD;
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
