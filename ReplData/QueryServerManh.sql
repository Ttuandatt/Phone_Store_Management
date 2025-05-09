/* !!!!!!!!!Với các procedure ở file này thì chạy trong server gốc sau đó phân tán tới các server, 
			procedure ko dùng cho ứng dụng toàn cục 
---------- 1. Quản lý nhân viên tại mảnh
----1.1 Lấy danh sách nhân viên - sp_LayDanhSachNhanVienKho
----1.2 Thêm nhân viên - sp_ThemNhanVienKho
----1.3 Sửa nhân viên - sp_SuaNhanVienKho
----1.4 Tìm kiếm nhân viên (theo từ khoá) - sp_TimNhanVienTheoTuKhoaKho
----1.5 Tìm kiếm nhân viên theo maNV - sp_TimNhanVienTheoMaNVKho
--------- 2. Quản lý chấm công tại mảnh
----2.1 Lấy danh sách chấm công - sp_LayDanhSachChamCongKho
----2.2 Thêm bảng chấm công - sp_ThemChamCongKho
----2.3 Sửa bảng chấm công - sp_SuaChamCongKho
----2.4 Tìm kiếm bảng chấm công theo mabcc - sp_TimBangCCTheoMaBCCKho
----2.5 Lấy danh sách bảng chấm công theo thời gian - sp_LayDSBangChamCongTheoTGKho
----2.6 Tìm danh sách bảng chấm công theo từ khoá (maBCC, maNV, tenNV) - sp_TimDanhSachCCTheoTuKhoaKho
---------- 3. Quản lý chi tiết chấm công tại mảnh
----3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCongKho
----3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCongKho
----3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCongKho
----3.4 Xoá danh sách chi tiết chấm công theo mabcc - sp_XoaChiTietChamCongTheoBCCKho
----3.5 Tìm kiếm chi tiết chấm công theo maCTCC - sp_LayChiTietChamCongTheoMaCTKho
----3.6 Lấy danh sách chi tiết chấm công theo maCC - sp_LayChiTietChamCongTheoMaCC
---------- 4. Quản lý bảng lương
----4.1 Thêm bảng lương	- sp_ThemBangLuongKho
----4.2 Sửa bảng lương - sp_SuaBangLuongKho
----4.3 Lấy danh sách bảng lương theo maNV - sp_LayDSBangLuongByMaNVKho
----4.4 Tìm Bảng lương bằng maBL - sp_TimBangLuongByMaBLKho
----4.5	Lấy danh sách bảng lương của kho - sp_LayDSBangLuongKho
---------- 5. Quản lý đơn xin nghỉ
----5.1 Thêm đơn xin nghỉ - sp_ThemDonXinNghiKho
----5.2 Sửa đơn xin nghỉ - sp_SuaDonXinNghiKho
----5.3 Xoá đơn xin nghỉ - sp_XoaDonXinNghiKho
----5.4 Lấy danh sách đơn theo maNV - sp_LayDSDonTheoMaNVKho
----5.5 Tìm đơn xin nghỉ bằng maDon - sp_TimDonTheoMaDonKho
----5.6 Lấy danh sách đơn của một kho - sp_LayDSDonKho
---------- 6. Quản lý phiếu nhập
---------- 7. Quản lý phiếu xuất 


use phonestore

-- Xem tất cả procedure đang có
SELECT *
FROM sys.procedures
WHERE name LIKE 'sp_%'

-- Kiểm tra tên server ta đang kết nối tới
select @@servername as ServerName

use phonestore;
SELECT * FROM NHANVIEN
delete from nhanvien where manv='NV021'

select * from nhanvien
where chinhanh='HN'
*/

--//////////
----1.1 Lấy danh sách nhân viên - sp_LayDanhSachNhanVienKho
CREATE PROCEDURE sp_LayDanhSachNhanVienKho
AS
BEGIN
    SET NOCOUNT ON;
    SELECT *
    FROM dbo.NHANVIEN;
END;
EXEC sp_LayDanhSachNhanVienKho
drop procedure sp_LayDanhSachNhanVienKho;

