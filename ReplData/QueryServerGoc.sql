/*Bao gồm
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
	2.3 Lấy danh sách bảng chấm công theo thời gian - sp_GetAllBangChamCongByTime
	2.4 Thêm bảng chấm công	- sp_ThemBangChamCong
	2.5 Sửa bảng chấm công - sp_CapNhatBangChamCong
	2.5 Tìm bảng chấm công theo maCC - sp_GetChamCongTheoMaCC
	2.6 Tìm danh sách bảng chấm công theo từ khoá (maBCC, maNV, tenNV) - sp_TimDanhSachBangChamCong----------------
3. Quản lý chi tiết chấm công
	3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCong
	3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCong
	3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCong
	3.4 Xoá danh sách chi tiết chấm công theo mã bảng chấm công - sp_XoaChiTietChamCongTheoBCC
	3.5 Tìm kiếm chi tiết chấm công theo maCTCC - sp_GetChiTietChamCongTheoMaCT
	3.6 Lấy danh sách chi tiết chấm công theo maCC - sp_GetChiTietChamCongTheoMaCC
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

--- Tìm nhân viên từ mã nhân viên
CREATE PROCEDURE sp_GetNhanVienByMaNV
    @maNV VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Tìm ở phân mảnh HN
    IF EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'HN' AS chiNhanh FROM LINK1.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Tìm ở phân mảnh DN
    IF EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'DN' AS chiNhanh FROM LINK2.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Tìm ở phân mảnh HCM
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'HCM' AS chiNhanh FROM LINK3.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Nếu không tìm thấy
    RAISERROR(N'Không tìm thấy nhân viên với mã đã cho.', 16, 1);
END


/*----- 1.5 TIM NHAN VIEN ---------- Tạo procedure ở server gốc rồi phân tán về các server phân mảnh (publication)
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
END;*/

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

----- 2.3 Lấy bảng chấm công theo thời gian 'mm/yyyy'
CREATE PROCEDURE sp_GetAllBangChamCongByTime
    @thang INT,
    @nam INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Bảng tạm lưu kết quả hợp nhất từ các phân mảnh
    CREATE TABLE #BangChamCong_Temp (
        maBCC VARCHAR(50),
        thangCC INT,
        namCC INT,
        soNgayLam FLOAT,
        soNgayNghiKP FLOAT,
        soNPKCoLuong FLOAT,
        soNPKKhongLuong FLOAT,
        soGioOTNgayThuong FLOAT,
        soGioOTNgayLe FLOAT,
        soGioOTCN FLOAT,
        maNV VARCHAR(50)
    );

    BEGIN TRY
        -- Phân mảnh HN
        INSERT INTO #BangChamCong_Temp
        SELECT maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong,
               soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV
        FROM LINK1.phonestore.dbo.BangChamCong
        WHERE thangCC = @thang AND namCC = @nam;
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi từ phân mảnh HN: ' + ERROR_MESSAGE();
    END CATCH;

    BEGIN TRY
        -- Phân mảnh DN
        INSERT INTO #BangChamCong_Temp
        SELECT maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong,
               soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV
        FROM LINK2.phonestore.dbo.BangChamCong
        WHERE thangCC = @thang AND namCC = @nam;
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi từ phân mảnh DN: ' + ERROR_MESSAGE();
    END CATCH;

    BEGIN TRY
        -- Phân mảnh HCM
        INSERT INTO #BangChamCong_Temp
        SELECT maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong,
               soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV
        FROM LINK3.phonestore.dbo.BangChamCong
        WHERE thangCC = @thang AND namCC = @nam;
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi từ phân mảnh HCM: ' + ERROR_MESSAGE();
    END CATCH;

    -- Trả kết quả
    SELECT * FROM #BangChamCong_Temp;

    -- Dọn dẹp bảng tạm
    DROP TABLE #BangChamCong_Temp;
END;

EXEC sp_GetAllBangChamCongByTime @thang = 5, @nam = 2025;


