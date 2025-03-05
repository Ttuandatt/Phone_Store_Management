create database phonestore;
use phonestore;

--############################################################ KHO #######################################################--
create table kho(
	makho int identity(1,1), -- Tự động tăng từ 1, mỗi lần tăng thêm 1.
	tenkho nvarchar(255) not null,
	diachi nvarchar(255) not null,
	sdt varchar(10) not null,
	trangthai varchar(10)not null check(trangthai in ('on','off')), -- Thay enum bằng CHECK

	primary key(makho)
);

INSERT INTO kho(tenkho, diachi, sdt, trangthai) VALUES
(N'Kho Hà Nội', N'123 Đường Hoàng Quốc Việt, Cầu Giấy, Hà Nội', '0987654321', 'on'),
(N'Kho TP.HCM', N'456 Đường Nguyễn Trãi, Quận 5, TP.HCM', '0978123456', 'on'),
(N'Kho Đà Nẵng', N'789 Đường Nguyễn Văn Linh, Hải Châu, Đà Nẵng', '0965123789', 'off'),
(N'Kho Hải Phòng', N'101 Đường Lê Hồng Phong, Ngô Quyền, Hải Phòng', '0956789012', 'on'),
(N'Kho Cần Thơ', N'202 Đường 3/2, Ninh Kiều, Cần Thơ', '0945678923', 'off');

drop table kho;
--#################################################################################################################################--




--################################################### CHỨC VỤ ######################################################################--
create table chucvu (
	macv varchar(10) not null,
	tencv nvarchar(50) not null,
	hesoluong float,
	luongcb float,
	trangthai varchar(3) check(trangthai in ('on', 'off')),

	primary key(macv)
);

INSERT INTO chucvu (macv, tencv, heSoLuong, luongCB, trangthai) VALUES
('AD', N'Admin', 3.0, 15000000, 'on'),
('QL', N'Quản lý', 2.5, 12000000, 'on'),
('QLNS', N'Quản lý nhân sự', 2.2, 10000000, 'on'),
('NV', N'Nhân viên', 1.5, 7000000, 'on');


drop table chucvu;
--##################################################################################################################################--



--################################################### NHÀ CUNG CẤP ######################################################################--
create table nhacungcap (
	mancc varchar(20) not null,
	tenncc nvarchar(255) not null,
	diachi nvarchar(255) not null,
	email nvarchar(50) not null, 
	trangthai varchar(3) check(trangthai in ('on', 'off')),

	primary key(mancc)
);

INSERT INTO nhacungcap (mancc, tenncc, diachi, email, trangthai) VALUES
('NCC01', N'Công ty TNHH Samsung Việt Nam', N'KCN Yên Phong, Bắc Ninh', 'contact@samsung.vn', 'on'),
('NCC02', N'Công ty TNHH Apple Việt Nam', N'72 Lê Thánh Tôn, Quận 1, TP.HCM', 'info@apple.vn', 'on'),
('NCC03', N'Công ty Oppo Việt Nam', N'15 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', 'support@oppo.vn', 'off'),
('NCC04', N'Công ty TNHH Xiaomi Việt Nam', N'KCN Thăng Long, Đông Anh, Hà Nội', 'xiaomi.vn@gmail.com', 'on'),
('NCC05', N'Công ty TNHH Sony Việt Nam', N'23 Nguyễn Huệ, Quận 1, TP.HCM', 'sony.vn@mail.com', 'on'),
('NCC06', N'Công ty TNHH Realme Việt Nam', N'45 Lê Văn Lương, Thanh Xuân, Hà Nội', 'realme.vn@gmail.com', 'off'),
('NCC07', N'Công ty TNHH Vivo Việt Nam', N'KCN Quang Minh, Mê Linh, Hà Nội', 'contact@vivo.vn', 'on');

drop table nhacungcap;
--##################################################################################################################################--




--################################################### THƯƠNG HIỆU ##################################################################--
create table thuonghieu(
	math varchar(10) not null,
	tenth nvarchar(50) not null,
	trangthai varchar(3) check(trangthai in('on', 'off')),

	primary key(math)
);

INSERT INTO thuonghieu (math, tenth, trangthai) VALUES
('TH01', N'Apple', 'on'),
('TH02', N'Samsung', 'on'),
('TH03', N'Xiaomi', 'on'),
('TH04', N'Oppo', 'on'),
('TH05', N'Vivo', 'on'),
('TH06', N'Realme', 'on'),
('TH07', N'Nokia', 'on'),
('TH08', N'Sony', 'off');

drop table thuonghieu;

--##################################################################################################################################--




--################################################### PHIẾU XUẤT ###################################################################--
create table phieuxuat(
	mapx varchar(15) not null,
	ngaytao date,
	diachi nvarchar(255) not null,
	tongtien float,
	httt nvarchar(20),
	trangthai varchar(3) check(trangthai in('on', 'off')),

	primary key(mapx)
)

INSERT INTO phieuxuat (mapx, ngaytao, diachi, tongtien, httt, trangthai) VALUES
('PX001', '2024-03-01', N'12 Nguyễn Trãi, Hà Nội', 15000000, 'Tiền mặt', 'on'),
('PX002', '2024-03-02', N'25 Lê Lợi, TP.HCM', 22000000, 'Chuyển khoản', 'on'),
('PX003', '2024-03-03', N'45 Trần Hưng Đạo, Đà Nẵng', 18000000, 'Tiền mặt', 'off'),
('PX004', '2024-03-04', N'78 Lạc Long Quân, Hà Nội', 25000000, 'Chuyển khoản', 'on'),
('PX005', '2024-03-05', N'90 Pasteur, TP.HCM', 12000000, 'Tiền mặt', 'off');

drop table phieuxuat;
--##################################################################################################################################--


--################################################### ALTER TABLE ##################################################################--
--##################################################################################################################################--


--################################################### QUERY ########################################################################--
select * from kho;
select * from chucvu;
select * from nhacungcap;
select * from thuonghieu;
--##################################################################################################################################--