----1.2 Thêm nhân viên - sp_ThemNhanVienKho
drop procedure sp_ThemNhanVienKho;

CREATE PROCEDURE sp_ThemNhanVienKho
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
    SET NOCOUNT ON;

    DECLARE @servername VARCHAR(300)
    SELECT @servername = @@SERVERNAME

    IF @servername = 'damian\MSSQLSERVER03'
        SET @maKho = 'HN'
    ELSE IF @servername = 'damian\MSSQLSERVER04'
        SET @maKho = 'DN'
    ELSE IF @servername = 'damian\MSSQLSERVER05'
        SET @maKho = 'HCM'

    -- 1. Gọi tới server gốc để lấy mã NV duy nhất
    EXEC LINK6.phonestore.dbo.sp_TaoMaNhanVien @maNV OUTPUT

    -- 2. Kiểm tra mã NV đã tồn tại trong bảng cục bộ hay chưa
    IF EXISTS (SELECT 1 FROM NHANVIEN WHERE maNV = @maNV)
    BEGIN
        RAISERROR(N'Lỗi: Mã nhân viên đã tồn tại trong hệ thống cục bộ.', 16, 1)
        RETURN
    END

    -- 3. Chèn vào bảng NHANVIEN cục bộ
    INSERT INTO NHANVIEN
        (maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh)
    VALUES
        (@maNV, @hoTen, @ngaySinh, @gioiTinh, @diaChi, @sdt, @email, @hinhAnh, @mk, 'on', @maCV, @maKho)
END
--TEST
DECLARE @maNV VARCHAR(10);

EXEC sp_ThemNhanVienKho
    @hoTen = N'Trần B',
    @ngaySinh = '1998-10-15',
    @gioiTinh = N'Nam',
    @diaChi = N'346 Đường ABC, Hà Nội',
    @sdt = '0912345678',
    @email = 'tranb@example.com',
    @hinhAnh = NULL, -- Hoặc có thể dùng: 0xFFD8FFE000104A464946 nếu có ảnh
    @mk = '123',
    @maCV = 'CV001',  -- Đảm bảo giá trị này có tồn tại trong bảng CHUCVU
    @maKho = 'HN',   -- Sẽ được xác định tự động trong procedure
    @maNV = @maNV OUTPUT;

-- Xem mã nhân viên vừa được tạo
SELECT @maNV AS MaNhanVienMoi;



----1.3 Sửa nhân viên - sp_SuaNhanVienKho
drop procedure sp_SuaNhanVienKho;

CREATE PROCEDURE sp_SuaNhanVienKho
    @maNV VARCHAR(10),
    @hoTen NVARCHAR(100),
    @ngaySinh DATE,
    @gioiTinh NVARCHAR(3),
    @diaChi NVARCHAR(200),
    @sdt NVARCHAR(20),
    @email NVARCHAR(255),
    --@hinhAnh VARBINARY(MAX),
    @matKhau NVARCHAR(200),
    @trangThai NVARCHAR(10),
    @maCV NVARCHAR(50)
AS
BEGIN

    IF EXISTS (SELECT 1 FROM NHANVIEN WHERE maNV = @maNV)
    BEGIN
        UPDATE NHANVIEN
        SET hoTen = @hoTen,
            ngaySinh = @ngaySinh,
            gioiTinh = @gioiTinh,
            diaChi = @diaChi,
            sdt = @sdt,
            email = @email,
            --hinhAnh = @hinhAnh,
            matKhau = @matKhau,
            trangThai = @trangThai,
            maCV = @maCV
        WHERE maNV = @maNV;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã nhân viên không tồn tại tại chi nhánh này.', 16, 1);
    END
END

----1.4 Tìm kiếm nhân viên (theo từ khoá) - sp_TimNhanVienTheoTuKhoaKho
drop procedure sp_TimNhanVienTheoTuKhoaKho;

CREATE PROCEDURE sp_TimNhanVienTheoTuKhoaKho
    @tuKhoa NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM NHANVIEN
    WHERE maNV LIKE '%' + @tuKhoa + '%'
       OR hoTen LIKE N'%' + @tuKhoa + '%'
       OR sdt LIKE '%' + @tuKhoa + '%'
       OR email LIKE '%' + @tuKhoa + '%'
