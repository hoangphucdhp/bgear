/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.dao;

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
public class ChucVuDAO extends BgearDAO<ChucVu, String>{

    String INSERT_SQL = "INSERT INTO ChucVu(MaCV, CV) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE ChucVu SET MaCV=?, CV=?";
    String DELETE_SQL = "DELETE FROM ChucVu WHERE MaCV=?";
    String SELECT_ALL_SQL = "SELECT * FROM ChucVu";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChucVu WHERE MaCV=?";
    String SELECT_BY_TenCv_SQL = "SELECT * FROM ChucVu WHERE CV=?";
    
    @Override
    public void insert(ChucVu entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getMaCV(),
                entity.getTenCV());
    }

    @Override
    public void update(ChucVu entity) {
        JdbcHelper.executeUpdate(INSERT_SQL,
                entity.getTenCV(),
                entity.getMaCV());
    }

    @Override
    public void delete(String key) {
        JdbcHelper.executeUpdate(DELETE_SQL, key);
    }

    @Override
    public List<ChucVu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChucVu selectById(String key) {
        List<ChucVu> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public List<ChucVu> selectByKey(String key) {
        return this.selectBySql(SELECT_BY_TenCv_SQL, key);      
    }

    @Override
    public List<ChucVu> selectBySql(String sql, Object... args) {
        List<ChucVu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                ChucVu entity = readFromResultSet(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private ChucVu readFromResultSet(ResultSet rs) throws SQLException {
        ChucVu entity = new ChucVu();
        entity.setMaCV(rs.getString("MaCV"));
        entity.setTenCV(rs.getString("CV"));
        
        return entity;
    }
    
}
