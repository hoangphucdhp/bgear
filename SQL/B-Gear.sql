use master
go

drop database BGear;
go

create database BGear
go
use BGear
go

create table NhanVien(
	MaNV nvarchar(9) primary key not null,
	MaCV nvarchar(9) not null,
	HoTen nvarchar(50),
	SDT nvarchar(10),
	Email nvarchar(50),
	DiaChi nvarchar(50),
	GioiTinh bit,
	NgaySinh date,
	MatKhau nvarchar(16)
);
go
create table ChucVu(
	MaCV nvarchar(9) primary key not null,
	CV nvarchar(50) 
);
go
create table ThuongHieu(
	MaTH nvarchar(9) primary key not null,
	TenTH nvarchar(50)
);
go
create table SanPham(
	MaSP nvarchar(9) primary key not null,
	TenSP nvarchar(50),
	MaTH nvarchar(9) null,
	DonGia int,
	Hinh image,
	SoLuongTonKho int,
	MauSac nvarchar(30),
	MoTa nvarchar(500)
);
go
create table KhachHang(
	MaKH nvarchar(9) primary key not null,
	TenKH nvarchar(50),
	SDT nvarchar(10),
	DiaChi nvarchar(100),
	GioiTinh bit
);
go
create table HoaDonChiTiet(
	MaHDCT nvarchar(13) primary key not null,
	MaHD nvarchar(13) null,
	MaSP nvarchar(9) null,
	SoLuong int
);
go
create table HoaDon(
	MaHD nvarchar(13) primary key not null,
	MaKH nvarchar(9) null,
	MaNV nvarchar(9) null,
	NgayLapHoaDon date,
);
go

alter table NhanVien
add constraint fk_chucvu_nhanvien
	foreign key (MaCV) references ChucVu(MaCV);
go
alter table SanPham
add constraint fk_thuonghieu_sanpham
	foreign key (MaTH) references ThuongHieu(MaTH);
go
alter table HoaDon
add constraint fk_hoadon
	foreign key (MaNV) references NhanVien(MaNV),
	foreign key (MaKH) references KhachHang(MaKH)
go
alter table HoaDonChiTiet
add constraint fk_hoadonchitiet
	foreign key (MaHD) references HoaDon(MaHD),
	foreign key (MaSP) references SanPham(MaSP)



INSERT INTO ChucVu
VALUES ('CV001', N'Quản lý'),
('CV002', N'Nhân viên')
GO


INSERT INTO NhanVien
VALUES ('NV001', 'CV001', N'Nguyễn Anh Đức', '0948342514', 'ducne123@gmail.com', N'Cà Mau', 0, '1995-01-22', 'Duc123'),
('NV002', 'CV001', N'Lê Hoàng Tuấn', '0938541584', 'tuandv123@gmail.com', N'Cần Thơ',  0, '1996-10-21', 'Tuan123'),
('NV003', 'CV002', N'Võ Hoàng Yến', '0947392112', 'yencute@gmail.com', N'Cà Mau', 1, '1999-12-11', 'Yen123'),
('NV004', 'CV002', N'Nguyễn Ưng Hoàng', '0991343564', 'unghoangne@gmail.com', N'Phú Quốc', 0, '2000-11-22', 'Hoang123'),
('NV005', 'CV002', N'Nguyễn Anh Phong', '0988312416', 'anhphong123@gmail.com', N'Sóc Trăng', 0, '2002-12-16', 'Phong123'),
('NV006', 'CV002', N'Nguyễn Ngọc Ánh', '091812255', 'anhne123@gmail.com', N'Cần Thơ', 1, '2003-05-11', 'Anh123'),
('NV007', 'CV002', N'Nguyễn Thanh Hà', '0922322511', 'habbb123@gmail.com', N'Cần Thơ', 0, '2003-08-16', 'Ha123'),
('NV008', 'CV002', N'Nguyễn Khả Ái', '0981312219', 'aicyti123@gmail.com', N'Cà Mau', 1, '2001-12-28', 'Ai123'),
('NV009', 'CV002', N'Nguyễn Đức Trọng', '0918312812', 'traongdv123@gmail.com', N'Cần Thơ', 0, '2000-09-22', 'Trong123'),
('NV010', 'CV002', N'Nguyễn Đức Phúc', '0968362524', 'phucdeptrai@gmail.com', N'Cần Thơ', 0, '2001-08-22', 'Phuc123'),
('NV011', 'CV002', N'Lê Quốc Anh', '0978372534', 'anhdeptrai123@gmail.com', N'Cần Thơ', 0, '2003-10-22', 'Anh123'),
('NV012', 'CV002', N'Nguyễn Minh Kha', '0928312564', 'khane123@gmail.com', N'Cà Mau', 0, '2003-02-22', 'Kha123'),
('admin', 'CV001', N'admin', '0948342534', 'admin@gmail.com', N'Cần Thơ', 0, '1995-01-01', 'admin')
GO


