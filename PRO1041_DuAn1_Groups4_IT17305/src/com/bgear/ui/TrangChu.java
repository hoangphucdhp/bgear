/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgear.ui;

import com.bgear.dao.*;
import com.bgear.entity.*;
import com.bgear.utils.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Admin
 */
public final class TrangChu extends javax.swing.JFrame {

    /**
     * Creates new form New
     */
    public TrangChu(String userID, String ngDung, String role) {
        initComponents();
        init();
        fillToComboBox();
        fillToTable();
        checkDangNhap(role);
        txtDu.setEditable(false);
        txtThanhToan.setEditable(false);
        txtNgayLap.setEditable(false);
        txtMaHD.setEditable(false);
        txtTenSPBanHang.setEditable(false);
        txtGiaBanHang.setEditable(false);
        txtMauBanHang.setEditable(false);
        txtMotaBanHang.setEditable(false);
        txtHangBanHang.setEditable(false);
        tblBanHang.getColumnModel().getColumn(1).setPreferredWidth(270);
        tblSPHD.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblSPHD.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblSPHD.getColumnModel().getColumn(2).setPreferredWidth(100);
        txtTenKH.setEditable(false);
        txtDiaChiKH.setEditable(false);
        txtTenNVHD.setEditable(false);
        tblCTHD.getColumnModel().getColumn(0).setPreferredWidth(170);
        lblTenNV.setText(ngDung);
        lblCV.setText(role);
        lblMaNV.setText(userID);
        txtTenNVHD.setText(ngDung);
        tblHD.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblHD.getColumnModel().getColumn(2).setPreferredWidth(250);
        if (role.equals("Nhân viên")) {
            TrangNhanVien.setEnabled(false);
            btnDoanhThuQLBH.setVisible(false);
            btnDoanhThuQLNVXS.setVisible(false);
        } else {
            TrangNhanVien.setEnabled(true);
            btnDoanhThuQLBH.setVisible(true);
            btnDoanhThuQLNVXS.setVisible(true);
        }
    }

    String maKH;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    SanPhamDAO spdao = new SanPhamDAO();
    ThuongHieuDAO thdao = new ThuongHieuDAO();
    ChucVuDAO cvdao = new ChucVuDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    KhachHangDAO khdao = new KhachHangDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    HoaDonChiTietDAO hdctdao = new HoaDonChiTietDAO();
    TongHopDAO tkdao = new TongHopDAO();
    int row = 0;
    String fileName = null;
    byte[] sp_anh = null;
    private static String P_SDT
            = "(((\\+|)84)|0)(1|3|5|7|8|9)+([0-9]{8})";
    private static String P_Email
            = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";

    public void init() {
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(true);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }

    public void checkDangNhap(String role) {
        if (role.equals("Nhân viên")) {
            btnThemQLNV.setEnabled(false);
            btnSuaQLNV.setEnabled(false);
            btnXoaQLNV.setEnabled(false);
        }
    }

    public void fillComboBoxThuongHieu() {
        DefaultComboBoxModel modelThuongHieu = (DefaultComboBoxModel) cboThuongHieu.getModel();
        modelThuongHieu.removeAllElements();
        List<ThuongHieu> list = thdao.selectAll();
        for (ThuongHieu th : list) {
//            Object[] row = {th.getTenTH()};
            modelThuongHieu.addElement(th.getTenTH());
        }
    }

    public void fillComboBoxChucVu() {
        DefaultComboBoxModel modelChucVu = (DefaultComboBoxModel) cboChucVu.getModel();
        modelChucVu.removeAllElements();
        List<ChucVu> list = cvdao.selectAll();
        for (ChucVu cv : list) {
//            Object[] row = {cv.getTenCV()};
            modelChucVu.addElement(cv.getTenCV());
        }
    }

