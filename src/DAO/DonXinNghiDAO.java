package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.DonXinNghiDTO;
import Database.JDBCConnection;

public class DonXinNghiDAO implements DAOInterface<DonXinNghiDTO>{

	JDBCConnection jdbc = new JDBCConnection();
	
	@Override
	public ArrayList<DonXinNghiDTO> selectAll() {
		ArrayList<DonXinNghiDTO> arr = new ArrayList<DonXinNghiDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from donxinnghi";
			PreparedStatement ps =  jdbc.getConnection().prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DonXinNghiDTO dxn = new DonXinNghiDTO();
				dxn.setMaDon(rs.getString("maDon"));
				log("maDon="+rs.getString("maDon"));
				dxn.setNgayTao(rs.getDate("ngayTao"));
				dxn.setNgayBD(rs.getDate("ngayBD"));
				dxn.setNgayKT(rs.getDate("ngayKT"));
				dxn.setLyDo(rs.getString("lyDo"));
				dxn.setNgayDuyet(rs.getDate("ngayDuyet"));
				dxn.setTrangThai(rs.getString("trangThai"));
				dxn.setMaNV(rs.getString("maNV"));
				dxn.setMaNguoiDuyet(rs.getString("maNguoiDuyet"));
				
				arr.add(dxn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			
		}
		
		return arr;
	}

	public ArrayList<DonXinNghiDTO> selectDonXinNghiDaTao(String maNV) {
		ArrayList<DonXinNghiDTO> arr = new ArrayList<DonXinNghiDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from donxinnghi where maNV=?";
			PreparedStatement ps =  jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DonXinNghiDTO dxn = new DonXinNghiDTO();
				dxn.setMaDon(rs.getString("maDon"));
				log("maDon="+rs.getString("maDon"));
				dxn.setNgayTao(rs.getDate("ngayTao"));
				dxn.setNgayBD(rs.getDate("ngayBD"));
				dxn.setNgayKT(rs.getDate("ngayKT"));
				dxn.setLyDo(rs.getString("lyDo"));
				dxn.setNgayDuyet(rs.getDate("ngayDuyet"));
				dxn.setTrangThai(rs.getString("trangThai"));
				dxn.setMaNV(rs.getString("maNV"));
				dxn.setMaNguoiDuyet(rs.getString("maNguoiDuyet"));
				
				arr.add(dxn);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			
		}
		
		return arr;
	}
	
	@Override
	public DonXinNghiDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(DonXinNghiDTO dxn) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "insert into donxinnghi(maDon, ngayTao, ngayBD, ngayKT, lyDo, ngayDuyet, trangThai, maNV, maNguoiDuyet) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, dxn.getMaDon());
			ps.setDate(2, dxn.getNgayTao());
			ps.setDate(3, dxn.getNgayBD());
			ps.setDate(4, dxn.getNgayKT());
			ps.setString(5, dxn.getLyDo());
			ps.setDate(6, dxn.getNgayDuyet());
			ps.setString(7, dxn.getTrangThai());			
			ps.setString(8, dxn.getMaNV());
			ps.setString(9, dxn.getMaNguoiDuyet());
		
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

	public int demSoLanXinNghiConLai(String maNV) {
		int soLanConLai = 0;
		log("maNV="+maNV);
		try {
			jdbc.openConnection();
			
			String query = "select count(*) from donxinnghi where maNV=?";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				soLanConLai = rs.getInt(1);  // lấy giá trị count(*)
			}
			log("soLanConLai="+soLanConLai);
			ps.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		
		return soLanConLai;
	}
	
	@Override
	public int delete(DonXinNghiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(DonXinNghiDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int updateTrangThai(String maDon, String trangThai, String maNguoiDuyet) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "update donxinnghi set trangThai=?, maNguoiDuyet=? where maDon=?";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, trangThai);
			ps.setString(2, maNguoiDuyet);
			ps.setString(3, maDon);
			
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return result;
	}
	
	public ArrayList<DonXinNghiDTO> getThongTinNgayNghi(String maNV){
		ArrayList<DonXinNghiDTO> arr = new ArrayList<DonXinNghiDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select ngayBD, ngayKT, lyDo from donxinnghi where trangThai = N'Đã duyệt' and maNV=?";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			log("maNV="+maNV);
			ps.setString(1, maNV);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DonXinNghiDTO dxn = new DonXinNghiDTO();
				dxn.setNgayBD(rs.getDate("ngayBD"));
				log("ngayBD=" + rs.getDate("ngayBD"));
				dxn.setNgayKT(rs.getDate("ngayKT"));
				log("ngayKT=" + rs.getDate("ngayKT"));
				dxn.setLyDo(rs.getString("lyDo"));
				log("lyDo=" + rs.getString("lyDo"));

				arr.add(dxn);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arr;
	}

	
	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
	
	
}