INSERT INTO KhachHang
VALUES ('KH001', N'Nguyễn Quốc Thịnh', '0918427619', N'Cần Thơ', 0),
('KH002', N'Tôn Trường Kỳ', '0918641233', N'Cần Thơ', 1),
('KH003', N'Lăng Gia Hiệp', '0918641311', N'Cần Thơ', 0),
('KH004', N'Hạ Mạnh Hùng', '0918526111', N'Cần Thơ', 0),
('KH005', N'Viên Cao Nghiệp', '0922342356', N'Cần Thơ', 0),
('KH006', N'Tinh Thành Ân', '0928433619', N'Cần Thơ', 0),
('KH007', N'Phan Phi Hải', '0938527113', N'Cần Thơ', 0),
('KH008', N'Danh Khải Tuấn', '0958527111', N'Cần Thơ', 0),
('KH009', N'Khà Gia Kiên', '0978221629', N'Cần Thơ', 0),
('KH010', N'Phạm Dũng Trí', '0916417411', N'Cần Thơ', 0),
('KH011', N'Đào Nhung Thùy', '0988447911', N'Cần Thơ', 1),
('KH012', N'Tinh Thành Ân', '0968467126', N'Cần Thơ', 0),
('KH013', N'Đống Ðắc Trọng', '0998172142', N'Cần Thơ', 0),
('KH014', N'Lư Phú Hưng', '0988122641', N'Cần Thơ', 0)
GO


INSERT INTO ThuongHieu
VALUES ('TH001', 'Asus'),
('TH002', 'Dell'),
('TH003', 'MSI'),
('TH004', 'Lenovo'),
('TH005', 'Macbook')
GO