END

----1.5 Tìm kiếm nhân viên theo maNV - sp_TimNhanVienTheoMaNVKho
drop procedure sp_TimNhanVienTheoMaNVKho;

CREATE PROCEDURE sp_TimNhanVienTheoMaNVKho
    @maNV VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT 1 FROM NHANVIEN WHERE maNV = @maNV)
    BEGIN
        SELECT * FROM NHANVIEN WHERE maNV = @maNV
    END
    ELSE
    BEGIN
        RAISERROR(N'Không tìm thấy nhân viên với mã đã cho.', 16, 1)
    END
END

--------- 2. Quản lý chấm công tại mảnh
----2.1 Lấy danh sách chấm công - sp_LayDanhSachChamCongKho
drop procedure sp_LayDanhSachChamCongKho;

CREATE PROCEDURE sp_LayDanhSachChamCongKho
AS
BEGIN
    SET NOCOUNT ON;
    SELECT *
    FROM dbo.BANGCHAMCONG;
END;

----2.2 Thêm bảng chấm công - sp_ThemChamCongKho
CREATE PROCEDURE sp_ThemChamCongKho
    @maBCC VARCHAR(50),
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

    -- 1. Kiểm tra xem mã chấm công đã tồn tại chưa
    IF EXISTS (
        SELECT 1 FROM BANGCHAMCONG WHERE maBCC = @maBCC
    )
    BEGIN
        RAISERROR(N'Mã chấm công đã tồn tại.', 16, 1);
        RETURN;
    END

    -- 2. Kiểm tra xem đã có chấm công cho nhân viên này trong tháng/năm chưa
    IF EXISTS (
        SELECT 1 FROM BANGCHAMCONG
        WHERE maNV = @maNV AND thangCC = @thangCC AND namCC = @namCC
    )
    BEGIN
        RAISERROR(N'Chấm công cho nhân viên này trong tháng/năm đã tồn tại.', 16, 1);
        RETURN;
    END

    -- 3. Thêm bản ghi chấm công
    INSERT INTO BANGCHAMCONG (
        maBCC, thangCC, namCC,
        soNgayLam, soNgayNghiKP, soNPCoLuong,
        soNPKhongLuong, soGioOTNgayThuong, soGioOTNgayLe,
        soGioOTCN, maNV)
    VALUES (
        @maBCC, @thangCC, @namCC,
        @soNgayLam, @soNgayNghiKP, @soNPCoLuong,
        @soNPKhongLuong, @soGioOTNgayThuong, @soGioOTNgayLe,
        @soGioOTCN, @maNV);
END


----2.3 Sửa bảng chấm công - sp_SuaChamCongKho
CREATE PROCEDURE sp_SuaChamCongKho
    @maBCC VARCHAR(50),
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

    IF EXISTS (SELECT 1 FROM BANGCHAMCONG WHERE maBCC = @maBCC)
    BEGIN
        UPDATE BANGCHAMCONG
        SET 
            thangCC = @thangCC,
            namCC = @namCC,
            soNgayLam = @soNgayLam,
            soNgayNghiKP = @soNgayNghiKP,
            soNPCoLuong = @soNPCoLuong,
            soNPKhongLuong = @soNPKhongLuong,
            soGioOTNgayThuong = @soGioOTNgayThuong,
            soGioOTNgayLe = @soGioOTNgayLe,
            soGioOTCN = @soGioOTCN,
            maNV = @maNV
        WHERE maBCC = @maBCC;
    END
    ELSE
    BEGIN
        RAISERROR(N'Mã chấm công không tồn tại tại chi nhánh này.', 16, 1);
    END
END

----2.4 Tìm kiếm bảng chấm công theo mabcc - sp_TimBangCCTheoMaBCCKho
CREATE PROCEDURE sp_TimBangCCTheoMaBCCKho
    @maBCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (SELECT 1 FROM BANGCHAMCONG WHERE maBCC = @maBCC)
    BEGIN
        SELECT * FROM BANGCHAMCONG WHERE maBCC = @maBCC;
    END
    ELSE
    BEGIN
        RAISERROR(N'Không tìm thấy bảng chấm công với mã đã nhập.', 16, 1);
    END
