package com.example.devicemanager_api.Entity;

import java.io.Serializable;

public class ChiTietTBEntity implements Serializable {
    private Integer idCTTB;
    private String tinhTrang, maThietBi;

    public ChiTietTBEntity() {
    }

    public ChiTietTBEntity(Integer idCTTB, String tinhTrang, String maThietBi) {
        this.idCTTB = idCTTB;
        this.tinhTrang = tinhTrang;
        this.maThietBi = maThietBi;
    }

    public ChiTietTBEntity( String tinhTrang, String maThietBi) {
        this.tinhTrang = tinhTrang;
        this.maThietBi = maThietBi;
    }

    public Integer getIdCTTB() {
        return idCTTB;
    }

    public void setIdCTTB(Integer idCTTB) {
        this.idCTTB = idCTTB;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }
}
