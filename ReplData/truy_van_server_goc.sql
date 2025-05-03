﻿/*Bao gồm
1. Quản lý nhân viên 
	1.1 Lấy danh sách nhân viên tất cả các kho - sp_GetAllNhanVien
	1.2 Lấy danh sách nhân viên của một kho - sp_GetNhanVienByKho
	1.3 Thêm nhân viên 
		1.3.1 Tạo mã nhân viên tự động tăng - sp_TaoMaNhanVien
		1.3.2 Thêm nhân viên - sp_ThemNhanVien
	1.4 Sửa nhân viên - sp_SuaNhanVien
	1.5 Tìm nhân viên - sp_GetNhanVien
2. Quản lý bảng chấm công
	2.1 Lấy danh sách bảng chấm công - sp_GetAllBangChamCong
	2.2 Lấy danh sách bảng chấm công của một kho - sp_GetAllBangChamCongByKho
	2.3 Thêm bảng chấm công	- sp_ThemBangChamCong
	2.4 Sửa bảng chấm công - sp_CapNhatBangChamCong
	2.4 Tìm bảng chấm công theo maCC - sp_GetChamCongTheoMaCC
3. Quản lý chi tiết chấm công
	3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCong
	3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCong
	3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCong
	3.4 Tìm kiếm chi tiết chấm công theo maCTCC - sp_GetChiTietChamCong
	3.5 Lấy danh sách chi tiết chấm công theo maCC - sp_GetChiTietChamCongTheoMaCC
4. Quản lý bảng lương
	4.1 Thêm bảng lương	- sp_ThemBangLuong
	4.2 Sửa bảng lương - sp_SuaBangLuong
	4.3 Lấy danh sách bảng lương theo maNV - sp_GetDSBangLuongByMaNV
	4.4 Tìm Bảng lương bằng maBL - sp_GetBangLuongByMaBL
	4.5 Lấy danh sách bảng lương của một kho - 
	4.6 Lấy danh sách bảng lương của các kho - 
5. Quản lý đơn xin nghỉ
	5.1 Thêm đơn xin nghỉ - sp_ThemBangLuong
	5.2 Sửa đơn xin nghỉ - sp_SuaBangLuong
	5.3 Xoá đơn xin nghỉ - sp_XoaBangLuong
	5.4 Lấy danh sách đơn theo maNV - sp_GetDSDonTheoMaNV
	5.5 Tìm đơn xin nghỉ bằng maDon - 
	5.6 Lấy danh sách đơn tất cả các kho
	5.7 Lấy danh sách đơn của một kho
*/

CREATE PROCEDURE sp_test
AS
BEGIN
    SET NOCOUNT ON;
    SELECT * FROM NHANVIEN where trangThai = 'on'  
END
DROP PROCEDURE sp_test


---------- 1. QUAN LY NHAN VIEN
----- 1.1 Lấy danh sách nhân viên tất cả các kho - sp_GetAllNhanVien
CREATE PROCEDURE sp_GetAllNhanVien
AS
BEGIN
    SET NOCOUNT ON;

    SELECT * FROM LINK1.phonestore.dbo.NHANVIEN where trangThai = 'on'
    UNION ALL
    SELECT * FROM LINK2.phonestore.dbo.NHANVIEN where trangThai = 'on'
    UNION ALL
    SELECT * FROM LINK3.phonestore.dbo.NHANVIEN where trangThai = 'on'
END

EXEC sp_GetAllNhanVien
DROP PROCEDURE sp_GetAllNhanVien
-- 1.2 Lấy danh sách nhân viên của một kho - sp_GetNhanVienByKho
CREATE PROCEDURE sp_GetNhanVienByKho
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        SELECT *, 'HN' AS chiNhanh FROM LINK1.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        SELECT *, 'DN' AS chiNhanh FROM LINK2.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        SELECT *, 'HCM' AS chiNhanh FROM LINK3.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

