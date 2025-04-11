create database phonestore;
drop database phonestore;
--USE master;
--ALTER DATABASE phonestore SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE phonestore;
use phonestore


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
    trangThai VARCHAR(50) CHECK (trangThai IN ('on', 'off')) NOT NULL,
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

-- Bảng Kho
CREATE TABLE KHO (
    maKho VARCHAR(50) NOT NULL,
    tenKho NVARCHAR(255) NOT NULL,
    diaChi NVARCHAR(255) NOT NULL,
    sdt VARCHAR(20) NOT NULL,
    trangThai NVARCHAR(50) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    PRIMARY KEY (maKho)
);
--DROP TABLE KHO;

-- Bảng Kho - Phiên Bản Sản Phẩm
CREATE TABLE KHO_PBSP (
    soLuong INT NOT NULL,
    maKho VARCHAR(50) NOT NULL,
    maPBSP VARCHAR(50) NOT NULL,

    PRIMARY KEY (maKho, maPBSP),
    CONSTRAINT FK_KHOPBSP_KHO FOREIGN KEY(maKho) REFERENCES KHO(maKho) ON DELETE NO ACTION,
    CONSTRAINT FK_KHOPBSP_PBSP FOREIGN KEY (maPBSP) REFERENCES PBSP(maPBSP) ON DELETE NO ACTION
);
--DROP TABLE KHO_PBSP;


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
    maCV VARCHAR(50) ,
    chiNhanh VARCHAR(50),

    PRIMARY KEY (maNV),
    CONSTRAINT FK_NHANVIEN_CHUCVU FOREIGN KEY (maCV) REFERENCES CHUCVU(maCV) ON DELETE NO ACTION,
    CONSTRAINT FK_NHANVIEN_KHO FOREIGN KEY (chiNhanh) REFERENCES KHO(maKho) ON DELETE NO ACTION
);
--DROP TABLE NHANVIEN;


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


