package com.bgear.entity;

import java.util.Date;

public class NhanVien {
    String maNv;
    String maCV;
    String tenCv;
    String hoTen;
    String SDT;
    String email;
    String diaChi;
    boolean gioiTinh;
    Date ngaySinh;
    String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNv, String maCV, String hoTen, String SDT, String email, String diaChi, boolean gioiTinh, Date ngaySinh, String matKhau) {
        this.maNv = maNv;
        this.maCV = maCV;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.email = email;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.matKhau = matKhau;
    }

    public String getTenCv(){
        if(maCV.equals("CV001")){
            tenCv = "Quản lý";
        }else if(maCV.equals("CV002")){
            tenCv = "Nhân viên";
        }
        return tenCv;
    }
    
    public void setTenCv(String tenCv){
        this.tenCv = tenCv;
    }
    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    
}