EXEC sp_GetNhanVienByKho 'HN'
----- 1.3 THEM NHAN VIEN
-- 1.3.1 Tao ma nhan vien tu dong tang
create procedure sp_TaoMaNhanVien
@mamoi varchar(10) OUTPUT
as
begin
	declare @sl1 INT = 0
    declare @sl2 INT = 0
    declare @sl3 INT = 0
    declare @tongSoLuong INT, @soThuTu INT

	select @sl1 = COUNT(*) FROM LINK1.PHONESTORE.dbo.NHANVIEN
    select @sl2 = COUNT(*) FROM LINK2.PHONESTORE.dbo.NHANVIEN
    select @sl3 = COUNT(*) FROM LINK3.PHONESTORE.dbo.NHANVIEN
	set @tongSoLuong = @sl1 + @sl2 + @sl3
	SET @soThuTu = @tongSoLuong + 1
	set @mamoi = 'NV' + RIGHT('000' + CONVERT(VARCHAR, @soThuTu), 3)
END

-- 1.3.2 Thêm nhân viên
CREATE PROCEDURE sp_ThemNhanVien
    @hoTen NVARCHAR(100),
    @ngaySinh DATE,
    @gioiTinh NVARCHAR(3),
    @diaChi NVARCHAR(200),
    @sdt NVARCHAR(20),
    @email NVARCHAR(255),
    @hinhAnh VARBINARY(MAX),
    @mk NVARCHAR(200),
    @maCV NVARCHAR(50),
    @maKho VARCHAR(10),
    @maNV VARCHAR(10) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;	-- Tránh gửi thông báo số dòng ảnh hưởng về client

    -- Tạo mã nhân viên tự động
    EXEC sp_TaoMaNhanVien @maNV OUTPUT;

    -- Kiểm tra mã nhân viên đã tồn tại ở bất kỳ phân mảnh nào
    IF EXISTS (
        SELECT 1 FROM LINK1.phonestore.dbo.NHANVIEN WHERE maNV = @maNV
        UNION ALL
        SELECT 1 FROM LINK2.phonestore.dbo.NHANVIEN WHERE maNV = @maNV
        UNION ALL
        SELECT 1 FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV
    )
    BEGIN
        PRINT N'Lỗi: Mã nhân viên đã tồn tại!';
        RETURN -1;
    END;

    -- Chèn vào phân mảnh phù hợp theo mã kho
    IF @maKho = 'HN'
    BEGIN
        INSERT INTO LINK1.phonestore.dbo.NHANVIEN
        (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh)
        VALUES (@maNV, @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email, @hinhAnh, @mk, 'on', @maCV, @maKho);
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        INSERT INTO LINK2.phonestore.dbo.NHANVIEN
        (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh)
        VALUES (@maNV, @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email, @hinhAnh, @mk, 'on', @maCV, @maKho);
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        INSERT INTO LINK3.phonestore.dbo.NHANVIEN
        (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh)
        VALUES (@maNV, @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email, @hinhAnh, @mk, 'on', @maCV, @maKho);
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ hoặc không xác định được phân mảnh.', 16, 1);
    END
END


drop procedure sp_ThemNhanVien
--\\\\\\\\\\\\\\\\\ TEST
DECLARE @maNhanVienMoi VARCHAR(10);

EXEC sp_ThemNhanVien
    @hoTen = N'Nguyễn Văn A',
    @ngaySinh = '1995-06-15',
    @gioiTinh = 'Nam',
    @diaChi = N'123 Trần Hưng Đạo, Hà Nội',
    @sdt = '0912345678',
    @email = 'vana@example.com',
    @hinhAnh = NULL,           -- Hoặc VARBINARY hợp lệ nếu có
    @mk = 'matkhau123',
    @maCV = 'CV001',
    @maKho = 'HN',
    @maNV = @maNhanVienMoi OUTPUT;

-- In kết quả để xem mã nhân viên đã tạo
SELECT @maNhanVienMoi AS MaNhanVienDuocTao;

----- 1.4 SUA NHAN VIEN
CREATE PROCEDURE sp_SuaNhanVien
    @maNV VARCHAR(10),
    @hoTen NVARCHAR(100),
    @ngaySinh DATE,
    @gioiTinh NVARCHAR(3),
    @diaChi NVARCHAR(200),
    @sdt NVARCHAR(20),
    @email NVARCHAR(255),
    @hinhAnh VARBINARY(MAX),
    @matKhau NVARCHAR(200),
    @trangThai NVARCHAR(10),
    @maCV NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    -- HN
    IF EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK1.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    -- DN
    IF EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK2.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    -- HCM
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK3.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    RAISERROR(N'Khong tim thay ma nhan vien.', 16, 1);
END
//////////// TEST
EXEC sp_SuaNhanVien
    @maNV = 'NV005',
    @hoTen = N'Lê Thị B',
    @ngaySinh = '1990-12-01',
    @gioiTinh = N'Nữ',  
    @diaChi = N'456 Nguyễn Huệ, Đà Nẵng',
    @sdt = '0987654321',
    @email = 'leb@example.com',
    @hinhAnh = NULL,  
    @matKhau = 'matkhau_moi123',
    @trangThai = 'on', 
    @maCV = 'CV002';


