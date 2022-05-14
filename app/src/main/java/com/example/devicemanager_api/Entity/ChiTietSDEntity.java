package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiTietSDEntity implements Serializable {
    private int idCTSD;
    private Date ngaySD;
    private int soLuongSD;
    private String maTB;
    private String maPhong;

    public ChiTietSDEntity() {
    }

    public ChiTietSDEntity(int idCTSD, Date ngaySD, int soLuongSD, String maTB, String maPhong) {
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
    public String getNgaySDForThongKe(){
        System.out.println("----------->>>" + ngaySD.toString());
        java.sql.Date sqlDate = new java.sql.Date(ngaySD.getTime());
        LocalDate localDate = sqlDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("----------->>>" + sqlDate.toString());

        return sqlDate.toString();
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
