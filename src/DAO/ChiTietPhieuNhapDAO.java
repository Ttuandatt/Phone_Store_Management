package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import Database.JDBCConnection;

public class ChiTietPhieuNhapDAO implements DAOInterface<ChiTietPhieuNhapDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<ChiTietPhieuNhapDTO> selectAll() {
		return null;
	}
	
	public ArrayList<ChiTietPhieuNhapDTO> selectAllById(String maPN) {
		ArrayList<ChiTietPhieuNhapDTO> arrCTPN = new ArrayList<ChiTietPhieuNhapDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from ctpn where maPN = ?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPN);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
				ctpn.setSoLuong(rs.getInt("soLuong"));
				ctpn.setGiaNhap(rs.getDouble("giaNhap"));
				ctpn.setMaPN(rs.getString("maPN"));
				ctpn.setMaPBSP(rs.getString("maPBSP"));
				
				arrCTPN.add(ctpn);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		return arrCTPN;
	}

	@Override
	public ChiTietPhieuNhapDTO selectById(String maPN) {
		return null;
	}

	@Override
	public int insert(ChiTietPhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(ChiTietPhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChiTietPhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<ChiTietPhieuNhapDTO> getThongTinCTPN(String maPN){
		ArrayList<ChiTietPhieuNhapDTO> arrCTPN = new ArrayList<ChiTietPhieuNhapDTO>();
		try {
			jdbc.openConnection();
			
			String query = "select soluong, gianhap, mapbsp from ctpn where mapn=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPN);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO();
				
				ctpn.setSoLuong(rs.getInt("soLuong"));
				ctpn.setGiaNhap(rs.getDouble("giaNhap"));
				ctpn.setMaPBSP(rs.getString("maPBSP"));
			
				
				arrCTPN.add(ctpn);
			}
			
			ps.close();
			rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrCTPN;
	}
}
