package com.bgear.entity;

import java.util.Date;

public class HoaDon {
    String maHd;
    String maKh;
    String maNv;
    String tenKh;
    String tenNv;
    String ngayLapHD;

    public HoaDon() {
    }

    public HoaDon(String maHd, String maKh, String maNv, String ngayLapHD) {
        this.maHd = maHd;
        this.maKh = maKh;
        this.maNv = maNv;
        this.ngayLapHD = ngayLapHD;
    }

    public String getMaHd() {
        return maHd;
    }

    public void setMaHd(String maHd) {
        this.maHd = maHd;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(String ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

}