-- Bảng Phiếu Nhập
CREATE TABLE PHIEUNHAP (
    maPN VARCHAR(50) NOT NULL,
    ngayTao DATE NOT NULL,
    tongTien FLOAT,
    trangThai NVARCHAR(50) CHECK (trangThai IN (N'Chờ xác nhận', N'Đã xác nhận', N'Đã nhận hàng')) NOT NULL,
    maNV VARCHAR(50) NOT NULL,
    maKho VARCHAR(50) NOT NULL,
    maNCC VARCHAR(50) NOT NULL,

	PRIMARY KEY(maPN),
    CONSTRAINT FK_PHIEUNHAP_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
	CONSTRAINT FK_PHIEUNHAP_KHO FOREIGN KEY(maKho) REFERENCES KHO(maKho) ON DELETE NO ACTION,
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
    maKho VARCHAR(50) NOT NULL,
    maKH VARCHAR(50) NOT NULL,
	CONSTRAINT FK_PHIEUXUAT_NHANVIEN FOREIGN KEY (maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
	CONSTRAINT FK_PHIEUXUAT_KHO FOREIGN KEY (maKho) REFERENCES KHO(maKho) ON DELETE NO ACTION,
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
<<<<<<< HEAD:ReplData/phone_store.sql
--DROP TABLE CTPX;
=======
DROP TABLE CTPX;


-- Bảng Nhà Cung Cấp
CREATE TABLE NHACUNGCAP (
    maNCC VARCHAR(50) NOT NULL,
    tenNCC NVARCHAR(255) NOT NULL,
    sdt NVARCHAR(20) NOT NULL,
    email NVARCHAR(255),
    diaChi NVARCHAR(255),
    trangThai NVARCHAR(50) CHECK (trangThai IN ('On', 'Off')) NOT NULL,
    PRIMARY KEY (maNCC)
);
DROP TABLE NHACUNGCAP;

-- Bảng Chức Vụ
CREATE TABLE CHUCVU (
    maCV VARCHAR(50) NOT NULL,
    tenCV NVARCHAR(255) NOT NULL,
    luongCB FLOAT NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,


    PRIMARY KEY (maCV)
);
DROP TABLE CHUCVU;

-- Bảng Kho
CREATE TABLE KHO (
    maKho VARCHAR(50) NOT NULL,
    tenKho NVARCHAR(255) NOT NULL,
    diaChi NVARCHAR(255) NOT NULL,
    sdt VARCHAR(20) NOT NULL,
    trangThai NVARCHAR(50) CHECK (trangThai IN ('On', 'Off')) NOT NULL,
    PRIMARY KEY (maKho)
);
DROP TABLE KHO;

-- Bảng Kho - Phiên Bản Sản Phẩm
CREATE TABLE KHO_PBSP (
    soLuong INT NOT NULL,
    maKho VARCHAR(50) NOT NULL,
    maPBSP VARCHAR(50) NOT NULL,

    PRIMARY KEY (maKho, maPBSP),
    CONSTRAINT FK_KHOPBSP_KHO FOREIGN KEY(maKho) REFERENCES KHO(maKho) ON DELETE NO ACTION,
    CONSTRAINT FK_KHOPBSP_PBSP FOREIGN KEY (maPBSP) REFERENCES PBSP(maPBSP) ON DELETE NO ACTION
);
DROP TABLE KHO_PBSP;

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
DROP TABLE KHACHHANG;


>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql


-- Bảng Đơn Xin Nghỉ
CREATE TABLE DONXINNGHI (
    maDon VARCHAR(50) NOT NULL,
    ngayTao DATE NOT NULL,
    ngayBD DATE NOT NULL,
    ngayKT DATE,
    lyDo NVARCHAR(MAX) NOT NULL,
    ngayDuyet DATE,
<<<<<<< HEAD:ReplData/phone_store.sql
    trangThai NVARCHAR(20) CHECK (trangThai IN (N'Chờ duyệt', N'Đã duyệt', N'Từ chối')) NOT NULL,
=======
    trangThai VARCHAR(20) CHECK (trangThai IN (N'Chờ Duyệt', N'Đã Duyệt', N'Từ chối')) NOT NULL,
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql
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
	maNV varchar(50),

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
	maNV varchar(50),

	PRIMARY KEY(maBCC),
	CONSTRAINT FK_BANGCHAMCONG_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
);
--DROP TABLE BANGCHAMCONG;

-- Bảng ghi chú
CREATE TABLE CHITIETCHAMCONG (
    maCTCC varchar(50) , 	-- = "CT" + thang + nam + manv 
    ngayTao DATE NOT NULL,
    loaiChamCong NVARCHAR(255) CHECK (loaiChamCong IN (N'Tăng ca ngày lễ', N'Tăng ca chủ nhật', N'Tăng ca ngày thường', 
	N'Nghỉ phép có lương', N'Nghỉ phép không lương ', N'Nghỉ không phép', N'Nghỉ nửa buổi', N'Nghỉ việc')) NOT NULL, 	-- Thiếu gì bổ sung thêm
<<<<<<< HEAD:ReplData/phone_store.sql
    chiTiet nvarchar(255),        -- Nếu nghỉ thì ghi lý do nghỉ
=======
    chiTiet varchar(255),        -- Nếu nghỉ thì ghi lý do nghỉ
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql
    maBCC VARCHAR(50),
    soGioOT float,
	
    PRIMARY KEY(maCTCC),
    CONSTRAINT FK_GHICHU_BANGCHAMCONG FOREIGN KEY (maBCC) REFERENCES BANGCHAMCONG(maBCC) ON DELETE NO ACTION
);
<<<<<<< HEAD:ReplData/phone_store.sql
--DROP TABLE ChiTietChamCong;
=======
DROP TABLE ChiTietChamCong;
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql


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
('PBSP001', N'Đen', '8GB', '128GB', 20000000.00, 10, 'on', 'SP001'),
('PBSP002', N'Trắng', '12GB', '256GB', 25000000.00, 8, 'on', 'SP002'),
('PBSP003', N'Xanh', '8GB', '128GB', 18000000.00, 15, 'on', 'SP003'),
('PBSP004', N'Vàng', '6GB', '64GB', 12000000.00, 12, 'on', 'SP004'),
('PBSP005', N'Tím', '8GB', '256GB', 22000000.00, 7, 'on', 'SP005');
INSERT INTO PBSP (maPBSP, mauSac, ram, rom, giaBan, soLuong, trangThai, maSP) 
VALUES
('PBSP006', N'Xám', '12GB', '512GB', 28000000.00, 5, 'on', 'SP002'),
('PBSP007', N'Xanh dương', '6GB', '128GB', 16000000.00, 9, 'on', 'SP003'),
('PBSP008', N'Hồng', '8GB', '256GB', 21000000.00, 6, 'on', 'SP004'),
('PBSP009', N'Bạc', '12GB', '1TB', 32000000.00, 4, 'on', 'SP002'),
('PBSP010', N'Đỏ', '8GB', '128GB', 19000000.00, 11, 'on', 'SP005'),
('PBSP011', N'Xanh lá', '6GB', '64GB', 14000000.00, 7, 'on', 'SP001');


-- Insert vào bảng NHACUNGCAP
INSERT INTO NHACUNGCAP (maNCC, tenNCC, sdt, email, diaChi, trangThai) VALUES
('NCC001', N'Công ty A', '0901234567', 'a@gmail.com', N'Hà Nội', 'on'),
('NCC002', N'Công ty B', '0912345678', 'b@gmail.com', N'Hồ Chí Minh', 'on'),
('NCC003', N'Công ty C', '0923456789', 'c@gmail.com', N'Đà Nẵng', 'on'),
('NCC004', N'Công ty D', '0934567890', 'd@gmail.com', N'Cần Thơ', 'on'),
('NCC005', N'Công ty E', '0945678901', 'e@gmail.com', N'Hải Phòng', 'on');

-- Insert bảng CHUCVU
INSERT INTO CHUCVU (maCV, tenCV, luongCB, trangThai) 
VALUES 
('CV001', N'Quản lý kho', 15000000, 'on'),
('CV002', N'Quản lý nhân sự', 8000000, 'on'),
('CV003', N'Nhân viên kho', 9000000, 'on'),
('CV004', N'Admin', 0, 'on');

-- Insert bảng KHO
INSERT INTO KHO (maKho, tenKho, diaChi, sdt, trangThai) 
VALUES 
('HCM', N'Kho Hồ Chí Minh', N'123 Lê Lợi, Quận 1, TP.HCM', '0901234567', 'on'),
('HN', N'Kho Hà Nội', N'456 Hai Bà Trưng, Quận Hoàn Kiếm, Hà Nội', '0912345678', 'on'),
('DN', N'Kho Đà Nẵng', N'789 Nguyễn Huệ, TP. Đà Nẵng', '0923456789', 'on');


-- Insert bảng NHANVIEN
INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh)
VALUES 
('NV001', N'Nguyễn Văn A', '1990-05-12', N'Nam', N'123 Lê Lợi, TP.HCM', '0901234567', 'nguyenvana@example.com', NULL, '123456', 'on', 'CV004', 'HCM'),
('NV002', N'Trần Thị B', '1995-08-22', N'Nữ', N'456 Hai Bà Trưng, Hà Nội', '0912345678', 'tranthib@example.com', NULL, 'abcdef', 'on', 'CV002', 'HCM'),
('NV003', N'Phạm Văn C', '1988-11-03', N'Nam', N'789 Nguyễn Huệ, Đà Nẵng', '0923456789', 'phamvanc@example.com', NULL, 'password', 'off', 'CV003', 'HCM'),
<<<<<<< HEAD:ReplData/phone_store.sql
('NV004', N'Lê Thị D', '1992-03-15', N'Nữ', N'321 Lạc Long Quân, Cần Thơ', '0934567890', 'lethid@example.com', NULL, 'letidpass', 'on', 'CV003', 'DN'),
('NV005', N'Hoàng Văn E', '1998-07-29', N'Nam', N'654 Trần Hưng Đạo, Hải Phòng', '0945678901', 'hoangvane@example.com', NULL, 'hoangepass', 'off', 'CV003', 'HN');
INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh) VALUES
('NV006', N'Nguyễn Thị F', '1996-02-14', N'Nữ', N'123 Nguyễn Trãi, TP.HCM', '0906789012', 'nguyenthif@example.com', NULL, 'passf123', 'on', 'CV003', 'HCM'),
('NV007', N'Võ Văn G', '1993-06-23', N'Nam', N'456 Lê Văn Sỹ, Hà Nội', '0917890123', 'vovang@example.com', NULL, 'passg456', 'on', 'CV003', 'HN'),
=======
('NV004', N'Lê Thị D', '1992-03-15', N'Nữ', N'321 Lạc Long Quân, Cần Thơ', '0934567890', 'lethid@example.com', NULL, 'letidpass', 'on', 'CV002', 'DN'),
('NV005', N'Hoàng Văn E', '1998-07-29', N'Nam', N'654 Trần Hưng Đạo, Hải Phòng', '0945678901', 'hoangvane@example.com', NULL, 'hoangepass', 'off', 'CV001', 'HN');
INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh) VALUES
('NV006', N'Nguyễn Thị F', '1996-02-14', N'Nữ', N'123 Nguyễn Trãi, TP.HCM', '0906789012', 'nguyenthif@example.com', NULL, 'passf123', 'on', 'CV002', 'HCM'),
('NV007', N'Võ Văn G', '1993-06-23', N'Nam', N'456 Lê Văn Sỹ, Hà Nội', '0917890123', 'vovang@example.com', NULL, 'passg456', 'on', 'CV002', 'HN'),
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql
('NV008', N'Bùi Thị H', '1999-09-10', N'Nữ', N'789 Cách Mạng Tháng 8, Đà Nẵng', '0928901234', 'buithih@example.com', NULL, 'passh789', 'on', 'CV002', 'DN'),
('NV009', N'Phan Văn I', '1990-12-05', N'Nam', N'321 Điện Biên Phủ, Cần Thơ', '0939012345', 'phanvani@example.com', NULL, 'passi101', 'on', 'CV003', 'HCM'),
('NV010', N'Lý Thị J', '1997-04-18', N'Nữ', N'654 Võ Văn Kiệt, Hải Phòng', '0940123456', 'lythij@example.com', NULL, 'passj202', 'on', 'CV002', 'HN'),
('NV011', N'Đặng Văn K', '1994-07-07', N'Nam', N'987 Phan Xích Long, TP.HCM', '0951234567', 'dangvank@example.com', NULL, 'passk303', 'on', 'CV003', 'DN'),
('NV012', N'Huỳnh Thị L', '1991-10-25', N'Nữ', N'246 Hoàng Hoa Thám, Hà Nội', '0962345678', 'huynhthil@example.com', NULL, 'passl404', 'on', 'CV003', 'HCM'),
('NV013', N'Tô Văn M', '1998-01-30', N'Nam', N'135 Nguyễn Thượng Hiền, Đà Nẵng', '0973456789', 'tovanm@example.com', NULL, 'passm505', 'on', 'CV003', 'HN'),
('NV014', N'Trịnh Thị N', '1995-05-20', N'Nữ', N'753 Lý Thường Kiệt, Cần Thơ', '0984567890', 'trinhthin@example.com', NULL, 'passn606', 'on', 'CV003', 'DN'),
('NV015', N'Ngô Văn O', '1992-11-11', N'Nam', N'852 Ba Tháng Hai, Hải Phòng', '0995678901', 'ngovano@example.com', NULL, 'passo707', 'on', 'CV003', 'HCM');


