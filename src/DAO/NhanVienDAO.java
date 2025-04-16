package DAO;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Result;

import Database.*;
import DTO.NhanVienDTO;

public class NhanVienDAO implements DAOInterface<NhanVienDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<NhanVienDTO> selectAll() {
		ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();

		try {

			// Thiết lập kết nối tới Database
			jdbc.openConnection();

			// Tạo query
			String query = "exec sp_layDanhSachNhanVien"; // dùng stored procedure thay vì dùng raw sql

			// Tạo đối tượng PreparedStatement
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

			// Xử lý kết quả
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				NhanVienDTO nv = new NhanVienDTO();
				nv.setMaNV(rs.getString("maNV"));
				nv.setHoTen(rs.getString("hoTen"));
				nv.setNgaySinh(rs.getDate("ngaySinh"));
				nv.setGioiTinh(rs.getString("gioiTinh"));
				nv.setDiaChi(rs.getString("diaChi"));
				nv.setSoDienThoai(rs.getString("sdt"));
				nv.setEmail(rs.getString("email"));
				nv.setTrangThai(rs.getString("trangThai"));
				nv.setChucVu(rs.getString("maCV"));
				nv.setChiNhanh(rs.getString("chiNhanh"));
				nv.setMatKhau(rs.getString("matKhau"));
				nv.setHinhAnh(rs.getBytes("hinhAnh")); // Xử lý ảnh (BLOB)

				// Thêm vào danh sách
				arrNhanVien.add(nv);
			}
			// Đóng tài nguyên
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		

