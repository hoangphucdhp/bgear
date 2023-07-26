/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.entity;

/**
 *
 * @author ASUS ZENBOOK
 */
public class DoanhThu {
    double tien;
    String thang;

    public DoanhThu() {
    }

    public DoanhThu(double tien, String thang) {
        this.tien = tien;
        this.thang = thang;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }
    
}