INSERT INTO KHACHHANG (maKH, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, trangThai)
VALUES
('KH001', N'Nguyễn Văn X', '1995-02-14', N'Nam', N'Hà Nội', '0967890123', 'x@gmail.com', 'on'),
('KH002', N'Trần Thị Y', '1998-06-10', N'Nữ', N'Đà Nẵng', '0978901234', 'y@gmail.com', 'off'),
('KH003', N'Phạm Văn Z', '1992-09-25', N'Nam', N'Hồ Chí Minh', '0989012345', 'z@gmail.com', 'on'),
('KH004', N'Lê Thị U', '1985-12-05', N'Nữ', N'Hải Phòng', '0990123456', 'u@gmail.com', 'off'),
('KH005', N'Hoàng Văn T', '2000-04-18', N'Nam', N'Bình Dương', '0901234568', 't@gmail.com', 'on');

-- Chèn dữ liệu vào bảng PHIEUNHAP
INSERT INTO PHIEUNHAP (maPN, ngayTao, tongTien, trangThai, maNV, maKho, maNCC) 
VALUES 
('PN001', '2024-03-01', 5000000, N'Đã nhận hàng', 'NV001', 'HCM', 'NCC001'),
('PN002', '2024-03-05', 3200000, N'Đã nhận hàng', 'NV002', 'HN', 'NCC002'),
('PN003', '2024-03-10', 4500000, N'Đã nhận hàng', 'NV003', 'HCM', 'NCC003'),
('PN004', '2024-03-15', 2800000, N'Đã xác nhận', 'NV004', 'DN', 'NCC001'),
('PN005', '2024-03-20', 6000000, N'Đã xác nhận', 'NV005', 'HN', 'NCC002');

