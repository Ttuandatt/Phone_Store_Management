---====================================================================================
-- Store Procedure cho phiếu nhập, phiếu xuất, ctpn, ctpx, pbsp, kho_pbspa
SELECT @@SERVERNAME AS ServerName;

-- Liệt kê các Stored Procedure đang có trong database
SELECT *
FROM sys.procedures
WHERE name='ThemPhieuNhap';


use phonestore
select * from PHIEUNHAP
---====================================================================================


--1. Thêm phiếu nhập
DROP PROCEDURE ThemPhieuNhap
CREATE PROCEDURE ThemPhieuNhap (
	@maPN VARCHAR(50),
    @NgayTao DATE,
    @tongTien float,
    @trangThai NVARCHAR(50),
	@maNV VARCHAR(50),
	@maKho VARCHAR(50),
	@maNCC VARCHAR(50),
	@ChiTietJSON NVARCHAR(MAX)

)
AS
BEGIN
	DECLARE @ChiTietNhapTemp TABLE (
			maPBSP VARCHAR(50),
			soLuong INT,
			giaNhap decimal(18,2) -- Bao gồm cả giá xuất nếu cần dùng sau này
		);
	INSERT INTO @ChiTietNhapTemp (maPBSP, soLuong, giaNhap)
	SELECT
		JSON_VALUE(j.value, '$.maPBSP'),
		JSON_VALUE(j.value, '$.soLuong'),
		JSON_VALUE(j.value, '$.giaNhap')
	FROM OPENJSON(@ChiTietJSON) AS j;

	INSERT INTO PhieuNhap (maPN, NgayTao, tongTien, trangThai, maNV, maKho, maNCC)
		VALUES (@maPN, @NgayTao, @tongTien, @trangThai, @maNV, @maKho, @maNCC);

	-- Chèn dữ liệu từ TVP vào bảng ChiTietPhieuNhap
	INSERT INTO CTPN (maPN, soLuong, giaNhap, maPBSP)
	SELECT
		@maPN, -- Sử dụng @maPN từ phiếu nhập chính
		JSON_VALUE(j.value, '$.soLuong'), -- Lấy giá trị soLuong từ JSON
		JSON_VALUE(j.value, '$.giaNhap'), -- Lấy giá trị giaNhap từ JSON
		JSON_VALUE(j.value, '$.maPBSP') -- Lấy giá trị maPBSP từ JSON
	FROM OPENJSON(@ChiTietJSON) AS j; -- Sử dụng OPENJSON để phân tích chuỗi JSON

	IF @trangThai = N'Đã nhận hàng'
	BEGIN 
		UPDATE kp
		SET kp.soLuong = kp.soLuong + ctn.soLuong
		FROM KHO_PBSP AS kp
		JOIN @ChiTietNhapTemp AS ctn ON kp.maPBSP = ctn.maPBSP;
	END
END

DROP TYPE CTPNType;
CREATE TYPE CTPNType AS TABLE (
    soLuong INT,
    giaNhap decimal(18,2),
    maPBSP VARCHAR(50)
);