----- 2.3 Them bang cham cong
CREATE PROCEDURE sp_ThemBangChamCong
    @maBCC VARCHAR(50),
    @thangCC INT,
    @namCC INT,
    @soNgayLam FLOAT,
    @soNgayNghiKP FLOAT,
    @soNPCoLuong FLOAT,
    @soNPKhongLuong FLOAT,
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
        (maBCC, thangCC, namCC, soNgayLam, soNgayNghiKP, soNPCoLuong, soNPKhongLuong,
         soGioOTNgayThuong, soGioOTNgayLe, soGioOTCN, maNV)
        VALUES (
            @maBCC, @thangCC, @namCC, @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV)'

    EXEC sp_executesql @SQL,
        N'@maBCC VARCHAR(50), @thangCC INT, @namCC INT, @soNgayLam FLOAT, @soNgayNghiKP FLOAT,
          @soNPCoLuong FLOAT, @soNPKhongLuong FLOAT, @soGioOTNgayThuong FLOAT,
          @soGioOTNgayLe FLOAT, @soGioOTCN FLOAT, @maNV VARCHAR(50)',
        @maBCC, @thangCC, @namCC, @soNgayLam, @soNgayNghiKP,
        @soNPCoLuong, @soNPKhongLuong, @soGioOTNgayThuong,
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
    @soNPCoLuong = 1.0,
    @soNPKhongLuong = 1.0,
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
--/////////////////// TEST
EXEC sp_CapNhatBangChamCong
    @maBCC = 'BCC20250501',
    @soNgayLam = 22.0,
    @soNgayNghiKP = 1.0,
    @soNPCoLuong = 2.0,
    @soNPKhongLuong = 0.0,
    @soGioOTNgayThuong = 5.5,
    @soGioOTNgayLe = 2.0,
    @soGioOTCN = 3.0,
    @maKho = 'HN'; 


----- 2.4 Tim kiem bang cham cong theo maCC

CREATE PROCEDURE sp_GetChamCongTheoMaCC
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
    SET @SQL = 'SELECT * 
        FROM [' + @ServerLink + '].phonestore.dbo.CHAMCONG 
        WHERE maBCC = @maBCC'

    EXEC sp_executesql @SQL, N'@maBCC VARCHAR(50)', @maBCC
END


----- 2.5 Lấy danh sách bảng chấm công theo maNV
create procedure sp_GETDSBangChamCongByMaNV
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    -- Hà Nội
    IF EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'HN' AS maKho, *
        FROM LINK1.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Đà Nẵng
    IF EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'DN' AS maKho, *
        FROM LINK2.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Hồ Chí Minh
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'HCM' AS maKho, *
        FROM LINK3.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Không tìm thấy nhân viên
    RAISERROR(N'Không tìm thấy bảng chấm công của nhân viên có mã %s.', 16, 1, @maNV);
END

EXEC sp_GETDSBangChamCongByMaNV @maNV = 'NV005';

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

    -- Chèn chi tiết chấm công vào phân mảnh phù hợp
    IF @maKho = 'HN'
    BEGIN
        INSERT INTO LINK1.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

    END
    ELSE IF @maKho = 'DN'
    BEGIN
        INSERT INTO LINK2.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        INSERT INTO LINK3.phonestore.dbo.ChiTietChamCong
        (maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT)
        VALUES (@maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT)

    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
        RETURN -1
    END
END

EXEC sp_ThemChiTietChamCong
    @maCTCC = 'CTCC001',
    @ngayTao = '2025-05-02',
    @loaiChamCong = N'Tăng ca ngày thường',
    @chiTiet = null,
    @maBCC = '',
    @soGioOT = 2.5,
    @maKho = 'HN';

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