    public void fillTableNhanVien() {
        DefaultTableModel modelnv = (DefaultTableModel) tblNhanVien.getModel();
        modelnv.setRowCount(0);
        try {
            List<NhanVien> list = nvdao.selectAll();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNv(), nv.getHoTen(), nv.getTenCv()};
                modelnv.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableKhachHang() {
        DefaultTableModel modelkh = (DefaultTableModel) tblKhachHang.getModel();
        modelkh.setRowCount(0);
        try {
            List<KhachHang> list = khdao.selectAll();
            for (KhachHang kh : list) {
                String sex = null;
                if (kh.isGioiTinh() == true) {
                    sex = "Nữ";
                } else if (kh.isGioiTinh() == false) {
                    sex = "Nam";
                }
                Object[] row = {kh.getMaKh(), kh.getTenKh(), kh.getSDT(), kh.getDiaChi(), sex};
                modelkh.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableSanPham() {
        DefaultTableModel modelsp = (DefaultTableModel) tblSanPham.getModel();
        modelsp.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectAll();
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSp(), sp.getTenSp(), sp.getThuongHieu(), sp.getDonGia()};
                modelsp.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableHoaDon() {
        DefaultTableModel modelhd = (DefaultTableModel) tblHD.getModel();
        modelhd.setRowCount(0);
        try {
            List<HoaDon> list = hddao.selectHD();
            for (HoaDon hd : list) {
                Object[] row = {hd.getMaHd(), hd.getTenKh(), hd.getTenNv(), hd.getNgayLapHD()};
                modelhd.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableHoaDonChiTiet() {
        DefaultTableModel modelhdct = (DefaultTableModel) tblCTHD.getModel();
        modelhdct.setRowCount(0);
        int viTri = tblHD.getSelectedRow();
        String maHD = tblHD.getValueAt(viTri, 0).toString();
        try {
            List<HoaDonChiTiet> list = hdctdao.selectHDCT(maHD);
            for (HoaDonChiTiet hdct : list) {
                Object[] row = {hdct.getTenSp(), hdct.getThuongHieu(), hdct.getSoLuong(), hdct.getDonGia(), hdct.getMauSac()};
                modelhdct.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillTableBanHang() {
        DefaultTableModel modelbh = (DefaultTableModel) tblBanHang.getModel();
        modelbh.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectBanHang();
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSp(), sp.getTenSp(), sp.getDonGia()};
                modelbh.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void fillToComboBox() {
        fillComboBoxThuongHieu();
        fillComboBoxChucVu();
    }

    public void fillToTable() {
        fillTableNhanVien();
        fillTableKhachHang();
        fillTableSanPham();
        fillTableHoaDon();
        fillTableBanHang();
        fillTableThuongHieu();
        fillTableTKNV();
        fillTableTKSP();
    }
//----------------------------------Quản lý nhân viên--------------------------------------//

    public boolean checkLoiNhanVien() {
        List<NhanVien> list = nvdao.selectAll();
        String email = null;
        String sdt = null;
        for (NhanVien nv : list) {
            email = nv.getEmail();
            sdt = nv.getSDT();
        }
        if (txtMaNV.getText().equals("") || txtTenNV.getText().equals("") || txtEmail.getText().equals("")
                || txtSdtNv.getText().equals("") || txtNgaySinh.equals("") || txtPassword.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin!!!");
            return false;
        }
        Matcher matcherSdt = Pattern.compile(P_SDT).matcher(txtSdtNv.getText());
        Matcher matcherEmail = Pattern.compile(P_Email).matcher(txtEmail.getText());
        if (!matcherSdt.matches()) {
            MsgBox.alert(this, "Sai định dạng số điện thoại");
            return false;
        }
        if (txtSdtNv.getText().length() != 10) {
            MsgBox.alert(this, "Số điện thoại phải đủ 10 số");
            return false;
        }
        if (!matcherEmail.matches()) {
            MsgBox.alert(this, "Sai định dạng email");
            return false;
        }
        if (txtPassword.getText().length() > 5) {
            MsgBox.alert(this, "Mật khẩu phải hơn 5 số");
            return false;
        }
        if (txtSdtNv.getText().equals(sdt)) {
            MsgBox.alert(this, "Đã có nhân viên sử dụng số điện thoại này!");
            return false;
        }
        if (txtEmail.getText().equals(email)) {
            MsgBox.alert(this, "Đã có nhân viên sử dụng email này!");
            return false;
        }

        return true;
    }

    /* Trang QL Sản Phẩm */
    void taoMaSP() {
        int lastRow = 0;
        lastRow = tblSanPham.getRowCount();//đếm số dòng trong table
        String maSPCuoiCung = (String) tblSanPham.getValueAt(lastRow - 1, 0);
        int chuoi2 = Integer.parseInt(maSPCuoiCung.substring(2, 5));
        if (chuoi2 + 1 < 10) {
            txtMaSP.setText("SP00" + (chuoi2 + 1));
        } else if (chuoi2 + 1 < 100) {
            txtMaSP.setText("SP0" + (chuoi2 + 1));
        } else if (chuoi2 + 1 < 1000) {
            txtMaSP.setText("SP" + (chuoi2 + 1));
        }
    }

    void lamMoiQLSP() {
        txtMaSP.setText("");
        txtTenSp.setText("");
        cboThuongHieu.setSelectedIndex(0);
        txtDonGia.setText("");
        txtMauSac.setText("");
        txtMoTaSP.setText("");
        sp_anh = null;
        lblAnhSp.setText("Nhấn đúp để chọn ảnh");
        lblAnhSp.setIcon(null);
    }

    public void search(String str) {
        DefaultTableModel model = (DefaultTableModel) tblBanHang.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model); //TableRowSorter: Thao tác sắp xếp
        tblBanHang.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));//RowFilter: Lọc bảng
    }

    void timSPTheoMa() {
        DefaultTableModel modelsp = (DefaultTableModel) tblSanPham.getModel();
        modelsp.setRowCount(0);
        btgSapXepSP.clearSelection();
        try {
            SanPham sp = spdao.selectById(txtTimKiemSP.getText());
            Object[] row = {sp.getMaSp(), sp.getTenSp(), sp.getThuongHieu(), sp.getDonGia()};
            modelsp.addRow(row);
            txtMaSP.setText(sp.getMaSp());
            txtTenSp.setText(sp.getTenSp());
            cboThuongHieu.setSelectedItem(sp.getThuongHieu());
            txtDonGia.setText(String.valueOf(sp.getDonGia()));
            txtMauSac.setText(sp.getMauSac());
            txtMoTaSP.setText(sp.getMoTa());
        } catch (NullPointerException e) {
            MsgBox.alert(this, "Không tìm thấy sản phẩm có mã: " + txtTimKiemSP.getText() + "trong danh sách.");
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void sapXepTheoGiaGiam() {
        DefaultTableModel modelsp = (DefaultTableModel) tblSanPham.getModel();
        modelsp.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectGiaGiam();
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSp(), sp.getTenSp(), sp.getThuongHieu(), sp.getDonGia()};
                modelsp.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void searchKH(String str) {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model); //TableRowSorter: Thao tác sắp xếp
        tblKhachHang.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));//RowFilter: Lọc bảng
    }

    void sapXepTheoTen() {
        DefaultTableModel modelsp = (DefaultTableModel) tblSanPham.getModel();
        modelsp.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectTheoTen();
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSp(), sp.getTenSp(), sp.getThuongHieu(), sp.getDonGia()};
                modelsp.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    boolean checkForm() {
        if (txtMaSP.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống mã sản phẩm");
            txtMaSP.requestFocus();
            return false;
        }
        if (txtTenSp.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống tên sản phẩm");
            txtTenSp.requestFocus();
            return false;
        }
        if (txtDonGia.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống đơn giá");
            txtDonGia.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtDonGia.getText());
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Vui lòng nhập chữ vào số lượng");
            txtDonGia.requestFocus();
        }
        if (txtMauSac.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống màu sắc");
            txtMauSac.requestFocus();
            return false;
        }
        if (txtMoTaSP.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống mô tả sản phẩm");
            txtMoTaSP.requestFocus();
            return false;
        }
        return true;
    }

    SanPham getForm() {
        ThuongHieu th = thdao.selectIDByName(cboThuongHieu.getSelectedItem().toString().trim());
        SanPham sp = new SanPham();
        sp.setMaSp(txtMaSP.getText());
        sp.setTenSp(txtTenSp.getText());
        sp.setMaTH(th.getMaTH());
        sp.setDonGia(Integer.parseInt(txtDonGia.getText()));
        sp.setHinh(sp_anh);
        sp.setMauSac(txtMauSac.getText());
        sp.setMoTa(txtMoTaSP.getText());

        return sp;
    }

    void themSP() {
        if (checkForm()) {
            SanPham sp = getForm();
            try {
                spdao.insert(sp);
                this.fillTableSanPham();
                this.lamMoiQLSP();
                MsgBox.alert(this, "Thêm mới thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm mới thất bại");
                e.printStackTrace();
            }
        }
    }

    void suaSP() {
        if (checkForm()) {
            SanPham nv = getForm();
            boolean check = false;
            List<SanPham> list = spdao.selectAll();
            for (SanPham sp : list) {
                if (txtMaSP.getText().equals(sp.getMaSp())) {
                    check = true;
                    break;
                }
            }
            if (check == true) {
                try {
                    spdao.update(nv);
                    this.fillTableSanPham();
                    MsgBox.alert(this, "Cập nhật thành công");
                    btnXoaQLSP.setEnabled(true);
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại");
                    e.printStackTrace();
                }
            } else {
                MsgBox.alert(this, "Không tìm thấy nhân viên muốn cập nhật, vui lòng kiểm tra lại mã nhân viên");
            }
        }

    }

    void xoaSP() {
        if (txtMaSP.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần xóa.");
        } else {
            if (MsgBox.confirm(this, "Tất cả dữ liệu liên quan đến sản phẩm ở bảng hóa đơn chi tiết sẽ được xóa. Bạn chắc chắn muốn xóa chứ ?")) {
                String MaSP = txtMaSP.getText();
                hdctdao.deleteByMaSP(MaSP);
                spdao.delete(MaSP);
                this.fillTableSanPham();
                this.lamMoiQLSP();
                MsgBox.alert(this, "Xóa thành công");
            }
        }

    }

    /* Trang QL Sản Phẩm */
 /*----------------------------------------------------------------------------------------------------------------*/
 /* Trang QL Hóa đơn */
    void xoaHD() {
        try {
            int viTri = tblHD.getSelectedRow();
            String MaHD = tblHD.getValueAt(viTri, 0).toString();
            if (MsgBox.confirm(this, "Tất cả dữ liệu liên quan đến hóa đơn ở bảng hóa đơn chi tiết sẽ được xóa. Bạn chắc chắn muốn xóa chứ ?")) {
                hdctdao.deleteByMaHD(MaHD);
                hddao.delete(MaHD);
                clearCTHD();
                this.fillTableHoaDon();
                MsgBox.alert(this, "Xóa thành công");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            MsgBox.alert(this, "Vui lòng chọn hóa đơn cần xóa.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void clearCTHD() {
        txtMaHoaDon.setText("");
        txtNgayLapHoaDon.setText("");
        DefaultTableModel modelhdct = (DefaultTableModel) tblCTHD.getModel();
        modelhdct.setRowCount(0);
    }

    void timHDTheoMa() {

        if (txtMaHoaDonTimKiem.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mã hóa đơn cần tìm.");
            txtMaHoaDonTimKiem.requestFocus();
        } else {
            try {
                clearCTHD();
                String maHD = txtMaHoaDonTimKiem.getText();
                List<HoaDon> list = hddao.selectHDByID(maHD);
                if (list.size() < 1) {
                    MsgBox.alert(this, "Không tìm thấy hóa đơn có mã: " + txtMaHoaDonTimKiem.getText() + " trong danh sách.");
                } else {
                    DefaultTableModel modelhd = (DefaultTableModel) tblHD.getModel();
                    modelhd.setRowCount(0);
                    btgSapXepSP.clearSelection();
                    for (HoaDon hd : list) {
                        Object[] row = {hd.getMaHd(), hd.getTenKh(), hd.getTenNv(), hd.getNgayLapHD()};
                        modelhd.addRow(row);
                    }
                }

            } catch (NullPointerException e) {
                MsgBox.alert(this, "Không tìm thấy hóa đơn có mã: " + txtMaHoaDonTimKiem.getText() + "trong danh sách.");
            } catch (Exception e) {
                throw new RuntimeException(e);
                //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            }
        }

    }

    // Trang QLNV
    public void lamMoiNv() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        cboChucVu.setSelectedIndex(0);
        txtEmail.setText("");
        txtSdtNv.setText("");
        txtDiaChiNv.setText("");
        txtNgaySinh.setDate(null);
        txtPassword.setText("");
        row = 0;
    }

    public NhanVien getFormNv() {
        NhanVien nv = new NhanVien();
        nv.setMaNv(txtMaNV.getText());
        String tenCv = cboChucVu.getSelectedItem().toString();
        String maCv = null;
        List<ChucVu> list = cvdao.selectByKey(tenCv);
        for (ChucVu cv : list) {
            maCv = cv.getMaCV();
        }
        nv.setMaCV(maCv);
        nv.setTenCv(cboChucVu.getSelectedItem().toString());
        nv.setHoTen(txtTenNV.getText());
        nv.setSDT(txtSdtNv.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChiNv.getText());
        if (rdoNamNv.isSelected() == true) {
            nv.setGioiTinh(false);
        } else if (rdoNuNv.isSelected() == true) {
            nv.setGioiTinh(true);
        }
        nv.setNgaySinh(txtNgaySinh.getDate());
        nv.setMatKhau(txtPassword.getText());
        return nv;
    }

    public void setFormNv() {
        NhanVien nv = new NhanVien();
        txtMaNV.setText(nv.getMaNv());
        txtTenNV.setText(nv.getHoTen());
        cboChucVu.setSelectedItem(nv.getTenCv());
        txtEmail.setText(nv.getEmail());
        txtSdtNv.setText(nv.getSDT());
        txtDiaChiNv.setText(nv.getDiaChi());
        txtNgaySinh.setDate(nv.getNgaySinh());
        txtPassword.setText(nv.getMatKhau());
        if (nv.isGioiTinh() == false) {
            rdoNamNv.setSelected(true);
        } else if (nv.isGioiTinh() == true) {
            rdoNuNv.setSelected(true);
        }
//        return nv;
    }

    public void taoMaNv() {
        int lastRow = 0;
        lastRow = tblNhanVien.getRowCount();//đếm số dòng trong table
        String maNvCuoiCung = (String) tblNhanVien.getValueAt(lastRow - 1, 0);
//        String chuoi1 = maNvCuoiCung.substring(0, 2);
        int chuoi2 = Integer.parseInt(maNvCuoiCung.substring(2, 5));
        if (chuoi2 + 1 < 10) {
            txtMaNV.setText("NV00" + (chuoi2 + 1));
        } else if (chuoi2 + 1 < 100) {
            txtMaNV.setText("NV0" + (chuoi2 + 1));
        } else if (chuoi2 + 1 < 1000) {
            txtMaNV.setText("NV" + (chuoi2 + 1));
        }
    }

    public void themNv() {
        NhanVien nv = getFormNv();
        try {
            nvdao.insert(nv);
            this.fillTableNhanVien();
            this.lamMoiNv();
            MsgBox.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
//                throw new RuntimeException(e);
            MsgBox.alert(this, "Thêm mới thất bại");
        }
    }

    public void xoaNv() {
        if (!Auth.quyenQuanLy()) {
            MsgBox.alert(this, "bạn không có quyền xóa nhân viên");
        } else {
            String MaNv = txtMaNV.getText();
            if (MaNv.equals(Auth.user.getMaNv())) {
                MsgBox.alert(this, "Bạn không được xóa chính bạn!");
            } else if (MsgBox.confirm(this, "Bạn thật sự muốn xóa nhân viên này ?")) {
                String maNv = txtMaNV.getText();
                nvdao.delete(maNv);
                this.fillTableNhanVien();
                this.lamMoiNv();
                MsgBox.alert(this, "Xóa thành công");
            }
        }
    }

    public void suaNv() {
        NhanVien nv = getFormNv();
        boolean check = false;
        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nhv : list) {
            String maNv = nhv.getMaNv();
            if (txtMaNV.getText().equals(maNv)) {
                check = true;
                break;
            }
        }
        if (check == true) {
            try {
                nvdao.update(nv);
                this.fillTableNhanVien();
                MsgBox.alert(this, "Cập nhật thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại");
            }
        } else {
            MsgBox.alert(this, "Không tìm thấy nhân viên muốn cập nhật, vui lòng kiểm tra lại mã nhân viên");
        }
    }

    public void searchNV(String str) {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model); //TableRowSorter: Thao tác sắp xếp
        tblNhanVien.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));//RowFilter: Lọc bảng
    }
//---------------------------------------------------------------------------------------------------------------------------//
//---------------------------------------Quản Lý Khách Hàng-----------------------------------------------------------------//

    public void lamMoiKh() {
        txtTenKH3.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtTenKH3.requestFocus();
    }

    boolean checkFormKH() {
        if (txtMaKH.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng chọn khách hàng muốn sửa.");
            txtMaKH.requestFocus();
            return false;
        }
        if (txtTenKH3.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên khách hàng.");
            txtTenKH3.requestFocus();
            return false;
        }
        if (txtSDT.getText().equals("")) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ! Vui lòng nhập lại.");
            txtSDT.requestFocus();
            return false;
        } else if (txtSDT.getText().length() != 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ! Vui lòng nhập lại.");
            txtSDT.requestFocus();
            return false;
        } else if (!"0".equals(txtSDT.getText().substring(0, 1)) || "00".equals(txtSDT.getText().substring(0, 2))) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ");
            txtSDT.requestFocus();
            return false;
        } else {
            List<KhachHang> list = khdao.selectAll();
            String maKH = null;
            String sdt = null;
            for (KhachHang kh : list) {
                maKH = kh.getMaKh();
                sdt = kh.getSDT();
            }
            if (txtSDT.getText().equals(sdt)) {
                MsgBox.alert(this, "Số điện thoại đã tồn tại! Vui lòng nhập lại.");
                txtSDT.requestFocus();
                return false;
            }
        }
        try {
            Integer.parseInt(txtSDT.getText());
        } catch (Exception e) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ! Vui lòng nhập lại.");
            txtSDT.requestFocus();
            return false;
        }
        if (txtDiaChi.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập địa chỉ.");
            txtDiaChi.requestFocus();
            return false;
        }
        return true;
    }

    KhachHang getFormKh() {
        KhachHang kh = new KhachHang();
        kh.setMaKh(txtMaKH.getText());
        kh.setTenKh(txtTenKH3.getText());
        kh.setSDT(txtSDT.getText());
        kh.setDiaChi(txtDiaChi.getText());
        if (rdoNamKh.isSelected() == true) {
            kh.setGioiTinh(false);
        } else if (rdoNuKh.isSelected() == true) {
            kh.setGioiTinh(true);
        }
        return kh;
    }

    HoaDon getFormBH() {
        HoaDon hd = new HoaDon();
        String ngayLapHD = txtNgayLap.getText();
        hd.setMaHd(txtMaHD.getText());
        hd.setNgayLapHD(ngayLapHD);
        hd.setMaKh(maKH);
        hd.setMaNv(lblMaNV.getText());
        return hd;
    }

    KhachHang getFormKH() {
        KhachHang kh = new KhachHang();
        List<KhachHang> list = khdao.selectAll();
        int maKH = 0;
        for (KhachHang kh1 : list) {
            maKH = Integer.parseInt(kh1.getMaKh().substring(3, 5));
        }
        maKH = maKH + 1;
        kh.setMaKh("KH0" + maKH);
        kh.setTenKh(txtTenKH.getText());
        kh.setDiaChi(txtDiaChiKH.getText());
        kh.setSDT(txtSDTKH.getText());
        boolean checkgt = true;
        if (rdoNamKH.isSelected()) {
            checkgt = false;
        }
        kh.setGioiTinh(checkgt);
        return kh;
    }
//------------------------------------------------------------------------------------------------------------------//
//---------------------------------------------------Quản lý thương hiệu--------------------------------------------//

    public void searchTH(String str) {
        DefaultTableModel model = (DefaultTableModel) tblThuongHieu.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model); //TableRowSorter: Thao tác sắp xếp
        tblThuongHieu.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));//RowFilter: Lọc bảng
    }

    public void fillTableThuongHieu() {
        DefaultTableModel modelth = (DefaultTableModel) tblThuongHieu.getModel();
        modelth.setRowCount(0);
        try {
            List<ThuongHieu> list = thdao.selectAll();
            for (ThuongHieu th : list) {
                Object[] row = {th.getMaTH(), th.getTenTH()};
                modelth.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
            //MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setFormTH(ThuongHieu th) {
        txtMaTH.setText(th.getMaTH());
        txtTenTH.setText(th.getTenTH());

    }

    ThuongHieu getFormTH() {
        ThuongHieu th = new ThuongHieu();
        th.setMaTH(txtMaTH.getText());
        th.setTenTH(txtTenTH.getText());

        return th;
    }

    void editTH() {
        String maTH = (String) tblThuongHieu.getValueAt(this.row, 0);
        ThuongHieu th = thdao.selectById(maTH);
        this.setFormTH(th);
        this.updateStatusTH();
    }

    void updateStatusTH() {
        boolean edit = (this.row >= 0);

        txtMaTH.setEditable(false);
        btnThemTH.setEnabled(false);
        btnSuaTH.setEnabled(true);
        btnXoaTH.setEnabled(true);

    }

    void ClearFormTH() {
        ThuongHieu th = new ThuongHieu();
        if (th != null) {
            this.setFormTH(th);
            this.row = 0;
            this.updateStatusTH();
            btnThemTH.setEnabled(true);
            txtMaTH.setEditable(true);
            txtMaTH.requestFocus();
            btnXoaTH.setEnabled(false);
        }
    }

    void updateTH() {
        boolean check = false;
        List<ThuongHieu> list = thdao.selectAll();
        for (ThuongHieu nv : list) {
            String maTH = nv.getMaTH();
            if (txtMaTH.getText().equals(maTH)) {
                check = true;
                break;
            }
        }
        if (check == true) {
            try {
                ThuongHieu nh = getFormTH();
                try {
                    thdao.update(nh);
                    this.fillTableThuongHieu();
                    MsgBox.alert(this, "Cập nhật thành công");
                    btnXoaTH.setEnabled(true);
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại");
                    System.out.println(e);
                }
            } catch (Exception e) {
            }
        } else {
            MsgBox.alert(this, "Không tìm thấy thương hiệu muốn cập nhật, vui lòng kiểm tra lại!!!");
        }
    }

    void ThemThuongHieu() {

        ThuongHieu th = getFormTH();
        try {
            thdao.insert(th);
            this.fillTableThuongHieu();
            this.ClearFormTH();
            MsgBox.alert(this, "Thêm mới thành công");
            txtMaTH.setEditable(false);
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại, trùng mã thương hiệu");

        }
    }

    void deleteTH() {
        int delete = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa người học này ?", "Xóa người học", JOptionPane.YES_NO_OPTION);
        String maTH = txtMaTH.getText();
        if (delete == JOptionPane.YES_OPTION) {
            try {
                thdao.delete(maTH);
                this.fillTableThuongHieu();
                this.ClearFormTH();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        } else if (delete == JOptionPane.NO_OPTION) {
            MsgBox.alert(this, "Bạn đã hủy thao tác");
        }
    }
//----------------------------------------------------------------------------------------------------//
//---------------------------------------------TỔNG HỢP THỐNG KÊ--------------------------------------//

    public void fillTableTKSP() {
        int month = Integer.parseInt(cboThangTKBH.getSelectedItem().toString());
        int year = Integer.parseInt(cboNamTKBH.getSelectedItem().toString());
        int day = Integer.parseInt(cboNgayTKBH.getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) tblTKSP.getModel();
        model.setRowCount(0);
        if (cboNgayTKBH.getSelectedIndex() != 0) {
            List<Object[]> listday = tkdao.getBanHangNgay(day, month, year);
            for (Object[] row : listday) {
                model.addRow(new Object[]{row[0], row[1], row[2]});
            }
        } else if (cboThangTKBH.getSelectedIndex() != 0) {
            List<Object[]> list = tkdao.getBanHangThang(month, year);
            for (Object[] row : list) {
                model.addRow(new Object[]{row[0], row[1], row[2]});
            }
        } else {
            List<Object[]> listyear = tkdao.getBanHangNam(year);
            for (Object[] row : listyear) {
                model.addRow(new Object[]{row[0], row[1], row[2]});
            }
        }
    }

    public void fillTableTKNV() {
        int month = Integer.parseInt(cboThangTKNV.getSelectedItem().toString());
        int year = Integer.parseInt(cboNamTKNV.getSelectedItem().toString());
        DefaultTableModel model = (DefaultTableModel) tblTKNV.getModel();
        model.setRowCount(0);
        List<Object[]> list = tkdao.getNhanVien(month, year);
        for (Object[] row : list) {
            model.addRow(new Object[]{row[0], row[1], row[2]});
        }
    }
//Startttttttt//

    public void setThongKeChart() {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        List<DoanhThu> list = tkdao.getDoanhThu();
        for (DoanhThu dt : list) {
            data.addValue(dt.getTien(), "kVND", dt.getThang());
        }
// create a chart...
        JFreeChart barChart = ChartFactory.createBarChart(
                "Doanh thu", "Tháng", "Tiền",
                data, PlotOrientation.VERTICAL,
                true, true, true
        );
// create and display a frame...
        ChartFrame frame = new ChartFrame("Thống kê doanh thu", barChart);
        frame.setExtendedState(ChartFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }

//STOPPPPPPPPPPPPPPPPPPPPPPPP//
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btgSapXepSP = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        pnlLeftContent = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        TrangChu = new javax.swing.JLabel();
        TrangBanHang = new javax.swing.JLabel();
        TrangHoaDon = new javax.swing.JLabel();
        TrangSanPham = new javax.swing.JLabel();
        TrangNhanVien = new javax.swing.JLabel();
        TrangKhachHang = new javax.swing.JLabel();
        TongHopThongKe = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblCV = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblMaNV = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        pnlRightContent = new javax.swing.JPanel();
        pnlCuaSoChinh = new javax.swing.JPanel();
        TieuDe = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlBanHang = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jpDSSP = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblHinhSP = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMotaBanHang = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        txtTenSPBanHang = new javax.swing.JTextField();
        txtGiaBanHang = new javax.swing.JTextField();
        txtMauBanHang = new javax.swing.JTextField();
        txtHangBanHang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBanHang = new javax.swing.JTable();
        txtSoLuong = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnThemSoLuong = new com.k33ptoo.components.KButton();
        jPanel4 = new javax.swing.JPanel();
        jpDSHD = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPHD = new javax.swing.JTable();
        btnXoaQLBH = new com.k33ptoo.components.KButton();
        txtSoLuongHoaDon = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jpThanhToan = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        txtDaThu = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDu = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        btnTimSDTQLBH = new com.k33ptoo.components.KButton();
        jLabel13 = new javax.swing.JLabel();
        txtTenNVHD = new javax.swing.JTextField();
        txtDiaChiKH = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        rdoNamKH = new javax.swing.JRadioButton();
        rdoNuKH = new javax.swing.JRadioButton();
        btnXuatHoaDon = new com.k33ptoo.components.KButton();
        pnlHoaDon = new javax.swing.JPanel();
        jpDSSP1 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        txtMaHoaDonTimKiem = new javax.swing.JTextField();
        btnTimQLHD = new com.k33ptoo.components.KButton();
        btnXoaQLHD = new com.k33ptoo.components.KButton();
        btnLamMoiQLHD = new com.k33ptoo.components.KButton();
        btnHienThiTatCaHoaDon = new com.k33ptoo.components.KButton();
        jpDSSP3 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jpDSSP4 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtNgayLapHoaDon = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtTongTienHoaDon = new javax.swing.JTextField();
        pnlSanPham = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        txtTimKiemSP = new javax.swing.JTextField();
        rdoTen = new javax.swing.JRadioButton();
        rdoGiaGiamDan = new javax.swing.JRadioButton();
        btnTimQLSP = new com.k33ptoo.components.KButton();
        rdoHienThiTatCa = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSp = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cboThuongHieu = new javax.swing.JComboBox<>();
        txtDonGia = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btnSuaQLSP = new com.k33ptoo.components.KButton();
        btnLamMoiQLSP = new com.k33ptoo.components.KButton();
        btnThemQLSP = new com.k33ptoo.components.KButton();
        btnXoaQLSP = new com.k33ptoo.components.KButton();
        txtMauSac = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtMoTaSP = new javax.swing.JTextArea();
        lblAnhSp = new javax.swing.JLabel();
        btnQuanLyThuongHieu = new com.k33ptoo.components.KButton();
        pnlThongKeBanHang = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        cboThangTKBH = new javax.swing.JComboBox<>();
        cboNamTKBH = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cboNgayTKBH = new javax.swing.JComboBox<>();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblTKSP = new javax.swing.JTable();
        btnDoanhThuQLBH = new com.k33ptoo.components.KButton();
        TieuDe1 = new javax.swing.JLabel();
        btnNhanVienQLBH = new com.k33ptoo.components.KButton();
        pnlKhachHang = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH3 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        rdoNamKh = new javax.swing.JRadioButton();
        rdoNuKh = new javax.swing.JRadioButton();
        btnSuaQLKH = new com.k33ptoo.components.KButton();
        btnLamMoiQLKH = new com.k33ptoo.components.KButton();
        jPanel11 = new javax.swing.JPanel();
        txtTimTenKH = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        pnlNhanVien = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtSdtNv = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtDiaChiNv = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        rdoNamNv = new javax.swing.JRadioButton();
        rdoNuNv = new javax.swing.JRadioButton();
        jLabel46 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        cboChucVu = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        btnThemQLNV = new com.k33ptoo.components.KButton();
        btnSuaQLNV = new com.k33ptoo.components.KButton();
        btnLamMoiQLNV = new com.k33ptoo.components.KButton();
        btnXoaQLNV = new com.k33ptoo.components.KButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel14 = new javax.swing.JPanel();
        txtMaNVTimKiem = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        pnlThongKeDoanhThu = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        pnl21 = new javax.swing.JPanel();
        btnBanHangTKDT = new com.k33ptoo.components.KButton();
        btnNhanVienTKDT = new com.k33ptoo.components.KButton();
        TieuDe2 = new javax.swing.JLabel();
        pnlThongKeNhanVien = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        cboThangTKNV = new javax.swing.JComboBox<>();
        cboNamTKNV = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblTKNV = new javax.swing.JTable();
        btnDoanhThuQLNVXS = new com.k33ptoo.components.KButton();
        btnBanHangQLNVSX = new com.k33ptoo.components.KButton();
        TieuDe3 = new javax.swing.JLabel();
        pnlThuongHieu = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblThuongHieu = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        txtTimKiemTH = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtMaTH = new javax.swing.JTextField();
        txtTenTH = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        btnThemTH = new com.k33ptoo.components.KButton();
        btnLamMoiTH = new com.k33ptoo.components.KButton();
        btnSuaTH = new com.k33ptoo.components.KButton();
        btnXoaTH = new com.k33ptoo.components.KButton();
        jLabel32 = new javax.swing.JLabel();
        btnQuayLai = new com.k33ptoo.components.KButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmeDangXuat = new javax.swing.JMenu();
        jmeHuongDAn = new javax.swing.JMenu();
        jmeGioiTthieu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("B-Gear Laptop Shop");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeftContent.setMaximumSize(new java.awt.Dimension(611, 800));
        pnlLeftContent.setMinimumSize(new java.awt.Dimension(611, 800));
        pnlLeftContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/B-GEAR-removebg-preview (2).png"))); // NOI18N
        pnlLeftContent.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 30, 300, 140));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel20MouseEntered(evt);
            }
        });
        pnlLeftContent.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(-510, 0, 200, 910));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        pnlLeftContent.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 1100, 700));

        kGradientPanel1.setkEndColor(new java.awt.Color(153, 204, 255));
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 153, 255));

        TrangChu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangChu.setForeground(new java.awt.Color(255, 255, 255));
        TrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/home_50px (1).png"))); // NOI18N
        TrangChu.setText("GIAO DIỆN MỞ ĐẦU");
        TrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangChuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TrangChuMouseEntered(evt);
            }
        });

        TrangBanHang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangBanHang.setForeground(new java.awt.Color(255, 255, 255));
        TrangBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/click_&_collect_20px.png"))); // NOI18N
        TrangBanHang.setText("QUẢN LÝ BÁN HÀNG");
        TrangBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangBanHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TrangBanHangMouseEntered(evt);
            }
        });

        TrangHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        TrangHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/paid_bill_20px.png"))); // NOI18N
        TrangHoaDon.setText("QUẢN LÝ HÓA ĐƠN");
        TrangHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangHoaDonMouseClicked(evt);
            }
        });

        TrangSanPham.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangSanPham.setForeground(new java.awt.Color(255, 255, 255));
        TrangSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/laptop_20px.png"))); // NOI18N
        TrangSanPham.setText("QUẢN LÝ SẢN PHẨM");
        TrangSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangSanPhamMouseClicked(evt);
            }
        });

        TrangNhanVien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        TrangNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/people_20px.png"))); // NOI18N
        TrangNhanVien.setText("QUẢN LÝ NHÂN VIÊN");
        TrangNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangNhanVienMouseClicked(evt);
            }
        });

        TrangKhachHang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TrangKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        TrangKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/Users_20px.png"))); // NOI18N
        TrangKhachHang.setText("QUẢN LÝ KHÁCH HÀNG");
        TrangKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TrangKhachHangMouseClicked(evt);
            }
        });

        TongHopThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TongHopThongKe.setForeground(new java.awt.Color(255, 255, 255));
        TongHopThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/bar_chart_20px.png"))); // NOI18N
        TongHopThongKe.setText("TỔNG HỢP - THỐNG KÊ");
        TongHopThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TongHopThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TongHopThongKeMouseEntered(evt);
            }
        });

        lblTenNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNV.setText("Tên nhân viên");

        lblCV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCV.setForeground(new java.awt.Color(255, 255, 255));
        lblCV.setText("Chức vụ");

        lblMaNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaNV.setForeground(new java.awt.Color(255, 255, 255));
        lblMaNV.setText("Mã nhân viên");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TrangKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TrangNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TrangSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TrangBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TrangChu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TongHopThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenNV)
                                    .addComponent(lblMaNV)
                                    .addComponent(lblCV)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                    .addContainerGap(22, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(61, Short.MAX_VALUE)))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(TrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(TrangBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TrangHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TrangSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TrangNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TrangKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(TongHopThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblMaNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCV)
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                    .addContainerGap(543, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(165, Short.MAX_VALUE)))
        );

        pnlLeftContent.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 710));

        getContentPane().add(pnlLeftContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, -7, 280, 710));

        pnlRightContent.setLayout(new javax.swing.OverlayLayout(pnlRightContent));

        TieuDe.setBackground(new java.awt.Color(0, 153, 153));
        TieuDe.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TieuDe.setForeground(new java.awt.Color(0, 153, 153));
        TieuDe.setText("ỨNG DỤNG QUẢN LÝ CỬA HÀNG BÁN LAPTOP B-GEAR");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/banner3.png"))); // NOI18N

        javax.swing.GroupLayout pnlCuaSoChinhLayout = new javax.swing.GroupLayout(pnlCuaSoChinh);
        pnlCuaSoChinh.setLayout(pnlCuaSoChinhLayout);
        pnlCuaSoChinhLayout.setHorizontalGroup(
            pnlCuaSoChinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuaSoChinhLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(pnlCuaSoChinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCuaSoChinhLayout.createSequentialGroup()
                    .addGap(172, 172, 172)
                    .addComponent(TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(179, Short.MAX_VALUE)))
        );
        pnlCuaSoChinhLayout.setVerticalGroup(
            pnlCuaSoChinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCuaSoChinhLayout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(pnlCuaSoChinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCuaSoChinhLayout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(TieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(643, Short.MAX_VALUE)))
        );

        pnlRightContent.add(pnlCuaSoChinh);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1202, 720));

        jpDSSP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết sản phẩm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Tìm kiếm theo mã sản phẩm");

        lblHinhSP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblHinhSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinhSP.setText("Hình Ảnh");
        lblHinhSP.setToolTipText("");
        lblHinhSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Hãng");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Giá");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Màu");

        txtMotaBanHang.setColumns(20);
        txtMotaBanHang.setRows(5);
        jScrollPane3.setViewportView(txtMotaBanHang);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Mô tả");

        txtTim.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTimMousePressed(evt);
            }
        });
        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKeyReleased(evt);
            }
        });

        txtTenSPBanHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtGiaBanHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtMauBanHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtHangBanHang.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout jpDSSPLayout = new javax.swing.GroupLayout(jpDSSP);
        jpDSSP.setLayout(jpDSSPLayout);
        jpDSSPLayout.setHorizontalGroup(
            jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTim)
                    .addGroup(jpDSSPLayout.createSequentialGroup()
                        .addComponent(lblHinhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSPBanHang)
                            .addGroup(jpDSSPLayout.createSequentialGroup()
                                .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtGiaBanHang)
                            .addComponent(txtHangBanHang, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane3)
                    .addGroup(jpDSSPLayout.createSequentialGroup()
                        .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel18)
                            .addComponent(txtMauBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(0, 172, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpDSSPLayout.setVerticalGroup(
            jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHinhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHangBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDSSPLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(txtTenSPBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel5)
                        .addGap(9, 9, 9)
                        .addComponent(txtGiaBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMauBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        tblBanHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBanHang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblBanHang.setSurrendersFocusOnKeystroke(true);
        tblBanHang.setUpdateSelectionOnSort(false);
        tblBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBanHang);

        txtSoLuong.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Số lượng");

        btnThemSoLuong.setText("Thêm");
        btnThemSoLuong.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemSoLuong.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnThemSoLuong.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnThemSoLuong.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnThemSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSoLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDSSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThemSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpDSSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jpDSHD.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jpDSHD.setMinimumSize(new java.awt.Dimension(488, 313));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Mã hóa đơn");

        txtMaHD.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Ngày lập hóa đơn");

        txtNgayLap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblSPHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSPHD);

        btnXoaQLBH.setText("Xóa");
        btnXoaQLBH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaQLBH.setkEndColor(new java.awt.Color(255, 51, 51));
        btnXoaQLBH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXoaQLBH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXoaQLBH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXoaQLBH.setkStartColor(new java.awt.Color(255, 0, 51));
        btnXoaQLBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaQLBHActionPerformed(evt);
            }
        });

        txtSoLuongHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongHoaDonKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Số lượng");

        javax.swing.GroupLayout jpDSHDLayout = new javax.swing.GroupLayout(jpDSHD);
        jpDSHD.setLayout(jpDSHDLayout);
        jpDSHDLayout.setHorizontalGroup(
            jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDSHDLayout.createSequentialGroup()
                        .addGroup(jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(txtSoLuongHoaDon))
                        .addGap(103, 103, 103)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDSHDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXoaQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jpDSHDLayout.setVerticalGroup(
            jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSHDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jpDSHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtSoLuongHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jpThanhToan.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thanh toán", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jpThanhToan.setMinimumSize(new java.awt.Dimension(488, 313));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Số tiền thanh toán");

        txtThanhToan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDaThu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDaThu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDaThuKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDaThuKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Địa chỉ");

        txtDu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Dư");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Tên khách hàng");

        txtTenKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Số điện thoại");

        txtSDTKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSDTKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTKHActionPerformed(evt);
            }
        });

        btnTimSDTQLBH.setText("Tìm");
        btnTimSDTQLBH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTimSDTQLBH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnTimSDTQLBH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnTimSDTQLBH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnTimSDTQLBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimSDTQLBHActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Đã thu");

        txtTenNVHD.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDiaChiKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Tên nhân viên");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Giới tính");

        buttonGroup1.add(rdoNamKH);
        rdoNamKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNamKH.setSelected(true);
        rdoNamKH.setText("Nam");

        buttonGroup1.add(rdoNuKH);
        rdoNuKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNuKH.setText("Nữ");

        javax.swing.GroupLayout jpThanhToanLayout = new javax.swing.GroupLayout(jpThanhToan);
        jpThanhToan.setLayout(jpThanhToanLayout);
        jpThanhToanLayout.setHorizontalGroup(
            jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThanhToanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThanhToanLayout.createSequentialGroup()
                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThanhToanLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(154, 154, 154)
                                .addComponent(jLabel9))
                            .addGroup(jpThanhToanLayout.createSequentialGroup()
                                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpThanhToanLayout.createSequentialGroup()
                                        .addComponent(rdoNamKH)
                                        .addGap(54, 54, 54)
                                        .addComponent(rdoNuKH, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(82, 82, 82)
                                .addComponent(jLabel12)))
                        .addGap(0, 579, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpThanhToanLayout.createSequentialGroup()
                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtThanhToan, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThanhToanLayout.createSequentialGroup()
                                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpThanhToanLayout.createSequentialGroup()
                                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(txtDaThu, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtDiaChiKH))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addGroup(jpThanhToanLayout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtDu, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel15))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpThanhToanLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txtTenNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(128, 128, 128))
                            .addGroup(jpThanhToanLayout.createSequentialGroup()
                                .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimSDTQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jpThanhToanLayout.setVerticalGroup(
            jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThanhToanLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpThanhToanLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(30, 30, 30))
                    .addGroup(jpThanhToanLayout.createSequentialGroup()
                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDaThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNVHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiaChiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimSDTQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNamKH)
                    .addComponent(rdoNuKH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnXuatHoaDon.setText("Xuất hóa đơn");
        btnXuatHoaDon.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXuatHoaDon.setkBorderRadius(40);
        btnXuatHoaDon.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXuatHoaDon.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXuatHoaDon.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpDSHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jpDSHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thanh toán", jPanel4);

        javax.swing.GroupLayout pnlBanHangLayout = new javax.swing.GroupLayout(pnlBanHang);
        pnlBanHang.setLayout(pnlBanHangLayout);
        pnlBanHangLayout.setHorizontalGroup(
            pnlBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        pnlBanHangLayout.setVerticalGroup(
            pnlBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBanHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );

        pnlRightContent.add(pnlBanHang);

        jpDSSP1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel53.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel53.setText("Mã hóa đơn");

        btnTimQLHD.setText("Tìm");
        btnTimQLHD.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTimQLHD.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnTimQLHD.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnTimQLHD.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnTimQLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimQLHDActionPerformed(evt);
            }
        });

        btnXoaQLHD.setText("Xóa");
        btnXoaQLHD.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaQLHD.setkEndColor(new java.awt.Color(255, 51, 51));
        btnXoaQLHD.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXoaQLHD.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXoaQLHD.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXoaQLHD.setkStartColor(new java.awt.Color(255, 0, 51));
        btnXoaQLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaQLHDActionPerformed(evt);
            }
        });

        btnLamMoiQLHD.setText("Làm mới");
        btnLamMoiQLHD.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoiQLHD.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLHD.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnLamMoiQLHD.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiQLHDActionPerformed(evt);
            }
        });

        btnHienThiTatCaHoaDon.setText("Hiển thị tất cả danh sách");
        btnHienThiTatCaHoaDon.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnHienThiTatCaHoaDon.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnHienThiTatCaHoaDon.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnHienThiTatCaHoaDon.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnHienThiTatCaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiTatCaHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDSSP1Layout = new javax.swing.GroupLayout(jpDSSP1);
        jpDSSP1.setLayout(jpDSSP1Layout);
        jpDSSP1Layout.setHorizontalGroup(
            jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSP1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53)
                    .addComponent(btnLamMoiQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaHoaDonTimKiem)
                    .addComponent(btnHienThiTatCaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDSSP1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnTimQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpDSSP1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
        );
        jpDSSP1Layout.setVerticalGroup(
            jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSP1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel53)
                        .addComponent(txtMaHoaDonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jpDSSP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHienThiTatCaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiQLHD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpDSSP3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MaHD", "TenKH", "TenNV", "NgayLap"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblHD);

        javax.swing.GroupLayout jpDSSP3Layout = new javax.swing.GroupLayout(jpDSSP3);
        jpDSSP3.setLayout(jpDSSP3Layout);
        jpDSSP3Layout.setHorizontalGroup(
            jpDSSP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDSSP3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jpDSSP3Layout.setVerticalGroup(
            jpDSSP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSP3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );

        jpDSSP4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Thương hiệu", "Số lượng", "Đơn giá", "Màu sắc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblCTHD);

        jLabel54.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel54.setText("Mã hóa đơn");

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel55.setText("Ngày lập");

        txtNgayLapHoaDon.setEditable(false);
        txtNgayLapHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLapHoaDonActionPerformed(evt);
            }
        });

        jLabel56.setText("Tổng tiền");

        txtTongTienHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDSSP4Layout = new javax.swing.GroupLayout(jpDSSP4);
        jpDSSP4.setLayout(jpDSSP4Layout);
        jpDSSP4Layout.setHorizontalGroup(
            jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDSSP4Layout.createSequentialGroup()
                .addGroup(jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDSSP4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDSSP4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel56)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongTienHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDSSP4Layout.createSequentialGroup()
                                .addComponent(jLabel54)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayLapHoaDon))))
                    .addGroup(jpDSSP4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpDSSP4Layout.setVerticalGroup(
            jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDSSP4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtNgayLapHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jpDSSP4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(txtTongTienHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpDSSP1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpDSSP3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jpDSSP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHoaDonLayout.createSequentialGroup()
                        .addComponent(jpDSSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpDSSP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jpDSSP4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(64, 64, 64))
        );

        pnlRightContent.add(pnlHoaDon);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSanPham);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Tìm kiếm mã sản phẩm");

        txtTimKiemSP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btgSapXepSP.add(rdoTen);
        rdoTen.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoTen.setText("Theo tên A - Z");
        rdoTen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTenMouseClicked(evt);
            }
        });

        btgSapXepSP.add(rdoGiaGiamDan);
        rdoGiaGiamDan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoGiaGiamDan.setText("Theo giá giảm dần");
        rdoGiaGiamDan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoGiaGiamDanMouseClicked(evt);
            }
        });

        btnTimQLSP.setText("Tìm");
        btnTimQLSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnTimQLSP.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnTimQLSP.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnTimQLSP.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnTimQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimQLSPActionPerformed(evt);
            }
        });

        btgSapXepSP.add(rdoHienThiTatCa);
        rdoHienThiTatCa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoHienThiTatCa.setSelected(true);
        rdoHienThiTatCa.setText("Hiển thị tất cả sản phẩm");
        rdoHienThiTatCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoHienThiTatCaMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Sắp xếp");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(rdoTen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoGiaGiamDan)
                        .addGap(18, 18, 18)
                        .addComponent(rdoHienThiTatCa))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(btnTimQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTen)
                    .addComponent(rdoGiaGiamDan)
                    .addComponent(rdoHienThiTatCa))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết"));

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Mã sản phẩm");

        txtMaSP.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaSP.setEnabled(false);

        txtTenSp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Tên sản phẩm");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Thương hiệu");

        cboThuongHieu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDonGia.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Đơn giá");

        btnSuaQLSP.setText("Sửa");
        btnSuaQLSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaQLSP.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnSuaQLSP.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnSuaQLSP.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnSuaQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaQLSPActionPerformed(evt);
            }
        });

        btnLamMoiQLSP.setText("Làm mới");
        btnLamMoiQLSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoiQLSP.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLSP.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnLamMoiQLSP.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiQLSPActionPerformed(evt);
            }
        });

        btnThemQLSP.setText("Thêm");
        btnThemQLSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemQLSP.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnThemQLSP.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnThemQLSP.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnThemQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemQLSPActionPerformed(evt);
            }
        });

        btnXoaQLSP.setText("Xóa");
        btnXoaQLSP.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaQLSP.setkEndColor(new java.awt.Color(255, 51, 51));
        btnXoaQLSP.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXoaQLSP.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXoaQLSP.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXoaQLSP.setkStartColor(new java.awt.Color(255, 0, 51));
        btnXoaQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaQLSPActionPerformed(evt);
            }
        });

        txtMauSac.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel61.setText("Màu sắc");

        jLabel62.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel62.setText("Mô tả");

        txtMoTaSP.setColumns(20);
        txtMoTaSP.setRows(5);
        jScrollPane7.setViewportView(txtMoTaSP);

        lblAnhSp.setText("Nhấn đúp để thêm ảnh");
        lblAnhSp.setToolTipText("");
        lblAnhSp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblAnhSp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAnhSp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhSpMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblAnhSp, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(btnLamMoiQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnThemQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSuaQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnXoaQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTenSp)
                    .addComponent(cboThuongHieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDonGia)
                    .addComponent(txtMauSac)
                    .addComponent(txtMaSP))
                .addGap(63, 63, 63))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAnhSp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoiQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaQLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        btnQuanLyThuongHieu.setText("Quản lý thương hiệu");
        btnQuanLyThuongHieu.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnQuanLyThuongHieu.setkBorderRadius(40);
        btnQuanLyThuongHieu.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnQuanLyThuongHieu.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnQuanLyThuongHieu.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnQuanLyThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyThuongHieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSanPhamLayout = new javax.swing.GroupLayout(pnlSanPham);
        pnlSanPham.setLayout(pnlSanPhamLayout);
        pnlSanPhamLayout.setHorizontalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSanPhamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQuanLyThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        pnlSanPhamLayout.setVerticalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuanLyThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlSanPham);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        cboThangTKBH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboThangTKBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cboThangTKBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangTKBHActionPerformed(evt);
            }
        });

        cboNamTKBH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboNamTKBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022" }));
        cboNamTKBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamTKBHActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel57.setText("Tháng");

        jLabel58.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel58.setText("Năm");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Ngày");

        cboNgayTKBH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboNgayTKBH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        cboNgayTKBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNgayTKBHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNgayTKBH, 0, 220, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addGap(18, 18, 18)
                .addComponent(cboThangTKBH, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNamTKBH, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(cboThangTKBH, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(cboNamTKBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(cboNgayTKBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85))
        );

        tblTKSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng sản phẩm bán được"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(tblTKSP);

        btnDoanhThuQLBH.setText("Doanh thu");
        btnDoanhThuQLBH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnDoanhThuQLBH.setkBorderRadius(40);
        btnDoanhThuQLBH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnDoanhThuQLBH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnDoanhThuQLBH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnDoanhThuQLBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoanhThuQLBHActionPerformed(evt);
            }
        });

        TieuDe1.setBackground(new java.awt.Color(0, 153, 153));
        TieuDe1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TieuDe1.setForeground(new java.awt.Color(0, 153, 153));
        TieuDe1.setText("BÁN HÀNG");

        btnNhanVienQLBH.setText("Nhân viên xuất sắc");
        btnNhanVienQLBH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNhanVienQLBH.setkBorderRadius(40);
        btnNhanVienQLBH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnNhanVienQLBH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnNhanVienQLBH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnNhanVienQLBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienQLBHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeBanHangLayout = new javax.swing.GroupLayout(pnlThongKeBanHang);
        pnlThongKeBanHang.setLayout(pnlThongKeBanHangLayout);
        pnlThongKeBanHangLayout.setHorizontalGroup(
            pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeBanHangLayout.createSequentialGroup()
                .addGroup(pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeBanHangLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThongKeBanHangLayout.createSequentialGroup()
                                .addComponent(btnDoanhThuQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(585, 585, 585)
                                .addComponent(btnNhanVienQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane12)
                                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnlThongKeBanHangLayout.createSequentialGroup()
                        .addGap(426, 426, 426)
                        .addComponent(TieuDe1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlThongKeBanHangLayout.setVerticalGroup(
            pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeBanHangLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(TieuDe1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlThongKeBanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoanhThuQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhanVienQLBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlThongKeBanHang);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin khách hàng", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText("Mã khách hàng");

        txtMaKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaKH.setEnabled(false);

        txtTenKH3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel34.setText("Tên khách hàng");

        txtSDT.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Số điện thoại");

        txtDiaChi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Địa chỉ");

        jLabel37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel37.setText("Giới tính");

        buttonGroup2.add(rdoNamKh);
        rdoNamKh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNamKh.setSelected(true);
        rdoNamKh.setText("Nam");
        rdoNamKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamKhActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoNuKh);
        rdoNuKh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNuKh.setText("Nữ");

        btnSuaQLKH.setText("Sửa");
        btnSuaQLKH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaQLKH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnSuaQLKH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnSuaQLKH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnSuaQLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaQLKHActionPerformed(evt);
            }
        });

        btnLamMoiQLKH.setText("Làm mới");
        btnLamMoiQLKH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoiQLKH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLKH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnLamMoiQLKH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiQLKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addComponent(txtTenKH3)
                    .addComponent(txtMaKH)
                    .addComponent(txtDiaChi))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(62, 62, 62)
                        .addComponent(rdoNamKh))
                    .addComponent(btnLamMoiQLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(rdoNuKh)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSuaQLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel33)
                .addGap(25, 25, 25)
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel34)
                .addGap(25, 25, 25)
                .addComponent(txtTenKH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel35)
                .addGap(25, 25, 25)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel36)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(rdoNamKh)
                            .addComponent(rdoNuKh))
                        .addGap(140, 140, 140))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaQLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoiQLKH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm theo mã khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        txtTimTenKH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTimTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimTenKHKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtTimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách khách hàng", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại ", "Địa chỉ ", "Giới tính "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblKhachHang);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
        );

        javax.swing.GroupLayout pnlKhachHangLayout = new javax.swing.GroupLayout(pnlKhachHang);
        pnlKhachHang.setLayout(pnlKhachHangLayout);
        pnlKhachHangLayout.setHorizontalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlKhachHangLayout.setVerticalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlKhachHangLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlKhachHang);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông tin nhân viên", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel40.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel40.setText("Mã nhân viên");

        txtMaNV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaNV.setEnabled(false);

        txtTenNV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel41.setText("Tên nhân viên");

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel42.setText("Email");

        txtSdtNv.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel43.setText("Số điện thoại");

        txtDiaChiNv.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel44.setText("Địa chỉ");

        jLabel45.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel45.setText("Giới tính");

        buttonGroup1.add(rdoNamNv);
        rdoNamNv.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNamNv.setText("Nam");

        buttonGroup1.add(rdoNuNv);
        rdoNuNv.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoNuNv.setText("Nữ");

        jLabel46.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel46.setText("Mật khẩu đăng nhập");

        txtPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setText("Chức vụ");

        cboChucVu.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel49.setText("Ngày sinh");

        btnThemQLNV.setText("Thêm");
        btnThemQLNV.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemQLNV.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnThemQLNV.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnThemQLNV.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnThemQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemQLNVActionPerformed(evt);
            }
        });

        btnSuaQLNV.setText("Sửa");
        btnSuaQLNV.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaQLNV.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnSuaQLNV.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnSuaQLNV.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnSuaQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaQLNVActionPerformed(evt);
            }
        });

        btnLamMoiQLNV.setText("Làm mới");
        btnLamMoiQLNV.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoiQLNV.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLNV.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnLamMoiQLNV.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnLamMoiQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiQLNVActionPerformed(evt);
            }
        });

        btnXoaQLNV.setText("Xóa");
        btnXoaQLNV.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaQLNV.setkEndColor(new java.awt.Color(255, 51, 51));
        btnXoaQLNV.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXoaQLNV.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXoaQLNV.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXoaQLNV.setkStartColor(new java.awt.Color(255, 0, 51));
        btnXoaQLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaQLNVActionPerformed(evt);
            }
        });

        txtNgaySinh.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel42)
                            .addGap(62, 62, 62)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel41)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTenNV))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel40)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel45)
                        .addComponent(jLabel46)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel47)
                            .addGap(43, 43, 43)
                            .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addComponent(rdoNamNv)
                                    .addGap(71, 71, 71)
                                    .addComponent(rdoNuNv))
                                .addGroup(jPanel13Layout.createSequentialGroup()
                                    .addComponent(btnLamMoiQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(btnThemQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(btnSuaQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(20, 20, 20)
                            .addComponent(btnXoaQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel44))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDiaChiNv, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSdtNv, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel43)
                    .addComponent(txtSdtNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtDiaChiNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel45)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNamNv)
                    .addComponent(rdoNuNv))
                .addGap(42, 42, 42)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoiQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaQLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        txtMaNVTimKiem.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaNVTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaNVTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtMaNVTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaNVTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách nhân viên", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Chức vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9))
        );

        javax.swing.GroupLayout pnlNhanVienLayout = new javax.swing.GroupLayout(pnlNhanVien);
        pnlNhanVien.setLayout(pnlNhanVienLayout);
        pnlNhanVienLayout.setHorizontalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        pnlNhanVienLayout.setVerticalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlNhanVienLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlNhanVien);

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jComboBox7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        jComboBox8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022" }));

        jLabel65.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel65.setText("Tháng");

        jLabel66.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel66.setText("Năm");

        jLabel63.setText("Ngày");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addGap(29, 29, 29)
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46)
                .addComponent(jLabel65)
                .addGap(18, 18, 18)
                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel66)
                .addGap(18, 18, 18)
                .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel65))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel63)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pnl21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Doanh thu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        javax.swing.GroupLayout pnl21Layout = new javax.swing.GroupLayout(pnl21);
        pnl21.setLayout(pnl21Layout);
        pnl21Layout.setHorizontalGroup(
            pnl21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 890, Short.MAX_VALUE)
        );
        pnl21Layout.setVerticalGroup(
            pnl21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        btnBanHangTKDT.setText("Bán hàng");
        btnBanHangTKDT.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnBanHangTKDT.setkBorderRadius(40);
        btnBanHangTKDT.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnBanHangTKDT.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnBanHangTKDT.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnBanHangTKDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangTKDTActionPerformed(evt);
            }
        });

        btnNhanVienTKDT.setText("Nhân viên xuất sắc");
        btnNhanVienTKDT.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNhanVienTKDT.setkBorderRadius(40);
        btnNhanVienTKDT.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnNhanVienTKDT.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnNhanVienTKDT.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnNhanVienTKDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienTKDTActionPerformed(evt);
            }
        });

        TieuDe2.setBackground(new java.awt.Color(0, 153, 153));
        TieuDe2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TieuDe2.setForeground(new java.awt.Color(0, 153, 153));
        TieuDe2.setText("DOANH THU");

        javax.swing.GroupLayout pnlThongKeDoanhThuLayout = new javax.swing.GroupLayout(pnlThongKeDoanhThu);
        pnlThongKeDoanhThu.setLayout(pnlThongKeDoanhThuLayout);
        pnlThongKeDoanhThuLayout.setHorizontalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                                .addComponent(btnBanHangTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(493, 493, 493)
                                .addComponent(btnNhanVienTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pnl21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(TieuDe2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongKeDoanhThuLayout.setVerticalGroup(
            pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeDoanhThuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TieuDe2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(pnl21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongKeDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBanHangTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhanVienTKDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pnlRightContent.add(pnlThongKeDoanhThu);

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        cboThangTKNV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboThangTKNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cboThangTKNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangTKNVActionPerformed(evt);
            }
        });

        cboNamTKNV.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboNamTKNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022" }));
        cboNamTKNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamTKNVActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel70.setText("Tháng");

        jLabel71.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel71.setText("Năm");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel70)
                .addGap(42, 42, 42)
                .addComponent(cboThangTKNV, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addGap(18, 18, 18)
                .addComponent(cboNamTKNV, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboThangTKNV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71)
                    .addComponent(cboNamTKNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tblTKNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Số lượng sản phẩm bán được"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(tblTKNV);

        btnDoanhThuQLNVXS.setText("Doanh thu");
        btnDoanhThuQLNVXS.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnDoanhThuQLNVXS.setkBorderRadius(40);
        btnDoanhThuQLNVXS.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnDoanhThuQLNVXS.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnDoanhThuQLNVXS.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnDoanhThuQLNVXS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoanhThuQLNVXSActionPerformed(evt);
            }
        });

        btnBanHangQLNVSX.setText("Bán hàng");
        btnBanHangQLNVSX.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnBanHangQLNVSX.setkBorderRadius(40);
        btnBanHangQLNVSX.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnBanHangQLNVSX.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnBanHangQLNVSX.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnBanHangQLNVSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangQLNVSXActionPerformed(evt);
            }
        });

        TieuDe3.setBackground(new java.awt.Color(0, 153, 153));
        TieuDe3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TieuDe3.setForeground(new java.awt.Color(0, 153, 153));
        TieuDe3.setText("NHÂN VIÊN XUẤT SẮC");

        javax.swing.GroupLayout pnlThongKeNhanVienLayout = new javax.swing.GroupLayout(pnlThongKeNhanVien);
        pnlThongKeNhanVien.setLayout(pnlThongKeNhanVienLayout);
        pnlThongKeNhanVienLayout.setHorizontalGroup(
            pnlThongKeNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeNhanVienLayout.createSequentialGroup()
                .addGroup(pnlThongKeNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongKeNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThongKeNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane14)
                            .addGroup(pnlThongKeNhanVienLayout.createSequentialGroup()
                                .addComponent(btnDoanhThuQLNVXS, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBanHangQLNVSX, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlThongKeNhanVienLayout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(TieuDe3)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnlThongKeNhanVienLayout.setVerticalGroup(
            pnlThongKeNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeNhanVienLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(TieuDe3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlThongKeNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoanhThuQLNVXS, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBanHangQLNVSX, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlThongKeNhanVien);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblThuongHieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã thương hiệu", "Tên thương hiệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuongHieuMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblThuongHieu);

        jPanel8.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 106, 460, 380));

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Tìm kiếm ");
        jPanel8.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 32, -1, -1));

        txtTimKiemTH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTimKiemTH.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTimKiemTH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTHKeyReleased(evt);
            }
        });
        jPanel8.add(txtTimKiemTH, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 60, 450, -1));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chi tiết"));

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Mã thương hiệu");

        txtMaTH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMaTH.setEnabled(false);

        txtTenTH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Tên thương hiệu");

        btnThemTH.setText("Thêm");
        btnThemTH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnThemTH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnThemTH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnThemTH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnThemTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTHActionPerformed(evt);
            }
        });

        btnLamMoiTH.setText("Làm mới");
        btnLamMoiTH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLamMoiTH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnLamMoiTH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnLamMoiTH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnLamMoiTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTHActionPerformed(evt);
            }
        });

        btnSuaTH.setText("Sửa");
        btnSuaTH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSuaTH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnSuaTH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnSuaTH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnSuaTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaTHActionPerformed(evt);
            }
        });

        btnXoaTH.setText("Xóa");
        btnXoaTH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnXoaTH.setkEndColor(new java.awt.Color(255, 51, 51));
        btnXoaTH.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnXoaTH.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnXoaTH.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnXoaTH.setkStartColor(new java.awt.Color(255, 0, 51));
        btnXoaTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnLamMoiTH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemTH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel30)
                    .addComponent(txtMaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(txtTenTH, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemTH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiTH, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 153, 153));
        jLabel32.setText("THƯƠNG HIỆU");

        btnQuayLai.setText("Quay lại");
        btnQuayLai.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnQuayLai.setkBorderRadius(40);
        btnQuayLai.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        btnQuayLai.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btnQuayLai.setkHoverStartColor(new java.awt.Color(204, 204, 204));
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThuongHieuLayout = new javax.swing.GroupLayout(pnlThuongHieu);
        pnlThuongHieu.setLayout(pnlThuongHieuLayout);
        pnlThuongHieuLayout.setHorizontalGroup(
            pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                .addGroup(pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                        .addGap(428, 428, 428)
                        .addComponent(jLabel32)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThuongHieuLayout.setVerticalGroup(
            pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnQuayLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pnlRightContent.add(pnlThuongHieu);

        getContentPane().add(pnlRightContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 1010, 690));

        jmeDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/7853741_logout_kashifarif_exit_out_close_icon.png"))); // NOI18N
        jmeDangXuat.setText("Đăng xuất");
        jmeDangXuat.addMenuDragMouseListener(new javax.swing.event.MenuDragMouseListener() {
            public void menuDragMouseDragged(javax.swing.event.MenuDragMouseEvent evt) {
                jmeDangXuatMenuDragMouseDragged(evt);
            }
            public void menuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {
            }
            public void menuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {
            }
            public void menuDragMouseReleased(javax.swing.event.MenuDragMouseEvent evt) {
            }
        });
        jmeDangXuat.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jmeDangXuatMenuSelected(evt);
            }
        });
        jmeDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmeDangXuatMouseClicked(evt);
            }
        });
        jmeDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmeDangXuatActionPerformed(evt);
            }
        });
        jMenuBar1.add(jmeDangXuat);

        jmeHuongDAn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/book1_20px.png"))); // NOI18N
        jmeHuongDAn.setText("Hướng dẫn");
        jMenuBar1.add(jmeHuongDAn);

        jmeGioiTthieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/bgear/icons/4172174_documentation_info_information_instruction_intelligence_icon.png"))); // NOI18N
        jmeGioiTthieu.setText("Giới thiệu");
        jMenuBar1.add(jmeGioiTthieu);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangChuMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(true);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }//GEN-LAST:event_TrangChuMouseClicked

    private void TrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangChuMouseEntered

    }//GEN-LAST:event_TrangChuMouseEntered

    private void TrangBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangBanHangMouseClicked
        pnlBanHang.setVisible(true);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
