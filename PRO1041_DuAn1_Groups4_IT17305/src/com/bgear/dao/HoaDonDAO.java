package com.bgear.dao;

import com.bgear.entity.*;
import com.bgear.utils.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonDAO extends BgearDAO<HoaDon, String>{
    String INSERT_SQL = "INSERT INTO HoaDon(MaHD, MaKH, MaNV, NgayLapHoaDon) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDon SET MaKH=?, MaNV=?, NgayLapHoaDon=? WHERE MaHD=?";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";
    String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    String SELECT_HOADON_SQL = "SELECT a.MaHD, c.TenKH ,b.HoTen, a.NgayLapHoaDon FROM HoaDon a INNER JOIN NhanVien b ON a.MaNV = b.MaNV INNER JOIN KhachHang c ON a.MaKH = c.MaKH";
    String SELECT_HOADON_BY_ID = "SELECT a.MaHD, c.TenKH ,b.HoTen, a.NgayLapHoaDon FROM HoaDon a INNER JOIN NhanVien b ON a.MaNV = b.MaNV INNER JOIN KhachHang c ON a.MaKH = c.MaKH and a.mahd =?";
    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaHd(),
                entity.getMaKh(),
                entity.getMaNv(),
                entity.getNgayLapHD());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,                
                entity.getMaKh(),
                entity.getMaNv(),
                entity.getNgayLapHD(),
                entity.getMaHd());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(String key) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon entity = new HoaDon();
        entity.setMaHd(rs.getString("MaHD"));
        entity.setMaKh(rs.getString("MaKH"));
        entity.setMaNv(rs.getString("MaNV"));
        entity.setNgayLapHD(rs.getString("NgayLapHoaDon"));
        
        return entity;
    }
    
    public List<HoaDon> selectHD() {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(SELECT_HOADON_SQL);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHd(rs.getString("MaHD"));
                entity.setTenKh(rs.getString("TenKH"));
                entity.setTenNv(rs.getString("HoTen"));
                entity.setNgayLapHD(rs.getString("NgayLapHoaDon"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<HoaDon> selectHDByID(String maHD) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(SELECT_HOADON_BY_ID, maHD);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHd(rs.getString("MaHD"));
                entity.setTenKh(rs.getString("TenKH"));
                entity.setTenNv(rs.getString("HoTen"));
                entity.setNgayLapHD(rs.getString("NgayLapHoaDon"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