INSERT INTO SanPham
VALUES
--Asus
('SP001', N'Laptop Asus TUF Gaming F15', 'TH001', 26990, 'anh1.jpg', 30, N'Đen', N'CPU Intel Core i7-12700H Processor 24MB Cache, up to 4.70GHz'),
('SP002', N'Laptop Asus Vivobook Pro 14', 'TH001', 17990, 'anh2.jpg', 18, N'Đen', N'CPU AMD Ryzen R7-5800H (16MB Cache, up to 4.40GHz), Ram 8GB DDR4 onboard'),
('SP003', N'Laptop ASUS Gaming TUF Dash F15 FX517ZC', 'TH001', 23690, 'anh3.jpg', 25, N'Đen', N'Core i5-12450H, R8DDR5, 512GB, 15.6FHD 144Hz, RTX3050, Win11'),
('SP004', N'Laptop Asus Gaming ROG Strix G15', 'TH001', 19390, 'anh4.jpg', 42, N'Đen', N'CPU AMD Ryzen R7-4800H (8MB, up to 4.20GHz), RAM 8GB DDR4 3200MHz'),
('SP005', N'Laptop ASUS Zenbook 14', 'TH001', 21490, 'anh5.jpg', 30, N'Đen', N'CPU AMD Ryzen™ 5 5625U Mobile Processor (16MB, upto 4.30GHz), RAM 8GB LPDDR4X 4266MHz onboard'),
('SP006', N'Laptop Asus X515EA', 'TH001', 10590, 'anh6.jpg', 20, N'Xám', N'CPU Intel Core i3-1115G4 (6MB, upto 4.10GHz), RAM 4GB DDR4 3200Mhz'),
--Dell
('SP007', N'Laptop Dell Latitude 3420', 'TH002', 11250, 'anh7.jpg', 20, N'Đen', N'CPU Intel Core i7-1255U (12MB, up to 4.70GHz), RAM 8GB DDR4 2666MHz'),
('SP008', N'Laptop Dell Inspiron 3520', 'TH002', 10590, 'anh8.jpg', 18, N'Xám', N'CPU Intel Core i3-1115G4 (6MB, upto 4.10GHz), RAM 4GB DDR4 3200Mhz'),
('SP009', N'Laptop Dell Inspiron 5620', 'TH002', 22390, 'anh9.jpg', 35, N'Xám', N'CPU Intel Core i7-1255U (12MB, up to 4.70GHz), RAM 8GB DDR4 3200MHz'),
('SP010', N'Laptop Dell Vostro 3510', 'TH002', 21190, 'anh10.jpg', 32, N'Xám', N'VGA NVIDIA GeForce MX350 2GB GDDR5, Display 15.6Inch FHD WVA 60Hz Anti-glare'),
--MSI
('SP011', N'Laptop MSI Modern 15 A11MU', 'TH003', 14890, 'anh11.jpg', 18, N'Đen', N'CPU Intel Core i5-1155G7 (8MB, up to 4.50GHz), RAM 8GB DDR4 3200MHz, SSD 512GB NVMe PCIe Gen3x4'),
('SP012', N'Laptop MSI GF63 Thin 11SC', 'TH003', 16990, 'anh12.jpg', 20, N'Đen', N'CPU Intel Core i5-11400H (12MB, up to 4.50GHz), RAM 8GB DDR4 3200MHz, SSD 512GB NVMe PCIe'),
('SP013', N'Laptop MSI Modern 14 B11MOU', 'TH003', 49990, 'anh13.jpg', 33, N'Xám', N'CPU Intel Core i7-1195G7 (12MB, up to 5.00GHz), RAM 16GB DDR4 3200MHz (8GB + 8GB AKC tặng), SSD 512GB NVMe PCIe Gen3x4'),
('SP014', N'Laptop MSI Creator Z16', 'TH003', 26990, 'anh14.jpg', 30, N'Xám', N'CPU Intel Core i7-11800H (24MB, up to 4.60GHz), Chipset Intel HM570, RAM 32GB DDR4 3200MHz (2x16GB)'),
('SP015', N'Laptop MSI Titan GT77', 'TH003', 135790, 'anh15.jpg', 22, N'Đen', N'CPU Intel Core i9-12900HX (30MB, up to 5.00GHz), RAM 64GB DDR5 4800MHz (2x32GB), SDD 2TB NVMe PCIe Gen4x4'),
--Lenovo
('SP016', N'Laptop Lenovo IdeaPad Gaming 3', 'TH004', 21390, 'anh16.jpg', 34, N'Đen', N'CPU: Ryzen R5 5600H (16MB Cache, up to 4.20GHz), RAM: 8GB DDR4 3200MHz'),
('SP017', N'Laptop Lenovo ThinkPad E15 Gen 4', 'TH004', 22490, 'anh17.jpg', 22, N'Đen', N'CPU Intel Core i7-1255U (12MB Cache, upto 4.70GHz), RAM 8GB DDR4 3200MHz Onboard'),
('SP018', N'Laptop Lenovo ThinkPad E14 Gen 4', 'TH004', 17890, 'anh18.jpg', 11, N'Đen', N'CPU Intel Core i5-1235U (12MB Cache, upto 4.40GHz), RAM 16GB DDR4 3200MHz Onboard (8G+8G AKC tặng)'),
('SP019', N'Laptop Lenovo ThinkBook 13s G2', 'TH004', 16590, 'anh19.jpg', 30, N'Xám', N'CPU Intel Core i7-1165G7 (12MB, up to 4.70GHz), RAM 8GB LPDDR4x 4266MHz Onboard'),
--Macbook
('SP020', N'MacBook Pro 13 inch 2020 M1 MYD82SA/A', 'TH005', 31390, 'anh20.jpg', 10, N'Xám', N'CPU Apple M1 chip 8-core CPU with 4 performance cores and 4 efficiency cores, RAM 8GB, SSD 256GB, Display 13.3Inch 2560x1600 IPS LED-backlit'),
('SP021', N'MacBook Pro 16 M1 Max', 'TH005', 160589, 'anh21.jpg', 9, N'Xám', N'CPU Apple M1 PRO 10-Core, SSD 8TB, RAM 64GB, Display 16.2Inch,Độ phân giải gốc 3456 x 2234 ở 254 pixel mỗi inch'),
('SP022', N'MacBook Pro 14 M1- Z15J001MF', 'TH005', 62689, 'anh22.jpg', 13, N'Xám', N'CPU Apple M1 PRO 8-Core, SSD 512GB, RAM 32GB, Display 14.2Inch, độ phân giải gốc (3024 x 1964) ở 254 pixel mỗi inch, độ sáng tối đa 1600 nits')
GO
	