-- Chèn dữ liệu vào bảng CTPN
INSERT INTO CTPN (soLuong, giaNhap, maPN, maPBSP) 
VALUES 
-- PN001 có 3 chi tiết
(10, 500000, 'PN001', 'PBSP001'),
(15, 520000, 'PN001', 'PBSP006'),
(8, 490000, 'PN001', 'PBSP007'),

-- PN002 có 3 chi tiết
(5, 640000, 'PN002', 'PBSP002'),
(12, 630000, 'PN002', 'PBSP008'),
(9, 650000, 'PN002', 'PBSP009'),

-- PN003 có 3 chi tiết
(8, 550000, 'PN003', 'PBSP003'),
(10, 530000, 'PN003', 'PBSP010'),
(6, 540000, 'PN003', 'PBSP011'),

-- PN004 có 3 chi tiết
(12, 230000, 'PN004', 'PBSP004'),
(14, 250000, 'PN004', 'PBSP005'),
(9, 240000, 'PN004', 'PBSP009'),

-- PN005 có 3 chi tiết
(7, 870000, 'PN005', 'PBSP005'),
(11, 860000, 'PN005', 'PBSP011'),
(13, 880000, 'PN005', 'PBSP010');

-- Chèn dữ liệu vào bảng PHIEUXUAT
INSERT INTO PHIEUXUAT (maPX, ngayTao, diaChi, tongTien, httt, trangThai, maNV, maKho, maKH) 
VALUES 
('PX001', '2024-03-01', N'12 Nguyễn Trãi, Hà Nội', 7500000, N'Tiền mặt', N'Đã xác nhận', 'NV001', 'HCM', 'KH001'),
('PX002', '2024-03-05', N'45 Lê Lợi, TP.HCM', 4200000, N'Chuyển khoản', N'Đã xác nhận', 'NV002', 'HN', 'KH002'),
('PX003', '2024-03-10', N'78 Trần Phú, Đà Nẵng', 5200000, N'Tiền mặt', N'Đã xác nhận', 'NV003', 'HCM', 'KH003'),
('PX004', '2024-03-15', N'90 Phạm Văn Đồng, Hải Phòng', 6100000, N'Tiền mặt', N'Đã xuất hàng', 'NV004', 'DN', 'KH004'),
('PX005', '2024-03-20', N'33 Võ Văn Kiệt, Cần Thơ', 8300000, N'Chuyển khoản', N'Đã xuất hàng', 'NV005', 'HN', 'KH005');