------ Xoá danh sách chi tiết chấm công theo mabcc
CREATE PROCEDURE sp_XoaChiTietChamCongTheoBCC
    @maBCC VARCHAR(50),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        IF @maKho = 'HN'
        BEGIN
            DELETE FROM LINK1.phonestore.dbo.ChiTietChamCong
            WHERE maBCC = @maBCC;
        END
        ELSE IF @maKho = 'DN'
        BEGIN
            DELETE FROM LINK2.phonestore.dbo.ChiTietChamCong
            WHERE maBCC = @maBCC;
        END
        ELSE IF @maKho = 'HCM'
        BEGIN
            DELETE FROM LINK3.phonestore.dbo.ChiTietChamCong
            WHERE maBCC = @maBCC;
        END
        ELSE
        BEGIN
            RAISERROR(N'Mã kho không hợp lệ.', 16, 1);
            RETURN -1;
        END
    END TRY
    BEGIN CATCH
        DECLARE @errMsg NVARCHAR(4000), @errSeverity INT, @errState INT;
        SELECT @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY(), @errState = ERROR_STATE();
        RAISERROR(@errMsg, @errSeverity, @errState);
        RETURN -1;
    END CATCH
END

EXEC sp_XoaChiTietChamCongTheoBCC @maBCC = 'CC01052025NV001', @maKho = 'DN';


------ Lấy chi tiết chấm công theo mactcc
CREATE PROCEDURE sp_GetChiTietChamCongTheoMaCT
    @maCTCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        IF EXISTS (
            SELECT 1 FROM LINK1.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC
        )
        BEGIN
            SELECT * FROM LINK1.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC;
            RETURN;
        END
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi khi truy cập LINK1: ' + ERROR_MESSAGE();
    END CATCH

    BEGIN TRY
        IF EXISTS (
            SELECT 1 FROM LINK2.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC
        )
        BEGIN
            SELECT * FROM LINK2.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC;
            RETURN;
        END
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi khi truy cập LINK2: ' + ERROR_MESSAGE();
    END CATCH

    BEGIN TRY
        IF EXISTS (
            SELECT 1 FROM LINK3.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC
        )
        BEGIN
            SELECT * FROM LINK3.phonestore.dbo.ChiTietChamCong
            WHERE maCTCC = @maCTCC;
            RETURN;
        END
    END TRY
    BEGIN CATCH
        PRINT N'Lỗi khi truy cập LINK3: ' + ERROR_MESSAGE();
    END CATCH

    -- Không tìm thấy
    RAISERROR(N'Không tìm thấy mã chi tiết chấm công trong hệ thống.', 16, 1);
END

drop procedure sp_GetChiTietChamCongTheoMaCT
EXEC sp_GetChiTietChamCongTheoMaCT @maCTCC = 'CT08032025NV002'

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

drop procedure sp_GetChiTietChamCongTheoMaCC
EXEC sp_GetChiTietChamCongTheoMaCC @maCC = 'CC032025NV002', @maKho = 'HCM'

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
        N'@maBL VARCHAR(50), @luongCB FLOAT, @heSo FLOAT,
          @phuCapAnTrua FLOAT, @phuCapDiLai FLOAT, @thuong FLOAT, @bhxh FLOAT,
          @bhyt FLOAT, @bhtn FLOAT, @thueTNCN FLOAT, @tamUng FLOAT, @thucNhan FLOAT,
          @maNV VARCHAR(50)',
        @maBL, @luongCB, @heSo,
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
CREATE PROCEDURE sp_GetBangLuongByKho (
    @maKho VARCHAR(50)
)
AS
BEGIN
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @ServerLink NVARCHAR(50);

    IF @maKho = 'HN' SET @ServerLink = 'LINK_HN'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK_DN'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK_HCM'
    ELSE
    BEGIN
        -- Nếu mã kho không hợp lệ, trả về lỗi
        RAISERROR ('Mã kho không hợp lệ. Vui lòng chọn HN, DN, hoặc HCM.', 16, 1);
        RETURN;
    END

    SET @sqlCommand = 'SELECT bl.*, nv.chiNhanh  
                       FROM ' + @ServerLink + '.phonestore.dbo.BANGLUONG bl
                       JOIN ' + @ServerLink + '.phonestore.dbo.NHANVIEN nv ON bl.maNV = nv.maNV
                       WHERE nv.chiNhanh = @maKho';

    EXEC sp_executesql @sqlCommand, N'@maKho VARCHAR(50)', @maKho = @maKho;
END;
GO