----- 1.5 TIM NHAN VIEN ---------- Tạo procedure ở server gốc rồi phân tán về các server phân mảnh (publication)
CREATE PROCEDURE sp_GetNhanVien
    @manv VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10);

    -- Tìm ở server hiện tại
    IF EXISTS (SELECT 1 FROM dbo.NHANVIEN WHERE maNV = @manv)
    BEGIN
        SELECT *, chiNhanh = (SELECT TOP 1 maKho FROM dbo.KHO)
        FROM dbo.NHANVIEN
        WHERE maNV = @manv;
    END
    ELSE 
    BEGIN
        -- Tìm trên server gốc
        IF EXISTS (SELECT 1 FROM LINK0.phonestore.dbo.NHANVIEN WHERE maNV = @manv)
        BEGIN
            -- Lấy chi nhánh
            SELECT @maKho = chiNhanh
            FROM LINK0.phonestore.dbo.NHANVIEN
            WHERE maNV = @manv;

            -- Lấy toàn bộ thông tin nhân viên
            SELECT *, chiNhanh = @maKho
            FROM LINK0.phonestore.dbo.NHANVIEN
            WHERE maNV = @manv;
        END
        ELSE
        BEGIN
            RAISERROR(N'Mã nhân viên không tồn tại', 16, 1);
        END
    END
END;

EXEC sp_GetNhanVien @manv = 'NV005'; -- Muốn thực thi trên server gốc thì phải union
---------- 2. QUAN LY BANG CHAM CONG
----- 2.1 Lay danh sach bang cham cong
CREATE PROCEDURE sp_GetAllBangChamCong
AS
BEGIN
    SET NOCOUNT ON;

    SELECT * FROM LINK1.phonestore.dbo.BANGCHAMCONG
    UNION ALL
    SELECT * FROM LINK2.phonestore.dbo.BANGCHAMCONG
    UNION ALL
    SELECT * FROM LINK3.phonestore.dbo.BANGCHAMCONG
END

----- 2.2 Lấy danh sách bảng chấm công của một kho - 
create procedure sp_GetAllBangChamCongByKho
    @maKho VARCHAR(10) -- Mã kho cần lấy danh sách chấm công
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @ServerLink NVARCHAR(100)

    -- Gán server tương ứng với mã kho
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Kho không hợp lệ', 16, 1)
        RETURN
    END

    -- Truy vấn danh sách chấm công từ kho tương ứng
    DECLARE @SQL NVARCHAR(MAX)

    SET @SQL = '
        SELECT *
        FROM [' + @ServerLink + '].phonestore.dbo.BANGCHAMCONG
        ORDER BY thangCC DESC, namCC DESC'

    -- Thực thi câu lệnh SQL động
    EXEC sp_executesql @SQL
END


----- 2.3 Them bang cham cong
CREATE PROCEDURE sp_ThemBangChamCong
    @maBCC VARCHAR(50),
    @thangCC INT,
    @namCC INT,
    @soNgayLam FLOAT,
    @soNgayNghiKP FLOAT,
    @soNPKCoLuong FLOAT,
    @soNPKKhongLuong FLOAT,
    @soGioOTNgayThuong FLOAT,
    @soGioOTNgayLe FLOAT,
    @soGioOTCN FLOAT,
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)

    -- 1. Tìm mã kho mà nhân viên thuộc về
    SELECT @maKho = n.chiNhanh
    FROM NHANVIEN n
    WHERE n.maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR('Không tìm thấy nhân viên', 16, 1)
        RETURN
    END

    -- 2. Gán server tương ứng với KHO_ID (tùy cấu hình hệ thống của bạn)
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Kho không hợp lệ', 16, 1)
        RETURN
    END

    -- 3. Kiểm tra trùng mã bảng chấm công (maBCC) trong kho hiện tại
    DECLARE @SQL NVARCHAR(MAX)

    SET @SQL = '
        IF EXISTS (SELECT 1 FROM [' + @ServerLink + '].phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC)
        BEGIN
            RAISERROR(''Mã bảng chấm công đã tồn tại trong kho'', 16, 1)
            RETURN
        END

        INSERT INTO [' + @ServerLink + '].phonestore.dbo.BANGCHAMCONG
        (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPKCoLuong, soNPKKhongLuong,
         soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV)
        VALUES (
            @maBCC, @thangCC, @namCC, @soNgayLam, @soNgayNghiKP, @soNPKCoLuong, @soNPKKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV)'

    EXEC sp_executesql @SQL,
        N'@maBCC VARCHAR(50), @thangCC INT, @namCC INT, @soNgayLam FLOAT, @soNgayNghiKP FLOAT,
          @soNPKCoLuong FLOAT, @soNPKKhongLuong FLOAT, @soGioOTNgayThuong FLOAT,
          @soGioOTNgayLe FLOAT, @soGioOTCN FLOAT, @maNV VARCHAR(50)',
        @maBCC, @thangCC, @namCC, @soNgayLam, @soNgayNghiKP,
        @soNPKCoLuong, @soNPKKhongLuong, @soGioOTNgayThuong,
        @soGioOTNgayLe, @soGioOTCN, @maNV
