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
				bcc.setSoNgayNghiKhongPhep(rs.getInt("soNgayNghiKP"));
				bcc.setSoNgayNghiPhepCoLuong(rs.getInt("soNPCoLuong"));
				bcc.setSoNgayNghiPhepKhongLuong(rs.getInt("soNPKhongLuong"));
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
				bcc.setSoNgayNghiKhongPhep(rs.getInt("soNgayNghiKhongPhep"));
				bcc.setSoNgayNghiPhepCoLuong(rs.getInt("soNgayNghiKhongPhepCoLuong"));
				bcc.setSoNgayNghiPhepKhongLuong(rs.getInt("soNgayNghiKhongPhepKhongLuong"));
				bcc.setMaNV(rs.getString("maNV"));
				
			}
			
			ps.close();
			rs.close();
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
			ps.setInt(5, bcc.getSoNgayNghiKhongPhep());
			ps.setInt(6, bcc.getSoNgayNghiPhepCoLuong());
			ps.setInt(7, bcc.getSoNgayNghiPhepKhongLuong());
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
	//get bang cham cong by nhan vien va thang nam
	public BangChamCongDTO getBangChamCongByNVAndThangNam(String maNV, int thang, int nam) {
		BangChamCongDTO bcc = new BangChamCongDTO();
		try {
			jdbc.openConnection();
			
			String query = "select * from bangchamcong where maNV=? and thangCC=? and namCC=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			ps.setInt(2, thang);
			ps.setInt(3, nam);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {				
				bcc.setMaBCC(rs.getString("maBCC"));
				bcc.setThangCC(rs.getInt("thangCC"));
				bcc.setNamCC(rs.getInt("namCC"));
				bcc.setSoNgayLam(rs.getInt("soNgayLam"));
				bcc.setSoNgayNghiKhongPhep(rs.getInt("soNgayNghiKhongPhep"));
				bcc.setSoNgayNghiPhepCoLuong(rs.getInt("soNgayNghiPhepCoLuong"));
				bcc.setSoNgayNghiPhepKhongLuong(rs.getInt("soNgayNghiPhepKhongLuong"));
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
}
