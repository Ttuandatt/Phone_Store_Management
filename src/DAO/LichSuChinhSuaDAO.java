package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.LichSuChinhSuaDTO;
import Database.JDBCConnection;

public class LichSuChinhSuaDAO implements DAOInterface<LichSuChinhSuaDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<LichSuChinhSuaDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LichSuChinhSuaDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(LichSuChinhSuaDTO lscs) {
		int result = 0;

		try {
			jdbc.openConnection();

			String query = "INSERT INTO LSCHINHSUA (maNguoiChinhSua, maNguoiBiChinhSua, thoiGian, noiDungChinhSua) VALUES (?, ?, ?, ?)";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, lscs.getMaNguoiChinhSua());
			ps.setString(2, lscs.getMaNguoiBiChinhSua());
			ps.setDate(3, lscs.getThoiGian()); // Đảm bảo thoiGian là kiểu java.sql.Date
			ps.setNString(4, lscs.getNoiDungChinhSua());
			
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}

		return result;
	}

	@Override
	public int delete(LichSuChinhSuaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LichSuChinhSuaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