EXEC sp_GetBangLuongByKho 'HCM';

----- 4.6 Lấy danh sách bảng lương các kho
CREATE PROCEDURE sp_GetAllBangLuong
AS
BEGIN
    SELECT 
        bl.*,
        nv.chiNhanh
    FROM LINK1.phonestore.dbo.BANGLUONG AS bl
    JOIN LINK1.phonestore.dbo.NHANVIEN AS nv ON bl.maNV = nv.maNV

    UNION ALL

    SELECT 
        bl.*,
        nv.chiNhanh
    FROM LINK2.phonestore.dbo.BANGLUONG AS bl
    JOIN LINK2.phonestore.dbo.NHANVIEN AS nv ON bl.maNV = nv.maNV

    UNION ALL

    SELECT 
        bl.*,
        nv.chiNhanh
    FROM LINK3.phonestore.dbo.BANGLUONG AS bl
    JOIN LINK3.phonestore.dbo.NHANVIEN AS nv ON bl.maNV = nv.maNV;
END;
GO

exec sp_GetAllBangLuong

---------- 5. QUAN LY DON XIN NGHI
----- 5.1 Them xin nghi
CREATE PROCEDURE sp_ThemDonXinNghi (
    @maNV VARCHAR(50),
    @ngayBD DATE,
    @ngayKT DATE,
    @lyDo NVARCHAR(MAX)
)
AS
BEGIN
    DECLARE @chiNhanh VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @serverLink VARCHAR(50);
    DECLARE @maDon VARCHAR(50);
    DECLARE @maxMaDonNumber INT;

    -- Xác định chi nhánh dựa vào bảng nhân viên
    SELECT @chiNhanh = ChiNhanh FROM LINK1.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
    IF @chiNhanh IS NULL
    BEGIN
        SELECT @chiNhanh = ChiNhanh FROM LINK2.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
        IF @chiNhanh IS NULL
        BEGIN
            SELECT @chiNhanh = ChiNhanh FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
            IF @chiNhanh IS NULL
            BEGIN
                -- Nếu không tìm thấy nhân viên, trả về lỗi
                RAISERROR ('Không tìm thấy nhân viên có mã %s.', 16, 1, @maNV);
                RETURN;
            END
        END
    END

    -- Kiểm tra ngày bắt đầu và ngày kết thúc
    IF @ngayBD >= @ngayKT
    BEGIN
        -- Nếu ngày bắt đầu không nhỏ hơn ngày kết thúc, trả về lỗi
        RAISERROR ('Ngày bắt đầu phải nhỏ hơn ngày kết thúc.', 16, 1);
        RETURN;
    END

    IF @chiNhanh = 'HN'
        SET @serverLink = 'LINK1';
    ELSE IF @chiNhanh = 'DN'
        SET @serverLink = 'LINK2';
    ELSE IF @chiNhanh = 'HCM'
        SET @serverLink = 'LINK3';

    -- Lấy max(maDon) từ bảng DONXINNGHI trên server tương ứng
    SET @sqlCommand = 'SELECT ISNULL(MAX(CAST(SUBSTRING(maDon, 4, LEN(maDon)) AS INT)), 0) FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon LIKE ''DON%''';
    EXEC sp_executesql @sqlCommand, N'@maxMaDonNumber INT OUTPUT', @maxMaDonNumber = @maxMaDonNumber OUTPUT;
    
    SET @maDon = 'DON' + FORMAT(@maxMaDonNumber + 1, '000');
    
    SET @sqlCommand = 'INSERT INTO ' + @serverLink + '.phonestore.dbo.DONXINNGHI (maDon, ngayBD, ngayKT, lyDo, trangThai, maNV, ngayTao) ' +
                       'VALUES (@maDon, @ngayBD, @ngayKT, @lyDo, N''Chờ Duyệt'', @maNV, GETDATE())';
    
    EXEC sp_executesql @sqlCommand, N'@maDon VARCHAR(50), @ngayBD DATE, @ngayKT DATE, @lyDo NVARCHAR(MAX), @maNV VARCHAR(50)',
                    @maDon, @ngayBD, @ngayKT, @lyDo, @maNV;

    SELECT @maDon AS maDonMoi;
    PRINT 'Đơn xin nghỉ đã được thêm thành công với mã đơn: ' + @maDon;
