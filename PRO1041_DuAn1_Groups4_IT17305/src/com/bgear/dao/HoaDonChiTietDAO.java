/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.dao;

import com.bgear.entity.*;
import com.bgear.utils.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonChiTietDAO extends BgearDAO<HoaDonChiTiet, String> {

    String INSERT_SQL = "INSERT INTO HoaDonChiTiet(MaHDCT, MaHD, MaSP, SoLuong) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDonChiTiet SET MaHD=?, MaSP=?, SoLuong=? WHERE MaHDCT=?";
    String DELETE_SQL = "DELETE FROM HoaDonChiTiet WHERE MaHDCT=?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDonChiTiet";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDonChiTiet WHERE MaHDCT=?";
    String SELECT_HDCT_SQL = "SELECT c.TenSP, d.TenTH, a.SoLuong, c.DonGia, c.MauSac FROM HoaDonChiTiet a INNER JOIN HoaDon b ON a.MaHD = b.MaHD INNER JOIN SanPham c ON a.MaSP = c.MaSP INNER JOIN ThuongHieu d on c.MaTH=d.MaTH";
    String DELETE_HDCT_MASP = "DELETE FROM HoaDonChiTiet WHERE MaSP=?";
    String SELECT_BY_ID_HOADON = "SELECT c.TenSP, d.TenTH, a.SoLuong, c.DonGia, c.MauSac FROM HoaDonChiTiet a INNER JOIN HoaDon b ON a.MaHD = b.MaHD INNER JOIN SanPham c ON a.MaSP = c.MaSP INNER JOIN ThuongHieu d on c.MaTH=d.MaTH and a.mahd =?";
    String DELETE_HDCT_MAHD = "DELETE FROM HoaDonChiTiet WHERE MaHD=?";
    String SELECT_THONGKE = "SELECT a.*, b.TenSP FROM HoaDonChiTiet a INNER JOIN SanPham b ON a.MaSP = b.MaSP";
    public void deleteByMaSP(String key) {
        JdbcHelper.executeUpdate(DELETE_HDCT_MASP, key);
    }
    public void deleteByMaHD(String key) {
        JdbcHelper.executeUpdate(DELETE_HDCT_MAHD, key);
    }
    public Object selectByIdHoaDon(String key) {
        return this.selectBySql(SELECT_BY_ID_HOADON, key);
    }
    @Override
    public void insert(HoaDonChiTiet entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaHDCT(),
                entity.getMaHd(),
                entity.getMaSp(),
                entity.getSoLuong());
    }

    @Override
    public void update(HoaDonChiTiet entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getMaHd(),
                entity.getMaSp(),
                entity.getSoLuong(),
                entity.getMaHDCT()
        );
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
       return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonChiTiet selectById(String key) {
        List<HoaDonChiTiet> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                HoaDonChiTiet entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private HoaDonChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonChiTiet entity = new HoaDonChiTiet();
        entity.setMaHDCT(rs.getString("MaHDCT"));
        entity.setMaHd(rs.getString("MaHD"));
        entity.setMaSp(rs.getString("MaSP"));
        entity.setSoLuong(rs.getInt("SoLuong"));
        
        return entity;
    }
    
    public List<HoaDonChiTiet> selectHDCT(String maHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(SELECT_BY_ID_HOADON, maHD);
            while (rs.next()) {
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setTenSp(rs.getString("TenSP"));
                entity.setThuongHieu(rs.getString("TenTH"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getInt("DonGia"));
                entity.setMauSac(rs.getString("MauSac"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<HoaDonChiTiet> selectThongKe(){
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(SELECT_THONGKE);
            while (rs.next()) {
                HoaDonChiTiet entity = new HoaDonChiTiet();
                entity.setMaHDCT(rs.getString("MaHDCT"));
                entity.setMaHd(rs.getString("MaHD"));
                entity.setMaSp(rs.getString("MaSP"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setTenSp(rs.getString("TenSP"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