END

drop procedure sp_ThemBangChamCong
////////// TEST
EXEC sp_ThemBangChamCong
    @maBCC = 'BCC001',
    @thangCC = 5,
    @namCC = 2025,
    @soNgayLam = 22.5,
    @soNgayNghiKP = 2.0,
    @soNPKCoLuong = 1.0,
    @soNPKKhongLuong = 1.0,
    @soGioOTNgayThuong = 5.5,
    @soGioOTNgayLe = 3.0,
    @soGioOTCN = 8.0,
    @maNV = 'NV001';


----- 2.4 Sửa bảng chấm công
CREATE PROCEDURE sp_CapNhatBangChamCong
    @maBCC VARCHAR(50),
    @soNgayLam FLOAT,
    @soNgayNghiKP FLOAT,
    @soNPCoLuong FLOAT,
    @soNPKhongLuong FLOAT,
    @soGioOTNgayThuong FLOAT,
    @soGioOTNgayLe FLOAT,
    @soGioOTCN FLOAT,
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        UPDATE LINK1.phonestore.dbo.BANGCHAMCONG
        SET 
            soNgayLam = @soNgayLam,
            soNgayNghiKP = @soNgayNghiKP,
            soNPCoLuong = @soNPCoLuong,
            soNPKhongLuong = @soNPKhongLuong,
            soGioOTNgayThuong = @soGioOTNgayThuong,
            soGioOTNgayLe = @soGioOTNgayLe,
            soGioOTCN = @soGioOTCN
        WHERE maBCC = @maBCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        UPDATE LINK2.phonestore.dbo.BANGCHAMCONG
        SET 
            soNgayLam = @soNgayLam,
            soNgayNghiKP = @soNgayNghiKP,
            soNPCoLuong = @soNPCoLuong,
            soNPKhongLuong = @soNPKhongLuong,
            soGioOTNgayThuong = @soGioOTNgayThuong,
            soGioOTNgayLe = @soGioOTNgayLe,
            soGioOTCN = @soGioOTCN
        WHERE maBCC = @maBCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        UPDATE LINK3.phonestore.dbo.BANGCHAMCONG
        SET 
            soNgayLam = @soNgayLam,
            soNgayNghiKP = @soNgayNghiKP,
            soNPCoLuong = @soNPCoLuong,
            soNPKhongLuong = @soNPKhongLuong,
            soGioOTNgayThuong = @soGioOTNgayThuong,
            soGioOTNgayLe = @soGioOTNgayLe,
            soGioOTCN = @soGioOTCN
        WHERE maBCC = @maBCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
        RETURN -1
    END
END

