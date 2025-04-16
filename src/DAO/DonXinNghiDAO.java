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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DonXinNghiDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(DonXinNghiDTO t) {
		// TODO Auto-generated method stub
		return 0;
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
