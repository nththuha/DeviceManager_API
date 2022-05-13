package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ThietBiEntity implements Serializable {
    private String maTB;
    private String tenTB;
    private String xuatXu;
    private int soLuong;
    private String maLoaiTB;


    public ThietBiEntity() {
    }

    public ThietBiEntity(String maTB, String tenTB, String xuatXu, int soLuong, String maLoaiTB) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXu = xuatXu;
        this.soLuong = soLuong;
        this.maLoaiTB = maLoaiTB;

    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaLoaiTB() {
        return maLoaiTB;
    }

    public void setMaLoaiTB(String maLoaiTB) {
        this.maLoaiTB = maLoaiTB;
    }


}
