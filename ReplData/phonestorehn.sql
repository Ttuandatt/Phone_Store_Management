use phonestore

select *
from sys.procedures
where name like 'sp_%' 

select @@SERVERNAME as ServerName

create database phonestorehn;
--drop database phonestorehn;
--USE master;
--ALTER DATABASE phonestore SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE phonestore;
use phonestore;
select * from LINK4.phonestore.dbo.sanpham
select * from sanpham
select * from nhanvien
select * from CHUCVU


-- Bảng Thương Hiệu
CREATE TABLE THUONGHIEU (
    maTH VARCHAR(50) NOT NULL,
    tenTH NVARCHAR(255) NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    PRIMARY KEY (maTH)
);
--DROP TABLE THUONGHIEU;



-- Bảng Sản Phẩm
CREATE TABLE SANPHAM (
    maSP VARCHAR(50) NOT NULL,
    tenSP NVARCHAR(255) NOT NULL,
    pin NVARCHAR(50) NOT NULL,
    OS NVARCHAR(50) NOT NULL,
    camTruoc NVARCHAR(50) NOT NULL,
    camSau NVARCHAR(50) NOT NULL,
    xuatXu NVARCHAR(100) NOT NULL,
    hinhAnh VARBINARY(MAX), -- Chỉnh sửa để lưu ảnh dạng BLOB
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maTH VARCHAR(50) NOT NULL,
	
    PRIMARY KEY (maSP),
    CONSTRAINT FK_SANPHAM_THUONGHIEU FOREIGN KEY (maTH) REFERENCES THUONGHIEU(maTH) ON DELETE NO ACTION
);
--drop table sanpham;

-- Bảng Phiên Bản Sản Phẩm
CREATE TABLE PBSP (
    maPBSP VARCHAR(50) NOT NULL,
    mauSac NVARCHAR(50) NOT NULL,
    ram NVARCHAR(50) NOT NULL,
    rom NVARCHAR(50) NOT NULL,
    giaBan DECIMAL(18,2) NOT NULL,
    soLuong INT,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maSP VARCHAR(50) NOT NULL,

    PRIMARY KEY (maPBSP),
    CONSTRAINT FK_PBSP_SANPHAM FOREIGN KEY (maSP) REFERENCES SANPHAM(maSP) ON DELETE NO ACTION
);
--DROP TABLE PBSP;

-- Bảng Nhà Cung Cấp
CREATE TABLE NHACUNGCAP (
    maNCC VARCHAR(50) NOT NULL,
    tenNCC NVARCHAR(255) NOT NULL,
    sdt NVARCHAR(20) NOT NULL,
    email NVARCHAR(255),
    diaChi NVARCHAR(255),
    trangThai NVARCHAR(50) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    PRIMARY KEY (maNCC)
);
--DROP TABLE NHACUNGCAP;


-- Bảng Chức Vụ
CREATE TABLE CHUCVU (
    maCV VARCHAR(50) NOT NULL,
    tenCV NVARCHAR(255) NOT NULL,
    luongCB FLOAT NOT NULL,
	trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,

    PRIMARY KEY (maCV)
);
--DROP TABLE CHUCVU;


-- Bảng Khách Hàng
CREATE TABLE KHACHHANG (
    maKH VARCHAR(50) NOT NULL,
    hoTen NVARCHAR(255) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh NVARCHAR(10) NOT NULL,
    diaChi NVARCHAR(255) NOT NULL,
    sdt NVARCHAR(20) NOT NULL,
    email NVARCHAR(255) NOT NULL,
	trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,

	PRIMARY KEY(maKH),
);
--DROP TABLE KHACHHANG;


-- Bảng Nhân Viên
CREATE TABLE NHANVIEN (
    maNV VARCHAR(50) NOT NULL,
    hoTen NVARCHAR(255) NOT NULL,
    ngaySinh DATE NOT NULL,
    gioiTinh NVARCHAR(3) CHECK (gioiTinh IN (N'Nam', N'Nữ')) NOT NULL,
    diaChi NVARCHAR(255) NOT NULL,
    sdt NVARCHAR(20) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    hinhAnh VARBINARY(MAX),
    matKhau NVARCHAR(255) NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maCV VARCHAR(50),

    PRIMARY KEY (maNV),
    CONSTRAINT FK_NHANVIEN_CHUCVU FOREIGN KEY (maCV) REFERENCES CHUCVU(maCV) ON DELETE NO ACTION,
);
--DROP TABLE NHANVIEN;


