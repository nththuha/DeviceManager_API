package com.example.devicemanager_api.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoaiThietBiEntity implements Serializable {
    private String maLoaiTB;
    private String tenLoaiTB;
    private List<ThietBiEntity> thietBis = new ArrayList<>();

    public LoaiThietBiEntity() {
    }

    public LoaiThietBiEntity(String maLoaiTB, String tenLoaiTB, List<ThietBiEntity> thietBis) {
        this.maLoaiTB = maLoaiTB;
        this.tenLoaiTB = tenLoaiTB;
        this.thietBis = thietBis;
    }

    public String getMaLoaiTB() {
        return maLoaiTB;
    }

    public void setMaLoaiTB(String maLoaiTB) {
        this.maLoaiTB = maLoaiTB;
    }

    public String getTenLoaiTB() {
        return tenLoaiTB;
    }

    public void setTenLoaiTB(String tenLoaiTB) {
        this.tenLoaiTB = tenLoaiTB;
    }

    public List<ThietBiEntity> getThietBis() {
        return thietBis;
    }

    public void setThietBis(List<ThietBiEntity> thietBis) {
        this.thietBis = thietBis;
    }
}
