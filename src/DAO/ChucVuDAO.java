package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChucVuDTO;
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
				cv.setHeSoLuong(rs.getFloat("heSo"));
				
				
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
	public ChucVuDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ChucVuDTO cv) {
		int result = 0;
		try {
			
			jdbc.openConnection();
			
			String query = "insert into chucvu values(?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, cv.getMaCV());
			ps.setString(2, cv.getTenCV());
			ps.setFloat(3, cv.getLuongCoBan());
			ps.setFloat(4, cv.getHeSoLuong());
			ps.setString(5, "on");
			
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
