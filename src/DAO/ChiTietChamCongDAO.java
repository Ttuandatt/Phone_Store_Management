package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietChamCongDTO;
import Database.JDBCConnection;
import java.time.LocalDate;
import java.sql.Date;

public class ChiTietChamCongDAO implements DAOInterface<ChiTietChamCongDTO>{
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<ChiTietChamCongDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChiTietChamCongDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChiTietChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public ArrayList<ChiTietChamCongDTO> getThongTinTangCa(String maBCC) {
		ArrayList<ChiTietChamCongDTO> arr = new ArrayList<ChiTietChamCongDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select ngayTao, loaiChamCong, soGioOT from chitietchamcong where mabcc=? and soGioOT>0";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maBCC);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ChiTietChamCongDTO ctcc = new ChiTietChamCongDTO();
				Date sqlDate = rs.getDate("ngayTao");
                                LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
				ctcc.setNgayTao(localDate);
				ctcc.setLoaiChamCong(rs.getString("loaiChamCong"));
				ctcc.setSoGioOT(rs.getFloat("soGioOT"));
				
				arr.add(ctcc);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arr;
	}
	
	public ArrayList<ChiTietChamCongDTO> getThongTinNgayNghi(String maBCC){
 		ArrayList<ChiTietChamCongDTO> arr = new ArrayList<ChiTietChamCongDTO>();
 		try {
 			jdbc.openConnection();
 			
 			String query = "select ngayChamCong, loaiChamCong, chiTiet from chitietchamcong where maBCC=? and loaiChamCong like N'Nghỉ%'";
 			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
 			ps.setString(1, maBCC);
 			
 			ResultSet rs = ps.executeQuery();
 			while(rs.next()) {
 				ChiTietChamCongDTO ctcc = new ChiTietChamCongDTO();
 				ctcc.setNgayTao(rs.getDate("ngayChamCong").toLocalDate());
 				ctcc.setLoaiChamCong(rs.getString("loaiChamCong"));
 				ctcc.setChiTiet(rs.getString("chiTiet"));
 				
 				arr.add(ctcc);
 			}
 		}catch (Exception e) {
 			e.printStackTrace();
 			e.getMessage();
 		}finally {
 			jdbc.closeConnection();
 		}
 		return arr;
 	}
	
	// Tìm theo mã bảng cc
    public ChiTietChamCongDTO GetChiTietChamCongTheoMaCT(String mact) {
    ChiTietChamCongDTO ct = null;
    try {
        jdbc.openConnection();
        String query = "exec sp_LayChiTietChamCongTheoMaCTKho @maCTCC = ?";
        PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
        ps.setString(1, mact);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ct = new ChiTietChamCongDTO();
            ct.setMaCTCC(rs.getString("maCTCC"));
            ct.setNgayTao(rs.getDate("ngayTao").toLocalDate());
            ct.setLoaiChamCong(rs.getString("loaiChamCong"));
            ct.setChiTiet(rs.getString("chiTiet"));
            ct.setSoGioOT(rs.getFloat("soGioOT"));
        }

            rs.close();
            ps.close();
        } catch (SQLException e) {
        } finally {
            jdbc.closeConnection();
        }
        return ct;
    }
    
    public ArrayList<ChiTietChamCongDTO> getChiTietCCTheoMaCC(String macc) {
        ArrayList<ChiTietChamCongDTO> arrCT = new ArrayList<ChiTietChamCongDTO>();
        try {
            jdbc.openConnection();
            String query = "EXEC sp_LayChiTietChamCongTheoMaCCKho @maCC = ?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, macc);
            
            ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    ChiTietChamCongDTO ct = new ChiTietChamCongDTO();
                    ct.setMaCTCC(rs.getString("maCTCC"));
                    ct.setNgayTao(rs.getDate("ngayTao").toLocalDate());
                    ct.setLoaiChamCong(rs.getString("loaiChamCong"));
                    ct.setChiTiet(rs.getString("chiTiet"));
                    ct.setMaBCC(rs.getString("maBCC"));
                    ct.setSoGioOT(rs.getFloat("soGioOT"));
                    arrCT.add(ct);
                }
        }catch (SQLException e) {
            e.getMessage();
        } finally {
            // Đóng kết nối CSDL
            jdbc.closeConnection();
        }
        return arrCT;
        
    }
    
    public int insert1(ChiTietChamCongDTO t) {
        int result = 0;
        try {
            jdbc.openConnection();

            String query = "exec sp_ThemChiTietChamCongKho @maCTCC=?, @ngayTao=?, @loaiChamCong=?, @chiTiet=?, @maBCC=?, @soGioOT=?";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, t.getMaCTCC());
            ps.setDate(2, Date.valueOf(t.getNgayTao()));
            ps.setString(3, t.getLoaiChamCong());
            ps.setString(4, t.getChiTiet());
            ps.setString(5, t.getMaBCC());
            ps.setFloat(6, t.getSoGioOT());

            result = ps.executeUpdate(); // Sử dụng executeUpdate() để lấy số dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để dễ debug
        } finally {
            jdbc.closeConnection();
        }
        return result;
    }
    
    public int delete1(String maCTCC) {
        int result = 0;
        try {
            jdbc.openConnection();

            String query = "exec sp_XoaChiTietChamCongKho @maCTCC=??";
            PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
            ps.setString(1, maCTCC);

            result = ps.executeUpdate(); // Số dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để kiểm tra
        } finally {
            jdbc.closeConnection();
        }

        return result;
    }


    public int deleteByMaCT(String mact) {
        int result = 0;
            try {
                jdbc.openConnection();
                String query = "EXEC sp_LayChiTietChamCongTheoMaCTKho @maCTCC = ?;";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, mact);

                result = ps.executeUpdate(); // Sử dụng executeUpdate() để lấy số dòng bị ảnh hưởng
            } catch (SQLException e) {
                e.printStackTrace(); // Ghi log lỗi để dễ debug
            } finally {
                jdbc.closeConnection();
            }
        return result;
    }

}
