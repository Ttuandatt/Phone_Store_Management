package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietPhieuNhapDTO;
import DTO.PhienBanSanPhamDTO;
import Database.JDBCConnection;

public class PhienBanSanPhamDAO implements DAOInterface<PhienBanSanPhamDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<PhienBanSanPhamDTO> selectAll() {
		ArrayList<PhienBanSanPhamDTO> arrPBSP = new ArrayList<PhienBanSanPhamDTO>();

		try {
			jdbc.openConnection();

			String query = "select * from pbsp";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDTO();
				pbsp.setMaPBSP(rs.getString("maPBSP"));
				pbsp.setMauSac(rs.getString("mauSac"));
				pbsp.setRam(rs.getString("ram"));
				pbsp.setRom(rs.getString("rom"));
				pbsp.setGiaBan(rs.getDouble("giaBan"));
				pbsp.setSoLuong(rs.getInt("soLuong"));
				pbsp.setTrangThai(rs.getString("trangThai"));
				pbsp.setMaSP(rs.getString("maSP"));
				
				arrPBSP.add(pbsp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}
		return arrPBSP;
	}

	@Override
	public PhienBanSanPhamDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(PhienBanSanPhamDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(PhienBanSanPhamDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhienBanSanPhamDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ArrayList<PhienBanSanPhamDTO> getThongTinPBSP(String maPBSP){
		ArrayList<PhienBanSanPhamDTO> thongTinPBSP = new ArrayList<PhienBanSanPhamDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select mausac, ram, rom, masp from pbsp where mapbsp=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPBSP);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PhienBanSanPhamDTO pbsp = new PhienBanSanPhamDTO();
				
				pbsp.setMauSac(rs.getString("mauSac"));
				pbsp.setRam(rs.getString("ram"));
				pbsp.setRom(rs.getString("rom"));
				pbsp.setMaSP(rs.getString("maSP"));
				
				thongTinPBSP.add(pbsp);
			}
			
			ps.close();
			rs.close();
		
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return thongTinPBSP;
	}

}
