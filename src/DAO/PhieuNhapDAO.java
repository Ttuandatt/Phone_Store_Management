package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.PhieuNhapDTO;
import Database.JDBCConnection;

public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<PhieuNhapDTO> selectAll() {
		ArrayList<PhieuNhapDTO> arrPhieuNhap = new ArrayList<PhieuNhapDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from phieunhap";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PhieuNhapDTO pn = new PhieuNhapDTO();
				pn.setMaPN(rs.getString("maPN"));
				pn.setNgayTao(rs.getDate("ngayTao"));
				pn.setTongTien(rs.getDouble("tongTien"));
				pn.setTrangThai(rs.getString("trangThai"));
				pn.setMaNV(rs.getString("maNV"));
				pn.setMaKho(rs.getString("maKho"));
				pn.setMaNCC(rs.getString("maNCC"));
				
				arrPhieuNhap.add(pn);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrPhieuNhap;
	}

	@Override
	public PhieuNhapDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
