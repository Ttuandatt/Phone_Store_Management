package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DTO.BangChamCongDTO;
import Database.JDBCConnection;
import java.sql.Date;
import java.sql.SQLException;

public class BangChamCongDAO implements DAOInterface<BangChamCongDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<BangChamCongDTO> selectAll() {
		ArrayList<BangChamCongDTO> arrBangChamCong = new ArrayList<BangChamCongDTO>();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from BangChamCong";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BangChamCongDTO bcc = new BangChamCongDTO();
				bcc.setMaBCC(rs.getString("maBCC"));
				bcc.setThangCC(rs.getInt("thangCC"));
				bcc.setNamCC(rs.getInt("namCC"));
				bcc.setSoNgayLam(rs.getFloat("soNgayLam"));
				bcc.setSoNgayNghiKP(rs.getFloat("soNgayNghiKP"));
				bcc.setSoNPCoLuong(rs.getFloat("soNPCoLuong"));
				bcc.setSoNPKhongLuong(rs.getFloat("soNPKhongLuong"));
                                bcc.setSoGioOTNgayThuong(rs.getFloat("soGioOTNgayThuong"));
                                bcc.setSoGioOTNgayLe(rs.getFloat("soGioOTNgayLe"));
                                bcc.setSoGioOTCN(rs.getFloat("soGioOTCN"));
				bcc.setMaNV(rs.getString("maNV"));
				
