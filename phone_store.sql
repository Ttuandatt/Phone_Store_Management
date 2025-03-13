create database phonestore;
--USE master;
--ALTER DATABASE phonestore SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE phonestore;
use phonestore;



-- Bảng Thương Hiệu
CREATE TABLE THUONGHIEU (
    maTH VARCHAR(50) NOT NULL,
    tenTH NVARCHAR(255) NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    PRIMARY KEY (maTH)
);
DROP TABLE THUONGHIEU;
SELECT * FROM THUONGHIEU;

-- Bảng Sản Phẩm
CREATE TABLE SANPHAM (
    maSP VARCHAR(50) NOT NULL,
    tenSP NVARCHAR(255) NOT NULL,
    pin NVARCHAR(50) NOT NULL,
    OS NVARCHAR(50) NOT NULL,
    camTruoc NVARCHAR(50) NOT NULL,
    camSau NVARCHAR(50) NOT NULL,
    xuatXu NVARCHAR(100) NOT NULL,
    hinhAnh VARBINARY(MAX) NOT NULL, -- Chỉnh sửa để lưu ảnh dạng BLOB
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maTH VARCHAR(50) NOT NULL,
	
    PRIMARY KEY (maSP),
    CONSTRAINT FK_SANPHAM_THUONGHIEU FOREIGN KEY (maTH) REFERENCES THUONGHIEU(maTH) ON DELETE NO ACTION
);
drop table sanpham;

-- Bảng Phiên Bản Sản Phẩm
CREATE TABLE PBSP (
    maPBSP VARCHAR(50) NOT NULL,
    mauSac NVARCHAR(50) NOT NULL,
    ram NVARCHAR(50) NOT NULL,
    rom NVARCHAR(50) NOT NULL,
    giaBan DECIMAL(18,2) NOT NULL,
    soLuong INT NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maSP VARCHAR(50) NOT NULL,

    PRIMARY KEY (maPBSP),
    CONSTRAINT FK_PBSP_SANPHAM FOREIGN KEY (maSP) REFERENCES SANPHAM(maSP) ON DELETE NO ACTION
);
DROP TABLE PBSP;

-- Bảng Nhà Cung Cấp
CREATE TABLE NHACUNGCAP (
    maNCC VARCHAR(50) NOT NULL,
    tenNCC NVARCHAR(255) NOT NULL,
    sdt NVARCHAR(20) NOT NULL,
    email NVARCHAR(255),
    diaChi NVARCHAR(255),
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    PRIMARY KEY (maNCC)
);
DROP TABLE NHACUNGCAP;

-- Bảng Chức Vụ
CREATE TABLE CHUCVU (
    maCV VARCHAR(50) NOT NULL,
    tenCV NVARCHAR(255) NOT NULL,
    luongCB DECIMAL(18,2) NOT NULL,
    heSo FLOAT NOT NULL,
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
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,

    PRIMARY KEY (maKho)
);
DROP TABLE KHO;

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
    vaiTro NVARCHAR(50) NOT NULL,
    matKhau NVARCHAR(255) NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maCV VARCHAR(50) ,
    noiLamViec VARCHAR(50),

    PRIMARY KEY (maNV),
    CONSTRAINT FK_NHANVIEN_CHUCVU FOREIGN KEY (maCV) REFERENCES CHUCVU(maCV) ON DELETE NO ACTION,
    CONSTRAINT FK_NHANVIEN_KHO FOREIGN KEY (noiLamViec) REFERENCES KHO(maKho) ON DELETE NO ACTION
);
DROP TABLE NHANVIEN;

-- Bảng Phiếu Nhập
CREATE TABLE PHIEUNHAP (
    maPN VARCHAR(50) NOT NULL,
    ngayTao DATE NOT NULL,
    kho NVARCHAR(255),
    tongTien DECIMAL(18,2),
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
	maNV VARCHAR(50) NOT NULL,
	maNCC VARCHAR(50) NOT NULL,

	PRIMARY KEY(maPN),
    CONSTRAINT FK_PHIEUNHAP_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    CONSTRAINT FK_PHIEUNHAP_NHACUNGCAP FOREIGN KEY(maNCC) REFERENCES NHACUNGCAP(maNCC) ON DELETE NO ACTION
);
DROP TABLE PHIEUNHAP;

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
DROP TABLE CTPN;

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

