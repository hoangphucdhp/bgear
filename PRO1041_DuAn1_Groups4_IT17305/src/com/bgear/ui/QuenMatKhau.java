/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgear.ui;

import com.bgear.dao.*;
import com.bgear.entity.*;
import com.bgear.utils.MsgBox;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class QuenMatKhau extends javax.swing.JFrame {

    int randomNumber = 0;

    /**
     * Creates new form DangNhap
     */
    public QuenMatKhau() {
        initComponents();
        lblGiay.setVisible(false);
        lblError.setVisible(false);
    }

    private void runLoading() {
        class Loading1 extends Thread {

            @Override
            public void run() {
                for (int i = 60; i >= 0; i--) {
                    try {
                        Thread.sleep(1000);
                        lblGiay.setVisible(true);
                        lblGiay.setText(i + "s");
                        if (i == 0) {
                            randomNumber = 0;
                            lblGiay.setVisible(false);
                            lblError.setVisible(true);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Loading.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        Loading1 l = new Loading1();
        l.start();
    }

    NhanVienDAO nvdao = new NhanVienDAO();

    void ketThuc() {
        if (MsgBox.confirm(this, "Bạn có chắc muốn quay lại ?")) {
            new DangNhap().setVisible(true);
            dispose();
        }
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNv(txtTenTaiKhoan.getText());
        nv.setMatKhau(txtPassword.getText());
        return nv;
    }

    boolean checkTaiKhoan() {
        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            String maNV = nv.getMaNv();
            if (txtTenTaiKhoan.getText().equals(maNV)) {
                return true;//có tài khoản
            }
        }
        return false;//tài khoản không tồn tại
    }

    void checkMatKhau() {
        List<NhanVien> list = nvdao.selectAll();
        boolean check = false;
        for (NhanVien nv : list) {
            String matKhau = nv.getMatKhau();
            if (txtPassword.getText().equals(matKhau)) {
                check = true;
                break;
            }
        }
        if (check == true) {
            MsgBox.alert(this, "Bạn đã nhập mật khẩu cũ, vui lòng nhập mật khẩu mới để tiến hành đổi");
        } else {
            checkNhapLaiMatKhau();
        }
    }

    void checkNhapLaiMatKhau() {
        if (txtPassword.getText().equals(txtRePassword.getText())) {
            NhanVien nv = getForm();
            try {
                nvdao.updatePassword(nv);
                MsgBox.alert(this, "Đổi mật khẩu thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Đổi mật khẩu thất bại");
            }
        } else {
            MsgBox.alert(this, "Bạn nhập sai mật khẩu xác nhận");
        }
    }

    void xacNhan() {
        List<NhanVien> list = nvdao.selectAll();
        boolean check = false;
        for (NhanVien nv : list) {
            String matKhau = nv.getMatKhau();
            if (txtPassword.getText().equals(matKhau)) {
                check = true;
                break;
            }
        }
    }

    public void sendcode() {
        final String usernameAccount = "khuong8177@gmail.com";
        final String passwordAccount = "moxgjccchcnjwweg";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameAccount, passwordAccount);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(txtEmail.getText()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(txtEmail.getText())
            );

            message.setSubject("Hệ thống gửi mã xác nhận để thay đổi mật khẩu");
            Random objGenerator = new Random();
            for (int iCount = 0; iCount < 10; iCount++) {
                randomNumber = objGenerator.nextInt(1000000);
            }
            message.setSubject("Mail thay đổi mật khẩu");
            String html = ("<h1>Hello</h1> <br> Your Code Is </b></h2> <br> " + randomNumber);
            message.setContent(html, "text/html");
            jakarta.mail.Transport.send(message);
        } catch (MessagingException e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        btnSendCode = new com.k33ptoo.components.KButton();
        btnThoat = new com.k33ptoo.components.KButton();
        txtMaXacNhan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnXacNhan = new com.k33ptoo.components.KButton();
        txtPassword = new javax.swing.JPasswordField();
        txtRePassword = new javax.swing.JPasswordField();
        btnCheckTK = new com.k33ptoo.components.KButton();
        lblGiay = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("B-Gear Laptop Shop");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/wepik-photo-mode-20221024-9529.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 690));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtTenTaiKhoan.setBackground(new java.awt.Color(153, 204, 255));
        txtTenTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenTaiKhoan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 204, 255)));

        btnSendCode.setText("Gửi mã");
        btnSendCode.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSendCode.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnSendCode.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnSendCode.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnSendCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendCodeActionPerformed(evt);
            }
        });

        btnThoat.setText("Quay lại");
        btnThoat.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThoat.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnThoat.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnThoat.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        txtMaXacNhan.setBackground(new java.awt.Color(153, 204, 255));
        txtMaXacNhan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaXacNhan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 204, 255)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("Mã xác nhận");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 204));
        jLabel10.setText("Mật khẩu mới");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 204));
        jLabel12.setText("Xác nhận mật khẩu mới");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("QUÊN MẬT KHẨU");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 204));
        jLabel5.setText("Tên tài khoản");

        txtEmail.setEditable(false);
        txtEmail.setBackground(new java.awt.Color(153, 204, 255));
        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 204, 255)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("Email");

        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXacNhan.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXacNhan.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXacNhan.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        txtPassword.setBackground(new java.awt.Color(153, 204, 255));
        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 204, 255)));

        txtRePassword.setBackground(new java.awt.Color(153, 204, 255));
        txtRePassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRePassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 204, 255)));

        btnCheckTK.setText("Kiểm tra");
        btnCheckTK.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCheckTK.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnCheckTK.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnCheckTK.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnCheckTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckTKActionPerformed(evt);
            }
        });

        lblGiay.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblGiay.setText("60s");

        lblError.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 51));
        lblError.setText("Mã xác nhận đã hết hạn, vui lòng lấy mã mới !");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtRePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addComponent(btnCheckTK, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(284, 284, 284))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtMaXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnSendCode, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblGiay))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblError)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCheckTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addGap(7, 7, 7)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSendCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGiay)))
                .addGap(16, 16, 16)
                .addComponent(jLabel10)
                .addGap(9, 9, 9)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel12)
                .addGap(11, 11, 11)
                .addComponent(txtRePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblError)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 510, 690));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (txtTenTaiKhoan.getText().equals("") || txtEmail.getText().equals("") || txtMaXacNhan.getText().equals("")
                || txtPassword.getText().equals("") || txtRePassword.getText().equals("")) {
            MsgBox.alert(this, "Không được để trống thông tin");
        } else {
            if (checkTaiKhoan() == false) { //tài khoản không tồn tại
                MsgBox.alert(this, "Tên tài khoản này không tồn tại");
                txtTenTaiKhoan.requestFocus();
            } else {
                if (txtPassword.equals("")) {
                    MsgBox.alert(this, "Không được để trống");
                } else {
                    if (txtMaXacNhan.getText().equals(String.valueOf(randomNumber))) {
                        checkMatKhau();
                    } else {
                        MsgBox.alert(this, "Bạn nhập sai mã xác nhận");
                    }
                }
            }
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        ketThuc();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnSendCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendCodeActionPerformed
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn bỏ trống Email");
        } else {
            this.sendcode();
            lblError.setVisible(false);
            runLoading();
            JOptionPane.showMessageDialog(this, "Mã xác nhận đã gửi qua email " + txtEmail.getText());
        }
    }//GEN-LAST:event_btnSendCodeActionPerformed

    private void btnCheckTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckTKActionPerformed
        List<NhanVien> list = nvdao.selectAll();
        String email;
        boolean check = true;
        for (NhanVien nv : list) {
            String maNV = nv.getMaNv();
            if (txtTenTaiKhoan.getText().equals(maNV)) {
                email = nv.getEmail();
                txtEmail.setText(email);
                check = false;
            }
        }
        if (check == true) {
            MsgBox.alert(this, "Không tìm thấy tài khoản " + txtTenTaiKhoan.getText());
        }
    }//GEN-LAST:event_btnCheckTKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhau().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KButton btnCheckTK;
    private com.k33ptoo.components.KButton btnSendCode;
    private com.k33ptoo.components.KButton btnThoat;
    private com.k33ptoo.components.KButton btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblGiay;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaXacNhan;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtRePassword;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
