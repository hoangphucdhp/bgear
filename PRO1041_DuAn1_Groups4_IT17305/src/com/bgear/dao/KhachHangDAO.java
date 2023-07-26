package com.bgear.dao;

import com.bgear.entity.*;
import com.bgear.utils.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO extends BgearDAO<KhachHang, String> {

    String INSERT_SQL = "INSERT INTO KhachHang(MaKH, TenKH, SDT ,DiaChi, GioiTinh) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?, SDT=?, DiaChi=?, GioiTinh=? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH=?";
    String SELECT_SDT = "select * from KhachHang where SDT = ?";

    @Override
    public void insert(KhachHang entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaKh(),
                entity.getTenKh(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.isGioiTinh());
    }

    @Override
    public void update(KhachHang entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getTenKh(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.isGioiTinh(),
                entity.getMaKh());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeQuery(DELETE_SQL, key);
    }

    public KhachHang selectSDT(String sdt) {
        List<KhachHang> list = this.selectBySql(SELECT_SDT, sdt);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String key) {
        List<KhachHang> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private KhachHang readFromResultSet(ResultSet rs) throws SQLException {
        KhachHang entity = new KhachHang();
        entity.setMaKh(rs.getString("MaKH"));
        entity.setTenKh(rs.getString("TenKH"));
        entity.setSDT(rs.getString("SDT"));
        entity.setDiaChi(rs.getString("DiaChi"));
        entity.setGioiTinh(rs.getBoolean("GioiTinh"));

        return entity;
    }

}