-- Bảng Phiếu Xuất
CREATE TABLE PHIEUXUAT (
    maPX VARCHAR(50) PRIMARY KEY,
    ngayTao DATE NOT NULL,
    diaChi NVARCHAR(255),
    tongTien DECIMAL(18,2),
    httt NVARCHAR(50),
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
    maNV VARCHAR(50) NOT NULL,
	maKH VARCHAR(50) NOT NULL,
	CONSTRAINT FK_PHIEUXUAT_NHANVIEN FOREIGN KEY (maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
	CONSTRAINT FK_PHIEUXUAT_KHACHHANG FOREIGN KEY (maKH) REFERENCES KHACHHANG(maKH) ON DELETE NO ACTION
);
DROP TABLE PHIEUXUAT;

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
DROP TABLE CTPX;

-- Bảng Kho - Phiên Bản Sản Phẩm
CREATE TABLE KHO_PBSP (
    soLuong INT NOT NULL,
    maKho VARCHAR(50) NOT NULL,
    maPBSP VARCHAR(50) NOT NULL,
    ngayCapNhat DATE NOT NULL,

    PRIMARY KEY (maKho, maPBSP),
    CONSTRAINT FK_KHOPBSP_KHO FOREIGN KEY(maKho) REFERENCES KHO(maKho) ON DELETE NO ACTION,
    CONSTRAINT FK_KHOPBSP_PBSP FOREIGN KEY (maPBSP) REFERENCES PBSP(maPBSP) ON DELETE NO ACTION
);
DROP TABLE KHO_PBSP;

-- Bảng Đơn Yêu Cầu
CREATE TABLE DONYEUCAU (
    maDon VARCHAR(50) NOT NULL,
    tenDon NVARCHAR(255),
    ngayTao DATE NOT NULL,
    loaiDon NVARCHAR(50),
    chiTiet NVARCHAR(MAX),
    lyDo NVARCHAR(MAX),
	ngayDuyet DATE NOT NULL,
    trangThai VARCHAR(10) CHECK (trangThai IN ('on', 'off')) NOT NULL,
	maNV VARCHAR(50) NOT NULL,
	maNguoiDuyet VARCHAR(50) NOT NULL,

	PRIMARY KEY(maNV),
    CONSTRAINT FK_DONYEUCAU_NHANVIEN  FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,	-- khi xóa nhân viên này đi thì giá trị khóa ngoại ở đây sẽ set về null, nhưng vẫn giữ mã người duyệt bên dưới
    CONSTRAINT FK_DONYEUCAU_NGUOIDUYET FOREIGN KEY(maNguoiDuyet) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
);
DROP TABLE DONYEUCAU;

-- Bảng Lịch Sử Chỉnh Sửa
CREATE TABLE LSCHINHSUA (
    maLSCS VARCHAR(50) PRIMARY KEY,
    maNguoiChinhSua VARCHAR(50) FOREIGN KEY REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    maNguoiBiChinhSua VARCHAR(50) FOREIGN KEY REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION,
    thoiGian DATETIME NOT NULL,
    noiDungChinhSua NVARCHAR(MAX)
);
DROP TABLE LSCHINHSUA;


-- Bảng lương
CREATE TABLE BANGLUONG(
	maBL varchar(50),
	thangLuong int,
	namLuong int,
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
DROP TABLE BANGLUONG;

-- Bảng chấm công
CREATE TABLE BANGCHAMCONG(
	maBCC varchar(50),
	thangCC int,
	namCC int,
	soNgayLam float,
	soNgayNghiPhep float,
	soNgayNghiKhongPhep float,
	soGioOT float,
	maNV varchar(50),

	PRIMARY KEY(maBCC),
	CONSTRAINT FK_BANGCHAMCONG_NHANVIEN FOREIGN KEY(maNV) REFERENCES NHANVIEN(maNV) ON DELETE NO ACTION
);
DROP TABLE BANGCHAMCONG;

-- Bảng ghi chú
CREATE TABLE GHICHU (
    maGC INT PRIMARY KEY IDENTITY(1,1), -- Dùng IDENTITY thay cho AUTO_INCREMENT
    ngayTao DATE NOT NULL,
    noiDung NVARCHAR(1000),
    maBCC VARCHAR(50),
    
    CONSTRAINT FK_GHICHU_BANGCHAMCONG FOREIGN KEY (maBCC) REFERENCES BANGCHAMCONG(maBCC) ON DELETE NO ACTION
);
DROP TABLE GHICHU;

-------------------------------------------- ALTER TABLE -------------------------------------------------


-- Cập nhật thêm các trạng thái của nhân viên: đang làm, đã nghỉ, đang nghỉ phép, 
-- 1️. Tìm tên ràng buộc CHECK hiện tại trên cột trangThai
SELECT name 
FROM sys.check_constraints 
WHERE parent_object_id = OBJECT_ID('NHANVIEN');

-- 2️. Xóa ràng buộc CHECK cũ (thay 'CK_NHANVIEN_TrangThai' bằng tên thực tế nếu khác)
ALTER TABLE NHANVIEN DROP CONSTRAINT CK__NHANVIEN__trangT__619B8048;

-- 3️. Cập nhật dữ liệu cũ từ 'on' và 'off' sang trạng thái mới
UPDATE NHANVIEN SET trangThai = 'Đang làm' WHERE trangThai = 'on';
UPDATE NHANVIEN SET trangThai = 'Đã nghỉ' WHERE trangThai = 'off';

-- 4️. Thêm CHECK CONSTRAINT mới để chỉ cho phép 3 giá trị cụ thể
ALTER TABLE NHANVIEN  
ADD CONSTRAINT CK_NHANVIEN_TrangThai  
CHECK (trangThai IN ('Đang làm', 'Đã nghỉ', 'Đang nghỉ phép'));

-- 5️. Kiểm tra lại dữ liệu xem đã cập nhật đúng chưa
SELECT DISTINCT trangThai FROM NHANVIEN;

-- Đổi tên cột vaiTro của nhân viên thành cột chucVu
EXEC sp_rename 'NHANVIEN.vaiTro', 'chucVu', 'COLUMN';

------------------------------------------ INSERT --------------------------------------
-- Insert bảng CHUCVU
INSERT INTO CHUCVU (maCV, tenCV, luongCB, heSo, trangThai) 
VALUES 
('CV001', N'Quản lý kho', 15000000, 2.0, 'on'),
('CV002', N'Nhân viên kho', 8000000, 1.2, 'on'),
('CV003', N'Quản lý nhân sự', 9000000, 1.3, 'on');


-- Insert bảng KHO
INSERT INTO KHO (maKho, tenKho, diaChi, sdt, trangThai) 
VALUES 
('KHO001', N'Kho Hồ Chí Minh', N'123 Lê Lợi, Quận 1, TP.HCM', '0901234567', 'on'),
('KHO002', N'Kho Hà Nội', N'456 Hai Bà Trưng, Quận Hoàn Kiếm, Hà Nội', '0912345678', 'on'),
('KHO003', N'Kho Huế', N'789 Nguyễn Huệ, TP. Huế', '0923456789', 'off');


-- Insert bảng NHANVIEN
INSERT INTO NHANVIEN (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, vaiTro, matKhau, trangThai, maCV, noiLamViec)
VALUES 
('NV001', N'Nguyễn Văn A', '1990-05-12', N'Nam', N'123 Lê Lợi, TP.HCM', '0901234567', 'nguyenvana@example.com', NULL, 'CV001', '123456', 'on', 'CV001', 'KHO001'),
('NV002', N'Trần Thị B', '1995-08-22', N'Nữ', N'456 Hai Bà Trưng, Hà Nội', '0912345678', 'tranthib@example.com', NULL, 'CV002', 'abcdef', 'on', 'CV002', 'KHO002'),
('NV003', N'Phạm Văn C', '1988-11-03', N'Nam', N'789 Nguyễn Huệ, Đà Nẵng', '0923456789', 'phamvanc@example.com', NULL, 'CV001', 'password', 'off', 'CV003', 'KHO001'),
('NV004', N'Lê Thị D', '1992-03-15', N'Nữ', N'321 Lạc Long Quân, Cần Thơ', '0934567890', 'lethid@example.com', NULL, 'CV002', 'letidpass', 'on', 'CV002', 'KHO003'),
('NV005', N'Hoàng Văn E', '1998-07-29', N'Nam', N'654 Trần Hưng Đạo, Hải Phòng', '0945678901', 'hoangvane@example.com', NULL, 'CV002', 'hoangepass', 'off', 'CV001', 'KHO002');


------------------------------------------ SELECT --------------------------------------
select * from nhanvien;
select * from sanpham;
select * from pbsp;
select * from PHIEUNHAP;
select * from chucvu;
SELECT maCV FROM NHANVIEN;
select * from chucvu

------------------------------------------ DELETE --------------------------------------
DELETE FROM GHICHU;
DELETE FROM BANGCHAMCONG;
DELETE FROM BANGLUONG;
DELETE FROM LSCHINHSUA;
DELETE FROM DONYEUCAU;
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