				arrBangChamCong.add(bcc);
			}
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return arrBangChamCong;
	}

	public ArrayList<BangChamCongDTO> selectByKeyWord(String tuKhoa) {
            ArrayList<BangChamCongDTO> arr_bcc = new ArrayList<BangChamCongDTO>();
		try {
                    jdbc.openConnection();
			
                    String query = "exec sp_TimDanhSachCCTheoTuKhoaKho @tuKhoa=?";
			
                    PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                    ps.setString(1, tuKhoa);

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        BangChamCongDTO bcc = new BangChamCongDTO();
                        bcc.setMaBCC(rs.getString("maBCC"));
                        bcc.setThangCC(rs.getInt("thangCC"));
                        bcc.setNamCC(rs.getInt("namCC"));
                        bcc.setSoNgayLam(rs.getFloat("soNgayLam"));
                        bcc.setSoNgayNghiKP(rs.getFloat("soNgayNghiKP"));
                        bcc.setSoNPCoLuong(rs.getFloat("soNPCoLuong"));
                        bcc.setSoNPKhongLuong(rs.getFloat("soNPKhongLuong"));
                        bcc.setSoGioOTNgayThuong(rs.getFloat("soGioOTNgayThuong"));
                        bcc.setSoGioOTNgayLe(rs.getFloat("soGioOTNgayLe"));
                        bcc.setSoGioOTCN(rs.getFloat("soGioOTCN"));
                        bcc.setMaNV(rs.getString("maNV"));
                        arr_bcc.add(bcc);
                    }

                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    jdbc.closeConnection();
                }

            return arr_bcc;
	}
	
	@Override
	public int insert(BangChamCongDTO bcc) {
		int result = 0;
		
		try {
			jdbc.openConnection();
			
			String query = "insert into bangchamcong values (?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, bcc.getMaBCC());
			ps.setInt(2, bcc.getThangCC());
			ps.setInt(3, bcc.getNamCC());
			ps.setFloat(4, bcc.getSoNgayLam());
			ps.setFloat(5, bcc.getSoNgayNghiKP());
			ps.setFloat(6, bcc.getSoNPCoLuong());
			ps.setFloat(7, bcc.getSoNPKhongLuong());
                        ps.setFloat(8, bcc.getSoGioOTNgayThuong());
                        ps.setFloat(9, bcc.getSoGioOTNgayLe());
                        ps.setFloat(10, bcc.getSoGioOTCN());
			ps.setString(11, bcc.getMaNV());
			
			result = ps.executeUpdate();
			
			ps.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSDL
			jdbc.closeConnection();
		}
		
		
		return result;
	}
	

	@Override
	public int delete(BangChamCongDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

        @Override
        public int update(BangChamCongDTO bcc) {
            int result = 0;
            try {
                jdbc.openConnection();

                String query = "exec sp_SuaChamCongKho @maBCC=?, @soNgayLam=?, @soNgayNghiKP=?, @soNPCoLuong=?, @soNPKhongLuong=?, @soGioOTNgayThuong=?, @soGioOTNgayLe=?, @soGioOTCN=?";
                PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
                ps.setString(1, bcc.getMaBCC());
                ps.setFloat(2, bcc.getSoNgayLam());
                ps.setFloat(3, bcc.getSoNgayNghiKP());
                ps.setFloat(4, bcc.getSoNPCoLuong());
                ps.setFloat(5, bcc.getSoNPKhongLuong());
                ps.setFloat(6, bcc.getSoGioOTNgayThuong());
                ps.setFloat(7, bcc.getSoGioOTNgayLe());
                ps.setFloat(8, bcc.getSoGioOTCN());

                result = ps.executeUpdate(); // Sử dụng executeUpdate() để lấy số dòng bị ảnh hưởng
            } catch (SQLException e) {
                e.printStackTrace(); // Ghi log lỗi để dễ debug
            } finally {
                jdbc.closeConnection();
            }
            return result;
        }

        public ArrayList<BangChamCongDTO> selectByTime(String thang, String nam) {
            ArrayList<BangChamCongDTO> arr_bcc = new ArrayList<>();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                jdbc.openConnection();

                StringBuilder query = new StringBuilder("SELECT * FROM BangChamCong WHERE 1=1");
                ArrayList<Object> params = new ArrayList<>();

                if (!thang.equals("Tháng")) {
                    query.append(" AND thangCC = ?");
                    params.add(Integer.parseInt(thang));
                }

                if (!nam.equals("Năm")) {
                    query.append(" AND namCC = ?");
                    params.add(Integer.parseInt(nam));
                }

                // Nếu không có điều kiện tìm kiếm, trả về rỗng hoặc toàn bộ tùy yêu cầu
                if (params.isEmpty()) {
                    return arr_bcc; // hoặc viết query toàn bộ nếu cần
                }

                ps = jdbc.getConnection().prepareStatement(query.toString());

                // Gán tham số
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    BangChamCongDTO bcc = new BangChamCongDTO();
                    bcc.setMaBCC(rs.getString("maBCC"));
                    bcc.setThangCC(rs.getInt("thangCC"));
                    bcc.setNamCC(rs.getInt("namCC"));
                    bcc.setSoNgayLam(rs.getFloat("soNgayLam"));
                    bcc.setSoNgayNghiKP(rs.getFloat("soNgayNghiKP"));
                    bcc.setSoNPCoLuong(rs.getFloat("soNPCoLuong"));
                    bcc.setSoNPKhongLuong(rs.getFloat("soNPKhongLuong"));
                    bcc.setSoGioOTNgayThuong(rs.getFloat("soGioOTNgayThuong"));
                    bcc.setSoGioOTNgayLe(rs.getFloat("soGioOTNgayLe"));
                    bcc.setSoGioOTCN(rs.getFloat("soGioOTCN"));
                    bcc.setMaNV(rs.getString("maNV"));

                    arr_bcc.add(bcc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jdbc.closeConnection();
            }

            return arr_bcc;
        }


    @Override
    public BangChamCongDTO selectById(String t) {
        BangChamCongDTO bcc = new BangChamCongDTO();
		
		try {
			jdbc.openConnection();
			
			String query = "select * from BangChamCong where maBCC = ?";
			
			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, t);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {				
                            bcc.setMaBCC(rs.getString("maBCC"));
                            bcc.setThangCC(rs.getInt("thangCC"));
                            bcc.setNamCC(rs.getInt("namCC"));
                            bcc.setSoNgayLam(rs.getFloat("soNgayLam"));
                            bcc.setSoNgayNghiKP(rs.getFloat("soNgayNghiKP"));
                            bcc.setSoNPCoLuong(rs.getFloat("soNPCoLuong"));
                            bcc.setSoNPKhongLuong(rs.getFloat("soNPKhongLuong"));
                            bcc.setSoGioOTNgayThuong(rs.getFloat("soGioOTNgayThuong"));
                            bcc.setSoGioOTNgayLe(rs.getFloat("soGioOTNgayLe"));
                            bcc.setSoGioOTCN(rs.getFloat("soGioOTCN"));
                            bcc.setMaNV(rs.getString("maNV"));
			}
			
			ps.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally {
			jdbc.closeConnection();
		}
		
		return bcc;
    }

	// get bang cham cong by nhan vien va thang nam
	public BangChamCongDTO getBangChamCongByNVAndThangNam(String maNV, int thang, int nam) {
		BangChamCongDTO bcc = new BangChamCongDTO();
		try {
			jdbc.openConnection();

			String query = "select * from bangchamcong where maNV=? and thangCC=? and namCC=?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, maNV);
			ps.setInt(2, thang);
			ps.setInt(3, nam);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bcc.setMaBCC(rs.getString("maBCC"));
				bcc.setThangCC(rs.getInt("thangCC"));
				bcc.setNamCC(rs.getInt("namCC"));
				bcc.setSoNgayLam(rs.getFloat("soNgayLam"));
				bcc.setSoNgayNghiKP(rs.getFloat("soNgayNghiKP"));
				bcc.setSoNPCoLuong(rs.getFloat("soNPCoLuong"));
				bcc.setSoNPKhongLuong(rs.getFloat("soNPKhongLuong"));
				bcc.setSoGioOTNgayThuong(rs.getFloat("soGioOTNgayThuong"));
				bcc.setSoGioOTNgayLe(rs.getFloat("soGioOTNgayLe"));
				bcc.setSoGioOTCN(rs.getFloat("soGioOTCN"));
				bcc.setMaNV(rs.getString("maNV"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}

		return bcc;
	}

}