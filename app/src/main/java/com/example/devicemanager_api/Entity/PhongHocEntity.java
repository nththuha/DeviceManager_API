package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhongHocEntity implements Serializable {
    private String maPhong;
    private String loaiPhong;
    private int tang;
    private List<ChiTietSuDungEntity> chiTietSDs = new ArrayList<>();

    public PhongHocEntity() {
    }

    public PhongHocEntity(String maPhong, String loaiPhong, int tang, List<ChiTietSuDungEntity> chiTietSDs) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tang = tang;
        this.chiTietSDs = chiTietSDs;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public List<ChiTietSuDungEntity> getChiTietSDs() {
        return chiTietSDs;
    }

    public void setChiTietSDs(List<ChiTietSuDungEntity> chiTietSDs) {
        this.chiTietSDs = chiTietSDs;
    }
}
