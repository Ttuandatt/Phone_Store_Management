package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietChamCongDTO;
import Database.JDBCConnection;
import java.time.LocalDate;
import java.sql.Date;

public class ChiTietChamCongDAO implements DAOInterface<ChiTietChamCongDTO>{
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<ChiTietChamCongDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChiTietChamCongDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC) {
		ArrayList<ChiTietChamCongDTO> arr = new ArrayList<ChiTietChamCongDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select ngayTao, loaiChamCong, soGioOT from chitietchamcong where mabcc=? and soGioOT>0";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maBCC);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietChamCongDTO ctcc = new ChiTietChamCongDTO();
				Date sqlDate = rs.getDate("ngayTao");
                                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
				ctcc.setNgayTao(localDate);
				ctcc.setLoaiChamCong(rs.getString("loaiChamCong"));
				ctcc.setSoGioOT(rs.getFloat("soGioOT"));
				
				arr.add(ctcc);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arr;
	}
	
	public ArrayList<ChiTietChamCongDTO> getThongTinNgayNghi(String maBCC){
 		ArrayList<ChiTietChamCongDTO> arr = new ArrayList<ChiTietChamCongDTO>();
 		try {
 			jdbc.openConnection();
 			
 			String query = "select ngayChamCong, loaiChamCong, chiTiet from chitietchamcong where maBCC=? and loaiChamCong like N'Nghỉ%'";
 			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
 			ps.setString(1, maBCC);
 			
 			ResultSet rs = ps.executeQuery();
 			while(rs.next()) {
 				ChiTietChamCongDTO ctcc = new ChiTietChamCongDTO();
 				ctcc.setNgayTao(rs.getDate("ngayChamCong").toLocalDate());
 				ctcc.setLoaiChamCong(rs.getString("loaiChamCong"));
 				ctcc.setChiTiet(rs.getString("chiTiet"));
 				
 				arr.add(ctcc);
 			}
 		}catch (Exception e) {
 			e.printStackTrace();
 			e.getMessage();
 		}finally {
 			jdbc.closeConnection();
 		}
 		return arr;
 	}
 	
}
