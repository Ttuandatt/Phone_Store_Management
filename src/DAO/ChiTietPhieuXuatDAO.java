package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietPhieuXuatDTO;
import DTO.PhieuXuatDTO;
import Database.JDBCConnection;

public class ChiTietPhieuXuatDAO implements DAOInterface<ChiTietPhieuXuatDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<ChiTietPhieuXuatDTO> selectAll() {
		return null;
	}
	
	public ArrayList<ChiTietPhieuXuatDTO> selectAllById(String maPX) {
		ArrayList<ChiTietPhieuXuatDTO> arrCTPX = new ArrayList<ChiTietPhieuXuatDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from ctpx where maPX = ?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPX);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietPhieuXuatDTO ctpx = new ChiTietPhieuXuatDTO();
				ctpx.setSoLuong(rs.getInt("soLuong"));
				ctpx.setGiaXuat(rs.getDouble("giaXuat"));
				ctpx.setMaPX(rs.getString("maPX"));
				ctpx.setMaPBSP(rs.getString("maPBSP"));
				
				arrCTPX.add(ctpx);
			}
			
			ps.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		return arrCTPX;
	}

	@Override
	public ChiTietPhieuXuatDTO selectById(String maPX) {
		return null;
	}

	@Override
	public int insert(ChiTietPhieuXuatDTO ctpx) {
		int result = 0;

		try {
			jdbc.openConnection();

			String query = "insert into ctpx(soLuong, giaXuat, maPX, ma values(?,?,?,?)";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setInt(1, ctpx.getSoLuong());
			ps.setDouble(2, ctpx.getGiaXuat());
			ps.setString(3, ctpx.getMaPX());
			ps.setString(4, ctpx.getMaPBSP());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();

		}
		return result;
	}

	@Override
	public int delete(ChiTietPhieuXuatDTO ctpx) {
		return 0;
	}

	@Override
	public int update(ChiTietPhieuXuatDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public ArrayList<ChiTietPhieuXuatDTO> getThongTinCTPX(String maPX){
		ArrayList<ChiTietPhieuXuatDTO> arrctpx = new ArrayList<ChiTietPhieuXuatDTO>();
		try {
			jdbc.openConnection();
			
			String query = "select soluong, giaxuat, mapbsp from ctpx where maPX=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPX);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietPhieuXuatDTO ctpx = new ChiTietPhieuXuatDTO();
				
				ctpx.setSoLuong(rs.getInt("soLuong"));
				ctpx.setGiaXuat(rs.getDouble("giaXuat"));
				ctpx.setMaPBSP(rs.getString("maPBSP"));
			
				
				arrctpx.add(ctpx);
			}
			
			ps.close();
			rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrctpx;
	}
}
