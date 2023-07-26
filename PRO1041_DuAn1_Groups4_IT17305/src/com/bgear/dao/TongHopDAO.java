/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.dao;

import com.bgear.entity.DoanhThu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.bgear.utils.JdbcHelper;
import java.util.Date;

/**
 *
 * @author ASUS ZENBOOK
 */
public class TongHopDAO {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Object[]> getBanHangNgay(int day, int month, int year) {
        String sql = "{CALL sp_BanHangNgay(?,?,?)}";
        String[] cols = {"MaSP", "TenSP", "SoLuong"};
        return this.getListOfArray(sql, cols, day, month, year);
    }
    public List<Object[]> getBanHangThang(int month, int year) {
        String sql = "{CALL sp_BanHangThang(?,?)}";
        String[] cols = {"MaSP", "TenSP", "SoLuong"};
        return this.getListOfArray(sql, cols, month, year);
    }
    public List<Object[]> getBanHangNam(int year) {
        String sql = "{CALL sp_BanHangNam(?)}";
        String[] cols = {"MaSP", "TenSP", "SoLuong"};
        return this.getListOfArray(sql, cols, year);
    }
    
    public List<Object[]> getNhanVien(int month, int year) {
        String sql = "{CALL sp_NhanVien(?,?)}";
        String[] cols = {"MaNV", "HoTen", "SoLuong"};
        return this.getListOfArray(sql, cols, month, year);
    }
    
    public List<DoanhThu> getDoanhThu() {
            List<DoanhThu> list = new ArrayList<>();
            String sql = "{CALL sp_DoanhThu}";
            try {
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                DoanhThu entity = new DoanhThu();
                entity.setThang(rs.getString("Thang"));
                entity.setTien(rs.getInt("ThanhTien"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
}
