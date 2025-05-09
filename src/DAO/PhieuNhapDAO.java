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
			
			rs.close();
			
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
	public int insert(PhieuNhapDTO pn) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "insert into phieunhap (maPN,ngayTao,tongTien,trangThai,maNV,maKho,maNCC) values (?,?,?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, pn.getMaPN());
			ps.setDate(2, pn.getNgayTao());
			ps.setDouble(3, pn.getTongTien());
			ps.setString(4, pn.getTrangThai());
			ps.setString(5, pn.getMaNV());
			ps.setString(6, pn.getMaKho());
			ps.setString(7, pn.getMaNCC());
			
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
	public int delete(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(PhieuNhapDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int updateTrangThai(String maPN, String trangThai) {
		int result=0;
		
		try {
			jdbc.openConnection();
			
			String query = "update phieunhap set trangThai=? where maPN=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, trangThai);
			ps.setString(2, maPN);
			
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
	
	

	public ArrayList<PhieuNhapDTO> getThongTinPhieuNhap(String maPN){
		ArrayList<PhieuNhapDTO> arrThongTinPN = new ArrayList<PhieuNhapDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select mapn, manv, makho, mancc, trangThai from phieunhap where mapn=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maPN);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PhieuNhapDTO pn = new PhieuNhapDTO();
				
				pn.setMaPN(rs.getString("maPN"));
				pn.setMaNV(rs.getString("maNV"));
				pn.setMaKho(rs.getString("maKho"));
				pn.setMaNCC(rs.getString("maNCC"));

				arrThongTinPN.add(pn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrThongTinPN;
	}
	
	public String getTrangThai(String mapn) {
		String trangThai = "";
		log("mapn=" + mapn);
		try {
			jdbc.openConnection();
			
			String query = "select trangThai from phieunhap where mapn=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, mapn);
			
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
