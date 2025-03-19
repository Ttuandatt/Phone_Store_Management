package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Database.*;
import DTO.NhanVienDTO;
public class NhanVienDAO implements DAOInterface<NhanVienDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<NhanVienDTO> selectAll() {
		ArrayList<NhanVienDTO> arrNhanVien = new ArrayList<NhanVienDTO>();
		
		try {
			
			//Thiết lập kết nối tới Database
			jdbc.openConnection();
			
			//Tạo query
			String query = "SELECT * FROM NHANVIEN";
			
			//Tạo đối tượng PreparedStatement
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			
			//Xử lý kết quả
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
	            NhanVienDTO nv = new NhanVienDTO();
	            nv.setMaNV(rs.getString("maNV"));
	            nv.setHoTen(rs.getString("hoTen"));
	            nv.setNgaySinh(rs.getDate("ngaySinh"));
	            nv.setGioiTinh(rs.getString("gioiTinh"));
	            nv.setDiaChi(rs.getString("diaChi"));
	            nv.setSoDienThoai(rs.getString("sdt"));
	            nv.setEmail(rs.getString("email"));
	            nv.setTrangThai(rs.getString("trangThai"));
	            nv.setChucVu(rs.getString("maCV"));
	            nv.setNoiLamViec(rs.getString("noiLamViec"));
	            nv.setMatKhau(rs.getString("matKhau"));
	            nv.setHinhAnh(rs.getBytes("hinhAnh")); 	            // Xử lý ảnh (BLOB)


	            // Thêm vào danh sách
	            arrNhanVien.add(nv);	
			}
			//Đóng tài nguyên
			rs.close();
			ps.close();		
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			//Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		return arrNhanVien;
	}
	@Override
	public NhanVienDTO selectById(String maNV) {
		NhanVienDTO nv = new NhanVienDTO();
		
		try {
			
			jdbc.openConnection();
			
			String query = "select * from nhanvien where maNV=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				nv.setMaNV(rs.getString("maNV"));
	            nv.setHoTen(rs.getString("hoTen"));
	            nv.setNgaySinh(rs.getDate("ngaySinh"));
	            nv.setGioiTinh(rs.getString("gioiTinh"));
	            nv.setDiaChi(rs.getString("diaChi"));
	            nv.setSoDienThoai(rs.getString("sdt"));
	            nv.setEmail(rs.getString("email"));
	            nv.setHinhAnh(rs.getBytes("hinhAnh")); 	            // Xử lý ảnh (BLOB)
	            nv.setMatKhau(rs.getString("matKhau"));
	            nv.setChucVu(rs.getString("maCV"));
	            nv.setNoiLamViec(rs.getString("noiLamViec"));
	            nv.setTrangThai(rs.getString("trangThai"));

			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			//Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		return nv;
	}

	@Override
	public int insert(NhanVienDTO nv) {
		int result = 0;
		try {
			
			jdbc.openConnection();
			
			String query = "INSERT INTO NHANVIEN VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, nv.getMaNV());
			ps.setString(2, nv.getHoTen());
			ps.setDate(3, nv.getNgaySinh());
			ps.setString(4, nv.getGioiTinh());
			ps.setString(5, nv.getDiaChi());
			ps.setString(6, nv.getSoDienThoai());
			ps.setString(7, nv.getEmail());
			ps.setBytes(8, nv.getHinhAnh());
			ps.setString(9, nv.getMatKhau());
			ps.setString(10, nv.getTrangThai());
			if(nv.getChucVu().equalsIgnoreCase("")) {
				
			};
			ps.setString(11, nv.getChucVu());
			ps.setString(12, nv.getNoiLamViec());
			
			
			//Thực thi query
			result = ps.executeUpdate();
			
			//Đóng tài nguyên
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
	public int delete(NhanVienDTO nv) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(NhanVienDTO nv) {
		int result = 0;
		
		try {
			
			jdbc.openConnection();
			
			String query = "update nhanvien set hoTen=?, ngaySinh=?, gioiTinh=?, diaChi=?, sdt=?, email=?, hinhAnh=?, vaiTro=?, matKhau=?, trangThai=?, maCV=?, noiLamViec=?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, nv.getHoTen());
			ps.setDate(2, nv.getNgaySinh());
			ps.setString(3, nv.getGioiTinh());
			ps.setString(4, nv.getDiaChi());
			ps.setString(5, nv.getSoDienThoai());
			ps.setString(6, nv.getEmail());
			ps.setBytes(7, nv.getHinhAnh());
			ps.setString(9, nv.getEmail());
			
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return result;
	}
	
}