END


----2.5 Lấy danh sách bảng chấm công theo thời gian - sp_LayDSBangChamCongTheoTGKho
CREATE PROCEDURE sp_LayDSBangChamCongTheoTGKho
    @thangCC INT,
    @namCC INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM BANGCHAMCONG
    WHERE thangCC = @thangCC AND namCC = @namCC;
END


----2.6 Tìm danh sách bảng chấm công theo từ khoá (maBCC, maNV, tenNV) - sp_TimDanhSachCCTheoTuKhoaKho
CREATE PROCEDURE sp_TimDanhSachCCTheoTuKhoaKho
    @tuKhoa NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT cc.*, nv.hoTen
    FROM BANGCHAMCONG cc
    JOIN NHANVIEN nv ON cc.maNV = nv.maNV
    WHERE cc.maBCC LIKE '%' + @tuKhoa + '%'
       OR cc.maNV LIKE '%' + @tuKhoa + '%'
       OR nv.hoTen LIKE N'%' + @tuKhoa + '%';
END

---------- 3. Quản lý chi tiết chấm công tại mảnh
----3.1 Thêm chi tiết chấm công - sp_ThemChiTietChamCongKho
CREATE PROCEDURE sp_ThemChiTietChamCongKho
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(255),
    @chiTiet NVARCHAR(255) = NULL,
    @maBCC VARCHAR(50),
    @soGioOT FLOAT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra trùng khóa chính
    IF EXISTS (SELECT 1 FROM CHITIETCHAMCONG WHERE maCTCC = @maCTCC)
    BEGIN
        RAISERROR(N'Mã chi tiết chấm công đã tồn tại. Không thể thêm trùng.', 16, 1);
        RETURN;
    END

    -- Thêm mới nếu không trùng
    INSERT INTO CHITIETCHAMCONG (
        maCTCC, ngayTao, loaiChamCong, chiTiet, maBCC, soGioOT
    )
    VALUES (
        @maCTCC, @ngayTao, @loaiChamCong, @chiTiet, @maBCC, @soGioOT
    );
END



----3.2 Sửa chi tiết chấm công - sp_SuaChiTietChamCongKho
CREATE PROCEDURE sp_SuaChiTietChamCongKho
    @maCTCC VARCHAR(50),
    @ngayTao DATE,
    @loaiChamCong NVARCHAR(255),
    @chiTiet NVARCHAR(255) = NULL,
    @maBCC VARCHAR(50),
    @soGioOT FLOAT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra tồn tại
    IF NOT EXISTS (SELECT 1 FROM CHITIETCHAMCONG WHERE maCTCC = @maCTCC)
    BEGIN
        RAISERROR(N'Mã chi tiết chấm công không tồn tại. Không thể sửa.', 16, 1);
        RETURN;
    END

    -- Cập nhật thông tin
    UPDATE CHITIETCHAMCONG
    SET 
        ngayTao = @ngayTao,
        loaiChamCong = @loaiChamCong,
        chiTiet = @chiTiet,
        maBCC = @maBCC,
        soGioOT = @soGioOT
    WHERE maCTCC = @maCTCC;
END


----3.3 Xoá chi tiết chấm công - sp_XoaChiTietChamCongKho
CREATE PROCEDURE sp_XoaChiTietChamCongKho
    @maCTCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    -- Kiểm tra tồn tại trước khi xóa
    IF NOT EXISTS (SELECT 1 FROM CHITIETCHAMCONG WHERE maCTCC = @maCTCC)
    BEGIN
        RAISERROR(N'Mã chi tiết chấm công không tồn tại. Không thể xóa.', 16, 1);
        RETURN;
    END

    -- Thực hiện xóa
    DELETE FROM CHITIETCHAMCONG
    WHERE maCTCC = @maCTCC;
END