END;
GO

-- DROP PROCEDURE sp_ThemDonXinNghi

--exec sp_ThemDonXinNghi @maNV = 'NV001',@ngayBD = '2025-05-02',@lyDo = 'ABCTEST'

----- 5.2 Sua don xin nghi
CREATE PROCEDURE sp_SuaDonXinNghi (
    @maDon VARCHAR(50),
    @ngayBD DATE = NULL,
    @ngayKT DATE = NULL,
    @lyDo NVARCHAR(MAX) = NULL
)
AS
BEGIN
    -- Khai báo biến
    DECLARE @chiNhanh VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @serverLink VARCHAR(50);
    DECLARE @ngayBD_db DATE;
    DECLARE @ngayKT_db DATE;

    SELECT @chiNhanh = 'HN' FROM LINK1.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
    IF @chiNhanh IS NULL
    BEGIN
        SELECT @chiNhanh = 'DN' FROM LINK2.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
        IF @chiNhanh IS NULL
        BEGIN
            SELECT @chiNhanh = 'HCM' FROM LINK3.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
             IF @chiNhanh IS NULL
            BEGIN
                RAISERROR ('Không tìm thấy đơn xin nghỉ có mã %s.', 16, 1, @maDon);
                RETURN;
            END
        END
    END

    IF @chiNhanh = 'HN'
        SET @serverLink = 'LINK1';
    ELSE IF @chiNhanh = 'DN'
        SET @serverLink = 'LINK2';
    ELSE IF @chiNhanh = 'HCM'
        SET @serverLink = 'LINK3';

    -- Kiểm tra trạng thái đơn xin nghỉ
    SET @sqlCommand = 'SELECT 1 FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon AND trangThai = N''Chờ Duyệt''';
    EXEC sp_executesql @sqlCommand;
    IF @@ROWCOUNT = 0
    BEGIN
        RAISERROR ('Chỉ có thể sửa đơn xin nghỉ ở trạng thái Chờ Duyệt.', 16, 1);
        RETURN;
    END

    -- Kiểm tra ngày bắt đầu và ngày kết thúc
    IF (@ngayBD IS NOT NULL)
    BEGIN
        SET @sqlCommand = 'SELECT ngayKT FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon';
        EXEC sp_executesql @sqlCommand ,N'@ngayKT_db DATE OUTPUT',@ngayKT_db =@ngayKT_db OUTPUT;
        IF (@ngayKT_db IS NOT NULL AND @ngayBD > @ngayKT_db)
        BEGIN
            RAISERROR ('Ngày bắt đầu phải trước ngày kết thúc.', 16, 1);
            RETURN;
        END
    END

    IF (@ngayKT IS NOT NULL)
    BEGIN
        SET @sqlCommand = 'SELECT ngayBD FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon';
        EXEC sp_executesql @sqlCommand ,N'@ngayBD_db DATE OUTPUT',@ngayBD_db =@ngayBD_db OUTPUT;
        IF (@ngayKT < @ngayBD_db)
        BEGIN
            RAISERROR ('Ngày kết thúc phải sau ngày bắt đầu.', 16, 1);
            RETURN;
        END
    END

    SET @sqlCommand = 'UPDATE ' + @serverLink + '.phonestore.dbo.DONXINNGHI SET ngayBD = ISNULL(@ngayBD, ngayBD), ngayKT = ISNULL(@ngayKT, ngayKT), lyDo = ISNULL(@lyDo, lyDo) WHERE maDon = @maDon';
    EXEC sp_executesql @sqlCommand, N'@maDon VARCHAR(50), @ngayBD DATE, @ngayKT DATE, @lyDo NVARCHAR(MAX)',
                    @maDon, @ngayBD, @ngayKT, @lyDo;
    PRINT 'Đơn xin nghỉ đã được cập nhật thành công.';
END;
GO

