/*Bao gồm
---------------1. Quản lý nhân viên 
-----1.1 Lấy danh sách nhân viên tất cả các kho - sp_LayDanhSachNhanVienGoc
-----1.2 Lấy danh sách nhân viên của một kho - sp_LayDanhSachNhanVienTheoKhoGoc
-----1.3 Thêm nhân viên
---1.3.1 Tạo mã nhân viên tự động tăng - sp_TaoMaNhanVien
---1.3.2 Thêm nhân viên - sp_ThemNhanVienGoc
-----1.4 Sửa nhân viên - sp_SuaNhanVienGoc
-----1.5 Tìm nhân viên theo manv- sp_TimNhanVienTheoMaNVGoc
-----1.6 Tìm kiếm nhân viên (theo từ khoá) - sp_TimNhanVienTheoTuKhoaGoc
2. Quản lý bảng chấm công
-----2.1 Lấy danh sách bảng chấm công - sp_LayDSChamCongGoc
-----2.2 Lấy danh sách bảng chấm công của một kho - sp_DSBangChamCongTheoKhoGoc
-----2.3 Lấy danh sách bảng chấm công theo thời gian - sp_LayDSBangChamCongTheoTGGoc
-----2.4 Thêm bảng chấm công	- sp_ThemChamCongGoc
-----2.5 Sửa bảng chấm công - sp_SuaChamCongGoc
-----2.5 Tìm bảng chấm công theo maCC - sp_TimBangCCTheoMaBCCGoc
-----2.6 Tìm danh sách bảng chấm công theo từ khoá (maBCC, maNV, tenNV) - sp_TimDanhSachCCTheoTuKhoaGoc
-----2.7 Lấy danh sách bảng chấm công theo maNV - sp_LayDSBangChamCongTheoMaNV
3. Quản lý chi tiết chấm công
-----3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCongGoc
-----3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCongGoc
-----3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCongGoc
-----3.4 Xoá danh sách chi tiết chấm công theo mabcc - sp_XoaChiTietChamCongTheoBCCGoc
-----3.5 Tìm kiếm chi tiết chấm công theo maCTCC - sp_LayChiTietChamCongTheoMaCTGoc
-----3.6 Lấy danh sách chi tiết chấm công theo maCC - sp_LayChiTietChamCongTheoMaCCGoc
4. Quản lý bảng lương
-----4.1 Thêm bảng lương	- sp_ThemBangLuongGoc
-----4.2 Sửa bảng lương - sp_SuaBangLuongGoc
-----4.3 Lấy danh sách bảng lương theo maNV - sp_LayDSBangLuongByMaNVGoc
-----4.4 Tìm Bảng lương bằng maBL - sp_TimBangLuongByMaBLGoc
-----4.5 Lấy danh sách bảng lương của các kho - sp_LayDSTatCaBangLuong
5. Quản lý đơn xin nghỉ
-----5.1 Thêm đơn xin nghỉ - sp_ThemDonXinNghiGoc
-----5.2 Sửa đơn xin nghỉ - sp_SuaDonXinNghiGoc
-----5.3 Xoá đơn xin nghỉ - sp_XoaDonXinNghiGoc
-----5.4 Lấy danh sách đơn theo maNV - sp_LayDSDonTheoMaNVGoc
-----5.5 Tìm đơn xin nghỉ bằng maDon - sp_TimDonTheoMaDonGoc
-----5.6 Lấy danh sách đơn tất cả các kho - sp_LayDSTatCaDonGoc
-----5.7 Lấy danh sách đơn của một kho - sp_LayDSDonGoc



-- Xem tất cả procedure đang có
SELECT *
FROM sys.procedures
-- Xem tất cả procedure đã tạo
SELECT *
FROM sys.procedures
WHERE name LIKE 'sp_%'

-- Kiểm tra tên server ta đang kết nối tới
select @@servername as ServerName

use phonestore
*/

---------------1. Quản lý nhân viên 
-----1.1 Lấy danh sách nhân viên tất cả các kho - sp_LayDanhSachNhanVienGoc
CREATE PROCEDURE sp_LayDanhSachNhanVienGoc
AS
BEGIN
    SET NOCOUNT ON;

    SELECT * FROM LINK3.phonestore.dbo.NHANVIEN where trangThai = 'on'
    UNION ALL
    SELECT * FROM LINK4.phonestore.dbo.NHANVIEN where trangThai = 'on'
    UNION ALL
    SELECT * FROM LINK5.phonestore.dbo.NHANVIEN where trangThai = 'on'