INSERT INTO HOADON
VALUES
('HD001', 'KH001', 'NV001', '2022-11-10'),
('HD002', 'KH001', 'NV001', '2022-11-10'),
('HD003', 'KH001', 'NV001', '2022-11-10'),
('HD004', 'KH002', 'NV002', '2022-11-11'),
('HD005', 'KH002', 'NV002', '2022-11-11'),
('HD006', 'KH002', 'NV002', '2022-11-11'),
('HD007', 'KH002', 'NV002', '2022-11-11'),
('HD008', 'KH003', 'NV003', '2022-11-13'),
('HD009', 'KH004', 'NV004', '2022-11-13'),
('HD010', 'KH004', 'NV004', '2022-11-13'),
('HD011', 'KH005', 'NV005', '2022-11-14'),
('HD012', 'KH006', 'NV006', '2022-11-14'),
('HD013', 'KH007', 'NV007', '2022-11-15'),
('HD014', 'KH007', 'NV007', '2022-11-15'),
('HD015', 'KH007', 'NV007', '2022-11-15'),
('HD016', 'KH008', 'NV008', '2022-11-17'),
('HD017', 'KH008', 'NV008', '2022-11-17'),
('HD018', 'KH009', 'NV009', '2022-11-18'),
('HD019', 'KH010', 'NV009', '2022-11-18'),
('HD020', 'KH011', 'NV010', '2022-11-19'),
('HD021', 'KH012', 'NV011', '2022-11-19'),
('HD022', 'KH013', 'NV012', '2022-11-19'),
('HD023', 'KH014', 'NV012', '2022-11-20'),
('HD024', 'KH014', 'NV012', '2022-11-20'),
('HD025', 'KH014', 'NV012', '2022-11-20')
--Đã sửa tháng lại rồi cho dễ code chứ tháng 11 hoài sao code
GO

INSERT INTO HoaDonChiTiet
VALUES
('ID001', 'HD001', 'SP001', 1),
('ID002', 'HD002', 'SP002', 1),
('ID003', 'HD003', 'SP003', 1),
('ID004', 'HD004', 'SP006', 1),
('ID005', 'HD005', 'SP007', 1),
('ID006', 'HD006', 'SP003', 1),
('ID007', 'HD007', 'SP011', 1),
('ID008', 'HD008', 'SP012', 1),
('ID009', 'HD009', 'SP014', 1),
('ID010', 'HD010', 'SP002', 1),
('ID011', 'HD011', 'SP015', 2),
('ID012', 'HD012', 'SP021', 1),
('ID013', 'HD013', 'SP022', 1),
('ID014', 'HD014', 'SP014', 1),
('ID015', 'HD015', 'SP017', 1),
('ID016', 'HD016', 'SP015', 1),
('ID017', 'HD017', 'SP001', 1),
('ID018', 'HD018', 'SP017', 1),
('ID019', 'HD019', 'SP011', 1),
('ID020', 'HD020', 'SP021', 1),
('ID021', 'HD021', 'SP014', 1),
('ID022', 'HD022', 'SP012', 1),
('ID023', 'HD023', 'SP011', 1),
('ID024', 'HD024', 'SP016', 1),
('ID025', 'HD025', 'SP019', 1)
GO
--proc
------------------Bán Hàng-------------------------
CREATE PROC sp_BanHangNgay(@Day int, @Month int , @Year int)
AS BEGIN
       SELECT 
			SP.MaSP,
			SP.TenSP,
			COUNT(HDCT.SoLuong) as SoLuong
	   FROM HoaDonChiTiet HDCT
	   JOIN SanPham SP ON HDCT.MaSP = SP.MaSP
	   JOIN HoaDon HD ON HD.MaHD = HDCT.MaHD
	   WHERE DAY(convert(date, HD.NgayLapHoaDon, 103)) = @Day and-- 1--
			 MONTH(convert(date, HD.NgayLapHoaDon, 103)) = @Month and-- 1--
			 YEAR(convert(date, HD.NgayLapHoaDon, 103)) = @Year -- 2022--
	   GROUP BY SP.TenSP,SP.MaSP
   END
