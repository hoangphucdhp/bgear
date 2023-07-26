package com.bgear.entity;

public class SanPham {

    String maSp;
    String tenSp;
    String maTH;
    int donGia;
    byte[] hinh;
    String mauSac;
    String moTa;
    String thuongHieu;

    public String getThuongHieu() {
        if (maTH.equals("TH001")) {
            thuongHieu = "Asus";
        } else if (maTH.equals("TH002")) {
            thuongHieu = "Dell";
        } else if (maTH.equals("TH003")) {
            thuongHieu = "MSI";
        } else if (maTH.equals("TH004")) {
            thuongHieu = "Lenovo";
        } else if (maTH.equals("TH005")) {
            thuongHieu = "Macbook";
        }
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public SanPham() {
    }

    public SanPham(String maSp, String tenSp, String maTH, int donGia, byte[] hinh, int soLuongTonKho, String mauSac, String moTa) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.maTH = maTH;
        this.donGia = donGia;
        this.hinh = hinh;
        this.mauSac = mauSac;
        this.moTa = moTa;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getMaTH() {
        return maTH;
    }

    public void setMaTH(String maTH) {
        this.maTH = maTH;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }


    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