-- Chèn dữ liệu vào bảng CTPX
INSERT INTO CTPX (soLuong, giaXuat, maPX, maPBSP) 
VALUES 
(3, 2500000, 'PX001', 'PBSP001'),
(2, 2100000, 'PX002', 'PBSP002'),
(5, 1040000, 'PX003', 'PBSP003'),
(4, 1525000, 'PX004', 'PBSP004'),
(6, 1380000, 'PX005', 'PBSP005');




-- Chèn dữ liệu vào bảng BANGCHAMCONG
<<<<<<< HEAD:ReplData/phone_store.sql
INSERT INTO BANGCHAMCONG (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV)
VALUES
('CC032025NV001', 3, 2025, 22, 1, 1, 0, 4.5, 2, 1.5, 'NV001'),
('CC032025NV002', 3, 2025, 21, 0, 2, 1, 3, 1, 2, 'NV002'),
('CC032025NV003', 3, 2025, 20, 2, 1, 1, 5, 0, 1, 'NV003'),
('CC032025NV004', 3, 2025, 23, 0, 0, 0, 6, 3, 0, 'NV004'),
('CC032025NV005', 3, 2025, 19, 1, 1, 2, 2.5, 0, 1.5, 'NV005'),
('CC032025NV006', 3, 2025, 22, 0, 2, 0, 4, 1, 2, 'NV006'),
('CC032025NV007', 3, 2025, 20, 2, 0, 2, 3, 2, 0, 'NV007'),
('CC032025NV008', 3, 2025, 21, 0, 1, 1, 4, 1, 1, 'NV008'),
('CC032025NV009', 3, 2025, 18, 3, 0, 3, 2, 0, 2.5, 'NV009'),
('CC032025NV010', 3, 2025, 22, 0, 1, 0, 4.5, 2, 1, 'NV010');
=======
INSERT INTO BANGCHAMCONG (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKhongPhep, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV) VALUES
('BCC0325NV001', 3, 2024, 16, 2, 4, 0, 0, 3.0, 0, 'NV001'),
('BCC0325NV002', 3, 2024, 20, 1, 1, 0, 0, 0, 2.0, 'NV002'),
('BCC0325NV003', 3, 2024, 19, 1, 1, 1, 0, 0, 0, 'NV003'),
('BCC0325NV004', 3, 2024, 21, 0, 1, 0, 0, 0, 0, 'NV004'),
('BCC0325NV005', 3, 2024, 21, 1, 0, 0, 1.5, 0, 0, 'NV005');
INSERT INTO BANGCHAMCONG (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKhongPhep, soNPCoLuong, soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV) VALUES
('BCC0325NV006', 3, 2024, 18, 1, 2, 1, 0, 0, 0, 'NV006'),
('BCC0325NV007', 3, 2024, 20, 0, 3, 0, 0, 0, 0, 'NV007'),
('BCC0325NV008', 3, 2024, 7, 0, 0, 1, 0, 0, 0, 'NV008'),
('BCC0325NV009', 3, 2024, 19, 1, 2, 0, 2.0, 0, 0, 'NV009'),
('BCC0325NV010', 3, 2024, 17, 2, 1, 2, 0, 0, 0, 'NV010');

INSERT INTO ChiTietChamCong (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT) VALUES 
('CT032025NV001', '2025-03-01', N'Tăng ca ngày lễ', null, 'BCC0325NV001', 3.0),
('CT032025NV002', '2025-03-02', N'Tăng ca chủ nhật', null, 'BCC0325NV002', 2.0),
('CT032025NV003', '2025-03-03', N'Nghỉ phép có lương', N'Nghỉ ốm', 'BCC0325NV003', 0),
('CT032025NV004', '2025-03-04', N'Nghỉ không phép', null, 'BCC0325NV004', 0),
('CT032025NV005', '2025-03-05', N'Tăng ca ngày thường', null, 'BCC0325NV005', 1.5),
('CT032025NV006', '2025-03-06', N'Nghỉ nửa buổi', N'Nghỉ khám bệnh', 'BCC0325NV006', 4.0),
('CT032025NV007', '2025-03-07', N'Nghỉ phép không lương', N'Nghỉ việc cá nhân', 'BCC0325NV007', 5.0),
('CT032025NV008', '2025-03-08', N'Nghỉ việc', null, 'BCC0325NV008', 0),
('CT032025NV009', '2025-03-09', N'Tăng ca ngày thường', null, 'BCC0325NV009', 2.0),
('CT032025NV010', '2025-03-10', N'Nghỉ phép có lương', N'Nghỉ lễ', 'BCC0325NV010', 0);
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql

