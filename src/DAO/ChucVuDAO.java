package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChucVuDTO;
import DTO.NhanVienDTO;
import Database.JDBCConnection;

public class ChucVuDAO implements DAOInterface<ChucVuDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	
	
	@Override
	public ArrayList<ChucVuDTO> selectAll() {
		ArrayList<ChucVuDTO> arrChucVu = new ArrayList<ChucVuDTO>();
		try {
			
			jdbc.openConnection();
			
			String query = "SELECT * FROM CHUCVU";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChucVuDTO cv = new ChucVuDTO();
				cv.setMaCV(rs.getString("maCV"));
				cv.setTenCV(rs.getString("tenCV"));
				cv.setLuongCoBan(rs.getFloat("luongCB"));
//				cv.setHeSoLuong(rs.getFloat("heSo"));
				
				
				arrChucVu.add(cv);
			}
			rs.close();
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrChucVu;
	}

	@Override
	public ChucVuDTO selectById(String maCV) {
		ChucVuDTO cv = new ChucVuDTO();
		
		try {
			
			jdbc.openConnection();
			
			String query = "select * from nhanvien where maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maCV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				cv.setMaCV(rs.getString("maCV"));
				cv.setTenCV(rs.getString("tenCV"));
				cv.setLuongCoBan(rs.getFloat("luongCB"));
				cv.setTrangThai(rs.getString("trangThai"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			//Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		return cv;
	}
	
	public String getIdByName(String tenCV) {
		String maCV="";

		try {

			jdbc.openConnection();

			String query = "select maCV from chucvu where tenCV = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, tenCV);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				maCV = rs.getString("maCV");
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSD
			jdbc.closeConnection();
		}

		return maCV;
	}

	public String getRoleNameByRoleId(String maCV) {
		String tenCV="";

		try {

			jdbc.openConnection();

			String query = "select tenCV from chucvu where maCV = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maCV);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tenCV = rs.getString("tenCV");
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSD
			jdbc.closeConnection();
		}

		return tenCV;
	}
	
	@Override
	public int insert(ChucVuDTO cv) {
		int result = 0;
		try {
			
			jdbc.openConnection();
			
			String query = "insert into chucvu values(maCV,tenCV,luongCB,trangThai)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, cv.getMaCV());
			ps.setString(2, cv.getTenCV());
			ps.setFloat(3, cv.getLuongCoBan());
//			ps.setFloat(4, cv.getHeSoLuong());
			ps.setString(4, "on");
			
			result = ps.executeUpdate();
			
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return result;
	}

	@Override
	public int delete(ChucVuDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChucVuDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