--EXEC sp_SuaDonXinNghi  @madon = 'DON007', @ngayKT = '2025-03-21';
-- EXEC sp_SuaDonXinNghi  @madon = 'DON009',  @ngayKT = '2025-03-24';

----- 5.3 Xoa don xin nghi
CREATE PROCEDURE sp_XoaDonXinNghi (
    @maDon VARCHAR(50)
)
AS
BEGIN
    DECLARE @chiNhanh VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @serverLink VARCHAR(50);

    SELECT @chiNhanh = 'HN' FROM LINK1.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
    IF @chiNhanh IS NULL
    BEGIN
        SELECT @chiNhanh = 'DN' FROM LINK2.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
        IF @chiNhanh IS NULL
        BEGIN
            SELECT @chiNhanh = 'HCM' FROM LINK3.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
            IF @chiNhanh IS NULL
            BEGIN
                RAISERROR ('Không tìm thấy đơn xin nghỉ có mã %s.', 16, 1, @maDon);
                RETURN;
            END
        END
    END

    -- Kiểm tra trạng thái đơn xin nghỉ và xóa nếu trạng thái hợp lệ
    IF @chiNhanh IS NOT NULL
    BEGIN
        IF @chiNhanh = 'HN'
            SET @serverLink = 'LINK1';
        ELSE IF @chiNhanh = 'DN'
            SET @serverLink = 'LINK2';
        ELSE IF @chiNhanh = 'HCM'
            SET @serverLink = 'LINK3';

        SET @sqlCommand = 'SELECT 1 FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon AND trangThai = N''Chờ Duyệt''';
        EXEC sp_executesql @sqlCommand;
        IF @@ROWCOUNT = 0
        BEGIN
            RAISERROR ('Chỉ có thể xóa đơn xin nghỉ ở trạng thái Chờ Duyệt.', 16, 1);
            RETURN;
        END

        SET @sqlCommand = 'DELETE FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon';
        EXEC sp_executesql @sqlCommand;

        PRINT 'Đơn xin nghỉ đã được xóa thành công.';
    END
END;
GO



--exec sp_XoaDonXinNghi 'DON011'


----- 5.4 Lấy danh sách đơn theo maNV - sp_GetDSDonTheoMaNV
CREATE PROCEDURE sp_GetDSDonTheoMaNV (
    @maNV VARCHAR(50)
)
AS
BEGIN
    DECLARE @chiNhanh VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @serverLink VARCHAR(50);

    SELECT @chiNhanh = ChiNhanh FROM LINK1.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
    IF @chiNhanh IS NULL
    BEGIN
        SELECT @chiNhanh = ChiNhanh FROM LINK2.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
        IF @chiNhanh IS NULL
        BEGIN
            SELECT @chiNhanh = ChiNhanh FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV;
            IF @chiNhanh IS NULL
            BEGIN
                RAISERROR ('Không tìm thấy nhân viên có mã %s.', 16, 1, @maNV);
                RETURN;
            END
        END
    END

    IF @chiNhanh = 'HN'
        SET @serverLink = 'LINK1';
    ELSE IF @chiNhanh = 'DN'
        SET @serverLink = 'LINK2';
    ELSE IF @chiNhanh = 'HCM'
        SET @serverLink = 'LINK3';

    SET @sqlCommand = 'SELECT maDon, ngayBD, ngayKT, lyDo, trangThai, maNV FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maNV = @maNV';

    EXEC sp_executesql @sqlCommand, N'@maNV VARCHAR(50)', @maNV;
END;
GO


--EXEC sp_GetDSDonTheoMaNV 'NV001';
	