-- Chèn dữ liệu vào bảng CHITIETCHAMCONG
INSERT INTO CHITIETCHAMCONG (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
VALUES
('CT032025NV001', '2025-03-05', N'Tăng ca ngày thường', N'Tăng ca hỗ trợ kho', 'CC032025NV001', 2),
('CT032025NV002', '2025-03-08', N'Nghỉ phép có lương', N'Nghỉ đám cưới bạn thân', 'CC032025NV002', 0),
('CT032025NV003', '2025-03-10', N'Nghỉ không phép', N'Không báo trước', 'CC032025NV003', 0),
('CT032025NV004', '2025-03-12', N'Tăng ca ngày lễ', N'Trực ngày lễ Giỗ Tổ', 'CC032025NV004', 3),
('CT032025NV005', '2025-03-15', N'Nghỉ phép không lương ', N'Nghỉ chăm người nhà bệnh', 'CC032025NV005', 0),
('CT032025NV006', '2025-03-18', N'Tăng ca ngày thường', N'Tăng ca hỗ trợ xử lý đơn hàng', 'CC032025NV006', 2.5),
('CT032025NV007', '2025-03-21', N'Nghỉ việc', N'Nghỉ hẳn không đi làm', 'CC032025NV007', 0),
('CT032025NV008', '2025-03-23', N'Tăng ca chủ nhật', N'Trực hỗ trợ khách hàng', 'CC032025NV008', 2),
('CT032025NV009', '2025-03-25', N'Nghỉ nửa buổi', N'Về sớm lo việc gia đình', 'CC032025NV009', 0),
('CT032025NV010', '2025-03-28', N'Tăng ca ngày lễ', N'Trực ngày 8/3', 'CC032025NV010', 3.5);


-- Chèn dữ liệu vào bảng BANGLUONG
INSERT INTO BANGLUONG (maBL, thangLuong, namLuong, luongCB, heSo, phuCapAnTrua, phuCapDiLai, thuong, bhxh, bhyt, bhtn, thueTNCN, tamUng, thucNhan, maNV)
VALUES
('BL032025NV001', 3, 2025, 6000000, 1.2, 500000, 400000, 800000, 480000, 240000, 120000, 300000, 1000000, 7220000, 'NV001'),
('BL032025NV002', 3, 2025, 6200000, 1.1, 450000, 350000, 600000, 496000, 248000, 124000, 270000, 900000, 6986000, 'NV002'),
('BL032025NV003', 3, 2025, 5800000, 1.3, 400000, 300000, 500000, 452000, 226000, 113000, 250000, 800000, 6839000, 'NV003'),
('BL032025NV004', 3, 2025, 5900000, 1.25, 550000, 420000, 700000, 472000, 236000, 118000, 280000, 850000, 7172000, 'NV004'),
('BL032025NV005', 3, 2025, 6100000, 1.15, 500000, 380000, 650000, 488000, 244000, 122000, 260000, 920000, 7039600, 'NV005'),
('BL032025NV006', 3, 2025, 6000000, 1.2, 480000, 370000, 600000, 480000, 240000, 120000, 240000, 850000, 7030000, 'NV006'),
('BL032025NV007', 3, 2025, 5900000, 1.1, 450000, 360000, 550000, 472000, 236000, 118000, 230000, 780000, 6775200, 'NV007'),
('BL032025NV008', 3, 2025, 6200000, 1.3, 520000, 400000, 750000, 496000, 248000, 124000, 290000, 1000000, 7397200, 'NV008'),
('BL032025NV009', 3, 2025, 6100000, 1.2, 500000, 390000, 680000, 488000, 244000, 122000, 270000, 950000, 7128600, 'NV009'),
('BL032025NV010', 3, 2025, 6000000, 1.1, 480000, 350000, 620000, 480000, 240000, 120000, 250000, 870000, 6921000, 'NV010');

-- Chèn dữ liệu vào bảng LSCHINHSUA
INSERT INTO LSCHINHSUA (maNguoiChinhSua, maNguoiBiChinhSua, thoiGian, giaTriCu, giaTriMoi)
VALUES 
('NV002', 'NV005', '2025-04-01', N'Nhân viên bán hàng', N'Trưởng nhóm bán hàng'),
('NV008', 'NV006', '2025-04-03', N'Trưởng nhóm kỹ thuật', N'Quản lý kỹ thuật'),
('NV010', 'NV004', '2025-04-05', N'Nhân viên kho', N'Trưởng kho'),
('NV002', 'NV009', '2025-04-07', N'Nhân viên hỗ trợ', N'Trưởng nhóm hỗ trợ'),
('NV008', 'NV003', '2025-04-09', N'Nhân viên bảo trì', N'Trưởng phòng kỹ thuật');

-- Chèn dữ liệu vào bảng KHO_PBSP
INSERT INTO KHO_PBSP (soLuong, maKho, maPBSP) VALUES
(5, 'HCM', 'PBSP001'),
(3, 'HCM', 'PBSP002'),
(7, 'HCM', 'PBSP003'),
(4, 'HN', 'PBSP001'),
(5, 'HN', 'PBSP004'),
(6, 'HN', 'PBSP005'),
(8, 'DN', 'PBSP003'),
(2, 'DN', 'PBSP002'),
(9, 'DN', 'PBSP004'),
(10, 'DN', 'PBSP005');

<<<<<<< HEAD:ReplData/phone_store.sql
-- Chèn dữ liệu vào bảng DONXINNGHI
INSERT INTO DONXINNGHI (maDon, ngayTao, ngayBD, ngayKT, lyDo, ngayDuyet, trangThai, maNV, maNguoiDuyet)
VALUES
('DON001', '2025-03-01', '2025-03-05', '2025-03-06', N'Nghỉ đám cưới em trai', '2025-03-03', N'Đã Duyệt', 'NV001', 'NV002'),
('DON002', '2025-03-02', '2025-03-10', '2025-03-10', N'Nghỉ khám bệnh', '2025-03-04', N'Đã Duyệt', 'NV002', 'NV005'),
('DON003', '2025-03-04', '2025-03-12', NULL, N'Nghỉ việc cá nhân', NULL, N'Chờ Duyệt', 'NV003', NULL),
('DON004', '2025-03-05', '2025-03-15', '2025-03-16', N'Nghỉ chăm sóc người thân', '2025-03-06', N'Từ chối', 'NV004', 'NV008'),
('DON005', '2025-03-06', '2025-03-18', NULL, N'Nghỉ đi thi bằng lái xe', NULL, N'Chờ Duyệt', 'NV005', NULL),
('DON006', '2025-03-07', '2025-03-20', '2025-03-21', N'Nghỉ đám giỗ ông', '2025-03-08', N'Đã Duyệt', 'NV006', 'NV002'),
('DON007', '2025-03-08', '2025-03-22', NULL, N'Nghỉ phép định kỳ', NULL, N'Chờ Duyệt', 'NV007', NULL),
('DON008', '2025-03-09', '2025-03-23', '2025-03-23', N'Nghỉ đi hiến máu', '2025-03-10', N'Đã Duyệt', 'NV008', 'NV005'),
('DON009', '2025-03-10', '2025-03-24', '2025-03-25', N'Nghỉ du lịch gia đình', '2025-03-11', N'Từ chối', 'NV009', 'NV008'),
('DON010', '2025-03-11', '2025-03-26', NULL, N'Nghỉ không rõ lý do', NULL, N'Chờ Duyệt', 'NV010', NULL);
=======

INSERT INTO KHO_PBSP (soLuong, maKho, maPBSP) VALUES
(5, 'HCM', 'PBSP001'),
(3, 'HCM', 'PBSP002'),
(7, 'HCM', 'PBSP003'),
(4, 'HN', 'PBSP001'),
(5, 'HN', 'PBSP004'),
(6, 'HN', 'PBSP005'),
(8, 'DN', 'PBSP003'),
(2, 'DN', 'PBSP002'),
(9, 'DN', 'PBSP004'),
(10, 'DN', 'PBSP005');

INSERT INTO DONXINNGHI (maDon, ngayTao, ngayBD, ngayKT, lyDo, ngayDuyet, trangThai, maNV, maNguoiDuyet) VALUES
('DXN001', '2024-03-10', '2024-03-11', '2024-03-12', N'Nghỉ lễ', '2024-03-11', 'Đã Duyệt', 'NV008', 'NV003'), 
('DXN002', '2024-03-15', '2024-03-23', '2024-03-24', N'Nghỉ ốm', '2024-03-20', 'Đã Duyệt', 'NV007', 'NV003'), 
('DXN003', '2024-03-20', '2024-04-01', null, N'Nghỉ việc', '2024-03-25', 'Từ chối', 'NV008', 'NV003'), 
('DXN004', '2024-03-25', '2024-03-30', '2024-04-12', N'Nghỉ phép năm', '2024-03-26', 'Đã Duyệt', 'NV009', 'NV003'), 
('DXN005', '2024-03-28', '2024-03-30', '2024-03-30', N'Nghỉ khám bệnh', '2024-03-28', 'Đã Duyệt', 'NV010', 'NV003'); 
>>>>>>> a2e2c117794901ad63ae1bd9e387468ce0d4ef56:phone_store.sql


------------------------------------------ SELECT --------------------------------------
select * from nhanvien;
select * from sanpham;
select * from pbsp;
select * from phieunhap; 
select * from ctpn;
select * from phieuxuat;
select * from ctpx;
select * from chucvu;
select * from kho;
select * from kho_pbsp;
select * from nhacungcap;
select * from thuonghieu;
select * from khachhang;
select * from bangchamcong;
select * from bangluong;
select * from chitietchamcong;
select * from lschinhsua;
select * from donxinnghi;
SELECT @@VERSION;
------------------------------------------ DELETE --------------------------------------
DELETE FROM GHICHU;
DELETE FROM BANGCHAMCONG;
DELETE FROM CHITIETCHAMCONG;
DELETE FROM BANGLUONG;
DELETE FROM LSCHINHSUA;
DELETE FROM DONXINNGHI;
DELETE FROM KHO_PBSP;
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
DELETE FROM KHO;
DELETE FROM NHACUNGCAP;
DELETE FROM CHUCVU;

------------------------------------------ UPDATE --------------------------------------

update nhanvien set maCV='CV004' where manv='NV001'

------------------------------------------ STORED PROCEDURE --------------------------------------
-- 1. Lấy danh sách nhân viên
create procedure sp_layDanhSachNhanVien
as
begin
	select * from nhanvien where trangThai='on' or trangThai='On';
end

exec sp_layDanhSachNhanVien;
go
drop procedure sp_layDanhSachNhanVien;

-- . Lấy nhân viên theo ID
create procedure sp_layNhanVienTheoID
@maNV nvarchar(50)
as
begin
	select * from nhanvien where maNV = @maNV
end

exec sp_layNhanVienTheoID 'NV001'
go

-- . Lấy nhân viên theo tên
-- . Lấy tên nhân viên theo ID
-- . Thêm nhân viên
CREATE PROCEDURE sp_themNhanVien
    @maNV VARCHAR(50),
    @hoTen NVARCHAR(255),
    @ngaySinh DATE,
    @gioiTinh NVARCHAR(3),
    @diaChi NVARCHAR(255),
    @sdt NVARCHAR(20),
    @email NVARCHAR(255),
    @hinhAnh VARBINARY(MAX),
    @matKhau NVARCHAR(255),
    @trangThai VARCHAR(10),
    @maCV VARCHAR(50) = NULL, -- Có thể NULL
    @ChiNhanh VARCHAR(50) = NULL -- Có thể NULL
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra trùng mã nhân viên
    IF EXISTS (SELECT 1 FROM NHANVIEN WHERE maNV = @maNV)
    BEGIN
        PRINT N'Lỗi: Mã nhân viên đã tồn tại!';
        RETURN -1;
    END;

    -- Kiểm tra nếu chức vụ không NULL nhưng không tồn tại trong bảng CHUCVU
    IF @maCV IS NOT NULL AND NOT EXISTS (SELECT 1 FROM CHUCVU WHERE maCV = @maCV)
    BEGIN
        PRINT N'Lỗi: Mã chức vụ không hợp lệ!';
        RETURN -2;
    END;

    -- Kiểm tra nếu nơi làm việc không NULL nhưng không tồn tại trong bảng KHO
    IF @noiLamViec IS NOT NULL AND NOT EXISTS (SELECT 1 FROM KHO WHERE maKho = @noiLamViec)
    BEGIN
        PRINT N'Lỗi: Mã kho (nơi làm việc) không hợp lệ!';
        RETURN -3;
    END;

    -- Chèn dữ liệu vào bảng NHANVIEN
    INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, noiLamViec)
    VALUES (@maNV, @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email, @hinhAnh, @matKhau, @trangThai, @maCV, @noiLamViec);

    PRINT N'Thêm nhân viên thành công!';
    RETURN 1;
END;
EXEC sp_themNhanVien 
    @maNV = 'NV099', 
    @hoTen = N'Nguyễn Văn Q', 
    @ngaySinh = '1995-06-15', 
    @gioiTinh = N'Nam', 
    @diaChi = N'123 Đường ABC, Quận 1, TP.HCM', 
    @sdt = '0901234567', 
    @email = 'nguyenvana@example.com', 
    @hinhAnh = NULL, -- Nếu không có ảnh thì để NULL
    @matKhau = '123456', 
    @trangThai = 'On', 
    @maCV = 'CV002', 
    @chiNhanh = 'KHO001';

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