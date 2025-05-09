package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.PhieuXuatDTO;
import Database.JDBCConnection;

public class PhieuXuatDAO implements DAOInterface<PhieuXuatDTO>{
	JDBCConnection jdbc = new JDBCConnection();
	@Override
	public ArrayList<PhieuXuatDTO> selectAll() {
		ArrayList<PhieuXuatDTO> arrPhieuXuat = new ArrayList<PhieuXuatDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from PhieuXuat";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PhieuXuatDTO px = new PhieuXuatDTO();
				px.setMaPX(rs.getString("mapx"));
				px.setNgayTao(rs.getDate("ngayTao"));
				px.setDiaChi(rs.getString("diaChi"));
				px.setTongTien(rs.getDouble("tongTien"));
				px.setHttt(rs.getString("httt"));
				px.setTrangThai(rs.getString("trangThai"));
				px.setMaNV(rs.getString("maNV"));
				px.setMaKho(rs.getString("maKho"));
				px.setMaKH(rs.getString("maKH"));
				
				arrPhieuXuat.add(px);
			}
			
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrPhieuXuat;
	}

	@Override
	public PhieuXuatDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(PhieuXuatDTO px) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "insert into phieuxuat(maPX,ngayTao,diaChi,tongTien,httt,trangThai,maNV,maKho,maKH) values(?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, px.getMaPX());
			log("maPX="+px.getMaPX());
			ps.setDate(2, px.getNgayTao());
			log("ngayTao="+px.getNgayTao());
			ps.setString(3, px.getDiaChi());
			log("diaChi="+px.getDiaChi());
			ps.setDouble(4, px.getTongTien());
			log("tongTien="+px.getTongTien());
			ps.setString(5, px.getHttt());
			log("httt="+px.getHttt());
			ps.setString(6, px.getTrangThai());
			log("trangThai="+px.getTrangThai());
			ps.setString(7, px.getMaNV());
			log("maNV="+px.getMaNV());
			ps.setString(8, px.getMaKho());
			log("maKho="+px.getMaKho());
			ps.setString(9, px.getMaKH());
			log("maKH="+px.getMaKH());
			
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
	public int delete(PhieuXuatDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhieuXuatDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int updateTrangThai(String mapx, String trangThai) {
		int result=0;
		
		try {
			jdbc.openConnection();
			
			String query = "update PhieuXuat set trangThai=? where mapx=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, trangThai);
			ps.setString(2, mapx);
			
			result = ps.executeUpdate();
			log("result="+result);
			ps.close();
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return result;
	}
	
	

	public ArrayList<PhieuXuatDTO> getThongTinPhieuXuat(String mapx){
		ArrayList<PhieuXuatDTO> arrThongTinpx = new ArrayList<PhieuXuatDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select mapx, manv, makho, maKH, trangThai from PhieuXuat where mapx=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, mapx);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhieuXuatDTO px = new PhieuXuatDTO();
				
				px.setMaPX(rs.getString("maPX"));
				px.setMaNV(rs.getString("maNV"));
				px.setMaKho(rs.getString("maKho"));
				px.setMaKH(rs.getString("maKH"));

				arrThongTinpx.add(px);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrThongTinpx;
	}
	
	public String getTrangThai(String mapx) {
		String trangThai = "";
		log("mapx=" + mapx);
		try {
			jdbc.openConnection();
			
			String query = "select trangThai from PhieuXuat where mapx=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, mapx);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
			    trangThai = rs.getString("trangThai"); 
			    log("trangThai=" + trangThai);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return trangThai;
	}
	
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}

}
