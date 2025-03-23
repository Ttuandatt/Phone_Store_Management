package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.KhoDTO;
import Database.JDBCConnection;

public class KhoDAO implements DAOInterface<KhoDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	
	@Override
	public ArrayList<KhoDTO> selectAll() {
		ArrayList<KhoDTO> arrKho = new ArrayList<KhoDTO>();
		
		try {
			
			jdbc.openConnection();
			
			String query = "select * from kho";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				KhoDTO kho = new KhoDTO();
				kho.setMaKho(rs.getString("maKho"));
				kho.setTenKho(rs.getString("tenKho"));
				kho.setDiaChi(rs.getString("diaChi"));
				kho.setSdt(rs.getString("sdt"));
				
				arrKho.add(kho);
			}
			
			//Đóng tài nguyên
			rs.close();
			ps.close();	
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrKho;
	}

	@Override
	public KhoDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdByName(String tenKho) {
		String maKho = "";
		
		try {

			jdbc.openConnection();

			String query = "select maKho from kho where tenKho = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, tenKho);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				maKho = rs.getString("maKho");
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSD
			jdbc.closeConnection();
		}
		
		return maKho;
	}
	
	@Override
	public int insert(KhoDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(KhoDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(KhoDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