END
exec sp_LayDanhSachNhanVienGoc

-----1.2 Lấy danh sách nhân viên của một kho - sp_LayDanhSachNhanVienTheoKhoGoc
drop procedure sp_LayDanhSachNhanVienTheoKhoGoc
CREATE PROCEDURE sp_LayDanhSachNhanVienTheoKhoGoc
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        SELECT *, 'HN' AS chiNhanh FROM LINK3.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        SELECT *, 'DN' AS chiNhanh FROM LINK4.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        SELECT *, 'HCM' AS chiNhanh FROM LINK5.[phonestore].dbo.NHANVIEN WHERE trangThai = 'on'
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

EXEC sp_LayDanhSachNhanVienTheoKhoGoc 'HCM'


-----1.3 Thêm nhân viên - sp_ThemNhanVienGoc
---1.3.1 Tạo mã nhân viên tự động tăng - sp_TaoMaNhanVien
drop procedure sp_TaoMaNhanVien
create procedure sp_TaoMaNhanVien
@mamoi varchar(10) OUTPUT
as
begin
	declare @sl1 INT = 0
    declare @sl2 INT = 0
    declare @sl3 INT = 0
    declare @tongSoLuong INT, @soThuTu INT

	select @sl1 = COUNT(*) FROM LINK3.PHONESTORE.dbo.NHANVIEN
    select @sl2 = COUNT(*) FROM LINK4.PHONESTORE.dbo.NHANVIEN
    select @sl3 = COUNT(*) FROM LINK5.PHONESTORE.dbo.NHANVIEN
	set @tongSoLuong = @sl1 + @sl2 + @sl3
	SET @soThuTu = @tongSoLuong + 1
	set @mamoi = 'NV' + RIGHT('000' + CONVERT(VARCHAR, @soThuTu), 3)
END

---1.3.2 Thêm nhân viên - sp_ThemNhanVienGoc
DROP PROCEDURE sp_ThemNhanVienGoc
CREATE PROCEDURE sp_ThemNhanVienGoc
    @hoTen NVARCHAR(100),
    @ngaySinh DATE,
    @gioiTinh NVARCHAR(3),
    @diaChi NVARCHAR(200),
    @sdt NVARCHAR(20),
    @email NVARCHAR(255),
    @hinhAnh VARBINARY(MAX),
    @matKhau NVARCHAR(200),
    @maCV NVARCHAR(50),
    @maKho VARCHAR(10),
	@maNV VARCHAR(50) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Tạo mã nhân viên mới duy nhất
    EXEC sp_TaoMaNhanVien @maNV OUTPUT

    -- 2. Gọi tới đúng thủ tục ở server phân mảnh để thêm nhân viên cục bộ
    IF @maKho = 'HN'
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_ThemNhanVienKho
            @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email,
            @hinhAnh, @matKhau, @maCV, @maKho, @maNV OUTPUT
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_ThemNhanVienKho
            @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email,
            @hinhAnh, @matKhau, @maCV, @maKho, @maNV OUTPUT
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_ThemNhanVienKho
            @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email,
            @hinhAnh, @matKhau, @maCV, @maKho, @maNV OUTPUT
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
        RETURN
    END
END
drop procedure sp_ThemNhanVienGoc
--\\\\\\\\\\\\\\\\\ TEST
DECLARE @maNhanVienMoi VARCHAR(10);

EXEC sp_ThemNhanVienGoc
    @hoTen = N'Nguyễn Trần D',
    @ngaySinh = '1995-06-15',
    @gioiTinh = 'Nam',
    @diaChi = N'123 Trần Hưng Đạo, Hà Nội',
    @sdt = '0912345678',
    @email = 'vana@example.com',
    @hinhAnh = NULL,
    @matKhau = '123',
    @maCV = 'CV003',
    @maKho = 'HCM',
    @maNV = @maNhanVienMoi OUTPUT;  -- thêm OUTPUT ở đây!
;


-----1.4 Sửa nhân viên - sp_SuaNhanVienGoc
drop procedure sp_SuaNhanVienGoc

