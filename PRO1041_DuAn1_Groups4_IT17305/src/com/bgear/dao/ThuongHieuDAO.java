/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.dao;

import com.bgear.entity.SanPham;
import com.bgear.entity.*;
import com.bgear.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS ZENBOOK
 */
public class ThuongHieuDAO extends BgearDAO<ThuongHieu, String>{

    String INSERT_SQL = "INSERT INTO ThuongHieu(MaTH, TenTH) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE ThuongHieu SET TenTH=? WHERE MaTH = ?";
    String DELETE_SQL = "DELETE FROM ThuongHieu WHERE MaTH=?";
    String SELECT_ALL_SQL = "SELECT * FROM ThuongHieu";
    String SELECT_BY_ID_SQL = "SELECT * FROM ThuongHieu WHERE MaTH=?";
    String SELECT_ID_THUONGHIEU = "SELECT * FROM THUONGHIEU WHERE TENTH=?";
    
    public ThuongHieu selectIDByName(String TenTH) {
        List<ThuongHieu> list = this.selectBySql(SELECT_ID_THUONGHIEU, TenTH);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void insert(ThuongHieu entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaTH(),
                entity.getTenTH());
    }

    @Override
    public void update(ThuongHieu entity) {
        JdbcHelper.executeUpdate(UPDATE_SQL,
                entity.getTenTH(),
                entity.getMaTH());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public List<ThuongHieu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ThuongHieu selectById(String key) {
        List<ThuongHieu> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ThuongHieu> selectBySql(String sql, Object... args) {
        List<ThuongHieu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                ThuongHieu entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private ThuongHieu readFromResultSet(ResultSet rs) throws SQLException {
        ThuongHieu entity = new ThuongHieu();
        entity.setMaTH(rs.getString("MaTH"));
        entity.setTenTH(rs.getString("TenTH"));
        
        return entity;
    }
}