-- Bảng Phiếu Nhập
CREATE TABLE PHIEUNHAP (
    maPN VARCHAR(50) NOT NULL,
    ngayTao DATE NOT NULL,
    tongTien FLOAT,
    trangThai NVARCHAR(50) CHECK (trangThai IN (N'Chờ xác nhận', N'Đã xác nhận', N'Đã nhận hàng')) NOT NULL,
    maNV VARCHAR(50) NOT NULL,
    maNCC VARCHAR(50) NOT NULL,

	PRIMARY KEY(maPN),
    CONSTRAINT FK_PHIEUNHAP_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    CONSTRAINT FK_PHIEUNHAP_NHACUNGCAP FOREIGN KEY(maNCC) REFERENCES NHACUNGCAP(maNCC) ON DELETE NO ACTION
);
--DROP TABLE PHIEUNHAP;


-- Bảng Chi Tiết Phiếu Nhập
CREATE TABLE CTPN (
    soLuong INT NOT NULL,
    giaNhap DECIMAL(18,2) NOT NULL,
    maPN VARCHAR(50) NOT NULL,
    maPBSP VARCHAR(50) NOT NULL,
    PRIMARY KEY (maPN, maPBSP),
    FOREIGN KEY (maPN) REFERENCES PHIEUNHAP(maPN) ON DELETE NO ACTION,
    FOREIGN KEY (maPBSP) REFERENCES PBSP(maPBSP) ON DELETE NO ACTION
);
--DROP TABLE CTPN;