CREATE PROCEDURE sp_SuaNhanVienGoc
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
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK3.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    -- DN
    IF EXISTS (SELECT 1 FROM LINK4.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK4.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    -- HCM
    IF EXISTS (SELECT 1 FROM LINK5.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE LINK5.phonestore.dbo.NHANVIEN
        SET hoTen = @hoTen, ngaySinh = @ngaySinh, gioiTinh = @gioiTinh, diaChi = @diaChi, sdt = @sdt, email = @email, 
			hinhAnh = @hinhAnh, matKhau = @matKhau, trangThai = @trangThai, maCV = @maCV
        WHERE maNV = @maNV;
        RETURN;
    END

    RAISERROR(N'Khong tim thay ma nhan vien.', 16, 1);
END
--//////////// TEST
EXEC sp_SuaNhanVienGoc
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


-----1.5 Tìm nhân viên theo manv- sp_TimNhanVienTheoMaNVGoc
drop procedure sp_TimNhanVienTheoMaNVGoc
CREATE PROCEDURE sp_TimNhanVienTheoMaNVGoc
    @maNV VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Tìm ở phân mảnh HN
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'HN' AS chiNhanh FROM LINK3.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Tìm ở phân mảnh DN
    IF EXISTS (SELECT 1 FROM LINK4.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'DN' AS chiNhanh FROM LINK4.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Tìm ở phân mảnh HCM
    IF EXISTS (SELECT 1 FROM LINK5.phonestore.dbo.NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT *, N'HCM' AS chiNhanh FROM LINK5.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV;
        RETURN;
    END

    -- Nếu không tìm thấy
    RAISERROR(N'Không tìm thấy nhân viên với mã đã cho.', 16, 1);
END

-----1.6 Tìm kiếm nhân viên (theo từ khoá) - sp_TimNhanVienTheoTuKhoaGoc



---------------2. Quản lý bảng chấm công
-----2.1 Lấy danh sách bảng chấm công - sp_LayDSChamCongGoc
drop procedure sp_LayDSChamCongGoc
CREATE PROCEDURE sp_LayDSChamCongGoc
AS
BEGIN
    SET NOCOUNT ON;

    SELECT * FROM LINK3.phonestore.dbo.BANGCHAMCONG
    UNION ALL
    SELECT * FROM LINK4.phonestore.dbo.BANGCHAMCONG
    UNION ALL
    SELECT * FROM LINK5.phonestore.dbo.BANGCHAMCONG
END

-----2.2 Lấy danh sách bảng chấm công của một kho - sp_DSBangChamCongTheoKhoGoc
drop procedure sp_DSBangChamCongTheoKhoGoc
create procedure sp_DSBangChamCongTheoKhoGoc
    @maKho VARCHAR(10) -- Mã kho cần lấy danh sách chấm công
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @ServerLink NVARCHAR(100)

    -- Gán server tương ứng với mã kho
    IF @maKho = 'HN' SET @ServerLink = 'LINK3'
    ELSE IF @maKho = 'DN' SET @ServerLink = 'LINK4'
    ELSE IF @maKho = 'HCM' SET @ServerLink = 'LINK5'
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

-----2.3 Lấy danh sách bảng chấm công theo thời gian - sp_LayDSBangChamCongTheoTGGoc
drop procedure sp_GetAllBangChamCongByTimeGoc
CREATE PROCEDURE sp_LayDSBangChamCongTheoTGGoc
    @thangCC INT,
    @namCC INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @sql NVARCHAR(MAX);

    SET @sql = '
    SELECT * FROM OPENQUERY(LINK3,
        ''SELECT * FROM phonestore.dbo.BANGCHAMCONG 
          WHERE thangCC = ' + CAST(@thangCC AS NVARCHAR) + ' AND namCC = ' + CAST(@namCC AS NVARCHAR) + ''')
    UNION ALL
    SELECT * FROM OPENQUERY(LINK4,
        ''SELECT * FROM phonestore.dbo.BANGCHAMCONG 
          WHERE thangCC = ' + CAST(@thangCC AS NVARCHAR) + ' AND namCC = ' + CAST(@namCC AS NVARCHAR) + ''')
    UNION ALL
    SELECT * FROM OPENQUERY(LINK5,
        ''SELECT * FROM phonestore.dbo.BANGCHAMCONG 
          WHERE thangCC = ' + CAST(@thangCC AS NVARCHAR) + ' AND namCC = ' + CAST(@namCC AS NVARCHAR) + ''')';

    EXEC sp_executesql @sql;
END

-----2.4 Thêm bảng chấm công	- sp_ThemChamCongGoc
CREATE PROCEDURE sp_ThemChamCongGoc
    @maCC VARCHAR(50),
    @thangCC INT,
    @namCC INT,
    @soNgayLam FLOAT = NULL,
    @soNgayNghiKP FLOAT = NULL,
    @soNPCoLuong FLOAT = NULL,
    @soNPKhongLuong FLOAT = NULL,
    @soGioOTNgayThuong FLOAT = NULL,
    @soGioOTNgayLe FLOAT = NULL,
    @soGioOTCN FLOAT = NULL,
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @found BIT = 0;

    -- Kiểm tra LINK3
    IF EXISTS (
        SELECT 1
        FROM LINK3.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV
    )
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_ThemChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Kiểm tra LINK4
    IF EXISTS (
        SELECT 1
        FROM LINK4.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV
    )
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_ThemChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Kiểm tra LINK5
    IF EXISTS (
        SELECT 1
        FROM LINK5.phonestore.dbo.NHANVIEN
        WHERE maNV = @maNV
    )
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_ThemChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Không tìm thấy mã nhân viên
    IF @found = 0
    BEGIN
        RAISERROR(N'Không tìm thấy nhân viên trong hệ thống. Không thể thêm chấm công.', 16, 1);
    END
END

drop procedure sp_ThemBangChamCongGoc


--////////// TEST
EXEC sp_ThemBangChamCongGoc
    @maBCC = 'CC052025NV007',
    @thangCC = 5,
    @namCC = 2025,
    @soNgayLam = 24,
    @soNgayNghiKP = 0.0,
    @soNPCoLuong = 0.0,
    @soNPKhongLuong = 0.0,
    @soGioOTNgayThuong = 0.0,
    @soGioOTNgayLe = 0.0,
    @soGioOTCN = 0.0,
    @maNV = 'NV007';

-----2.5 Sửa bảng chấm công - sp_SuaChamCongGoc
CREATE PROCEDURE sp_SuaChamCongGoc
    @maCC VARCHAR(50),
    @thangCC INT,
    @namCC INT,
    @soNgayLam FLOAT = NULL,
    @soNgayNghiKP FLOAT = NULL,
    @soNPCoLuong FLOAT = NULL,
    @soNPKhongLuong FLOAT = NULL,
    @soGioOTNgayThuong FLOAT = NULL,
    @soGioOTNgayLe FLOAT = NULL,
    @soGioOTCN FLOAT = NULL,
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @found BIT = 0;

    -- Phân mảnh 1: LINK3
    IF EXISTS (
        SELECT 1 FROM LINK3.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maCC
    )
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_SuaChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Phân mảnh 2: LINK4
    IF EXISTS (
        SELECT 1 FROM LINK4.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maCC
    )
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_SuaChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Phân mảnh 3: LINK5
    IF EXISTS (
        SELECT 1 FROM LINK5.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maCC
    )
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_SuaChamCongKho
            @maCC, @thangCC, @namCC,
            @soNgayLam, @soNgayNghiKP, @soNPCoLuong, @soNPKhongLuong,
            @soGioOTNgayThuong, @soGioOTNgayLe, @soGioOTCN, @maNV;
        SET @found = 1;
        RETURN;
    END

    -- Không tìm thấy mã chấm công
    IF @found = 0
    BEGIN
        RAISERROR(N'Mã chấm công không tồn tại trong toàn hệ thống.', 16, 1);
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

-----2.5 Tìm bảng chấm công theo maCC - sp_TimBangCCTheoMaBCCGoc
CREATE PROCEDURE sp_TimBangCCTheoMaBCCGoc 
    @maBCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @found BIT = 0;

    -- Kiểm tra tại LINK3
    IF EXISTS (
        SELECT 1 FROM LINK3.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_TimBangCCTheoMaBCCKho @maBCC;
        SET @found = 1;
        RETURN;
    END

    -- Kiểm tra tại LINK4
    IF EXISTS (
        SELECT 1 FROM LINK4.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_TimBangCCTheoMaBCCKho @maBCC;
        SET @found = 1;
        RETURN;
    END

    -- Kiểm tra tại LINK5
    IF EXISTS (
        SELECT 1 FROM LINK5.phonestore.dbo.BANGCHAMCONG
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_TimBangCCTheoMaBCCKho @maBCC;
        SET @found = 1;
        RETURN;
    END

    -- Nếu không tìm thấy
    IF @found = 0
    BEGIN
        RAISERROR(N'Không tìm thấy bảng chấm công với mã đã nhập trong toàn hệ thống.', 16, 1);
    END
END


-----2.6 Tìm danh sách bảng chấm công theo từ khoá (maBCC, maNV, tenNV) - sp_TimDanhSachCCTheoTuKhoaGoc
CREATE PROCEDURE sp_TimDanhSachBangChamCongGoc 
    @tuKhoa NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    -- Kết quả tìm kiếm từ LINK3
    SELECT cc.*, nv.hoTen
    FROM LINK3.phonestore.dbo.BANGCHAMCONG cc
    JOIN LINK3.phonestore.dbo.NHANVIEN nv ON cc.maNV = nv.maNV
    WHERE cc.maBCC LIKE '%' + @tuKhoa + '%'
       OR cc.maNV LIKE '%' + @tuKhoa + '%'
       OR nv.hoTen LIKE N'%' + @tuKhoa + '%'

    UNION ALL

    -- Kết quả tìm kiếm từ LINK4
    SELECT cc.*, nv.hoTen
    FROM LINK4.phonestore.dbo.BANGCHAMCONG cc
    JOIN LINK4.phonestore.dbo.NHANVIEN nv ON cc.maNV = nv.maNV
    WHERE cc.maBCC LIKE '%' + @tuKhoa + '%'
       OR cc.maNV LIKE '%' + @tuKhoa + '%'
       OR nv.hoTen LIKE N'%' + @tuKhoa + '%'

    UNION ALL

    -- Kết quả tìm kiếm từ LINK5
    SELECT cc.*, nv.hoTen
    FROM LINK5.phonestore.dbo.BANGCHAMCONG cc
    JOIN LINK5.phonestore.dbo.NHANVIEN nv ON cc.maNV = nv.maNV
    WHERE cc.maBCC LIKE '%' + @tuKhoa + '%'
       OR cc.maNV LIKE '%' + @tuKhoa + '%'
       OR nv.hoTen LIKE N'%' + @tuKhoa + '%';
END

EXEC sp_TimDanhSachBangChamCongGoc @tuKhoa = N'NV001';



-----2.7 Lấy danh sách bảng chấm công theo maNV - sp_LayDSBangChamCongTheoMaNV
drop procedure sp_LayDSBangChamCongTheoMaNV
create procedure sp_LayDSBangChamCongTheoMaNV
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    -- Hà Nội
    IF EXISTS (SELECT 1 FROM LINK3.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'HN' AS maKho, *
        FROM LINK3.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Đà Nẵng
    IF EXISTS (SELECT 1 FROM LINK4.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'DN' AS maKho, *
        FROM LINK4.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Hồ Chí Minh
    IF EXISTS (SELECT 1 FROM LINK5.phonestore.dbo.BANGCHAMCONG WHERE maNV = @maNV)
    BEGIN
        SELECT 'HCM' AS maKho, *
        FROM LINK5.phonestore.dbo.BANGCHAMCONG
        WHERE maNV = @maNV
        ORDER BY namCC DESC, thangCC DESC;
        RETURN;
    END

    -- Không tìm thấy nhân viên
    RAISERROR(N'Không tìm thấy bảng chấm công của nhân viên có mã %s.', 16, 1, @maNV);
END

EXEC sp_GETDSBangChamCongByMaNV @maNV = 'NV005';

---------------3. Quản lý chi tiết chấm công
-----3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCongGoc
CREATE PROCEDURE sp_ThemChiTietChamCongGoc
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(255),
    @chiTiet NVARCHAR(255) = NULL,
    @maBCC VARCHAR(50),
    @soGioOT FLOAT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @found BIT = 0;

    -- Phân mảnh: LINK3
    IF EXISTS (
        SELECT 1 
        FROM LINK3.phonestore.dbo.BANGCHAMCONG 
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_ThemChiTietChamCongKho
            @maCTCC = @maCTCC,
            @ngayTao = @ngayTao,
            @loaiChamCong = @loaiChamCong,
            @chiTiet = @chiTiet,
            @maBCC = @maBCC,
            @soGioOT = @soGioOT;

        SET @found = 1;
    END

    -- Phân mảnh: LINK4
    ELSE IF EXISTS (
        SELECT 1 
        FROM LINK4.phonestore.dbo.BANGCHAMCONG 
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_ThemChiTietChamCongKho
            @maCTCC = @maCTCC,
            @ngayTao = @ngayTao,
            @loaiChamCong = @loaiChamCong,
            @chiTiet = @chiTiet,
            @maBCC = @maBCC,
            @soGioOT = @soGioOT;

        SET @found = 1;
    END

    -- Phân mảnh: LINK5
    ELSE IF EXISTS (
        SELECT 1 
        FROM LINK5.phonestore.dbo.BANGCHAMCONG 
        WHERE maBCC = @maBCC
    )
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_ThemChiTietChamCongKho
            @maCTCC = @maCTCC,
            @ngayTao = @ngayTao,
            @loaiChamCong = @loaiChamCong,
            @chiTiet = @chiTiet,
            @maBCC = @maBCC,
            @soGioOT = @soGioOT;

        SET @found = 1;
    END

    -- Không tìm thấy phân mảnh phù hợp
    IF @found = 0
    BEGIN
        RAISERROR(N'Mã bảng chấm công không tồn tại trong bất kỳ phân mảnh nào.', 16, 1);
    END
END

EXEC sp_ThemChiTietChamCongGoc
    @maCTCC = 'CTCC001',
    @ngayTao = '2025-05-02',
    @loaiChamCong = N'Tăng ca ngày thường',
    @chiTiet = null,
    @maBCC = '',
    @soGioOT = 2.5,
    @maKho = 'HN';


-----3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCongGoc
CREATE PROCEDURE sp_SuaChiTietChamCongGoc
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(255),
    @chiTiet NVARCHAR(255) = NULL,
    @maBCC VARCHAR(50),
    @soGioOT FLOAT = NULL,
    @maKho VARCHAR(10)  -- thêm tham số mã kho
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_SuaChiTietChamCongKho
            @maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT;
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_SuaChiTietChamCongKho
            @maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT;
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_SuaChiTietChamCongKho
            @maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ. Không thể sửa chi tiết chấm công.', 16, 1);
    END
END

drop procedure sp_SuaChiTietChamCongGoc

-----3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCongGoc
CREATE PROCEDURE sp_XoaChiTietChamCongGoc
    @maCTCC VARCHAR(50),
    @maKho VARCHAR(10)  -- Dùng để xác định phân mảnh
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_XoaChiTietChamCongKho @maCTCC;
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_XoaChiTietChamCongKho @maCTCC;
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_XoaChiTietChamCongKho @maCTCC;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ. Không thể xác định nơi lưu dữ liệu.', 16, 1);
    END
END

-----3.4 Xoá danh sách chi tiết chấm công theo mabcc - sp_XoaChiTietChamCongTheoBCCGoc
CREATE PROCEDURE sp_XoaChiTietChamCongTheoBCCGoc
    @maBCC VARCHAR(50),
    @maKho VARCHAR(10)  -- Dùng để xác định phân mảnh tương ứng
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_XoaChiTietChamCongTheoBCCKho @maBCC;
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_XoaChiTietChamCongTheoBCCKho @maBCC;
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_XoaChiTietChamCongTheoBCCKho @maBCC;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ. Không thể xác định nơi xoá dữ liệu.', 16, 1);
    END
END


EXEC sp_XoaChiTietChamCongTheoBCCGoc @maBCC = 'CC01052025NV001', @maKho = 'DN';
drop procedure sp_XoaChiTietChamCongTheoBCCGoc


-----3.5 Tìm kiếm chi tiết chấm công theo maCTCC - sp_LayChiTietChamCongTheoMaCTGoc
CREATE PROCEDURE sp_LayChiTietChamCongTheoMaCTGoc
    @maCTCC VARCHAR(50),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        EXEC LINK3.phonestore.dbo.sp_LayChiTietChamCongTheoMaCTKho @maCTCC;
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        EXEC LINK4.phonestore.dbo.sp_LayChiTietChamCongTheoMaCTKho @maCTCC;
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        EXEC LINK5.phonestore.dbo.sp_LayChiTietChamCongTheoMaCTKho @maCTCC;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ. Không thể tìm kiếm chi tiết chấm công.', 16, 1);
    END
END


drop procedure sp_GetChiTietChamCongTheoMaCTGoc
EXEC sp_GetChiTietChamCongTheoMaCTGoc @maCTCC = 'CT08032025NV002'


-----3.6 Lấy danh sách chi tiết chấm công theo maCC - sp_LayChiTietChamCongTheoMaCCGoc
CREATE PROCEDURE sp_LayChiTietChamCongTheoMaCCGoc
    @maCC VARCHAR(20),
    @maKho VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF @maKho = 'HN'
    BEGIN
        SELECT * 
        FROM LINK3.phonestore.dbo.sp_LayChiTietChamCongTheoMaCCKho
        WHERE maBCC = @maCC
    END
    ELSE IF @maKho = 'DN'
    BEGIN
        SELECT * 
        FROM LINK4.phonestore.dbo.sp_LayChiTietChamCongTheoMaCCKho
        WHERE maBCC = @maCC
    END
    ELSE IF @maKho = 'HCM'
    BEGIN
        SELECT * 
        FROM LINK5.phonestore.dbo.sp_LayChiTietChamCongTheoMaCCKho
        WHERE maBCC = @maCC
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã kho không hợp lệ.', 16, 1)
    END
END

drop procedure sp_LayChiTietChamCongTheoMaCCGoc
EXEC sp_LayChiTietChamCongTheoMaCCGoc @maCC = 'CC032025NV002', @maKho = 'HCM'


---------------4. Quản lý bảng lương
-----4.1 Thêm bảng lương - sp_ThemBangLuongGoc
CREATE PROCEDURE sp_ThemBangLuongGoc
    @maBL VARCHAR(50),
    @thangLuong INT,
    @namLuong INT,
    @luongCB FLOAT,
    @heSo FLOAT,
    @phuCapAnTrua FLOAT = NULL,
    @phuCapDiLai FLOAT = NULL,
    @thuong FLOAT = NULL,
    @bhxh FLOAT = NULL,
    @bhyt FLOAT = NULL,
    @bhtn FLOAT = NULL,
    @thueTNCN FLOAT = NULL,
    @tamUng FLOAT = NULL,
    @thuNhan FLOAT = NULL,
    @maNV VARCHAR(50),
    @maKho VARCHAR(15)
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Xác định tên linked server dựa trên mã kho
    DECLARE @serverName NVARCHAR(100);

    SET @serverName = CASE @maKho
        WHEN 'HN' THEN 'LINK3'
        WHEN 'DN' THEN 'LINK4'
        WHEN 'HCM' THEN 'LINK5'
        ELSE NULL
    END;

    IF @serverName IS NULL
    BEGIN
        RAISERROR(N'Không xác định được server cho mã kho %s.', 16, 1, @maKho);
        RETURN;
    END

    -- 2. Gọi thủ tục ở server phân mảnh để thêm bảng lương
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = '
    EXEC [' + @serverName + '].phonestore.[dbo].[sp_ThemBangLuongKho]
        @maBL, @thangLuong, @namLuong, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong,
        @bhxh, @bhyt, @bhtn, @thueTNCN,
        @tamUng, @thuNhan, @maNV';

    EXEC sp_executesql @sql,
        N'@maBL VARCHAR(50), @thangLuong INT, @namLuong INT, @luongCB FLOAT, @heSo FLOAT,
          @phuCapAnTrua FLOAT, @phuCapDiLai FLOAT, @thuong FLOAT,
          @bhxh FLOAT, @bhyt FLOAT, @bhtn FLOAT, @thueTNCN FLOAT,
          @tamUng FLOAT, @thuNhan FLOAT, @maNV VARCHAR(50)',
        @maBL, @thangLuong, @namLuong, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong,
        @bhxh, @bhyt, @bhtn, @thueTNCN,
        @tamUng, @thuNhan, @maNV;
END

-----4.2 Sửa bảng lương - sp_SuaBangLuongGoc
CREATE PROCEDURE sp_SuaBangLuongGoc
    @maBL VARCHAR(50),
    @luongCB FLOAT,
    @heSo FLOAT,
    @phuCapAnTrua FLOAT = NULL,
    @phuCapDiLai FLOAT = NULL,
    @thuong FLOAT = NULL,
    @bhxh FLOAT = NULL,
    @bhyt FLOAT = NULL,
    @bhtn FLOAT = NULL,
    @thueTNCN FLOAT = NULL,
    @tamUng FLOAT = NULL,
    @thuNhan FLOAT = NULL,
    @maKho VARCHAR(15)
AS
BEGIN
    SET NOCOUNT ON;

    -- Xác định linked server
    DECLARE @serverName NVARCHAR(100);
    SET @serverName = CASE @maKho
        WHEN 'HN' THEN 'LINK3'
        WHEN 'DN' THEN 'LINK4'
        WHEN 'HCM' THEN 'LINK5'
        ELSE NULL
    END;

    IF @serverName IS NULL
    BEGIN
        RAISERROR(N'Không xác định được server cho mã kho %s.', 16, 1, @maKho);
        RETURN;
    END;

    -- Gọi stored procedure sửa bảng lương ở server phân mảnh
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = '
    EXEC [' + @serverName + '].phonestore.[dbo].[sp_SuaBangLuongKho]
        @maBL, @luongCB, @heSo, @phuCapAnTrua, @phuCapDiLai,
        @thuong, @bhxh, @bhyt, @bhtn, @thueTNCN, @tamUng, @thuNhan';

    EXEC sp_executesql @sql,
        N'@maBL VARCHAR(50), @luongCB FLOAT, @heSo FLOAT,
          @phuCapAnTrua FLOAT, @phuCapDiLai FLOAT, @thuong FLOAT,
          @bhxh FLOAT, @bhyt FLOAT, @bhtn FLOAT, @thueTNCN FLOAT,
          @tamUng FLOAT, @thuNhan FLOAT',
        @maBL, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong,
        @bhxh, @bhyt, @bhtn, @thueTNCN,
        @tamUng, @thuNhan;
END

-----4.3 Lấy danh sách bảng lương theo maNV - sp_LayDSBangLuongByMaNVGoc
CREATE PROCEDURE sp_LayDSBangLuongByMaNV_Goc
    @maNV VARCHAR(50),
    @maKho VARCHAR(15)
AS
BEGIN
    SET NOCOUNT ON;

    -- Xác định linked server
    DECLARE @serverName NVARCHAR(100);
    SET @serverName = CASE @maKho
        WHEN 'HN' THEN 'LINK3'
        WHEN 'DN' THEN 'LINK4'
        WHEN 'HCM' THEN 'LINK5'
        ELSE NULL
    END;

    IF @serverName IS NULL
    BEGIN
        RAISERROR(N'Không xác định được server cho mã kho %s.', 16, 1, @maKho);
        RETURN;
    END;

    -- Tạo câu lệnh truy vấn động
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = '
    SELECT *
    FROM [' + @serverName + '].phonestore.[dbo].[BangLuong]
    WHERE maNV = @maNV';

    EXEC sp_executesql @sql,
        N'@maNV VARCHAR(50)',
        @maNV;
END

-----4.4 Tìm Bảng lương bằng maBL - sp_TimBangLuongByMaBLGoc
CREATE PROCEDURE sp_TimBangLuongByMaBLGoc
    @maBL VARCHAR(50),
    @maKho VARCHAR(15)
AS
BEGIN
    SET NOCOUNT ON;

    -- Xác định linked server
    DECLARE @serverName NVARCHAR(100);
    SET @serverName = CASE @maKho
        WHEN 'HN' THEN 'LINK3'
        WHEN 'DN' THEN 'LINK4'
        WHEN 'HCM' THEN 'LINK5'
        ELSE NULL
    END;

    IF @serverName IS NULL
    BEGIN
        RAISERROR(N'Không xác định được server cho mã kho %s.', 16, 1, @maKho);
        RETURN;
    END;

    -- Gọi stored procedure hoặc truy vấn trực tiếp đến bảng từ linked server
    DECLARE @sql NVARCHAR(MAX);
    SET @sql = '
    SELECT *
    FROM [' + @serverName + '].phonestore.[dbo].[BangLuong]
    WHERE maBL = @maBL';

    EXEC sp_executesql @sql,
        N'@maBL VARCHAR(50)',
        @maBL;
END

-----4.5 Lấy danh sách bảng lương của các kho - sp_LayDSTatCaBangLuong
CREATE PROCEDURE sp_LayDSTatCaBangLuong
AS
BEGIN
    SET NOCOUNT ON;

    -- Lấy dữ liệu bảng lương từ tất cả các linked server tương ứng với các kho
    SELECT 'HN' AS MaKho, *
    FROM LINK3.phonestore.dbo.BangLuong

    UNION ALL

    SELECT 'DN' AS MaKho, *
    FROM LINK4.phonestore.dbo.BangLuong

    UNION ALL

    SELECT 'HCM' AS MaKho, *
    FROM LINK5.phonestore.dbo.BangLuong;
END

---------------5. Quản lý đơn xin nghỉ
-----5.1 Thêm đơn xin nghỉ - sp_ThemDonXinNghiGoc
-----5.2 Sửa đơn xin nghỉ - sp_SuaDonXinNghiGoc
-----5.3 Xoá đơn xin nghỉ - sp_XoaDonXinNghiGoc
-----5.4 Lấy danh sách đơn theo maNV - sp_LayDSDonTheoMaNVGoc
-----5.5 Tìm đơn xin nghỉ bằng maDon - sp_TimDonTheoMaDonGoc
-----5.6 Lấy danh sách đơn tất cả các kho - sp_LayDSTatCaDonGoc
-----5.7 Lấy danh sách đơn của một kho - sp_LayDSDonGoc

