----- 5.5 Tìm đơn xin nghỉ bằng maDon - sp_GetDSDonTheoMaNV
CREATE PROCEDURE sp_GetDSDonTheoMaDon (
    @maDon VARCHAR(50)
)
AS
BEGIN
    DECLARE @chiNhanh VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);
    DECLARE @serverLink VARCHAR(50);

    SELECT @chiNhanh = 'HN' FROM LINK1.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
    IF @chiNhanh IS NULL
    BEGIN
        SELECT @chiNhanh = 'DN' FROM LINK2.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
        IF @chiNhanh IS NULL
        BEGIN
            SELECT @chiNhanh = 'HCM' FROM LINK3.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon;
            IF @chiNhanh IS NULL
            BEGIN
                RAISERROR ('Không tìm thấy đơn xin nghỉ có mã %s.', 16, 1, @maDon);
                RETURN;
            END
        END
    END

    IF @chiNhanh = 'HN'
        SET @serverLink = 'LINK1';
    ELSE IF @chiNhanh = 'DN'
        SET @serverLink = 'LINK2';
    ELSE IF @chiNhanh = 'HCM'
        SET @serverLink = 'LINK3';

    -- Lấy danh sách đơn xin nghỉ từ chi nhánh tương ứng
    SET @sqlCommand = 'SELECT maDon, ngayBD, ngayKT, lyDo, trangThai, maNV FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI WHERE maDon = @maDon';

    EXEC sp_executesql @sqlCommand, N'@maDon VARCHAR(50)', @maDon;
END;
GO

--EXEC sp_GetDSDonTheoMaDon 'DON001';

	
----- 5.6 Lấy danh sách đơn tất cả các kho - sp_GetDonByKho
CREATE PROCEDURE sp_GetAllDon
AS
BEGIN
    DECLARE @sqlCommand NVARCHAR(MAX);

    SET @sqlCommand = N'SELECT maDon, ngayBD, ngayKT, lyDo, trangThai, maNV FROM LINK1.phonestore.dbo.DONXINNGHI ' +
                    'UNION ALL ' +
                    'SELECT maDon, ngayBD, ngayKT, lyDo, trangThai, maNV FROM LINK2.phonestore.dbo.DONXINNGHI ' +
                    'UNION ALL ' +
                    'SELECT maDon, ngayBD, ngayKT, lyDo, trangThai, maNV FROM LINK3.phonestore.dbo.DONXINNGHI';

    EXEC sp_executesql @sqlCommand;
END;
GO

	
-- EXEC sp_GetAllDon

----- 5.7 Lấy danh sách đơn của một kho - sp_GetAllDon
CREATE PROCEDURE sp_GetDonByKho (
    @maKho VARCHAR(50)
)
AS
BEGIN
    DECLARE @serverLink VARCHAR(50);
    DECLARE @sqlCommand NVARCHAR(MAX);

    IF @maKho = 'HN'
        SET @serverLink = 'LINK1';
    ELSE IF @maKho = 'DN'
        SET @serverLink = 'LINK2';
    ELSE IF @maKho = 'HCM'
        SET @serverLink = 'LINK3';
    ELSE
    BEGIN
        RAISERROR ('Không tìm thấy kho có mã %s.', 16, 1, @maKho);
        RETURN;
    END

    SET @sqlCommand = 'SELECT d.maDon, d.ngayBD, d.ngayKT, d.lyDo, d.trangThai, d.maNV ' +
                    'FROM ' + @serverLink + '.phonestore.dbo.DONXINNGHI d ' +
                    'JOIN ' + @serverLink + '.phonestore.dbo.NHANVIEN nv ON d.maNV = nv.maNV ' +
                    'WHERE nv.ChiNhanh = @maKho';

    EXEC sp_executesql @sqlCommand, N'@maKho VARCHAR(50)', @maKho;
END;
GO


-- exec sp_GetDonByKho 'HCM'


--////////////// Procedure tạo bảng chấm công và bảng lương cho nhân viên vào ngày 1 mỗi tháng