----- 2.4 Tim kiem bang cham cong theo maCC
CREATE PROCEDURE sp_TimBangChamCong
    @maBCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maNV VARCHAR(50)
    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)
    DECLARE @SQL NVARCHAR(MAX)

    -- 1. Tìm mã nhân viên từ tất cả các phân mảnh
    IF EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC)
        SELECT @maNV = maNV FROM LINK1.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC
    ELSE IF EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC)
        SELECT @maNV = maNV FROM LINK2.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC
    ELSE IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC)
        SELECT @maNV = maNV FROM LINK3.phonestore.dbo.BANGCHAMCONG WHERE maBCC = @maBCC
    ELSE
    BEGIN
        RAISERROR(N'Không tìm thấy mã bảng chấm công.', 16, 1)
        RETURN
    END

    -- 2. Tìm kho của nhân viên
    SELECT @maKho = chiNhanh FROM NHANVIEN WHERE maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR(N'Không xác định được chi nhánh nhân viên.', 16, 1)
        RETURN
    END

    -- 3. Xác định server tương ứng
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
        RETURN
    END

    -- 4. Truy vấn dữ liệu bảng chấm công
    SET @SQL = '
        SELECT * 
        FROM [' + @ServerLink + '].phonestore.dbo.CHAMCONG 
        WHERE maBCC = @maBCC'

    EXEC sp_executesql @SQL, N'@maBCC VARCHAR(50)', @maBCC
END


----- 2.5 Lấy danh sách bảng chấm công theo maNV
create procedure sp_GETDSBangChamCongByMaNV
	@manv varchar(20)


---------- 3. QUAN LY CHI TIET CHAM CONG
----- Note: Kem theo trigger update bang cham cong khi them/xoa/sua ctcc
----- 3.1 Them chi tiet cham cong
CREATE PROCEDURE sp_ThemChiTietChamCong
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(50),
    @chiTiet NVARCHAR(255),
    @maBCC VARCHAR(50),
    @soGioOT FLOAT,
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra trùng mã chi tiết chấm công trong phân mảnh tương ứng
    IF (
        (@maKho = 'HN' AND EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.ChiTietChamCong WHERE maCTCC = @maCTCC)) OR
        (@maKho = 'DN' AND EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.ChiTietChamCong WHERE maCTCC = @maCTCC)) OR
        (@maKho = 'HCM' AND EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.ChiTietChamCong WHERE maCTCC = @maCTCC))
    )
    BEGIN
        PRINT N'Lỗi: Chi tiết chấm công đã tồn tại!';
        RETURN -1;
    END;

    -- Biến kiểm tra thao tác có thành công
    DECLARE @inserted BIT = 0;

    -- Chèn chi tiết chấm công vào phân mảnh phù hợp
    IF @maKho = 'HN'
    BEGIN
        INSERT INTO LINK1.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

        SET @inserted = 1
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        INSERT INTO LINK2.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

        SET @inserted = 1
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        INSERT INTO LINK3.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

        SET @inserted = 1
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
        RETURN -1
    END

    -- Gọi thủ tục cập nhật nếu thêm thành công
    IF @inserted = 1
    BEGIN
        PRINT ("Thêm chi tiết chấm công thành công")
    END
END

----- 3.2 Sua chi tiet cham cong
CREATE PROCEDURE sp_SuaChiTietChamCong
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(50),
    @chiTiet NVARCHAR(255),
    @soGioOT FLOAT,
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        UPDATE LINK1.phonestore.dbo.ChiTietChamCong
        SET 
            loaiChamCong = @loaiChamCong,
            chiTiet = @chiTiet,
            soGioOT = @soGioOT
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        UPDATE LINK2.phonestore.dbo.ChiTietChamCong
        SET 
            loaiChamCong = @loaiChamCong,
            chiTiet = @chiTiet,
            soGioOT = @soGioOT
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        UPDATE LINK3.phonestore.dbo.ChiTietChamCong
        SET 
            loaiChamCong = @loaiChamCong,
            chiTiet = @chiTiet,
            soGioOT = @soGioOT
        WHERE maCTCC = @maCTCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

----- 3.3 Xoa chi tiet cham cong
CREATE PROCEDURE sp_XoaChiTietChamCong
    @maCTCC VARCHAR(50),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        DELETE FROM LINK1.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        DELETE FROM LINK2.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        DELETE FROM LINK3.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END


----- 3.4 Tim kiem chi tiet cham cong theo maCTCC
CREATE PROCEDURE sp_GetChiTietChamCong
    @maCTCC VARCHAR(50),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        SELECT * FROM LINK1.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        SELECT * FROM LINK2.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        SELECT * FROM LINK3.phonestore.dbo.ChiTietChamCong
        WHERE maCTCC = @maCTCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