		return arrNhanVien;
	}
	
	public ArrayList<NhanVienDTO> selectAllByRoleName(String role){
		ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from nhanvien inner join chucvu on nhanvien.maCV = chucvu.maCV where chucvu.tenCV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, role);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVienDTO nv = new NhanVienDTO();
				nv.setMaNV(rs.getString("maNV"));
				nv.setHoTen(rs.getString("hoTen"));
				nv.setNgaySinh(rs.getDate("ngaySinh"));
				nv.setGioiTinh(rs.getString("gioiTinh"));
				nv.setDiaChi(rs.getString("diaChi"));
				nv.setSoDienThoai(rs.getString("sdt"));
				nv.setEmail(rs.getString("email"));
				nv.setTrangThai(rs.getString("trangThai"));
				nv.setChucVu(rs.getString("maCV"));
				nv.setChiNhanh(rs.getString("chiNhanh"));
				nv.setMatKhau(rs.getString("matKhau"));
				nv.setHinhAnh(rs.getBytes("hinhAnh")); // Xử lý ảnh (BLOB)

				// Thêm vào danh sách
				arrNhanVien.add(nv);

			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		return arrNhanVien;
	}

	
	public ArrayList<NhanVienDTO> selectAllByWarehouseName(String kho){
		ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from nhanvien inner join kho on nhanvien.chiNhanh = kho.maKho where kho.tenKho=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, kho);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVienDTO nv = new NhanVienDTO();
				nv.setMaNV(rs.getString("maNV"));
				nv.setHoTen(rs.getString("hoTen"));
				nv.setNgaySinh(rs.getDate("ngaySinh"));
				nv.setGioiTinh(rs.getString("gioiTinh"));
				nv.setDiaChi(rs.getString("diaChi"));
				nv.setSoDienThoai(rs.getString("sdt"));
				nv.setEmail(rs.getString("email"));
				nv.setTrangThai(rs.getString("trangThai"));
				nv.setChucVu(rs.getString("maCV"));
				nv.setChiNhanh(rs.getString("chiNhanh"));
				nv.setMatKhau(rs.getString("matKhau"));
				nv.setHinhAnh(rs.getBytes("hinhAnh")); // Xử lý ảnh (BLOB)

				// Thêm vào danh sách
				arrNhanVien.add(nv);

			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		return arrNhanVien;
	}
	
	
	@Override
	public NhanVienDTO selectById(String maNV) {
		NhanVienDTO nv = new NhanVienDTO();

		try {

			jdbc.openConnection();

			String query = "exec sp_layNhanVienTheoID ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nv.setMaNV(rs.getString("maNV"));
				nv.setHoTen(rs.getString("hoTen"));
				nv.setNgaySinh(rs.getDate("ngaySinh"));
				nv.setGioiTinh(rs.getString("gioiTinh"));
				nv.setDiaChi(rs.getString("diaChi"));
				nv.setSoDienThoai(rs.getString("sdt"));
				nv.setEmail(rs.getString("email"));
				nv.setHinhAnh(rs.getBytes("hinhAnh")); // Xử lý ảnh (BLOB)
				nv.setMatKhau(rs.getString("matKhau"));
				nv.setChucVu(rs.getString("maCV"));
				nv.setChiNhanh(rs.getString("chiNhanh"));
				nv.setTrangThai(rs.getString("trangThai"));

			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}

		return nv;
	}

	@Override
	public int insert(NhanVienDTO nv) {
		int result = 0;
		try {
			jdbc.openConnection();

			// Gọi stored procedure
			String query = "{ ? = CALL sp_themNhanVien(maNV, hoTen, ngaySinh, gioiTinh, diaChi, sdt, email, hinhAnh, matKhau, trangThai, maCV, chiNhanh) }";
			CallableStatement cs = jdbc.getConnection().prepareCall(query); // Dùng CallableStatement thay vì
																			// PreparedStatement vì PreparedStatement
																			// không thể lấy được giá trị logic được trả
																			// về từ Stored Procedure còn
																			// CallableStatement thì có thể. Search
																			// ChatGPT để hiêu rõ hơn khác biệt giữa
																			// chúng

			// Đăng ký tham số trả về
			cs.registerOutParameter(1, java.sql.Types.INTEGER);

			// Truyền tham số đầu vào
			cs.setString(2, nv.getMaNV());
			cs.setString(3, nv.getHoTen());
			cs.setDate(4, nv.getNgaySinh());
			cs.setString(5, nv.getGioiTinh());
			cs.setString(6, nv.getDiaChi());
			cs.setString(7, nv.getSoDienThoai());
			cs.setString(8, nv.getEmail());
			cs.setBytes(9, nv.getHinhAnh());
			cs.setString(10, nv.getMatKhau());
			cs.setString(11, nv.getTrangThai());

			if (nv.getChucVu() == null || nv.getChucVu().trim().isEmpty()) {
				cs.setNull(12, java.sql.Types.VARCHAR);
			} else {
				cs.setString(12, nv.getChucVu());
			}

			cs.setString(13, nv.getChiNhanh());

			// Thực thi Stored Procedure
			cs.execute();

			// Lấy giá trị trả về từ stored procedure
			result = cs.getInt(1);

			// Đóng tài nguyên
			cs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.closeConnection();
		}

		System.out.println("NhanVienDAO | method: insert: giá trị của biến int result: " + result);
		return result;
	}

	@Override
	public int delete(NhanVienDTO nv) {
		int result = 0;
		
		try {
			
			jdbc.openConnection();
			
			String query = "update nhanvien set trangThai='Off' where maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, nv.getMaNV());
			
			result = ps.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		
		return result;
	}

	@Override
	public int update(NhanVienDTO nv) {
		int result = 0;

		try {

			jdbc.openConnection();

			String query = "update nhanvien set hoTen=?, ngaySinh=?, gioiTinh=?, diaChi=?, sdt=?, email=?, hinhAnh=?, matKhau=?, trangThai=?, maCV=?, chiNhanh=? where maNV=?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, nv.getHoTen());
			ps.setDate(2, nv.getNgaySinh());
			ps.setString(3, nv.getGioiTinh());
			ps.setString(4, nv.getDiaChi());
			ps.setString(5, nv.getSoDienThoai());
			ps.setString(6, nv.getEmail());
			ps.setBytes(7, nv.getHinhAnh());
			ps.setString(8,  nv.getMatKhau());
			ps.setString(9, nv.getTrangThai());
			ps.setString(10, nv.getChucVu());
			ps.setString(11, nv.getChiNhanh());
			ps.setString(12, nv.getMaNV());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}

		return result;
	}

	//Hàm update khi ta không sửa ảnh của nhân viên trong quá trình cập nhật thông tin nhân viên
	public int updateWithoutChangingImage(NhanVienDTO nv) {
		int result = 0;

		try {

			jdbc.openConnection();

			String query = "update nhanvien set hoTen=?, ngaySinh=?, gioiTinh=?, diaChi=?, sdt=?, email=?, matKhau=?, trangThai=?, maCV=?, chiNhanh=? where maNV=?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, nv.getHoTen());
			ps.setDate(2, nv.getNgaySinh());
			ps.setString(3, nv.getGioiTinh());
			ps.setString(4, nv.getDiaChi());
			ps.setString(5, nv.getSoDienThoai());
			ps.setString(6, nv.getEmail());
			ps.setString(7, nv.getMatKhau());
			ps.setString(8, nv.getTrangThai());
			ps.setString(9, nv.getChucVu());
			ps.setString(10, nv.getChiNhanh());
			ps.setString(11, nv.getMaNV());
			
			result = ps.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		System.out.println(result);
		return result;
	}
	
	
	public Double getBaseSalaryByRoleID(String maCV) {
		Double baseSalary = 0.0;
		try {

			jdbc.openConnection();

			String query = "select luongCB from chucvu where maCV = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maCV);

			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				baseSalary = rs.getDouble("luongCB");
			}
			
			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		return baseSalary;
	}

	public int getSoNgayCong(int thangCC, int namCC, String maNV) {
		int soNgayCong = 0;
		log("thangCC="+thangCC);
		log("namCC="+namCC);
		log("maNV="+maNV);
		try {
			jdbc.openConnection();
			
			String query = "select soNgayLam from bangchamcong where thangCC=? and namCC=? and maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, thangCC);
			ps.setInt(2, namCC);
			ps.setString(3, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soNgayCong = rs.getInt("soNgayLam");
			}
			
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		
		log("soNgayCong=" + soNgayCong);
		return soNgayCong;
	}
	
	public int getSoNgayNghiPhepCoLuong(int thangCC, int namCC, String maNV) {
		int soNgayNghiPhepCoLuong = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "select soNPCoLuong from bangchamcong where thangCC=? and namCC=? and maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, thangCC);
			ps.setInt(2, namCC);
			ps.setString(3, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soNgayNghiPhepCoLuong = rs.getInt("soNPCoLuong");
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			
		}
			jdbc.closeConnection();
		
		
		return soNgayNghiPhepCoLuong;
	}
	
	public int getSoNgayNghiPhepKhongLuong(int thangCC, int namCC, String maNV) {
		int soNgayNghiPhepKhongLuong = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "select soNPKhongLuong from bangchamcong where thangCC=? and namCC=? and maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, thangCC);
			ps.setInt(2, namCC);
			ps.setString(3, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soNgayNghiPhepKhongLuong = rs.getInt("soNPKhongLuong");
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			
		}
			jdbc.closeConnection();
		
		
		return soNgayNghiPhepKhongLuong;
	}
	
	public int getSoNgayNghiKhongPhep(int thangCC, int namCC, String maNV) {
		int soNgayNghiKhongPhep = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "select soNgayNghiKP from bangchamcong where thangCC=? and namCC=? and maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, thangCC);
			ps.setInt(2, namCC);
			ps.setString(3, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soNgayNghiKhongPhep = rs.getInt("soNgayNghiKP");
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			
		}
			jdbc.closeConnection();
		
		
		return soNgayNghiKhongPhep;
	}
	
	public double getSoGioTangCa(int thangCC, int namCC, String maNV) {
		double soGioTangCaNgayThuong = 0;
		double soGioTangCaNgayLe = 0;
		double soGioTangCaChuNhat = 0;
		double tongSoGioTangCa=0;
		try {
			jdbc.openConnection();
			
			String query = "select soGioOTNgayThuong, soGioOTNgayLe, soGioOTCn from bangchamcong where thangCC=? and namCC=? and maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, thangCC);
			ps.setInt(2, namCC);
			ps.setString(3, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soGioTangCaNgayThuong = rs.getDouble("soGioOTNgayThuong");
				soGioTangCaNgayLe = rs.getDouble("soGioOTNgayLe");
				soGioTangCaChuNhat = rs.getDouble("soGioOTCn");
				tongSoGioTangCa = soGioTangCaNgayThuong + soGioTangCaNgayLe + soGioTangCaChuNhat;
			}
			
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			
		}
			jdbc.closeConnection();
		
		
		return tongSoGioTangCa;
	}
	
	//hàm hiển thị thông tin dòng code
  	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}
}