//        BanHang.setForeground(Color.red);
    }//GEN-LAST:event_TrangBanHangMouseClicked

    private void TrangBanHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangBanHangMouseEntered

    }//GEN-LAST:event_TrangBanHangMouseEntered

    private void TrangHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangHoaDonMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(true);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }//GEN-LAST:event_TrangHoaDonMouseClicked

    private void TrangSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangSanPhamMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(true);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }//GEN-LAST:event_TrangSanPhamMouseClicked

    private void TrangNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangNhanVienMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);

        pnlNhanVien.setVisible(true);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }//GEN-LAST:event_TrangNhanVienMouseClicked

    private void TongHopThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TongHopThongKeMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(true);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
//        XuatHoaDon.setVisible(false);
    }//GEN-LAST:event_TongHopThongKeMouseClicked

    private void TongHopThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TongHopThongKeMouseEntered

    }//GEN-LAST:event_TongHopThongKeMouseEntered

    private void TrangKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TrangKhachHangMouseClicked
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(true);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);

    }//GEN-LAST:event_TrangKhachHangMouseClicked

    private void jLabel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseEntered

    }//GEN-LAST:event_jLabel20MouseEntered

    private void jmeDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmeDangXuatMouseClicked
        int DangXuat = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn đăng xuất ?", "Đăng xuất", JOptionPane.YES_NO_OPTION);
        if (DangXuat == JOptionPane.YES_OPTION) {
            new DangNhap().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jmeDangXuatMouseClicked

    private void jmeDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmeDangXuatActionPerformed

    }//GEN-LAST:event_jmeDangXuatActionPerformed

    private void txtDuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuActionPerformed

    private void txtSDTKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTKHActionPerformed

    private void btnTimSDTQLBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimSDTQLBHActionPerformed
        try {
            if (btnTimSDTQLBH.getText().equals("Thêm")) {
                if (txtTenKH.getText().equals("") || txtDiaChiKH.getText().equals("") || txtSDTKH.getText().equals("")) {
                    MsgBox.alert(this, "Vui lòng không bỏ trống thông tin khi thêm khách hàng mới");
                } else {
                    KhachHang kh = getFormKH();
                    khdao.insert(kh);
                    btnXuatHoaDon.setEnabled(true);
                    txtSDTKH.setEditable(true);
                    MsgBox.alert(this, "Thêm khách hàng thành công");
                    fillToTable();
                    btnTimSDTQLBH.setText("Tìm");
                    String sdt = txtSDTKH.getText();
                    KhachHang khcheck = khdao.selectSDT(sdt);
                    maKH = khcheck.getMaKh();
                    txtTenKH.setText(khcheck.getTenKh());
                    txtDiaChiKH.setText(khcheck.getDiaChi());
                    boolean gt = khcheck.isGioiTinh();
                    if (gt == false) {
                        rdoNamKH.setSelected(true);
                    } else {
                        rdoNuKH.setSelected(true);
                    }
                    txtTenKH.setEditable(false);
                    txtDiaChiKH.setEditable(false);
                }
            } else {
                if (txtSDTKH.getText().length() != 10) {
                    MsgBox.alert(this, "Vui lòng nhập số điện thoại đúng 10 số");
                } else {
                    int sodt = Integer.parseInt(txtSDTKH.getText());
                    boolean check = true;
                    List<KhachHang> list = khdao.selectAll();
                    for (KhachHang kh : list) {
                        String sdt = kh.getSDT();
                        if (txtSDTKH.getText().equals(sdt)) {
                            check = false;
                            break;
                        }
                    }
                    if (check == false) {
                        String sdt = txtSDTKH.getText();
                        KhachHang khcheck = khdao.selectSDT(sdt);
                        maKH = khcheck.getMaKh();
                        txtTenKH.setText(khcheck.getTenKh());
                        txtDiaChiKH.setText(khcheck.getDiaChi());
                        boolean gt = khcheck.isGioiTinh();
                        if (gt == false) {
                            rdoNamKH.setSelected(true);
                        } else {
                            rdoNuKH.setSelected(true);
                        }
                        txtTenKH.setEditable(false);
                        txtDiaChiKH.setEditable(false);
                    } else {
                        int add = JOptionPane.showConfirmDialog(this, "Không tìm thấy, bạn muốn thêm khách hàng mới không ?", "Thêm khách hàng mới ?", JOptionPane.YES_NO_OPTION);
                        if (add == JOptionPane.YES_OPTION) {
                            txtTenKH.setEditable(true);
                            txtDiaChiKH.setEditable(true);
                            btnTimSDTQLBH.setText("Thêm");
                            txtSDTKH.setEditable(false);
                            btnXuatHoaDon.setEnabled(false);
                        }
                    }
                }
            }
        } catch (java.lang.NumberFormatException e) {
            MsgBox.alert(this, "Không được nhập chữ vào số điện thoại");
        }
    }//GEN-LAST:event_btnTimSDTQLBHActionPerformed

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed
        HoaDon hd = getFormBH();
        try {
            if (txtDaThu.getText().equals("")) {
                MsgBox.alert(this, "Bạn chưa nhập số tiền đã thu của khách");
            } else if (txtDu.getText().equals("")) {
                MsgBox.alert(this, "Chưa có số tiền dư để trả cho khách");
            } else if (txtTenKH.getText().equals("") || txtDiaChiKH.getText().equals("")) {
                MsgBox.alert(this, "Bạn chưa nhập đầy đủ thông tin của khách hàng");
            } else {
                hddao.insert(hd);
                int rowCount = tblSPHD.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    int maHDCT = 0;
                    List<HoaDonChiTiet> list = hdctdao.selectAll();
                    for (HoaDonChiTiet hdct : list) {
                        maHDCT = Integer.parseInt(hdct.getMaHDCT().substring(3, 5));
                    }
                    maHDCT = maHDCT + 1;
                    HoaDonChiTiet hdct1 = new HoaDonChiTiet();
                    hdct1.setMaHDCT("ID0" + maHDCT);
                    hdct1.setMaHd(txtMaHD.getText());
                    hdct1.setMaSp(String.valueOf(tblSPHD.getValueAt(i, 0)));
                    hdct1.setSoLuong(Integer.parseInt(String.valueOf(tblSPHD.getValueAt(i, 3))));
                    hdctdao.insert(hdct1);
                    fillToTable();
                }
                MsgBox.alert(this, "Xuất hóa đơn thành công");
                txtMaHD.setText("");
                txtNgayLapHoaDon.setText("");
                txtSoLuongHoaDon.setText("");
                txtDaThu.setText("");
                txtThanhToan.setText("");
                txtDu.setText("");
                txtTenKH.setText("");
                txtDiaChiKH.setText("");
                txtSDTKH.setText("");
                tblSPHD.removeAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatHoaDonActionPerformed

    private void btnXoaQLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaQLHDActionPerformed
        xoaHD();
    }//GEN-LAST:event_btnXoaQLHDActionPerformed

    private void btnLamMoiQLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiQLHDActionPerformed
        txtMaHoaDonTimKiem.setText("");
    }//GEN-LAST:event_btnLamMoiQLHDActionPerformed

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void txtNgayLapHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLapHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapHoaDonActionPerformed

    private void btnTimQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimQLSPActionPerformed
        timSPTheoMa();
    }//GEN-LAST:event_btnTimQLSPActionPerformed

    private void btnSuaQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaQLSPActionPerformed
        suaSP();
    }//GEN-LAST:event_btnSuaQLSPActionPerformed

    private void btnLamMoiQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiQLSPActionPerformed
        lamMoiQLSP();
    }//GEN-LAST:event_btnLamMoiQLSPActionPerformed

    private void btnThemQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemQLSPActionPerformed
        if (btnThemQLSP.getText().equals("Thêm")) {
            txtTenSp.setText("");
            txtDonGia.setText("");
            txtMauSac.setText("");
            txtMoTaSP.setText("");
            lblHinhSP.setText("Nháy đúp để thêm hình ảnh");
            btnThemQLSP.setText("Xác nhận");
            btnXoaQLSP.setText("Hủy");
            taoMaSP();
        } else if (btnThemQLSP.getText().equals("Xác nhận")) {
            btnThemQLSP.setText("Thêm");
            btnXoaQLSP.setText("Xóa");
            themSP();
        }


    }//GEN-LAST:event_btnThemQLSPActionPerformed

    private void btnXoaQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaQLSPActionPerformed
        if (btnXoaQLSP.getText().equals("Xóa")) {
            xoaSP();
        } else if (btnXoaQLSP.getText().equals("Hủy")) {
            btnThemQLSP.setText("Thêm");
            btnXoaQLSP.setText("Xóa");
            lamMoiQLSP();
        }

    }//GEN-LAST:event_btnXoaQLSPActionPerformed

    private void btnQuanLyThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyThuongHieuActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(true);
    }//GEN-LAST:event_btnQuanLyThuongHieuActionPerformed

    private void btnDoanhThuQLBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoanhThuQLBHActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(true);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
        setThongKeChart();
    }//GEN-LAST:event_btnDoanhThuQLBHActionPerformed

    private void btnNhanVienQLBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienQLBHActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(true);
        pnlThuongHieu.setVisible(false);
    }//GEN-LAST:event_btnNhanVienQLBHActionPerformed

    private void rdoNamKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamKhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamKhActionPerformed

    private void btnSuaQLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaQLKHActionPerformed
        if (checkFormKH()) {
            KhachHang kh = getFormKh();
            boolean check = false;
            List<KhachHang> list = khdao.selectAll();
            for (KhachHang mkh : list) {
                if (txtMaKH.getText().equals(mkh.getMaKh())) {
                    check = true;
                    break;
                }
            }
            if (check == true) {
                try {
                    khdao.update(kh);
                    this.fillTableKhachHang();
                    this.fillTableHoaDon();
                    MsgBox.alert(this, "Cập nhật thành công");
                    
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại");
                    e.printStackTrace();
                }
            } else {
                MsgBox.alert(this, "Không tìm thấy nhân viên muốn cập nhật, vui lòng kiểm tra lại mã nhân viên");
            }
        }
    }//GEN-LAST:event_btnSuaQLKHActionPerformed

    private void btnLamMoiQLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiQLKHActionPerformed
        lamMoiKh();
    }//GEN-LAST:event_btnLamMoiQLKHActionPerformed

    private void txtTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNVActionPerformed

    private void btnThemQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemQLNVActionPerformed
        if (btnThemQLNV.getText().equals("Thêm")) {
            btnThemQLNV.setText("Xác nhận");
            btnXoaQLNV.setText("Hủy");
            txtTenNV.setText("");
            cboChucVu.setSelectedItem(0);
            txtEmail.setText("");
            txtSdtNv.setText("");
            txtNgaySinh.cleanup();
            txtPassword.setText("");
            lamMoiNv();
            taoMaNv();
        } else if (btnThemQLNV.getText().equals("Xác nhận")) {
            if (checkLoiNhanVien()) {
                themNv();
                btnThemQLNV.setText("Thêm");
                btnXoaQLNV.setText("Xóa");
            }
        }
    }//GEN-LAST:event_btnThemQLNVActionPerformed

    private void btnSuaQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaQLNVActionPerformed
        int sua = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin nhân viên này ?", "Sửa thông tin nhân viên", JOptionPane.YES_OPTION);

        if (sua == JOptionPane.YES_OPTION) {
            if (txtMaNV.getText().equals("") || txtTenNV.getText().equals("") || txtEmail.getText().equals("") || txtSdtNv.getText().equals("") || txtNgaySinh.equals("") || txtPassword.getText().equals("")) {
                MsgBox.alert(this, "Không được bỏ trống thông tin khi thêm nhân viên");
            } else if (txtSdtNv.getText().length() != 10) {
                MsgBox.alert(this, "Số điện thoại bắt buộc phải có 10 số");
            } else if (!"0".equals(txtSdtNv.getText().substring(0, 1)) || "00".equals(txtSdtNv.getText().substring(0, 2))) {
                MsgBox.alert(this, "Số điện thoại không hợp lệ");
            } else {
                List<NhanVien> list = nvdao.selectAll();
                String maNV = null;
                String sdt = null;
                for (NhanVien nv : list) {
                    maNV = nv.getMaNv();
                    sdt = nv.getSDT();
                }
                if (txtEmail.getText().equals(maNV)) {
                    MsgBox.alert(this, "Đã có nhân viên sử dụng email " + txtEmail.getText());
                } else if (txtSdtNv.getText().equals(sdt)) {
                    MsgBox.alert(this, "Đã có nhân viên sử dụng số điện thoại " + txtSdtNv.getText());
                } else {
                    suaNv();
                }
            }
        }
    }//GEN-LAST:event_btnSuaQLNVActionPerformed

    private void btnLamMoiQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiQLNVActionPerformed
        lamMoiNv();
    }//GEN-LAST:event_btnLamMoiQLNVActionPerformed

    private void btnXoaQLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaQLNVActionPerformed
        if (btnXoaQLNV.getText().equals("Xóa")) {
            xoaNv();
        } else if (btnXoaQLNV.getText().equals("Hủy")) {
            btnThemQLNV.setText("Thêm");
            btnXoaQLNV.setText("Xóa");
            lamMoiNv();
        }
    }//GEN-LAST:event_btnXoaQLNVActionPerformed

    private void btnBanHangTKDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangTKDTActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(true);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
    }//GEN-LAST:event_btnBanHangTKDTActionPerformed

    private void btnNhanVienTKDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienTKDTActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(true);
        pnlThuongHieu.setVisible(false);
    }//GEN-LAST:event_btnNhanVienTKDTActionPerformed

    private void btnDoanhThuQLNVXSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoanhThuQLNVXSActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(true);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
        setThongKeChart();
    }//GEN-LAST:event_btnDoanhThuQLNVXSActionPerformed

    private void btnBanHangQLNVSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangQLNVSXActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(false);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(true);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
    }//GEN-LAST:event_btnBanHangQLNVSXActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        try {
            this.row = tblSanPham.getSelectedRow();
            if (this.row >= 0) {
                String maSp = (String) tblSanPham.getValueAt(this.row, 0);
                SanPham sp = spdao.selectById(maSp);
                txtMaSP.setText(sp.getMaSp());
                txtTenSp.setText(sp.getTenSp());
                cboThuongHieu.setSelectedItem(sp.getThuongHieu());
                txtDonGia.setText(String.valueOf(sp.getDonGia()));
                txtMauSac.setText(sp.getMauSac());
                txtMoTaSP.setText(sp.getMoTa());
                Image img = XImage.createImageFromByteArray(sp.getHinh(), "jpg");
                lblAnhSp.setIcon(new ImageIcon(img));
                sp_anh = sp.getHinh();
                lblAnhSp.setText("");
                btnThemQLSP.setText("Thêm");
            }
            btnSuaQLSP.setEnabled(true);
            btnXoaQLSP.setEnabled(true);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        this.row = tblNhanVien.getSelectedRow();
        if (this.row >= 0) {
            String maNv = (String) tblNhanVien.getValueAt(this.row, 0);
            NhanVien nv = nvdao.selectById(maNv);
            txtMaNV.setText(nv.getMaNv());
            txtTenNV.setText(nv.getHoTen());
            cboChucVu.setSelectedItem(nv.getTenCv());
            txtEmail.setText(nv.getEmail());
            txtSdtNv.setText(nv.getSDT());
            txtDiaChiNv.setText(nv.getDiaChi());
            txtNgaySinh.setDate(nv.getNgaySinh());
            txtPassword.setText(nv.getMatKhau());
            btnThemQLNV.setText("Thêm");
            btnXoaQLNV.setText("Xóa");
            if (nv.isGioiTinh() == false) {
                rdoNamNv.setSelected(true);
            } else if (nv.isGioiTinh() == true) {
                rdoNuNv.setSelected(true);
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void rdoHienThiTatCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoHienThiTatCaMouseClicked
        fillTableSanPham();
    }//GEN-LAST:event_rdoHienThiTatCaMouseClicked

    private void rdoGiaGiamDanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoGiaGiamDanMouseClicked
        sapXepTheoGiaGiam();
    }//GEN-LAST:event_rdoGiaGiamDanMouseClicked

    private void rdoTenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTenMouseClicked
        sapXepTheoTen();
    }//GEN-LAST:event_rdoTenMouseClicked

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        int viTri = tblHD.getSelectedRow();
        txtMaHoaDon.setText(tblHD.getValueAt(viTri, 0).toString());
        txtNgayLapHoaDon.setText(tblHD.getValueAt(viTri, 3).toString());
        fillTableHoaDonChiTiet();
        int tongTien = 0;
        for (int i = 0; i <= tblCTHD.getRowCount() - 1; i++) {
            int temp = Integer.parseInt(tblCTHD.getValueAt(i, 3).toString());
            tongTien += temp;
        }
        txtTongTienHoaDon.setText(String.valueOf(tongTien));
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnTimQLHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimQLHDActionPerformed
        timHDTheoMa();
    }//GEN-LAST:event_btnTimQLHDActionPerformed

    private void btnHienThiTatCaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiTatCaHoaDonActionPerformed
        fillTableHoaDon();
        txtMaHoaDonTimKiem.setText("");
    }//GEN-LAST:event_btnHienThiTatCaHoaDonActionPerformed

    private void lblAnhSpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhSpMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            JFileChooser chooser = new JFileChooser("src\\com\\bgear\\icons\\AnhSp");
            int kq = chooser.showOpenDialog(null);
            if (kq == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
//                fileName = f.getAbsolutePath();
                try {
                    ImageIcon icon = new ImageIcon(f.getPath());
                    Image img = XImage.resize(icon.getImage(), 200, 200);
                    ImageIcon resizedIcon = new ImageIcon(img);
                    lblAnhSp.setIcon(resizedIcon);
                    sp_anh = XImage.toByArray(img, "jpg");
                } catch (Exception e) {

                }
            }
        }
    }//GEN-LAST:event_lblAnhSpMouseClicked

    private void tblBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanHangMouseClicked
        btnThemSoLuong.setEnabled(true);
        this.row = tblBanHang.getSelectedRow();
        if (this.row >= 0) {
            try {
                String maSp = (String) tblBanHang.getValueAt(this.row, 0);
                SanPham sp = spdao.selectById(maSp);
                txtMaSP.setText(sp.getMaSp());
                txtTenSPBanHang.setText(sp.getTenSp());
                txtHangBanHang.setText(sp.getThuongHieu());
                txtGiaBanHang.setText(String.valueOf(sp.getDonGia()));
                txtMauBanHang.setText(sp.getMauSac());
                txtMotaBanHang.setText(sp.getMoTa());

                Image img = XImage.createImageFromByteArray(sp.getHinh(), "jpg");
                lblHinhSP.setIcon(new ImageIcon(img));
                sp_anh = sp.getHinh();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tblBanHangMouseClicked

    private void btnThemSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSoLuongActionPerformed
        try {
            TableModel model1 = tblBanHang.getModel();
            int[] indexs = tblBanHang.getSelectedRows();
            Object[] row = new Object[5];
            int soluong = 0;
            int soluongtest = Integer.parseInt(txtSoLuong.getText());
            int gia = 0;
            boolean check = true;

            String maHD = null;
            List<HoaDon> list = hddao.selectAll();
            for (HoaDon hd : list) {
                maHD = hd.getMaHd();
            }
            int maHDHC = Integer.parseInt(maHD.substring(3, 5)) + 1;
            txtMaHD.setText("HD0" + maHDHC);
            soluong = Integer.parseInt(txtSoLuong.getText());
            gia = Integer.parseInt(txtGiaBanHang.getText());
            txtNgayLap.setText(String.valueOf(dtf.format(now)));
            DefaultTableModel model2 = (DefaultTableModel) tblSPHD.getModel();
            for (int i = 0; i < indexs.length; i++) {
                row[0] = model1.getValueAt(indexs[i], 0);
                row[1] = model1.getValueAt(indexs[i], 1);
                row[2] = model1.getValueAt(indexs[i], 2);
                row[3] = Integer.parseInt(txtSoLuong.getText());
                row[4] = soluong * gia;
                for (int index = 0; index < tblSPHD.getRowCount(); index++) {
                    if (tblBanHang.getValueAt(this.row, 1).equals(tblSPHD.getValueAt(index, 0))) {
                        check = false;
                    } else {
                        check = true;
                    }
                }
                if (check == false) {
                    MsgBox.alert(this, "Bạn đã thêm sản phẩm này, nếu muốn chỉnh sửa vui lòng di chuyển sang bảng hóa đơn");
                } else {
                    if (Integer.parseInt(txtSoLuong.getText()) <= 0) {
                        MsgBox.alert(this, "Không được nhập số lượng bé hơn hoặc bằng 0");
                    } else {
                        model2.addRow(row);
                        MsgBox.alert(this, "Thêm sản phẩm thành công");
                        txtDaThu.setText("");
                        txtDu.setText("");
                    }
                }

                //Tổng tiền cần thanh toán
                double sum = 0;

                for (double a = 0; a < tblSPHD.getRowCount(); a++) {
                    sum = (sum + Double.parseDouble(tblSPHD.getValueAt((int) a, 4).toString()));
                }
                txtThanhToan.setText(Double.toString(sum));
                txtSoLuong.setText("");
            }
        } catch (java.lang.NullPointerException e) {
            MsgBox.alert(this, "Vui lòng nhập số lượng");
        } catch (java.lang.NumberFormatException e) {
            MsgBox.alert(this, "Không nhập chữ vào số lượng");
        }
    }//GEN-LAST:event_btnThemSoLuongActionPerformed

    private void txtDaThuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDaThuKeyPressed
        try {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if (Double.parseDouble(txtDaThu.getText()) < Double.parseDouble(txtThanhToan.getText())) {
                    MsgBox.alert(this, "Số tiền đã thu không được bé hơn tổng tiền cần thanh toán");
                    txtDu.setText("");
                } else {
                    double sum = 0;
                    int thu = 0;
                    int du = 0;

                    for (double a = 0; a < tblSPHD.getRowCount(); a++) {
                        sum = (sum + Double.parseDouble(tblSPHD.getValueAt((int) a, 4).toString()));
                    }
                    du = (int) (Double.parseDouble(txtDaThu.getText()) - sum);
                    txtDu.setText(Double.toString(du));
                }
            }
        } catch (java.lang.NumberFormatException e) {
            MsgBox.alert(this, "Không được nhập chữ vào số tiền");
            e.printStackTrace();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_txtDaThuKeyPressed

    private void txtSoLuongHoaDonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongHoaDonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Integer.parseInt(txtSoLuongHoaDon.getText()) <= 0) {
                MsgBox.alert(this, "Không nhập số lượng bé hơn hơn hoặc bằng 0");
            } else {
                try {
                    int row = tblSPHD.getSelectedRow();
                    tblSPHD.setValueAt(txtSoLuongHoaDon.getText(), row, 3);
                    tblSPHD.setValueAt(Integer.parseInt(txtSoLuongHoaDon.getText()) * Integer.parseInt(tblSPHD.getValueAt(row, 2).toString()), row, 4);
                    double sum = 0;

                    for (double a = 0; a < tblSPHD.getRowCount(); a++) {
                        sum = (sum + Double.parseDouble(tblSPHD.getValueAt((int) a, 4).toString()));
                    }
                    txtThanhToan.setText(Double.toString(sum));
                } catch (java.lang.NullPointerException e) {
                    MsgBox.alert(this, "Vui lòng không bỏ trống số lượng");
                } catch (java.lang.NumberFormatException e) {
                    MsgBox.alert(this, "Không được nhập chữ vào số lượng");
                    e.printStackTrace();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_txtSoLuongHoaDonKeyPressed

    private void tblSPHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPHDMouseClicked
        int row = tblSPHD.getSelectedRow();
        txtSoLuongHoaDon.setText(tblSPHD.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tblSPHDMouseClicked

    private void btnXoaQLBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaQLBHActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblSPHD.getModel();
        int del = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn gỡ sản phảm này khỏi giỏ hàng ?", "Gỡ sản phẩm", JOptionPane.YES_NO_OPTION);
        if (del == JOptionPane.YES_OPTION) {
            int row = tblSPHD.getSelectedRow();
            model.removeRow(row);
            txtSoLuongHoaDon.setText("");
        }
    }//GEN-LAST:event_btnXoaQLBHActionPerformed

    private void txtTimMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimMousePressed

    }//GEN-LAST:event_txtTimMousePressed

    private void txtTimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyPressed

    }//GEN-LAST:event_txtTimKeyPressed

    private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
        String query = txtTim.getText();
        search(query);
    }//GEN-LAST:event_txtTimKeyReleased

    private void txtDaThuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDaThuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDaThuKeyReleased

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        this.row = tblKhachHang.getSelectedRow();
        if (this.row >= 0) {
            String maKH = (String) tblKhachHang.getValueAt(this.row, 0);
            KhachHang kh = khdao.selectById(maKH);
            txtMaKH.setText(kh.getMaKh());
            txtTenKH3.setText(kh.getTenKh());
            txtSDT.setText(kh.getSDT());
            txtDiaChi.setText(kh.getDiaChi());
            if (kh.isGioiTinh() == false) {
                rdoNamKh.setSelected(true);
            } else if (kh.isGioiTinh() == true) {
                rdoNuKh.setSelected(true);
            }
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void txtTongTienHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienHoaDonActionPerformed

    private void tblThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuongHieuMouseClicked
        this.row = tblThuongHieu.getSelectedRow();
        if (this.row >= 0) {
            String maTh = (String) tblThuongHieu.getValueAt(this.row, 0);
            ThuongHieu th = thdao.selectById(maTh);

            txtMaTH.setText(th.getMaTH());
            txtTenTH.setText(th.getTenTH());
        }
        txtMaTH.setEnabled(false);
        btnXoaTH.setEnabled(true);
    }//GEN-LAST:event_tblThuongHieuMouseClicked

    private void txtTimKiemTHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTHKeyReleased
        String queryTH = txtTimKiemTH.getText();
        searchTH(queryTH);
    }//GEN-LAST:event_txtTimKiemTHKeyReleased

    private void btnThemTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTHActionPerformed
        if (txtMaTH.getText().equals("") || txtTenTH.getText().equals("")) {
            MsgBox.alert(this, "Không được bỏ trống thông tin");
        } else {
            try {

                this.ThemThuongHieu();
                fillComboBoxThuongHieu();

            } catch (Exception e) {
                MsgBox.alert(this, "Vui lòng kiểm tra lại");
            }
        }
    }//GEN-LAST:event_btnThemTHActionPerformed

    private void btnLamMoiTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTHActionPerformed
        txtMaTH.setText("");
        txtTenTH.setText("");
        txtMaTH.requestFocus();
        txtMaTH.setEnabled(true);
    }//GEN-LAST:event_btnLamMoiTHActionPerformed

    private void btnSuaTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaTHActionPerformed

        try {
            this.updateTH();
            fillComboBoxThuongHieu();
        } catch (Exception e) {
            MsgBox.alert(this, "Vui lòng không bỏ trống");
        }
    }//GEN-LAST:event_btnSuaTHActionPerformed

    private void btnXoaTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTHActionPerformed

        if (txtMaTH.getText().equals("") || txtTenTH.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa chọn thương hiệu cần xóa");
        } else {

            this.deleteTH();
            fillComboBoxThuongHieu();
        }
    }//GEN-LAST:event_btnXoaTHActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        pnlBanHang.setVisible(false);
        pnlCuaSoChinh.setVisible(false);
        pnlSanPham.setVisible(true);
        pnlHoaDon.setVisible(false);
        pnlNhanVien.setVisible(false);
        pnlKhachHang.setVisible(false);
        pnlThongKeBanHang.setVisible(false);
        pnlThongKeDoanhThu.setVisible(false);
        pnlThongKeNhanVien.setVisible(false);
        pnlThuongHieu.setVisible(false);
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void jmeDangXuatMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jmeDangXuatMenuSelected

    }//GEN-LAST:event_jmeDangXuatMenuSelected

    private void jmeDangXuatMenuDragMouseDragged(javax.swing.event.MenuDragMouseEvent evt) {//GEN-FIRST:event_jmeDangXuatMenuDragMouseDragged

    }//GEN-LAST:event_jmeDangXuatMenuDragMouseDragged

    private void txtTimTenKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimTenKHKeyReleased
        String query = txtTimTenKH.getText();
        searchKH(query);
    }//GEN-LAST:event_txtTimTenKHKeyReleased

    private void txtMaNVTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaNVTimKiemKeyReleased
        String query = txtMaNVTimKiem.getText();
        searchNV(query);
    }//GEN-LAST:event_txtMaNVTimKiemKeyReleased

    private void cboThangTKNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangTKNVActionPerformed
        this.fillTableTKNV();
    }//GEN-LAST:event_cboThangTKNVActionPerformed

    private void cboNamTKNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamTKNVActionPerformed
        this.fillTableTKNV();
    }//GEN-LAST:event_cboNamTKNVActionPerformed

    private void cboThangTKBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangTKBHActionPerformed
        if (cboThangTKBH.getSelectedIndex() != 0) {
            cboNgayTKBH.setSelectedIndex(0);
            this.fillTableTKSP();
        }
    }//GEN-LAST:event_cboThangTKBHActionPerformed

    private void cboNamTKBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamTKBHActionPerformed
        cboThangTKBH.setSelectedIndex(0);
        cboNgayTKBH.setSelectedIndex(0);
        this.fillTableTKSP();
    }//GEN-LAST:event_cboNamTKBHActionPerformed

    private void cboNgayTKBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNgayTKBHActionPerformed
        if (cboNgayTKBH.getSelectedIndex() != 0) {
            this.fillTableTKSP();
        }
    }//GEN-LAST:event_cboNgayTKBHActionPerformed

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
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loading().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel TieuDe;
    private javax.swing.JLabel TieuDe1;
    private javax.swing.JLabel TieuDe2;
    private javax.swing.JLabel TieuDe3;
    private javax.swing.JLabel TongHopThongKe;
    private javax.swing.JLabel TrangBanHang;
    private javax.swing.JLabel TrangChu;
    private javax.swing.JLabel TrangHoaDon;
    private javax.swing.JLabel TrangKhachHang;
    private javax.swing.JLabel TrangNhanVien;
    private javax.swing.JLabel TrangSanPham;
    private javax.swing.ButtonGroup btgSapXepSP;
    private com.k33ptoo.components.KButton btnBanHangQLNVSX;
    private com.k33ptoo.components.KButton btnBanHangTKDT;
    private com.k33ptoo.components.KButton btnDoanhThuQLBH;
    private com.k33ptoo.components.KButton btnDoanhThuQLNVXS;
    private com.k33ptoo.components.KButton btnHienThiTatCaHoaDon;
    private com.k33ptoo.components.KButton btnLamMoiQLHD;
    private com.k33ptoo.components.KButton btnLamMoiQLKH;
    private com.k33ptoo.components.KButton btnLamMoiQLNV;
    private com.k33ptoo.components.KButton btnLamMoiQLSP;
    private com.k33ptoo.components.KButton btnLamMoiTH;
    private com.k33ptoo.components.KButton btnNhanVienQLBH;
    private com.k33ptoo.components.KButton btnNhanVienTKDT;
    private com.k33ptoo.components.KButton btnQuanLyThuongHieu;
    private com.k33ptoo.components.KButton btnQuayLai;
    private com.k33ptoo.components.KButton btnSuaQLKH;
    private com.k33ptoo.components.KButton btnSuaQLNV;
    private com.k33ptoo.components.KButton btnSuaQLSP;
    private com.k33ptoo.components.KButton btnSuaTH;
    private com.k33ptoo.components.KButton btnThemQLNV;
    private com.k33ptoo.components.KButton btnThemQLSP;
    private com.k33ptoo.components.KButton btnThemSoLuong;
    private com.k33ptoo.components.KButton btnThemTH;
    private com.k33ptoo.components.KButton btnTimQLHD;
    private com.k33ptoo.components.KButton btnTimQLSP;
    private com.k33ptoo.components.KButton btnTimSDTQLBH;
    private com.k33ptoo.components.KButton btnXoaQLBH;
    private com.k33ptoo.components.KButton btnXoaQLHD;
    private com.k33ptoo.components.KButton btnXoaQLNV;
    private com.k33ptoo.components.KButton btnXoaQLSP;
    private com.k33ptoo.components.KButton btnXoaTH;
    private com.k33ptoo.components.KButton btnXuatHoaDon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboNamTKBH;
    private javax.swing.JComboBox<String> cboNamTKNV;
    private javax.swing.JComboBox<String> cboNgayTKBH;
    private javax.swing.JComboBox<String> cboThangTKBH;
    private javax.swing.JComboBox<String> cboThangTKNV;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenu jmeDangXuat;
    private javax.swing.JMenu jmeGioiTthieu;
    private javax.swing.JMenu jmeHuongDAn;
    private javax.swing.JPanel jpDSHD;
    private javax.swing.JPanel jpDSSP;
    private javax.swing.JPanel jpDSSP1;
    private javax.swing.JPanel jpDSSP3;
    private javax.swing.JPanel jpDSSP4;
    private javax.swing.JPanel jpThanhToan;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lblAnhSp;
    private javax.swing.JLabel lblCV;
    private javax.swing.JLabel lblHinhSP;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JPanel pnl21;
    private javax.swing.JPanel pnlBanHang;
    private javax.swing.JPanel pnlCuaSoChinh;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlLeftContent;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlRightContent;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKeBanHang;
    private javax.swing.JPanel pnlThongKeDoanhThu;
    private javax.swing.JPanel pnlThongKeNhanVien;
    private javax.swing.JPanel pnlThuongHieu;
    private javax.swing.JRadioButton rdoGiaGiamDan;
    private javax.swing.JRadioButton rdoHienThiTatCa;
    private javax.swing.JRadioButton rdoNamKH;
    private javax.swing.JRadioButton rdoNamKh;
    private javax.swing.JRadioButton rdoNamNv;
    private javax.swing.JRadioButton rdoNuKH;
    private javax.swing.JRadioButton rdoNuKh;
    private javax.swing.JRadioButton rdoNuNv;
    private javax.swing.JRadioButton rdoTen;
    private javax.swing.JTable tblBanHang;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblSPHD;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblTKNV;
    private javax.swing.JTable tblTKSP;
    private javax.swing.JTable tblThuongHieu;
    private javax.swing.JTextField txtDaThu;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiaChiKH;
    private javax.swing.JTextField txtDiaChiNv;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDu;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtGiaBanHang;
    private javax.swing.JTextField txtHangBanHang;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaHoaDonTimKiem;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaNVTimKiem;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMaTH;
    private javax.swing.JTextField txtMauBanHang;
    private javax.swing.JTextField txtMauSac;
    private javax.swing.JTextArea txtMoTaSP;
    private javax.swing.JTextArea txtMotaBanHang;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtNgayLapHoaDon;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSdtNv;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongHoaDon;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenKH3;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNVHD;
    private javax.swing.JTextField txtTenSPBanHang;
    private javax.swing.JTextField txtTenSp;
    private javax.swing.JTextField txtTenTH;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTimKiemSP;
    private javax.swing.JTextField txtTimKiemTH;
    private javax.swing.JTextField txtTimTenKH;
    private javax.swing.JTextField txtTongTienHoaDon;
    // End of variables declaration//GEN-END:variables
}
