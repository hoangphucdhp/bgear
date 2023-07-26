package com.bgear.dao;

import java.util.List;

/**
 *
 * @author ASUS ZENBOOK
 */
public abstract class BgearDAO<E,K> { //E: Entity, K: Key
    abstract public void insert(E entity);
    
    abstract public void update(E entity);
    
    abstract public void delete(K key);
    
    abstract public List<E> selectAll();
    
    abstract public E selectById(K key);
    
    abstract public List<E> selectBySql(String sql, Object...args);
}
