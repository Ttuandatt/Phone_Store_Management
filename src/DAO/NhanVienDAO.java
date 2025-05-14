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
		String query="";
		try {

			// Thiết lập kết nối tới Database
			jdbc.openConnection();
			
			//Nếu kết nối tới CSDL là server gốc thì dùng Stored Procedure sp_LayDanhSachNhanVienGoc
			if(JDBCConnection.getDatabaseUrl().equalsIgnoreCase("jdbc:sqlserver://DAMIAN\\MSSQLSERVER06;databaseName=phonestore;integratedSecurity=true;encrypt=false")) {
				query = "exec sp_LayDanhSachNhanVienGoc";
			}else { // Nếu kết nối tới CSDL là server mảnh thì dùng Stored Procedure sp_LayDanhSachNhanVienKho
				query = "exec sp_LayDanhSachNhanVienKho";
			}
			
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

	
	public ArrayList<NhanVienDTO> selectAllByWarehouseId(String maKho) {
	    ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();

	    try {
	        jdbc.openConnection();

	        // Gọi stored procedure
	        String query = "{CALL sp_LayDanhSachNhanVienTheoKhoGoc(?)}";
	        CallableStatement cs = jdbc.getConnection().prepareCall(query);
	        cs.setString(1, maKho);

	        ResultSet rs = cs.executeQuery();
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
	            nv.setChiNhanh(rs.getString("chiNhanh")); // do procedure thêm cột chiNhanh
	            nv.setMatKhau(rs.getString("matKhau"));
	            nv.setHinhAnh(rs.getBytes("hinhAnh"));

	            arrNhanVien.add(nv);
	        }

	        rs.close();
	        cs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
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
	    String query = "";
	    
	    try {
	        jdbc.openConnection();

			// Nếu kết nối tới CSDL là server gốc thì dùng Stored Procedure sp_ThemNhanVienGoc
			if (JDBCConnection.getDatabaseUrl().equalsIgnoreCase("jdbc:sqlserver://DAMIAN\\MSSQLSERVER06;databaseName=phonestore;integratedSecurity=true;encrypt=false")) {
		        query = "{CALL sp_ThemNhanVienGoc(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			} else { // Nếu kết nối tới CSDL là server mảnh thì dùng Stored Procedure sp_ThemNhanVienKho
		        query = "{CALL sp_ThemNhanVienKho(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			}	        
			log("query="+query);
	        CallableStatement cs = jdbc.getConnection().prepareCall(query);

	        // Truyền các tham số IN
	        cs.setString(1, nv.getHoTen());
	        cs.setDate(2, nv.getNgaySinh());
	        cs.setString(3, nv.getGioiTinh());
	        cs.setString(4, nv.getDiaChi());
	        cs.setString(5, nv.getSoDienThoai());
	        cs.setString(6, nv.getEmail());
	        cs.setBytes(7, nv.getHinhAnh());
	        cs.setString(8, nv.getMatKhau());
	        cs.setString(9, nv.getChucVu());

	        // Vì procedure sẽ tự xác định chi nhánh từ server name,
	        // bạn không cần truyền vào từ DAO. Tuy nhiên nếu bạn vẫn giữ tham số @maKho
	        // thì có thể truyền null hoặc bỏ đi (trong bản procedure bạn đã bỏ rồi)
	        cs.setString(10, nv.getChiNhanh()); // @maKho (không dùng nữa)

	        // Đăng ký tham số OUT: @maNV
	        cs.registerOutParameter(11, java.sql.Types.VARCHAR);

	        // Thực thi
	        cs.execute();

	        // Lấy mã nhân viên đã tạo từ procedure
	        String maNV = cs.getString(11);
	        nv.setMaNV(maNV); // lưu lại nếu muốn dùng

	        System.out.println("Mã nhân viên được tạo: " + maNV);
	        result = 1; // Đánh dấu thêm thành công

	        cs.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        result = 0; // Thêm thất bại
	    } finally {
	        jdbc.closeConnection();
	    }
	    
	    log("reuslt="+result);
	    return result;
	}

	@Override
	public int delete(NhanVienDTO nv) {
		int result = 0;
		
		try {
			
			jdbc.openConnection();
			
			String query = "update nhanvien set trangThai='off' where maNV=?";
			
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

	public int updateWithoutChangingImage(NhanVienDTO nv) {
	    int result = 0;
	    String query="";
	    try {
	        jdbc.openConnection();

			// Nếu kết nối tới CSDL là server gốc thì dùng Stored Procedure
			// sp_ThemNhanVienGoc
			if (JDBCConnection.getDatabaseUrl().equalsIgnoreCase("jdbc:sqlserver://DAMIAN\\MSSQLSERVER06;databaseName=phonestore;integratedSecurity=true;encrypt=false")) {
				query = "{call sp_SuaNhanVienGoc(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			} else { // Nếu kết nối tới CSDL là server mảnh thì dùng Stored Procedure
						// sp_ThemNhanVienKho
				query = "{call sp_SuaNhanVienKho(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			}
			log("query="+query);
			CallableStatement cs = jdbc.getConnection().prepareCall(query);

	        cs.setString(1, nv.getMaNV());
	        cs.setString(2, nv.getHoTen());
	        cs.setDate(3, nv.getNgaySinh());
	        cs.setString(4, nv.getGioiTinh());
	        cs.setString(5, nv.getDiaChi());
	        cs.setString(6, nv.getSoDienThoai());
	        cs.setString(7, nv.getEmail());
	        cs.setString(8, nv.getMatKhau());
	        cs.setString(9, nv.getTrangThai());
	        cs.setString(10, nv.getChucVu());

	        
	        // Vì trong Procedure có lệnh SET NO COUNT ON, có tác dụng vô hiệu hóa việc trả về số dòng bị ảnh hưởng sau mỗi lệnh SQL như INSERT, UPDATE, DELETE,... khi chạy trong Stored Procedure.
	        // Vì vậy dù cho có cập nhật được nhưng result trả về vẫn là -1
	        result = cs.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        jdbc.closeConnection();
	    }

	    log("result="+result);
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
	
	
	// update thông tin nhân viên NhanVienDTO(maNV, hoTen, soDienThoai, diaChi, ngaySinh, gioiTinh,hinhAnh);
		public boolean updatePersonalInfo(NhanVienDTO nv) {
			int result = 0;
			try {
				jdbc.openConnection();
				
				String query = "update nhanvien set hoTen=?, sdt=?, diaChi=?, ngaySinh=?, gioiTinh=?,hinhAnh=? where maNV=?";			
				
				PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
				ps.setString(1, nv.getHoTen());
				ps.setString(2, nv.getSoDienThoai());
				ps.setString(3, nv.getDiaChi());
				ps.setDate(4, nv.getNgaySinh());
				ps.setString(5, nv.getGioiTinh());
				ps.setBytes(6, nv.getHinhAnh());
				ps.setString(7, nv.getMaNV());
				result = ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			} finally {
				jdbc.closeConnection();
			}
			
			if(result > 0) {
				return true;
			}
			return false;
		}
		
		
		public String getChucVuByMaNV(String maNV) {
			String chucVu = "";
		
			try {
				jdbc.openConnection();
				
				String query = "select tenCV from chucvu left join nhanvien on nhanvien.maCV = chucvu.maCV where maNV=?";
				PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
				ps.setString(1, maNV);

				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					chucVu = rs.getString("tenCV");
				}
			}catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}finally {
				jdbc.closeConnection();
			}
			
			return chucVu;
		}

		
		public ArrayList<NhanVienDTO> selectNhanVienMoi() {
	        ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();

	        try {
	            // Mở kết nối CSDL
	            jdbc.openConnection();

	            // Gọi stored procedure
	            String query = "select * from NhanVien WHERE maNV NOT IN (SELECT maNV FROM BangLuong)";
	            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

	            // Thực thi truy vấn
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
	                nv.setHinhAnh(rs.getBytes("hinhAnh")); // BLOB ảnh

	                arrNhanVien.add(nv);
	            }

	            rs.close();
	            ps.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            jdbc.closeConnection();
	        }

	        return arrNhanVien;
	    }
		
		
		
		// hàm hiển thị thông tin dòng code
		public static void log(String message) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
			System.out.println(element.getClassName() + " | method: " + element.getMethodName() + " | line: "
					+ element.getLineNumber() + " | " + message);
		}
}