-- Bảng Phiếu Xuất
CREATE TABLE PHIEUXUAT (
    maPX VARCHAR(50) PRIMARY KEY,
    ngayTao DATE NOT NULL,
    diaChi NVARCHAR(255),
    tongTien FLOAT,
    httt NVARCHAR(50),
    trangThai NVARCHAR(50) CHECK (trangThai IN (N'Chờ xác nhận', N'Đã xác nhận', N'Đã xuất hàng')) NOT NULL,
    maNV VARCHAR(50) NOT NULL,
    maKH VARCHAR(50) NOT NULL,
	CONSTRAINT FK_PHIEUXUAT_NHANVIEN FOREIGN KEY (maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
	CONSTRAINT FK_PHIEUXUAT_KHACHHANG FOREIGN KEY (maKH) REFERENCES KHACHHANG(maKH) ON DELETE NO ACTION
);
--DROP TABLE PHIEUXUAT;


-- Bảng Chi Tiết Phiếu Xuất
CREATE TABLE CTPX (
    soLuong INT NOT NULL,
    giaXuat DECIMAL(18,2) NOT NULL,
    maPX VARCHAR(50) NOT NULL,
    maPBSP VARCHAR(50) NOT NULL,
    PRIMARY KEY (maPX, maPBSP),
    FOREIGN KEY (maPX) REFERENCES PHIEUXUAT(maPX) ON DELETE NO ACTION,
    FOREIGN KEY (maPBSP) REFERENCES PBSP(maPBSP) ON DELETE NO ACTION
);
--DROP TABLE CTPX;


-- Bảng Đơn Xin Nghỉ
CREATE TABLE DONXINNGHI (
    maDon VARCHAR(50) NOT NULL,
    ngayTao DATE NOT NULL,
    ngayBD DATE NOT NULL,
    ngayKT DATE,
    lyDo NVARCHAR(MAX),
    ngayDuyet DATE,
    trangThai VARCHAR(20) CHECK (trangThai IN (N'Chờ Duyệt', N'Đã Duyệt', N'Từ chối')) NOT NULL,
    maNV VARCHAR(50) NOT NULL,
    maNguoiDuyet VARCHAR(50),

	PRIMARY KEY(maDon),
    CONSTRAINT FK_DONXINNGHI_NHANVIEN  FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,	-- khi xóa nhân viên này đi thì giá trị khóa ngoại ở đây sẽ set về null, nhưng vẫn giữ mã người duyệt bên dưới
    CONSTRAINT FK_DONXINNGHI_NGUOIDUYET FOREIGN KEY(maNguoiDuyet) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
);
--DROP TABLE DONXINNGHI;


-- Bảng Lịch Sử Chỉnh Sửa chức vụ
CREATE TABLE LSCHINHSUA (
    maLSCS INT IDENTITY(1,1) PRIMARY KEY,
    maNguoiChinhSua VARCHAR(50) FOREIGN KEY REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    maNguoiBiChinhSua VARCHAR(50) FOREIGN KEY REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    thoiGian DATE NOT NULL,
    giaTriCu NVARCHAR(MAX) NOT NULL,
    giaTriMoi NVARCHAR(MAX) NOT NULL
);
--DROP TABLE LSCHINHSUA;


-- Bảng lương
CREATE TABLE BANGLUONG(
	maBL varchar(50),	-- = "BL" + thang + nam + manv 
	thangLuong int NOT NULL,
	namLuong int NOT NULL,
	luongCB float NOT NULL,
	heSo float NOT NULL,
	phuCapAnTrua float,
	phuCapDiLai float,
	thuong float, 
	bhxh float,
	bhyt float,
	bhtn float,
	thueTNCN float,
	tamUng float,
	thucNhan float,
	maNV varchar(50) NOT NULL,

	PRIMARY KEY(maBL),
	CONSTRAINT FK_BANGLUONG_NHANVIEN FOREIGN KEY (maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
)
--DROP TABLE BANGLUONG;


-- Bảng chấm công
CREATE TABLE BANGCHAMCONG(
	maBCC varchar(50),   -- = "CC" + thang + nam + manv 
	thangCC int NOT NULL,
	namCC int NOT NULL,
	soNgayLam float,
	soNgayNghiKP float,
	soNPCoLuong float,
	soNPKhongLuong float,
	soGioOTNgayThuong float,
	soGioOTNgayLe float,
	soGioOTCN float,
	maNV varchar(50) NOT NULL,

	PRIMARY KEY(maBCC),
	CONSTRAINT FK_BANGCHAMCONG_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
);
--DROP TABLE BANGCHAMCONG;


-- Bảng ghi chú
CREATE TABLE ChiTietChamCong (
    maCTCC varchar(50) , 	-- = "CT" + thang + nam + manv 
    ngayTao DATE NOT NULL,
    loaiChamCong NVARCHAR(255) CHECK (loaiChamCong IN (N'Tăng ca ngày lễ', N'Tăng ca chủ nhật', N'Tăng ca ngày thường', 
	N'Nghỉ phép có lương', N'Nghỉ phép không lương ', N'Nghỉ không phép', N'Nghỉ nửa buổi', N'Nghỉ việc')) NOT NULL, 	-- Thiếu gì bổ sung thêm
    chiTiet varchar(255),        -- Nếu OT thì chi tiết là số giờ OT, nếu nghỉ thì ghi lý do nghỉ
    maBCC VARCHAR(50),
	soGioOT float,

    PRIMARY KEY(maCTCC),
    CONSTRAINT FK_GHICHU_BANGCHAMCONG FOREIGN KEY (maBCC) REFERENCES BANGCHAMCONG(maBCC) ON DELETE NO ACTION
);
--DROP TABLE ChiTietChamCong;

-------------------------------------------- ALTER TABLE -------------------------------------------------



------------------------------------------ INSERT --------------------------------------
-- Insert vào bảng THUONGHIEU
INSERT INTO THUONGHIEU (maTH, tenTH, trangThai) VALUES
('TH001', N'Apple', 'on'),
('TH002', N'Samsung', 'on'),
('TH003', N'Xiaomi', 'off'),
('TH004', N'Oppo', 'on'),
('TH005', N'Vivo', 'off');

-- Insert vào bảng SANPHAM
INSERT INTO SANPHAM (maSP, tenSP, pin, OS, camTruoc, camSau, xuatXu, hinhAnh, trangThai, maTH) VALUES
('SP001', N'iPhone 13', '3240mAh', 'iOS', '12MP', '12MP + 12MP', 'USA', 0x, 'on', 'TH001'),
('SP002', N'Galaxy S22', '3700mAh', 'Android', '10MP', '50MP + 12MP', 'Korea', 0x, 'on', 'TH002'),
('SP003', N'Redmi Note 11', '5000mAh', 'Android', '13MP', '50MP + 8MP', 'China', 0x, 'off', 'TH003'),
('SP004', N'Find X5', '4800mAh', 'Android', '32MP', '50MP + 50MP', 'China', 0x, 'on', 'TH004'),
('SP005', N'Vivo X70', '4400mAh', 'Android', '32MP', '50MP + 12MP', 'China', 0x, 'off', 'TH005');

-- Thêm dữ liệu vào bảng PBSP
INSERT INTO PBSP (maPBSP, mauSac, ram, rom, giaBan, soLuong, trangThai, maSP)
VALUES
('PBSP001', N'Vàng', '8GB', '128GB', 20000000.00, 20, 'on', 'SP001'),
('PBSP002', N'Xanh dương', '12GB', '256GB', 25000000.00, 19, 'on', 'SP002'),
('PBSP003', N'Tím', '8GB', '128GB', 18000000.00, 18, 'on', 'SP003'),
('PBSP004', N'Bạc', '6GB', '64GB', 12000000.00, 17, 'on', 'SP004'),
('PBSP005', N'Đen', '8GB', '256GB', 22000000.00, 16, 'on', 'SP005');
INSERT INTO PBSP (maPBSP, mauSac, ram, rom, giaBan, soLuong, trangThai, maSP) 
VALUES
('PBSP006', N'Xám', '12GB', '512GB', 28000000.00, 15, 'on', 'SP002'),
('PBSP007', N'Xanh dương', '6GB', '128GB', 16000000.00, 14, 'on', 'SP003'),
('PBSP008', N'Hồng', '8GB', '256GB', 21000000.00, 13, 'on', 'SP004'),
('PBSP009', N'Bạc', '12GB', '1TB', 32000000.00, 12, 'on', 'SP002'),
('PBSP010', N'Đỏ', '8GB', '128GB', 19000000.00, 11, 'on', 'SP005'),
('PBSP011', N'Xanh lá', '6GB', '64GB', 14000000.00, 10, 'on', 'SP001');


-- Insert vào bảng NHACUNGCAP
INSERT INTO NHACUNGCAP (maNCC, tenNCC, sdt, email, diaChi, trangThai) VALUES
('NCC001', N'Công ty A', '0901234567', 'a@gmail.com', N'Hà Nội', N'On'),
('NCC002', N'Công ty B', '0912345678', 'b@gmail.com', N'Hồ Chí Minh', N'On'),
('NCC003', N'Công ty C', '0923456789', 'c@gmail.com', N'Đà Nẵng', N'On'),
('NCC004', N'Công ty D', '0934567890', 'd@gmail.com', N'Cần Thơ', N'On'),
('NCC005', N'Công ty E', '0945678901', 'e@gmail.com', N'Hải Phòng', N'On');

-- Insert bảng CHUCVU
INSERT INTO CHUCVU (maCV, tenCV, luongCB, trangThai) 
VALUES 
('CV001', N'Quản lý kho', 15000000, 'on'),
('CV002', N'Quản lý nhân sự', 8000000, 'on'),
('CV003', N'Nhân viên kho', 9000000, 'on'),
('CV004', N'Admin', 0, 'on');


-- Insert bảng NHANVIEN
INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV)
VALUES 
('NV001', N'Nguyễn Thị F', '1996-02-14', N'Nữ', N'123 Nguyễn Trãi, TP.HCM', '0906789012', 'nguyenthif@example.com', NULL, 'passf123', 'on', 'CV002'),
('NV002', N'Võ Văn G', '1993-06-23', N'Nam', N'456 Lê Văn Sỹ, Hà Nội', '0917890123', 'vovang@example.com', NULL, 'passg456', 'on', 'CV002'),
('NV003', N'Bùi Thị H', '1999-09-10', N'Nữ', N'789 Cách Mạng Tháng 8, Đà Nẵng', '0928901234', 'buithih@example.com', NULL, 'passh789', 'on', 'CV002'),
('NV004', N'Phan Văn I', '1990-12-05', N'Nam', N'321 Điện Biên Phủ, Cần Thơ', '0939012345', 'phanvani@example.com', NULL, 'passi101', 'on', 'CV002'),
('NV005', N'Lý Thị J', '1997-04-18', N'Nữ', N'654 Võ Văn Kiệt, Hải Phòng', '0940123456', 'lythij@example.com', NULL, 'passj202', 'on', 'CV002');


INSERT INTO KHACHHANG (maKH, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, trangThai)
VALUES
('KH001', N'Nguyễn Văn X', '1995-02-14', N'Nam', N'Hà Nội', '0967890123', 'x@gmail.com', 'on'),
('KH002', N'Trần Thị Y', '1998-06-10', N'Nữ', N'Đà Nẵng', '0978901234', 'y@gmail.com', 'off'),
('KH003', N'Phạm Văn Z', '1992-09-25', N'Nam', N'Hồ Chí Minh', '0989012345', 'z@gmail.com', 'on'),
('KH004', N'Lê Thị U', '1985-12-05', N'Nữ', N'Hải Phòng', '0990123456', 'u@gmail.com', 'off'),
('KH005', N'Hoàng Văn T', '2000-04-18', N'Nam', N'Bình Dương', '0901234568', 't@gmail.com', 'on');

-- Chèn dữ liệu vào bảng PHIEUNHAP
INSERT INTO PHIEUNHAP (maPN, ngayTao, tongTien, trangThai, maNV, maNCC) 
VALUES 
('PN001', '2024-03-01', 5000000, N'Chờ xác nhận', 'NV001', 'NCC005'),
('PN002', '2024-03-05', 3200000, N'Đã nhận hàng', 'NV002', 'NCC002'),
('PN003', '2024-03-10', 4500000, N'Đã nhận hàng', 'NV003', 'NCC004'),
('PN004', '2024-03-15', 2800000, N'Đã xác nhận', 'NV004', 'NCC003');


-- Chèn dữ liệu vào bảng CTPN
INSERT INTO CTPN (soLuong, giaNhap, maPN, maPBSP) VALUES 
-- PN001 có 3 chi tiết
(10, 500000, 'PN001', 'PBSP001'),
(15, 520000, 'PN001', 'PBSP002'),
(8, 490000, 'PN001', 'PBSP003'),

-- PN002 có 3 chi tiết
(5, 640000, 'PN002', 'PBSP004'),
(12, 630000, 'PN002', 'PBSP005'),
(9, 650000, 'PN002', 'PBSP006'),

-- PN003 có 3 chi tiết
(8, 550000, 'PN003', 'PBSP007'),
(10, 530000, 'PN003', 'PBSP008'),
(6, 540000, 'PN003', 'PBSP009'),

-- PN004 có 3 chi tiết
(12, 230000, 'PN004', 'PBSP010'),
(14, 250000, 'PN004', 'PBSP011'),
(9, 240000, 'PN004', 'PBSP012');

-- Chèn dữ liệu vào bảng PHIEUXUAT
INSERT INTO PHIEUXUAT (maPX, ngayTao, diaChi, tongTien, httt, trangThai, maNV, maKH) VALUES 
('PX001', '2024-04-01', N'12 Nguyễn Trãi, Hà Nội', 7500000, N'Chuyển khoản', N'Đã xác nhận', 'NV002', 'KH001'),
('PX002', '2024-04-05', N'45 Lê Lợi, TP.HCM', 4200000, N'Chuyển khoản', N'Đã xác nhận', 'NV002', 'KH002'),
('PX003', '2024-04-16', N'78 Trần Phú, TP.HCM', 5200000, N'Tiền mặt', N'Đã xác nhận', 'NV003', 'KH003'),
('PX004', '2024-04-15', N'20 Nguyễn Trãi, TP.HCM', 6100000, N'Tiền mặt', N'Chờ xác nhận', 'NV004', 'KH004');


-- Chèn dữ liệu vào bảng CTPX
INSERT INTO CTPX (soLuong, giaXuat, maPX, maPBSP) 
VALUES 
(3, 2500000, 'PX001', 'PBSP001'),
(2, 2100000, 'PX002', 'PBSP002'),
(5, 1040000, 'PX003', 'PBSP003'),
(4, 1525000, 'PX004', 'PBSP004'),
(6, 1380000, 'PX005', 'PBSP005');


-- Chèn dữ liệu vào bảng BANGCHAMCONG
INSERT INTO BANGCHAMCONG (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV) VALUES
('BCC0325NV001', 3, 2024, 21.5, 0.5, 0, 0, 0, 0, 0, 'NV001'),
('BCC0325NV002', 3, 2024, 21, 0, 0, 1, 0, 0, 0, 'NV002'),
('BCC0325NV003', 3, 2024, 8, 0, 0, 0, 0, 0, 0, 'NV003'),
('BCC0325NV004', 3, 2024, 22, 0, 0, 0, 2.0, 0, 0, 'NV004'),
('BCC0325NV005', 3, 2024, 21, 0, 1, 0, 0, 0, 0, 'NV005');



-- Chèn dữ liệu vào bảng BANGLUONG
INSERT INTO BANGLUONG (maBL, thangLuong, namLuong, luongCB, heSo, phuCapAnTrua, phuCapDiLai, thuong, bhxh, bhyt, bhtn, thueTNCN, tamUng, thucNhan, maNV) VALUES
('BL032024001', 3, 2024, 8000000, 1, 500000, 300000, 200000, 800000, 500000, 200000, 300000, 1000000, 9000000, 'NV001'),
('BL032024002', 3, 2024, 8000000, 1.2, 450000, 250000, 180000, 750000, 480000, 190000, 280000, 900000, 8500000, 'NV002'),
('BL032024003', 3, 2024, 9000000, 1, 320000, 250000, 820000, 520000, 220000, 350000, 1100000, 9200000, 9200000, 'NV003'),
('BL032024004', 3, 2024, 9500000, 1, 290000, 210000, 780000, 490000, 200000, 320000, 950000, 8800000, 9400000, 'NV004'),
('BL032024005', 3, 2024, 12000000, 1, 420000, 220000, 170000, 730000, 450000, 180000, 270000, 800000, 8100000, 'NV005');

INSERT INTO ChiTietChamCong (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT) VALUES 
('CT032025NV001', '2025-03-06', N'Nghỉ nửa buổi', N'Nghỉ chiều vì khám bệnh', 'BCC0325NV001', 4.0),
('CT032025NV002', '2025-03-07', N'Nghỉ phép không lương', N'Nghỉ việc cá nhân', 'BCC0325NV002', 5.0),
('CT032025NV003', '2025-03-08', N'Nghỉ việc', N'Nghỉ do thôi việc', 'BCC0325NV003', 0),
('CT032025NV004', '2025-03-09', N'Tăng ca ngày thường', null, 'BCC0325NV004', 2.0),
('CT032025NV005', '2025-03-10', N'Nghỉ phép có lương', N'Nghỉ ốm', 'BCC0325NV05', 0);


------------------------------------------ SELECT --------------------------------------
select * from nhanvien;
select * from sanpham;
select * from pbsp;
select * from phieunhap; 
select * from ctpn;
select * from phieuxuat;
select * from ctpx;
select * from chucvu;
select * from nhacungcap;
select * from thuonghieu;
select * from khachhang;
select * from bangchamcong;
select * from bangluong;
select * from lschinhsua;
select * from donxinnghi;
select * from chitietchamcong;
SELECT @@VERSION;
------------------------------------------ DELETE --------------------------------------
DELETE FROM CHITIETCHAMCONG;
DELETE FROM BANGCHAMCONG;
DELETE FROM BANGLUONG;
DELETE FROM LSCHINHSUA;
DELETE FROM DONXINNGHI;
DELETE FROM CTPX;
DELETE FROM PHIEUXUAT;
DELETE FROM KHACHHANG;
DELETE FROM CTPN;
DELETE FROM PHIEUNHAP;
DELETE FROM NHANVIEN;
DELETE FROM SANPHAM;
DELETE FROM PBSP;
DELETE FROM THUONGHIEU;
DELETE FROM KHACHHANG;
DELETE FROM NHACUNGCAP;
DELETE FROM CHUCVU;


------------------------------------------ STORED PROCEDURE --------------------------------------
-- 1. Lấy danh sách nhân viên
-- . Cập nhật nhân viên
-- . Lấy danh sách chức vụ
-- . Lấy chức vụ theo ID
-- . Lấy chức vụ theo tên
-- . Lấy tên chức vụ theo ID
-- . Thêm chức vụ
-- . Cập nhật chức vụ
-- .


-------------------------------- Các tiêu chí để lọc/tìm kiếm ---------------------------------
-- Đối với nhân viên:
--		- Tìm kiếm theo mã
--		- Tìm kiếm theo email
-- Đối với khách hàng:
-- Đối với sản phẩm:
-- Đối với kho:
-- Đối với nhà cung cấp:


-- Xem database được cấu hình làm distributor 
EXEC sp_get_distributor;

-- Gỡ bỏ distribution trên damian/mssqlserver01
-- 1️. Xóa các Publications và Subscriptions trước (nếu có)
-- ✅ Xóa tất cả Subscriptions
EXEC sp_dropsubscription @publication = 'Tên_Publication', @subscriber = 'all';
-- Hoặc nếu không biết tên Publication, bạn có thể liệt kê bằng lệnh:
EXEC sp_helppublication;
-- ✅ Xóa tất cả Publications
EXEC sp_droppublication @publication = 'Tên_Publication';

-- 2️. Xóa Distributor
USE master;
EXEC sp_dropdistributor @no_checks = 1, @ignore_distributor = 1;

-- 3️. Xóa cấu hình Distribution từ Publisher
EXEC sp_dropdistpublisher @publisher = 'DAMIAN\MSSQLSERVER01';