/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bgear.utils;

import com.bgear.entity.NhanVien;

/**
 *
 * @author ASUS ZENBOOK
 */
public class Auth {
    public static NhanVien user = null;
    
    public static void clear(){
        user = null;
    }
    
//    public static boolean isLogin(){
//        return Auth.user!=null;
//    }
//    
    public static boolean quyenQuanLy(){
        return Auth.user.getTenCv().equals("Quản lý");
    }
}
