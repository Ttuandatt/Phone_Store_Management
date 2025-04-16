package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Database.JDBCConnection;

public class DangNhapDAO {
	JDBCConnection jdbc = new JDBCConnection();
	
	public int kiemTraDangNhap(String maNV, String matKhau) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			//kiểm tra xem có nhân viên này tồn tại hay không
			String query = "select 1 from nhanvien where manv=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			
			ResultSet rsKiemTraNhanVien = ps.executeQuery();
			log("rsKiemTra="+rsKiemTraNhanVien);
			
			//nếu tồn tại nhân viên này, kiểm tra xem có đúng mật khẩu không
			if(rsKiemTraNhanVien.next()) {
				String query2 = "select 1 from nhanvien where matkhau=? and maNV=?";
				
				PreparedStatement ps2 = jdbc.getConnection().prepareStatement(query2);
				ps2.setString(1, matKhau);
				ps2.setString(2, maNV);
				
				ResultSet rsKiemTraMatKhau = ps2.executeQuery();
				log("rsKiemTraMatKhau="+rsKiemTraMatKhau);
				
				//nếu đúng mật khẩu
				if(rsKiemTraMatKhau.next()) {
					result=rsKiemTraMatKhau.getRow();	//tồn tại nhân viên & đúng mật khẩu
				}else {
					result=2;	//tồn tại nhân viên nhưng sai mật khẩu
				}
				
				rsKiemTraMatKhau.close();
				ps2.close();
			}else {
				result=3;	//không tồn tại nhân viên
			}
			
			rsKiemTraNhanVien.close();
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		log("result="+result);
		return result;
	}
	
	
	//hàm hiển thị thông tin dòng code
	public static void log(String message) {
	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
	    System.out.println(element.getClassName() + " | method: " 
	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
	}
	
}