GO
EXECUTE sp_BanHangNgay
drop proc sp_BanHangNgay

CREATE PROC sp_BanHangThang(@Month int , @Year int)
AS BEGIN
       SELECT 
			SP.MaSP,
			SP.TenSP,
			COUNT(HDCT.SoLuong) as SoLuong
	   FROM HoaDonChiTiet HDCT
	   JOIN SanPham SP ON HDCT.MaSP = SP.MaSP
	   JOIN HoaDon HD ON HD.MaHD = HDCT.MaHD
	   WHERE MONTH(convert(date, HD.NgayLapHoaDon, 103)) = @Month and-- 1--
			 YEAR(convert(date, HD.NgayLapHoaDon, 103)) = @Year -- 2022--
	   GROUP BY SP.TenSP,SP.MaSP
   END
GO
EXECUTE sp_BanHangThang
drop proc sp_BanHangThang

CREATE PROC sp_BanHangNam(@Year int)
AS BEGIN
       SELECT 
			SP.MaSP,
			SP.TenSP,
			COUNT(HDCT.SoLuong) as SoLuong
	   FROM HoaDonChiTiet HDCT
	   JOIN SanPham SP ON HDCT.MaSP = SP.MaSP
	   JOIN HoaDon HD ON HD.MaHD = HDCT.MaHD
	   WHERE YEAR(convert(date, HD.NgayLapHoaDon, 103)) = @Year -- 2022--
	   GROUP BY SP.TenSP,SP.MaSP
   END
GO
EXECUTE sp_BanHangNam
drop proc sp_BanHangNam
-------------Nhân Viên-----------------------
CREATE PROC sp_NhanVien(@Month int , @Year int)
AS BEGIN
       SELECT 
			NV.MaNV,
			NV.HoTen,
			COUNT(HDCT.SoLuong ) as SoLuong
	   FROM NhanVien NV
	   INNER JOIN HoaDon HD ON HD.MaNV = NV.MaNV
	   INNER JOIN HoaDonChiTiet HDCT ON HDCT.MaHD = HD.MaHD
	   WHERE YEAR(convert(date, HD.NgayLapHoaDon, 103)) =  @Year and--2022
			 MONTH(convert(date, HD.NgayLapHoaDon, 103)) = @Month--3 
	   GROUP BY NV.MaNV, NV.HoTen 
	   END
GO
EXECUTE sp_NhanVien 2,2022
drop proc sp_NhanVien
---------------------------Doanh Thu---------------
CREATE PROC sp_DoanhThu
AS BEGIN
       SELECT 
			MONTH(HD.NgayLapHoaDon) AS Thang,
			SUM(SP.DonGia * HDCT.SoLuong) as ThanhTien
	   FROM SanPham SP
	   INNER JOIN HoaDonChiTiet HDCT ON HDCT.MaSP = SP.MaSP
	   INNER JOIN HoaDon HD ON HD.MaHD = HDCT.MaHD
	   GROUP BY MONTH(HD.NgayLapHoaDon)
	   END
GO
drop proc sp_DoanhThu	
EXECUTE sp_DoanhThu
--------------------------------------------------------------------------------------------------------------
SELECT b.TenTH FROM SanPham a inner join ThuongHieu b on a.MaTH = b.MaTH GROUP BY b.TenTH
SELECT a.MaSP, a.TenSP, a.DonGia FROM SanPham a
SELECT c.TenSP, d.TenTH, a.SoLuong, c.DonGia, c.MauSac FROM HoaDonChiTiet a INNER JOIN HoaDon b ON a.MaHD = b.MaHD INNER JOIN SanPham c ON a.MaSP = c.MaSP INNER JOIN ThuongHieu d on c.MaTH=d.MaTH
SELECT * FROM SanPham
SELECT a.*, b.TenSP FROM HoaDonChiTiet a INNER JOIN SanPham b ON a.MaSP = b.MaSP
SELECT * FROM HoaDonChiTiet
SELECT * FROM HoaDon
SELECT * FROM ChucVu
SELECT * FROM NhanVien