----3.4 Xoá danh sách chi tiết chấm công theo mabcc - sp_XoaChiTietChamCongTheoBCCKho
CREATE PROCEDURE sp_XoaChiTietChamCongTheoBCCKho
    @maBCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    DELETE FROM CHITIETCHAMCONG
    WHERE maBCC = @maBCC;
END

----3.5 Tìm kiếm chi tiết chấm công theo maCTCC - sp_LayChiTietChamCongTheoMaCTKho
drop procedure sp_LayChiTietChamCongTheoMaCTKho

CREATE PROCEDURE sp_LayChiTietChamCongTheoMaCTKho
    @maCTCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM CHITIETCHAMCONG
    WHERE maCTCC = @maCTCC;
END


----3.6 Lấy danh sách chi tiết chấm công theo maCC - sp_LayChiTietChamCongTheoMaCCKho
drop procedure sp_LayChiTietChamCongTheoMaCCKho

CREATE PROCEDURE sp_LayChiTietChamCongTheoMaCCKho
    @maCC VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM CHITIETCHAMCONG
    WHERE maBCC = @maCC;
END
EXEC sp_LayChiTietChamCongTheoMaCCKho 'CC042025NV002'

select * from chitietchamcong

---------- 4. Quản lý bảng lương
----4.1 Thêm bảng lương	- sp_ThemBangLuongKho
CREATE PROCEDURE sp_ThemBangLuongKho_PhanManh
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
    @maNV VARCHAR(50)
AS
BEGIN
    INSERT INTO BANGLUONG (
        maBL, thangLuong, namLuong, luongCB, heSo,
        phuCapAnTrua, phuCapDiLai, thuong,
        bhxh, bhyt, bhtn, thueTNCN,
        tamUng, thuNhan, maNV)
    VALUES (
        @maBL, @thangLuong, @namLuong, @luongCB, @heSo,
        @phuCapAnTrua, @phuCapDiLai, @thuong,
        @bhxh, @bhyt, @bhtn, @thueTNCN,
        @tamUng, @thuNhan, @maNV);
END
----4.2 Sửa bảng lương - sp_SuaBangLuongKho
CREATE PROCEDURE sp_SuaBangLuongKho
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
    @thuNhan FLOAT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    IF NOT EXISTS (SELECT 1 FROM BangLuong WHERE maBL = @maBL)
    BEGIN
        RAISERROR(N'Không tồn tại bảng lương với mã: %s.', 16, 1, @maBL);
        RETURN;
    END

    UPDATE BangLuong
    SET luongCB = @luongCB,
        heSo = @heSo,
        phuCapAnTrua = @phuCapAnTrua,
        phuCapDiLai = @phuCapDiLai,
        thuong = @thuong,
        bhxh = @bhxh,
        bhyt = @bhyt,
        bhtn = @bhtn,
        thueTNCN = @thueTNCN,
        tamUng = @tamUng,
        thuNhan = @thuNhan
    WHERE maBL = @maBL;
END

----4.3 Lấy danh sách bảng lương theo maNV - sp_LayDSBangLuongByMaNVKho
CREATE PROCEDURE sp_LayDSBangLuongByMaNVKho
    @maNV VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM BangLuong
    WHERE maNV = @maNV;
END

----4.4 Tìm Bảng lương bằng maBL - sp_TimBangLuongByMaBLKho
CREATE PROCEDURE sp_TimBangLuongByMaBLKho
    @maBL VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT *
    FROM BangLuong
    WHERE maBL = @maBL;
END

---------- 5. Quản lý đơn xin nghỉ
----5.1 Thêm đơn xin nghỉ - sp_ThemDonKho
----5.2 Sửa đơn xin nghỉ - sp_SuaDonKho
----5.3 Xoá đơn xin nghỉ - sp_XoaDonKho
----5.4 Lấy danh sách đơn theo maNV - sp_LayDSDonTheoMaNVKho
----5.5 Tìm đơn xin nghỉ bằng maDon - sp_TimDonTheoMaDonKho
----5.6 Lấy danh sách đơn của một kho - sp_LayDSDonKho