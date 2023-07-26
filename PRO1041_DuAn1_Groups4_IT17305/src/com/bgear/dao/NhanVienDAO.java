package com.bgear.dao;

import com.bgear.entity.*;
import com.bgear.utils.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAO extends BgearDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO NhanVien(MaNV, MaCV, HoTen, SDT, Email, DiaChi, GioiTinh, NgaySinh, MatKhau) VALUES(?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET MaCV=?, HoTen=?, SDT=?, Email=?, DiaChi=?, GioiTinh=?, NgaySinh=?, MatKhau=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";
    String UPDATE_PASSWORD = "UPDATE NhanVien SET MatKhau = ? WHERE MaNV = ?";
    String SELECT_MANVBYNAME = "SELECT * FROM NhanVien WHERE HoTen = ?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaNv(),
                entity.getMaCV(),
                entity.getHoTen(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getMatKhau());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getMaCV(),
                entity.getHoTen(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getMatKhau(),
                entity.getMaNv());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NhanVien selectByName(String key) {
        List<NhanVien> list = this.selectBySql(SELECT_MANVBYNAME, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePassword(NhanVien entity) {
        JdbcHelper.executeUpdate(UPDATE_PASSWORD,
                entity.getMatKhau(),
                entity.getMaNv());
    }
    
    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien entity = new NhanVien();
        entity.setMaNv(rs.getString("MaNV"));
        entity.setMaCV(rs.getString("MaCV"));        
        entity.setHoTen(rs.getString("HoTen"));        
        entity.setSDT(rs.getString("SDT"));        
        entity.setEmail(rs.getString("Email"));       
        entity.setDiaChi(rs.getString("DiaChi"));       
        entity.setGioiTinh(rs.getBoolean("GioiTinh"));
        entity.setNgaySinh(rs.getDate("NgaySinh"));
        entity.setMatKhau(rs.getString("MatKhau"));        
        
        return entity;
    }
}