----- 3.5 Lay danh sach chi tiet cham cong theo maCC
CREATE PROCEDURE sp_GetChiTietChamCongTheoMaCC
    @maCC VARCHAR(20),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        SELECT * 
        FROM LINK1.phonestore.dbo.ChiTietChamCong
        WHERE maBCC = @maCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        SELECT * 
        FROM LINK2.phonestore.dbo.ChiTietChamCong
        WHERE maBCC = @maCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        SELECT * 
        FROM LINK3.phonestore.dbo.ChiTietChamCong
        WHERE maBCC = @maCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

EXEC sp_GetChiTietChamCongTheoMaCC @maCC = 'CC', @maKho = 'DN'

---------- 4. QUAN LY BANG LUONG
----- 4.1 Them bang luong
CREATE PROCEDURE sp_ThemBangLuong
    @maBL VARCHAR(50),
    @thangLuong INT,
    @namLuong INT,
    @luongCB FLOAT,
    @heSo FLOAT,
    @phuCapAnTrua FLOAT,
    @phuCapDiLai FLOAT,
    @thuong FLOAT,
    @bhxh FLOAT,
    @bhyt FLOAT,
    @bhtn FLOAT,
    @thueTNCN FLOAT,
    @tamUng FLOAT,
    @thucNhan FLOAT,
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)
    DECLARE @SQL NVARCHAR(MAX)

    -- 1. Lấy mã kho của nhân viên
    SELECT @maKho = chiNhanh
    FROM NHANVIEN
    WHERE maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR('Không tìm thấy nhân viên với mã đã cho.', 16, 1)
        RETURN
    END

    -- 2. Xác định linked server tương ứng
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Mã kho không hợp lệ.', 16, 1)
        RETURN
    END

    -- 3. Kiểm tra trùng mã bảng lương
    SET @SQL = '
        IF EXISTS (
            SELECT 1 FROM [' + @ServerLink + '].phonestore.dbo.BANGLUONG WHERE maBL = @maBL
        )
        BEGIN
            RAISERROR(''Mã bảng lương đã tồn tại.'', 16, 1)
            RETURN
        END
        ELSE
        BEGIN
            INSERT INTO [' + @ServerLink + '].phonestore.dbo.BANGLUONG
            (maBL, thangLuong, namLuong, luongCB, heSo, phuCapAnTrua, phuCapDiLai,
             thuong, bhxh, bhyt, bhtn, thueTNCN, tamUng, thucNhan, maNV)
            VALUES (
                @maBL, @thangLuong, @namLuong, @luongCB, @heSo, @phuCapAnTrua, @phuCapDiLai,
                @thuong, @bhxh, @bhyt, @bhtn, @thueTNCN, @tamUng, @thucNhan, @maNV)
        END
    '

    EXEC sp_executesql @SQL,
        N'@maBL VARCHAR(50), @thangLuong INT, @namLuong INT, @luongCB FLOAT, @heSo FLOAT,
          @phuCapAnTrua FLOAT, @phuCapDiLai FLOAT, @thuong FLOAT, @bhxh FLOAT,
          @bhyt FLOAT, @bhtn FLOAT, @thueTNCN FLOAT, @tamUng FLOAT, @thucNhan FLOAT,
          @maNV VARCHAR(50)',
        @maBL, @thangLuong, @namLuong, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong, @bhxh,
        @bhyt, @bhtn, @thueTNCN, @tamUng, @thucNhan,
        @maNV
END



----- 4.2 Sua bang luong
CREATE PROCEDURE sp_CapNhatBangLuong
    @maBL VARCHAR(50),
    @luongCB FLOAT,
    @heSo FLOAT,
    @phuCapAnTrua FLOAT,
    @phuCapDiLai FLOAT,
    @thuong FLOAT,
    @bhxh FLOAT,
    @bhyt FLOAT,
    @bhtn FLOAT,
    @thueTNCN FLOAT,
    @tamUng FLOAT,
    @thucNhan FLOAT,
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)

    -- 1. Lấy mã kho của nhân viên
    SELECT @maKho = chiNhanh
    FROM NHANVIEN
    WHERE maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR('Không tìm thấy nhân viên với mã đã cho.', 16, 1)
        RETURN
    END

    -- 2. Xác định tên liên kết server dựa vào kho
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Mã kho không hợp lệ.', 16, 1)
        RETURN
    END

    -- 3. Câu lệnh cập nhật động
    DECLARE @SQL NVARCHAR(MAX)

    SET @SQL = '
        UPDATE [' + @ServerLink + '].phonestore.dbo.BANGLUONG
        SET
            luongCB = @luongCB,
            heSo = @heSo,
            phuCapAnTrua = @phuCapAnTrua,
            phuCapDiLai = @phuCapDiLai,
            thuong = @thuong,
            bhxh = @bhxh,
            bhyt = @bhyt,
            bhtn = @bhtn,
            thueTNCN = @thueTNCN,
            tamUng = @tamUng,
            thucNhan = @thucNhan
        WHERE maBL = @maBL AND maNV = @maNV'

    EXEC sp_executesql @SQL,
        N'@maBL VARCHAR(50), @thangLuong INT, @namLuong INT, @luongCB FLOAT, @heSo FLOAT,
          @phuCapAnTrua FLOAT, @phuCapDiLai FLOAT, @thuong FLOAT, @bhxh FLOAT,
          @bhyt FLOAT, @bhtn FLOAT, @thueTNCN FLOAT, @tamUng FLOAT, @thucNhan FLOAT,
          @maNV VARCHAR(50)',
        @maBL, @thangLuong, @namLuong, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong, @bhxh,
        @bhyt, @bhtn, @thueTNCN, @tamUng, @thucNhan,
        @maNV
