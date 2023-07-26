package com.bgear.dao;

import com.bgear.entity.*;
import com.bgear.utils.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO extends BgearDAO<SanPham, String> {

    String INSERT_SQL = "INSERT INTO SanPham(MaSP, TenSP, MaTH ,DonGia, Hinh, MauSac, MoTa) VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE SanPham SET TenSP=?, MaTH=?, DonGia=?, Hinh=?, MauSac=?, MoTa=? WHERE MaSP=?";
    String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP=?";
    String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham WHERE MaSP=?";
    String SELECT_BANHANG_SQL = "SELECT MaSP, TenSP, DonGia FROM SanPham ";
    String SELLECT_GIA_GIAM = "select * from sanpham order by DONGIA desc";
    String SELLECT_THEO_TEN = "select * from sanpham order by TENSP asc";
    @Override
    public void insert(SanPham entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaSp(),
                entity.getTenSp(),
                entity.getMaTH(),
                entity.getDonGia(),
                entity.getHinh(),
                entity.getMauSac(),
                entity.getMoTa());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getTenSp(),
                entity.getMaTH(),
                entity.getDonGia(),
                entity.getHinh(),
                entity.getMauSac(),
                entity.getMoTa(),
                entity.getMaSp());
    }
    
    public List<SanPham> selectGiaGiam (){
        return this.selectBySql(SELLECT_GIA_GIAM);
    }
    public List<SanPham> selectTheoTen (){
        return this.selectBySql(SELLECT_THEO_TEN);
    }
    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectById(String key) {
        List<SanPham> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                SanPham entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SanPham readFromResultSet(ResultSet rs) throws SQLException {
        SanPham entity = new SanPham();
        entity.setMaSp(rs.getString("MaSP"));
        entity.setTenSp(rs.getString("TenSP"));
        entity.setMaTH(rs.getString("MaTH"));
        entity.setDonGia(rs.getInt("DonGia"));
        Blob blob = rs.getBlob("Hinh");
        if (blob != null){
            entity.setHinh(blob.getBytes(1, (int) blob.length()));
        }        
        entity.setMauSac(rs.getString("MauSac"));
        entity.setMoTa(rs.getString("MoTa"));

        return entity;
    }

    public List<SanPham> selectBanHang() {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(SELECT_BANHANG_SQL);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSp(rs.getString("MaSP"));
                entity.setTenSp(rs.getString("TenSP"));
                entity.setDonGia(rs.getInt("DonGia"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
