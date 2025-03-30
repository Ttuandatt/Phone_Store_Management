package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DTO.BangChamCongDTO;
import Database.JDBCConnection;

public class BangChamCongDAO implements DAOInterface<BangChamCongDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	
	@Override
	public ArrayList<BangChamCongDTO> selectAll() {
		ArrayList<BangChamCongDTO> arrBangChamCong = new ArrayList<BangChamCongDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from bangchamcong";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BangChamCongDTO bcc = new BangChamCongDTO();
				bcc.setMaBCC(rs.getString("maBCC"));
				bcc.setThangCC(rs.getInt("thangCC"));
				bcc.setNamCC(rs.getInt("namCC"));
				bcc.setSoNgayLam(rs.getInt("soNgayLam"));
				bcc.setSoNgayNghiPhep(rs.getInt("soNgayNghiPhep"));
				bcc.setSoNgayNghiKhongPhep(rs.getInt("SoNgayNghiKhongPhep"));
				bcc.setSoGioOT(rs.getInt("soGioOT"));
				bcc.setMaNV(rs.getString("maNV"));
				
				arrBangChamCong.add(bcc);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrBangChamCong;
	}

	@Override
	public BangChamCongDTO selectById(String maNV) {
		BangChamCongDTO bcc = new BangChamCongDTO();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from bangchamcong where maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bcc.setMaBCC(rs.getString("maBCC"));
				bcc.setThangCC(rs.getInt("thangCC"));
				bcc.setNamCC(rs.getInt("namCC"));
				bcc.setSoNgayLam(rs.getInt("soNgayLam"));
				bcc.setSoNgayNghiPhep(rs.getInt("soNgayNghiPhep"));
				bcc.setSoNgayNghiKhongPhep(rs.getInt("setSoNgayNghiKhongPhep"));
				bcc.setSoGioOT(rs.getInt("soGioOT"));
				bcc.setMaNV(rs.getString("maNV"));
				

			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return bcc;
	}

	@Override
	public int insert(BangChamCongDTO bcc) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "insert into bangchamcong values (?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, bcc.getMaBCC());
			ps.setInt(2, bcc.getThangCC());
			ps.setInt(3, bcc.getNamCC());
			ps.setInt(4, bcc.getSoNgayLam());
			ps.setInt(5, bcc.getSoNgayNghiPhep());
			ps.setInt(6, bcc.getSoNgayNghiKhongPhep());
			ps.setFloat(7, bcc.getSoGioOT());
			ps.setString(8, bcc.getMaNV());
			
			result = ps.executeUpdate();
			
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		
		return result;
	}
	

	@Override
	public int delete(BangChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BangChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