CREATE PROCEDURE sp_TaoBCCVaLuongThangMoi
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @ngay DATE = GETDATE();
    DECLARE @thang INT = MONTH(@ngay);
    DECLARE @nam INT = YEAR(@ngay);
    DECLARE @maBCC VARCHAR(50), @maBL VARCHAR(50), @maNV VARCHAR(50), @maKho VARCHAR(10);
    DECLARE @luongCB FLOAT, @heSo FLOAT = 1.0, @maChucVu VARCHAR(50);

    -- Cursor lấy danh sách nhân viên cùng kho phân mảnh
    DECLARE cur CURSOR FOR
        SELECT maNV, 'HN' FROM LINK1.phonestore.dbo.NHANVIEN
        UNION
        SELECT maNV, 'DN' FROM LINK2.phonestore.dbo.NHANVIEN
        UNION
        SELECT maNV, 'HCM' FROM LINK3.phonestore.dbo.NHANVIEN;

    OPEN cur;
    FETCH NEXT FROM cur INTO @maNV, @maKho;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @maBCC = 'CC' + FORMAT(@ngay, 'ddMMyyyy') + @maNV;
        SET @maBL = 'BL' + FORMAT(@ngay, 'MMyyyy') + @maNV;

        IF @maKho = 'HN'
        BEGIN
            -- Lấy lương CB từ chức vụ
            SELECT TOP 1 @maChucVu = maCV
            FROM LINK1.phonestore.dbo.NHANVIEN
            WHERE maNV = @maNV;

            SELECT TOP 1 @luongCB = luongCB
            FROM LINK1.phonestore.dbo.CHUCVU
            WHERE maCV = @maChucVu;

            -- Chỉ thêm nếu chưa tồn tại
            IF NOT EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.BangChamCong WHERE maBCC = @maBCC)
            BEGIN
                INSERT INTO LINK1.phonestore.dbo.BangChamCong(maBCC, thangCC, namCC, maNV)
                VALUES (@maBCC, @thang, @nam, @maNV);
            END

            IF NOT EXISTS (SELECT 1 FROM LINK1.phonestore.dbo.BangLuong WHERE maBL = @maBL)
            BEGIN
                INSERT INTO LINK1.phonestore.dbo.BangLuong(maBL, thangLuong, namLuong, luongCB, heSo, maNV)
                VALUES (@maBL, @thang, @nam, @luongCB, @heSo, @maNV);
            END
        END

        ELSE IF @maKho = 'DN'
        BEGIN
            SELECT TOP 1 @maChucVu = maCV
            FROM LINK2.phonestore.dbo.NHANVIEN
            WHERE maNV = @maNV;

            SELECT TOP 1 @luongCB = luongCB
            FROM LINK2.phonestore.dbo.CHUCVU
            WHERE maCV = @maChucVu;

            IF NOT EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.BangChamCong WHERE maBCC = @maBCC)
            BEGIN
                INSERT INTO LINK2.phonestore.dbo.BangChamCong(maBCC, thangCC, namCC, maNV)
                VALUES (@maBCC, @thang, @nam, @maNV);
            END

            IF NOT EXISTS (SELECT 1 FROM LINK2.phonestore.dbo.BangLuong WHERE maBL = @maBL)
            BEGIN
                INSERT INTO LINK2.phonestore.dbo.BangLuong(maBL, thangLuong, namLuong, luongCB, heSo, maNV)
                VALUES (@maBL, @thang, @nam, @luongCB, @heSo, @maNV);
            END
        END

        ELSE IF @maKho = 'HCM'
        BEGIN
            SELECT TOP 1 @maChucVu = maCV
            FROM LINK3.phonestore.dbo.NHANVIEN
            WHERE maNV = @maNV;

            SELECT TOP 1 @luongCB = luongCB
            FROM LINK3.phonestore.dbo.CHUCVU
            WHERE maCV = @maChucVu;

            IF NOT EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.BangChamCong WHERE maBCC = @maBCC)
            BEGIN
                INSERT INTO LINK3.phonestore.dbo.BangChamCong(maBCC, thangCC, namCC, maNV)
                VALUES (@maBCC, @thang, @nam, @maNV);
            END

            IF NOT EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.BangLuong WHERE maBL = @maBL)
            BEGIN
                INSERT INTO LINK3.phonestore.dbo.BangLuong(maBL, thangLuong, namLuong, luongCB, heSo, maNV)
                VALUES (@maBL, @thang, @nam, @luongCB, @heSo, @maNV);
            END
        END

        FETCH NEXT FROM cur INTO @maNV, @maKho;
    END

    CLOSE cur;
    DEALLOCATE cur;
END
