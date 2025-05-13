package DAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import DTO.LSChinhSuaDTO;
import Database.JDBCConnection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LSChinhSuaDAO implements DAOInterface<LSChinhSuaDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<LSChinhSuaDTO> selectAll() {
            ArrayList<LSChinhSuaDTO> arrLS = new ArrayList<>();
            try {
                jdbc.openConnection();
                String query = "SELECT * FROM LSChinhSua";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    LSChinhSuaDTO ls = new LSChinhSuaDTO();
                    ls.setMaNguoiBiChinhSua(rs.getString("maNguoiBiChinhSua"));
                    ls.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime()); // nếu kiểu dữ liệu là DATETIME
                    ls.setMaNguoiChinhSua(rs.getString("maNguoiChinhSua"));
                    ls.setGiaTriCu(rs.getString("giaTriCu"));
                    ls.setGiaTriMoi(rs.getString("giaTriMoi"));
                    arrLS.add(ls);
                }

                rs.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace(); // dùng e.printStackTrace() thay vì chỉ gọi e.getMessage()
            } finally {
                jdbc.closeConnection();
            }
            return arrLS;
	}

	@Override
	public LSChinhSuaDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(LSChinhSuaDTO lscs) {
		int result = 0;

		try {
			jdbc.openConnection();

			String query = "INSERT INTO LSCHINHSUA (maNguoiChinhSua, maNguoiBiChinhSua, thoiGian, giaTriCu, giaTriMoi) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, lscs.getMaNguoiChinhSua());
			ps.setString(2, lscs.getMaNguoiBiChinhSua());
			ps.setTimestamp(3, Timestamp.valueOf(lscs.getThoiGian()));
			ps.setNString(4, lscs.getGiaTriCu());
                        ps.setNString(5, lscs.getGiaTriMoi());	
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
	public int delete(LSChinhSuaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(LSChinhSuaDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

    public ArrayList<LSChinhSuaDTO> selectByMaNV(String maNV) {
        ArrayList<LSChinhSuaDTO> arrLS = new ArrayList<>();
        try {
            jdbc.openConnection();
            String query = "SELECT * FROM LSChinhSua WHERE maNguoiBiChinhSua = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maNV);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LSChinhSuaDTO ls = new LSChinhSuaDTO();
                ls.setMaNguoiBiChinhSua(rs.getString("maNguoiBiChinhSua"));
                ls.setThoiGian(rs.getTimestamp("thoiGian").toLocalDateTime());
                ls.setMaNguoiChinhSua(rs.getString("maNguoiChinhSua"));
                ls.setGiaTriCu(rs.getString("giaTriCu"));
                ls.setGiaTriMoi(rs.getString("giaTriMoi"));
                arrLS.add(ls);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace(); // dùng e.printStackTrace() thay vì chỉ gọi e.getMessage()
        } finally {
            jdbc.closeConnection();
        }
        return arrLS;
    }

}
