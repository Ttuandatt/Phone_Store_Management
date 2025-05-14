/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.BangLuongDTO;
import DTO.NhanVienDTO;
import Database.JDBCConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class BangLuongDAO implements DAOInterface<BangLuongDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public BangLuongDTO selectById(String maBL) {
		BangLuongDTO bl = new BangLuongDTO();
		try {
			jdbc.openConnection();

			String query = "select * from BangLuong where maBL = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maBL);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bl.setMaLuong(rs.getString("maBL"));
				bl.setThangLuong(rs.getInt("thangLuong"));
				bl.setNamLuong(rs.getInt("namLuong"));
				bl.setLuongCB(rs.getFloat("luongCB"));
				bl.setHeSo(rs.getFloat("heSo"));
				bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
				bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
				bl.setThuong(rs.getFloat("thuong"));
				bl.setBhxh(rs.getFloat("bhxh"));
				bl.setBhyt(rs.getFloat("bhyt"));
				bl.setBhtn(rs.getFloat("bhtn"));
				bl.setThue(rs.getFloat("thueTNCN"));
				bl.setTamUng(rs.getFloat("tamUng"));
				bl.setThucNhan(rs.getFloat("thucNhan"));
				bl.setMaNV(rs.getString("maNV"));
				bl.setTrangThai(rs.getString("trangThai"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		return bl;
	}

	public ArrayList<BangLuongDTO> selectByTime(String thang, String nam) {
		ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			jdbc.openConnection();

			StringBuilder query = new StringBuilder("SELECT * FROM BangLuong WHERE 1=1");
			ArrayList<Object> params = new ArrayList<>();

			if (!thang.equals("Tháng")) {
				query.append(" AND thangLuong = ?");
				params.add(Integer.parseInt(thang));
			}

			if (!nam.equals("Năm")) {
				query.append(" AND namLuong = ?");
				params.add(Integer.parseInt(nam));
			}

			// Không có điều kiện tìm kiếm → trả về rỗng hoặc toàn bộ, tuỳ yêu cầu
			if (params.isEmpty()) {
				return arrBangLuong; // hoặc query toàn bộ nếu muốn
			}

			ps = jdbc.getConnection().prepareStatement(query.toString());

			// Gán tham số cho PreparedStatement
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				BangLuongDTO bl = new BangLuongDTO();
				bl.setMaLuong(rs.getString("maBL"));
				bl.setThangLuong(rs.getInt("thangLuong"));
				bl.setNamLuong(rs.getInt("namLuong"));
				bl.setLuongCB(rs.getFloat("luongCB"));
				bl.setHeSo(rs.getFloat("heSo"));
				bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
				bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
				bl.setThuong(rs.getFloat("thuong"));
				bl.setBhxh(rs.getFloat("bhxh"));
				bl.setBhyt(rs.getFloat("bhyt"));
				bl.setBhtn(rs.getFloat("bhtn"));
				bl.setThue(rs.getFloat("thueTNCN"));
				bl.setTamUng(rs.getFloat("tamUng"));
				bl.setThucNhan(rs.getFloat("thucNhan"));
				bl.setMaNV(rs.getString("maNV"));
				bl.setTrangThai(rs.getString("trangThai"));

				arrBangLuong.add(bl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			jdbc.closeConnection();
		}

		return arrBangLuong;
	}

	@Override
	public ArrayList<BangLuongDTO> selectAll() {
		ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<BangLuongDTO>();
		try {
			jdbc.openConnection();

			String query = "select * from BangLuong";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BangLuongDTO bl = new BangLuongDTO();
				bl.setMaLuong(rs.getString("maBL"));
				bl.setThangLuong(rs.getInt("thangLuong"));
				bl.setNamLuong(rs.getInt("namLuong"));
				bl.setLuongCB(rs.getFloat("luongCB"));
				bl.setHeSo(rs.getFloat("heSo"));
				bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
				bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
				bl.setThuong(rs.getFloat("thuong"));
				bl.setBhxh(rs.getFloat("bhxh"));
				bl.setBhyt(rs.getFloat("bhyt"));
				bl.setBhtn(rs.getFloat("bhtn"));
				bl.setThue(rs.getFloat("thueTNCN"));
				bl.setTamUng(rs.getFloat("tamUng"));
				bl.setThucNhan(rs.getFloat("thucNhan"));
				bl.setMaNV(rs.getString("maNV"));
				bl.setTrangThai(rs.getString("trangThai"));

				arrBangLuong.add(bl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		return arrBangLuong;
	}

	@Override
	public int delete(BangLuongDTO t) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from
																		// nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public int insert(BangLuongDTO t) {
		int result = 0;
		try {
			jdbc.openConnection();

			String sql = "INSERT INTO BangLuong (maBL, thangLuong, namLuong, luongCB, heSo, phuCapAnTrua, phuCapDiLai, "
					+ "thuong, bhxh, bhyt, bhtn, thueTNCN, tamUng, thucNhan, maNV, trangThai) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(sql);
			ps.setString(1, t.getMaLuong());
			ps.setInt(2, t.getThangLuong());
			ps.setInt(3, t.getNamLuong());
			ps.setFloat(4, t.getLuongCB());
			ps.setFloat(5, t.getHeSo());
			ps.setFloat(6, t.getPhuCapAnTrua());
			ps.setFloat(7, t.getPhuCapDiLai());
			ps.setFloat(8, t.getThuong());
			ps.setFloat(9, t.getBhxh());
			ps.setFloat(10, t.getBhyt());
			ps.setFloat(11, t.getBhtn());
			ps.setFloat(12, t.getThue());
			ps.setFloat(13, t.getTamUng());
			ps.setFloat(14, t.getThucNhan());
			ps.setString(15, t.getMaNV());
			ps.setString(16, t.getTrangThai());

			result = ps.executeUpdate(); // result = 1 nếu insert thành công
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.closeConnection();
		}
		return result;
	}

	@Override
	public int update(BangLuongDTO t) {
		int result = 0;
		try {
			jdbc.openConnection();

			String query = "update BangLuong set luongCB=?, heSo=?, phuCapAnTrua=?, phuCapDiLai=?, thuong=?, bhxh=?, bhyt=?, bhtn=?, thueTNCN=?, tamUng=?, thucNhan=?, trangThai=? where maBL=?";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setFloat(1, t.getLuongCB());
			ps.setFloat(2, t.getHeSo());
			ps.setFloat(3, t.getPhuCapAnTrua());
			ps.setFloat(4, t.getPhuCapDiLai());
			ps.setFloat(5, t.getThuong());
			ps.setFloat(6, t.getBhxh());
			ps.setFloat(7, t.getBhyt());
			ps.setFloat(8, t.getBhtn());
			ps.setFloat(9, t.getThue());
			ps.setFloat(10, t.getTamUng());
			ps.setFloat(11, t.getThucNhan());
			ps.setString(12, t.getTrangThai());
			ps.setString(13, t.getMaLuong());

			result = ps.executeUpdate(); // Sử dụng executeUpdate() để lấy số dòng bị ảnh hưởng
		} catch (SQLException e) {
			e.printStackTrace(); // Ghi log lỗi để dễ debug
		} finally {
			jdbc.closeConnection();
		}
		return result;
	}

	public ArrayList<BangLuongDTO> selectByKeyWord(String keyWord) {
		ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<>();
		try {
			jdbc.openConnection();

			String query = "SELECT * FROM BangLuong bl " + "JOIN NhanVien nv ON bl.maNV = nv.maNV "
					+ "WHERE maBL LIKE ? OR bl.maNV LIKE ? OR hoTen LIKE ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			String keywordPattern = "%" + keyWord + "%";
			ps.setString(1, keywordPattern);
			ps.setString(2, keywordPattern);
			ps.setString(3, keywordPattern);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BangLuongDTO bl = new BangLuongDTO();
				bl.setMaLuong(rs.getString("maBL"));
				bl.setThangLuong(rs.getInt("thangLuong"));
				bl.setNamLuong(rs.getInt("namLuong"));
				bl.setLuongCB(rs.getFloat("luongCB"));
				bl.setHeSo(rs.getFloat("heSo"));
				bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
				bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
				bl.setThuong(rs.getFloat("thuong"));
				bl.setBhxh(rs.getFloat("bhxh"));
				bl.setBhyt(rs.getFloat("bhyt"));
				bl.setBhtn(rs.getFloat("bhtn"));
				bl.setThue(rs.getFloat("thueTNCN"));
				bl.setTamUng(rs.getFloat("tamUng"));
				bl.setThucNhan(rs.getFloat("thucNhan")); // sửa đúng thuộc tính
				bl.setMaNV(rs.getString("maNV"));
				bl.setTrangThai(rs.getString("trangThai"));

				arrBangLuong.add(bl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbc.closeConnection();
		}
		return arrBangLuong;
	}

	public ArrayList<BangLuongDTO> selectByNV(String maNV) {
		ArrayList<BangLuongDTO> arrBangLuong = new ArrayList<BangLuongDTO>();
		try {
			jdbc.openConnection();

			String query = "select * from bangluong where maNV=?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BangLuongDTO bl = new BangLuongDTO();
				bl.setMaLuong(rs.getString("maBL"));
				bl.setThangLuong(rs.getInt("thangLuong"));
				bl.setNamLuong(rs.getInt("namLuong"));
				bl.setLuongCB(rs.getFloat("luongCB"));
				bl.setHeSo(rs.getFloat("heSo"));
				bl.setPhuCapAnTrua(rs.getFloat("phuCapAnTrua"));
				bl.setPhuCapDiLai(rs.getFloat("phuCapDiLai"));
				bl.setThuong(rs.getFloat("thuong"));
				bl.setBhxh(rs.getFloat("bhxh"));
				bl.setBhyt(rs.getFloat("bhyt"));
				bl.setBhtn(rs.getFloat("bhtn"));
				bl.setThue(rs.getFloat("thueTNCN"));
				bl.setTamUng(rs.getFloat("tamUng"));
				bl.setThucNhan(rs.getFloat("thucNhan"));
				bl.setMaNV(rs.getString("maNV"));
				bl.setTrangThai(rs.getString("trangThai"));

				arrBangLuong.add(bl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}

		return arrBangLuong;
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

}