END

----- 4.3 Lấy danh sách bảng lương theo maNV 
CREATE PROCEDURE sp_GetDSBangLuongByMaNV
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)
    DECLARE @SQL NVARCHAR(MAX)

    -- 1. Tìm chi nhánh theo mã nhân viên
    SELECT @maKho = chiNhanh
    FROM NHANVIEN
    WHERE maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR('Không tìm thấy nhân viên.', 16, 1)
        RETURN
    END

    -- 2. Chọn server tương ứng
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Mã kho không hợp lệ.', 16, 1)
        RETURN
    END

    -- 3. Truy vấn dữ liệu từ server phân mảnh
    SET @SQL = 'SELECT * FROM [' + @ServerLink + '].phonestore.dbo.BANGLUONG
        WHERE maNV = @maNV'

    EXEC sp_executesql @SQL,
        N'@maNV VARCHAR(50)',
        @maNV
END


----- 4.4 Tim bang luong bang maBL
CREATE PROCEDURE sp_GetBangLuongByMaBL
    @maBL VARCHAR(50), @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @maKho VARCHAR(10)
    DECLARE @ServerLink NVARCHAR(100)
    DECLARE @SQL NVARCHAR(MAX)

    -- 1. Tìm chi nhánh theo mã nhân viên
    SELECT @maKho = chiNhanh
    FROM NHANVIEN
    WHERE maNV = @maNV

    IF @maKho IS NULL
    BEGIN
        RAISERROR('Không tìm thấy nhân viên.', 16, 1)
        RETURN
    END

    -- 2. Chọn server tương ứng
    IF @maKho = 'HN' SET @ServerLink = 'LINK1'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK2'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK3'
    ELSE
    BEGIN
        RAISERROR('Mã kho không hợp lệ.', 16, 1)
        RETURN
    END

    -- 3. Truy vấn dữ liệu từ server phân mảnh
    SET @SQL = 'SELECT * FROM [' + @ServerLink + '].phonestore.dbo.BANGLUONG
        WHERE maBL = @maBL and maNV = @maNV'

    EXEC sp_executesql @SQL,
        N'@maNV VARCHAR(50)',
        @maNV
END

----- 4.5 Lấy danh sách bảng lương của một kho 
create procedure sp_GetBangLuongByKho


----- 4.6 Lấy danh sách bảng lương các kho
create procedure sp_GetAllBangLuong


---------- 5. QUAN LY DON XIN NGHI
----- 5.1 Them xin nghi
create procedure sp_ThemBangLuong


----- 5.2 Sua don xin nghi
create procedure sp_SuaBangLuong


----- 5.3 Xoa don xin nghi
create procedure sp_XoaBangLuong


----- 5.4 Lấy danh sách đơn theo maNV - sp_GetDSDonTheoMaNV
create procedure sp_GetDSDonTheoMaNV	
	
----- 5.5 Tìm đơn xin nghỉ bằng maDon - sp_GetDSDonTheoMaNV
create procedure sp_GetDSDonTheoMaNV	
	
----- 5.6 Lấy danh sách đơn tất cả các kho - sp_GetDonByKho
create procedure sp_GetDonByKho	
	
----- 5.7 Lấy danh sách đơn của một kho - sp_GetAllDon
create procedure sp_GetAllDon