DROP PROCEDURE themPN_Goc
CREATE PROCEDURE themPN_Goc (
	@maPN VARCHAR(50),
    @NgayTao DATE,
    @tongTien float,
    @trangThai NVARCHAR(50),
	@maNV VARCHAR(50),
	@maKho VARCHAR(50),
	@maNCC VARCHAR(50),
	@ChiTietPhieuNhap CTPNType READONLY
)
AS
BEGIN
	DECLARE @remoteMaPN VARCHAR(100);
	DECLARE @InvalidProductCount INT;
	-- Khai báo biến để lưu dữ liệu chi tiết dưới dạng chuỗi JSON
	DECLARE @ChiTietJSON NVARCHAR(MAX);

	-- Chuyển đổi dữ liệu từ TVP sang chuỗi JSON
	SELECT @ChiTietJSON = (
		SELECT soLuong, giaNhap, maPBSP
		FROM @ChiTietPhieuNhap
		FOR JSON PATH -- Sử dụng FOR JSON PATH để tạo chuỗi JSON
	);
	-- Kiểm tra mã kho và thực hiện chèn dữ liệu vào linked server tương ứng
	IF @maKho = 'HN'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK3 không
		IF EXISTS (SELECT 1 FROM LINK3.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPN = 'PN_HN' + @maPN;
			SELECT @InvalidProductCount = COUNT(T1.maPBSP)
			FROM @ChiTietPhieuNhap AS T1
			LEFT JOIN LINK3.phonestore.DBO.KHO_PBSP AS T2 ON T1.maPBSP = T2.maPBSP
			WHERE T2.maPBSP IS NULL;

			-- Thực thi procedure ThemPhieuNhap trên LINK3 với biến đã nối chuỗi
			IF @InvalidProductCount > 0
			BEGIN
				-- Báo lỗi nếu có mã sản phẩm không tồn tại trên LINK3
				RAISERROR('Có mã sản phẩm không hợp lệ trong chi tiết phiếu nhập cho kho HN.', 16, 1);
				RETURN 1;
			END
			Exec LINK3.phonestore.DBO.ThemPhieuNhap @remoteMaPN, @NgayTao, @tongTien, @trangThai, @maNV, @maKho, @maNCC, @ChiTietJSON;
		END
		ELSE
		BEGIN
			-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK3
			RAISERROR('Mã NV không hợp lệ trên kho HN.', 16, 1);
			RETURN 1;
		END
	END
	ELSE IF @maKho = 'DN'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK4 không
		IF EXISTS (SELECT 1 FROM LINK4.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPN = 'PN_DN' + @maPN;
			-- Thực thi procedure ThemPhieuNhap trên LINK4 với biến đã nối chuỗi
			Exec LINK4.phonestore.DBO.ThemPhieuNhap @remoteMaPN, @NgayTao, @tongTien, @trangThai, @maNV, @maKho, @maNCC, @ChiTietJSON;
		END
		ELSE
		BEGIN
			-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK4
			RAISERROR('Mã NV không hợp lệ trên kho DN.', 16, 1);
			RETURN 1;
		END
	END
	ELSE IF @maKho = 'HCM'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK5 không
		IF EXISTS (SELECT 1 FROM LINK5.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPN = 'PN_HCM' + @maPN;
			-- Thực thi procedure ThemPhieuNhap trên LINK5 với biến đã nối chuỗi
			Exec LINK5.phonestore.DBO.ThemPhieuNhap @remoteMaPN, @NgayTao, @tongTien, @trangThai, @maNV, @maKho, @maNCC, @ChiTietJSON;
		END
		ELSE
		BEGIN
			-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK5
			RAISERROR('Mã NV không hợp lệ trên kho HCM.', 16, 1);
			RETURN 1;
		END
	END
	ELSE
	BEGIN
		-- Báo lỗi nếu mã kho không hợp lệ
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END
END

DECLARE @ChiTietNhap CTPNType;

-- Chèn dữ liệu vào TVP
INSERT INTO @ChiTietNhap (soLuong, giaNhap, maPBSP)
VALUES
    (10, 50000, 'PBSP001'),
    (5, 100000, 'PBSP004'),
    (10, 50000, 'PBSP005');

-- Chuyển dữ liệu TVP thành chuỗi JSON
DECLARE @ChiTietJSON NVARCHAR(MAX);
SELECT @ChiTietJSON = (SELECT soLuong, giaNhap, maPBSP
                       FROM @ChiTietNhap
                       FOR JSON PATH);

-- Gọi thủ tục qua Linked Server, truyền chuỗi JSON
EXEC LINK0.phonestore.dbo.themPN_Goc 
    '003', '2025-02-02', 100000, N'Chờ xác nhận', 'NV005', 'HN', 'NCC002', @ChiTietJSON;

SELECT * FROM LINK3.phonestore.DBO.PHIEUNHAP
SELECT * FROM LINK3.phonestore.DBO.CTPN
SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP

DELETE FROM LINK3.phonestore.DBO.PHIEUNHAP WHERE maPN = 'PN_HN003'


-- 2. Cập nhật phiếu nhập

-- =============================================
-- Script này cần chạy trên các LINKED SERVER (LINK3, LINK4, LINK5)
-- Cập nhật stored procedure CapNhatPhieuNhap trên Linked Server
-- Procedure này thực hiện cập nhật thực tế trên DB phân mảnh
-- Đã sửa lỗi "không tìm thấy cột giaNhap" trong MERGE KHO_PBSP
-- Đã sửa điều kiện MERGE ON để bao gồm maKho
-- =============================================

IF OBJECT_ID('CapNhatPhieuNhap', 'P') IS NOT NULL
    DROP PROCEDURE CapNhatPhieuNhap;
GO

CREATE PROCEDURE CapNhatPhieuNhap (
	@maPN VARCHAR(100), -- Mã PN đầy đủ (ví dụ: PN_HN003) <-- Đổi kiểu dữ liệu sang 100 cho chắc
    @maKho VARCHAR(50), -- <-- THÊM tham số mã kho để dùng cho bảng KHO_PBSP
    @trangThai NVARCHAR(50), -- Trạng thái MỚI được phép (đã kiểm tra từ server gốc)
	@maNV VARCHAR(50), -- Không sử dụng trong logic này nhưng giữ lại tham số
	@maNCC VARCHAR(50), -- Không sử dụng trong logic này nhưng giữ lại tham số
	-- Thêm tham số JSON cho chi tiết phiếu nhập cần cập nhật/thêm/xóa
	@ChiTietJSON NVARCHAR(MAX) -- Nhận dữ liệu chi tiết dưới dạng chuỗi JSON
)
AS
BEGIN

	-- Bắt đầu giao dịch để đảm bảo tính toàn vẹn
	BEGIN TRANSACTION;

	BEGIN TRY
		-- Xóa tất cả chi tiết cũ của phiếu nhập này
		-- và chèn lại toàn bộ chi tiết mới từ JSON
		-- Thao tác này cho phép sửa đổi số lượng, thêm hoặc xóa sản phẩm
		DELETE FROM CTPN WHERE maPN = @maPN;

		-- Chèn dữ liệu chi tiết mới từ chuỗi JSON vào bảng CTPN
		-- Bảng CTPN có cột giaNhap nên lệnh này giữ nguyên
		INSERT INTO CTPN (maPN, soLuong, giaNhap, maPBSP)
		SELECT
			@maPN, -- Sử dụng @maPN từ phiếu nhập chính
			JSON_VALUE(j.value, '$.soLuong'), -- Lấy giá trị soLuong từ JSON
			JSON_VALUE(j.value, '$.giaNhap'), -- Lấy giá trị giaNhap từ JSON
			JSON_VALUE(j.value, '$.maPBSP') -- Lấy giá trị maPBSP từ JSON
		FROM OPENJSON(@ChiTietJSON) AS j;

		-- Tính toán lại tổng tiền dựa trên chi tiết phiếu nhập mới
		DECLARE @calculatedTongTien float;
		-- Đảm bảo SUM không trả về NULL nếu không có chi tiết nào
		-- CTPN có cột giaNhap nên tính toán này vẫn đúng
		SELECT @calculatedTongTien = ISNULL(SUM(CAST(soLuong AS float) * giaNhap), 0)
		FROM CTPN
		WHERE maPN = @maPN;

		-- Cập nhật lại tổng tiền và trạng thái trong bảng PhieuNhap
		UPDATE PhieuNhap
		SET
			tongTien = @calculatedTongTien, -- Cập nhật tổng tiền đã tính lại
			trangThai = @trangThai -- Cập nhật trạng thái mới (đã được kiểm tra hợp lệ ở server gốc)
		WHERE maPN = @maPN;

		-- Cập nhật số lượng trong bảng KHO_PBSP
		-- Chỉ thực hiện khi trạng thái MỚI là 'Đã nhận hàng'
		IF @trangThai = N'Đã nhận hàng'
		BEGIN
			-- Khai báo biến bảng tạm để lưu chi tiết nhập đã phân tích từ JSON
			DECLARE @ChiTietNhapTemp TABLE (
				maPBSP VARCHAR(50),
				soLuongNhap INT,
				giaNhap decimal(18,2) -- Giữ lại giaNhap trong biến tạm (dù không dùng khi merge vào KHO_PBSP)
			);
			INSERT INTO @ChiTietNhapTemp (maPBSP, soLuongNhap, giaNhap)
			SELECT
				JSON_VALUE(j.value, '$.maPBSP'),
				JSON_VALUE(j.value, '$.soLuong'),
				JSON_VALUE(j.value, '$.giaNhap')
			FROM OPENJSON(@ChiTietJSON) AS j;

			-- Sử dụng MERGE để cập nhật hoặc thêm mới sản phẩm vào bảng KHO_PBSP
			-- ĐÃ SỬA: Chỉ thao tác trên các cột tồn tại trong KHO_PBSP (maKho, maPBSP, soLuong)
            -- ĐÃ SỬA: Điều kiện ON bao gồm maKho và maPBSP
			MERGE KHO_PBSP AS Target
			-- Điều kiện JOIN phải bao gồm maKho và maPBSP
			USING @ChiTietNhapTemp AS Source ON Target.maKho = @maKho AND Target.maPBSP = Source.maPBSP -- <-- ĐÃ SỬA ĐIỀU KIỆN ON
			WHEN MATCHED THEN
				-- Nếu maPBSP đã tồn tại trong kho, chỉ tăng số lượng. KHÔNG cập nhật giaNhap.
				UPDATE SET
					Target.soLuong = Target.soLuong + Source.soLuongNhap
			WHEN NOT MATCHED THEN
				-- Nếu maPBSP chưa tồn tại trong kho, thêm mới.
				-- Chỉ chèn maKho, maPBSP, soLuong. KHÔNG chèn giaNhap.
				INSERT (maKho, maPBSP, soLuong)
				VALUES (@maKho, Source.maPBSP, Source.soLuongNhap);

		END -- Kết thúc IF @trangThai = N'Đã nhận hàng'

		-- Nếu mọi thứ thành công, commit giao dịch
		COMMIT TRANSACTION;

	END TRY
	BEGIN CATCH
		-- Nếu có lỗi, rollback giao dịch
		IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

		-- Ném lại lỗi để server gốc nhận được
		THROW;

	END CATCH
END
GO

-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Giữ nguyên định nghĩa User-Defined Table Type CTPNType
-- Kiểu này được sử dụng trên server gốc để nhận dữ liệu chi tiết từ ứng dụng
-- =============================================

--IF TYPE_ID(N'CTPNType') IS NOT NULL
--   DROP TYPE CTPNType;
--GO

CREATE TYPE CTPNType AS TABLE (
    soLuong INT,
    giaNhap decimal(18,2),
    maPBSP VARCHAR(50)
);
GO

-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Cập nhật stored procedure CapNhatPN_Goc trên Server Gốc
-- Procedure này thực hiện kiểm tra trạng thái, quyền hạn và gọi CapNhatPhieuNhap từ xa
-- Đã sửa lỗi sử dụng mã PN đầy đủ, thêm lại tham số maNV, maNCC
-- Đã sửa định nghĩa tham số @paramDefinition và lệnh gọi sp_executesql
-- Đã sửa kiểm tra @maCV về 'QL'
-- =============================================

IF OBJECT_ID('CapNhatPN_Goc', 'P') IS NOT NULL
    DROP PROCEDURE CapNhatPN_Goc;
GO

CREATE PROCEDURE CapNhatPN_Goc (
	@maPN VARCHAR(50), -- Mã PN gốc (chưa có tiền tố kho)
    @trangThai NVARCHAR(50), -- Trạng thái MỚI được yêu cầu
    @maNV VARCHAR(50), -- <-- THÊM LẠI tham số mã NV
    @maNCC VARCHAR(50), -- <-- THÊM LẠI tham số mã NCC
	@maKho VARCHAR(50), -- Tham số mã kho cần giữ lại
	@maCV VARCHAR(50), -- Mã chức vụ của người thực hiện ('NV' hoặc 'QL')
	@ChiTietPhieuNhap CTPNType READONLY -- Dữ liệu chi tiết MỚI (cho phép rỗng nếu chỉ đổi trạng thái mà không sửa chi tiết)
)
AS
BEGIN
	-- Khai báo biến để lưu trạng thái HIỆN TẠI của phiếu nhập trên linked server
	DECLARE @currentTrangThai NVARCHAR(50);
	-- Khai báo biến để lưu dữ liệu chi tiết MỚI dưới dạng chuỗi JSON
	DECLARE @ChiTietJSON NVARCHAR(MAX);
	-- Khai báo biến để lưu chuỗi lệnh SQL động
	DECLARE @sql NVARCHAR(MAX);
	-- Khai báo biến để lưu định nghĩa tham số cho sp_executesql khi gọi Linked Server
	DECLARE @paramDefinition NVARCHAR(MAX);
	-- Biến để lưu tên linked server
	DECLARE @linkedServerName VARCHAR(50);

	-- Kiểm tra mã kho hợp lệ và xác định linked server
	IF @maKho = 'HN'
		SET @linkedServerName = 'LINK3';
	ELSE IF @maKho = 'DN'
		SET @linkedServerName = 'LINK4';
	ELSE IF @maKho = 'HCM'
		SET @linkedServerName = 'LINK5';
	ELSE
	BEGIN
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END
	-- Lấy trạng thái HIỆN TẠI của phiếu nhập từ linked server
	-- Sử dụng sp_executesql để lấy dữ liệu từ xa
	SET @sql = N'SELECT @currentTrangThai = trangThai FROM [' + @linkedServerName + '].phonestore.DBO.PhieuNhap WHERE maPN = @maPN;';
	SET @paramDefinition = N'@currentTrangThai NVARCHAR(50) OUTPUT, @maPN VARCHAR(100)'; -- SỬA: Kiểu dữ liệu maPN

	EXEC sp_executesql @sql, @paramDefinition, @currentTrangThai OUTPUT, @maPN; -- SỬA: Truyền @maPN

	-- Kiểm tra xem phiếu nhập có tồn tại không
	IF @currentTrangThai IS NULL
	BEGIN
		RAISERROR('Phiếu nhập ''%s'' không tồn tại.', 16, 1, @maPN); -- SỬA: Báo lỗi dùng @maPN
		RETURN 1;
	END

	-- =============================================
	-- BẮT ĐẦU KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN THEO YÊU CẦU
	-- Logic này giữ nguyên nhưng đã sửa kiểm tra @maCV thành 'QL'
	-- =============================================

	-- Quy tắc 1: "Đã nhận hàng" thì khỏi xoá, sửa
	IF @currentTrangThai = N'Đã nhận hàng'
	BEGIN
		RAISERROR('Không được cập nhật phiếu nhập đã nhận hàng.', 16, 1);
		RETURN 1;
	END

	DECLARE @allowUpdate BIT = 0;

	IF @currentTrangThai = N'Chờ xác nhận'
	BEGIN
		IF @trangThai = N'Chờ xác nhận'
		BEGIN
			SET @allowUpdate = 1;
		END
		ELSE IF @trangThai = N'Đã xác nhận'
		BEGIN
			-- Chỉ Quản lý kho (QL) được đổi sang trạng thái "Đã xác nhận"
			IF @maCV = 'CV001' -- SỬA: Kiểm tra @maCV = 'QL'
				SET @allowUpdate = 1;
			ELSE
			BEGIN
				RAISERROR('Bạn không có quyền xác nhận phiếu nhập.', 16, 1);
				RETURN 1;
			END
		END
		ELSE
		BEGIN
			RAISERROR('Không thể chuyển trạng thái từ ''Chờ xác nhận'' sang ''%s''.', 16, 1, @trangThai);
			RETURN 1;
		END
	END
	ELSE IF @currentTrangThai = N'Đã xác nhận'
	BEGIN
		-- Ở trạng thái "Đã xác nhận", chỉ QL kho mới được phép thao tác
		IF @maCV = 'CV001' -- SỬA: Kiểm tra @maCV = 'QL'
		BEGIN
			IF @trangThai = N'Đã xác nhận'
			BEGIN
				SET @allowUpdate = 1;
			END
			ELSE IF @trangThai = N'Đã nhận hàng'
			BEGIN
				SET @allowUpdate = 1;
			END
			ELSE
			BEGIN
				RAISERROR('Không thể chuyển trạng thái từ ''Đã xác nhận'' sang ''%s'' đối với Quản lý kho.', 16, 1, @trangThai);
				RETURN 1;
			END
		END
		ELSE -- Nếu không phải QL kho cố gắng thao tác trên PN 'Đã xác nhận'
		BEGIN
			RAISERROR('Bạn không có quyền cập nhật phiếu nhập ở trạng thái ''Đã xác nhận''.', 16, 1);
			RETURN 1;
		END
	END
	ELSE
	BEGIN
		RAISERROR('Trạng thái phiếu nhập hiện tại ''%s'' không hợp lệ để cập nhật.', 16, 1, @currentTrangThai);
		RETURN 1;
	END

	-- =============================================
	-- KẾT THÚC KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN
	-- =============================================

	IF @allowUpdate = 1
	BEGIN
		-- Chuyển đổi dữ liệu chi tiết MỚI từ TVP sang chuỗi JSON
		IF EXISTS (SELECT 1 FROM @ChiTietPhieuNhap)
		BEGIN
			SELECT @ChiTietJSON = (
				SELECT soLuong, giaNhap, maPBSP
				FROM @ChiTietPhieuNhap
				FOR JSON PATH -- Giữ lại FOR JSON PATH để tạo mảng
			);
		END
		ELSE
		BEGIN
			-- Nếu không có chi tiết nào được gửi, gửi JSON mảng rỗng.
			SET @ChiTietJSON = '[]';
		END

		-- Sử dụng sp_executesql để gọi procedure CapNhatPhieuNhap trên linked server
		-- SỬA: Thêm tham số @maKho, @maNV, @maNCC vào lệnh gọi
		SET @sql = N'EXEC [' + @linkedServerName + '].phonestore.DBO.CapNhatPhieuNhap @maPN, @maKho, @trangThai, @maNV, @maNCC, @ChiTietJSON';
        -- SỬA: Định nghĩa tham số bao gồm tất cả các tham số sẽ truyền
		SET @paramDefinition = N'@maPN VARCHAR(100), @maKho VARCHAR(50), @trangThai NVARCHAR(50), @maNV VARCHAR(50), @maNCC VARCHAR(50), @ChiTietJSON NVARCHAR(MAX)'; -- SỬA: Định nghĩa đủ tham số

		-- Thực thi lệnh gọi từ xa
		-- SỬA: Truyền đủ giá trị cho tất cả các tham số đã định nghĩa
		EXEC sp_executesql @sql, @paramDefinition,
			@maPN,   -- SỬA: Truyền mã PN đầy đủ
            @maKho = @maKho,     -- SỬA: Truyền mã kho
			@trangThai = @trangThai,
			@maNV = @maNV,
			@maNCC = @maNCC,
			@ChiTietJSON = @ChiTietJSON;

	END

END
GO

SELECT * FROM LINK3.phonestore.DBO.PHIEUNHAP
SELECT * FROM LINK3.phonestore.DBO.CTPN
SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP


-- =============================================
-- Ví dụ thực thi procedure CapNhatPN_Goc trên Server Gốc
-- Đã cập nhật các ví dụ để phù hợp với sửa đổi
-- =============================================

-- Khai báo một biến kiểu TVP cho chi tiết cập nhật 
DECLARE @ChiTietNhapCapNhat CTPNType;

-- Thêm dữ liệu chi tiết CẬP NHẬT vào biến TVP
-- Đây là toàn bộ danh sách chi tiết MỚI cho phiếu nhập cần cập nhật.
-- Lưu ý: Nếu bạn muốn xóa hết chi tiết, hãy khai báo @ChiTietNhapCapNhat nhưng không INSERT dòng nào vào đó.
INSERT INTO @ChiTietNhapCapNhat (soLuong, giaNhap, maPBSP) VALUES
(10, 60000, 'PBSP001'), -- Cập nhật số lượng/giá cho sản phẩm đã có
(5, 120000, 'PBSP004'), -- Cập nhật số lượng/giá cho sản phẩm đã có
(10, 70000, 'PBSP005'); -- Thêm sản phẩm mới (nếu PBSP015 tồn tại trên kho đích)

-- GIẢ ĐỊNH:
-- Phiếu nhập '003' ở kho 'HN' đang có trạng thái là 'Chờ xác nhận'
-- Phiếu nhập '004' ở kho 'HN' đang có trạng thái là 'Đã xác nhận'
-- Phiếu nhập '005' ở kho 'HN' đang có trạng thái là 'Đã nhận hàng'
-- Có 2 mã chức vụ: 'NV' (Nhân viên), 'QL' (Quản lý Kho)
/*
-- Ví dụ 1: NV cập nhật chi tiết phiếu nhập '003' ở trạng thái 'Chờ xác nhận'. Trạng thái yêu cầu vẫn là 'Chờ xác nhận'. (ĐƯỢC)
EXEC CapNhatPN_Goc
	@maPN = 'PN_HN004',
	@trangThai = N'Chờ xác nhận', -- Trạng thái MỚI = Trạng thái HIỆN TẠI
	@maNV = 'NV005',
	@maNCC = 'NCC001',
	@maKho = 'HN',
	@maCV = 'CV003', -- Nhân viên
	@ChiTietPhieuNhap = @ChiTietNhapCapNhat;
-- Kết quả mong đợi: Cập nhật thành công chi tiết và tổng tiền trên LINK3. Trạng thái vẫn là 'Chờ xác nhận'.

-- Ví dụ 3: QL đổi trạng thái phiếu nhập '003' từ 'Chờ xác nhận' sang 'Đã xác nhận'. (ĐƯỢC)
-- Giả định phiếu '003' vẫn đang 'Chờ xác nhận' sau Vd 2
EXEC CapNhatPN_Goc
	@maPN = 'PN_HN004',
	@trangThai = N'Đã xác nhận', -- Trạng thái MỚI
	@maNV = 'NV002', -- Mã NV của QL
	@maNCC = 'NCC002',
	@maKho = 'HN',
	@maCV = 'CV001', -- Quản lý Kho
	@ChiTietPhieuNhap = @ChiTietNhapCapNhat; -- Có thể gửi chi tiết để cập nhật cùng lúc hoặc gửi rỗng (thực tế nên gửi đầy đủ chi tiết hiện tại)
-- Kết quả mong đợi: Cập nhật thành công chi tiết (nếu có) và đổi trạng thái trên LINK3 thành 'Đã xác nhận'. Tồn kho KHÔNG thay đổi.
*/
-- Ví dụ 4: NV cố gắng đổi trạng thái phiếu nhập '003' từ 'Chờ xác nhận' sang 'Đã xác nhận'. (KHÔNG ĐƯỢC)
-- Giả định phiếu '003' vẫn đang 'Chờ xác nhận' (Chạy lại Vd 2 nếu cần)
-- EXEC CapNhatPN_Goc
--	@maPN = '003',
--	@trangThai = N'Đã xác nhận', -- Trạng thái MỚI
--	@maNV = 'NV_A',
--	@maNCC = 'NCC001',
--	@maKho = 'HN',
--	@maCV = 'NV', -- Nhân viên
--	@ChiTietPhieuNhap = @ChiTietNhapCapNhat;
-- Kết quả mong đợi: Báo lỗi "Bạn không có quyền xác nhận phiếu nhập."

-- Ví dụ 5: QL cập nhật chi tiết phiếu nhập '004' ở trạng thái 'Đã xác nhận'. Trạng thái yêu cầu vẫn là 'Đã xác nhận'. (ĐƯỢC)
-- Giả định phiếu '004' ở kho 'HN' đang có trạng thái là 'Đã xác nhận'
EXEC CapNhatPN_Goc
	@maPN = 'PN_HN004',
	@trangThai = N'Đã xác nhận', -- Trạng thái MỚI = Trạng thái HIỆN TẠI
	@maNV = 'NV005', -- Mã NV của QL
	@maNCC = 'NCC002',
	@maKho = 'HN',
	@maCV = 'CV001', -- Quản lý Kho
	@ChiTietPhieuNhap = @ChiTietNhapCapNhat; -- Có thể gửi chi tiết để cập nhật cùng lúc hoặc gửi rỗng (thực tế nên gửi đầy đủ chi tiết hiện tại)
-- Kết quả mong đợi: Cập nhật thành công chi tiết và tổng tiền trên LINK3. Trạng thái vẫn là 'Đã xác nhận'.

-- Ví dụ 6: QL đổi trạng thái phiếu nhập '004' từ 'Đã xác nhận' sang 'Đã nhận hàng'. (ĐƯỢC)
-- Giả định phiếu '004' ở kho 'HN' đang có trạng thái là 'Đã xác nhận' sau Vd 5
EXEC CapNhatPN_Goc
	@maPN = '004',
	@trangThai = N'Đã nhận hàng', -- Trạng thái MỚI
	@maNV = 'NV_B', -- Mã NV của QL
	@maNCC = 'NCC001',
	@maKho = 'HN',
	@maCV = 'QL', -- Quản lý Kho
	@ChiTietPhieuNhap = @ChiTietNhapCapNhat; -- Có thể gửi chi tiết để cập nhật cùng lúc hoặc gửi rỗng (thực tế nên gửi đầy đủ chi tiết hiện tại)
-- Kết quả mong đợi: Cập nhật thành công chi tiết (nếu có), đổi trạng thái trên LINK3 thành 'Đã nhận hàng'. Tồn kho SẼ được cập nhật (tăng) theo chi tiết đã gửi.



-- 3. Thêm phiếu xuất
DROP PROCEDURE ThemPhieuXuat

CREATE PROCEDURE ThemPhieuXuat (
	@maPX VARCHAR(50),
    @NgayTao DATE,
	@diaChi NVARCHAR(255),
    @tongTien float,
	@httt NVARCHAR(50),
    @trangThai NVARCHAR(50),
	@maNV VARCHAR(50),
	@maKho VARCHAR(50),
	@maKH VARCHAR(50),
	@ChiTietJSON NVARCHAR(MAX)
)
AS
BEGIN

	DECLARE @ChiTietXuatTemp TABLE (
			maPBSP VARCHAR(50),
			soLuong INT,
			giaXuat decimal(18,2) -- Bao gồm cả giá xuất nếu cần dùng sau này
		);
	INSERT INTO @ChiTietXuatTemp (maPBSP, soLuong, giaXuat)
	SELECT
		JSON_VALUE(j.value, '$.maPBSP'),
		JSON_VALUE(j.value, '$.soLuong'),
		JSON_VALUE(j.value, '$.giaXuat')
	FROM OPENJSON(@ChiTietJSON) AS j;

	-- Kiểm tra xem có sản phẩm nào trong chi tiết xuất mà số lượng tồn kho không đủ không
		IF EXISTS (
			SELECT 1
			FROM @ChiTietXuatTemp AS ctx
			JOIN KHO_PBSP AS kp ON ctx.maPBSP = kp.maPBSP
			WHERE kp.soLuong < ctx.soLuong
		)
		BEGIN

			RAISERROR('Số lượng tồn kho không đủ.', 16, 1);
			return 1;
		END

		INSERT INTO PhieuXuat (maPX, NgayTao, diaChi, tongTien, httt, trangThai, maNV, maKho, maKH)
		VALUES (@maPX, @NgayTao, @diaChi, @tongTien, @httt, @trangThai, @maNV, @maKho, @maKH);

		-- Chèn dữ liệu từ TVP vào bảng ChiTietPhieuNhap
		INSERT INTO CTPX (maPX, soLuong, giaXuat, maPBSP)
		SELECT
		@maPX, -- Sử dụng @maPN từ phiếu nhập chính
		JSON_VALUE(j.value, '$.soLuong'), -- Lấy giá trị soLuong từ JSON
		JSON_VALUE(j.value, '$.giaXuat'), -- Lấy giá trị giaNhap từ JSON
		JSON_VALUE(j.value, '$.maPBSP') -- Lấy giá trị maPBSP từ JSON
		FROM OPENJSON(@ChiTietJSON) AS j; -- Sử dụng OPENJSON để phân tích chuỗi JSON

		-- Thực hiện cập nhật số lượng cho các sản phẩm đã tồn tại trong KHO_PBSP
		-- Sửa lỗi alias và phép toán: giảm số lượng tồn kho
		IF @trangThai = N'Đã xuất hàng'
		BEGIN 
			UPDATE kp
			SET kp.soLuong = kp.soLuong - ctx.soLuong
			FROM KHO_PBSP AS kp
			JOIN @ChiTietXuatTemp AS ctx ON kp.maPBSP = ctx.maPBSP;
		END
END

CREATE TYPE CTPXType AS TABLE (
    soLuong INT,
    giaXuat decimal(18,2),
    maPBSP VARCHAR(50)
);
CREATE PROCEDURE themPX_Goc (
	@maPX VARCHAR(50),
    @NgayTao DATE,
	@diaChi NVARCHAR(255),
    @tongTien float,
	@httt NVARCHAR(50),
	@trangThai NVARCHAR(50),
	@maNV VARCHAR(50),
	@maKho VARCHAR(50),
	@maKH VARCHAR(50),
	@ChiTietPhieuXuat CTPXType READONLY
)
AS
BEGIN

	DECLARE @remoteMaPX VARCHAR(100);

	DECLARE @InvalidProductCount INT;
	-- Khai báo biến để lưu dữ liệu chi tiết dưới dạng chuỗi JSON
	DECLARE @ChiTietJSON NVARCHAR(MAX);

	-- Chuyển đổi dữ liệu từ TVP sang chuỗi JSON

	SELECT @ChiTietJSON = (
	SELECT soLuong, giaXuat, maPBSP
	FROM @ChiTietPhieuXuat
	FOR JSON PATH -- Sử dụng FOR JSON PATH để tạo chuỗi JSON
	);

	-- Kiểm tra mã kho và thực hiện chèn dữ liệu vào linked server tương ứng
	IF @maKho = 'HN'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK3 không
		IF EXISTS (SELECT 1 FROM LINK3.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPX = 'PX_HN' + @maPX;
			SELECT @InvalidProductCount = COUNT(T1.maPBSP)
			FROM @ChiTietPhieuXuat AS T1
			LEFT JOIN LINK3.phonestore.DBO.KHO_PBSP AS T2 ON T1.maPBSP = T2.maPBSP
			WHERE T2.maPBSP IS NULL;

			-- Thực thi procedure ThemPhieuNhap trên LINK3 với biến đã nối chuỗi
			IF @InvalidProductCount > 0
			BEGIN
				-- Báo lỗi nếu có mã sản phẩm không tồn tại trên LINK3
				RAISERROR('Có mã sản phẩm không hợp lệ trong chi tiết phiếu nhập cho kho HN.', 16, 1);
				RETURN 1;
			END
			Exec LINK3.phonestore.DBO.ThemPhieuXuat @remoteMaPX, @NgayTao, @diaChi, @tongTien, @httt, @trangThai, @maNV, @maKho, @maKH, @ChiTietJSON;
		END
		ELSE
			BEGIN
				-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK3
				RAISERROR('Mã NV không hợp lệ trên kho HN.', 16, 1);
				RETURN 1;
			END
	END
	ELSE IF @maKho = 'DN'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK4 không
		IF EXISTS (SELECT 1 FROM LINK4.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPX = 'PX_DN' + @maPX;
			-- Thực thi procedure ThemPhieuNhap trên LINK4 với biến đã nối chuỗi
			Exec LINK4.phonestore.DBO.ThemPhieuXuat @remoteMaPX, @NgayTao, @diaChi, @tongTien, @httt, @trangThai, @maNV, @maKho, @maKH, @ChiTietJSON
		END
		ELSE
		BEGIN
			-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK4
			RAISERROR('Mã NV không hợp lệ trên kho DN.', 16, 1);
			RETURN 1;
		END
	END
	ELSE IF @maKho = 'HCM'
	BEGIN
		-- Kiểm tra xem nhân viên có tồn tại trên linked server LINK5 không
		IF EXISTS (SELECT 1 FROM LINK5.phonestore.DBO.NHANVIEN WHERE maNV = @maNV)
		BEGIN
			-- Nối chuỗi mã phiếu nhập
			SET @remoteMaPX = 'PX_HCM' + @maPX;
			-- Thực thi procedure ThemPhieuNhap trên LINK5 với biến đã nối chuỗi
			Exec LINK5.phonestore.DBO.ThemPhieuXuat @remoteMaPX, @NgayTao, @diaChi, @tongTien, @httt, @trangThai, @maNV, @maKho, @maKH, @ChiTietJSON;
		END
		ELSE
		BEGIN
			-- Báo lỗi nếu mã nhân viên không hợp lệ trên LINK5
			RAISERROR('Mã NV không hợp lệ trên kho HCM.', 16, 1);
			RETURN 1;
		END
	END
	ELSE
	BEGIN
		-- Báo lỗi nếu mã kho không hợp lệ
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END
END


-- Khai báo một biến kiểu TVP
DECLARE @ChiTietXuat CTPXType;

-- Thêm dữ liệu chi tiết vào biến TVP
INSERT INTO @ChiTietXuat (soLuong, giaXuat, maPBSP) VALUES
(1, 50000, 'PBSP001'),
(1, 100000, 'PBSP004'),
(1, 50000, 'PBSP005');

Exec themPX_Goc '003', '2025-02-02', N'45 Lê Lợi, TP.HCM' , 5000000, N'Chuyển khoản', N'Đã xác nhận', 'NV005', 'HN', 'KH002', @ChiTietXuat;

SELECT * FROM LINK3.phonestore.DBO.PHIEUXUAT

SELECT * FROM LINK3.phonestore.DBO.CTPX

SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP

DELETE FROM LINK3.phonestore.DBO.PHIEUXUAT WHERE maPX = 'PX_HN003'


-- 4. Cập nhật phiếu xuất

-- =============================================
-- Script này cần chạy trên các LINKED SERVER (LINK3, LINK4, LINK5)
-- Cập nhật stored procedure CapNhatPhieuXuat trên Linked Server
-- Procedure này thực hiện cập nhật thực tế trên DB phân mảnh
-- Đã sửa lỗi cú pháp OPENJSON, logic cập nhật tồn kho (giảm), kiểm tra tồn kho.
-- =============================================

IF OBJECT_ID('CapNhatPhieuXuat', 'P') IS NOT NULL
    DROP PROCEDURE CapNhatPhieuXuat;
GO

CREATE PROCEDURE CapNhatPhieuXuat (
	@maPX VARCHAR(100), -- Mã PX đầy đủ (ví dụ: PX_HN003)
    @maKho VARCHAR(50), -- <-- THÊM tham số mã kho để dùng cho bảng KHO_PBSP
    @trangThai NVARCHAR(50), -- Trạng thái MỚI được phép (đã kiểm tra từ server gốc)
	@maNV VARCHAR(50), -- Không sử dụng trong logic này nhưng giữ lại tham số
	@maKH VARCHAR(50), -- Không sử dụng trong logic này nhưng giữ lại tham số
	-- Thêm tham số JSON cho chi tiết phiếu xuất cần cập nhật/thêm/xóa
	@ChiTietJSON NVARCHAR(MAX) -- Nhận dữ liệu chi tiết dưới dạng chuỗi JSON
)
AS
BEGIN
	-- Xóa tất cả chi tiết cũ của phiếu xuất này
	-- và chèn lại toàn bộ chi tiết mới từ JSON
	-- Thao tác này cho phép sửa đổi số lượng, thêm hoặc xóa sản phẩm
	DELETE FROM CTPX WHERE maPX = @maPX; -- SỬA: Dùng CTPX

	-- Chèn dữ liệu chi tiết mới từ chuỗi JSON vào bảng CTPX
	-- ĐÃ SỬA: Đọc trực tiếp cột từ kết quả OPENJSON WITH (không dùng JSON_VALUE(j.value, ...))
	INSERT INTO CTPX (maPX, soLuong, giaXuat, maPBSP) -- SỬA: Dùng CTPX
	SELECT
		@maPX, -- Sử dụng @maPX từ phiếu xuất chính
		j.soLuong, -- SỬA: Đọc trực tiếp cột soLuong
		j.giaXuat, -- SỬA: Đọc trực tiếp cột giaXuat
		j.maPBSP   -- SỬA: Đọc trực tiếp cột maPBSP
	FROM OPENJSON(@ChiTietJSON) WITH (soLuong INT, giaXuat decimal(18,2), maPBSP VARCHAR(50)) AS j; -- SỬA: THÊM WITH clause và alias j

	-- Tính toán lại tổng tiền dựa trên chi tiết phiếu xuất mới
	DECLARE @calculatedTongTien float;
	-- Đảm bảo SUM không trả về NULL nếu không có chi tiết nào
	-- CTPX có cột soLuong và giaXuat
	SELECT @calculatedTongTien = ISNULL(SUM(CAST(soLuong AS float) * giaXuat), 0) -- SỬA: Tính tổng dùng soLuong và giaXuat
	FROM CTPX
	WHERE maPX = @maPX; -- SỬA: Dùng CTPX

	-- Cập nhật lại tổng tiền và trạng thái trong bảng PhieuXuat
	UPDATE PhieuXuat
	SET
		tongTien = @calculatedTongTien, -- Cập nhật tổng tiền đã tính lại
		trangThai = @trangThai -- Cập nhật trạng thái mới (đã được kiểm tra hợp lệ ở server gốc)
	WHERE maPX = @maPX;

	-- Cập nhật số lượng trong bảng KHO_PBSP (GIẢM tồn kho)
	-- Chỉ thực hiện khi trạng thái MỚI là 'Đã xuất hàng'
	IF @trangThai = N'Đã xuất hàng' -- SỬA: Điều kiện trạng thái cho Phiếu Xuất
	BEGIN
		-- Khai báo biến bảng tạm để lưu chi tiết xuất đã phân tích từ JSON
		DECLARE @ChiTietXuatTemp TABLE (
			maPBSP VARCHAR(50),
			soLuongXuat INT, -- Sửa tên cột cho rõ ràng
			giaXuat decimal(18,2) -- Giữ lại giaXuat trong biến tạm (dù không dùng khi merge vào KHO_PBSP)
		);
		-- ĐÃ SỬA: Đọc trực tiếp cột từ kết quả OPENJSON WITH
		INSERT INTO @ChiTietXuatTemp (maPBSP, soLuongXuat, giaXuat)
		SELECT
			j.maPBSP,    -- SỬA: Đọc trực tiếp
			j.soLuong, -- SỬA: Đọc trực tiếp (tên cột trong JSON vẫn là soLuong)
			j.giaXuat  -- SỬA: Đọc trực tiếp
		FROM OPENJSON(@ChiTietJSON) WITH (soLuong INT, giaXuat decimal(18,2), maPBSP VARCHAR(50)) AS j; -- SỬA: THÊM WITH clause và alias j

        -- BẮT ĐẦU KIỂM TRA TỒN KHO TRƯỚC KHI XUẤT
        -- Sử dụng Cursor hoặc vòng lặp (trong SQL Server, Cursor/vòng lặp không tối ưu, dùng set-based sẽ tốt hơn, nhưng dùng loop/cursor dễ hiểu cho ví dụ này)
        -- Hoặc dùng CTE/Subquery để kiểm tra trước khi MERGE
        -- Cách hiệu quả hơn là dùng JOIN hoặc subquery trong MERGE source
        -- Kiểm tra tồn kho đủ dùng EXISTS/NOT EXISTS hoặc JOIN
        IF EXISTS (
            SELECT 1
            FROM @ChiTietXuatTemp ctx
            JOIN KHO_PBSP kho ON kho.maKho = @maKho AND kho.maPBSP = ctx.maPBSP
            WHERE kho.soLuong < ctx.soLuongXuat -- Tìm bất kỳ sản phẩm nào không đủ tồn kho
        )
        BEGIN
            -- Nếu có sản phẩm không đủ tồn kho, báo lỗi và rollback
            -- Có thể lấy chi tiết sản phẩm không đủ tồn kho để thông báo chi tiết hơn
            DECLARE @InsufficientStockMsg NVARCHAR(MAX) = N'Không đủ tồn kho cho các sản phẩm sau tại kho ' + @maKho + ': ';
            SELECT @InsufficientStockMsg += N'PBSP: ' + ctx.maPBSP + N', Yêu cầu: ' + CAST(ctx.soLuongXuat AS NVARCHAR) + N', Tồn: ' + CAST(ISNULL(kho.soLuong, 0) AS NVARCHAR) + N'; '
            FROM @ChiTietXuatTemp ctx
            LEFT JOIN KHO_PBSP kho ON kho.maKho = @maKho AND kho.maPBSP = ctx.maPBSP
            WHERE ISNULL(kho.soLuong, 0) < ctx.soLuongXuat;

            RAISERROR(@InsufficientStockMsg, 16, 1);
            -- Lỗi này sẽ được bắt bởi khối CATCH và rollback giao dịch
            RETURN 1;
        END
        -- KẾT THÚC KIỂM TRA TỒN KHO

		-- Sử dụng MERGE để CẬP NHẬT số lượng trong bảng KHO_PBSP (GIẢM tồn kho)
        -- Chỉ CẬP NHẬT khi MATCHED. Bỏ WHEN NOT MATCHED.
		MERGE KHO_PBSP AS Target
		USING @ChiTietXuatTemp AS Source ON Target.maKho = @maKho AND Target.maPBSP = Source.maPBSP
		WHEN MATCHED THEN
			-- Nếu maPBSP đã tồn tại trong kho VÀ tồn kho đã được kiểm tra là đủ, giảm số lượng.
			UPDATE SET
				Target.soLuong = Target.soLuong - Source.soLuongXuat; -- SỬA: GIẢM số lượng

        -- Bỏ hẳn WHEN NOT MATCHED vì không thể xuất nếu không có trong kho.
        -- Việc kiểm tra tồn kho ở trên đã đảm bảo rằng tất cả Source.maPBSP đều phải MATCHED với Target.maPBSP có đủ số lượng.

	END -- Kết thúc IF @trangThai = N'Đã xuất hàng'
END
GO

-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Định nghĩa User-Defined Table Type CTPXType
-- Kiểu này được sử dụng trên server gốc để nhận dữ liệu chi tiết từ ứng dụng
-- =============================================

IF TYPE_ID(N'CTPXType') IS NOT NULL
    DROP TYPE CTPXType;
GO

CREATE TYPE CTPXType AS TABLE ( -- SỬA TÊN TYPE
    soLuong INT, -- Số lượng xuất
    giaXuat decimal(18,2), -- Giá xuất
    maPBSP VARCHAR(50)
);
GO


-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Cập nhật stored procedure CapNhatPX_Goc trên Server Gốc
-- Procedure này thực hiện kiểm tra trạng thái, quyền hạn và gọi CapNhatPhieuXuat từ xa
-- Đã sửa việc sử dụng mã PX đầy đủ, kiểu TVP, thông báo lỗi.
-- =============================================

IF OBJECT_ID('CapNhatPX_Goc', 'P') IS NOT NULL
    DROP PROCEDURE CapNhatPX_Goc;
GO

ALTER PROCEDURE CapNhatPX_Goc (
	@maPX VARCHAR(50), -- Mã PX gốc (chưa có tiền tố kho)
    @trangThai NVARCHAR(50), -- Trạng thái MỚI được yêu cầu
    @maNV VARCHAR(50), -- Mã nhân viên thực hiện
    @maKH VARCHAR(50), -- Mã khách hàng
	@maKho VARCHAR(50), -- Tham số mã kho cần giữ lại
	@maCV VARCHAR(50), -- Mã chức vụ của người thực hiện ('NV' hoặc 'QL')
	@ChiTietPhieuXuat CTPXType READONLY -- SỬA KIỂU TVP thành CTPXType
)
AS
BEGIN

	-- Khai báo biến để lưu trạng thái HIỆN TẠI của phiếu xuất trên linked server
	DECLARE @currentTrangThai NVARCHAR(50);
	-- Khai báo biến để lưu dữ liệu chi tiết MỚI dưới dạng chuỗi JSON
	DECLARE @ChiTietJSON NVARCHAR(MAX);
	-- Khai báo biến để lưu chuỗi lệnh SQL động
	DECLARE @sql NVARCHAR(MAX);
	-- Khai báo biến để lưu định nghĩa tham số cho sp_executesql khi gọi Linked Server
	DECLARE @paramDefinition NVARCHAR(MAX);
	-- Biến để lưu tên linked server
	DECLARE @linkedServerName VARCHAR(50);

	-- Kiểm tra mã kho hợp lệ và xác định linked server
	IF @maKho = 'HN'
		SET @linkedServerName = 'LINK3';
	ELSE IF @maKho = 'DN'
		SET @linkedServerName = 'LINK4';
	ELSE IF @maKho = 'HCM'
		SET @linkedServerName = 'LINK5';
	ELSE
	BEGIN
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END

	-- Lấy trạng thái HIỆN TẠI của phiếu xuất từ linked server
	-- Sử dụng sp_executesql để lấy dữ liệu từ xa
    -- SỬA: Sử dụng @maPX để truy vấn PhieuXuat
	SET @sql = N'SELECT @currentTrangThai = trangThai FROM [' + @linkedServerName + '].phonestore.DBO.PhieuXuat WHERE maPX = @maPX;'; -- SỬA tên bảng PhieuXuat
	SET @paramDefinition = N'@currentTrangThai NVARCHAR(50) OUTPUT, @maPX VARCHAR(100)'; -- SỬA: Kiểu dữ liệu maPX

	EXEC sp_executesql @sql, @paramDefinition, @currentTrangThai OUTPUT, @maPX = @maPX; -- SỬA: Truyền @maPX

	-- Kiểm tra xem phiếu xuất có tồn tại không
	IF @currentTrangThai IS NULL
	BEGIN
		RAISERROR('Phiếu xuất ''%s'' không tồn tại.', 16, 1, @maPX); -- SỬA: Báo lỗi dùng @maPX và "Phiếu xuất"
		RETURN 1;
	END

	-- =============================================
	-- BẮT ĐẦU KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN THEO YÊU CẦU
	-- Giữ nguyên logic kiểm tra trạng thái/quyền như bạn đã sửa, chỉ sửa thông báo lỗi
	-- Sửa kiểm tra @maCV thành 'QL' nếu 'CV001' là mã chức vụ thực tế
	-- =============================================

	-- Quy tắc 1: "Đã xuất hàng" thì khỏi xoá, sửa
	IF @currentTrangThai = N'Đã xuất hàng' -- SỬA: Trạng thái Đã xuất hàng
	BEGIN
		RAISERROR('Không được cập nhật phiếu xuất đã xuất hàng.', 16, 1); -- SỬA: Thông báo "phiếu xuất"
		RETURN 1;
	END

	DECLARE @allowUpdate BIT = 0;

	IF @currentTrangThai = N'Chờ xác nhận' -- GIẢ ĐỊNH: Trạng thái ban đầu là 'Chờ xác nhận'
	BEGIN
		IF @trangThai = N'Chờ xác nhận' -- Sửa chi tiết ở trạng thái Chờ xác nhận
		BEGIN
			SET @allowUpdate = 1; -- Cả NV và QL được phép
		END
		ELSE IF @trangThai = N'Đã xác nhận' -- Chuyển sang Đã xác nhận
		BEGIN
			-- Chỉ Quản lý kho (QL) được đổi sang trạng thái "Đã xác nhận"
			IF @maCV = 'CV001' -- SỬA: Kiểm tra @maCV = 'QL' (hoặc 'CV001' nếu đó là mã thực tế)
				SET @allowUpdate = 1;
			ELSE
			BEGIN
				RAISERROR('Bạn không có quyền xác nhận phiếu xuất.', 16, 1); -- SỬA: Thông báo "phiếu xuất"
				RETURN 1;
			END
		END
		-- Thêm các trạng thái chuyển đổi khác nếu có (ví dụ: sang Đã hủy)
		ELSE
		BEGIN
			RAISERROR('Không thể chuyển trạng thái từ ''Chờ xác nhận'' sang ''%s''.', 16, 1, @trangThai); -- SỬA: Thông báo
			RETURN 1;
		END
	END
	ELSE IF @currentTrangThai = N'Đã xác nhận' -- Từ trạng thái Đã xác nhận
	BEGIN
		-- Ở trạng thái "Đã xác nhận", chỉ QL kho mới được phép thao tác (sửa chi tiết hoặc chuyển trạng thái)
		IF @maCV = 'CV001' -- SỬA: Kiểm tra @maCV = 'QL' (hoặc 'CV001')
		BEGIN
			IF @trangThai = N'Đã xác nhận' -- Sửa chi tiết ở trạng thái Đã xác nhận
			BEGIN
				SET @allowUpdate = 1;
			END
			ELSE IF @trangThai = N'Đã xuất hàng' -- Chuyển sang Đã xuất hàng
			BEGIN
				SET @allowUpdate = 1; -- QL kho được chuyển
			END
			-- Thêm các trạng thái chuyển đổi khác nếu có (ví dụ: sang Đang giao hàng)
			ELSE
			BEGIN
				RAISERROR('Không thể chuyển trạng thái từ ''Đã xác nhận'' sang ''%s'' đối với Quản lý kho.', 16, 1, @trangThai); -- SỬA: Thông báo
				RETURN 1;
			END
		END
		ELSE -- Nếu không phải QL kho cố gắng thao tác trên PX 'Đã xác nhận'
		BEGIN
			RAISERROR('Bạn không có quyền cập nhật phiếu xuất ở trạng thái ''Đã xác nhận''.', 16, 1); -- SỬA: Thông báo "phiếu xuất"
			RETURN 1;
		END
	END
	-- Nếu trạng thái hiện tại không phải các trạng thái được phép cập nhật
	ELSE
	BEGIN
		RAISERROR('Trạng thái phiếu xuất hiện tại ''%s'' không hợp lệ để cập nhật.', 16, 1, @currentTrangThai); -- SỬA: Thông báo "phiếu xuất"
		RETURN 1;
	END

	-- =============================================
	-- KẾT THÚC KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN
	-- =============================================

	IF @allowUpdate = 1
	BEGIN
		-- Chuyển đổi dữ liệu chi tiết MỚI từ TVP sang chuỗi JSON
		IF EXISTS (SELECT 1 FROM @ChiTietPhieuXuat)
		BEGIN
			SELECT @ChiTietJSON = (
				SELECT soLuong, giaXuat, maPBSP -- SỬA tên cột giaXuat
				FROM @ChiTietPhieuXuat
				FOR JSON PATH -- Giữ lại FOR JSON PATH để tạo mảng
			);
		END
		ELSE
		BEGIN
			-- Nếu không có chi tiết nào được gửi, gửi JSON mảng rỗng.
			SET @ChiTietJSON = '[]';
		END

		-- Sử dụng sp_executesql để gọi procedure CapNhatPhieuXuat trên linked server
		-- SỬA: Thêm tham số @maKho, @maNV, @maKH vào lệnh gọi
		SET @sql = N'EXEC [' + @linkedServerName + '].phonestore.DBO.CapNhatPhieuXuat @maPX, @maKho, @trangThai, @maNV, @maKH, @ChiTietJSON'; -- SỬA tên proc, thêm @maKH
        -- SỬA: Định nghĩa tham số bao gồm tất cả các tham số sẽ truyền
		SET @paramDefinition = N'@maPX VARCHAR(100), @maKho VARCHAR(50), @trangThai NVARCHAR(50), @maNV VARCHAR(50), @maKH VARCHAR(50), @ChiTietJSON NVARCHAR(MAX)'; -- SỬA: Định nghĩa đủ tham số, thêm @maKH

		-- Thực thi lệnh gọi từ xa
		-- SỬA: Truyền đủ giá trị cho tất cả các tham số đã định nghĩa
		EXEC sp_executesql @sql, @paramDefinition,
			@maPX = @maPX,   -- SỬA: Truyền mã PX đầy đủ
            @maKho = @maKho,     -- SỬA: Truyền mã kho
			@trangThai = @trangThai,
			@maNV = @maNV,
			@maKH = @maKH,       -- SỬA: Truyền mã KH
			@ChiTietJSON = @ChiTietJSON;
	END
END
GO


-- Các lệnh SELECT kiểm tra (chạy trên Server Gốc, thay LINK3 nếu cần)
SELECT * FROM LINK3.phonestore.DBO.PHIEUXUAT;
SELECT * FROM LINK3.phonestore.DBO.CTPX;
SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP;


-- Khai báo một biến kiểu TVP
DECLARE @ChiTietXuat CTPXType;

-- Thêm dữ liệu chi tiết vào biến TVP
INSERT INTO @ChiTietXuat (soLuong, giaXuat, maPBSP) VALUES
(3, 50000, 'PBSP001'),
(4, 100000, 'PBSP004'),
(5, 50000, 'PBSP005');

Exec themPX_Goc '003', '2025-02-02', N'45 Lê Lợi, TP.HCM' , 5000000, N'Chuyển khoản', N'Chờ xác nhận', 'NV005', 'HN', 'KH002', @ChiTietXuat;


-- =============================================
-- Ví dụ thực thi procedure CapNhatPX_Goc trên Server Gốc
-- Đã cập nhật các ví dụ để phù hợp với sửa đổi
-- =============================================

-- Khai báo một biến kiểu TVP cho chi tiết cập nhật
DECLARE @ChiTietXuatCapNhat CTPXType; -- SỬA KIỂU TVP

-- Thêm dữ liệu chi tiết CẬP NHẬT vào biến TVP
-- Đây là toàn bộ danh sách chi tiết MỚI cho phiếu xuất cần cập nhật.
-- Lưu ý: Nếu bạn muốn xóa hết chi tiết, hãy khai báo @ChiTietXuatCapNhat nhưng không INSERT dòng nào vào đó.
INSERT INTO @ChiTietXuatCapNhat (soLuong, giaXuat, maPBSP) VALUES -- SỬA tên cột giaXuat
(1, 50000, 'PBSP001'),
(5, 100000, 'PBSP004'),
(3, 50000, 'PBSP005');
-- Sản phẩm PBSP005 không có trong ví dụ này để đơn giản, bạn có thể thêm nếu cần

-- GIẢ ĐỊNH:
-- Phiếu xuất '001' ở kho 'HN' đang có trạng thái là 'Chờ xác nhận'
-- Phiếu xuất '002' ở kho 'HN' đang có trạng thái là 'Đã xác nhận'
-- Phiếu xuất '003' ở kho 'HN' đang có trạng thái là 'Đã xuất hàng' (theo ảnh)
-- Tồn kho PBSP001 tại kho HN là 10, PBSP004 là 8 (theo ảnh)
-- Có 2 mã chức vụ: 'NV' (Nhân viên - giả định mã CV là 'CV003'), 'QL' (Quản lý Kho - giả định mã CV là 'CV001')

-- Ví dụ 1: NV cập nhật chi tiết phiếu xuất '001' ở trạng thái 'Chờ xác nhận'. Trạng thái yêu cầu vẫn là 'Chờ xác nhận'. (ĐƯỢC)
-- EXEC CapNhatPX_Goc
-- @maPX = 'PX_HN003', -- Mã gốc
-- @trangThai = N'Chờ xác nhận', -- Trạng thái MỚI = Trạng thái HIỆN TẠI
-- @maNV = 'NV005', -- Mã NV thực hiện
-- @maKH = 'KH002', -- Mã khách hàng
-- @maKho = 'HN',
-- @maCV = 'CV003', -- Chức vụ Nhân viên
-- @ChiTietPhieuXuat = @ChiTietXuatCapNhat; -- SỬA tên TVP
-- Kết quả mong đợi: Cập nhật thành công chi tiết và tổng tiền trên LINK3. Trạng thái vẫn là 'Chờ xác nhận'.

-- Ví dụ 3: QL đổi trạng thái phiếu xuất '001' từ 'Chờ xác nhận' sang 'Đã xác nhận'. (ĐƯỢC)
-- Giả định phiếu '001' vẫn đang 'Chờ xác nhận'
EXEC CapNhatPX_Goc
@maPX = 'PX_HN003', -- Mã gố
@trangThai = N'Đã xác nhận', -- Trạng thái MỚI
@maNV = 'NV005', -- Mã NV của QL
@maKH = 'KH002', -- Mã khách hàng
@maKho = 'HN',
@maCV = 'CV001', -- Chức vụ Quản lý Kho
@ChiTietPhieuXuat = @ChiTietXuatCapNhat; -- SỬA tên TVP (có thể gửi chi tiết hoặc rỗng)
-- Kết quả mong đợi: Cập nhật thành công chi tiết (nếu có) và đổi trạng thái trên LINK3 thành 'Đã xác nhận'. Tồn kho KHÔNG thay đổi.


-- 5. xóa phiếu nhập


-- =============================================
-- Script này cần chạy trên các LINKED SERVER (LINK3, LINK4, LINK5)
-- Tạo stored procedure XoaPhieuNhap trên Linked Server
-- Procedure này thực hiện xóa vật lý trên DB phân mảnh
-- =============================================

IF OBJECT_ID('XoaPhieuNhap', 'P') IS NOT NULL
    DROP PROCEDURE XoaPhieuNhap;
GO

CREATE PROCEDURE XoaPhieuNhap (
	@maPN VARCHAR(100) -- Mã PN đầy đủ (ví dụ: PN_HN003)
)
AS
BEGIN
	SET NOCOUNT ON;

	-- Bắt đầu giao dịch để đảm bảo tính toàn vẹn
	BEGIN TRANSACTION;

	BEGIN TRY
		-- Xóa chi tiết phiếu nhập trước
		DELETE FROM CTPN WHERE maPN = @maPN;

		-- Xóa phiếu nhập chính
		DELETE FROM PhieuNhap WHERE maPN = @maPN;

		-- Nếu mọi thứ thành công, commit giao dịch
		COMMIT TRANSACTION;

	END TRY
	BEGIN CATCH
		-- Nếu có lỗi, rollback giao dịch
		IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

		-- Ném lại lỗi để server gốc nhận được
		THROW;

	END CATCH
END
GO


-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Tạo stored procedure XoaPN_Goc trên Server Gốc
-- Procedure này kiểm tra trạng thái, quyền hạn và gọi XoaPhieuNhap từ xa
-- =============================================

IF OBJECT_ID('XoaPN_Goc', 'P') IS NOT NULL
    DROP PROCEDURE XoaPN_Goc;
GO

CREATE PROCEDURE XoaPN_Goc (
	@maPN VARCHAR(50), -- Mã PN gốc (chưa có tiền tố kho)
	@maKho VARCHAR(50), -- Tham số mã kho cần giữ lại
	@maCV VARCHAR(50) -- Mã chức vụ của người thực hiện ('NV' hoặc 'QL')
)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @currentTrangThai NVARCHAR(50);
	DECLARE @sql NVARCHAR(MAX);
	DECLARE @paramDefinition NVARCHAR(MAX);
	DECLARE @linkedServerName VARCHAR(50);

	-- Kiểm tra mã kho hợp lệ và xác định linked server
	IF @maKho = 'HN'
		SET @linkedServerName = 'LINK3';
	ELSE IF @maKho = 'DN'
		SET @linkedServerName = 'LINK4';
	ELSE IF @maKho = 'HCM'
		SET @linkedServerName = 'LINK5';
	ELSE
	BEGIN
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END

	-- Lấy trạng thái HIỆN TẠI của phiếu nhập từ linked server
	SET @sql = N'SELECT @currentTrangThai = trangThai FROM [' + @linkedServerName + '].phonestore.DBO.PhieuNhap WHERE maPN = @maPN;';
	SET @paramDefinition = N'@currentTrangThai NVARCHAR(50) OUTPUT, @maPN VARCHAR(100)';

	EXEC sp_executesql @sql, @paramDefinition, @currentTrangThai OUTPUT, @maPN = @maPN;

	-- Kiểm tra xem phiếu nhập có tồn tại không
	IF @currentTrangThai IS NULL
	BEGIN
		RAISERROR('Phiếu nhập ''%s'' không tồn tại.', 16, 1, @maPN);
		RETURN 1;
	END

	-- =============================================
	-- BẮT ĐẦU KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN XÓA
	-- =============================================

	DECLARE @canDelete BIT = 0;

	IF @maCV = 'CV003' -- Kiểm tra nếu là Nhân viên
	BEGIN
		IF @currentTrangThai = N'Chờ xác nhận' -- NV chỉ được xóa ở trạng thái "Chờ xác nhận"
			SET @canDelete = 1;
		ELSE
		BEGIN
			RAISERROR('Nhân viên chỉ được xóa phiếu nhập ở trạng thái ''Chờ xác nhận''.', 16, 1);
			RETURN 1;
		END
	END
	ELSE IF @maCV = 'CV001' -- Kiểm tra nếu là Quản lý Kho
	BEGIN
		IF @currentTrangThai = N'Chờ xác nhận' OR @currentTrangThai = N'Đã xác nhận' -- QL được xóa ở "Chờ xác nhận" hoặc "Đã xác nhận"
			SET @canDelete = 1;
		ELSE
		BEGIN
			RAISERROR('Quản lý kho chỉ được xóa phiếu nhập ở trạng thái ''Chờ xác nhận'' hoặc ''Đã xác nhận''.', 16, 1);
			RETURN 1;
		END
	END
	ELSE -- Chức vụ không xác định hoặc không có quyền xóa
	BEGIN
	     RAISERROR('Chức vụ không hợp lệ hoặc không có quyền xóa phiếu nhập.', 16, 1);
	     RETURN 1;
	END

	-- =============================================
	-- KẾT THÚC KIỂM TRA XÓA
	-- =============================================

	-- Nếu được phép xóa, gọi procedure xóa trên linked server
	IF @canDelete = 1
	BEGIN
		SET @sql = N'EXEC [' + @linkedServerName + '].phonestore.DBO.XoaPhieuNhap @maPN';
		SET @paramDefinition = N'@maPN VARCHAR(100)';

		EXEC sp_executesql @sql, @paramDefinition, @maPN = @maPN; -- Truyền mã PN đầy đủ để xóa

		-- Thông báo xóa thành công (Tùy chọn)
        -- SELECT N'Đã xóa phiếu nhập ' + @maPN;
	END
	-- Nếu không được phép xóa (@canDelete = 0), các khối IF/ELSE IF kiểm tra đã RETURN với lỗi cụ thể.

END
GO


-- Các lệnh SELECT kiểm tra (chạy trên Server Gốc, thay LINK3 nếu cần)
SELECT * FROM LINK3.phonestore.DBO.PHIEUNHAP;
SELECT * FROM LINK3.phonestore.DBO.CTPN;
SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP


-- =============================================
-- Ví dụ thực thi procedure XoaPN_Goc trên Server Gốc
-- =============================================

-- GIẢ ĐỊNH:
-- Phiếu nhập '006' ở kho 'HN' đang có trạng thái là 'Chờ xác nhận'
-- Phiếu nhập '007' ở kho 'HN' đang có trạng thái là 'Đã xác nhận'
-- Phiếu nhập '008' ở kho 'HN' đang có trạng thái là 'Đã nhận hàng'
-- Có 2 mã chức vụ: 'NV' (Nhân viên), 'QL' (Quản lý Kho)

-- Ví dụ 1: NV xóa phiếu nhập '006' ở trạng thái 'Chờ xác nhận'. (ĐƯỢC)
EXEC XoaPN_Goc
	@maPN = 'PN_HN005',
	@maKho = 'HN',
	@maCV = 'CV003'; -- Nhân viên
-- Kết quả mong đợi: Xóa thành công PN_HN006 và chi tiết của nó trên LINK3.



-- 6. Xóa phiếu xuất

-- =============================================
-- Script này cần chạy trên các LINKED SERVER (LINK3, LINK4, LINK5)
-- Tạo stored procedure XoaPhieuXuat trên Linked Server
-- Procedure này thực hiện xóa vật lý trên DB phân mảnh
-- =============================================

IF OBJECT_ID('XoaPhieuXuat', 'P') IS NOT NULL
    DROP PROCEDURE XoaPhieuXuat;
GO

CREATE PROCEDURE XoaPhieuXuat (
	@maPX VARCHAR(100) -- Mã PX đầy đủ (ví dụ: PX_HN003)
)
AS
BEGIN
	SET NOCOUNT ON;

	-- Bắt đầu giao dịch để đảm bảo tính toàn vẹn
	BEGIN TRANSACTION;

	BEGIN TRY
		-- Xóa chi tiết phiếu xuất trước
		DELETE FROM CTPX WHERE maPX = @maPX; -- SỬA: Xóa từ bảng CTPX

		-- Xóa phiếu xuất chính
		DELETE FROM PhieuXuat WHERE maPX = @maPX; -- SỬA: Xóa từ bảng PhieuXuat

		-- Nếu mọi thứ thành công, commit giao dịch
		COMMIT TRANSACTION;

	END TRY
	BEGIN CATCH
		-- Nếu có lỗi, rollback giao dịch
		IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

		-- Ném lại lỗi để server gốc nhận được
		THROW;

	END CATCH
END
GO


-- =============================================
-- Script này cần chạy trên SERVER GỐC
-- Tạo stored procedure XoaPX_Goc trên Server Gốc
-- Procedure này kiểm tra trạng thái, quyền hạn và gọi XoaPhieuXuat từ xa
-- =============================================

IF OBJECT_ID('XoaPX_Goc', 'P') IS NOT NULL
    DROP PROCEDURE XoaPX_Goc;
GO

CREATE PROCEDURE XoaPX_Goc (
	@maPX VARCHAR(50), -- Mã PX gốc (chưa có tiền tố kho)
	@maKho VARCHAR(50), -- Tham số mã kho cần giữ lại
	@maCV VARCHAR(50) -- Mã chức vụ của người thực hiện ('NV' hoặc 'QL')
)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @currentTrangThai NVARCHAR(50);
	DECLARE @sql NVARCHAR(MAX);
	DECLARE @paramDefinition NVARCHAR(MAX);
	DECLARE @linkedServerName VARCHAR(50);

	-- Kiểm tra mã kho hợp lệ và xác định linked server
	IF @maKho = 'HN'
		SET @linkedServerName = 'LINK3';
	ELSE IF @maKho = 'DN'
		SET @linkedServerName = 'LINK4';
	ELSE IF @maKho = 'HCM'
		SET @linkedServerName = 'LINK5';
	ELSE
	BEGIN
		RAISERROR('Mã kho không hợp lệ.', 16, 1);
		RETURN 1;
	END

	-- Lấy trạng thái HIỆN TẠI của phiếu xuất từ linked server
	SET @sql = N'SELECT @currentTrangThai = trangThai FROM [' + @linkedServerName + '].phonestore.DBO.PhieuXuat WHERE maPX = @maPX;'; -- SỬA: Truy vấn bảng PhieuXuat
	SET @paramDefinition = N'@currentTrangThai NVARCHAR(50) OUTPUT, @maPX VARCHAR(100)';

	EXEC sp_executesql @sql, @paramDefinition, @currentTrangThai OUTPUT, @maPX = @maPX;

	-- Kiểm tra xem phiếu xuất có tồn tại không
	IF @currentTrangThai IS NULL
	BEGIN
		RAISERROR('Phiếu xuất ''%s'' không tồn tại.', 16, 1, @maPX); -- SỬA: Thông báo "Phiếu xuất"
		RETURN 1;
	END

	-- =============================================
	-- BẮT ĐẦU KIỂM TRA TRẠNG THÁI VÀ QUYỀN HẠN XÓA PHIẾU XUẤT
	-- Áp dụng quy tắc xóa của PN sang trạng thái của PX
	-- =============================================

	DECLARE @canDelete BIT = 0;

	IF @maCV = 'CV003' -- Kiểm tra nếu là Nhân viên
	BEGIN
		IF @currentTrangThai = N'Chờ xác nhận' -- NV chỉ được xóa ở trạng thái "Chờ xác nhận" (tương đương "Chờ xác nhận" của PN)
			SET @canDelete = 1;
		ELSE
		BEGIN
			RAISERROR('Nhân viên chỉ được xóa phiếu xuất ở trạng thái ''Chờ xác nhận''.', 16, 1); -- SỬA: Thông báo và trạng thái
			RETURN 1;
		END
	END
	ELSE IF @maCV = 'CV001' -- Kiểm tra nếu là Quản lý Kho
	BEGIN
		IF @currentTrangThai = N'Chờ xác nhận' OR @currentTrangThai = N'Đã xác nhận' -- QL được xóa ở "Chờ xác nhận" hoặc "Đã xác nhận"
			SET @canDelete = 1;
		ELSE -- Bao gồm cả trạng thái 'Đã xuất hàng' và các trạng thái khác không cho phép xóa
		BEGIN
			RAISERROR('Quản lý kho chỉ được xóa phiếu xuất ở trạng thái ''Chờ xác nhận'' hoặc ''Đã xác nhận''.', 16, 1); -- SỬA: Thông báo và trạng thái
			RETURN 1;
		END
	END
	ELSE -- Chức vụ không xác định hoặc không có quyền xóa
	BEGIN
	     RAISERROR('Chức vụ không hợp lệ hoặc không có quyền xóa phiếu xuất.', 16, 1); -- SỬA: Thông báo "phiếu xuất"
	     RETURN 1;
	END

	-- =============================================
	-- KẾT THÚC KIỂM TRA XÓA
	-- =============================================

	-- Nếu được phép xóa, gọi procedure xóa trên linked server
	IF @canDelete = 1
	BEGIN
		SET @sql = N'EXEC [' + @linkedServerName + '].phonestore.DBO.XoaPhieuXuat @maPX'; -- SỬA: Gọi procedure XoaPhieuXuat
		SET @paramDefinition = N'@maPX VARCHAR(100)';

		EXEC sp_executesql @sql, @paramDefinition, @maPX = @maPX; -- Truyền mã PX đầy đủ để xóa

		-- Thông báo xóa thành công (Tùy chọn)
        -- SELECT N'Đã xóa phiếu xuất ' + @maPX;
	END
	-- Nếu không được phép xóa (@canDelete = 0), các khối IF/ELSE IF kiểm tra đã RETURN với lỗi cụ thể.

END
GO

SELECT * FROM LINK3.phonestore.DBO.PHIEUXUAT
SELECT * FROM LINK3.phonestore.DBO.CTPX
SELECT * FROM LINK3.phonestore.DBO.KHO_PBSP


-- =============================================
-- Ví dụ thực thi procedure XoaPX_Goc trên Server Gốc
-- =============================================

-- GIẢ ĐỊNH:
-- Phiếu xuất '004' ở kho 'HN' đang có trạng thái là 'Chờ xác nhận'
-- Phiếu xuất '005' ở kho 'HN' đang có trạng thái là 'Đã xác nhận'
-- Phiếu xuất '003' ở kho 'HN' đang có trạng thái là 'Đã xuất hàng' (theo ảnh)
-- Có 2 mã chức vụ: 'NV' (Nhân viên), 'QL' (Quản lý Kho)

-- Ví dụ 1: NV xóa phiếu xuất '004' ở trạng thái 'Chờ xác nhận'. (ĐƯỢC)
EXEC XoaPX_Goc
	@maPX = 'PX_HN004', -- Mã gốc
	@maKho = 'HN',
	@maCV = 'CV003'; -- Nhân viên
-- Kết quả mong đợi: Xóa thành công PX_HN004 và chi tiết của nó trên LINK3.



-- 7. Thêm pbsp

DROP PROCEDURE ThemPBSP

CREATE PROCEDURE ThemPBSP (
	@maPBSP VARCHAR(50),
	@mauSac NVARCHAR(50),
	@ram NVARCHAR(50),
	@rom NVARCHAR(50),
	@giaBan decimal(18, 2),
	@trangThai VARCHAR(10),
	@maSP VARCHAR(50)
)
AS
BEGIN
	INSERT INTO PBSP(maPBSP, mauSac, ram, rom, giaBan, trangThai, maSP)
	VALUES (@maPBSP, @mauSac, @ram, @rom, @giaBan, @trangThai, @maSP);
END


ALTER PROCEDURE themPBSP_Goc (
	@maPBSP VARCHAR(50),
	@mauSac NVARCHAR(50),
	@ram NVARCHAR(50),
	@rom NVARCHAR(50),
	@giaBan decimal(18, 2),
	@trangThai VARCHAR(10),
	@maSP VARCHAR(50)
)
AS
BEGIN

	IF NOT EXISTS (
			SELECT 1
			FROM PBSP 
			WHERE mauSac = @mauSac AND ram = @ram AND rom = @rom AND maSP = @maSP
		)
	BEGIN
		Exec LINK0.phonestore.DBO.ThemPBSP @maPBSP, @mauSac, @ram, @rom, @giaBan, @trangThai, @maSP;
	END
	ELSE
	BEGIN
		-- Báo lỗi nếu mã kho không hợp lệ
		RAISERROR('Mã sản phẩm bị trùng vui lòng thay đổi', 16, 1);
		RETURN 1;
	END
END

Exec themPBSP_Goc 'PBSP013', N'Đen', '18GB', '128GB', 20000000, 'on', 'SP001';



SELECT * FROM LINK0.phonestore.DBO.PBSP

DELETE FROM PBSP WHERE maPBSP = 'PBSP012'

